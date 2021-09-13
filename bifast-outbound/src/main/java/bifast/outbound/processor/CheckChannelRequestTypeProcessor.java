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
//			exchange.getMessage().setHeader("hdr_channelRequest", req.getAccountEnquiryRequest());
			exchange.getMessage().setBody(req.getAccountEnquiryRequest());
		}

		else if (!(null == req.getCreditTransferRequest())) {
			exchange.getMessage().setHeader("hdr_msgType", "crdttrns");
//			exchange.getMessage().setHeader("hdr_channelRequest", req.getCreditTransferRequest());
//			exchange.getMessage().setHeader("rcv_intrnRefId", req.getCreditTransferRequest().getOrignReffId());
			exchange.getMessage().setBody(req.getCreditTransferRequest());
		}
		
		else if  (!(null == req.getFiCreditTransferRequest())) {
			exchange.getMessage().setHeader("hdr_msgType", "ficrdttrns");
//			exchange.getMessage().setHeader("hdr_channelRequest", req.getFiCreditTransferRequest());
//			exchange.getMessage().setHeader("rcv_intrnRefId", req.getFiCreditTransferRequest().getOrignReffId());
			exchange.getMessage().setBody(req.getFiCreditTransferRequest());
		}
		
		else if (!(null == req.getPaymentStatusRequest())) {
			exchange.getMessage().setHeader("hdr_msgType", "pymtsts");
//			exchange.getMessage().setHeader("hdr_channelRequest", req.getPaymentStatusRequest());
//			exchange.getMessage().setHeader("rcv_intrnRefId", req.getPaymentStatusRequest().getOrignReffId());
			exchange.getMessage().setBody(req.getPaymentStatusRequest());
		}
		
//		else if (!(null == req.getReverseCreditTransferRequest())) {
//			exchange.getMessage().setHeader("rcv_msgType", "ReverseCreditTransfer");
//			exchange.getMessage().setHeader("rcv_channel", req.getReverseCreditTransferRequest());
//		}

		else if (!(null == req.getProxyRegistrationReq())) {
			exchange.getMessage().setHeader("hdr_msgType", "prxyrgst");
//			exchange.getMessage().setHeader("hdr_channelRequest", req.getProxyRegistrationReq());
			exchange.getMessage().setBody(req.getProxyRegistrationReq());

//			exchange.getMessage().setHeader("rcv_intrnRefId", req.getProxyRegistrationReq().getOrignReffId());
		}

		else if (!(null == req.getProxyResolutionReq())) {
			exchange.getMessage().setHeader("hdr_msgType", "prxyrslt");
//			exchange.getMessage().setHeader("hdr_channelRequest", req.getProxyResolutionReq());
			exchange.getMessage().setBody(req.getProxyResolutionReq());
		}

	}

}
