package bifast.outbound.credittransfer.processor;

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
import bifast.outbound.pojo.chnlrequest.ChnlCreditTransferRequestPojo;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.pojo.chnlresponse.ChnlCreditTransferResponsePojo;

@Component
public class CreditTransferResponseProcessor implements Processor {

	DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

	@Override
	public void process(Exchange exchange) throws Exception {
		
		ChannelResponseWrapper chnlResponseWr = new ChannelResponseWrapper();
		chnlResponseWr.setResponseCode("U000");
		chnlResponseWr.setResponseMessage("Success/ Transaction Accepted");
		chnlResponseWr.setDate(LocalDateTime.now().format(dateformatter));
		chnlResponseWr.setTime(LocalDateTime.now().format(timeformatter));
		chnlResponseWr.setContent(new ArrayList<>());
		
		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		ChnlCreditTransferRequestPojo chnRequest = rmw.getChnlCreditTransferRequest();

		ChnlCreditTransferResponsePojo chnResponse = new ChnlCreditTransferResponsePojo();
		chnResponse.setOrignReffId(chnRequest.getChannelRefId());
		chnResponse.setAccountNumber(chnRequest.getCrdtAccountNo());

		Object objResponse = exchange.getMessage().getBody(Object.class);
		if (objResponse.getClass().getSimpleName().equals("ChnlFailureResponsePojo")) {
			
			ChnlFailureResponsePojo fault = (ChnlFailureResponsePojo)objResponse;
			chnlResponseWr.setResponseCode(fault.getErrorCode());
			if (fault.getErrorCode().equals("ERROR-KM"))
				chnlResponseWr.setResponseMessage("KOMI Internal Error");
			else if (fault.getErrorCode().equals("ERROR-CIHUB"))
				chnlResponseWr.setResponseMessage("CIHUB Internal Error");
			else
				chnlResponseWr.setResponseMessage("KOMI Error");
			
			chnlResponseWr.getContent().add(chnResponse);
			exchange.getIn().setBody(chnlResponseWr);

		}

		else {
			
			BusinessMessage response = (BusinessMessage)objResponse;
			
			if (null != response.getDocument().getFiToFIPmtStsRpt()) {
				
				PaymentTransaction110 biResp = response.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);
	
				// from CI-HUB response
				chnResponse.setBizMsgId(response.getAppHdr().getBizMsgIdr());
							
				chnlResponseWr.setResponseCode(biResp.getStsRsnInf().get(0).getRsn().getPrtry());
			
				if (!(null == biResp.getOrgnlTxRef().getCdtr().getPty()))
					if (!(null == biResp.getOrgnlTxRef().getCdtr().getPty().getNm()))
						chnResponse.setCreditorName(biResp.getOrgnlTxRef().getCdtr().getPty().getNm());
				
		
				chnlResponseWr.setResponseMessage("SUCCESS");

				chnlResponseWr.getContent().add(chnResponse);
				exchange.getIn().setBody(chnlResponseWr);
				
			}
			
		}
	}
}
