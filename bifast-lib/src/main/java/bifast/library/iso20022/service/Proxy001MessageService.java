package bifast.library.iso20022.service;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bifast.library.config.LibConfig;
import bifast.library.iso20022.prxy001.AccountIdentification4Choice;
import bifast.library.iso20022.prxy001.BIAddtlCstmrInf;
import bifast.library.iso20022.prxy001.BISupplementaryData1;
import bifast.library.iso20022.prxy001.BISupplementaryDataEnvelope1;
import bifast.library.iso20022.prxy001.BranchAndFinancialInstitutionIdentification5;
import bifast.library.iso20022.prxy001.CashAccount40;
import bifast.library.iso20022.prxy001.CashAccountType2ChoiceProxy;
import bifast.library.iso20022.prxy001.FinancialInstitutionIdentification8;
import bifast.library.iso20022.prxy001.GenericAccountIdentification1;
import bifast.library.iso20022.prxy001.GenericFinancialIdentification1;
import bifast.library.iso20022.prxy001.GroupHeader59;
import bifast.library.iso20022.prxy001.Party12Choice;
import bifast.library.iso20022.prxy001.ProxyDefinition1;
import bifast.library.iso20022.prxy001.ProxyRegistration1;
import bifast.library.iso20022.prxy001.ProxyRegistrationAccount1;
import bifast.library.iso20022.prxy001.ProxyRegistrationType1Code;
import bifast.library.iso20022.prxy001.ProxyRegistrationV01;
import bifast.library.iso20022.prxy001.ScndIdDefinition1;

@Service
public class Proxy001MessageService {


	@Autowired
	private LibConfig config;

