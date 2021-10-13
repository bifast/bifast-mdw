package bifast.outbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.admi002.MessageRejectV01;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.pojo.ChnlFailureResponsePojo;

@Component
public class RejectToFaultMapProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		BusinessMessage bm = exchange.getMessage().getBody(BusinessMessage.class);
		MessageRejectV01 reject = bm.getDocument().getMessageReject();
		ChnlFailureResponsePojo fault = new ChnlFailureResponsePojo();
		
		fault.setErrorCode("ERROR-CIHUB");
		fault.setLocation(reject.getRsn().getErrLctn());
		fault.setReason(reject.getRsn().getRsnDesc());
		
		if (null != reject.getRsn().getAddtlData())
			fault.setAdditionalData(reject.getRsn().getAddtlData());
		
		exchange.getMessage().setHeader("hdr_error_status", "ERROR-CIHUB");

		exchange.getMessage().setBody(fault);
	}

}
