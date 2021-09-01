package bifast.outbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.model.DomainCode;
import bifast.library.repository.DomainCodeRepository;
import bifast.outbound.accountenquiry.ChannelAccountEnquiryReq;
import bifast.outbound.pojo.ChannelRequest;

@Component
public class ValidateInputProcessor implements Processor {

	@Autowired
	private DomainCodeRepository domainCodeRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		String msgType = exchange.getIn().getHeader("rcv_msgType", String.class);
		ChannelRequest req = exchange.getIn().getBody(ChannelRequest.class);

		if (msgType.equals("acctenqr")) {
			// periksa channel
			ChannelAccountEnquiryReq acctEnqReq = req.getAccountEnquiryRequest();
			DomainCode channel = domainCodeRepo.findByGrpAndValue("CHANNEL.TYPE", acctEnqReq.getChannelName()).orElseThrow();
			// TODO periksa kode bank
		}
			
	}

}
