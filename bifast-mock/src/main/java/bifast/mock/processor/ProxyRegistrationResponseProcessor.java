package bifast.mock.processor;

import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.prxy001.ProxyRegistrationType1Code;
import bifast.library.iso20022.prxy002.ProxyRegistrationResponseV01;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Proxy002MessageService;
import bifast.library.iso20022.service.Proxy002Seed;
import bifast.mock.persist.AccountProxy;
import bifast.mock.persist.AccountProxyRepository;

@Component
//@ComponentScan(basePackages = {"bifast.library.iso20022.service", "bifast.library.config"} )
public class ProxyRegistrationResponseProcessor implements Processor{

	@Autowired
	private AppHeaderService hdrService;
	@Autowired
	private Proxy002MessageService proxy002MessageService;
	@Autowired
	private UtilService utilService;
	
	@Autowired
	AccountProxyRepository accountProxyRepository;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		String bizMsgId = utilService.genRfiBusMsgId("710", "01", "INDOIDJA");
		String ap = utilService.genMessageId("710", "INDOIDJA");

		Random rand = new Random();
		Proxy002Seed seed = new Proxy002Seed();
        int posbl = rand.nextInt(10);
		if (posbl == 0) {
			seed.setStatus("RJCT");
			seed.setReason("U001");
		}
		else {
			seed.setStatus("ACTC");
			seed.setReason("U000");
			
		}

		seed.setAdditionalInfo("Terimakasih atas perhaitiannya");
		seed.setCstmrId("5302022290990001");
		seed.setCstmrTp("01");
		seed.setCstmrTwnNm("0300");
		seed.setCstmrRsdntSts("01");
		
		BusinessMessage msg = exchange.getIn().getBody(BusinessMessage.class);
		
		AccountProxy accountProxy = new AccountProxy();
		
		String scndIdTp = msg.getDocument().getPrxyRegn().getRegn().getPrxyRegn().getScndId().getTp();
		String scndIdVal = msg.getDocument().getPrxyRegn().getRegn().getPrxyRegn().getScndId().getVal();
		
		accountProxy = accountProxyRepository.getByScndIdTpAndByScndIdVal(scndIdTp,scndIdVal);
		
		System.out.println(msg.getDocument().getPrxyRegn().getRegn().getRegnTp());
		
		if(msg.getDocument().getPrxyRegn().getRegn().getRegnTp() ==  ProxyRegistrationType1Code.NEWR) {
			seed = this.processNewr(msg, accountProxy, seed);
		}else if(msg.getDocument().getPrxyRegn().getRegn().getRegnTp() ==  ProxyRegistrationType1Code.DEAC) {
			seed = this.processDeac(msg, accountProxy, seed);
		}else if(msg.getDocument().getPrxyRegn().getRegn().getRegnTp() ==  ProxyRegistrationType1Code.SUSP) {
			seed = this.processSusp(msg, accountProxy, seed);
		}else if(msg.getDocument().getPrxyRegn().getRegn().getRegnTp() ==  ProxyRegistrationType1Code.AMND) {
			seed = this.processAmdn(msg, accountProxy, seed);
		}else if(msg.getDocument().getPrxyRegn().getRegn().getRegnTp() ==  ProxyRegistrationType1Code.PORT) {
			seed = this.processPort(msg, accountProxy, seed);
		}else if(msg.getDocument().getPrxyRegn().getRegn().getRegnTp() ==  ProxyRegistrationType1Code.ACTV) {
			seed = this.processActv(msg, accountProxy, seed);
		}
		
		ProxyRegistrationResponseV01 response = proxy002MessageService.proxyRegistrationResponse(seed, msg);

		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = hdrService.getAppHdr(msg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId(), 
									"prxy.002.001.01", bizMsgId);
		Document doc = new Document();
		doc.setPrxyRegnRspn(response);
		BusinessMessage busMesg = new BusinessMessage();
		busMesg.setAppHdr(hdr);
		busMesg.setDocument(doc);

