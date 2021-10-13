package bifast.outbound.pojo.flat;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Pacs009")
public class FlatPacs009Pojo extends FlatMessageBase {

	private String msgId;
	private String creDtTm;
	private String SettlementMtd;
	private String endToEndId;
	private String transactionId;
	private String categoryPurpose;
	private String amount;
	private String currency;
	private String settlementDt;

	private String debtorFinInstId;
	private String creditorFinInstId;
	
	private String remittanceInfo;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getCreDtTm() {
		return creDtTm;
	}

	public void setCreDtTm(String creDtTm) {
		this.creDtTm = creDtTm;
	}

	public String getSettlementMtd() {
		return SettlementMtd;
	}

	public void setSettlementMtd(String settlementMtd) {
		SettlementMtd = settlementMtd;
	}

	public String getEndToEndId() {
		return endToEndId;
	}

	public void setEndToEndId(String endToEndId) {
		this.endToEndId = endToEndId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCategoryPurpose() {
		return categoryPurpose;
	}

	public void setCategoryPurpose(String categoryPurpose) {
		this.categoryPurpose = categoryPurpose;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSettlementDt() {
		return settlementDt;
	}

	public void setSettlementDt(String settlementDt) {
		this.settlementDt = settlementDt;
	}

	public String getDebtorFinInstId() {
		return debtorFinInstId;
	}

	public void setDebtorFinInstId(String debtorFinInstId) {
		this.debtorFinInstId = debtorFinInstId;
	}

	public String getCreditorFinInstId() {
		return creditorFinInstId;
	}

	public void setCreditorFinInstId(String creditorFinInstId) {
		this.creditorFinInstId = creditorFinInstId;
	}

	public String getRemittanceInfo() {
		return remittanceInfo;
	}

	public void setRemittanceInfo(String remittanceInfo) {
		this.remittanceInfo = remittanceInfo;
	}
	
	

	
}
