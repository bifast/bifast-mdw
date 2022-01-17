package bifast.outbound.service;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Service;

import bifast.library.iso20022.admi002.MessageRejectV01;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs002.GroupHeader91;
import bifast.library.iso20022.pacs002.OriginalGroupHeader17;
import bifast.library.iso20022.pacs002.PaymentTransaction110;
import bifast.library.iso20022.pacs008.FIToFICustomerCreditTransferV08;
import bifast.library.iso20022.prxy002.ProxyRegistrationResponseV01;
import bifast.library.iso20022.prxy004.ProxyLookUpResponseV01;
import bifast.library.iso20022.prxy006.ProxyEnquiryInformation1;
import bifast.library.iso20022.prxy006.ProxyEnquiryResponseV01;
import bifast.outbound.pojo.flat.FlatAdmi002Pojo;
import bifast.outbound.pojo.flat.FlatPacs002Pojo;
import bifast.outbound.pojo.flat.FlatPacs008Pojo;
import bifast.outbound.pojo.flat.FlatPrxy002Pojo;
import bifast.outbound.pojo.flat.FlatPrxy004Pojo;
import bifast.outbound.pojo.flat.FlatPrxy006AliasPojo;
import bifast.outbound.pojo.flat.FlatPrxy006Pojo;

@Service
public class FlattenIsoMessageService {

    DateTimeFormatter dtf = DateTimeFormatter.ISO_INSTANT;

	public FlatPacs002Pojo flatteningPacs002 (BusinessMessage busMsg) {

		FlatPacs002Pojo flatMsg = new FlatPacs002Pojo();

		BusinessApplicationHeaderV01 hdr = busMsg.getAppHdr();
		
		flatMsg.setFrBic(hdr.getFr().getFIId().getFinInstnId().getOthr().getId());
		
		flatMsg.setToBic(hdr.getTo().getFIId().getFinInstnId().getOthr().getId());
			
		flatMsg.setBizMsgIdr(hdr.getBizMsgIdr());
		
		flatMsg.setMsgDefIdr(hdr.getMsgDefIdr());

		if (!(null == hdr.getBizSvc()))
			flatMsg.setBizSvc(hdr.getBizSvc());

//		flatMsg.setCreDt(hdr.getCreDt().format(dtf));
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
				if (!(null == txInfAndSts.getOrgnlTxRef().getCdtr().getPty())) {
					
					flatMsg.setCdtrNm(txInfAndSts.getOrgnlTxRef().getCdtr().getPty().getNm());
	
				}
			}

