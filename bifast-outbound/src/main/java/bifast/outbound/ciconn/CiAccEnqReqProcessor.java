package bifast.outbound.ciconn;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.service.UtilService;

@Component
public class CiAccEnqReqProcessor implements Processor {

	@Autowired
	private UtilService utilService;
	
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	DecimalFormat df = new DecimalFormat("00000000");

	@Override
	public void process(Exchange exchange) throws Exception {

		
//		String strToday = rmw.getKomiTrxId().substring(0,8);
//		String komiNumber = rmw.getKomiTrxId().substring(9);
//		return strToday + config.getBankcode() + trxType + "O" + rmw.getChannelType() + komiNumber;
//
//		String bizMsgId = 
//		String msgId = utilService.genMessageId(msgType, rmw);
//		
//		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
//
//		hdr = appHeaderService.getAppHdr(chnReq.getRecptBank(), "pacs.008.001.08", bizMsgId);
//		
//		Pacs008Seed seedAcctEnquiry = new Pacs008Seed();
//		
//		seedAcctEnquiry.setMsgId(msgId);
//		seedAcctEnquiry.setBizMsgId(hdr.getBizMsgIdr());
//		
//		seedAcctEnquiry.setAmount(new BigDecimal(chnReq.getAmount()));
//		
//		seedAcctEnquiry.setCategoryPurpose(chnReq.getCategoryPurpose());
//		seedAcctEnquiry.setDbtrAccountNo(chnReq.getSenderAccountNumber());
//		seedAcctEnquiry.setCrdtAccountNo(chnReq.getCreditorAccountNumber());
//		seedAcctEnquiry.setOrignBank(config.getBankcode());
//		seedAcctEnquiry.setRecptBank(chnReq.getRecptBank());
//		seedAcctEnquiry.setTrnType(msgType);
//
//		Document doc = new Document();
//		doc.setFiToFICstmrCdtTrf(pacs008MessageService.accountEnquiryRequest(seedAcctEnquiry));
//
//		
//		BusinessMessage busMsg = new BusinessMessage();
//		busMsg.setAppHdr(hdr);
//		busMsg.setDocument(doc);
//	
//		rmw.setAccountEnquiryRequest(busMsg);
//		exchange.getIn().setBody(busMsg);
	}

}
