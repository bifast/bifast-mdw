package bifast.outbound.model;

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

//	private Long logMessageId;

	private String msgType;
	
	@Column(name="ORIGN_BANK")
	private String originatingBank;
	@Column(name="RECPT_BANK")
	private String recipientBank;
	
	@Column(name="DEBTOR_ACCT_NO")
	private String debtorAccountNumber;
	@Column(name="DEBTOR_ACCT_TYPE")
	private String debtorAccountType;

	private String debtorId;
	private String debtorType;
	
	@Column(name="CREDITOR_ACCT_NO")
	private String creditorAccountNumber;
	@Column(name="CREDITOR_ACCT_TYPE")
	private String creditorAccountType;
	@Column(name="CREDITOR_ID")
	private String creditorId;
	private String creditorType;
	
	private BigDecimal amount;	
	
//	@Column(length=20)
//	private String status;

	private LocalDateTime creDt;

	@Column(name="CRDTTRN_REQ_BIZMSGID")
	private String crdtTrnRequestBizMsgIdr;

	@Column(name="CRDTTRN_RESP_BIZMSGID")
	private String crdtTrnResponseBizMsgIdr;

	@Column(name="SETTLCONF_BIZMSGID")
	private String SettlementBizMsgId;
	
	@Column(name="CIHUB_REQ_TIME")
	private LocalDateTime cihubRequestDT;
	
	@Column(name="CIHUB_RESP_TIME")
	private LocalDateTime cihubResponseDT;	
	
	@Column(name="FULL_REQUEST_MSG", length=4000)
	private String fullRequestMessage;

	@Column(name="FULL_RESPONSE_MSG", length=4000)
	private String fullResponseMsg;

	private String reversal;

	@Column(length=20)
	private String callStatus;
	@Column(name="RESP_STATUS", length=20)
	private String responseStatus;
	
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
	public String getDebtorAccountType() {
		return debtorAccountType;
	}
	public void setDebtorAccountType(String debtorAccountType) {
		this.debtorAccountType = debtorAccountType;
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
	public String getCreditorAccountType() {
		return creditorAccountType;
	}
	public void setCreditorAccountType(String creditorAccountType) {
		this.creditorAccountType = creditorAccountType;
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
	public String getCrdtTrnResponseBizMsgIdr() {
		return crdtTrnResponseBizMsgIdr;
	}
	public void setCrdtTrnResponseBizMsgIdr(String crdtTrnResponseBizMsgIdr) {
		this.crdtTrnResponseBizMsgIdr = crdtTrnResponseBizMsgIdr;
	}
	public String getSettlementBizMsgId() {
		return SettlementBizMsgId;
	}
	public void setSettlementBizMsgId(String settlementBizMsgId) {
		SettlementBizMsgId = settlementBizMsgId;
	}
	public LocalDateTime getCihubRequestDT() {
		return cihubRequestDT;
	}
	public void setCihubRequestDT(LocalDateTime cihubRequestDT) {
		this.cihubRequestDT = cihubRequestDT;
	}
	public LocalDateTime getCihubResponseDT() {
		return cihubResponseDT;
	}
	public void setCihubResponseDT(LocalDateTime cihubResponseDT) {
		this.cihubResponseDT = cihubResponseDT;
	}
	public String getFullRequestMessage() {
		return fullRequestMessage;
	}
	public void setFullRequestMessage(String fullRequestMessage) {
		this.fullRequestMessage = fullRequestMessage;
	}
	public String getFullResponseMsg() {
		return fullResponseMsg;
	}
	public void setFullResponseMsg(String fullResponseMsg) {
		this.fullResponseMsg = fullResponseMsg;
	}
	public String getReversal() {
		return reversal;
	}
	public void setReversal(String reversal) {
		this.reversal = reversal;
	}
	public String getCallStatus() {
		return callStatus;
	}
	public void setCallStatus(String callStatus) {
		this.callStatus = callStatus;
	}
	public String getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	
	
	

}
