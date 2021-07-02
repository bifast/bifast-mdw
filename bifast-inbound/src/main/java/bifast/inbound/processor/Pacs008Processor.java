package bifast.inbound.processor;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs008.CreditTransferTransaction39;
import bifast.library.model.AccountEnquiry;
import bifast.library.model.CreditTransfer;
import bifast.library.repository.AccountEnquiryRepository;
import bifast.library.repository.CreditTransferRepository;

@Component
public class Pacs008Processor implements Processor{

	@Autowired
	private CreditTransferRepository creditTrnRepo;
	@Autowired
	private AccountEnquiryRepository accountEnquiryRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage busMesg = exchange.getIn().getBody(BusinessMessage.class);
		CreditTransferTransaction39 cdtTrfInf = busMesg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0);

		String trnType = busMesg.getAppHdr().getBizMsgIdr().substring(16, 19);

		//cek apakah account enq
		if (trnType.equals("510")) {
      
			AccountEnquiry accenq = new AccountEnquiry();
			accenq.setDirection("INBOUND");
			accenq.setOriginatingBank(busMesg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId());
			accenq.setRecipientBank(busMesg.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());
			accenq.setReqBizMsgId(busMesg.getAppHdr().getBizMsgIdr());
			accenq.setAccountNo(cdtTrfInf.getCdtrAcct().getId().getOthr().getId());
			accenq.setCreDt(LocalDateTime.now());

			// TODO Disini panggil corebank untuk  account enquiry
//			accenq.setRespBizMsgId(null);
//			accenq.setRespStatus(null);
			accountEnquiryRepo.save(accenq);
			
			exchange.getMessage().setHeader("rcv_msgtype", "ACCTENQR");
		}
		
		// atau credit transfer request
		else if (trnType.equals("010")) {
			
			// TODO Disini panggil corebank untuk check account
			
			CreditTransfer crdt = new CreditTransfer();
		
			crdt.setDirection("INBOUND");
			crdt.setMsgType("CSTMR_TRN");
			crdt.setAmount(cdtTrfInf.getIntrBkSttlmAmt().getValue());
			crdt.setCrdtTrnRequestBisMsgId(busMesg.getAppHdr().getBizMsgIdr());
			
//			crdt.setCrdtTrnResponseBisMsgId(null);
//			crdt.setCrdtTrnResponseStatus(null);

			crdt.setCreditorAccount(cdtTrfInf.getCdtrAcct().getId().getOthr().getId());
			crdt.setDebtorAccount(cdtTrfInf.getDbtrAcct().getId().getOthr().getId());
			crdt.setOriginatingBank(busMesg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId());
			crdt.setRecipientBank(busMesg.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());
			crdt.setCreDt(LocalDateTime.now());
		
			
			creditTrnRepo.save(crdt);

			exchange.getMessage().setHeader("rcv_msgtype", "CRDTTRN");

		}
		
		// atau credit transfer request
		else if (trnType.equals("011")) {
			// TODO Disini panggil corebank untuk Reversal CT
			exchange.getMessage().setHeader("rcv_msgtype", "REVCRDTTRN");

		}

	}

}
