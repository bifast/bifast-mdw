package bifast.inbound.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.Optional;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bifast.inbound.model.InboundCounter;
import bifast.inbound.repository.InboundCounterRepository;
import bifast.library.config.LibConfig;


@Service
public class UtilService {

	@Autowired
	private LibConfig config;
	@Autowired
	private InboundCounterRepository inboundCounterRepo;

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

	public Integer getInboundCounter () {
        Integer intNow = Integer.valueOf(LocalDateTime.now().format(formatter));
        
		Optional<InboundCounter> optCnt = inboundCounterRepo.findById(intNow); 
		if (optCnt.isPresent()) {
			InboundCounter msgCnt = optCnt.get();
			msgCnt.setLastNumber(msgCnt.getLastNumber()+1);
			inboundCounterRepo.save(msgCnt);
			return (msgCnt.getLastNumber());
		}
		else {
			InboundCounter msgCnt = new InboundCounter(intNow, 50000001);
			inboundCounterRepo.save(msgCnt);
			return (50000001);
		}
	}

	public String genMessageId (String trxType) {
		String strToday = LocalDateTime.now().format(formatter);
		DecimalFormat df = new DecimalFormat("00000000");
		String strCounter = df.format(getInboundCounter());
		String msgId = strToday + config.getBankcode() + trxType + strCounter;
		return msgId;
	}
	

	public String genRfiBusMsgId (String trxType, String channel ) {
		String strToday = LocalDateTime.now().format(formatter);
		DecimalFormat df = new DecimalFormat("00000000");
		String strCounter = df.format(getInboundCounter());
		String msgId = strToday + config.getBankcode() + trxType + "R" + channel + strCounter;
		return msgId;
	}

	public XMLGregorianCalendar CalenderConvert(String v) throws Exception {
		GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(v));
        XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar( cal);
		return calendar;
	}

}
