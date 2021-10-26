package bifast.outbound.corebank;

import com.fasterxml.jackson.annotation.JsonProperty;

import bifast.outbound.corebank.pojo.CBDebitRequestPojo;

public class CBInstructionWrapper {

	@JsonProperty("DebitInstructionRequest")
	private CBDebitRequestPojo cbDebitInstructionRequest;


	public CBDebitRequestPojo getCbDebitInstructionRequest() {
		return cbDebitInstructionRequest;
	}

	public void setCbDebitInstructionRequest(CBDebitRequestPojo cbDebitInstructionRequest) {
		this.cbDebitInstructionRequest = cbDebitInstructionRequest;
	}

	
}
