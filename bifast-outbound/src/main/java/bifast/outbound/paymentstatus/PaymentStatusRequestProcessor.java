package bifast.outbound.paymentstatus;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Pacs008MessageService;
import bifast.library.iso20022.service.Pacs008Seed;
import bifast.library.iso20022.service.Pacs028MessageService;
import bifast.library.iso20022.service.Pacs028Seed;
import bifast.outbound.accountenquiry.ChannelAccountEnquiryReq;
import bifast.outbound.config.Config;

@Component
//@ComponentScan(basePackages = {"bifast.library.iso20022.service", "bifast.library.config"} )
public class PaymentStatusRequestProcessor implements Processor {

	@Autowired
	private Config config;
	@Autowired
	private AppHeaderService appHeaderService;
	@Autowired
	private Pacs028MessageService pacs028MessageService;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		ChannelPaymentStatusRequest chnReq = exchange.getIn().getBody(ChannelPaymentStatusRequest.class);

		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = appHeaderService.initAppHdr(chnReq.getRecptBank(), "pacs.028.001.04", "000", "99");
		
		Pacs028Seed seed = new Pacs028Seed();
		
		seed.setOrgnlEndToEnd(chnReq.getEndToEndId());

		Document doc = new Document();
		doc.setFIToFIPmtStsReq(pacs028MessageService.paymentStatusRequest(seed));

		BusinessMessage busMsg = new BusinessMessage();
		busMsg.setAppHdr(hdr);
		busMsg.setDocument(doc);
	
		exchange.getIn().setBody(busMsg);
	}

}
