package bifast.outbound.corebank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import bifast.outbound.corebank.pojo.CbDebitRequestPojo;
import bifast.outbound.corebank.pojo.CbDebitResponsePojo;
import bifast.outbound.corebank.pojo.DebitRequestDTO;
import bifast.outbound.model.CorebankTransaction;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.CorebankTransactionRepository;

@Component
public class SaveCBTableProcessor implements Processor{

	@Autowired
	private CorebankTransactionRepository cbTransactionRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
		
		Object oCbRequest = exchange.getProperty("pr_cbrequest", Object.class);
		
		String requestClassName = oCbRequest.getClass().getSimpleName();
		
		CorebankTransaction cb = new CorebankTransaction();

		cb.setKomiTrnsId(rmw.getKomiTrxId());	
		cb.setUpdateTime(LocalDateTime.now());
		
	
		String response = exchange.getProperty("pr_response", String.class);
		String reason = exchange.getProperty("pr_reason", String.class);
		cb.setResponse(response);
		cb.setReason(reason);

		ObjectMapper mapper = new ObjectMapper();
	    mapper.setSerializationInclusion(Include.NON_NULL);
	    
		if (requestClassName.equals("DebitRequestDTO")) {

			DebitRequestDTO debitReq = (DebitRequestDTO) oCbRequest;
			
			String textRequest = mapper.writeValueAsString(debitReq);
			cb.setFullTextRequest(textRequest);
			
//			cb.setOrgnlChnlNoref(debitReq.getOriginalNoRef());
//			cb.setOrgnlDateTime(debitReq.getOriginalDateTime());
//			
			cb.setDateTime(debitReq.getDateTime());
			cb.setKomiNoref(debitReq.getNoRef());
			
			DateTimeFormatter fdt = DateTimeFormatter.ofPattern("yyyyMMdd");
			cb.setTrnsDate(fdt.format(LocalDate.now()));
		
			cb.setDebitAmount(new BigDecimal(debitReq.getAmount()));
			cb.setFeeAmount(new BigDecimal(debitReq.getFeeTransfer()));
			
			cb.setCstmAccountName(debitReq.getDebtorName());
			cb.setCstmAccountNo(debitReq.getDebtorAccountNumber());
			cb.setCstmAccountType(debitReq.getDebtorAccountType());
			cb.setTransactionType("Debit");

		}

		
		cbTransactionRepo.save(cb);
	}

}
