package bifast.outbound.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.outbound.history.HistoryRetrievalRequest;
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
	@Autowired
	private SaveTablesProcessor saveTablesProcessor;
	@Autowired
	private SaveTableAwalProcessor saveTableAwalProcessor;
	@Autowired
	private ValidateInputProcessor validateInputProcessor;
	@Autowired
	private FaultProcessor faultProcessor;
	
	JacksonDataFormat chnlRequestFormat = new JacksonDataFormat(ChannelRequestWrapper.class);
	JacksonDataFormat chnlFaultFormat = new JacksonDataFormat(ChannelFaultResponse.class);
	JacksonDataFormat historyRequestJDF = new JacksonDataFormat(HistoryRetrievalRequest.class);

	@Override
	public void configure() throws Exception {

		chnlRequestFormat.setInclude("NON_NULL");
		chnlRequestFormat.setInclude("NON_EMPTY");
		chnlFaultFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		chnlFaultFormat.setInclude("NON_NULL");
		chnlFaultFormat.setInclude("NON_EMPTY");
		chnlFaultFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
//		historyRequestJDF.setInclude("NON_NULL");
//		historyRequestJDF.setInclude("NON_EMPTY");

        onException(Exception.class).routeId("Generic Exception Handler")
        	.log("Fault di EnpointRoute, ${header.hdr_errorlocation}")
	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
			.process(faultProcessor)
//			.to("seda:savetableerror")
			.to("sql:update outbound_message set resp_status = 'ERROR', "
					+ " error_msg= :#${body.reason} "
					+ "where id= :#${header.hdr_idtable}  ")

			.marshal(chnlFaultFormat)
			.removeHeaders("rcv_*")
//			.removeHeaders("req*")
//			.removeHeaders("resp_*")
			.removeHeaders("hdr_*")
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

		from("direct:aa")
			.convertBodyTo(String.class)
			.log("${body}")
			.unmarshal(historyRequestJDF)
			.to("direct:history")
		;

		from("direct:outbound")
			.convertBodyTo(String.class)

			.setHeader("hdr_errorlocation", constant("EndpointRoute/Cekpoint-1"))
					
			.setHeader("req_channelRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.unmarshal(chnlRequestFormat)

			.process(checkChannelRequest)		// produce header hdr_msgType,hdr_channelRequest
			.setHeader("hdr_channelRequest", simple("${body}"))

			.setHeader("hdr_errorlocation", constant("EndpointRoute/Cekpoint-5"))

			.choice()
				.when().simple("${header.hdr_msgType} == 'acctenqr'")
					.log("Account Enquiry")
					.to("direct:acctenqr")
					
				.when().simple("${header.hdr_msgType} == 'crdttrns'")
					.log("Credit Transfer")
					.to("direct:ctreq")

				.when().simple("${header.hdr_msgType} == 'ficrdttrns'")
					.log("FI Credit Transfer")
					.to("direct:fictreq")

				.when().simple("${header.hdr_msgType} == 'pymtsts'")
					.log("Payment Status Request")
					.to("direct:chnlpymtsts")

				.when().simple("${header.hdr_msgType} == 'prxyrgst'")
					.log("Proxy Registration")
					.to("direct:proxyregistration")

				.when().simple("${header.hdr_msgType} == 'prxyrslt'")
					.log("Proxy Resolution")
					.to("direct:proxyresolution")

			.end()
			
			.setHeader("req_channelResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))

			.setHeader("hdr_errorlocation", constant("EndpointRoute/Cekpoint-10"))

			// save audit tables
//			.to("seda:savetables?exchangePattern=InOnly")

			.removeHeaders("req*")
			.removeHeaders("resp_*")
			.removeHeaders("hdr_*")

		;
	
//		from("seda:savelogfiles")
//			.choice()
//				.when().simple("${header.rcv_msgType} == 'acctenqr'")
//					.log(LoggingLevel.INFO, "accountenquiry", "[RefId:${header.req_refId}][${header.log_label}] ${body}")
//				.when().simple("${header.rcv_msgType} == 'crdttrns'")
//					.log(LoggingLevel.INFO, "credittransfer", "[RefId:${header.req_refId}][${header.log_label}] ${body}")
//				.when().simple("${header.rcv_msgType} == 'ficrdttrns'")
//					.log(LoggingLevel.INFO, "credittransfer", "[RefId:${header.req_refId}][${header.log_label}] ${body}")
//				.when().simple("${header.rcv_msgType} == 'prxyrslt'")
//					.log(LoggingLevel.INFO, "proxy", "[RefId:${header.req_refId}][${header.log_label}] ${body}")
//			.end()
//		;

		from("seda:savetableawal")
			.setHeader("hdr_tmp", simple("${body}"))
//			.setBody(simple("${header.hdr_fullrequestmessage}"))
			.marshal().zipDeflater()
			.marshal().base64()
			.setHeader("hdr_fullrequestmessage", simple("${body}"))
			.process(saveTableAwalProcessor)
			.setBody(simple("${header.hdr_tmp}"))
			.removeHeader("hdr_tmp")
		;
		
		from("seda:savetables")
			.setHeader("hdr_tmp", simple("${body}"))
			.setBody(simple("${header.hdr_fullresponsemessage}"))
			.marshal().zipDeflater()
			.marshal().base64()
			.setHeader("hdr_fullresponsemessage", simple("${body}"))
			.process(saveTablesProcessor)
			.setBody(simple("${header.hdr_tmp}"))
		;
		
		from("seda:savetableerror")
			.to("sql:update outbound_message set resp_status = 'ERROR', "
					+ " error_msg= :#${body.reason} "
					+ "where id= :#${header.hdr_idtable}  ")
		;
	
	}

}
