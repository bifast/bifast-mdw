package bifast.outbound.paymentstatus;

import java.net.SocketTimeoutException;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.FaultProcessor;

@Component
public class PaymentStatusRoute extends RouteBuilder {

	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
//	@Autowired
//	private CheckHistoryProcessor checkHistoryProcessor;
	@Autowired
	private PaymentStatusRequestProcessor paymentStatusRequestProcessor;
//	@Autowired
//	private PaymentStatusResponseProcessor paymentStatusResponseProcessor;
//	@Autowired
//	private FaultProcessor faultProcessor;

	JacksonDataFormat PaymentStatusRequestJDF = new JacksonDataFormat(ChnlPaymentStatusRequestPojo.class);
	JacksonDataFormat PaymentStatusResponseJDF = new JacksonDataFormat(ChnlPaymentStatusResponsePojo.class);
	JacksonDataFormat jsonBusinessMessageFormat = new JacksonDataFormat(BusinessMessage.class);

	@Override
	public void configure() throws Exception {

		PaymentStatusRequestJDF.setInclude("NON_NULL");
		PaymentStatusRequestJDF.setInclude("NON_EMPTY");
		
		PaymentStatusResponseJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		PaymentStatusResponseJDF.setInclude("NON_NULL");
		PaymentStatusResponseJDF.setInclude("NON_EMPTY");

		jsonBusinessMessageFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonBusinessMessageFormat.setInclude("NON_NULL");
		jsonBusinessMessageFormat.setInclude("NON_EMPTY");
		jsonBusinessMessageFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		jsonBusinessMessageFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		
		from ("direct:paymentstatus")
		
			// cek settlement dulu
			.log("di direct:paymentstatus")
			.setHeader("ps_pymtstsreq", simple("${body}"))
			.process(paymentStatusRequestProcessor)
			.marshal(jsonBusinessMessageFormat)
			
			.setHeader("ps_fullresponsemessage", simple("${body}"))
//			.to("seda:savetableawal")

//			.setHeader("req_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.doTry()
//				
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{bifast.ciconnector-url}}?"
						+ "socketTimeout={{bifast.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)

//				.setHeader("hdr_fullresponsemessage", simple("${body}"))
//
//				.setHeader("req_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
//
//				.unmarshal(jsonBusinessMessageFormat)
//				.setHeader("resp_objbi", simple("${body}"))
//				
//				// prepare untuk response ke channel
//				.process(paymentStatusResponseProcessor)
//				.setHeader("resp_channel", simple("${body}"))
//			
			.doCatch(SocketTimeoutException.class)     // klo timeout maka kirim payment status
		    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(504))
		    	.setBody(constant(null))
//				.process(faultProcessor)
//				.setHeader("resintrnRefIdp_channel", simple("${body}"))
//				
			.end()
			
			.log("selesai dotry PS")
//			.setHeader("req_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
//
//			.marshal(PaymentStatusResponseJDF)

			.removeHeaders("ps_*")
		;
			
		
	}

}
