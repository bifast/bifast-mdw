package bifast.inbound.processor;

import java.time.Instant;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatAdmi004Pojo;
import bifast.inbound.pojo.flat.FlatPacs002Pojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.inbound.pojo.flat.FlatPrxy901Pojo;
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

		ProcessDataPojo processData = new ProcessDataPojo();

		String trnType = inputMsg.getAppHdr().getBizMsgIdr().substring(16,19);

		if ((inputMsg.getAppHdr().getMsgDefIdr().startsWith("pacs.002")) &&
			(inputMsg.getAppHdr().getBizSvc().equals("STTL"))	) {
			
			trnType = "SETTLEMENT";
			FlatPacs002Pojo flat002 = flatMsgService.flatteningPacs002(inputMsg); 
			processData.setBiRequestFlat(flat002);
		}

		else if (inputMsg.getAppHdr().getMsgDefIdr().startsWith("pacs.002")) {
			FlatPacs002Pojo flat002 = flatMsgService.flatteningPacs002(inputMsg); 
			processData.setBiRequestFlat(flat002);
		}

		else if (inputMsg.getAppHdr().getMsgDefIdr().startsWith("prxy.901")) {
			trnType = "PROXYNOTIF";
			FlatPrxy901Pojo flat = flatMsgService.flatteningPrxy901(inputMsg);
			processData.setBiRequestFlat(flat);
		}
		
		else if (inputMsg.getAppHdr().getMsgDefIdr().startsWith("pacs.008")) {
			FlatPacs008Pojo flat008 = flatMsgService.flatteningPacs008(inputMsg); 
			processData.setBiRequestFlat(flat008);
		}

		else if (inputMsg.getAppHdr().getMsgDefIdr().startsWith("admi.004")) {
			System.out.println("jenis msg System Notification");
			FlatAdmi004Pojo flat004 = flatMsgService.flatteningAdmi004(inputMsg);
			processData.setBiRequestFlat(flat004);
			trnType = "EVENTNOTIF";
		}

		exchange.getMessage().setHeader("hdr_msgType", trnType);
		
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
