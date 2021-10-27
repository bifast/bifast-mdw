package bifast.inbound.accountenquiry;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.inbound.service.FlattenIsoMessageService;
import bifast.inbound.service.JacksonDataFormatService;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class AccountEnquiryRoute extends RouteBuilder {

	@Autowired
	private AECorebankReponseProcessor aeCorebankReponseProcessor;
	@Autowired
	private BuildAccountEnquiryRequestMessageProcessor buildAccountEnquiryRequestProcessor;
	@Autowired
	private FlattenIsoMessageService flatteningIsoMessageService;
	@Autowired
	private JacksonDataFormatService jdfService;

	@Override
	public void configure() throws Exception {
//		onException(org.apache.http.conn.HttpHostConnectException.class)
//			.maximumRedeliveries(5).delay(1000)
//			.log("Route level onException")
////			.handled(true)
//			.setBody(constant(null))
//		;
		
		JacksonDataFormat jdf = jdfService.basic(FlatPacs008Pojo.class);
		
		from("direct:accountenq").routeId("komi.accountenq")
				
			.log(LoggingLevel.DEBUG, "komi.accountenq", "[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] ${body}")

			//flattening disini
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					BusinessMessage inboundMessage = exchange.getMessage().getBody(BusinessMessage.class);
					FlatPacs008Pojo flat = flatteningIsoMessageService.flatteningPacs008(inboundMessage);
					exchange.getMessage().setBody(flat);
				}
				
			})
			
			.marshal(jdf)
			.log("${body}")
			.unmarshal(jdf)
			
//			.stop()
			// prepare untuk request ke corebank
			.process(buildAccountEnquiryRequestProcessor)
				
			//TODO call corebank Account Enquiry
			
			
			.process(aeCorebankReponseProcessor)
			

		;
	}

}
