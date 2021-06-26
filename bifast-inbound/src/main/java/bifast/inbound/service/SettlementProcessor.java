package bifast.inbound.service;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.config.Config;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV02;
import bifast.library.model.CreditTransfer;
import bifast.library.repository.CreditTransferRepository;


@Component
public class SettlementProcessor implements Processor {

	@Autowired
	private Config config;
	@Autowired
	private CreditTransferRepository creditTransferRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		String sttlmOf = "";
		
		BusinessMessage rcvMessage = exchange.getIn().getBody(BusinessMessage.class);
		BusinessApplicationHeaderV02 sttlHeader = rcvMessage.getAppHdr();
		String sttlBizMsgId = sttlHeader.getBizMsgIdr();
		String orglBizMsgId = rcvMessage.getDocument().getFiToFIPmtStsRpt().getOrgnlGrpInfAndSts().get(0).getOrgnlMsgId();
		

		if (sttlHeader.getFr().getFIId().getFinInstnId().getOthr().getId().equals(config.getBankcode())) {  // outbound msg from us
			System.out.println ("Settlement outbound from us");
			Optional<CreditTransfer> optCt = creditTransferRepo.findByCrdtTrnRequestBisMsgId(orglBizMsgId);
			if (optCt.isPresent()) {
				CreditTransfer ct = optCt.get();
				ct.setSettlementBizMsgId(sttlBizMsgId);
				creditTransferRepo.save(ct);
			}
			
		} else if (sttlHeader.getTo().getFIId().getFinInstnId().getOthr().getId().equals(config.getBankcode())) {
			
			System.out.println("Stellment inbound for us");
			Optional<CreditTransfer> optCt = creditTransferRepo.findByCrdtTrnRequestBisMsgId(orglBizMsgId);

			// check status dari inbound CreditTrnRequest ini
			if (optCt.isPresent()) {
				CreditTransfer ct = optCt.get();
				if (ct.getCrdtTrnResponseStatus().equals("ACTC")) {
					// CreditTrnRequest sebelum sudah accepted 
					ct.setSettlementBizMsgId(sttlBizMsgId);
					creditTransferRepo.save(ct);
				}
				else {	// CreditTrnRequest sebelumnya udah RJCT, kok masih ada Settlement ?
					System.out.println("CreditTrnRequest sebelumnya udah RJCT, kok masih ada Settlement? ");
					// untuk diteruskan proses Reverse Payment
					
				}

			} 
			else {
				// jika tidak pernah terima crditTransfer request ini ?
				System.out.println("CT Request atas settlement ini tidak pernah diterima.");
				
				// cek ke core bank apakah bisa diproses ?
				// jika tidak mesti proses Reverse Payment
				
			}
				
			
		} else {
			System.out.println("Setllement bukan untuk dan dari bank ini");

		}
		
		exchange.getMessage().setHeader("rcv_sttlmOf", sttlmOf);
		
	}

}
