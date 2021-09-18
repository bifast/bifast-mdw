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
import bifast.outbound.credittransfer.processor.CTCorebankRequestProcessor;
import bifast.outbound.credittransfer.processor.CreditTransferRequestProcessor;
import bifast.outbound.credittransfer.processor.CreditTransferResponseProcessor;
import bifast.outbound.credittransfer.processor.SaveCTTablesProcessor;
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
	private BuildPaymentStatusRequestProcessor initPaymentStatusRequestProcessor;
	@Autowired
	private SaveCTTablesProcessor saveCTTableProcessor;
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
//		settlementResponseJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

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

//			.process(validateRequest)
			.setHeader("hdr_chnl_req_id", simple("${body.orignReffId}"))
			
			.process(crdtTransferProcessor)

			.log("[crdt_trnsf] [${body.appHdr.bizMsgIdr}]")

			.setHeader("ct_objreqbi", simple("${body}"))
			.marshal(businessMessageJDF)

			.to("seda:encryptCTbody")
			.to("seda:saveCTtables")

			// invoke corebanking
			.process(ctCorebankRequestProcessor)
			.marshal(cbDebitInstructionRequestJDF)
			
			.setHeader("hdr_errorlocation", constant("corebank service call"))		
			.to("direct:callcb")
			.setHeader("ct_cbresponse", simple("${body.cbDebitInstructionResponse}"))
				
			.choice()
				.when().simple("${header.ct_cbresponse.status} == 'SUCCESS'")
					.log("akan seda lolos corebank")
					.to("seda:ct_lolos_corebank")

				.when().simple("${header.ct_cbresponse.status} == 'FAILED'")
//					.to("seda:ct_tidaklolos_corebank")
					.log("Corebanking reject")
					.process(corebankTransactionFailureProcessor)
					.to("sql:update outbound_message set resp_status = 'RJCT-CB', "
							+ "error_msg = :#${header.ct_cbresponse.addtInfo} "
							+ "where id= :#${header.hdr_idtable}  ")
			.end()

			.removeHeaders("ct_*")
		;

//		from("seda:ct_tidaklolos_corebank")
//			.log("Corebanking reject")
//			.process(corebankTransactionFailureProcessor)
//			.to("sql:update outbound_message set resp_status = 'RJCT-CB', "
//					+ "error_msg = :#${header.ct_cbresponse.addtInfo} "
//					+ "where id= :#${header.hdr_idtable}  ")
//			.marshal(chnlResponseJDF)
//		;
		
		from("seda:ct_lolos_corebank").routeId("ct_after_cb_call")
			.log("sudah di seda lolos corebank")
			.setBody(simple("${header.ct_objreqbi}"))
//			.log("${body}")
			
			// kirim ke CI-HUB
			.setHeader("ct_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.log("Submit CT no: ${header.ct_objreqbi.appHdr.bizMsgIdr}")
			.doTry()
				.marshal(businessMessageJDF)
				.setHeader("hdr_errorlocation", constant("CTRoute/pre-call-cihub"))		

				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{bifast.ciconnector-url}}?"
						+ "socketTimeout={{bifast.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)
				.unmarshal(businessMessageJDF)
				.log("Selesai submit CT")
				
			.doCatch(SocketTimeoutException.class)     // klo timeout maka kirim payment status
    			.log(LoggingLevel.ERROR, "Timeout process Credit Transfer ")
    			.to("sql:update outbound_message set resp_status = 'TIMEOUT', "
    					+ " error_msg= 'Timeout' "
    					+ "where id= :#${header.hdr_idtable}  ")
    			.setBody(constant(null))
    		.end()

    		// periksa apakah ada hasil dari call cihub
    		.choice().when().simple("${body} == null")
    			.log("after call cihub, body is null}")
    			// check settlement
    			.setBody(simple("${header.ct_objreqbi}"))
				.setHeader("hdr_errorlocation", constant("CTRoute/pre-inq-settlement"))		

    			.process(initSettlementRequest)
    			.setHeader("hdr_errorlocation", constant("CTRoute/SettlementCall"))
    			
    			.doTry()
	    			.marshal(settlementRequestJDF)
	    			.log("Settlement enquiry: ${body}")
					.setHeader("HttpMethod", constant("POST"))
					.enrich("http:{{komi.inbound-url}}?"
							+ "bridgeEndpoint=true",
							enrichmentAggregator)
					.convertBodyTo(String.class)
					.log("Hasil call settlement: ${body}")
					
					.unmarshal(settlementResponseJDF)
					.log("call settlement finish")
	    		.doCatch(Exception.class)
	    			.log("Settlement error")
	    			.setBody(constant(null))
	    		.end()
	    		
	    		// jika berupa settlement, ubah object type dari settlementResponse jadi BusinessMessage
	    		.choice()
	    			.when().simple("${body.businessMessage} != null")
	    				.marshal(settlementResponseJDF)
	    				.log("settlementResponse format: ${body}")
	    				.unmarshal(businessMessageJDF)
//	    				.marshal(businessMessageJDF)
//	    				.log("businessMessage format: ${body}")
//	    				.unmarshal(businessMessageJDF)
	    			.when().simple("${body.messageNotFound} != null")
		    			.log("Setttlement notfound")
		    			.setBody(constant(null))
	    		.endChoice()
		
    			
    		.endChoice()
    		.end()
    		
	    		
    		// klo masih kosong, call Payment Status
    		.choice()
				.when().simple("${body} == null")
					.log("tidak ada settlement, harus PS")
		    		.setHeader("hdr_errorlocation", constant("CTRoute/PaymentStatus"))
	    			.setBody(simple("${header.ct_objreqbi}"))
	    			.process(initPaymentStatusRequestProcessor)
	    			.to("direct:paymentstatus")
//	    			.unmarshal(businessMessageJDF)
    			.otherwise()
					.log("Ada ct response / statement")
//    				.marshal(settlementResponseJDF)
//    				.log("disini: ${body}")
//    				.unmarshal(businessMessageJDF)
			.endChoice()
			.end()	

			//	Check hasil akhir setelah settlement dan PS
//			.log("PS1 harus obj: ${body}")
			.choice()
				.when().simple("${body} != null")
					.setHeader("ct_objresponsebi", simple("${body}"))	
					.setHeader("ct_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
					.marshal(businessMessageJDF)

					.to("seda:encryptCTbody")
				.otherwise()
					.log("Body null akhirnya")
			    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(504))
					.setHeader("ct_encrMessage", constant(null))	
	
			.end()
			
//			.log("PS2: ${body}")
			// prepare untuk response ke channel
    		.setHeader("hdr_errorlocation", constant("CTRoute/ResponseProcessor"))
			.process(crdtTransferResponseProcessor)
//			.marshal(chnlResponseJDF)

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
