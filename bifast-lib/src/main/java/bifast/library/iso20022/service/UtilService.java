package bifast.library.iso20022.service;

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

import bifast.library.config.LibConfig;
import bifast.library.model.MessageCounter;
import bifast.library.repository.MessageCounterRepository;


@Service
public class UtilService {

	@Autowired
	private LibConfig config;
	@Autowired
	private MessageCounterRepository msgCounterRepo;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	public Integer getCounter () {
        Integer intNow = Integer.valueOf(LocalDateTime.now().format(formatter));
        
		Optional<MessageCounter> optCnt = msgCounterRepo.findById(intNow); 
		if (optCnt.isPresent()) {
			MessageCounter msgCnt = optCnt.get();
			msgCnt.setLastNumber(msgCnt.getLastNumber()+1);
			msgCounterRepo.save(msgCnt);
			return (msgCnt.getLastNumber());
		}
		else {
			MessageCounter msgCnt = new MessageCounter(intNow, 1);
			msgCounterRepo.save(msgCnt);
			return (1);
		}
	}
	
	public String genMessageId (String trxType) {
		String strToday = LocalDateTime.now().format(formatter);
		DecimalFormat df = new DecimalFormat("00000000");
		String strCounter = df.format(getCounter());
		String msgId = strToday + config.getBankcode() + trxType + strCounter;
		return msgId;
	}
	
	public String genBusMsgId (String trxType, String channel ) {
		String strToday = LocalDateTime.now().format(formatter);
		DecimalFormat df = new DecimalFormat("00000000");
		String strCounter = df.format(getCounter());
		String msgId = strToday + config.getBankcode() + trxType + "O" + channel + strCounter;
		return msgId;
	}

	public XMLGregorianCalendar CalenderConvert(String v) throws Exception {
		GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(v));
        XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar( cal);
		return calendar;
	}

}
