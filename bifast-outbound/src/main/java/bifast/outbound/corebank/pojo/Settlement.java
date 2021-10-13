package bifast.outbound.corebank.pojo;

public class Settlement extends CBRequestBase{

	private Long orgnlTrnsId;
	private String orgnlDateTime;
	private String originationAccountNumber;
	private String recipientAccountNumber;
    private String originatingBank;
    private String recipientBank;
    
	public Long getOrgnlTrnsId() {
		return orgnlTrnsId;
	}
	public void setOrgnlTrnsId(Long orgnlTrnsId) {
		this.orgnlTrnsId = orgnlTrnsId;
	}
	public String getOrgnlDateTime() {
		return orgnlDateTime;
	}
	public void setOrgnlDateTime(String orgnlDateTime) {
		this.orgnlDateTime = orgnlDateTime;
	}
	public String getOriginationAccountNumber() {
		return originationAccountNumber;
	}
	public void setOriginationAccountNumber(String originationAccountNumber) {
		this.originationAccountNumber = originationAccountNumber;
	}
	public String getRecipientAccountNumber() {
		return recipientAccountNumber;
	}
	public void setRecipientAccountNumber(String recipientAccountNumber) {
		this.recipientAccountNumber = recipientAccountNumber;
	}
	public String getOriginatingBank() {
		return originatingBank;
	}
	public void setOriginatingBank(String originatingBank) {
		this.originatingBank = originatingBank;
	}
	public String getRecipientBank() {
		return recipientBank;
	}
	public void setRecipientBank(String recipientBank) {
		this.recipientBank = recipientBank;
	}
    
    
    
}
