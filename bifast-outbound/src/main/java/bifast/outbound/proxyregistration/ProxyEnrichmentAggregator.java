package bifast.outbound.proxyregistration;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.pojo.flat.FlatPrxy004Pojo;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationRequestPojo;

@Component
public class ProxyEnrichmentAggregator implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		
		FlatPrxy004Pojo pxrxResltn = newExchange.getMessage().getBody(FlatPrxy004Pojo.class);
		
		ResponseMessageCollection rmc = oldExchange.getMessage().getHeader("hdr_response_list", ResponseMessageCollection.class);
		rmc.setProxyResolutionResponse(pxrxResltn);
		oldExchange.getMessage().setHeader("hdr_response_list", rmc);
		
		RequestMessageWrapper rmw = oldExchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		ChnlProxyRegistrationRequestPojo regnReq = rmw.getChnlProxyRegistrationRequest();

		if (regnReq.getRegistrationType().equals("NEWR"))
			regnReq.setRegistrationId("");
		else {
			regnReq.setRegistrationId(pxrxResltn.getRegistrationId());
		}
		
		// jika mau activate dan dari resolution status SUSP
		if ((regnReq.getRegistrationType().equals("ACTV")) &&
			(pxrxResltn.getReasonCode().equals("U805")) ) {
			
			regnReq.setRegisterBic(pxrxResltn.getRegisterBank());
			
			if (null == regnReq.getDisplayName()) 
				regnReq.setDisplayName(pxrxResltn.getDisplayName());

			if (null == regnReq.getAccountNumber()) 
				regnReq.setAccountNumber(pxrxResltn.getAccountNumber());

			if (null == regnReq.getAccountType()) 
				regnReq.setAccountType(pxrxResltn.getAccountType());
			
			if (null == regnReq.getAccountName()) 
				regnReq.setAccountName(pxrxResltn.getAccountName());

			if (null == regnReq.getCustomerType()) 
				regnReq.setCustomerType(pxrxResltn.getCustomerType());

			if (null == regnReq.getCustomerId()) 
				regnReq.setCustomerId(pxrxResltn.getCustomerId());

			if (null == regnReq.getResidentialStatus()) 
				regnReq.setResidentialStatus(pxrxResltn.getResidentialStatus());

			if (null == regnReq.getTownName()) 
				regnReq.setTownName(pxrxResltn.getTownName());
			
			rmw.setChnlProxyRegistrationRequest(regnReq);
			oldExchange.getMessage().setHeader("hdr_request_list", rmw);
			oldExchange.getMessage().setBody(regnReq);

		}
			
		else if (pxrxResltn.getResponseCode().equals("RJCT")) {
			oldExchange.getMessage().setBody(pxrxResltn);
		}
		
		else {		
		
			regnReq.setRegisterBic(pxrxResltn.getRegisterBank());
			
			if (null == regnReq.getDisplayName()) 
				regnReq.setDisplayName(pxrxResltn.getDisplayName());

			if (null == regnReq.getAccountNumber()) 
				regnReq.setAccountNumber(pxrxResltn.getAccountNumber());

			if (null == regnReq.getAccountType()) 
				regnReq.setAccountType(pxrxResltn.getAccountType());
			
			if (null == regnReq.getAccountName()) 
				regnReq.setAccountName(pxrxResltn.getAccountName());

			if (null == regnReq.getCustomerType()) 
				regnReq.setCustomerType(pxrxResltn.getCustomerType());

			if (null == regnReq.getCustomerId()) 
				regnReq.setCustomerId(pxrxResltn.getCustomerId());

			if (null == regnReq.getResidentialStatus()) 
				regnReq.setResidentialStatus(pxrxResltn.getResidentialStatus());

			if (null == regnReq.getTownName()) 
				regnReq.setTownName(pxrxResltn.getTownName());
			
			rmw.setChnlProxyRegistrationRequest(regnReq);
			oldExchange.getMessage().setHeader("hdr_request_list", rmw);
			oldExchange.getMessage().setBody(regnReq);
		}
		
		return oldExchange;
	}

}
