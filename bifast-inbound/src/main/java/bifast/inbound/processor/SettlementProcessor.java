package bifast.inbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.FIToFIPaymentStatusReportV10;

@Component
public class SettlementProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage fullMsg = exchange.getMessage().getHeader("hdr_frBIobj", BusinessMessage.class);

		FIToFIPaymentStatusReportV10 settlement = fullMsg.getDocument().getFiToFIPmtStsRpt();
		
		// find related credit transfer
		
		// SAF
		

	}

}
