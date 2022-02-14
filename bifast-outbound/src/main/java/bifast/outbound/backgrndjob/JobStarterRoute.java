package bifast.outbound.backgrndjob;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class JobStarterRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {

		from("timer://jobmon?fixedRate=true&period=120000")
			.to("controlbus:route?routeId=komi.ps.saf&action=resume&async=true")
		;
		
	}

}
