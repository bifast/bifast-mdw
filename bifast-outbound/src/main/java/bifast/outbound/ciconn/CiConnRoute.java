package bifast.outbound.ciconn;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.service.JacksonDataFormatService;

@Component
public class CiConnRoute extends RouteBuilder{
	@Autowired private JacksonDataFormatService jdfService;
	
	
	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
		restConfiguration().component("servlet");
		
		rest("/ciconn")
			.post("/accountenquiry")
				.consumes("application/json")
				.to("direct:ciacctenq")
		;
		
		from("direct:ciacctenq").routeId("komi.ciacctenq")
			.convertBodyTo(String.class)
			.log("${body}")
			;
		

	}
	
}