package komi.control.balanceinq.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class BalanceInquiryResponse extends BaseInboundResponseDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String balance;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

}
