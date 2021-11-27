package bifast.inbound.route;

import javax.xml.bind.JAXBContext;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;


@Component
public class InboundXMLRoute extends RouteBuilder {


	@Override
	public void configure() throws Exception {
		JaxbDataFormat jaxb = new JaxbDataFormat();
		JAXBContext con = JAXBContext.newInstance(BusinessMessage.class);
		jaxb.setContext(con);
		
		restConfiguration()
			.component("servlet")
		;
			
		rest("/api")
			.post("/xmlinbound")
				.consumes("application/xml")
				.type(BusinessMessage.class)
				.to("direct:receivexml")
		;

	

		from("direct:receivexml").routeId("komi.xmlInboundEndpoint")
			.convertBodyTo(String.class)
			
			.unmarshal(jaxb)	
			
			.log("${body}")
			.stop()
			
		
		;


	}
}
