package bifast.inbound.reversecrdttrns;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.inbound.corebank.pojo.CbCreditRequestPojo;
import bifast.inbound.corebank.pojo.CbCreditResponsePojo;
import bifast.inbound.credittransfer.CTCorebankRequestProcessor;
import bifast.inbound.processor.EnrichmentAggregator;
import bifast.inbound.reversecrdttrns.pojo.DebitReversalRequestPojo;
import bifast.inbound.service.JacksonDataFormatService;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class ReverseCTRoute extends RouteBuilder {
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private ReverseCTProcessor reverseCTProcessor;
	@Autowired private ReverseCTResponseProcessor responseProcessor;
	

	@Override
	public void configure() throws Exception {

		JacksonDataFormat businessMessageJDF = jdfService.wrapRoot(BusinessMessage.class);
		JacksonDataFormat debitReversalReqJDF = jdfService.basic(DebitReversalRequestPojo.class);
		JacksonDataFormat cbCreditTransferResponseJDF = jdfService.wrapRoot(CbCreditResponsePojo.class);

		from("direct:reverct").routeId("reversect")

			.log("akan reverse Credit Transfer")
			.process(reverseCTProcessor)
			.log("${body.class}")
			.filter().simple("${body.class} endsWith 'DebitReversalRequestPojo' ")
				.log("akan call cb debit_reversal")
				.to("direct:cbdebitreversal")
				.log("selesai call cb debit_reversal ${body}")
				
			.end()
			
			.process(responseProcessor)		
			
		;

	
	}

}
