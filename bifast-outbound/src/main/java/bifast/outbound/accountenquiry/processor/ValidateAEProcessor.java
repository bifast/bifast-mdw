package bifast.outbound.accountenquiry.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.exception.InputValidationException;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlAccountEnquiryRequestPojo;

@Component
public class ValidateAEProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		ChnlAccountEnquiryRequestPojo aeReq = rmw.getChnlAccountEnquiryRequest();
		
		if (null == aeReq.getCreditorAccountNumber())
			if (null == aeReq.getProxyId())
				throw new InputValidationException("CreditorAccountNumber atau ProxyId tidak boleh kosong.");
		
	}

}
