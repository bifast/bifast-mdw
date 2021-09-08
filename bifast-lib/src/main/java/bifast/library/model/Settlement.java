package bifast.library.model;

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
	
	private String orignBank;
	private String recptBank;
	
	@Column(name="SETTL_CONF_BIZMSGID")
	private String settlConfBizMsgId;
	@Column(name="ORGNL_CRDT_TRN_BIZMSGID")
	private String orgnlCrdtTrnReqBizMsgId;

	private String crdtAccountNo;
	private String crdtAccountType;
	private String crdtId;
	private String crdtIdType;
	private String dbtrAccountNo;
	private String dbtrAccountType;
	private String dbtrId;
	private String dbtrIdType;

	public Settlement () {}
	
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

	public String getCrdtAccountType() {
		return crdtAccountType;
	}

	public void setCrdtAccountType(String crdtAccountType) {
		this.crdtAccountType = crdtAccountType;
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

}
