package fransmz.bifast.outbound.service;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.model.InboundMessage;
import bifast.library.repository.InboundMessageRepository;

@Component
public class TestProcessor implements Processor {

	@Autowired
	private InboundMessageRepository inboundRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		Optional<InboundMessage> optMesg = inboundRepo.findByBizMsgIdr("2020062074CO44702");
		if (optMesg.isPresent()) {
			InboundMessage mesg = optMesg.get();
			System.out.println("processor: " + mesg.getFullMessage());
		}
		else System.out.println("kosongggg");
	}

}
