package bifast.outbound.repository;

import org.springframework.stereotype.Service;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs002.GroupHeader91;
import bifast.library.iso20022.pacs002.OriginalGroupHeader17;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.outbound.pojo.FlatPacs002Pojo;
import bifast.outbound.pojo.FlatPacs008Pojo;

@Service
public class FlattenIsoMessageService {

	public FlatPacs002Pojo flatteningPacs002 (BusinessMessage busMsg) {
		
		GroupHeader91 grpHdr = busMsg.getDocument().getFiToFIPmtStsRpt().getGrpHdr();
		OriginalGroupHeader17 orgnlGrpInf = busMsg.getDocument().getFiToFIPmtStsRpt().getOrgnlGrpInfAndSts().get(0);
		PaymentTransaction110 txInfAndSts = busMsg.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0);
		
		FlatPacs002Pojo flatMsg = new FlatPacs002Pojo();
		
		flatMsg.setCdtrAcctId(null);
		flatMsg.setCdtrAcctTp(null);
		flatMsg.setCdtrAgtAcctId(null);
		flatMsg.setCdtrAgtFinInstnId(null);
		flatMsg.setCdtrId(null);
		flatMsg.setCdtrNm(null);
		
		flatMsg.setCdtrRsdntSts(null);
		
		flatMsg.setCdtrTp(null);
		flatMsg.setCdtrTwnNm(null);
		
		flatMsg.setCreDtTm(grpHdr.getCreDtTm().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
		
		flatMsg.setDbtrAcctId(null);
		flatMsg.setDbtrAcctTp(null);
		flatMsg.setDbtrAgtAcctId(null);
		flatMsg.setCdtrAgtFinInstnId(null);
		flatMsg.setCdtrId(null);
		flatMsg.setCdtrNm(null);
		flatMsg.setCdtrRsdntSts(null);
		flatMsg.setCdtrTp(null);
		flatMsg.setCdtrTwnNm(null);
		flatMsg.setCreDtTm(null);
		
		flatMsg.setDbtrAcctId(null);
		flatMsg.setDbtrAcctTp(null);
		flatMsg.setDbtrAgtAcctId(null);
		flatMsg.setDbtrAgtFinInstnId(null);
		flatMsg.setDbtrId(null);
		flatMsg.setDbtrNm(null);
		flatMsg.setDbtrRsdntSts(null);
		flatMsg.setDbtrTp(null);
		flatMsg.setDbtrTwnNm(null);
		
		flatMsg.setIntrBkSttlmDt(null);
		
		flatMsg.setMsgId(grpHdr.getMsgId());
		
		if (!(null == orgnlGrpInf.getOrgnlCreDtTm()))
			flatMsg.setOrgnlCreDtTm(orgnlGrpInf.getOrgnlCreDtTm().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
		
		flatMsg.setOrgnlEndToEndId(null);
		
		if (!(null == orgnlGrpInf.getOrgnlMsgId()))
				flatMsg.setOrgnlMsgId(orgnlGrpInf.getOrgnlMsgId());

		if (!(null == orgnlGrpInf.getOrgnlMsgNmId()))
			flatMsg.setOrgnlMsgNmId(orgnlGrpInf.getOrgnlMsgNmId());
		
		flatMsg.setOrgnlTxId(null);
		
		flatMsg.setRsnInf(null);
		flatMsg.setRsnInfAddtlInf(null);
		flatMsg.setTxSts(null);
		
		
		return flatMsg;
	}
	
	public FlatPacs008Pojo flatteningPacs008 (BusinessMessage busMsg) {
		
		FlatPacs008Pojo flatMsg = new FlatPacs008Pojo();

		
		
		return flatMsg;
	}

	
}
