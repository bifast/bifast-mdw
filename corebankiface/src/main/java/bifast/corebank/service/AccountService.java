package bifast.corebank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bifast.corebank.model.Account;
import bifast.corebank.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountEnquiryRepository;

    public Account getAccountByAccountNumber(String accountNumber) {

        return accountEnquiryRepository.getAccountByAccountNumber(accountNumber);
    }
    
}
