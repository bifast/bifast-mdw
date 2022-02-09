package bifast.outbound.backgrndjob.processor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.backgrndjob.dto.UndefinedCTPojo;
import bifast.outbound.config.Config;
import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.repository.ChannelTransactionRepository;
import bifast.outbound.repository.CreditTransferRepository;

@Component
public class UpdateStatusSAFProcessor implements Processor{
	@Autowired private ChannelTransactionRepository chnlTrnsRepo;
	@Autowired private CreditTransferRepository ctRepo;
	@Autowired private Config config;
	
//	private static Logger logger = LoggerFactory.getLogger(UpdateStatusSAFProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

//		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
//		UndefinedCTPojo psReq = (UndefinedCTPojo) rmw.getChannelRequest();
		UndefinedCTPojo psReq = exchange.getProperty("pr_psrequest", UndefinedCTPojo.class);
		
		CreditTransfer ct = ctRepo.findById(Long.parseLong(psReq.getId())).orElse(new CreditTransfer());
//		System.out.println("CT id : " + ct.getId() + " status " + ct.getCallStatus());
		
		ChannelTransaction ch = chnlTrnsRepo.findById(psReq.getKomiTrnsId()).orElse(new ChannelTransaction());
		
		
		if (psReq.getPsStatus().equals("STTL_FOUND")) {
			
			ct.setCallStatus("SUCCESS");
			ct.setResponseCode(psReq.getResponseCode());
			ct.setReasonCode(psReq.getReasonCode());

			ch.setCallStatus("SUCCESS");
			ch.setResponseCode(psReq.getResponseCode());

		}

		
		else if (psReq.getPsStatus().equals("REJECTED")) {
			ct.setCallStatus("SUCCESS");
			ct.setResponseCode(psReq.getResponseCode());
			ct.setReasonCode(psReq.getReasonCode());
			ct.setSettlementConfBizMsgIdr("");

			ch.setCallStatus("SUCCESS");
			ch.setResponseCode(psReq.getResponseCode());

		}

		else if (psReq.getPsStatus().equals("NOTFOUND")) {
			ct.setCallStatus("NOTFOUND");
			ct.setResponseCode(psReq.getResponseCode());
			ct.setReasonCode(psReq.getReasonCode());

			ch.setCallStatus("SUCCESS");
			ch.setResponseCode(psReq.getResponseCode());
		}
			
		ct.setPsCounter(ct.getPsCounter() + 1);

		ct.setLastUpdateDt(LocalDateTime.now());
		
		Long dur = ChronoUnit.MILLIS.between(ct.getCreateDt(), LocalDateTime.now());
		ct.setCihubElapsedTime(dur);

		if (ct.getPsCounter()== config.getMaxRetryBeforeNotif()) 
			exchange.setProperty("pr_notif", "yes");
		else
			exchange.setProperty("pr_notif", "no");
		
		ctRepo.save(ct);
		chnlTrnsRepo.save(ch);
		
//		System.out.println("Status CT: " + ct.getCallStatus());
		
	}

}
