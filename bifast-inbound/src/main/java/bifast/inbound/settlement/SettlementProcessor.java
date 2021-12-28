package bifast.inbound.settlement;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CbSettlementRequestPojo;
import bifast.inbound.model.CreditTransfer;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs002Pojo;
import bifast.inbound.repository.CreditTransferRepository;

@Component
public class SettlementProcessor implements Processor {
	@Autowired private CreditTransferRepository ctRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
		FlatPacs002Pojo flatSttl = (FlatPacs002Pojo) processData.getBiRequestFlat();

		CbSettlementRequestPojo sttl = new CbSettlementRequestPojo();
		sttl.setKomiTrnsId(processData.getKomiTrnsId());

		List<CreditTransfer> lCrdtTrns = ctRepo.findAllByCrdtTrnRequestBizMsgIdr(flatSttl.getOrgnlEndToEndId());	
		if (lCrdtTrns.size()>0)
			sttl.setOrgnlKomiTrnsId(lCrdtTrns.get(0).getKomiTrnsId());
		
		exchange.getMessage().setBody(sttl);
				
	}

}
