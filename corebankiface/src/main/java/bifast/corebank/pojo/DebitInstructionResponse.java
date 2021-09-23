package bifast.corebank.pojo;

import java.util.Date;

public class DebitInstructionResponse {
	
	private String transactionId;
	private String status; 
	private String reason;
	private String accountNumber;
	private String amount;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
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
