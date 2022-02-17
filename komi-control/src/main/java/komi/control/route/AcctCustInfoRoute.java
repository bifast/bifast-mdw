package komi.control.route;

import java.time.format.DateTimeFormatter;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import komi.control.balanceinq.dto.BalanceInquiryRequest;
import komi.control.balanceinq.dto.BalanceInquiryResponse;
import komi.control.service.JacksonDataFormatService;


@Component
public class AcctCustInfoRoute extends RouteBuilder{
    @Autowired JacksonDataFormatService jdfService;
    @Autowired EnrichmentAggregator enrichmentAggr;
    
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");


	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat balRequestJDF = jdfService.wrapUnwrapRoot(BalanceInquiryRequest.class);
		JacksonDataFormat balResponseJDF = jdfService.wrapUnwrapRoot(BalanceInquiryResponse.class);

		from("direct:balanceinquiry").routeId("komi.util.balanceinquiry")

			.marshal(balRequestJDF)
			// build request msg

			.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.enrich()
					.simple("{{isoadapter.url.balanceinquiry}}?"
						+ "socketTimeout=5000&" 
						+ "bridgeEndpoint=true")
					.aggregationStrategy(enrichmentAggr)
				
				.convertBodyTo(String.class)				
				.log("CB response: ${body}")
						
				.unmarshal(balResponseJDF)
			
		
			.endDoTry()
			.doCatch(java.net.SocketTimeoutException.class)
				.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] CI-HUB TIMEOUT.")
			.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Call CI-HUB Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
			.end()

			
			// process resposne

			
		;
	}

}
