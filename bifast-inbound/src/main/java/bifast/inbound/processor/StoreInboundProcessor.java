package bifast.inbound.processor;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.model.InboundMessage;
import bifast.library.repository.InboundMessageRepository;

@Component
public class StoreInboundProcessor implements Processor {

	@Autowired
	private InboundMessageRepository inboundMessageRepo;

	@Override
	public void process(Exchange exchange) throws Exception {
		 
		BusinessMessage busMesg = exchange.getMessage().getHeader("rcv_obj",BusinessMessage.class);
		BusinessApplicationHeaderV01 hdr = busMesg.getAppHdr();
		
		InboundMessage inboundMsg = new InboundMessage();
		
		inboundMsg.setFrFinId(hdr.getFr().getFIId().getFinInstnId().getOthr().getId());
		inboundMsg.setBizMsgIdr(hdr.getBizMsgIdr());
		inboundMsg.setMsgDefIdr(hdr.getMsgDefIdr());
		inboundMsg.setBizSvc(hdr.getBizSvc());
		inboundMsg.setCpyDplct(hdr.getCpyDplct());
		
		inboundMsg.setReceiveDt(LocalDateTime.now());
		inboundMsg.setFullMessage(exchange.getMessage().getHeader("rcv_json",String.class));
		inboundMsg.setResponseMessage(exchange.getMessage().getHeader("resp_json", String.class));
		
		inboundMessageRepo.save(inboundMsg);

	}
}
