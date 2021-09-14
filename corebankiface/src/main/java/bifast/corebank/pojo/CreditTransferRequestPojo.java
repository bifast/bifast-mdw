package bifast.corebank.pojo;

import java.math.BigDecimal;

public class CreditTransferRequestPojo {

	private String transactionId;
	private String categoryPurpose; //  CrdtTrn

	private BigDecimal amount; // AcctEnq CrdtTrn
	
	private String crdtId;  // CrdtTrn
	private String crdtIdType; // CrdtTrn
	private String crdtAccountNo;   // AcctEnq CrdtTrn
	private String crdtAccountType;  // CrdtTrn
	
	private String paymentInfo;  // CrdtTrn

}
