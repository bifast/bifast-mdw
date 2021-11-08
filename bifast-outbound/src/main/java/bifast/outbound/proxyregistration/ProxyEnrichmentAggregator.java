package bifast.outbound.proxyregistration;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlProxyRegistrationRequestPojo;
import bifast.outbound.pojo.flat.FlatPrxy004Pojo;

@Component
public class ProxyEnrichmentAggregator implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		
		FlatPrxy004Pojo newBody = newExchange.getMessage().getBody(FlatPrxy004Pojo.class);
		String regnId = newBody.getRegistrationId();
		
		RequestMessageWrapper rmw = oldExchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		ChnlProxyRegistrationRequestPojo regnReq = rmw.getChnlProxyRegistrationRequest();
		regnReq.setRegistrationId(regnId);
		rmw.setChnlProxyRegistrationRequest(regnReq);
		oldExchange.getMessage().setHeader("hdr_request_list", rmw);
		
		return oldExchange;
	}

}
