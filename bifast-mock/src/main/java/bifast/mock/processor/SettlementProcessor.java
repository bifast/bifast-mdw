package bifast.mock.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.AccountIdentification4Choice;
import bifast.library.iso20022.pacs002.CashAccount38;
import bifast.library.iso20022.pacs002.GenericAccountIdentification1;
import bifast.mock.persist.MockPacs002;
import bifast.mock.persist.MockPacs002Repository;

@Component
public class SettlementProcessor implements Processor {

	@Autowired
	private MockPacs002Repository mockPacs002Repo;
	@Autowired
	private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {
	
		BusinessMessage objRequest = exchange.getMessage().getHeader("objRequest", BusinessMessage.class);		

		String cdtrAcct = objRequest.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId();
		String dbtrAcct = objRequest.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAcct().getId().getOthr().getId();

		
		GenericAccountIdentification1 oth1 = new GenericAccountIdentification1();
		oth1.setId(cdtrAcct);
		
		AccountIdentification4Choice id1 = new AccountIdentification4Choice();
		id1.setOthr(oth1);

		CashAccount38 cdtrAcctT = new CashAccount38();
		cdtrAcctT.setId(id1);

		GenericAccountIdentification1 oth2 = new GenericAccountIdentification1();
		oth2.setId(dbtrAcct);
		AccountIdentification4Choice id2 = new AccountIdentification4Choice();
		id1.setOthr(oth2);

		CashAccount38 dbtrAcctT = new CashAccount38();
		dbtrAcctT.setId(id1);


		String bizMsgId = utilService.genRfiBusMsgId("010", "02");
		String msgId = utilService.genMessageId("010");

		BusinessMessage in = exchange.getMessage().getBody(BusinessMessage.class);
	
		in.getAppHdr().setBizMsgIdr(bizMsgId);
		in.getDocument().getFiToFIPmtStsRpt().getGrpHdr().setMsgId(msgId);

		in.getAppHdr().setBizSvc("SETTLEMENTCONFIRMATION");
		in.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().setCdtrAcct(cdtrAcctT);
		in.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().setDbtrAcct(dbtrAcctT);


		MockPacs002 pacs002 = new MockPacs002();
		pacs002.setBizMsgIdr(bizMsgId);
		// pacs002.setFullMessage(fullMessage);
		pacs002.setOrgnlEndToEndId(objRequest.getAppHdr().getBizMsgIdr());
		pacs002.setOrgnlMsgId(objRequest.getDocument().getFiToFICstmrCdtTrf().getGrpHdr().getMsgId());
		pacs002.setOrgnlMsgName(objRequest.getAppHdr().getMsgDefIdr());

		pacs002.setTrxType("SettlementConfirmation");
		mockPacs002Repo.save(pacs002);


		exchange.getMessage().setBody(in);
	}

}
