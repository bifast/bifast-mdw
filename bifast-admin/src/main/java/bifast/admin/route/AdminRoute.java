package bifast.admin.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.admin.pojo.CbAccountEnquiryRequestPojo;
import bifast.admin.pojo.CbAccountEnquiryResponsePojo;
import bifast.admin.pojo.CbCreditRequestPojo;
import bifast.admin.pojo.CbCreditResponsePojo;
import bifast.admin.pojo.CbCustomerInfoRequestPojo;
import bifast.admin.pojo.CbCustomerInfoResponsePojo;
import bifast.admin.pojo.CbDebitRequestPojo;
import bifast.admin.pojo.CbDebitResponsePojo;
import bifast.admin.pojo.CbDebitReversalPojo;
import bifast.admin.processor.AccountEnquiryProcessor;
import bifast.admin.processor.CreditProcessor;
import bifast.admin.processor.DebitProcessor;
import bifast.admin.processor.DebitReversalProcessor;
import bifast.admin.processor.EmailPhoneProcessor;
import bifast.admin.service.JacksonDataFormatService;

@Component
public class AdminRoute extends RouteBuilder {
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private AccountEnquiryProcessor accountEnquiryProcessor;
	@Autowired private DebitProcessor debitProcessor;
	@Autowired private CreditProcessor creditProcessor;
	@Autowired private DebitReversalProcessor debitReversalProcessor;
	@Autowired private EmailPhoneProcessor emailPhoneProcessor;

	@Override
	public void configure() throws Exception {

		JacksonDataFormat acctEnquiryJDF = jdfService.basicPrettyPrint(CbAccountEnquiryRequestPojo.class);
		JacksonDataFormat acctEnquiryResponseJDF = jdfService.basicPrettyPrint(CbAccountEnquiryResponsePojo.class);
		JacksonDataFormat debitRequestJDF = jdfService.basicPrettyPrint(CbDebitRequestPojo.class);
		JacksonDataFormat debitResponseJDF = jdfService.basicPrettyPrint(CbDebitResponsePojo.class);
		JacksonDataFormat debitReversalRequestJDF = jdfService.basicPrettyPrint(CbDebitReversalPojo.class);
		JacksonDataFormat creditRequestJDF = jdfService.basicPrettyPrint(CbCreditRequestPojo.class);
		JacksonDataFormat creditResponseJDF = jdfService.basicPrettyPrint(CbCreditResponsePojo.class);
		JacksonDataFormat customerInfoRequestJDF = jdfService.basicPrettyPrint(CbCustomerInfoRequestPojo.class);
		JacksonDataFormat customerInfoResponseJDF = jdfService.basicPrettyPrint(CbCustomerInfoResponsePojo.class);

		restConfiguration()
			.component("servlet")
		;
		
		rest("/v1/adapter")
		
			.post("/accountinquiry")
				.consumes("application/json")
				.produces("application/json")
				.to("direct:accountinquiry")
					
			.post("/debit")
				.consumes("application/json")
				.produces("application/json")
				.to("direct:debit")
					
			.post("/debitreversal")
				.consumes("application/json")
				.to("direct:debitreversal")
				
			.post("/credit")
				.consumes("application/json")
				.to("direct:credit")
				
			.post("/settlement")
				.consumes("application/json")
				.to("direct:accountinquiry")
				
			.post("/emailphonelist")
				.consumes("application/json")
				.to("direct:emailphonelist")
				
			.post("/balanceinquiry")
				.consumes("application/json")
				.to("direct:accountinquiry")

			;
		
		from("direct:accountinquiry").routeId("accountinquiry")
			.convertBodyTo(String.class)
			.unmarshal(acctEnquiryJDF)
			.process(accountEnquiryProcessor)
			.marshal(acctEnquiryResponseJDF)
		;

		from("direct:debit").routeId("debit")
			.convertBodyTo(String.class)
			.log("manggil debit ${body}")
			.unmarshal(debitRequestJDF)
			.process(debitProcessor)
			.marshal(debitResponseJDF)
		;

		from("direct:debitreversal").routeId("debitreversal")
			.convertBodyTo(String.class)
			.log("manggil debit reversal ${body}")
			.unmarshal(debitReversalRequestJDF)
			.process(debitReversalProcessor)
			.marshal(debitResponseJDF)
		;

		from("direct:credit").routeId("credit")
			.convertBodyTo(String.class)
			.unmarshal(creditRequestJDF)
			.process(creditProcessor)
			.marshal(creditResponseJDF)
		;
		
		from("direct:emailphonelist").routeId("emailphonelist")
			.convertBodyTo(String.class)
			.unmarshal(customerInfoRequestJDF)
			.process(emailPhoneProcessor)
			.marshal(customerInfoResponseJDF)
		;


	}

}
