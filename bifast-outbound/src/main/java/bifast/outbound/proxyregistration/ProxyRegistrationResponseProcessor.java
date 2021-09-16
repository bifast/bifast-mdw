package bifast.outbound.proxyregistration;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.admi002.MessageRejectV01;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.prxy002.ProxyRegistrationResponseV01;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.ChnlFailureResponsePojo;

@Component
public class ProxyRegistrationResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		BusinessMessage obj_crdtrnResp = exchange.getIn().getBody(BusinessMessage.class);

		ChnlProxyRegistrationRequestPojo chnRequest = exchange.getMessage().getHeader("hdr_channelRequest", ChnlProxyRegistrationRequestPojo.class);

		ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();

		if (null == obj_crdtrnResp.getDocument().getMessageReject())  {   // cek apakah response berupa bukan message reject 

			ChnlProxyRegistrationResponse chnResponse = new ChnlProxyRegistrationResponse();
			ProxyRegistrationResponseV01 biResp = obj_crdtrnResp.getDocument().getPrxyRegnRspn();

			chnResponse.setOrignReffId(chnRequest.getOrignReffId());
			
			// from CI-HUB response
			chnResponse.setBizMsgId(obj_crdtrnResp.getAppHdr().getBizMsgIdr());
			chnResponse.setResponseType(obj_crdtrnResp.getAppHdr().getBizSvc());
			
			chnResponse.setStatus(biResp.getRegnRspn().getPrxRspnSts().toString());
			chnResponse.setReason(biResp.getRegnRspn().getStsRsnInf().getPrtry());

			channelResponseWr.setProxyRegistrationResponse(chnResponse);
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
