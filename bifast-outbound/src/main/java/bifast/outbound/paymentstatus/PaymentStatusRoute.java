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

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.paymentstatus.processor.PaymentStatusRequestProcessor;
import bifast.outbound.paymentstatus.processor.PaymentStatusResponseProcessor;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlCreditTransferRequestPojo;
import bifast.outbound.pojo.chnlrequest.ChnlPaymentStatusRequestPojo;
import bifast.outbound.pojo.chnlrequest.PaymentStatusRequestSAFPojo;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.pojo.chnlresponse.ChnlCreditTransferResponsePojo;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.report.pojo.RequestPojo;
import bifast.outbound.report.pojo.ResponsePojo;
import bifast.outbound.repository.ChannelTransactionRepository;
import bifast.outbound.repository.CreditTransferRepository;

@Component
public class PaymentStatusRoute extends RouteBuilder {
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");
   
    @Autowired
    private PaymentStatusChannelRequestProcessor paymentStatusChannelRequestProcessor;
	@Autowired
	private PaymentStatusRequestProcessor paymentStatusRequestProcessor;
	@Autowired
	private PaymentStatusResponseProcessor paymentStatusResponseProcessor;
	@Autowired
	private ChannelTransactionRepository channelRequestRepo;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;

	@Autowired
	private CreditTransferRepository creditTransferRepo;
	
	JacksonDataFormat ctRequestJDF = new JacksonDataFormat(ChnlCreditTransferRequestPojo.class);

	@Override
	public void configure() throws Exception {
		ctRequestJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		ctRequestJDF.setInclude("NON_NULL");
		ctRequestJDF.setInclude("NON_EMPTY");
		ctRequestJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		ctRequestJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		
		from("direct:pschnl").routeId("komi.ps.chnl")
			.log("Payment_status channel")
			
			.process(paymentStatusChannelRequestProcessor)

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
