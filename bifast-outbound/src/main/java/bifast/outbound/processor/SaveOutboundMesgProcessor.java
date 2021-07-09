package bifast.outbound.processor;

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
		outboundMessage.setRespBizMsgId(objResponse.getAppHdr().getBizMsgIdr());

		
		if (null == objResponse.getDocument().getMessageReject())
			if(objResponse.getDocument().getFiToFIPmtStsRpt() != null) {
				outboundMessage.setRespStatus(objResponse.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
			}else {
				outboundMessage.setRespStatus(objResponse.getDocument().getPrxyRegnRspn().getRegnRspn().getPrxRspnSts().toString());
			}

		else {
				
			String rjctMesg = objResponse.getDocument().getMessageReject().getRsn().getRsnDesc();
			if (rjctMesg.length() > 400)
				rjctMesg = rjctMesg.substring(0, 400);
			
			outboundMessage.setRespStatus("FAILURE");
			outboundMessage.setFailureMessage(rjctMesg);
		}
	
		outboundRepo.save(outboundMessage);
		
	}

}
