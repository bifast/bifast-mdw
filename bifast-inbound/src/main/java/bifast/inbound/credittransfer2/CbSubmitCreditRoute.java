package bifast.inbound.credittransfer2;

import org.apache.camel.LoggingLevel;
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
import bifast.inbound.processor.EnrichmentAggregator;
import bifast.inbound.service.JacksonDataFormatService;

@Component
public class CbSubmitCreditRoute extends RouteBuilder {
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private CbCallExceptionProcessor cbCallExceptionProcessor;


	@Override
	public void configure() throws Exception {
		JacksonDataFormat accountEnquiryJDF = jdfService.wrapRoot(CbAccountEnquiryRequestPojo.class);
		JacksonDataFormat accountEnquiryResponseJDF = jdfService.basic(CbAccountEnquiryResponsePojo.class);
		JacksonDataFormat creditJDF = jdfService.wrapRoot(CbCreditRequestPojo.class);
		JacksonDataFormat creditResponseJDF = jdfService.basic(CbCreditResponsePojo.class);
		JacksonDataFormat settlementJDF = jdfService.wrapRoot(CbSettlementRequestPojo.class);
		JacksonDataFormat settlementResponseJDF = jdfService.basic(CbSettlementResponsePojo.class);

		from("direct:post_credit_cb").routeId("post_cbs")
			
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

 			.log(LoggingLevel.DEBUG,"post_cbs", "Akan kirim credit: ${body}")

	 		.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.enrich()
					.simple("{{komi.url.corebank}}?"
						+ "socketTimeout=10000&" 
						+ "bridgeEndpoint=true")
					.aggregationStrategy(enrichmentAggregator)
				.convertBodyTo(String.class)
		 		.log("CB Response: ${body}")
		 		
				.choice()
					.when().simple("${header.cb_requestName} == 'accountinquiry'")
						.unmarshal(accountEnquiryResponseJDF)
					.when().simple("${header.cb_requestName} == 'credit'")
						.unmarshal(creditResponseJDF)
					.when().simple("${header.cb_requestName} == 'settlement'")
						.unmarshal(settlementResponseJDF)
				.end()

				.log(LoggingLevel.DEBUG,"post_cbs", "Selesai post ke cbs")
	 		.endDoTry()
	    	.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[CTJob] Call CB Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
		    	.process(cbCallExceptionProcessor)
	    	.end()

 			.log("Hasil post cbs: ${body}")

//	    	.delay(1500)	
		;
		
		
	}

}
