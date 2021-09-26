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

import bifast.corebank.model.DebitTransferRequest;
import bifast.corebank.pojo.AccountEnquiryRequestPojo;
import bifast.corebank.pojo.AccountEnquiryRequest;
import bifast.corebank.pojo.AccountEnquiryResponsePojo;
import bifast.corebank.pojo.DebitInstructionRequestPojo;
import bifast.corebank.pojo.DebitInstructionResponse;
import bifast.corebank.pojo.DebitInstructionResponsePojo;
import bifast.corebank.pojo.AccountEnquiryResponse;
import bifast.corebank.service.AccountService;
import bifast.corebank.service.DebitTransferRequestService;

import java.text.DateFormat;
import java.text.ParseException;
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
public class DebitTransferRequestController {

    static final Logger log= LoggerFactory.getLogger(DebitTransferRequestController.class);

    @Autowired
    DebitTransferRequestService debitTransferRequestService;
    
    @Autowired
    AccountService accountService;
    
    @PostMapping("/DebitInstructionRequest")
    public DebitInstructionResponsePojo DebitInstructionRequest(@RequestBody DebitInstructionRequestPojo debitInstructionRequest) throws ParseException{
    	
    	Account account =  new Account();
    	account = accountService.getAccountByAccountNumber(debitInstructionRequest.getDebitInstructionRequest().getAccountNumber());
    	Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(date);
       
        DebitInstructionResponsePojo  debitInstructionResponsePojo = new DebitInstructionResponsePojo();   
        DebitTransferRequest debitTransferRequest = new DebitTransferRequest();
    	debitTransferRequest.setTransactionId(debitInstructionRequest.getDebitInstructionRequest().getTransactionId());
    	debitTransferRequest.setAccountNumber(debitInstructionRequest.getDebitInstructionRequest().getAccountNumber());
    	debitTransferRequest.setAccountType(debitInstructionRequest.getDebitInstructionRequest().getAccountType());
    	debitTransferRequest.setAmount(debitInstructionRequest.getDebitInstructionRequest().getAmount());
    	debitTransferRequest.setDebitorName(debitInstructionRequest.getDebitInstructionRequest().getDebitorName());
    	debitTransferRequest.setPaymentInfo(debitInstructionRequest.getDebitInstructionRequest().getPaymentInfo());
        
    	if(account != null) {
    		if(!account.getCreditorStatus().equals("HOLD") ) {
    			if(debitInstructionRequest.getDebitInstructionRequest().getRequestTime() != null) {
	        		Date requestTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(debitInstructionRequest.getDebitInstructionRequest().getRequestTime());
	        		debitTransferRequest.setRequestTime(requestTime);
	        	}
	        	
	        	debitTransferRequest =  debitTransferRequestService.save(debitTransferRequest);
	            
	            if (debitTransferRequest.getId() == null) {
	            	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found");
	            }
	            
	            DebitInstructionResponse debitInstructionResponse =  new DebitInstructionResponse();
	            debitInstructionResponse.setTransactionId(debitTransferRequest.getTransactionId());
	            debitInstructionResponse.setStatus("SUCCESS");
	            debitInstructionResponse.setAccountNumber(debitTransferRequest.getAccountNumber());
	            debitInstructionResponse.setAmount(debitTransferRequest.getAmount());
	            debitInstructionResponse.setAddtInfo(debitTransferRequest.getPaymentInfo());
	            debitInstructionResponse.setResponseTime(strDate);
	            
	            debitInstructionResponsePojo.setDebitInstructionResponse(debitInstructionResponse);
    		}else {
    			 DebitInstructionResponse debitInstructionResponse =  new DebitInstructionResponse();
                 debitInstructionResponse.setTransactionId(debitTransferRequest.getTransactionId());
                 debitInstructionResponse.setStatus("FAILED");
                 debitInstructionResponse.setReason("Status Rekening HOLD");
                 debitInstructionResponse.setAccountNumber(debitTransferRequest.getAccountNumber());
                 debitInstructionResponse.setAmount(debitTransferRequest.getAmount());
                 debitInstructionResponse.setAddtInfo(debitTransferRequest.getPaymentInfo());
                 debitInstructionResponse.setResponseTime(strDate);
                 
                 debitInstructionResponsePojo.setDebitInstructionResponse(debitInstructionResponse);
    		}
	        	
    	}else {
    		 DebitInstructionResponse debitInstructionResponse =  new DebitInstructionResponse();
             debitInstructionResponse.setTransactionId(debitTransferRequest.getTransactionId());
             debitInstructionResponse.setStatus("FAILED");
             debitInstructionResponse.setReason("Rekening Tidak Ditemukan");
             debitInstructionResponse.setAccountNumber(debitTransferRequest.getAccountNumber());
             debitInstructionResponse.setAmount(debitTransferRequest.getAmount());
             debitInstructionResponse.setAddtInfo(debitTransferRequest.getPaymentInfo());
             debitInstructionResponse.setResponseTime(strDate);
             
             debitInstructionResponsePojo.setDebitInstructionResponse(debitInstructionResponse);
    	}
    	
        
        return  debitInstructionResponsePojo;

    }
}
