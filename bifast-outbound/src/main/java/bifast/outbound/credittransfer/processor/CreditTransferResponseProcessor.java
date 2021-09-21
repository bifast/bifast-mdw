package bifast.outbound.credittransfer.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.admi002.MessageRejectV01;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryResponsePojo;
import bifast.outbound.corebank.CBDebitInstructionResponsePojo;
import bifast.outbound.credittransfer.ChnlCreditTransferRequestPojo;
import bifast.outbound.credittransfer.ChnlCreditTransferResponsePojo;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.ChnlFailureResponsePojo;

@Component
public class CreditTransferResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		String ctFailurePoint = exchange.getMessage().getHeader("ct_failure_point", String.class);
		
		ChnlCreditTransferRequestPojo chnRequest = exchange.getMessage().getHeader("ct_channelRequest", ChnlCreditTransferRequestPojo.class);
		
		ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();

		if ((!(null== ctFailurePoint)) && (ctFailurePoint.equals("AERJCT"))) {
			
			ChannelResponseWrapper aeResponseWr = exchange.getMessage().getBody(ChannelResponseWrapper.class);	
			ChnlAccountEnquiryResponsePojo aeResponse = aeResponseWr.getAccountEnquiryResponse();
			ChnlCreditTransferResponsePojo chnResponse = new ChnlCreditTransferResponsePojo();
			
			chnResponse.setOrignReffId(chnRequest.getOrignReffId());
			chnResponse.setReason("Account Enquiry Rejected");
			chnResponse.setStatus(aeResponse.getStatus());
			chnResponse.setAddtInfo(aeResponse.getReason());

			channelResponseWr.setCreditTransferResponse(chnResponse);
			exchange.getIn().setBody(channelResponseWr);

		}
		
		else if ((!(null== ctFailurePoint)) && (ctFailurePoint.equals("CBRJCT"))) {
			
			CBDebitInstructionResponsePojo cbResponse = exchange.getMessage().getHeader("ct_cbresponse", CBDebitInstructionResponsePojo.class);

			ChnlCreditTransferResponsePojo chnResponse = new ChnlCreditTransferResponsePojo();
			
			chnResponse.setOrignReffId(chnRequest.getOrignReffId());
			chnResponse.setReason("Corebank Service Reject");
			if (cbResponse.getStatus().equals("FAILED"))
				chnResponse.setStatus("REJECT-CB");
			chnResponse.setAddtInfo(cbResponse.getAddtInfo());
	
			channelResponseWr.setCreditTransferResponse(chnResponse);
			exchange.getIn().setBody(channelResponseWr);

		}
		
		else if ((!(null== ctFailurePoint)) && (ctFailurePoint.equals("PSTIMEOUT"))) {
			ChnlCreditTransferResponsePojo chnResponse = new ChnlCreditTransferResponsePojo();
			chnResponse.setOrignReffId(chnRequest.getOrignReffId());
			chnResponse.setReason("Tidak terima response dari CI-Connector");
			chnResponse.setStatus("Timeout");
			
			channelResponseWr.setCreditTransferResponse(chnResponse);
			exchange.getIn().setBody(channelResponseWr);
		}

		else {
			
			BusinessMessage obj_crdtrnResp = exchange.getMessage().getHeader("ct_biresponse", BusinessMessage.class);

			if (null == obj_crdtrnResp.getDocument().getMessageReject())  {   // cek apakah response berupa bukan message reject 
		
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
		
				channelResponseWr.setFaultResponse(reject);
	
				exchange.getIn().setBody(channelResponseWr);
	
			}
		}
	}
}
