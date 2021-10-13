package bifast.inbound.model;

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
	private Long id;

	private String komiTrnsId;
	
	private Integer cbTrnsId;
	private String dateTime;
	
	private String transactionType;   //  AE, DEB, CRD, REV, PRX, STTL

	private String channelId;
	
	private BigDecimal debitAmount;
	private BigDecimal creditAmount;
	private BigDecimal feeAmount;
	
	private String cstmAccountNo;
	private String cstmAccountType;
	private String cstmAccountName;

	private String debtorBank;
	private String creditorBank;

	private Integer orgnlCbTrnsId;
	private String orgnlDateTime;

	private LocalDateTime trnsDt;
	private String status;
	private String addtInfo;
	
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

	public Integer getCbTrnsId() {
		return cbTrnsId;
	}

	public void setCbTrnsId(Integer cbTrnsId) {
		this.cbTrnsId = cbTrnsId;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
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

	public BigDecimal getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
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

	public Integer getOrgnlCbTrnsId() {
		return orgnlCbTrnsId;
	}

	public void setOrgnlCbTrnsId(Integer orgnlCbTrnsId) {
		this.orgnlCbTrnsId = orgnlCbTrnsId;
	}

	public String getOrgnlDateTime() {
		return orgnlDateTime;
	}

	public void setOrgnlDateTime(String orgnlDateTime) {
		this.orgnlDateTime = orgnlDateTime;
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
