package bifast.inbound.settlement;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CbSettlementConfirmationPojo;
import bifast.inbound.model.CorebankTransaction;
import bifast.inbound.model.CreditTransfer;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs002Pojo;
import bifast.inbound.repository.CorebankTransactionRepository;
import bifast.inbound.repository.CreditTransferRepository;
import bifast.inbound.service.FlattenIsoMessageService;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class SettlementProcessor implements Processor {
	@Autowired private CreditTransferRepository ctRepo;
	@Autowired private CorebankTransactionRepository cbTrnsRepository;
	@Autowired private FlattenIsoMessageService flatMesgService;

	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
		
		BusinessMessage bm = exchange.getMessage().getBody(BusinessMessage.class);
		FlatPacs002Pojo flat = flatMesgService.flatteningPacs002(bm);
				
		String orgnlMsgType = flat.getOrgnlEndToEndId().substring(16,19);
		CreditTransfer orgnlCT = null;
		List<CreditTransfer> lCrdtTrns = ctRepo.findAllByCrdtTrnRequestBizMsgIdr(flat.getOrgnlEndToEndId());	
		for (CreditTransfer ct : lCrdtTrns) {
			if (ct.getResponseCode().equals("ACTC"))
				orgnlCT = ct;
		}
		
		if (null != orgnlCT) {
			
			List<CorebankTransaction> lCbTransaction = cbTrnsRepository.findByTransactionTypeAndKomiTrnsId("DebitAccount", 
																					orgnlCT.getKomiTrnsId());
			if (lCbTransaction.size()>0) {
				CorebankTransaction cbTransaction = lCbTransaction.get(0);
				String orgnlTextCT = cbTransaction.getFullTextRequest();
				
				CbSettlementConfirmationPojo settlement = new CbSettlementConfirmationPojo();
				
				settlement.setTransactionId("000000");
//				settlement.setNoRef(ctBizMsgId);
//				settlement.setDateTime(ctBizMsgId);
				settlement.setMerchantId("0000");
				settlement.setTerminalId("000000");
				settlement.setOriginalNoRef(cbTransaction.getOrgnlChnlNoref());
				settlement.setReason("U000");
				settlement.setStatus("ACTC");
				//			settlement.setAdditionalInfo(ctBizMsgId);
			}
		}
		
		//TODO kirim settlement ke COREBANK
		
	}

}
