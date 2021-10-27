package bifast.mock.processor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

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
import bifast.library.iso20022.prxy006.ProxyEnquiryResponseV01;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Proxy002MessageService;
import bifast.library.iso20022.service.Proxy002Seed;
import bifast.library.iso20022.service.Proxy004Seed;
import bifast.library.iso20022.service.Proxy006MessageService;
import bifast.library.iso20022.service.Proxy006Seed;
import bifast.library.iso20022.service.Proxy006SeedAccount;
import bifast.mock.persist.AccountProxy;
import bifast.mock.persist.AccountProxyRepository;

@Component
//@ComponentScan(basePackages = {"bifast.library.iso20022.service", "bifast.library.config"} )
public class ProxyRegistrationInquiryProcessor implements Processor{

	@Autowired
	private AppHeaderService hdrService;
	@Autowired
	private Proxy006MessageService proxy006MessageService;
	@Autowired
	private UtilService utilService;
	
	@Autowired
	AccountProxyRepository accountProxyRepository;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		String bizMsgId = utilService.genRfiBusMsgId("610", "01", "INDOIDJA");
		String ap = utilService.genMessageId("610", "INDOIDJA");
		
		Random rand = new Random();
		Proxy006Seed seed = new Proxy006Seed();
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
	
		List<AccountProxy> accountProxylist = new ArrayList<AccountProxy>();
		
		String scndIdTp = msg.getDocument().getPrxyNqryReq().getNqry().getScndId().getTp();
		String scndIdVal = msg.getDocument().getPrxyNqryReq().getNqry().getScndId().getVal();
		
		seed.setOrgnlMsgId(msg.getAppHdr().getBizMsgIdr());
		seed.setOrgnlMsgNmId(msg.getAppHdr().getMsgDefIdr());
		seed.setOrgnlCreDtTm(msg.getAppHdr().getCreDt());
		
		accountProxylist = accountProxyRepository.getListByScndIdTpAndByScndIdVal(scndIdTp,scndIdVal);
		
		if(accountProxylist.size() > 0) {
			List<Proxy006SeedAccount> proxy006SeedAccountList = new ArrayList<Proxy006SeedAccount>();
			for(AccountProxy data:accountProxylist) {
				
				Proxy006SeedAccount seedAcc =  new Proxy006SeedAccount();
				seedAcc.setRegnId(data.getReginId());
				seedAcc.setAccountName(data.getAccountName());
				seedAcc.setAccountNumber(data.getAccountNumber());
				seedAcc.setAccountType(data.getAccountType());
				seedAcc.setDisplayName(data.getDisplayName());
				seedAcc.setRegisterBank(data.getRegisterBank());
				seedAcc.setPrxyInfTp(data.getProxyType());
				seedAcc.setPrxyInfVal(data.getProxyVal());
				seedAcc.setAccountStatus(data.getAccountStatus());
				seedAcc.setAdditionalInfo("Terimakasih atas perhaitiannya");
				seedAcc.setCstmrId(data.getCstmrId());
				seedAcc.setCstmrTp(data.getCstmrTp());
				seedAcc.setCstmrTwnNm(data.getCstmrTwnNm());
				seedAcc.setCstmrRsdntSts(data.getCstmrRsdntSts());
				
				proxy006SeedAccountList.add(seedAcc);
			}
			seed.setProxy006SeedAccountList(proxy006SeedAccountList);

		}else {
			seed.setStatus("RJCT");
			seed.setReason("U804");
		}
		
		ProxyEnquiryResponseV01 response = proxy006MessageService.proxyRegistrationInquiryResponse(seed, msg);

		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = hdrService.getAppHdr(msg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId(), 
									"prxy.006.001.01", bizMsgId);
		Document doc = new Document();
		doc.setPrxyNqryRspn(response);
		BusinessMessage busMesg = new BusinessMessage();
		busMesg.setAppHdr(hdr);
		busMesg.setDocument(doc);

		exchange.getMessage().setBody(busMesg);
	}

}
