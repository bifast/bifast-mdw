package bifast.mock.route;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.mock.processor.AccountEnquiryResponseProcessor;
import bifast.mock.processor.CreditResponseStoreProcessor;
import bifast.mock.processor.CreditTransferResponseProcessor;
import bifast.mock.processor.FICreditTransferResponseProcessor;
import bifast.mock.processor.OnRequestProcessor;
import bifast.mock.processor.PaymentStatusResponseProcessor;
import bifast.mock.processor.ProxyRegistrationResponseProcessor;
import bifast.mock.processor.ProxyResolutionResponseProcessor;
import bifast.mock.processor.SettlementProcessor;

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
	@Autowired
	private PaymentStatusResponseProcessor paymentStatusResponseProcessor;
	@Autowired
	private ProxyRegistrationResponseProcessor proxyRegistrationResponseProcessor;
	@Autowired
	private ProxyResolutionResponseProcessor proxyResolutionResponseProcessor;
	@Autowired
	private SettlementProcessor settlementProcessor;
	@Autowired
	private CreditResponseStoreProcessor creditResponseStoreProcessor;

	
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
		
		onException(IOException.class)
			.log("Client close connection")
			.handled(true)
		;
		
		restConfiguration()
			.component("servlet")
		;
		
		rest("/")
			.post("/cihub")
				.consumes("application/json")
				.to("direct:receive")
			.post("/cihub-proxy-regitrastion")
				.description("Pengiriman instruksi Proxy Registration BI-FAST")
				.consumes("application/json")
				.to("direct:proxyRegistration")
		;
	

		from("direct:receive").routeId("MsgReceive")

			.setExchangePattern(ExchangePattern.InOut)
			.convertBodyTo(String.class)
			.log("Terima di mock")
			.log("${body}")

			.unmarshal(jsonBusinessMessageDataFormat)
			.setHeader("objRequest", simple("${body}"))
			
			.process(checkMessageTypeProcessor)
			.log("${header.msgType}")

			.setHeader("delay", constant(300))

			.choice()
				.when().simple("${header.msgType} == 'AccountEnquiryRequest'")
					.process(accountEnquiryResponseProcessor)
					
					.choice()
						.when().simple("${header.hdr_account_no} startsWith '77' ")
							.setHeader("delay", simple("${random(2000,3000)}"))
						.otherwise()
							.setHeader("delay", constant(500))
					.endChoice()

				.when().simple("${header.msgType} == 'CreditTransferRequest'")
					.process(creditTransferResponseProcessor)
					.setHeader("hdr_ctResponseObj",simple("${body}"))
					.log("hasil CT1: ${header.hdr_ctResponseObj.appHdr.bizSvc}")
					.marshal(jsonBusinessMessageDataFormat)
					.log("Hasil proses: ${body}")
					.process(creditResponseStoreProcessor)
					.setBody(simple("${header.hdr_ctResponseObj}"))
					.log("hasil CT2: ${header.hdr_ctResponseObj.appHdr.bizSvc}")

					.choice()
						.when().simple("${header.hdr_account_no} startsWith '8' ")
							.setHeader("delay", simple("${random(2000,3000)}"))
					.endChoice()
					.log("hasil CT3: ${header.hdr_ctResponseObj.appHdr.bizSvc}")

					.log("Finish process CT")

					// .setExchangePattern(ExchangePattern.InOnly)
//					.to("seda:settlement")
					// .log("hasil CT4: ${header.hdr_ctResponseObj.appHdr.bizSvc}")

				.when().simple("${header.msgType} == 'FICreditTransferRequest'")
		
					.process(fICreditTransferResponseProcessor)
					.setHeader("hdr_ctResponseObj",simple("${body}"))
					.marshal(jsonBusinessMessageDataFormat)
					.log("Hasil fICreditTransferResponseProcessor: ${body}")
					.process(creditResponseStoreProcessor)
					.setBody(simple("${header.hdr_ctResponseObj}"))
					.log("${header.hdr_ctResponseObj.appHdr.bizSvc}")

					.choice()
						.when().simple("${header.hdr_rcptBank} == 'CITYIDJA' ")
							.setHeader("delay", simple("${random(2000,3000)}"))
						.otherwise()
							.setHeader("delay", constant(500))
					.endChoice()

					// .setExchangePattern(ExchangePattern.InOnly)
					// .to("seda:settlement?exchangePattern=InOnly")

				.when().simple("${header.msgType} == 'PaymentStatusRequest'")
					.log("Akan proses paymentStatusResponseProcessor")
					.process(paymentStatusResponseProcessor)
					
					.choice()
						.when().simple("${body} == null")
							.log("ga nemu payment status")
							.setHeader("delay", simple("${random(2100,3000)}"))
					.endChoice()

				.when().simple("${header.msgType} == 'ReverseCreditTransferRequest'")
					.log("akan proses creditTransferResponseProcessor")
					.process(creditTransferResponseProcessor)

				.when().simple("${header.msgType} == 'ProxyRegistrationRequest'")
					.process(proxyRegistrationResponseProcessor)

				.when().simple("${header.msgType} == 'ProxyResolutionRequest'")
					.log("ProxyResolutionRequest")
					.process(proxyResolutionResponseProcessor)
					.unmarshal(jsonBusinessMessageDataFormat)
				.otherwise()	
					.log("Other message")
			.end()

			.log("hasil CT5: ${body.appHdr.bizSvc}")
			.log("delay ${header.delay}")
			.delay(simple("${header.delay}"))
			.marshal(jsonBusinessMessageDataFormat)  // remark bila rejection

			// .process(proxyResolutionResponseProcessor)
			.log("hasil CT6: ${header.hdr_ctResponseObj.appHdr.bizSvc}")
			.log("Response mock: ${body}")
			.removeHeader("msgType")
			.removeHeaders("delay*")
			.removeHeader("objRequest")
			.removeHeaders("hdr_*")
		;
		

		from("seda:settlement").routeId("Settlement").setExchangePattern(ExchangePattern.InOnly)
			.log("Response: ${header.hdr_ctRespondStatus}")
			.log("hasil CT11: ${body.appHdr.bizSvc}")

			.setHeader("delay_sttl", constant(500))
			// .filter().simple("${header.hdr_account_no} startsWith '88' ")
			// 	.setHeader("delay_sttl", simple("${random(2000,4000)}"))
			// .end()
		
			.filter().simple("${header.hdr_ctRespondStatus} == 'ACTC'")
				.log("Akan proses settlement")
				.marshal(jsonBusinessMessageDataFormat)
				.process(settlementProcessor)
				.log("hasil CT12: ${body.appHdr.bizSvc}")
				.marshal(jsonBusinessMessageDataFormat)

				.log("settlment delay ${header.delay_sttl}")
				.delay(simple("${header.delay_sttl}"))
				.to("rest:post:inbound?host={{komi.inbound-url}}&"
						+ "bridgeEndpoint=true")
				.log("kirim sttl: ${body}")
			.end()
			
			.removeHeaders("hdr_*")
		;
		
		from("direct:proxyRegistration").routeId("proxyRegistration")

			.setExchangePattern(ExchangePattern.InOut)
			.convertBodyTo(String.class)
			.log("Terima di mock")
			.log("${body}")
			.delay(500)
			.log("end-delay")
			.unmarshal(jsonBusinessMessageDataFormat)
			.process(proxyRegistrationResponseProcessor)
			.marshal(jsonBusinessMessageDataFormat)  // remark bila rejection
			.log("Response dari mock")

			.removeHeader("msgType")
			
		;
	}

}
