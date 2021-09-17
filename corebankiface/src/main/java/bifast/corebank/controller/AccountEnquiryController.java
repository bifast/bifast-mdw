package bifast.corebank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import bifast.corebank.exception.DataNotFoundException;
import bifast.corebank.model.Account;

import bifast.corebank.pojo.AccountEnquiryRequestPojo;
import bifast.corebank.pojo.AccountEnquiryRequest;
import bifast.corebank.pojo.AccountEnquiryResponsePojo;
import bifast.corebank.pojo.AccountEnquiryResponse;
import bifast.corebank.service.AccountService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {
        RequestMethod.GET, 
        RequestMethod.POST, 
        RequestMethod.OPTIONS,
        RequestMethod.PUT,
        RequestMethod.PATCH,
        RequestMethod.HEAD,
        RequestMethod.DELETE,
        RequestMethod.TRACE})
@RestController
@RequestMapping("api")
public class AccountEnquiryController {

    static final Logger log= LoggerFactory.getLogger(AccountEnquiryController.class);
    
    @Autowired
    AccountService accountService;
    
    @GetMapping("/accountenquiry")
    public AccountEnquiryResponsePojo getListByNoSo(@RequestBody AccountEnquiryRequestPojo accountEnquiryRequestPojo){
        
        Account account =  accountService.getAccountInquiry(
        		accountEnquiryRequestPojo.getAccountEnquiryRequest().getTransactionId(),
        		accountEnquiryRequestPojo.getAccountEnquiryRequest().getAccountNumber(),
        		accountEnquiryRequestPojo.getAccountEnquiryRequest().getAmount());
        
        AccountEnquiryResponsePojo  accountEnquiryResponsePojo = new AccountEnquiryResponsePojo();     
        AccountEnquiryResponse  accountEnquiryResponse = new AccountEnquiryResponse();     
        if (account == null) {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found");
        }else {
        	
            accountEnquiryResponse.setTransactionId(account.getIntrRefId());
            
            accountEnquiryResponse.setTransactionId(accountEnquiryRequestPojo.getAccountEnquiryRequest().getTransactionId());
            accountEnquiryResponse.setCreditorStatus(account.getCreditorStatus());
            accountEnquiryResponse.setAccountType(account.getAccountType());
            accountEnquiryResponse.setAccountNumber(account.getAccountNo());
            accountEnquiryResponse.setCreditorId(account.getCreditorId());
            
            accountEnquiryResponse.setCreditorName(account.getCreditorName());
            
            accountEnquiryResponse.setCreditorType(account.getCreditorType());
            accountEnquiryResponse.setResidentStatus(account.getCreditorResidentStatus());
            accountEnquiryResponse.setTownName(account.getCreditorTownName());
            accountEnquiryResponse.setAdditionalInfo(account.getAdditionalInfo());
            
            accountEnquiryResponsePojo.setAccountEnquiryResponse(accountEnquiryResponse);
        }
        
        
        return  accountEnquiryResponsePojo;

    }
}
