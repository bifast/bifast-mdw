package bifast.mock.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.mock.persist.MockPacs002;
import bifast.mock.persist.MockPacs002Repository;

@Component
public class CreditResponseStoreProcessor implements Processor {

    @Autowired
    private MockPacs002Repository mockPacs002Repo;

    @Override
    public void process(Exchange exchange) throws Exception {

        BusinessMessage objRequest = exchange.getMessage().getHeader("objRequest", BusinessMessage.class);		
        BusinessMessage responseMsg = exchange.getMessage().getHeader("hdr_ctResponseObj", BusinessMessage.class);
        String fullMsg = exchange.getMessage().getBody(String.class);

        // String sts = exchange.getMessage().getHeader("hdr_ctRespondStatus", String.class);

        // simpan sbg history
			MockPacs002 pacs002 = new MockPacs002();
			pacs002.setBizMsgIdr(responseMsg.getAppHdr().getBizMsgIdr());
	
			pacs002.setFullMessage(fullMsg);
			
	        pacs002.setOrgnlEndToEndId(responseMsg.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlEndToEndId());
			
	        pacs002.setOrgnlMsgId(responseMsg.getDocument().getFiToFIPmtStsRpt().getOrgnlGrpInfAndSts().get(0).getOrgnlMsgId());
			
	        pacs002.setOrgnlMsgName(objRequest.getAppHdr().getMsgDefIdr());

            if (objRequest.getAppHdr().getMsgDefIdr().startsWith("pacs.008"))
                pacs002.setTrxType("CreditConfirmation");
            else
                pacs002.setTrxType("FICreditConfirmation");

	        mockPacs002Repo.save(pacs002);
        
    }
    
}
