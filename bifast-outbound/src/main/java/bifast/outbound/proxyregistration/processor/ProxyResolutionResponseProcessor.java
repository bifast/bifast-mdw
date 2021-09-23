package bifast.outbound.proxyregistration.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.admi002.MessageRejectV01;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.prxy004.ProxyLookUpResponseV01;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.ChnlFailureResponsePojo;
import bifast.outbound.proxyregistration.ChnlProxyResolutionRequestPojo;
import bifast.outbound.proxyregistration.ChnlProxyResolutionResponse;

@Component
public class ProxyResolutionResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		ChnlProxyResolutionRequestPojo chnRequest = exchange.getMessage().getHeader("hdr_channelRequest", ChnlProxyResolutionRequestPojo.class);

		BusinessMessage pxryLookup = exchange.getIn().getBody(BusinessMessage.class);
		
		ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();

		if (null == pxryLookup) {

			ChnlFailureResponsePojo reject = new ChnlFailureResponsePojo();

			reject.setReferenceId(chnRequest.getOrignReffId());
			
			String errorStatus = exchange.getMessage().getHeader("hdr_error_status", String.class);
			String errorMesg = exchange.getMessage().getHeader("hdr_error_mesg", String.class);
			reject.setReason(errorStatus);
			reject.setDescription(errorMesg);
			
//			reject.setLocation(rejectResp.getRsn().getErrLctn());
//			reject.setAdditionalData(rejectResp.getRsn().getAddtlData());
	
			channelResponseWr.setFaultResponse(reject);
			exchange.getIn().setBody(channelResponseWr);

		}
		
		else if (null == pxryLookup.getDocument().getMessageReject())  {   // cek apakah response berupa bukan message reject 

			ChnlProxyResolutionResponse chnResponse = new ChnlProxyResolutionResponse();
			ProxyLookUpResponseV01 biResp = pxryLookup.getDocument().getPrxyLookUpRspn();

			chnResponse.setOrignReffId(chnRequest.getOrignReffId());
			// from CI-HUB response
			chnResponse.setBizMsgId(pxryLookup.getAppHdr().getBizMsgIdr());
			
			chnResponse.setStatus(biResp.getLkUpRspn().getRegnRspn().getPrxRspnSts().value());
			chnResponse.setReason(biResp.getLkUpRspn().getRegnRspn().getStsRsnInf().getPrtry());

			chnResponse.setProxyType(biResp.getLkUpRspn().getRegnRspn().getPrxy().getTp());
			chnResponse.setProxyValue(biResp.getLkUpRspn().getRegnRspn().getPrxy().getVal());
			chnResponse.setProxyRegistrationId(biResp.getLkUpRspn().getRegnRspn().getRegn().getRegnId());
			chnResponse.setProxyDisplayName(biResp.getLkUpRspn().getRegnRspn().getRegn().getDsplNm());
			chnResponse.setProxyBic(biResp.getLkUpRspn().getRegnRspn().getRegn().getAgt().getFinInstnId().getOthr().getId());

			chnResponse.setCustomerAccountNumber(biResp.getLkUpRspn().getRegnRspn().getRegn().getAcct().getId().getOthr().getId());
			chnResponse.setCustomerAccountType(biResp.getLkUpRspn().getRegnRspn().getRegn().getAcct().getTp().getPrtry());

			if (!(null==biResp.getLkUpRspn().getRegnRspn().getRegn().getAcct().getNm()))
				chnResponse.setCustomerAccountName(biResp.getLkUpRspn().getRegnRspn().getRegn().getAcct().getNm());
		
			if (!(null==biResp.getSplmtryData())) {
			
				if (!(null==biResp.getSplmtryData().get(0).getEnvlp().getCstmr().getTp()))
					chnResponse.setCustomerType(biResp.getSplmtryData().get(0).getEnvlp().getCstmr().getTp());
				
				if (!(null==biResp.getSplmtryData().get(0).getEnvlp().getCstmr().getId()))
					chnResponse.setCustomerIdNumber(biResp.getSplmtryData().get(0).getEnvlp().getCstmr().getId());
				
				if (!(null==biResp.getSplmtryData().get(0).getEnvlp().getCstmr().getRsdntSts()))
					chnResponse.setCustomerResidentStatus(biResp.getSplmtryData().get(0).getEnvlp().getCstmr().getRsdntSts());

				if (!(null==biResp.getSplmtryData().get(0).getEnvlp().getCstmr().getTwnNm()))
					chnResponse.setCustomerTownName(biResp.getSplmtryData().get(0).getEnvlp().getCstmr().getTwnNm());
			}			
			
			channelResponseWr.setProxyResolutionResponse(chnResponse);
			exchange.getIn().setBody(channelResponseWr);

			exchange.getIn().setBody(channelResponseWr);

		}
		
		else {   // ternyata berupa message reject
			MessageRejectV01 rejectResp = pxryLookup.getDocument().getMessageReject();

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
