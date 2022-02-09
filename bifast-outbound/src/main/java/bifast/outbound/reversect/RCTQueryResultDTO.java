package bifast.outbound.reversect;

public class RCTQueryResultDTO {
	
	private Long id;
	private String komiTrnsId;
	private String fullTextMsg;
	private String endToEndId;
	private String errorMsg;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getKomiTrnsId() {
		return komiTrnsId;
	}


	public void setKomiTrnsId(String komiTrnsId) {
		this.komiTrnsId = komiTrnsId;
	}


	public String getFullTextMsg() {
		return fullTextMsg;
	}


	public void setFullTextMsg(String fullTextMsg) {
		this.fullTextMsg = fullTextMsg;
	}


	public String getEndToEndId() {
		return endToEndId;
	}


	public void setEndToEndId(String endToEndId) {
		this.endToEndId = endToEndId;
	}


	public String getErrorMsg() {
		return errorMsg;
	}


	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
