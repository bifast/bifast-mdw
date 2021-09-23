package bifast.outbound.processor;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Service;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs002.GroupHeader91;
import bifast.library.iso20022.pacs002.OriginalGroupHeader17;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.outbound.pojo.FlatAdmi002Pojo;
import bifast.outbound.pojo.FlatPacs002Pojo;
import bifast.outbound.pojo.FlatPacs008Pojo;
import bifast.outbound.pojo.FlatPrxy004Pojo;
import bifast.outbound.pojo.FlatPrxy006Pojo;

@Service
public class FlattenIsoMessageService {

	public FlatPacs002Pojo flatteningPacs002 (BusinessMessage busMsg) {

		FlatPacs002Pojo flatMsg = new FlatPacs002Pojo();

		BusinessApplicationHeaderV01 hdr = busMsg.getAppHdr();
		
		flatMsg.setFrBic(hdr.getFr().getFIId().getFinInstnId().getOthr().getId());
		
		flatMsg.setToBic(hdr.getTo().getFIId().getFinInstnId().getOthr().getId());
			
		flatMsg.setBizMsgIdr(hdr.getBizMsgIdr());
		
		flatMsg.setMsgDefIdr(hdr.getMsgDefIdr());

		if (!(null == hdr.getBizSvc()))
			flatMsg.setBizSvc(hdr.getBizSvc());

		
		flatMsg.setCreDt(strTgl(hdr.getCreDt()));

		if (!(null == hdr.getCpyDplct()))
			flatMsg.setCpyDplct(hdr.getCpyDplct().value());

		if (!(null == hdr.isPssblDplct()))
			flatMsg.setPssblDplct(hdr.isPssblDplct());
		
		GroupHeader91 grpHdr = busMsg.getDocument().getFiToFIPmtStsRpt().getGrpHdr();

		flatMsg.setMsgId(grpHdr.getMsgId());
		
		flatMsg.setCreDtTm(strTgl(grpHdr.getCreDtTm()));
		
		OriginalGroupHeader17 orgnlGrpInf = new OriginalGroupHeader17();
		if (busMsg.getDocument().getFiToFIPmtStsRpt().getOrgnlGrpInfAndSts().size()>0) {
			orgnlGrpInf = busMsg.getDocument().getFiToFIPmtStsRpt().getOrgnlGrpInfAndSts().get(0);
		
			if (!(null == orgnlGrpInf.getOrgnlMsgId()))
				flatMsg.setOrgnlMsgId(orgnlGrpInf.getOrgnlMsgId());
	
			if (!(null == orgnlGrpInf.getOrgnlMsgNmId()))
				flatMsg.setOrgnlMsgNmId(orgnlGrpInf.getOrgnlMsgNmId());
			
			if (!(null == orgnlGrpInf.getOrgnlCreDtTm()))
				flatMsg.setOrgnlCreDtTm(strTgl(orgnlGrpInf.getOrgnlCreDtTm()));
		}

		PaymentTransaction110 txInfAndSts = new PaymentTransaction110();

		txInfAndSts = busMsg.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);

		if (!(null == txInfAndSts.getOrgnlEndToEndId()))
			flatMsg.setOrgnlEndToEndId(txInfAndSts.getOrgnlEndToEndId());

		flatMsg.setTxSts(txInfAndSts.getTxSts());
		
		if (txInfAndSts.getStsRsnInf().size()>0) {
			flatMsg.setRsnInf(txInfAndSts.getStsRsnInf().get(0).getRsn().getPrtry());

			if (txInfAndSts.getStsRsnInf().get(0).getAddtlInf().size()>0)
				flatMsg.setRsnInfAddtlInf(txInfAndSts.getStsRsnInf().get(0).getAddtlInf().get(0));
			
		}

		if (!(null == txInfAndSts.getOrgnlTxRef())) {
			
			if (!(null == txInfAndSts.getOrgnlTxRef().getIntrBkSttlmDt()))
				flatMsg.setIntrBkSttlmDt(strTgl(txInfAndSts.getOrgnlTxRef().getIntrBkSttlmDt()));
		
			if (!(null == txInfAndSts.getOrgnlTxRef().getDbtr())) {
			
				flatMsg.setDbtrNm(txInfAndSts.getOrgnlTxRef().getDbtr().getPty().getNm());

				if (!(null == txInfAndSts.getOrgnlTxRef().getDbtr().getAgt()))
					flatMsg.setDbtrAgtFinInstnId(txInfAndSts.getOrgnlTxRef().getDbtr().getAgt().getFinInstnId().getOthr().getId());
				
				
			}
		}
		
		flatMsg.setCdtrNm(null);

		flatMsg.setCdtrAcctId(null);
		flatMsg.setCdtrAcctTp(null);
		flatMsg.setCdtrAgtAcctId(null);
		flatMsg.setCdtrAgtFinInstnId(null);
		flatMsg.setCdtrId(null);
		flatMsg.setCdtrNm(null);
		
		flatMsg.setCdtrRsdntSts(null);
		
		flatMsg.setCdtrTp(null);
		flatMsg.setCdtrTwnNm(null);
		
		
		flatMsg.setDbtrAcctId(null);
		flatMsg.setDbtrAcctTp(null);
		flatMsg.setDbtrAgtAcctId(null);
		flatMsg.setCdtrAgtFinInstnId(null);
		flatMsg.setCdtrId(null);
		flatMsg.setCdtrRsdntSts(null);
		flatMsg.setCdtrTp(null);
		flatMsg.setCdtrTwnNm(null);
		flatMsg.setCreDtTm(null);
		
		flatMsg.setDbtrAcctId(null);
		flatMsg.setDbtrAcctTp(null);
		flatMsg.setDbtrAgtAcctId(null);
		flatMsg.setDbtrId(null);
		flatMsg.setDbtrRsdntSts(null);
		flatMsg.setDbtrTp(null);
		flatMsg.setDbtrTwnNm(null);
		
		
		
		
		
		flatMsg.setOrgnlTxId(null);
		
		
		
		return flatMsg;
	}
	
	public FlatAdmi002Pojo flatteningAdmi002 (BusinessMessage busMsg) {
		
		FlatAdmi002Pojo flatMsg = new FlatAdmi002Pojo();

		
		
		return flatMsg;
	}

	public FlatPrxy004Pojo flatteningPrxy004 (BusinessMessage busMsg) {
		
		FlatPrxy004Pojo flatMsg = new FlatPrxy004Pojo();

		
		
		return flatMsg;
	}

	public FlatPrxy006Pojo flatteningPrxy006 (BusinessMessage busMsg) {
		
		FlatPrxy006Pojo flatMsg = new FlatPrxy006Pojo();

		
		
		return flatMsg;
	}
	
	public FlatPacs008Pojo flatteningPacs008 (BusinessMessage busMsg) {
		
		FlatPacs008Pojo flatMsg = new FlatPacs008Pojo();

		
		
		return flatMsg;
	}

	private String strTgl (XMLGregorianCalendar tgl) {
		String strDate = String.format("%04d-%02d-%02dT%02d:%02d:%02d.%03d", 
				tgl.getYear(),tgl.getMonth(), tgl.getDay(),
				tgl.getHour(), tgl.getMinute(), tgl.getSecond(), tgl.getMillisecond());

		return strDate;
	}
	
}
