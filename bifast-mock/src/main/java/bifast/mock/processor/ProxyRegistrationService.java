package bifast.mock.processor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.service.Proxy002Seed;
import bifast.mock.persist.AccountProxy;
import bifast.mock.persist.AccountProxyRepository;

@Service
public class ProxyRegistrationService {
	@Autowired AccountProxyRepository proxyRepo;
	@Autowired UtilService utilService;
	
	public Proxy002Seed proxyPort (BusinessMessage bm) {
		
		Proxy002Seed seed = new Proxy002Seed();
		
//		String bizMsgId = utilService.genRfiBusMsgId("710", "01", "INDOIDJA");
//		String msgId = utilService.genMessageId("710", "INDOIDJA");
//		seed.setMsgId(msgId);

		String proxyType = bm.getDocument().getPrxyRegn().getRegn().getPrxy().getTp();
		String proxyVal = bm.getDocument().getPrxyRegn().getRegn().getPrxy().getVal();

		
		Optional<AccountProxy> oAccountProxy = proxyRepo.findByProxyTypeAndProxyVal(proxyType, proxyVal);

		if (oAccountProxy.isEmpty()) {
			seed.setStatus("RJCT");
			seed.setReason("U804");
			
		}
		
		else {
			AccountProxy proxy = oAccountProxy.get();

			seed.setAgtId("SIHBIDJ1");
			seed.setCreDtTm(proxyVal);
			seed.setCstmrId(proxy.getCstmrId());
			seed.setCstmrRsdntSts(proxy.getCstmrRsdntSts());
			seed.setCstmrTp(proxy.getCstmrTp());
			seed.setCstmrTwnNm(proxy.getCstmrTwnNm());
			seed.setMsgRcptAgtId("SIHBIDJ1");
			seed.setRegnId(proxy.getReginId());

			seed.setStatus("ACTC");
			seed.setReason("U000");

			proxy.setRegisterBank("SIHBIDJ1");
			
			proxyRepo.save(proxy);
		}
		
		
		return seed;
	}
	
}
