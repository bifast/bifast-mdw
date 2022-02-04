package bifast.mock.isoapt.pojo;

public class CIFResponse extends BaseResponseDTO {
    private String accountNumber;
    
    private String creditorName;
    
    private String creditorId;
    
    private String creditorType;
    
    private String residentStatus;
    
    private String townName;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCreditorName() {
		return creditorName;
	}

	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}

	public String getCreditorId() {
		return creditorId;
	}

	public void setCreditorId(String creditorId) {
		this.creditorId = creditorId;
	}

	public String getCreditorType() {
		return creditorType;
	}

	public void setCreditorType(String creditorType) {
		this.creditorType = creditorType;
	}

	public String getResidentStatus() {
		return residentStatus;
	}

	public void setResidentStatus(String residentStatus) {
		this.residentStatus = residentStatus;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}
    
    
}
