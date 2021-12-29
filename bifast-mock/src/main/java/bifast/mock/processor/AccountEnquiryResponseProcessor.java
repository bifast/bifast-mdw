package bifast.mock.processor;

import java.util.Optional;
import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs002.FIToFIPaymentStatusReportV10;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Pacs002MessageService;
import bifast.library.iso20022.service.Pacs002Seed;
import bifast.mock.persist.AccountProxy;
import bifast.mock.persist.AccountProxyRepository;

@Component
@ComponentScan(basePackages = {"bifast.library.iso20022.service", "bifast.library.config"} )
public class AccountEnquiryResponseProcessor implements Processor {
	@Autowired AccountProxyRepository accountRepo;
	@Autowired
	private AppHeaderService hdrService;
	@Autowired
	private Pacs002MessageService pacs002Service;
	@Autowired
	private UtilService utilService;
	
	@Override
	public void process(Exchange exchange) throws Exception {


		BusinessMessage msg = exchange.getIn().getBody(BusinessMessage.class);
		String bizMsgId = utilService.genRfiBusMsgId("510", "02", msg.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());
		String msgId = utilService.genMessageId("510", msg.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());

		String acctNo = msg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId();
		String bank = msg.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId();
		exchange.getMessage().setHeader("hdr_account_no", acctNo);

		AccountProxy account = null;
		Optional<AccountProxy> oAcct = accountRepo.findByAccountNumberAndRegisterBank(acctNo, bank);
		
		Pacs002Seed seed = new Pacs002Seed();
		seed.setMsgId(msgId);
		seed.setCreditorAccountNo(acctNo);

		if (oAcct.isPresent()) {
			account = oAcct.get();
			
//			if (account.getAccountStatus().equals("ACTV")) {
				seed.setStatus("ACTC");
				seed.setReason("U000");				
//			}
//			else {
//				seed.setStatus("RJCT");
//				seed.setReason("U102");				
//			}
			
			seed.setCreditorName(account.getAccountName());
			seed.setCreditorAccountIdType(account.getAccountType());
			seed.setCreditorType(account.getCstmrTp());
			seed.setCreditorId(account.getCstmrId());
			seed.setCreditorTown(account.getCstmrTwnNm());
			seed.setCreditorResidentialStatus(account.getCstmrRsdntSts());
		}
		
		else {
			seed.setStatus("RJCT");
			seed.setReason("U101");
			seed.setCreditorAccountIdType("OTHR");
		}
		
		
		FIToFIPaymentStatusReportV10 response = pacs002Service.accountEnquiryResponse(seed, msg);
		
		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = hdrService.getAppHdr(msg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId(), 
									"pacs.002.001.10", bizMsgId);
		
		Document doc = new Document();
		doc.setFiToFIPmtStsRpt(response);
		
		BusinessMessage busMesg = new BusinessMessage();
		busMesg.setAppHdr(hdr);
		busMesg.setDocument(doc);
		
		exchange.getMessage().setBody(busMesg);
		
	}

}
