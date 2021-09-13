package bifast.outbound.ficredittransfer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.BankCode;
import bifast.outbound.model.DomainCode;
import bifast.outbound.repository.BankCodeRepository;
import bifast.outbound.repository.DomainCodeRepository;


@Component
public class ValidateFICTRequestProcessor implements Processor  {

	@Autowired
	private DomainCodeRepository domainCodeRepo;
	@Autowired
	private BankCodeRepository bankCodeRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		ChnlFICreditTransferRequestPojo ctReq = exchange.getIn().getBody(ChnlFICreditTransferRequestPojo.class);

		// periksa channel
		exchange.getMessage().setHeader("hdr_errorlocation", "FICreditTransferRequest/channel");
		
		DomainCode channelDC = domainCodeRepo.findByGrpAndValue("CHANNEL.TYPE", ctReq.getChannel()).orElseThrow();
		ctReq.setChannel(channelDC.getKey());
		
		//periksa kode bank
		exchange.getMessage().setHeader("hdr_errorlocation", "FICreditTransferRequest/recipientBank");
		BankCode bankCode = bankCodeRepo.findById(ctReq.getRecptBank()).orElseThrow();
		ctReq.setRecptBank(bankCode.getBicCode());
	}

}
