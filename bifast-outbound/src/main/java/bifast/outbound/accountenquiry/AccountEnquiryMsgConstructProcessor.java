package bifast.outbound.accountenquiry;

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
import bifast.outbound.config.Config;
import bifast.outbound.processor.UtilService;

@Component
@ComponentScan(basePackages = {"bifast.library.iso20022.service", "bifast.library.config"} )
public class AccountEnquiryMsgConstructProcessor implements Processor {

	@Autowired
	private Config config;
	@Autowired
	private AppHeaderService appHeaderService;
	@Autowired
	private Pacs008MessageService pacs008MessageService;
	@Autowired
	private UtilService utilService;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		ChnlAccountEnquiryRequestPojo chnReq = exchange.getIn().getHeader("hdr_channelRequest",ChnlAccountEnquiryRequestPojo.class);

		String msgType = "510";
		String bizMsgId = utilService.genOfiBusMsgId(msgType, chnReq.getChannel());
		String msgId = utilService.genMessageId(msgType);
		
		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();

		hdr = appHeaderService.getAppHdr(chnReq.getRecptBank(), "pacs.008.001.08", bizMsgId);
		
		Pacs008Seed seedAcctEnquiry = new Pacs008Seed();
		
		seedAcctEnquiry.setMsgId(msgId);
		seedAcctEnquiry.setBizMsgId(hdr.getBizMsgIdr());
		seedAcctEnquiry.setAmount(chnReq.getAmount());
		seedAcctEnquiry.setCategoryPurpose(chnReq.getCategoryPurpose());
		seedAcctEnquiry.setCrdtAccountNo(chnReq.getCreditorAccountNumber());
		seedAcctEnquiry.setOrignBank(config.getBankcode());
		seedAcctEnquiry.setRecptBank(chnReq.getRecptBank());
		seedAcctEnquiry.setTrnType(msgType);

		Document doc = new Document();
		doc.setFiToFICstmrCdtTrf(pacs008MessageService.accountEnquiryRequest(seedAcctEnquiry));

		BusinessMessage busMsg = new BusinessMessage();
		busMsg.setAppHdr(hdr);
		busMsg.setDocument(doc);
	
		exchange.getIn().setBody(busMsg);
	}

}
