package bifast.corebank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bifast.corebank.model.CbAccount;
import bifast.corebank.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountEnquiryRepository;

    
    public CbAccount getAccountInquiry(String transactionId,String accountNumber,BigDecimal amount) {

        return accountEnquiryRepository.getAccountInquiry(transactionId,accountNumber,amount);
    }
    
    public CbAccount getAccountByAccountNumber(String accountNumber) {

        return accountEnquiryRepository.getAccountByAccountNumber(accountNumber);
    }
    
}