			if (!(null == txInfAndSts.getOrgnlTxRef().getCdtrAcct())) {
				
				flatMsg.setCdtrAcctId(txInfAndSts.getOrgnlTxRef().getCdtrAcct().getId().getOthr().getId());
				if (null != txInfAndSts.getOrgnlTxRef().getCdtrAcct().getTp())
					flatMsg.setCdtrAcctTp(txInfAndSts.getOrgnlTxRef().getCdtrAcct().getTp().getPrtry());
				
			}
				
		}
		
		if (txInfAndSts.getSplmtryData().size()>0) {
			
			if (!(null == txInfAndSts.getSplmtryData().get(0).getEnvlp().getDtl().getDbtr())) {
			
				flatMsg.setDbtrTp(txInfAndSts.getSplmtryData().get(0).getEnvlp().getDtl().getDbtr().getTp());
				flatMsg.setDbtrId(txInfAndSts.getSplmtryData().get(0).getEnvlp().getDtl().getDbtr().getId());
				flatMsg.setDbtrRsdntSts(txInfAndSts.getSplmtryData().get(0).getEnvlp().getDtl().getDbtr().getRsdntSts());
				flatMsg.setDbtrTwnNm(txInfAndSts.getSplmtryData().get(0).getEnvlp().getDtl().getDbtr().getTwnNm());

			}					

			if (!(null == txInfAndSts.getSplmtryData().get(0).getEnvlp().getDtl().getCdtr())) {
				
				flatMsg.setCdtrTp(txInfAndSts.getSplmtryData().get(0).getEnvlp().getDtl().getCdtr().getTp());
				flatMsg.setCdtrId(txInfAndSts.getSplmtryData().get(0).getEnvlp().getDtl().getCdtr().getId());
				flatMsg.setCdtrRsdntSts(txInfAndSts.getSplmtryData().get(0).getEnvlp().getDtl().getCdtr().getRsdntSts());
				flatMsg.setCdtrTwnNm(txInfAndSts.getSplmtryData().get(0).getEnvlp().getDtl().getCdtr().getTwnNm());
				
			}

//			if (!(null == txInfAndSts.getSplmtryData().get(0).getEnvlp().getDbtrAgtAcct())) {
//				flatMsg.setDbtrAgtAcctId(txInfAndSts.getSplmtryData().get(0).getEnvlp().getDbtrAgtAcct().getId().getOthr().getId());
//			}
//			
//			if (!(null == txInfAndSts.getSplmtryData().get(0).getEnvlp().getCdtrAgtAcct())) {
//				flatMsg.setCdtrAgtAcctId(txInfAndSts.getSplmtryData().get(0).getEnvlp().getCdtrAgtAcct().getId().getOthr().getId());
//			}
			
		}
		
		return flatMsg;
	}
	
	public FlatAdmi002Pojo flatteningAdmi002 (BusinessMessage busMsg) {
		
		FlatAdmi002Pojo flatMsg = new FlatAdmi002Pojo();

		MessageRejectV01 reject = busMsg.getDocument().getMessageReject();
		
		flatMsg.setAddtData(reject.getRsn().getAddtlData());
//		flatMsg.setDateTime(null);
		flatMsg.setErrorLocation(reject.getRsn().getErrLctn());
		flatMsg.setReason(reject.getRsn().getRjctgPtyRsn());
		flatMsg.setReasonDesc(reject.getRsn().getRsnDesc());
		flatMsg.setRefId(reject.getRltdRef().getRef());
		
		return flatMsg;
	}

	public FlatPrxy002Pojo flatteningPrxy002 (BusinessMessage busMsg) {
		FlatPrxy002Pojo flatMsg = new FlatPrxy002Pojo();
		ProxyRegistrationResponseV01 data = busMsg.getDocument().getPrxyRegnRspn();
		
		flatMsg.setMsgId(data.getGrpHdr().getMsgId());
		flatMsg.setCreDtTm(strTgl(data.getGrpHdr().getCreDtTm()));		
		flatMsg.setResponseCode(data.getRegnRspn().getPrxRspnSts().value());
		flatMsg.setStatusReason(data.getRegnRspn().getStsRsnInf().getPrtry());
		
		flatMsg.setOrgnlMsgId(data.getOrgnlGrpInf().getOrgnlMsgId());
		flatMsg.setOrgnlMsgName(data.getOrgnlGrpInf().getOrgnlMsgNmId());
		flatMsg.setOrgnlRegistrationType(data.getRegnRspn().getOrgnlRegnTp().value());

		flatMsg.setRegistrationId(data.getRegnRspn().getPrxyRegn().get(0).getRegnId());
		flatMsg.setRegisterBank(data.getRegnRspn().getPrxyRegn().get(0).getAgt().getFinInstnId().getOthr().getId());

		if ((null != data.getSplmtryData()) && 
			(data.getSplmtryData().size() > 0)) {

			if (null != data.getSplmtryData().get(0).getEnvlp().getDtl().getCstmr().getId())
				flatMsg.setCustomerId(data.getSplmtryData().get(0).getEnvlp().getDtl().getCstmr().getId());
			
			if (null != data.getSplmtryData().get(0).getEnvlp().getDtl().getCstmr().getTp())
				flatMsg.setCustomerType(data.getSplmtryData().get(0).getEnvlp().getDtl().getCstmr().getTp());
	
			if (null != data.getOrgnlGrpInf().getOrgnlCreDtTm())
				flatMsg.setOrgnlCreDtTm(strTgl(data.getOrgnlGrpInf().getOrgnlCreDtTm()));
	
			if (null != data.getSplmtryData().get(0).getEnvlp().getDtl().getCstmr().getRsdntSts())
				flatMsg.setResidentialStatus(data.getSplmtryData().get(0).getEnvlp().getDtl().getCstmr().getRsdntSts());
			
			if (null != data.getSplmtryData().get(0).getEnvlp().getDtl().getCstmr().getTwnNm())
				flatMsg.setTownName(data.getSplmtryData().get(0).getEnvlp().getDtl().getCstmr().getTwnNm());
		
		}
		
		return flatMsg;
	}
	
	public FlatPrxy004Pojo flatteningPrxy004 (BusinessMessage busMsg) {
		
		FlatPrxy004Pojo flatMsg = new FlatPrxy004Pojo();
		ProxyLookUpResponseV01 prxResp = busMsg.getDocument().getPrxyLookUpRspn();
		
		if (null != prxResp.getLkUpRspn().getRegnRspn().getRegn()) {
			flatMsg.setDisplayName(prxResp.getLkUpRspn().getRegnRspn().getRegn().getDsplNm());
			flatMsg.setAccountType(prxResp.getLkUpRspn().getRegnRspn().getRegn().getAcct().getTp().getPrtry());
			flatMsg.setAccountNumber(prxResp.getLkUpRspn().getRegnRspn().getRegn().getAcct().getId().getOthr().getId());
			flatMsg.setRegisterBank(prxResp.getLkUpRspn().getRegnRspn().getRegn().getAgt().getFinInstnId().getOthr().getId());
			flatMsg.setRegistrationId(prxResp.getLkUpRspn().getRegnRspn().getRegn().getRegnId());

			if (null != prxResp.getLkUpRspn().getRegnRspn().getRegn().getAcct().getNm())
				flatMsg.setAccountName(prxResp.getLkUpRspn().getRegnRspn().getRegn().getAcct().getNm());
		}

		flatMsg.setResponseCode(prxResp.getLkUpRspn().getRegnRspn().getPrxRspnSts().value());
		flatMsg.setReasonCode(prxResp.getLkUpRspn().getRegnRspn().getStsRsnInf().getPrtry());
		
		flatMsg.setProxyType(prxResp.getLkUpRspn().getOrgnlPrxyRtrvl().getTp());
		flatMsg.setProxyValue(prxResp.getLkUpRspn().getOrgnlPrxyRtrvl().getVal());

		if (prxResp.getSplmtryData().size() > 0 ) {

			if (null != prxResp.getSplmtryData().get(0).getEnvlp().getDtl().getCstmr().getTp())
				flatMsg.setCustomerType(prxResp.getSplmtryData().get(0).getEnvlp().getDtl().getCstmr().getTp());
		
			if (null != prxResp.getSplmtryData().get(0).getEnvlp().getDtl().getCstmr().getId())
				flatMsg.setCustomerId(prxResp.getSplmtryData().get(0).getEnvlp().getDtl().getCstmr().getId());
			
			if (null != prxResp.getSplmtryData().get(0).getEnvlp().getDtl().getCstmr().getRsdntSts())
				flatMsg.setResidentialStatus(prxResp.getSplmtryData().get(0).getEnvlp().getDtl().getCstmr().getRsdntSts());
	
			if (null != prxResp.getSplmtryData().get(0).getEnvlp().getDtl().getCstmr().getTwnNm())
				flatMsg.setTownName(prxResp.getSplmtryData().get(0).getEnvlp().getDtl().getCstmr().getTwnNm());
			
		}
		
		return flatMsg;
	}

	public FlatPrxy006Pojo flatteningPrxy006 (BusinessMessage busMsg) {
		
		FlatPrxy006Pojo flatMsg = new FlatPrxy006Pojo();
		ProxyEnquiryResponseV01 response = busMsg.getDocument().getPrxyNqryRspn();
		
		if (response.getNqryRspn().getRspn().size() != 0) {
			
			List<FlatPrxy006AliasPojo> aliasList = new ArrayList<FlatPrxy006AliasPojo>();
			
			for(ProxyEnquiryInformation1 data:response.getNqryRspn().getRspn()) {
				FlatPrxy006AliasPojo flatMsgAlias = new FlatPrxy006AliasPojo();
				
				flatMsgAlias.setDisplayName(data.getDsplNm());
				flatMsgAlias.setAccountType(data.getAcctInf().getAcct().getTp().getPrtry());
				flatMsgAlias.setAccountNumber(data.getAcctInf().getAcct().getId().getOthr().getId());
				flatMsgAlias.setRegisterBank(data.getAcctInf().getAgt().getFinInstnId().getOthr().getId());
				flatMsgAlias.setRegistrationId(data.getRegnId());
				flatMsgAlias.setAccountName(data.getAcctInf().getAcct().getNm());
				
				flatMsgAlias.setProxyType(data.getPrxyInf().getTp());
				flatMsgAlias.setProxyValue(data.getPrxyInf().getVal());
				flatMsgAlias.setProxySatus(data.getPrxyInf().getSts());
				
				if (data.getSplmtryData() != null) {

					if (null != data.getSplmtryData() .getEnvlp().getCstmr().getTp())
						flatMsgAlias.setCustomerType(data.getSplmtryData() .getEnvlp().getCstmr().getTp());
				
					if (null != data.getSplmtryData() .getEnvlp().getCstmr().getId())
						flatMsgAlias.setCustomerId(data.getSplmtryData() .getEnvlp().getCstmr().getId());
					
					if (null != data.getSplmtryData() .getEnvlp().getCstmr().getRsdntSts())
						flatMsgAlias.setResidentialStatus(data.getSplmtryData() .getEnvlp().getCstmr().getRsdntSts());
			
					if (null != data.getSplmtryData() .getEnvlp().getCstmr().getTwnNm())
						flatMsgAlias.setTownName(data.getSplmtryData().getEnvlp().getCstmr().getTwnNm());
					
				}
				aliasList.add(flatMsgAlias);
			}
			
			flatMsg.setAlias(aliasList);
		}
		
		flatMsg.setResponseCode(response.getNqryRspn().getPrxRspnSts().value());
		flatMsg.setReasonCode(response.getNqryRspn().getStsRsnInf().getPrtry());
		
		
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
		
//		flatMsg.setCreDt(hdr.getCreDt().format(dtf));
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
				flatMsg.setCategoryPurpose(ct.getCdtTrfTxInf().get(0).getPmtTpInf().getCtgyPurp().getPrtry());
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
			
			if (!(null == ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getDbtr())) {
				
				if (!(null == ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getDbtr().getTp() )) 
					flatMsg.setDebtorType(ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getDbtr().getTp());
			
				if (!(null == ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getDbtr().getRsdntSts() )) 
					flatMsg.setDebtorResidentialStatus(ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getDbtr().getRsdntSts());

				if (!(null == ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getDbtr().getTwnNm() )) 
					flatMsg.setDebtorTownName(ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getDbtr().getTwnNm());
			}
			
			if (!(null == ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getCdtr())) {
				
				if (!(null == ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getCdtr().getTp() )) 
					flatMsg.setCreditorType(ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getCdtr().getTp());
			
				if (!(null == ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getCdtr().getRsdntSts() )) 
					flatMsg.setCreditorResidentialStatus(ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getCdtr().getRsdntSts());;

				if (!(null == ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getCdtr().getTwnNm() )) 
					flatMsg.setCreditorTownName(ct.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getCdtr().getTwnNm());
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
