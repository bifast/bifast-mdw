package bifast.inbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.isopojo.AccountEnquiryInboundRequest;
import bifast.inbound.corebank.isopojo.AccountEnquiryInboundResponse;
import bifast.inbound.corebank.pojo.CbCreditResponsePojo;
import bifast.inbound.corebank.pojo.CbSettlementRequestPojo;
import bifast.inbound.corebank.pojo.CbSettlementResponsePojo;
import bifast.inbound.pojo.FaultPojo;
import bifast.inbound.processor.EnrichmentAggregator;
import bifast.inbound.reversecrdttrns.pojo.AccountCustInfoResponsePojo;
import bifast.inbound.reversecrdttrns.pojo.AccountCustInfoRequestPojo;
import bifast.inbound.reversecrdttrns.pojo.DebitReversalRequestPojo;
import bifast.inbound.reversecrdttrns.pojo.DebitReversalResponsePojo;
import bifast.inbound.service.JacksonDataFormatService;


@Component
public class IsoAdapterRoute extends RouteBuilder{
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private CbCallFaultProcessor cbFaultProcessor;

	@Override
	public void configure() throws Exception {
		JacksonDataFormat aciRequestJDF = jdfService.basic(AccountCustInfoRequestPojo.class);
		JacksonDataFormat aciResponseJDF = jdfService.basic(AccountCustInfoResponsePojo.class);
		JacksonDataFormat aeRequestJDF = jdfService.basic(AccountEnquiryInboundRequest.class);
		JacksonDataFormat aeResponseJDF = jdfService.basic(AccountEnquiryInboundResponse.class);
		JacksonDataFormat creditResponseJDF = jdfService.basic(CbCreditResponsePojo.class);
		JacksonDataFormat settlementJDF = jdfService.basic(CbSettlementRequestPojo.class);
		JacksonDataFormat settlementResponseJDF = jdfService.basic(CbSettlementResponsePojo.class);
		JacksonDataFormat debitReversalReqJDF = jdfService.basic(DebitReversalRequestPojo.class);
		JacksonDataFormat debitReversalResponseJDF = jdfService.basic(DebitReversalResponsePojo.class);

		// ROUTE CALLCB 
		from("seda:isoadpt").routeId("komi.iso.adapter")
			.log("isoadapter")
			.log(LoggingLevel.DEBUG,"komi.iso.adapter", "[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] Terima di corebank: ${body}")

			.setProperty("bkp_hdr_process_data").header("hdr_process_data")
			.removeHeaders("*")

			.choice()
				.when().simple("${body.class} endsWith 'CustomerAccountInfoInboundRequest'")
					.setHeader("cb_requestName", constant("accountcustinfo"))
					.marshal(aciRequestJDF)
				.when().simple("${body.class} endsWith 'AccountEnquiryInboundRequest'")
					.setHeader("cb_requestName", constant("accountenquiry"))
					.setHeader("cb_url", simple("{{komi.url.isoadapter.accountinquiry}}"))
					.marshal(aeRequestJDF)
				.when().simple("${body.class} endsWith 'DebitReversalRequestPojo'")
					.setHeader("cb_requestName", constant("debitreversal"))
					.marshal(debitReversalReqJDF)

				.otherwise()
		 			.log("Akan kirim settlment: ${body.class}")
			.end()
					
	 		.log("[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}]"
	 														+ " CB Request: ${body}")

			
	 		.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
				.enrich().simple("${header.cb_url}?bridgeEndpoint=true")
//						+ "bridgeEndpoint=true")
					
					.aggregationStrategy(enrichmentAggregator)
					.convertBodyTo(String.class)
				
				.log("Corebank response: ${body}")

		    	.choice()
		 			.when().simple("${header.cb_requestName} == 'accountcustinfo'")
		 				.unmarshal(aciResponseJDF)
	 				.endChoice()
		 			.when().simple("${header.cb_requestName} == 'accountenquiry'")
		 				.unmarshal(aeResponseJDF)
		 				.setHeader("cb_response", simple("${body.status}"))
		 				.setHeader("cb_reason", simple("${body.reason}"))
	 				.endChoice()
		 			.when().simple("${header.cb_requestName} == 'debitreversal'")
	 					.unmarshal(debitReversalResponseJDF)
	 				.endChoice()
		 			.when().simple("${header.cb_requestName} == 'settlement'")
	 					.unmarshal(settlementResponseJDF)
	 				.endChoice()
		 		.end()

				.filter().simple("${header.cb_response} != 'ACTC' ")
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						FaultPojo fault = new FaultPojo();
						String cbResponse = exchange.getMessage().getHeader("cb_response", String.class);
						String cbReason = exchange.getMessage().getHeader("cb_reason", String.class);
						fault.setResponseCode(cbResponse);
						fault.setReasonCode(cbReason);
						exchange.getMessage().setBody(fault);
					}
				})
				.setHeader("hdr_process_data", exchangeProperty("bkp_hdr_process_data"))

//			.end()

	 		.endDoTry()
	    	.doCatch(Exception.class)
			.setHeader("hdr_process_data", exchangeProperty("bkp_hdr_process_data"))
				.log(LoggingLevel.ERROR, "[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] Call CB Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
		    	.process(cbFaultProcessor)
	    	.end()

			.removeHeaders("cb_*")

		;
		
		
	}

}
