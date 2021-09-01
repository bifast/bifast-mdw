package bifast.outbound.reversect;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Pacs008MessageService;
import bifast.library.iso20022.service.Pacs008Seed;
import bifast.outbound.config.Config;

@Component
public class ReverseCreditTrnRequestProcessor implements Processor {

	@Autowired
	private Config config;
	@Autowired
	private AppHeaderService appHeaderService;
	@Autowired
	private Pacs008MessageService pacs008MessageService;

	@Override
	public void process(Exchange exchange) throws Exception {

		ChannelReverseCreditTransferRequest chnReq = exchange.getIn().getHeader("req_channelReq",ChannelReverseCreditTransferRequest.class);
		
		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = appHeaderService.initAppHdr(chnReq.getRecptBank(), "pacs.008.001.08", "011", "99");

		Pacs008Seed seedCreditTrn = new Pacs008Seed();
		seedCreditTrn.setAmount(chnReq.getAmount());
		seedCreditTrn.setBizMsgId(hdr.getBizMsgIdr());
		seedCreditTrn.setCategoryPurpose("99");
		
		seedCreditTrn.setChannel("99");
		
		seedCreditTrn.setCrdtAccountNo(chnReq.getCrdtAccountNo());		
		seedCreditTrn.setCrdtAccountType(chnReq.getCrdtAccountType());
		seedCreditTrn.setCrdtId(chnReq.getCrdtId());
		seedCreditTrn.setCrdtName(chnReq.getCrdtName());
		seedCreditTrn.setCrdtIdType(chnReq.getCrdtIdType());
		
		seedCreditTrn.setDbtrAccountNo(chnReq.getDbtrAccountNo());
		seedCreditTrn.setDbtrAccountType(chnReq.getDbtrAccountType());
		seedCreditTrn.setDbtrName(chnReq.getDbtrName());
		seedCreditTrn.setDbtrId(chnReq.getDbtrId());
		seedCreditTrn.setDbtrIdType(chnReq.getDbtrIdType());
		seedCreditTrn.setOrignBank(config.getBankcode());
		seedCreditTrn.setPaymentInfo(chnReq.getPaymentInfo());
		seedCreditTrn.setRecptBank(chnReq.getRecptBank());
		seedCreditTrn.setTrnType("011");
		
		Document doc = new Document();
		doc.setFiToFICstmrCdtTrf(pacs008MessageService.creditTransferRequest(seedCreditTrn));

		BusinessMessage busMsg = new BusinessMessage();
		busMsg.setAppHdr(hdr);
		busMsg.setDocument(doc);

		exchange.getIn().setBody(busMsg);

	}

}
