package bifast.outbound.ficredittransfer;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("FICreditTransferResponse")
public class ChnlFICreditTransferResponsePojo {
	private String orignReffId;
	private String bizMsgId;
	private String status;
	private String reason;
	
	public String getOrignReffId() {
		return orignReffId;
	}
	public void setOrignReffId(String orignReffId) {
		this.orignReffId = orignReffId;
	}
	public String getBizMsgId() {
		return bizMsgId;
	}
	public void setBizMsgId(String bizMsgId) {
		this.bizMsgId = bizMsgId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

}
