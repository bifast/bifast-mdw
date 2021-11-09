package bifast.corebank.settlement;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("SettlementConfirmation")
public class CbSettlementRequestPojo {

	private String komiTrnsId;
	private String orgnlKomiTrnsId;
	
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
	

}
