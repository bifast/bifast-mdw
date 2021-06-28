package bifast.outbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.outbound.pojo.ChannelCreditTransferRequest;
import bifast.outbound.pojo.ChannelCreditTransferResponse;

@Component
public class CreditTransferResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		BusinessMessage obj_crdtrnResp = exchange.getMessage().getHeader("resp_objbicrdttrn", BusinessMessage.class);
		PaymentTransaction110 biResp = obj_crdtrnResp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);
		
		ChannelCreditTransferRequest chnRequest = exchange.getMessage().getHeader("req_channelReq", ChannelCreditTransferRequest.class);

		ChannelCreditTransferResponse chnResponse = new ChannelCreditTransferResponse();
		
		chnResponse.setAmount(chnRequest.getAmount());
		chnResponse.setCategoryPurpose(chnRequest.getCategoryPurpose());
		chnResponse.setChannel(chnRequest.getChannel());
		chnResponse.setCrdtAccountNo(chnRequest.getCrdtAccountNo());
		chnResponse.setCrdtAccountType(chnRequest.getCrdtAccountType());
		chnResponse.setCrdtId(chnRequest.getCrdtId());
		chnResponse.setCrdtIdType(chnRequest.getCrdtIdType());
		chnResponse.setDbtrAccountNo(chnRequest.getDbtrAccountNo());
		chnResponse.setDbtrAccountType(chnRequest.getDbtrAccountType());
		chnResponse.setDbtrId(chnRequest.getDbtrId());
		chnResponse.setDbtrIdType(chnRequest.getDbtrIdType());
		chnResponse.setIntrnRefId(chnRequest.getIntrnRefId());
		chnResponse.setPaymentInfo(chnRequest.getPaymentInfo());
		chnResponse.setRecptBank(chnRequest.getRecptBank());
	
		// from CI-HUB response
		chnResponse.setBizMsgId(obj_crdtrnResp.getAppHdr().getBizMsgIdr());
		chnResponse.setStatus(biResp.getTxSts());
		chnResponse.setReason(biResp.getStsRsnInf().get(0).getRsn().getPrtry());
		chnResponse.setAddtInfo(biResp.getStsRsnInf().get(0).getAddtlInf().get(0));
		chnResponse.setCreditorName(biResp.getOrgnlTxRef().getCdtr().getPty().getNm());
		chnResponse.setCreditorType(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTp());
		chnResponse.setCreditorId(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getId());
		chnResponse.setCreditorResidentStatus(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getRsdntSts());
		chnResponse.setCreditorTownName(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTwnNm());

		exchange.getIn().setBody(chnResponse);
	
	}

}
