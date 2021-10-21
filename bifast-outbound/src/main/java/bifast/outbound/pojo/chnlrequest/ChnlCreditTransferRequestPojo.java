package bifast.outbound.pojo.chnlrequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("CreditTransferRequest")
public class ChnlCreditTransferRequestPojo {
	
	private String channelRefId;
	private String terminalId; 
	private String categoryPurpose; 
	private String dbtrName; 
	private String dbtrType; 
	private String dbtrId;   
	private String dbtrAccountNo;  
	private String dbtrAccountType;
	private String dbtrResidentialStatus;
	private String dbtrTownName;
	private String amount; 
	private String feeTransfer; 
	private String recptBank; 
	private String crdtName; 
	private String crdtType; 
	private String crdtId;  
	private String crdtAccountNo;   
	private String crdtAccountType;  
	private String crdtResidentialStatus;
	private String crdtTownName;
	private String crdtProxyIdValue;
	private String crdtProxyIdType;
	private String paymentInfo;
	
	@JsonCreator
	public ChnlCreditTransferRequestPojo (
			@JsonProperty(value="NoRef", required=true) String channelRefId,
			@JsonProperty(value="TerminalId", required=true) String terminalId, 
			@JsonProperty(value="CategoryPurpose", required=true) String categoryPurpose, 
			@JsonProperty(value="DebtorName", required=true) String dbtrName, 
			@JsonProperty(value="DebtorType", required=true) String dbtrType, 
			@JsonProperty(value="DebtorId", required=true) String dbtrId,   
			@JsonProperty(value="DebtorAccountNumber", required=true) String dbtrAccountNo,  
			@JsonProperty(value="DebtorAccountType", required=true) String dbtrAccountType,
			@JsonProperty(value="DebtorResidentialStatus", required=true) String dbtrResidentialStatus,
			@JsonProperty(value="DebtorTownName", required=true) String dbtrTownName,
			@JsonProperty(value="Amount", required=true) String amount, 
			@JsonProperty(value="FeeTransfer") String feeTransfer, 
			@JsonProperty(value="RecipientBank", required=true) String recptBank, 
			@JsonProperty(value="CreditorName", required=true) String crdtName, 
			@JsonProperty(value="CreditorType", required=true) String crdtType, 
			@JsonProperty(value="CreditorId", required=true) String crdtId,  
			@JsonProperty(value="CreditorAccountNumber", required=true) String crdtAccountNo,   
			@JsonProperty(value="CreditorAccountType", required=true) String crdtAccountType,  
			@JsonProperty(value="CreditorResidentialStatus", required=true) String crdtResidentialStatus,
			@JsonProperty(value="CreditorTownName", required=true) String crdtTownName,
			@JsonProperty(value="CreditorProxyId") String crdtProxyIdValue,
			@JsonProperty(value="CreditorProxyType") String crdtProxyIdType,
			@JsonProperty(value="PaymentInformation") String paymentInfo
			) 
	{
		this.channelRefId = channelRefId;
		this.terminalId = terminalId;
		this.categoryPurpose = categoryPurpose;
		this.dbtrName = dbtrName;
		this.dbtrType = dbtrType;
		this.dbtrId = dbtrId;
		this.dbtrAccountNo = dbtrAccountNo;
		this.dbtrAccountType = dbtrAccountType;
		this.dbtrResidentialStatus = dbtrResidentialStatus;
		this.dbtrTownName = dbtrTownName;
		this.amount = amount;
		this.feeTransfer = feeTransfer;
		this.recptBank = recptBank;
		this.crdtName = crdtName;
		this.crdtType = crdtType;
		this.crdtId = crdtId;
		this.crdtAccountNo = crdtAccountNo;
		this.crdtAccountType =  crdtAccountType;
		this.crdtResidentialStatus = crdtResidentialStatus;
		this.crdtTownName = crdtTownName;
		this.crdtProxyIdValue = crdtProxyIdValue;
		this.crdtProxyIdType = crdtProxyIdType;
		this.paymentInfo = paymentInfo;
	}
	

	public String getChannelRefId() {
		return channelRefId;
	}

	public void setChannelRefId(String channelRefId) {
		this.channelRefId = channelRefId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getCategoryPurpose() {
		return categoryPurpose;
	}

	public void setCategoryPurpose(String categoryPurpose) {
		this.categoryPurpose = categoryPurpose;
	}

	public String getDbtrName() {
		return dbtrName;
	}

	public void setDbtrName(String dbtrName) {
		this.dbtrName = dbtrName;
	}

	public String getDbtrType() {
		return dbtrType;
	}

	public void setDbtrType(String dbtrType) {
		this.dbtrType = dbtrType;
	}

	public String getDbtrId() {
		return dbtrId;
	}

	public void setDbtrId(String dbtrId) {
		this.dbtrId = dbtrId;
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

	public String getDbtrResidentialStatus() {
		return dbtrResidentialStatus;
	}

	public void setDbtrResidentialStatus(String dbtrResidentialStatus) {
		this.dbtrResidentialStatus = dbtrResidentialStatus;
	}

	public String getDbtrTownName() {
		return dbtrTownName;
	}

	public void setDbtrTownName(String dbtrTownName) {
		this.dbtrTownName = dbtrTownName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFeeTransfer() {
		return feeTransfer;
	}

	public void setFeeTransfer(String feeTransfer) {
		this.feeTransfer = feeTransfer;
	}

	public String getRecptBank() {
		return recptBank;
	}

	public void setRecptBank(String recptBank) {
		this.recptBank = recptBank;
	}

	public String getCrdtName() {
		return crdtName;
	}

	public void setCrdtName(String crdtName) {
		this.crdtName = crdtName;
	}

	public String getCrdtType() {
		return crdtType;
	}

	public void setCrdtType(String crdtType) {
		this.crdtType = crdtType;
	}

	public String getCrdtId() {
		return crdtId;
	}

	public void setCrdtId(String crdtId) {
		this.crdtId = crdtId;
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

	public String getCrdtResidentialStatus() {
		return crdtResidentialStatus;
	}

	public void setCrdtResidentialStatus(String crdtResidentialStatus) {
		this.crdtResidentialStatus = crdtResidentialStatus;
	}

	public String getCrdtTownName() {
		return crdtTownName;
	}

	public void setCrdtTownName(String crdtTownName) {
		this.crdtTownName = crdtTownName;
	}

	public String getCrdtProxyIdValue() {
		return crdtProxyIdValue;
	}

	public void setCrdtProxyIdValue(String crdtProxyIdValue) {
		this.crdtProxyIdValue = crdtProxyIdValue;
	}

	public String getCrdtProxyIdType() {
		return crdtProxyIdType;
	}

	public void setCrdtProxyIdType(String crdtProxyIdType) {
		this.crdtProxyIdType = crdtProxyIdType;
	}

	public String getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}  


	
}
