package bifast.corebank.pojo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;


public class AccountEnquiryRequest {

	private String transactionId;
	private String categoryPurpose;  //M
	private String accountNumber; //M
	private BigDecimal amount;  //M
	
	
	public AccountEnquiryRequest () {}
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getCategoryPurpose() {
		return categoryPurpose;
	}
	public void setCategoryPurpose(String categoryPurpose) {
		this.categoryPurpose = categoryPurpose;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
}
