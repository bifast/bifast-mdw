package bifast.outbound.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class UtilRoute extends RouteBuilder{
	@Autowired JacksonDataFormatService jdfService;
	
	@Override
	public void configure() throws Exception {

		JacksonDataFormat businessMessageJDF = jdfService.wrapUnwrapRoot(BusinessMessage.class);

		from("direct:encrypbody")
			.marshal().zipDeflater()
			.marshal().base64()
			;
		
		
		from("direct:decryp_unmarshal").routeId("komi.decryp")
			.unmarshal().base64()
			.unmarshal().zipDeflater()
			.unmarshal(businessMessageJDF)
		;
	}

	
}
