package bifast.outbound.proxyregistration;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.flat.FlatPrxy004Pojo;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationRequestPojo;

@Component
public class ProxyEnrichmentAggregator implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		
		FlatPrxy004Pojo newBody = newExchange.getMessage().getBody(FlatPrxy004Pojo.class);
		
		RequestMessageWrapper rmw = oldExchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		
		if (newBody.getResponseCode().equals("RJCT")) {
			oldExchange.getMessage().setBody(newBody);
		}
		
		else {
			
			ChnlProxyRegistrationRequestPojo regnReq = rmw.getChnlProxyRegistrationRequest();
			
			regnReq.setRegistrationId(newBody.getRegistrationId());
			
			regnReq.setRegisterBic(newBody.getRegisterBank());
			
			if (null == regnReq.getDisplayName()) 
				regnReq.setDisplayName(newBody.getDisplayName());

			if (null == regnReq.getAccountName()) 
				regnReq.setAccountName(newBody.getAccountName());

			if (null == regnReq.getAccountName()) 
				regnReq.setCustomerType(newBody.getCustomerType());

			if (null == regnReq.getCustomerId()) 
				regnReq.setCustomerId(newBody.getCustomerId());

			if (null == regnReq.getResidentialStatus()) 
				regnReq.setResidentialStatus(newBody.getResidentialStatus());

			if (null == regnReq.getTownName()) 
				regnReq.setTownName(newBody.getTownName());
			
			rmw.setChnlProxyRegistrationRequest(regnReq);
			oldExchange.getMessage().setHeader("hdr_request_list", rmw);
			oldExchange.getMessage().setBody(regnReq);
		}
		
		
		return oldExchange;
	}

}
