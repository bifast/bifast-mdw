package bifast.corebank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bifast.corebank.model.CreditTransferRequest;
import bifast.corebank.repository.CreditTransferRequestRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CreditTransferRequestService {

    @Autowired
    private CreditTransferRequestRepository accountEnquiryRepository;

    public CreditTransferRequest save(CreditTransferRequest creditTransferRequest) {

        return accountEnquiryRepository.save(creditTransferRequest);
    }

    
}
