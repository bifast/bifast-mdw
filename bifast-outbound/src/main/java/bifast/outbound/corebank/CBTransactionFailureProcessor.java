package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.CBDebitInstructionResponsePojo;
import bifast.outbound.pojo.ChnlFailureResponsePojo;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;

@Component
public class CBTransactionFailureProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		String msgType = exchange.getMessage().getHeader("hdr_msgType", String.class);
		
		CBDebitInstructionResponsePojo cbResponse = exchange.getMessage().getHeader("hdr_cbresponse", CBDebitInstructionResponsePojo.class);

		ChnlFailureResponsePojo cbFailure = new ChnlFailureResponsePojo();

		String orignReffId = exchange.getMessage().getHeader("hdr_chnlRefId", String.class);
		cbFailure.setReferenceId(orignReffId);

		if (!(null == cbResponse.getAddtInfo()))
			cbFailure.setDescription(cbResponse.getAddtInfo());
		
//		cbFailure.setLocation("Corebank service call");
		
		
		ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
		channelResponseWr.setFaultResponse(cbFailure);
		
		exchange.getMessage().setBody(channelResponseWr);

	}

}
