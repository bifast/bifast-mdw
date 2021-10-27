package bifast.library.iso20022.service;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Service;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.prxy002.BIAddtlCstmrInf;
import bifast.library.iso20022.prxy002.BISupplementaryData1;
import bifast.library.iso20022.prxy002.BISupplementaryDataEnvelope1;
import bifast.library.iso20022.prxy002.ProxyRegistrationAccount1;
import bifast.library.iso20022.prxy002.BranchAndFinancialInstitutionIdentification5;
import bifast.library.iso20022.prxy002.FinancialInstitutionIdentification8;
import bifast.library.iso20022.prxy002.GenericFinancialIdentification1;
import bifast.library.iso20022.prxy002.GroupHeader60;
import bifast.library.iso20022.prxy002.OriginalGroupInformation3;
import bifast.library.iso20022.prxy002.Party12Choice;
import bifast.library.iso20022.prxy002.ProxyRegistrationResponse1;
import bifast.library.iso20022.prxy002.ProxyRegistrationResponseV01;
import bifast.library.iso20022.prxy002.ProxyRegistrationType1Code;
import bifast.library.iso20022.prxy002.ProxyStatusCode;
import bifast.library.iso20022.prxy002.ProxyStatusChoice;


@Service
public class Proxy002MessageService {

	public ProxyRegistrationResponseV01 proxyRegistrationResponse (Proxy002Seed seed, 
			BusinessMessage orgnlMessage) 
			throws DatatypeConfigurationException {
		
		
		ProxyRegistrationResponseV01 proxy002 = new ProxyRegistrationResponseV01();
//		String msgId = utilService.genMessageId("710");
		
		// GrpHdr
		GroupHeader60 grpHdr = new GroupHeader60();
		grpHdr.setMsgId(seed.getMsgId());
		
		GregorianCalendar gcal = new GregorianCalendar();
		XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		grpHdr.setCreDtTm(xcal);
		
		proxy002.setGrpHdr(grpHdr);

		///msgRcpt
		Party12Choice msgRcpt = new Party12Choice();
		
		GenericFinancialIdentification1 othrGrp = new GenericFinancialIdentification1();
		othrGrp.setId(seed.getMsgRcptAgtId());
		
		FinancialInstitutionIdentification8 finInstnIdGrp = new FinancialInstitutionIdentification8();
		finInstnIdGrp.setOthr(othrGrp);
		
		BranchAndFinancialInstitutionIdentification5 agtGrp = new BranchAndFinancialInstitutionIdentification5();
		agtGrp.setFinInstnId(finInstnIdGrp);
		msgRcpt.setAgt(agtGrp);
		grpHdr.setMsgRcpt(msgRcpt);
		
		///orgnlGrpInf
		OriginalGroupInformation3 orgnlGrpInf = new OriginalGroupInformation3();
		orgnlGrpInf.setOrgnlMsgId(orgnlMessage.getAppHdr().getBizMsgIdr());
		orgnlGrpInf.setOrgnlMsgNmId(orgnlMessage.getAppHdr().getMsgDefIdr());
		orgnlGrpInf.setOrgnlCreDtTm(orgnlMessage.getAppHdr().getCreDt());
		
		
		/// Registration Response
		ProxyRegistrationResponse1 regnRspn = new ProxyRegistrationResponse1();
		if(seed.getStatus().equals("ACTC")) {
			regnRspn.setPrxRspnSts(ProxyStatusCode.ACTC);
		}else {
			regnRspn.setPrxRspnSts(ProxyStatusCode.RJCT);
		}
			
		ProxyStatusChoice StsRsnInf = new ProxyStatusChoice();
		StsRsnInf.setPrtry(seed.getReason());
		regnRspn.setStsRsnInf(StsRsnInf);
		
		//regnRspn.setOrgnlRegnTp(ProxyRegistrationType1Code.NEWR);
		
		ProxyRegistrationAccount1 prxyRegn = new ProxyRegistrationAccount1();
		
		prxyRegn.setRegnId(seed.getRegnId());

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
		prxyRegn.getSplmtryData().add(biSupplementaryData1);
		
		
		GenericFinancialIdentification1 othrRegnRspn = new GenericFinancialIdentification1();
		othrRegnRspn.setId(seed.getAgtId());
		
		FinancialInstitutionIdentification8 finInstnIdRegnRspn = new FinancialInstitutionIdentification8();
		finInstnIdRegnRspn.setOthr(othrRegnRspn);
		
		BranchAndFinancialInstitutionIdentification5 agtRegnRspn = new BranchAndFinancialInstitutionIdentification5();
		agtRegnRspn.setFinInstnId(finInstnIdRegnRspn);
		prxyRegn.setAgt(agtRegnRspn);
		
		regnRspn.getPrxyRegn().add(prxyRegn);
		
		proxy002.setGrpHdr(grpHdr);
		proxy002.setOrgnlGrpInf(orgnlGrpInf);
		proxy002.setRegnRspn(regnRspn);
		
		return proxy002;
	}

}
