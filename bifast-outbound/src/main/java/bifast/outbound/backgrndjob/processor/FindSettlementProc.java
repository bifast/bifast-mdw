package bifast.outbound.backgrndjob.processor;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.backgrndjob.dto.UndefinedCTPojo;
import bifast.outbound.model.Settlement;
import bifast.outbound.repository.SettlementRepository;

@Component
public class FindSettlementProc implements Processor {

	@Autowired
	private SettlementRepository settlementRepo;
	
	private static Logger logger = LoggerFactory.getLogger(FindSettlementProc.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		UndefinedCTPojo psReq = exchange.getProperty("pr_psrequest", UndefinedCTPojo.class);
				
		Optional<Settlement> oSettlement = settlementRepo.findByOrgnlEndToEndId(psReq.getEndToEndId());
		if (oSettlement.isPresent()) {
			psReq.setPsStatus("STTL_FOUND");
			psReq.setResponseCode("ACTC");
			psReq.setReasonCode("U000");

			logger.debug("[PyStsSAF:" + psReq.getChannelNoref() + "] Settlement sudah diterima");
		}
		else {
			psReq.setPsStatus("STTL_NOTFOUND");
			logger.debug("[PyStsSAF:" + psReq.getChannelNoref() + "] Settlement belum diterima");
		}
		exchange.getMessage().setBody(psReq);
		
	}

}
