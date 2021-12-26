package bifast.outbound.route;

import java.time.Instant;

import javax.xml.bind.JAXBContext;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.ExceptionToFaultProcessor;
import bifast.outbound.processor.FlatResponseProcessor;
import bifast.outbound.processor.SetRemainTimeProcessor;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class CihubRoute extends RouteBuilder {

	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	@Autowired
	private ExceptionToFaultProcessor exceptionToFaultMap;
	@Autowired
	private FlatResponseProcessor flatResponseProcessor;
	@Autowired
	private JacksonDataFormatService jdfService;
	@Autowired
	private SetRemainTimeProcessor setRemainTime;

	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat businessMessageJDF = jdfService.wrapUnwrapRoot(BusinessMessage.class);
		
		JaxbDataFormat jaxb = new JaxbDataFormat();
		JAXBContext con = JAXBContext.newInstance(BusinessMessage.class);
		jaxb.setContext(con);

		// ** ROUTE GENERAL UNTUK POSTING KE CI-HUB ** //
		from("direct:call-cihub").routeId("komi.call-cihub")
		
			.setHeader("hdr_cihub_request", simple("${body}")).id("start_route")
	
			.choice()
				.when().simple("${properties:komi.output-format} == 'json'")
					.marshal(businessMessageJDF)
				.otherwise()
					.marshal(jaxb)
			.end()
	

			// zip dulu body ke cihubroute_encr_request
			.setHeader("tmp_body", simple("${body}"))
			.marshal().zipDeflater()
			.marshal().base64()
			.setHeader("cihubroute_encr_request", simple("${body}"))
			.setBody(simple("${header.tmp_body}"))
			
			// daftarkan encrypted dan startTime ke rmw
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
					String encrMsg = exchange.getMessage().getHeader("cihubroute_encr_request", String.class);
					rmw.setCihubEncriptedRequest(encrMsg);
					rmw.setCihubStart(Instant.now());
					exchange.getMessage().setHeader("hdr_request_list", rmw);
				}
			})
			
			.process(setRemainTime)
			.log(LoggingLevel.DEBUG, "komi.call-cihub", "[ChnlReq:${header.hdr_request_list.requestId}][${header.hdr_request_list.msgName}] CIHUB request: ${body}")
			
			.doTry()
				.setHeader("HttpMethod", constant("POST"))
				
				.enrich()
					.simple("http:{{komi.url.ciconnector}}?"
						+ "socketTimeout=${header.hdr_remain_time}&" 
						+ "bridgeEndpoint=true")
					.aggregationStrategy(enrichmentAggregator)
				
				.convertBodyTo(String.class)				
				.log(LoggingLevel.DEBUG, "komi.call-cihub", "[ChnlReq:${header.hdr_request_list.requestId}][${header.hdr_request_list.msgName}] CIHUB response: ${body}")
	
				.setHeader("tmp_body", simple("${body}"))
				.marshal().zipDeflater()
				.marshal().base64()
				.setHeader("cihubroute_encr_response", simple("${body}"))
				
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						String body = exchange.getMessage().getBody(String.class);
						RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
						rmw.setCihubEncriptedResponse(body);
						exchange.getMessage().setHeader("hdr_request_list", rmw);
					}
				})
				
				.setBody(simple("${header.tmp_body}"))	
				.unmarshal(businessMessageJDF)
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						BusinessMessage bm = exchange.getMessage().getBody(BusinessMessage.class);
						ResponseMessageCollection rmc = exchange.getMessage().getHeader("hdr_response_list", ResponseMessageCollection.class);
						rmc.setCihubResponse(bm);
						exchange.getMessage().setHeader("hdr_response_list", rmc);
					}
				})
				
				.setHeader("hdr_error_status", constant(null))
				
				.process(flatResponseProcessor)
				
				.filter().simple("${body.class} endsWith 'FaultPojo'")
					.log(LoggingLevel.ERROR, "[ChnlReq:${header.hdr_request_list.requestId}][${header.hdr_request_list.msgName}] CI-HUB Response: ${header.tmp_body}")
		    	.end()
			
			.endDoTry()
	    	.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[ChnlReq:${header.hdr_request_list.requestId}] Call CI-HUB Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
		    	.process(exceptionToFaultMap)
	
//			.endDoTry()
			.end()
	
			.removeHeaders("tmp_*")
			.removeHeaders("cihubroute_*")
		;

	
	}		
}
