package bifast.outbound.paymentstatus;

import java.util.List;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Pacs028MessageService;
import bifast.library.iso20022.service.Pacs028Seed;
import bifast.library.model.CreditTransfer;
import bifast.library.model.Settlement;
import bifast.library.repository.CreditTransferRepository;
import bifast.library.repository.SettlementRepository;
import bifast.outbound.config.Config;

@Component
public class PaymentStatusRequestProcessor implements Processor {

	@Autowired
	private AppHeaderService appHeaderService;
	@Autowired
	private Pacs028MessageService pacs028MessageService;
	@Autowired
	private Config config;
	

	@Override
	public void process(Exchange exchange) throws Exception {
		PaymentStatusRequest psReq = exchange.getMessage().getBody(PaymentStatusRequest.class);
			
		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = appHeaderService.initAppHdr(config.getBicode(), "pacs.028.001.04", "000", "99");

		Pacs028Seed seed = new Pacs028Seed();	
		seed.setOrgnlEndToEnd(psReq.getEndToEndId());

		Document doc = new Document();
		doc.setFIToFIPmtStsReq(pacs028MessageService.paymentStatusRequest(seed));

		BusinessMessage busMsg = new BusinessMessage();
		busMsg.setAppHdr(hdr);
		busMsg.setDocument(doc);
	
		exchange.getIn().setBody(busMsg);

	}

}