		exchange.getMessage().setBody(busMesg);
	}
	
	public Proxy002Seed processNewr(BusinessMessage msg,AccountProxy accountProxy,Proxy002Seed seed) {
		if(accountProxy == null) {
			this.saveAccountProxy(msg);
		}else {
			String regisrerBank = msg.getDocument().getPrxyRegn().getRegn().getPrxyRegn().getAgt().getFinInstnId().getOthr().getId();
			if(regisrerBank.equals(accountProxy.getRegisterBank()) && (accountProxy.getAccountStatus().equals("ACTV") || accountProxy.getAccountStatus().equals("SUSP") || accountProxy.getAccountStatus().equals("SUSB"))){
				seed.setStatus("RJCT");
				seed.setReason("U808");
			}else if(!regisrerBank.equals(accountProxy.getRegisterBank()) && (accountProxy.getAccountStatus().equals("ACTV") || accountProxy.getAccountStatus().equals("SUSP") || accountProxy.getAccountStatus().equals("SUSB"))){
				seed.setStatus("RJCT");
				seed.setReason("U807");
			}else {
				this.saveAccountProxy(msg);
			}
		}
		
		return seed;
	}
	
	public Proxy002Seed processDeac(BusinessMessage msg,AccountProxy accountProxy,Proxy002Seed seed) {
		
		if(accountProxy != null) {
			String regisrerBank = msg.getDocument().getPrxyRegn().getRegn().getPrxyRegn().getAgt().getFinInstnId().getOthr().getId();
			if(regisrerBank.equals(accountProxy.getRegisterBank())){
				if(accountProxy.getAccountStatus().equals("ICTV")) {
					seed.setStatus("RJCT");
					seed.setReason("U804");
				}else if(accountProxy.getAccountStatus().equals("SUSP")) {
					seed.setStatus("RJCT");
					seed.setReason("U000");
				}else if(accountProxy.getAccountStatus().equals("SUSB")) {
					seed.setStatus("RJCT");
					seed.setReason("U811");
				}else if(accountProxy.getAccountStatus().equals("ACTV")) {
					accountProxy.setAccountStatus("ICTV");
					accountProxyRepository.save(accountProxy);
				}
			}else {

				if(accountProxy.getAccountStatus().equals("ICTV") || accountProxy.getAccountStatus().equals("SUSP") || accountProxy.getAccountStatus().equals("SUSB")) {
					seed.setStatus("RJCT");
					seed.setReason("U809");
				}else if(accountProxy.getAccountStatus().equals("ACTV")) {
					accountProxy.setAccountStatus("ICTV");
					accountProxyRepository.save(accountProxy);
				}
			}
		}
		
		return seed;
	}
	
	public Proxy002Seed processSusp(BusinessMessage msg,AccountProxy accountProxy,Proxy002Seed seed) {
		
		if(accountProxy != null) {
			String regisrerBank = msg.getDocument().getPrxyRegn().getRegn().getPrxyRegn().getAgt().getFinInstnId().getOthr().getId();
			if(regisrerBank.equals(accountProxy.getRegisterBank())){
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
					accountProxy.setAccountStatus("SUSP");
					accountProxyRepository.save(accountProxy);
				}
			
			}else {
				if(accountProxy.getAccountStatus().equals("ICTV") || accountProxy.getAccountStatus().equals("SUSP") || accountProxy.getAccountStatus().equals("SUSB")) {
					seed.setStatus("RJCT");
					seed.setReason("U809");
				}else if(accountProxy.getAccountStatus().equals("ACTV")) {
					accountProxy.setAccountStatus("SUSP");
					accountProxyRepository.save(accountProxy);
				}
			}
		}else {
			seed.setStatus("RJCT");
			seed.setReason("U804");
		}
		
		return seed;
	}
	
	public Proxy002Seed processAmdn(BusinessMessage msg,AccountProxy accountProxy,Proxy002Seed seed) {
		
		if(accountProxy != null) {
			String regisrerBank = msg.getDocument().getPrxyRegn().getRegn().getPrxyRegn().getAgt().getFinInstnId().getOthr().getId();
			if(regisrerBank.equals(accountProxy.getRegisterBank())){
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
					this.saveAccountProxy(msg);
				}
			
			}else {

				if(accountProxy.getAccountStatus().equals("ICTV") || accountProxy.getAccountStatus().equals("SUSP") || accountProxy.getAccountStatus().equals("SUSB")) {
					seed.setStatus("RJCT");
					seed.setReason("U809");
				}else if(accountProxy.getAccountStatus().equals("ACTV")) {
					this.saveAccountProxy(msg);
				}
			}
		}else {
			seed.setStatus("RJCT");
			seed.setReason("U804");
		}
		
		return seed;
	}
	
	public Proxy002Seed processPort(BusinessMessage msg,AccountProxy accountProxy,Proxy002Seed seed) {
		
		if(accountProxy != null) {
			String regisrerBank = msg.getDocument().getPrxyRegn().getRegn().getPrxyRegn().getAgt().getFinInstnId().getOthr().getId();
			if(regisrerBank.equals(accountProxy.getRegisterBank())){
				
				seed.setStatus("RJCT");
				seed.setReason("U810");
			}else {

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
					
				}
			}
		}else {
			seed.setStatus("RJCT");
			seed.setReason("U804");
		}
		
		return seed;
	}
	
	public Proxy002Seed processActv(BusinessMessage msg,AccountProxy accountProxy,Proxy002Seed seed) {
		
		if(accountProxy != null) {
			String regisrerBank = msg.getDocument().getPrxyRegn().getRegn().getPrxyRegn().getAgt().getFinInstnId().getOthr().getId();
			if(regisrerBank.equals(accountProxy.getRegisterBank())){
				if(accountProxy.getAccountStatus().equals("ICTV")) {
					seed.setStatus("RJCT");
					seed.setReason("U804");
				}else if(accountProxy.getAccountStatus().equals("SUSP")) {
					accountProxy.setAccountStatus("ACTV");
					accountProxyRepository.save(accountProxy);
				}else if(accountProxy.getAccountStatus().equals("SUSB")) {
					seed.setStatus("RJCT");
					seed.setReason("U811");
				}else if(accountProxy.getAccountStatus().equals("ACTV")) {
					seed.setStatus("RJCT");
					seed.setReason("U805");
				}
			
			}else {
				if(accountProxy.getAccountStatus().equals("ACTV") || accountProxy.getAccountStatus().equals("ICTV") || accountProxy.getAccountStatus().equals("SUSP") || accountProxy.getAccountStatus().equals("SUSB")) {
					seed.setStatus("RJCT");
					seed.setReason("U809");
				}
			}
		}else {
			seed.setStatus("RJCT");
			seed.setReason("U804");
		}
		
		return seed;
	}
	
	void saveAccountProxy (BusinessMessage msg) {
		AccountProxy accountProxy =  new AccountProxy();
		accountProxy.setAccountNumber(msg.getDocument().getPrxyRegn().getRegn().getPrxyRegn().getAcct().getId().getOthr().getId());
		accountProxy.setDisplayName(msg.getDocument().getPrxyRegn().getRegn().getPrxyRegn().getDsplNm());
		accountProxy.setProxyType(msg.getDocument().getPrxyRegn().getRegn().getPrxy().getTp());
		accountProxy.setProxyVal(msg.getDocument().getPrxyRegn().getRegn().getPrxy().getVal());
		accountProxy.setRegisterBank(msg.getDocument().getPrxyRegn().getRegn().getPrxyRegn().getAgt().getFinInstnId().getOthr().getId());
		accountProxy.setAccountName(msg.getDocument().getPrxyRegn().getRegn().getPrxyRegn().getAcct().getNm());
		accountProxy.setAccountType(msg.getDocument().getPrxyRegn().getRegn().getPrxyRegn().getAcct().getTp().getPrtry());
		accountProxy.setAccountStatus("ACTV");
		accountProxy.setScndIdTp(msg.getDocument().getPrxyRegn().getRegn().getPrxyRegn().getScndId().getTp());
		accountProxy.setScndIdVal(msg.getDocument().getPrxyRegn().getRegn().getPrxyRegn().getScndId().getVal());
		accountProxyRepository.save(accountProxy);
	}


}
