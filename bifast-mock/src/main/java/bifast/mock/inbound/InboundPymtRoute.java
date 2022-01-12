package bifast.mock.inbound;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.mock.inbound.pojo.AERequestPojo;
import bifast.mock.inbound.pojo.CTResponsePojo;
import bifast.mock.inbound.pojo.PaymentRequestPojo;

@Component
public class InboundPymtRoute extends RouteBuilder{
	@Autowired BuildAERequestProcessor buildAERequest;

	JacksonDataFormat paymtRequestJDF = new JacksonDataFormat(PaymentRequestPojo.class);
	JacksonDataFormat busMesgJDF = new JacksonDataFormat(BusinessMessage.class);

	@Override
	public void configure() throws Exception {
		paymtRequestJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		paymtRequestJDF.setInclude("NON_NULL");
		paymtRequestJDF.setInclude("NON_EMPTY");
		paymtRequestJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
//		aeRequestJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		
		busMesgJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		busMesgJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		busMesgJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		busMesgJDF.setInclude("NON_NULL");
		busMesgJDF.setInclude("NON_EMPTY");


		from("direct:payment2").routeId("payment2")
			.setExchangePattern(ExchangePattern.InOnly)
			.log("proses Payment")
			.log("${body}")
			.setHeader("hdr_pymtreq", simple("${body}"))
			
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					PaymentRequestPojo pymtReq = exchange.getMessage().getBody(PaymentRequestPojo.class);
					AERequestPojo aeReq = new AERequestPojo();
					aeReq.setAmount(pymtReq.getAmount());
					aeReq.setCreditorAccountNo(pymtReq.getCreditorAccountNo());
					aeReq.setPaymentInfo(pymtReq.getPaymentInfo());
					exchange.getMessage().setBody(aeReq);
				}
			})
			
			.to("direct:inb_ae")
			
			.log("AE Response: ${body}")
			.unmarshal(busMesgJDF)
			
			.setHeader("inb_aeresponse", simple("${body}"))
			.setHeader("inb_respCode", simple("${body.document.fiToFIPmtStsRpt.txInfAndSts[0].txSts}"))
			.log("${header.inb_respCode}")

			.choice()
				.when().simple("${header.inb_respCode} == 'ACTC'")
					.log("lanjut dengan CT")
					.to("direct:inb_ct")
				.otherwise()
					.log("kita sudahi sampai disini")
					.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							BusinessMessage msg = exchange.getMessage().getBody(BusinessMessage.class);
							String responseCode = exchange.getMessage().getHeader("inb_aeresponse", String.class);
							String reasonCode = msg.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getStsRsnInf().get(0).getRsn().getPrtry();
							CTResponsePojo inbResponse = new CTResponsePojo();
							inbResponse.setResponseCode(responseCode);
							inbResponse.setReasonCode(reasonCode);
							exchange.getMessage().setBody(inbResponse);
						}
					})
			.end()

		;
	}

}
