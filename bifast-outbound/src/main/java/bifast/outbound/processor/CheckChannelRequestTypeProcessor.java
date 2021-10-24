package bifast.outbound.processor;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.RequestMessageWrapper;

@Component
public class CheckChannelRequestTypeProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list",RequestMessageWrapper.class );
		RequestMessageWrapper req = exchange.getIn().getBody(RequestMessageWrapper.class);

		if (!(null == req.getChnlAccountEnquiryRequest())) {
			rmw.setChnlAccountEnquiryRequest(req.getChnlAccountEnquiryRequest());
			rmw.setMsgName("AEReq");
			exchange.getMessage().setHeader("hdr_msgType", "AEReq");
			exchange.getMessage().setBody(req.getChnlAccountEnquiryRequest());
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getChnlAccountEnquiryRequest().getChannelRefId());
			rmw.setRequestId(req.getChnlAccountEnquiryRequest().getChannelRefId());

		}

		else if (!(null == req.getChnlCreditTransferRequest())) {
			rmw.setChnlCreditTransferRequest(req.getChnlCreditTransferRequest());
			rmw.setMsgName("CTReq");
			exchange.getMessage().setHeader("hdr_msgType", "CTReq");
			exchange.getMessage().setBody(req.getChnlCreditTransferRequest());
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getChnlCreditTransferRequest().getChannelRefId());
			rmw.setRequestId(req.getChnlCreditTransferRequest().getChannelRefId());
		}
		
	
		else if (null != req.getChnlPaymentStatusRequest()) {
			rmw.setChnlPaymentStatusRequest(req.getChnlPaymentStatusRequest());
			rmw.setMsgName("PSReq");
			rmw.setRequestId(req.getChnlPaymentStatusRequest().getChannelRefId());
		}
		
		else if (!(null == req.getChnlProxyRegistrationRequest())) {
			rmw.setChnlProxyRegistrationRequest(req.getChnlProxyRegistrationRequest());
			rmw.setMsgName("PrxRegnReq");
			rmw.setRequestId(req.getChnlProxyRegistrationRequest().getChannelRefId());
			exchange.getMessage().setBody(req.getChnlProxyRegistrationRequest());
		}
		
		else if (!(null == req.getChnlProxyResolutionRequest())) {
			rmw.setChnlProxyResolutionRequest(req.getChnlProxyResolutionRequest());
			rmw.setMsgName("ProxyResolution");
			rmw.setRequestId(req.getChnlProxyResolutionRequest().getChannelRefId());
			exchange.getMessage().setBody(req.getChnlProxyResolutionRequest());
		}

		exchange.getMessage().setHeader("hdr_request_list", rmw);

	}

}
