package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.ChnlFailureResponsePojo;

@Component
public class CBTransactionFailureProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		String msgType = exchange.getMessage().getHeader("hdr_msgType", String.class);
		
		CBDebitInstructionResponsePojo cbResponse = exchange.getMessage().getHeader("ct_cbresponse", CBDebitInstructionResponsePojo.class);

		ChnlFailureResponsePojo cbFailure = new ChnlFailureResponsePojo();

		String orignReffId = exchange.getMessage().getHeader("hdr_chnlRefId", String.class);
		cbFailure.setReferenceId(orignReffId);

		if (!(null == cbResponse.getAddtInfo()))
			cbFailure.setDescription(cbResponse.getAddtInfo());
		
		cbFailure.setLocation("Corebank service call");
		
		if (msgType.equals("crdttrns"))
			cbFailure.setReason("Debit transaction failed");
		
		
		ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
		channelResponseWr.setFaultResponse(cbFailure);
		
		exchange.getMessage().setBody(channelResponseWr);

	}

}
