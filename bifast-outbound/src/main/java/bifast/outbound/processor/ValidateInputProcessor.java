package bifast.outbound.processor;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.model.DomainCode;
import bifast.library.repository.DomainCodeRepository;
import bifast.outbound.accountenquiry.ChannelAccountEnquiryReq;
import bifast.outbound.pojo.ChannelRequestWrapper;

@Component
public class ValidateInputProcessor implements Processor  {

	@Autowired
	private DomainCodeRepository domainCodeRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		String msgType = exchange.getIn().getHeader("hdr_msgType", String.class);
		Object objRequest = exchange.getIn().getBody(Object.class);

		String className = objRequest.getClass().getName();
		
		if (className.equals("bifast.outbound.accountenquiry.ChannelAccountEnquiryReq")) {
			ChannelAccountEnquiryReq acctEnqReq = (ChannelAccountEnquiryReq) objRequest;

			// periksa channel
			exchange.getMessage().setHeader("hdr_errorlocation", "AccountEnquiryRequest/channel");
			DomainCode channelDC = domainCodeRepo.findByGrpAndValue("CHANNEL.TYPE", acctEnqReq.getChannel()).orElseThrow();
			acctEnqReq.setChannel(channelDC.getKey());

			// periksa categoryPurpose
			exchange.getMessage().setHeader("hdr_errorlocation", "AccountEnquiryRequest/categoryPurpose");
			DomainCode categoryPurposeDC = domainCodeRepo.findByGrpAndValue("CATEGORY.PURPOSE", acctEnqReq.getCategoryPurpose()).orElseThrow();
			acctEnqReq.setCategoryPurpose(categoryPurposeDC.getKey());

		}
			
	}

}
