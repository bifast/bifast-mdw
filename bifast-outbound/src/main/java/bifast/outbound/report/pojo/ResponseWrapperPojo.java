package bifast.outbound.report.pojo;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.report.NotFoundResponsePojo;

public class ResponseWrapperPojo {

	@JsonProperty("AccountEnquiry")
	private BusinessMessage accountEnquiryMessage;

	@JsonProperty("CreditTransfer")
	private BusinessMessage creditTransferMessage;

	@JsonProperty("FICreditTransfer")
	private BusinessMessage fiCreditTransferMessage;

	@JsonProperty("MessageNotFound")
	private NotFoundResponsePojo messageNotFound;

	public BusinessMessage getAccountEnquiryMessage() {
		return accountEnquiryMessage;
	}

	public void setAccountEnquiryMessage(BusinessMessage accountEnquiryMessage) {
		this.accountEnquiryMessage = accountEnquiryMessage;
	}

	public BusinessMessage getCreditTransferMessage() {
		return creditTransferMessage;
	}

	public void setCreditTransferMessage(BusinessMessage creditTransferMessage) {
		this.creditTransferMessage = creditTransferMessage;
	}

	public BusinessMessage getFiCreditTransferMessage() {
		return fiCreditTransferMessage;
	}

	public void setFiCreditTransferMessage(BusinessMessage fiCreditTransferMessage) {
		this.fiCreditTransferMessage = fiCreditTransferMessage;
	}

	public NotFoundResponsePojo getMessageNotFound() {
		return messageNotFound;
	}

	public void setMessageNotFound(NotFoundResponsePojo messageNotFound) {
		this.messageNotFound = messageNotFound;
	}

    
    
	
}
