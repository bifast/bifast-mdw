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
import bifast.outbound.accountenquiry.processor.AccountEnquiryMsgConstructProcessor;
import bifast.outbound.accountenquiry.processor.AccountEnquiryResponseProcessor;
import bifast.outbound.accountenquiry.processor.SaveAETablesProcessor;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.FaultProcessor;
import bifast.outbound.processor.TimoutFaultProcessor;

@Component
public class AccountEnquiryRoute extends RouteBuilder{

	@Autowired
	private AccountEnquiryMsgConstructProcessor AEMsgConstructProcessor;
	@Autowired
	private AccountEnquiryResponseProcessor accountEnqrResponseProcessor;
	@Autowired
	private FaultProcessor faultProcessor;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	@Autowired
	private TimoutFaultProcessor timeoutFaultProcessor;
	@Autowired
	private SaveAETablesProcessor saveAETableProcessor;

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
        	.log("AE Excp, ${header.hdr_errorlocation}")
	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
			.process(faultProcessor)
			.to("sql:update outbound_message set resp_status = 'ERROR-KM', "
					+ " error_msg= :#${body.faultResponse.reason} "
					+ "where id= :#${header.ae_table_id}  ")
			.marshal(chnlResponseJDF)
			.removeHeaders("hdr_*")
			.removeHeaders("req_*")
			.removeHeaders("ae_*")
			.removeHeader("HttpMethod")
	    	.handled(true)
      	;

		from("direct:acctenqr").routeId("direct:acctenqr")
			
			.setHeader("ae_channelRequest", simple("${body}"))

			.setHeader("hdr_errorlocation", constant("AERoute/AEMsgConstructProcessor"))
			.process(AEMsgConstructProcessor)
//			.setHeader("req_objbi", simple("${body}"))
			.setHeader("ae_objreq_bi", simple("${body}"))
			
			.marshal(businessMessageJDF)
			
			.to("seda:encryptAEbody")
			.to("seda:saveAEtables")
			
			
			// kirim ke CI-HUB
			.setHeader("req_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.doTry()
				.setHeader("hdr_errorlocation", constant("AERoute/sendCIHub"))
	
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{bifast.ciconnector-url}}?"
						+ "socketTimeout={{bifast.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)
				.to("seda:encryptAEbody")

				.setHeader("hdr_fullresponsemessage", simple("${body}"))
				.setHeader("req_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
		
				.unmarshal(businessMessageJDF)
				.setHeader("resp_objbi", simple("${body}"))
				
				// prepare untuk response ke channel
				.setHeader("hdr_errorlocation", constant("AERoute/ResponseProcessor"))
				.process(accountEnqrResponseProcessor)

				.setHeader("req_channelResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))

				// save audit tables
				.to("seda:saveAEtables?exchangePattern=InOnly")

			.doCatch(SocketTimeoutException.class)     // klo timeout maka kirim payment status
	    		.log(LoggingLevel.ERROR, "[AE][ChnlRefId:${header.hdr_chnlRefId}] timeout")
		    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(504))
				.to("sql:update outbound_message set resp_status = 'TIMEOUT', "
						+ " error_msg= 'Timeout' "
						+ "where id= :#${header.ae_table_id}  ")

				.process(timeoutFaultProcessor)
			.endDoTry().end()
				
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
			.process(saveAETableProcessor)
			.setHeader("ae_table_id", simple("${header.hdr_idtable}"))
		;

		
	}

}
