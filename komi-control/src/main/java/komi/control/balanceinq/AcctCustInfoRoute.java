package komi.control.balanceinq;

import java.time.format.DateTimeFormatter;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AcctCustInfoRoute extends RouteBuilder{
//    @Autowired JacksonDataFormatService jdfService;
    @Autowired EnrichmentAggregator enrichmentAggr;
    
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");


	@Override
	public void configure() throws Exception {
		
		rest("/api/v1/adapter")
			.post("/balanceinquiry")
				.consumes("application/json")
				.to("direct:balanceinquiry")
		;

		from("direct:balanceinquiry").routeId("komi.balanceinquiry")

			.convertBodyTo(String.class)
			
			.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.enrich()
					.simple("{{isoadapter.url.balanceinquiry}}?"
						+ "socketTimeout=5000&" 
						+ "bridgeEndpoint=true")
					.aggregationStrategy(enrichmentAggr)
				
				.convertBodyTo(String.class)				
				.log("CB response: ${body}")
		
			.endDoTry()
			.doCatch(java.net.SocketTimeoutException.class)
				.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] CI-HUB TIMEOUT.")
			.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Call CI-HUB Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
			.end()

		
		;
	}

}
