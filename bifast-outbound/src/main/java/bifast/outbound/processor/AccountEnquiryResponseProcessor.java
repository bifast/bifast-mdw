package bifast.outbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.outbound.pojo.ChannelAccountEnquiryReq;
import bifast.outbound.pojo.ChannelAccountEnquiryResp;

@Component
public class AccountEnquiryResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage busMesg = exchange.getIn().getBody(BusinessMessage.class);
		PaymentTransaction110 biResp = busMesg.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);
		
		ChannelAccountEnquiryReq chnReq = exchange.getMessage().getHeader("req_channelReq",ChannelAccountEnquiryReq.class);
		
		ChannelAccountEnquiryResp chnResp = new ChannelAccountEnquiryResp();
		chnResp.setAmount(chnReq.getAmount());
		chnResp.setCategoryPurpose(chnReq.getCategoryPurpose());
		chnResp.setChannelType(chnReq.getChannelType());
		
		chnResp.setCreditorAccountNumber(biResp.getOrgnlTxRef().getCdtrAcct().getId().getOthr().getId());
		chnResp.setCreditorAccountType(biResp.getOrgnlTxRef().getCdtrAcct().getTp().getPrtry());
		chnResp.setCreditorIdNumber(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getId());
		
		chnResp.setCreditorName(biResp.getOrgnlTxRef().getCdtr().getPty().getNm());
		chnResp.setCreditorResidentStatus(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getRsdntSts());
		chnResp.setCreditorTownName(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTwnNm());
		chnResp.setCreditorType(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTp());
		
		chnResp.setStatus(biResp.getTxSts());
		chnResp.setReason(biResp.getStsRsnInf().get(0).getRsn().getPrtry());
		
		chnResp.setReceivingParticipant(chnReq.getReceivingParticipant());
		
		exchange.getIn().setBody(chnResp);
	}

}
