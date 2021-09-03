package bifast.outbound.route;

import java.util.NoSuchElementException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import bifast.outbound.pojo.ChannelRequest;
import bifast.outbound.processor.CheckChannelRequestTypeProcessor;
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
	
	JacksonDataFormat ChnlRequestFormat = new JacksonDataFormat(ChannelRequest.class);

	@Override
	public void configure() throws Exception {

		ChnlRequestFormat.setInclude("NON_NULL");
		ChnlRequestFormat.setInclude("NON_EMPTY");
		
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
			
			.unmarshal(ChnlRequestFormat)
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
	
        from("seda:b").to("seda:c");

	}

}
