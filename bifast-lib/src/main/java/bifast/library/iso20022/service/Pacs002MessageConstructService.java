package bifast.library.iso20022.service;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.PartyAddtInfo;
import bifast.library.iso20022.custom.SupplEnvl;
import bifast.library.iso20022.pacs002.BranchAndFinancialInstitutionIdentification6;
import bifast.library.iso20022.pacs002.FIToFIPaymentStatusReportV11;
import bifast.library.iso20022.pacs002.FinancialInstitutionIdentification18;
import bifast.library.iso20022.pacs002.GenericFinancialIdentification1;
import bifast.library.iso20022.pacs002.GroupHeader91;
import bifast.library.iso20022.pacs002.OriginalGroupHeader17;
import bifast.library.iso20022.pacs002.OriginalTransactionReference31;
import bifast.library.iso20022.pacs002.Party40Choice;
import bifast.library.iso20022.pacs002.PartyIdentification135;
import bifast.library.iso20022.pacs002.PaymentTransaction123;
import bifast.library.iso20022.pacs002.StatusReason6Choice;
import bifast.library.iso20022.pacs002.StatusReasonInformation12;
import bifast.library.iso20022.pacs002.SupplementaryData1;



@Service
public class Pacs002MessageConstructService {

	@Autowired
	private UtilService utilService;

	public FIToFIPaymentStatusReportV11 creditTransferRequestResponse (Pacs002Seed seed, 
							BusinessMessage orgnlMessage) throws DatatypeConfigurationException {
		
		FIToFIPaymentStatusReportV11 pacs002 = new FIToFIPaymentStatusReportV11();

		// GrpHdr
		GroupHeader91 grpHdr = new GroupHeader91();
		grpHdr.setMsgId(utilService.genMessageId(seed.getTrnType()));
		
		GregorianCalendar gcal = new GregorianCalendar();
		XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		grpHdr.setCreDtTm(xcal);
		
		pacs002.setGrpHdr(grpHdr);

		// OrgnlGrpInfAndSts
		OriginalGroupHeader17 OrgnlGrpInfAndSts = new OriginalGroupHeader17();
		OrgnlGrpInfAndSts.setOrgnlMsgId(orgnlMessage.getDocument().getFiToFICstmrCdtTrf().getGrpHdr().getMsgId());
		OrgnlGrpInfAndSts.setOrgnlMsgNmId( orgnlMessage.getAppHdr().getMsgDefIdr() );
		
		pacs002.getOrgnlGrpInfAndSts().add(OrgnlGrpInfAndSts);
		
		// TxInfAndSts
		
		PaymentTransaction123 txInfAndSts = new PaymentTransaction123();
		
		txInfAndSts.setOrgnlEndToEndId( orgnlMessage.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtId().getEndToEndId() );
		txInfAndSts.setOrgnlTxId(orgnlMessage.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtId().getTxId() );
		txInfAndSts.setTxSts(seed.getStatus());
		
		// TxInfAndSts / StsRsnInf
		
		StatusReason6Choice rsn = new StatusReason6Choice();
		rsn.setPrtry(seed.getReason());
		StatusReasonInformation12 stsRsnInf = new StatusReasonInformation12();
		stsRsnInf.setRsn(rsn);
		
		stsRsnInf.getAddtlInf().add(seed.getAdditionalInfo());
		txInfAndSts.getStsRsnInf().add(stsRsnInf);
		
		// TxInfAndSts / OrgnlTxRef
		OriginalTransactionReference31 orgnlTxRef = new OriginalTransactionReference31();
		
		orgnlTxRef.setIntrBkSttlmDt(orgnlMessage.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmDt() );

		// TxInfAndSts / OrgnlTxRef / Cdtr
		PartyIdentification135 pty = new PartyIdentification135();
		pty.setNm(seed.getCreditorName());
		Party40Choice cdtr = new Party40Choice();
		cdtr.setPty(pty);
		orgnlTxRef.setCdtr(cdtr);
		
		txInfAndSts.setOrgnlTxRef(orgnlTxRef);
		
		// TxInfAndSts / SplmtryData
		
		PartyAddtInfo supplCdtr = new PartyAddtInfo();
		supplCdtr.setTp(seed.getCreditorType());
		supplCdtr.setId(seed.getCreditorId());
		supplCdtr.setRsdntSts(seed.getCreditorResidentialStatus());
		supplCdtr.setTwnNm(seed.getCreditorTown());
		
		SupplEnvl supplEnv = new SupplEnvl().setCdtr(supplCdtr);
		SupplementaryData1 splmtryData = new SupplementaryData1();
		splmtryData.setEnvlp(supplEnv);

		txInfAndSts.getSplmtryData().add(splmtryData);
	
		pacs002.getTxInfAndSts().add(txInfAndSts);
		
		return pacs002;
	}


