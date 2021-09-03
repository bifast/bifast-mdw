package bifast.outbound.processor;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.model.Settlement;
import bifast.library.repository.SettlementRepository;

@Component
public class CheckSettlementProcessor implements Processor {

	@Autowired
	private SettlementRepository settlementRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage reqMesg = exchange.getMessage().getHeader("req_objbi", BusinessMessage.class);
	
		List<Settlement> settlementList = settlementRepo.findByOrgnlCrdtTrnReqBizMsgId(reqMesg.getAppHdr().getBizMsgIdr());

		if (settlementList.size() > 0) {
			Settlement settlement = settlementList.get(0);
			settlementRepo.save(settlement);
			
			exchange.getIn().setBody(settlement.getFullMessage());
		}
		
		else
			exchange.getIn().setBody("");

	}
	

}
