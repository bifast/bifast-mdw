package bifast.outbound.route;

import java.net.SocketTimeoutException;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.pojo.ChannelResponseMessage;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.FaultProcessor;
import bifast.outbound.proxyregistration.ChannelProxyRegistrationReq;
import bifast.outbound.proxyregistration.ChannelProxyResolutionReq;
import bifast.outbound.proxyregistration.ProxyRegistrationRequestProcessor;
import bifast.outbound.proxyregistration.ProxyRegistrationResponseProcessor;
import bifast.outbound.proxyregistration.ProxyResolutionRequestProcessor;
import bifast.outbound.proxyregistration.ProxyResolutionResponseProcessor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

@Component
public class ProxyRoute extends RouteBuilder {

	@Autowired
	private ProxyRegistrationRequestProcessor proxyRegistrationRequestProcessor;
	@Autowired
	private ProxyRegistrationResponseProcessor proxyRegistrationResponseProcessor;
	@Autowired
	private ProxyResolutionRequestProcessor proxyResolutionRequestProcessor;
	@Autowired
	private ProxyResolutionResponseProcessor proxyResolutionResponseProcessor;
	@Autowired
	private FaultProcessor faultProcessor;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;

	JacksonDataFormat jsonChnlResponseFormat = new JacksonDataFormat(ChannelResponseMessage.class);
	JacksonDataFormat jsonChnlProxyRegistrationFormat = new JacksonDataFormat(ChannelProxyRegistrationReq.class);
	JacksonDataFormat jsonChnlProxyResolutionFormat = new JacksonDataFormat(ChannelProxyResolutionReq.class);

	JacksonDataFormat jsonBusinessMessageFormat = new JacksonDataFormat(BusinessMessage.class);

	private void configureJsonDataFormat() {

		jsonChnlProxyRegistrationFormat.setInclude("NON_NULL");
		jsonChnlProxyRegistrationFormat.setInclude("NON_EMPTY");
		
		jsonChnlResponseFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonChnlResponseFormat.setInclude("NON_NULL");
		jsonChnlResponseFormat.setInclude("NON_EMPTY");
//		jsonChnlResponseFormat.setPrettyPrint(true);
		jsonChnlResponseFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

		jsonBusinessMessageFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonBusinessMessageFormat.setInclude("NON_NULL");
		jsonBusinessMessageFormat.setInclude("NON_EMPTY");
		jsonBusinessMessageFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		jsonBusinessMessageFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
	}
	
	@Override
	public void configure() throws Exception {

		configureJsonDataFormat();
		
		onException(SocketTimeoutException.class)
			.log("Timeout Exception")
		    .handled(true)
		    .process(faultProcessor)
			.marshal(jsonChnlResponseFormat)
			.removeHeaders("resp_*")
			.removeHeaders("req_*")
			.removeHeaders("log_*")
		;
		
		restConfiguration().component("servlet")
        	.apiContextPath("/api-doc")
	            .apiProperty("api.title", "KOMI API Documentation").apiProperty("api.version", "1.0.0")
	            .apiProperty("cors", "true");

        ;
		
		// Untuk Proses Proxy Registration Request

		from("direct:proxyregistration").routeId("proxyregistration")

			.setHeader("log_filename", simple("prxyrgst.${header.rcv_channel.intrnRefId}.arch"))
			 
			//log channel request message
			.marshal(jsonChnlProxyRegistrationFormat)
			.setHeader("log_label", constant("Channel Request Message"))
			.to("seda:savelogfiles?exchangePattern=InOnly")

			// convert channel request jadi prxy.001 message
			.unmarshal(jsonChnlProxyRegistrationFormat)
			.process(proxyRegistrationRequestProcessor)
			.setHeader("req_objbi", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)

			//log message ke ci-hub
			.setHeader("log_label", constant("Outbound Message"))
			.to("seda:savelogfiles?exchangePattern=InOnly")

			// kirim ke CI-HUB
			.setHeader("req_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.setHeader("HttpMethod", constant("POST"))
			.enrich("http:{{bifast.ciconnector-url}}?"
					+ "socketTimeout={{bifast.timeout}}&" 
					+ "bridgeEndpoint=true",
					enrichmentAggregator)
			.convertBodyTo(String.class)
			.setHeader("req_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))

			// log message dari ci-hub
			.setHeader("log_label", constant("CI-Hub Response Message"))
			.to("seda:savelogfiles?exchangePattern=InOnly")

			.unmarshal(jsonBusinessMessageFormat)
			.setHeader("resp_objbi", simple("${body}"))	
			
			// prepare untuk response ke channel
			.process(proxyRegistrationResponseProcessor)
			.setHeader("resp_channel", simple("${body}"))			
			.marshal(jsonChnlResponseFormat)
			
			// log message reponse ke channel
			.setHeader("log_label", constant("Channel Response Message"))
			.to("seda:savelogfiles?exchangePattern=InOnly")

		;	

		
		from("direct:proxyresolution").routeId("proxyresolution")
			.setHeader("log_filename", simple("prxyrslt.${header.rcv_channel.intrnRefId}.arch"))
			
			//log channel request message
			.marshal(jsonChnlProxyResolutionFormat)
			.setHeader("log_label", constant("Channel Request Message"))
			.to("seda:savelogfiles?exchangePattern=InOnly")

			// convert channel request jadi prxy.003 message
			.unmarshal(jsonChnlProxyResolutionFormat)
			.process(proxyResolutionRequestProcessor)
			.setHeader("req_objbi", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)

			// kirim ke CI-HUB
			.setHeader("HttpMethod", constant("POST"))
			.enrich("http:{{bifast.ciconnector-url}}?"
					+ "socketTimeout={{bifast.timeout}}&" 
					+ "bridgeEndpoint=true",
					enrichmentAggregator)
			.convertBodyTo(String.class)

			// log message dari ci-hub
			.setHeader("log_label", constant("CI-Hub Response Message"))
			.to("seda:savelogfiles?exchangePattern=InOnly")

			.unmarshal(jsonBusinessMessageFormat)
			.setHeader("resp_objbi", simple("${body}"))	
					
			// prepare untuk response ke channel
			.process(proxyResolutionResponseProcessor)
			.setHeader("resp_channel", simple("${body}"))
			.marshal(jsonChnlResponseFormat)

			// log message reponse ke channel
			.setHeader("log_label", constant("Channel Response Message"))
			.to("seda:savelogfiles?exchangePattern=InOnly")
			
		;


	}
}
