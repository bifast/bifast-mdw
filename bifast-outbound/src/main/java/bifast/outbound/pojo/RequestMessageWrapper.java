package bifast.outbound.pojo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.corebank.CBDebitInstructionRequestPojo;
import bifast.outbound.credittransfer.ChnlCreditTransferRequestPojo;
import bifast.outbound.paymentstatus.ChnlPaymentStatusRequestPojo;
import bifast.outbound.proxyregistration.ChnlProxyRegistrationRequestPojo;
import bifast.outbound.proxyregistration.ChnlProxyResolutionRequestPojo;

public class RequestMessageWrapper {

	private String requestId;  // no internal reference dari channel
	private String msgName;
	private String channelId;   // nomor id channel = clientId
	private String channelType;
	private Long chnlTrnsIdTable;
	
	private String komiTrxId;   // internal tranaction id dari komi
								// digenerate dari utilService.getkomiid;
	private LocalDateTime requestTime;

	@JsonProperty("DebitRequest")
	private CBDebitInstructionRequestPojo debitRequest;
	
	@JsonProperty("AccountEnquiryRequest")
	private ChnlAccountEnquiryRequestPojo chnlAccountEnquiryRequest;
	
	@JsonProperty("CreditTransferRequest")
	private ChnlCreditTransferRequestPojo chnlCreditTransferRequest;

	private BusinessMessage accountEnquiryRequest;

	@JsonProperty("BICreditTransferRequest")
	private BusinessMessage creditTransferRequest;

	@JsonProperty("ChnlPaymentStatusRequest")
	private ChnlPaymentStatusRequestPojo chnlPaymentStatusRequest;

	@JsonProperty("ProxyRegistrationRequest")
	private ChnlProxyRegistrationRequestPojo proxyRegistrationReq;
	
	@JsonProperty("ProxyResolutionRequest")
	private ChnlProxyResolutionRequestPojo proxyResolutionReq;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getMsgName() {
		return msgName;
	}

	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Long getChnlTrnsIdTable() {
		return chnlTrnsIdTable;
	}

	public void setChnlTrnsIdTable(Long chnlTrnsIdTable) {
		this.chnlTrnsIdTable = chnlTrnsIdTable;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getKomiTrxId() {
		return komiTrxId;
	}

	public void setKomiTrxId(String komiTrxId) {
		this.komiTrxId = komiTrxId;
	}

	public LocalDateTime getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(LocalDateTime requestTime) {
		this.requestTime = requestTime;
	}

	public CBDebitInstructionRequestPojo getDebitRequest() {
		return debitRequest;
	}

	public void setDebitRequest(CBDebitInstructionRequestPojo debitRequest) {
		this.debitRequest = debitRequest;
	}

	public ChnlAccountEnquiryRequestPojo getChnlAccountEnquiryRequest() {
		return chnlAccountEnquiryRequest;
	}

	public void setChnlAccountEnquiryRequest(ChnlAccountEnquiryRequestPojo chnlAccountEnquiryRequest) {
		this.chnlAccountEnquiryRequest = chnlAccountEnquiryRequest;
	}

	public ChnlCreditTransferRequestPojo getChnlCreditTransferRequest() {
		return chnlCreditTransferRequest;
	}

	public void setChnlCreditTransferRequest(ChnlCreditTransferRequestPojo chnlCreditTransferRequest) {
		this.chnlCreditTransferRequest = chnlCreditTransferRequest;
	}

	public BusinessMessage getAccountEnquiryRequest() {
		return accountEnquiryRequest;
	}

	public void setAccountEnquiryRequest(BusinessMessage accountEnquiryRequest) {
		this.accountEnquiryRequest = accountEnquiryRequest;
	}

	public BusinessMessage getCreditTransferRequest() {
		return creditTransferRequest;
	}

	public void setCreditTransferRequest(BusinessMessage creditTransferRequest) {
		this.creditTransferRequest = creditTransferRequest;
	}

	public ChnlPaymentStatusRequestPojo getChnlPaymentStatusRequest() {
		return chnlPaymentStatusRequest;
	}

	public void setChnlPaymentStatusRequest(ChnlPaymentStatusRequestPojo chnlPaymentStatusRequest) {
		this.chnlPaymentStatusRequest = chnlPaymentStatusRequest;
	}

	public ChnlProxyRegistrationRequestPojo getProxyRegistrationReq() {
		return proxyRegistrationReq;
	}

	public void setProxyRegistrationReq(ChnlProxyRegistrationRequestPojo proxyRegistrationReq) {
		this.proxyRegistrationReq = proxyRegistrationReq;
	}

	public ChnlProxyResolutionRequestPojo getProxyResolutionReq() {
		return proxyResolutionReq;
	}

	public void setProxyResolutionReq(ChnlProxyResolutionRequestPojo proxyResolutionReq) {
		this.proxyResolutionReq = proxyResolutionReq;
	}


}
