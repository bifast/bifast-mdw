package bifast.outbound.proxyregistration;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.admi002.MessageRejectV01;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.prxy004.ProxyLookUpRegistration1;
import bifast.library.iso20022.prxy004.ProxyLookUpResponse1;
import bifast.library.iso20022.prxy004.ProxyLookUpResponseV01;
import bifast.outbound.pojo.ChannelReject;
import bifast.outbound.pojo.ChannelResponse;
import bifast.outbound.pojo.ChannelResponseMessage;

@Component
public class ProxyResolutionResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		

		ChannelProxyResolutionReq chnRequest = exchange.getMessage().getHeader("rcv_channel", ChannelProxyResolutionReq.class);

		ChannelResponseMessage ctResponse = new ChannelResponseMessage();
		ctResponse.setProxyResolutionRequest(chnRequest);

		ChannelResponse chnResponse = new ChannelResponse();
		
		BusinessMessage obj_pxryLookup = exchange.getIn().getBody(BusinessMessage.class);
		if (null == obj_pxryLookup.getDocument().getMessageReject())  {   // cek apakah response berupa bukan message reject 

			ProxyLookUpResponseV01 biResp = obj_pxryLookup.getDocument().getPrxyLookUpRspn();

			// from CI-HUB response
			chnResponse.setBizMsgId(obj_pxryLookup.getAppHdr().getBizMsgIdr());
			
			chnResponse.setStatus(biResp.getLkUpRspn().getRegnRspn().getPrxRspnSts().toString());
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
			
			ctResponse.setResponse(chnResponse);
		}
		
		else {   // ternyata berupa message reject
			MessageRejectV01 rejectResp = obj_pxryLookup.getDocument().getMessageReject();

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
