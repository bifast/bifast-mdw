package bifast.outbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV02;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Pacs008MessageService;
import bifast.library.iso20022.service.Pacs008Seed;
import bifast.outbound.config.Config;
import bifast.outbound.pojo.ChannelCreditTransferRequest;

@Component
public class CreditTransferRequestProcessor implements Processor {

	@Autowired
	private Config config;
	@Autowired
	private AppHeaderService appHeaderService;
	@Autowired
	private Pacs008MessageService pacs008MessageService;

	@Override
	public void process(Exchange exchange) throws Exception {

		ChannelCreditTransferRequest chnReq = exchange.getIn().getHeader("req_channelReq",ChannelCreditTransferRequest.class);
		
		BusinessApplicationHeaderV02 hdr = new BusinessApplicationHeaderV02();
		hdr = appHeaderService.initAppHdr(chnReq.getRecptBank(), "pacs.008.001.08", "010", chnReq.getChannel());

		Pacs008Seed seedCreditTrn = new Pacs008Seed();
		seedCreditTrn.setAmount(chnReq.getAmount());
		seedCreditTrn.setBizMsgId(hdr.getBizMsgIdr());
		seedCreditTrn.setCategoryPurpose(chnReq.getCategoryPurpose());
		
		seedCreditTrn.setChannel(chnReq.getChannel());
		
		seedCreditTrn.setCrdtAccountNo(chnReq.getCrdtAccountNo());		
		seedCreditTrn.setCrdtAccountType(chnReq.getCrdtAccountType());
		seedCreditTrn.setCrdtId(chnReq.getCrdtId());
		seedCreditTrn.setCrdtIdType(chnReq.getCrdtIdType());
		
		seedCreditTrn.setDbtrAccountNo(chnReq.getDbtrAccountNo());
		seedCreditTrn.setDbtrAccountType(chnReq.getDbtrAccountType());
		seedCreditTrn.setDbtrId(chnReq.getDbtrId());
		seedCreditTrn.setDbtrIdType(chnReq.getDbtrIdType());
		seedCreditTrn.setOrignBank(config.getBankcode());
		seedCreditTrn.setPaymentInfo(chnReq.getPaymentInfo());
		seedCreditTrn.setRecptBank(chnReq.getRecptBank());
		seedCreditTrn.setTrnType("010");
		
		Document doc = new Document();
		doc.setFiToFICstmrCdtTrf(pacs008MessageService.creditTransferRequest(seedCreditTrn));

		BusinessMessage busMsg = new BusinessMessage();
		busMsg.setAppHdr(hdr);
		busMsg.setDocument(doc);

		exchange.getIn().setBody(busMsg);

	}

}
