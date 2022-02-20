package komi.control.komilog;

import java.time.format.DateTimeFormatter;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import komi.control.balanceinq.EnrichmentAggregator;

@Component
public class ReadLogRoute extends RouteBuilder{
    @Autowired EnrichmentAggregator enrichmentAggr;
    @Autowired ReadZipFileProcessor readZip;
    
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");


	@Override
	public void configure() throws Exception {
		
		rest("/log")
			.get("/inbound")
				.produces("text/plain")
				.to("direct:readlog")
		;

		
		from("direct:readlog")
			.process(readZip)
		;
		
	}

}
