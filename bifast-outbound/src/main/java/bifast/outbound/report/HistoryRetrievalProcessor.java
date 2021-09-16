package bifast.outbound.report;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.OutboundMessage;
import bifast.outbound.repository.OutboundMessageRepository;

@Component
public class HistoryRetrievalProcessor implements Processor {

	@Autowired
	private OutboundMessageRepository outboundMessageRepo;

	public void process(Exchange exchange) throws Exception {

		RequestPojo req = exchange.getIn().getBody(RequestPojo.class);
		
			List<OutboundMessage> listOutboundMessage = outboundMessageRepo.findAllByBizMsgIdr(req.getBizMsgIdr());
			if (listOutboundMessage.size() >0 ) {
				System.out.println("nemu outbound_message " + listOutboundMessage.get(0).getBizMsgIdr() + " dgn id:" + listOutboundMessage.get(0).getId());
				exchange.getMessage().setBody(listOutboundMessage.get(0).getFullRequestMessage());
			}
			else {
				exchange.getMessage().setBody(null);	
			}
		
	}

}
