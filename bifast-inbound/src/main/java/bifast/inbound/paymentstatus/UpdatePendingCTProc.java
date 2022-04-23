package bifast.inbound.paymentstatus;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.model.CreditTransfer;
import bifast.inbound.repository.CreditTransferRepository;

@Component
public class UpdatePendingCTProc implements Processor {
	@Autowired private CreditTransferRepository ctRepo;

	@Override
	public void process(Exchange exchange) throws Exception {
		String response = exchange.getProperty("pr_psresponse", String.class);
		String reason = exchange.getProperty("pr_psreason", String.class);
		CTQryDTO qry = exchange.getProperty("pr_psrequest", CTQryDTO.class);
		CreditTransfer ct = ctRepo.findById(qry.getId()).orElse(new CreditTransfer());
		ct.setPsCounter(ct.getPsCounter()+1);
		ct.setLastUpdateDt(LocalDateTime.now());
		if (response.equals("ACSC")) 
			ct.setCbStatus("DONE");
		
		if (reason.equals("U106"))
			ct.setSettlementConfBizMsgIdr("NOTFOUND");
		
		ctRepo.save(ct);		
	}

}
