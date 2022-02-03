package bifast.inbound.corebank.isopojo;

import java.util.Optional;

public class AccountCustInfoRequest {
    private String transactionId;
    
    private String noRef;
    
    private String merchantType;
    
    private String terminalId;
    
    private String dateTime;

    private String accountNumber;

    public String getTransactionId() {
        return transactionId;
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * @return the noRef
     */
    public String getNoRef() {
        return noRef;
    }

    /**
     * @param noRef the noRef to set
     */
    public void setNoRef(String noRef) {
        this.noRef = noRef;
    }

    /**
     * @return the merchantType
     */
    public String getMerchantType() {
        return merchantType;
    }

    /**
     * @param merchantType the merchantType to set
     */
    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    /**
     * @return the terminalId
     */
    public String getTerminalId() {
        return terminalId;
    }

    /**
     * @param terminalId the terminalId to set
     */
    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    /**
     * @return the dateTime
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * @param dateTime the dateTime to set
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    
    public String getAccountNumber() {
        return Optional.ofNullable(accountNumber).orElse("");
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

}
