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
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.ExceptionToFaultProc;
import bifast.outbound.processor.FlatResponseProcessor;
import bifast.outbound.processor.PreCihubRequestProc;
import bifast.outbound.service.CallRouteService;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class CiConnRoute extends RouteBuilder {

	@Autowired private CallRouteService routeService;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private ExceptionToFaultProc exceptionToFaultMap;
	@Autowired private FlatResponseProcessor flatResponseProcessor;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private PreCihubRequestProc preCihubRequestProc;

//	private static Logger logger = LoggerFactory.getLogger(CihubRoute.class);

	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat businessMessageJDF = jdfService.wrapUnwrapRoot(BusinessMessage.class);
		JAXBContext con = JAXBContext.newInstance(BusinessMessage.class);
		JaxbDataFormat jaxb = new JaxbDataFormat();
		jaxb.setContext(con);

		onException(java.net.SocketTimeoutException.class).onWhen(header("cihubMsgName").isEqualTo("PSReq"))
			.maximumRedeliveries(5).redeliveryDelay(5000)
			.log(LoggingLevel.ERROR, "[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] PSReq TIMEOUT.")
	    	.process(exceptionToFaultMap)
	    	.continued(true);
		onException(java.net.SocketTimeoutException.class).onWhen(header("cihubMsgName").isNotEqualTo("PSReq"))
			.log(LoggingLevel.ERROR, "[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] CI-CONN TIMEOUT.")
	    	.process(exceptionToFaultMap)
	    	.continued(true);
		onException(Exception.class)
			.log(LoggingLevel.ERROR, "[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] Call CI-CONN Error.")
	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    	.process(exceptionToFaultMap)
	    	.continued(true);
		
		// ** ROUTE GENERAL UNTUK POSTING KE CI-HUB ** //
		from("direct:call-ciconn").routeId("komi.call-ciconn")
		
			.choice()
				.when().simple("${properties:komi.output-format} == 'json'").marshal(businessMessageJDF)
				.otherwise().marshal(jaxb)
			.end()

			// daftarkan encrypted dan startTime ke rmw, set remain time
			.process(preCihubRequestProc)
			.log("[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] request CICONN: ${body}")
			
			.removeHeaders("hdr*")

			.setHeader("HttpMethod", constant("POST"))
			.log(LoggingLevel.DEBUG, "komi.call-ciconn", 
					"[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] "
					+ "sisa waktu ${exchangeProperty.prop_remain_time}")
			.enrich()
				.simple("{{komi.url.ciconnector}}?"
					+ "socketTimeout=${exchangeProperty.prop_remain_time}&" 
					+ "bridgeEndpoint=true")
				.aggregationStrategy(enrichmentAggregator)
			
			.filter(body().not().isInstanceOf(FaultPojo.class))
	
			.convertBodyTo(String.class)				
			.log("[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] CI-CONN response: ${body}")
				
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
		;
	}		
}
