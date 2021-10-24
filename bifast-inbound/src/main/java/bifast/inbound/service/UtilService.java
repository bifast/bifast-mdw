package bifast.inbound.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.camel.MessageHistory;
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

	public String genKomiTrnsId () {
		int doy = LocalDate.now().getDayOfYear();
		DecimalFormat doyDf = new DecimalFormat("000");
		String strDoy = doyDf.format(doy);
		DecimalFormat df = new DecimalFormat("00000");
		String strCounter = strDoy + df.format(getInboundCounter());
		return strCounter;
	}

	public String genMessageId (String trxType) {
		String strToday = LocalDateTime.now().format(formatter);
		DecimalFormat df = new DecimalFormat("00000000");
		String strCounter = df.format(getInboundCounter());
		String msgId = strToday + config.getBankcode() + trxType + strCounter;
		return msgId;
	}
	

	public String genRfiBusMsgId (String trxType, String channel, String komiId ) {
		String strToday = LocalDateTime.now().format(formatter);
//		DecimalFormat df = new DecimalFormat("00000000");
//		String strCounter = df.format(getInboundCounter());
		String msgId = strToday + config.getBankcode() + trxType + "R" + channel + komiId;
		return msgId;
	}

	public XMLGregorianCalendar CalenderConvert(String v) throws Exception {
		GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(v));
        XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar( cal);
		return calendar;
	}

	
	public long getElapsedFromMessageHistory (List<MessageHistory> list, String nodeId) {
		long elapsed = -1;
		for (MessageHistory msg : list) {
			if (nodeId == msg.getNode().getId())
				elapsed = msg.getElapsed();
		}
		return elapsed;
	}

	public LocalDateTime getTimestampFromMessageHistory (List<MessageHistory> list, String nodeId) {
		LocalDateTime ldt = LocalDateTime.now();
		for (MessageHistory msg : list) {
			if (nodeId == msg.getNode().getId()) {
				long time = msg.getTime();
				ldt = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDateTime();
			}
		}

		return ldt;
	}

	public long getRouteElapsed (List<MessageHistory> messageList, String routeId) {
		long start = 0;
		long end = 0;
		for (MessageHistory msg : messageList) {
			if ( (msg.getRouteId().equals(routeId)) && 
				 (msg.getNode().getId().equals("start_route")) ) {
				start = msg.getTime();
			}
			if ( (msg.getRouteId().equals(routeId)) && 
					 (msg.getNode().getId().equals("end_route")) ) {
				end = msg.getTime();
				}
		}

		return (end-start);

	}


}
