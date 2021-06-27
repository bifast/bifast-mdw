package bifast.outbound.pojo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("CreditTransferResponse")
public class ChannelCreditTransferResponse {

	private String intrnRefId;
	private String channel; 
	private String categoryPurpose; 
	private BigDecimal amount; 

	private String dbtrId;   
	private String dbtrIdType;  
	private String dbtrAccountNo;  
	private String dbtrAccountType; 
	
	private String recptBank; 

	private String crdtId;  
	private String crdtIdType; 
	private String crdtAccountNo;   
	private String crdtAccountType;  
	
	private String paymentInfo;
	
	// response from CI-HUB
	
	private String bizMsgId;
	private String status;
	private String reason;
	private String addtInfo;
	private String creditorName;
	private String creditorType;
	private String creditorId;
	private String creditorResidentStatus;
	private String creditorTownName;
	

	public ChannelCreditTransferResponse () {}
	
	public String getIntrnRefId() {
		return intrnRefId;
	}

	public void setIntrnRefId(String intrnRefId) {
		this.intrnRefId = intrnRefId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRecptBank() {
		return recptBank;
	}

	public void setRecptBank(String recptBank) {
		this.recptBank = recptBank;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCategoryPurpose() {
		return categoryPurpose;
	}

	public void setCategoryPurpose(String categoryPurpose) {
		this.categoryPurpose = categoryPurpose;
	}

	public String getDbtrId() {
		return dbtrId;
	}

	public void setDbtrId(String dbtrId) {
		this.dbtrId = dbtrId;
	}

	public String getDbtrIdType() {
		return dbtrIdType;
	}

	public void setDbtrIdType(String dbtrIdType) {
		this.dbtrIdType = dbtrIdType;
	}

	public String getDbtrAccountNo() {
		return dbtrAccountNo;
	}

	public void setDbtrAccountNo(String dbtrAccountNo) {
		this.dbtrAccountNo = dbtrAccountNo;
	}

	public String getDbtrAccountType() {
		return dbtrAccountType;
	}

	public void setDbtrAccountType(String dbtrAccountType) {
		this.dbtrAccountType = dbtrAccountType;
	}

	public String getCrdtId() {
		return crdtId;
	}

	public void setCrdtId(String crdtId) {
		this.crdtId = crdtId;
	}

	public String getCrdtIdType() {
		return crdtIdType;
	}

	public void setCrdtIdType(String crdtIdType) {
		this.crdtIdType = crdtIdType;
	}

	public String getCrdtAccountNo() {
		return crdtAccountNo;
	}

	public void setCrdtAccountNo(String crdtAccountNo) {
		this.crdtAccountNo = crdtAccountNo;
	}

	public String getCrdtAccountType() {
		return crdtAccountType;
	}

	public void setCrdtAccountType(String crdtAccountType) {
		this.crdtAccountType = crdtAccountType;
	}

	public String getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public String getBizMsgId() {
		return bizMsgId;
	}

	public void setBizMsgId(String bizMsgId) {
		this.bizMsgId = bizMsgId;
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

	public String getAddtInfo() {
		return addtInfo;
	}

	public void setAddtInfo(String addtInfo) {
		this.addtInfo = addtInfo;
	}

	public String getCreditorName() {
		return creditorName;
	}

	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}

	public String getCreditorType() {
		return creditorType;
	}

	public void setCreditorType(String creditorType) {
		this.creditorType = creditorType;
	}

	public String getCreditorId() {
		return creditorId;
	}

	public void setCreditorId(String creditorId) {
		this.creditorId = creditorId;
	}

	public String getCreditorResidentStatus() {
		return creditorResidentStatus;
	}

	public void setCreditorResidentStatus(String creditorResidentStatus) {
		this.creditorResidentStatus = creditorResidentStatus;
	}

	public String getCreditorTownName() {
		return creditorTownName;
	}

	public void setCreditorTownName(String creditorTownName) {
		this.creditorTownName = creditorTownName;
	}  
	
	

	
}
