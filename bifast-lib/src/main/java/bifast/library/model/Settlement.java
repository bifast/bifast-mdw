package bifast.library.model;

import java.math.BigDecimal;

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
	
	private String orignBank;
	private String recptBank;
	
	@Column(name="SETTL_CONF_BIZMSGID")
	private String settlConfBizMsgId;
	@Column(name="ORGNL_CRDT_TRN_BIZMSGID")
	private String orgnlCrdtTrnReqBizMsgId;
	@Column(name="REVERSAL_BIZMSGID")
	private String reversalBizMsgId;

	private String crdtAccountNo;
	private String crdtAccountType;
	private String crdtName;
	private String crdtId;
	private String crdtIdType;
	private String dbtrAccountNo;
	private String dbtrAccountType;
	private String dbtrName;
	private String dbtrId;
	private String dbtrIdType;
	private BigDecimal amount;
	
	
	private String ack;
	private String forReversal;

	@Column(name="FULL_MESG", length=6000)
	private String fullMessage;

	public Settlement () {}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getReversalBizMsgId() {
		return reversalBizMsgId;
	}

	public void setReversalBizMsgId(String reversalBizMsgId) {
		this.reversalBizMsgId = reversalBizMsgId;
	}

	public String getCrdtAccountNo() {
		return crdtAccountNo;
	}

	public void setCrdtAccountNo(String crdtAccountNo) {
		this.crdtAccountNo = crdtAccountNo;
	}

	public String getCrdtAccountType() {
		return crdtAccountType;
	}

	public void setCrdtAccountType(String crdtAccountType) {
		this.crdtAccountType = crdtAccountType;
	}

	public String getCrdtName() {
		return crdtName;
	}

	public void setCrdtName(String crdtName) {
		this.crdtName = crdtName;
	}

	public String getCrdtId() {
		return crdtId;
	}

	public void setCrdtId(String crdtId) {
		this.crdtId = crdtId;
	}

	public String getCrdtIdType() {
		return crdtIdType;
	}

	public void setCrdtIdType(String crdtIdType) {
		this.crdtIdType = crdtIdType;
	}

	public String getDbtrAccountNo() {
		return dbtrAccountNo;
	}

	public void setDbtrAccountNo(String dbtrAccountNo) {
		this.dbtrAccountNo = dbtrAccountNo;
	}

	public String getDbtrAccountType() {
		return dbtrAccountType;
	}

	public void setDbtrAccountType(String dbtrAccountType) {
		this.dbtrAccountType = dbtrAccountType;
	}

	public String getDbtrName() {
		return dbtrName;
	}

	public void setDbtrName(String dbtrName) {
		this.dbtrName = dbtrName;
	}

	public String getDbtrId() {
		return dbtrId;
	}

	public void setDbtrId(String dbtrId) {
		this.dbtrId = dbtrId;
	}

	public String getDbtrIdType() {
		return dbtrIdType;
	}

	public void setDbtrIdType(String dbtrIdType) {
		this.dbtrIdType = dbtrIdType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getAck() {
		return ack;
	}

	public void setAck(String ack) {
		this.ack = ack;
	}

	public String getForReversal() {
		return forReversal;
	}

	public void setForReversal(String forReversal) {
		this.forReversal = forReversal;
	}

	public String getFullMessage() {
		return fullMessage;
	}

	public void setFullMessage(String fullMessage) {
		this.fullMessage = fullMessage;
	}
	


}
