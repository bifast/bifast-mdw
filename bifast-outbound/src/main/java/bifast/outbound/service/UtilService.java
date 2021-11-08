package bifast.outbound.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bifast.outbound.config.Config;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.ChannelTransactionRepository;

@Service
public class UtilService {

	@Autowired
	private Config config;
	@Autowired private ChannelTransactionRepository channelTrnsRepo;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	DecimalFormat df = new DecimalFormat("00000000");


	public String genKomiTrnsId () {
		String strToday = LocalDate.now().format(formatter);
		Long cnt = channelTrnsRepo.getKomiSequence();
		return (strToday + "O" + df.format(cnt));
	}

	public String genBusMsgId (String trxType, RequestMessageWrapper rmw) {
		String strToday = rmw.getKomiTrxId().substring(0,8);
		String komiNumber = rmw.getKomiTrxId().substring(9);
		return strToday + config.getBankcode() + trxType + "O" + rmw.getChannelType() + komiNumber;
	}

	public String genMessageId (String trxType, RequestMessageWrapper rmw) {
		String strToday = LocalDateTime.now().format(formatter);
		String komiNumber = rmw.getKomiTrxId().substring(9);

		String msgId = strToday + config.getBankcode() + trxType + komiNumber;
		return msgId;
	}

	

//	public long getElapsedFromMessageHistory (List<MessageHistory> list, String nodeId) {
//		long elapsed = -1;
//		for (MessageHistory msg : list) {
//			if (nodeId == msg.getNode().getId())
//				elapsed = msg.getElapsed();
//		}
//		return elapsed;
//	}

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
