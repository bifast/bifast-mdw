package bifast.outbound.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="PROXY_MESSAGE")
public class ProxyMessage {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String intrnRefId;
	
	private Long chnlTrxId;

	private String operationType;
	private String proxyType;
	private String proxyValue;
	@Column(length=100)
	private String displayName;
	private String accountNumber;
	private String accountType;
	
	@Column(length=100)
	private String accountName;
	private String scndIdType;
	private String scndValue;
	private String customerType;
	private String customerId;
	private String residentStatus;
	private String townName;
	
	@Column(length=4000)
	private String fullRequestMesg;
	@Column(length=4000)
	private String fullResponseMesg;
	
	private LocalDateTime requestDt;
	private LocalDateTime responseDt;
	
	@Column(length=50)
	private String callStatus;
	@Column(length=50)
	private String respStatus;
	@Column(length=400)
	private String errorMessage;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIntrnRefId() {
		return intrnRefId;
	}
	public void setIntrnRefId(String intrnRefId) {
		this.intrnRefId = intrnRefId;
	}
	public Long getChnlTrxId() {
		return chnlTrxId;
	}
	public void setChnlTrxId(Long chnlTrxId) {
		this.chnlTrxId = chnlTrxId;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getProxyType() {
		return proxyType;
	}
	public void setProxyType(String proxyType) {
		this.proxyType = proxyType;
	}
	public String getProxyValue() {
		return proxyValue;
	}
	public void setProxyValue(String proxyValue) {
		this.proxyValue = proxyValue;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getScndIdType() {
		return scndIdType;
	}
	public void setScndIdType(String scndIdType) {
		this.scndIdType = scndIdType;
	}
	public String getScndValue() {
		return scndValue;
	}
	public void setScndValue(String scndValue) {
		this.scndValue = scndValue;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	public String getFullRequestMesg() {
		return fullRequestMesg;
	}
	public void setFullRequestMesg(String fullRequestMesg) {
		this.fullRequestMesg = fullRequestMesg;
	}
	public String getFullResponseMesg() {
		return fullResponseMesg;
	}
	public void setFullResponseMesg(String fullResponseMesg) {
		this.fullResponseMesg = fullResponseMesg;
	}
	public LocalDateTime getRequestDt() {
		return requestDt;
	}
	public void setRequestDt(LocalDateTime requestDt) {
		this.requestDt = requestDt;
	}
	public LocalDateTime getResponseDt() {
		return responseDt;
	}
	public void setResponseDt(LocalDateTime responseDt) {
		this.responseDt = responseDt;
	}
	public String getCallStatus() {
		return callStatus;
	}
	public void setCallStatus(String callStatus) {
		this.callStatus = callStatus;
	}
	public String getRespStatus() {
		return respStatus;
	}
	public void setRespStatus(String respStatus) {
		this.respStatus = respStatus;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	

	
}
