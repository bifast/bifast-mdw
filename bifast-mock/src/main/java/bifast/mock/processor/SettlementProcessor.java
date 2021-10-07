package bifast.mock.processor;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

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
	
		BusinessMessage in = exchange.getMessage().getBody(BusinessMessage.class);		

//		BusinessMessage in = exchange.getMessage().getHeader("hdr_ctResponseObj", BusinessMessage.class);
		HashMap<String, String> frTable = exchange.getMessage().getHeader("sttl_tableqry", HashMap.class);
		
		String bizMsgId = "";
		String msgId = "";
		String msgName = frTable.get("ORGNL_MSG_NAME");
		
		String orgnlMsgId = exchange.getMessage().getHeader("sttl_orgnlmsgid", String.class);

		if (msgName.startsWith("pacs.008")) {
			bizMsgId = utilService.genRfiBusMsgId("010", "02", in.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId());
			msgId = utilService.genMessageId("010", in.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId());
System.out.println(bizMsgId);

		} else {
			bizMsgId = utilService.genRfiBusMsgId("019", "02", 
							in.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());
			msgId = utilService.genMessageId("019",
							in.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());
		}
		
		if (msgName.startsWith("pacs.008")) {

			String cdtrAcct = frTable.get("cdtr_acct");
			String dbtrAcct = frTable.get("dbtr_acct"); 
		
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
		in.getAppHdr().setBizSvc("STTL");
		
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

		
		ObjectMapper map = new ObjectMapper();
		map.registerModule(new JaxbAnnotationModule());
		map.enable(SerializationFeature.WRAP_ROOT_VALUE);

		String strSttl = map.writeValueAsString(in);
		
		MockPacs002 pacs002 = new MockPacs002();
		pacs002.setBizMsgIdr(bizMsgId);
		pacs002.setFullMessage(strSttl);
		pacs002.setOrgnlEndToEndId(frTable.get("orgnl_end_to_end_id"));
		pacs002.setOrgnlMsgId(orgnlMsgId);
		pacs002.setOrgnlMsgName(msgName);
		pacs002.setSttl("DONE");

		pacs002.setTrxType("STTL");
		mockPacs002Repo.save(pacs002);

		exchange.getMessage().setBody(in);
	}

}
