package bifast.outbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.ChannelRequest;

@Component
public class CheckChannelRequestTypeProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		ChannelRequest req = exchange.getIn().getBody(ChannelRequest.class);
		
		if (!(null == req.getAccountEnquiryRequest())) {
			exchange.getMessage().setHeader("rcv_msgType", "AccountEnquiry");
			exchange.getMessage().setHeader("rcv_channel", req.getAccountEnquiryRequest());
		}

		else if (!(null == req.getCreditTransferRequest())) {
			exchange.getMessage().setHeader("rcv_msgType", "CreditTransfer");
			exchange.getMessage().setHeader("rcv_channel", req.getCreditTransferRequest());
		}
		
		else if  (!(null == req.getFiCreditTransferRequest())) {
			exchange.getMessage().setHeader("rcv_msgType", "FICreditTransfer");
			exchange.getMessage().setHeader("rcv_channel", req.getFiCreditTransferRequest());
		}
		
		else if (!(null == req.getPaymentStatusRequest())) {
			exchange.getMessage().setHeader("rcv_msgType", "PaymentStatus");
			exchange.getMessage().setHeader("rcv_channel", req.getPaymentStatusRequest());
		}
		
		else if (!(null == req.getReverseCreditTransferRequest())) {
			exchange.getMessage().setHeader("rcv_msgType", "ReverseCreditTransfer");
			exchange.getMessage().setHeader("rcv_channel", req.getReverseCreditTransferRequest());
		}

	}

}
