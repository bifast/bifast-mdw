package bifast.library.iso20022.custom;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BusinessMessageWrap {

	@JsonProperty("BusMsg")
	private BusinessMessage businessMessage;

	public BusinessMessageWrap (BusinessMessage businessMessage) {
		this.businessMessage = businessMessage;
	}
	
	public BusinessMessage getBusinessMessage() {
		return businessMessage;
	}

	public void setBusinessMessage(BusinessMessage businessMessage) {
		this.businessMessage = businessMessage;
	}
	
	
}
