package bifast.outbound.corebank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import bifast.outbound.corebank.pojo.CbAccountCustomerInfoRequestPojo;
import bifast.outbound.corebank.pojo.CbAccountCustomerInfoResponsePojo;
import bifast.outbound.corebank.pojo.CbDebitRequestPojo;
import bifast.outbound.corebank.pojo.CbDebitResponsePojo;
import bifast.outbound.corebank.pojo.CbDebitReversalPojo;
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

		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		
		Object oCbRequest = rmw.getCorebankRequest();
		Object oCbResponse = exchange.getMessage().getBody();
		
		String requestClassName = oCbRequest.getClass().getSimpleName();
		
		CorebankTransaction cb = new CorebankTransaction();

		cb.setKomiTrnsId(rmw.getKomiTrxId());	
		cb.setUpdateTime(LocalDateTime.now());
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.setSerializationInclusion(Include.NON_NULL);
	    
		if (requestClassName.equals("CbDebitRequestPojo")) {

			CbDebitRequestPojo debitReq = (CbDebitRequestPojo) oCbRequest;

			String textRequest = mapper.writeValueAsString(debitReq);
			cb.setFullTextRequest(textRequest);
			
			cb.setOrgnlChnlNoref(debitReq.getOriginalNoRef());
			cb.setOrgnlDateTime(debitReq.getOriginalDateTime());
			
			cb.setDateTime(debitReq.getDateTime());
			cb.setKomiNoref(debitReq.getKomiNoRef());
			cb.setTrnsDate("20" + debitReq.getKomiNoRef().substring(3, 9));
		
			cb.setDebitAmount(new BigDecimal(debitReq.getAmount()));
			cb.setFeeAmount(new BigDecimal(debitReq.getFeeTransfer()));
			
			cb.setCstmAccountName(debitReq.getDebtorName());
			cb.setCstmAccountNo(debitReq.getDebtorAccountNumber());
			cb.setCstmAccountType(debitReq.getDebtorAccountType());
			cb.setTransactionType("DebitAccount");
			
			if (oCbResponse.getClass().getSimpleName().equals("CbDebitResponsePojo")) {
				CbDebitResponsePojo debitResp = (CbDebitResponsePojo) oCbResponse;
				cb.setResponse(debitResp.getStatus());
				cb.setReason(debitResp.getReason());
			}
			else {
				FaultPojo fault = (FaultPojo) oCbResponse;
				cb.setResponse(fault.getResponseCode());
				cb.setReason(fault.getReasonCode());
			}

		}
		
		else if (requestClassName.equals("CbAccountCustomerInfoRequestPojo")) {
			CbAccountCustomerInfoRequestPojo aciReq = (CbAccountCustomerInfoRequestPojo) oCbRequest;

			cb.setOrgnlChnlNoref(aciReq.getOriginalNoRef());
			cb.setOrgnlDateTime(aciReq.getOriginalDateTime());
			
			cb.setDateTime(aciReq.getDateTime());
			cb.setKomiNoref(aciReq.getKomiNoRef());
			cb.setTrnsDate("20" + aciReq.getKomiNoRef().substring(3, 9));
				
			cb.setCstmAccountNo(aciReq.getAccountNumber());
			cb.setTransactionType("AccountCustomerInfo");
			
			if (oCbResponse.getClass().getSimpleName().equals("CbAccountCustomerInfoResponsePojo")) {
				CbAccountCustomerInfoResponsePojo aciResponse = (CbAccountCustomerInfoResponsePojo) oCbResponse;
				cb.setCstmAccountName(aciResponse.getCustomerName());
				cb.setCstmAccountType(aciResponse.getAccountType());
				cb.setResponse(aciResponse.getStatus());
				cb.setReason(aciResponse.getReason());
			}
			else {
				FaultPojo fault = (FaultPojo) oCbResponse;
				cb.setResponse(fault.getResponseCode());
				cb.setReason(fault.getReasonCode());
			}
		}

		else if (requestClassName.equals("CbDebitReversalPojo")) {

			CbDebitReversalPojo debitReq = (CbDebitReversalPojo) oCbRequest;

			cb.setOrgnlChnlNoref(debitReq.getOriginalNoRef());
			cb.setOrgnlDateTime(debitReq.getOriginalDateTime());
			
			cb.setDateTime(debitReq.getDateTime());
			cb.setKomiNoref(debitReq.getKomiNoRef());
			cb.setTrnsDate("20" + debitReq.getKomiNoRef().substring(3, 9));
		
			cb.setCreditAmount(new BigDecimal(debitReq.getAmount()));
			cb.setFeeAmount(new BigDecimal(debitReq.getFeeTransfer()));
			
			cb.setCstmAccountName(debitReq.getDebtorName());
			cb.setCstmAccountNo(debitReq.getDebtorAccountNumber());
			cb.setCstmAccountType(debitReq.getDebtorAccountType());
			cb.setTransactionType("DebitReversal");
			
			if (oCbResponse.getClass().getSimpleName().equals("CbDebitResponsePojo")) {
				CbDebitResponsePojo debitResp = (CbDebitResponsePojo) oCbResponse;
				cb.setResponse(debitResp.getStatus());
				cb.setReason(debitResp.getReason());
			}
			else {
				FaultPojo fault = (FaultPojo) oCbResponse;
				cb.setResponse(fault.getResponseCode());
				cb.setReason(fault.getReasonCode());
			}
		}
		
		cbTransactionRepo.save(cb);
	}

}
