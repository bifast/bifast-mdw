package bifast.outbound.history;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.model.OutboundMessage;
import bifast.library.repository.OutboundMessageRepository;

@Component
public class HistoryRetrievalProcessor implements Processor {

	@Autowired
	private OutboundMessageRepository outboundMessageRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		HistoryRetrievalRequest req = exchange.getIn().getBody(HistoryRetrievalRequest.class);
		
		List<OutboundMessage> listOutboundMessage = outboundMessageRepo.findAllByBizMsgIdr(req.getBizMsgIdr());
		if (listOutboundMessage.size() >0 ) {
			exchange.getMessage().setBody(listOutboundMessage.get(0).getFullRequestMessage());
		}
		else {
			exchange.getMessage().setBody(null);	
		}
		
	}

}
