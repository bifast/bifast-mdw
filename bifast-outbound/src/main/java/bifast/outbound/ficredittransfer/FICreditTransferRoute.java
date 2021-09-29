package bifast.outbound.ficredittransfer;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.corebank.CBTransactionFailureProcessor;
import bifast.outbound.ficredittransfer.processor.FICTCorebankRequestProcessor;
import bifast.outbound.ficredittransfer.processor.FICreditTransferRequestProcessor;
import bifast.outbound.ficredittransfer.processor.FICreditTransferResponseProcessor;
import bifast.outbound.paymentstatus.BuildPaymentStatusRequestProcessor;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.report.InitSettlementRequestProcessor;
import bifast.outbound.report.pojo.RequestPojo;
import bifast.outbound.report.pojo.ResponsePojo;

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
	private EnrichmentAggregator enrichmentAggregator;
	@Autowired
	private InitSettlementRequestProcessor initSettlementRequest;
	@Autowired
	private BuildPaymentStatusRequestProcessor initPaymentStatusRequestProcessor;
	@Autowired
	private CBTransactionFailureProcessor corebankTransactionFailureProcessor;

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

		settlementRequestJDF.setInclude("NON_NULL");
		settlementRequestJDF.setInclude("NON_EMPTY");
		settlementRequestJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		
		settlementResponseJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		settlementResponseJDF.setInclude("NON_NULL");
		settlementResponseJDF.setInclude("NON_EMPTY");

		chnlResponseJDF.setInclude("NON_NULL");
		chnlResponseJDF.setInclude("NON_EMPTY");
		chnlResponseJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

	}
	
	@Override
	public void configure() throws Exception {

		configureJsonDataFormat();


		from("direct:fictreq").routeId("komi.fict.callcb")

			.log("Prepare untuk call CB")
			.log(LoggingLevel.DEBUG, "komi.fict.callcb", "[ChnlReq:${header.hdr_chnlRefId}] prepare call CB...")

			.process(fiCTCorebankRequestProcessor)
			.to("direct:callcb")
			.setHeader("fict_cbresponse", simple("${body}"))
			
			// evalutate reponse cb
			.choice()
				.when().simple("${header.fict_cbresponse.status} == 'SUCCESS'")
					.log(LoggingLevel.DEBUG, "komi.fict.callcb", "[ChnlReq:${header.hdr_chnlRefId}] prepare call CB...")
					.to("seda:fict_corebank_accpt")
				.when().simple("${header.fict_cbresponse.status} == 'FAILED'")
					.process(corebankTransactionFailureProcessor)
			.end()

			.removeHeaders("fict_*")
		;

		
		from("seda:fict_corebank_accpt").routeId("komi.fict.cihub")
			.log(LoggingLevel.DEBUG, "komi.fict.cihub", "[ChnlReq:${header.hdr_chnlRefId}] Corebank accepted.")
			
			.process(fiCrdtTransferRequestProcessor)
			.setHeader("fict_objreqbi", simple("${body}"))
			
			// kirim ke CI-HUB
			.to("direct:call-cihub")

    		
    		// kalo body==null berarti harus settlement
    		.choice()
    			.when().simple("${body} == null")
    				.log(LoggingLevel.DEBUG, "komi.fict.cihub", "[ChnlReq:${header.hdr_chnlRefId}] call cari Settlement.")
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
						
						.log("Hasil enquiry Settlement: ${body}")
						.unmarshal(settlementResponseJDF)
    				.doCatch(Exception.class)
		    			.log(LoggingLevel.ERROR, "[ChnlReq:${header.hdr_chnlRefId}] Settlement Enquiry error")
				    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
    	    			.setBody(constant(null))
    				.end()

    	    		// jika berupa settlement, ubah object type dari settlementResponse jadi BusinessMessage
    	    		.choice()
		    			.when().simple("${body.businessMessage} != null")
		    				.log(LoggingLevel.DEBUG, "komi.fict.cihub", "[ChnlReq:${header.hdr_chnlRefId}] nemu Settlement.")
		    				.marshal(settlementResponseJDF)
		    				.log("hasil settlement: ${body}")
		    				.unmarshal(businessMessageJDF)
		    			.when().simple("${body.messageNotFound} != null")
	    					.log(LoggingLevel.DEBUG, "komi.fict.cihub", "[ChnlReq:${header.hdr_chnlRefId}] tidak nemu Settlement.")
			    			.setBody(constant(null))
		    		.endChoice()

			.endChoice()
			.end()
			
			// EVALUATE HASIL ENQ SETTLEMENT

//	    	 kalo masih body=null berarti harus Payment Status
			.filter().simple("${body} == null")
				.log(LoggingLevel.DEBUG, "komi.fict.cihub", "[ChnlReq:${header.hdr_chnlRefId}] tidak nemu Settlement, harus PS.")
    			.setBody(simple("${header.fict_objreqbi}"))
    			.process(initPaymentStatusRequestProcessor)
    			.to("direct:ps")
			.end()
				
			.choice()
			
				.when().simple("${body} != null")
					.setHeader("fict_biresponse", simple("${body}"))
					.marshal(businessMessageJDF)
			    	.setHeader("hdr_error_status", constant(null))
			    	.setHeader("hdr_error_mesg", constant(null))

				.otherwise()
					.log(LoggingLevel.DEBUG, "komi.fict.cihub", "[ChnlReq:${header.hdr_chnlRefId}] PS juga gagal.")
					.setHeader("ct_failure_point", constant("PSTIMEOUT"))
			    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(504))
			    	.setHeader("hdr_error_status", constant("TIMEOUT-CIHUB"))
			    	.setHeader("hdr_error_mesg", constant("Timeout waiting CIHUB response"))

			.end()
				
			// prepare untuk response ke channel
			.process(fiCrdtTransferResponseProcessor)
			
			.removeHeaders("fict_*")
		;



	}
}
