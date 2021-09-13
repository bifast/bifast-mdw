package bifast.outbound.reversect;

import java.math.BigDecimal;
import java.util.Map;

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
import bifast.outbound.processor.UtilService;

@Component
public class ReverseCreditTrnRequestProcessor implements Processor {

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

	    Map<String,Object> out = (Map<String,Object>) exchange.getIn().getHeader("rcv_qryresult");
	    
		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		String bizMsgId = utilService.genOfiBusMsgId("011", "99");
		String msgId = utilService.genMessageId("011");
		
		hdr = appHeaderService.getAppHdr((String)out.get("orign_bank"), "pacs.008.001.08", bizMsgId);

		Pacs008Seed seed = new Pacs008Seed();
		
		seed.setMsgId(msgId);
		seed.setAmount((BigDecimal) out.get("amount"));
		seed.setBizMsgId(hdr.getBizMsgIdr());
		seed.setCategoryPurpose("99");
		seed.setChannel("99");
		
		seed.setDbtrId((String) out.get("creditor_id"));
		seed.setDbtrIdType((String)out.get("creditor_type"));
		seed.setDbtrAccountNo((String) out.get("creditor_acct_no"));
		seed.setDbtrAccountType((String) out.get("creditor_acct_type"));

		seed.setCrdtId((String) out.get("debtor_id"));
		seed.setCrdtIdType((String)out.get("debtor_type"));
		seed.setCrdtAccountNo((String) out.get("debtor_acct_no"));
		seed.setCrdtAccountType((String) out.get("debtor_acct_type"));

		seed.setOrignBank(config.getBankcode());
		seed.setPaymentInfo("Reversal");

		seed.setRecptBank((String) out.get("orign_bank"));
		seed.setTrnType("011");
		
		seed.setEndToEndId((String) out.get("crdttrn_req_bizmsgid"));
		
		Document doc = new Document();
		doc.setFiToFICstmrCdtTrf(pacs008MessageService.reverseCreditTransferRequest(seed));

		BusinessMessage busMsg = new BusinessMessage();
		busMsg.setAppHdr(hdr);
		busMsg.setDocument(doc);

		exchange.getIn().setBody(busMsg);

	}

}
