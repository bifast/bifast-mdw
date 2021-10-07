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
	@Autowired
	private ChannelRepository channelRepo;
	@Autowired
	private UtilService utilService;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		RequestMessageWrapper req = exchange.getIn().getBody(RequestMessageWrapper.class);

		String clientId = exchange.getMessage().getHeader("clientId", String.class);
		String channelType = channelRepo.findById(clientId).orElse(new Channel()).getChannelType();
		String komiTrnsId = utilService.genKomiTrnsId();
		
		RequestMessageWrapper rmw = new RequestMessageWrapper();

		rmw.setChannelId(clientId);
		rmw.setChannelType(channelType);
		rmw.setKomiTrxId(komiTrnsId);
		rmw.setRequestTime(LocalDateTime.now());
		
//		List<DomainCode> listDM = domainCodeRepo.findByGrp("REQUEST.METHOD.CODE");
//		for (DomainCode dm : listDM) {
//			try {
//				Method mtd = req.getClass().getMethod(dm.getKey());
////				Object body = (Object) mtd.invoke(req);
//				rmw.setMsgName(dm.getValue());
//				exchange.getMessage().setBody(mtd.invoke(req));
//			} catch (NoSuchMethodException e) {}
//		}
		
		
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
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getChnlCreditTransferRequest().getOrignReffId());
			rmw.setRequestId(req.getChnlCreditTransferRequest().getOrignReffId());
		}
		
	
		else if (!(null == req.getChnlPaymentStatusRequest())) {
			exchange.getMessage().setHeader("hdr_msgType", "pymtsts");
			exchange.getMessage().setBody(req.getChnlPaymentStatusRequest());
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getChnlPaymentStatusRequest().getIntrnRefId());
			rmw.setRequestId(req.getChnlPaymentStatusRequest().getIntrnRefId());
		}
		
		else if (!(null == req.getProxyRegistrationReq())) {
			exchange.getMessage().setHeader("hdr_msgType", "prxyrgst");
			exchange.getMessage().setBody(req.getProxyRegistrationReq());
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getProxyRegistrationReq().getIntrnRefId());
			rmw.setRequestId(req.getProxyRegistrationReq().getIntrnRefId());
		}
		
		else if (!(null == req.getProxyResolutionReq())) {
			exchange.getMessage().setHeader("hdr_msgType", "prxyrslt");
			exchange.getMessage().setBody(req.getProxyResolutionReq());
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getProxyResolutionReq().getOrignReffId());
			exchange.getMessage().setHeader("hdr_chnlRefId", req.getProxyResolutionReq().getOrignReffId());
		}

		exchange.getMessage().setHeader("hdr_request_list", rmw);

	}

}
