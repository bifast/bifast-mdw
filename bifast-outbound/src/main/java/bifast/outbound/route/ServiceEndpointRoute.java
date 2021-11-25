package bifast.outbound.route;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.notification.BuildLogMessageForPortalProcessor;
import bifast.outbound.notification.pojo.PortalApiPojo;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.ChnlRequestWrapper;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.processor.CheckChannelRequestTypeProcessor;
import bifast.outbound.processor.ExceptionProcessor;
import bifast.outbound.processor.InitRequestMessageWrapperProcessor;
import bifast.outbound.processor.SaveChannelTransactionProcessor;
import bifast.outbound.processor.ValidateProcessor;
import bifast.outbound.repository.ChannelTransactionRepository;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class ServiceEndpointRoute extends RouteBuilder {

	@Autowired
	private CheckChannelRequestTypeProcessor checkChannelRequest;
	@Autowired
	private ValidateProcessor validateInputProcessor;
	@Autowired
	private InitRequestMessageWrapperProcessor initRmwProcessor;
	@Autowired
	private SaveChannelTransactionProcessor saveChannelTransactionProcessor;
	@Autowired
	private ChannelTransactionRepository channelTransactionRepo;
	@Autowired
	private JacksonDataFormatService jdfService;
	@Autowired
	private ExceptionProcessor exceptionProcessor;
	@Autowired private BuildLogMessageForPortalProcessor buildLogMessage;

    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");


	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat chnlResponseJDF = jdfService.basicPrettyPrint(ChannelResponseWrapper.class);
		JacksonDataFormat portalLogJDF = jdfService.wrapPrettyPrint(PortalApiPojo.class);
		JacksonDataFormat chnlRequestJDF = jdfService.basic(ChnlRequestWrapper.class);

		onException(Exception.class).routeId("komi.endpointRoute.onException")
			.log(LoggingLevel.ERROR, "${exception.stacktrace}")
			.process(exceptionProcessor)
			.marshal(chnlResponseJDF)
			.removeHeaders("*")
			.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
	    	.handled(true)
 		;
		
		
		restConfiguration().component("servlet");
		
		rest("/")
			.post("/service")
				.consumes("application/json")
				.to("direct:service")
		;

		
		from("direct:service").routeId("komi.endpointRoute")
			.convertBodyTo(String.class)
			
			.log(LoggingLevel.DEBUG, "komi.endpointRoute", "Terima: ${body}")

			.setHeader("hdr_fulltextinput", simple("${body}"))
			
			.unmarshal(chnlRequestJDF)
	
			.process(initRmwProcessor)
			.log(LoggingLevel.DEBUG, "komi.endpointRoute", "dari ${header.hdr_request_list.channelId}")

			.process(checkChannelRequest)		// produce header hdr_msgType,hdr_channelRequest

			.process(validateInputProcessor)

			// save data awal ke table channel_transaction dulu
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					ChannelTransaction chnlTrns = new ChannelTransaction();
					RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list",RequestMessageWrapper.class );
					String fullTextInput = exchange.getMessage().getHeader("hdr_fulltextinput", String.class);
					chnlTrns.setChannelRefId(rmw.getRequestId());
					chnlTrns.setKomiTrnsId(rmw.getKomiTrxId());
					chnlTrns.setChannelId(rmw.getChannelId());
					chnlTrns.setRequestTime(LocalDateTime.now());
					chnlTrns.setMsgName(rmw.getMsgName());
					chnlTrns.setTextMessage(fullTextInput);
					channelTransactionRepo.save(chnlTrns);
				}
			})

			// siapkan header penampung data2 hasil process.
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					ResponseMessageCollection rmc = new ResponseMessageCollection();
					exchange.getMessage().setHeader("hdr_response_list", rmc);
				}
			})
			
			.log("[ChnlReq:${header.hdr_request_list.requestId}] ${header.hdr_request_list.msgName} start.")

			.choice()
				.when().simple("${header.hdr_request_list.msgName} == 'AEReq'")
					.to("direct:acctenqr")
					
				.when().simple("${header.hdr_request_list.msgName} == 'CTReq'")
					.to("seda:credittrns")

				.when().simple("${header.hdr_request_list.msgName} == 'PSReq'")
					.to("direct:pschnl")

				.when().simple("${header.hdr_request_list.msgName} == 'PrxRegnReq'")
					.to("direct:prxyrgst")

				.when().simple("${header.hdr_request_list.msgName} == 'ProxyResolution'")
					.to("direct:proxyresolution")
					
				.when().simple("${header.hdr_request_list.msgName} == 'PrxRegnInquiryReq'")
					.to("direct:prxyrgstinquiry")

				.when().simple("${header.hdr_request_list.msgName} == 'AcctCustmrInfo'")
					.to("direct:acctcustmrinfo")

			.end()
			

			.log("[ChnlReq:${header.hdr_request_list.requestId}] ${header.hdr_request_list.msgName} finish.")

			.to("seda:savetablechannel?exchangePattern=InOnly")
			
			.to("seda:logportal?exchangePattern=InOnly")
				
			.removeHeaders("*")
			.marshal(chnlResponseJDF)
			.log(LoggingLevel.DEBUG, "komi.endpointRoute", "[ChnlReq:${header.hdr_request_list.requestId}] ${header.hdr_request_list.msgName} Response: ${body}")

		;
		
		from("seda:savetablechannel").routeId("komi.savechnltrns")
			.log(LoggingLevel.DEBUG, "komi.savechnltrns", "[ChnlReq:${header.hdr_request_list.requestId}][${header.hdr_request_list.msgName}] save table channel_transaction.")
			.process(saveChannelTransactionProcessor)
		;		

		from("seda:logportal").routeId("logportal")
			.process(buildLogMessage)
			.marshal(portalLogJDF)
		    .removeHeaders("CamelHttp*")
			.to("rest:post:portalapi?host={{komi.url.portalapi}}")
			.log("setelah logportal")
		;

	}

}
