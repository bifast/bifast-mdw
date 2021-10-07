package bifast.inbound.report;

import java.util.List;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.model.CreditTransfer;
import bifast.inbound.model.InboundMessage;
import bifast.inbound.model.Settlement;
import bifast.inbound.repository.CreditTransferRepository;
import bifast.inbound.repository.SettlementRepository;

@Component
public class EnquiryProcessor implements Processor{

	@Autowired
	private SettlementRepository settlementRepo;
	@Autowired
	private CreditTransferRepository creditTransferRepository;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		RequestPojo messageRequest = exchange.getMessage().getBody(RequestPojo.class);
		String response = null;
		
	
		if (messageRequest.getMsgType().equals("Settlement")) {
			List<Settlement> listSettlement = settlementRepo.findByOrgnlCrdtTrnReqBizMsgId(messageRequest.getEndToEndId());

			System.out.println("Cari orgnlCrdtTrnReqBizMsgId = " + messageRequest.getEndToEndId());
			
			if (listSettlement.size() > 0) {
				System.out.println("dan nemu");
				Settlement settlement = listSettlement.get(0);
//				InboundMessage inboundMessage = inboundMessageRepo.findById(settlement.getLogMessageId()).orElse(new InboundMessage());

				String fullMessage = settlement.getFullMessage();
				if (null==fullMessage) {}
				else if (fullMessage.isEmpty()) {} 
				else {
					response = fullMessage;	
				}
			}
		}
		
		else if (messageRequest.getMsgType().equals("Credit Transfer")) {
			
			System.out.println("Cari bizMsgIdr = " + messageRequest.getBizMsgIdr());

			Optional<CreditTransfer> listCT = creditTransferRepository.findByCrdtTrnRequestBizMsgIdr(messageRequest.getBizMsgIdr());
			if (listCT.isPresent()) {
				String fullMessage = listCT.get().getFullRequestMessage();
				if (null==fullMessage) {}
				else if (fullMessage.isEmpty()) {}
				else
					response = listCT.get().getFullRequestMessage();
			}
		}

		exchange.getIn().setBody(response);

	}

}
