package bifast.outbound.credittransfer;

import java.net.SocketTimeoutException;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.corebank.CBDebitInstructionRequestPojo;
import bifast.outbound.corebank.CBDebitInstructionResponsePojo;
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
public class CreditTransferRoute extends RouteBuilder {

	@Autowired
	private CTCorebankRequestProcessor ctCorebankRequestProcessor;
	@Autowired
	private CreditTransferRequestProcessor crdtTransferProcessor;
	@Autowired
	private CreditTransferResponseProcessor crdtTransferResponseProcessor;
	@Autowired
	private FaultProcessor faultProcessor;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	@Autowired
	private InitSettlementRequestProcessor initSettlementRequest;
	@Autowired
	private InitPaymentStatusRequestCTProcessor initPaymentStatusRequestProcessor;
	@Autowired
	private ValidateInputProcessor validateRequest;
	@Autowired
	private SaveCTTablesProcessor saveCTTableProcessor;
	@Autowired
	private SettlementEnquiryResultProcessor settlementEnquiryResultProcessor;
	@Autowired
	private CBTransactionFailureProcessor corebankTransactionFailureProcessor;

	JacksonDataFormat cbDebitInstructionRequestJDF = new JacksonDataFormat(CBDebitInstructionRequestPojo.class);
	JacksonDataFormat cbDebitInstructionResponseJDF = new JacksonDataFormat(CBDebitInstructionResponsePojo.class);
	
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

		settlementRequestJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		settlementRequestJDF.setInclude("NON_NULL");
		settlementRequestJDF.setInclude("NON_EMPTY");
		settlementRequestJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

		settlementResponseJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		settlementResponseJDF.setInclude("NON_NULL");
		settlementResponseJDF.setInclude("NON_EMPTY");
		settlementResponseJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

