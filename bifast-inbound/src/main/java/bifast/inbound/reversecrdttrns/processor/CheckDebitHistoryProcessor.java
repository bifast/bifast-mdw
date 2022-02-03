package bifast.inbound.reversecrdttrns.processor;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.model.CreditTransfer;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.inbound.repository.CreditTransferRepository;

@Component
public class CheckDebitHistoryProcessor implements Processor {
	@Autowired private CreditTransferRepository ctRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		FlatPacs008Pojo request = exchange.getProperty("flatRequest", FlatPacs008Pojo.class);
		Optional<CreditTransfer> oCrdtTrns = ctRepo.getSuccessByEndToEndId(request.getOrgnlEndToEndId());
		if (oCrdtTrns.isPresent()) {
			CreditTransfer ct = oCrdtTrns.get();
			if (ct.getAmount().compareTo(request.getAmount()) == 0)
				exchange.setProperty("revCTCheckRsl", "AmountMatch");
			else 
				exchange.setProperty("revCTCheckRsl", "AmountNotMatch");
		}
		
		else {
			exchange.setProperty("revCTCheckRsl", "NotFound");
		}
		
	}

}
