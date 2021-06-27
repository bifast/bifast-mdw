package bifast.inbound.processor;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import bifast.inbound.config.Config;
import bifast.library.model.CreditTransfer;
import bifast.library.model.SettlementProc;
import bifast.library.repository.CreditTransferRepository;
import bifast.library.repository.InboundMessageRepository;


@Component
public class SettlementProcessor implements Processor {

	@Autowired
	private Config config;
	@Autowired InboundMessageRepository inboundMsgRepo;
	@Autowired
	private CreditTransferRepository creditTransferRepo;

	@Override
	public void process(Exchange exchange) throws Exception {
		SettlementProc settlProc = exchange.getMessage().getHeader("rcv_qryresult", SettlementProc.class);
	
		if ( settlProc.getOrignBank().equals(config.getBankcode())) {  // outbound msg from us
			System.out.println ("Settlement outbound from us");
			Optional<CreditTransfer> optCt = creditTransferRepo.findByCrdtTrnRequestBisMsgId(settlProc.getOrgnlCrdtTrnReqBizMsgId());
			if (optCt.isPresent()) {
				CreditTransfer ct = optCt.get();
				ct.setSettlementBizMsgId(settlProc.getSettlConfBizMsgId());
				creditTransferRepo.save(ct);
				
				exchange.getMessage().setHeader("resp_reversal", "N");

			}
			else {
				// TODO settlement tapi ga nemu catatan Credit Transfer nya
				exchange.getMessage().setHeader("resp_reversal", "Y");
			}
			
		} else if (settlProc.getRecptBank().equals(config.getBankcode())) {   // inbound for us
			
			System.out.println("Stellment inbound for us");
			Optional<CreditTransfer> optCt = creditTransferRepo.findByCrdtTrnRequestBisMsgId(settlProc.getOrgnlCrdtTrnReqBizMsgId());

			// check status dari inbound CreditTrnRequest ini
			if (optCt.isPresent()) {
				CreditTransfer ct = optCt.get();
				if (ct.getCrdtTrnResponseStatus().equals("ACTC")) {
					// CreditTrnRequest sebelum sudah accepted 
					ct.setSettlementBizMsgId(settlProc.getSettlConfBizMsgId());
					creditTransferRepo.save(ct);
					
					exchange.getMessage().setHeader("resp_reversal", "N");
				}
				
				else {	// CreditTrnRequest sebelumnya udah RJCT, kok masih ada Settlement ?
					System.out.println("CreditTrnRequest sebelumnya udah RJCT, kok masih ada Settlement? ");
					// untuk diteruskan proses Reverse Payment
					exchange.getMessage().setHeader("resp_reversal", "Y");
				}

			} 
			else {
				// jika tidak pernah terima crditTransfer request ini ?
				System.out.println("CT Request atas settlement ini tidak pernah diterima.");
				exchange.getMessage().setHeader("resp_reversal", "Y");
				// TODO CT Request atas settlement tidak ditemukan
				// cek ke core bank apakah bisa diproses ?
				// jika tidak mesti proses Reverse Payment
				
			}
				
			
		} else {
			System.out.println("Setllement bukan untuk dan dari bank ini");

		}
		

	}

}
