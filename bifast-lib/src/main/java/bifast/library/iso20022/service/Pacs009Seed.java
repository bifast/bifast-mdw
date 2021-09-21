package bifast.library.iso20022.service;

import java.math.BigDecimal;

public class Pacs009Seed {

	private String msgId;
	private String trnType;   
	private String bizMsgId; 
	private BigDecimal amount; 
	private String orignBank;
	private String recptBank; 
	private String channel; 
	private String paymentInfo;
	private String debtorBank;
	private String creditorBank;
	
	
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getTrnType() {
		return trnType;
	}
	public void setTrnType(String trnType) {
		this.trnType = trnType;
	}
	public String getBizMsgId() {
		return bizMsgId;
	}
	public void setBizMsgId(String bizMsgId) {
		this.bizMsgId = bizMsgId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
	public String getDebtorBank() {
		return debtorBank;
	}
	public void setDebtorBank(String debtorBank) {
		this.debtorBank = debtorBank;
	}
	public String getCreditorBank() {
		return creditorBank;
	}
	public void setCreditorBank(String creditorBank) {
		this.creditorBank = creditorBank;
	}  

	
	
}
