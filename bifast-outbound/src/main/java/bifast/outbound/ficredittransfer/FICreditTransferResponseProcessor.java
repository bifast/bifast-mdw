package bifast.outbound.ficredittransfer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.admi002.MessageRejectV01;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.outbound.pojo.ChannelReject;

@Component
public class FICreditTransferResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		ChannelFICreditTransferReq chnRequest = exchange.getMessage().getHeader("hdr_channelRequest", ChannelFICreditTransferReq.class);

		BusinessMessage obj_crdtrnResp = exchange.getMessage().getHeader("resp_objbi", BusinessMessage.class);
		
		if (null == obj_crdtrnResp.getDocument().getMessageReject())  {   // cek apakah response berupa bukan message reject 
			PaymentTransaction110 biResp = obj_crdtrnResp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);

			ChnlFiCreditTransferResponse chnResponse = new ChnlFiCreditTransferResponse();
			
			chnResponse.setOrignReffId(chnRequest.getOrignReffId());
			// from CI-HUB response
			chnResponse.setBizMsgId(obj_crdtrnResp.getAppHdr().getBizMsgIdr());
			chnResponse.setStatus(biResp.getTxSts());
			chnResponse.setReason(biResp.getStsRsnInf().get(0).getRsn().getPrtry());
			
			exchange.getIn().setBody(chnResponse);

		}
		else {   // ternyata berupa message reject
			MessageRejectV01 rejectResp = obj_crdtrnResp.getDocument().getMessageReject();

			ChannelReject reject = new ChannelReject();
			
			reject.setReference(rejectResp.getRltdRef().getRef());
			reject.setReason(rejectResp.getRsn().getRjctgPtyRsn());
			reject.setDescription(rejectResp.getRsn().getRsnDesc());
			reject.setLocation(rejectResp.getRsn().getErrLctn());
			reject.setAdditionalData(rejectResp.getRsn().getAddtlData());
			
//			ctMesg.setRejection(reject);
			exchange.getIn().setBody(reject);

		}

//		exchange.getIn().setBody(ctMesg);
		
	
	}

}
