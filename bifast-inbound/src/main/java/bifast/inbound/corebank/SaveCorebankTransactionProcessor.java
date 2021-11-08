package bifast.inbound.corebank;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CbAccountEnquiryResponsePojo;
import bifast.inbound.model.CorebankTransaction;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.repository.CorebankTransactionRepository;

@Component
public class SaveCorebankTransactionProcessor implements Processor {
	@Autowired private CorebankTransactionRepository cbTrnsRepo;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		String msgType= exchange.getMessage().getHeader("cb_requestName", String.class);
		ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
		
		CorebankTransaction ct = new CorebankTransaction();
		ct.setKomiTrnsId(processData.getKomiTrnsId());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		ct.setTrnsDate(LocalDate.now().format(formatter));
		ct.setUpdateTime(LocalDateTime.now());
		
		if (msgType.equals("accountinquiry")) {
			CbAccountEnquiryResponsePojo cbAEResponse = (CbAccountEnquiryResponsePojo) processData.getCorebankResponse();
			
			ct.setCstmAccountNo(cbAEResponse.getAccountNumber());
			ct.setCstmAccountType(cbAEResponse.getAccountType());
			ct.setKomiNoref(cbAEResponse.getNoRef());
			ct.setResponse(cbAEResponse.getStatus());
			ct.setReason(cbAEResponse.getReason());
			ct.setTransactionType("AccountEnquiry");
			
		}
		

		cbTrnsRepo.save(ct);
		
	}

}
