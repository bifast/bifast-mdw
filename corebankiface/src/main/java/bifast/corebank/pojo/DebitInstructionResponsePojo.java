package bifast.corebank.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DebitInstructionResponsePojo {
	
	@JsonProperty("DebitInstructionResponse")
	DebitInstructionResponse debitInstructionResponse;

	public DebitInstructionResponse getDebitInstructionResponse() {
		return debitInstructionResponse;
	}

	public void setDebitInstructionResponse(DebitInstructionResponse debitInstructionResponse) {
		this.debitInstructionResponse = debitInstructionResponse;
	}
	
}
