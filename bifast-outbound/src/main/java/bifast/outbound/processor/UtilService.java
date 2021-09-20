package bifast.outbound.processor;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.Optional;

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

//	public Integer getCounter () {
//        Integer intNow = Integer.valueOf(LocalDateTime.now().format(formatter));
//        
//		Optional<InboundCounter> optCnt = inboundCounterRepo.findById(intNow); 
//		if (optCnt.isPresent()) {
//			InboundCounter msgCnt = optCnt.get();
//			msgCnt.setLastNumber(msgCnt.getLastNumber()+1);
//			inboundCounterRepo.save(msgCnt);
//			return (msgCnt.getLastNumber());
//		}
//		else {
//			InboundCounter msgCnt = new InboundCounter(intNow, 50000001);
//			inboundCounterRepo.save(msgCnt);
//			return (50000001);
//		}
//	}

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
	

	public String genOfiBusMsgId (String trxType, String channel ) {
		String strToday = LocalDateTime.now().format(formatter);
		DecimalFormat df = new DecimalFormat("00000000");
		String strCounter = df.format(getOutboundCounter());
		String msgId = strToday + config.getBankcode() + trxType + "O" + channel + strCounter;
		return msgId;
	}

//	public XMLGregorianCalendar CalenderConvert(String v) throws Exception {
//		GregorianCalendar cal = new GregorianCalendar();
//        cal.setTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(v));
//        XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar( cal);
//		return calendar;
//	}

}
