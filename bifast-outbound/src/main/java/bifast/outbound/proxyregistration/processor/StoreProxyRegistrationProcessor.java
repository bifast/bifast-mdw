package bifast.outbound.proxyregistration.processor;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.prxy001.ProxyRegistrationV01;
import bifast.outbound.model.ProxyMessage;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlProxyRegistrationRequestPojo;
import bifast.outbound.pojo.flat.FlatPrxy002Pojo;
import bifast.outbound.repository.ProxyMessageRepository;

@Component
public class StoreProxyRegistrationProcessor implements Processor{

	@Autowired
	private ProxyMessageRepository proxyMessageRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		
		BusinessMessage proxyRequest = rmw.getProxyRegistrationRequest();
		ProxyRegistrationV01 regRequest = proxyRequest.getDocument().getPrxyRegn();
		
		ProxyMessage proxyMessage = new ProxyMessage();
				
		proxyMessage.setKomiTrnsId(rmw.getKomiTrxId());
		proxyMessage.setChnlNoRef(rmw.getRequestId());

		proxyMessage.setCustomerId(regRequest.getSplmtryData().get(0).getEnvlp().getCstmr().getId());
		proxyMessage.setCustomerType(regRequest.getSplmtryData().get(0).getEnvlp().getCstmr().getTp());
		
		proxyMessage.setFullRequestMesg(rmw.getCihubEncriptedRequest());
		if (null != rmw.getCihubEncriptedResponse()) {
			proxyMessage.setFullResponseMesg(rmw.getCihubEncriptedResponse());
		}

		long timeElapsed = Duration.between(rmw.getCihubStart(), Instant.now()).toMillis();
		proxyMessage.setRequestDt(LocalDateTime.ofInstant(rmw.getCihubStart(), ZoneId.systemDefault()));
		proxyMessage.setCihubElapsedTime(timeElapsed);

		ChnlProxyRegistrationRequestPojo channelRequest = rmw.getChnlProxyRegistrationRequest();
		
		proxyMessage.setOperationType(channelRequest.getRegistrationType());
		proxyMessage.setProxyType(channelRequest.getProxyType());
		proxyMessage.setProxyValue(channelRequest.getProxyValue());
		
		if (null != channelRequest.getDisplayName())
			proxyMessage.setDisplayName(channelRequest.getDisplayName());
		
		if (null != channelRequest.getAccountNumber())
			proxyMessage.setAccountNumber(channelRequest.getAccountNumber());
		if (null != channelRequest.getAccountType())
			proxyMessage.setAccountType(channelRequest.getAccountType());
		if (null != channelRequest.getAccountName())
			proxyMessage.setAccountName(channelRequest.getAccountName());

		proxyMessage.setScndIdType(channelRequest.getSecondIdType());
		proxyMessage.setScndValue(channelRequest.getSecondIdValue());
		
		if (null != channelRequest.getTownName())
			proxyMessage.setTownName(channelRequest.getTownName());
		if (null != channelRequest.getResidentialStatus())
			proxyMessage.setResidentStatus(channelRequest.getResidentialStatus());

		
		Object oBiResponse = exchange.getMessage().getBody(Object.class);

		if (oBiResponse.getClass().getSimpleName().equals("FaultPojo")) {
			FaultPojo fault = (FaultPojo)oBiResponse;
			proxyMessage.setErrorMessage(fault.getErrorMessage());
			proxyMessage.setRespStatus(fault.getReasonCode());
			proxyMessage.setCallStatus(fault.getCallStatus());
		}
			
		else if (oBiResponse.getClass().getSimpleName().equals("FlatAdmi002Pojo")) {
			proxyMessage.setCallStatus("REJECT-CICONN");
			proxyMessage.setRespStatus("RJCT");
			proxyMessage.setErrorMessage("Message Rejected with Admi.002");
		}
		
		else {
			proxyMessage.setCallStatus("SUCCESS");
			FlatPrxy002Pojo prslResponse = (FlatPrxy002Pojo) oBiResponse;
			proxyMessage.setRespStatus(prslResponse.getStatusReason());
			
			if (!(null==rmw.getCihubEncriptedResponse()))
				proxyMessage.setFullResponseMesg(rmw.getCihubEncriptedResponse());
		}

		
		proxyMessageRepo.save(proxyMessage);
		
	}

}
