package bifast.outbound.ciconn;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.service.JacksonDataFormatService;

@Component
public class CiConnRoute extends RouteBuilder{
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private CiAccEnqReqProcessor accEnqReqProcessor;
	
	
	@Override
	public void configure() throws Exception {
		JacksonDataFormat accEnqRequestJDF = jdfService.basic(CiAccountEnquiryRequestPojo.class);
		
		restConfiguration().component("servlet");
		
		rest("/ciconn")
			.post("/accountenquiry")
				.consumes("application/json")
				.to("direct:ciacctenq")
		;
		
		from("direct:ciacctenq").routeId("komi.ciacctenq")
			.convertBodyTo(String.class)
			.log("${body}")
			.marshal(accEnqRequestJDF)
			.process(accEnqReqProcessor)
			;
		

	}
	
}