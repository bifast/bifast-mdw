package bifast.outbound.corebank.processor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.DebitRequestDTO;
import bifast.outbound.model.CorebankTransaction;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.CorebankTransactionRepository;
import bifast.outbound.service.UtilService;

@Component
public class SaveCbDebitProcessor implements Processor{
	@Autowired private CorebankTransactionRepository cbTransactionRepo;
	@Autowired private UtilService utilService;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
		
		Object oCbRequest = exchange.getProperty("pr_cbrequest", Object.class);
//		String requestClassName = oCbRequest.getClass().getSimpleName();
		DebitRequestDTO debitReq = (DebitRequestDTO) oCbRequest;
		
		CorebankTransaction cb = new CorebankTransaction();

		cb.setKomiTrnsId(rmw.getKomiTrxId());	
		cb.setUpdateTime(LocalDateTime.now());
		
		String response = exchange.getProperty("pr_response", String.class);
		String reason = exchange.getProperty("pr_reason", String.class);
		cb.setResponse(response);
		cb.setReason(reason);

		DateTimeFormatter fdt = DateTimeFormatter.ofPattern("yyyyMMdd");
		cb.setTrnsDate(fdt.format(LocalDate.now()));

		cb.setFullTextRequest(utilService.serialize(debitReq));
		
		cb.setDateTime(debitReq.getDateTime());
		cb.setKomiNoref(debitReq.getNoRef());
		cb.setOrgnlChnlNoref(debitReq.getNoRef());
	
		cb.setDebitAmount(new BigDecimal(debitReq.getAmount()));
		cb.setFeeAmount(new BigDecimal(debitReq.getFeeTransfer()));
		
		cb.setCstmAccountName(debitReq.getDebtorName());
		cb.setCstmAccountNo(debitReq.getDebtorAccountNumber());
		cb.setCstmAccountType(debitReq.getDebtorAccountType());
		cb.setTransactionType("Debit");

		cbTransactionRepo.save(cb);
	}

}
