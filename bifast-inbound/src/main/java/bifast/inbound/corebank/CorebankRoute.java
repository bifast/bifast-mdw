package bifast.inbound.corebank;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CorebankRoute extends RouteBuilder {

	@Autowired
	private BuildCBResponseProcessor buildCBResponse;
	
	@Override
	public void configure() throws Exception {


		rest("/api")

			.post("/accountenquiry")
				.consumes("application/json")
				.to("direct:router")
			;
		
		from("direct:router")
			.log("terima message")
			.setHeader("cb_request", simple("${body}"))
			.process(buildCBResponse)
			
		;

	}

}
