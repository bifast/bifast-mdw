package bifast.mock.inbound;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.AccountIdentification4Choice;
import bifast.library.iso20022.pacs002.BISupplementaryData1;
import bifast.library.iso20022.pacs002.BISupplementaryDataEnvelope1;
import bifast.library.iso20022.pacs002.CashAccount38;
import bifast.library.iso20022.pacs002.CashAccountType2Choice;
import bifast.library.iso20022.pacs002.GenericAccountIdentification1;
import bifast.mock.inbound.pojo.PaymentRequestPojo;
import bifast.mock.persist.MockPacs002;
import bifast.mock.persist.MockPacs002Repository;
import bifast.mock.processor.UtilService;

@Component
public class BuildSttlProcessor implements Processor {

	@Autowired private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {
	
//		BusinessMessage in = exchange.getMessage().getBody(BusinessMessage.class);		
//		BusinessMessage aeResp = exchange.getMessage().getHeader("inb_aeresponse", BusinessMessage.class);
		BusinessMessage ctResp = exchange.getMessage().getHeader("inb_ctResponse", BusinessMessage.class);
		PaymentRequestPojo ibRequest = exchange.getMessage().getHeader("inb_request", PaymentRequestPojo.class);
		
		ctResp.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().setId("INDOIDJA");
		ctResp.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().setId("SIHBIDJ1");
		
//		String orgnlMsgId = exchange.getMessage().getHeader("sttl_orgnlmsgid", String.class);

		String cdtrAcct = ibRequest.getCreditorAccountNo();
		
		CashAccount38 cdtrAcctT = new CashAccount38();
		cdtrAcctT.setId(new AccountIdentification4Choice());
		cdtrAcctT.getId().setOthr(new GenericAccountIdentification1());
		cdtrAcctT.getId().getOthr().setId(cdtrAcct);

		cdtrAcctT.setTp(new CashAccountType2Choice());
		cdtrAcctT.getTp().setPrtry("CACC");
		
		ctResp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().setCdtrAcct(cdtrAcctT);

		String dbtrAcct = ibRequest.getDebtorAccountNo(); 
		
		ctResp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().setDbtrAcct(new CashAccount38());
		ctResp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getDbtrAcct().setId(new AccountIdentification4Choice());
		ctResp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getDbtrAcct().getId().setOthr(new GenericAccountIdentification1());
		ctResp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getDbtrAcct().getId().getOthr().setId(dbtrAcct);
		
		ctResp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getDbtrAcct().setTp(new CashAccountType2Choice());
		ctResp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getDbtrAcct().getTp().setPrtry(ibRequest.getDebtorAccountNo());
		
		String bizMsgId = utilService.genRfiBusMsgId("010", "02", "INDOIDJA");
		String msgId = utilService.genMessageId("010", "INDOIDJA");

		ctResp.getAppHdr().setBizMsgIdr(bizMsgId);
		ctResp.getAppHdr().setBizSvc("STTL");
		ctResp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).setTxSts("ACSC");

		ctResp.getDocument().getFiToFIPmtStsRpt().getGrpHdr().setMsgId(msgId);
		
		XMLGregorianCalendar orgnlDateTime = ctResp.getDocument().getFiToFIPmtStsRpt().getGrpHdr().getCreDtTm();
		ctResp.getDocument().getFiToFIPmtStsRpt().getOrgnlGrpInfAndSts().get(0).setOrgnlCreDtTm(orgnlDateTime);

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
		
		exchange.getMessage().setBody(ctResp);
		
	}

}
