package bifast.inbound.service;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV02;
import bifast.library.model.SettlementProc;
import bifast.library.repository.SettlementProcRepository;

@Component
public class SaveSettlementTableProcessor implements Processor {

	@Autowired
	private SettlementProcRepository settlementRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

//		String sttlmOf = "";
		
		BusinessMessage rcvMessage = exchange.getIn().getBody(BusinessMessage.class);
		BusinessApplicationHeaderV02 sttlHeader = rcvMessage.getAppHdr();
		
		String sttlBizMsgId = sttlHeader.getBizMsgIdr();
		String orglBizMsgId = rcvMessage.getDocument().getFiToFIPmtStsRpt().getOrgnlGrpInfAndSts().get(0).getOrgnlMsgId();
		String sttlMsgName = sttlHeader.getMsgDefIdr();
		
//		if (sttlHeader.getFr().getFIId().getFinInstnId().getOthr().getId().equals(config.getBankcode())) {  // outbound msg from us
//			System.out.println ("Settlement outbound from us");
//			Optional<CreditTransfer> optCt = creditTransferRepo.findByCrdtTrnRequestBisMsgId(orglBizMsgId);
//			if (optCt.isPresent()) {
//				CreditTransfer ct = optCt.get();
//				ct.setSettlementBizMsgId(sttlBizMsgId);
//				creditTransferRepo.save(ct);
//			} 
//			else {
//				// TODO settlement tapi tidak ada catatan Credit Transfer sebelumnya ??
//			}
//		}	
//		else {   // inbound credit trn settlement msg for us
//			// TODO cari credit transfer pre advice
//			
//		}
		
		SettlementProc sttl = new SettlementProc();
		sttl.setOrgnlCrdtTrnReqBizMsgId(orglBizMsgId);
		sttl.setSettlConfBizMsgId(sttlBizMsgId);
		sttl.setSettlConfMesgName(sttlMsgName);
		settlementRepo.save(sttl);
	
		
	}

}
