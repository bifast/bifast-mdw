package komi.control.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="KC_COREBANK_TRANSACTION")
public class CorebankTransaction {

	@Id
	private Long id;
	
	private BigDecimal creditAmount;
	private BigDecimal debitAmount;
	private BigDecimal feeAmount;
	
	private String cstmAccountName;
	private String cstmAccountNo;
	private String cstmAccountType;
	private String dateTime;
	private String komiNoref;
	private String komiTrnsId;
	private String orgnlChnlNoref;
	private String orgnlDateTime;
	private String reason;
	private String response;
	private Integer retryCounter;
	private String transactionType;
	private String trnsDate;
	private LocalDateTime updateTime;
	private String fullTextRequest;
	private String settlBizmsgidr;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}
	public BigDecimal getDebitAmount() {
		return debitAmount;
	}
	public void setDebitAmount(BigDecimal debitAmount) {
		this.debitAmount = debitAmount;
	}
	public BigDecimal getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}
	public String getCstmAccountName() {
		return cstmAccountName;
	}
	public void setCstmAccountName(String cstmAccountName) {
		this.cstmAccountName = cstmAccountName;
	}
	public String getCstmAccountNo() {
		return cstmAccountNo;
	}
	public void setCstmAccountNo(String cstmAccountNo) {
		this.cstmAccountNo = cstmAccountNo;
	}
	public String getCstmAccountType() {
		return cstmAccountType;
	}
	public void setCstmAccountType(String cstmAccountType) {
		this.cstmAccountType = cstmAccountType;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getKomiNoref() {
		return komiNoref;
	}
	public void setKomiNoref(String komiNoref) {
		this.komiNoref = komiNoref;
	}
	public String getKomiTrnsId() {
		return komiTrnsId;
	}
	public void setKomiTrnsId(String komiTrnsId) {
		this.komiTrnsId = komiTrnsId;
	}
	public String getOrgnlChnlNoref() {
		return orgnlChnlNoref;
	}
	public void setOrgnlChnlNoref(String orgnlChnlNoref) {
		this.orgnlChnlNoref = orgnlChnlNoref;
	}
	public String getOrgnlDateTime() {
		return orgnlDateTime;
	}
	public void setOrgnlDateTime(String orgnlDateTime) {
		this.orgnlDateTime = orgnlDateTime;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Integer getRetryCounter() {
		return retryCounter;
	}
	public void setRetryCounter(Integer retryCounter) {
		this.retryCounter = retryCounter;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getTrnsDate() {
		return trnsDate;
	}
	public void setTrnsDate(String trnsDate) {
		this.trnsDate = trnsDate;
	}
	public LocalDateTime getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}
	public String getFullTextRequest() {
		return fullTextRequest;
	}
	public void setFullTextRequest(String fullTextRequest) {
		this.fullTextRequest = fullTextRequest;
	}
	public String getSettlBizmsgidr() {
		return settlBizmsgidr;
	}
	public void setSettlBizmsgidr(String settlBizmsgidr) {
		this.settlBizmsgidr = settlBizmsgidr;
	}
	
	
}
