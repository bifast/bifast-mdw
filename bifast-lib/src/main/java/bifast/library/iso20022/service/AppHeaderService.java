package bifast.library.iso20022.service;

import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bifast.library.config.LibConfig;
import bifast.library.iso20022.head001.BranchAndFinancialInstitutionIdentification5;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.head001.FinancialInstitutionIdentification8;
import bifast.library.iso20022.head001.GenericFinancialIdentification1;
import bifast.library.iso20022.head001.Party9Choice;



@Service
public class AppHeaderService {

	@Autowired
	private LibConfig config;
	@Autowired
	private UtilService utilService;
	
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	public BusinessApplicationHeaderV01 initAppHdr(String bicTo, String msgType, String trxType, String channel) {
		
        		
		BusinessApplicationHeaderV01 appHdr = new BusinessApplicationHeaderV01();
		
		Party9Choice fr = new Party9Choice();
		fr.setFIId(new BranchAndFinancialInstitutionIdentification5());
		fr.getFIId().setFinInstnId(new FinancialInstitutionIdentification8());
		fr.getFIId().getFinInstnId().setOthr(new GenericFinancialIdentification1());
		fr.getFIId().getFinInstnId().getOthr().setId(config.getBankcode());
		appHdr.setFr(fr);

		Party9Choice to = new Party9Choice();
		to.setFIId(new BranchAndFinancialInstitutionIdentification5());
		to.getFIId().setFinInstnId(new FinancialInstitutionIdentification8());
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
