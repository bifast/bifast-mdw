package bifast.outbound.fraud.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.fraud.dao.FdsChannelInputDAO;
import bifast.outbound.fraud.dao.FdsInputDAO;
import bifast.outbound.fraud.dao.FdsTransactionInputDAO;
import bifast.outbound.pojo.RequestMessageWrapper;

@Component
public class FdsInputProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {

		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
		ChnlCreditTransferRequestPojo chnReq = rmw.getChnlCreditTransferRequest();
		
		FdsTransactionInputDAO input = new FdsTransactionInputDAO();
		input.setTanggal(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

		input.setChannel(rmw.getChannelId());
		input.setMerchantType(rmw.getMerchantType());
		input.setNoReff(chnReq.getChannelRefId());
		input.setTerminalId(chnReq.getTerminalId());

		input.setMataUang("IDR");
		input.setAmount(chnReq.getAmount());
		input.setFee(chnReq.getFeeTransfer());

		input.setBeneficierResidencyStatus(chnReq.getCrdtResidentialStatus());
		input.setBenfName(chnReq.getCrdtName());
		
		input.setRekTujuan(chnReq.getCrdtAccountNo());
		input.setCreditorAccountType(chnReq.getCrdtAccountType());
		input.setCreditorId(chnReq.getCrdtId());
		input.setToAccountName(chnReq.getCrdtName());
		input.setCreditorProxyId(chnReq.getCrdtProxyIdValue());
		input.setCreditorProxyType(chnReq.getCrdtProxyIdType());
		input.setCreditorTownName(chnReq.getCrdtTownName());
		input.setCreditorType(chnReq.getCrdtType());
		input.setCustName(chnReq.getCrdtName());
		
		input.setRekSumber(chnReq.getDbtrAccountNo());
		input.setDebtorAccountType(chnReq.getDbtrAccountType());
		input.setDebtorId(chnReq.getDbtrId());
		input.setDebtorResidentialStatus(chnReq.getDbtrResidentialStatus());
		input.setDebtorTownName(chnReq.getDbtrTownName());
		input.setDebtorType(chnReq.getDbtrType());
		
		input.setPaymentInformation(chnReq.getPaymentInfo());
		input.setRecipientBank(chnReq.getRecptBank());
		
		input.setTransactionCode(chnReq.getCategoryPurpose());
		
		FdsInputDAO inp = new FdsInputDAO();
		inp.setChannel(new FdsChannelInputDAO("C01"));
		inp.setTransaction(input);
		
		exchange.getMessage().setBody(inp);
	}

}
