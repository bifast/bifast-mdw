package bifast.outbound.processor;


import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.pojo.ChnlRequestWrapper;
import bifast.outbound.pojo.RequestMessageWrapper;

@Component
public class CheckChannelRequestTypeProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list",RequestMessageWrapper.class );
		
//		RequestMessageWrapper req = exchange.getIn().getBody(RequestMessageWrapper.class);
		ChnlRequestWrapper req = exchange.getIn().getBody(ChnlRequestWrapper.class);
		
		LocalDateTime ldt = LocalDateTime.ofInstant(rmw.getKomiStart(), ZoneOffset.systemDefault());

		if (!(null == req.getChnlAccountEnquiryRequest())) {
//			rmw.setChnlAccountEnquiryRequest(req.getChnlAccountEnquiryRequest());
			rmw.setChannelRequest(req.getChnlAccountEnquiryRequest());
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
			rmw.setChannelRequest(req.getChnlPaymentStatusRequest());
			rmw.setMsgName("PSReq");
			rmw.setRequestId(req.getChnlPaymentStatusRequest().getChannelRefId());
		}
		
		else if (!(null == req.getChnlProxyRegistrationRequest())) {
			rmw.setChnlProxyRegistrationRequest(req.getChnlProxyRegistrationRequest());
			rmw.setChannelRequest(req.getChnlProxyRegistrationRequest());
			rmw.setMsgName("PrxRegn");
			rmw.setRequestId(req.getChnlProxyRegistrationRequest().getChannelRefId());
			exchange.getMessage().setBody(req.getChnlProxyRegistrationRequest());
		}
		
		else if (!(null == req.getChnlProxyResolutionRequest())) {
			rmw.setChnlProxyResolutionRequest(req.getChnlProxyResolutionRequest());
			rmw.setChannelRequest(req.getChnlProxyResolutionRequest());
			rmw.setMsgName("PrxResl");
			rmw.setRequestId(req.getChnlProxyResolutionRequest().getChannelRefId());
			exchange.getMessage().setBody(req.getChnlProxyResolutionRequest());
		}
		
		else if (!(null == req.getChnlProxyRegistrationInquiryRequest())) {
			rmw.setChnlProxyRegistrationInquiryRequest(req.getChnlProxyRegistrationInquiryRequest());
			rmw.setChannelRequest(req.getChnlProxyRegistrationInquiryRequest());
			rmw.setMsgName("PrxRegInq");
			rmw.setRequestId(req.getChnlProxyRegistrationInquiryRequest().getChannelRefId());
			exchange.getMessage().setBody(req.getChnlProxyRegistrationInquiryRequest());
		}

		else if (null != req.getChnlAccountCstmrInfoRequest()) {
			rmw.setChannelRequest(req.getChnlAccountCstmrInfoRequest());
			rmw.setMsgName("ACReq");
			rmw.setRequestId(req.getChnlAccountCstmrInfoRequest().getNoRef());
			exchange.getMessage().setBody(req.getChnlAccountCstmrInfoRequest());
		}

		exchange.getMessage().setHeader("cihubMsgName", rmw.getMsgName());
		exchange.setProperty("prop_request_list", rmw);

	}

}
