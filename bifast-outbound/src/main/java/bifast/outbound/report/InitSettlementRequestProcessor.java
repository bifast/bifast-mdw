package bifast.outbound.report;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.report.pojo.RequestPojo;

@Component
public class InitSettlementRequestProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		BusinessMessage reqData = exchange.getMessage().getBody(BusinessMessage.class);
		RequestPojo histReq = new RequestPojo();
		
		histReq.setMsgType("Settlement");
		histReq.setEndToEndId(reqData.getAppHdr().getBizMsgIdr());
		exchange.getIn().setBody(histReq);
	}

}
