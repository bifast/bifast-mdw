package bifast.inbound.credittransfer;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.model.CreditTransfer;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.repository.CreditTransferRepository;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs008.FIToFICustomerCreditTransferV08;

@Component
public class SaveCreditTransferProcessor implements Processor {

	@Autowired
	private CreditTransferRepository creditTrnRepo;

	@Override
	public void process(Exchange exchange) throws Exception {
		 

		BusinessMessage rcvBi = exchange.getMessage().getHeader("hdr_frBIobj",BusinessMessage.class);
		BusinessApplicationHeaderV01 hdr = rcvBi.getAppHdr();

		ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
		
		CreditTransfer ct = new CreditTransfer();

		ct.setKomiTrnsId(processData.getKomiTrnsId());
		
		String fullReqMsg = exchange.getMessage().getHeader("hdr_frBI_jsonzip",String.class);
		String fullRespMsg = exchange.getMessage().getHeader("hdr_toBI_jsonzip",String.class);
		
		ct.setFullRequestMessage(fullReqMsg);
		ct.setFullResponseMsg(fullRespMsg);
		
		if (null != processData.getBiResponseMsg()) {
			BusinessMessage respBi = processData.getBiResponseMsg();

			ct.setResponseCode(respBi.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
			ct.setCrdtTrnResponseBizMsgIdr(respBi.getAppHdr().getBizMsgIdr());
			ct.setReasonCode(respBi.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getStsRsnInf().get(0).getRsn().getPrtry());
			ct.setCallStatus("SUCCESS");
			
		}
		
		ct.setCihubRequestDT(processData.getReceivedDt());
		ct.setLastUpdateDt(LocalDateTime.now());
		long timeElapsed = Duration.between(processData.getStartTime(), Instant.now()).toMillis();
		ct.setCihubElapsedTime(timeElapsed);

		String reversal = exchange.getMessage().getHeader("hdr_reversal",String.class);

		FIToFICustomerCreditTransferV08 creditTransferReq = rcvBi.getDocument().getFiToFICstmrCdtTrf();
		
		ct.setAmount(creditTransferReq.getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue());
		ct.setCrdtTrnRequestBizMsgIdr(hdr.getBizMsgIdr());
			
		ct.setCreditorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId());

		if (!(null == creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getTp()))
			ct.setCreditorAccountType(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getTp().getPrtry());

		// jika node splmtryData ada, ambil data custType dari sini; jika tidak maka cek apakah ada di prvtId atau orgId
		
		if (creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().size()>0) {	
			if (!(null==creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getCdtr()))
				ct.setCreditorType(creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getCdtr().getTp());
		}
		
		else if (!(null==creditTransferReq.getCdtTrfTxInf().get(0).getCdtr())) {
				ct.setCreditorType("01");
			}

		else 
			ct.setCreditorType("02");
		

		if (!(null==creditTransferReq.getCdtTrfTxInf().get(0).getCdtr())) {

			if (!(null==creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId().getPrvtId()))
				ct.setCreditorId(creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId().getPrvtId().getOthr().get(0).getId());
			else
				ct.setCreditorId(creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId().getOrgId().getOthr().get(0).getId());

		}
		
		ct.setCreateDt(LocalDateTime.now());
		
		ct.setDebtorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getDbtrAcct().getId().getOthr().getId());
		ct.setDebtorAccountType(creditTransferReq.getCdtTrfTxInf().get(0).getDbtrAcct().getTp().getPrtry());
		
		// tentukan debtorType: Personal atau bukan
		if (creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().size()>0) {	
			if (!(null==creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getDbtr()))
				ct.setDebtorType(creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getDbtr().getTp());
		}
		else if (!(null==creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getPrvtId())) 
				ct.setDebtorType("01");
		else 
			ct.setDebtorType("02");

		if (!(null==creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getPrvtId()))
			ct.setDebtorId(creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getPrvtId().getOthr().get(0).getId());
		else
			ct.setDebtorId(creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getOrgId().getOthr().get(0).getId());

		String msgType = exchange.getMessage().getHeader("hdr_msgType", String.class);

		if ((msgType.equals("010")) || (msgType.equals("110")))
			ct.setMsgType("Credit Transfer");
		else
			ct.setMsgType("Reverse CT");
			
		String orgnBank = hdr.getFr().getFIId().getFinInstnId().getOthr().getId();
		String recptBank = hdr.getTo().getFIId().getFinInstnId().getOthr().getId();

		ct.setOriginatingBank(orgnBank);
		ct.setRecipientBank(recptBank);

		ct.setReversal(reversal);
		
		creditTrnRepo.save(ct);
	}
	
}