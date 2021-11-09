package bifast.inbound.credittransfer;

import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CbCreditRequestPojo;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;

@Component
public class CTCorebankRequestProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
		FlatPacs008Pojo biReq = (FlatPacs008Pojo) processData.getBiRequestFlat();
		
		CbCreditRequestPojo cbRequest = new CbCreditRequestPojo();
	
		cbRequest.setTransactionId(biReq.getBizMsgIdr());
		cbRequest.setCreditorAccountNumber(biReq.getCreditorAccountNo());
		cbRequest.setCreditorAccountType(biReq.getCreditorAccountType());
		
//		DecimalFormat df = new DecimalFormat("#############.00");
		cbRequest.setAmount(biReq.getAmount());
		
		cbRequest.setDebtorName(biReq.getDebtorName());
		
		if (!(null == biReq.getPaymentInfo()))
			cbRequest.setPaymentInformation(biReq.getPaymentInfo());
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		cbRequest.setRequestTime(LocalDateTime.now().format(dtf));
		
		exchange.getMessage().setBody(cbRequest);
	}

}
