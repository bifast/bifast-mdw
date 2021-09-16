package bifast.inbound.report;

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

	
	JacksonDataFormat messageRequestJDF = new JacksonDataFormat(RequestPojo.class);
	JacksonDataFormat businessMessageJDF = new JacksonDataFormat(BusinessMessage.class);
	JacksonDataFormat messageReponseJDF = new JacksonDataFormat(ResponseWrapperPojo.class);

	private void configureJson() {
		messageRequestJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		messageRequestJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		messageRequestJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		messageRequestJDF.setInclude("NON_NULL");
		messageRequestJDF.setInclude("NON_EMPTY");
		
		businessMessageJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		businessMessageJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		businessMessageJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		businessMessageJDF.setInclude("NON_NULL");
		businessMessageJDF.setInclude("NON_EMPTY");

		messageReponseJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		messageReponseJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		messageReponseJDF.setInclude("NON_NULL");
		messageReponseJDF.setInclude("NON_EMPTY");

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
			
			.unmarshal(messageRequestJDF)
			.setHeader("enq_request", simple("${body}"))
			.process(enquiryProcessor)
			
			.choice()
				.when().simple("${body} != null")
					.log("Siap")
					.unmarshal().base64()
					.unmarshal().zipDeflater()
					.log("${body}")
					.unmarshal(businessMessageJDF)
			.end()

			.process(responseProcessor)
			.marshal(messageReponseJDF)
			
			.removeHeaders("enq_*")
		;


	}
}
