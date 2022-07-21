package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
public class DebitReversalRoute extends RouteBuilder{
	@Autowired private Config config;
	@Autowired private DebitReversalFaultProcessor dbrevFaultProcessor;
	@Autowired private DebitReversalRequestProcessor debitReversalRequestProcessor;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private SaveCbDebitReversalProc saveCbDebitReversalProc;

//	private static Logger logger = LoggerFactory.getLogger(DebitReversalRoute.class);

	@Override
	public void configure() throws Exception {

		JacksonDataFormat debitReversalRequestJDF = jdfService.basic(DebitReversalRequestPojo.class);
		JacksonDataFormat debitReversalResponseJDF = jdfService.basic(DebitReversalResponsePojo.class);
	
		onException(HttpOperationFailedException.class).onWhen(simple("${exception.statusCode} == '504'")) 
			.routeId("komi_debitrev_excp")
			.maximumRedeliveries(config.getDebitrev().getRetry())
			.redeliveryDelay(config.getDebitrev().getRetryInterval())
			.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Call CB Timeout(504).")
	    	.process(dbrevFaultProcessor).marshal(debitReversalResponseJDF)
			.continued(true);

		onException(Exception.class)
			.log(LoggingLevel.ERROR, "[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] Call Corebank Error.")
			.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    	.process(dbrevFaultProcessor).marshal(debitReversalResponseJDF)
			.continued(true);

		from("seda:debitreversal").routeId("komi.debit_rev.seda")
			.to("direct:debitreversal");

		from("direct:debitreversal").routeId("komi.debit_rev")
			// build reversal message from rmw.getChnlCreditTransferRequest
			.process(debitReversalRequestProcessor)
			.marshal(debitReversalRequestJDF)

			//submit reversal
			.log(LoggingLevel.DEBUG, "komi.debit_rev", "[${header.cihubMsgName}:"
					+ "${exchangeProperty.prop_request_list.requestId}] POST {{komi.url.isoadapter.reversal}}")
			.log("[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] Request ISOAdapter: ${body}")
			
			.removeHeaders("hdr_*")
			.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
			.setHeader("HttpMethod", constant("POST"))
			
			.enrich()
				.simple("{{komi.url.isoadapter.reversal}}?bridgeEndpoint=true")
				.aggregationStrategy(enrichmentAggregator)

			.convertBodyTo(String.class)
			.log("[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] Response ISOAdapter: ${body}")
			.unmarshal(debitReversalResponseJDF)

			.wireTap("direct:savecbdebitrevr")
		;
		
		from("direct:savecbdebitrevr").routeId("komi.savecbdebitrevr")
			.log(LoggingLevel.DEBUG, "komi.savecbdebitrevr", 
				"[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] akan save Debit Reversal ke CB-log")
			.process(saveCbDebitReversalProc)
		;
		
	}
		
}
