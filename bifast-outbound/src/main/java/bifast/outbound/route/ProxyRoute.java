package bifast.outbound.route;

import java.net.SocketTimeoutException;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.pojo.ChannelRequest;
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
	private ProxyRegistrationResponseProcessor ProxyRegistrationResponseProcessor;
	@Autowired
	private ProxyResolutionRequestProcessor proxyResolutionRequestProcessor;
	@Autowired
	private ProxyResolutionResponseProcessor proxyResolutionResponseProcessor;
	@Autowired
	private FaultProcessor faultProcessor;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	
	JacksonDataFormat chnlRequestFormat = new JacksonDataFormat(ChannelRequest.class);
	JacksonDataFormat jsonChnlResponseFormat = new JacksonDataFormat(ChannelResponseMessage.class);
	JacksonDataFormat jsonChnlProxyRegistrationFormat = new JacksonDataFormat(ChannelProxyRegistrationReq.class);
	JacksonDataFormat jsonChnlProxyResolutionFormat = new JacksonDataFormat(ChannelProxyResolutionReq.class);

	JacksonDataFormat jsonBusinessMessageFormat = new JacksonDataFormat(BusinessMessage.class);

	private void configureJsonDataFormat() {

		chnlRequestFormat.setInclude("NON_NULL");
		chnlRequestFormat.setInclude("NON_EMPTY");

		jsonChnlProxyRegistrationFormat.setInclude("NON_NULL");
		jsonChnlProxyRegistrationFormat.setInclude("NON_EMPTY");
		
		jsonChnlResponseFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonChnlResponseFormat.setInclude("NON_NULL");
		jsonChnlResponseFormat.setInclude("NON_EMPTY");
		jsonChnlResponseFormat.setPrettyPrint(true);
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
		;
		
		restConfiguration().component("servlet")
        	.apiContextPath("/api-doc")
	            .apiProperty("api.title", "KOMI API Documentation").apiProperty("api.version", "1.0.0")
	            .apiProperty("cors", "true");

        ;
		
//		rest("/komi")
//			.post("/proxyregistration")
//				.description("Pendaftaran data proxy account ke BI")
//				.consumes("application/json")
//				.to("direct:proxyregistration")
		
//			.post("/proxyresolution")
//				.description("Periksa / enquiry data proxy account ke BI")
//				.consumes("application/json")
//				.to("direct:proxyresolution")
		;

		// Untuk Proses Proxy Registration Request

		from("direct:proxyregistration").routeId("proxyregistration")
			.convertBodyTo(String.class)

//			.setExchangePattern(ExchangePattern.InOnly)  // simpan log file dulu
//			.setHeader("req_loglabel", constant("Channel Message"))
//			.to("seda:savelogfiles")

			.unmarshal(jsonChnlProxyRegistrationFormat)
			.setHeader("req_channelReq",simple("${body}"))
			.setHeader("req_msgType", constant("ProxyRegistration"))

			// convert channel request jadi prxy.001 message
			.process(proxyRegistrationRequestProcessor)
			.setHeader("req_objbi", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)
			

			
			// kirim ke CI-HUB
			.setHeader("HttpMethod", constant("POST"))
			.enrich("http:{{bifast.ciconnector-url}}?"
					+ "socketTimeout={{bifast.timeout}}&" 
					+ "bridgeEndpoint=true",
					enrichmentAggregator)

			.convertBodyTo(String.class)
			.unmarshal(jsonBusinessMessageFormat)
			.setHeader("resp_objbi", simple("${body}"))	
			
			// prepare untuk response ke channel
			.process(ProxyRegistrationResponseProcessor)
			.setHeader("resp_channel", simple("${body}"))
			
			.setExchangePattern(ExchangePattern.InOnly)
			.to("seda:endlog")
//			.to("seda:savelogfiles")
			
			.setBody(simple("${header.resp_channel}"))
			.marshal(jsonChnlResponseFormat)
			.removeHeaders("req*")
			.removeHeaders("resp_*")
		;	

		from("direct:proxyresolution").routeId("proxyresolution")
			.convertBodyTo(String.class)
			.unmarshal(jsonChnlProxyResolutionFormat)
			.setHeader("req_channelReq",simple("${body}"))
			.setHeader("req_msgType", constant("ProxyResolution"))

			// convert channel request jadi prxy.003 message
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
			.unmarshal(jsonBusinessMessageFormat)
			.setHeader("resp_objbi", simple("${body}"))	
			
			// prepare untuk response ke channel
			.process(proxyResolutionResponseProcessor)
			.setHeader("resp_channel", simple("${body}"))
			
			.setExchangePattern(ExchangePattern.InOnly)
			.to("seda:endlog")
			
			.setBody(simple("${header.resp_channel}"))
			.marshal(jsonChnlResponseFormat)
			.removeHeaders("req*")
			.removeHeaders("resp_*")
		;

//		from("seda:savelogfiles")
//			.log("Save log files")
//			.setHeader("tmp_body", simple("${body}"))
//			.setHeader("req_namafile", simple("${header.req_objbi.appHdr.msgDefIdr}"))
//			
//			.setBody(simple("### ${header.req_loglabel} ###\n"))
//			.toD("file:{{bifast.outbound-log-folder}}?fileName=${header.req_namafile}&fileExist=Append")
//			.setBody(simple("${header.tmp_body}"))
//			.toD("file:{{bifast.outbound-log-folder}}?fileName=${header.req_namafile}&fileExist=Append")
//			.removeHeader("tmp_body")
//		;

	}
}
