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

@Component
public class InboundCTRoute extends RouteBuilder{
	@Autowired BuildAERequestProcessor buildAERequest;
	@Autowired BuildCTRequestProcessor buildCTRequest;

	JacksonDataFormat aeRequestJDF = new JacksonDataFormat(AERequestPojo.class);
	JacksonDataFormat busMesgJDF = new JacksonDataFormat(BusinessMessage.class);

	@Override
	public void configure() throws Exception {
		
		busMesgJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		busMesgJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		busMesgJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		busMesgJDF.setInclude("NON_NULL");
		busMesgJDF.setInclude("NON_EMPTY");


		from("direct:inb_ct").routeId("inbound_ct")
			.setExchangePattern(ExchangePattern.InOnly)
			.log("start kirim ct")
			.process(buildCTRequest)
			.marshal(busMesgJDF)
			.log("CT Request: ${body}")
			
			.stop()
			.to("rest:post:?host={{komi.inbound-url}}"
					+ "&exchangePattern=InOnly"
						+ "&bridgeEndpoint=true"
					)
			.convertBodyTo(String.class)
			.log("CT Response: ${body}")
			.unmarshal(busMesgJDF)
			.setHeader("inb_ctResponse", simple("${body}"))

			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					BusinessMessage msg = exchange.getMessage().getBody(BusinessMessage.class);
					String responseCode = msg.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts();
					String reasonCode = msg.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getStsRsnInf().get(0).getRsn().getPrtry();
					CTResponsePojo inbResponse = new CTResponsePojo();
					inbResponse.setResponseCode(responseCode);
					inbResponse.setReasonCode(reasonCode);
					exchange.getMessage().setBody(inbResponse);
				}
			})
			

		;
	}

}
