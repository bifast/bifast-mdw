package bifast.outbound.proxyinquiry.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Proxy005MessageService;
import bifast.library.iso20022.service.Proxy005Seed;
import bifast.outbound.config.Config;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationInquiryRequestPojo;
import bifast.outbound.service.UtilService;

@Component
public class ProxyRegistrationInquiryRequestProcessor implements Processor {

	@Autowired
	private Config config;
	@Autowired
	private AppHeaderService appHeaderService;
	@Autowired
	private Proxy005MessageService proxy005MessageService;
	@Autowired
	private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {

		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
		ChnlProxyRegistrationInquiryRequestPojo chnReq = rmw.getChnlProxyRegistrationInquiryRequest();
		
		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();

		String trxType = "610";
		String bizMsgId = utilService.genBusMsgId(trxType, rmw);
		String msgId = utilService.genMessageId(trxType, rmw);
		
		hdr = appHeaderService.getAppHdr(config.getBicode(), "prxy.005.001.01", bizMsgId);
		
		Proxy005Seed seedProxyResolution = new Proxy005Seed();
		
		seedProxyResolution.setMsgId(msgId);
		seedProxyResolution.setTrnType(trxType);
		
		seedProxyResolution.setScndIdType(chnReq.getScndIdType());
		seedProxyResolution.setScndIdValue(chnReq.getScndIdValue());
		
		Document doc = new Document();
		doc.setPrxyNqryReq(proxy005MessageService.proxyRegistrationInquiryRequest(seedProxyResolution));

		BusinessMessage busMsg = new BusinessMessage();
		busMsg.setAppHdr(hdr);
		busMsg.setDocument(doc);
	
		rmw.setProxyRegistrationRequest(busMsg);
		exchange.setProperty("prop_request_list",  rmw);
		exchange.getIn().setBody(busMsg);
	}

}
