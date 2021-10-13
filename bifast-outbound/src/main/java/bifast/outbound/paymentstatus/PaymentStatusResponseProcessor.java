package bifast.outbound.paymentstatus;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.admi002.MessageRejectV01;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.outbound.pojo.ChnlFailureResponsePojo;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.pojo.chnlresponse.ChnlCreditTransferResponsePojo;

@Component
public class PaymentStatusResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		ChnlPaymentStatusRequestPojo chnReq = exchange.getMessage().getHeader("ps_channelrequest",ChnlPaymentStatusRequestPojo.class);

		ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();

		BusinessMessage busMesg = exchange.getIn().getBody(BusinessMessage.class);

		if (null == busMesg) {
			
			ChnlFailureResponsePojo reject = new ChnlFailureResponsePojo();

			reject.setReferenceId(chnReq.getIntrnRefId());
			
			String errorStatus = exchange.getMessage().getHeader("hdr_error_status", String.class);
			String errorMesg = exchange.getMessage().getHeader("hdr_error_mesg", String.class);
			reject.setReason(errorStatus);
			reject.setDescription(errorMesg);
			
			channelResponseWr.setFaultResponse(reject);
			exchange.getIn().setBody(channelResponseWr);

		}

		else if (null == busMesg.getDocument().getMessageReject())  {   // cek apakah response berupa bukan message reject 
			
			PaymentTransaction110 biResp = busMesg.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);
			ChnlCreditTransferResponsePojo chnResponse = new ChnlCreditTransferResponsePojo();

			// from CI-HUB response
			chnResponse.setOrignReffId(chnReq.getIntrnRefId());
			chnResponse.setBizMsgId(busMesg.getAppHdr().getBizMsgIdr());
			
			
			
			
			if (!(null == biResp.getOrgnlTxRef().getCdtr().getPty()))
				if (!(null == biResp.getOrgnlTxRef().getCdtr().getPty().getNm()))
					chnResponse.setCreditorName(biResp.getOrgnlTxRef().getCdtr().getPty().getNm());
	

			channelResponseWr.setChnlCreditTransferResponse(chnResponse);

			exchange.getIn().setBody(channelResponseWr);

		}
		
		else {   // ternyata berupa message reject
			
			MessageRejectV01 rejectResp = busMesg.getDocument().getMessageReject();
			
			ChnlFailureResponsePojo reject = new ChnlFailureResponsePojo();

			reject.setReferenceId(rejectResp.getRltdRef().getRef());
			reject.setReason(rejectResp.getRsn().getRjctgPtyRsn());
			reject.setDescription(rejectResp.getRsn().getRsnDesc());
			reject.setLocation(rejectResp.getRsn().getErrLctn());
			reject.setAdditionalData(rejectResp.getRsn().getAddtlData());
	
			channelResponseWr.setFaultResponse(reject);

			exchange.getIn().setBody(channelResponseWr);
	
		}
	}
}
