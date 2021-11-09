package bifast.corebank.settlement;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class SettlementProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {

		CbSettlementRequestPojo settlement = exchange.getMessage().getBody(CbSettlementRequestPojo.class);
		
		CbSettlementResponsePojo settlementResponse = new CbSettlementResponsePojo();
		settlementResponse.setKomiTrnsId(settlement.getKomiTrnsId());
		settlementResponse.setOrgnlKomiTrnsId(settlement.getOrgnlKomiTrnsId());
		settlementResponse.setStatus("ACTC");
		
		exchange.getMessage().setBody(settlementResponse);
	
	}
}
