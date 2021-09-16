package bifast.outbound.ficredittransfer;

import java.net.SocketTimeoutException;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.corebank.CBFITransferRequestPojo;
import bifast.outbound.corebank.CBFITransferResponsePojo;
import bifast.outbound.corebank.CBTransactionFailureProcessor;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.FaultProcessor;
import bifast.outbound.processor.ValidateInputProcessor;
import bifast.outbound.report.InitSettlementRequestProcessor;
import bifast.outbound.report.RequestPojo;
import bifast.outbound.report.ResponsePojo;
import bifast.outbound.report.SettlementEnquiryResultProcessor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

@Component
public class FICreditTransferRoute extends RouteBuilder {

	@Autowired
	private FICTCorebankRequestProcessor fiCTCorebankRequestProcessor;
	@Autowired
	private FICreditTransferRequestProcessor fiCrdtTransferRequestProcessor;
	@Autowired
	private FICreditTransferResponseProcessor fiCrdtTransferResponseProcessor;
	@Autowired
	private FaultProcessor faultProcessor;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	@Autowired
	private InitSettlementRequestProcessor initSettlementRequest;
	@Autowired
	private InitPaymentStatusRequestFICTProcessor initPaymentStatusRequestProcessor;
	@Autowired
	private ValidateInputProcessor validateRequest;
	@Autowired
	private SaveFICTTablesProcessor saveFICTTableProcessor;
	@Autowired
	private SettlementEnquiryResultProcessor settlementEnquiryResultProcessor;
	@Autowired
	private CBTransactionFailureProcessor corebankTransactionFailureProcessor;

	JacksonDataFormat cbFITransferRequestJDF = new JacksonDataFormat(CBFITransferRequestPojo.class);
	JacksonDataFormat cbFITransferResponseJDF = new JacksonDataFormat(CBFITransferResponsePojo.class);
	JacksonDataFormat businessMessageJDF = new JacksonDataFormat(BusinessMessage.class);
	JacksonDataFormat SettlementRequestJDF = new JacksonDataFormat(RequestPojo.class);
	JacksonDataFormat SettlementResponseJDF = new JacksonDataFormat(ResponsePojo.class);
	JacksonDataFormat chnlResponseJDF = new JacksonDataFormat(ChannelResponseWrapper.class);

	private void configureJsonDataFormat() {

		businessMessageJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		businessMessageJDF.setInclude("NON_NULL");
		businessMessageJDF.setInclude("NON_EMPTY");
		businessMessageJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		businessMessageJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		SettlementRequestJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		SettlementRequestJDF.setInclude("NON_NULL");
		SettlementRequestJDF.setInclude("NON_EMPTY");
		SettlementRequestJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		
		SettlementResponseJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		SettlementResponseJDF.setInclude("NON_NULL");
		SettlementResponseJDF.setInclude("NON_EMPTY");
		SettlementResponseJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		
		cbFITransferResponseJDF.setInclude("NON_NULL");
		chnlResponseJDF.setInclude("NON_EMPTY");
		SettlementResponseJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		cbFITransferResponseJDF.setInclude("NON_NULL");
		cbFITransferResponseJDF.setInclude("NON_EMPTY");
		cbFITransferResponseJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

	}
	
