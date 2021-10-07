package bifast.outbound.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class ChannelTransaction {

	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
	@SequenceGenerator(name="seq1_generator", sequenceName = "table1_seq_generator", allocationSize=1)
	private Long id;
	@Column(length=16)
	private String transactionId;

	@Column(length=16)
	private String komiTrnsId;
	
	@Column(length=50)
	private String channelId;
	@Column(length=100)
	private String msgName;
	
	private BigDecimal amount;

	@Column(length=35)
	private String debtorAccountNumber;
	private String debtorAccountName;
	@Column(length=8)
	private String recptBank;
	@Column(length=35)
	private String creditorAccountNumber;
	private String creditorAccountName;
	private LocalDateTime requestTime;
	private LocalDateTime responseTime;
	@Column(length=12)
	private String status;
	@Column(length=250)
	private String errorMsg;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getMsgName() {
		return msgName;
	}
	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}
	public String getKomiTrnsId() {
		return komiTrnsId;
	}
	public void setKomiTrnsId(String komiTrnsId) {
		this.komiTrnsId = komiTrnsId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getDebtorAccountNumber() {
		return debtorAccountNumber;
	}
	public void setDebtorAccountNumber(String debtorAccountNumber) {
		this.debtorAccountNumber = debtorAccountNumber;
	}
	public String getDebtorAccountName() {
		return debtorAccountName;
	}
	public void setDebtorAccountName(String debtorAccountName) {
		this.debtorAccountName = debtorAccountName;
	}
	public String getRecptBank() {
		return recptBank;
	}
	public void setRecptBank(String recptBank) {
		this.recptBank = recptBank;
	}
	public String getCreditorAccountNumber() {
		return creditorAccountNumber;
	}
	public void setCreditorAccountNumber(String creditorAccountNumber) {
		this.creditorAccountNumber = creditorAccountNumber;
	}
	public String getCreditorAccountName() {
		return creditorAccountName;
	}
	public void setCreditorAccountName(String creditorAccountName) {
		this.creditorAccountName = creditorAccountName;
	}
	public LocalDateTime getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(LocalDateTime requestTime) {
		this.requestTime = requestTime;
	}
	public LocalDateTime getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(LocalDateTime responseTime) {
		this.responseTime = responseTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
	
}
