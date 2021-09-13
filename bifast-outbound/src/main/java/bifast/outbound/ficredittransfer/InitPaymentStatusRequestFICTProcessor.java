package bifast.outbound.ficredittransfer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.paymentstatus.ChnlPaymentStatusRequestPojo;

@Component
public class InitPaymentStatusRequestFICTProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage reqData = exchange.getMessage().getBody(BusinessMessage.class);
		ChnlPaymentStatusRequestPojo request = new ChnlPaymentStatusRequestPojo();
		
		request.setEndToEndId(reqData.getAppHdr().getBizMsgIdr());
		exchange.getIn().setBody(request);
	}

}
