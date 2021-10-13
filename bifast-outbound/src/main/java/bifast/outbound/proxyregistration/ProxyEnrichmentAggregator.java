package bifast.outbound.proxyregistration;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlProxyRegistrationRequestPojo;

@Component
public class ProxyEnrichmentAggregator implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		BusinessMessage newBody = newExchange.getMessage().getBody(BusinessMessage.class);
		String regnId = newBody.getDocument().getPrxyLookUpRspn().getLkUpRspn().getRegnRspn().getRegn().getRegnId();
		
		BusinessMessage oldBody = oldExchange.getMessage().getBody(BusinessMessage.class);
		RequestMessageWrapper rmw = oldExchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		ChnlProxyRegistrationRequestPojo regnReq = rmw.getChnlProxyRegistrationRequest();
		regnReq.setRegistrationId(regnId);
		rmw.setChnlProxyRegistrationRequest(regnReq);
		oldExchange.getMessage().setHeader("hdr_request_list", rmw);
		
		return oldExchange;
	}

}
