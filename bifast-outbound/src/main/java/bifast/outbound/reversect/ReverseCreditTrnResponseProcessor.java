package bifast.outbound.reversect;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.outbound.pojo.ChannelResponse;
import bifast.outbound.pojo.ChannelResponseMessage;

@Component
public class ReverseCreditTrnResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		BusinessMessage obj_crdtrnResp = exchange.getMessage().getHeader("resp_objbi", BusinessMessage.class);
		PaymentTransaction110 biResp = obj_crdtrnResp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);
		
		ChannelReverseCreditTransferRequest chnRequest = exchange.getMessage().getHeader("req_channelReq", ChannelReverseCreditTransferRequest.class);

		ChannelResponse chnResponse = new ChannelResponse();
		
		// from CI-HUB response
		chnResponse.setBizMsgId(obj_crdtrnResp.getAppHdr().getBizMsgIdr());
		chnResponse.setResponseType(obj_crdtrnResp.getAppHdr().getBizSvc());
		
		chnResponse.setStatus(biResp.getTxSts());
		chnResponse.setReason(biResp.getStsRsnInf().get(0).getRsn().getPrtry());
		if (!(null == biResp.getStsRsnInf().get(0).getAddtlInf()))
			chnResponse.setAddtInfo(biResp.getStsRsnInf().get(0).getAddtlInf().get(0));

		if (!(null == biResp.getOrgnlTxRef().getCdtr().getPty()))
			if (!(null == biResp.getOrgnlTxRef().getCdtr().getPty().getNm()))
				chnResponse.setCreditorName(biResp.getOrgnlTxRef().getCdtr().getPty().getNm());

		if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getId()))
			chnResponse.setCreditorId(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getId());

		if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTp()))
			chnResponse.setCreditorType(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTp());

		if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getRsdntSts()))
			chnResponse.setCreditorResidentStatus(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getRsdntSts());

		if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTwnNm()))
			chnResponse.setCreditorTownName(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTwnNm());

//		ChannelReverseCreditTransferMessage ctMesg = new ChannelReverseCreditTransferMessage();
		ChannelResponseMessage ctMesg = new ChannelResponseMessage();
		ctMesg.setReverseCreditTransferRequest(chnRequest);
		ctMesg.setResponse(chnResponse);
		
		exchange.getIn().setBody(ctMesg);
		
	
	}

}
