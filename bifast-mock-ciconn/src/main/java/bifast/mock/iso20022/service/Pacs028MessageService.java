package bifast.mock.iso20022.service;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bifast.mock.iso20022.pacs028.FIToFIPaymentStatusRequestV04;
import bifast.mock.iso20022.pacs028.GroupHeader91;
import bifast.mock.iso20022.pacs028.PaymentTransaction121;



@Service
public class Pacs028MessageService {
	
	@Autowired
	private UtilService utilService;

	public FIToFIPaymentStatusRequestV04 paymentStatusRequest (Pacs028Seed seed) 
			throws DatatypeConfigurationException {
		
		FIToFIPaymentStatusRequestV04 pacs028 = new FIToFIPaymentStatusRequestV04();
		String msgId = utilService.genMessageId("000");     // dari sample file kode 000 dipake unt trn type PaymentStatus

		// GrpHdr
		GroupHeader91 grpHdr = new GroupHeader91();
		grpHdr.setMsgId(msgId);

		GregorianCalendar gcal = new GregorianCalendar();
		XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		grpHdr.setCreDtTm(xcal);

		pacs028.setGrpHdr(grpHdr);
		
		PaymentTransaction121 endToEndId = new PaymentTransaction121();
		endToEndId.setOrgnlEndToEndId(seed.getOrgnlEndToEnd());

		pacs028.getTxInf().add(endToEndId);
		
		return pacs028;
		
	}
	
		
}
