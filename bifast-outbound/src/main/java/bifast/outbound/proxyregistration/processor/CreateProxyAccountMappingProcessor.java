package bifast.outbound.proxyregistration.processor;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.prxy001.ProxyRegistrationV01;
import bifast.library.iso20022.prxy002.ProxyRegistrationResponseV01;
import bifast.outbound.model.ProxyAccountMapping;
import bifast.outbound.repository.ProxyAccountMappingRepository;

@Component
public class CreateProxyAccountMappingProcessor implements Processor {

	@Autowired
	private ProxyAccountMappingRepository proxyAccountMappingRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		BusinessMessage bmRequest = exchange.getMessage().getHeader("prx_birequest", BusinessMessage.class);
		BusinessMessage bmResponse = exchange.getMessage().getHeader("prx_biresponse", BusinessMessage.class);

		ProxyRegistrationV01 req = bmRequest.getDocument().getPrxyRegn();
		ProxyRegistrationResponseV01 response = bmResponse.getDocument().getPrxyRegnRspn();

		ProxyAccountMapping paMap = new ProxyAccountMapping();

		paMap.setAccountName(req.getRegn().getPrxyRegn().getAcct().getNm());
		paMap.setAccountNumber(req.getRegn().getPrxyRegn().getAcct().getId().getOthr().getId());
		paMap.setAccountType(req.getRegn().getPrxyRegn().getAcct().getTp().getPrtry());
		
		if (req.getSplmtryData().size() > 0) {
			if (!(null == req.getSplmtryData().get(0).getEnvlp().getCstmr().getId()))
				paMap.setCustomerId(req.getSplmtryData().get(0).getEnvlp().getCstmr().getId());
			
			if (!(null == req.getSplmtryData().get(0).getEnvlp().getCstmr().getTp()))
				paMap.setCustomerType(null);

			if (!(null == req.getSplmtryData().get(0).getEnvlp().getCstmr().getTwnNm()))
				paMap.setCustomerTownName(null);
			
			if (!(null == req.getSplmtryData().get(0).getEnvlp().getCstmr().getRsdntSts()))
				paMap.setResidentStatus(req.getSplmtryData().get(0).getEnvlp().getCstmr().getRsdntSts());
		}		
		
		if (!(null == req.getRegn().getPrxyRegn().getDsplNm()))
			paMap.setDisplayName(req.getRegn().getPrxyRegn().getDsplNm());

		paMap.setProxyRegAgtId(req.getRegn().getPrxyRegn().getAgt().getFinInstnId().getOthr().getId());
		paMap.setProxyStatus(null);
		paMap.setProxyType(req.getRegn().getPrxy().getTp());
		paMap.setProxyValue(req.getRegn().getPrxy().getVal());
		
		paMap.setRegistDt(null);
		
		
		paMap.setRegistId(response.getRegnRspn().getPrxyRegn().get(0).getRegnId());
		
		paMap.setResidentStatus(null);
		
		paMap.setScndCustomerId(req.getRegn().getPrxyRegn().getScndId().getVal());
		paMap.setScndCustomerIdType(req.getRegn().getPrxyRegn().getScndId().getTp());
		
		paMap.setUpdateDt(LocalDateTime.now());
		
		proxyAccountMappingRepo.save(paMap);
		
	}

}
