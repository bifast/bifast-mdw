package bifast.outbound.accountenquiry.processor;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.chnlrequest.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.pojo.chnlresponse.ChnlProxyResolutionResponsePojo;

@Component
public class AEProxyEnrichment implements AggregationStrategy{

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

		ChnlProxyResolutionResponsePojo newx = newExchange.getMessage().getBody(ChnlProxyResolutionResponsePojo.class);
		ChnlAccountEnquiryRequestPojo old = oldExchange.getMessage().getBody(ChnlAccountEnquiryRequestPojo.class);
		
		old.setCreditorAccountNumber(newx.getAccountNumber());
		oldExchange.getMessage().setBody(old);
		return oldExchange;
	}

}
