package bifast.outbound.paymentstatus;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class BuildPaymentStatusRequestProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage reqData = exchange.getMessage().getBody(BusinessMessage.class);
		ChnlPaymentStatusRequestPojo request = new ChnlPaymentStatusRequestPojo();
		
		request.setEndToEndId(reqData.getAppHdr().getBizMsgIdr());
		request.setRecptBank(reqData.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());
		
		exchange.getIn().setBody(request);
	}

}
