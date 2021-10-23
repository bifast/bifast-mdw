package bifast.outbound.paymentstatus.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.outbound.pojo.ChnlFailureResponsePojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.PaymentStatusRequestSAFPojo;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.pojo.chnlresponse.ChnlCreditTransferResponsePojo;

@Component
public class PaymentStatusResponseProcessor implements Processor {
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");
   
	@Override
	public void process(Exchange exchange) throws Exception {
		
		ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
		channelResponseWr.setResponseCode("U000");
		channelResponseWr.setResponseMessage("Success/ Transaction Accepted");
		channelResponseWr.setDate(LocalDateTime.now().format(dateformatter));
		channelResponseWr.setTime(LocalDateTime.now().format(timeformatter));
		channelResponseWr.setResponses(new ArrayList<>());

		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list",RequestMessageWrapper.class);
		PaymentStatusRequestSAFPojo chnReq = rmw.getPaymentStatusRequestSAF();
		
		ChnlCreditTransferResponsePojo chnResp = new ChnlCreditTransferResponsePojo();
		
		chnResp.setOrignReffId(chnReq.getChannelRefId());
//		chnResp.setAccountNumber(chnReq.getCrdtAccountNo());

		Object objBody = exchange.getMessage().getBody(Object.class);

		if (objBody.getClass().getSimpleName().equals("ChnlFailureResponsePojo")) {
			ChnlFailureResponsePojo fault = (ChnlFailureResponsePojo)objBody;
			channelResponseWr.setResponseCode(fault.getResponseCode());
			if (fault.getFaultCategory().equals("ERROR-KM"))
				channelResponseWr.setResponseMessage("KOMI Internal Error");
			else if (fault.getFaultCategory().equals("ERROR-CIHUB"))
				channelResponseWr.setResponseMessage("CIHUB Internal Error");
			else
				channelResponseWr.setResponseMessage("KOMI Error");
			
		}

		else if (objBody.getClass().getSimpleName().equals("BusinessMessage")) {
			
			BusinessMessage busMesg = exchange.getIn().getBody(BusinessMessage.class);

			PaymentTransaction110 biResp = busMesg.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);
			ChnlCreditTransferResponsePojo chnResponse = new ChnlCreditTransferResponsePojo();

			// from CI-HUB response
			chnResponse.setOrignReffId(chnReq.getChannelRefId());
			
			if (!(null == biResp.getOrgnlTxRef().getCdtr().getPty()))
				if (!(null == biResp.getOrgnlTxRef().getCdtr().getPty().getNm()))
					chnResponse.setCreditorName(biResp.getOrgnlTxRef().getCdtr().getPty().getNm());
				
			chnResponse.setAccountNumber(chnReq.getCreditorAccountNumber());
			channelResponseWr.getResponses().add(chnResponse);

			exchange.getIn().setBody(channelResponseWr);
		}
				
	}
}
