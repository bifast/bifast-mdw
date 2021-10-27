package bifast.inbound.service;

import java.text.DecimalFormat;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Service;

import bifast.inbound.pojo.flat.FlatPacs002Pojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs002.GroupHeader91;
import bifast.library.iso20022.pacs002.OriginalGroupHeader17;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.library.iso20022.pacs008.FIToFICustomerCreditTransferV08;

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

		flatMsg.setTransactionStatus(txInfAndSts.getTxSts());
		
		if (txInfAndSts.getStsRsnInf().size()>0) {
			flatMsg.setReasonCode(txInfAndSts.getStsRsnInf().get(0).getRsn().getPrtry());

			if (txInfAndSts.getStsRsnInf().get(0).getAddtlInf().size()>0)
				flatMsg.setRsnInfAddtlInf(txInfAndSts.getStsRsnInf().get(0).getAddtlInf().get(0));
			
		}

		if (!(null == txInfAndSts.getOrgnlTxRef())) {
			
			if (!(null == txInfAndSts.getOrgnlTxRef().getIntrBkSttlmDt()))
				flatMsg.setIntrBkSttlmDt(strTgl(txInfAndSts.getOrgnlTxRef().getIntrBkSttlmDt()));
		
			if (!(null == txInfAndSts.getOrgnlTxRef().getDbtr())) {
			
				flatMsg.setDbtrNm(txInfAndSts.getOrgnlTxRef().getDbtr().getPty().getNm());

			}

			if (!(null == txInfAndSts.getOrgnlTxRef().getDbtrAcct())) {

				flatMsg.setDbtrAcctId(txInfAndSts.getOrgnlTxRef().getDbtrAcct().getId().getOthr().getId());
				
				if (!(null== txInfAndSts.getOrgnlTxRef().getDbtrAcct().getTp()))
						flatMsg.setDbtrAcctTp(txInfAndSts.getOrgnlTxRef().getDbtrAcct().getTp().getPrtry());
				
			}

			if (!(null == txInfAndSts.getOrgnlTxRef().getDbtrAgt())) {
				flatMsg.setDbtrAgtFinInstnId(txInfAndSts.getOrgnlTxRef().getDbtrAgt().getFinInstnId().getOthr().getId());
				
			}

			if (!(null == txInfAndSts.getOrgnlTxRef().getCdtrAgt())) {
				flatMsg.setCdtrAgtFinInstnId(txInfAndSts.getOrgnlTxRef().getCdtrAgt().getFinInstnId().getOthr().getId());
			}
			
			if (!(null == txInfAndSts.getOrgnlTxRef().getCdtr())) {
				
				flatMsg.setCdtrNm(txInfAndSts.getOrgnlTxRef().getCdtr().getPty().getNm());

			}

			if (!(null == txInfAndSts.getOrgnlTxRef().getCdtrAcct())) {
				
				flatMsg.setCdtrAcctId(txInfAndSts.getOrgnlTxRef().getCdtrAcct().getId().getOthr().getId());
				flatMsg.setCdtrAcctTp(txInfAndSts.getOrgnlTxRef().getCdtrAcct().getTp().getPrtry());
				
			}
				
		}
		
		if (!(null == txInfAndSts.getSplmtryData())) {
			
			if (!(null == txInfAndSts.getSplmtryData().get(0).getEnvlp().getDbtr())) {
			
				flatMsg.setDbtrTp(txInfAndSts.getSplmtryData().get(0).getEnvlp().getDbtr().getTp());
				flatMsg.setDbtrId(txInfAndSts.getSplmtryData().get(0).getEnvlp().getDbtr().getId());
				flatMsg.setDbtrRsdntSts(txInfAndSts.getSplmtryData().get(0).getEnvlp().getDbtr().getRsdntSts());
				flatMsg.setDbtrTwnNm(txInfAndSts.getSplmtryData().get(0).getEnvlp().getDbtr().getTwnNm());

			}					

			if (!(null == txInfAndSts.getSplmtryData().get(0).getEnvlp().getCdtr())) {
				
				flatMsg.setCdtrTp(txInfAndSts.getSplmtryData().get(0).getEnvlp().getCdtr().getTp());
				flatMsg.setCdtrId(txInfAndSts.getSplmtryData().get(0).getEnvlp().getCdtr().getId());
				flatMsg.setCdtrRsdntSts(txInfAndSts.getSplmtryData().get(0).getEnvlp().getCdtr().getRsdntSts());
				flatMsg.setCdtrTwnNm(txInfAndSts.getSplmtryData().get(0).getEnvlp().getCdtr().getTwnNm());
				
			}

			if (!(null == txInfAndSts.getSplmtryData().get(0).getEnvlp().getDbtrAgtAcct())) {
				flatMsg.setDbtrAgtAcctId(txInfAndSts.getSplmtryData().get(0).getEnvlp().getDbtrAgtAcct().getId().getOthr().getId());
			}
			
			if (!(null == txInfAndSts.getSplmtryData().get(0).getEnvlp().getCdtrAgtAcct())) {
				flatMsg.setCdtrAgtAcctId(txInfAndSts.getSplmtryData().get(0).getEnvlp().getCdtrAgtAcct().getId().getOthr().getId());
			}
			
		}
		
		return flatMsg;
	}
	


	
	public FlatPacs008Pojo flatteningPacs008 (BusinessMessage busMsg) {
		
		FlatPacs008Pojo flatMsg = new FlatPacs008Pojo();

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
		
		FIToFICustomerCreditTransferV08 ct = busMsg.getDocument().getFiToFICstmrCdtTrf();
		
		flatMsg.setMsgId(ct.getGrpHdr().getMsgId()); 
		
		flatMsg.setCreDtTm(strTgl(ct.getGrpHdr().getCreDtTm()));
		
		flatMsg.setSettlementMtd(ct.getGrpHdr().getSttlmInf().getSttlmMtd().value());

		flatMsg.setEndToEndId(ct.getCdtTrfTxInf().get(0).getPmtId().getEndToEndId());
		
		if (!(null == ct.getCdtTrfTxInf().get(0).getPmtId().getTxId()))
			flatMsg.setTransactionId(ct.getCdtTrfTxInf().get(0).getPmtId().getTxId());
		
		if (!(null == ct.getCdtTrfTxInf().get(0).getPmtTpInf())) {
			
			if (!(null == ct.getCdtTrfTxInf().get(0).getPmtTpInf().getLclInstrm()))
				flatMsg.setPaymentChannel(ct.getCdtTrfTxInf().get(0).getPmtTpInf().getLclInstrm().getPrtry());
		
			if (!(null == ct.getCdtTrfTxInf().get(0).getPmtTpInf().getCtgyPurp()))
				flatMsg.setCategoryPurpose(ct.getCdtTrfTxInf().get(0).getPmtTpInf().getCtgyPurp().getPrtry().substring(3, 5));
		}
			
		DecimalFormat df = new DecimalFormat("#############.00");
		flatMsg.setAmount(df.format(ct.getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue()));	
			
		flatMsg.setCurrency(ct.getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getCcy());
		
		flatMsg.setChargeBearer(ct.getCdtTrfTxInf().get(0).getChrgBr().value());
		
		if (!(null == ct.getCdtTrfTxInf().get(0).getDbtr())) {
			
			if (!(null == ct.getCdtTrfTxInf().get(0).getDbtr().getNm())) 
				flatMsg.setDebtorName(ct.getCdtTrfTxInf().get(0).getDbtr().getNm());

			if (!(null == ct.getCdtTrfTxInf().get(0).getDbtr().getId())) {

				if (!(null == ct.getCdtTrfTxInf().get(0).getDbtr().getId().getOrgId()))
					flatMsg.setDebtorOrgId(ct.getCdtTrfTxInf().get(0).getDbtr().getId().getOrgId().getOthr().get(0).getId());
	
				if (!(null == ct.getCdtTrfTxInf().get(0).getDbtr().getId().getPrvtId()))
					flatMsg.setDebtorPrvId(ct.getCdtTrfTxInf().get(0).getDbtr().getId().getPrvtId().getOthr().get(0).getId());

			}
		}

		if (!(null == ct.getCdtTrfTxInf().get(0).getDbtrAcct())) {
			flatMsg.setDebtorAccountNo(ct.getCdtTrfTxInf().get(0).getDbtrAcct().getId().getOthr().getId());

			if (!(null == ct.getCdtTrfTxInf().get(0).getDbtrAcct().getTp()))
				flatMsg.setDebtorAccountType(ct.getCdtTrfTxInf().get(0).getDbtrAcct().getTp().getPrtry());
		}
		
		flatMsg.setDebtorAgentId(ct.getCdtTrfTxInf().get(0).getDbtrAgt().getFinInstnId().getOthr().getId());
		flatMsg.setCreditorAgentId(ct.getCdtTrfTxInf().get(0).getCdtrAgt().getFinInstnId().getOthr().getId());

		if (!(null == ct.getCdtTrfTxInf().get(0).getCdtr())) {
			
			if (!(null == ct.getCdtTrfTxInf().get(0).getCdtr().getNm())) 
				flatMsg.setCreditorName(ct.getCdtTrfTxInf().get(0).getCdtr().getNm());

			if (!(null == ct.getCdtTrfTxInf().get(0).getCdtr().getId())) {

				if (!(null == ct.getCdtTrfTxInf().get(0).getCdtr().getId().getOrgId()))
					flatMsg.setCreditorOrgId(ct.getCdtTrfTxInf().get(0).getCdtr().getId().getOrgId().getOthr().get(0).getId());
	
				if (!(null == ct.getCdtTrfTxInf().get(0).getCdtr().getId().getPrvtId()))
					flatMsg.setCreditorPrvId(ct.getCdtTrfTxInf().get(0).getCdtr().getId().getPrvtId().getOthr().get(0).getId());

			}
		}

		if (!(null == ct.getCdtTrfTxInf().get(0).getCdtrAcct())) {
			flatMsg.setCreditorAccountNo(ct.getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId());

			if (!(null == ct.getCdtTrfTxInf().get(0).getCdtrAcct().getTp()))
				flatMsg.setCreditorAccountType(ct.getCdtTrfTxInf().get(0).getCdtrAcct().getTp().getPrtry());
		}

		if (!(null == ct.getCdtTrfTxInf().get(0).getCdtrAcct().getPrxy())) {
			flatMsg.setCreditorAccountProxyType(ct.getCdtTrfTxInf().get(0).getCdtrAcct().getPrxy().getTp().getPrtry());
			flatMsg.setCreditorAccountProxyType(ct.getCdtTrfTxInf().get(0).getCdtrAcct().getPrxy().getId());
		}

		if (!(null == ct.getCdtTrfTxInf().get(0).getRmtInf())) {
			flatMsg.setPaymentInfo(ct.getCdtTrfTxInf().get(0).getRmtInf().getUstrd().get(0));
		}

		if (ct.getCdtTrfTxInf().get(0).getSplmtryData().size() > 0) {
			
			if (!(null == ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDbtr())) {
				
				if (!(null == ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDbtr().getTp() )) 
					flatMsg.setDebtorType(ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDbtr().getTp());
			
				if (!(null == ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDbtr().getRsdntSts() )) 
					flatMsg.setDebtorResidentialStatus(ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDbtr().getRsdntSts());

				if (!(null == ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDbtr().getTwnNm() )) 
					flatMsg.setDebtorTownName(ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDbtr().getTwnNm());
			}
			
			if (!(null == ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getCdtr())) {
				
				if (!(null == ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getCdtr().getTp() )) 
					flatMsg.setCreditorType(ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getCdtr().getTp());
			
				if (!(null == ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getCdtr().getRsdntSts() )) 
					flatMsg.setCreditorResidentialStatus(ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getCdtr().getRsdntSts());;

				if (!(null == ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getCdtr().getTwnNm() )) 
					flatMsg.setCreditorTownName(ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getCdtr().getTwnNm());
			}

		}

		return flatMsg;
	}

	
	private String strTgl (XMLGregorianCalendar tgl) {
		String strDate = String.format("%04d-%02d-%02dT%02d:%02d:%02d.%03d", 
				tgl.getYear(),tgl.getMonth(), tgl.getDay(),
				tgl.getHour(), tgl.getMinute(), tgl.getSecond(), tgl.getMillisecond());

		return strDate;
	}
	
}
