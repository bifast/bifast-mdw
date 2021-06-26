package library.service;

import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import library.config.Config;
import library.iso20022.head001.BranchAndFinancialInstitutionIdentification6;
import library.iso20022.head001.BusinessApplicationHeaderV02;
import library.iso20022.head001.FinancialInstitutionIdentification18;
import library.iso20022.head001.GenericFinancialIdentification1;
import library.iso20022.head001.Party44Choice;


@Service
public class AppHeaderService {

	@Autowired
	private Config config;
	@Autowired
	private UtilService utilService;
	
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	public BusinessApplicationHeaderV02 initAppHdr(String bicTo, String msgType, String trxType, String channel) {
		
        		
		BusinessApplicationHeaderV02 appHdr = new BusinessApplicationHeaderV02();
		
		Party44Choice fr = new Party44Choice();
		fr.setFIId(new BranchAndFinancialInstitutionIdentification6());
		fr.getFIId().setFinInstnId(new FinancialInstitutionIdentification18());
		fr.getFIId().getFinInstnId().setOthr(new GenericFinancialIdentification1());
		fr.getFIId().getFinInstnId().getOthr().setId(config.getBankcode());
		appHdr.setFr(fr);

		Party44Choice to = new Party44Choice();
		to.setFIId(new BranchAndFinancialInstitutionIdentification6());
		to.getFIId().setFinInstnId(new FinancialInstitutionIdentification18());
		to.getFIId().getFinInstnId().setOthr(new GenericFinancialIdentification1());
		to.getFIId().getFinInstnId().getOthr().setId(bicTo);
		appHdr.setTo(to);

		appHdr.setBizMsgIdr(utilService.genBusMsgId(trxType, channel));
		appHdr.setMsgDefIdr(msgType);
		
		GregorianCalendar gcal = new GregorianCalendar();
		XMLGregorianCalendar xcal;
		try {
//			gcal.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND);
			xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
			appHdr.setCreDt(xcal);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return appHdr;
	}
	



}
