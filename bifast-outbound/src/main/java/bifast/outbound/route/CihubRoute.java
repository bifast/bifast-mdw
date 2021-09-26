package bifast.outbound.route;

import java.net.SocketTimeoutException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.accountenquiry.processor.SaveAccountEnquiryProcessor;
import bifast.outbound.credittransfer.processor.StoreCreditTransferProcessor;
import bifast.outbound.ficredittransfer.processor.SaveFICreditTransferProcessor;
import bifast.outbound.paymentstatus.StorePaymentStatusProcessor;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.SetTransactionTypeProcessor;

@Component
public class CihubRoute extends RouteBuilder {

	JacksonDataFormat businessMessageJDF = new JacksonDataFormat(BusinessMessage.class);

	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	@Autowired
	private SetTransactionTypeProcessor setTransactionTypeProcessor;
	@Autowired
	private SaveAccountEnquiryProcessor saveAccountEnquiryProcessor;
	@Autowired
	private StoreCreditTransferProcessor storeCreditTransferProcessor;
	@Autowired
	private StorePaymentStatusProcessor storePaymentStatusProcessor;
	@Autowired
	private SaveFICreditTransferProcessor saveFICreditTransferProcessor;

	@Override
	public void configure() throws Exception {
		
		businessMessageJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		businessMessageJDF.setInclude("NON_NULL");
		businessMessageJDF.setInclude("NON_EMPTY");
		businessMessageJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		businessMessageJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		

		// ** ROUTE GENERAL UNTUK POSTING KE CI-HUB ** //
		from("direct:call-cihub").routeId("komi.call-cihub")
		
			.process(setTransactionTypeProcessor)
			.log("${header.hdr_trxname}")		
			
			.setHeader("hdr_cihub_request", simple("${body}"))

			.marshal(businessMessageJDF)

			.log(LoggingLevel.DEBUG, "komi.call-cihub", "[ChRefId:${header.hdr_chnlRefId}] Post ${header.hdr_trxname} request: ${body}")
	
			.setHeader("tmp_body", simple("${body}"))
			.marshal().zipDeflater()
			.marshal().base64()

			.setHeader("cihubroute_encr_request", simple("${body}"))
			.setBody(simple("${header.tmp_body}"))
			
			.setHeader("hdr_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
	
			.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{komi.ciconnector-url}}?"
						+ "socketTimeout={{komi.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)				
				.log(LoggingLevel.DEBUG, "komi.call-cihub", "[ChRefId:${header.hdr_chnlRefId}] CI-HUB response: ${body}")
	
				.setHeader("tmp_body", simple("${body}"))
				.marshal().zipDeflater()
				.marshal().base64()
				.setHeader("cihubroute_encr_response", simple("${body}"))
				.setBody(simple("${header.tmp_body}"))
	
				.unmarshal(businessMessageJDF)
				.setHeader("hdr_error_status", constant(null))
	
			.doCatch(SocketTimeoutException.class)
				.log(LoggingLevel.ERROR, "[ChRefId:${header.hdr_chnlRefId}] Call CI-HUB Timeout")
				.setHeader("hdr_error_status", constant("TIMEOUT-CIHUB"))
				.setHeader("hdr_error_mesg", constant("Timeout menunggu response dari CIHUB"))
		    	.setBody(constant(null))
	
	    	.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[ChRefId:${header.hdr_chnlRefId}] Call CI-HUB Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
				.setHeader("hdr_error_status", constant("ERROR-CIHUB"))
				.setHeader("hdr_error_mesg", simple("${exception.message}"))
		    	.setBody(constant(null))
	
			.endDoTry()
			.end()
	
			.setHeader("hdr_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.setHeader("hdr_cihub_response", simple("${body}"))
			
			.choice()
				.when().simple("${header.hdr_trxname} == 'Account Enquiry'")
					.process(saveAccountEnquiryProcessor)
				.when().simple("${header.hdr_trxname} == 'Credit Transfer'")
					.process(storeCreditTransferProcessor)
				.when().simple("${header.hdr_trxname} == 'FI to FI Credit Transfer'")
					.process(saveFICreditTransferProcessor)
				.when().simple("${header.hdr_trxname} == 'Payment Status'")
					.process(storePaymentStatusProcessor)
			.end()

			.setHeader("hdr_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			
			.removeHeaders("tmp_*")
			.removeHeaders("cihubroute_*")
		;

	
	}		
}
