package bifast.inbound.processor;

import java.time.Instant;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.service.UtilService;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class CheckRequestMsgProcessor implements Processor {
	@Autowired private UtilService utilService;
	
	
	@Override
	public void process(Exchange exchange) throws Exception {
		BusinessMessage inputMsg = exchange.getMessage().getBody(BusinessMessage.class);

		String trnType = inputMsg.getAppHdr().getBizMsgIdr().substring(16,19);
		if (inputMsg.getAppHdr().getMsgDefIdr().startsWith("pacs.002")) 
			trnType = "SETTLEMENT";
		else if (inputMsg.getAppHdr().getMsgDefIdr().startsWith("prxy.901"))
			trnType = "PROXYNOTIF";
		exchange.getMessage().setHeader("hdr_msgType", trnType);
		
		ProcessDataPojo processData = new ProcessDataPojo();

		processData.setBiRequestMsg(inputMsg);
		
		processData.setStartTime(Instant.now());
		
		processData.setInbMesgType(trnType);
		
//		processData.setKomiCounter(null);

		processData.setKomiTrnsId(utilService.genKomiTrnsId());
		
		processData.setReceivedDt(LocalDateTime.now());
		
//		processData.setTextDataReceived(null);
		
		exchange.getMessage().setHeader("hdr_process_data", processData);

	}
	

}
