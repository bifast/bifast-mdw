package bifast.outbound.route;

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
import bifast.outbound.processor.PreCihubRequestProc;
import bifast.outbound.service.CallRouteService;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class CihubRoute extends RouteBuilder {

	@Autowired private CallRouteService routeService;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private ExceptionToFaultProcessor exceptionToFaultMap;
	@Autowired private FlatResponseProcessor flatResponseProcessor;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private PreCihubRequestProc preCihubRequestProc;

//	private static Logger logger = LoggerFactory.getLogger(CihubRoute.class);

	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat businessMessageJDF = jdfService.wrapUnwrapRoot(BusinessMessage.class);
		
		JaxbDataFormat jaxb = new JaxbDataFormat();
		JAXBContext con = JAXBContext.newInstance(BusinessMessage.class);
		jaxb.setContext(con);

		// ** ROUTE GENERAL UNTUK POSTING KE CI-HUB ** //
		from("direct:call-cihub").routeId("komi.call-cihub")
			.choice()
				.when().simple("${properties:komi.output-format} == 'json'")
					.marshal(businessMessageJDF)
				.otherwise()
					.marshal(jaxb)
			.end()

			// daftarkan encrypted dan startTime ke rmw, set remain time
			.process(preCihubRequestProc)
			.log("[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] request CIHUB: ${body}")
			
			.removeHeaders("hdr*")

			.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.log(LoggingLevel.DEBUG, "komi.call-cihub", 
						"[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] "
						+ "sisa waktu ${exchangeProperty.prop_remain_time}")
				.enrich()
					.simple("{{komi.url.ciconnector}}?"
						+ "socketTimeout=${exchangeProperty.prop_remain_time}&" 
						+ "bridgeEndpoint=true")
					.aggregationStrategy(enrichmentAggregator)
				
				.convertBodyTo(String.class)				
				.log("[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] CIHUB response: ${body}")
					
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
						rmw.setCihubEncriptedResponse(routeService.encrypt_body(exchange));
					}
				})
				
				.unmarshal(businessMessageJDF)
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						BusinessMessage bm = exchange.getMessage().getBody(BusinessMessage.class);
						ResponseMessageCollection rmc = exchange.getProperty("prop_response_list", ResponseMessageCollection.class);
						rmc.setCihubResponse(bm);
					}
				})
								
				.process(flatResponseProcessor)
		
			.endDoTry()
			.doCatch(java.net.SocketTimeoutException.class)
				.log(LoggingLevel.ERROR, "[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] CI-HUB TIMEOUT.")
//		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
		    	.process(exceptionToFaultMap)
			.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] Call CI-HUB Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
		    	.process(exceptionToFaultMap)
			.end()
	
			.removeHeaders("cihubroute_*")
		;
	
	}		
}
