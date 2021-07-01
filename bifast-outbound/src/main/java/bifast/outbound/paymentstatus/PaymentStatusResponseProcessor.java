package bifast.outbound.paymentstatus;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.pojo.ChannelResponse;
import bifast.outbound.pojo.ChannelResponseMessage;
import bifast.outbound.processor.ChannelResponseService;

@Component
public class PaymentStatusResponseProcessor implements Processor {

	@Autowired
	private ChannelResponseService responseService;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage busMesg = exchange.getIn().getBody(BusinessMessage.class);
		
		ChannelResponse resp = responseService.proses(busMesg);
		
		ChannelPaymentStatusRequest chnReq = exchange.getMessage().getHeader("req_channelReq", ChannelPaymentStatusRequest.class);
		
		ChannelResponseMessage respMesg = new ChannelResponseMessage();
		respMesg.setPaymentStatusRequest(chnReq);
		respMesg.setResponse(resp);
		
		exchange.getIn().setBody(respMesg);
	}

}
