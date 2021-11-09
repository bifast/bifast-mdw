package bifast.corebank.settlement;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class SettlementProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {

		CbSettlementRequestPojo settlement = exchange.getMessage().getBody(CbSettlementRequestPojo.class);
		
		//TODO send ke corebank
		
		//TODO save history table
		
		CbSettlementResponsePojo settlementResponse = new CbSettlementResponsePojo();
		settlementResponse.setKomiTrnsId(settlement.getKomiTrnsId());
		//TODO build response message
		
		exchange.getMessage().setBody(settlementResponse);
	
	}
}
