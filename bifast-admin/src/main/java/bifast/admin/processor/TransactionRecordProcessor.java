package bifast.admin.processor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bifast.admin.pojo.MessageHistoryPojo;
import bifast.admin.pojo.TransxHistoryRequestParam;
import bifast.library.model.OutboundMessage;
import bifast.library.repository.InboundMessageRepository;
import bifast.library.repository.OutboundMessageRepository;

@Component
public class TransactionRecordProcessor implements Processor{

	@Autowired
	private InboundMessageRepository inboundMessageRepo;
	@Autowired
	private OutboundMessageRepository outboundMessageRepo;
	

	@Value("${bifast.bankcode}")
	private String orngl_bic;
	
	Map<String, String> trxType = getTransxTypeMap();
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

		List<OutboundMessage> outMessages = new ArrayList<>();
		List<MessageHistoryPojo> outMsgPojoList = new ArrayList<>();
		TransxHistoryRequestParam req = new TransxHistoryRequestParam();
		String messageBound = "";

		if (null==exchange.getMessage().getBody()) {
			System.out.println("Body kosong");
			outMessages = outboundMessageRepo.findAll();
			exchange.getMessage().setBody("");
			return;
		}
		
		else {
			req = exchange.getMessage().getBody(TransxHistoryRequestParam.class);
			if (req.getBicSrc().equals(orngl_bic)) messageBound="Outbound";
			else if (req.getBicDst().equals(orngl_bic)) messageBound = "Inbound";
			else messageBound = "InOut";
			
		}
		

		if (messageBound.contains("Out")) {
			outMessages = getOutMessages (req);
		
			System.out.println("Ada " + outMessages.size() + " out messages");
			
			for (OutboundMessage msg : outMessages) {
				MessageHistoryPojo newMsg = new MessageHistoryPojo();
				
				newMsg.setUuid(msg.getBizMsgIdr());
				newMsg.setTimestamp(msg.getSendDt().format(dtf));
				newMsg.setStatus_code(msg.getRespStatus());
				newMsg.setStatus_message(msg.getFailureMessage());
				newMsg.setSource_bic(orngl_bic);
				newMsg.setDestination_bic(msg.getToFinId());
				newMsg.setTransaction_type(trxType.get(msg.getTransactionType()));
				
				outMsgPojoList.add(newMsg);
			}
		}

		exchange.getMessage().setBody(outMsgPojoList);
		
	}
	
	private List<OutboundMessage> getOutMessages (TransxHistoryRequestParam reqParam) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime startDate = LocalDateTime.now().minusDays(30);
		LocalDateTime endDate = LocalDateTime.now();
		
		if (!(null==reqParam.getStartDate())) 
			startDate = LocalDate.parse(reqParam.getStartDate(), formatter).atStartOfDay();
		
		if (!(null==reqParam.getEndDate())) 
			endDate = LocalDate.parse(reqParam.getEndDate(), formatter).plusDays(1).atStartOfDay();
		
		List<OutboundMessage> outMessages = new ArrayList<>();
		
		String jenisMessage = "";		
		if (null==reqParam.getTranType()) {
			outMessages = outboundMessageRepo.findAllBySendDtBetween(startDate, endDate);
		}
		
		else {
			for (String key : trxType.keySet()) {
			    if (reqParam.getTranType().equals(trxType.get(key))) {
			    	jenisMessage = key;
			    	break;
			    }
			}
			outMessages = outboundMessageRepo.findAllByTransactionTypeAndSendDtBetween(jenisMessage, startDate, endDate);
		}
			
		return outMessages;
	}

	private Map<String, String> getTransxTypeMap () {
		Map<String, String> trxType = new HashMap<String, String>();
		trxType.put("010", "Credit Transfer");
		trxType.put("019", "FI to FI Credit Transfer");
		trxType.put("011", "Reverse Credit Transfer");
		trxType.put("110", "Proxy Credit Transfer");
		trxType.put("510", "Account Enquiry");
		trxType.put("610", "Proxy Lookup");
		trxType.put("710", "Proxy Registration");
		trxType.put("720", "Proxy Maintenance");
		trxType.put("000", "Network Management Messages");
		return trxType;
	}
}
