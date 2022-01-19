package bifast.inbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
		JacksonDataFormat aciRequestJDF = jdfService.wrapRoot(AccountCustInfoRequestPojo.class);
		JacksonDataFormat aciResponseJDF = jdfService.basic(AccountCustInfoResponsePojo.class);
		JacksonDataFormat creditResponseJDF = jdfService.basic(CbCreditResponsePojo.class);
		JacksonDataFormat settlementJDF = jdfService.wrapRoot(CbSettlementRequestPojo.class);
		JacksonDataFormat settlementResponseJDF = jdfService.basic(CbSettlementResponsePojo.class);
		JacksonDataFormat debitReversalReqJDF = jdfService.basic(DebitReversalRequestPojo.class);
		JacksonDataFormat debitReversalResponseJDF = jdfService.basic(DebitReversalResponsePojo.class);

		// ROUTE CALLCB 
		from("seda:isoadpt").routeId("komi.iso.adapter")
			.log("isoadapter")
			.log(LoggingLevel.DEBUG,"komi.iso.adapter", "[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] Terima di corebank: ${body}")

			.choice()
				.when().simple("${body.class} endsWith 'CustomerAccountInfoInboundRequest'")
					.setHeader("cb_requestName", constant("accountcustinfo"))
					.marshal(aciRequestJDF)
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
				.enrich("http:{{komi.url.isoadapter}}/accountinquiry?"
		//						+ "socketTimeout={{komi.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)
				.log("Corebank response: ${body}")

		    	.choice()
		 			.when().simple("${header.cb_requestName} == 'accountcustinfo'")
		 				.unmarshal(aciResponseJDF)
	 				.endChoice()
		 			.when().simple("${header.cb_requestName} == 'debitreversal'")
	 					.unmarshal(debitReversalResponseJDF)
	 				.endChoice()
		 			.when().simple("${header.cb_requestName} == 'settlement'")
	 					.unmarshal(settlementResponseJDF)
	 				.endChoice()
		 		.end()

				.filter().simple("${body.status} != 'ACTC' ")
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						DebitReversalResponsePojo resp = exchange.getMessage().getBody(DebitReversalResponsePojo.class);
						FaultPojo fault = new FaultPojo();
						fault.setResponseCode(resp.getStatus());
						fault.setReasonCode(resp.getReason());
						exchange.getMessage().setBody(fault);
					}
				})
			.end()

	 		.endDoTry()
	    	.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] Call CB Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
		    	.process(cbFaultProcessor)
	    	.end()

			.removeHeaders("cb_*")
		;
		
		
	}

}
