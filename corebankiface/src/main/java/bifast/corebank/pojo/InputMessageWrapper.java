package bifast.corebank.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import bifast.corebank.accountenquiry.CbAccountEnquiryRequestPojo;
import bifast.corebank.balance.CbAccountBalanceRequestPojo;
import bifast.corebank.credit.CbCreditRequestPojo;
import bifast.corebank.customerinfo.CbCustomerInfoRequestPojo;
import bifast.corebank.debit.CbDebitRequestPojo;
import bifast.corebank.debitreversal.CbDebitReversalRequestPojo;
import bifast.corebank.settlement.CbSettlementRequestPojo;


public class InputMessageWrapper {

	@JsonProperty("AccountEnquiryRequest")
	private CbAccountEnquiryRequestPojo accountEnquiryRequest;

	@JsonProperty("DebitRequest")
	private CbDebitRequestPojo debitRequest;
	
	@JsonProperty("CreditRequest")
	private CbCreditRequestPojo creditRequest;

	@JsonProperty("CustomerInfoRequest")
	private CbCustomerInfoRequestPojo customerInfoRequest;
	
	@JsonProperty("DebitReversalRequest")
	private CbDebitReversalRequestPojo debitReversalRequest;

	@JsonProperty("SettlementConfirmation")
	private CbSettlementRequestPojo settlement;

	@JsonProperty("AccountBalanceRequest")
	private CbAccountBalanceRequestPojo accountBalanceRequest;

	public CbAccountEnquiryRequestPojo getAccountEnquiryRequest() {
		return accountEnquiryRequest;
	}

	public void setAccountEnquiryRequest(CbAccountEnquiryRequestPojo accountEnquiryRequest) {
		this.accountEnquiryRequest = accountEnquiryRequest;
	}

	public CbDebitRequestPojo getDebitRequest() {
		return debitRequest;
	}

	public void setDebitRequest(CbDebitRequestPojo debitRequest) {
		this.debitRequest = debitRequest;
	}

	public CbCreditRequestPojo getCreditRequest() {
		return creditRequest;
	}

	public void setCreditRequest(CbCreditRequestPojo creditRequest) {
		this.creditRequest = creditRequest;
	}

	public CbCustomerInfoRequestPojo getCustomerInfoRequest() {
		return customerInfoRequest;
	}

	public void setCustomerInfoRequest(CbCustomerInfoRequestPojo customerInfoRequest) {
		this.customerInfoRequest = customerInfoRequest;
	}

	public CbDebitReversalRequestPojo getDebitReversalRequest() {
		return debitReversalRequest;
	}

	public void setDebitReversalRequest(CbDebitReversalRequestPojo debitReversalRequest) {
		this.debitReversalRequest = debitReversalRequest;
	}

	public CbSettlementRequestPojo getSettlement() {
		return settlement;
	}

	public void setSettlement(CbSettlementRequestPojo settlement) {
		this.settlement = settlement;
	}

	public CbAccountBalanceRequestPojo getAccountBalanceRequest() {
		return accountBalanceRequest;
	}

	public void setAccountBalanceRequest(CbAccountBalanceRequestPojo accountBalanceRequest) {
		this.accountBalanceRequest = accountBalanceRequest;
	}


}
