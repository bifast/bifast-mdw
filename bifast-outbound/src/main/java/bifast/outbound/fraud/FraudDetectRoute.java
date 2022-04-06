package bifast.outbound.fraud;


import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.fraud.dao.FdsInputDAO;
import bifast.outbound.fraud.dao.FdsResponseDAO;
import bifast.outbound.fraud.dao.FdsResponseListDAO;
import bifast.outbound.fraud.processor.FdsInputProcessor;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class FraudDetectRoute extends RouteBuilder {

	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private FdsInputProcessor fdsInputProc;
	
	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat inputJDF = jdfService.basic(FdsInputDAO.class);
		JacksonDataFormat outputJDF = jdfService.wrapUnwrapRoot(FdsResponseListDAO.class);
		
		// ** ROUTE GENERAL UNTUK POSTING KE CI-HUB ** //
		from("direct:call-fds").routeId("komi.fds")

			.setHeader("tmp_body", simple("${body}"))
			
			.process(fdsInputProc)
			.marshal(inputJDF)		
			.log("[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] call FDS: ${body}")
			
			.doTry()
				.setHeader("HttpMethod", constant("POST"))

				.enrich()
					.simple("{{komi.url.fds}}?"
						+ "socketTimeout=1000&bridgeEndpoint=true")
					.aggregationStrategy(enrichmentAggregator)
				
				.convertBodyTo(String.class)				
				.log("[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] response FDS: ${body}")
				
				.unmarshal(outputJDF)
								
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						FdsResponseListDAO responseList = exchange.getMessage().getBody(FdsResponseListDAO.class);
						FdsResponseDAO response = responseList.getResponseList().get(0);
						if (response.getRuleDecision().equals("02"))
							exchange.setProperty("prop_fds", "Decline");
						else
							exchange.setProperty("prop_fds", "Pass");
					}
				})
			
			.endDoTry()
			.doCatch(java.net.SocketTimeoutException.class)
				.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] FDS response TIMEOUT.")
		    	.setProperty("prop_fds", constant("Pass"))
			.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] FDS response Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
		    	.setProperty("prop_fds", constant("Pass"))
	    	.end()
	
	    	.filter().simple("${exchangeProperty.prop_fds} != 'Pass'")
	    		.log("Save ke log sekarang")
	    	
	    	.end()
	    	
	    	.setBody(simple("${header.tmp_body}"))
			.removeHeader("tmp_body")
		;

	
	}		
}
