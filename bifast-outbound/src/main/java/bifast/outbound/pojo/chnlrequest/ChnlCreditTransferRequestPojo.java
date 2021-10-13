package bifast.outbound.pojo.chnlrequest;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("CreditTransferRequest")
public class ChnlCreditTransferRequestPojo extends ChannelRequestBase {
	
	@JsonProperty("CategoryPurpose")
	private String categoryPurpose; 

	@JsonProperty("DebtorId")
	private String dbtrId;   
	@JsonProperty("DebtorIdType")
	private String dbtrIdType; 
	
	@JsonProperty("DebtorAccountNumber")
	private String dbtrAccountNo;  
	@JsonProperty("DebtorAccountType")
	private String dbtrAccountType;

	@JsonProperty("Amount")
	private BigDecimal amount; // AcctEnq CrdtTrn
	
	@JsonProperty("FeeTransfer")
	private BigDecimal feeTransfer; // AcctEnq CrdtTrn

	@JsonProperty("RecipientBank")
	private String recptBank; 
	
	@JsonProperty("CreditorId")
	private String crdtId;  // CrdtTrn
	@JsonProperty("CreditorType")
	private String crdtIdType; // CrdtTrn
	@JsonProperty("CreditorAccountNumber")
	private String crdtAccountNo;   // AcctEnq CrdtTrn
	@JsonProperty("CreditorAccountType")
	private String crdtAccountType;  // CrdtTrn
//	private String crdtName; //optional
	
	@JsonProperty("CreditorProxyType")
	private String crdtProxyIdType;
	@JsonProperty("CreditorProxyId")
	private String crdtProxyIdValue;
	
	@JsonProperty("PaymentInformation")
	private String paymentInfo;  // CrdtTrn

//	private String orgnlEndToEndId;

	public ChnlCreditTransferRequestPojo() {}

//	public String getOrignReffId() {
//		return orignReffId;
//	}
//
//	public void setOrignReffId(String orignReffId) {
//		this.orignReffId = orignReffId;
//	}
//
//	public String getChannel() {
//		return channel;
//	}
//
//	public void setChannel(String channel) {
//		this.channel = channel;
//	}

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getFeeTransfer() {
		return feeTransfer;
	}

	public void setFeeTransfer(BigDecimal feeTransfer) {
		this.feeTransfer = feeTransfer;
	}

	public String getRecptBank() {
		return recptBank;
	}

	public void setRecptBank(String recptBank) {
		this.recptBank = recptBank;
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

	public String getCrdtProxyIdType() {
		return crdtProxyIdType;
	}

	public void setCrdtProxyIdType(String crdtProxyIdType) {
		this.crdtProxyIdType = crdtProxyIdType;
	}

	public String getCrdtProxyIdValue() {
		return crdtProxyIdValue;
	}

	public void setCrdtProxyIdValue(String crdtProxyIdValue) {
		this.crdtProxyIdValue = crdtProxyIdValue;
	}

	public String getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

//	public String getOrgnlEndToEndId() {
//		return orgnlEndToEndId;
//	}
//
//	public void setOrgnlEndToEndId(String orgnlEndToEndId) {
//		this.orgnlEndToEndId = orgnlEndToEndId;
//	}
//
	
}
