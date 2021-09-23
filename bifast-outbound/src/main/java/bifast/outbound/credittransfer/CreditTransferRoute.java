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
import bifast.outbound.credittransfer.processor.BuildAERequestProcessor;
import bifast.outbound.credittransfer.processor.CTCorebankRequestProcessor;
import bifast.outbound.credittransfer.processor.CreditTransferRequestProcessor;
import bifast.outbound.credittransfer.processor.CreditTransferResponseProcessor;
import bifast.outbound.credittransfer.processor.SaveCreditTransferProcessor;
import bifast.outbound.credittransfer.processor.StoreCreditTransferProcessor;
import bifast.outbound.paymentstatus.BuildPaymentStatusRequestProcessor;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.FaultResponseProcessor;
import bifast.outbound.processor.FlatResponseProcessor;
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
	private FaultResponseProcessor faultProcessor;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	@Autowired
	private InitSettlementRequestProcessor buildSettlementRequest;
	@Autowired
	private BuildPaymentStatusRequestProcessor initPaymentStatusRequestProcessor;
	@Autowired
	private SaveCreditTransferProcessor saveCTTableProcessor;
	@Autowired
	private StoreCreditTransferProcessor storeCTProcessor;
	@Autowired
	private BuildAERequestProcessor buildAERequestProcessor;
	@Autowired
	private FlatResponseProcessor flatResponseProcessor;

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
			.to("seda:saveCTtables?exchangePattern=InOnly")
			.marshal(chnlResponseJDF)
			.removeHeaders("hdr_*")
			.removeHeaders("req_*")
			.removeHeaders("ct_*")
			.removeHeaders("ps_*")
			.removeHeader("HttpMethod")
	    	.handled(true)
	  	;


		from("direct:flatctreq").routeId("komi.flatct.start")
			.setHeader("ct_channelRequest", simple("${body}"))
			.process(crdtTransferProcessor)

			.setHeader("ct_birequest", simple("${body}"))	
			.to("direct:call-cihub")
			.setHeader("ct_biresponse", simple("${body}"))

			.process(flatResponseProcessor)
			.process(storeCTProcessor)
			
			.removeHeaders("ct*")

		;
		
		from("direct:ctreq").routeId("komi.ct.start")
		
			.setHeader("ct_channelRequest", simple("${body}"))
			
			// Account Enquiry dulu
			.process(buildAERequestProcessor)
			.to("direct:acctenqr")
			// check hasilnya
			.choice()
				.when().simple("${body.accountEnquiryResponse} != null")
					.choice()
						.when().simple("${body.accountEnquiryResponse.status} == 'ACTC'")
							.log(LoggingLevel.DEBUG, "komi.ct.start", "[ChRefId:${header.hdr_chnlRefId}][CT] AE passed.")
							.to("direct:ct_aepass")
						.otherwise()
							.log(LoggingLevel.DEBUG, "komi.ct.start", "[ChRefId:${header.hdr_chnlRefId}][CT] AE reject.")
							.setHeader("ct_failure_point", constant("AERJCT"))
							.process(crdtTransferResponseProcessor)
					    	.setHeader("hdr_error_status", constant("REJECT-CIHUB"))
					    	.setHeader("hdr_error_mesg", constant("Account Enquiry reject."))
					.endChoice()
			.end()
			
			.removeHeaders("ct_*")
			.removeHeaders("resp_*")
			;
		
		from("direct:ct_aepass").routeId("komi.ct.afterAERoute")

			.log(LoggingLevel.DEBUG, "komi.ct.afterAERoute", "[ChRefId:${header.hdr_chnlRefId}][CT] direct:ct_aepass...")

			// invoke corebanking
			.setHeader("hdr_errorlocation", constant("corebank service call"))		
			.process(ctCorebankRequestProcessor)  // build request param
			.to("direct:callcb")
			.setHeader("hdr_cbresponse", simple("${body}"))
			
			.choice()
				.when().simple("${header.hdr_cbresponse.status} == 'SUCCESS'")
					.to("seda:ct_lolos_corebank")

				.when().simple("${header.hdr_cbresponse.status} == 'FAILED'")
					.log(LoggingLevel.ERROR, "[ChRefId:${header.hdr_chnlRefId}] Corebank reject.")
					.setHeader("ct_failure_point", constant("CBRJCT"))
					.setHeader("hdr_error_status", constant("REJECT-CB"))
					.process(crdtTransferResponseProcessor)
			.end()

			.removeHeaders("ct_*")
			.removeHeaders("resp_*")
		;


		from("seda:ct_lolos_corebank").routeId("komi.ct.after_cbcall")
			.log(LoggingLevel.DEBUG, "komi.ct.after_cbcall", "[ChRefId:${header.hdr_chnlRefId}] Corebank accept.")

			.process(crdtTransferProcessor)
			.setHeader("ct_birequest", simple("${body}"))
		
			// kirim ke CI-HUB

			.setHeader("ct_birequest", simple("${body}"))	
			.to("direct:call-cihub")
			.setHeader("ct_biresponse", simple("${body}"))
			
			.process(storeCTProcessor)

			.log(LoggingLevel.DEBUG, "komi.ct.after_cbcall", "[ChRefId:${header.hdr_chnlRefId}][CT] setelah call CIHUB.")

    		// periksa apakah ada hasil dari call cihub
    		.choice().when().simple("${body} == null")
    			.log("after call cihub, body is null}")
    			// check settlement
    			
				.setHeader("hdr_errorlocation", constant("CTRoute/pre-inq-settlement"))		
    			.setBody(simple("${header.ct_birequest}"))    			
    			.process(buildSettlementRequest)

    			.doTry()
	    			.marshal(settlementRequestJDF)
	    			.log(LoggingLevel.DEBUG, "komi.ct.after_cbcall", "[ChRefId:${header.hdr_chnlRefId}][CT] Settlement request: ${body}")
					.setHeader("HttpMethod", constant("POST"))
					.enrich("http:{{komi.inbound-url}}?"
							+ "bridgeEndpoint=true",
							enrichmentAggregator)
					.convertBodyTo(String.class)
					.log("Hasil call settlement: ${body}")
					
					.unmarshal(settlementResponseJDF)
					.log("call settlement finish")
	    		.doCatch(Exception.class)
	    			.log(LoggingLevel.ERROR, "Settlement Enquiry error")
			    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    			.setBody(constant(null))
	    		.end()
	    		
	    		// jika berupa settlement, ubah object type dari settlementResponse jadi BusinessMessage
	    		.choice()
	    			.when().simple("${body.businessMessage} != null")
	    				.marshal(settlementResponseJDF)
	    				.log("settlementResponse format: ${body}")
	    				.unmarshal(businessMessageJDF)
	    			.when().simple("${body.messageNotFound} != null")
	    				.log(LoggingLevel.DEBUG, "komi.ct.after_cbcall", "[ChRefId:${header.hdr_chnlRefId}][CT] Settlement not found.")
		    			.setBody(constant(null))
	    		.endChoice()
		
    		.endChoice()
    		.end()
    		
	    		
    		// klo masih kosong, call Payment Status
    		.choice()
				.when().simple("${body} == null")
					.log(LoggingLevel.DEBUG, "komi.ct.after_cbcall", "[ChRefId:${header.hdr_chnlRefId}][CT] tidak ada settlement, harus PS.")
		    		.setHeader("hdr_errorlocation", constant("CTRoute/PaymentStatus"))
	    			.setBody(simple("${header.ct_objreqbi}"))
	    			.process(initPaymentStatusRequestProcessor)
	    			.to("direct:ps")
