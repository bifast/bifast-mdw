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
import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryResponsePojo;
import bifast.outbound.pojo.ChannelFaultResponse;
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
	private TimoutFaultProcessor timeoutFaultProcessor;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;

	@Autowired
	private SaveAETablesProcessor saveAETableProcessor;
	@Autowired
	private ValidateAEInputProcessor validateInputProcessor;

	JacksonDataFormat jsonChnlAccountEnqrRespFormat = new JacksonDataFormat(ChnlAccountEnquiryResponsePojo.class);
	JacksonDataFormat businessMessageJDF = new JacksonDataFormat(BusinessMessage.class);
	JacksonDataFormat faultJDF = new JacksonDataFormat(ChannelFaultResponse.class);
	JacksonDataFormat chnlResponseJDF = new JacksonDataFormat(ChannelResponseWrapper.class);


	private void configureJsonDataFormat() {

		jsonChnlAccountEnqrRespFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonChnlAccountEnqrRespFormat.setInclude("NON_NULL");
		jsonChnlAccountEnqrRespFormat.setInclude("NON_EMPTY");
		jsonChnlAccountEnqrRespFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

		businessMessageJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		businessMessageJDF.setInclude("NON_NULL");
		businessMessageJDF.setInclude("NON_EMPTY");
		businessMessageJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		businessMessageJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		
		faultJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		faultJDF.setInclude("NON_NULL");
		faultJDF.setInclude("NON_EMPTY");
		faultJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

//		chnlResponseJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		chnlResponseJDF.setInclude("NON_NULL");
		chnlResponseJDF.setInclude("NON_EMPTY");
//		chnlResponseJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

	}
	
	
	@Override
	public void configure() throws Exception {

		configureJsonDataFormat();

        onException(Exception.class).routeId("AE Exception Handler")
        	.log("Fault di AE Excp, ${header.hdr_errorlocation}")
	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
			.process(faultProcessor)
			.to("sql:update outbound_message set resp_status = 'ERROR-KM', "
					+ " error_msg= :#${body.faultResponse.reason} "
					+ "where id= :#${header.hdr_idtable}  ")
			.marshal(chnlResponseJDF)
			.removeHeaders("hdr_*")
			.removeHeaders("req_*")
			.removeHeaders("ae_*")
			.removeHeader("HttpMethod")
	    	.handled(true)
      	;

		from("direct:acctenqr").routeId("direct:acctenqr")
			
			.log("direct:acctenqr")
			.setHeader("hdr_errorlocation", constant("AERoute/Cekpoint 1"))
			.process(validateInputProcessor)
			
			.setHeader("hdr_errorlocation", constant("AERoute/AEMsgConstructProcessor"))
			.process(AEMsgConstructProcessor)
			.setHeader("req_objbi", simple("${body}"))
			
			.marshal(businessMessageJDF)
			.setHeader("hdr_errorlocation", constant("AERoute/saveTabelInit"))

			.to("seda:encryptAEbody")
			.to("seda:saveAEtables")
			
			
			// kirim ke CI-HUB
			.setHeader("req_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.doTry()
				.setHeader("hdr_errorlocation", constant("AERoute/sendCIHub"))
				.log("akan call cihub")
	
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{bifast.ciconnector-url}}?"
						+ "socketTimeout={{bifast.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)
				.to("seda:encryptAEbody")
				.log("selesai call cihub")
				.setHeader("hdr_fullresponsemessage", simple("${body}"))
				.setHeader("req_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
	
				.setHeader("hdr_errorlocation", constant("AERoute/processResponse"))
	
				.unmarshal(businessMessageJDF)
				.setHeader("resp_objbi", simple("${body}"))
				
				// prepare untuk response ke channel
				.setHeader("hdr_errorlocation", constant("AERoute/ResponseProcessor"))
				.process(accountEnqrResponseProcessor)
				.marshal(chnlResponseJDF)
//				.setHeader("resp_channel", simple("${body}"))

				.setHeader("req_channelResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))

				// save audit tables
				.setHeader("hdr_errorlocation", constant("AERoute/updateTable"))
				.to("seda:saveAEtables?exchangePattern=InOnly")

			.doCatch(SocketTimeoutException.class)     // klo timeout maka kirim payment status
	    		.log(LoggingLevel.ERROR, "Timeout process Account Enquiry ref:${header.hdr_channelRequest.channelRefId}")
		    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(504))
				.to("sql:update outbound_message set resp_status = 'TIMEOUT', "
						+ " error_msg= 'Timeout' "
						+ "where id= :#${header.hdr_idtable}  ")

				.process(timeoutFaultProcessor)
				.marshal(chnlResponseJDF)
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
		from("seda:saveAEtables")
			.process(saveAETableProcessor)
		;

		
	}

}
