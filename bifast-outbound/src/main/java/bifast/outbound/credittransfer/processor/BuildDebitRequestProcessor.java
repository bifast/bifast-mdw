package bifast.outbound.credittransfer.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.DebitRequestDTO;
import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.service.RefUtils;

@Component
public class BuildDebitRequestProcessor implements Processor{
//	@Autowired private Config config;
	
	@Value("${komi.isoadapter.txid}")
	String txid;

	@Override
	public void process(Exchange exchange) throws Exception {
		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
		ChnlCreditTransferRequestPojo chnReq = rmw.getChnlCreditTransferRequest();
		
		DebitRequestDTO debitReq = new DebitRequestDTO();	

//		debitReq.setKomiTrnsId(rmw.getKomiTrxId());
		debitReq.setTransactionId(txid);
		debitReq.setNoRef(rmw.getRequestId());
		debitReq.setMerchantType(rmw.getMerchantType());
		debitReq.setTerminalId(chnReq.getTerminalId());
		
		RefUtils.Ref ref = RefUtils.setRef(chnReq.getChannelRefId());

		debitReq.setDateTime(ref.getDateTime());
		
		debitReq.setOriginalDateTime(ref.getDateTime());
		debitReq.setOriginalNoRef(ref.getNoRef());
		
		debitReq.setCategoryPurpose(chnReq.getCategoryPurpose());
		
		
		debitReq.setDebtorName(chnReq.getDbtrName());
		debitReq.setDebtorType(chnReq.getDbtrType());
		debitReq.setDebtorId(chnReq.getDbtrId());
		debitReq.setDebtorAccountNumber(chnReq.getDbtrAccountNo());	
		debitReq.setDebtorAccountType(chnReq.getDbtrAccountType());
		debitReq.setDebtorResidentStatus(chnReq.getDbtrResidentialStatus());
		debitReq.setDebtorTownName(chnReq.getDbtrTownName());
		
		debitReq.setAmount(chnReq.getAmount());
		debitReq.setFeeTransfer(chnReq.getFeeTransfer());

//		debitReq.setRecipientBank(config.getBankcode()); 
		debitReq.setRecipientBank(chnReq.getRecptBank()); 
		
		debitReq.setCreditorName(chnReq.getCrdtName());
		debitReq.setCreditorType(chnReq.getCrdtType());
		debitReq.setCreditorId(chnReq.getCrdtId());
		debitReq.setCreditorAccountNumber(chnReq.getCrdtAccountNo());
		debitReq.setCreditorAccountType(chnReq.getCrdtAccountType());
		debitReq.setCreditorResidentStatus(chnReq.getCrdtResidentialStatus());
		debitReq.setCreditorTownName(chnReq.getCrdtTownName());

		debitReq.setCreditorProxyId(chnReq.getCrdtProxyIdValue());
		debitReq.setCreditorProxyType(chnReq.getCrdtProxyIdType());

		debitReq.setPaymentInformation(chnReq.getPaymentInfo());
		
		rmw.setDebitAccountRequest(debitReq);
		
		exchange.setProperty("prop_request_list", rmw);
		exchange.getMessage().setBody(debitReq);
	}

}
