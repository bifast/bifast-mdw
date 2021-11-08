package bifast.inbound.settlement;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettlementRoute extends RouteBuilder {
	@Autowired private SettlementProcessor settlementProcessor;

	@Override
	public void configure() throws Exception {

		from("direct:settlement")
			.process(settlementProcessor)
			
		;

	}

}
