package bifast.inbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.model.BankCode;
import bifast.inbound.model.DomainCode;
import bifast.inbound.pojo.ReverseCTRequestPojo;
import bifast.inbound.repository.BankCodeRepository;
import bifast.inbound.repository.DomainCodeRepository;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs008.CreditTransferTransaction39;
import bifast.library.iso20022.pacs008.FIToFICustomerCreditTransferV08;

@Component
public class ReversalRequestProcessor implements Processor {
	
	@Autowired
	private BankCodeRepository bankCodeRepo;
	@Autowired
	private DomainCodeRepository domainCodeRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage businessMessage = exchange.getMessage().getBody(BusinessMessage.class);
		CreditTransferTransaction39 ctReq = businessMessage.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0);
		
		ReverseCTRequestPojo reversalReq = new ReverseCTRequestPojo();
		
		reversalReq.setAmount(ctReq.getIntrBkSttlmAmt().getValue());

		String categoryPurposeCode = ctReq.getPmtTpInf().getCtgyPurp().getPrtry();
		DomainCode domainCode = domainCodeRepo.findByGrpAndKey("CATEGORY.PURPOSE", categoryPurposeCode).orElse(new DomainCode());
		reversalReq.setCategoryPurpose(domainCode.getValue());

		reversalReq.setChannel("Other");
		
		reversalReq.setCrdtAccountNo(ctReq.getDbtrAcct().getId().getOthr().getId());
		reversalReq.setCrdtAccountType(ctReq.getDbtrAcct().getTp().getPrtry());

		if (!(null== ctReq.getDbtr().getId().getPrvtId())) {
			reversalReq.setCrdtId(ctReq.getDbtr().getId().getPrvtId().getOthr().get(0).getId());
			reversalReq.setCrdtIdType("01");
		}
		else if (!(null== ctReq.getCdtr().getId().getOrgId())) {
			reversalReq.setCrdtId(ctReq.getDbtr().getId().getOrgId().getOthr().get(0).getId());
			reversalReq.setCrdtIdType("02");
		}
		
		if (!(null== ctReq.getSplmtryData().get(0).getEnvlp().getDbtr()))
			reversalReq.setCrdtIdType(ctReq.getSplmtryData().get(0).getEnvlp().getDbtr().getTp());
		
		reversalReq.setCrdtName(ctReq.getDbtr().getNm());
			
		reversalReq.setDbtrAccountNo(ctReq.getCdtrAcct().getId().getOthr().getId());
		reversalReq.setDbtrAccountType(ctReq.getCdtrAcct().getTp().getPrtry());

		if (!(null== ctReq.getCdtr().getId().getPrvtId())) {
			reversalReq.setDbtrId(ctReq.getCdtr().getId().getPrvtId().getOthr().get(0).getId());
			reversalReq.setDbtrIdType("01");
		}
		else if (!(null== ctReq.getCdtr().getId().getOrgId())) {
			reversalReq.setDbtrId(ctReq.getCdtr().getId().getOrgId().getOthr().get(0).getId());
			reversalReq.setDbtrIdType("02");
		}
		
		if (!(null== ctReq.getSplmtryData().get(0).getEnvlp().getCdtr()))
			reversalReq.setDbtrIdType(ctReq.getSplmtryData().get(0).getEnvlp().getCdtr().getTp());

		reversalReq.setDbtrName(ctReq.getCdtr().getNm());
		
		reversalReq.setOrgnlEndToEndId(businessMessage.getAppHdr().getBizMsgIdr());
		reversalReq.setOrignReffId(businessMessage.getAppHdr().getBizMsgIdr());

		String bankBic = businessMessage.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();
		BankCode bankCode = bankCodeRepo.findByBicCode(bankBic).orElse(new BankCode());
		
		reversalReq.setRecptBank(bankCode.getBankCode());

		if (null== ctReq.getRmtInf())
			reversalReq.setPaymentInfo("[REVERSAL]");
		else
			reversalReq.setPaymentInfo("[REVERSAL] " + ctReq.getRmtInf().getUstrd().get(0));
		
		exchange.getMessage().setBody(reversalReq);
	
	}

}
