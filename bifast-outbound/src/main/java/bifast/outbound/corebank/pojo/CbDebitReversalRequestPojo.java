package bifast.outbound.corebank.pojo;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("DebitReversalRequest")
public class CbDebitReversalRequestPojo {

	private String komiTrnsId;
	private String orgnlKomiTrnsId;
	private String orgnlAccountNumber;
	private String paymentInformation;
	
	public String getKomiTrnsId() {
		return komiTrnsId;
	}
	public void setKomiTrnsId(String komiTrnsId) {
		this.komiTrnsId = komiTrnsId;
	}
	public String getOrgnlKomiTrnsId() {
		return orgnlKomiTrnsId;
	}
	public void setOrgnlKomiTrnsId(String orgnlKomiTrnsId) {
		this.orgnlKomiTrnsId = orgnlKomiTrnsId;
	}
	public String getOrgnlAccountNumber() {
		return orgnlAccountNumber;
	}
	public void setOrgnlAccountNumber(String orgnlAccountNumber) {
		this.orgnlAccountNumber = orgnlAccountNumber;
	}
	public String getPaymentInformation() {
		return paymentInformation;
	}
	public void setPaymentInformation(String paymentInformation) {
		this.paymentInformation = paymentInformation;
	}
	

}
