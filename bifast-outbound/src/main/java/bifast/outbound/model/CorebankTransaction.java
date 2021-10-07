package bifast.outbound.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class CorebankTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
	@SequenceGenerator(name="seq_generator", sequenceName = "table_seq_generator", allocationSize=1)
	private Long transactionId;
	
	private String channelRefId;
	
	private String transactionType;
	private Long chnlTrxId;

	private BigDecimal debitAmount;
	private BigDecimal creditAmount;
	
	private String cstmAccountNo;
	private String cstmAccountType;
	private String cstmAccountName;

	private String debtorBank;
	private String creditorBank;
	
	private LocalDateTime trnsDt;
	private String status;
	private String addtInfo;
	
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public String getChannelRefId() {
		return channelRefId;
	}
	public void setChannelRefId(String channelRefId) {
		this.channelRefId = channelRefId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Long getChnlTrxId() {
		return chnlTrxId;
	}
	public void setChnlTrxId(Long chnlTrxId) {
		this.chnlTrxId = chnlTrxId;
	}
	public BigDecimal getDebitAmount() {
		return debitAmount;
	}
	public void setDebitAmount(BigDecimal debitAmount) {
		this.debitAmount = debitAmount;
	}
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
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
	public String getCstmAccountName() {
		return cstmAccountName;
	}
	public void setCstmAccountName(String cstmAccountName) {
		this.cstmAccountName = cstmAccountName;
	}
	public String getDebtorBank() {
		return debtorBank;
	}
	public void setDebtorBank(String debtorBank) {
		this.debtorBank = debtorBank;
	}
	public String getCreditorBank() {
		return creditorBank;
	}
	public void setCreditorBank(String creditorBank) {
		this.creditorBank = creditorBank;
	}
	public LocalDateTime getTrnsDt() {
		return trnsDt;
	}
	public void setTrnsDt(LocalDateTime trnsDt) {
		this.trnsDt = trnsDt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAddtInfo() {
		return addtInfo;
	}
	public void setAddtInfo(String addtInfo) {
		this.addtInfo = addtInfo;
	}
	
	
}
