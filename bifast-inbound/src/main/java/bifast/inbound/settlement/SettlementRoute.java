package bifast.inbound.settlement;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettlementRoute extends RouteBuilder {
	@Autowired private SettlementProcessor settlementProcessor;

	@Override
	public void configure() throws Exception {

		from("direct:settlement")
			
			// prepare untuk request ke corebank
			.process(settlementProcessor)

	 		.log("[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] Akan submit Settlement ke corebank")

			.to("seda:callcb")

	 		.log("[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] selesai call AE corebank")
//			.process(aeResponseProcessor)
		;

	}

}
