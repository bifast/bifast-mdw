package bifast.mock.route;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.mock.processor.AccountEnquiryResponseProcessor;
import bifast.mock.processor.CreditTransferResponseProcessor;
import bifast.mock.processor.FICreditTransferResponseProcessor;
import bifast.mock.processor.OnRequestProcessor;

@Component
public class CiHubRoute extends RouteBuilder {
	
	@Autowired
	private OnRequestProcessor checkMessageTypeProcessor;
	@Autowired
	private AccountEnquiryResponseProcessor accountEnquiryResponseProcessor;
	@Autowired
	private CreditTransferResponseProcessor creditTransferResponseProcessor;
	@Autowired
	private FICreditTransferResponseProcessor fICreditTransferResponseProcessor;
	
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
		
		restConfiguration()
			.component("servlet")
		;
		
		rest("/")
			.post("/cihub")
			.consumes("application/json")
			.to("direct:receive")
		;
	

		from("direct:receive").routeId("receive")
			.setExchangePattern(ExchangePattern.InOut)
			.convertBodyTo(String.class)
			.log("Terima di mock")
			.unmarshal(jsonBusinessMessageDataFormat)
			.process(checkMessageTypeProcessor)

			.choice()
				.when().simple("${header.msgType} == 'AccountEnquiryRequest'")
					.process(accountEnquiryResponseProcessor)
					
				.when().simple("${header.msgType} == 'CreditTransferRequest'")
					.process(creditTransferResponseProcessor)
				
				.when().simple("${header.msgType} == 'FICreditTransferRequest'")
					.process(fICreditTransferResponseProcessor)
					
				.otherwise()	
					.log("CreditTranfsr")
			.end()
				
			.log("Selesai dari mock")
			.marshal(jsonBusinessMessageDataFormat)
			.removeHeader("msgType")
		;

	}

}
