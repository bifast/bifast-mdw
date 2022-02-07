package bifast.outbound.backgrndjob.processor;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.backgrndjob.dto.UndefinedCTPojo;
import bifast.outbound.model.Settlement;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.SettlementRepository;

@Component
public class FindingSettlementProcessor implements Processor {

	@Autowired
	private SettlementRepository settlementRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
		UndefinedCTPojo safCT = (UndefinedCTPojo) rmw.getChannelRequest();

		Optional<Settlement> oSettlement = settlementRepo.findByOrgnlEndToEndId(safCT.getEndToEndId());
		
		if (oSettlement.isPresent()) {
			String strSettlement = oSettlement.get().getFullMessage();
			exchange.getMessage().setBody(strSettlement);
			
			rmw.setCihubEncriptedResponse(strSettlement);
			exchange.setProperty("prop_request_list", rmw);
			
			safCT.setPsStatus("ACCEPTED");
			safCT.setResponseCode("ACTC");
			safCT.setReasonCode("U000");
			exchange.getMessage().setBody(safCT);

		}
		
		else
			exchange.getMessage().setBody(null);
			
	}

}
