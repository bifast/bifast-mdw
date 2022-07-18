package bifast.outbound.accountcustmrinfo;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.AccountCustInfoRequestDTO;
import bifast.outbound.corebank.pojo.AccountCustInfoResponseDTO;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class CbAccountInfoRoute extends RouteBuilder{
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private JacksonDataFormatService jdfService;
	
	@Override
	public void configure() throws Exception {
		JacksonDataFormat aciRequestJDF = jdfService.basic(AccountCustInfoRequestDTO.class);
		JacksonDataFormat aciResponseJDF = jdfService.basic(AccountCustInfoResponseDTO.class);
		
		onException(HttpOperationFailedException.class).onWhen(simple("${exception.statusCode} == '504'")) 
			.maximumRedeliveries(1).redeliveryDelay(5000)
			.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Corebank TIMEOUT: \n ${exception.message}") 
			.setProperty("pr_response", constant("CB-TIMEOUT"))
			.setProperty("pr_reason", constant("U900"))
			.continued(true);
		onException(Exception.class)
			.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Caught exception ${exception.class}: \n ${exception.message}")
			.setProperty("pr_response", constant("CB-ERROR"))
			.setProperty("pr_reason", constant("U901"))
			.continued(true);

		// ROUTE CALLCB 
		from("direct:accinfo").routeId("komi.cb.accinfo")
			.removeHeaders("*")

			.log(LoggingLevel.DEBUG,"komi.cb.accinfo", "[${exchangeProperty.prop_request_list.msgName}:"
					+ "${exchangeProperty.prop_request_list.requestId}] Terima di corebank: ${body}")
					
			.marshal(aciRequestJDF)
			
	 		.log(LoggingLevel.DEBUG, "komi.cb.accinfo", "[${exchangeProperty.prop_request_list.msgName}:"
	 				+ "${exchangeProperty.prop_request_list.requestId}] POST {{komi.url.isoadapter.customerinfo}}")
	 		.log("[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] CB Request: ${body}")
			
			.setProperty("pr_response", constant("CB"))

			.setHeader("HttpMethod", constant("POST"))
			.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
			.enrich()
				.simple("{{komi.url.isoadapter.customerinfo}}?bridgeEndpoint=true")
				.aggregationStrategy(enrichmentAggregator)

			.convertBodyTo(String.class)
			
			.filter().simple("${exchangeProperty.pr_response} == 'CB' ")
			
			.log("[${exchangeProperty.prop_request_list.msgName}:"
					+ "${exchangeProperty.prop_request_list.requestId}] CB response: ${body}")

			.unmarshal(aciResponseJDF)

			.setProperty("pr_response", simple("${body.status}"))
			.setProperty("pr_reason", simple("${body.reason}"))
		;
		
	}
}
