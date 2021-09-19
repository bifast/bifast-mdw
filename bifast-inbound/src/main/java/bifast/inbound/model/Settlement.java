package bifast.inbound.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="SETTLEMENT")
public class Settlement {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Long logMessageId;
	@Column(length=20)
	private String orignBank;
	@Column(length=20)
	private String recptBank;
	
	@Column(name="SETTL_CONF_BIZMSGID", length=50)
	private String settlConfBizMsgId;
	@Column(name="ORGNL_CRDT_TRN_BIZMSGID", length=50)
	private String orgnlCrdtTrnReqBizMsgId;

	@Column(length=100)
	private String crdtAccountNo;
	@Column(length=100)
	private String dbtrAccountNo;
	
	@Column(length=100)
	private String crdtBankAccountNo;
	@Column(length=100)
	private String dbtrBankAccountNo;
	
	private LocalDateTime receiveDate;
	
	@Column(length=5000)
	private String fullMessage;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLogMessageId() {
		return logMessageId;
	}
	public void setLogMessageId(Long logMessageId) {
		this.logMessageId = logMessageId;
	}
	public String getOrignBank() {
		return orignBank;
	}
	public void setOrignBank(String orignBank) {
		this.orignBank = orignBank;
	}
	public String getRecptBank() {
		return recptBank;
	}
	public void setRecptBank(String recptBank) {
		this.recptBank = recptBank;
	}
	public String getSettlConfBizMsgId() {
		return settlConfBizMsgId;
	}
	public void setSettlConfBizMsgId(String settlConfBizMsgId) {
		this.settlConfBizMsgId = settlConfBizMsgId;
	}
	public String getOrgnlCrdtTrnReqBizMsgId() {
		return orgnlCrdtTrnReqBizMsgId;
	}
	public void setOrgnlCrdtTrnReqBizMsgId(String orgnlCrdtTrnReqBizMsgId) {
		this.orgnlCrdtTrnReqBizMsgId = orgnlCrdtTrnReqBizMsgId;
	}
	public String getCrdtAccountNo() {
		return crdtAccountNo;
	}
	public void setCrdtAccountNo(String crdtAccountNo) {
		this.crdtAccountNo = crdtAccountNo;
	}
	public String getDbtrAccountNo() {
		return dbtrAccountNo;
	}
	public void setDbtrAccountNo(String dbtrAccountNo) {
		this.dbtrAccountNo = dbtrAccountNo;
	}
	public String getCrdtBankAccountNo() {
		return crdtBankAccountNo;
	}
	public void setCrdtBankAccountNo(String crdtBankAccountNo) {
		this.crdtBankAccountNo = crdtBankAccountNo;
	}
	public String getDbtrBankAccountNo() {
		return dbtrBankAccountNo;
	}
	public void setDbtrBankAccountNo(String dbtrBankAccountNo) {
		this.dbtrBankAccountNo = dbtrBankAccountNo;
	}
	public LocalDateTime getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(LocalDateTime receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getFullMessage() {
		return fullMessage;
	}
	public void setFullMessage(String fullMessage) {
		this.fullMessage = fullMessage;
	}

	
}
