package bifast.outbound.processor;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.model.CreditTransfer;
import bifast.library.model.OutboundMessage;
import bifast.library.repository.CreditTransferRepository;
import bifast.library.repository.OutboundMessageRepository;
import bifast.outbound.pojo.ChannelCreditTransferRequest;

@Component
public class SaveCstmrCreditTransferProcessor implements Processor {

	@Autowired
	private OutboundMessageRepository outboundRepo;
	@Autowired
	private CreditTransferRepository creditTransferRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		String jsonRequest = exchange.getMessage().getHeader("req_jsonbicrdttrn", String.class);
		String jsonResponse = exchange.getMessage().getHeader("resp_jsonbicrdttrn", String.class);
		BusinessMessage objRequest = exchange.getMessage().getHeader("req_objcrdtTrnReq", BusinessMessage.class);
		BusinessMessage objResponse = exchange.getMessage().getHeader("resp_objbicrdttrn", BusinessMessage.class);
				
		OutboundMessage outboundMessage = new OutboundMessage();
		
		outboundMessage.setBizMsgIdr(objRequest.getAppHdr().getBizMsgIdr());
		outboundMessage.setFullMessage(jsonRequest);
		outboundMessage.setMsgDefIdr(objRequest.getAppHdr().getMsgDefIdr());
//		outboundMessage.setSafCounter(null);
		outboundMessage.setSendDt(LocalDateTime.now());
		outboundMessage.setToFinId(objRequest.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());
		outboundMessage.setResponseMessage(jsonResponse);
		
		outboundMessage.setHttpResponse("200");
		
		outboundRepo.save(outboundMessage);
		
		// save Credit Transfer Table

		ChannelCreditTransferRequest chnReq = exchange.getMessage().getHeader("req_channelReq", ChannelCreditTransferRequest.class); 
		
		CreditTransfer creditTransfer = new CreditTransfer();
		creditTransfer.setAmount(chnReq.getAmount());
		creditTransfer.setCrdtTrnRequestBisMsgId(objRequest.getAppHdr().getBizMsgIdr());
		creditTransfer.setCrdtTrnResponseBisMsgId(objResponse.getAppHdr().getBizMsgIdr());
		creditTransfer.setCrdtTrnResponseStatus(objResponse.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
		creditTransfer.setCreditorAccount(chnReq.getCrdtAccountNo());
		creditTransfer.setCreDt(LocalDateTime.now());
		creditTransfer.setDebtorAccount(chnReq.getDbtrAccountNo());
		
		creditTransfer.setDirection("OUTBOUND");
		creditTransfer.setIntrRefId(chnReq.getIntrnRefId());
		creditTransfer.setMsgType(objRequest.getAppHdr().getMsgDefIdr());
		creditTransfer.setOriginatingBank(objRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId());
		creditTransfer.setRecipientBank(chnReq.getRecptBank());
		
		creditTransferRepo.save(creditTransfer);

	}

	
}
