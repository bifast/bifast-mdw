package bifast.outbound.credittransfer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.BankCode;
import bifast.outbound.model.DomainCode;
import bifast.outbound.repository.BankCodeRepository;
import bifast.outbound.repository.DomainCodeRepository;


@Component
public class ValidateCTRequestProcessor implements Processor  {

	@Autowired
	private DomainCodeRepository domainCodeRepo;
	@Autowired
	private BankCodeRepository bankCodeRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		ChnlCreditTransferRequestPojo ctReq = exchange.getIn().getBody(ChnlCreditTransferRequestPojo.class);

		// periksa channel
		exchange.getMessage().setHeader("hdr_errorlocation", "CreditTransferRequest/channel");
		
		DomainCode channelDC = domainCodeRepo.findByGrpAndValue("CHANNEL.TYPE", ctReq.getChannel()).orElseThrow();
		ctReq.setChannel(channelDC.getKey());

		// periksa categoryPurpose
		exchange.getMessage().setHeader("hdr_errorlocation", "CreditTransferRequest/categoryPurpose");
		DomainCode categoryPurposeDC = domainCodeRepo.findByGrpAndValue("CATEGORY.PURPOSE", ctReq.getCategoryPurpose()).orElseThrow();
		ctReq.setCategoryPurpose(categoryPurposeDC.getKey());
			
		//periksa kode bank
		exchange.getMessage().setHeader("hdr_errorlocation", "CreditTransferRequest/recipientBank");
		BankCode bankCode = bankCodeRepo.findById(ctReq.getRecptBank()).orElseThrow();
		ctReq.setRecptBank(bankCode.getBicCode());
	}

}
