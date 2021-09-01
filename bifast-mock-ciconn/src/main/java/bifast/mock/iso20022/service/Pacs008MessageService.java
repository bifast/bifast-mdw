package bifast.mock.iso20022.service;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bifast.mock.iso20022.pacs008.AccountIdentification4Choice;
import bifast.mock.iso20022.pacs008.ActiveCurrencyAndAmount;
import bifast.mock.iso20022.pacs008.BranchAndFinancialInstitutionIdentification6;
import bifast.mock.iso20022.pacs008.CashAccount38;
import bifast.mock.iso20022.pacs008.CashAccountType2Choice;
import bifast.mock.iso20022.pacs008.CategoryPurpose1Choice;
import bifast.mock.iso20022.pacs008.ChargeBearerType1Code;
import bifast.mock.iso20022.pacs008.CreditTransferTransaction39;
import bifast.mock.iso20022.pacs008.FIToFICustomerCreditTransferV08;
import bifast.mock.iso20022.pacs008.FinancialInstitutionIdentification18;
import bifast.mock.iso20022.pacs008.GenericAccountIdentification1;
import bifast.mock.iso20022.pacs008.GenericFinancialIdentification1;
import bifast.mock.iso20022.pacs008.GenericOrganisationIdentification1;
import bifast.mock.iso20022.pacs008.GenericPersonIdentification1;
import bifast.mock.iso20022.pacs008.GroupHeader93;
import bifast.mock.iso20022.pacs008.LocalInstrument2Choice;
import bifast.mock.iso20022.pacs008.OrganisationIdentification29;
import bifast.mock.iso20022.pacs008.Party38Choice;
import bifast.mock.iso20022.pacs008.PartyIdentification135;
import bifast.mock.iso20022.pacs008.PaymentIdentification7;
import bifast.mock.iso20022.pacs008.PaymentTypeInformation28;
import bifast.mock.iso20022.pacs008.PersonIdentification13;
import bifast.mock.iso20022.pacs008.ProxyAccountIdentification1;
import bifast.mock.iso20022.pacs008.ProxyAccountType1Choice;
import bifast.mock.iso20022.pacs008.RemittanceInformation16;
import bifast.mock.iso20022.pacs008.SettlementInstruction7;
import bifast.mock.iso20022.pacs008.SettlementMethod1Code;

@Service
public class Pacs008MessageService {

	@Autowired
	private UtilService utilService;

