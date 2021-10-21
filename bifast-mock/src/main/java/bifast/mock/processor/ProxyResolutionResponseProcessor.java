package bifast.mock.processor;

import java.util.GregorianCalendar;
import java.util.Random;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.config.LibConfig;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.prxy001.ProxyRegistrationType1Code;
import bifast.library.iso20022.prxy003.ProxyLookUpType1Code;
import bifast.library.iso20022.prxy004.AccountIdentification4Choice;
import bifast.library.iso20022.prxy004.BIAddtlCstmrInf;
import bifast.library.iso20022.prxy004.BISupplementaryData1;
import bifast.library.iso20022.prxy004.BISupplementaryDataEnvelope1;
import bifast.library.iso20022.prxy004.BranchAndFinancialInstitutionIdentification5;
import bifast.library.iso20022.prxy004.CashAccount40;
import bifast.library.iso20022.prxy004.CashAccountType2ChoiceProxy;
import bifast.library.iso20022.prxy004.FinancialInstitutionIdentification8;
import bifast.library.iso20022.prxy004.GenericAccountIdentification1;
import bifast.library.iso20022.prxy004.GenericFinancialIdentification1;
import bifast.library.iso20022.prxy004.GroupHeader60;
import bifast.library.iso20022.prxy004.OriginalGroupInformation3;
import bifast.library.iso20022.prxy004.Party12Choice;
import bifast.library.iso20022.prxy004.ProxyDefinition1;
import bifast.library.iso20022.prxy004.ProxyLookUpAccount1;
import bifast.library.iso20022.prxy004.ProxyLookUpRegistration1;
import bifast.library.iso20022.prxy004.ProxyLookUpResponse1;
import bifast.library.iso20022.prxy004.ProxyLookUpResponseV01;
import bifast.library.iso20022.prxy004.ProxyStatusChoice;
import bifast.library.iso20022.prxy004.ProxyStatusCode;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Proxy002MessageService;
import bifast.library.iso20022.service.Proxy002Seed;
import bifast.library.iso20022.service.Proxy003MessageService;
import bifast.library.iso20022.service.Proxy003Seed;
import bifast.mock.persist.AccountProxy;
import bifast.mock.persist.AccountProxyRepository;


@Component
//@ComponentScan(basePackages = {"bifast.library.iso20022.service", "bifast.library.config"} )
public class ProxyResolutionResponseProcessor implements Processor{

	@Autowired
	private AppHeaderService hdrService;
	@Autowired
	private Proxy002MessageService proxy002MessageService;
	
	@Autowired
	private Proxy003MessageService proxy003MessageService;
	
	@Autowired
	private UtilService utilService;
	
	@Autowired
	AccountProxyRepository accountProxyRepository;
	
	@Autowired
	private LibConfig config;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		
		String bizMsgId = utilService.genRfiBusMsgId("610", "01", "INDOIDJA");
		String msgId = utilService.genMessageId("610", "INDOIDJA");

		Random rand = new Random();
		Proxy003Seed seed = new Proxy003Seed();
		
		String status = "";
		String reason = "";
		
		seed.setMsgId(msgId);
        int posbl = rand.nextInt(10);
		if (posbl == 0) {
			status = "RJCT";
			reason = "U001";
			//seed.setStatus("RJCT");
			//seed.setReason("U001");
		}
		else {
			status = "ACTC";
			reason = "U000";
			//seed.setStatus("ACTC");
			//seed.setReason("U000");
			
		}
		
		BusinessMessage msg = exchange.getIn().getBody(BusinessMessage.class);
		
		String proxyType = msg.getDocument().getPrxyLookUp().getLookUp().getPrxyOnly().getPrxyRtrvl().getTp();
		String proxyVal =  msg.getDocument().getPrxyLookUp().getLookUp().getPrxyOnly().getPrxyRtrvl().getVal();;
		
		AccountProxy accountProxy = new AccountProxy();	
		accountProxy = accountProxyRepository.getByProxyTypeAndByProxyVal(proxyType,proxyVal);
		
		if(msg.getDocument().getPrxyLookUp().getLookUp().getPrxyOnly().getLkUpTp() ==  ProxyLookUpType1Code.PXRS) {
			if(accountProxy != null) {
				if(accountProxy.getAccountStatus().equals("ICTV")) {
					status = "RJCT";
					reason = "U804";
				}else if(accountProxy.getAccountStatus().equals("SUSP")) {
					status = "RJCT";
					reason = "U805";
				}else if(accountProxy.getAccountStatus().equals("SUSB")) {
					status = "RJCT";
					reason = "U811";
				}else if(accountProxy.getAccountStatus().equals("ACTV")) {
					accountProxy.setRegisterBank(msg.getDocument().getPrxyLookUp().getGrpHdr().getMsgSndr().getAgt().getFinInstnId().getOthr().getId());
					accountProxyRepository.save(accountProxy);
				}
			}else {
				status = "RJCT";
				reason = "U811";
			}
		}else if(msg.getDocument().getPrxyLookUp().getLookUp().getPrxyOnly().getLkUpTp()  ==  ProxyLookUpType1Code.CHCK) {
			if(accountProxy != null) {
				if(accountProxy.getAccountStatus().equals("ICTV")) {
					status = "RJCT";
					reason = "U804";
				}else if(accountProxy.getAccountStatus().equals("SUSP")) {
					status = "RJCT";
					reason = "U805";
				}else if(accountProxy.getAccountStatus().equals("SUSB")) {
					status = "RJCT";
					reason = "U811";
				}
			}else {
				status = "RJCT";
				reason = "U811";
			}
		}else if(msg.getDocument().getPrxyLookUp().getLookUp().getPrxyOnly().getLkUpTp()  ==  ProxyLookUpType1Code.NMEQ) {
			if(accountProxy != null) {
				if(accountProxy.getAccountStatus().equals("ICTV")) {
					status = "RJCT";
					reason = "U804";
				}else if(accountProxy.getAccountStatus().equals("SUSP")) {
					status = "RJCT";
					reason = "U805";
				}else if(accountProxy.getAccountStatus().equals("SUSB")) {
					status = "RJCT";
					reason = "U811";
				}
			}else {
				status = "RJCT";
				reason = "U811";
			}
		}
		//Response
		ProxyLookUpResponseV01 rest = new ProxyLookUpResponseV01();
		
