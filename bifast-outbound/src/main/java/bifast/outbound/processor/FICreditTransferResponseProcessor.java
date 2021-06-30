package bifast.outbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.outbound.pojo.ChannelCreditTransferMessage;
import bifast.outbound.pojo.ChannelCreditTransferRequest;
import bifast.outbound.pojo.ChannelCreditTransferResponse;

@Component
public class FICreditTransferResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		BusinessMessage obj_crdtrnResp = exchange.getMessage().getHeader("resp_objbi", BusinessMessage.class);
		PaymentTransaction110 biResp = obj_crdtrnResp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);
		
		ChannelCreditTransferRequest chnRequest = exchange.getMessage().getHeader("req_channelReq", ChannelCreditTransferRequest.class);

		ChannelCreditTransferResponse chnResponse = new ChannelCreditTransferResponse();
		
		// from CI-HUB response
		chnResponse.setBizMsgId(obj_crdtrnResp.getAppHdr().getBizMsgIdr());
		chnResponse.setStatus(biResp.getTxSts());
		chnResponse.setReason(biResp.getStsRsnInf().get(0).getRsn().getPrtry());
		
		ChannelCreditTransferMessage ctMesg = new ChannelCreditTransferMessage();
		ctMesg.setCreditTransferRequest(chnRequest);
		ctMesg.setCreditTransferResponse(chnResponse);
		
		exchange.getIn().setBody(ctMesg);
		
	
	}

}
