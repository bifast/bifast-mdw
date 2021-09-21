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
		String requestClassName = objCbRequest.getClass().getSimpleName();
		CBInstructionWrapper responseWr = exchange.getMessage().getBody(CBInstructionWrapper.class);

		CBDebitInstructionResponsePojo debitResp = responseWr.getCbDebitInstructionResponse();

		CorebankTransaction cb = new CorebankTransaction();

		cb.setAddtInfo(debitResp.getAddtInfo());		
		cb.setStatus(debitResp.getStatus());
		cb.setTrnsDt(LocalDateTime.now());
		
		if (requestClassName.equals("CBDebitInstructionRequestPojo")) {
			CBDebitInstructionRequestPojo debitReq = (CBDebitInstructionRequestPojo) objCbRequest;

			cb.setDebitAmount(new BigDecimal(debitReq.getAmount()));
			cb.setChannelRefId(debitReq.getTransactionId());
			cb.setCstmAccountName(debitReq.getDebtorName());
			cb.setCstmAccountNo(debitReq.getAccountNumber());
			cb.setCstmAccountType(debitReq.getAccountType());
			cb.setTransactionType("Debit");

		}
		
		else {
			CBFITransferRequestPojo fiReq = (CBFITransferRequestPojo) objCbRequest;
			
			cb.setDebitAmount(new BigDecimal(fiReq.getAmount()));
			cb.setChannelRefId(fiReq.getTransactionId());
			cb.setCreditorBank(fiReq.getCreditorBank());
			cb.setDebtorBank(fiReq.getDebtorBank());
			cb.setTransactionType("FI Transfer");
		}

		cbTransactionRepo.save(cb);
	}

}
