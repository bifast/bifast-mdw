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

		Object objChnlRequest = exchange.getMessage().getHeader("hdr_channelRequest", Object.class);
		String chnlRequestClassName = objChnlRequest.getClass().getName();

		CBDebitInstructionResponsePojo cbResponse = exchange.getMessage().getHeader("ct_cbresponse", CBDebitInstructionResponsePojo.class);

		ChnlFailureResponsePojo cbFailure = new ChnlFailureResponsePojo();

		String orignReffId = (String) objChnlRequest.getClass().getMethod("getOrignReffId").invoke(objChnlRequest);
		cbFailure.setReferenceId(orignReffId);

		cbFailure.setDescription(cbResponse.getAddtInfo());
		cbFailure.setLocation("Corebank service call");
		
		if (chnlRequestClassName.equals("bifast.outbound.credittransfer.ChnlCreditTransferRequestPojo"))
			cbFailure.setReason("Debit transaction failed");
		
		
		ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
		channelResponseWr.setFaultResponse(cbFailure);
		
		exchange.getMessage().setBody(channelResponseWr);

	}

}
