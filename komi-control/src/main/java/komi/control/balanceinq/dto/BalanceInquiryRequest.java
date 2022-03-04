package komi.control.balanceinq.dto;

import java.util.Optional;

public class BalanceInquiryRequest extends BaseInboundRequestDTO{

    private String accountNumber;

    public String getAccountNumber() {
        return Optional.ofNullable(accountNumber).orElse("");
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

}
