package bifast.admin.pojo;

public class TransxHistoryRequestParam {

	private String bicSrc;
	private String bicDst;
	private String tranType;
	private String rspCode;
	private String startDate;
	private String endDate;
	
	public String getBicSrc() {
		return bicSrc;
	}
	public void setBicSrc(String bicSrc) {
		this.bicSrc = bicSrc;
	}
	public String getBicDst() {
		return bicDst;
	}
	public void setBicDst(String bicDst) {
		this.bicDst = bicDst;
	}
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	public String getRspCode() {
		return rspCode;
	}
	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
}
