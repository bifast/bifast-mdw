package bifast.corebank.model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="DEBIT_TRANSFER_REQUEST")
public class DebitTransferRequest {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String transactionId;
	
	private String accountNumber;
	
	private String accountType;
	
	private String amount;
	
	private String debitorName;
	
	private String paymentInfo;
	
	private Date requestTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

}
