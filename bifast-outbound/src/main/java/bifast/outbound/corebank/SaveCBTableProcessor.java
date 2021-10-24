package bifast.outbound.corebank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.CBDebitInstructionRequestPojo;
import bifast.outbound.corebank.pojo.CBDebitInstructionResponsePojo;
import bifast.outbound.model.CorebankTransaction;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.repository.CorebankTransactionRepository;

@Component
public class SaveCBTableProcessor implements Processor{

	@Autowired
	private CorebankTransactionRepository cbTransactionRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		Object objCbRequest = exchange.getMessage().getHeader("cb_request", Object.class);
		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		
		String requestClassName = objCbRequest.getClass().getSimpleName();
		
		CBDebitInstructionResponsePojo debitResp = exchange.getMessage().getBody(CBDebitInstructionResponsePojo.class);

		CorebankTransaction cb = new CorebankTransaction();

		cb.setAddtInfo(debitResp.getAddtInfo());		
		cb.setStatus(debitResp.getStatus());
		cb.setTrnsDt(LocalDateTime.now());
		
		cb.setKomiTrnsId(rmw.getKomiTrxId());

		if (requestClassName.equals("CBDebitInstructionRequestPojo")) {
			CBDebitInstructionRequestPojo debitReq = (CBDebitInstructionRequestPojo) objCbRequest;

			cb.setDebitAmount(new BigDecimal(debitReq.getAmount()));
			cb.setChannelId(rmw.getChannelId());
			cb.setCstmAccountName(debitReq.getDebtorName());
			cb.setCstmAccountNo(debitReq.getAccountNumber());
			cb.setCstmAccountType(debitReq.getAccountType());
			cb.setTransactionType("Debit");

		}
		
		else {

		}

		cbTransactionRepo.save(cb);
	}

}
