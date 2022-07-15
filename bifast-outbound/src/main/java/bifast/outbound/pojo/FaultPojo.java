package bifast.outbound.pojo;

public class FaultPojo {
	
	private String callStatus;
	
	private String responseCode;

	private String reasonCode;
	
	private String reasonMessage;
	
	private String errorMessage;
	
	private String location;
	
	private Object orgnlResponse;
	
	private String redeliveryCounter;

	public FaultPojo () {
	}
	
	public FaultPojo (String callStatus, 
					  String responseCode, 
					  String reasonCode) {
		this.callStatus = callStatus;
		this.responseCode = responseCode;
		this.reasonCode = reasonCode;
	}
	
	public String getCallStatus() {
		return callStatus;
	}

	public void setCallStatus(String callStatus) {
		this.callStatus = callStatus;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonMessage() {
		return reasonMessage;
	}

	public void setReasonMessage(String reasonMessage) {
		this.reasonMessage = reasonMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Object getOrgnlResponse() {
		return orgnlResponse;
	}

	public void setOrgnlResponse(Object orgnlResponse) {
		this.orgnlResponse = orgnlResponse;
	}

	public String getRedeliveryCounter() {
		return redeliveryCounter;
	}

	public void setRedeliveryCounter(String redeliveryCounter) {
		this.redeliveryCounter = redeliveryCounter;
	}

	@Override
	public String toString() {
		return "FaultPojo [callStatus=" + callStatus + ", responseCode=" + responseCode + ", reasonCode=" + reasonCode
				+ ", retryCounter= " + redeliveryCounter + ", reasonMessage=" + reasonMessage + ", errorMessage=" + errorMessage + "]";
	}



	
}
