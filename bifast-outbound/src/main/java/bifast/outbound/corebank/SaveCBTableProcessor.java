package bifast.outbound.corebank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.CbDebitRequestPojo;
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

		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		
		Object oCbRequest = rmw.getCorebankRequest();
		String requestClassName = oCbRequest.getClass().getSimpleName();
		
		Object oCbResponse = exchange.getMessage().getBody(Object.class);

		CorebankTransaction cb = new CorebankTransaction();

		cb.setChannelId(rmw.getChannelId());
		cb.setChannelNoref(rmw.getRequestId());

//		cb.setAddtInfo(debitResp.getAddtInfo());		
//		cb.setStatus(debitResp.getStatus());
		cb.setTrnsDt(LocalDateTime.now());
		
		cb.setKomiTrnsId(rmw.getKomiTrxId());

		if (requestClassName.equals("CBDebitRequestPojo")) {
			CbDebitRequestPojo debitReq = (CbDebitRequestPojo) oCbRequest;
			
			cb.setTrxDate(debitReq.getOriginalDateTime());
			
			cb.setDebitAmount(new BigDecimal(debitReq.getAmount()));
			cb.setCstmAccountName(debitReq.getDebtorName());
			cb.setCstmAccountNo(debitReq.getDebtorAccountNumber());
			cb.setCstmAccountType(debitReq.getDebtorAccountType());
			cb.setTransactionType("Debit");

		}
		
		else {

		}

		cbTransactionRepo.save(cb);
	}

}
