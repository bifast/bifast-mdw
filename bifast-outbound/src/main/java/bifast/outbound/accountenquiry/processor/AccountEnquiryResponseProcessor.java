package bifast.outbound.accountenquiry.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.outbound.model.StatusReason;
import bifast.outbound.pojo.ChnlFailureResponsePojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.pojo.chnlresponse.ChnlAccountEnquiryResponsePojo;
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
		channelResponseWr.setResponseMessage("Success/ Transaction Accepted");
		channelResponseWr.setDate(LocalDateTime.now().format(dateformatter));
		channelResponseWr.setTime(LocalDateTime.now().format(timeformatter));
		channelResponseWr.setContent(new ArrayList<>());

		Object objBody = exchange.getMessage().getBody(Object.class);
		String bodyClass = objBody.getClass().getSimpleName();
		
//		ChnlAccountEnquiryRequestPojo chnReq = exchange.getMessage().getHeader("ae_channel_request",ChnlAccountEnquiryRequestPojo.class);
		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list",RequestMessageWrapper.class);
		ChnlAccountEnquiryRequestPojo chnReq = rmw.getChnlAccountEnquiryRequest();
		
		ChnlAccountEnquiryResponsePojo chnResp = new ChnlAccountEnquiryResponsePojo();
		chnResp.setOrignReffId(chnReq.getIntrnRefId());

		if (bodyClass.equals("ChnlFailureResponsePojo")) {
			ChnlFailureResponsePojo fault = (ChnlFailureResponsePojo)objBody;
		
			channelResponseWr.setResponseCode(fault.getErrorCode());
			if (fault.getErrorCode().equals("ERROR-KM"))
				channelResponseWr.setResponseMessage("KOMI Internal Error");
			else if (fault.getErrorCode().equals("ERROR-CIHUB"))
				channelResponseWr.setResponseMessage("CIHUB Internal Error");
			else
				channelResponseWr.setResponseMessage("KOMI Error");
			
		}

		else {
			
			BusinessMessage bm = (BusinessMessage)objBody;
						
			PaymentTransaction110 biResp = bm.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);
			
			channelResponseWr.setResponseCode(biResp.getStsRsnInf().get(0).getRsn().getPrtry());
			
			Optional<StatusReason> optStatusReason = statusReasonRepo.findById(channelResponseWr.getResponseCode());
			if (optStatusReason.isPresent()) {
				String desc = optStatusReason.get().getDescription();
				channelResponseWr.setResponseMessage(desc);
			}	
			
			chnResp.setCreditorAccountNumber(chnReq.getCreditorAccountNumber());					
			chnResp.setCreditorAccountType(biResp.getOrgnlTxRef().getCdtrAcct().getTp().getPrtry());
			
			if (null != biResp.getOrgnlTxRef().getCdtr())
				chnResp.setCreditorName(biResp.getOrgnlTxRef().getCdtr().getPty().getNm());
			
			if (biResp.getSplmtryData().size()>0) {
				if (null != biResp.getSplmtryData().get(0).getEnvlp().getCdtr()) {
					if (null != biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getId())
						chnResp.setCreditorId(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getId());
					if (null != biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTp())
						chnResp.setCreditorType(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTp());
					if (null != biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getRsdntSts())
						chnResp.setCreditorResidentStatus(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getRsdntSts());
					if (null != biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTwnNm())
						chnResp.setCreditorTownName(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTwnNm());
				}
			}			
		}

		channelResponseWr.getContent().add(chnResp);

		exchange.getMessage().setBody(channelResponseWr);
	}

}
