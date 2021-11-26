package bifast.outbound.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.exception.InputValidationException;
import bifast.outbound.model.DomainCode;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationRequestPojo;
import bifast.outbound.repository.DomainCodeRepository;

@Service
public class ValidationService {
	@Autowired private DomainCodeRepository domainCodeRepo;

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
		if (!(ctReq.getFeeTransfer().matches(pattern))) 
			throw new InputValidationException("Format Amount salah.");
		if (ctReq.getFeeTransfer().length() > 19)
				throw new InputValidationException("Format Amount salah.");	

		try {
			@SuppressWarnings("unused")
			DomainCode dm = domainCodeRepo.findByGrpAndKey("CUSTOMER.TYPE", ctReq.getCrdtType()).orElseThrow();
			dm = domainCodeRepo.findByGrpAndKey("CUSTOMER.TYPE", ctReq.getDbtrType()).orElseThrow();
		}
		catch(NoSuchElementException ne) {
				throw new InputValidationException ("Input value error");
		}

	}

	public void validateProxyRegistration (ChnlProxyRegistrationRequestPojo regnReq) throws Exception {
		
		try {
			@SuppressWarnings("unused")
			DomainCode dm = domainCodeRepo.findByGrpAndKey("PRXYOPER.TYPE", regnReq.getRegistrationType()).orElseThrow();
		}
		catch(NoSuchElementException ne) {
				throw new InputValidationException ("Proxy Operation type error");
		}

		if (regnReq.getRegistrationType().equals("NEWR")) {
			if (null == regnReq.getDisplayName()) 
				throw new InputValidationException("DisplayName tidak boleh kosong.");
			if (null == regnReq.getAccountName()) 
				throw new InputValidationException("AccountName tidak boleh kosong.");
			if (null == regnReq.getAccountNumber()) 
				throw new InputValidationException("AccountNumber tidak boleh kosong.");
			if (null == regnReq.getAccountType()) 
				throw new InputValidationException("AccountType tidak boleh kosong.");
			if (null == regnReq.getCustomerType()) 
				throw new InputValidationException("CustomerType tidak boleh kosong.");
			if (null == regnReq.getResidentialStatus()) 
				throw new InputValidationException("ResidentialStatus tidak boleh kosong.");
			if (null == regnReq.getTownName()) 
				throw new InputValidationException("TownName tidak boleh kosong.");
			
			if (regnReq.getDisplayName().isBlank())
				throw new InputValidationException("DisplayName tidak boleh kosong.");
			if (regnReq.getAccountName().isBlank()) 
				throw new InputValidationException("AccountName tidak boleh kosong.");
			if (regnReq.getAccountNumber().isBlank()) 
				throw new InputValidationException("AccountNumber tidak boleh kosong.");
			if (regnReq.getAccountType().isBlank()) 
				throw new InputValidationException("AccountType tidak boleh kosong.");
			if (regnReq.getCustomerType().isBlank()) 
				throw new InputValidationException("CustomerType tidak boleh kosong.");
			if (regnReq.getResidentialStatus().isBlank()) 
				throw new InputValidationException("ResidentialStatus tidak boleh kosong.");
			if (regnReq.getTownName().isBlank()) 
				throw new InputValidationException("TownName tidak boleh kosong.");

		}
	}


}
