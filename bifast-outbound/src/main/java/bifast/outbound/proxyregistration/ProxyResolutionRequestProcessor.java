package bifast.outbound.proxyregistration;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.prxy003.ProxyLookUpType1Code;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Proxy003MessageService;
import bifast.library.iso20022.service.Proxy003Seed;
import bifast.outbound.config.Config;

@Component
@ComponentScan(basePackages = {"bifast.library.iso20022.service", "bifast.library.config"} )
public class ProxyResolutionRequestProcessor implements Processor {

	@Autowired
	private Config config;
	@Autowired
	private AppHeaderService appHeaderService;
	@Autowired
	private Proxy003MessageService proxy003MessageService;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		ChannelProxyResolutionReq chnReq = exchange.getIn().getBody(ChannelProxyResolutionReq.class);

		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = appHeaderService.initAppHdr(config.getBicode(), "prxy.003.001.01", "610", chnReq.getChannel());
		
		Proxy003Seed seedProxyResolution = new Proxy003Seed();
		
		seedProxyResolution.setTrnType("610");
		
		if (chnReq.getLookupType().equals("PXRS")) seedProxyResolution.setLookupType(ProxyLookUpType1Code.PXRS);
		else if (chnReq.getLookupType().equals("CHCK")) seedProxyResolution.setLookupType(ProxyLookUpType1Code.CHCK);
		else if (chnReq.getLookupType().equals("NMEQ")) seedProxyResolution.setLookupType(ProxyLookUpType1Code.NMEQ);

		seedProxyResolution.setProxyType(chnReq.getProxyType());
		seedProxyResolution.setProxyValue(chnReq.getProxyValue());
		
		Document doc = new Document();
		doc.setPrxyLookUp(proxy003MessageService.proxyResolutionRequest(seedProxyResolution));

		BusinessMessage busMsg = new BusinessMessage();
		busMsg.setAppHdr(hdr);
		busMsg.setDocument(doc);
	
		exchange.getIn().setBody(busMsg);
	}

}
