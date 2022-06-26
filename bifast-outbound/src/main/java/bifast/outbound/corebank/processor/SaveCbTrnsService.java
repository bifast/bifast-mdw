package bifast.outbound.corebank.processor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import bifast.outbound.corebank.pojo.DebitReversalRequestPojo;
import bifast.outbound.corebank.pojo.DebitReversalResponsePojo;
import bifast.outbound.model.CorebankTransaction;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.CorebankTransactionRepository;
import bifast.outbound.service.UtilService;

@Component
public class SaveCbTrnsService {
	@Autowired private CorebankTransactionRepository cbRepo;
	@Autowired private UtilService utilService;

	void debitReversal (Exchange exchange) throws Exception {
		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
		String komiTrnsId = rmw.getKomiTrxId();
		DebitReversalRequestPojo cbRequest = rmw.getDebitReversalRequest();

		CorebankTransaction cbTrns = new CorebankTransaction();
		cbTrns.setCreditAmount(new BigDecimal(cbRequest.getAmount()));
		cbTrns.setCstmAccountName(cbRequest.getDebtorName());
		cbTrns.setCstmAccountNo(cbRequest.getDebtorAccountNumber());
		cbTrns.setCstmAccountType(cbRequest.getDebtorAccountType());
		cbTrns.setDateTime(cbRequest.getDateTime());
		cbTrns.setFeeAmount(new BigDecimal(cbRequest.getFeeTransfer()));
		cbTrns.setFullTextRequest(utilService.serialize(cbRequest));
		cbTrns.setKomiNoref(cbRequest.getNoRef());
		cbTrns.setKomiTrnsId(komiTrnsId);

		cbTrns.setOrgnlChnlNoref(cbRequest.getOriginalNoRef());
		cbTrns.setOrgnlDateTime(cbRequest.getOriginalDateTime());
		cbTrns.setTransactionType("DebitReversal");
		cbTrns.setTrnsDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		cbTrns.setUpdateTime(LocalDateTime.now());
		
		DebitReversalResponsePojo resp = exchange.getMessage().getBody(DebitReversalResponsePojo.class);
		cbTrns.setReason(resp.getReason());
		cbTrns.setResponse(resp.getStatus());

		cbRepo.save(cbTrns);

	}

}
