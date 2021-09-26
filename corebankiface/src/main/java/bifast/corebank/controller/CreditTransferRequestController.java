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

import bifast.corebank.model.CreditTransferRequest;
import bifast.corebank.pojo.AccountEnquiryRequestPojo;
import bifast.corebank.pojo.AccountEnquiryRequest;
import bifast.corebank.pojo.AccountEnquiryResponsePojo;
import bifast.corebank.pojo.CreditInstructionRequestPojo;
import bifast.corebank.pojo.CreditInstructionResponse;
import bifast.corebank.pojo.CreditInstructionResponsePojo;
import bifast.corebank.pojo.AccountEnquiryResponse;

import bifast.corebank.service.AccountService;
import bifast.corebank.service.CreditTransferRequestService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class CreditTransferRequestController {

    static final Logger log= LoggerFactory.getLogger(CreditTransferRequestController.class);

    @Autowired
    CreditTransferRequestService creditTransferRequestService;
    
    @Autowired
    AccountService accountService;
    
    @PostMapping("/CreditInstructionRequest")
    public CreditInstructionResponsePojo CreditInstructionRequest(@RequestBody CreditInstructionRequestPojo creditInstructionRequest){
    	
    	CreditTransferRequest creditTransferRequest = new CreditTransferRequest();
    	
    	Account account =  new Account();
    	account = accountService.getAccountByAccountNumber(creditInstructionRequest.getCreditInstructionRequest().getAccountNumber());
        
        CreditInstructionResponsePojo  creditInstructionResponsePojo = new CreditInstructionResponsePojo();   
        CreditInstructionResponse creditInstructionResponse =  new CreditInstructionResponse();
        Date date = Calendar.getInstance().getTime();  
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
    	String strDate = dateFormat.format(date);
    	
        if(account != null) {
        	if(!account.getCreditorStatus().equals("HOLD") ) {
	        	creditTransferRequest.setTransactionId(creditInstructionRequest.getCreditInstructionRequest().getTransactionId());
	        	creditTransferRequest.setAccountNumber(creditInstructionRequest.getCreditInstructionRequest().getAccountNumber());
	        	creditTransferRequest.setAccountType(creditInstructionRequest.getCreditInstructionRequest().getAccountType());
	        	creditTransferRequest.setAmount(creditInstructionRequest.getCreditInstructionRequest().getAmount());
	        	creditTransferRequest.setCreditorName(creditInstructionRequest.getCreditInstructionRequest().getCreditorName());
	        	creditTransferRequest.setPaymentInfo(creditInstructionRequest.getCreditInstructionRequest().getPaymentInfo());
	        	
	        	creditTransferRequest =  creditTransferRequestService.save(creditTransferRequest);
	            
	            if (creditTransferRequest.getId() == null) {
	            	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found");
	            }
	            
	            creditInstructionResponse.setTransactionId(creditTransferRequest.getTransactionId());
	            creditInstructionResponse.setStatus("SUCCESS");
	            creditInstructionResponse.setReason("");
	            creditInstructionResponse.setAccountNumber(creditTransferRequest.getAccountNumber());
	            creditInstructionResponse.setAmount(creditTransferRequest.getAmount());
	            creditInstructionResponse.setAddtInfo(creditTransferRequest.getPaymentInfo());
	            creditInstructionResponse.setResponseTime(strDate);
	            
	            creditInstructionResponsePojo.setCreditInstructionResponse(creditInstructionResponse);
        	}else {
        		creditInstructionResponse.setTransactionId(creditTransferRequest.getTransactionId());
                creditInstructionResponse.setStatus("FAILED");
                creditInstructionResponse.setReason("Status Rekening HOLD");
                creditInstructionResponse.setAccountNumber(creditTransferRequest.getAccountNumber());
                creditInstructionResponse.setAmount(creditTransferRequest.getAmount());
                creditInstructionResponse.setAddtInfo(creditTransferRequest.getPaymentInfo());
                creditInstructionResponse.setResponseTime(strDate);
                
                creditInstructionResponsePojo.setCreditInstructionResponse(creditInstructionResponse);
        	}
            
        }else {
        	

        	creditInstructionResponse.setTransactionId(creditTransferRequest.getTransactionId());
            creditInstructionResponse.setStatus("FAILED");
            creditInstructionResponse.setReason("Rekening Tidak Ditemukan");
            creditInstructionResponse.setAccountNumber(creditTransferRequest.getAccountNumber());
            creditInstructionResponse.setAmount(creditTransferRequest.getAmount());
            creditInstructionResponse.setAddtInfo(creditTransferRequest.getPaymentInfo());
            creditInstructionResponse.setResponseTime(strDate);
            
            creditInstructionResponsePojo.setCreditInstructionResponse(creditInstructionResponse);
        }
    	
    	
        
        return  creditInstructionResponsePojo;

    }
}
