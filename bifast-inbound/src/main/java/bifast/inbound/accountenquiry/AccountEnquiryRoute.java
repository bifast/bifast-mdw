package bifast.inbound.accountenquiry;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.inbound.corebank.CBAccountEnquiryRequestPojo;
import bifast.inbound.processor.EnrichmentAggregator;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class AccountEnquiryRoute extends RouteBuilder {

	@Autowired
	private AECorebankReponseProcessor aeCorebankReponseProcessor;
	@Autowired
	private AECorebankRequestProcessor aeCorebankRequestProcessor;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;

	JacksonDataFormat businessMessageJDF = new JacksonDataFormat(BusinessMessage.class);
	JacksonDataFormat cbAccountEnquiryRequestJDF = new JacksonDataFormat(CBAccountEnquiryRequestPojo.class);

	private void configureJson() {
		businessMessageJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		businessMessageJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		businessMessageJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		businessMessageJDF.setInclude("NON_NULL");
		businessMessageJDF.setInclude("NON_EMPTY");

		cbAccountEnquiryRequestJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		cbAccountEnquiryRequestJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		cbAccountEnquiryRequestJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		cbAccountEnquiryRequestJDF.setInclude("NON_NULL");
		cbAccountEnquiryRequestJDF.setInclude("NON_EMPTY");
	}

	@Override
	public void configure() throws Exception {
		configureJson();
		
//		onException(org.apache.http.conn.HttpHostConnectException.class)
//			.maximumRedeliveries(5).delay(1000)
//			.log("Route level onException")
////			.handled(true)
//			.setBody(constant(null))
//		;
		
		from("direct:accountenq").routeId("accountenq")
			
			.log("AE: ${body}")
			// prepare untuk request ke corebank
			.unmarshal(businessMessageJDF)
			.process(aeCorebankRequestProcessor)
			.setHeader("ae_cbrequest", simple("${body}"))
			.marshal(cbAccountEnquiryRequestJDF)
			.log("CB AE: ${body}")
		
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
				.log(LoggingLevel.ERROR, "Gagal ke corebank")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
				.setBody(constant(null))
				.delay(3000)
			.end()
			
			.log("Setelah call corebank")
			
			// process corebank response
//			.process(accountEnquiryProcessor)
			.process(aeCorebankReponseProcessor)
			.marshal(businessMessageJDF)
			
			.removeHeaders("ae_*")

		;
	}

}
