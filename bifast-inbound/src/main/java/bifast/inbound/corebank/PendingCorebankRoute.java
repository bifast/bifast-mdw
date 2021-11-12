package bifast.inbound.corebank;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PendingCorebankRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("scheduler://foo?delay=10000").routeId("komi.schd.pendingrvsl")
			.log("satu")
		;

	}

}
