package bifast.outbound.accountenquiry.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.admi002.MessageRejectV01;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryResponsePojo;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.ChnlFailureResponsePojo;

@Component
public class AccountEnquiryResponseProcessor2 implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		Object objBody = exchange.getMessage().getBody(Object.class);
		String bodyClass = objBody.getClass().getSimpleName();
		
		ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
		ChnlFailureResponsePojo fault = new ChnlFailureResponsePojo();

		if (bodyClass.equals("ChnlFailureResponsePojo")) {
			fault = (ChnlFailureResponsePojo)objBody;
			channelResponseWr.setFaultResponse(fault);
		}
		
		else {
			BusinessMessage bm = (BusinessMessage)objBody;
			
			ChnlAccountEnquiryRequestPojo chnReq = exchange.getMessage().getHeader("ae_channel_request",ChnlAccountEnquiryRequestPojo.class);

			if (null != bm.getDocument().getFiToFIPmtStsRpt()) {   // jika bukan reject
				
				ChnlAccountEnquiryResponsePojo chnResp = new ChnlAccountEnquiryResponsePojo();

				PaymentTransaction110 biResp = bm.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);

				chnResp.setOrignReffId(chnReq.getChannelRefId());
				
				chnResp.setCreditorAccountType(biResp.getOrgnlTxRef().getCdtrAcct().getTp().getPrtry());
				chnResp.setCreditorId(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getId());
				
				chnResp.setCreditorName(biResp.getOrgnlTxRef().getCdtr().getPty().getNm());
				chnResp.setCreditorResidentStatus(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getRsdntSts());
				chnResp.setCreditorTownName(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTwnNm());
				chnResp.setCreditorType(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTp());
				
				chnResp.setCreditorAccountNumber(chnReq.getCreditorAccountNumber());
				chnResp.setStatus(biResp.getTxSts());
				chnResp.setReason(biResp.getStsRsnInf().get(0).getRsn().getPrtry());
						
				channelResponseWr.setAccountEnquiryResponse(chnResp);
				
			}
			
			else {
				
				MessageRejectV01 rejectResp = bm.getDocument().getMessageReject();

				fault.setReferenceId(chnReq.getChannelRefId());
				fault.setTransactionType ("AccountEnquiry");
				fault.setReason(rejectResp.getRsn().getRjctgPtyRsn());
				fault.setDescription(rejectResp.getRsn().getRsnDesc());
				fault.setLocation("[CI-HUB] " + rejectResp.getRsn().getErrLctn());
				fault.setAdditionalData(rejectResp.getRsn().getAddtlData());

				channelResponseWr.setFaultResponse(fault);

			}
		}
	
		exchange.getMessage().setBody(channelResponseWr);
	}

}
