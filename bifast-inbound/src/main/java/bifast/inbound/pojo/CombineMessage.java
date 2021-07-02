package bifast.inbound.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import bifast.library.iso20022.custom.BusinessMessageWrap;

@JsonRootName("CombineMessages")
@JsonPropertyOrder({"inboundMessage", 
					"responseMessage"})
public class CombineMessage {

	@JsonProperty("InboundMessage")
	private BusinessMessageWrap inboundMessage;
	
	@JsonProperty("ResponseMessage")
	private BusinessMessageWrap responseMessage;

	public BusinessMessageWrap getInboundMessage() {
		return inboundMessage;
	}

	public void setInboundMessage(BusinessMessageWrap inboundMessage) {
		this.inboundMessage = inboundMessage;
	}

	public BusinessMessageWrap getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(BusinessMessageWrap responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	
}
