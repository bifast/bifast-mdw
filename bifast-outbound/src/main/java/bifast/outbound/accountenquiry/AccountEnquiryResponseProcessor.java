package bifast.outbound.accountenquiry;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.admi002.MessageRejectV01;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.outbound.pojo.ChannelReject;
import bifast.outbound.pojo.ChannelResponse;
import bifast.outbound.pojo.ChannelResponseMessage;

@Component
public class AccountEnquiryResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage busMesg = exchange.getIn().getBody(BusinessMessage.class);
		
		ChannelAccountEnquiryReq chnReq = exchange.getMessage().getHeader("req_channelReq",ChannelAccountEnquiryReq.class);

		ChannelResponseMessage responseMessage = new ChannelResponseMessage();
		responseMessage.setAccountEnquiryRequest(chnReq);

		if (null == busMesg.getDocument().getMessageReject())  {   // cek apakah response berupa bukan message reject 
			
			PaymentTransaction110 biResp = busMesg.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);
	
			ChannelResponse chnResp = new ChannelResponse();
			
			chnResp.setCreditorAccountType(biResp.getOrgnlTxRef().getCdtrAcct().getTp().getPrtry());
			chnResp.setCreditorId(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getId());
			
			chnResp.setCreditorName(biResp.getOrgnlTxRef().getCdtr().getPty().getNm());
			chnResp.setCreditorResidentStatus(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getRsdntSts());
			chnResp.setCreditorTownName(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTwnNm());
			chnResp.setCreditorType(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTp());
			
			chnResp.setStatus(biResp.getTxSts());
			chnResp.setReason(biResp.getStsRsnInf().get(0).getRsn().getPrtry());
						
			responseMessage.setResponse(chnResp);

		}
		
		else {   // ternyata berupa message reject
			
			MessageRejectV01 rejectResp = busMesg.getDocument().getMessageReject();

			ChannelReject reject = new ChannelReject();

			reject.setReference(rejectResp.getRltdRef().getRef());
			reject.setReason(rejectResp.getRsn().getRjctgPtyRsn());
			reject.setDescription(rejectResp.getRsn().getRsnDesc());
			reject.setLocation(rejectResp.getRsn().getErrLctn());
			reject.setAdditionalData(rejectResp.getRsn().getAddtlData());
	
			responseMessage.setRejection(reject);
		}
		
		exchange.getIn().setBody(responseMessage); 

	}

}
