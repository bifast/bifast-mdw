package bifast.inbound.reversecrdttrns.processor;

import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.inbound.model.CreditTransfer;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;

@Component
public class SaveReversalCTProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
//		ProcessDataPojo processData = exchange.getProperty("prop_process_data", ProcessDataPojo.class);
//		FlatPacs008Pojo flatReq = (FlatPacs008Pojo)processData.getBiRequestFlat();
		FlatPacs008Pojo flatReq = exchange.getProperty("flatRequest", FlatPacs008Pojo.class);

		CreditTransfer ct = new CreditTransfer();
		ct.setKomiTrnsId(exchange.getProperty("pr_komitrnsid", String.class));

		ct.setAmount(flatReq.getAmount());
		
		LocalDateTime start = exchange.getProperty("starttime", LocalDateTime.class);
		Duration duration = Duration.between(start, LocalDateTime.now());
		ct.setCihubElapsedTime(duration.toMillis());
		
		ct.setCihubRequestDT(LocalDateTime.now());
		
		
		ct.setCpyDplct(flatReq.getCpyDplct());
		ct.setCrdtTrnRequestBizMsgIdr(flatReq.getEndToEndId());
		
		ct.setCrdtTrnResponseBizMsgIdr(null);
		ct.setCreateDt(LocalDateTime.now());
		
		ct.setCreditorAccountNumber(flatReq.getCreditorAccountNo());
		ct.setCreditorAccountType(flatReq.getCreditorAccountType());
		ct.setCreditorId(flatReq.getCreditorId());
		ct.setCreditorType(flatReq.getCreditorType());

		ct.setDebtorAccountNumber(flatReq.getDebtorAccountNo());
		ct.setDebtorAccountType(flatReq.getDebtorAccountType());
		ct.setDebtorId(flatReq.getDebtorId());
		ct.setDebtorType(flatReq.getDebtorType());
		ct.setErrorMessage(null);
		ct.setFullRequestMessage(null);
		ct.setFullResponseMsg(null);
		ct.setKomiTrnsId(null);
		
		ct.setLastUpdateDt(LocalDateTime.now());
		
		ct.setMsgType("ReversalCT");

		ct.setOriginatingBank(flatReq.getDebtorAgentId());
		ct.setRecipientBank(flatReq.getCreditorAgentId());
		
		ct.setCallStatus(null);

		ct.setResponseCode(null);
		ct.setReasonCode(null);
		ct.setCbStatus(null);

		
	
	}

}
