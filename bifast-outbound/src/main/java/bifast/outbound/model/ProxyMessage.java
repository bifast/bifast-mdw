package bifast.outbound.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name="KC_PROXY_MGMT")
public class ProxyMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
	@SequenceGenerator(name="seq_generator", sequenceName = "table_seq_generator", allocationSize=1)
	private Long id;
	@Column(length=20)
	private String komiTrnsId;
	@Column(length=20)
	private String chnlNoRef;
	
	@Column(length=20)
	private String operationType;
	@Column(length=20)
	private String proxyType;
	@Column(length=140)
	private String proxyValue;
	
	@Column(length=100)
	private String displayName;
	@Column(length=100)
	private String accountNumber;
	@Column(length=10)
	private String accountType;
	
	@Column(length=100)
	private String accountName;
	
	@Column(length=10)
	private String scndIdType;
	@Column(length=100)
	private String scndValue;
	
	@Column(length=10)
	private String customerType;
	@Column(length=10)
	private String customerId;
	@Column(length=10)
	private String residentStatus;
	@Column(length=10)
	private String townName;
	
	@Column(length=4000)
	private String fullRequestMesg;
	@Column(length=4000)
	private String fullResponseMesg;
	
	private LocalDateTime requestDt;
	private Long cihubElapsedTime;

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
	public String getKomiTrnsId() {
		return komiTrnsId;
	}
	public void setKomiTrnsId(String komiTrnsId) {
		this.komiTrnsId = komiTrnsId;
	}
	public String getChnlNoRef() {
		return chnlNoRef;
	}
	public void setChnlNoRef(String chnlNoRef) {
		this.chnlNoRef = chnlNoRef;
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
	public Long getCihubElapsedTime() {
		return cihubElapsedTime;
	}
	public void setCihubElapsedTime(Long cihubElapsedTime) {
		this.cihubElapsedTime = cihubElapsedTime;
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