		GroupHeader60 grpHdr = new GroupHeader60();
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
		
		grpHdr.setMsgRcpt(msgSndr);
		rest.setGrpHdr(grpHdr);	
		
		
		ProxyLookUpResponse1 regnRspn = new ProxyLookUpResponse1();
				  
		regnRspn.setOrgnlId(seed.getMsgId());
		  
		 ProxyDefinition1 prxyRtrvl = new ProxyDefinition1();
		 prxyRtrvl.setTp(msg.getDocument().getPrxyLookUp().getLookUp().getPrxyOnly().getPrxyRtrvl().getTp()); 
		 prxyRtrvl.setVal(msg.getDocument().getPrxyLookUp().getLookUp().getPrxyOnly().getPrxyRtrvl().getVal());
		 
		 regnRspn.setOrgnlPrxyRtrvl(prxyRtrvl);
		 
		 ProxyLookUpRegistration1 regn = new ProxyLookUpRegistration1();
		 
			if(status.equals("ACTC")) {
				regn.setPrxRspnSts(ProxyStatusCode.ACTC);
			}else {
				regn.setPrxRspnSts(ProxyStatusCode.RJCT);
			}
				
			ProxyStatusChoice StsRsnInf = new ProxyStatusChoice();
			StsRsnInf.setPrtry(reason);
			regn.setStsRsnInf(StsRsnInf);
			
			regn.setPrxy(prxyRtrvl);
			
			if(accountProxy != null) {
			
				ProxyLookUpAccount1 prxyAcc = new ProxyLookUpAccount1();
				GenericFinancialIdentification1 othrRegnRspn = new GenericFinancialIdentification1();
				othrRegnRspn.setId(accountProxy.getRegisterBank());
				
				FinancialInstitutionIdentification8 finInstnIdRegnRspn = new FinancialInstitutionIdentification8();
				finInstnIdRegnRspn.setOthr(othrRegnRspn);
				
				BranchAndFinancialInstitutionIdentification5 agtRegnRspn = new BranchAndFinancialInstitutionIdentification5();
				agtRegnRspn.setFinInstnId(finInstnIdRegnRspn);
				prxyAcc.setAgt(agtRegnRspn);
				
				GenericAccountIdentification1 othr = new GenericAccountIdentification1();
				othr.setId(accountProxy.getAccountNumber());
				
				AccountIdentification4Choice id = new AccountIdentification4Choice();
				id.setOthr(othr);
				
				CashAccountType2ChoiceProxy tp = new CashAccountType2ChoiceProxy();
				tp.setPrtry(accountProxy.getAccountType());
				
				CashAccount40 cashAccount40 =  new CashAccount40();
				cashAccount40.setId(id);
				
				cashAccount40.setTp(tp);
				cashAccount40.setNm(accountProxy.getAccountName());
				
				prxyAcc.setAcct(cashAccount40);
				prxyAcc.setDsplNm(accountProxy.getDisplayName());
				prxyAcc.setRegnId("0102030405060708");
				
				regn.setRegn(prxyAcc);
				
				
			 // Lookup / PrxyOnly ProxyLookUpChoice1 lookup = new ProxyLookUpChoice1();
				
			}	 
		regnRspn.setRegnRspn(regn);
		rest.setLkUpRspn(regnRspn);
		
		///
		BIAddtlCstmrInf bIAddtlCstmrInf = new BIAddtlCstmrInf();
		bIAddtlCstmrInf.setId("5302022290990001");
		bIAddtlCstmrInf.setTp("01");
		bIAddtlCstmrInf.setTwnNm("0300");
		
		///
		BISupplementaryDataEnvelope1 biSupplementaryDataEnvelope1 =  new BISupplementaryDataEnvelope1();
		biSupplementaryDataEnvelope1.setCstmr(bIAddtlCstmrInf);
		
		///
		BISupplementaryData1 biSupplementaryData1 = new BISupplementaryData1();
		biSupplementaryData1.setEnvlp(biSupplementaryDataEnvelope1);
		
		///
		rest.getSplmtryData().add(biSupplementaryData1);
		
		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = hdrService.getAppHdr(msg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId(), 
									"prxy.003.001.01", bizMsgId);
		OriginalGroupInformation3 originalGroupInformation3 =  new OriginalGroupInformation3();
		originalGroupInformation3.setOrgnlMsgId(hdr.getBizMsgIdr());
		originalGroupInformation3.setOrgnlCreDtTm(hdr.getCreDt());
		originalGroupInformation3.setOrgnlMsgNmId(hdr.getMsgDefIdr());
		rest.setOrgnlGrpInf(originalGroupInformation3);
		
		Document doc = new Document();
		doc.setPrxyLookUpRspn(rest);
		BusinessMessage busMesg = new BusinessMessage();
		busMesg.setAppHdr(hdr);
		busMesg.setDocument(doc);

		exchange.getMessage().setBody(busMesg);
		
	}


}
