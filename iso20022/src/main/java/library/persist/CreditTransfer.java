package library.persist;

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
	
	private String originatingBank;
	private String recipientBank;

	private String orignlRefId;

	private String msgType;
	private String debtorAccount;
	private String creditorAccount;
	private BigDecimal amount;

	private LocalDateTime sendDt;

	@Column(name="ACCTENQ_REQ_BISMSGID")
	private String acctEnqRequestBisMsgId;
	private String acctEnqResponseBisMsgId;
	private String acctEnqResponseStatus;

	@Column(name="CRDTTRN_REQ_BIZMSGID")
	private String crdtTrnRequestBisMsgId;
	@Column(name="CRDTTRN_RESP_BIZMSGID")
	private String crdtTrnResponseBisMsgId;
	@Column(name="CRDTTRN_RESP_STATUS")
	private String crdtTrnResponseStatus;
	
	@Column(name="SETTLCONF_BIZMSGID")
	private String SettlementBizMsgId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getOrignlRefId() {
		return orignlRefId;
	}
	public void setOrignlRefId(String orignlRefId) {
		this.orignlRefId = orignlRefId;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getDebtorAccount() {
		return debtorAccount;
	}
	public void setDebtorAccount(String debtorAccount) {
		this.debtorAccount = debtorAccount;
	}
	public String getCreditorAccount() {
		return creditorAccount;
	}
	public void setCreditorAccount(String creditorAccount) {
		this.creditorAccount = creditorAccount;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public LocalDateTime getSendDt() {
		return sendDt;
	}
	public void setSendDt(LocalDateTime sendDt) {
		this.sendDt = sendDt;
	}
	public String getAcctEnqRequestBisMsgId() {
		return acctEnqRequestBisMsgId;
	}
	public void setAcctEnqRequestBisMsgId(String acctEnqRequestBisMsgId) {
		this.acctEnqRequestBisMsgId = acctEnqRequestBisMsgId;
	}
	public String getAcctEnqResponseBisMsgId() {
		return acctEnqResponseBisMsgId;
	}
	public void setAcctEnqResponseBisMsgId(String acctEnqResponseBisMsgId) {
		this.acctEnqResponseBisMsgId = acctEnqResponseBisMsgId;
	}
	public String getAcctEnqResponseStatus() {
		return acctEnqResponseStatus;
	}
	public void setAcctEnqResponseStatus(String acctEnqResponseStatus) {
		this.acctEnqResponseStatus = acctEnqResponseStatus;
	}
	public String getCrdtTrnRequestBisMsgId() {
		return crdtTrnRequestBisMsgId;
	}
	public void setCrdtTrnRequestBisMsgId(String crdtTrnRequestBisMsgId) {
		this.crdtTrnRequestBisMsgId = crdtTrnRequestBisMsgId;
	}
	public String getCrdtTrnResponseBisMsgId() {
		return crdtTrnResponseBisMsgId;
	}
	public void setCrdtTrnResponseBisMsgId(String crdtTrnResponseBisMsgId) {
		this.crdtTrnResponseBisMsgId = crdtTrnResponseBisMsgId;
	}
	public String getCrdtTrnResponseStatus() {
		return crdtTrnResponseStatus;
	}
	public String getSettlementBizMsgId() {
		return SettlementBizMsgId;
	}
	public void setSettlementBizMsgId(String settlementBizMsgId) {
		SettlementBizMsgId = settlementBizMsgId;
	}
	public void setCrdtTrnResponseStatus(String crdtTrnResponseStatus) {
		this.crdtTrnResponseStatus = crdtTrnResponseStatus;
	}
	

	
	
}
