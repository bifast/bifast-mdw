package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
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
			.maximumRedeliveries(2).redeliveryDelay(5000)
			.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Corebank debit-reversal error.")
//	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
    		.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Caught exception ${exception.class}: \n ${exception.message}")
	    	.process(dbrevFaultProcessor)
			.to("seda:savecbdebitrevr?exchangePattern=InOnly")
			;

		from("seda:sdebitreversal").routeId("komi.cb.debit_rev")
//			.errorHandler(defaultErrorHandler().maximumRedeliveries(2).redeliveryDelay(60000))

			.log("[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] POST {{komi.url.isoadapter.reversal}}")
			.process(debitReversalRequestProcessor)
			.setHeader("revct_revRequest", simple("${body}"))
			.marshal(debitReversalRequestJDF)
			.setHeader("revct_strRequest", simple("${body}"))
			.log("[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Request ISOAdapter: ${body}")

            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))

			.setHeader("HttpMethod", constant("POST"))
		
			.enrich()
				.simple("{{komi.url.isoadapter.reversal}}?bridgeEndpoint=true")
				.aggregationStrategy(enrichmentAggregator)

			.convertBodyTo(String.class)
			
			.log("[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Response ISOAdapter: ${body}")

			.to("seda:savecbdebitrevr?exchangePattern=InOnly")
		;
		
		
		
	}

		
		
}
