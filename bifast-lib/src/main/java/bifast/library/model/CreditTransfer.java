package bifast.library.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="CREDIT_TRANSFER")
public class CreditTransfer {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="INTR_REF_ID")
	private String intrRefId;

	private Long logMessageId;
	private String msgType;
	
	@Column(name="ORIGN_BANK")
	private String originatingBank;
	@Column(name="RECPT_BANK")
	private String recipientBank;
	
	@Column(name="DEBTOR_ACCT_NO")
	private String debtorAccountNumber;
	private String debtorId;
	private String debtorType;
	
	@Column(name="CREDITOR_ACCT_NO")
	private String creditorAccountNumber;
	@Column(name="CREDITOR_ID")
	private String creditorId;
	private String creditorType;
	
	private BigDecimal amount;	
	private String status;

	private LocalDateTime creDt;

	@Column(name="CRDTTRN_REQ_BIZMSGID")
	private String crdtTrnRequestBizMsgIdr;
	
	@Column(name="SETTLCONF_BIZMSGID")
	private String SettlementBizMsgId;
	
	private String reversal;
	
	public CreditTransfer() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIntrRefId() {
		return intrRefId;
	}

	public void setIntrRefId(String intrRefId) {
		this.intrRefId = intrRefId;
	}

	public Long getLogMessageId() {
		return logMessageId;
	}

	public void setLogMessageId(Long logMessageId) {
		this.logMessageId = logMessageId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getOriginatingBank() {
		return originatingBank;
	}

	public void setOriginatingBank(String originatingBank) {
		this.originatingBank = originatingBank;
	}

	public String getRecipientBank() {
		return recipientBank;
	}

	public void setRecipientBank(String recipientBank) {
		this.recipientBank = recipientBank;
	}

	public String getDebtorAccountNumber() {
		return debtorAccountNumber;
	}

	public void setDebtorAccountNumber(String debtorAccountNumber) {
		this.debtorAccountNumber = debtorAccountNumber;
	}

	public String getDebtorId() {
		return debtorId;
	}

	public void setDebtorId(String debtorId) {
		this.debtorId = debtorId;
	}

	public String getDebtorType() {
		return debtorType;
	}

	public void setDebtorType(String debtorType) {
		this.debtorType = debtorType;
	}

	public String getCreditorAccountNumber() {
		return creditorAccountNumber;
	}

	public void setCreditorAccountNumber(String creditorAccountNumber) {
		this.creditorAccountNumber = creditorAccountNumber;
	}

	public String getCreditorId() {
		return creditorId;
	}

	public void setCreditorId(String creditorId) {
		this.creditorId = creditorId;
	}

	public String getCreditorType() {
		return creditorType;
	}

	public void setCreditorType(String creditorType) {
		this.creditorType = creditorType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreDt() {
		return creDt;
	}

	public void setCreDt(LocalDateTime creDt) {
		this.creDt = creDt;
	}

	public String getCrdtTrnRequestBizMsgIdr() {
		return crdtTrnRequestBizMsgIdr;
	}

	public void setCrdtTrnRequestBizMsgIdr(String crdtTrnRequestBizMsgIdr) {
		this.crdtTrnRequestBizMsgIdr = crdtTrnRequestBizMsgIdr;
	}

	public String getSettlementBizMsgId() {
		return SettlementBizMsgId;
	}

	public void setSettlementBizMsgId(String settlementBizMsgId) {
		SettlementBizMsgId = settlementBizMsgId;
	}

	public String getReversal() {
		return reversal;
	}

	public void setReversal(String reversal) {
		this.reversal = reversal;
	}

	
}
