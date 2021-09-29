package bifast.inbound.credittransfer;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.MessageHistory;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.model.CreditTransfer;
import bifast.inbound.repository.CreditTransferRepository;
import bifast.inbound.service.UtilService;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs008.FIToFICustomerCreditTransferV08;

@Component
public class SaveCreditTransferProcessor implements Processor {

	@Autowired
	private CreditTransferRepository creditTrnRepo;
	@Autowired
	private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {
		 
		@SuppressWarnings("unchecked")
		List<MessageHistory> listHistory = exchange.getProperty(Exchange.MESSAGE_HISTORY, List.class);

		long routeElapsed = utilService.getRouteElapsed(listHistory, "Inbound");

		BusinessMessage rcvBi = exchange.getMessage().getHeader("hdr_frBIobj",BusinessMessage.class);
		BusinessApplicationHeaderV01 hdr = rcvBi.getAppHdr();


		CreditTransfer ct = new CreditTransfer();

		String fullReqMsg = exchange.getMessage().getHeader("hdr_frBI_jsonzip",String.class);
		String fullRespMsg = exchange.getMessage().getHeader("hdr_toBI_jsonzip",String.class);
		
		ct.setFullRequestMessage(fullReqMsg);
		ct.setFullResponseMsg(fullRespMsg);
		
//		if (!(null==hdr.getCpyDplct()))
//				inboundMsg.setCopyDupl(hdr.getCpyDplct().name());

		if (!(null == exchange.getMessage().getHeader("hdr_toBIobj"))) {
			BusinessMessage respBi = exchange.getMessage().getHeader("hdr_toBIobj",BusinessMessage.class);

			ct.setResponseStatus(respBi.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
			ct.setCrdtTrnResponseBizMsgIdr(respBi.getAppHdr().getBizMsgIdr());
			
		}
		
		ct.setCihubRequestDT(utilService.getTimestampFromMessageHistory(listHistory, "start_route"));
		ct.setCihubResponseDT(utilService.getTimestampFromMessageHistory(listHistory, "end_route"));
		ct.setCihubElapsedTime(routeElapsed);
		
		String reversal = exchange.getMessage().getHeader("resp_reversal",String.class);

		FIToFICustomerCreditTransferV08 creditTransferReq = rcvBi.getDocument().getFiToFICstmrCdtTrf();

		
		ct.setAmount(creditTransferReq.getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue());
		ct.setCrdtTrnRequestBizMsgIdr(hdr.getBizMsgIdr());
		
	
		ct.setCreditorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId());
		

		if (!(null == creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getTp()))
			ct.setCreditorAccountType(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getTp().getPrtry());
		

		// jika node splmtryData ada, ambil data custType dari sini; jika tidak maka cek apakah ada di prvtId atau orgId
		
		if (creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().size()>0) {	
			if (!(null==creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getCdtr()))
				ct.setCreditorType(creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getCdtr().getTp());
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
		
		ct.setCreDt(LocalDateTime.now());
		
		ct.setDebtorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getDbtrAcct().getId().getOthr().getId());
		ct.setDebtorAccountType(creditTransferReq.getCdtTrfTxInf().get(0).getDbtrAcct().getTp().getPrtry());
		
		// tentukan debtorType: Personal atau bukan
		if (creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().size()>0) {	
			if (!(null==creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDbtr()))
				ct.setDebtorType(creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDbtr().getTp());
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

		if (msgType.equals("010"))
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