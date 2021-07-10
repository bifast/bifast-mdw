package bifast.library.iso20022.service;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bifast.library.iso20022.prxy001.BIAddtlCstmrInf;
import bifast.library.iso20022.prxy001.BISupplementaryData1;
import bifast.library.iso20022.prxy001.BISupplementaryDataEnvelope1;
import bifast.library.iso20022.prxy001.CashAccount40;
import bifast.library.iso20022.prxy001.GroupHeader59;
import bifast.library.iso20022.prxy001.ProxyDefinition1;
import bifast.library.iso20022.prxy001.ProxyRegistration1;
import bifast.library.iso20022.prxy001.ProxyRegistrationAccount1;
import bifast.library.iso20022.prxy001.ProxyRegistrationType1Code;
import bifast.library.iso20022.prxy001.ProxyRegistrationV01;
import bifast.library.iso20022.prxy001.ScndIdDefinition1;

@Service
public class Proxy001MessageService {

	@Autowired
	private UtilService utilService;

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

		///
		ProxyRegistration1 pRegistration = new ProxyRegistration1();
		pRegistration.setRegnTp(ProxyRegistrationType1Code.NEWR);
		
		
		///
		ProxyDefinition1 pDefinition = new ProxyDefinition1();
		pDefinition.setTp(seed.getProxyTp());
		pDefinition.setVal(seed.getProxyVal());
		pRegistration.setPrxy(pDefinition);
		
		///
		ScndIdDefinition1 scndIdDefinition1 = new ScndIdDefinition1();
		scndIdDefinition1.setTp(seed.getScndIdTp());
		scndIdDefinition1.setVal(seed.getScndIdVal());
		
		///
		ProxyRegistrationAccount1 pRegistrationaccount = new ProxyRegistrationAccount1();
		pRegistrationaccount.setRegnId(seed.getRegnId());
		pRegistrationaccount.setDsplNm(seed.getDsplNm());
		pRegistrationaccount.setRegnSts(seed.getCstmrRsdntSts());
		pRegistrationaccount.setScndId(scndIdDefinition1);
		
		///
		CashAccount40 cashAccount40 = new CashAccount40();
		cashAccount40.setNm(seed.getAccName());
		pRegistrationaccount.setAcct(cashAccount40);
		
		///
		BIAddtlCstmrInf bIAddtlCstmrInf = new BIAddtlCstmrInf();
		bIAddtlCstmrInf.setId(seed.getCstmrId());
		bIAddtlCstmrInf.setTp(seed.getCstmrTp());
		bIAddtlCstmrInf.setTwnNm(seed.getCstmrTwnNm());
		
		///
		BISupplementaryDataEnvelope1 biSupplementaryDataEnvelope1 =  new BISupplementaryDataEnvelope1();
		biSupplementaryDataEnvelope1.setCstmr(bIAddtlCstmrInf);
		
		
		///
		BISupplementaryData1 biSupplementaryData1 = new BISupplementaryData1();
		biSupplementaryData1.setEnvlp(biSupplementaryDataEnvelope1);
		
		///
		pRegistration.setPrxy(pDefinition);
		pRegistration.setPrxyRegn(pRegistrationaccount);
		
		///
		proxy001.setRegn(pRegistration);
		proxy001.getSplmtryData().add(biSupplementaryData1);
		
		return proxy001;
	}

}
