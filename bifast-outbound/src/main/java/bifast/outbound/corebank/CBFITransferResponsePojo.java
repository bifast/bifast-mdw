package bifast.outbound.corebank;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("FITransferResponse")
public class CBFITransferResponsePojo {

	private String transactionId;
	private String transactionStatus;
	private String addtInfo;
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
