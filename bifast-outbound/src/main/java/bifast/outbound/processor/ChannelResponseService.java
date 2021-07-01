package bifast.outbound.processor;

import org.springframework.stereotype.Service;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.outbound.pojo.ChannelResponse;

@Service
public class ChannelResponseService {

	public ChannelResponse proses (BusinessMessage busMesg) {
		
		PaymentTransaction110 biResp = busMesg.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);

		ChannelResponse resp = new ChannelResponse();
		
		resp.setBizMsgId(busMesg.getAppHdr().getBizMsgIdr());
		
		resp.setStatus(biResp.getTxSts());
		
		resp.setReason(biResp.getStsRsnInf().get(0).getRsn().getPrtry());

		if (!(null == busMesg.getAppHdr().getBizSvc()))
				resp.setResponseType(busMesg.getAppHdr().getBizSvc());
		
		if (!(null == biResp.getStsRsnInf().get(0).getAddtlInf()))
			resp.setAddtInfo(biResp.getStsRsnInf().get(0).getAddtlInf().get(0));
		
		if (!(null == biResp.getOrgnlTxRef().getCdtr().getPty()))
			if (!(null == biResp.getOrgnlTxRef().getCdtr().getPty().getNm()))
				resp.setCreditorName(biResp.getOrgnlTxRef().getCdtr().getPty().getNm());
			
		if (!(null == biResp.getSplmtryData()))
			if (biResp.getSplmtryData().size() > 0) {

				if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTp()))
					resp.setCreditorType(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTp());
				
				if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getId()))
					resp.setCreditorId(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getId());

				if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getRsdntSts()))
					resp.setCreditorResidentStatus(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getRsdntSts());
				
				if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTwnNm()))
					resp.setCreditorTownName(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTwnNm());
			}
		
		
		return resp;

	}
}
