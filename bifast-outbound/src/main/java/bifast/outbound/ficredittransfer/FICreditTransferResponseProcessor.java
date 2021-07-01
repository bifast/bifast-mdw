package bifast.outbound.ficredittransfer;

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
public class FICreditTransferResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		ChannelFICreditTransferReq chnRequest = exchange.getMessage().getHeader("req_channelReq", ChannelFICreditTransferReq.class);
		ChannelResponseMessage ctMesg = new ChannelResponseMessage();
		ctMesg.setfICreditTransferRequest(chnRequest);

		BusinessMessage obj_crdtrnResp = exchange.getMessage().getHeader("resp_objbi", BusinessMessage.class);
		
		if (null == obj_crdtrnResp.getDocument().getMessageReject())  {   // cek apakah response berupa bukan message reject 
			PaymentTransaction110 biResp = obj_crdtrnResp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);

			ChannelResponse chnResponse = new ChannelResponse();
			
			// from CI-HUB response
			chnResponse.setBizMsgId(obj_crdtrnResp.getAppHdr().getBizMsgIdr());
			chnResponse.setStatus(biResp.getTxSts());
			chnResponse.setReason(biResp.getStsRsnInf().get(0).getRsn().getPrtry());
			
			ctMesg.setResponse(chnResponse);
		}
		else {   // ternyata berupa message reject
			MessageRejectV01 rejectResp = obj_crdtrnResp.getDocument().getMessageReject();

			ChannelReject reject = new ChannelReject();

			reject.setReference(rejectResp.getRltdRef().getRef());
			reject.setReason(rejectResp.getRsn().getRjctgPtyRsn());
			reject.setDescription(rejectResp.getRsn().getRsnDesc());
			reject.setLocation(rejectResp.getRsn().getErrLctn());
			reject.setAdditionalData(rejectResp.getRsn().getAddtlData());
	
			ctMesg.setRejection(reject);

		}

		exchange.getIn().setBody(ctMesg);
		
	
	}

}
