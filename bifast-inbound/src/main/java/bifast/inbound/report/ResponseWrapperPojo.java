package bifast.inbound.report;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import bifast.library.iso20022.custom.BusinessMessage;

@JsonRootName(value = "MessageEnquiryResult")
public class ResponseWrapperPojo {

	@JsonProperty("BusMsg")
	private BusinessMessage businessMessage;
	@JsonProperty("MessageNotFound")
	private NotFoundResponsePojo messageNotFound;
	
	public BusinessMessage getBusinessMessage() {
		return businessMessage;
	}
	public void setBusinessMessage(BusinessMessage businessMessage) {
		this.businessMessage = businessMessage;
	}
	public NotFoundResponsePojo getMessageNotFound() {
		return messageNotFound;
	}
	public void setMessageNotFound(NotFoundResponsePojo messageNotFound) {
		this.messageNotFound = messageNotFound;
	}
	
	
}
