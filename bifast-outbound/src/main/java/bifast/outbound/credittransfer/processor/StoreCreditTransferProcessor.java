package bifast.outbound.credittransfer.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs008.FIToFICustomerCreditTransferV08;
import bifast.outbound.credittransfer.ChnlCreditTransferRequestPojo;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.repository.CreditTransferRepository;

@Component
public class StoreCreditTransferProcessor implements Processor {

	@Autowired
	private CreditTransferRepository creditTransferRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		CreditTransfer ct = new CreditTransfer();

		ChnlCreditTransferRequestPojo chnlRequest = exchange.getMessage().getHeader("ct_channelRequest", ChnlCreditTransferRequestPojo.class);				
		String chnlRefId = exchange.getMessage().getHeader("hdr_chnlRefId", String.class);

		ct.setIntrRefId(chnlRefId);

		BusinessMessage biRequest = exchange.getMessage().getHeader("ct_birequest", BusinessMessage.class);
		
		FIToFICustomerCreditTransferV08 creditTransferReq = biRequest.getDocument().getFiToFICstmrCdtTrf();

		
		ct.setAmount(creditTransferReq.getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue());

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");

		String strCiHubRequestTime = exchange.getMessage().getHeader("hdr_cihubRequestTime", String.class);
		String strCiHubResponseTime = exchange.getMessage().getHeader("hdr_cihubResponseTime", String.class);
		
		LocalDateTime cihubRequestTime = LocalDateTime.parse(strCiHubRequestTime, dtf);
		ct.setCihubRequestDT(cihubRequestTime);
		LocalDateTime cihubResponseTime = LocalDateTime.parse(strCiHubResponseTime, dtf);
		ct.setCihubResponseDT(cihubResponseTime);

		ct.setCrdtTrnRequestBizMsgIdr(biRequest.getAppHdr().getBizMsgIdr());
		
		
		ct.setCreditorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId());
		ct.setCreditorAccountType(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getTp().getPrtry());

		ct.setCreditorType(creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getCdtr().getTp());

		if (ct.getCreditorType().equals("01"))
			ct.setCreditorId(creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId().getPrvtId().getOthr().get(0).getId());
		else
			ct.setCreditorId(creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId().getOrgId().getOthr().get(0).getId());

		ct.setCreDt(creditTransferReq.getGrpHdr().getCreDtTm().toGregorianCalendar().toZonedDateTime().toLocalDateTime());

		ct.setDebtorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getDbtrAcct().getId().getOthr().getId());
		ct.setDebtorAccountType(creditTransferReq.getCdtTrfTxInf().get(0).getDbtrAcct().getTp().getPrtry());
		ct.setDebtorType(creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDbtr().getTp());

		if (ct.getDebtorType().endsWith("01"))
			ct.setDebtorId(creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getPrvtId().getOthr().get(0).getId());
		else
			ct.setDebtorId(creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getOrgId().getOthr().get(0).getId());		
		
		String encrRequestMesg = exchange.getMessage().getHeader("hdr_encr_request", String.class);
		String encrResponseMesg = exchange.getMessage().getHeader("hdr_encr_response", String.class);
		
		ct.setFullRequestMessage(encrRequestMesg);
		if (!(null==encrResponseMesg))
			ct.setFullResponseMsg(encrResponseMesg);

		if (null==chnlRequest.getOrgnlEndToEndId())
			ct.setMsgType("Credit Transfer");
		else
			ct.setMsgType("Reverse CT");

		ct.setOriginatingBank(biRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId());
		ct.setRecipientBank(biRequest.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());

		
		// CHECK RESPONSE
		BusinessMessage biResponse = exchange.getMessage().getHeader("ct_biresponse", BusinessMessage.class);

		if (!(null==biResponse)) {
			ct.setCrdtTrnResponseBizMsgIdr(biResponse.getAppHdr().getBizMsgIdr());
			ct.setResponseStatus(biResponse.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
		}
		
		String errorStatus = exchange.getMessage().getHeader("hdr_error_status", String.class);
//		String errorMesg = exchange.getMessage().getHeader("hdr_error_mesg", String.class);
		

		if (!(null==errorStatus)) {
			ct.setCallStatus(errorStatus);
		}
		else
			ct.setCallStatus("SUCCESS");
		

		creditTransferRepo.save(ct);
	}
}
