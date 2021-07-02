package bifast.inbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.model.SettlementProc;
import bifast.library.repository.SettlementProcRepository;

@Component
public class SaveSettlementTableProcessor implements Processor {

	@Autowired
	private SettlementProcRepository settlementRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage rcvMessage = exchange.getMessage().getHeader("rcv_bi", BusinessMessage.class);
		BusinessApplicationHeaderV01 sttlHeader = rcvMessage.getAppHdr();
		
		String sttlBizMsgId = sttlHeader.getBizMsgIdr();
		String orglBizMsgId = rcvMessage.getDocument().getFiToFIPmtStsRpt().getOrgnlGrpInfAndSts().get(0).getOrgnlMsgId();
		String sttlMsgName = sttlHeader.getMsgDefIdr();
	
		SettlementProc sttl = new SettlementProc();
		sttl.setOrgnlCrdtTrnReqBizMsgId(orglBizMsgId);
		sttl.setSettlConfBizMsgId(sttlBizMsgId);
		sttl.setSettlConfMesgName(sttlMsgName);
		sttl.setOrignBank(sttlHeader.getFr().getFIId().getFinInstnId().getOthr().getId());
		sttl.setRecptBank(sttlHeader.getTo().getFIId().getFinInstnId().getOthr().getId());
		settlementRepo.save(sttl);
	
		
	}

}
