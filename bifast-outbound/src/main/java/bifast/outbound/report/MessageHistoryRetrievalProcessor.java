package bifast.outbound.report;

import java.util.List;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.model.AccountEnquiry;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.model.FICreditTransfer;
import bifast.outbound.report.pojo.RequestPojo;
import bifast.outbound.report.pojo.ResponsePojo;
import bifast.outbound.repository.AccountEnquiryRepository;
import bifast.outbound.repository.CreditTransferRepository;
import bifast.outbound.repository.FICreditTransferRepository;
import bifast.outbound.service.FindMessageService;

@Component
public class MessageHistoryRetrievalProcessor implements Processor {

	@Autowired
	private FindMessageService findMessageService;

	@Override
	public void process(Exchange exchange) throws Exception {

		RequestPojo request = exchange.getMessage().getBody(RequestPojo.class);

		String strMessage = null;
		
		if ( (null == request.getMsgType()) && (!(null == request.getBizMsgIdr())) ) 
			strMessage = findMessageService.findByBizMsgIdr(request.getBizMsgIdr());
			
		else if (request.getMsgType().equals("Account Enquiry")) 
			strMessage = findMessageService.findAccountEnquiry (request.getBizMsgIdr());

		else if (request.getMsgType().equals("Credit Transfer")) 
			strMessage = findMessageService.findCreditTransfer (request.getBizMsgIdr());
		
		else if (request.getMsgType().equals("FI Credit Transfer")) 
			strMessage = findMessageService.findFICreditTransfer (request.getBizMsgIdr());

		exchange.getMessage().setBody(strMessage);
	}
	

}
