package bifast.outbound.route;

import java.util.NoSuchElementException;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.outbound.pojo.ChannelRequest;
import bifast.outbound.pojo.ChannelResponseMessage;
import bifast.outbound.processor.CheckChannelRequestTypeProcessor;
import bifast.outbound.processor.FaultProcessor;
import bifast.outbound.processor.SaveTablesProcessor;
import bifast.outbound.processor.ValidateInputProcessor;

@Component
public class GenericOutboundRoute extends RouteBuilder {

	@Autowired
	private CheckChannelRequestTypeProcessor checkChannelRequest;
	@Autowired
	private SaveTablesProcessor saveTablesProcessor;
	@Autowired
	private ValidateInputProcessor validateInputProcessor;
	@Autowired
	private FaultProcessor faultProcessor;
	
	JacksonDataFormat chnlRequestFormat = new JacksonDataFormat(ChannelRequest.class);
	JacksonDataFormat jsonChnlResponseFormat = new JacksonDataFormat(ChannelResponseMessage.class);

	@Override
	public void configure() throws Exception {

		chnlRequestFormat.setInclude("NON_NULL");
		chnlRequestFormat.setInclude("NON_EMPTY");
		jsonChnlResponseFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonChnlResponseFormat.setInclude("NON_NULL");
		jsonChnlResponseFormat.setInclude("NON_EMPTY");
		jsonChnlResponseFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

        onException(NoSuchElementException.class).routeId("Error Exception")
        	.log(LoggingLevel.ERROR, "Ada error barusan ")
        	.handled(true)
        ;

		restConfiguration().component("servlet");
		
		rest("/komi")
			.post("/outbound")
			.consumes("application/json")
			.to("direct:outbound")
		;
	
		from("direct:outbound")
			.errorHandler(deadLetterChannel("seda:error"))

			.convertBodyTo(String.class)

			.setHeader("req_channelRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			
			.unmarshal(chnlRequestFormat)
			.process(checkChannelRequest)		// produce header rcv_msgType, rcv_channel
			.process(validateInputProcessor)
			
			.setHeader("req_refId", simple("${header.rcv_channel.intrnRefId}"))

			.setBody(simple("${header.rcv_channel}"))

			.choice()
				.when().simple("${header.rcv_msgType} == 'acctenqr'")
					.log("Account Enquiry")
					.to("direct:acctenqr")
					
				.when().simple("${header.rcv_msgType} == 'crdttrns'")
					.log("Credit Transfer")
					.to("direct:ctreq")

				.when().simple("${header.rcv_msgType} == 'ficrdttrns'")
					.log("FI Credit Transfer")
					.to("direct:fictreq")

				.when().simple("${header.rcv_msgType} == 'prxyrgst'")
					.log("Proxy Registration")
					.to("direct:proxyregistration")

				.when().simple("${header.rcv_msgType} == 'prxyrslt'")
					.log("Proxy Resolution")
					.to("direct:proxyresolution")

			.end()
			
			.setHeader("req_channelResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))

			// save audit tables
			.to("seda:savetables?exchangePattern=InOnly")

			.removeHeaders("rcv_*")
			.removeHeaders("req*")
			.removeHeaders("resp_*")
			.removeHeaders("log_*")

		;
	
		from("seda:savelogfiles")
//			.setHeader("tmp_body", simple("${body}"))	
//			.setBody(simple("### [${date:now:yyyyMMdd hh:mm:ss}] ${header.log_label} ###\\n"))
//			.toD("file:{{bifast.outbound-log-folder}}?fileName=${header.log_filename}&fileExist=Append")
//			.setBody(simple("${header.tmp_body}\\n\\n"))
//			.toD("file:{{bifast.outbound-log-folder}}?fileName=${header.log_filename}&fileExist=Append")
			.choice()
				.when().simple("${header.rcv_msgType} == 'acctenqr'")
					.log(LoggingLevel.INFO, "accountenquiry", "[RefId:${header.req_refId}][${header.log_label}] ${body}")
				.when().simple("${header.rcv_msgType} == 'crdttrns'")
					.log(LoggingLevel.INFO, "credittransfer", "[RefId:${header.req_refId}][${header.log_label}] ${body}")
				.when().simple("${header.rcv_msgType} == 'ficrdttrns'")
					.log(LoggingLevel.INFO, "credittransfer", "[RefId:${header.req_refId}][${header.log_label}] ${body}")
				.when().simple("${header.rcv_msgType} == 'prxyrslt'")
					.log(LoggingLevel.INFO, "proxy", "[RefId:${header.req_refId}][${header.log_label}] ${body}")
			.end()
//			.removeHeader("tmp_body")
		;

		from("seda:savetables")
			.process(saveTablesProcessor)
		;
	
		from("seda:error")
			.log("Ada error dari seda:error")
			.process(faultProcessor)
			.marshal(jsonChnlResponseFormat)
			.log("${body}")
		;
	}

}
