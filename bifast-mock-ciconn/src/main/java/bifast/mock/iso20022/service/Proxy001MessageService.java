package bifast.mock.iso20022.service;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bifast.mock.iso20022.prxy001.AccountIdentification4Choice;
import bifast.mock.iso20022.prxy001.BIAddtlCstmrInf;
import bifast.mock.iso20022.prxy001.BISupplementaryData1;
import bifast.mock.iso20022.prxy001.BISupplementaryDataEnvelope1;
import bifast.mock.iso20022.prxy001.BranchAndFinancialInstitutionIdentification5;
import bifast.mock.iso20022.prxy001.CashAccount40;
import bifast.mock.iso20022.prxy001.CashAccountType2ChoiceProxy;
import bifast.mock.iso20022.prxy001.FinancialInstitutionIdentification8;
import bifast.mock.iso20022.prxy001.GenericAccountIdentification1;
import bifast.mock.iso20022.prxy001.GenericFinancialIdentification1;
import bifast.mock.iso20022.prxy001.GroupHeader59;
import bifast.mock.iso20022.prxy001.Party12Choice;
import bifast.mock.iso20022.prxy001.ProxyDefinition1;
import bifast.mock.iso20022.prxy001.ProxyRegistration1;
import bifast.mock.iso20022.prxy001.ProxyRegistrationAccount1;
import bifast.mock.iso20022.prxy001.ProxyRegistrationType1Code;
import bifast.mock.iso20022.prxy001.ProxyRegistrationV01;
import bifast.mock.iso20022.prxy001.ScndIdDefinition1;


@Service
public class Proxy001MessageService {

	@Autowired
	private UtilService utilService;
	
	@Autowired
	private LibConfig config;

	public ProxyRegistrationV01 proxyRegistrationRequest (Proxy001Seed seed) 
			throws DatatypeConfigurationException {
		
		ProxyRegistrationV01 proxy001 = new ProxyRegistrationV01();
		String msgId = utilService.genMessageId(seed.getTrnType());
		
		// GrpHdr
		GroupHeader59 grpHdr = new GroupHeader59();
		grpHdr.setMsgId(msgId);
		
		GregorianCalendar gcal = new GregorianCalendar();
		XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		grpHdr.setCreDtTm(xcal);
		
		proxy001.setGrpHdr(grpHdr);	
		
		Party12Choice msgSndr = new Party12Choice();
		msgSndr.setAgt(new BranchAndFinancialInstitutionIdentification5());
		msgSndr.getAgt().setFinInstnId(new FinancialInstitutionIdentification8());
		msgSndr.getAgt().getFinInstnId().setOthr(new GenericFinancialIdentification1());
		msgSndr.getAgt().getFinInstnId().getOthr().setId(config.getBicode());
		grpHdr.setMsgSndr(msgSndr);
		///
		ProxyRegistration1 pRegistration = new ProxyRegistration1();
		pRegistration.setRegnTp(ProxyRegistrationType1Code.NEWR);		
		
		///
		ProxyDefinition1 prxy = new ProxyDefinition1();
		prxy.setTp(seed.getProxyTp());
		prxy.setVal(seed.getProxyVal());
		pRegistration.setPrxy(prxy);
		
		///
		ScndIdDefinition1 scndIdDefinition1 = new ScndIdDefinition1();
		scndIdDefinition1.setTp(seed.getScndIdTp());
		scndIdDefinition1.setVal(seed.getScndIdVal());
		
		///
		ProxyRegistrationAccount1 pRegistrationaccount = new ProxyRegistrationAccount1();
		pRegistrationaccount.setRegnId(seed.getRegnId());
		pRegistrationaccount.setDsplNm(seed.getDsplNm());
		pRegistrationaccount.setRegnSts(ProxyRegistrationType1Code.ACTV.toString());
		pRegistrationaccount.setScndId(scndIdDefinition1);
		
		pRegistrationaccount.setAgt(new BranchAndFinancialInstitutionIdentification5());
		pRegistrationaccount.getAgt().setFinInstnId(new FinancialInstitutionIdentification8());
		pRegistrationaccount.getAgt().getFinInstnId().setOthr(new GenericFinancialIdentification1());
		pRegistrationaccount.getAgt().getFinInstnId().getOthr().setId(config.getBicode());
		
		///
		CashAccount40 cashAccount40 = new CashAccount40();
		cashAccount40.setNm(seed.getAccName());
		cashAccount40.setId(new AccountIdentification4Choice());
		cashAccount40.getId().setOthr(new GenericAccountIdentification1());
		cashAccount40.getId().getOthr().setId(seed.getAccNumber());
		
		cashAccount40.setTp(new CashAccountType2ChoiceProxy());
		cashAccount40.getTp().setPrtry(seed.getAccTpPrtry());
		
		pRegistrationaccount.setAcct(cashAccount40);
		
		///
		BIAddtlCstmrInf bIAddtlCstmrInf = new BIAddtlCstmrInf();
		bIAddtlCstmrInf.setId(seed.getCstmrId());
		bIAddtlCstmrInf.setTp(seed.getCstmrTp());
		bIAddtlCstmrInf.setTwnNm(seed.getCstmrTwnNm());
		bIAddtlCstmrInf.setRsdntSts(seed.getCstmrRsdntSts());
		///
		BISupplementaryDataEnvelope1 biSupplementaryDataEnvelope1 =  new BISupplementaryDataEnvelope1();
		biSupplementaryDataEnvelope1.setCstmr(bIAddtlCstmrInf);
		
		
		///
		BISupplementaryData1 biSupplementaryData1 = new BISupplementaryData1();
		biSupplementaryData1.setEnvlp(biSupplementaryDataEnvelope1);
		
		///
		pRegistration.setPrxy(prxy);
		pRegistration.setPrxyRegn(pRegistrationaccount);
		
		///
		proxy001.setRegn(pRegistration);
		proxy001.getSplmtryData().add(biSupplementaryData1);
		
		return proxy001;
	}

}
