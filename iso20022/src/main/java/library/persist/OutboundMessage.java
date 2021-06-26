package library.persist;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="OUTBOUND_MESSAGE")
public class OutboundMessage {

	@Id
	private String busMsgId;
	private String refId;
	private String msgType;
	private LocalDateTime creDt;
	
	@Column(length=1000)
	private String msgHeader;

	@Column(length=3000)
	private String fullMessage;

	public String getBusMsgId() {
		return busMsgId;
	}

	public void setBusMsgId(String busMsgId) {
		this.busMsgId = busMsgId;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public LocalDateTime getCreDt() {
		return creDt;
	}

	public void setCreDt(LocalDateTime creDt) {
		this.creDt = creDt;
	}

	public String getMsgHeader() {
		return msgHeader;
	}

	public void setMsgHeader(String msgHeader) {
		this.msgHeader = msgHeader;
	}

	public String getFullMessage() {
		return fullMessage;
	}

	public void setFullMessage(String fullMessage) {
		this.fullMessage = fullMessage;
	}

	
}
