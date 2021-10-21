package bifast.outbound.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bifast.outbound.model.AccountEnquiry;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.repository.AccountEnquiryRepository;
import bifast.outbound.repository.CreditTransferRepository;

@Service
public class FindMessageService {

	@Autowired
	private AccountEnquiryRepository accountEnquiryRepo;
	@Autowired
	private CreditTransferRepository creditTransferRepo;
//	@Autowired
//	private FICreditTransferRepository fiCreditTransferRepo;

	public String findByBizMsgIdr (String bizMsgIdr) {
		
		String strResult = findAccountEnquiry(bizMsgIdr);
		
		if (null == strResult) {
			strResult = findCreditTransfer(bizMsgIdr);
//			if (null == strResult)
//				strResult = findFICreditTransfer(bizMsgIdr);
		}
			
		return strResult;
		
	}
	
	public String findAccountEnquiry (String bizMsgIdr) {
		
		List<AccountEnquiry> listAE = accountEnquiryRepo.findAllByBizMsgIdr(bizMsgIdr);
		if (listAE.size() > 0 )
			return listAE.get(0).getFullRequestMessage();
		else 
			return null;
	}

	public String findCreditTransfer (String bizMsgIdr) {
		
		List<CreditTransfer> listCT = creditTransferRepo.findByCrdtTrnRequestBizMsgIdr(bizMsgIdr);
		if (listCT.size() > 0 )
			return listCT.get(0).getFullRequestMessage();
		else 
			return null;
	}

//	public String findFICreditTransfer (String bizMsgIdr) {
//		
//		Optional<FICreditTransfer> optFICT = fiCreditTransferRepo.findByRequestBizMsgIdr(bizMsgIdr);
//		if (optFICT.isPresent() )
//			return optFICT.get().getFullRequestMessage();
//		else 
//			return null;
//	}

//	public List<String> findByEndToEndId (String endToEndId) {
//		List<>
//	}
}
