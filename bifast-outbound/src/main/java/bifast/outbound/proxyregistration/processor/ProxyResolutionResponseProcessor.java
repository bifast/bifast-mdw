package bifast.outbound.proxyregistration.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.prxy004.ProxyLookUpResponseV01;
import bifast.outbound.pojo.ChnlFailureResponsePojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlProxyResolutionRequestPojo;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.pojo.chnlresponse.ChnlProxyResolutionResponsePojo;

@Component
public class ProxyResolutionResponseProcessor implements Processor {

	DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");


	@Override
	public void process(Exchange exchange) throws Exception {

		ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
		channelResponseWr.setResponseCode("U000");
		channelResponseWr.setResponseMessage("Success/ Transaction Accepted");
		channelResponseWr.setDate(LocalDateTime.now().format(dateformatter));
		channelResponseWr.setTime(LocalDateTime.now().format(timeformatter));
		channelResponseWr.setContent(new ArrayList<>());

		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		ChnlProxyResolutionRequestPojo chnRequest = rmw.getChnlProxyResolutionRequest();

		Object objBody = exchange.getMessage().getBody(Object.class);
		String bodyClass = objBody.getClass().getSimpleName();
		
		ChnlProxyResolutionResponsePojo chnResponse = new ChnlProxyResolutionResponsePojo();
		chnResponse.setNoRef(chnRequest.getIntrnRefId());
		
		if (bodyClass.equals("ChnlFailureResponsePojo")) {

			ChnlFailureResponsePojo reject = new ChnlFailureResponsePojo();

			channelResponseWr.setResponseCode(reject.getErrorCode());
			if (reject.getErrorCode().equals("ERROR-KM"))
				channelResponseWr.setResponseMessage("KOMI Internal Error");
			else if (reject.getErrorCode().equals("ERROR-CIHUB"))
				channelResponseWr.setResponseMessage("CIHUB Internal Error");
			else
				channelResponseWr.setResponseMessage("KOMI Error");
			
		}
		
		else { 
			
			BusinessMessage bm = (BusinessMessage)objBody;

			ProxyLookUpResponseV01 biResp = bm.getDocument().getPrxyLookUpRspn();

			chnResponse.setNoRef(chnRequest.getIntrnRefId());
			// from CI-HUB response
			
			chnResponse.setProxyType(biResp.getLkUpRspn().getRegnRspn().getPrxy().getTp());
			chnResponse.setProxyValue(biResp.getLkUpRspn().getRegnRspn().getPrxy().getVal());
			chnResponse.setRegistrationId(biResp.getLkUpRspn().getRegnRspn().getRegn().getRegnId());
			chnResponse.setDisplayName(biResp.getLkUpRspn().getRegnRspn().getRegn().getDsplNm());
			chnResponse.setRegisterBank(biResp.getLkUpRspn().getRegnRspn().getRegn().getAgt().getFinInstnId().getOthr().getId());

			chnResponse.setAccountNumber(biResp.getLkUpRspn().getRegnRspn().getRegn().getAcct().getId().getOthr().getId());
			chnResponse.setAccountType(biResp.getLkUpRspn().getRegnRspn().getRegn().getAcct().getTp().getPrtry());

			if (!(null==biResp.getLkUpRspn().getRegnRspn().getRegn().getAcct().getNm()))
				chnResponse.setAccountName(biResp.getLkUpRspn().getRegnRspn().getRegn().getAcct().getNm());
		
			if (!(null==biResp.getSplmtryData())) {
			
				if (!(null==biResp.getSplmtryData().get(0).getEnvlp().getCstmr().getTp()))
					chnResponse.setCustomerType(biResp.getSplmtryData().get(0).getEnvlp().getCstmr().getTp());
				
				if (!(null==biResp.getSplmtryData().get(0).getEnvlp().getCstmr().getId()))
					chnResponse.setCustomerId(biResp.getSplmtryData().get(0).getEnvlp().getCstmr().getId());
				
				if (!(null==biResp.getSplmtryData().get(0).getEnvlp().getCstmr().getRsdntSts()))
					chnResponse.setResidentStatus(biResp.getSplmtryData().get(0).getEnvlp().getCstmr().getRsdntSts());

				if (!(null==biResp.getSplmtryData().get(0).getEnvlp().getCstmr().getTwnNm()))
					chnResponse.setTownName(biResp.getSplmtryData().get(0).getEnvlp().getCstmr().getTwnNm());
			}			
			
			
			channelResponseWr.getContent().add(chnResponse);
			exchange.getIn().setBody(channelResponseWr);


		}
		
	
	}

}
