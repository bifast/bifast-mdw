package bifast.outbound.credittransfer.processor;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs008.FIToFICustomerCreditTransferV08;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.pojo.flat.FlatPacs002Pojo;
import bifast.outbound.repository.CreditTransferRepository;

@Component
public class StoreCreditTransferProcessor implements Processor {

	@Autowired
	private CreditTransferRepository creditTransferRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		
		CreditTransfer ct = new CreditTransfer();

		ct.setKomiTrnsId(rmw.getKomiTrxId());

		BusinessMessage biRequest = rmw.getCreditTransferRequest();
		FIToFICustomerCreditTransferV08 creditTransferReq = biRequest.getDocument().getFiToFICstmrCdtTrf();
		
		ct.setAmount(creditTransferReq.getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue());

		ct.setCihubRequestDT(LocalDateTime.now());
		long timeElapsed = Duration.between(rmw.getCihubStart(), Instant.now()).toMillis();
		ct.setCihubElapsedTime(timeElapsed);

		ct.setCrdtTrnRequestBizMsgIdr(biRequest.getAppHdr().getBizMsgIdr());
		
		
		ct.setCreditorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId());
		ct.setCreditorAccountType(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getTp().getPrtry());

		ct.setCreditorType(creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getCdtr().getTp());

		if (ct.getCreditorType().equals("01"))
			ct.setCreditorId(creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId().getPrvtId().getOthr().get(0).getId());
		else
			ct.setCreditorId(creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId().getOrgId().getOthr().get(0).getId());

		ct.setCreateDt(creditTransferReq.getGrpHdr().getCreDtTm().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
		ct.setLastUpdateDt(LocalDateTime.now());
		
		ct.setDebtorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getDbtrAcct().getId().getOthr().getId());
		ct.setDebtorAccountType(creditTransferReq.getCdtTrfTxInf().get(0).getDbtrAcct().getTp().getPrtry());
		ct.setDebtorType(creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getDbtr().getTp());

		if (ct.getDebtorType().endsWith("01"))
			ct.setDebtorId(creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getPrvtId().getOthr().get(0).getId());
		else
			ct.setDebtorId(creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getOrgId().getOthr().get(0).getId());		
		
		ct.setFullRequestMessage(rmw.getCihubEncriptedRequest());
		
		if (null != rmw.getCihubEncriptedResponse())
			ct.setFullResponseMsg(rmw.getCihubEncriptedResponse());

		ct.setMsgType("Credit Transfer");
//		if (null != chnlRequest.getOrgnlEndToEndId())
//			ct.setMsgType("Reverse CT");

		ct.setOriginatingBank(biRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId());
		ct.setRecipientBank(biRequest.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());

		ResponseMessageCollection rmc = exchange.getMessage().getHeader("hdr_response_list", ResponseMessageCollection.class);
		ct.setErrorMessage(rmc.getLastError());
		
		// CHECK RESPONSE
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
			
		creditTransferRepo.save(ct);
	}
}
