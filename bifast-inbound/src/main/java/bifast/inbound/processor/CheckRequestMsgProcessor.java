package bifast.inbound.processor;

import java.time.Instant;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs002Pojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.inbound.service.FlattenIsoMessageService;
import bifast.inbound.service.UtilService;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class CheckRequestMsgProcessor implements Processor {
	@Autowired private UtilService utilService;
	@Autowired private FlattenIsoMessageService flatMsgService;
	
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

		if (inputMsg.getAppHdr().getMsgDefIdr().startsWith("pacs.008")) {
			System.out.println("jenis msg " + inputMsg.getAppHdr().getMsgDefIdr());
			FlatPacs008Pojo flat008 = flatMsgService.flatteningPacs008(inputMsg); 
			processData.setBiRequestFlat(flat008);
		}
		else if (inputMsg.getAppHdr().getMsgDefIdr().startsWith("pacs.002")) {
			System.out.println("jenis msg " + inputMsg.getAppHdr().getMsgDefIdr());
			FlatPacs002Pojo flat002 = flatMsgService.flatteningPacs002(inputMsg); 
			processData.setBiRequestFlat(flat002);

		}
		else if (inputMsg.getAppHdr().getMsgDefIdr().startsWith("prxy.901")) {
			System.out.println("jenis msg ProxyNotificatio");

		}

		exchange.getMessage().setHeader("hdr_process_data", processData);

		
		
	}
	

}
