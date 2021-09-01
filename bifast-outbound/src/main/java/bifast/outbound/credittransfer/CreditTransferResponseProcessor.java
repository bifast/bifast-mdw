package bifast.outbound.credittransfer;

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
public class CreditTransferResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		BusinessMessage obj_crdtrnResp = exchange.getIn().getBody(BusinessMessage.class);

		ChannelCreditTransferRequest chnRequest = exchange.getMessage().getHeader("rcv_channel", ChannelCreditTransferRequest.class);

		ChannelResponseMessage ctResponse = new ChannelResponseMessage();
		ctResponse.setCreditTransferRequest(chnRequest);

		ChannelResponse chnResponse = new ChannelResponse();

		
		if (null == obj_crdtrnResp.getDocument().getMessageReject())  {   // cek apakah response berupa bukan message reject 

			PaymentTransaction110 biResp = obj_crdtrnResp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);

			// from CI-HUB response
			chnResponse.setBizMsgId(obj_crdtrnResp.getAppHdr().getBizMsgIdr());
			chnResponse.setResponseType(obj_crdtrnResp.getAppHdr().getBizSvc());
			
			chnResponse.setStatus(biResp.getTxSts());
			chnResponse.setReason(biResp.getStsRsnInf().get(0).getRsn().getPrtry());
			if (!(null == biResp.getStsRsnInf().get(0).getAddtlInf()))
				chnResponse.setAddtInfo(biResp.getStsRsnInf().get(0).getAddtlInf().get(0));
	
			if (!(null == biResp.getOrgnlTxRef().getCdtr().getPty()))
				if (!(null == biResp.getOrgnlTxRef().getCdtr().getPty().getNm()))
					chnResponse.setCreditorName(biResp.getOrgnlTxRef().getCdtr().getPty().getNm());
	
			if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getId()))
				chnResponse.setCreditorId(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getId());
	
			if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTp()))
				chnResponse.setCreditorType(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTp());
	
			if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getRsdntSts()))
				chnResponse.setCreditorResidentStatus(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getRsdntSts());
	
			if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTwnNm()))
				chnResponse.setCreditorTownName(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTwnNm());

			ctResponse.setResponse(chnResponse);
		}
		
		else {   // ternyata berupa message reject
			MessageRejectV01 rejectResp = obj_crdtrnResp.getDocument().getMessageReject();

			ChannelReject reject = new ChannelReject();

			reject.setReference(rejectResp.getRltdRef().getRef());
			reject.setReason(rejectResp.getRsn().getRjctgPtyRsn());
			reject.setDescription(rejectResp.getRsn().getRsnDesc());
			reject.setLocation(rejectResp.getRsn().getErrLctn());
			reject.setAdditionalData(rejectResp.getRsn().getAddtlData());
	
			ctResponse.setRejection(reject);

		}
		
		exchange.getIn().setBody(ctResponse);
		
	
	}

}
