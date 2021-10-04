package bifast.mock.route;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.mock.processor.ProxyRegistrationResponseProcessor;
import bifast.mock.processor.ProxyResolutionResponseProcessor;

@Component
public class ProxyRoute extends RouteBuilder {

    @Autowired
	private ProxyRegistrationResponseProcessor proxyRegistrationResponseProcessor;
	@Autowired
	private ProxyResolutionResponseProcessor proxyResolutionResponseProcessor;

	JacksonDataFormat jsonBusinessMessageDataFormat = new JacksonDataFormat(BusinessMessage.class);

    @Override
    public void configure() throws Exception {

        jsonBusinessMessageDataFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonBusinessMessageDataFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		jsonBusinessMessageDataFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		jsonBusinessMessageDataFormat.setInclude("NON_NULL");
		jsonBusinessMessageDataFormat.setInclude("NON_EMPTY");

        from("direct:prxyregn").routeId("proxyregistration")
            .convertBodyTo(String.class)
            .log("Terima di mock")
            .log("${body}")
            .delay(500)
            .log("end-delay")
            .unmarshal(jsonBusinessMessageDataFormat)
            .process(proxyRegistrationResponseProcessor)
            .marshal(jsonBusinessMessageDataFormat)  // remark bila rejection
            .log("Response dari mock")

            .removeHeader("msgType")

        ;
    }
    
}
