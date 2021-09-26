package bifast.inbound.credittransfer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CBDebitInstructionRequestPojo;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs008.CreditTransferTransaction39;

@Component
public class CTCorebankRequestProcessor implements Processor {


	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage inboundMessage = exchange.getMessage().getBody(BusinessMessage.class);
		CBDebitInstructionRequestPojo cbRequest = new CBDebitInstructionRequestPojo();
		
		CreditTransferTransaction39 biReq = inboundMessage.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0);
		
		cbRequest.setTransactionId(inboundMessage.getAppHdr().getBizMsgIdr());
		cbRequest.setAccountNumber(biReq.getCdtrAcct().getId().getOthr().getId());
		cbRequest.setAccountType(biReq.getCdtrAcct().getTp().getPrtry());
		
		DecimalFormat df = new DecimalFormat("#############.00");
		BigDecimal amount = biReq.getIntrBkSttlmAmt().getValue();
		cbRequest.setAmount(df.format(amount));
		
		cbRequest.setDebtorName(biReq.getCdtr().getNm());
		
		if (!(null == biReq.getRmtInf()))
			cbRequest.setPaymentInfo(biReq.getRmtInf().getUstrd().get(0));
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		cbRequest.setRequestTime(LocalDateTime.now().format(dtf));
		
		exchange.getMessage().setBody(cbRequest);
	}

}
