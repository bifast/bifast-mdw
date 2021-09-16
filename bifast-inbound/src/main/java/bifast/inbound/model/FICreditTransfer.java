package bifast.inbound.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="FI_CREDIT_TRANSFER")
public class FICreditTransfer {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="INTR_REF_ID")
	private String intrRefId;

	private Long logMessageId;

	@Column(name="ORIGN_BANK")
	private String originatingBank;
	
	private String debtorBic;
	private String creditBic;
	
	private BigDecimal amount;	
	private String status;

	private LocalDateTime creDt;

	@Column(name="REQ_BIZMSGID", length=50)
	private String requestBizMsgIdr;
	
	@Column(name="SETTLCONF_BIZMSGID", length=50)
	private String SettlementBizMsgId;
	
	@Column(length=20)
	private String saf;
	private Integer safCounter;
	
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
	public String getOriginatingBank() {
		return originatingBank;
	}
	public void setOriginatingBank(String originatingBank) {
		this.originatingBank = originatingBank;
	}
	public String getDebtorBic() {
		return debtorBic;
	}
	public void setDebtorBic(String debtorBic) {
		this.debtorBic = debtorBic;
	}
	public String getCreditBic() {
		return creditBic;
	}
	public void setCreditBic(String creditBic) {
		this.creditBic = creditBic;
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
	public String getRequestBizMsgIdr() {
		return requestBizMsgIdr;
	}
	public void setRequestBizMsgIdr(String requestBizMsgIdr) {
		this.requestBizMsgIdr = requestBizMsgIdr;
	}
	public String getSettlementBizMsgId() {
		return SettlementBizMsgId;
	}
	public void setSettlementBizMsgId(String settlementBizMsgId) {
		SettlementBizMsgId = settlementBizMsgId;
	}
	public String getSaf() {
		return saf;
	}
	public void setSaf(String saf) {
		this.saf = saf;
	}
	public Integer getSafCounter() {
		return safCounter;
	}
	public void setSafCounter(Integer safCounter) {
		this.safCounter = safCounter;
	}
	

	
	
}
