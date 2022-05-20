package bifast.outbound.corebank;


import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.DebitReversalRequestPojo;
import bifast.outbound.corebank.processor.DebitReversalFaultProcessor;
import bifast.outbound.corebank.processor.DebitReversalRequestProcessor;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class DebitReversalSedaRoute extends RouteBuilder{
	@Autowired private DebitReversalFaultProcessor dbrevFaultProcessor;
	@Autowired private DebitReversalRequestProcessor debitReversalRequestProcessor;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private JacksonDataFormatService jdfService;

//	private static Logger logger = LoggerFactory.getLogger(DebitReversalRoute.class);

	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat debitReversalRequestJDF = jdfService.basic(DebitReversalRequestPojo.class);

		onException(Exception.class)
			.handled(true)
			.maximumRedeliveries(2).redeliveryDelay(20000)
			.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Call debit-reversal error.")
	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    	.process(dbrevFaultProcessor)
			.to("seda:savecbdebitrevr?exchangePattern=InOnly")
			;

		from("seda:sdebitreversal").routeId("komi.seda_debit_rev")
//			.errorHandler(defaultErrorHandler().maximumRedeliveries(2).redeliveryDelay(60000))

			.log("[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] POST {{komi.url.isoadapter.reversal}}")
			.process(debitReversalRequestProcessor)
			.setHeader("revct_revRequest", simple("${body}"))
			.marshal(debitReversalRequestJDF)
			.setHeader("revct_strRequest", simple("${body}"))
			.log("[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Request ISOAdapter: ${body}")
//
//			//submit reversal
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
//			.process(new Processor() {
//				public void process(Exchange exchange) throws Exception {
//		            exchange.getIn().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON);
//				}
//			})
			.setHeader("HttpMethod", constant("POST"))
		
//			.doTry()
				.enrich()
					.simple("{{komi.url.isoadapter.reversal}}?"
						+ "bridgeEndpoint=true")
						.aggregationStrategy(enrichmentAggregator)
	
				.convertBodyTo(String.class)
				
				.log("[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Response ISOAdapter: ${body}")

//				.unmarshal(debitReversalResponseJDF)
//			.endDoTry()
//	    	.doCatch(Exception.class)
//				.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Call Corebank Error.")
//		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
//		    	.process(dbrevFaultProcessor)
//			.end()

//			
//			.setExchangePattern(ExchangePattern.InOnly)
			.to("seda:savecbdebitrevr?exchangePattern=InOnly")
//
//			.setBody(simple("${header.revct_tmpbody}"))
//			.removeHeaders("revct_*")
		;
		
		
		
	}

		
		
}
