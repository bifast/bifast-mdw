package bifast.inbound.processor;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EnrichmentAggregator implements AggregationStrategy {
	private static Logger logger = LoggerFactory.getLogger(EnrichmentAggregator.class);
	
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		logger.debug("Akan proses response");
		oldExchange.getIn().setBody(newExchange.getIn().getBody());
		return oldExchange;
	}

}
