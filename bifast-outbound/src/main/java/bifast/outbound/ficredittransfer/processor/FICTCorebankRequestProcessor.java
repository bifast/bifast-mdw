package bifast.outbound.ficredittransfer.processor;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.CBFITransferRequestPojo;
import bifast.outbound.ficredittransfer.ChnlFICreditTransferRequestPojo;

@Component
public class FICTCorebankRequestProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
		
		ChnlFICreditTransferRequestPojo chnlCTRequest = exchange.getIn().getHeader("hdr_channelRequest",ChnlFICreditTransferRequestPojo.class);

		CBFITransferRequestPojo cbFITransferRequest = new CBFITransferRequestPojo();

		DecimalFormat df = new DecimalFormat("#############.00");
		BigDecimal amount = chnlCTRequest.getAmount();
		cbFITransferRequest.setAmount(df.format(amount));

		cbFITransferRequest.setPaymentInfo(chnlCTRequest.getPaymentInfo());
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		cbFITransferRequest.setRequestTime(LocalDateTime.now().format(dtf));

		cbFITransferRequest.setTransactionId(chnlCTRequest.getIntrnRefId());
		
		exchange.getMessage().setBody(cbFITransferRequest);

	}

}
