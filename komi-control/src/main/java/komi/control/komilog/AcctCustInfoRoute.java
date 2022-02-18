package komi.control.komilog;

import java.time.format.DateTimeFormatter;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import komi.control.balanceinq.EnrichmentAggregator;

@Component
public class AcctCustInfoRoute extends RouteBuilder{
    @Autowired EnrichmentAggregator enrichmentAggr;
    
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");


	@Override
	public void configure() throws Exception {
		
		rest("/log")
			.post("/inbound")
				.consumes("application/json")
				.to("direct:loginbound")
		;

		from("direct:loginbound").routeId("komi.log.inbound")

			

		
		;
	}

}
