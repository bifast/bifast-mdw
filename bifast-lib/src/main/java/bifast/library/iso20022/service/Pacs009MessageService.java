package bifast.library.iso20022.service;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Service;

import bifast.library.iso20022.pacs009.ActiveCurrencyAndAmount;
import bifast.library.iso20022.pacs009.BranchAndFinancialInstitutionIdentification6;
import bifast.library.iso20022.pacs009.CategoryPurpose1Choice;
import bifast.library.iso20022.pacs009.CreditTransferTransaction44;
import bifast.library.iso20022.pacs009.FinancialInstitutionCreditTransferV09;
import bifast.library.iso20022.pacs009.FinancialInstitutionIdentification18;
import bifast.library.iso20022.pacs009.GenericFinancialIdentification1;
import bifast.library.iso20022.pacs009.GroupHeader93;
import bifast.library.iso20022.pacs009.PaymentIdentification13;
import bifast.library.iso20022.pacs009.PaymentTypeInformation28;
import bifast.library.iso20022.pacs009.RemittanceInformation2;
import bifast.library.iso20022.pacs009.SettlementInstruction7;
import bifast.library.iso20022.pacs009.SettlementMethod1Code;

@Service
public class Pacs009MessageService {
	
	public FinancialInstitutionCreditTransferV09 creditTransferRequest (Pacs009Seed seed) 
			throws DatatypeConfigurationException {
		
		FinancialInstitutionCreditTransferV09 pacs009 = new FinancialInstitutionCreditTransferV09();

		// GrpHdr
		GroupHeader93 grpHdr = new GroupHeader93();
		grpHdr.setMsgId(seed.getMsgId());

		GregorianCalendar gcal = new GregorianCalendar();
		XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		grpHdr.setCreDtTm(xcal);

		grpHdr.setNbOfTxs("1");

		SettlementInstruction7 sttlmInf =new SettlementInstruction7();
		sttlmInf.setSttlmMtd(SettlementMethod1Code.CLRG);
		grpHdr.setSttlmInf(sttlmInf);
		
		pacs009.setGrpHdr(grpHdr);


		// CdtTrfTxInf / PmtId
		PaymentIdentification13 pmtId = new PaymentIdentification13();
		pmtId.setEndToEndId(seed.getBizMsgId());
		pmtId.setTxId(seed.getMsgId());

		CreditTransferTransaction44 cdtTrfTx = new CreditTransferTransaction44();
		cdtTrfTx.setPmtId(pmtId);

		// CdtTrfTxInf / PmtTpInf
		CategoryPurpose1Choice ctgyPurp = new CategoryPurpose1Choice();
		ctgyPurp.setPrtry("01999");
		PaymentTypeInformation28 pmtTpInf = new PaymentTypeInformation28();
		pmtTpInf.setCtgyPurp(ctgyPurp);
		cdtTrfTx.setPmtTpInf(pmtTpInf);

		// CdtTrfTxInf / IntrBkSttlmAmt
		ActiveCurrencyAndAmount currencyAmount = new ActiveCurrencyAndAmount();
		currencyAmount.setValue(seed.getAmount());
		currencyAmount.setCcy("IDR");
		
		cdtTrfTx.setIntrBkSttlmAmt(currencyAmount);

		// CdtTrfTxInf / Dbtr     (bukan DbtrAgt)
		GenericFinancialIdentification1 orign = new GenericFinancialIdentification1();
		orign.setId(seed.getDebtorBank());
		FinancialInstitutionIdentification18 othr = new FinancialInstitutionIdentification18();
		othr.setOthr(orign);
		BranchAndFinancialInstitutionIdentification6 finInstnId = new BranchAndFinancialInstitutionIdentification6();
		finInstnId.setFinInstnId(othr);

		cdtTrfTx.setDbtr(finInstnId);	
		
		// CdtTrfTxInf / CdtrAgt
		GenericFinancialIdentification1 recpt = new GenericFinancialIdentification1();
		recpt.setId(seed.getCreditorBank());
		FinancialInstitutionIdentification18 othrRecpt = new FinancialInstitutionIdentification18();
		othrRecpt.setOthr(recpt);
		BranchAndFinancialInstitutionIdentification6 finInstnIdRecpt = new BranchAndFinancialInstitutionIdentification6();
		finInstnIdRecpt.setFinInstnId(othrRecpt);

		cdtTrfTx.setCdtr(finInstnIdRecpt);
		
		// CdtTrfTxInf / RmtInf
		RemittanceInformation2 rmtInf = new RemittanceInformation2();
		rmtInf.getUstrd().add(seed.getPaymentInfo());
		cdtTrfTx.setRmtInf(rmtInf);
		
		// CdtTrfTxInf
		FinancialInstitutionCreditTransferV09 cdtTrfTxInf = new FinancialInstitutionCreditTransferV09();
		cdtTrfTxInf.getCdtTrfTxInf().add(cdtTrfTx);

		pacs009.getCdtTrfTxInf().add(cdtTrfTx);
		
		return pacs009;
		
	}
	
}
