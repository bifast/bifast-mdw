package bifast.corebank.pojo;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("DebitInstructionRequest")
public class DebitInstructionRequest {
	
	private String transactionId;
	private String accountNumber;  
	private String accountType;
	private String amount;
	private String debitorName;
	private String paymentInfo;
	private String requestTime;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getDebitorName() {
		return debitorName;
	}
	public void setDebitorName(String debitorName) {
		this.debitorName = debitorName;
	}
	public String getPaymentInfo() {
		return paymentInfo;
	}
	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	
	
	
}
