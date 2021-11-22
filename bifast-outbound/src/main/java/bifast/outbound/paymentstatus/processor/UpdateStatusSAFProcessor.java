package bifast.outbound.paymentstatus.processor;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.paymentstatus.UndefinedCTPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.ChannelTransactionRepository;
import bifast.outbound.repository.CreditTransferRepository;

@Component
public class UpdateStatusSAFProcessor implements Processor{
	@Autowired
	private CreditTransferRepository ctRepo;
	@Autowired
	private ChannelTransactionRepository chnlTrnsRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		UndefinedCTPojo psReq = (UndefinedCTPojo) rmw.getChannelRequest();
		
		CreditTransfer ct = ctRepo.findById(Long.parseLong(psReq.getId())).orElse(new CreditTransfer());
		
		ChannelTransaction ch = chnlTrnsRepo.findById(psReq.getKomiTrnsId()).orElse(new ChannelTransaction());
		
		if (psReq.getPsStatus().equals("ACCEPTED")) {
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

			ch.setCallStatus("SUCCESS");
			ch.setResponseCode(psReq.getResponseCode());

		}

		else if (psReq.getPsStatus().equals("NOTFOUND")) {
			ct.setCallStatus("NOTFOUND");
			ct.setResponseCode(psReq.getResponseCode());
			ct.setReasonCode(psReq.getReasonCode());

			ch.setCallStatus("NOTFOUND");
			ch.setResponseCode(psReq.getResponseCode());
		}
			
		Integer cnt = ct.getPsCounter();
		if (null == cnt) cnt = 0;
		ct.setPsCounter(cnt + 1);

		ct.setLastUpdateDt(LocalDateTime.now());
		
		if (ct.getPsCounter()==5) 
			exchange.getMessage().setHeader("ps_notif", "yes");
		else
			exchange.getMessage().setHeader("ps_notif", "no");
		
		ctRepo.save(ct);
		chnlTrnsRepo.save(ch);
	}

}
