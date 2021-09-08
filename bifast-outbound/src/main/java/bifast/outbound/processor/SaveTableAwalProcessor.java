package bifast.outbound.processor;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.model.DomainCode;
import bifast.library.model.OutboundMessage;
import bifast.library.repository.BankCodeRepository;
import bifast.library.repository.DomainCodeRepository;
import bifast.library.repository.OutboundMessageRepository;

@Component
public class SaveTableAwalProcessor implements Processor {

	@Autowired
	private OutboundMessageRepository outboundRepo;
	@Autowired
	private DomainCodeRepository domainCodeRepo;
	@Autowired
	private BankCodeRepository bankCodeRepo;


	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage outRequest = exchange.getMessage().getHeader("req_objbi", BusinessMessage.class);

		Object channelRequest = exchange.getMessage().getHeader("hdr_channelRequest", Object.class);
		
		String intrnRefId = exchange.getMessage().getHeader("rcv_intrnRefId", String.class);

		OutboundMessage outboundMessage = new OutboundMessage();

		outboundMessage.setBizMsgIdr(outRequest.getAppHdr().getBizMsgIdr());
		
		String bic = outRequest.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId();
		outboundMessage.setRecipientBank(bankCodeRepo.findByBicCode(bic).get().getBankCode());
		
		outboundMessage.setChannelRequestDT(LocalDateTime.now());
		
		String fullRequestMsg = exchange.getMessage().getHeader("hdr_fullrequestmessage",String.class);
		outboundMessage.setFullRequestMessage(fullRequestMsg);

		outboundMessage.setInternalReffId(intrnRefId);

		//channel kembalikan keposisi descriptive
		Method getChannelMethod = channelRequest.getClass().getMethod("getChannel", null);
		String chnCode = (String) getChannelMethod.invoke(channelRequest, null);
		outboundMessage.setChannel(domainCodeRepo.findByGrpAndKey("CHANNEL.TYPE", chnCode).get().getValue());
		
		String requestClass = channelRequest.getClass().getName();
		
		String msgName = domainCodeRepo.findByGrpAndKey("REQUEST.CLASS", requestClass).orElse(new DomainCode()).getValue();

		outboundMessage.setMessageName(msgName);

		outboundMessage = outboundRepo.save(outboundMessage);
		
		exchange.getMessage().setHeader("hdr_idtable", outboundMessage.getId());
		
	}

}
