package bifast.mock.processor;

import java.util.List;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.mock.persist.OutboundMessage;
import bifast.mock.persist.OutboundMessageRepository;

@Component
public class PaymentStatusResponseProcessor implements Processor{

	@Autowired
	private MessageHistory messageHistory;
	@Autowired
	private OutboundMessageRepository outboundMessageRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		List<OutboundMessage> optOutboundMessage = outboundMessageRepo.findAllByBizMsgIdr("20210912MNDRIDJA010O0100000085");
		String fullMsg = null;
		if (optOutboundMessage.size()>0)
			fullMsg = optOutboundMessage.get(0).getFullResponseMsg();

		else 
			System.out.println("Kok ga ada ya ");
		
		exchange.getIn().setBody(fullMsg);
		
	}

}
