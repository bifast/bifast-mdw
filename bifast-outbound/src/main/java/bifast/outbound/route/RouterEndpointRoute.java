package bifast.outbound.route;

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
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.pojo.flat.FlatMessageWrapper;
import bifast.outbound.processor.CheckChannelRequestTypeProcessor;
import bifast.outbound.processor.ValidateProcessor;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class RouterEndpointRoute extends RouteBuilder {

	@Autowired
	private CheckChannelRequestTypeProcessor checkChannelRequest;
	@Autowired
	private ValidateProcessor validateInputProcessor;
	@Autowired
	private JacksonDataFormatService jdfService;

	@Override
	public void configure() throws Exception {
		JacksonDataFormat chnlRequestJDF = jdfService.basic(RequestMessageWrapper.class);	
		JacksonDataFormat flatResponseJDF = jdfService.basic(FlatMessageWrapper.class);	
	
		restConfiguration().component("servlet");
		
		rest("/")

			.post("/routing")
				.consumes("application/json")
				.to("direct:router")

		;

		
		from("direct:router").routeId("komi.direct:router")
			.convertBodyTo(String.class)

			.setHeader("hdr_errorlocation", constant("EndpointRoute"))
					
			.setHeader("req_channelRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.unmarshal(chnlRequestJDF)


			.setHeader("hdr_errorlocation", constant("EndpointRoute/checkChannelRequest"))
			.process(checkChannelRequest)		// produce header hdr_msgType,hdr_channelRequest
			.setHeader("hdr_channelRequest", simple("${body}"))
			
			.process(validateInputProcessor)
			
//			.process(saveChannelRequestProcessor)

			.choice()
				.when().simple("${header.hdr_msgType} == 'AEReq'")
					.log("[ChRefId:${header.hdr_chnlRefId}][AE] start.")
					.to("direct:acctenqrflt")
					
				.when().simple("${header.hdr_msgType} == 'crdttrns'")
					.log("[ChRefId:${header.hdr_chnlRefId}][CT] start.")
					.to("direct:flatctreq")

				.when().simple("${header.hdr_msgType} == 'ficrdttrns'")
					.log("[ChRefId:${header.hdr_chnlRefId}][FICT] start.")
					.to("direct:fictreq")

				.when().simple("${header.hdr_msgType} == 'pymtsts'")
					.log("[ChRefId:${header.hdr_chnlRefId}][PS] start.")
					.to("direct:ps4chnl")

				.when().simple("${header.hdr_msgType} == 'prxyrgst'")
					.log("[ChRefId:${header.hdr_chnlRefId}][PREG] start.")
					.to("direct:prxyrgst")

				.when().simple("${header.hdr_msgType} == 'prxyrslt'")
					.log("[ChRefId:${header.hdr_chnlRefId}][PRES] start.")
					.to("direct:proxyresolution")

			.end()

			.marshal(flatResponseJDF)
			
			
			.choice()
					
				.when().simple("${header.hdr_msgType} == 'AEReq'")
					.log("[ChRefId:${header.hdr_chnlRefId}][AE] finish.")

				.when().simple("${header.hdr_msgType} == 'crdttrns'")
					.log("[ChRefId:${header.hdr_chnlRefId}][CT] finish.")
	
				.when().simple("${header.hdr_msgType} == 'ficrdttrns'")
					.log("[ChRefId:${header.hdr_chnlRefId}][FICT] finish.")
	
				.when().simple("${header.hdr_msgType} == 'pymtsts'")
					.log("[ChRefId:${header.hdr_chnlRefId}][PS] finish.")
	
				.when().simple("${header.hdr_msgType} == 'prxyrgst'")
					.log("[ChRefId:${header.hdr_chnlRefId}][PREG] finish.")
	
				.when().simple("${header.hdr_msgType} == 'prxyrslt'")
					.log("[ChRefId:${header.hdr_chnlRefId}][PRES] finish.")
	
			.end()

//			.process(saveChannelRequestProcessor)

			.removeHeaders("req_*")
			.removeHeaders("hdr_*")
			.removeHeaders("fict_*")
			.removeHeaders("ps_*")
			.removeHeaders("ae_*")
			.removeHeader("HttpMethod")

		;
		
	}


}
