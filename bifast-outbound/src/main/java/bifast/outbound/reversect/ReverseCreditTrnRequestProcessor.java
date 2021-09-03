package bifast.outbound.reversect;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
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

	    Map<String,Object> out = (Map<String,Object>) exchange.getIn().getHeader("rcv_qryresult");
	    
	    System.out.println("Hasilnya " + out.get("id"));
		
		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = appHeaderService.initAppHdr((String)out.get("recpt_bank"), "pacs.008.001.08", "011", "99");

		Pacs008Seed seed = new Pacs008Seed();
		
		seed.setAmount((BigDecimal) out.get("amount"));
		seed.setBizMsgId(hdr.getBizMsgIdr());
		seed.setCategoryPurpose("99");
		seed.setChannel("99");
		
		seed.setCrdtAccountNo((String) out.get("creditor_acct_no"));		
//		seedCreditTrn.setCrdtAccountType(chnReq.getCrdtAccountType());
		seed.setCrdtId((String) out.get("creditor_id"));
//		seedCreditTrn.setCrdtName(chnReq.getCrdtName());
//		seedCreditTrn.setCrdtIdType(chnReq.getCrdtIdType());
//		
		seed.setDbtrAccountNo((String) out.get("debtor_acct_no"));
//		seed.setDbtrAccountType(chnReq.getDbtrAccountType());
//		seedCreditTrn.setDbtrName(chnReq.getDbtrName());
//		seedCreditTrn.setDbtrId(chnReq.getDbtrId());
//		seedCreditTrn.setDbtrIdType(chnReq.getDbtrIdType());
		seed.setOrignBank(config.getBankcode());
//		seedCreditTrn.setPaymentInfo(chnReq.getPaymentInfo());
		seed.setRecptBank((String) out.get("orign_bank"));
		seed.setTrnType("011");
		
		Document doc = new Document();
		doc.setFiToFICstmrCdtTrf(pacs008MessageService.reverseCreditTransferRequest(seed));

		BusinessMessage busMsg = new BusinessMessage();
		busMsg.setAppHdr(hdr);
		busMsg.setDocument(doc);

		exchange.getIn().setBody(busMsg);

	}

}
