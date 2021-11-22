package bifast.library.iso20022.service;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bifast.library.config.LibConfig;
import bifast.library.iso20022.prxy005.BranchAndFinancialInstitutionIdentification5;
import bifast.library.iso20022.prxy005.FinancialInstitutionIdentification8;
import bifast.library.iso20022.prxy005.GenericFinancialIdentification1;
import bifast.library.iso20022.prxy005.GroupHeader59;
import bifast.library.iso20022.prxy005.Party12Choice;
import bifast.library.iso20022.prxy005.ProxyEnquiryChoice1;
import bifast.library.iso20022.prxy005.ProxyEnquiryV01;
import bifast.library.iso20022.prxy005.ScndIdDefinition1;

@Service
public class Proxy005MessageService {

	@Autowired
	private LibConfig config;

	public ProxyEnquiryV01 proxyRegistrationInquiryRequest (Proxy005Seed seed) 
			throws DatatypeConfigurationException {
			
		ProxyEnquiryV01 proxy005 = new ProxyEnquiryV01();

		// GrpHdr
		GroupHeader59 grpHdr = new GroupHeader59();
		grpHdr.setMsgId(seed.getMsgId());
		
		GregorianCalendar gcal = new GregorianCalendar();
		XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		grpHdr.setCreDtTm(xcal);
		
		// GrpHdr / MsgSndr / Agt/ FinInstnId/ Othr /Id
		GenericFinancialIdentification1 sndrId = new GenericFinancialIdentification1();
		sndrId.setId(config.getBankcode());
		FinancialInstitutionIdentification8 finInstnId = new FinancialInstitutionIdentification8();
		finInstnId.setOthr(sndrId);
		BranchAndFinancialInstitutionIdentification5 sndrAgt = new BranchAndFinancialInstitutionIdentification5();
		sndrAgt.setFinInstnId(finInstnId);
		Party12Choice msgSndr = new Party12Choice();
		msgSndr.setAgt(sndrAgt);
		grpHdr.setMsgSndr(msgSndr);
		
		proxy005.setGrpHdr(grpHdr);	
		
		ProxyEnquiryChoice1 Nqry = new ProxyEnquiryChoice1();
		
		ScndIdDefinition1 ScndId = new ScndIdDefinition1();
		ScndId.setTp(seed.getScndIdType());
		ScndId.setVal(seed.getScndIdValue());
		
		Nqry.setScndId(ScndId);
		proxy005.setNqry(Nqry);
		
		return proxy005;
	}

}
