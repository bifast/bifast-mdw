package bifast.outbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Pacs009MessageService;
import bifast.library.iso20022.service.Pacs009Seed;
import bifast.outbound.config.Config;
import bifast.outbound.pojo.ChannelFICreditTransferReq;

@Component
//@ComponentScan(basePackages = {"bifast.library.iso20022.service", "bifast.library.config"} )
public class FICreditTransferRequestProcessor implements Processor {

	@Autowired
	private Config config;
	@Autowired
	private AppHeaderService appHeaderService;
	@Autowired
	private Pacs009MessageService pacs009MessageService;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		ChannelFICreditTransferReq chnReq = exchange.getIn().getBody(ChannelFICreditTransferReq.class);

		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = appHeaderService.initAppHdr(chnReq.getRecptBank(), "pacs.009.001.09", "019", chnReq.getChannel());
		
		Pacs009Seed seedFICT = new Pacs009Seed();
		
		seedFICT.setBizMsgId(hdr.getBizMsgIdr());
		seedFICT.setAmount(chnReq.getAmount());
		seedFICT.setOrignBank(config.getBankcode());
		seedFICT.setRecptBank(chnReq.getRecptBank());
		seedFICT.setTrnType("019");

		Document doc = new Document();
		doc.setFiCdtTrf(pacs009MessageService.creditTransferRequest(seedFICT));

		BusinessMessage busMsg = new BusinessMessage();
		busMsg.setAppHdr(hdr);
		busMsg.setDocument(doc);
	
		exchange.getIn().setBody(busMsg);
	}

}
