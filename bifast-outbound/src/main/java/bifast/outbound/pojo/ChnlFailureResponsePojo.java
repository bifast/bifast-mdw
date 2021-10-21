package bifast.outbound.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
	"referenceId"
	,"errorCode"
	,"location"
	,"description"
	,"additionalData"
	})
public class ChnlFailureResponsePojo {
	
	private String faultCategory;   // TIMEOUT-CICONN, TIMEOUT-CB, ERROR-KOMI, ERROR-CICONN, ERROR-CB
	
	@JsonProperty("ErrorCode")
	private String errorCode;
	
	private String responseCode;
	
	@JsonProperty("TransactionId")
	private String referenceId;
	

	@JsonProperty("Description")
	private String description;


	public String getFaultCategory() {
		return faultCategory;
	}


	public void setFaultCategory(String faultCategory) {
		this.faultCategory = faultCategory;
	}


	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	public String getResponseCode() {
		return responseCode;
	}


	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}


	public String getReferenceId() {
		return referenceId;
	}


	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	
}
