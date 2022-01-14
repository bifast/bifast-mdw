package bifast.inbound.reversecrdttrns;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ReverseCTRoute extends RouteBuilder {
	@Autowired private ReverseCTProcessor reverseCTProcessor;
	@Autowired private ReverseCTResponseProcessor responseProcessor;
	

	@Override
	public void configure() throws Exception {

		from("direct:reverct").routeId("reversect")

			.log("akan reverse Credit Transfer")
			.process(reverseCTProcessor)
			.log("${body.class}")
			.filter().simple("${body.class} endsWith 'DebitReversalRequestPojo' ")
				.log("akan call cb debit_reversal")
				.to("direct:cbdebitreversal")
				.log("selesai call cb debit_reversal ${body}")
				
			.end()
			
			.to("seda:save_ct")

			.process(responseProcessor)		
			
		;

	
	}

}
