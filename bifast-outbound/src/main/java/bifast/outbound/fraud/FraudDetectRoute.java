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
import bifast.outbound.fraud.dao.FdsResponseList;
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
		JacksonDataFormat outputJDF = jdfService.basic(FdsResponseList.class);
		
		from("direct:call-fds").routeId("komi.fds")

			.setHeader("tmp_body", simple("${body}"))
			
			.process(fdsInputProc)
			.marshal(inputJDF)		
			.log("[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] call FDS: ${body}")
			
			.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.setHeader("PS-API-Key", constant("d9657164-83ff-4641-a9a9-edb30c8b78b9"))
				
				.enrich()
					.simple("{{komi.url.fds}}?"
						+ "socketTimeout=2000&bridgeEndpoint=true")
					.aggregationStrategy(enrichmentAggregator)
				
				.convertBodyTo(String.class)				
				.log("[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] response FDS: ${body}")
				
				.unmarshal(outputJDF)
								
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						FdsResponseList responseList = exchange.getMessage().getBody(FdsResponseList.class);
						FdsResponseDAO response = responseList.get(0);
						if (response.getTransaction().getRuleDecision().equals("02"))
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
