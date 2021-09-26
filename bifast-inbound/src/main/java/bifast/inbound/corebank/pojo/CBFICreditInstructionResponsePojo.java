package bifast.inbound.corebank.pojo;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("FICreditInstructionResponse")
public class CBFICreditInstructionResponsePojo {

	private String transactionId;
	private String transactionStatus;
	private String reason;

	private String responseTime;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	
	
}
