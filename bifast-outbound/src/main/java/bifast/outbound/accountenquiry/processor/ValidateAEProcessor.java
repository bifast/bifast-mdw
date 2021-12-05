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
		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		ChnlAccountEnquiryRequestPojo aeReq = rmw.getChnlAccountEnquiryRequest();
		
		try {
			@SuppressWarnings("unused")
			DomainCode domain = domainRepo.findByGrpAndKey("CATEGORY.PURPOSE", aeReq.getCategoryPurpose()).orElseThrow();
		}
		catch(NoSuchElementException ne) {
			throw new InputValidationException ("Category Purpose type error");
		}
		
		if (null == aeReq.getCreditorAccountNumber()) {
			if (null == aeReq.getProxyId())
				throw new InputValidationException("CreditorAccountNumber atau ProxyId/ProxyType tidak boleh kosong.");
			if (null == aeReq.getProxyType())
				throw new InputValidationException("CreditorAccountNumber atau ProxyId/ProxyType tidak boleh kosong.");
		}
		
		if ((null != aeReq.getCreditorAccountNumber()) && (null == aeReq.getRecptBank())) 
			throw new InputValidationException("RecipientBank tidak boleh kosong.");

	}

}
