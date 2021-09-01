package bifast.mock.iso20022.service;

import java.math.BigDecimal;

public class Pacs008Seed {

	private String trnType;   // AcctEnq & CrdtTrn
	private String bizMsgId;  // AcctEnq CrdtTrn
	private BigDecimal amount; // AcctEnq CrdtTrn
	private String orignBank; //  AcctEnq  CrdtTrn
	private String recptBank; //  AcctEnq  CrdtTrn
	private String channel; //    CrdtTrn
	private String categoryPurpose; //  CrdtTrn
	
	private String dbtrId;   
	private String dbtrName;
	private String dbtrIdType;  
	private String dbtrAccountNo;  
	private String dbtrAccountType; 
	
	private String crdtId;  // CrdtTrn
	private String crdtName;
	private String crdtIdType; // CrdtTrn
	private String crdtAccountNo;   // AcctEnq CrdtTrn
	private String crdtAccountType;  // CrdtTrn
	
	private String crdtProxyIdType;
	private String crdtProxyIdValue;

	private String paymentInfo;  // CrdtTrn
	
	public Pacs008Seed () {}
	
	public String getTrnType() {
		return trnType;
	}
	public void setTrnType(String trnType) {
		this.trnType = trnType;
	}
	public String getBizMsgId() {
		return bizMsgId;
	}
	public void setBizMsgId(String bizMsgId) {
		this.bizMsgId = bizMsgId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getOrignBank() {
		return orignBank;
	}
	public void setOrignBank(String orignBank) {
		this.orignBank = orignBank;
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
