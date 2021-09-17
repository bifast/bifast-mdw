package bifast.corebank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AccountEnquiry {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String direction;
	
	private String intrRefId;
	
	@Column(name="ORIGN_BANK")
	private String originatingBank;
	@Column(name="RECPT_BANK")
	private String recipientBank;
	
	private String accountNo;
	private String reqBizMsgId;
	private String respStatus;
	private LocalDateTime creDt;
	
	private BigDecimal amount;
	
	public AccountEnquiry () {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getIntrRefId() {
		return intrRefId;
	}

	public void setIntrRefId(String intrRefId) {
		this.intrRefId = intrRefId;
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

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getReqBizMsgId() {
		return reqBizMsgId;
	}

	public void setReqBizMsgId(String reqBizMsgId) {
		this.reqBizMsgId = reqBizMsgId;
	}

	public String getRespStatus() {
		return respStatus;
	}

	public void setRespStatus(String respStatus) {
		this.respStatus = respStatus;
	}

	public LocalDateTime getCreDt() {
		return creDt;
	}

	public void setCreDt(LocalDateTime creDt) {
		this.creDt = creDt;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
}
