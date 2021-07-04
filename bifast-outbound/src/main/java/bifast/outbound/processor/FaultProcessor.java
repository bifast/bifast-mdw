package bifast.outbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.accountenquiry.ChannelAccountEnquiryReq;
import bifast.outbound.pojo.ChannelReject;
import bifast.outbound.pojo.ChannelResponseMessage;

@Component
public class FaultProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

		ChannelReject reject = new ChannelReject();
		reject.setLocation("Internal/TransactionRoute");
		reject.setReason(cause.getMessage());
		reject.setDescription(cause.getStackTrace()[0].toString());

		ChannelAccountEnquiryReq chnReq = exchange.getMessage().getHeader("req_channelReq",ChannelAccountEnquiryReq.class);
		
		ChannelResponseMessage responseMessage = new ChannelResponseMessage();
		responseMessage.setAccountEnquiryRequest(chnReq);
		responseMessage.setRejection(reject);
		
		exchange.getIn().setBody(responseMessage);
	}

}
