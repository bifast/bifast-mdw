package bifast.outbound.proxyinquiry.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.prxy003.ProxyLookUpType1Code;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Proxy003MessageService;
import bifast.library.iso20022.service.Proxy003Seed;
import bifast.outbound.config.Config;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.proxyinquiry.pojo.ChnlProxyResolutionRequestPojo;
import bifast.outbound.service.UtilService;

@Component
public class ProxyResolutionRequestProcessor implements Processor {

	@Autowired
	private Config config;
	@Autowired
	private AppHeaderService appHeaderService;
	@Autowired
	private Proxy003MessageService proxy003MessageService;
	@Autowired
	private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {

		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		ChnlProxyResolutionRequestPojo chnReq = rmw.getChnlProxyResolutionRequest();
		
		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();

		String trxType = "610";
		String bizMsgId = utilService.genBusMsgId(trxType, rmw);
		String msgId = utilService.genMessageId(trxType, rmw);
		String trxId = msgId.replace(trxType, "");
		
		hdr = appHeaderService.getAppHdr(config.getBicode(), "prxy.003.001.01", bizMsgId);
		
		Proxy003Seed seedProxyResolution = new Proxy003Seed();
		
		seedProxyResolution.setMsgId(msgId);
		
		seedProxyResolution.setId(trxId);
		
		seedProxyResolution.setTrnType(trxType);
		
		seedProxyResolution.setLookupType(ProxyLookUpType1Code.PXRS);
		
		seedProxyResolution.setProxyType(chnReq.getProxyType());
		seedProxyResolution.setProxyValue(chnReq.getProxyValue());
		
		seedProxyResolution.setSndrAccountNumber(chnReq.getSenderAccountNumber());
		
		Document doc = new Document();
		doc.setPrxyLookUp(proxy003MessageService.proxyResolutionRequest(seedProxyResolution));

		BusinessMessage busMsg = new BusinessMessage();
		busMsg.setAppHdr(hdr);
		busMsg.setDocument(doc);
	
		rmw.setProxyResolutionRequest(busMsg);
		exchange.getMessage().setHeader("hdr_request_list",  rmw);
		exchange.getIn().setBody(busMsg);
	}

}
