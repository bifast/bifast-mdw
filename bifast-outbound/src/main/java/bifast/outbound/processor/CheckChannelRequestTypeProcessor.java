package bifast.outbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.RequestMessageWrapper;

@Component
public class CheckChannelRequestTypeProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		RequestMessageWrapper req = exchange.getIn().getBody(RequestMessageWrapper.class);

		if (!(null == req.getChnlAccountEnquiryRequest())) {
			exchange.getMessage().setHeader("hdr_msgType", "acctenqr");
			exchange.getMessage().setBody(req.getChnlAccountEnquiryRequest());
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getChnlAccountEnquiryRequest().getChannelRefId());
		}

		else if (!(null == req.getChnlCreditTransferRequest())) {
			exchange.getMessage().setHeader("hdr_msgType", "crdttrns");
			exchange.getMessage().setBody(req.getChnlCreditTransferRequest());
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getChnlCreditTransferRequest().getOrignReffId());
		}
		
	
		else if (!(null == req.getChnlPaymentStatusRequest())) {
			exchange.getMessage().setHeader("hdr_msgType", "pymtsts");
			exchange.getMessage().setBody(req.getChnlPaymentStatusRequest());
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getChnlPaymentStatusRequest().getIntrnRefId());
		}
		
		else if (!(null == req.getProxyRegistrationReq())) {
			exchange.getMessage().setHeader("hdr_msgType", "prxyrgst");
			exchange.getMessage().setBody(req.getProxyRegistrationReq());
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getProxyRegistrationReq().getIntrnRefId());
		}

		else if (!(null == req.getProxyResolutionReq())) {
			exchange.getMessage().setHeader("hdr_msgType", "prxyrslt");
			exchange.getMessage().setBody(req.getProxyResolutionReq());
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getProxyResolutionReq().getOrignReffId());
		}

	}

}
