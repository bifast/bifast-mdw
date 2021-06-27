package fransmz.bifast.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import fransmz.bifast.fificredittransfer.ConstructPacs009Processor;
import fransmz.bifast.fificredittransfer.FIFICreditTransferInput;
import fransmz.bifast.fificredittransfer.FIFIResponseMsgProcessor;
import fransmz.bifast.fificredittransfer.ResponseMsg;
import fransmz.bifast.pojo.BusinessMessage;
import fransmz.bifast.service.ExtractStatusResponseProcessor;


@Component
public class FIFICreditTransferRoute extends RouteBuilder {

	@Autowired
	private ConstructPacs009Processor constructPacs009Processor;
	@Autowired
	private ExtractStatusResponseProcessor extractStatusResponseProcessor;
	@Autowired
	private FIFIResponseMsgProcessor responseMsgProcessor;

	JacksonDataFormat jsonFIFICreditTransferInputDataFormat = new JacksonDataFormat(FIFICreditTransferInput.class);
	JacksonDataFormat jsonBusinessMessageFormat = new JacksonDataFormat(BusinessMessage.class);
	JacksonDataFormat jsonResponseMsgFormat = new JacksonDataFormat(ResponseMsg.class);

	private void configureJson() {
		jsonFIFICreditTransferInputDataFormat.setInclude("NON_NULL");
		jsonFIFICreditTransferInputDataFormat.setInclude("NON_EMPTY");
		jsonFIFICreditTransferInputDataFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonBusinessMessageFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonBusinessMessageFormat.setInclude("NON_NULL");
		jsonBusinessMessageFormat.setInclude("NON_EMPTY");
		jsonBusinessMessageFormat.setPrettyPrint(true);
		jsonBusinessMessageFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		jsonBusinessMessageFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonResponseMsgFormat.setInclude("NON_NULL");
		jsonResponseMsgFormat.setInclude("NON_EMPTY");
		jsonResponseMsgFormat.setPrettyPrint(true);
		jsonResponseMsgFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		
	}
	
	@Override
	public void configure() throws Exception {
		configureJson();

		restConfiguration().component("servlet");
		rest("/case")
			.post("/finct")
				.consumes("application/json")
				.to("direct:finct")
		;
		

		from("direct:finct").routeId("finct")
			.convertBodyTo(String.class)
			.unmarshal(jsonFIFICreditTransferInputDataFormat)
			.setHeader("req_obj", simple("${body}"))

			.process(constructPacs009Processor)
			.marshal(jsonBusinessMessageFormat)
			.to("file://D:\\Workspace\\ISO20022\\target?fileName=fificrdtransfer.txt&fileExist=Append&appendChars=\\r\\n")

			.to("rest:post:ficrdttrN1?host=demo3216691.mockable.io&producerComponentName=http&bridgeEndpoint=true")
			.log("${body}")
			
			.unmarshal(jsonBusinessMessageFormat)
			.process(extractStatusResponseProcessor)
			.log("Status: ${header.resp_status}")

			.choice()
				.when().simple("${header.resp_status} == 'ACTC'")
					.log("Accepted")
//					.process(responseMsgProcessor)
//					.marshal(jsonResponseMsgFormat)
				.otherwise()
//					.process(responseMsgProcessor)
//					.marshal(jsonResponseMsgFormat)
					.log("Rejected")
			.end()
			
			.process(responseMsgProcessor)
			.marshal(jsonResponseMsgFormat)

			.removeHeaders("resp_*")
			.removeHeader("req_*")
		;
		

	}
}
