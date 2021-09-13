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
		
		System.out.println("FICreditTransferResponseProcessor");
		
		ChnlFICreditTransferRequestPojo chnRequest = exchange.getMessage().getHeader("hdr_channelRequest", ChnlFICreditTransferRequestPojo.class);

		BusinessMessage obj_ficrdtrnResp = exchange.getMessage().getHeader("fict_objresponsebi", BusinessMessage.class);
		
		Integer lastHttpResponse = (Integer) exchange.getMessage().getHeader(Exchange.HTTP_RESPONSE_CODE);
		if ((!(null==lastHttpResponse)) && (lastHttpResponse == 504)) {
			ChnlFICreditTransferResponsePojo chnResponse = new ChnlFICreditTransferResponsePojo();
			chnResponse.setOrignReffId(chnRequest.getOrignReffId());
			chnResponse.setReason("Tidak terima response dari CI-Connector");
			chnResponse.setStatus("Timeout");
			exchange.getIn().setBody(chnResponse);

		}
		
		else if (null == obj_ficrdtrnResp.getDocument().getMessageReject())  {   // cek apakah response berupa bukan message reject 
			PaymentTransaction110 biResp = obj_ficrdtrnResp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);

			ChnlFICreditTransferResponsePojo chnResponse = new ChnlFICreditTransferResponsePojo();
			
			chnResponse.setOrignReffId(chnRequest.getOrignReffId());
			// from CI-HUB response
			chnResponse.setBizMsgId(obj_ficrdtrnResp.getAppHdr().getBizMsgIdr());
			chnResponse.setStatus(biResp.getTxSts());
			chnResponse.setReason(biResp.getStsRsnInf().get(0).getRsn().getPrtry());
			
			exchange.getIn().setBody(chnResponse);

		}
		else {   // ternyata berupa message reject
			MessageRejectV01 rejectResp = obj_ficrdtrnResp.getDocument().getMessageReject();

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
