package bifast.corebank.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreditInstructionResponsePojo {
	
	@JsonProperty("CreditInstructionResponse")
	CreditInstructionResponse creditInstructionResponse;

	public CreditInstructionResponse getCreditInstructionResponse() {
		return creditInstructionResponse;
	}

	public void setCreditInstructionResponse(CreditInstructionResponse creditInstructionResponse) {
		this.creditInstructionResponse = creditInstructionResponse;
	}

	
	
}
