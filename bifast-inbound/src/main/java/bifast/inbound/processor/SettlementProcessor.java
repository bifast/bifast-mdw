package bifast.inbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class SettlementProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		
		//TODO kirim settlement ke COREBANK
		
	}

}
