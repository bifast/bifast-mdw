package komi.control.rekon;

public class RekonDAO {

	private String noRef;
	private String endToEndId;
	private String cstmAccountNo;
	private String cstmAccountName; 
	private String transactionType;
	private String creditAmount;
	private String debitAmount;
	private String counterpartBank; 
//	private String recptBank;
	private String reason;
	private String response; 
	private String sttlStatus;
	
	public RekonDAO () {
	}

	public RekonDAO (
		String komiTrnsId,
		String cstmAccountNo,
		String cstmAccountName, 
		String transaction_type,
		String creditAmount,
		String debitAmount,
		String orignBank,
		String recptBank,
		String reason,
		String response, 
		String sttlStatus ) {
		
		this.noRef = komiTrnsId;
	}

	public String getKomiTrnsId() {
		return noRef;
	}
	public void setKomiTrnsId(String noRef) {
		this.noRef = noRef;
	}
	public String getEndToEndId() {
		return endToEndId;
	}

	public void setEndToEndId(String endToEndId) {
		this.endToEndId = endToEndId;
	}

	public String getCstmAccountNo() {
		return cstmAccountNo;
	}
	public void setCstmAccountNo(String cstmAccountNo) {
		this.cstmAccountNo = cstmAccountNo;
	}
	public String getCstmAccountName() {
		return cstmAccountName;
	}
	public void setCstmAccountName(String cstmAccountName) {
		this.cstmAccountName = cstmAccountName;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}
	public String getDebitAmount() {
		return debitAmount;
	}
	public void setDebitAmount(String debitAmount) {
		this.debitAmount = debitAmount;
	}
	public String getCounterpartBank() {
		return counterpartBank;
	}
	public void setCounterpartBank(String counterpartBank) {
		this.counterpartBank = counterpartBank;
	}
//	public String getRecptBank() {
//		return recptBank;
//	}
//	public void setRecptBank(String recptBank) {
//		this.recptBank = recptBank;
//	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getSttlStatus() {
		return sttlStatus;
	}
	public void setSttlStatus(String sttlStatus) {
		this.sttlStatus = sttlStatus;
	}
	
	public String getCsv () {
		return (
				"\"" + noRef + "\"," +
				"\"" + endToEndId + "\", " +
				"\"" + cstmAccountNo + "\"," +
				"\"" + cstmAccountName + "\", " +
				"\"" + transactionType + "\"," +
				creditAmount + "," +
				debitAmount + "," +
				"\"" + counterpartBank + "\", " +
				"\"" + reason + "\"," +
				"\"" + sttlStatus + "\"\n" ); 
	}
	
}
