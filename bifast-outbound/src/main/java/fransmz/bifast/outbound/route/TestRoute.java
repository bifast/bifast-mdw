package fransmz.bifast.outbound.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fransmz.bifast.outbound.service.TestProcessor;

@Component
public class TestRoute extends RouteBuilder {

	@Autowired 
	private TestProcessor testProcessor;

	@Override
	public void configure() throws Exception {

		
		from("timer:t1?period=5&repeatCount=20")
			.log("halo")
			.process(testProcessor)
		;
	}

}
