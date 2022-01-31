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
import bifast.inbound.corebank.isopojo.CreditRequest;
import bifast.inbound.corebank.isopojo.CreditResponse;
import bifast.inbound.corebank.isopojo.SettlementRequest;
import bifast.inbound.corebank.isopojo.SettlementResponse;
import bifast.inbound.pojo.FaultPojo;
import bifast.inbound.pojo.ProcessDataPojo;
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
	@Autowired private SaveCBTransactionProc saveCBTransactionProc;

	@Override
	public void configure() throws Exception {
		JacksonDataFormat aciRequestJDF = jdfService.basic(AccountCustInfoRequestPojo.class);
		JacksonDataFormat aciResponseJDF = jdfService.basic(AccountCustInfoResponsePojo.class);
		JacksonDataFormat aeRequestJDF = jdfService.basic(AccountEnquiryInboundRequest.class);
		JacksonDataFormat aeResponseJDF = jdfService.basic(AccountEnquiryInboundResponse.class);
		JacksonDataFormat creditRequestJDF = jdfService.basic(CreditRequest.class);
		JacksonDataFormat creditResponseJDF = jdfService.basic(CreditResponse.class);
		JacksonDataFormat settlementJDF = jdfService.basic(SettlementRequest.class);
		JacksonDataFormat settlementResponseJDF = jdfService.basic(SettlementResponse.class);
		JacksonDataFormat debitReversalReqJDF = jdfService.basic(DebitReversalRequestPojo.class);
		JacksonDataFormat debitReversalResponseJDF = jdfService.basic(DebitReversalResponsePojo.class);

		// ROUTE CALLCB 
		from("direct:isoadpt").routeId("komi.isoadapter")
//			.setProperty("bkp_hdr_process_data").header("hdr_process_data")

			.removeHeaders("*")

			.setHeader("cb_msgname", simple("${exchangeProperty[prop_process_data.inbMsgName]}"))
			.setHeader("cb_e2eid", simple("${exchangeProperty[prop_process_data.endToEndId]}"))

			.setProperty("cb_request", simple("${body}"))
			
			.log(LoggingLevel.DEBUG,"komi.isoadapter", "[${header.cb_msgname}:${header.cb_e2eid}] Terima di corebank: ${body}")
					
			.choice()
				.when().simple("${body.class} endsWith 'CustomerAccountInfoInboundRequest'")
					.setHeader("cb_requestName", constant("accountcustinfo"))
					.setHeader("cb_url", simple("{{komi.url.isoadapter.customerinfo}}"))
					.marshal(aciRequestJDF)
				.when().simple("${body.class} endsWith 'AccountEnquiryInboundRequest'")
					.setHeader("cb_requestName", constant("accountenquiry"))
					.setHeader("cb_url", simple("{{komi.url.isoadapter.accountinquiry}}"))
					.marshal(aeRequestJDF)
				.when().simple("${body.class} endsWith 'CreditRequest'")
					.setHeader("cb_requestName", constant("credit"))
					.setHeader("cb_url", simple("{{komi.url.isoadapter.credit}}"))
					.marshal(creditRequestJDF)
				.when().simple("${body.class} endsWith 'DebitReversalRequestPojo'")
					.setHeader("cb_requestName", constant("debitreversal"))
					.marshal(debitReversalReqJDF)
				.when().simple("${body.class} endsWith 'SettlementRequest'")
					.setHeader("cb_requestName", constant("settlement"))
					.setHeader("cb_url", simple("{{komi.url.isoadapter.settlement}}"))
					.marshal(settlementJDF)

				.otherwise()
		 			.log("Akan kirim : ${body.class}")
			.end()
			
			.setProperty("cb_request_str", simple("${body}"))
			
	 		.log(LoggingLevel.DEBUG, "komi.isoadapter", "POST ${header.cb_url}")
	 		.log("[${header.cb_msgname}:${header.cb_e2eid}] CB Request: ${body}")
			
	 		.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
				.enrich().simple("${header.cb_url}?bridgeEndpoint=true")
//						+ "bridgeEndpoint=true")
					
					.aggregationStrategy(enrichmentAggregator)
					.convertBodyTo(String.class)
				
				.log("[${header.cb_msgname}:${header.cb_e2eid}] CB response: ${body}")


		    	.choice()
		 			.when().simple("${header.cb_requestName} == 'accountcustinfo'")
		 				.unmarshal(aciResponseJDF)
	 				.endChoice()
		 			.when().simple("${header.cb_requestName} == 'accountenquiry'")
		 				.unmarshal(aeResponseJDF)
	 				.endChoice()
		 			.when().simple("${header.cb_requestName} == 'credit'")
		 				.unmarshal(creditResponseJDF)
		 			.when().simple("${header.cb_requestName} == 'debitreversal'")
	 					.unmarshal(debitReversalResponseJDF)
	 				.endChoice()
		 			.when().simple("${header.cb_requestName} == 'settlement'")
	 					.unmarshal(settlementResponseJDF)
	 				.endChoice()
		 		.end()

 				.setHeader("cb_response", simple("${body.status}"))
 				.setHeader("cb_reason", simple("${body.reason}"))

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
				.end()
				
	 		.endDoTry()
	    	.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[${header.cb_msgname}:${header.cb_e2eid}] Call CB Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
		    	.process(cbFaultProcessor)
	    	.end()

			.process(new Processor() {
				@Override
				public void process(Exchange exchange) throws Exception {
					ProcessDataPojo processData = exchange.getProperty("prop_process_data", ProcessDataPojo.class);
					Object cbResponse = exchange.getMessage().getBody(Object.class);
					processData.setCorebankResponse(cbResponse);
					exchange.setProperty("prop_process_data", processData);
				}
			})
			
			.process(saveCBTransactionProc)
			.removeHeaders("cb_*")
		;
		
		
	}

}
