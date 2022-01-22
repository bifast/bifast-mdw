package bifast.inbound.accountenquiry;

import java.text.DecimalFormat;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CbAccountEnquiryRequestPojo;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.inbound.repository.CorebankTransactionRepository;

@Component
public class BuildAERequestForCbProcessor implements Processor {
	@Autowired CorebankTransactionRepository cbTransactionRepo;
	
	private static Logger logger = LoggerFactory.getLogger(AEPortalLogProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
		FlatPacs008Pojo biReq = (FlatPacs008Pojo) processData.getBiRequestFlat();
		
		CbAccountEnquiryRequestPojo cbRequest = new CbAccountEnquiryRequestPojo();

		cbRequest.setKomiTrnsId(processData.getKomiTrnsId());

		DecimalFormat df = new DecimalFormat("#############.00");
		cbRequest.setAmount(df.format(biReq.getAmount()));

		cbRequest.setCategoryPurpose(biReq.getCategoryPurpose());
		cbRequest.setAccountNumber(biReq.getCreditorAccountNo());
		
		exchange.getMessage().setBody(cbRequest);
	}

}
