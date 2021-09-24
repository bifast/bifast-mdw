package bifast.outbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.model.DomainCode;
import bifast.outbound.repository.DomainCodeRepository;

@Component
public class SetTransactionTypeProcessor implements Processor {

	@Autowired
	private DomainCodeRepository domainCodeRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		BusinessMessage bm = exchange.getIn().getBody(BusinessMessage.class);

		String trxName = "";
		
		if (bm.getAppHdr().getMsgDefIdr().startsWith("pacs.028")) {
			trxName = "Payment Status";
		}

		else {
			String trxCode = bm.getAppHdr().getBizMsgIdr().substring(16,19);
			
			trxName = domainCodeRepo.findByGrpAndKey("TRANSACTION.TYPE", trxCode).orElse(new DomainCode()).getValue();
		}
		
		exchange.getMessage().setHeader("hdr_trxname", trxName);
	}
		
}
