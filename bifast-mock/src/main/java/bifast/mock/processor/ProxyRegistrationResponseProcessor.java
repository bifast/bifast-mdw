package bifast.mock.processor;

import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.prxy001.BISupplementaryDataEnvelope1;
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
		
		BusinessMessage msg = exchange.getIn().getBody(BusinessMessage.class);
		
		BISupplementaryDataEnvelope1 enlp = msg.getDocument().getPrxyRegn().getSplmtryData().get(0).getEnvlp();
		
		seed.setAdditionalInfo("Terimakasih atas perhaitiannya");
		seed.setCstmrId(enlp.getCstmr().getId());
		seed.setCstmrTp(enlp.getCstmr().getTp());
		seed.setCstmrTwnNm(enlp.getCstmr().getTwnNm());
		seed.setCstmrRsdntSts(enlp.getCstmr().getRsdntSts());
		
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
			Random rand = new Random();
			long random = (long)(rand.nextDouble()*10000000000L);
			seed.setRegnId(String.valueOf(random));
			this.insertAccountProxy(msg,seed);
		}else {
			String regisrerBank = msg.getDocument().getPrxyRegn().getRegn().getPrxyRegn().getAgt().getFinInstnId().getOthr().getId();
			if(regisrerBank.equals(accountProxy.getRegisterBank()) && (accountProxy.getAccountStatus().equals("ACTV") || accountProxy.getAccountStatus().equals("SUSP") || accountProxy.getAccountStatus().equals("SUSB"))){
				seed.setStatus("RJCT");
				seed.setReason("U808");
			}else if(!regisrerBank.equals(accountProxy.getRegisterBank()) && (accountProxy.getAccountStatus().equals("ACTV") || accountProxy.getAccountStatus().equals("SUSP") || accountProxy.getAccountStatus().equals("SUSB"))){
				seed.setStatus("RJCT");
				seed.setReason("U807");
			}else {
				Random rand = new Random();
				long random = (long)(rand.nextDouble()*10000000000L);
				seed.setRegnId(String.valueOf(random));
				this.insertAccountProxy(msg,seed);
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
					this.UpdateAccountProxy(msg,accountProxy);
				}
			
			}else {

				if(accountProxy.getAccountStatus().equals("ICTV") || accountProxy.getAccountStatus().equals("SUSP") || accountProxy.getAccountStatus().equals("SUSB")) {
					seed.setStatus("RJCT");
					seed.setReason("U809");
				}else if(accountProxy.getAccountStatus().equals("ACTV")) {
					this.UpdateAccountProxy(msg,accountProxy);
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
	
	void insertAccountProxy (BusinessMessage msg,Proxy002Seed seed) {
		
		AccountProxy accountProxy =  new AccountProxy();
		accountProxy.setReginId(seed.getRegnId());
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
		accountProxy.setCstmrId(msg.getDocument().getPrxyRegn().getSplmtryData().get(0).getEnvlp().getCstmr().getId());
		accountProxy.setCstmrRsdntSts(msg.getDocument().getPrxyRegn().getSplmtryData().get(0).getEnvlp().getCstmr().getRsdntSts());
		accountProxy.setCstmrTp(msg.getDocument().getPrxyRegn().getSplmtryData().get(0).getEnvlp().getCstmr().getTp());
		accountProxy.setCstmrTwnNm(msg.getDocument().getPrxyRegn().getSplmtryData().get(0).getEnvlp().getCstmr().getTwnNm());
		accountProxyRepository.save(accountProxy);
	}
	
	void UpdateAccountProxy (BusinessMessage msg,AccountProxy accountProxy) {

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
