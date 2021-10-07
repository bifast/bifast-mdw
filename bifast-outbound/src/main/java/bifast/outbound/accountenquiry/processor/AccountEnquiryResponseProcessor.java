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
public class AccountEnquiryResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

//		BusinessMessage busMesg = exchange.getMessage().getHeader("ae_objresp_bi", BusinessMessage.class);
		
		ChnlAccountEnquiryRequestPojo chnReq = exchange.getMessage().getHeader("ae_channel_request",ChnlAccountEnquiryRequestPojo.class);
		
		ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
		
		BusinessMessage busMesg = exchange.getIn().getBody(BusinessMessage.class);

		if (null == busMesg) {
			
			ChnlFailureResponsePojo reject = new ChnlFailureResponsePojo();

			reject.setReferenceId(chnReq.getIntrnRefId());
			
			String errorStatus = exchange.getMessage().getHeader("hdr_error_status", String.class);
			String errorMesg = exchange.getMessage().getHeader("hdr_error_mesg", String.class);
			reject.setReason(errorStatus);
			reject.setDescription(errorMesg);
			
//			reject.setLocation(rejectResp.getRsn().getErrLctn());
//			reject.setAdditionalData(rejectResp.getRsn().getAddtlData());
	
			channelResponseWr.setFaultResponse(reject);
			exchange.getIn().setBody(channelResponseWr);

		}
		
		else if (null == busMesg.getDocument().getMessageReject())  {   // cek apakah response berupa bukan message reject 
			
			PaymentTransaction110 biResp = busMesg.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);
	
			ChnlAccountEnquiryResponsePojo chnResp = new ChnlAccountEnquiryResponsePojo();
			
			chnResp.setOrignReffId(chnReq.getIntrnRefId());
			
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
		
		else {   // ternyata berupa message reject
			
			MessageRejectV01 rejectResp = busMesg.getDocument().getMessageReject();

			ChnlFailureResponsePojo reject = new ChnlFailureResponsePojo();
		
			reject.setReferenceId(chnReq.getIntrnRefId());
			reject.setTransactionType ("AccountEnquiry");
			reject.setReason(rejectResp.getRsn().getRjctgPtyRsn());
			reject.setDescription(rejectResp.getRsn().getRsnDesc());
			reject.setLocation("[CI-HUB] " + rejectResp.getRsn().getErrLctn());
			reject.setAdditionalData(rejectResp.getRsn().getAddtlData());

			channelResponseWr.setFaultResponse(reject);
		}
		
		exchange.getMessage().setBody(channelResponseWr);
	}

}
