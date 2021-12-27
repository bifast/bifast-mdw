package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.DebitReversalRequestPojo;
import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.pojo.RequestMessageWrapper;

@Component
public class DebitReversalRequestProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		ChnlCreditTransferRequestPojo chnReq = rmw.getChnlCreditTransferRequest();
		
		DebitReversalRequestPojo reversalReq = new DebitReversalRequestPojo();
		
		reversalReq.setAmount(chnReq.getAmount());
		reversalReq.setCategoryPurpose(chnReq.getCategoryPurpose());
		reversalReq.setCreditorAccountNumber(chnReq.getCrdtAccountNo());
		reversalReq.setCreditorAccountType(chnReq.getCrdtAccountType());
		reversalReq.setCreditorId(chnReq.getCrdtId());
		reversalReq.setCreditorName(chnReq.getCrdtName());
		reversalReq.setCreditorProxyId(chnReq.getCrdtProxyIdValue());
		reversalReq.setCreditorProxyType(chnReq.getCrdtProxyIdType());
		reversalReq.setCreditorResidentStatus(chnReq.getCrdtResidentialStatus());
		reversalReq.setCreditorTownName(chnReq.getCrdtTownName());
		reversalReq.setCreditorType(chnReq.getCrdtType());
		
		reversalReq.setDateTime(null);
		reversalReq.setDebtorAccountNumber(chnReq.getDbtrAccountNo());
		reversalReq.setDebtorAccountType(chnReq.getDbtrAccountType());
		reversalReq.setDebtorId(chnReq.getDbtrId());
		reversalReq.setDebtorName(chnReq.getDbtrName());
		reversalReq.setDebtorResidentStatus(chnReq.getDbtrResidentialStatus());
		reversalReq.setDebtorTownName(chnReq.getDbtrTownName());
		reversalReq.setDebtorType(chnReq.getDbtrType());
		reversalReq.setFeeTransfer(chnReq.getFeeTransfer());
		
		reversalReq.setMerchantType(rmw.getMerchantType());
		reversalReq.setNoRef(chnReq.getChannelRefId()e);
		reversalReq.setOriginalDateTime(null);
		
		reversalReq.setOriginalNoRef(null);
		reversalReq.setPaymentInformation(chnReq.getPaymentInfo());
		reversalReq.setRecipientBank(chnReq.getRecptBank());
		reversalReq.setTerminalId(chnReq.getTerminalId());
		reversalReq.setTransactionId();
		
		exchange.getMessage().setBody(reversalReq);

	}

}
