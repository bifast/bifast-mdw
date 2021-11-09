package bifast.outbound.service;

import org.springframework.stereotype.Service;

import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.exception.InputValidationException;

@Service
public class ValidationService {

	public void validateAccountEnquiryRequest (ChnlAccountEnquiryRequestPojo aeReq) throws Exception {
		
		if (null == aeReq.getCreditorAccountNumber()) {
			if (null == aeReq.getProxyId())
				throw new InputValidationException("CreditorAccountNumber atau ProxyId/ProxyType tidak boleh kosong.");
			if (null == aeReq.getProxyType())
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
	
	public void validateCreditTransferRequest (ChnlCreditTransferRequestPojo ctReq) throws Exception {
		
		String pattern = "^\\d+\\.\\d\\d";
		if (!(ctReq.getAmount().matches(pattern))) 
			throw new InputValidationException("Format Amount salah.");
		if (ctReq.getAmount().length() > 19)
				throw new InputValidationException("Format Amount salah.");	

	}
}
