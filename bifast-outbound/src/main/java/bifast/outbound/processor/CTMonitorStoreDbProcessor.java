package fransmz.bifast.credittransfer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fransmz.bifast.config.Config;
import fransmz.bifast.persist.CreditTransfer;
import fransmz.bifast.persist.CreditTransferRepository;
import fransmz.bifast.pojo.BusinessMessage;
import iso20022.head001.BusinessApplicationHeaderV02;
import iso20022.pacs002.FIToFIPaymentStatusReportV11;

@Component
public class CTMonitorStoreDbProcessor implements Processor {

	@Autowired
	private CreditTransferRepository creditTransferRepo;
	@Autowired
	private Config config;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		String step = exchange.getMessage().getHeader("req_step", String.class);
		CreditTransferRequestInput orgnlReq = exchange.getMessage().getHeader("req_orgnlReq", CreditTransferRequestInput.class);

		if (step.equals("acctEnqr")) {
			BusinessMessage acctEnqReq = exchange.getMessage().getHeader("req_acctEnqReq", BusinessMessage.class);
			BusinessMessage acctEnqResp = exchange.getMessage().getBody(BusinessMessage.class);

			BusinessApplicationHeaderV02 acctEnqReqHdr = acctEnqReq.getAppHdr();
			BusinessApplicationHeaderV02 acctEnqRespHdr = acctEnqResp.getAppHdr();
			FIToFIPaymentStatusReportV11 acctEnqRespMsg = acctEnqResp.getDocument().getFiToFIPmtStsRpt();

			// FIToFICustomerCreditTransferV09 acctEnqMsg = bizMsg.getDocument().getFiToFICstmrCdtTrf();
			CreditTransfer ct = new CreditTransfer();

			ct.setAcctEnqRequestBisMsgId(acctEnqReqHdr.getBizMsgIdr());
			ct.setAcctEnqResponseBisMsgId(acctEnqRespHdr.getBizMsgIdr());
			ct.setAcctEnqResponseStatus(acctEnqRespMsg.getTxInfAndSts().get(0).getTxSts());
			ct.setAmount(orgnlReq.getAmount());
			ct.setCreditorAccount(orgnlReq.getCreditorAccountNumber());
			ct.setDebtorAccount(orgnlReq.getDebtorAccountNumber());
			ct.setMsgType("CstmrCrdtTrn");
			ct.setOriginatingBank(config.getBankcode());
			ct.setOrignlRefId(orgnlReq.getMessageId());
			ct.setRecipientBank(orgnlReq.getReceivingParticipant());
			ct.setSendDt(LocalDateTime.now());

			creditTransferRepo.save(ct);
			exchange.getMessage().setHeader("resp_dbid", ct.getId());
		}

		else {

			BusinessMessage crdtTrnReq = exchange.getMessage().getHeader("req_crdtTrnReq", BusinessMessage.class);
			BusinessMessage crdtTrnResp = exchange.getMessage().getBody(BusinessMessage.class);

			BusinessApplicationHeaderV02 crdtTrnReqHdr = crdtTrnReq.getAppHdr();
			BusinessApplicationHeaderV02 crdtTrnRespHdr = crdtTrnResp.getAppHdr();
			FIToFIPaymentStatusReportV11 crdtTrnRespMsg = crdtTrnResp.getDocument().getFiToFIPmtStsRpt();

			Long dbid = exchange.getMessage().getHeader("resp_dbid", Long.class);
			Optional<CreditTransfer> optCt = creditTransferRepo.findById(dbid);
			if (optCt.isPresent()) {
				CreditTransfer ct = optCt.get();
				ct.setCrdtTrnRequestBisMsgId(crdtTrnReqHdr.getBizMsgIdr());
				ct.setCrdtTrnResponseBisMsgId(crdtTrnRespHdr.getBizMsgIdr());
				ct.setCrdtTrnResponseStatus(crdtTrnRespMsg.getTxInfAndSts().get(0).getTxSts());

				creditTransferRepo.save(ct);
			}

		}

		
	}

}
