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
	
	private String internRefId;
	
	private Long chnlTrxId;

	@Column(length=5000)
	private String requestFullMessage;
	@Column(length=5000)
	private String responseFullMessage;
	
	@Column(name="BIZMSGID", length=50)
	private String BizMsgIdr;
	
	@Column(name="ORGN_ENDTOENDID", length=50)
	private String orgnlEndToEndId;
	
	private String status;
	private String errorMsg;

	private LocalDateTime requestDt;
	private LocalDateTime responseDt;
	
//	private LocalDateTime updateDt;

	private String saf;
	private int retryCount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInternRefId() {
		return internRefId;
	}
	public void setInternRefId(String internRefId) {
		this.internRefId = internRefId;
	}
	public Long getChnlTrxId() {
		return chnlTrxId;
	}
	public void setChnlTrxId(Long chnlTrxId) {
		this.chnlTrxId = chnlTrxId;
	}
	public String getRequestFullMessage() {
		return requestFullMessage;
	}
	public void setRequestFullMessage(String requestFullMessage) {
		this.requestFullMessage = requestFullMessage;
	}
	public String getResponseFullMessage() {
		return responseFullMessage;
	}
	public void setResponseFullMessage(String responseFullMessage) {
		this.responseFullMessage = responseFullMessage;
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
	public LocalDateTime getRequestDt() {
		return requestDt;
	}
	public void setRequestDt(LocalDateTime requestDt) {
		this.requestDt = requestDt;
	}
	public LocalDateTime getResponseDt() {
		return responseDt;
	}
	public void setResponseDt(LocalDateTime responseDt) {
		this.responseDt = responseDt;
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
