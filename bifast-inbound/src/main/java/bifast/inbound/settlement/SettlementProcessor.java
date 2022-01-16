package bifast.inbound.settlement;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.config.Config;
import bifast.inbound.corebank.pojo.CbSettlementRequestPojo;
import bifast.inbound.model.CreditTransfer;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs002Pojo;
import bifast.inbound.repository.CreditTransferRepository;

@Component
public class SettlementProcessor implements Processor {
	@Autowired private Config config;
	@Autowired private CreditTransferRepository ctRepo;

	private static Logger logger = LoggerFactory.getLogger(SettlementProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		
		ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
		FlatPacs002Pojo flatSttl = (FlatPacs002Pojo) processData.getBiRequestFlat();

		CbSettlementRequestPojo sttlRequest = new CbSettlementRequestPojo();
		sttlRequest.setKomiTrnsId(processData.getKomiTrnsId());
		
		sttlRequest.setBizMsgId(flatSttl.getBizMsgIdr());
		sttlRequest.setMsgId(flatSttl.getMsgId());	
		
			
		List<CreditTransfer> lCrdtTrns = ctRepo.findAllByCrdtTrnRequestBizMsgIdr(flatSttl.getOrgnlEndToEndId());
		CreditTransfer ct = null;
		for (CreditTransfer runningCT : lCrdtTrns) {
			if ((runningCT.getResponseCode().equals("ACTC")) ||
				(runningCT.getResponseCode().equals("ACSC"))) {
				ct = runningCT;
				break;
			}
		}

		if (null != ct) {
			ct.setSettlementConfBizMsgIdr(flatSttl.getBizMsgIdr());
			ct.setCbStatus("READY");
			ctRepo.save(ct);
			sttlRequest.setOrgnlKomiTrnsId(ct.getKomiTrnsId());
		}

		logger.debug("orgnl Bank " + ct.getOriginatingBank());

		if (config.getBankcode().equals(ct.getOriginatingBank()))
			sttlRequest.setCounterParty(ct.getRecipientBank());
		else
			sttlRequest.setCounterParty(ct.getOriginatingBank());

		logger.debug("Counterparty " + sttlRequest.getCounterParty());
		
		exchange.getMessage().setBody(sttlRequest);

//		Settlement sttl = new Settlement();
////		sttl.setCorebankResponseId(null);
//		sttl.setCrdtAccountNo(flatSttl.getCdtrAcctId());
//		sttl.setDbtrAccountNo(flatSttl.getDbtrAcctId());
//		sttl.setFullMessage(null);
//
//		sttlRepo.save(sttl);
		
		
				
	}

}
