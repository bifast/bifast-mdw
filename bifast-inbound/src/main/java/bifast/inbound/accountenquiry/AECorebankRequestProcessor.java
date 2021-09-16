package bifast.inbound.accountenquiry;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.CBAccountEnquiryRequestPojo;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs008.CreditTransferTransaction39;

@Component
public class AECorebankRequestProcessor implements Processor {


	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage inboundMessage = exchange.getMessage().getBody(BusinessMessage.class);
		CBAccountEnquiryRequestPojo cbRequest = new CBAccountEnquiryRequestPojo();
		
		CreditTransferTransaction39 biReq = inboundMessage.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0);
		cbRequest.setAccountNumber(biReq.getCdtrAcct().getId().getOthr().getId());

		cbRequest.setAmount(biReq.getIntrBkSttlmAmt().getValue());
		cbRequest.setTransactionId(null);
		
		exchange.getMessage().setBody(cbRequest);
	}

}
