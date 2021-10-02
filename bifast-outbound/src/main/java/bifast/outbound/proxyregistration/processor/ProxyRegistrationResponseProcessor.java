package bifast.outbound.proxyregistration.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.admi002.MessageRejectV01;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.prxy002.ProxyRegistrationResponseV01;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.ChnlFailureResponsePojo;
import bifast.outbound.proxyregistration.ChnlProxyRegistrationRequestPojo;
import bifast.outbound.proxyregistration.ChnlProxyRegistrationResponse;

@Component
public class ProxyRegistrationResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		BusinessMessage prxyRegnResponse = exchange.getIn().getBody(BusinessMessage.class);

		ChnlProxyRegistrationRequestPojo chnRequest = exchange.getMessage().getHeader("hdr_channelRequest", ChnlProxyRegistrationRequestPojo.class);

		ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();


		if (null == prxyRegnResponse) {

			ChnlFailureResponsePojo reject = new ChnlFailureResponsePojo();

			reject.setReferenceId(chnRequest.getIntrnRefId());
			
			String errorStatus = exchange.getMessage().getHeader("hdr_error_status", String.class);
			String errorMesg = exchange.getMessage().getHeader("hdr_error_mesg", String.class);
			reject.setReason(errorStatus);
			reject.setDescription(errorMesg);
	
			channelResponseWr.setFaultResponse(reject);
			exchange.getIn().setBody(channelResponseWr);

		}
		
		else if (null == prxyRegnResponse.getDocument().getMessageReject())  {   // cek apakah response berupa bukan message reject 

			ChnlProxyRegistrationResponse chnResponse = new ChnlProxyRegistrationResponse();
			ProxyRegistrationResponseV01 biResp = prxyRegnResponse.getDocument().getPrxyRegnRspn();

			chnResponse.setIntrnRefId(chnRequest.getIntrnRefId());
			
			// from CI-HUB response
			if (biResp.getRegnRspn().getPrxyRegn().size() > 0) 
				if (!(null == biResp.getRegnRspn().getPrxyRegn().get(0).getRegnId()))
					chnResponse.setRegistrationId(biResp.getRegnRspn().getPrxyRegn().get(0).getRegnId());
			
			
			chnResponse.setStatus(biResp.getRegnRspn().getPrxRspnSts().toString());
			chnResponse.setReason(biResp.getRegnRspn().getStsRsnInf().getPrtry());

			channelResponseWr.setProxyRegistrationResponse(chnResponse);
			exchange.getIn().setBody(channelResponseWr);

		}
		
		else {   // ternyata berupa message reject
			MessageRejectV01 rejectResp = prxyRegnResponse.getDocument().getMessageReject();

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
