package bifast.outbound.reversect;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ReverseCreditTransferRequest")
public class ChnlReverseCTRequestPojo {

	private String intrnRefId;
	private String orgnlEndToEndId;
	private BigDecimal amount; 
	private String categoryPurpose; 
	private String recptBank; 
	
	private String dbtrId;   // CrdtTrn
	private String dbtrName;  //optional
	private String dbtrIdType;  // CrdtTrn
	private String dbtrAccountNo;  // CrdtTrn
	private String dbtrAccountType; // CrdtTrn
	
	private String crdtId;  // CrdtTrn
	private String crdtName; //optional
	private String crdtIdType; // CrdtTrn
	private String crdtAccountNo;   // AcctEnq CrdtTrn
	private String crdtAccountType;  // CrdtTrn
	
	private String crdtProxyIdType;
	private String crdtProxyIdValue;
	
	private String paymentInfo;  

	public String getIntrnRefId() {
		return intrnRefId;
	}
	public void setIntrnRefId(String intrnRefId) {
		this.intrnRefId = intrnRefId;
	}
	public String getOrgnlEndToEndId() {
		return orgnlEndToEndId;
	}
	public void setOrgnlEndToEndId(String orgnlEndToEndId) {
		this.orgnlEndToEndId = orgnlEndToEndId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getCategoryPurpose() {
		return categoryPurpose;
	}
	public void setCategoryPurpose(String categoryPurpose) {
		this.categoryPurpose = categoryPurpose;
	}
	public String getRecptBank() {
		return recptBank;
	}
	public void setRecptBank(String recptBank) {
		this.recptBank = recptBank;
	}
	public String getDbtrId() {
		return dbtrId;
	}
	public void setDbtrId(String dbtrId) {
		this.dbtrId = dbtrId;
	}
	public String getDbtrName() {
		return dbtrName;
	}
	public void setDbtrName(String dbtrName) {
		this.dbtrName = dbtrName;
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
	public String getCrdtName() {
		return crdtName;
	}
	public void setCrdtName(String crdtName) {
		this.crdtName = crdtName;
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


	
}
