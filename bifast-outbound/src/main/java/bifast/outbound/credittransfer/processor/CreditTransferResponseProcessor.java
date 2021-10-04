package bifast.outbound.credittransfer.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.admi002.MessageRejectV01;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.outbound.credittransfer.ChnlCreditTransferRequestPojo;
import bifast.outbound.credittransfer.ChnlCreditTransferResponsePojo;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.ChnlFailureResponsePojo;
import bifast.outbound.pojo.RequestMessageWrapper;

@Component
public class CreditTransferResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		ChnlCreditTransferRequestPojo chnRequest = rmw.getChnlCreditTransferRequest();
		
		Object objResponse = exchange.getMessage().getBody(Object.class);
		ChannelResponseWrapper chnlResponseWr = new ChannelResponseWrapper();
		
		if (objResponse.getClass().getSimpleName().equals("BusinessMessage")) {
			
			BusinessMessage response = (BusinessMessage)objResponse;
			
			if (null != response.getDocument().getFiToFIPmtStsRpt()) {
				
				PaymentTransaction110 biResp = response.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);
				ChnlCreditTransferResponsePojo chnResponse = new ChnlCreditTransferResponsePojo();
	
				// from CI-HUB response
				chnResponse.setOrignReffId(chnRequest.getOrignReffId());
				chnResponse.setBizMsgId(response.getAppHdr().getBizMsgIdr());
				
				if ((biResp.getTxSts().equals("ACSP")) || (biResp.getTxSts().equals("ACTC"))) 
					chnResponse.setStatus("ACTC");
				else
					chnResponse.setStatus("RJCT");
					
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
			
//					if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getRsdntSts()))
//						chnResponse.setCreditorResidentStatus(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getRsdntSts());
//			
//					if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTwnNm()))
//						chnResponse.setCreditorTownName(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTwnNm());
			
				}
	
				chnlResponseWr.setChnlCreditTransferResponse(chnResponse);
				exchange.getIn().setBody(chnlResponseWr);
				
			}
			
			else {
			
				MessageRejectV01 rejectResp = response.getDocument().getMessageReject();
				ChnlFailureResponsePojo reject = new ChnlFailureResponsePojo();
				
				reject.setReferenceId(rejectResp.getRltdRef().getRef());
				reject.setReason(rejectResp.getRsn().getRjctgPtyRsn());
				reject.setDescription(rejectResp.getRsn().getRsnDesc());
				reject.setLocation(rejectResp.getRsn().getErrLctn());
				reject.setAdditionalData(rejectResp.getRsn().getAddtlData());
		
				chnlResponseWr.setFaultResponse(reject);
	
				exchange.getIn().setBody(chnlResponseWr);
	
			}
		}
	}
}
