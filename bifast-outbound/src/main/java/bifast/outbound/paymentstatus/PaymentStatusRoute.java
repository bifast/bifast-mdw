package bifast.outbound.paymentstatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.paymentstatus.processor.PaymentStatusRequestProcessor;
import bifast.outbound.paymentstatus.processor.PaymentStatusResponseProcessor;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.pojo.chnlrequest.ChnlCreditTransferRequestPojo;
import bifast.outbound.pojo.chnlrequest.ChnlPaymentStatusRequestPojo;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.repository.ChannelTransactionRepository;
import bifast.outbound.repository.CreditTransferRepository;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class PaymentStatusRoute extends RouteBuilder {
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");
   
	@Autowired
	private ChannelTransactionRepository channelRequestRepo;
	@Autowired
	private CreditTransferRepository creditTransferRepo;
	@Autowired
	private JacksonDataFormatService jdfService;


	@Override
	public void configure() throws Exception {
		JacksonDataFormat ctRequestJDF = jdfService.unwrapRoot(ChnlCreditTransferRequestPojo.class);

		
		from("direct:pschnl").routeId("komi.ps.chnl")
			.log("Payment_status channel")
			
			//find CT
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
					ChnlPaymentStatusRequestPojo chnReq = rmw.getChnlPaymentStatusRequest();
					ResponseMessageCollection rmc = exchange.getMessage().getHeader("hdr_response_list", ResponseMessageCollection.class);
					
					String status = "#000";
					List<ChannelTransaction> lCT = channelRequestRepo.findByChannelIdAndChannelRefId(rmw.getChannelId(), chnReq.getOrgnlRefId());
					System.out.println("Jumlah " + lCT.size() + "dengan komiID " + lCT.get(0).getKomiTrnsId());
					
					if (lCT.size()>0) {
						System.out.println("Cari CreditTransfer unt " + lCT.get(0).getKomiTrnsId());
						Optional<CreditTransfer> oCreditTransfer = creditTransferRepo.findByKomiTrnsId(lCT.get(0).getKomiTrnsId());
						if (oCreditTransfer.isPresent()) {
							CreditTransfer crTrns = oCreditTransfer.get();
							rmc.setResponseCode(crTrns.getResponseStatus());
							exchange.getMessage().setBody(lCT.get(0).getTextMessage());
						}
					}
					exchange.getMessage().setHeader("hdr_response_list", rmc);
				}
			})
			
//			.process(paymentStatusChannelRequestProcessor)

			
			.log("${body}")
		
			.unmarshal(ctRequestJDF)
			
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					ChnlCreditTransferRequestPojo ctReq = exchange.getMessage().getBody(ChnlCreditTransferRequestPojo.class);
					ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
					channelResponseWr.setResponseCode("U000");
					channelResponseWr.setDate(LocalDateTime.now().format(dateformatter));
					channelResponseWr.setTime(LocalDateTime.now().format(timeformatter));
					channelResponseWr.setContent(new ArrayList<>());
					channelResponseWr.getContent().add(ctReq);
					exchange.getMessage().setBody(channelResponseWr);
				}
			})
			
		;
		
		

	}

}
