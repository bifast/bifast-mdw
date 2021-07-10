package bifast.outbound.proxyregistration;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.admi002.MessageRejectV01;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.prxy002.ProxyRegistrationResponseV01;
import bifast.outbound.pojo.ChannelReject;
import bifast.outbound.pojo.ChannelResponse;
import bifast.outbound.pojo.ChannelResponseMessage;

@Component
public class ProxyRegistrationResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		BusinessMessage obj_crdtrnResp = exchange.getIn().getBody(BusinessMessage.class);

		ChannelProxyRegistrationReq chnRequest = exchange.getMessage().getHeader("req_channelReq", ChannelProxyRegistrationReq.class);

		ChannelResponseMessage ctResponse = new ChannelResponseMessage();
		ctResponse.setProxyRegistrationRequest(chnRequest);

		ChannelResponse chnResponse = new ChannelResponse();

		
		if (null == obj_crdtrnResp.getDocument().getMessageReject())  {   // cek apakah response berupa bukan message reject 

			
			ProxyRegistrationResponseV01 biResp = obj_crdtrnResp.getDocument().getPrxyRegnRspn();

			// from CI-HUB response
			chnResponse.setBizMsgId(obj_crdtrnResp.getAppHdr().getBizMsgIdr());
			chnResponse.setResponseType(obj_crdtrnResp.getAppHdr().getBizSvc());
			
			chnResponse.setStatus(biResp.getRegnRspn().getPrxRspnSts().toString());
			chnResponse.setReason(biResp.getRegnRspn().getStsRsnInf().getPrtry());

			ctResponse.setResponse(chnResponse);
		}
		
		else {   // ternyata berupa message reject
			MessageRejectV01 rejectResp = obj_crdtrnResp.getDocument().getMessageReject();

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
