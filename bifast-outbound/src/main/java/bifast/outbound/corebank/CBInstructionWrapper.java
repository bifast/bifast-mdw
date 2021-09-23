package bifast.outbound.corebank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CBInstructionWrapper {

	@JsonProperty("DebitInstructionRequest")
	private CBDebitInstructionRequestPojo cbDebitInstructionRequest;

//	@JsonProperty("DebitInstructionResponse")
//	private CBDebitInstructionResponsePojo cbDebitInstructionResponse;
	
	@JsonProperty("FITransferRequest")
	private CBFITransferRequestPojo cbFITransferRequest;
	
//	@JsonProperty("FITransferResponse")
//	private CBFITransferResponsePojo cbFITransferResponse;

	public CBDebitInstructionRequestPojo getCbDebitInstructionRequest() {
		return cbDebitInstructionRequest;
	}

	public void setCbDebitInstructionRequest(CBDebitInstructionRequestPojo cbDebitInstructionRequest) {
		this.cbDebitInstructionRequest = cbDebitInstructionRequest;
	}

//	public CBDebitInstructionResponsePojo getCbDebitInstructionResponse() {
//		return cbDebitInstructionResponse;
//	}
//
//	public void setCbDebitInstructionResponse(CBDebitInstructionResponsePojo cbDebitInstructionResponse) {
//		this.cbDebitInstructionResponse = cbDebitInstructionResponse;
//	}

	public CBFITransferRequestPojo getCbFITransferRequest() {
		return cbFITransferRequest;
	}

	public void setCbFITransferRequest(CBFITransferRequestPojo cbFITransferRequest) {
		this.cbFITransferRequest = cbFITransferRequest;
	}

//	public CBFITransferResponsePojo getCbFITransferResponse() {
//		return cbFITransferResponse;
//	}
//
//	public void setCbFITransferResponse(CBFITransferResponsePojo cbFITransferResponse) {
//		this.cbFITransferResponse = cbFITransferResponse;
//	}

	
}