	public FIToFICustomerCreditTransferV08 accountEnquiryRequest (Pacs008Seed seed) 
			throws DatatypeConfigurationException {
		
		FIToFICustomerCreditTransferV08 pacs008 = new FIToFICustomerCreditTransferV08();
		String msgId = utilService.genMessageId(seed.getTrnType());
		
		// GrpHdr
		GroupHeader93 grpHdr = new GroupHeader93();
		grpHdr.setMsgId(msgId);
		
		GregorianCalendar gcal = new GregorianCalendar();
		XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		grpHdr.setCreDtTm(xcal);

		grpHdr.setNbOfTxs("1");

		SettlementInstruction7 sttlmInf =new SettlementInstruction7();
		sttlmInf.setSttlmMtd(SettlementMethod1Code.CLRG);
		grpHdr.setSttlmInf(sttlmInf);
		
		pacs008.setGrpHdr(grpHdr);

		// list of CdtTrfTxInf
		CreditTransferTransaction39 cdtTrfTxInf = new CreditTransferTransaction39();
		
		// CdtTrfTxInf / PmtId
		
		PaymentIdentification7 PmtId = new PaymentIdentification7();
		PmtId.setEndToEndId(seed.getBizMsgId());
		PmtId.setTxId(grpHdr.getMsgId());
		
		cdtTrfTxInf.setPmtId(PmtId);
	
		// CdtTrfTxInf / IntrBkSttlmAmt

		ActiveCurrencyAndAmount ccyAmount = new ActiveCurrencyAndAmount();
		ccyAmount.setValue(seed.getAmount());
		ccyAmount.setCcy("IDR");
		cdtTrfTxInf.setIntrBkSttlmAmt(ccyAmount);
		
		// CdtTrfTxInf / ChrgBr
		cdtTrfTxInf.setChrgBr(ChargeBearerType1Code.DEBT);
		
		// CdtTrfTxInf / Dbtr
		PartyIdentification135 dbtr = new PartyIdentification135();
		cdtTrfTxInf.setDbtr(dbtr);
		
		// CdtTrfTxInf / DbtrAgt
		
		GenericFinancialIdentification1 dbtrInstnIdOthId  = new GenericFinancialIdentification1();
		dbtrInstnIdOthId.setId(seed.getOrignBank());
		FinancialInstitutionIdentification18 dbtrInstnId = new FinancialInstitutionIdentification18();
		dbtrInstnId.setOthr(dbtrInstnIdOthId);
		BranchAndFinancialInstitutionIdentification6 dbtrAgt = new BranchAndFinancialInstitutionIdentification6();
		dbtrAgt.setFinInstnId(dbtrInstnId);
		
		cdtTrfTxInf.setDbtrAgt(dbtrAgt);

		// CdtTrfTxInf / CdtrAgt
		GenericFinancialIdentification1 cdtrInstnIdOthId  = new GenericFinancialIdentification1();
		cdtrInstnIdOthId.setId(seed.getRecptBank());
		FinancialInstitutionIdentification18 cdtrInstnId = new FinancialInstitutionIdentification18();
		cdtrInstnId.setOthr(cdtrInstnIdOthId);
		BranchAndFinancialInstitutionIdentification6 cdtrAgt = new BranchAndFinancialInstitutionIdentification6();
		cdtrAgt.setFinInstnId(cdtrInstnId);

		cdtTrfTxInf.setCdtrAgt(cdtrAgt);
		
		// CdtTrfTxInf / Cdtr 
		PartyIdentification135 cdtr = new PartyIdentification135();
		cdtTrfTxInf.setCdtr(cdtr);
		
		// CdtTrfTxInf / CdtrAcct
		CashAccount38 cdtrAcct = new CashAccount38();

		GenericAccountIdentification1 cdtrAcctIdOthr = new GenericAccountIdentification1();
		cdtrAcctIdOthr.setId(seed.getCrdtAccountNo());
		AccountIdentification4Choice cdtrAcctId = new AccountIdentification4Choice();
		cdtrAcctId.setOthr(cdtrAcctIdOthr);
		cdtrAcct.setId(cdtrAcctId);

		cdtTrfTxInf.setCdtrAcct(cdtrAcct);
		
		// CdtTrfTxInf / SplmtryData
		
		pacs008.getCdtTrfTxInf().add(cdtTrfTxInf);
		
		return pacs008;
	}

