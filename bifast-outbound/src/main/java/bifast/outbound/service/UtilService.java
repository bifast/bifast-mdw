package bifast.outbound.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.apache.camel.MessageHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bifast.outbound.config.Config;
import bifast.outbound.model.MessageCounter;
import bifast.outbound.repository.MessageCounterRepository;


@Service
public class UtilService {

	@Autowired
	private Config config;
	@Autowired
	private MessageCounterRepository messageCounterRepo;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	public Integer getOutboundCounter () {
        Integer intNow = Integer.valueOf(LocalDateTime.now().format(formatter));
        
		Optional<MessageCounter> optCnt = messageCounterRepo.findById(intNow); 
		if (optCnt.isPresent()) {
			MessageCounter msgCnt = optCnt.get();
			msgCnt.setLastNumber(msgCnt.getLastNumber()+1);
			messageCounterRepo.save(msgCnt);
			return (msgCnt.getLastNumber());
		}
		else {
			MessageCounter msgCnt = new MessageCounter(intNow, 1);
			messageCounterRepo.save(msgCnt);
			return (1);
		}
	}

	public String genMessageId (String trxType) {
		String strToday = LocalDateTime.now().format(formatter);
		DecimalFormat df = new DecimalFormat("00000000");
		String strCounter = df.format(getOutboundCounter());
		String msgId = strToday + config.getBankcode() + trxType + strCounter;
		return msgId;
	}
	
	public String genKomiTrnsId () {
		int doy = LocalDate.now().getDayOfYear();
		DecimalFormat doyDf = new DecimalFormat("000");
		DecimalFormat df = new DecimalFormat("00000");
		
		Integer counter = getOutboundCounter();
		if (counter > 99999) {
			counter = counter - 100000;
			doy = 400 + doy;
		}
		String strCounter = doyDf.format(doy) + df.format(getOutboundCounter());
		return strCounter;
	}

	public String genOfiBusMsgId (String trxType, String channel ) {
		String strToday = LocalDateTime.now().format(formatter);
//		DecimalFormat df = new DecimalFormat("00000000");
//		String strCounter = df.format(getOutboundCounter());
		String msgId = strToday + config.getBankcode() + trxType + "O" + channel + genKomiTrnsId();
		return msgId;
	}

	
	public String genMsgId (String trxType, String intrnRefId) {
		String strToday = LocalDateTime.now().format(formatter);
		String leading = "00000000".concat(intrnRefId);
		int l = leading.length();
		String msgId = strToday + config.getBankcode() + trxType + leading.substring(l-8);
		return msgId;
	}
	
	public long getElapsedFromMessageHistory (List<MessageHistory> list, String nodeId) {
		long elapsed = -1;
		for (MessageHistory msg : list) {
			if (nodeId == msg.getNode().getId())
				elapsed = msg.getElapsed();
		}
		return elapsed;
	}

//	public LocalDateTime getTimestampFromMessageHistory (List<MessageHistory> list, String nodeId) {
//		LocalDateTime ldt = LocalDateTime.now();
//		for (MessageHistory msg : list) {
//			if (nodeId == msg.getNode().getId()) {
//				long time = msg.getTime();
//				ldt = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDateTime();
//			}
//		}
//
//		return ldt;
//	}

//	public long getRouteElapsed (List<MessageHistory> messageList, String routeId) {
//		long start = 0;
//		long end = 0;
//
//		for (MessageHistory msg : messageList) {
//
//			if ( (msg.getRouteId().equals(routeId)) && 
//				 (msg.getNode().getId().equals("start_route")) ) {
//				start = msg.getTime();
//			}
//			if ( (msg.getRouteId().equals(routeId)) && 
//					 (msg.getNode().getId().equals("end_route")) ) {
//				end = msg.getTime();
//				}
//		}
//
//		return (end-start);
//	}


	public String getMsgType (String bizDefIdr, String bizMsgIdr) {
		
		String msgType = "";
		String code = bizMsgIdr.substring(16,19);
		
		if (bizDefIdr.startsWith("pacs.002")) {
			if (code.equals("010"))
				msgType = "CTResp";
			if (code.equals("510"))
				msgType = "AEResp";
		}
		
		else if (bizDefIdr.startsWith("pacs.008")) {
			if (code.equals("510"))
				msgType = "AEReq";
			if (code.equals("010"))
				msgType = "CTReq";
			if (code.equals("110"))
				msgType = "CTReq";
		}
			
		else if (bizDefIdr.startsWith("pacs.028"))   
			msgType = "PSReq";

		else if (bizDefIdr.startsWith("prxy.001"))   
			msgType = "PxRegReq";
		
		else if (bizDefIdr.startsWith("prxy.002"))   
			msgType = "PxRegResp";

		else if (bizDefIdr.startsWith("prxy.003"))   
			msgType = "PxResReq";
		
		else if (bizDefIdr.startsWith("prxy.004"))   
			msgType = "PxRsltResp";

		else if (bizDefIdr.startsWith("admi.002"))   
			msgType = "RjctResp";

		return msgType;
	}
}
