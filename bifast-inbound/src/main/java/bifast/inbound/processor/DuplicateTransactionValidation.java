package bifast.inbound.processor;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.exception.DuplicateTransactionException;
import bifast.inbound.model.CreditTransfer;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.inbound.repository.CreditTransferRepository;
import bifast.inbound.service.FlattenIsoMessageService;
import bifast.inbound.service.UtilService;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs002.FIToFIPaymentStatusReportV10;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Pacs002MessageService;
import bifast.library.iso20022.service.Pacs002Seed;

@Component
public class DuplicateTransactionValidation implements Processor{
	@Autowired private CreditTransferRepository ctRepo;
	@Autowired private AppHeaderService hdrService;
	@Autowired private FlattenIsoMessageService flatService;
	@Autowired private Pacs002MessageService pacs002Service;
	@Autowired private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {

		ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
		BusinessMessage bmRequest = exchange.getMessage().getBody(BusinessMessage.class);

		FlatPacs008Pojo flat008 = flatService.flatteningPacs008(bmRequest);
		
		List<CreditTransfer> lCreditTransfer = ctRepo.findAllByCrdtTrnRequestBizMsgIdr(flat008.getBizMsgIdr());
		
		if ((lCreditTransfer.size()>0) && (!(flat008.getCpyDplct().equals("DUPL")))) {
			BusinessMessage response = ctResponse (bmRequest, processData.getKomiTrnsId());
			exchange.getMessage().setBody(response);

			exchange.getMessage().setBody(response);
			exchange.getMessage().setHeader("hdr_toBIobj", response);

			throw new DuplicateTransactionException("Nomor BizMsgIdr duplikat");

		}
	}
	
	private BusinessMessage ctResponse (BusinessMessage bmRequest, String komiTrnsId) throws Exception {
		FlatPacs008Pojo request = flatService.flatteningPacs008(bmRequest);

		String msgType = request.getBizMsgIdr().substring(16, 19);
		String bizMsgId = utilService.genRfiBusMsgId(msgType, komiTrnsId);
		String msgId = utilService.genMsgId(msgType, komiTrnsId);

		Pacs002Seed seed = new Pacs002Seed();
		
		seed.setAdditionalInfo("No BizMsgIdr sudah pernah dikirim");
		seed.setCreditorAccountIdType(request.getCreditorAccountType());
		seed.setCreditorAccountNo(request.getCreditorAccountNo());
		
		if (null != request.getCreditorPrvId())
			seed.setCreditorId(request.getCreditorPrvId());
		else 
			seed.setCreditorId(request.getCreditorOrgId());
		
		seed.setCreditorName(request.getCreditorName());
		seed.setCreditorResidentialStatus(request.getCreditorResidentialStatus());
		seed.setCreditorTown(request.getCreditorTownName());
		seed.setCreditorType(request.getCreditorType());
		seed.setMsgId(msgId);
		seed.setReason("U149");
		seed.setStatus("RJCT");

		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = hdrService.getAppHdr(request.getFrBic(), "pacs.002.001.10", bizMsgId);

		BusinessMessage bmResponse = new BusinessMessage();
		bmResponse.setAppHdr(hdr);
		
		Document doc = new Document();
		
		FIToFIPaymentStatusReportV10 ctRespDoc = pacs002Service.creditTransferRequestResponse(seed, bmRequest);

		doc.setFiToFIPmtStsRpt(ctRespDoc);
		bmResponse.setDocument(doc);
		
		return bmResponse;
	}
	

}
