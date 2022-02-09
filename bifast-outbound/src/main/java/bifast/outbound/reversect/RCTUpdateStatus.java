package bifast.outbound.reversect;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.CreditTransfer;
import bifast.outbound.pojo.flat.FlatPacs002Pojo;
import bifast.outbound.repository.CreditTransferRepository;

@Component
public class RCTUpdateStatus implements Processor{
	@Autowired private CreditTransferRepository ctRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		Object oBody = exchange.getMessage().getBody(Object.class);
		
		String reversalStatus = "ERROR";
		if (oBody.getClass().getSimpleName().equals("FlatPacs002Pojo")) {
			
			FlatPacs002Pojo pacs002 = (FlatPacs002Pojo) oBody;
			if (pacs002.getReasonCode().equals("U000"))
				reversalStatus = "DONE";
			else
				reversalStatus = "REJECT";
		}

		RCTQueryResultDTO qry = exchange.getProperty("pr_rctrequest", RCTQueryResultDTO.class);
		Optional<CreditTransfer> oct = ctRepo.findById(qry.getId());
		if (oct.isPresent()) {
			CreditTransfer ct = oct.get();
			ct.setReversal(reversalStatus);
			ctRepo.save(ct);
		}
		

		if (!(reversalStatus.equals("DONE")))
			exchange.setProperty("pr_notif", "yes");
		
	}

}
