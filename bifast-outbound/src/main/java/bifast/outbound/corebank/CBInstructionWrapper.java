package bifast.outbound.corebank;

import com.fasterxml.jackson.annotation.JsonProperty;

import bifast.outbound.corebank.pojo.CbDebitRequestPojo;

public class CBInstructionWrapper {

	@JsonProperty("DebitInstructionRequest")
	private CbDebitRequestPojo cbDebitInstructionRequest;


	public CbDebitRequestPojo getCbDebitInstructionRequest() {
		return cbDebitInstructionRequest;
	}

	public void setCbDebitInstructionRequest(CbDebitRequestPojo cbDebitInstructionRequest) {
		this.cbDebitInstructionRequest = cbDebitInstructionRequest;
	}

	
}