	public ProxyRegistrationV01 proxyRegistrationRequest (Proxy001Seed seed) 
			throws DatatypeConfigurationException {
		
		ProxyRegistrationV01 proxy001 = new ProxyRegistrationV01();
//		String msgId = utilService.genMessageId(seed.getTrnType());
		
		// GrpHdr
		GroupHeader59 grpHdr = new GroupHeader59();
		grpHdr.setMsgId(seed.getMsgId());
		
		GregorianCalendar gcal = new GregorianCalendar();
		XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		grpHdr.setCreDtTm(xcal);
		
		Party12Choice msgSndr = new Party12Choice();
		msgSndr.setAgt(new BranchAndFinancialInstitutionIdentification5());
		msgSndr.getAgt().setFinInstnId(new FinancialInstitutionIdentification8());
		msgSndr.getAgt().getFinInstnId().setOthr(new GenericFinancialIdentification1());
		msgSndr.getAgt().getFinInstnId().getOthr().setId(config.getBankcode());
		
		grpHdr.setMsgSndr(msgSndr);
		
		proxy001.setGrpHdr(grpHdr);	
		
		// PrxyRegn/ Regn
		ProxyRegistration1 pRegistration = new ProxyRegistration1();
		proxy001.setRegn(pRegistration);
		
		if (seed.getRegistrationType().equals("NEWR"))
			proxy001.getRegn().setRegnTp(ProxyRegistrationType1Code.NEWR);
		else if (seed.getRegistrationType().equals("AMND"))
			proxy001.getRegn().setRegnTp(ProxyRegistrationType1Code.AMND);
		else if (seed.getRegistrationType().equals("DEAC"))
			proxy001.getRegn().setRegnTp(ProxyRegistrationType1Code.DEAC);
		else if (seed.getRegistrationType().equals("SUSP"))
			proxy001.getRegn().setRegnTp(ProxyRegistrationType1Code.SUSP);
		else if (seed.getRegistrationType().equals("ACTV"))
			proxy001.getRegn().setRegnTp(ProxyRegistrationType1Code.ACTV);
		else if (seed.getRegistrationType().equals("PORT"))
			proxy001.getRegn().setRegnTp(ProxyRegistrationType1Code.PORT);

		ProxyDefinition1 prxy = new ProxyDefinition1();
		prxy.setTp(seed.getProxyType());
		prxy.setVal(seed.getProxyValue());

		proxy001.getRegn().setPrxy(prxy);

		// PrxyRegn / +Regn / ++PrxyRegn
		proxy001.getRegn().setPrxyRegn(new ProxyRegistrationAccount1());

		if (!(seed.getRegistrationType().equals("NEWR"))) {
			proxy001.getRegn().getPrxyRegn().setRegnId(seed.getRegistrationId());
		}
		
		if (!(null == seed.getRegisterDisplayName()))
			proxy001.getRegn().getPrxyRegn().setDsplNm(seed.getRegisterDisplayName());
		
		// PrxyRegn / +Regn / ++PrxyRegn / +++Agt

		proxy001.getRegn().getPrxyRegn().setAgt(new BranchAndFinancialInstitutionIdentification5());
		proxy001.getRegn().getPrxyRegn().getAgt().setFinInstnId(new FinancialInstitutionIdentification8() );
		proxy001.getRegn().getPrxyRegn().getAgt().getFinInstnId().setOthr(new GenericFinancialIdentification1());;
		proxy001.getRegn().getPrxyRegn().getAgt().getFinInstnId().getOthr().setId(seed.getRegisterAgentId());

		// PrxyRegn / +Regn / ++PrxyRegn / +++Acct / ++++Id
		proxy001.getRegn().getPrxyRegn().setAcct(new CashAccount40());
		proxy001.getRegn().getPrxyRegn().getAcct().setId(new AccountIdentification4Choice ());;
		proxy001.getRegn().getPrxyRegn().getAcct().getId().setOthr(new GenericAccountIdentification1());
		proxy001.getRegn().getPrxyRegn().getAcct().getId().getOthr().setId(seed.getRegisterAccountNumber());

		// PrxyRegn / +Regn / ++PrxyRegn / +++Acct / ++++Tp
		if (!(null == seed.getRegisterAccountType())) {
			
			proxy001.getRegn().getPrxyRegn().getAcct().setTp(new CashAccountType2ChoiceProxy());
			proxy001.getRegn().getPrxyRegn().getAcct().getTp().setPrtry(seed.getRegisterAccountType());
		}
		
		// PrxyRegn / +Regn / ++PrxyRegn / +++Acct / ++++Nm
		if (!(null == seed.getRegisterAccountName()))
			proxy001.getRegn().getPrxyRegn().getAcct().setNm(seed.getRegisterAccountName());

		// PrxyRegn / +Regn / ++PrxyRegn / +++ScndId
		proxy001.getRegn().getPrxyRegn().setScndId(new ScndIdDefinition1());
		
		proxy001.getRegn().getPrxyRegn().getScndId().setTp(seed.getRegisterSecondIdType());
		proxy001.getRegn().getPrxyRegn().getScndId().setVal(seed.getRegisterSecondIdValue());
		
		// PrxyRegn / +Regn / ++PrxyRegn / +++RegnSts
//		if (!(null == seed.setstgetRegnStatus()))
//			proxy001.getRegn().getPrxyRegn().setRegnSts(seed.getRegnStatus());
		
		// PrxyRegn / +SplmtryData

		Boolean addtInfoExists = false;
		BIAddtlCstmrInf addtCstmrInf = new BIAddtlCstmrInf();
	
		if (!(null == seed.getCustomerType())) {
			addtCstmrInf.setTp(seed.getCustomerType());
			addtInfoExists = true;
		}

		if (!(null == seed.getCustomerId())) {
			addtCstmrInf.setId(seed.getCustomerId());
			addtInfoExists = true;
		}

		if (!(null == seed.getResidentialStatus())) {
			addtCstmrInf.setRsdntSts(seed.getResidentialStatus());
			addtInfoExists = true;
		}

		if (!(null == seed.getTownName())) {
			addtCstmrInf.setTwnNm(seed.getTownName());
			addtInfoExists = true;
		}

		if (addtInfoExists) {
			proxy001.getSplmtryData().add(new BISupplementaryData1());
			proxy001.getSplmtryData().get(0).setEnvlp(new BISupplementaryDataEnvelope1());
			proxy001.getSplmtryData().get(0).getEnvlp().setCstmr(addtCstmrInf);
		}
		
		return proxy001;
	}

}
