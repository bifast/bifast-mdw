package bifast.outbound.route;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;

import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.processor.CheckChannelRequestTypeProcessor;
import bifast.outbound.processor.InitRequestMessageWrapperProcessor;
import bifast.outbound.processor.SaveChannelTransactionProcessor;
import bifast.outbound.processor.ValidateProcessor;
import bifast.outbound.repository.ChannelTransactionRepository;

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

    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

	JacksonDataFormat chnlRequestJDF = new JacksonDataFormat(RequestMessageWrapper.class);
	JacksonDataFormat jdfChnlRequestNoWr = new JacksonDataFormat(RequestMessageWrapper.class);
	JacksonDataFormat chnlResponseJDF = new JacksonDataFormat(ChannelResponseWrapper.class);

	@Override
	public void configure() throws Exception {

		jdfChnlRequestNoWr.setInclude("NON_NULL");
		jdfChnlRequestNoWr.setInclude("NON_EMPTY");
		jdfChnlRequestNoWr.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
//		jdfChnlRequestNoWr.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

		chnlRequestJDF.setInclude("NON_NULL");
		chnlRequestJDF.setInclude("NON_EMPTY");
		chnlResponseJDF.setInclude("NON_NULL");
		chnlResponseJDF.setInclude("NON_EMPTY");
		chnlResponseJDF.setPrettyPrint(true);


		restConfiguration().component("servlet");
		
		rest("/")

			.post("/service")
				.consumes("application/json")
				.to("direct:service")
		;

		
		from("direct:service").routeId("komi.endpointRoute")
			.convertBodyTo(String.class)
			.setHeader("hdr_fulltextinput", simple("${body}"))
			.setHeader("req_channelRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			
//			.log("${body}")
			.unmarshal(chnlRequestJDF)

			.setHeader("hdr_errorlocation", constant("EndpointRoute/checkChannelRequest"))
			
			.process(initRmwProcessor)
						
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
//			.process(saveChannelTransactionProcessor)

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
//					.to("direct:crdttrns")
					.to("seda:ct_aft_corebank")

				.when().simple("${header.hdr_request_list.msgName} == 'PSReq'")
					.to("direct:pschnl")

				.when().simple("${header.hdr_request_list.msgName} == 'PrxRegnReq'")
					.to("direct:prxyrgst")

				.when().simple("${header.hdr_request_list.msgName} == 'ProxyResolution'")
					.to("direct:proxyresolution")

			.end()

			.log("[ChnlReq:${header.hdr_chnlRefId}] ${header.hdr_request_list.msgName} finish.")

			.to("seda:savetablechannel")

			.marshal(chnlResponseJDF)

			.removeHeader("clientid")
			.removeHeaders("hdr_*")
			.removeHeaders("req_*")
			.removeHeader("HttpMethod")
			.removeHeader("cookie")
		;
		
		from("seda:savetablechannel")
			.log("Akan save channel transaction")
			.process(saveChannelTransactionProcessor)
		;		


	}

}
