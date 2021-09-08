package bifast.outbound.history;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class HistoryRoute extends RouteBuilder {
	
	JacksonDataFormat BusinessMessageJDF = new JacksonDataFormat(BusinessMessage.class);

	@Autowired
	private HistoryRetrievalProcessor historyRetrievalProcessor;

	@Override
	public void configure() throws Exception {

		BusinessMessageJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		BusinessMessageJDF.setInclude("NON_NULL");
		BusinessMessageJDF.setInclude("NON_EMPTY");
		BusinessMessageJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		from("direct:history")
			.log("sedang di direct:history")

			.process(historyRetrievalProcessor)
			.choice()
				.when().simple("${body} != null")
					.log("nemu datanya")
					.unmarshal().base64()
					.unmarshal().zipDeflater()
			.end()		
		;
	}

}
