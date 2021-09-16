package bifast.outbound.report;

import javax.xml.bind.annotation.XmlElement;

public class ResponseWrapperPojo {

    @XmlElement(name = "MessageEnquiryResult")
    private ResponsePojo messageEnquiryResult;

	public ResponsePojo getMessageEnquiryResult() {
		return messageEnquiryResult;
	}

	public void setMessageEnquiryResult(ResponsePojo messageEnquiryResult) {
		this.messageEnquiryResult = messageEnquiryResult;
	}
    
	
}
