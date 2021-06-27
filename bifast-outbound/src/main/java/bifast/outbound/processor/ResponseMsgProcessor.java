package fransmz.bifast.credittransfer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import fransmz.bifast.pojo.BusinessMessage;
import iso20022.pacs002.FIToFIPaymentStatusReportV11;

@Component
public class ResponseMsgProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		ResponseMsg resp = new ResponseMsg();
		
		CreditTransferRequestInput input = exchange.getMessage().getHeader("req_orgnlReq", CreditTransferRequestInput.class);
		
		BusinessMessage cTrfResponse = exchange.getIn().getBody(BusinessMessage.class);
		FIToFIPaymentStatusReportV11 rptSts = cTrfResponse.getDocument().getFiToFIPmtStsRpt();
		
		resp.setMessageId(input.getMessageId());
		resp.setDebtorAccountNumber(input.getDebtorAccountNumber());
		resp.setAmount(input.getAmount());
		resp.setReceivingParticipant(input.getReceivingParticipant());
		resp.setCreditorAccountNumber(input.getCreditorAccountNumber());
		resp.setCreditorName(rptSts.getTxInfAndSts().get(0).getOrgnlTxRef().getCdtr().getPty().getNm());
		
		String status = rptSts.getTxInfAndSts().get(0).getTxSts();
		if (status.equals("ACTC")) 
			resp.setStatus("Transaction completed");
		else {
			resp.setStatus("Transaction rejected ");
			resp.setReason(rptSts.getTxInfAndSts().get(0).getStsRsnInf().get(0).getRsn().getPrtry());
		}		
		exchange.getMessage().setBody(resp);
	}

}
