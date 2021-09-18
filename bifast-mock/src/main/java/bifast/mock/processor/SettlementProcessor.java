package bifast.mock.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.AccountIdentification4Choice;
import bifast.library.iso20022.pacs002.BISupplementaryData1;
import bifast.library.iso20022.pacs002.BISupplementaryDataEnvelope1;
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

		BusinessMessage in = exchange.getMessage().getHeader("hdr_ctResponseObj", BusinessMessage.class);
		String fullMsg = exchange.getMessage().getBody(String.class);
		String bizMsgId = "";
		String msgId = "";
		String msgType = exchange.getMessage().getHeader("msgType", String.class);
		String orgnlMsgId = "";

		if (msgType.equals("CreditTransferRequest")) {
			bizMsgId = utilService.genRfiBusMsgId("010", "02");
			msgId = utilService.genMessageId("010");

			orgnlMsgId = objRequest.getDocument().getFiToFICstmrCdtTrf().getGrpHdr().getMsgId();

		} else {
			bizMsgId = utilService.genRfiBusMsgId("019", "02");
			msgId = utilService.genMessageId("019");
			orgnlMsgId = objRequest.getDocument().getFiCdtTrf().getGrpHdr().getMsgId();
		}

		if (msgType.equals("CreditTransferRequest")) {

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
			id2.setOthr(oth2);

			CashAccount38 dbtrAcctT = new CashAccount38();
			dbtrAcctT.setId(id2);

			in.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().setCdtrAcct(cdtrAcctT);
			in.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().setDbtrAcct(dbtrAcctT);

		}

	
		in.getAppHdr().setBizMsgIdr(bizMsgId);
		in.getDocument().getFiToFIPmtStsRpt().getGrpHdr().setMsgId(msgId);
		in.getAppHdr().setBizSvc("SETTLEMENTCONFIRMATION");
		
		GenericAccountIdentification1 dbtrAgtAcctIdOth = new GenericAccountIdentification1();
		dbtrAgtAcctIdOth.setId("123456");
		AccountIdentification4Choice dbtrAgtAcctId = new AccountIdentification4Choice();
		dbtrAgtAcctId.setOthr(dbtrAgtAcctIdOth);		
		CashAccount38 dbtrAgtAcct = new CashAccount38();
		dbtrAgtAcct.setId(dbtrAgtAcctId);
		
		GenericAccountIdentification1 cdtrAgtAcctIdOth = new GenericAccountIdentification1();
		cdtrAgtAcctIdOth.setId("654321");
		AccountIdentification4Choice cdtrAgtAcctId = new AccountIdentification4Choice();
		cdtrAgtAcctId.setOthr(dbtrAgtAcctIdOth);		
		CashAccount38 cdtrAgtAcct = new CashAccount38();
		cdtrAgtAcct.setId(cdtrAgtAcctId);
		
		
		if (in.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getSplmtryData().size() == 0 ) {
		
			BISupplementaryData1 supplData = new BISupplementaryData1();
			in.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getSplmtryData().add(supplData);
			
			BISupplementaryDataEnvelope1 dataEnvl = new BISupplementaryDataEnvelope1();
			in.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getSplmtryData().get(0).setEnvlp(dataEnvl);
		}
		
		in.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getSplmtryData().get(0).getEnvlp().setCdtrAgtAcct(cdtrAgtAcct);
		in.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getSplmtryData().get(0).getEnvlp().setDbtrAgtAcct(dbtrAgtAcct);

		
		MockPacs002 pacs002 = new MockPacs002();
		pacs002.setBizMsgIdr(bizMsgId);
		pacs002.setFullMessage(fullMsg);
		pacs002.setOrgnlEndToEndId(objRequest.getAppHdr().getBizMsgIdr());
		pacs002.setOrgnlMsgId(orgnlMsgId);
		pacs002.setOrgnlMsgName(objRequest.getAppHdr().getMsgDefIdr());

		pacs002.setTrxType("SettlementConfirmation");
		mockPacs002Repo.save(pacs002);

		exchange.getMessage().setBody(in);
	}

}
