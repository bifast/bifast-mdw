package bifast.corebank.balance;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class AccountBalanceProcessor implements Processor{


	@Override
	public void process(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
		CbAccountBalanceRequestPojo balanceRequest = exchange.getMessage().getBody(CbAccountBalanceRequestPojo.class);

		//TODO process Account Balance
		
		
		CbAccountBalanceResponsePojo balanceResponse = new CbAccountBalanceResponsePojo();
		balanceResponse.setBalance("999999999.99");
		balanceResponse.setKomiTrnsId(balanceRequest.getKomiTrnsId());
		balanceResponse.setReason("U000");
		balanceResponse.setStatus("ACTC");
		
		exchange.getMessage().setBody(balanceResponse);

	}

}
