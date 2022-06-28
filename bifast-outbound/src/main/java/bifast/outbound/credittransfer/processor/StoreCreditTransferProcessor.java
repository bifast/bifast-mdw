package bifast.outbound.credittransfer.processor;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs008.FIToFICustomerCreditTransferV08;
import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.pojo.flat.FlatPacs002Pojo;
import bifast.outbound.repository.CreditTransferRepository;

@Component
public class StoreCreditTransferProcessor implements Processor {

	@Autowired private CreditTransferRepository creditTransferRepo;
	
//	private static Logger logger = LoggerFactory.getLogger(StoreCreditTransferProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
//		logger.debug("Save table credit transfer");
		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
		BusinessMessage biRequest = rmw.getCreditTransferRequest();
		FIToFICustomerCreditTransferV08 creditTransferReq = biRequest.getDocument().getFiToFICstmrCdtTrf();
		
		ChnlCreditTransferRequestPojo chnlReq = rmw.getChnlCreditTransferRequest();
		
		CreditTransfer ct = null;

		Optional<CreditTransfer> oCT = creditTransferRepo.findByKomiTrnsId(rmw.getKomiTrxId());
		if (oCT.isEmpty()) {
			ct = new CreditTransfer();
			ct.setKomiTrnsId(rmw.getKomiTrxId());

			ct.setMsgType("Credit Transfer");
			ct.setCihubRequestDT(LocalDateTime.now());
			ct.setAmount(creditTransferReq.getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue());
			ct.setCrdtTrnRequestBizMsgIdr(biRequest.getAppHdr().getBizMsgIdr());
			ct.setEndToEndId(creditTransferReq.getCdtTrfTxInf().get(0).getPmtId().getEndToEndId());

			ct.setCreditorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId());
			
			if (null != creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getTp())
				ct.setCreditorAccountType(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getTp().getPrtry());

			if (creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().size()>0)
				if (null != creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getCdtr())
					if (null != creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getCdtr().getTp())
						ct.setCreditorType(creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getCdtr().getTp());
			
			ct.setCreateDt(creditTransferReq.getGrpHdr().getCreDtTm().toGregorianCalendar().toZonedDateTime().toLocalDateTime());

			ct.setDebtorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getDbtrAcct().getId().getOthr().getId());
			ct.setDebtorAccountType(creditTransferReq.getCdtTrfTxInf().get(0).getDbtrAcct().getTp().getPrtry());
			
			if (creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().size()>0)
				if (null != creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getDbtr())
					if (null != creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getDbtr().getTp())
						ct.setDebtorType(creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getDbtr().getTp());

			if (chnlReq.getDbtrType().equals("01"))
				ct.setDebtorId(creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getPrvtId().getOthr().get(0).getId());
			else
				ct.setDebtorId(creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getOrgId().getOthr().get(0).getId());		
			
			ct.setOriginatingBank(biRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId());
			ct.setRecipientBank(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAgt().getFinInstnId().getOthr().getId());
			ct.setPsCounter(-1);

			creditTransferRepo.save(ct);
		}
		
		else {
			ct = oCT.get();

			long timeElapsed = Duration.between(rmw.getCihubStart(), Instant.now()).toMillis();
			ct.setCihubElapsedTime(timeElapsed);
			ct.setLastUpdateDt(LocalDateTime.now());
			
			if (ct.getPsCounter() < 0)
				ct.setFullRequestMessage(rmw.getCihubEncriptedRequest());
			
			if (null != rmw.getCihubEncriptedResponse())
				ct.setFullResponseMsg(rmw.getCihubEncriptedResponse());
			
			
			ResponseMessageCollection rmc = exchange.getProperty("prop_response_list", ResponseMessageCollection.class);
			ct.setErrorMessage(rmc.getLastError());

			ct.setSettlementConfBizMsgIdr("WAITING");

			Object oBiResponse = exchange.getMessage().getBody(Object.class);

			if (oBiResponse.getClass().getSimpleName().equals("FaultPojo")) {

				FaultPojo fault = (FaultPojo)oBiResponse;

				ct.setCallStatus(fault.getCallStatus());
				ct.setResponseCode(fault.getResponseCode());
				ct.setReasonCode(fault.getReasonCode());
				ct.setErrorMessage(fault.getErrorMessage());
			}
				
			else if (oBiResponse.getClass().getSimpleName().equals("FlatAdmi002Pojo")) {
				ct.setCallStatus("REJECT");
				ct.setResponseCode("RJCT");
				ct.setReasonCode("U215");
				ct.setErrorMessage("Message Rejected with Admi.002");
			}
			
			else {

				FlatPacs002Pojo ctResponse = (FlatPacs002Pojo) oBiResponse;
			
				if (ctResponse.getReasonCode().equals("U900")) {
					ct.setCallStatus("TIMEOUT");
				}
				else {
					ct.setCallStatus("SUCCESS");
				}
				
				ct.setResponseCode(ctResponse.getTransactionStatus());
				ct.setReasonCode(ctResponse.getReasonCode());
				ct.setCrdtTrnResponseBizMsgIdr(ctResponse.getBizMsgIdr());
				
				if (!(null==rmw.getCihubEncriptedResponse()))
					ct.setFullResponseMsg(rmw.getCihubEncriptedResponse());
				
			}

			ct.setPsCounter(ct.getPsCounter()+1);
			
			creditTransferRepo.save(ct);
		}

	}
}
