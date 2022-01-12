package bifast.inbound.reversecrdttrns;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.inbound.corebank.pojo.CbCreditRequestPojo;
import bifast.inbound.corebank.pojo2.DebitTransferRequest;
import bifast.inbound.model.ChannelTransaction;
import bifast.inbound.model.CorebankTransaction;
import bifast.inbound.model.CreditTransfer;
import bifast.inbound.pojo.FaultPojo;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.inbound.repository.ChannelTransactionRepository;
import bifast.inbound.repository.CorebankTransactionRepository;
import bifast.inbound.repository.CreditTransferRepository;
import bifast.inbound.reversecrdttrns.pojo.ChnlCreditTransferRequestPojo;
import bifast.inbound.reversecrdttrns.pojo.DebitReversalRequestPojo;
import bifast.inbound.service.TransRef;

@Component
public class ReverseCTProcessor implements Processor {
	@Autowired ChannelTransactionRepository channelTrnsRepo;
	@Autowired CorebankTransactionRepository cbRepo;
	@Autowired CreditTransferRepository ctRepo;
	
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Value ("${komi.isoadapter.merchant}")
    String merchant;
    @Value ("${komi.isoadapter.txid}")
    String transactionId;
    
	@Override
	public void process(Exchange exchange) throws Exception {
		
		ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
		FlatPacs008Pojo flatCT = (FlatPacs008Pojo)processData.getBiRequestFlat();
	
		List<CreditTransfer> lOrgnlCT = ctRepo.findAllByCrdtTrnRequestBizMsgIdr(flatCT.getTransactionId());
		CreditTransfer orgnlCrdtTrns = null;
		for (CreditTransfer ct : lOrgnlCT) {
			if ((ct.getResponseCode().equals("ACTC")) ||
				(ct.getResponseCode().equals("ACSC"))) {
				orgnlCrdtTrns = ct;
				break;
			}
		}

		String orgnlDateTime = "";
		String orgnlNoRef = "";

		CbCreditRequestPojo orignlDebitRequest = null;
		if (null != orgnlCrdtTrns) {
//			Optional<ChannelTransaction> oChnlTrns = channelTrnsRepo.findByKomiTrnsId(orgnlCrdtTrns.getKomiTrnsId());
//			if (oChnlTrns.isPresent()) {
//				strOrignlChannelRequest = oChnlTrns.get().getTextMessage();
//			
//		    	ObjectMapper map = new ObjectMapper();
//		    	map.registerModule(new JaxbAnnotationModule());
//		    	map.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
//		    	map.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//		    	orignlChannelRequest = map.readValue(strOrignlChannelRequest, ChnlCreditTransferRequestPojo.class);
//			}
			Optional<CorebankTransaction>  oCbTrns = cbRepo.findByTransactionTypeAndKomiTrnsId("Debit", orgnlCrdtTrns.getKomiTrnsId());
			if (oCbTrns.isPresent()) {
				String strOrignlDebitRequest = oCbTrns.get().getFullTextRequest();
				System.out.println(strOrignlDebitRequest);
		    	ObjectMapper map = new ObjectMapper();
		    	map.registerModule(new JaxbAnnotationModule());
//		    	map.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
		    	map.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		    	orignlDebitRequest = map.readValue(strOrignlDebitRequest, CbCreditRequestPojo.class);
		    	
		    	orgnlDateTime= oCbTrns.get().getDateTime();
		    	orgnlNoRef = oCbTrns.get().getOrgnlChnlNoref();
			}
		}
	
		
		if (null == orignlDebitRequest) {
			System.out.println("Tidak nemu credit Transfer asal");
			FaultPojo fault = new FaultPojo();
			fault.setCallStatus("ERROR");
			fault.setErrorMessage("Original Payment Not Found");
			fault.setReasonCode("U106");
			fault.setResponseCode("OTHR");
			exchange.getMessage().setBody(fault);
		}
		
		else {
			
			// siapkan untuk debit reversal
			DebitReversalRequestPojo reversalReq = new DebitReversalRequestPojo();

			reversalReq.setAmount(orignlDebitRequest.getAmount());
			reversalReq.setCategoryPurpose(orignlDebitRequest.getCategoryPurpose());
			reversalReq.setCreditorAccountNumber(orignlDebitRequest.getCreditorAccountNumber());
			reversalReq.setCreditorAccountType(orignlDebitRequest.getCreditorAccountType());
			reversalReq.setCreditorId(orignlDebitRequest.getCreditorId());
			reversalReq.setCreditorName(orignlDebitRequest.getCreditorName());
			reversalReq.setCreditorProxyId(orignlDebitRequest.getCreditorProxyId());
			reversalReq.setCreditorProxyType(orignlDebitRequest.getCreditorProxyType());
			reversalReq.setCreditorResidentStatus(orignlDebitRequest.getCreditorResidentStatus());
			reversalReq.setCreditorTownName(orignlDebitRequest.getCreditorTownName());
			reversalReq.setCreditorType(orignlDebitRequest.getCreditorType());
			
			reversalReq.setDebtorAccountNumber(orignlDebitRequest.getDebtorAccountNumber());
			reversalReq.setDebtorAccountType(orignlDebitRequest.getDebtorAccountType());
			reversalReq.setDebtorId(orignlDebitRequest.getDebtorId());
			reversalReq.setDebtorName(orignlDebitRequest.getDebtorName());
			reversalReq.setDebtorResidentStatus(orignlDebitRequest.getDebtorResidentStatus());
			reversalReq.setDebtorTownName(orignlDebitRequest.getDebtorTownName());
			reversalReq.setDebtorType(orignlDebitRequest.getDebtorType());
			reversalReq.setFeeTransfer(orignlDebitRequest.getFeeTransfer());
			
			reversalReq.setMerchantType(merchant);
			
			TransRef.Ref ref = TransRef.newRef();
			reversalReq.setNoRef(ref.getNoRef());
			
//			String orgnlDateTime = orignlDebitRequest.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
			reversalReq.setOriginalDateTime(orgnlDateTime);
			reversalReq.setDateTime(ref.getDateTime());
			
			reversalReq.setOriginalNoRef(orgnlNoRef);

			reversalReq.setPaymentInformation(orignlDebitRequest.getPaymentInformation());
			reversalReq.setRecipientBank(orignlDebitRequest.getRecipientBank());
			reversalReq.setTerminalId(orignlDebitRequest.getTerminalId());
			
			reversalReq.setTransactionId(transactionId);
			
			exchange.getMessage().setBody(reversalReq);
			
		}
			

	}

}
