package bifast.outbound.proxyregistration.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.prxy002.ProxyRegistrationResponseV01;
import bifast.outbound.model.StatusReason;
import bifast.outbound.pojo.ChnlFailureResponsePojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlProxyRegistrationRequestPojo;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.pojo.chnlresponse.ChnlProxyRegistrationResponsePojo;
import bifast.outbound.repository.StatusReasonRepository;

@Component
public class ProxyRegistrationResponseProcessor implements Processor {
	
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

    @Autowired
    private StatusReasonRepository statusReasonRepo;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
		channelResponseWr.setResponseCode("U000");
		channelResponseWr.setResponseMessage("Success");
		channelResponseWr.setDate(LocalDateTime.now().format(dateformatter));
		channelResponseWr.setTime(LocalDateTime.now().format(timeformatter));
		channelResponseWr.setContent(new ArrayList<>());

		Object objBody = exchange.getMessage().getBody(Object.class);
		String bodyClass = objBody.getClass().getSimpleName();

		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list",RequestMessageWrapper.class);
		ChnlProxyRegistrationRequestPojo chnRequest = rmw.getChnlProxyRegistrationRequest();

		ChnlProxyRegistrationResponsePojo chnResponse = new ChnlProxyRegistrationResponsePojo();
		chnResponse.setNoRef(chnRequest.getChannelRefId());

		if (bodyClass.equals("ChnlFailureResponsePojo")) {

			ChnlFailureResponsePojo fault = (ChnlFailureResponsePojo)objBody;

			channelResponseWr.setResponseCode(fault.getErrorCode());
			if (fault.getErrorCode().equals("ERROR-KM"))
				channelResponseWr.setResponseMessage("KOMI Internal Error");
			else if (fault.getErrorCode().equals("ERROR-CIHUB"))
				channelResponseWr.setResponseMessage("CIHUB Internal Error");
			else
				channelResponseWr.setResponseMessage("KOMI Error");		
		}
		
		else {
			BusinessMessage bm = (BusinessMessage)objBody;
			ProxyRegistrationResponseV01 biResp = bm.getDocument().getPrxyRegnRspn();

			channelResponseWr.setResponseCode(biResp.getRegnRspn().getStsRsnInf().getPrtry());
			Optional<StatusReason> optStatusReason = statusReasonRepo.findById(channelResponseWr.getResponseCode());
			if (optStatusReason.isPresent()) {
				String desc = optStatusReason.get().getDescription();
				channelResponseWr.setResponseMessage(desc);
			}	

			chnResponse.setRegistrationType(chnRequest.getRegistrationType());

			if (biResp.getRegnRspn().getPrxyRegn().size() > 0) 
				if (!(null == biResp.getRegnRspn().getPrxyRegn().get(0).getRegnId()))
					chnResponse.setRegistrationId(biResp.getRegnRspn().getPrxyRegn().get(0).getRegnId());
			
		}
		
		channelResponseWr.getContent().add(chnResponse);

		exchange.getMessage().setBody(channelResponseWr);

	}
}
