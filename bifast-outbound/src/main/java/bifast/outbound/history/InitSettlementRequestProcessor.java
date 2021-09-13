package bifast.outbound.history;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class InitSettlementRequestProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		System.out.println("InitSettlementRequestProcessor");
		BusinessMessage reqData = exchange.getMessage().getBody(BusinessMessage.class);
		RequestPojo histReq = new RequestPojo();
		
		histReq.setMsgType("Settlement");
		histReq.setEndToEndId(reqData.getAppHdr().getBizMsgIdr());
		exchange.getIn().setBody(histReq);
	}

}
