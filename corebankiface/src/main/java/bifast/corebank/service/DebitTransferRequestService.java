package bifast.corebank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bifast.corebank.model.DebitTransferRequest;
import bifast.corebank.repository.DebitTransferRequestRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DebitTransferRequestService {

    @Autowired
    private DebitTransferRequestRepository accountEnquiryRepository;

    public DebitTransferRequest save(DebitTransferRequest debitTransferRequest) {

        return accountEnquiryRepository.save(debitTransferRequest);
    }

    
}
