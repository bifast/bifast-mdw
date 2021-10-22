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
import bifast.library.iso20022.prxy002.ProxyRegistrationResponseV01;
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
import bifast.library.iso20022.service.Proxy004MessageService;
import bifast.library.iso20022.service.Proxy004Seed;
import bifast.mock.persist.AccountProxy;
import bifast.mock.persist.AccountProxyRepository;


@Component
//@ComponentScan(basePackages = {"bifast.library.iso20022.service", "bifast.library.config"} )
public class ProxyResolutionResponseProcessor implements Processor{

	@Autowired
	private AppHeaderService hdrService;
	@Autowired
	private Proxy004MessageService proxy004MessageService;
	
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
		Proxy004Seed seed = new Proxy004Seed();
		
		seed.setMsgId(msgId);
		
        int posbl = rand.nextInt(10);
		if (posbl == 0) {
			seed.setStatus("RJCT");
			seed.setReason("U001");
		}
		else {

			seed.setStatus("ACTC");
			seed.setReason("U000");
			
		}
		
		BusinessMessage msg = exchange.getIn().getBody(BusinessMessage.class);
		
		String proxyType = msg.getDocument().getPrxyLookUp().getLookUp().getPrxyOnly().getPrxyRtrvl().getTp();
		String proxyVal =  msg.getDocument().getPrxyLookUp().getLookUp().getPrxyOnly().getPrxyRtrvl().getVal();;
		seed.setPrxyRtrvlTp(proxyType);
		seed.setPrxyRtrvlVal(proxyVal);
		
		AccountProxy accountProxy = new AccountProxy();	
		accountProxy = accountProxyRepository.getByProxyTypeAndByProxyVal(proxyType,proxyVal);
		
		if(msg.getDocument().getPrxyLookUp().getLookUp().getPrxyOnly().getLkUpTp() ==  ProxyLookUpType1Code.PXRS) {
			if(accountProxy != null) {
				if(accountProxy.getAccountStatus().equals("ICTV")) {
					seed.setStatus("RJCT");
					seed.setReason("U804");
				}else if(accountProxy.getAccountStatus().equals("SUSP")) {
					seed.setStatus("RJCT");
					seed.setReason("U805");
				}else if(accountProxy.getAccountStatus().equals("SUSB")) {
					seed.setStatus("RJCT");
					seed.setReason("U811");
				}else if(accountProxy.getAccountStatus().equals("ACTV")) {
					accountProxy.setRegisterBank(msg.getDocument().getPrxyLookUp().getGrpHdr().getMsgSndr().getAgt().getFinInstnId().getOthr().getId());
					accountProxyRepository.save(accountProxy);
				}
			}else {
				seed.setStatus("RJCT");
				seed.setReason("U811");
			}
		}else if(msg.getDocument().getPrxyLookUp().getLookUp().getPrxyOnly().getLkUpTp()  ==  ProxyLookUpType1Code.CHCK) {
			if(accountProxy != null) {
				if(accountProxy.getAccountStatus().equals("ICTV")) {
					seed.setStatus("RJCT");
					seed.setReason("U804");
				}else if(accountProxy.getAccountStatus().equals("SUSP")) {
					seed.setStatus("RJCT");
					seed.setReason("U805");
				}else if(accountProxy.getAccountStatus().equals("SUSB")) {
					seed.setStatus("RJCT");
					seed.setReason("U811");
				}
			}else {
				seed.setStatus("RJCT");
				seed.setReason("U811");
			}
		}else if(msg.getDocument().getPrxyLookUp().getLookUp().getPrxyOnly().getLkUpTp()  ==  ProxyLookUpType1Code.NMEQ) {
			if(accountProxy != null) {
				if(accountProxy.getAccountStatus().equals("ICTV")) {
					seed.setStatus("RJCT");
					seed.setReason("U804");
				}else if(accountProxy.getAccountStatus().equals("SUSP")) {
					seed.setStatus("RJCT");
					seed.setReason("U805");
				}else if(accountProxy.getAccountStatus().equals("SUSB")) {
					seed.setStatus("RJCT");
					seed.setReason("U811");
				}
			}else {
				seed.setStatus("RJCT");
				seed.setReason("U811");
			}
		}
		
		if(accountProxy != null) {
			seed.setAccountName(accountProxy.getAccountName());
			seed.setAccountNumber(accountProxy.getAccountNumber());
			seed.setAccountType(accountProxy.getAccountType());
			seed.setDisplayName(accountProxy.getDisplayName());
		}
		
		ProxyLookUpResponseV01 response = proxy004MessageService.proxyResolutionResponse(seed, msg);
		
		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = hdrService.getAppHdr(msg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId(), 
									"prxy.004.001.01", bizMsgId);
		OriginalGroupInformation3 originalGroupInformation3 =  new OriginalGroupInformation3();
		originalGroupInformation3.setOrgnlMsgId(hdr.getBizMsgIdr());
		originalGroupInformation3.setOrgnlCreDtTm(hdr.getCreDt());
		originalGroupInformation3.setOrgnlMsgNmId(hdr.getMsgDefIdr());
		response.setOrgnlGrpInf(originalGroupInformation3);
		
		Document doc = new Document();
		doc.setPrxyLookUpRspn(response);
		BusinessMessage busMesg = new BusinessMessage();
		busMesg.setAppHdr(hdr);
		busMesg.setDocument(doc);

		exchange.getMessage().setBody(busMesg);
		
	}


}