//	    			.unmarshal(businessMessageJDF)
			.endChoice()
			.end()	

//				Check hasil akhir setelah settlement dan PS
			.choice()
				.when().simple("${body} != null")
					.setHeader("ct_biresponse", simple("${body}"))
					.marshal(businessMessageJDF)
			    	.setHeader("hdr_error_status", constant(null))
			    	.setHeader("hdr_error_mesg", constant(null))

				.otherwise()
					.log("Body null akhirnya")
					.setHeader("ct_failure_point", constant("PSTIMEOUT"))
			    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(504))
			    	.setHeader("hdr_error_status", constant("TIMEOUT-CIHUB"))
			    	.setHeader("hdr_error_mesg", constant("Timeout waiting CIHUB response"))
	
			.end()
			
			// TODO jika response RJCT, harus reversal ke corebanking
			
//			// prepare untuk response ke channel
    		.setHeader("hdr_errorlocation", constant("CTRoute/ResponseProcessor"))
			.process(crdtTransferResponseProcessor)
			
			.removeHeaders("ct_*")
		;


//		from("seda:encryptCTbody").routeId("komi.ct.encryption")
//			.setHeader("ct_tmp", simple("${body}"))
//			.marshal().zipDeflater()
//			.marshal().base64()
//			.setHeader("ct_encrMessage", simple("${body}"))
//			.setBody(simple("${header.ct_tmp}"))
//		;
		
		from("seda:saveCTtables").routeId("komi.ct.save_logtable")
			.process(saveCTTableProcessor)
		;
		
	}
}
