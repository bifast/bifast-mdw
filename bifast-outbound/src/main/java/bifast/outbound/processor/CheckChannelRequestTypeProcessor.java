package bifast.outbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.ChannelRequestWrapper;

@Component
public class CheckChannelRequestTypeProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		ChannelRequestWrapper req = exchange.getIn().getBody(ChannelRequestWrapper.class);
		
		if (!(null == req.getAccountEnquiryRequest())) {
			exchange.getMessage().setHeader("hdr_msgType", "acctenqr");
			exchange.getMessage().setBody(req.getAccountEnquiryRequest());
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getAccountEnquiryRequest().getChannelRefId());
		}

		else if (!(null == req.getCreditTransferRequest())) {
			exchange.getMessage().setHeader("hdr_msgType", "crdttrns");
			exchange.getMessage().setBody(req.getCreditTransferRequest());
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getCreditTransferRequest().getOrignReffId());
		}
		
		else if  (!(null == req.getFiCreditTransferRequest())) {
			exchange.getMessage().setHeader("hdr_msgType", "ficrdttrns");
			exchange.getMessage().setBody(req.getFiCreditTransferRequest());
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getFiCreditTransferRequest().getIntrnRefId());
		}
		
		else if (!(null == req.getPaymentStatusRequest())) {
			exchange.getMessage().setHeader("hdr_msgType", "pymtsts");
			exchange.getMessage().setBody(req.getPaymentStatusRequest());
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getPaymentStatusRequest().getChannelRefId());
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
