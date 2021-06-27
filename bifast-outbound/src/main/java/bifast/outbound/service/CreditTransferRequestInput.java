package bifast.outbound.service;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("creditTransferInput")
public class CreditTransferRequestInput {

	private String messageId;   //M
	private String channelType;  //M
	private String categoryPurpose;  //M
//	private String transactionType; //M
	private BigDecimal amount;  //M

	private String debtorId;   // M
	private String debtorIdType; //M   Private or Organisation
	private String debtorAccountNumber; //M
	private String debtorAccountType; //M
	
	private String receivingParticipant; //M
	
//	private String creditorName;
	private String creditorId; //M
	private String creditorIdType; //O private or organization
	private String creditorAccountNumber; //M
	private String creditorAccountType; //M

	private String paymentInfo;

	public CreditTransferRequestInput() {
		this.channelType = "99";
		this.debtorIdType = "Private";
		this.creditorIdType = "Private";
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getCategoryPurpose() {
		return categoryPurpose;
	}

	public void setCategoryPurpose(String categoryPurpose) {
		this.categoryPurpose = categoryPurpose;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDebtorId() {
		return debtorId;
	}

	public void setDebtorId(String debtorId) {
		this.debtorId = debtorId;
	}

	public String getDebtorIdType() {
		return debtorIdType;
	}

	public void setDebtorIdType(String debtorIdType) {
		this.debtorIdType = debtorIdType;
	}

	public String getDebtorAccountNumber() {
		return debtorAccountNumber;
	}

	public void setDebtorAccountNumber(String debtorAccountNumber) {
		this.debtorAccountNumber = debtorAccountNumber;
	}

	public String getDebtorAccountType() {
		return debtorAccountType;
	}

	public void setDebtorAccountType(String debtorAccountType) {
		this.debtorAccountType = debtorAccountType;
	}

	public String getReceivingParticipant() {
		return receivingParticipant;
	}

	public void setReceivingParticipant(String receivingParticipant) {
		this.receivingParticipant = receivingParticipant;
	}

	public String getCreditorId() {
		return creditorId;
	}

	public void setCreditorId(String creditorId) {
		this.creditorId = creditorId;
	}

	public String getCreditorIdType() {
		return creditorIdType;
	}

	public void setCreditorIdType(String creditorIdType) {
		this.creditorIdType = creditorIdType;
	}

	public String getCreditorAccountNumber() {
		return creditorAccountNumber;
	}

	public void setCreditorAccountNumber(String creditorAccountNumber) {
		this.creditorAccountNumber = creditorAccountNumber;
	}

	public String getCreditorAccountType() {
		return creditorAccountType;
	}

	public void setCreditorAccountType(String creditorAccountType) {
		this.creditorAccountType = creditorAccountType;
	}

	public String getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	
}
