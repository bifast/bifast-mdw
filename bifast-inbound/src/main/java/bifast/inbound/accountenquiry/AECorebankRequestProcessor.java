package bifast.inbound.accountenquiry;

import java.text.DecimalFormat;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CBAccountEnquiryRequestPojo;
import bifast.inbound.service.UtilService;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs008.CreditTransferTransaction39;

@Component
public class AECorebankRequestProcessor implements Processor {

	@Autowired
	private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage inboundMessage = exchange.getMessage().getBody(BusinessMessage.class);
		CBAccountEnquiryRequestPojo cbRequest = new CBAccountEnquiryRequestPojo();
		
		DecimalFormat df = new DecimalFormat("00000000");
		String trxid = df.format(utilService.getInboundCounter());
		
		CreditTransferTransaction39 biReq = inboundMessage.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0);
		cbRequest.setAccountNumber(biReq.getCdtrAcct().getId().getOthr().getId());

		cbRequest.setAmount(biReq.getIntrBkSttlmAmt().getValue());
		cbRequest.setTransactionId("040502345");
		
		exchange.getMessage().setBody(cbRequest);
	}

}