	@Override
	public void configure() throws Exception {

		configureJsonDataFormat();

        onException(Exception.class).routeId("FICT Exception Handler")
	    	.log("Fault di FICT Excp, ${header.hdr_errorlocation}")
	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
			.process(faultProcessor)
			.to("sql:update outbound_message set resp_status = 'ERROR-KM', "
					+ " error_msg= :#${body.faultResponse.reason} "
					+ "where id= :#${header.hdr_idtable}  ")
			.marshal(chnlResponseJDF)
			.removeHeaders("fict_*")
			.removeHeaders("req_*")
	    	.handled(true)
	  	;

		// Untuk Proses Credit Transfer Request

		from("direct:fictreq2").routeId("fictreq2")

			.log("FI Credit Transfer")
			.process(validateRequest)
			.process(fiCrdtTransferRequestProcessor)
			.setHeader("fict_objreqbi", simple("${body}"))
			.marshal(businessMessageJDF)

			.to("seda:encryptFICTbody")
			.to("seda:saveFICTtables")

			// TODO call ke CB dulu
			.process(fiCTCorebankRequestProcessor)
			.marshal(cbFITransferRequestJDF)
			.to("direct:callcb")
			.unmarshal(cbFITransferResponseJDF)

			// evalutate reponse cb
			.choice()
				.when().simple("${body.transactionStatus} == 'SUCCESS'")
					.to("seda:fict_corebank_accpt")
				.when().simple("${body.transactionStatus} == 'FAILED'")
					.to("seda:fict_corebank_reject")
			.end()

			.removeHeaders("fict_*")
		;

		from("seda:fict_corebank_reject")
			.log("FI Corebanking failure")
			.process(corebankTransactionFailureProcessor)
			.to("sql:update outbound_message set resp_status = 'RJCT-CB' "
					+ "where id= :#${header.hdr_idtable}  ")
			.marshal(chnlResponseJDF)
		;
		
		from("seda:fict_corebank_accpt")
			.log("Akan call FI CT")
			.setBody(simple("${header.fict_objreqbi}"))
			.marshal(businessMessageJDF)

			// kirim ke CI-HUB
			.setHeader("fict_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.doTry()
				.log("Submit CT no: ${header.fict_objreqbi.appHdr.bizMsgIdr}")
				.log("${body}")
				.setHeader("hdr_errorlocation", constant("FICT/call-CIHUB"))
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{bifast.ciconnector-url}}?"
						+ "socketTimeout={{bifast.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)
				
				.log("Hasil Mock: ${body}")
				
			.doCatch(SocketTimeoutException.class)     // klo timeout maka kirim payment status
    			.log(LoggingLevel.ERROR, "Timeout process Credit Transfer ref:${header.hdr_channelRequest.orignReffId}")
    			.to("sql:update outbound_message set resp_status = 'TIMEOUT', "
    					+ " error_msg= 'Timeout' "
    					+ "where id= :#${header.hdr_idtable}  ")
    			.setBody(constant(null))
    		.end()
    		
    		// kalo body==null berarti harus settlement
    		.choice()
    			.when().simple("${body} == null")
    				.log("Cari settlement untuk : ${header.fict_objreqbi.appHdr.bizMsgIdr}")
    				.setBody(simple("${header.fict_objreqbi}"))
    				.process(initSettlementRequest)
    				.marshal(SettlementRequestJDF)
    				.log("Settlement request data: ${body}")
    				.doTry()
						.enrich("http://localhost:9001/services/api/enquiry?"
								+ "bridgeEndpoint=true",
								enrichmentAggregator)
						.convertBodyTo(String.class)
		    			.log("Settlement: ${body}")
						.unmarshal(SettlementResponseJDF)
		    			.process(settlementEnquiryResultProcessor)
			    		.marshal(businessMessageJDF)	    			
    				.doCatch(Exception.class)
    					.log("Error call settlement")
    	    			.setBody(constant(null))
    				.end()
    				
			.endChoice()
			.end()
			
			.log("2: ${body}")

	    	// kalo masih body=null berarti harus Payment Status
			.choice()
				.when().simple("${body} == null")
					.log("tidak ada settlement, harus PS")
	    			.setBody(simple("${header.fict_objreqbi}"))
	    			.process(initPaymentStatusRequestProcessor)
	    			.to("direct:paymentstatus")
			.end()
				
			.log("3: ${body}")

			.choice()
				.when().simple("${body} != null")
					.log("Proses encryption")
					.log("${body}")
					.setHeader("fict_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
					.to("seda:encryptFICTbody")

					.unmarshal(businessMessageJDF)
					.setHeader("fict_objresponsebi", simple("${body}"))	
					.marshal(businessMessageJDF)
					.log("After encription: ${body}")
					
				.otherwise()
					.log("Masih null hasil PS nya")
					.setHeader("fict_encrMessage", constant(null))	
			    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(504))
			.end()
				
	
			// prepare untuk response ke channel
			.unmarshal(businessMessageJDF)
			.process(fiCrdtTransferResponseProcessor)
			.marshal(chnlResponseJDF)

			.setHeader("fict_channelResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.to("seda:saveFICTtables?exchangePattern=InOnly")
			
			.removeHeaders("fict_*")
		;


		from("seda:encryptFICTbody")
			.setHeader("fict_tmp", simple("${body}"))
			.marshal().zipDeflater()
			.marshal().base64()
			.setHeader("fict_encrMessage", simple("${body}"))
			.setBody(simple("${header.fict_tmp}"))
		;
		from("seda:saveFICTtables")
			.process(saveFICTTableProcessor)
		;

	}
}