	public FIToFICustomerCreditTransferV08 creditTransferRequest (Pacs008Seed seed) 
			throws DatatypeConfigurationException {
		
		FIToFICustomerCreditTransferV08 pacs008 = new FIToFICustomerCreditTransferV08();
		String msgId = utilService.genMessageId(seed.getTrnType());

		// GrpHdr
		GroupHeader93 grpHdr = new GroupHeader93();
		grpHdr.setMsgId(msgId);

		GregorianCalendar gcal = new GregorianCalendar();
		XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		grpHdr.setCreDtTm(xcal);

		grpHdr.setNbOfTxs("1");

		SettlementInstruction7 sttlmInf =new SettlementInstruction7();
		sttlmInf.setSttlmMtd(SettlementMethod1Code.CLRG);
		grpHdr.setSttlmInf(sttlmInf);
		
		pacs008.setGrpHdr(grpHdr);

		// list of CdtTrfTxInf
		CreditTransferTransaction39 cdtTrfTxInf = new CreditTransferTransaction39();
		
		// CdtTrfTxInf / PmtId
		
		PaymentIdentification7 PmtId = new PaymentIdentification7();
		PmtId.setEndToEndId(seed.getBizMsgId());
		PmtId.setTxId(msgId);
		
		cdtTrfTxInf.setPmtId(PmtId);
		
		// CdtTrfTxInf / PmtTpInf

		LocalInstrument2Choice lclInstrm = new LocalInstrument2Choice();
		lclInstrm.setPrtry(seed.getChannel());
		PaymentTypeInformation28 pmtTpInf = new PaymentTypeInformation28();
		pmtTpInf.setLclInstrm(lclInstrm);

		CategoryPurpose1Choice ctgyPurp = new CategoryPurpose1Choice();
		if (null==seed.getCategoryPurpose()) 
			ctgyPurp.setPrtry("99");
		else
			ctgyPurp.setPrtry(seed.getCategoryPurpose());
		pmtTpInf.setCtgyPurp(ctgyPurp);
		
		cdtTrfTxInf.setPmtTpInf(pmtTpInf);
		
		// CdtTrfTxInf / IntrBkSttlmAmt

		ActiveCurrencyAndAmount ccyAmount = new ActiveCurrencyAndAmount();
		ccyAmount.setValue(seed.getAmount());
		ccyAmount.setCcy("IDR");
		cdtTrfTxInf.setIntrBkSttlmAmt(ccyAmount);
		
		// CdtTrfTxInf / ChrgBr
		cdtTrfTxInf.setChrgBr(ChargeBearerType1Code.DEBT);
		
		// CdtTrfTxInf / Dbtr
		PartyIdentification135 dbtr = new PartyIdentification135();
		
		if (!(null==seed.getDbtrName()))
			dbtr.setNm(seed.getDbtrName());

		Party38Choice dbtrId = new Party38Choice();
		if (seed.getDbtrIdType().equals("01")) {
			GenericPersonIdentification1 debtId = new GenericPersonIdentification1();
			debtId.setId(seed.getDbtrId());
			PersonIdentification13 personIdentification13 = new PersonIdentification13();
			personIdentification13.getOthr().add(debtId);
			dbtrId.setPrvtId(personIdentification13);
		} else {
			GenericOrganisationIdentification1 orgOthrId = new GenericOrganisationIdentification1();
			orgOthrId.setId(seed.getDbtrId());
			OrganisationIdentification29 orgId = new OrganisationIdentification29();
			orgId.getOthr().add(orgOthrId);
			dbtrId.setOrgId(orgId);
		}			
		dbtr.setId(dbtrId);
		
		cdtTrfTxInf.setDbtr(dbtr);
		
		// CdtTrfTxInf / DbtrAcct
		
		GenericAccountIdentification1 dbtrAcctIdOthId = new GenericAccountIdentification1();
		dbtrAcctIdOthId.setId(seed.getDbtrAccountNo());
		AccountIdentification4Choice dbtrAcctId = new AccountIdentification4Choice();
		dbtrAcctId.setOthr(dbtrAcctIdOthId);
		
		CashAccountType2Choice dbtrAcctTp = new CashAccountType2Choice();
		dbtrAcctTp.setPrtry(seed.getDbtrAccountType());
		
		CashAccount38 dbtrAcct = new CashAccount38();
		dbtrAcct.setId(dbtrAcctId);
		dbtrAcct.setTp(dbtrAcctTp);
		
		cdtTrfTxInf.setDbtrAcct(dbtrAcct);
	
		// CdtTrfTxInf / DbtrAgt
		
		GenericFinancialIdentification1 dbtrInstnIdOthId  = new GenericFinancialIdentification1();
		dbtrInstnIdOthId.setId(seed.getOrignBank());
		FinancialInstitutionIdentification18 dbtrInstnId = new FinancialInstitutionIdentification18();
		dbtrInstnId.setOthr(dbtrInstnIdOthId);
		BranchAndFinancialInstitutionIdentification6 dbtrAgt = new BranchAndFinancialInstitutionIdentification6();
		dbtrAgt.setFinInstnId(dbtrInstnId);
		
		cdtTrfTxInf.setDbtrAgt(dbtrAgt);

		// CdtTrfTxInf / CdtrAgt
		GenericFinancialIdentification1 cdtrInstnIdOthId  = new GenericFinancialIdentification1();
		cdtrInstnIdOthId.setId(seed.getRecptBank());
		FinancialInstitutionIdentification18 cdtrInstnId = new FinancialInstitutionIdentification18();
		cdtrInstnId.setOthr(cdtrInstnIdOthId);
		BranchAndFinancialInstitutionIdentification6 cdtrAgt = new BranchAndFinancialInstitutionIdentification6();
		cdtrAgt.setFinInstnId(cdtrInstnId);

		cdtTrfTxInf.setCdtrAgt(cdtrAgt);
		
		// CdtTrfTxInf / Cdtr 
		PartyIdentification135 cdtr = new PartyIdentification135();
		
		if (!(null==seed.getCrdtName())); 
			cdtr.setNm(seed.getCrdtName());
			
		Party38Choice cdtrId = new Party38Choice();
		if (seed.getCrdtIdType().equals("01")) {
			GenericPersonIdentification1 cdtrPrvOthrId = new GenericPersonIdentification1();
			cdtrPrvOthrId.setId(seed.getCrdtId());
			PersonIdentification13 personId = new PersonIdentification13();
			personId.getOthr().add(cdtrPrvOthrId);
			cdtrId.setPrvtId(personId);
		} else {
			GenericOrganisationIdentification1 cdtrOrgOthrId = new GenericOrganisationIdentification1();
			cdtrOrgOthrId.setId(seed.getCrdtId());
			OrganisationIdentification29 orgId = new OrganisationIdentification29();
			orgId.getOthr().add(cdtrOrgOthrId);
			cdtrId.setOrgId(orgId);
		}
		cdtr.setId(cdtrId);

		cdtTrfTxInf.setCdtr(cdtr);
		
		// CdtTrfTxInf / CdtrAcct
		CashAccount38 cdtrAcct = new CashAccount38();

		GenericAccountIdentification1 cdtrAcctIdOthr = new GenericAccountIdentification1();
		cdtrAcctIdOthr.setId(seed.getCrdtAccountNo());
		AccountIdentification4Choice cdtrAcctId = new AccountIdentification4Choice();
		cdtrAcctId.setOthr(cdtrAcctIdOthr);
		cdtrAcct.setId(cdtrAcctId);

		CashAccountType2Choice cdtrAcctTp = new CashAccountType2Choice();
		cdtrAcctTp.setPrtry(seed.getCrdtAccountType());
		cdtrAcct.setTp(cdtrAcctTp);
		
		if (!(null == seed.getCrdtProxyIdType())) {
			ProxyAccountType1Choice prxyTp = new ProxyAccountType1Choice();
			
			prxyTp.setPrtry(seed.getCrdtProxyIdType());
			ProxyAccountIdentification1 prxyAcct = new ProxyAccountIdentification1();
			
			prxyAcct.setTp(prxyTp);
			prxyAcct.setId(seed.getCrdtProxyIdValue());

			cdtrAcct.setPrxy(prxyAcct);
		}
		
		cdtTrfTxInf.setCdtrAcct(cdtrAcct);
		
		// CdtTrfTxInf / RmtInf
		if (!(null==seed.getPaymentInfo())) {
			RemittanceInformation16 rmtInf = new RemittanceInformation16();
			rmtInf.getUstrd().add(seed.getPaymentInfo());
			cdtTrfTxInf.setRmtInf(rmtInf);
		}
		
		// CdtTrfTxInf / SplmtryData
		// unt credit transfer tidak digunakan
		
		pacs008.getCdtTrfTxInf().add(cdtTrfTxInf);
		
		return pacs008;

	}
}
