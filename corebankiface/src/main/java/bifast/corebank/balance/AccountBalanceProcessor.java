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
		
		
		CbAccountBalanceResponsePojo aeResponse = new CbAccountBalanceResponsePojo();
		exchange.getMessage().setBody(aeResponse);

	}

}
