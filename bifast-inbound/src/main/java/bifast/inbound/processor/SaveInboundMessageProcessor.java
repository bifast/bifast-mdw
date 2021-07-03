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
public class SaveInboundMessageProcessor implements Processor {

	@Autowired
	private InboundMessageRepository inboundMessageRepo;

	@Override
	public void process(Exchange exchange) throws Exception {
		 
		BusinessMessage rcvBi = exchange.getMessage().getHeader("rcv_bi",BusinessMessage.class);

		InboundMessage inboundMsg = new InboundMessage();

		
		BusinessApplicationHeaderV01 hdr = rcvBi.getAppHdr();
		
		inboundMsg.setFrFinId(hdr.getFr().getFIId().getFinInstnId().getOthr().getId());
		inboundMsg.setBizMsgIdr(hdr.getBizMsgIdr());
		inboundMsg.setMsgDefIdr(hdr.getMsgDefIdr());
		inboundMsg.setBizSvc(hdr.getBizSvc());
		inboundMsg.setCpyDplct(hdr.getCpyDplct());
		
		inboundMsg.setReceiveDt(LocalDateTime.now());

		if (!(null == exchange.getMessage().getHeader("resp_bi"))) {
			BusinessMessage respBi = new BusinessMessage();
			respBi = exchange.getMessage().getHeader("resp_bi",BusinessMessage.class);
			inboundMsg.setRespStatus(respBi.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
			inboundMsg.setRespBizMsgIdr(respBi.getAppHdr().getBizMsgIdr());
		}
		
		inboundMessageRepo.save(inboundMsg);
		

	}
}