	public FIToFIPaymentStatusReportV11 fIFICreditTransferRequestResponse (Pacs002Seed seed, 
			BusinessMessage orgnlMessage) throws DatatypeConfigurationException {
		
		FIToFIPaymentStatusReportV11 pacs002 = new FIToFIPaymentStatusReportV11();

		// GrpHdr
		GroupHeader91 grpHdr = new GroupHeader91();
		grpHdr.setMsgId(utilService.genMessageId(seed.getTrnType()));
		
		GregorianCalendar gcal = new GregorianCalendar();
		XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		grpHdr.setCreDtTm(xcal);
		
		pacs002.setGrpHdr(grpHdr);

		// OrgnlGrpInfAndSts
		OriginalGroupHeader17 orgnlGrpInfAndSts = new OriginalGroupHeader17();
		orgnlGrpInfAndSts.setOrgnlMsgId(orgnlMessage.getDocument().getFiCdtTrf().getGrpHdr().getMsgId());
		orgnlGrpInfAndSts.setOrgnlMsgNmId( orgnlMessage.getAppHdr().getMsgDefIdr() );
		orgnlGrpInfAndSts.setOrgnlCreDtTm(orgnlMessage.getAppHdr().getCreDt());
		
		pacs002.getOrgnlGrpInfAndSts().add(orgnlGrpInfAndSts);
		
		// TxInfAndSts
		
		PaymentTransaction123 txInfAndSts = new PaymentTransaction123();
		
		txInfAndSts.setOrgnlEndToEndId( orgnlMessage.getDocument().getFiCdtTrf().getCdtTrfTxInf().get(0).getPmtId().getEndToEndId() );
		txInfAndSts.setOrgnlTxId(orgnlMessage.getDocument().getFiCdtTrf().getCdtTrfTxInf().get(0).getPmtId().getTxId() );
		txInfAndSts.setTxSts(seed.getStatus());
		
		// TxInfAndSts / StsRsnInf
		
		StatusReason6Choice rsn = new StatusReason6Choice();
		rsn.setPrtry(seed.getReason());
		StatusReasonInformation12 stsRsnInf = new StatusReasonInformation12();
		stsRsnInf.setRsn(rsn);
		
		txInfAndSts.getStsRsnInf().add(stsRsnInf);
		
		// TxInfAndSts / OrgnlTxRef
		OriginalTransactionReference31 orgnlTxRef = new OriginalTransactionReference31();
		
		orgnlTxRef.setIntrBkSttlmDt(orgnlMessage.getDocument().getFiCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmDt() );

		// TxInfAndSts / OrgnlTxRef / Dbtr
		GenericFinancialIdentification1 dbtrOthr = new GenericFinancialIdentification1();
		dbtrOthr.setId( orgnlMessage.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId() );
		FinancialInstitutionIdentification18 dbtrFinInstnId = new FinancialInstitutionIdentification18();
		dbtrFinInstnId.setOthr(dbtrOthr);
		BranchAndFinancialInstitutionIdentification6 dbtrAgt = new BranchAndFinancialInstitutionIdentification6();
		dbtrAgt.setFinInstnId(dbtrFinInstnId);
		Party40Choice dbtr = new Party40Choice();
		dbtr.setAgt(dbtrAgt);

		orgnlTxRef.setDbtr(dbtr);
		
		// TxInfAndSts / OrgnlTxRef / Cdtr
		GenericFinancialIdentification1 cdtrOthr = new GenericFinancialIdentification1();
		cdtrOthr.setId( orgnlMessage.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId() );
		FinancialInstitutionIdentification18 cdtrFinInstnId = new FinancialInstitutionIdentification18();
		cdtrFinInstnId.setOthr(cdtrOthr);
		BranchAndFinancialInstitutionIdentification6 cdtrAgt = new BranchAndFinancialInstitutionIdentification6();
		cdtrAgt.setFinInstnId(dbtrFinInstnId);
		Party40Choice cdtr = new Party40Choice();
		cdtr.setAgt(cdtrAgt);

		orgnlTxRef.setCdtr(cdtr);
		
		txInfAndSts.setOrgnlTxRef(orgnlTxRef);
		
		// TxInfAndSts / SplmtryData
		
		
		pacs002.getTxInfAndSts().add(txInfAndSts);
		
		return pacs002;
	}

	
}
