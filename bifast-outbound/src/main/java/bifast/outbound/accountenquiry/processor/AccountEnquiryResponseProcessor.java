package bifast.outbound.accountenquiry.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryResponsePojo;
import bifast.outbound.model.StatusReason;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.flat.FlatPacs002Pojo;
import bifast.outbound.pojo.flat.FlatPrxy004Pojo;
import bifast.outbound.repository.StatusReasonRepository;

@Component
public class AccountEnquiryResponseProcessor implements Processor {

    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

    @Autowired
    private StatusReasonRepository statusReasonRepo;
    
	@Override
	public void process(Exchange exchange) throws Exception {

		ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
		channelResponseWr.setResponseCode("U000");
		channelResponseWr.setDate(LocalDateTime.now().format(dateformatter));
		channelResponseWr.setTime(LocalDateTime.now().format(timeformatter));
		channelResponseWr.setResponses(new ArrayList<>());

		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list",RequestMessageWrapper.class);
		ChnlAccountEnquiryRequestPojo chnReq = (ChnlAccountEnquiryRequestPojo) rmw.getChannelRequest();
		
		ChnlAccountEnquiryResponsePojo chnResp = new ChnlAccountEnquiryResponsePojo();
		chnResp.setOrignReffId(chnReq.getChannelRefId());

		Object objBody = exchange.getMessage().getBody(Object.class);
		if (objBody.getClass().getSimpleName().equals("FaultPojo")) {
			FaultPojo fault = (FaultPojo)objBody;
			
			channelResponseWr.setResponseCode(fault.getResponseCode());
			channelResponseWr.setReasonCode(fault.getReasonCode());
			if (null != fault.getReasonMessage())
				channelResponseWr.setReasonMessage(fault.getReasonMessage());
			else
				channelResponseWr.setResponseMessage("General Error");
		}
		
		else if (objBody.getClass().getSimpleName().equals("FlatPrxy004Pojo")) {
			FlatPrxy004Pojo prxRsltResponse = (FlatPrxy004Pojo) objBody;
			
			channelResponseWr.setResponseCode(prxRsltResponse.getResponseCode());
			channelResponseWr.setReasonCode(prxRsltResponse.getReasonCode());
			Optional<StatusReason> oStatusReason = statusReasonRepo.findById(prxRsltResponse.getReasonCode());
			if (oStatusReason.isPresent())
				channelResponseWr.setReasonMessage(oStatusReason.get().getDescription());
			else
				channelResponseWr.setReasonMessage("General Error");
			
			chnResp.setProxyId(prxRsltResponse.getProxyValue());			
			chnResp.setProxyType(prxRsltResponse.getProxyType());

			if (null != prxRsltResponse.getRegisterBank())
				chnResp.setRecipientBank(prxRsltResponse.getRegisterBank());

			if (null != prxRsltResponse.getAccountNumber()) {
				chnResp.setCreditorAccountNumber(prxRsltResponse.getAccountNumber());
				chnResp.setCreditorAccountType(prxRsltResponse.getAccountType());
			}
			
			if (null != prxRsltResponse.getDisplayName())
				chnResp.setCreditorName(prxRsltResponse.getDisplayName());
			
			if (null != prxRsltResponse.getCustomerId())
				chnResp.setCreditorId(prxRsltResponse.getCustomerId());

			if (null != prxRsltResponse.getCustomerType())
				chnResp.setCreditorType(prxRsltResponse.getCustomerType());
			
			if (null != prxRsltResponse.getResidentialStatus())
				chnResp.setCreditorResidentStatus(prxRsltResponse.getResidentialStatus());
			
			if (null != prxRsltResponse.getTownName())
				chnResp.setCreditorTownName(prxRsltResponse.getTownName());
		}

		else {
			
			FlatPacs002Pojo bm = (FlatPacs002Pojo)objBody;
									
			channelResponseWr.setResponseCode(bm.getTransactionStatus());
			channelResponseWr.setReasonCode(bm.getReasonCode());
			
			Optional<StatusReason> optStatusReason = statusReasonRepo.findById(channelResponseWr.getReasonCode());
			if (optStatusReason.isPresent()) {
				String desc = optStatusReason.get().getDescription();
				channelResponseWr.setReasonMessage(desc);
			}	
			
			chnResp.setRecipientBank(chnReq.getRecptBank());
				
			chnResp.setCreditorAccountNumber(bm.getCdtrAcctId());
			chnResp.setCreditorAccountType(bm.getCdtrAcctTp());
			
			if (null != chnReq.getProxyId())
				chnResp.setProxyId(chnReq.getProxyId());
			
			if (null != chnReq.getProxyType())
				chnResp.setProxyType(chnReq.getProxyType());

			if (null != bm.getCdtrNm())
				chnResp.setCreditorName(bm.getCdtrNm());
			
			if (null != bm.getCdtrId())
				chnResp.setCreditorId(bm.getCdtrId());

			if (null != bm.getCdtrTp())
				chnResp.setCreditorType(bm.getCdtrTp());
			
			if (null != bm.getCdtrRsdntSts())
				chnResp.setCreditorResidentStatus(bm.getCdtrRsdntSts());
			
			if (null != bm.getCdtrTwnNm())
				chnResp.setCreditorTownName(bm.getCdtrTwnNm());
		}

		channelResponseWr.getResponses().add(chnResp);

		exchange.getMessage().setBody(channelResponseWr);
	}

}
