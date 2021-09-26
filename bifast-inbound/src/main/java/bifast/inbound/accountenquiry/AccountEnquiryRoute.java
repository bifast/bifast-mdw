package bifast.inbound.accountenquiry;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.inbound.corebank.BuildCBResponseProcessor;
import bifast.inbound.corebank.pojo.CBAccountEnquiryRequestPojo;
import bifast.inbound.corebank.pojo.CBAccountEnquiryResponsePojo;
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
	@Autowired
	private BuildCBResponseProcessor buildCBResponse;

	JacksonDataFormat businessMessageJDF = new JacksonDataFormat(BusinessMessage.class);
	JacksonDataFormat cbAccountEnquiryRequestJDF = new JacksonDataFormat(CBAccountEnquiryRequestPojo.class);
	JacksonDataFormat cbAccountEnquiryResponseJDF = new JacksonDataFormat(CBAccountEnquiryResponsePojo.class);

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
		
		cbAccountEnquiryResponseJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		cbAccountEnquiryResponseJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		cbAccountEnquiryResponseJDF.setInclude("NON_NULL");
		cbAccountEnquiryResponseJDF.setInclude("NON_EMPTY");

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
		
		from("direct:accountenq").routeId("komi.accountenq")
		
		
			.log(LoggingLevel.DEBUG, "komi.accountenq", "[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] ${body}")
			// prepare untuk request ke corebank
			.unmarshal(businessMessageJDF)
			.process(aeCorebankRequestProcessor)
			.setHeader("ae_cbrequest", simple("${body}"))
		
			// send ke corebank
//			.doTry()
//				.marshal(cbAccountEnquiryRequestJDF)
//				.log("CB AE: ${body}")
//				.setHeader("HttpMethod", constant("POST"))
//				.enrich("http:{{komi.corebank-url}}/accountenquiry?"
////						+ "socketTimeout={{komi.timeout}}&" 
//						+ "bridgeEndpoint=true",
//						enrichmentAggregator)
//				.convertBodyTo(String.class)
//				
//				.log(LoggingLevel.DEBUG, "komi.accountenq", "[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] Response CB: ${body}")
//			
//			.doCatch(Exception.class)
//				// TODO bagaimana kalau error waktu call corebanking
//				.log(LoggingLevel.ERROR, "Gagal ke corebank")
//		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
//				.setBody(constant(null))
//				.delay(3000)
//			.end()
//			.process(buildCBResponse)
//			.marshal(cbAccountEnquiryResponseJDF)
//			
//			.log("Setelah call corebank: ${body}")
			
			// process corebank response
//			.process(accountEnquiryProcessor)
			.delay(1000)
			.process(aeCorebankReponseProcessor)
			.marshal(businessMessageJDF)
			.log("response body: ${body}")
			
			.removeHeaders("ae_*")

		;
	}

}
