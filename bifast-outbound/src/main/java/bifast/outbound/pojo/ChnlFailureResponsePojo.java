package bifast.outbound.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
	"transactionType"
	,"referenceId"
	,"errorCode"
	,"reason"
	,"location"
	,"description"
	,"additionalData"
	})
public class ChnlFailureResponsePojo {
	
	private String transactionType;

	@JsonProperty("ErrorCode")
	private String errorCode;
	
	@JsonProperty("TransactionId")
	private String referenceId;
	
	@JsonProperty("Reason")
	private String reason;
	
	private String location;

	@JsonProperty("Description")
	private String description;

	@JsonProperty("AddtInfo")
	private String additionalData;
	
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAdditionalData() {
		return additionalData;
	}
	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
	}

	
}
