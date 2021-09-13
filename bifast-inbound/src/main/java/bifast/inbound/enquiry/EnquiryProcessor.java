package bifast.inbound.enquiry;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.model.InboundMessage;
import bifast.inbound.model.Settlement;
import bifast.inbound.repository.InboundMessageRepository;
import bifast.inbound.repository.SettlementRepository;

@Component
public class EnquiryProcessor implements Processor{

	@Autowired
	private SettlementRepository settlementRepo;
	@Autowired
	private InboundMessageRepository inboundMessageRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		RequestPojo messageRequest = exchange.getMessage().getBody(RequestPojo.class);
		String response = null;
		
	
		if (messageRequest.getMsgType().equals("Settlement")) {
			List<Settlement> listSettlement = settlementRepo.findByOrgnlCrdtTrnReqBizMsgId(messageRequest.getEndToEndId());
			if (listSettlement.size() > 0) {
				Settlement settlement = listSettlement.get(0);
				InboundMessage inboundMessage = inboundMessageRepo.findById(settlement.getLogMessageId()).orElse(new InboundMessage());

				String fullMessage = inboundMessage.getFullRequestMessage();
				if (null==fullMessage) {}
				else if (fullMessage.isEmpty()) {}
				else
					response = inboundMessage.getFullRequestMessage();	
			}
		}
		
		else if (messageRequest.getMsgType().equals("Credit Transfer")) {
			List<InboundMessage> listInboundMessage = inboundMessageRepo.findByBizMsgIdr(messageRequest.getBizMsgIdr());
			if (listInboundMessage.size() > 0) {
				String fullMessage = listInboundMessage.get(0).getFullRequestMessage();
				if (null==fullMessage) {}
				else if (fullMessage.isEmpty()) {}
				else
					response = listInboundMessage.get(0).getFullRequestMessage();
			}
		}

		exchange.getIn().setBody(response);

	}

}
