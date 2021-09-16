package bifast.inbound.ficredittransfer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.CBFICreditInstructionRequestPojo;
import bifast.inbound.model.BankCode;
import bifast.inbound.repository.BankCodeRepository;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs009.FinancialInstitutionCreditTransferV09;

@Component
public class FICTCorebankRequestProcessor implements Processor {

	@Autowired
	private BankCodeRepository bankCodeRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage inboundMessage = exchange.getMessage().getBody(BusinessMessage.class);
		CBFICreditInstructionRequestPojo cbRequest = new CBFICreditInstructionRequestPojo();
		
		FinancialInstitutionCreditTransferV09 biReq = inboundMessage.getDocument().getFiCdtTrf();
		
		cbRequest.setTransactionId(inboundMessage.getAppHdr().getBizMsgIdr());

		
		DecimalFormat df = new DecimalFormat("#############.00");
		BigDecimal amount = biReq.getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue();
		cbRequest.setAmount(df.format(amount));

		String debtor = biReq.getCdtTrfTxInf().get(0).getDbtrAgt().getFinInstnId().getOthr().getId();
		String creditor = biReq.getCdtTrfTxInf().get(0).getCdtrAgt().getFinInstnId().getOthr().getId();
		BankCode creditorBankCode = bankCodeRepo.findByBicCode(creditor).orElse(new BankCode());
		BankCode debtorBankCode = bankCodeRepo.findByBicCode(debtor).orElse(new BankCode());
		
		cbRequest.setDebtorBank(debtorBankCode.getBankCode());
		cbRequest.setCreditorBank(creditorBankCode.getBankCode());
		
		if (!(null == biReq.getCdtTrfTxInf().get(0).getRmtInf()))
			cbRequest.setPaymentInfo(biReq.getCdtTrfTxInf().get(0).getRmtInf().getUstrd().get(0));
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		cbRequest.setRequestTime(LocalDateTime.now().format(dtf));
		
		exchange.getMessage().setBody(cbRequest);
	}

}
