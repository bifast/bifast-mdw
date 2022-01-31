package bifast.inbound.reversecrdttrns;

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
import bifast.inbound.model.CorebankTransaction;
import bifast.inbound.model.CreditTransfer;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.inbound.repository.ChannelTransactionRepository;
import bifast.inbound.repository.CorebankTransactionRepository;
import bifast.inbound.repository.CreditTransferRepository;
import bifast.inbound.reversecrdttrns.pojo.DebitReversalRequestPojo;

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
		
		ProcessDataPojo processData = exchange.getProperty("prop_process_data", ProcessDataPojo.class);
		FlatPacs008Pojo flatCT = (FlatPacs008Pojo)processData.getBiRequestFlat();
	
		System.out.println(flatCT.getTransactionId());
		
		List<CreditTransfer> lOrgnlCT = ctRepo.findAllByCrdtTrnRequestBizMsgIdr(flatCT.getTransactionId());
		System.out.println("Nemu: " + lOrgnlCT.size());
		CreditTransfer orgnlCrdtTrns = null;
		for (CreditTransfer ct : lOrgnlCT) {
			System.out.println(ct.getResponseCode());
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
	

	
		else {
			
			// siapkan untuk debit reversal
			DebitReversalRequestPojo reversalReq = new DebitReversalRequestPojo();


			
			reversalReq.setMerchantType(merchant);
			

			
		}
			

	}

}
