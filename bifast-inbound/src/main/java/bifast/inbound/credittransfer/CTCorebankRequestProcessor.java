package bifast.inbound.credittransfer;

import java.text.DecimalFormat;
import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.config.Config;
import bifast.inbound.corebank.pojo.CbCreditRequestPojo;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;

@Component
public class CTCorebankRequestProcessor implements Processor {
	@Autowired Config config;

	@Override
	public void process(Exchange exchange) throws Exception {
		ProcessDataPojo processData= exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);

		FlatPacs008Pojo biReq = (FlatPacs008Pojo) processData.getBiRequestFlat();


		@SuppressWarnings("unchecked")
		HashMap<String, Object> arr = exchange.getMessage().getHeader("ctsaf_qryresult", HashMap.class);

		CbCreditRequestPojo cbRequest = new CbCreditRequestPojo();

		cbRequest.setKomiTrnsId(String.valueOf(arr.get("komi_trns_id")));
		cbRequest.setRecipientBank(config.getBankcode());
		
		DecimalFormat df = new DecimalFormat("#############.00");
		cbRequest.setAmount(df.format(biReq.getAmount()));

		cbRequest.setCategoryPurpose(biReq.getCategoryPurpose());
		cbRequest.setCreditorAccountNumber(biReq.getCreditorAccountNo());
		cbRequest.setCreditorAccountType(biReq.getCreditorAccountType());

		if (null != biReq.getCreditorId())
			cbRequest.setCreditorId(biReq.getCreditorId());

		cbRequest.setCreditorName(biReq.getCreditorName());
		
		if (null != biReq.getCreditorAccountProxyId()) {
			cbRequest.setCreditorProxyId(biReq.getCreditorAccountProxyId());
			cbRequest.setCreditorProxyType(biReq.getCreditorAccountProxyType());
		}
		
		cbRequest.setCreditorResidentStatus(biReq.getCreditorResidentialStatus());
		cbRequest.setCreditorTownName(biReq.getCreditorTownName());
		cbRequest.setCreditorType(biReq.getCreditorType());
		
		cbRequest.setDebtorAccountNumber(biReq.getDebtorAccountNo());
		cbRequest.setDebtorAccountType(biReq.getDebtorAccountType());
		if (null != biReq.getDebtorId())
			cbRequest.setDebtorId(biReq.getDebtorId());
			
		cbRequest.setDebtorName(biReq.getDebtorName());
		cbRequest.setDebtorResidentStatus(biReq.getDebtorResidentialStatus());
		cbRequest.setDebtorTownName(biReq.getDebtorTownName());
		cbRequest.setDebtorType(biReq.getDebtorType());
		
		cbRequest.setFeeTransfer("0.00");

		
		if (!(null == biReq.getPaymentInfo()))
			cbRequest.setPaymentInformation(biReq.getPaymentInfo());
				
		exchange.getMessage().setBody(cbRequest);
	}

}
