package bifast.outbound.accountenquiry;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.model.BankCode;
import bifast.outbound.model.DomainCode;
import bifast.outbound.repository.BankCodeRepository;
import bifast.outbound.repository.DomainCodeRepository;


@Component
public class ValidateAEInputProcessor implements Processor  {

	@Autowired
	private DomainCodeRepository domainCodeRepo;
	@Autowired
	private BankCodeRepository bankCodeRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		ChnlAccountEnquiryRequestPojo acctEnqReq = exchange.getIn().getBody(ChnlAccountEnquiryRequestPojo.class);
		System.out.println(acctEnqReq.getChannel());
		// periksa channel
		exchange.getMessage().setHeader("hdr_errorlocation", "AccountEnquiryRequest/channel");
		DomainCode channelDC = domainCodeRepo.findByGrpAndValue("CHANNEL.TYPE", acctEnqReq.getChannel()).orElseThrow();
		acctEnqReq.setChannel(channelDC.getKey());

		// periksa categoryPurpose
		exchange.getMessage().setHeader("hdr_errorlocation", "AccountEnquiryRequest/categoryPurpose");
		DomainCode categoryPurposeDC = domainCodeRepo.findByGrpAndValue("CATEGORY.PURPOSE", acctEnqReq.getCategoryPurpose()).orElseThrow();
		acctEnqReq.setCategoryPurpose(categoryPurposeDC.getKey());
			
		//periksa kode bank
		exchange.getMessage().setHeader("hdr_errorlocation", "AccountEnquiryRequest/recipientBank");
		BankCode bankCode = bankCodeRepo.findById(acctEnqReq.getRecptBank()).orElseThrow();
		acctEnqReq.setRecptBank(bankCode.getBicCode());
	}

}
