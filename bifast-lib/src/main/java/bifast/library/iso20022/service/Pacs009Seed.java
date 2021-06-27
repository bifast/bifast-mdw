package bifast.library.iso20022.service;

import java.math.BigDecimal;

public class Pacs009Seed {

	private String trnType;   // AcctEnq & CrdtTrn
	private String bizMsgId;  // AcctEnq CrdtTrn
	private BigDecimal amount; // AcctEnq CrdtTrn
	private String orignBank; //  AcctEnq  CrdtTrn
	private String recptBank; //  AcctEnq  CrdtTrn
	private String channel; //    CrdtTrn
	private String categoryPurpose; //  CrdtTrn
	
	private String dbtrId;   // CrdtTrn
	private String dbtrIdType;  // CrdtTrn
	private String dbtrAccountNo;  // CrdtTrn
	private String dbtrAccountType; // CrdtTrn
	
	private String crdtId;  // CrdtTrn
	private String crdtIdType; // CrdtTrn
	private String crdtAccountNo;   // AcctEnq CrdtTrn
	private String crdtAccountType;  // CrdtTrn
	
	private String paymentInfo;  // CrdtTrn

}
