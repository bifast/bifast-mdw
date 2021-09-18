package bifast.outbound.corebank;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("FITransferResponse")
public class CBFITransferResponsePojo {

	private String transactionId;
	private String status;
	private String addtInfo;
	private String responseTime;
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAddtInfo() {
		return addtInfo;
	}
	public void setAddtInfo(String addtInfo) {
		this.addtInfo = addtInfo;
	}
	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	


}
