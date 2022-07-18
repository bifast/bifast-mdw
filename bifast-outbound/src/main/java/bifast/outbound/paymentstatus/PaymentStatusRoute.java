package bifast.outbound.paymentstatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.model.StatusReason;
import bifast.outbound.paymentstatus.processor.CheckSAFResultProcessor;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.repository.StatusReasonRepository;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class PaymentStatusRoute extends RouteBuilder {
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");
   
	@Autowired
	private CheckSAFResultProcessor checkSAFResult;
	@Autowired
	private JacksonDataFormatService jdfService;
    @Autowired
    private StatusReasonRepository statusReasonRepo;

	@Override
	public void configure() throws Exception {
		JacksonDataFormat ctRequestJDF = jdfService.unwrapRoot(ChnlCreditTransferRequestPojo.class);

		
		from("direct:pschnl").routeId("komi.ps.chnl")
			.log(LoggingLevel.DEBUG, "komi.ps.chnl", 
					"[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] PaymentStatus inquiry")
			
			//find CT
			.process(checkSAFResult)

//			.log(LoggingLevel.DEBUG, "komi.ps.chnl", "check result: ${body}")
			.log(LoggingLevel.DEBUG, "komi.ps.chnl", 
					"[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] check result: ${body}")

			.filter().simple("${exchangeProperty.prop_response_list.reasonCode} == 'U000'")
				.unmarshal(ctRequestJDF)
			.end()
			
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {					
					ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
					channelResponseWr.setDate(LocalDateTime.now().format(dateformatter));
					channelResponseWr.setTime(LocalDateTime.now().format(timeformatter));
					channelResponseWr.setResponses(new ArrayList<>());

					ResponseMessageCollection rmc = exchange.getProperty("prop_response_list", ResponseMessageCollection.class);
					channelResponseWr.setResponseCode(rmc.getResponseCode());
					channelResponseWr.setReasonCode(rmc.getReasonCode());

					String reasonMessage = statusReasonRepo.findById(rmc.getReasonCode()).orElse(new StatusReason()).getDescription();
					channelResponseWr.setReasonMessage(reasonMessage);
					
					RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);				
					ChnlCreditTransferRequestPojo ctReq = new ChnlCreditTransferRequestPojo();
					ctReq.setChannelRefId(rmw.getRequestId());

					if (rmc.getReasonCode().equals("U000")) {
						ctReq = exchange.getMessage().getBody(ChnlCreditTransferRequestPojo.class);
					}
					channelResponseWr.getResponses().add(ctReq);
					exchange.getMessage().setBody(channelResponseWr);
				}
			})
			
		;

	}

}
