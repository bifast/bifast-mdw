package komi.control.komilog;

import java.time.format.DateTimeFormatter;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import komi.control.balanceinq.EnrichmentAggregator;

@Component
public class ReadLogRoute extends RouteBuilder{
    @Autowired EnrichmentAggregator enrichmentAggr;
    @Autowired ReadInboundLogProcessor readInboundLogPrc;
    @Autowired ReadOutboundLogProcessor readOutboundLogPrc;
    
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

	@Override
	public void configure() throws Exception {
		
		rest("/log")
			.get("/inbound")
				.produces("text/plain")
				.to("direct:readinboundlog")
			.get("/outbound")
				.produces("text/plain")
				.to("direct:readoutboundlog")
		;

		
		from("direct:readinboundlog")
			.process(readInboundLogPrc)
		;

		from("direct:readoutboundlog")
			.process(readOutboundLogPrc)
	;

	}

}
