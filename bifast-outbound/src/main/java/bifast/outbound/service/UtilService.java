package bifast.outbound.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bifast.outbound.config.Config;
import bifast.outbound.pojo.RequestMessageWrapper;

@Service
public class UtilService {

	@Autowired
	private Config config;
//	@Autowired private ChannelTransactionRepository channelTrnsRepo;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	DecimalFormat df = new DecimalFormat("000");
	static DecimalFormat secondOfDayFm = new DecimalFormat("00000");

    private static int komiSeq = 0;

    private synchronized static int nextKomiTrnsId() {
    	komiSeq++;
        if (komiSeq > 999) komiSeq = 0;
        return komiSeq;
    }
	
	public String genKomiTrnsId () {
		
//		LocalTime now = LocalTime.now(ZoneId.systemDefault());
//		int secofday = now.toSecondOfDay() ;

		int secofday = LocalTime.now(ZoneId.systemDefault()).toSecondOfDay() ;
		String strSecofday = secondOfDayFm.format(secofday);
		
		String strToday = LocalDate.now().format(formatter);
//		Long cnt = channelTrnsRepo.getKomiSequence();
		int cnt = nextKomiTrnsId();
		return (strToday + "O" + strSecofday + df.format(cnt));
	}

	public String genBusMsgId (String trxType, RequestMessageWrapper rmw) {
		String strToday = rmw.getKomiTrxId().substring(0,8);
		String komiNumber = rmw.getKomiTrxId().substring(9);
		return strToday + config.getBankcode() + trxType + "O" + rmw.getChannelType() + komiNumber;
	}

	public String genBusMsgId (String trxType, String komiTrxId, String chnlType) {
		String strToday = komiTrxId.substring(0,8);
		String komiNumber = komiTrxId.substring(9);
		return strToday + config.getBankcode() + trxType + "O" + chnlType + komiNumber;
	}

	public String genMessageId (String trxType, RequestMessageWrapper rmw) {
		String strToday = LocalDateTime.now().format(formatter);
		String komiNumber = rmw.getKomiTrxId().substring(9);

		String msgId = strToday + config.getBankcode() + trxType + komiNumber;
		return msgId;
	}

	public String genMessageId (String trxType, String komiTrxId) {
		String strToday = LocalDateTime.now().format(formatter);
		String komiNumber = komiTrxId.substring(9);

		String msgId = strToday + config.getBankcode() + trxType + komiNumber;
		return msgId;
	
	}


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
	
	public String serialize (Object objData, Class<?> className) throws JsonProcessingException {
////		Class<?> datatba = (className.class) objData;
//		className.class.cast(objData);
		System.out.println(className.getName());
		
//		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		String text = mapper.writeValueAsString(null);
		return text;
	}
}
