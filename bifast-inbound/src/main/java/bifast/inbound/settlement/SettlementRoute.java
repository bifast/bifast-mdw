package bifast.inbound.settlement;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettlementRoute extends RouteBuilder {
	@Autowired private SettlementProcessor settlementProcessor;

	@Override
	public void configure() throws Exception {

		from("direct:settlement").routeId("komi.settlement")
			
			// prepare untuk request ke corebank
			.log("Terima settlement ${body}")
			.process(settlementProcessor)

	 		.log("[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] Submit Settlement ke corebank")

	 		.filter().simple("${body.orgnlKomiTrnsId} != null")
				.to("seda:callcb")
			.end()

	 		.log("[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] selesai proses Settlement ")
		;

	}

}
