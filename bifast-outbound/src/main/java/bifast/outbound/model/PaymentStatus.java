package bifast.outbound.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="PAYMENT_STATUS")
public class PaymentStatus {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="BIZMSGID")
	private String BizMsgIdr;
	
	@Column(name="ORGN_ENDTOENDID")
	private String orgnlEndToEndId;
	
	private String status;
	private String errorMsg;

	private LocalDateTime updateDt;

	private String saf;
	private int retryCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBizMsgIdr() {
		return BizMsgIdr;
	}

	public void setBizMsgIdr(String bizMsgIdr) {
		BizMsgIdr = bizMsgIdr;
	}

	public String getOrgnlEndToEndId() {
		return orgnlEndToEndId;
	}

	public void setOrgnlEndToEndId(String orgnlEndToEndId) {
		this.orgnlEndToEndId = orgnlEndToEndId;
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

	public LocalDateTime getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(LocalDateTime updateDt) {
		this.updateDt = updateDt;
	}

	public String getSaf() {
		return saf;
	}

	public void setSaf(String saf) {
		this.saf = saf;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	
	
}
