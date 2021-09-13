package bifast.inbound.enquiry;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.library.iso20022.custom.BusinessMessage;


@Component
public class EnquiryRoute extends RouteBuilder {

	
	JacksonDataFormat MessageRequestJDF = new JacksonDataFormat(RequestPojo.class);
	JacksonDataFormat BusinessMessageJDF = new JacksonDataFormat(BusinessMessage.class);
	JacksonDataFormat MessageReponseJDF = new JacksonDataFormat(ResponseWrapperPojo.class);

	private void configureJson() {
		MessageRequestJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		MessageRequestJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		MessageRequestJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		MessageRequestJDF.setInclude("NON_NULL");
		MessageRequestJDF.setInclude("NON_EMPTY");
		
		BusinessMessageJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		BusinessMessageJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		BusinessMessageJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		BusinessMessageJDF.setInclude("NON_NULL");
		BusinessMessageJDF.setInclude("NON_EMPTY");

		MessageReponseJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		MessageReponseJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		MessageReponseJDF.setInclude("NON_NULL");
		MessageReponseJDF.setInclude("NON_EMPTY");

	}
	
	@Autowired
	private EnquiryProcessor enquiryProcessor;
	@Autowired
	private PackResponseProcessor responseProcessor;
	
	@Override
	public void configure() throws Exception {
		configureJson();

		restConfiguration()
			.component("servlet")
		;
			
		rest("/api")
			.post("/enquiry")
				.consumes("application/json")
				.to("direct:enquiry")
			;
		
		from("direct:enquiry").routeId("enquiry")
			.convertBodyTo(String.class)
			
			.unmarshal(MessageRequestJDF)
			.setHeader("enq_request", simple("${body}"))
			.process(enquiryProcessor)
			
			.choice()
				.when().simple("${body} != null")
					.log("Siap")
					.unmarshal().base64()
					.unmarshal().zipDeflater()
					.unmarshal(BusinessMessageJDF)
			.end()

			.process(responseProcessor)
			.marshal(MessageReponseJDF)
			
			.removeHeaders("enq_*")
		;


	}
}
