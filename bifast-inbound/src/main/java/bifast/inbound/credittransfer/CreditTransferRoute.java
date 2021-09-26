package bifast.inbound.credittransfer;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.inbound.corebank.pojo.CBCreditInstructionRequestPojo;
import bifast.inbound.corebank.pojo.CBCreditInstructionResponsePojo;
import bifast.inbound.processor.EnrichmentAggregator;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class CreditTransferRoute extends RouteBuilder {

	@Autowired
	private CreditTransferProcessor creditTransferProcessor;
	@Autowired
	private CTCorebankRequestProcessor ctCorebankRequestProcessor;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;

	JacksonDataFormat businessMessageJDF = new JacksonDataFormat(BusinessMessage.class);
	JacksonDataFormat cbCreditTransferRequestJDF = new JacksonDataFormat(CBCreditInstructionRequestPojo.class);
	JacksonDataFormat cbCreditTransferResponseJDF = new JacksonDataFormat(CBCreditInstructionResponsePojo.class);

	private void configureJson() {
		businessMessageJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		businessMessageJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		businessMessageJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		businessMessageJDF.setInclude("NON_NULL");
		businessMessageJDF.setInclude("NON_EMPTY");

		cbCreditTransferRequestJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		cbCreditTransferRequestJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		cbCreditTransferRequestJDF.setInclude("NON_NULL");
		cbCreditTransferRequestJDF.setInclude("NON_EMPTY");

		cbCreditTransferResponseJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		cbCreditTransferResponseJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		cbCreditTransferResponseJDF.setInclude("NON_NULL");
		cbCreditTransferResponseJDF.setInclude("NON_EMPTY");

	}

	@Override
	public void configure() throws Exception {
		configureJson();
		
		from("direct:crdttransfer").routeId("crdttransfer")
			
			.log("CT: ${body}")
			// prepare untuk request ke corebank
//			.unmarshal(businessMessageJDF)
			.process(ctCorebankRequestProcessor)
			.setHeader("ct_cbrequest", simple("${body}"))
			.marshal(cbCreditTransferRequestJDF)
			.log("CB CT: ${body}")
		
			// send ke corebank
			.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{komi.corebank-url}}?"
//						+ "socketTimeout={{komi.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)
				.unmarshal(cbCreditTransferResponseJDF)
			.doCatch(Exception.class)
				// TODO bagaimana kalau error waktu call corebanking
				.log("Gagal ke corebank")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
				.setBody(constant(null))
			.end()
			
			// process corebank response
			.process(creditTransferProcessor)
//			.marshal(businessMessageJDF)
			
			.removeHeaders("ct_*")

		;
	}

}
