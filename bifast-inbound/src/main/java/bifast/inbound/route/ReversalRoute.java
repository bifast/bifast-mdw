package bifast.inbound.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.inbound.pojo.ReverseCTRequestPojo;
import bifast.inbound.processor.EnrichmentAggregator;
import bifast.inbound.processor.ReversalRequestProcessor;


@Component
public class ReversalRoute extends RouteBuilder {

	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	@Autowired
	private ReversalRequestProcessor reversalRequestProcessor;
	
	JacksonDataFormat reversalRequestJDF = new JacksonDataFormat(ReverseCTRequestPojo.class);

	private void configureJson() {
		reversalRequestJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		reversalRequestJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		reversalRequestJDF.setInclude("NON_NULL");
		reversalRequestJDF.setInclude("NON_EMPTY");
	}
	
	@Override
	public void configure() throws Exception {
		configureJson();

		
		from("direct:reversal").routeId("reversal")
			
			.process(reversalRequestProcessor)
			.setHeader("rev_chnlrequest", simple("${body}"))
			.marshal(reversalRequestJDF)
			
			.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http://localhost:9002/services/komi/outbound?"
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)

				.to("sql:update credit_transfer set reversal = 'DONE' where crdttrn_req_bizmsgid = :#${header.rev_chnlrequest.orignReffId}")

			.doCatch(Exception.class)
				.log(LoggingLevel.WARN, "Reverse CT no. ${header.rev_chnlrequest.orignReffId} gagal")
				
			.end()
			
			.log("Response: ${body}")
			.removeHeaders("rev_*")
		;



	}
}
