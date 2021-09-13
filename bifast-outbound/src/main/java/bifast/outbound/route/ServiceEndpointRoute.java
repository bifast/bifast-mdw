package bifast.outbound.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.outbound.history.RequestPojo;
import bifast.outbound.pojo.ChannelFaultResponse;
import bifast.outbound.pojo.ChannelRequestWrapper;
import bifast.outbound.processor.CheckChannelRequestTypeProcessor;
import bifast.outbound.processor.FaultProcessor;
import bifast.outbound.processor.SaveTableAwalProcessor;
import bifast.outbound.processor.SaveTablesProcessor;
import bifast.outbound.processor.ValidateInputProcessor;

@Component
public class ServiceEndpointRoute extends RouteBuilder {

	@Autowired
	private CheckChannelRequestTypeProcessor checkChannelRequest;
//	@Autowired
//	private SaveTablesProcessor saveTablesProcessor;
//	@Autowired
//	private SaveTableAwalProcessor saveTableAwalProcessor;
	@Autowired
	private FaultProcessor faultProcessor;
	
	JacksonDataFormat chnlRequestFormat = new JacksonDataFormat(ChannelRequestWrapper.class);
	JacksonDataFormat chnlFaultFormat = new JacksonDataFormat(ChannelFaultResponse.class);
	JacksonDataFormat historyRequestJDF = new JacksonDataFormat(RequestPojo.class);

	@Override
	public void configure() throws Exception //	@Autowired
//	private SaveTablesProcessor saveTablesProcessor;
//	@Autowired
//	private SaveTableAwalProcessor saveTableAwalProcessor;
{

		chnlRequestFormat.setInclude("NON_NULL");
		chnlRequestFormat.setInclude("NON_EMPTY");
		chnlFaultFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		chnlFaultFormat.setInclude("NON_NULL");
		chnlFaultFormat.setInclude("NON_EMPTY");
		chnlFaultFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
//		historyRequestJDF.setInclude("NON_NULL");
//		historyRequestJDF.setInclude("NON_EMPTY");

        onException(Exception.class).routeId("Generic Exception Handler")
        	.log("Fault di EndpointRoute, ${header.hdr_errorlocation}")
	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
			.process(faultProcessor)
			.to("sql:update outbound_message set resp_status = 'ERROR', "
					+ " error_msg= :#${body.reason} "
					+ "where id= :#${header.hdr_idtable}  ")
			.marshal(chnlFaultFormat)
			.removeHeaders("req_*")
			.removeHeaders("hdr_*")
			.removeHeaders("fict_*")
	    	.handled(true)
    	;

		restConfiguration().component("servlet");
		
		rest("/komi")

			.post("/outbound")
				.consumes("application/json")
				.to("direct:outbound")

			.post("/history")
				.consumes("application/json")
				.to("direct:aa")
		;

		from("direct:outbound")
			.convertBodyTo(String.class)

			.setHeader("hdr_errorlocation", constant("EndpointRoute"))
					
			.setHeader("req_channelRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.unmarshal(chnlRequestFormat)

			.setHeader("hdr_errorlocation", constant("EndpointRoute/checkChannelRequest"))
			.process(checkChannelRequest)		// produce header hdr_msgType,hdr_channelRequest
			.setHeader("hdr_channelRequest", simple("${body}"))

			.choice()
				.when().simple("${header.hdr_msgType} == 'acctenqr'")
					.log("Account Enquiry")
					.to("direct:acctenqr")
					
				.when().simple("${header.hdr_msgType} == 'crdttrns'")
					.to("direct:ctreq")

				.when().simple("${header.hdr_msgType} == 'ficrdttrns'")
					.to("direct:fictreq")

				.when().simple("${header.hdr_msgType} == 'pymtsts'")
					.log("Payment Status Request")
					.to("direct:paymentstatus")

				.when().simple("${header.hdr_msgType} == 'prxyrgst'")
					.log("Proxy Registration")
					.to("direct:proxyregistration")

				.when().simple("${header.hdr_msgType} == 'prxyrslt'")
					.log("Proxy Resolution")
					.to("direct:proxyresolution")

			.end()
			
			.removeHeaders("req*")
			.removeHeaders("resp_*")
			.removeHeaders("hdr_*")

		;
	
	}

}
