package bifast.outbound.corebank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CBInstructionWrapper {

	@JsonProperty("DebitInstructionRequest")
	private CBDebitInstructionRequestPojo cbDebitInstructionRequest;

	@JsonProperty("DebitInstructionResponse")
	private CBDebitInstructionResponsePojo cbDebitInstructionResponse;
	
	public CBDebitInstructionRequestPojo getCbDebitInstructionRequest() {
		return cbDebitInstructionRequest;
	}

	public void setCbDebitInstructionRequest(CBDebitInstructionRequestPojo cbDebitInstructionRequest) {
		this.cbDebitInstructionRequest = cbDebitInstructionRequest;
	}

	public CBDebitInstructionResponsePojo getCbDebitInstructionResponse() {
		return cbDebitInstructionResponse;
	}

	public void setCbDebitInstructionResponse(CBDebitInstructionResponsePojo cbDebitInstructionResponse) {
		this.cbDebitInstructionResponse = cbDebitInstructionResponse;
	}

	
	
}
