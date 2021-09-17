package bifast.mock.processor;

import java.util.List;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.mock.persist.MockPacs002;
import bifast.mock.persist.MockPacs002Repository;
import bifast.mock.persist.OutboundMessage;
import bifast.mock.persist.OutboundMessageRepository;

@Component
public class PaymentStatusResponseProcessor implements Processor{

	@Autowired
	private MockPacs002Repository mockPacs002Repo;


	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage psRequest = exchange.getMessage().getHeader("objRequest", BusinessMessage.class);
		String reqEndToEndId = psRequest.getDocument().getFIToFIPmtStsReq().getTxInf().get(0).getOrgnlEndToEndId();

		List<MockPacs002> listPacs002 = mockPacs002Repo.findByOrgnlEndToEndId(reqEndToEndId);
		
		if (listPacs002.size()>0) {
			MockPacs002 pacs002 = listPacs002.get(0);
			exchange.getMessage().setBody(pacs002.getFullMessage());
		}
		else
			exchange.getMessage().setBody(null);

	}

}
