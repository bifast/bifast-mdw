package bifast.outbound.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name="KC_CREDIT_TRANSFER")
public class CreditTransfer {

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
	@SequenceGenerator(name="seq_generator", sequenceName = "table_seq_generator", allocationSize=1)
	private Long id;
	
	@Column(length=50)
	private String komiTrnsId;
	
	@Column(length=50)
	private String msgType;
	
	@Column(name="ORIGN_BANK", length=10)
	private String originatingBank;
	@Column(name="RECPT_BANK", length=10)
	private String recipientBank;
	
	@Column(name="DEBTOR_ACCT_NO", length=50)
	private String debtorAccountNumber;
	@Column(name="DEBTOR_ACCT_TYPE", length=10)
	private String debtorAccountType;

	@Column(length=50)
	private String debtorId;
	@Column(length=10)
	private String debtorType;
	
	@Column(name="CREDITOR_ACCT_NO", length=50)
	private String creditorAccountNumber;
	@Column(name="CREDITOR_ACCT_TYPE", length=10)
	private String creditorAccountType;
	@Column(name="CREDITOR_ID", length=50)
	private String creditorId;
	@Column(length=10)
	private String creditorType;
	
	private BigDecimal amount;	
	
	@Column(name="REQ_BIZMSGID")
	private String crdtTrnRequestBizMsgIdr;

	@Column(name="RESP_BIZMSGID", length=50)
	private String crdtTrnResponseBizMsgIdr;

	@Column(name="STTL_BIZMSGID", length=50)
	private String SettlementBizMsgId;
	
	@Column(name="CIHUB_REQ_TIME")
	private LocalDateTime cihubRequestDT;
	
	private Long cihubElapsedTime;
	
	@Column(name="FULL_REQUEST_MSG", length=4000)
	private String fullRequestMessage;

	@Column(name="FULL_RESPONSE_MSG", length=4000)
	private String fullResponseMsg;
	
	@Column(length=400)
	private String errorMessage;
	
	private Integer ps_counter;
		
	private String reversal;

	@Column(length=20)
	private String callStatus;
	
	@Column(name="RESP_STATUS", length=20)
	private String responseStatus;
	
	private LocalDateTime createDt;
	private LocalDateTime lastUpdateDt;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKomiTrnsId() {
		return komiTrnsId;
	}
	public void setKomiTrnsId(String komiTrnsId) {
		this.komiTrnsId = komiTrnsId;
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
	public Long getCihubElapsedTime() {
		return cihubElapsedTime;
	}
	public void setCihubElapsedTime(Long cihubElapsedTime) {
		this.cihubElapsedTime = cihubElapsedTime;
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
	public Integer getPs_counter() {
		return ps_counter;
	}
	public void setPs_counter(Integer ps_counter) {
		this.ps_counter = ps_counter;
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
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public LocalDateTime getCreateDt() {
		return createDt;
	}
	public void setCreateDt(LocalDateTime createDt) {
		this.createDt = createDt;
	}
	public LocalDateTime getLastUpdateDt() {
		return lastUpdateDt;
	}
	public void setLastUpdateDt(LocalDateTime lastUpdateDt) {
		this.lastUpdateDt = lastUpdateDt;
	}

	
}
