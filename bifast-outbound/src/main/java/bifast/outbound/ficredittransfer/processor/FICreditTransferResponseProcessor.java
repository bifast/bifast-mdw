package bifast.outbound.ficredittransfer.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.admi002.MessageRejectV01;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.outbound.corebank.CBDebitInstructionResponsePojo;
import bifast.outbound.credittransfer.ChnlCreditTransferResponsePojo;
import bifast.outbound.ficredittransfer.ChnlFICreditTransferRequestPojo;
import bifast.outbound.ficredittransfer.ChnlFICreditTransferResponsePojo;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.ChnlFailureResponsePojo;

@Component
public class FICreditTransferResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		String ctFailurePoint = exchange.getMessage().getHeader("ct_failure_point", String.class);

		ChnlFICreditTransferRequestPojo chnRequest = exchange.getMessage().getHeader("hdr_channelRequest", ChnlFICreditTransferRequestPojo.class);

		ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();

		
		if ((!(null== ctFailurePoint)) && (ctFailurePoint.equals("CBRJCT"))) {
			
			CBDebitInstructionResponsePojo cbResponse = exchange.getMessage().getHeader("fict_cbresponse", CBDebitInstructionResponsePojo.class);

			ChnlCreditTransferResponsePojo chnResponse = new ChnlCreditTransferResponsePojo();
			
			chnResponse.setOrignReffId(chnRequest.getIntrnRefId());
			chnResponse.setReason("Corebank Service Reject");
			if (cbResponse.getStatus().equals("FAILED"))
				chnResponse.setStatus("REJECT-CB");
			chnResponse.setAddtInfo(cbResponse.getAddtInfo());
	
			channelResponseWr.setCreditTransferResponse(chnResponse);
			exchange.getIn().setBody(channelResponseWr);

		}

		else if ((!(null== ctFailurePoint)) && (ctFailurePoint.equals("PSTIMEOUT"))) {
			ChnlCreditTransferResponsePojo chnResponse = new ChnlCreditTransferResponsePojo();
			chnResponse.setOrignReffId(chnRequest.getIntrnRefId());
			chnResponse.setReason("Tidak terima response dari CI-Connector");
			chnResponse.setStatus("Timeout");
			
			channelResponseWr.setCreditTransferResponse(chnResponse);
			exchange.getIn().setBody(channelResponseWr);
		}

		
		else {
			
			BusinessMessage obj_ficrdtrnResp = exchange.getMessage().getHeader("fict_biresponse", BusinessMessage.class);
		
			if (null == obj_ficrdtrnResp.getDocument().getMessageReject())  {   // cek apakah response berupa bukan message reject 
				PaymentTransaction110 biResp = obj_ficrdtrnResp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);
	
				ChnlFICreditTransferResponsePojo chnResponse = new ChnlFICreditTransferResponsePojo();
				
				chnResponse.setOrignReffId(chnRequest.getIntrnRefId());
				// from CI-HUB response
				chnResponse.setBizMsgId(obj_ficrdtrnResp.getAppHdr().getBizMsgIdr());
				chnResponse.setStatus(biResp.getTxSts());
				chnResponse.setReason(biResp.getStsRsnInf().get(0).getRsn().getPrtry());
				
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
				
				channelResponseWr.setFaultResponse(reject);
	
				exchange.getIn().setBody(channelResponseWr);
	
	
			}

		}


	
	}

}
