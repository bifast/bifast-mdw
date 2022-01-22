package bifast.library.iso20022.service;

import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bifast.library.config.LibConfig;
import bifast.library.iso20022.prxy003.AccountIdentification4Choice;
import bifast.library.iso20022.prxy003.BranchAndFinancialInstitutionIdentification5;
import bifast.library.iso20022.prxy003.CashAccount40;
import bifast.library.iso20022.prxy003.FinancialInstitutionIdentification8;
import bifast.library.iso20022.prxy003.GenericAccountIdentification1;
import bifast.library.iso20022.prxy003.GenericFinancialIdentification1;
import bifast.library.iso20022.prxy003.GroupHeader69;
import bifast.library.iso20022.prxy003.Party12Choice;
import bifast.library.iso20022.prxy003.ProxyDefinition1;
import bifast.library.iso20022.prxy003.ProxyLookUp11;
import bifast.library.iso20022.prxy003.ProxyLookUpChoice1;
import bifast.library.iso20022.prxy003.ProxyLookUpType1Code;
import bifast.library.iso20022.prxy003.ProxyLookUpV01;

@Service
public class Proxy003MessageService {

	@Autowired
	private LibConfig config;

	public ProxyLookUpV01 proxyResolutionRequest (Proxy003Seed seed) 
			throws DatatypeConfigurationException {
			
		ProxyLookUpV01 proxy003 = new ProxyLookUpV01();
//		String msgId = utilService.genMessageId(seed.getTrnType());

		// GrpHdr
		proxy003.setGrpHdr(new GroupHeader69());
		proxy003.getGrpHdr().setMsgId(seed.getMsgId());
		
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
		XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		proxy003.getGrpHdr().setCreDtTm(xcal);

		// GrpHdr / MsgSndr / Agt/ FinInstnId/ Othr /Id
		proxy003.getGrpHdr().setMsgSndr(new Party12Choice());
		proxy003.getGrpHdr().getMsgSndr().setAgt(new BranchAndFinancialInstitutionIdentification5());
		proxy003.getGrpHdr().getMsgSndr().getAgt().setFinInstnId(new FinancialInstitutionIdentification8());
		proxy003.getGrpHdr().getMsgSndr().getAgt().getFinInstnId().setOthr(new GenericFinancialIdentification1());
		proxy003.getGrpHdr().getMsgSndr().getAgt().getFinInstnId().getOthr().setId(config.getBankcode());
		
		// GrpHdr / MsgSndr / Acct / Id/ Othr /Id
		proxy003.getGrpHdr().getMsgSndr().setAcct(new CashAccount40());
		proxy003.getGrpHdr().getMsgSndr().getAcct().setId(new AccountIdentification4Choice());
		proxy003.getGrpHdr().getMsgSndr().getAcct().getId().setOthr(new GenericAccountIdentification1());
		proxy003.getGrpHdr().getMsgSndr().getAcct().getId().getOthr().setId(seed.getSndrAccountNumber());
		
		// Lookup / PrxyOnly
		proxy003.setLookUp(new ProxyLookUpChoice1());
		proxy003.getLookUp().setPrxyOnly(new ProxyLookUp11());

		proxy003.getLookUp().getPrxyOnly().setLkUpTp(seed.getLookupType()); 
		proxy003.getLookUp().getPrxyOnly().setId(seed.getId());
		
		// Lookup / PrxyOnly /PrxyRtrvl
		proxy003.getLookUp().getPrxyOnly().setPrxyRtrvl(new ProxyDefinition1());
		proxy003.getLookUp().getPrxyOnly().getPrxyRtrvl().setTp(seed.getProxyType());
		proxy003.getLookUp().getPrxyOnly().getPrxyRtrvl().setVal(seed.getProxyValue());
		
		return proxy003;
	}

}
