package bifast.outbound.proxyregistration.processor;

import java.time.LocalDateTime;
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
		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
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



