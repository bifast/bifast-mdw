package bifast.outbound.proxyregistration.processor;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.config.Config;
import bifast.outbound.model.Proxy;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.flat.FlatPrxy002Pojo;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationRequestPojo;
import bifast.outbound.repository.ProxyRepository;

@Component
public class StoreProxyRegistrationProcessor implements Processor{
	@Autowired private ProxyRepository proxyRepo;
	@Autowired private Config config;

	private static Logger logger = LoggerFactory.getLogger(StoreProxyRegistrationProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		logger.debug("Akan save data proxy");
		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		ChnlProxyRegistrationRequestPojo chnlRequest = rmw.getChnlProxyRegistrationRequest();
		Object oResponse = exchange.getMessage().getBody(Object.class);
		System.out.println("Response berupa: " + oResponse.getClass().getSimpleName());
		
		if (oResponse.getClass().getSimpleName().equals("FlatPrxy002Pojo")) {
			FlatPrxy002Pojo response = (FlatPrxy002Pojo) oResponse;
			if (response.getResponseCode().equals("ACTC")) {

				Proxy proxy = null;
				Optional<Proxy> oPrxy = proxyRepo.getByProxyTypeAndProxyValue(chnlRequest.getProxyType(), chnlRequest.getProxyValue());

				String lastPrxyStatus = null;
				if (chnlRequest.getRegistrationType().equals("DEAC"))
					lastPrxyStatus = "ICTV";
				else if (chnlRequest.getRegistrationType().equals("SUSP"))
					lastPrxyStatus = "SUSP";
				else if (chnlRequest.getRegistrationType().equals("SUSB"))
					lastPrxyStatus = "SUSB";
				else
					lastPrxyStatus = "ACTV";
					
				if (oPrxy.isEmpty()) {
					proxy = new Proxy();
					proxy.setCreateDt(LocalDateTime.now());
					proxy.setProxyType(chnlRequest.getProxyType());
					proxy.setProxyValue(chnlRequest.getProxyValue());
					proxy.setRegistrationId(response.getRegistrationId());
				}
				
				else {
					proxy = oPrxy.get();
				}

				proxy.setRegisterBank(config.getBankcode());
				
				proxy.setAccountNumber(chnlRequest.getAccountNumber());
				proxy.setDisplayName(chnlRequest.getDisplayName());
				proxy.setProxyStatus(lastPrxyStatus);
				proxy.setScndIdType(response.getCustomerType());
				proxy.setScndValue(response.getCustomerId());
				proxy.setUpdateDt(LocalDateTime.now());
				
				proxyRepo.save(proxy);
					
			}
		}

			
	}
			
}

	
	
//	@Override
//	public void process(Exchange exchange) throws Exception {
//
//		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
//		
//		ChnlProxyRegistrationRequestPojo channelRequest = rmw.getChnlProxyRegistrationRequest();
//
////		BusinessMessage proxyRequest = rmw.getProxyRegistrationRequest();
////		ProxyRegistrationV01 regRequest = proxyRequest.getDocument().getPrxyRegn();
//		
//		ProxyMessage proxyMessage = new ProxyMessage();
//				
//		proxyMessage.setKomiTrnsId(rmw.getKomiTrxId());
//		proxyMessage.setChnlNoRef(rmw.getRequestId());
//
//		proxyMessage.setCustomerId(channelRequest.getSecondIdValue());
//		proxyMessage.setCustomerType(channelRequest.getSecondIdType());
//		
//		proxyMessage.setFullRequestMesg(rmw.getCihubEncriptedRequest());
//		if (null != rmw.getCihubEncriptedResponse()) {
//			proxyMessage.setFullResponseMesg(rmw.getCihubEncriptedResponse());
//		}
//
//		long timeElapsed = Duration.between(rmw.getCihubStart(), Instant.now()).toMillis();
//		proxyMessage.setRequestDt(LocalDateTime.ofInstant(rmw.getCihubStart(), ZoneId.systemDefault()));
//		proxyMessage.setCihubElapsedTime(timeElapsed);
//
//		proxyMessage.setOperationType(channelRequest.getRegistrationType());
//		proxyMessage.setProxyType(channelRequest.getProxyType());
//		proxyMessage.setProxyValue(channelRequest.getProxyValue());
//		
//		if (null != channelRequest.getDisplayName())
//			proxyMessage.setDisplayName(channelRequest.getDisplayName());
//		
//		if (null != channelRequest.getAccountNumber())
//			proxyMessage.setAccountNumber(channelRequest.getAccountNumber());
//		if (null != channelRequest.getAccountType())
//			proxyMessage.setAccountType(channelRequest.getAccountType());
//		if (null != channelRequest.getAccountName())
//			proxyMessage.setAccountName(channelRequest.getAccountName());
//
//		proxyMessage.setScndIdType(channelRequest.getSecondIdType());
//		proxyMessage.setScndValue(channelRequest.getSecondIdValue());
//		
//		if (null != channelRequest.getTownName())
//			proxyMessage.setTownName(channelRequest.getTownName());
//		if (null != channelRequest.getResidentialStatus())
//			proxyMessage.setResidentStatus(channelRequest.getResidentialStatus());
//
//		
//		Object oBiResponse = exchange.getMessage().getBody(Object.class);
//
//		if (oBiResponse.getClass().getSimpleName().equals("FaultPojo")) {
//			FaultPojo fault = (FaultPojo)oBiResponse;
//			proxyMessage.setErrorMessage(fault.getErrorMessage());
//			proxyMessage.setRespStatus(fault.getReasonCode());
//			proxyMessage.setCallStatus(fault.getCallStatus());
//		}
//			
//		else if (oBiResponse.getClass().getSimpleName().equals("FlatAdmi002Pojo")) {
//			proxyMessage.setCallStatus("REJECT-CICONN");
//			proxyMessage.setRespStatus("RJCT");
//			proxyMessage.setErrorMessage("Message Rejected with Admi.002");
//		}
//		
//		else {
//			proxyMessage.setCallStatus("SUCCESS");
//			FlatPrxy002Pojo prslResponse = (FlatPrxy002Pojo) oBiResponse;
//			proxyMessage.setRespStatus(prslResponse.getStatusReason());
//			
//			if (!(null==rmw.getCihubEncriptedResponse()))
//				proxyMessage.setFullResponseMesg(rmw.getCihubEncriptedResponse());
//		}
//
//		
//		proxyMessageRepo.save(proxyMessage);
//		
//	}


