package bifast.outbound.proxyregistration.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.prxy004.ProxyLookUpResponseV01;
import bifast.outbound.model.ProxyMessage;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlProxyResolutionRequestPojo;
import bifast.outbound.repository.ProxyMessageRepository;

@Component
public class StoreProxyResolutionProcessor implements Processor{

	@Autowired
	private ProxyMessageRepository proxyMessageRepo;

	@Override
	public void process(Exchange exchange) throws Exception {


		ProxyMessage proxyMessage = new ProxyMessage();
		
		String encrRequestMesg = exchange.getMessage().getHeader("hdr_encr_request", String.class);
		String encrResponseMesg = exchange.getMessage().getHeader("hdr_encr_response", String.class);
		
		proxyMessage.setFullRequestMesg(encrRequestMesg);
		proxyMessage.setFullResponseMesg(encrResponseMesg);

		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		
		ChnlProxyResolutionRequestPojo channelRequest = rmw.getChnlProxyResolutionRequest();

	
		proxyMessage.setOperationType("PXRS");
		proxyMessage.setProxyType(channelRequest.getProxyType());
		proxyMessage.setProxyValue(channelRequest.getProxyValue());
		
//		proxyMessage.setRequestDt(utilService.getTimestampFromMessageHistory(listHistory, "start_route"));
//		proxyMessage.setResponseDt(utilService.getTimestampFromMessageHistory(listHistory, "end_route"));
//		proxyMessage.setCihubElapsedTime(routeElapsed);

		
//		proxyMessage.setScndIdType(regRequest.getRegn().getPrxyRegn().getScndId().getTp());
//		proxyMessage.setScndValue(regRequest.getRegn().getPrxyRegn().getScndId().getVal());

		BusinessMessage proxyResponse = exchange.getMessage().getHeader("prx_biresponse", BusinessMessage.class);

		if (!(null == proxyResponse)) {
			ProxyLookUpResponseV01 resolutionResponse = proxyResponse.getDocument().getPrxyLookUpRspn();

			proxyMessage.setAccountName(resolutionResponse.getLkUpRspn().getRegnRspn().getRegn().getAcct().getNm());
			proxyMessage.setAccountNumber(resolutionResponse.getLkUpRspn().getRegnRspn().getRegn().getAcct().getId().getOthr().getId());
			proxyMessage.setAccountType(resolutionResponse.getLkUpRspn().getRegnRspn().getRegn().getAcct().getTp().getPrtry());
			proxyMessage.setCustomerId(resolutionResponse.getSplmtryData().get(0).getEnvlp().getCstmr().getId());
			proxyMessage.setCustomerType(resolutionResponse.getSplmtryData().get(0).getEnvlp().getCstmr().getTp());
	
			proxyMessage.setDisplayName(resolutionResponse.getLkUpRspn().getRegnRspn().getRegn().getDsplNm());
			proxyMessage.setResidentStatus(resolutionResponse.getSplmtryData().get(0).getEnvlp().getCstmr().getRsdntSts());
			proxyMessage.setTownName(resolutionResponse.getSplmtryData().get(0).getEnvlp().getCstmr().getTwnNm());

			proxyMessage.setRespStatus(resolutionResponse.getLkUpRspn().getRegnRspn().getPrxRspnSts().value());
		}
		
		
		String errorStatus = exchange.getMessage().getHeader("hdr_error_status", String.class);
		String errorMesg = exchange.getMessage().getHeader("hdr_error_mesg", String.class);

		
		if (!(null == errorStatus)) {
			proxyMessage.setCallStatus(errorStatus);
			if (!(null == errorMesg)) 
				proxyMessage.setErrorMessage(errorMesg);
		} 

		else
			proxyMessage.setCallStatus("SUCCESS");

		
		proxyMessageRepo.save(proxyMessage);
		
	}

}
