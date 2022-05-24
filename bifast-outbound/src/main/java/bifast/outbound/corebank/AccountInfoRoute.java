package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.AccountCustInfoRequestDTO;
import bifast.outbound.corebank.pojo.AccountCustInfoResponseDTO;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class AccountInfoRoute extends RouteBuilder{
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private JacksonDataFormatService jdfService;
	
	@SuppressWarnings("deprecation")
	@Override
	public void configure() throws Exception {
		JacksonDataFormat aciRequestJDF = jdfService.basic(AccountCustInfoRequestDTO.class);
		JacksonDataFormat aciResponseJDF = jdfService.basic(AccountCustInfoResponseDTO.class);
		
		// ROUTE CALLCB 
		from("direct:accinfo").routeId("komi.cb.accinfo")
			.removeHeaders("*")

			.log(LoggingLevel.DEBUG,"komi.cb.accinfo", "[${exchangeProperty.prop_request_list.msgName}:"
					+ "${exchangeProperty.prop_request_list.requestId}] Terima di corebank: ${body}")
					
			.marshal(aciRequestJDF)
			
	 		.log(LoggingLevel.DEBUG, "komi.cb.accinfo", "[${exchangeProperty.prop_request_list.msgName}:"
	 				+ "${exchangeProperty.prop_request_list.requestId}] POST {{komi.url.isoadapter.customerinfo}}")
	 		.log("[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] CB Request: ${body}")
			
	 		.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
				.enrich().simple("{{komi.url.isoadapter.customerinfo}}?bridgeEndpoint=true")
				.aggregationStrategy(enrichmentAggregator)

				.convertBodyTo(String.class)
				
				.log("[${exchangeProperty.prop_request_list.msgName}:"
						+ "${exchangeProperty.prop_request_list.requestId}] CB response: ${body}")

 				.unmarshal(aciResponseJDF)

 				.setProperty("pr_response", simple("${body.status}"))
 				.setProperty("pr_reason", simple("${body.reason}"))
 			
	 		.endDoTry()
	    	.doCatch(org.apache.camel.http.common.HttpOperationFailedException.class).onWhen(simple("${exception.statusCode} == '504'"))
	    		.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Corebank TIMEOUT: \n ${exception.message}")
	    		.setProperty("pr_response", constant("CB-TIMEOUT"))
	    		.setProperty("pr_reason", constant("U900"))
	    	.doCatch(Exception.class)
	    		.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Caught exception ${exception.class}: \n ${exception.message}")
	    		.setProperty("pr_response", constant("CB-ERROR"))
	    		.setProperty("pr_reason", constant("U901"))
    		.doFinally()
    			.log("${exception.message}")
	    		.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						String cbResponse = exchange.getProperty("pr_response", String.class);
						ResponseMessageCollection respColl = exchange.getProperty("prop_response_list",ResponseMessageCollection.class );
						if ((cbResponse.equals("CB-TIMEOUT")) || (cbResponse.equals("CB-ERROR"))) {
							respColl.setCallStatus("CB-ERROR");
							respColl.setCbResponse("RJCT");
							respColl.setLastError(exchange.getProperty(Exchange.EXCEPTION_CAUGHT, String.class));
						}
//						else if (cbResponse.equals("RJCT")) {
//							respColl.setCallStatus("SUCCESS");
//							respColl.setCbResponse("RJCT");
//							respColl.setCbReason(exchange.getProperty("pr_reason", String.class));
//						}
//						else if (cbResponse.equals("ACTC")) {
//							respColl.setCallStatus("SUCCESS");
//							respColl.setCbResponse("ACTC");
//							respColl.setCorebankResponse(exchange.getMessage().getBody(Object.class));
//						}
						else {
							respColl.setCallStatus("SUCCESS");
							respColl.setCbResponse(cbResponse);
							respColl.setCorebankResponse(exchange.getMessage().getBody(Object.class));

						}
						exchange.setProperty("prop_response_list", respColl);
					}
	    		})
    		
			.end()

			
//			.removeHeaders("cb_*")
		;
		
		
	}

}
