package bifast.outbound.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bifast.outbound.corebank.pojo.CbAccountCustomerInfoRequestPojo;
import bifast.outbound.corebank.pojo.CbDebitRequestPojo;
import bifast.outbound.corebank.pojo.CbDebitReversalPojo;
import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.CorebankTransactionRepository;

@Service
public class CorebankService {
	
	@Autowired
	private CorebankTransactionRepository cbRepo;

    DateTimeFormatter fmtMillis = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    DateTimeFormatter fmtSecond = DateTimeFormatter.ofPattern("yyMMddHHmmss");
	
	public CbDebitRequestPojo initDebitRequest (RequestMessageWrapper rmw, Object oChnlRequest) {
		CbDebitRequestPojo debitReq = new CbDebitRequestPojo();

		LocalDateTime now = LocalDateTime.now();
		Long cbId = cbRepo.getCorebankSequence();

		debitReq.setTransactionId("000000");
		
		String cbNoRef = "KOM" + fmtSecond.format(now) + String.format("%05d", cbId);	
		debitReq.setKomiNoRef(cbNoRef);

		debitReq.setDateTime(fmtMillis.format(LocalDateTime.now()));
		
		debitReq.setOriginalNoRef(rmw.getRequestId());
		
		debitReq.setOriginalDateTime(fmtMillis.format(now));

		debitReq.setMerchantType(rmw.getMerchantType());
		
		if (oChnlRequest.getClass().getSimpleName().equals("ChnlCreditTransferRequestPojo")) {
			ChnlCreditTransferRequestPojo req = (ChnlCreditTransferRequestPojo) oChnlRequest;
			debitReq.setTerminalId(req.getTerminalId());
		}

		return debitReq;
	}
	
	public CbAccountCustomerInfoRequestPojo initAccountCustInfoRequest (RequestMessageWrapper rmw) {
		
		CbAccountCustomerInfoRequestPojo aciReq = new CbAccountCustomerInfoRequestPojo();
		
		LocalDateTime now = LocalDateTime.now();
		Long cbId = cbRepo.getCorebankSequence();

		aciReq.setTransactionId("000000");
		aciReq.setDateTime(fmtMillis.format(now));

		String cbNoRef = "KOM" + fmtSecond.format(now) + String.format("%05d", cbId);	
		aciReq.setKomiNoRef(cbNoRef);
		
		aciReq.setMerchantType(rmw.getMerchantType());
		
		aciReq.setTerminalId("000");

		return aciReq;
	}
	
	public CbDebitReversalPojo initDebitReversal (ChnlCreditTransferRequestPojo chnlReq, String merchantType) {
		CbDebitReversalPojo reversalReq = new CbDebitReversalPojo();

		LocalDateTime now = LocalDateTime.now();
		Long cbId = cbRepo.getCorebankSequence();

		reversalReq.setTransactionId("000000");
		reversalReq.setDateTime(fmtMillis.format(now));
		
		String cbNoRef = "KOM" + fmtSecond.format(now) + String.format("%05d", cbId);	
		reversalReq.setKomiNoRef(cbNoRef);

		reversalReq.setOriginalNoRef(chnlReq.getChannelRefId());
		reversalReq.setOriginalDateTime(fmtMillis.format(chnlReq.getDateTime()));
		
		reversalReq.setMerchantType(merchantType);
		reversalReq.setTerminalId(chnlReq.getTerminalId());

		reversalReq.setAmount(chnlReq.getAmount());
		reversalReq.setCategoryPurpose(chnlReq.getCategoryPurpose());
		reversalReq.setCreditorAccountNumber(chnlReq.getCrdtAccountNo());
		reversalReq.setCreditorAccountType(chnlReq.getCrdtAccountType());
		reversalReq.setCreditorId(chnlReq.getCrdtId());
		reversalReq.setCreditorName(chnlReq.getCrdtName());
		
		if (null != chnlReq.getCrdtProxyIdValue()) {
			reversalReq.setCreditorProxyId(chnlReq.getCrdtProxyIdValue());
			reversalReq.setCreditorProxyType(chnlReq.getCrdtProxyIdType());
		}

		reversalReq.setCreditorResidentStatus(chnlReq.getCrdtResidentialStatus());
		reversalReq.setCreditorTownName(chnlReq.getCrdtTownName());
		reversalReq.setCreditorType(chnlReq.getCrdtType());
		reversalReq.setDebtorAccountNumber(chnlReq.getDbtrAccountNo());
		reversalReq.setDebtorAccountType(chnlReq.getDbtrAccountType());
		reversalReq.setDebtorId(chnlReq.getDbtrId());
		reversalReq.setDebtorName(chnlReq.getDbtrName());
		reversalReq.setDebtorResidentStatus(chnlReq.getDbtrResidentialStatus());
		reversalReq.setDebtorTownName(chnlReq.getDbtrTownName());
		reversalReq.setDebtorType(chnlReq.getDbtrType());
		reversalReq.setFeeTransfer(chnlReq.getFeeTransfer());
		reversalReq.setPaymentInformation(chnlReq.getPaymentInfo());
		reversalReq.setRecipientBank(chnlReq.getRecptBank());
				
		return reversalReq;
	}

}
