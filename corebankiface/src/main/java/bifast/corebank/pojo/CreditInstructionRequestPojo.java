package bifast.corebank.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreditInstructionRequestPojo {
	
	@JsonProperty("CreditInstructionRequest")
	CreditInstructionRequest creditInstructionRequest;

	public CreditInstructionRequest getCreditInstructionRequest() {
		return creditInstructionRequest;
	}

	public void setCreditInstructionRequest(CreditInstructionRequest creditInstructionRequest) {
		this.creditInstructionRequest = creditInstructionRequest;
	}
	
	
}
