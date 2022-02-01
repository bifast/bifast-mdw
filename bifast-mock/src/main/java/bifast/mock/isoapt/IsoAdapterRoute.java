package bifast.mock.isoapt;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.mock.isoapt.pojo.AccountEnquiryRequest;
import bifast.mock.isoapt.pojo.AccountEnquiryResponse;
import bifast.mock.isoapt.pojo.CreditRequest;
import bifast.mock.isoapt.pojo.CreditResponse;
import bifast.mock.isoapt.pojo.SettlementRequest;
import bifast.mock.isoapt.pojo.SettlementResponse;


@Component
public class IsoAdapterRoute extends RouteBuilder {
	@Autowired private CreditRequestProcessor creditRequestPrc;
	@Autowired private AccountEnquiryProcessor accountEnquiryPrc;
	@Autowired private SettlementRequestProcessor settlementRequestPrc;

	JacksonDataFormat aeRequestJDF = new JacksonDataFormat(AccountEnquiryRequest.class);
	JacksonDataFormat aeResponseJDF = new JacksonDataFormat(AccountEnquiryResponse.class);
	JacksonDataFormat creditRequestJDF = new JacksonDataFormat(CreditRequest.class);
	JacksonDataFormat creditResponseJDF = new JacksonDataFormat(CreditResponse.class);
	JacksonDataFormat settlementRequestJDF = new JacksonDataFormat(SettlementRequest.class);
	JacksonDataFormat settlementResponseJDF = new JacksonDataFormat(SettlementResponse.class);

	@Override
	public void configure() throws Exception {
		
		creditRequestJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		creditRequestJDF.setInclude("NON_NULL");
		creditRequestJDF.setInclude("NON_EMPTY");
		creditResponseJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		creditResponseJDF.setInclude("NON_NULL");
		creditResponseJDF.setInclude("NON_EMPTY");
		settlementRequestJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		settlementRequestJDF.setInclude("NON_NULL");
		settlementRequestJDF.setInclude("NON_EMPTY");
		settlementResponseJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		settlementResponseJDF.setInclude("NON_NULL");
		settlementResponseJDF.setInclude("NON_EMPTY");

		restConfiguration()
			.component("servlet")
		;
		
		rest("/adapter")
			.post("/accountinquiry")
				.consumes("application/json")
				.to("direct:accountenquiry")
			.post("/credit")
				.consumes("application/json")
				.to("direct:credit")
			.post("/settlement")
				.consumes("application/json")
				.to("direct:settlement")
		;
	
		from("direct:accountenquiry").routeId("accountenquiry")
			.convertBodyTo(String.class)
			.unmarshal(aeRequestJDF)
			.process(accountEnquiryPrc)
			.marshal(aeResponseJDF)
			;

		from("direct:credit").routeId("credit")
			.convertBodyTo(String.class)
			.unmarshal(creditRequestJDF)
			.process(creditRequestPrc)
			.marshal(creditResponseJDF)
			;

		from("direct:settlement").routeId("settlement2")
			.convertBodyTo(String.class)
			.unmarshal(settlementRequestJDF)
			.process(settlementRequestPrc)
			.marshal(settlementResponseJDF)
			;

	}

}
