package bifast.mock.iso20022.service;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bifast.mock.iso20022.prxy003.BranchAndFinancialInstitutionIdentification5;
import bifast.mock.iso20022.prxy003.FinancialInstitutionIdentification8;
import bifast.mock.iso20022.prxy003.GenericFinancialIdentification1;
import bifast.mock.iso20022.prxy003.GroupHeader69;
import bifast.mock.iso20022.prxy003.Party12Choice;
import bifast.mock.iso20022.prxy003.ProxyDefinition1;
import bifast.mock.iso20022.prxy003.ProxyLookUp11;
import bifast.mock.iso20022.prxy003.ProxyLookUpChoice1;
import bifast.mock.iso20022.prxy003.ProxyLookUpV01;

@Service
public class Proxy003MessageService {

	@Autowired
	private UtilService utilService;
	
	@Autowired
	private LibConfig config;

	public ProxyLookUpV01 proxyResolutionRequest (Proxy003Seed seed) 
			throws DatatypeConfigurationException {
			
		ProxyLookUpV01 proxy003 = new ProxyLookUpV01();
		String msgId = utilService.genMessageId(seed.getTrnType());
		
		// GrpHdr
		GroupHeader69 grpHdr = new GroupHeader69();
		grpHdr.setMsgId(msgId);
		
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
		
		proxy003.setGrpHdr(grpHdr);	
		
		// Lookup / PrxyOnly / PrxyRtrvl

		ProxyLookUp11 prxyOnly = new ProxyLookUp11();
		prxyOnly.setLkUpTp(seed.getLookupType());
		prxyOnly.setId(msgId);
		
		ProxyDefinition1 prxyRtrvl = new ProxyDefinition1();
		prxyRtrvl.setTp(seed.getProxyType());
		prxyRtrvl.setVal(seed.getProxyValue());
		
		prxyOnly.setPrxyRtrvl(prxyRtrvl);

		// Lookup / PrxyOnly
		ProxyLookUpChoice1 lookup = new ProxyLookUpChoice1();
		lookup.setPrxyOnly(prxyOnly);
		
		return proxy003;
	}

}
