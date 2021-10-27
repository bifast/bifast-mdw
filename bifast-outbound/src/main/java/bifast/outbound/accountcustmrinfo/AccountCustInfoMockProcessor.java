package bifast.outbound.accountcustmrinfo;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class AccountCustInfoMockProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		String strMsg = "{\"transactionId\": \"000000\", "
					+ "\"dateTime\": \"2021-10-20T20:20:20.200\", "
					+ "";
				
				
		
	}

}
