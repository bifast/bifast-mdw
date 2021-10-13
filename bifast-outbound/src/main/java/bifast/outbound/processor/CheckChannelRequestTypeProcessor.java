package bifast.outbound.processor;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.Channel;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.ChannelRepository;
import bifast.outbound.service.UtilService;

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
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getChnlAccountEnquiryRequest().getIntrnRefId());
			rmw.setRequestId(req.getChnlAccountEnquiryRequest().getIntrnRefId());

		}

		else if (!(null == req.getChnlCreditTransferRequest())) {
			rmw.setChnlCreditTransferRequest(req.getChnlCreditTransferRequest());
			rmw.setMsgName("CTReq");
			exchange.getMessage().setHeader("hdr_msgType", "CTReq");
			exchange.getMessage().setBody(req.getChnlCreditTransferRequest());
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getChnlCreditTransferRequest().getIntrnRefId());
			rmw.setRequestId(req.getChnlCreditTransferRequest().getIntrnRefId());
		}
		
	
//		else if (!(null == req.getChnlPaymentStatusRequest())) {
//			exchange.getMessage().setHeader("hdr_msgType", "pymtsts");
//			exchange.getMessage().setBody(req.getChnlPaymentStatusRequest());
//			exchange.getMessage().setHeader("hdr_chnlRefId", req.getChnlPaymentStatusRequest().getIntrnRefId());
//			rmw.setRequestId(req.getChnlPaymentStatusRequest().getIntrnRefId());
//		}
		
		else if (!(null == req.getChnlProxyRegistrationRequest())) {
			rmw.setChnlProxyRegistrationRequest(req.getChnlProxyRegistrationRequest());
			rmw.setMsgName("PrxRegistrationReq");
			rmw.setRequestId(req.getChnlProxyRegistrationRequest().getIntrnRefId());
			exchange.getMessage().setHeader("hdr_msgType", "prxyrgst");
			exchange.getMessage().setBody(req.getChnlProxyRegistrationRequest());
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getChnlProxyRegistrationRequest().getIntrnRefId());
		}
		
		else if (!(null == req.getChnlProxyResolutionRequest())) {
			rmw.setChnlProxyResolutionRequest(req.getChnlProxyResolutionRequest());
			rmw.setMsgName("ProxyResolutionReq");
			rmw.setRequestId(req.getChnlProxyResolutionRequest().getIntrnRefId());
			exchange.getMessage().setHeader("hdr_msgType", "prxyrslt");
			exchange.getMessage().setBody(req.getChnlProxyResolutionRequest());
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getChnlProxyResolutionRequest().getIntrnRefId());
		}

		exchange.getMessage().setHeader("hdr_request_list", rmw);

	}

}
