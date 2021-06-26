package bifast.library.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="INBOUND_MESSAGE")
public class InboundMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@Column(name="BIZMSGID")
	private String bizMsgIdr;
	
	@Column(name="MSG_NAME")
	private String msgDefIdr;
	@Column(name="ORGN_BANK")
	private String frFinId;
	
	@Column(name="BIZSVC")
	private String bizSvc;
	@Column(name="COPY")
	private String cpyDplct;
	@Column(name="DUPL")
	private String pssblDplct;
	@Column(name="RECEIVE_DT")
	private LocalDateTime receiveDt;
	
	@Column(name="FULL_MSG", length=5000)
	private String fullMessage;

	public InboundMessage() {}
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getBizMsgIdr() {
		return bizMsgIdr;
	}

	public void setBizMsgIdr(String bizMsgIdr) {
		this.bizMsgIdr = bizMsgIdr;
	}

	public String getMsgDefIdr() {
		return msgDefIdr;
	}

	public void setMsgDefIdr(String msgDefIdr) {
		this.msgDefIdr = msgDefIdr;
	}

	public String getFrFinId() {
		return frFinId;
	}

	public void setFrFinId(String frFinId) {
		this.frFinId = frFinId;
	}

	public String getBizSvc() {
		return bizSvc;
	}

	public void setBizSvc(String bizSvc) {
		this.bizSvc = bizSvc;
	}

	public String getCpyDplct() {
		return cpyDplct;
	}

	public void setCpyDplct(String cpyDplct) {
		this.cpyDplct = cpyDplct;
	}

	public String getPssblDplct() {
		return pssblDplct;
	}

	public void setPssblDplct(String pssblDplct) {
		this.pssblDplct = pssblDplct;
	}

	public LocalDateTime getReceiveDt() {
		return receiveDt;
	}

	public void setReceiveDt(LocalDateTime receiveDt) {
		this.receiveDt = receiveDt;
	}

	public String getFullMessage() {
		return fullMessage;
	}

	public void setFullMessage(String fullMessage) {
		this.fullMessage = fullMessage;
	}

	@Override
	public String toString() {
		return "InboundMessage [bizMsgIdr=" + bizMsgIdr + ", msgDefIdr=" + msgDefIdr + ", frFinId=" + frFinId
				+ ", bizSvc=" + bizSvc + "]";
	}
	

	
}
