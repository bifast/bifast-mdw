package bifast.inbound.reversecrdttrns.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.isopojo.AccountCustInfoRequest;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.inbound.service.RefUtils;

@Component
public class CbAcctCustInfoRequestProcessor implements Processor{
	
	@Value("${komi.isoadapter.merchant}")
	String merchant;
	@Value("${komi.isoadapter.terminal}")
	String terminal;
	@Value("${komi.isoadapter.txid}")
	String trxId;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		AccountCustInfoRequest aciRequest = new AccountCustInfoRequest();
//		ProcessDataPojo processData = exchange.getProperty("prop_process_data", ProcessDataPojo.class);		
//		FlatPacs008Pojo revRequest = (FlatPacs008Pojo) processData.getBiRequestFlat();
		FlatPacs008Pojo revRequest = exchange.getProperty("flatRequest", FlatPacs008Pojo.class);
		
		aciRequest.setAccountNumber(revRequest.getCreditorAccountNo());
		aciRequest.setDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
		aciRequest.setMerchantType("01");

		aciRequest.setNoRef("KOM" + RefUtils.genKomiTrnsId());
		aciRequest.setTerminalId(terminal);
		aciRequest.setTransactionId(trxId);
		
		exchange.getMessage().setBody(aciRequest);
		
	}

}
