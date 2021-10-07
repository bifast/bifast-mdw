package bifast.outbound.credittransfer.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.credittransfer.ChnlCreditTransferRequestPojo;

@Component
public class BuildAERequestProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		ChnlCreditTransferRequestPojo chnlCTReq = exchange.getMessage().getBody(ChnlCreditTransferRequestPojo.class);
		
		ChnlAccountEnquiryRequestPojo chnlAEReq = new ChnlAccountEnquiryRequestPojo();
		
		chnlAEReq.setAmount(chnlCTReq.getAmount());
		chnlAEReq.setCategoryPurpose(chnlCTReq.getCategoryPurpose());
		chnlAEReq.setChannel(chnlCTReq.getChannel());
		chnlAEReq.setIntrnRefId(chnlCTReq.getOrignReffId());
		chnlAEReq.setCreditorAccountNumber(chnlCTReq.getCrdtAccountNo());
		chnlAEReq.setRecptBank(chnlCTReq.getRecptBank());
		
		exchange.getMessage().setBody(chnlAEReq);
	}

}