		cbDebitInstructionRequestJDF.setInclude("NON_NULL");
		cbDebitInstructionRequestJDF.setInclude("NON_EMPTY");
		cbDebitInstructionRequestJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		
		cbDebitInstructionResponseJDF.setInclude("NON_NULL");
		cbDebitInstructionResponseJDF.setInclude("NON_EMPTY");
		cbDebitInstructionResponseJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		chnlResponseJDF.setInclude("NON_NULL");
		chnlResponseJDF.setInclude("NON_EMPTY");

	}
	
	@Override
	public void configure() throws Exception {

		configureJsonDataFormat();


        onException(Exception.class).routeId("CT Exception Handler")
	    	.log("Fault di CT Excp, ${header.hdr_errorlocation}")
	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
			.process(faultProcessor)
			.to("sql:update outbound_message set resp_status = 'ERROR-KM', "
					+ " error_msg= :#${body.faultResponse.reason} "
					+ "where id= :#${header.hdr_idtable}  ")
			.marshal(chnlResponseJDF)
			.removeHeaders("hdr_*")
			.removeHeaders("req_*")
			.removeHeaders("ct_*")
			.removeHeaders("ps_*")
			.removeHeader("HttpMethod")
	    	.handled(true)
	  	;


		from("direct:ctreq").routeId("crdt_trnsf")

			.log("Credit Transfer")
			.process(validateRequest)
			
			.process(crdtTransferProcessor)

			.setHeader("ct_objreqbi", simple("${body}"))
			.marshal(businessMessageJDF)

			.to("seda:encryptCTbody")
			.to("seda:saveCTtables")

			// invoke corebanking
			.process(ctCorebankRequestProcessor)
			.marshal(cbDebitInstructionRequestJDF)
			.log("cbRequest: ${body}")
			
			.setHeader("hdr_errorlocation", constant("corebank service call"))		
			.to("direct:callcb")
			.log("dari CB: ${body}")
			.unmarshal(cbDebitInstructionResponseJDF)
			
			.choice()
				.when().simple("${body.status} == 'SUCCESS'")
					.log("akan seda lolos corebank")
					.to("seda:ct_lolos_corebank")
				.when().simple("${body.status} == 'FAILED'")
					.to("seda:ct_tidaklolos_corebank")
			.end()

			.removeHeaders("ct_*")
		;

		from("seda:ct_tidaklolos_corebank")
			.log("Corebanking failure")
			.process(corebankTransactionFailureProcessor)
			.to("sql:update outbound_message set resp_status = 'RJCT-CB' "
					+ "where id= :#${header.hdr_idtable}  ")
			.marshal(chnlResponseJDF)
		;
		
		from("seda:ct_lolos_corebank")
			.log("sudah di seda lolos corebank")
			.setBody(simple("${header.ct_objreqbi}"))
			.marshal(businessMessageJDF)
			.log("${body}")
			
			// kirim ke CI-HUB
			.setHeader("ct_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.doTry()
				.log("Submit CT no: ${header.ct_objreqbi.appHdr.bizMsgIdr}")
				.setHeader("hdr_errorlocation", constant("CTRoute/pre-call-cihub"))		

				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{bifast.ciconnector-url}}?"
						+ "socketTimeout={{bifast.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)
				
				.log("Selesai submit CT")
				
			.doCatch(SocketTimeoutException.class)     // klo timeout maka kirim payment status
    			.log(LoggingLevel.ERROR, "Timeout process Credit Transfer ")
    			.to("sql:update outbound_message set resp_status = 'TIMEOUT', "
    					+ " error_msg= 'Timeout' "
    					+ "where id= :#${header.hdr_idtable}  ")

    			// check settlement
    			.setBody(simple("${header.ct_objreqbi}"))
				.setHeader("hdr_errorlocation", constant("CTRoute/pre-inq-settlement"))		

    			.process(initSettlementRequest)
    			.marshal(settlementRequestJDF)
    			.log("Settlement: ${body}")
    			
    			.doTry()
	    			.setHeader("hdr_errorlocation", constant("CTRoute/SettlementCall"))
					.setHeader("HttpMethod", constant("POST"))
					.enrich("http://localhost:9001/services/api/enquiry?"
							+ "bridgeEndpoint=true",
							enrichmentAggregator)
					.convertBodyTo(String.class)
					.unmarshal(settlementResponseJDF)
					
	    			.setHeader("hdr_errorlocation", constant("CTRoute/SettlementReady"))
	    			.process(settlementEnquiryResultProcessor)
	    		.doCatch(Exception.class)
	    			.log("Settlement error")
	    			.setBody(constant(null))
	    		.end()

	    		.log("Selesai panggil settlement")
	    		
	    		.choice()
    				.when().simple("${body} == null")
    					.log("tidak ada settlement, harus PS")
    		    		.setHeader("hdr_errorlocation", constant("CTRoute/PaymentStatus"))
    	    			.setBody(simple("${header.ct_objreqbi}"))
    	    			.process(initPaymentStatusRequestProcessor)
    	    			.to("direct:paymentstatus")

	    			.otherwise()
						.log("Ada statement")
    					.marshal(businessMessageJDF)
				.endChoice()
				.end()	

			.endDoTry()
			.end()

			.log("selesai dotry")
			.log("${body}")
			.choice()
				.when().simple("${body} != null")
					.setHeader("ct_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
					.to("seda:encryptCTbody")
					.unmarshal(businessMessageJDF)
					.setHeader("ct_objresponsebi", simple("${body}"))	
					.marshal(businessMessageJDF)
				.otherwise()
					.log("Body null akhirnya")
			    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(504))
					.setHeader("ct_encrMessage", constant(null))	
	
			.end()
			
			.log("${body}")
			// prepare untuk response ke channel
    		.setHeader("hdr_errorlocation", constant("CTRoute/ResponseProcessor"))
			.process(crdtTransferResponseProcessor)
			.marshal(chnlResponseJDF)

			.setHeader("ct_channelResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.to("seda:saveCTtables?exchangePattern=InOnly")
		;


		from("seda:encryptCTbody")
			.setHeader("ct_tmp", simple("${body}"))
			.marshal().zipDeflater()
			.marshal().base64()
			.setHeader("ct_encrMessage", simple("${body}"))
			.setBody(simple("${header.ct_tmp}"))
		;
		from("seda:saveCTtables")
			.process(saveCTTableProcessor)
		;

	}
}
