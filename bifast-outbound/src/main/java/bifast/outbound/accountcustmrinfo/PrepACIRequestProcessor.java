package bifast.outbound.accountcustmrinfo;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.accountcustmrinfo.pojo.ChnlAccountCustomerInfoRequestPojo;
import bifast.outbound.corebank.pojo.AccountCustInfoRequestDTO;
import bifast.outbound.credittransfer.processor.AftDebitCallProc;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.service.CallRouteService;
import bifast.outbound.service.RefUtils;

@Component
public class PrepACIRequestProcessor implements Processor{
	@Autowired CallRouteService callRouteService;
	
    private static Logger logger = LoggerFactory.getLogger(PrepACIRequestProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		logger.debug("Prepare ACI request msg");
		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
		ChnlAccountCustomerInfoRequestPojo chnlReq = (ChnlAccountCustomerInfoRequestPojo) rmw.getChannelRequest();
		AccountCustInfoRequestDTO req = new AccountCustInfoRequestDTO();
		req.setAccountNumber(chnlReq.getAccountNumber());
		RefUtils.Ref ref = RefUtils.setRef(chnlReq.getNoRef());

		req.setDateTime(ref.getDateTime());
		req.setMerchantType(rmw.getMerchantType());
		req.setNoRef(ref.getNoRef());
		req.setTerminalId(chnlReq.getTerminalId());
		req.setTransactionId("000001");
		
		exchange.getMessage().setBody(req);
			
	}

}
