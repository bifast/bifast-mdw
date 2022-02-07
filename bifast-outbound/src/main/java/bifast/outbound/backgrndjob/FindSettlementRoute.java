package bifast.outbound.backgrndjob;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.backgrndjob.dto.UndefinedCTPojo;
import bifast.outbound.model.Settlement;
import bifast.outbound.repository.SettlementRepository;

@Component
public class FindSettlementRoute extends RouteBuilder{
	@Autowired private SettlementRepository settlementRepo;

	@Override
	public void configure() throws Exception {
		
		from("direct:findSettlement").routeId("komi.findsettl")

			.log(LoggingLevel.DEBUG, "komi.findsettl", "[PyStsSAF:${exchangeProperty.pr_psrequest.channelNoref}] sedang cari settlement.")
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					UndefinedCTPojo psReq = exchange.getProperty("pr_psrequest", UndefinedCTPojo.class);
					Optional<Settlement> oSettlement = settlementRepo.findByOrgnlEndToEndId(psReq.getEndToEndId());
					if (oSettlement.isPresent()) {
						psReq.setPsStatus("STTL_FOUND");
						psReq.setResponseCode("ACTC");
						psReq.setReasonCode("U000");
					}
					else {
						psReq.setPsStatus("STTL_NOTFOUND");
					}
					exchange.getMessage().setBody(psReq);
				}
			})

			.choice()
				.when().simple("${body.psStatus} == 'STTL_FOUND'")
					.log("[PyStsSAF:${exchangeProperty.pr_psrequest.channelNoref}] Settlement sudah diterima")
				.otherwise()
					.log(LoggingLevel.DEBUG, "komi.findsettl", 
							"[PyStsSAF:${exchangeProperty.pr_psrequest.channelNoref}] belum terima settlement")
			.end()
			
		;		

	}

}
