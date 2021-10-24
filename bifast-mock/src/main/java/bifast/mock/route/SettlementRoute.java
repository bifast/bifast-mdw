package bifast.mock.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.mock.processor.SettlementProcessor;

@Component
public class SettlementRoute extends RouteBuilder {
	
	@Autowired
	private SettlementProcessor settlementProcessor;
	
	JacksonDataFormat jsonBusinessMessageDataFormat = new JacksonDataFormat(BusinessMessage.class);

	private void configureJson() {
		jsonBusinessMessageDataFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonBusinessMessageDataFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		jsonBusinessMessageDataFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		jsonBusinessMessageDataFormat.setInclude("NON_NULL");
		jsonBusinessMessageDataFormat.setInclude("NON_EMPTY");
	}

	
	@Override
	public void configure() throws Exception {

		configureJson();
		
	
		from("sql:select * from mock_pacs002 where sttl is null")
			.routeId("settlement")
			
			.setHeader("sttl_timount", constant("NO"))
			
			.filter().simple("${body[cdtr_acct]} startsWith '88' ")
				.setHeader("sttl_delay", constant("YES"))
			.end()
			.delay(1000)
			
			.setHeader("sttl_tableqry", simple("${body}"))
			.setHeader("sttl_id", simple("${body[id]}"))

			.filter().simple("${header.sttl_tableqry[result]} == 'ACTC'")

				.filter().simple("${header.sttl_delay} == null")
					.setBody(simple("${body[full_message]}"))
					.unmarshal(jsonBusinessMessageDataFormat)
				
					.process(settlementProcessor)
					.marshal(jsonBusinessMessageDataFormat)
					.log("Submit settlement: ${body}")
	
					.to("rest:post:inbound?host={{komi.inbound-url}}&"
							+ "exchangePattern=InOnly&"
							+ "bridgeEndpoint=true")
				
				.end()
				
			.end()
			
			.to("sql:update mock_pacs002 set sttl = 'DONE' where id::varchar = :#${header.sttl_id}::varchar")
			.removeHeaders("sttl_*")

		;
		
	}

}
