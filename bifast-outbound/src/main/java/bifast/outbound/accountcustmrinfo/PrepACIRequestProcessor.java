package bifast.outbound.accountcustmrinfo;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.accountcustmrinfo.pojo.ChnlAccountCustomerInfoRequestPojo;
import bifast.outbound.corebank.pojo.AccountCustInfoRequestDTO;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.service.RefUtils;

@Component
public class PrepACIRequestProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {

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
