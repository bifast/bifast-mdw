package bifast.outbound.paymentstatus;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.admi002.MessageRejectV01;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.outbound.pojo.ChannelReject;

@Component
public class PaymentStatusResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		BusinessMessage obj_pymnStatusResp = exchange.getIn().getBody(BusinessMessage.class);

		PaymentStatusResponse psResponse = new PaymentStatusResponse();

		
		if (null == obj_pymnStatusResp.getDocument().getMessageReject())  {   // cek apakah response berupa bukan message reject 

			PaymentTransaction110 biResp = obj_pymnStatusResp.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);

			// from CI-HUB response

			psResponse.setCtBizMsgId(biResp.getOrgnlEndToEndId());
			
			psResponse.setStatus(biResp.getTxSts());
			psResponse.setReason(biResp.getStsRsnInf().get(0).getRsn().getPrtry());
			if (!(null == biResp.getStsRsnInf().get(0).getAddtlInf()))
				psResponse.setAddtInfo(biResp.getStsRsnInf().get(0).getAddtlInf().get(0));
	
			if (!(null == biResp.getOrgnlTxRef().getCdtr().getPty()))
				if (!(null == biResp.getOrgnlTxRef().getCdtr().getPty().getNm()))
					psResponse.setCreditorName(biResp.getOrgnlTxRef().getCdtr().getPty().getNm());
	
			if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getId()))
				psResponse.setCreditorId(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getId());
	
			if (!(null == biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTp()))
				psResponse.setCreditorType(biResp.getSplmtryData().get(0).getEnvlp().getCdtr().getTp());
	
			exchange.getIn().setBody(psResponse);

		}
		
		else {   // ternyata berupa message reject
			MessageRejectV01 rejectResp = obj_pymnStatusResp.getDocument().getMessageReject();

			ChannelReject reject = new ChannelReject();

			reject.setReference(rejectResp.getRltdRef().getRef());
			reject.setReason(rejectResp.getRsn().getRjctgPtyRsn());
			reject.setDescription(rejectResp.getRsn().getRsnDesc());
			reject.setLocation(rejectResp.getRsn().getErrLctn());
			reject.setAdditionalData(rejectResp.getRsn().getAddtlData());
	
			exchange.getIn().setBody(reject);

		}
	}
}
