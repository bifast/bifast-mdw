package bifast.corebank.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DebitInstructionRequestPojo {
	
	@JsonProperty("DebitInstructionRequest")
	DebitInstructionRequest debitInstructionRequest;

	public DebitInstructionRequest getDebitInstructionRequest() {
		return debitInstructionRequest;
	}

	public void setDebitInstructionRequest(DebitInstructionRequest debitInstructionRequest) {
		this.debitInstructionRequest = debitInstructionRequest;
	}

	
	
}
