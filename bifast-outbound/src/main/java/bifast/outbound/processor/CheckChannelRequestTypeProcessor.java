package bifast.outbound.processor;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlCreditTransferRequestPojo;
import bifast.outbound.pojo.chnlrequest.ChnlRequestWrapper;

@Component
public class CheckChannelRequestTypeProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list",RequestMessageWrapper.class );
		
//		RequestMessageWrapper req = exchange.getIn().getBody(RequestMessageWrapper.class);
		ChnlRequestWrapper req = exchange.getIn().getBody(ChnlRequestWrapper.class);
		
		LocalDateTime ldt = LocalDateTime.ofInstant(rmw.getKomiStart(), ZoneOffset.systemDefault());

		if (!(null == req.getChnlAccountEnquiryRequest())) {
			rmw.setChnlAccountEnquiryRequest(req.getChnlAccountEnquiryRequest());
			rmw.setMsgName("AEReq");
			exchange.getMessage().setHeader("hdr_msgType", "AEReq");
			exchange.getMessage().setBody(req.getChnlAccountEnquiryRequest());
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getChnlAccountEnquiryRequest().getChannelRefId());
			rmw.setRequestId(req.getChnlAccountEnquiryRequest().getChannelRefId());

		}

		else if (!(null == req.getChnlCreditTransferRequest())) {
			ChnlCreditTransferRequestPojo ctReq = req.getChnlCreditTransferRequest();
			ctReq.setDateTime(ldt);
			rmw.setChnlCreditTransferRequest(ctReq);
			rmw.setChannelRequest(req.getChnlCreditTransferRequest());
			rmw.setTerminalId(req.getChnlCreditTransferRequest().getTerminalId());
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
		
		else if (!(null == req.getChnlProxyRegistrationInquiryRequest())) {
			rmw.setChnlProxyRegistrationInquiryRequest(req.getChnlProxyRegistrationInquiryRequest());
			rmw.setMsgName("PrxRegnInquiryReq");
			rmw.setRequestId(req.getChnlProxyRegistrationInquiryRequest().getChannelRefId());
			exchange.getMessage().setBody(req.getChnlProxyRegistrationInquiryRequest());
		}

		else if (null != req.getChnlAccountCstmrInfoRequest()) {
			rmw.setChannelRequest(req.getChnlAccountCstmrInfoRequest());
			rmw.setMsgName("AcctCustmrInfo");
			rmw.setRequestId(req.getChnlAccountCstmrInfoRequest().getNoRef());
			exchange.getMessage().setBody(req.getChnlAccountCstmrInfoRequest());
		}

		exchange.getMessage().setHeader("hdr_request_list", rmw);

	}

}
