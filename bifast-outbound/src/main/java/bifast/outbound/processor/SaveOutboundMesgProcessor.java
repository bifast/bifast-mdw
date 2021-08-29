package bifast.outbound.processor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.model.OutboundMessage;
import bifast.library.repository.OutboundMessageRepository;

@Component
public class SaveOutboundMesgProcessor implements Processor {

	@Autowired
	private OutboundMessageRepository outboundRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage objRequest = exchange.getMessage().getHeader("req_objbi", BusinessMessage.class);

		BusinessMessage objResponse = exchange.getMessage().getHeader("resp_objbi", BusinessMessage.class);
		
		OutboundMessage outboundMessage = new OutboundMessage();
		
		outboundMessage.setBizMsgIdr(objRequest.getAppHdr().getBizMsgIdr());
		outboundMessage.setMsgDefIdr(objRequest.getAppHdr().getMsgDefIdr());
		outboundMessage.setSendDt(LocalDateTime.now());
		outboundMessage.setToFinId(objRequest.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());
		outboundMessage.setTransactionType(objRequest.getAppHdr().getBizMsgIdr().substring(16, 19));
		
//		BigDecimal amount = objRequest.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue();
//		outboundMessage.setAmount(amount);

		if (null == objResponse) {  // tidak terima response dari BI berarti timeout
			outboundMessage.setRespStatus("TIMEOUT");
			outboundMessage.setFailureMessage("Timeout terima message dari CI-HUB");
		}
		
		else if (!(null == objResponse.getDocument().getFiToFIPmtStsRpt())) {
		
			outboundMessage.setRespBizMsgId(objResponse.getAppHdr().getBizMsgIdr());
			outboundMessage.setRespStatus(objResponse.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
		
		}			

		else if (!(null == objResponse.getDocument().getPrxyRegnRspn())) {    // Response dari Proxy Registration
			outboundMessage.setRespBizMsgId(objResponse.getAppHdr().getBizMsgIdr());
			outboundMessage.setRespStatus(objResponse.getDocument().getPrxyRegnRspn().getRegnRspn().getPrxRspnSts().name());
		
		}
		
		else if (!(null == objResponse.getDocument().getPrxyLookUpRspn())) {    // Response dari Proxy Resolution
			outboundMessage.setRespBizMsgId(objResponse.getAppHdr().getBizMsgIdr());
			outboundMessage.setRespStatus(objResponse.getDocument().getPrxyLookUpRspn().getLkUpRspn().getRegnRspn().getPrxRspnSts().name());
		}
		
		else {  // msg Reject is not nul
					
			String rjctMesg = objResponse.getDocument().getMessageReject().getRsn().getRsnDesc();
			if (rjctMesg.length() > 400)
				rjctMesg = rjctMesg.substring(0, 400);
			
			outboundMessage.setRespStatus("FAILURE");
			outboundMessage.setFailureMessage(rjctMesg);

		}

		outboundRepo.save(outboundMessage);
		
	}

}
