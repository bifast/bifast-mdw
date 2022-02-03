package bifast.inbound.settlement;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bifast.inbound.config.Config;
import bifast.inbound.corebank.isopojo.SettlementRequest;
import bifast.inbound.model.CreditTransfer;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs002Pojo;
import bifast.inbound.repository.CreditTransferRepository;
import bifast.inbound.service.RefUtils;

@Component
public class SettlementDebitProcessor implements Processor {
	@Autowired private CreditTransferRepository ctRepo;
	
	@Value("${komi.isoadapter.merchant}")
	String merchant;

	@Value("${komi.isoadapter.terminal}")
	String terminal;

	@Value("${komi.isoadapter.txid}")
	String txid;

//	private static Logger logger = LoggerFactory.getLogger(SettlementProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		
		ProcessDataPojo processData = exchange.getProperty("prop_process_data", ProcessDataPojo.class);
		FlatPacs002Pojo flatSttl = (FlatPacs002Pojo) processData.getBiRequestFlat();

		CreditTransfer ct = exchange.getProperty("pr_orgnlCT", CreditTransfer.class);
		
		SettlementRequest sttlRequest = new SettlementRequest();
		
		RefUtils.Ref ref = RefUtils.newRef();

		sttlRequest.setAdditionalInfo(flatSttl.getRsnInfAddtlInf());
		sttlRequest.setDateTime(ref.getDateTime());
		sttlRequest.setMerchantType(merchant);
		sttlRequest.setNoRef(ref.getNoRef());
		
		sttlRequest.setReason("U000");
		sttlRequest.setStatus("ACSC");
		sttlRequest.setTerminalId(terminal);
		sttlRequest.setTransactionId(txid);

//		sttlRequest.setBizMsgId(());
		sttlRequest.setMsgId(flatSttl.getOrgnlEndToEndId());
		
		sttlRequest.setCounterParty(flatSttl.getCdtrAgtFinInstnId());

//		sttlRequest.setOriginalNoRef();
				
		exchange.getMessage().setBody(sttlRequest);

				
	}

}
