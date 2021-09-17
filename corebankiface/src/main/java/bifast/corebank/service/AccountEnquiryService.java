package bifast.corebank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bifast.corebank.model.AccountEnquiry;
import bifast.corebank.repository.AccountEnquiryRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AccountEnquiryService {

    @Autowired
    private AccountEnquiryRepository accountEnquiryRepository;

    public AccountEnquiry getAccountInquiry(String transactionId,String accountNumber,BigDecimal amount) {

        return accountEnquiryRepository.getAccountInquiry(transactionId,accountNumber,amount);
    }

    public AccountEnquiry getAccountInquiryByAccountNumber(String accountNumber) {

        return accountEnquiryRepository.getAccountInquiryByAccountNumber(accountNumber);
    }
    
}
