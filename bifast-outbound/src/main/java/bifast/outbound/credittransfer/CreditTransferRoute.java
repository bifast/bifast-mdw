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
	private InitSettlementRequestProcessor buildSettlementRequest;
	@Autowired
	private BuildPaymentStatusRequestProcessor initPaymentStatusRequestProcessor;
	@Autowired
	private SaveCTTablesProcessor saveCTTableProcessor;
	@Autowired
	private BuildAERequestProcessor buildAERequestProcessor;

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
			.to("sql:update outbound_message set resp_status = 'ERROR-KM', "
					+ " error_msg= :#${body.faultResponse.reason} "
					+ "where id= :#${header.ct_table_id}  ")
			.marshal(chnlResponseJDF)
			.removeHeaders("hdr_*")
			.removeHeaders("req_*")
			.removeHeaders("ct_*")
			.removeHeaders("ps_*")
			.removeHeader("HttpMethod")
	    	.handled(true)
	  	;


		from("direct:ctreq").routeId("crdt_trnsf")

			.setHeader("ct_channelRequest", simple("${body}"))
			
			// Account Enquiry dulu
			.process(buildAERequestProcessor)
			.to("direct:acctenqr")
			// check hasilnya
			.choice()
				.when().simple("${body.accountEnquiryResponse} != null")
					.log("Apakah AE Response SUCCESS or FAILED")
					.choice()
						.when().simple("${body.accountEnquiryResponse.status} == 'ACTC'")
							.log("ternyata AE lolos")
							.to("direct:ct_aepass")
						.otherwise()
							.log("AE REJECT")
							.setHeader("ct_failure_point", constant("AERJCT"))
							.process(crdtTransferResponseProcessor)
					.endChoice()
					.log("Selesai cek AE Response SUCCESS or FAILED")
				.when().simple("${body.faultResponse} != null")
					.log("kirim AE fault response")
			.end()
			.log("baru selesai proses AE")
			
			.removeHeaders("ct_*")
			.removeHeaders("resp_*")
			;
		
		from("direct:ct_aepass").routeId("ct_aepass")

			.process(crdtTransferProcessor)

			.log("[CT][ChRefId:${header.hdr_chnlRefId}] processing...")

			.setHeader("ct_objreqbi", simple("${body}"))
			.marshal(businessMessageJDF)

			.to("seda:encryptCTbody")
			.to("seda:saveCTtables")

			// invoke corebanking
			.setHeader("hdr_errorlocation", constant("corebank service call"))		
			.process(ctCorebankRequestProcessor)  // build request param
			.marshal(cbDebitInstructionRequestJDF)			
			.to("direct:callcb")
			.setHeader("ct_cbresponse", simple("${body.cbDebitInstructionResponse}"))
				
			.choice()
				.when().simple("${header.ct_cbresponse.status} == 'SUCCESS'")
					.log("akan seda lolos corebank")
					.to("seda:ct_lolos_corebank")

				.when().simple("${header.ct_cbresponse.status} == 'FAILED'")
					.log("Corebanking reject")
					.setHeader("ct_failure_point", constant("CBRJCT"))
//					.process(corebankTransactionFailureProcessor)
					.process(crdtTransferResponseProcessor)
					.to("sql:update outbound_message set resp_status = 'RJCT-CB', "
							+ "error_msg = :#${header.ct_cbresponse.addtInfo} "
							+ "where id= :#${header.ct_table_id}  ")
			.end()

			.removeHeaders("ct_*")
			.removeHeaders("resp_*")
		;


		from("seda:ct_lolos_corebank").routeId("ct_after_cb_call")
			.log("sudah di seda lolos corebank")
			.setBody(simple("${header.ct_objreqbi}"))
			
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
    			.log(LoggingLevel.ERROR, "[CT][ChRefId:${header.hdr_chnlRefId}] call CI-HUB timeout")
				.setHeader("ct_failure_point", constant("CIHUBTIMEOUT"))
    			.to("sql:update outbound_message set resp_status = 'TIMEOUT', "
    					+ " error_msg= 'Timeout' "
    					+ "where id= :#${header.ct_table_id}  ")
    			.setBody(constant(null))
    		.end()

    		// periksa apakah ada hasil dari call cihub
    		.choice().when().simple("${body} == null")
    			.log("after call cihub, body is null}")
    			// check settlement
    			
				.setHeader("hdr_errorlocation", constant("CTRoute/pre-inq-settlement"))		
    			.setBody(simple("${header.ct_objreqbi}"))    			
    			.process(buildSettlementRequest)
    			
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
			.endChoice()
			.end()	

			//	Check hasil akhir setelah settlement dan PS
			.choice()
				.when().simple("${body} != null")
					.setHeader("ct_objresponsebi", simple("${body}"))	
					.setHeader("ct_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
					.marshal(businessMessageJDF)

					.to("seda:encryptCTbody")
				.otherwise()
					.log("Body null akhirnya")
					.setHeader("ct_failure_point", constant("PSTIMEOUT"))
			    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(504))
					.setHeader("ct_encrMessage", constant(null))	
	
			.end()
			
			// prepare untuk response ke channel
    		.setHeader("hdr_errorlocation", constant("CTRoute/ResponseProcessor"))
			.process(crdtTransferResponseProcessor)

			.setHeader("ct_channelResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.to("seda:saveCTtables?exchangePattern=InOnly")
			
			.removeHeaders("ct_*")
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
			.setHeader("ct_table_id", simple("${header.hdr_idtable}"))
		;

	}
}
