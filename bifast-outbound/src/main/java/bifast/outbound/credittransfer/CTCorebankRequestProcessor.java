package bifast.outbound.credittransfer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.CBDebitInstructionRequestPojo;

@Component
public class CTCorebankRequestProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
		
//		ChnlCreditTransferRequestPojo chnlCTRequest = exchange.getMessage().getBody(ChnlCreditTransferRequestPojo.class);
		ChnlCreditTransferRequestPojo chnlCTRequest = exchange.getIn().getHeader("hdr_channelRequest",ChnlCreditTransferRequestPojo.class);

		CBDebitInstructionRequestPojo cbDebitRequest = new CBDebitInstructionRequestPojo();

		cbDebitRequest.setAccountNumber(chnlCTRequest.getDbtrAccountNo());
		cbDebitRequest.setAccountType(chnlCTRequest.getDbtrAccountType());
		
		DecimalFormat df = new DecimalFormat("#############.00");
		BigDecimal amount = chnlCTRequest.getAmount();
		cbDebitRequest.setAmount(df.format(amount));

		cbDebitRequest.setDebtorName(chnlCTRequest.getDbtrName());
		
		cbDebitRequest.setPaymentInfo(chnlCTRequest.getPaymentInfo());
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		cbDebitRequest.setRequestTime(LocalDateTime.now().format(dtf));

		cbDebitRequest.setTransactionId(chnlCTRequest.getOrignReffId());
		
		exchange.getMessage().setBody(cbDebitRequest);

	}

}
