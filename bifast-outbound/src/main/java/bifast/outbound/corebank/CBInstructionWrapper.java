package bifast.outbound.corebank;

import com.fasterxml.jackson.annotation.JsonProperty;

import bifast.outbound.corebank.pojo.CBDebitInstructionRequestPojo;

public class CBInstructionWrapper {

	@JsonProperty("DebitInstructionRequest")
	private CBDebitInstructionRequestPojo cbDebitInstructionRequest;


	public CBDebitInstructionRequestPojo getCbDebitInstructionRequest() {
		return cbDebitInstructionRequest;
	}

	public void setCbDebitInstructionRequest(CBDebitInstructionRequestPojo cbDebitInstructionRequest) {
		this.cbDebitInstructionRequest = cbDebitInstructionRequest;
	}

	
}
