package bifast.outbound.proxyregistration.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Proxy001MessageService;
import bifast.library.iso20022.service.Proxy001Seed;
import bifast.outbound.config.Config;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationRequestPojo;
import bifast.outbound.service.UtilService;

@Component
@ComponentScan(basePackages = {"bifast.library.iso20022.service", "bifast.library.config"} )
public class ProxyRegistrationRequestProcessor implements Processor {

	@Autowired
	private Config config;
	@Autowired
	private AppHeaderService appHeaderService;
	@Autowired
	private Proxy001MessageService proxy001MessageService;
	@Autowired
	private UtilService utilService;

	private static Logger logger = LoggerFactory.getLogger(ProxyRegistrationRequestProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		RequestMessageWrapper rmw = exchange.getIn().getHeader("hdr_request_list", RequestMessageWrapper.class);
		ChnlProxyRegistrationRequestPojo chnReq = rmw.getChnlProxyRegistrationRequest();

		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		
		String trxType = "710";
		String bizMsgId = utilService.genBusMsgId(trxType, rmw);
		String msgId = utilService.genMessageId(trxType, rmw);
		
		hdr = appHeaderService.getAppHdr(config.getBicode(), "prxy.001.001.01", bizMsgId);
		
		if (null == chnReq.getCustomerId()) 
			chnReq.setCustomerId(chnReq.getSecondIdValue());
			
		Proxy001Seed seedProxyRegis = new Proxy001Seed();
		
		seedProxyRegis.setMsgId(msgId);
		seedProxyRegis.setRegistrationType(chnReq.getRegistrationType());
		seedProxyRegis.setProxyType(chnReq.getProxyType());
		seedProxyRegis.setProxyValue(chnReq.getProxyValue());
		seedProxyRegis.setRegistrationId(msgId);
		
		logger.debug("Registration Type: " + chnReq.getRegistrationType());
		logger.debug("Registration ID: " + chnReq.getRegistrationId());
		if (null != chnReq.getRegistrationId()) 
			seedProxyRegis.setRegistrationId(chnReq.getRegistrationId());
		
		seedProxyRegis.setRegisterDisplayName(chnReq.getDisplayName());
	
		seedProxyRegis.setRegisterAgentId(config.getBankcode());
		
		seedProxyRegis.setRegisterAccountType(chnReq.getAccountType());
		seedProxyRegis.setRegisterAccountName(chnReq.getAccountName());
		
		seedProxyRegis.setRegisterAccountNumber(chnReq.getAccountNumber());
		
		seedProxyRegis.setRegisterSecondIdType(chnReq.getSecondIdType());
		seedProxyRegis.setRegisterSecondIdValue(chnReq.getSecondIdValue());
		
		if (null != chnReq.getCustomerType())
			seedProxyRegis.setCustomerType(chnReq.getCustomerType());
		if (null != chnReq.getCustomerId())
			seedProxyRegis.setCustomerId(chnReq.getCustomerId());
		if (null != chnReq.getResidentialStatus())
			seedProxyRegis.setResidentialStatus(chnReq.getResidentialStatus());
		if (null != chnReq.getTownName())
			seedProxyRegis.setTownName(chnReq.getTownName());
		
		seedProxyRegis.setSenderAccountNumber(chnReq.getAccountNumber());
		
		Document doc = new Document();
		doc.setPrxyRegn(proxy001MessageService.proxyRegistrationRequest(seedProxyRegis));

		BusinessMessage busMsg = new BusinessMessage();
		busMsg.setAppHdr(hdr);
		busMsg.setDocument(doc);
	
		rmw.setProxyRegistrationRequest(busMsg);
		exchange.getMessage().setHeader("hdr_request_list", rmw);
		exchange.getIn().setBody(busMsg);
		
	}

}
