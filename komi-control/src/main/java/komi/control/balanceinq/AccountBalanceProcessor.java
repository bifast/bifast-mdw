package komi.control.balanceinq;

import org.apache.camel.Exchange;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import komi.control.balanceinq.dto.BalanceInquiryRequest;
import komi.control.balanceinq.dto.BalanceInquiryResponse;
import komi.control.balanceinq.dto.CbAccountBalanceRequestPojo;
import komi.control.balanceinq.dto.CbAccountBalanceResponsePojo;

@Component
public class AccountBalanceProcessor implements Processor{
	private static Logger logger = LoggerFactory.getLogger(AccountBalanceProcessor.class);

	@Value("${adapter.api.balanceinquiry.url}")
	String url;

	@Value("${adapter.api.corebic}")
	String corebic;

	@Value("${adapter.api.merchant}")
	String merchant;

	@Value("${adapter.api.terminal}")
	String terminal;

	@Value("${adapter.api.txid}")
	String txid;

	@Value("${adapter.api.timeoutseconds}")
	Integer timeout;

	@Override
	public void process(Exchange exchange) throws Exception {

		CbAccountBalanceRequestPojo cbBalanceRequest = exchange.getMessage().getBody(CbAccountBalanceRequestPojo.class);

		BalanceInquiryRequest req = new BalanceInquiryRequest();
		req.setAccountNumber(cbBalanceRequest.getAccountNumber());
		Utils.Ref ref = Utils.newRef();
		req.setDateTime(ref.getDateTime());
		req.setMerchantType(merchant);
		req.setNoRef(ref.getNoRef());
		req.setTerminalId(terminal);
		req.setTransactionId(txid);
		
		// call rest api
		FluentProducerTemplate template = exchange.getContext().createFluentProducerTemplate();
		BalanceInquiryResponse cbbalInqrResp = template.withBody(req).to("direct:balanceinquiry").request(BalanceInquiryResponse.class);

		CbAccountBalanceResponsePojo response = new CbAccountBalanceResponsePojo();
		response.setKomiTrnsId(cbBalanceRequest.getKomiTrnsId());
		response.setBalance(cbbalInqrResp.getBalance());
		response.setReason(cbbalInqrResp.getReason());
		response.setStatus(cbbalInqrResp.getStatus());
		
		exchange.getMessage().setBody(response);

		logger.debug("Selesai call balanceInquiry");
	}

}
