package bifast.outbound.credittransfer.processor;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.config.Config;
import bifast.outbound.corebank.pojo.CBDebitRequestPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlCreditTransferRequestPojo;
import bifast.outbound.repository.CorebankTransactionRepository;

@Component
public class BuildCBDebitRequestProcessor implements Processor{
	@Autowired
	private CorebankTransactionRepository cbRepo;
	@Autowired
	private Config config;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		ChnlCreditTransferRequestPojo chnReq = rmw.getChnlCreditTransferRequest();
		
		CBDebitRequestPojo debitReq = new CBDebitRequestPojo();
		
		
//	    DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyMMddHHmmss")
//	            .withZone(ZoneId.systemDefault());
////	            .withZone(ZoneOffset.UTC);

	    LocalDateTime ldtKomiStart = LocalDateTime.ofInstant(rmw.getKomiStart(), ZoneId.systemDefault());
	    DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("yyMMddHHmmss");
		
		Long cbId = cbRepo.getCorebankSequence();
		
		debitReq.setTransactionId("000000");

	    DateTimeFormatter fmtMillis = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

		debitReq.setDateTime(fmtMillis.format(LocalDateTime.now()));
		
		debitReq.setMerchantType(rmw.getMerchantType());
		debitReq.setTerminalId(chnReq.getTerminalId());
		
		String cbNoRef = "KOM" + fmt1.format(ldtKomiStart) + String.format("%05d", cbId);
		debitReq.setNoRef(cbNoRef);
		
		debitReq.setOriginalNoRef(chnReq.getChannelRefId());
		
		debitReq.setOriginalDateTime(fmtMillis.format(ldtKomiStart));

		debitReq.setCategoryPurpose(chnReq.getCategoryPurpose());
		
		debitReq.setDebtorName(chnReq.getDbtrName());
		debitReq.setDebtorType(chnReq.getDbtrType());
		debitReq.setDebtorId(chnReq.getDbtrId());
		debitReq.setDebtorAccountNumber(chnReq.getDbtrAccountNo());	
		debitReq.setDebtorAccountType(chnReq.getDbtrAccountType());
		debitReq.setDebtorResidentStatus(chnReq.getDbtrResidentialStatus());
		debitReq.setDebtorTownName(chnReq.getDbtrTownName());
		
		debitReq.setAmount(chnReq.getAmount());
		debitReq.setFeeTransfer(chnReq.getFeeTransfer());

		debitReq.setRecipientBank(config.getBankcode()); 
		
		debitReq.setCreditorName(chnReq.getCrdtName());
		debitReq.setCreditorType(chnReq.getCrdtType());
		debitReq.setCreditorId(chnReq.getCrdtId());
		debitReq.setCreditorAccountNumber(chnReq.getCrdtAccountNo());
		debitReq.setCreditorAccountType(chnReq.getCrdtAccountType());
		debitReq.setCreditorResidentStatus(chnReq.getCrdtResidentialStatus());
		debitReq.setCreditorTownName(chnReq.getCrdtTownName());

		debitReq.setCreditorProxyId(chnReq.getCrdtProxyIdValue());
		debitReq.setCreditorProxyType(chnReq.getCrdtProxyIdType());

		debitReq.setPaymentInformation(chnReq.getPaymentInfo());
		
		rmw.setDebitAccountRequest(debitReq);
		
		exchange.getMessage().setHeader("hdr_request_list", rmw);
		exchange.getMessage().setBody(debitReq);
	}

}
