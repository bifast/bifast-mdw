package bifast.outbound.proxyregistration;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.pojo.flat.FlatPrxy004Pojo;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationRequestPojo;

@Component
public class ProxyEnrichmentAggregator implements AggregationStrategy {

	private static Logger logger = LoggerFactory.getLogger(ProxyEnrichmentAggregator.class);

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		
		RequestMessageWrapper rmw = oldExchange.getProperty("prop_request_list", RequestMessageWrapper.class);
		ChnlProxyRegistrationRequestPojo regnReq = rmw.getChnlProxyRegistrationRequest();

		Object oPxrxResltn = newExchange.getMessage().getBody(Object.class);
		
//		logger.debug("ProxyResolution class : " + oPxrxResltn.getClass().getSimpleName());
		FlatPrxy004Pojo pxrxResltn = null;
		

		if (oPxrxResltn.getClass().getSimpleName().equals("FlatPrxy004Pojo")) {
			pxrxResltn = newExchange.getMessage().getBody(FlatPrxy004Pojo.class);
//			logger.debug("ProxyResolution response : " + pxrxResltn.getReasonCode());

			if (pxrxResltn.getReasonCode().equals("U000")) {
			
				ResponseMessageCollection rmc = oldExchange.getProperty("prop_response_list", ResponseMessageCollection.class);
				rmc.setProxyResolutionResponse(pxrxResltn);
				oldExchange.setProperty("prop_response_list", rmc);
	
				regnReq.setRegistrationId(pxrxResltn.getRegistrationId());
				rmw.setChnlProxyRegistrationRequest(regnReq);
				oldExchange.setProperty("prop_request_list", rmw);
				oldExchange.getMessage().setBody(regnReq);
				logger.debug("Dapat registration ID: " + regnReq.getRegistrationId());
			}
			
			else {  // result selain U000
				FaultPojo fault = new FaultPojo();
				fault.setCallStatus("ERROR");
				fault.setResponseCode(pxrxResltn.getResponseCode());
				fault.setReasonCode(pxrxResltn.getReasonCode());
				oldExchange.getMessage().setBody(fault);
			}
		}
		
		else if (oPxrxResltn.getClass().getSimpleName().equals("FaultPojo")) {
			FaultPojo fault = (FaultPojo) oPxrxResltn;
			oldExchange.getMessage().setBody(fault);
		}

		
		return oldExchange;
	}

}
