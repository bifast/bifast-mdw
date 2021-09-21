package bifast.outbound.ficredittransfer.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.admi002.MessageRejectV01;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.outbound.ficredittransfer.ChnlFICreditTransferRequestPojo;
import bifast.outbound.ficredittransfer.ChnlFICreditTransferResponsePojo;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.ChnlFailureResponsePojo;

@Component
public class FICreditTransferResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
	
		ChnlFICreditTransferRequestPojo chnRequest = exchange.getMessage().getHeader("hdr_channelRequest", ChnlFICreditTransferRequestPojo.class);

		BusinessMessage obj_ficrdtrnResp = exchange.getMessage().getHeader("fict_objresponsebi", BusinessMessage.class);
		
		Integer lastHttpResponse = (Integer) exchange.getMessage().getHeader(Exchange.HTTP_RESPONSE_CODE);
		if ((!(null==lastHttpResponse)) && (lastHttpResponse == 504)) {
			ChnlFICreditTransferResponsePojo chnResponse = new ChnlFICreditTransferResponsePojo();
			
			chnResponse.setOrignReffId(chnRequest.getIntrnRefId());
			chnResponse.setReason("Tidak terima response dari CI-Connector");
			chnResponse.setStatus("Timeout");
			
			ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
			channelResponseWr.setFiCreditTransferResponse(chnResponse); 
			exchange.getIn().setBody(channelResponseWr);
			
		}
		
		else if (null == obj_ficrdtrnResp.getDocument().getMessageReject())  {   // cek apakah response berupa bukan message reject 
			PaymentTransaction110 biResp = obj_ficrdtrnResp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);

			ChnlFICreditTransferResponsePojo chnResponse = new ChnlFICreditTransferResponsePojo();
			
			chnResponse.setOrignReffId(chnRequest.getIntrnRefId());
			// from CI-HUB response
			chnResponse.setBizMsgId(obj_ficrdtrnResp.getAppHdr().getBizMsgIdr());
			chnResponse.setStatus(biResp.getTxSts());
			chnResponse.setReason(biResp.getStsRsnInf().get(0).getRsn().getPrtry());
			
			ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
			channelResponseWr.setFiCreditTransferResponse(chnResponse); 
			exchange.getIn().setBody(channelResponseWr);

		}
		else {   // ternyata berupa message reject
			MessageRejectV01 rejectResp = obj_ficrdtrnResp.getDocument().getMessageReject();

			ChnlFailureResponsePojo reject = new ChnlFailureResponsePojo();
			
			reject.setReferenceId(rejectResp.getRltdRef().getRef());
			reject.setReason(rejectResp.getRsn().getRjctgPtyRsn());
			reject.setDescription(rejectResp.getRsn().getRsnDesc());
			reject.setLocation(rejectResp.getRsn().getErrLctn());
			reject.setAdditionalData(rejectResp.getRsn().getAddtlData());
			
			ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
			channelResponseWr.setFaultResponse(reject);

			exchange.getIn().setBody(channelResponseWr);


		}

	
	}

}
