package bifast.inbound.settlement;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bifast.inbound.config.Config;
import bifast.inbound.corebank.isopojo.SettlementRequest;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs002Pojo;
import bifast.inbound.service.RefUtils;

@Component
public class SettlementRequestProcessor implements Processor {
	@Autowired private Config config;
//	@Autowired private CorebankTransactionRepository cbTrnsRepo;

	@Value("${komi.isoadapter.merchant}")
	String merchant;

	@Value("${komi.isoadapter.terminal}")
	String terminal;

	@Value("${komi.isoadapter.txid}")
	String txid;

//	private static Logger logger = LoggerFactory.getLogger(SettlementProcessor.class);


	@Override
	public void process(Exchange exchange) throws Exception {
		
		ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
		FlatPacs002Pojo flatSttl = (FlatPacs002Pojo) processData.getBiRequestFlat();

		SettlementRequest sttlRequest = new SettlementRequest();

		RefUtils.Ref ref = RefUtils.newRef();

		sttlRequest.setAdditionalInfo("");
		sttlRequest.setDateTime(ref.getDateTime());
		sttlRequest.setMerchantType(merchant);
		sttlRequest.setNoRef(ref.getNoRef());
		sttlRequest.setOriginalNoRef(null);
		sttlRequest.setReason("U000");
		sttlRequest.setStatus("ACSC");
		sttlRequest.setTerminalId(terminal);
		sttlRequest.setTransactionId(txid);
		
		sttlRequest.setBizMsgId(flatSttl.getBizMsgIdr());
		sttlRequest.setMsgId(flatSttl.getOrgnlEndToEndId());	
		
		if (flatSttl.getCdtrAgtFinInstnId().equals(config.getBankcode()))
			sttlRequest.setCounterParty(flatSttl.getDbtrAgtFinInstnId());
		else
			sttlRequest.setCounterParty(flatSttl.getCdtrAgtFinInstnId());			
				
		String ctNoref = exchange.getProperty("cb_noref", String.class);
		sttlRequest.setOriginalNoRef(ctNoref);
//		List<CreditTransfer> lCrdtTrns = ctRepo.findAllByCrdtTrnRequestBizMsgIdr(flatSttl.getOrgnlEndToEndId());
//		for (CreditTransfer runningCT : lCrdtTrns) {
//			if ((runningCT.getResponseCode().equals("ACTC")) ||
//				(runningCT.getResponseCode().equals("ACSC"))) {
////				runningCT.get
//				break;
//			}
//		}

		exchange.getMessage().setBody(sttlRequest);
	
	}

}
