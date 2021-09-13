package bifast.outbound.reversect;

import java.net.SocketTimeoutException;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.paymentstatus.PaymentStatusRequestProcessor;
import bifast.outbound.processor.EnrichmentAggregator;

//@Component
public class ReversalRoute extends RouteBuilder {

	@Autowired
	private ReverseCreditTrnRequestProcessor reverseCTRequestProcessor;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
//	@Autowired
//	private CheckHistoryProcessor checkSettlementProcessor;
	@Autowired
	private PaymentStatusRequestProcessor paymentStatusTimeoutProcessor;

	JacksonDataFormat jsonBusinessMessageFormat = new JacksonDataFormat(BusinessMessage.class);

	@Override
	public void configure() throws Exception {

		jsonBusinessMessageFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonBusinessMessageFormat.setInclude("NON_NULL");
		jsonBusinessMessageFormat.setInclude("NON_EMPTY");
		jsonBusinessMessageFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		jsonBusinessMessageFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		from("sql:select * "
				+ "from CREDIT_TRANSFER ct "
				+ "where ct.reversal = 'PENDING'?"
					+ "outputType=SelectList&"
					+ "outputHeader=rcv_qryresult&"
					+ "maxMessagesPerPoll=3")
			.routeId("QueryReversal")

			.setHeader("req_channelRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))

			.process(reverseCTRequestProcessor)
			.setHeader("req_objbi", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)

			//log message ke ci-hub
			.setHeader("log_label", constant("CI-Hub Request"))
			.to("seda:savelogfiles?exchangePattern=InOnly")

			// kirim ke CI-HUB
			.setHeader("req_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.doTry()
				.log("Submit Reversal CT no: ${header.req_objbi.appHdr.bizMsgIdr}")
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{bifast.ciconnector-url}}?"
						+ "socketTimeout={{bifast.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)
				
			.doCatch(SocketTimeoutException.class)     // klo timeout maka kirim payment status
				.log("Timeout")
				.setHeader("log_label", constant("TIMEOUT"))
				.to("seda:savelogfiles?exchangePattern=InOnly")

				// cek settlement dulu, kalo ga ada baru payment status
//				.process(checkSettlementProcessor)
				.choice()
					.when().simple("${body} == ''")
						.log("Payment Status")
						.process(paymentStatusTimeoutProcessor)
						.marshal(jsonBusinessMessageFormat)

						// log message dari ci-hub
						.setHeader("log_label", constant("Payment Status Request"))
						.to("seda:savelogfiles?exchangePattern=InOnly")

						.setHeader("HttpMethod", constant("POST"))
						.enrich("http:{{bifast.ciconnector-url}}?bridgeEndpoint=true", enrichmentAggregator)
						.convertBodyTo(String.class)

						// log message dari ci-hub
						.setHeader("log_label", constant("CI-Hub Response"))
						.to("seda:savelogfiles?exchangePattern=InOnly")

					.otherwise()
						.log("Nemu settlement")
						// log message dari ci-hub
						.setHeader("log_label", constant("Settlement"))
						.to("seda:savelogfiles?exchangePattern=InOnly")

				.end()
			.endDoTry()
			.end()
			.setHeader("req_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			
			.unmarshal(jsonBusinessMessageFormat)
			.setHeader("resp_objbi", simple("${body}"))	

			.setHeader("req_channelResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))

			// save audit tables
			.setHeader("rcv_msgType", constant("reversal"))
			.to("seda:savetables?exchangePattern=InOnly")

			.to("sql:update CREDIT_TRANSFER set reversal = 'DONE' "
					+ "where id = :#${header.rcv_qryresult[id]}")
			
			.removeHeaders("req_*")
			.removeHeader("rcv_*")
		;

		
	}

}
