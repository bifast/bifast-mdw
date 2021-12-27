package bifast.inbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CbAccountEnquiryRequestPojo;
import bifast.inbound.corebank.pojo.CbAccountEnquiryResponsePojo;
import bifast.inbound.corebank.pojo.CbCreditRequestPojo;
import bifast.inbound.corebank.pojo.CbCreditResponsePojo;
import bifast.inbound.corebank.pojo.CbSettlementRequestPojo;
import bifast.inbound.corebank.pojo.CbSettlementResponsePojo;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.processor.EnrichmentAggregator;
import bifast.inbound.service.JacksonDataFormatService;


@Component
public class CorebankRoute extends RouteBuilder{
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private CbCallFaultProcessor cbFaultProcessor;

	@Override
	public void configure() throws Exception {
		JacksonDataFormat accountEnquiryJDF = jdfService.wrapRoot(CbAccountEnquiryRequestPojo.class);
		JacksonDataFormat accountEnquiryResponseJDF = jdfService.basic(CbAccountEnquiryResponsePojo.class);
		JacksonDataFormat creditJDF = jdfService.wrapRoot(CbCreditRequestPojo.class);
		JacksonDataFormat creditResponseJDF = jdfService.basic(CbCreditResponsePojo.class);
		JacksonDataFormat settlementJDF = jdfService.wrapRoot(CbSettlementRequestPojo.class);
		JacksonDataFormat settlementResponseJDF = jdfService.basic(CbSettlementResponsePojo.class);

		// ROUTE CALLCB 
		from("seda:callcb").routeId("komi.corebank")
			.log("Terima di corebank: ${body}")

			.choice()
				.when().simple("${body.class} endsWith 'CbAccountEnquiryRequestPojo'")
					.setHeader("cb_requestName", constant("accountinquiry"))
					.marshal(accountEnquiryJDF)

				.when().simple("${body.class} endsWith 'CbCreditRequestPojo'")
					.setHeader("cb_requestName", constant("credit"))
					.marshal(creditJDF)

				.when().simple("${body.class} endsWith 'CbSettlementRequestPojo'")
					.setHeader("cb_requestName", constant("settlement"))
					.marshal(settlementJDF)
		 			.log("Akan kirim settlment: ${body}")
			.end()
					
	 		.log(LoggingLevel.DEBUG, "komi.corebank", "[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}]"
	 														+ " CB Request: ${body}")

	 		.doTry()

				.setHeader("HttpMethod", constant("POST"))
				.enrich()
					.simple("http://{{komi.url.corebank}}?"
						+ "socketTimeout=5000&" 
						+ "bridgeEndpoint=true")
					.aggregationStrategy(enrichmentAggregator)
				.convertBodyTo(String.class)
		 		.log(LoggingLevel.DEBUG, "komi.corebank", "[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] CB Response: ${body}")

		    	.choice()
		 			.when().simple("${header.cb_requestName} == 'accountinquiry'")
		 				.unmarshal(accountEnquiryResponseJDF)
	 				.endChoice()
		 			.when().simple("${header.cb_requestName} == 'credit'")
	 					.unmarshal(creditResponseJDF)
	 				.endChoice()
		 			.when().simple("${header.cb_requestName} == 'settlement'")
	 					.unmarshal(settlementResponseJDF)
	 				.endChoice()
		 		.end()

	 		.endDoTry()
	    	.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] Call CB Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
		    	.process(cbFaultProcessor)
	    	.end()

	    	.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
					Object oBody = exchange.getMessage().getBody(Object.class);
					processData.setCorebankResponse(oBody);
					exchange.getMessage().setHeader("hdr_process_data", processData);
				}
	    		
	    	})
			.removeHeaders("cb_*")
		;
		
		
	}

}
