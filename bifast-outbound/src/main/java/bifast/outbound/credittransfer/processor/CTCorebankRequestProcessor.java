package bifast.outbound.credittransfer.processor;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.CBDebitRequestPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlCreditTransferRequestPojo;

@Component
public class CTCorebankRequestProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		ChnlCreditTransferRequestPojo chnReq = rmw.getChnlCreditTransferRequest();


		CBDebitRequestPojo debitRequest = new CBDebitRequestPojo();

		debitRequest.setDebtorAccountNumber(chnReq.getDbtrAccountNo());
		
		
		DecimalFormat df = new DecimalFormat("#############.00");
//		BigDecimal amount = new BigDecimal(chnlCTRequest.getAmount());
//		cbDebitRequest.setAmount(df.format(amount));
//
////		cbDebitRequest.setDebtorName(chnlCTRequest.getDbtrName());
//		
//		cbDebitRequest.setPaymentInfo(chnlCTRequest.getPaymentInfo());
//		
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		cbDebitRequest.setRequestTime(LocalDateTime.now().format(dtf));
//
//		cbDebitRequest.setTransactionId(chnlCTRequest.getChannelRefId());
//		
//		exchange.getMessage().setBody(cbDebitRequest);

	}

}
