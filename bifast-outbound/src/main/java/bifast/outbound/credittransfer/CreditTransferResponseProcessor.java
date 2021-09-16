package bifast.outbound.credittransfer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.admi002.MessageRejectV01;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.ChnlFailureResponsePojo;

@Component
public class CreditTransferResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		BusinessMessage obj_crdtrnResp = exchange.getMessage().getHeader("ct_objresponsebi", BusinessMessage.class);

		ChnlCreditTransferRequestPojo chnRequest = exchange.getMessage().getHeader("hdr_channelRequest", ChnlCreditTransferRequestPojo.class);

		Integer lastHttpResponse = (Integer) exchange.getMessage().getHeader(Exchange.HTTP_RESPONSE_CODE);
		
		if ((!(null==lastHttpResponse)) && (lastHttpResponse == 504)) {
		
			System.out.println("HTTP RESPONSE");

			ChnlCreditTransferResponsePojo chnResponse = new ChnlCreditTransferResponsePojo();
			chnResponse.setOrignReffId(chnRequest.getOrignReffId());
			chnResponse.setReason("Tidak terima response dari CI-Connector");
			chnResponse.setStatus("Timeout");
			
			ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
			channelResponseWr.setCreditTransferResponse(chnResponse);
			exchange.getIn().setBody(channelResponseWr);

		}

		else if (null == obj_crdtrnResp.getDocument().getMessageReject())  {   // cek apakah response berupa bukan message reject 

			PaymentTransaction110 biResp = obj_crdtrnResp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);
			ChnlCreditTransferResponsePojo chnResponse = new ChnlCreditTransferResponsePojo();

			// from CI-HUB response
			chnResponse.setOrignReffId(chnRequest.getOrignReffId());
			chnResponse.setBizMsgId(obj_crdtrnResp.getAppHdr().getBizMsgIdr());
			
			chnResponse.setStatus(biResp.getTxSts());
			chnResponse.setReason(biResp.getStsRsnInf().get(0).getRsn().getPrtry());
			
			
			if (biResp.getStsRsnInf().get(0).getAddtlInf().size() > 0) {
				chnResponse.setAddtInfo(biResp.getStsRsnInf().get(0).getAddtlInf().get(0));
			}
			
			if (!(null == biResp.getOrgnlTxRef().getCdtr().getPty()))
				if (!(null == biResp.getOrgnlTxRef().getCdtr().getPty().getNm()))
					chnResponse.setCreditorName(biResp.getOrgnlTxRef().getCdtr().getPty().getNm());
	
			
			if (biResp.getSplmtryData().size() >0 ) {
				
				if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getId()))
					chnResponse.setCreditorId(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getId());
	
				if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTp()))
					chnResponse.setCreditorType(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTp());
		
				if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getRsdntSts()))
					chnResponse.setCreditorResidentStatus(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getRsdntSts());
		
				if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTwnNm()))
					chnResponse.setCreditorTownName(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTwnNm());
		
			}

			ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
			channelResponseWr.setCreditTransferResponse(chnResponse);

			exchange.getIn().setBody(channelResponseWr);

		}
		
		else {   // ternyata berupa message reject
			
			MessageRejectV01 rejectResp = obj_crdtrnResp.getDocument().getMessageReject();
			
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
