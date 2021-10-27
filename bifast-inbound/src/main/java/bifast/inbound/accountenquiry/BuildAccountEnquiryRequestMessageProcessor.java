package bifast.inbound.accountenquiry;

import java.text.DecimalFormat;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CbAccountEnquiryRequestPojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.inbound.service.UtilService;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs008.CreditTransferTransaction39;
import bifast.library.iso20022.pacs008.FIToFICustomerCreditTransferV08;

@Component
public class BuildAccountEnquiryRequestMessageProcessor implements Processor {

	@Autowired
	private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {

//		BusinessMessage inboundMessage = exchange.getMessage().getBody(BusinessMessage.class);
		FlatPacs008Pojo biReq = exchange.getMessage().getBody(FlatPacs008Pojo.class);
//		CreditTransferTransaction39 biReq = inboundMessage.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0);
		
		CbAccountEnquiryRequestPojo cbRequest = new CbAccountEnquiryRequestPojo();
		
		cbRequest.setAccountNumber(biReq.getCreditorAccountNo());
		cbRequest.setAmount(biReq.getAmount());
		
		cbRequest.setCategoryPurpose(biReq.getCategoryPurpose());

		cbRequest.setDateTime(biReq.getCreDtTm());
		
		cbRequest.setMerchantType("0000");
		
		cbRequest.setNoRef(null);
		
		cbRequest.setRecipientBank(biReq.getToBic());
		
		cbRequest.setTerminalId("000000");
		cbRequest.setTransactionId("000000");
		
		exchange.getMessage().setBody(cbRequest);
	}

}
