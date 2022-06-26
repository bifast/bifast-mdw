package bifast.outbound.corebank;


import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.config.Config;
import bifast.outbound.corebank.pojo.DebitReversalRequestPojo;
import bifast.outbound.corebank.pojo.DebitReversalResponsePojo;
import bifast.outbound.corebank.processor.DebitReversalFaultProcessor;
import bifast.outbound.corebank.processor.DebitReversalRequestProcessor;
import bifast.outbound.corebank.processor.SaveCbDebitReversalProc;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class DebitReversalSedaRoute extends RouteBuilder{
	@Autowired private Config config;
	@Autowired private DebitReversalFaultProcessor dbrevFaultProcessor;
	@Autowired private DebitReversalRequestProcessor debitReversalRequestProcessor;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private SaveCbDebitReversalProc saveCbDebitReversalProc;

//	private static Logger logger = LoggerFactory.getLogger(DebitReversalSedaRoute.class);

	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat debitReversalRequestJDF = jdfService.basic(DebitReversalRequestPojo.class);
		JacksonDataFormat debitReversalResponseJDF = jdfService.wrapRoot(DebitReversalResponsePojo.class);

		onException(Exception.class)
			.handled(true)
			.maximumRedeliveries(config.getDebitrev().getRetry()).redeliveryDelay(config.getDebitrev().getRetryInterval())
			.choice()
				.when().simple("${exchangeProperty.CamelExceptionCaught.statusCode} == '504' ")
					.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Corebank debit-reversal TIMEOUT.")
				.otherwise()
					.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Corebank debit-reversal error.")
					.log(LoggingLevel.ERROR, "${exception.stacktrace}")
			.end()
	    	.process(dbrevFaultProcessor)
			.process(saveCbDebitReversalProc)
			;

		from("seda:sdebitreversal").routeId("komi.cb.debit_rev")
//			.errorHandler(defaultErrorHandler().maximumRedeliveries(2).redeliveryDelay(10000))

			.log("[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] POST {{komi.url.isoadapter.reversal}}")
			.process(debitReversalRequestProcessor)
			.setHeader("revct_revRequest", simple("${body}"))
			.marshal(debitReversalRequestJDF)
			.log("[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Request ISOAdapter: ${body}")

            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
			.setHeader("HttpMethod", constant("POST"))
		
			.enrich().simple("{{komi.url.isoadapter.reversal}}?"
//					+ "socketTimeout=7000&" 
					+ "bridgeEndpoint=true")
				.aggregationStrategy(enrichmentAggregator)
			.convertBodyTo(String.class)
			
			.log("[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] Response ISOAdapter: ${body}")
			.unmarshal(debitReversalResponseJDF)
			
//			.bean("SaveCbTrnsService", "debitReversal")
			.process(saveCbDebitReversalProc)

			;
		
	}

		
}
