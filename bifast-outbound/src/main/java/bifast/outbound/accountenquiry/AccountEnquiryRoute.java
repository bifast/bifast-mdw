package bifast.outbound.accountenquiry;

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
import bifast.outbound.pojo.ChannelFaultResponse;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.FaultProcessor;

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

	JacksonDataFormat jsonChnlAccountEnqrRespFormat = new JacksonDataFormat(ChnlAccountEnquiryResp.class);
	JacksonDataFormat businessMessageJDF = new JacksonDataFormat(BusinessMessage.class);
	JacksonDataFormat faultJDF = new JacksonDataFormat(ChannelFaultResponse.class);

	@Autowired
	private SaveAETablesProcessor saveAETableProcessor;
	@Autowired
	private ValidateAEInputProcessor validateInputProcessor;
	
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

	}
	
	
	@Override
	public void configure() throws Exception {

		configureJsonDataFormat();

        onException(Exception.class).routeId("AE Exception Handler")
        	.log("Fault di AE Excp, ${header.hdr_errorlocation}")
	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
			.process(faultProcessor)
			.to("sql:update outbound_message set resp_status = 'ERROR', "
					+ " error_msg= :#${body.reason} "
					+ "where id= :#${header.hdr_idtable}  ")
			.marshal(faultJDF)
			.removeHeaders("hdr_*")
			.removeHeaders("req_*")
	    	.handled(true)
      	;

		from("direct:acctenqr").routeId("direct:acctenqr")
			
			.log("direct:acctenqr")
			.log("${body}")
			.setHeader("hdr_errorlocation", constant("AERoute/Cekpoint 1"))
			.process(validateInputProcessor)
			
			.setHeader("hdr_errorlocation", constant("AERoute/AEMsgConstructProcessor"))
			.process(AEMsgConstructProcessor)
			.setHeader("req_objbi", simple("${body}"))
			.marshal(businessMessageJDF)

			.setHeader("hdr_errorlocation", constant("AERoute/saveTabelInit"))
			.to("seda:saveAEtables")
			
			
			// kirim ke CI-HUB
			.setHeader("req_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
//			.doTry()
				
			.setHeader("hdr_errorlocation", constant("AERoute/sendCIHub"))

			.setHeader("HttpMethod", constant("POST"))
			.enrich("http:{{bifast.ciconnector-url}}?"
					+ "socketTimeout={{bifast.timeout}}&" 
					+ "bridgeEndpoint=true",
					enrichmentAggregator)
			.convertBodyTo(String.class)
			.setHeader("hdr_fullresponsemessage", simple("${body}"))

			.setHeader("hdr_errorlocation", constant("AERoute/Cekpoint 12"))

			.unmarshal(businessMessageJDF)
			.setHeader("resp_objbi", simple("${body}"))
			
			// prepare untuk response ke channel
			.setHeader("hdr_errorlocation", constant("AERoute/ProcessResponse"))
			.process(accountEnqrResponseProcessor)
			.setHeader("resp_channel", simple("${body}"))
			
//			.doCatch(SocketTimeoutException.class)     // klo timeout maka kirim payment status
//	    		.log(LoggingLevel.ERROR, "Timeout process Account Enquiry ref:${header.rcv_channel.orignReffId}")
//		    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(504))
//				.process(faultProcessor)
//				.setHeader("resintrnRefIdp_channel", simple("${body}"))
//	
//			.end()
			.setHeader("req_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
	
			.marshal(jsonChnlAccountEnqrRespFormat)
			.setHeader("hdr_errorlocation", constant("AERoute/Cekpoint 20"))
			
			.setHeader("req_channelResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))

			// save audit tables
			.setHeader("hdr_errorlocation", constant("AERoute/updateTable"))
			.to("seda:saveAEtables?exchangePattern=InOnly")

		;

		from("seda:saveAEtables")
			.setHeader("hdr_tmp", simple("${body}"))
			.marshal().zipDeflater()
			.marshal().base64()
			.setHeader("hdr_encrMessage", simple("${body}"))
			.process(saveAETableProcessor)
			.setBody(simple("${header.hdr_tmp}"))
			.removeHeader("hdr_tmp")
			;

	}

}
