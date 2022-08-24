package bifast.outbound.reversect;

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
import bifast.outbound.pojo.flat.FlatPacs002Pojo;
import bifast.outbound.repository.CreditTransferRepository;

@Component
public class SaveReversalCTProc implements Processor {

	@Autowired private CreditTransferRepository creditTransferRepo;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
		BusinessMessage biRequest = rmw.getCreditTransferRequest();
		FIToFICustomerCreditTransferV08 creditTransferReq = biRequest.getDocument().getFiToFICstmrCdtTrf();
		
		CreditTransfer ct = null;

		ct = new CreditTransfer();
		ct.setKomiTrnsId(rmw.getKomiTrxId());

		ct.setMsgType("Reversal CT");
		ct.setCihubRequestDT(LocalDateTime.now());
		ct.setAmount(creditTransferReq.getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue());
		ct.setCrdtTrnRequestBizMsgIdr(biRequest.getAppHdr().getBizMsgIdr());
		ct.setEndToEndId(creditTransferReq.getCdtTrfTxInf().get(0).getPmtId().getEndToEndId());
		ct.setCreditorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId());
		ct.setCreditorAccountType(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getTp().getPrtry());

		if (null != creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId())
			if (null != creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId().getPrvtId())
				ct.setCreditorType(creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId().getPrvtId().getOthr().get(0).getId());
		
		if (null != creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId())
			if (null != creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getOrgId())
				ct.setDebtorType(creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getOrgId().getOthr().get(0).getId());
		
		ct.setCreateDt(creditTransferReq.getGrpHdr().getCreDtTm().toGregorianCalendar().toZonedDateTime().toLocalDateTime());

		ct.setDebtorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getDbtrAcct().getId().getOthr().getId());
		ct.setDebtorAccountType(creditTransferReq.getCdtTrfTxInf().get(0).getDbtrAcct().getTp().getPrtry());

		if ((null != ct.getDebtorType()) && (ct.getDebtorType().endsWith("01")))
			ct.setDebtorId(creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getPrvtId().getOthr().get(0).getId());
		else
			ct.setDebtorId(creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getOrgId().getOthr().get(0).getId());		
		
		ct.setOriginatingBank(biRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId());
		ct.setRecipientBank(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAgt().getFinInstnId().getOthr().getId());
		
		ct.setFullRequestMessage(rmw.getCihubEncriptedRequest());
		
		if (null != rmw.getCihubEncriptedResponse())
			ct.setFullResponseMsg(rmw.getCihubEncriptedResponse());

		Object oBiResponse = exchange.getMessage().getBody(Object.class);

		if (oBiResponse.getClass().getSimpleName().equals("FaultPojo")) {

			FaultPojo fault = (FaultPojo)oBiResponse;

			ct.setCallStatus(fault.getCallStatus());
			ct.setResponseCode(fault.getResponseCode());
			ct.setReasonCode(fault.getReasonCode());
			ct.setErrorMessage(fault.getErrorMessage());
		}

		else {

			FlatPacs002Pojo ctResponse = (FlatPacs002Pojo) oBiResponse;
		
			if (ctResponse.getReasonCode().equals("U900")) {
				ct.setCallStatus("TIMEOUT");
				ct.setPsCounter(0);
			}
			else {
				ct.setCallStatus("SUCCESS");
				ct.setSettlementConfBizMsgIdr("WAITING");
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
