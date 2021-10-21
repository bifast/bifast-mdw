package bifast.inbound.processor;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.model.CreditTransfer;
import bifast.inbound.repository.CreditTransferRepository;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class SettlementProcessor implements Processor {
	@Autowired
	private CreditTransferRepository ctRepo;
	
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		BusinessMessage bm = exchange.getMessage().getBody(BusinessMessage.class);
		
		String ctBizMsgId = bm.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlEndToEndId();
		Optional<CreditTransfer> oCrdtTrns = ctRepo.findByCrdtTrnRequestBizMsgIdr(ctBizMsgId);
		
		if (oCrdtTrns.isPresent()) {
			CreditTransfer ct = oCrdtTrns.get();
			ct.setSettlementBizMsgId(bm.getAppHdr().getBizMsgIdr());
			ctRepo.save(ct);
		}
		
		
		//TODO kirim settlement ke COREBANK
		
	}

}
