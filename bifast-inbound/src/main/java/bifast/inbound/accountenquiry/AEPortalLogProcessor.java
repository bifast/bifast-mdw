package bifast.inbound.accountenquiry;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.model.StatusReason;
import bifast.inbound.notification.LogDataPojo;
import bifast.inbound.notification.PortalApiPojo;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.inbound.repository.StatusReasonRepository;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class AEPortalLogProcessor implements Processor{
	@Autowired StatusReasonRepository statusReasonRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
		BusinessMessage resp = processData.getBiResponseMsg();
		
		String msgType = exchange.getMessage().getHeader("hdr_msgType", String.class);

		FlatPacs008Pojo req = (FlatPacs008Pojo) processData.getBiRequestFlat();
		
		PortalApiPojo logMsg = new PortalApiPojo();
		LogDataPojo data = new LogDataPojo();

		data.setStatus_code("SUCCESS");
		
		data.setResponse_code(resp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
		
		String reasonCode = resp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getStsRsnInf().get(0).getRsn().getPrtry();
		StatusReason stsRsn = statusReasonRepo.findById(reasonCode).orElse(new StatusReason());

		data.setReason_code(reasonCode);
		data.setReason_message(stsRsn.getDescription());

		data.setKomi_trx_no(processData.getKomiTrnsId());
		data.setKomi_unique_id(req.getBizMsgIdr());
//		data.setChannel_type(rmw.getChannelType());
		data.setSender_bank(req.getFrBic());
		data.setTrx_type("I");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		data.setTrx_initiation_date(processData.getReceivedDt().format(formatter));

		long timeElapsed = Duration.between(processData.getReceivedDt(), LocalDateTime.now()).toMillis();
		data.setTrx_duration(String.valueOf(timeElapsed));

		if (msgType.equals("510"))
			logMsg.setCodelog("AE");
		else if (msgType.equals("010"))
			logMsg.setCodelog("CT");
		else if (msgType.equals("110"))
			logMsg.setCodelog("CT");
		else if (msgType.equals("011"))
			logMsg.setCodelog("RCT");

		data.setBifast_trx_no(req.getBizMsgIdr());
			
		data.setProxyFlag("T");
		data.setRecipient_bank(req.getToBic());
		data.setRecipient_account_no(req.getCreditorAccountNo());
		data.setSender_account_no(req.getDebtorAccountNo());
		data.setTrx_amount(req.getAmount());
		
		logMsg.setData(data);
		exchange.getMessage().setBody(logMsg);
		
	}

}
