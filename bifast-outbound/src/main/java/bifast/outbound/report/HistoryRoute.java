package bifast.outbound.report;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.pojo.FlatMessageWrapper;
import bifast.outbound.report.pojo.RequestPojo;

@Component
public class HistoryRoute extends RouteBuilder {
	
	JacksonDataFormat businessMessageJDF = new JacksonDataFormat(BusinessMessage.class);
	JacksonDataFormat RequestJDF = new JacksonDataFormat(RequestPojo.class);
	JacksonDataFormat FlatResponseJDF = new JacksonDataFormat(FlatMessageWrapper.class);

	@Autowired
	private MessageHistoryRetrievalProcessor messageRetrievalProcessor;
	@Autowired
	private FlatteningMessageProcessor messageFlattingProcessor;

	@Override
	public void configure() throws Exception {

		businessMessageJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		businessMessageJDF.setInclude("NON_NULL");
		businessMessageJDF.setInclude("NON_EMPTY");
		businessMessageJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		businessMessageJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		RequestJDF.setInclude("NON_NULL");
		RequestJDF.setInclude("NON_EMPTY");
		
		FlatResponseJDF.setInclude("NON_NULL");
		FlatResponseJDF.setInclude("NON_EMPTY");
//		FlatResponseJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

		restConfiguration().component("servlet");
		
		rest("/")
			.post("/report")
				.consumes("application/json")
				.to("direct:report")

			.get("/report")
				.consumes("application/json")
				.to("direct:getreport")
		;

		from("direct:getreport")
			.convertBodyTo(String.class)
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					RequestPojo req = new RequestPojo();

					req.setBizMsgIdr(exchange.getMessage().getHeader("bizmsgidr", String.class));
					exchange.getIn().setBody(req);
				}
			})
			.marshal(RequestJDF)
			.to("direct:report")
			.removeHeader("bizmsgidr")
		;

		from("direct:report").routeId("komi.report")
			.convertBodyTo(String.class)
			.unmarshal(RequestJDF)
			.process(messageRetrievalProcessor)
			
			.filter(simple("${body} != null"))
				.unmarshal().base64()
				.unmarshal().zipDeflater()
				.log("${body}")
				.unmarshal(businessMessageJDF)
			.end()
					

			.process(messageFlattingProcessor)
			
			.marshal(FlatResponseJDF)
		;
	}

}
