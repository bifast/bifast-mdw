package bifast.outbound.route;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.config.Config;
import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.exception.DuplicateIdException;
import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.ChnlRequestWrapper;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.processor.CheckChannelRequestTypeProcessor;
import bifast.outbound.processor.ExceptionProcessor;
import bifast.outbound.processor.InitRequestMessageWrapperProcessor;
import bifast.outbound.processor.SaveChannelRequestProc;
import bifast.outbound.processor.ValidateProcessor;
import bifast.outbound.repository.ChannelTransactionRepository;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class ServiceEndpointRoute extends RouteBuilder {

	@Autowired private ChannelTransactionRepository channelTransactionRepo;
	@Autowired private CheckChannelRequestTypeProcessor checkChannelRequest;
	@Autowired private Config config;
	@Autowired private ExceptionProcessor exceptionProcessor;
	@Autowired private InitRequestMessageWrapperProcessor initRmwProcessor;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private SaveChannelRequestProc saveChannelRequestProc;
	@Autowired private ValidateProcessor validateInputProcessor;

    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");


	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat chnlResponseJDF = jdfService.basicPrettyPrint(ChannelResponseWrapper.class);
		JacksonDataFormat chnlRequestJDF = jdfService.basic(ChnlRequestWrapper.class);

		onException(Exception.class).routeId("komi.endpointRoute.onException")
			.log(LoggingLevel.ERROR, "${exception.stacktrace}")
			.process(exceptionProcessor)
			.marshal(chnlResponseJDF)
			.log(LoggingLevel.DEBUG, "komi.endpointRoute.onException", "[${exchangeProperty.prop_request_list.msgName}:"
					+ "${exchangeProperty.prop_request_list.requestId}] Response: ${body}")
			.removeHeaders("*")
	    	.handled(true)
 		;
		
		onException(DuplicateIdException.class).routeId("komi.endpointRoute.duplikatExcp")
			.process(exceptionProcessor)
			.marshal(chnlResponseJDF)
			.log(LoggingLevel.DEBUG, "komi.endpointRoute.duplikatExcp", "[${exchangeProperty.prop_request_list.msgName}:"
					+ "${exchangeProperty.prop_request_list.requestId}] Response: ${body}")
			.removeHeaders("*")
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

			.process(initRmwProcessor)
			.unmarshal(chnlRequestJDF)
	
			.log("======*******======")
			.log("Terima dari ${exchangeProperty.prop_request_list.channelId}\n"
					+ "${exchangeProperty.prop_request_list.channelRequestStr}")

			.process(checkChannelRequest)		// produce header hdr_msgType,hdr_channelRequest
			.process(validateInputProcessor)

			// save data awal ke table channel_transaction dulu
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					ChannelTransaction chnlTrns = new ChannelTransaction();
					RequestMessageWrapper rmw = exchange.getProperty("prop_request_list",RequestMessageWrapper.class );
					chnlTrns.setChannelRefId(rmw.getRequestId());
					chnlTrns.setKomiTrnsId(rmw.getKomiTrxId());
					chnlTrns.setChannelId(rmw.getChannelId());
					chnlTrns.setRequestTime(LocalDateTime.now());
					chnlTrns.setMsgName(rmw.getMsgName());
					chnlTrns.setTextMessage(rmw.getChannelRequestStr());

					if (rmw.getMsgName().equals("AEReq")) {
						ChnlAccountEnquiryRequestPojo aeReq = (ChnlAccountEnquiryRequestPojo) rmw.getChannelRequest();
						chnlTrns.setAmount(new BigDecimal(aeReq.getAmount()));
						chnlTrns.setRecptBank(aeReq.getRecptBank());			
					}
					else if (rmw.getMsgName().equals("CTReq")) {
						ChnlCreditTransferRequestPojo ctReq = (ChnlCreditTransferRequestPojo) rmw.getChannelRequest();
						chnlTrns.setAmount(new BigDecimal(ctReq.getAmount()));
						chnlTrns.setRecptBank(ctReq.getRecptBank());
					}
					else if (rmw.getMsgName().equals(config.getBicode())) {
						chnlTrns.setRecptBank(config.getBicode());
					}

					channelTransactionRepo.save(chnlTrns);
				}
			})

			.choice()
				.when().simple("${exchangeProperty.prop_request_list.msgName} == 'AEReq'")
					.to("direct:acctenqr")
					
				.when().simple("${exchangeProperty.prop_request_list.msgName} == 'CTReq'")
					.to("direct:credittrns?timeout=0")

				.when().simple("${exchangeProperty.prop_request_list.msgName} == 'PSReq'")
					.to("direct:pschnl")

				.when().simple("${exchangeProperty.prop_request_list.msgName} == 'PrxRegn'")
					.to("direct:prxyrgst")

				.when().simple("${exchangeProperty.prop_request_list.msgName} == 'PrxResl'")
					.to("direct:proxyresolution")
					
				.when().simple("${exchangeProperty.prop_request_list.msgName} == 'PrxRegInq'")
					.to("direct:prxyrgstinquiry")

				.when().simple("${exchangeProperty.prop_request_list.msgName} == 'ACReq'")
					.to("direct:acctcustmrinfo")

			.end()


			.log(LoggingLevel.DEBUG, "komi.endpointRoute", 
					"[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] process complete.")

			.wireTap("direct:savetablechannel")
			.wireTap("direct:logportal")
			
			.removeHeaders("*")
			.marshal(chnlResponseJDF)
			.log("[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Response: ${body}")
		;
		
		from("direct:savetablechannel").routeId("komi.savechnltrns")
			.log(LoggingLevel.DEBUG, "komi.savechnltrns", 
					"[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Save table channel_transaction.")
			.process(saveChannelRequestProc)
		;		


	}

}
