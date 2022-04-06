package bifast.outbound.accountenquiry.processor;

import java.util.NoSuchElementException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.exception.InputValidationException;
import bifast.outbound.model.DomainCode;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.DomainCodeRepository;

@Component
public class ValidateAEProcessor implements Processor{
	@Autowired DomainCodeRepository domainRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
		ChnlAccountEnquiryRequestPojo aeReq = rmw.getChnlAccountEnquiryRequest();
		
		try {
			@SuppressWarnings("unused")
			DomainCode domain = domainRepo.findByGrpAndKey("CATEGORY.PURPOSE", aeReq.getCategoryPurpose()).orElseThrow();
		}
		catch(NoSuchElementException ne) {
			throw new InputValidationException ("Input Error: Category Purpose error");
		}
		
		if ((null == aeReq.getCreditorAccountNumber() || aeReq.getCreditorAccountNumber().isBlank())) {
			if ((null == aeReq.getProxyId() || aeReq.getProxyId().isBlank()))
				throw new InputValidationException("CreditorAccountNumber atau ProxyId/ProxyType tidak boleh kosong.");
			if ((null == aeReq.getProxyType() || aeReq.getProxyType().isBlank()))
				throw new InputValidationException("CreditorAccountNumber atau ProxyId/ProxyType tidak boleh kosong.");
		}
		
		if ((null != aeReq.getCreditorAccountNumber()) && (null == aeReq.getRecptBank())) 
			throw new InputValidationException("RecipientBank tidak boleh kosong.");

		String pattern = "^\\d+\\.\\d\\d";
		if (!(aeReq.getAmount().matches(pattern))) 
			throw new InputValidationException("Format Amount salah.");
		if (aeReq.getAmount().length() > 19)
				throw new InputValidationException("Format Amount salah.");	

	}


}
