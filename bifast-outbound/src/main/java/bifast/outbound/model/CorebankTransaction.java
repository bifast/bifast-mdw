package bifast.outbound.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name="KC_COREBANK_TRANSACTION")
public class CorebankTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
	@SequenceGenerator(name="seq_generator", sequenceName = "table_seq_generator", allocationSize=1)
	private Long id;

	@Column(length=50)
	private String komiTrnsId;
	
	@Column(length=8)
	private String trxDate;
	@Column(length=50)
	private String dateTime;
	
	@Column(length=10)
	private String transactionType;   //  AE, DEB, CRD, REV, STTL

	@Column(length=20)
	private String channelId;
	@Column(length=50)
	private String channelNoref;
	@Column(length=50)
	private String komiNoref;

	private BigDecimal debitAmount;
	private BigDecimal creditAmount;
	private BigDecimal feeAmount;
	
	@Column(length=50)
	private String cstmAccountNo;
	@Column(length=10)
	private String cstmAccountType;
	@Column(length=140)
	private String cstmAccountName;

	@Column(length=10)
	private String debtorBank;
	@Column(length=10)
	private String creditorBank;

	private Integer orgnlChnlNoref;
	@Column(length=50)
	private String orgnlDateTime;

	private LocalDateTime trnsDt;
	@Column(length=20)
	private String status;
	@Column(length=140)
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
	public String getTrxDate() {
		return trxDate;
	}
	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
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
	public String getChannelNoref() {
		return channelNoref;
	}
	public void setChannelNoref(String channelNoref) {
		this.channelNoref = channelNoref;
	}
	public String getKomiNoref() {
		return komiNoref;
	}
	public void setKomiNoref(String komiNoref) {
		this.komiNoref = komiNoref;
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
	public Integer getOrgnlChnlNoref() {
		return orgnlChnlNoref;
	}
	public void setOrgnlChnlNoref(Integer orgnlChnlNoref) {
		this.orgnlChnlNoref = orgnlChnlNoref;
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
