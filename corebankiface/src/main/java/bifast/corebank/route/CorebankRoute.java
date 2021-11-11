package bifast.corebank.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.corebank.accountenquiry.AccountEnquiryProcessor;
import bifast.corebank.accountenquiry.CbAccountEnquiryResponsePojo;
import bifast.corebank.balance.AccountBalanceProcessor;
import bifast.corebank.balance.CbAccountBalanceResponsePojo;
import bifast.corebank.credit.CbCreditResponsePojo;
import bifast.corebank.credit.CreditProcessor;
import bifast.corebank.customerinfo.CbCustomerInfoResponsePojo;
import bifast.corebank.customerinfo.CustomerInfoProcessor;
import bifast.corebank.debit.CbDebitResponsePojo;
import bifast.corebank.debit.DebitProcessor;
import bifast.corebank.debitreversal.CbDebitReversalResponsePojo;
import bifast.corebank.debitreversal.DebitReversalProcessor;
import bifast.corebank.pojo.InputMessageWrapper;
import bifast.corebank.service.JacksonDataFormatService;
import bifast.corebank.settlement.CbSettlementResponsePojo;
import bifast.corebank.settlement.SettlementProcessor;

@Component
public class CorebankRoute extends RouteBuilder{

	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private AccountEnquiryProcessor accountEnquiryProc;
	@Autowired private AccountBalanceProcessor accountBalanceProc;
	@Autowired private CustomerInfoProcessor custInfoProc;
	@Autowired private CreditProcessor creditProc;
	@Autowired private DebitProcessor debitProc;
	@Autowired private DebitReversalProcessor debitReversalProc;
	@Autowired private SettlementProcessor settlementProc;

	@Override
	public void configure() throws Exception {

		JacksonDataFormat inputMessageJDF = jdfService.basic(InputMessageWrapper.class);
		JacksonDataFormat accountEnquiryResponseJDF = jdfService.basic(CbAccountEnquiryResponsePojo.class);
		JacksonDataFormat balanceResponseJDF = jdfService.basic(CbAccountBalanceResponsePojo.class);
		JacksonDataFormat creditResponseJDF = jdfService.basic(CbCreditResponsePojo.class);
		JacksonDataFormat debitResponseJDF = jdfService.basic(CbDebitResponsePojo.class);
		JacksonDataFormat debitReversalResponseJDF = jdfService.basic(CbDebitReversalResponsePojo.class);
		JacksonDataFormat settlementResponseJDF = jdfService.basic(CbSettlementResponsePojo.class);
		JacksonDataFormat custInfoResponseJDF = jdfService.basic(CbCustomerInfoResponsePojo.class);

		restConfiguration().component("servlet");
		
		rest("/mock")
			.post("/corebank")
				.consumes("application/json")
				.to("direct:cbservice")
		;

		from("direct:cbservice").routeId("komi.corebankRoute")
			.convertBodyTo(String.class)
			
			.unmarshal(inputMessageJDF)
			
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					InputMessageWrapper imw = exchange.getMessage().getBody(InputMessageWrapper.class);
					Object inputRequest = null;
					if (null != imw.getAccountBalanceRequest())
						inputRequest = imw.getAccountBalanceRequest();
					else if (null != imw.getAccountEnquiryRequest())
						inputRequest = imw.getAccountEnquiryRequest();
					else if (null != imw.getCreditRequest())
						inputRequest = imw.getCreditRequest();
					else if (null != imw.getCustomerInfoRequest())
						inputRequest = imw.getCustomerInfoRequest();
					else if (null != imw.getDebitRequest())
						inputRequest = imw.getDebitRequest();
					else if (null != imw.getDebitReversalRequest())
						inputRequest = imw.getDebitReversalRequest();
					else if (null != imw.getSettlement())
						inputRequest = imw.getSettlement();
					exchange.getMessage().setBody(inputRequest);
				}
			})
			
			.choice()
				.when().simple("${body.class} endsWith 'CbAccountEnquiryRequestPojo'")
					.process(accountEnquiryProc)
					.marshal(accountEnquiryResponseJDF)
				.when().simple("${body.class} endsWith 'CbCustomerInfoRequestPojo'")
					.process(custInfoProc)
					.marshal(custInfoResponseJDF)
				.when().simple("${body.class} endsWith 'CbAccountBalanceRequestPojo'")
					.process(accountBalanceProc)
					.marshal(balanceResponseJDF)
				.when().simple("${body.class} endsWith 'CbCreditRequestPojo'")
					.process(creditProc)
					.marshal(creditResponseJDF)
				.when().simple("${body.class} endsWith 'CbDebitRequestPojo'")
					.process(debitProc)
					.marshal(debitResponseJDF)
				.when().simple("${body.class} endsWith 'CbDebitReversalRequestPojo'")
					.process(debitReversalProc)
					.marshal(debitReversalResponseJDF)
				.when().simple("${body.class} endsWith 'CbSettlementRequestPojo'")
					.process(settlementProc)
					.marshal(settlementResponseJDF)
			.end()
					
			.removeHeaders("*")
		;

		
	}

}