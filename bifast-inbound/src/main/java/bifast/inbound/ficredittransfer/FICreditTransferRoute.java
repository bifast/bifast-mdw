package bifast.inbound.ficredittransfer;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.inbound.corebank.CBFICreditInstructionRequestPojo;
import bifast.inbound.processor.EnrichmentAggregator;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class FICreditTransferRoute extends RouteBuilder {

	@Autowired
	private FICreditTransferProcessor fiCreditTransferProcessor;
	@Autowired
	private FICTCorebankRequestProcessor fictCorebankRequestProcessor;
//	@Autowired
//	private FICTCorebankResponseProcessor fictCorebankResponseProcessor;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;

	JacksonDataFormat businessMessageJDF = new JacksonDataFormat(BusinessMessage.class);
	JacksonDataFormat ficbCreditTransferRequestJDF = new JacksonDataFormat(CBFICreditInstructionRequestPojo.class);

	private void configureJson() {
		businessMessageJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		businessMessageJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		businessMessageJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		businessMessageJDF.setInclude("NON_NULL");
		businessMessageJDF.setInclude("NON_EMPTY");

		ficbCreditTransferRequestJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		ficbCreditTransferRequestJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		ficbCreditTransferRequestJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		ficbCreditTransferRequestJDF.setInclude("NON_NULL");
		ficbCreditTransferRequestJDF.setInclude("NON_EMPTY");
	}

	@Override
	public void configure() throws Exception {
		configureJson();
		
		from("direct:ficrdttransfer").routeId("ficrdttransfer")
			
			.log("[ficrdttransfer] started")
			// prepare untuk request ke corebank
			.unmarshal(businessMessageJDF)
			.process(fictCorebankRequestProcessor)
			.setHeader("fict_cbrequest", simple("${body}"))
			.marshal(ficbCreditTransferRequestJDF)
			.log("CB FICT: ${body}")
		
			// send ke corebank
			.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{komi.corebank-url}}?"
//						+ "socketTimeout={{komi.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)
			.doCatch(Exception.class)
				// TODO bagaimana kalau error waktu call corebanking
				.log("Gagal ke corebank")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
				.setBody(constant(null))
			.end()
			
			// process corebank response
			.process(fiCreditTransferProcessor)
//			.process(fictCorebankReponseProcessor)
			.marshal(businessMessageJDF)
			
			.removeHeaders("fict_*")

		;
	}

}
