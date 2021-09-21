package bifast.outbound.accountenquiry;

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
import bifast.outbound.accountenquiry.processor.AccountEnquiryRequestProcessor;
import bifast.outbound.accountenquiry.processor.AccountEnquiryResponseProcessor;
import bifast.outbound.accountenquiry.processor.SaveAccountEnquiryProcessor;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.FaultResponseProcessor;

@Component
public class AccountEnquiryRoute extends RouteBuilder{

	@Autowired
	private AccountEnquiryRequestProcessor buildAccountEnquiryRequestProcessor;
	@Autowired
	private AccountEnquiryResponseProcessor accountEnqrResponseProcessor;
	@Autowired
	private FaultResponseProcessor faultProcessor;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	@Autowired
	private SaveAccountEnquiryProcessor saveAccountEnquiryProcessor;

	JacksonDataFormat businessMessageJDF = new JacksonDataFormat(BusinessMessage.class);
	JacksonDataFormat chnlResponseJDF = new JacksonDataFormat(ChannelResponseWrapper.class);


	private void configureJsonDataFormat() {

		businessMessageJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		businessMessageJDF.setInclude("NON_NULL");
		businessMessageJDF.setInclude("NON_EMPTY");
		businessMessageJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		businessMessageJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		
		chnlResponseJDF.setInclude("NON_NULL");
		chnlResponseJDF.setInclude("NON_EMPTY");

	}
	
	
	@Override
	public void configure() throws Exception {

		configureJsonDataFormat();

        onException(Exception.class).routeId("AE Exception Handler")
			.log(LoggingLevel.ERROR, "[ChRefId:${header.hdr_chnlRefId}][AE] terjadi error")
	    	.handled(true)
	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
			.process(faultProcessor)
			.to("sql:update channel_transaction set status = 'ERROR-KM', "
					+ " error_msg= :#${body.faultResponse.reason} "
					+ " where id= :#${header.hdr_chnlTable_id}  ")
			.marshal(chnlResponseJDF)
			.removeHeaders("hdr_*")
			.removeHeaders("req_*")
			.removeHeaders("ae_*")
			.removeHeader("HttpMethod")
      	;

		from("direct:acctenqr").routeId("AccountEnquiryRoute")
			
			.setHeader("ae_channelRequest", simple("${body}"))

			.setHeader("hdr_errorlocation", constant("AERoute/AEMsgConstructProcessor"))
			.process(buildAccountEnquiryRequestProcessor)
			.setHeader("ae_objreq_bi", simple("${body}"))
			
			.marshal(businessMessageJDF)
			
			.to("seda:encryptAEbody") 
			.setHeader("ae_encrypt_request", simple("${header.ae_encrMessage}"))
				
			// kirim ke CI-HUB
			.setHeader("ae_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.log(LoggingLevel.DEBUG, "bifast.outbound.accountenquiry", "[ChRefId:${header.hdr_chnlRefId}][AE:${header.ae_objreq_bi.appHdr.bizMsgIdr}] call CI-HUB start.")
			.doTry()
				.setHeader("hdr_errorlocation", constant("AERoute - call CIHub"))
	
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{komi.ciconnector-url}}?"
						+ "socketTimeout={{komi.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)

				.log(LoggingLevel.DEBUG, "bifast.outbound.accountenquiry", "[ChRefId:${header.hdr_chnlRefId}][AE:${header.ae_objreq_bi.appHdr.bizMsgIdr}] call CI-HUB finish.")

				.setHeader("ae_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))

				.to("seda:encryptAEbody")
				.setHeader("ae_encrypt_response", simple("${header.ae_encrMessage}"))
	
				.unmarshal(businessMessageJDF)
				.setHeader("ae_objresp_bi", simple("${body}"))
				
				// prepare untuk response ke channel
				.setHeader("hdr_errorlocation", constant("AERoute/ResponseProcessor"))
				.process(accountEnqrResponseProcessor)
				// save audit tables
				.to("seda:saveAEtables?exchangePattern=InOnly")

			.doCatch(SocketTimeoutException.class)     // klo timeout maka kirim payment status
				.log(LoggingLevel.ERROR, "[ChRefId:${header.hdr_chnlRefId}][AE:${header.ae_objreq_bi.appHdr.bizMsgIdr}] call CI-HUB timeout.")
		    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(504))
		    	.setHeader("hdr_error_status", constant("TIMEOUT-CIHUB"))
		    	.setHeader("hdr_error_mesg", constant("Timeout waiting response from CI-HUB"))
				.setHeader("ae_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
				.process(faultProcessor)
				.to("seda:saveAEtables?exchangePattern=InOnly")
			.doCatch(Exception.class)     // klo timeout maka kirim payment status
				.log(LoggingLevel.ERROR, "[ChRefId:${header.hdr_chnlRefId}][AE:${header.ae_objreq_bi.appHdr.bizMsgIdr}] call CI-HUB generic error.")
		    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))		    	
				.setHeader("ae_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
		    	.setHeader("hdr_error_status", constant("ERROR-CIHUB"))
		    	.setHeader("hdr_error_mesg", simple("${exception.message}"))
				.process(faultProcessor)
				.to("seda:saveAEtables?exchangePattern=InOnly")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")

			.endDoTry().end()
				
			.setHeader("req_channelResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))

			.removeHeaders("ae_*")
		;

		from("seda:encryptAEbody")
			.setHeader("ae_tmp", simple("${body}"))
			.marshal().zipDeflater()
			.marshal().base64()
			.setHeader("ae_encrMessage", simple("${body}"))
			.setBody(simple("${header.ae_tmp}"))
			.removeHeader("ae_tmp")
		;
		from("seda:saveAEtables").routeId("saveAEtables")
			.setHeader("hdr_errorlocation", constant("AERoute/saveTabel"))
			.process(saveAccountEnquiryProcessor)
		;

		
	}

}
