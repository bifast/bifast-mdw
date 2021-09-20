package bifast.outbound.corebank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.CorebankTransaction;
import bifast.outbound.repository.CorebankTransactionRepository;

@Component
public class SaveCBTableProcessor implements Processor{

	@Autowired
	private CorebankTransactionRepository cbTransactionRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		Object objCbRequest = exchange.getMessage().getHeader("cb_request", Object.class);
		CBInstructionWrapper responseWr = exchange.getMessage().getHeader("cb_response", CBInstructionWrapper.class);
		String requestClassName = objCbRequest.getClass().getSimpleName();

		CorebankTransaction cb = new CorebankTransaction();

		if (requestClassName.equals("CBDebitInstructionRequestPojo")) {
			CBDebitInstructionRequestPojo debitReq = (CBDebitInstructionRequestPojo) objCbRequest;
			CBDebitInstructionResponsePojo debitResp = responseWr.getCbDebitInstructionResponse();
		
			cb.setAddtInfo(debitResp.getAddtInfo());
			
			cb.setDebitAmount(new BigDecimal(debitReq.getAmount()));

			cb.setChannelRefId(debitReq.getTransactionId());
//			cb.setCreditorBank(debitReq.get);
//			cb.setCreditorBankAccount(null);
			cb.setCstmAccountName(debitReq.getDebtorName());
			cb.setCstmAccountNo(debitReq.getAccountNumber());
			cb.setCstmAccountType(debitReq.getAccountType());
			
			cb.setStatus(debitResp.getStatus());
			cb.setTransactionType("Debit");
			cb.setTrnsDt(LocalDateTime.now());
		}
		
		else {
			CBFITransferRequestPojo fiReq = (CBFITransferRequestPojo) objCbRequest;
			CBFITransferResponsePojo fiResp = responseWr.getCbFITransferResponse();
				
			cb.setAddtInfo(fiResp.getAddtInfo());
			
			cb.setDebitAmount(new BigDecimal(fiReq.getAmount()));

			cb.setChannelRefId(fiReq.getTransactionId());
			cb.setCreditorBank(fiReq.getCreditorBank());
			cb.setDebtorBank(fiReq.getDebtorBank());
			
//			cb.setCreditorBankAccount(fiReq.get);
//			cb.setCstmAccountName(debitReq.getDebtorName());
//			cb.setCstmAccountNo(debitReq.getAccountNumber());
//			cb.setCstmAccountType(debitReq.getAccountType());
		
			cb.setStatus(fiResp.getStatus());
			cb.setTransactionType("FI Transfer");
			cb.setTrnsDt(LocalDateTime.now());

		}

		cbTransactionRepo.save(cb);
	}

}
