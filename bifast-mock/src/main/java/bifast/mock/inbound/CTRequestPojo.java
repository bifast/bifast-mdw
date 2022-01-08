package bifast.mock.inbound;

public class CTRequestPojo {

	private String amount;
	
	private String debtorName;
	
	private String debtorAccountNo;
//	private String debtorAccountType;
//	private String debtorAgentId;
//	private String creditorAgentId;
	private String creditorName;
//	private String creditorPrvId;
//	private String creditorOrgId;
	
	private String creditorAccountNo;
//	private String creditorAccountType;
	
	private String paymentInfo;
	

	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDebtorName() {
		return debtorName;
	}
	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}
	public String getDebtorAccountNo() {
		return debtorAccountNo;
	}
	public void setDebtorAccountNo(String debtorAccountNo) {
		this.debtorAccountNo = debtorAccountNo;
	}
	public String getCreditorName() {
		return creditorName;
	}
	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}
	public String getCreditorAccountNo() {
		return creditorAccountNo;
	}
	public void setCreditorAccountNo(String creditorAccountNo) {
		this.creditorAccountNo = creditorAccountNo;
	}
	public String getPaymentInfo() {
		return paymentInfo;
	}
	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
	
	
}
