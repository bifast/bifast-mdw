package bifast.outbound.processor;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class AccountEnquiryAggregator implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		oldExchange.getIn().setBody(newExchange.getIn().getBody());
		return oldExchange;
	}

}
