package bifast.outbound.accountenquiry.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.pojo.flat.FlatPrxy004Pojo;

@Component
public class AEProxyEnrichmentProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {

		Object resp = exchange.getMessage().getBody(Object.class);
		String responseClassName = resp.getClass().getSimpleName();

		if (responseClassName.equals("FlatPrxy004Pojo")) {
			FlatPrxy004Pojo prx004 = (FlatPrxy004Pojo)resp;
			
			RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
			ChnlAccountEnquiryRequestPojo aeReq = rmw.getChnlAccountEnquiryRequest();
			aeReq.setCreditorAccountNumber(prx004.getAccountNumber());
			
			exchange.getMessage().setBody(aeReq);

		}
	}
}
