package bifast.outbound.credittransfer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.history.HistoryRetrievalRequest;

@Component
public class InitSettlementRetrievalProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage reqData = exchange.getMessage().getBody(BusinessMessage.class);
		HistoryRetrievalRequest histReq = new HistoryRetrievalRequest();
		
		histReq.setBizMsgIdr(reqData.getAppHdr().getBizMsgIdr());
//		histReq.setChnlRefId();
		histReq.setMsgType("Settlement");
		exchange.getIn().setBody(histReq);
	
//		ObjectMapper map = new ObjectMapper();
//		map.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		String str = map.writeValueAsString(histReq);
//		System.out.println("hasil jackson: " + str);
		
//		exchange.getIn().setBody(str);
	}

}
