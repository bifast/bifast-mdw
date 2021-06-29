package bifast.outbound.processor;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.model.AccountEnquiry;
import bifast.library.model.OutboundMessage;
import bifast.library.repository.AccountEnquiryRepository;
import bifast.library.repository.OutboundMessageRepository;
import bifast.outbound.config.Config;
import bifast.outbound.pojo.ChannelAccountEnquiryReq;

@Component
public class SaveAccountEnquiryProcessor implements Processor {

	@Autowired
	private OutboundMessageRepository outboundRepo;
	@Autowired
	private AccountEnquiryRepository acctEnquiryRepo;
	@Autowired
	private Config config;

	@Override
	public void process(Exchange exchange) throws Exception {

		String jsonRequest = exchange.getMessage().getHeader("req_jsonbiacctenqr", String.class);
		BusinessMessage objRequest = exchange.getMessage().getHeader("req_objbiacctenqr", BusinessMessage.class);

		String jsonResponse = exchange.getMessage().getHeader("resp_jsonbiacctenqr", String.class);
		BusinessMessage objResponse = exchange.getMessage().getHeader("resp_objbiacctenqr", BusinessMessage.class);
		
		OutboundMessage outboundMessage = new OutboundMessage();
		
		outboundMessage.setBizMsgIdr(objRequest.getAppHdr().getBizMsgIdr());
		outboundMessage.setFullMessage(jsonRequest);
		outboundMessage.setMsgDefIdr(objRequest.getAppHdr().getMsgDefIdr());
		outboundMessage.setSendDt(LocalDateTime.now());
		outboundMessage.setToFinId(objRequest.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());
		outboundMessage.setResponseMessage(jsonResponse);
		outboundMessage.setRespBizMsgId(objResponse.getAppHdr().getBizMsgIdr());
		
		outboundMessage.setHttpResponse("200");
		
		outboundRepo.save(outboundMessage);

		// save Account_Enquiry Table

		ChannelAccountEnquiryReq chnReq = exchange.getMessage().getHeader("req_channelReq", ChannelAccountEnquiryReq.class); 
		
		AccountEnquiry accountEnquiry = new AccountEnquiry();
		accountEnquiry.setAccountNo(chnReq.getCreditorAccountNumber());
		accountEnquiry.setCreDt(LocalDateTime.now());
		accountEnquiry.setDirection("OUTBOUND");
		accountEnquiry.setIntrRefId(chnReq.getIntrnRefId());
		accountEnquiry.setOriginatingBank(config.getBankcode());
		accountEnquiry.setRecipientBank(chnReq.getReceivingParticipant());
		accountEnquiry.setReqBizMsgId(objRequest.getAppHdr().getBizMsgIdr());
		accountEnquiry.setRespStatus(objResponse.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
		
		acctEnquiryRepo.save(accountEnquiry);
	}

}
