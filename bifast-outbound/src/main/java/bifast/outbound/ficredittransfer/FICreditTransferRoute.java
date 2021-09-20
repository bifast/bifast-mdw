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
import bifast.outbound.corebank.CBTransactionFailureProcessor;
import bifast.outbound.ficredittransfer.processor.FICTCorebankRequestProcessor;
import bifast.outbound.ficredittransfer.processor.FICreditTransferRequestProcessor;
import bifast.outbound.ficredittransfer.processor.FICreditTransferResponseProcessor;
import bifast.outbound.ficredittransfer.processor.SaveFICTTablesProcessor;
import bifast.outbound.paymentstatus.BuildPaymentStatusRequestProcessor;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.FaultProcessor;
import bifast.outbound.report.InitSettlementRequestProcessor;
import bifast.outbound.report.RequestPojo;
import bifast.outbound.report.ResponsePojo;

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
	private BuildPaymentStatusRequestProcessor initPaymentStatusRequestProcessor;
	@Autowired
	private SaveFICTTablesProcessor saveFICTTableProcessor;
	@Autowired
	private CBTransactionFailureProcessor corebankTransactionFailureProcessor;

	JacksonDataFormat cbFITransferRequestJDF = new JacksonDataFormat(CBFITransferRequestPojo.class);
	JacksonDataFormat businessMessageJDF = new JacksonDataFormat(BusinessMessage.class);
	JacksonDataFormat settlementRequestJDF = new JacksonDataFormat(RequestPojo.class);
	JacksonDataFormat settlementResponseJDF = new JacksonDataFormat(ResponsePojo.class);
	JacksonDataFormat chnlResponseJDF = new JacksonDataFormat(ChannelResponseWrapper.class);

	private void configureJsonDataFormat() {

		businessMessageJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		businessMessageJDF.setInclude("NON_NULL");
		businessMessageJDF.setInclude("NON_EMPTY");
		businessMessageJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		businessMessageJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

//		SettlementRequestJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		settlementRequestJDF.setInclude("NON_NULL");
		settlementRequestJDF.setInclude("NON_EMPTY");
		settlementRequestJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		
		settlementResponseJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		settlementResponseJDF.setInclude("NON_NULL");
		settlementResponseJDF.setInclude("NON_EMPTY");
//		SettlementResponseJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		
		cbFITransferRequestJDF.setInclude("NON_NULL");
		cbFITransferRequestJDF.setInclude("NON_EMPTY");
		cbFITransferRequestJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

		chnlResponseJDF.setInclude("NON_NULL");
		chnlResponseJDF.setInclude("NON_EMPTY");
//		chnlResponseJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

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
			.removeHeaders("hdr_*")
	    	.handled(true)
	  	;

		// Untuk Proses Credit Transfer Request

		from("direct:fictreq").routeId("fictreq")

			.log("FI Credit Transfer")
//			.process(validateRequest)
			.log("[fictreq] ChReqId:${body.orignReffId} started")

			.process(fiCrdtTransferRequestProcessor)
			.setHeader("fict_objreqbi", simple("${body}"))
			.marshal(businessMessageJDF)

			.to("seda:encryptFICTbody")
			.to("seda:saveFICTtables")

			.process(fiCTCorebankRequestProcessor)
//			.marshal(cbFITransferRequestJDF)
			.log("10: ${body}")
			.to("direct:callcb")
			.log("20: ${body}")
			.setHeader("fict_cbresponse", simple("${body.cbFITransferResponse}"))
//			.unmarshal(cbFITransferResponseJDF)

			// evalutate reponse cb
			.choice()
				.when().simple("${header.fict_cbresponse.status} == 'SUCCESS'")
					.to("seda:fict_corebank_accpt")
				.when().simple("${header.fict_cbresponse.status} == 'FAILED'")
					.process(corebankTransactionFailureProcessor)
					.to("sql:update outbound_message set resp_status = 'RJCT-CB' "
							+ "where id= :#${header.hdr_idtable}  ")
			.end()

			.removeHeaders("fict_*")
		;

		
		from("seda:fict_corebank_accpt")
			.log("Akan call FI CT")
			.setBody(simple("${header.fict_objreqbi}"))
			.setHeader("fict_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))

			// kirim ke CI-HUB
			.marshal(businessMessageJDF)
			.doTry()
				.log("Submit CT no: ${header.fict_objreqbi.appHdr.bizMsgIdr}")
				.log("${body}")
				.setHeader("hdr_errorlocation", constant("FICT/call-CIHUB"))
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{komi.ciconnector-url}}?"
						+ "socketTimeout={{komi.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)
				
				.unmarshal(businessMessageJDF)
				
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
    				.marshal(settlementRequestJDF)
    				.log("Settlement request data: ${body}")
    				
    				.doTry()
						.setHeader("HttpMethod", constant("POST"))
						.enrich("http://localhost:9001/services/api/enquiry?"
								+ "bridgeEndpoint=true",
								enrichmentAggregator)
						.convertBodyTo(String.class)
						.unmarshal(settlementResponseJDF)
    				.doCatch(Exception.class)
	   					.log("Error call settlement")
    	    			.setBody(constant(null))
    				.end()

					.log("akan periksa hasil settlement call")
    	    		.choice()
		    			.when().simple("${body.businessMessage} != null")
		    				.marshal(settlementResponseJDF)
		    				.unmarshal(businessMessageJDF)
		    			.when().simple("${body.messageNotFound} != null")
			    			.setBody(constant(null))
		    		.endChoice()

    				.log("selesai periksa hasil settlement")
			.endChoice()
			.end()
			
			// EVALUATE HASIL ENQ SETTLEMENT
			.log("selesai panggil settlment..")

	    	// kalo masih body=null berarti harus Payment Status
			.choice().when().simple("${body} == null")
				.log("tidak ada settlement, harus PS")
    			.setBody(simple("${header.fict_objreqbi}"))
    			.process(initPaymentStatusRequestProcessor)
    			.log("${body.recptBank}")
    			.to("direct:paymentstatus")
//    			.unmarshal(businessMessageJDF)
			.end()
				
			.choice()
				.when().simple("${body} != null")
					.log("Proses encryption")
					.marshal(businessMessageJDF)
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
			.process(fiCrdtTransferResponseProcessor)
//			.marshal(chnlResponseJDF)

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
