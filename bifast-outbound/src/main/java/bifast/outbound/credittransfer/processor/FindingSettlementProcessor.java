package bifast.outbound.credittransfer.processor;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.Settlement;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.SettlementRepository;

@Component
public class FindingSettlementProcessor implements Processor {

	@Autowired
	private SettlementRepository settlementRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		String orglBizMsgId = rmw.getCreditTransferRequest().getAppHdr().getBizMsgIdr();
		Optional<Settlement> oSettlement = settlementRepo.findByOrgnlCrdtTrnReqBizMsgId(orglBizMsgId);
		
		if (oSettlement.isPresent()) {
			String strSettlement = oSettlement.get().getFullMessage();
			exchange.getMessage().setBody(strSettlement);
			
			rmw.setCihubEncriptedResponse(strSettlement);
			exchange.getMessage().setHeader("hdr_request_list", rmw);
		}
		
		else
			exchange.getMessage().setBody(null);
			
	}

}
