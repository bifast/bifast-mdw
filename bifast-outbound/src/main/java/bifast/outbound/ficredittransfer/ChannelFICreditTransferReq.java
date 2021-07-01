package bifast.outbound.ficredittransfer;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("FICreditTransferRequest")
public class ChannelFICreditTransferReq {

	private String intrnRefId;
	private BigDecimal amount; 
	private String recptBank; 
	private String channel; 
	private String paymentInfo;
	
	public String getIntrnRefId() {
		return intrnRefId;
	}
	public void setIntrnRefId(String intrnRefId) {
		this.intrnRefId = intrnRefId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getRecptBank() {
		return recptBank;
	}
	public void setRecptBank(String recptBank) {
		this.recptBank = recptBank;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getPaymentInfo() {
		return paymentInfo;
	}
	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	

	
}
