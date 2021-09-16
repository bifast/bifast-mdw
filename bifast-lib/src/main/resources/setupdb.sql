-- public.account_enquiry definition

-- Drop table

-- DROP TABLE public.account_enquiry;

CREATE TABLE public.account_enquiry (
	id int8 NOT NULL,
	account_no varchar(255) NULL,
	cre_dt timestamp NULL,
	intr_ref_id varchar(255) NULL,
	orign_bank varchar(255) NULL,
	recpt_bank varchar(255) NULL,
	amount numeric(19, 2) NULL,
	log_message_id int8 NULL,
	req_biz_msg_id varchar(255) NULL,
	resp_status varchar(255) NULL,
	CONSTRAINT account_enquiry_pkey PRIMARY KEY (id)
);


-- public.account_proxy definition

-- Drop table

-- DROP TABLE public.account_proxy;

CREATE TABLE public.account_proxy (
	id varchar(255) NOT NULL,
	account_name varchar(255) NULL,
	account_number varchar(255) NULL,
	account_type varchar(255) NULL,
	customer_id varchar(255) NULL,
	customer_type varchar(255) NULL,
	display_name varchar(255) NULL,
	proxy_type varchar(255) NULL,
	proxy_value varchar(255) NULL,
	resident_status varchar(255) NULL,
	scnd_id_type varchar(255) NULL,
	scnd_value varchar(255) NULL,
	town_name varchar(255) NULL,
	CONSTRAINT account_proxy_pkey PRIMARY KEY (id)
);


-- public.credit_transfer definition

-- Drop table

-- DROP TABLE public.credit_transfer;

CREATE TABLE public.credit_transfer (
	id int8 NOT NULL,
	settlconf_bizmsgid varchar(255) NULL,
	amount numeric(19, 2) NULL,
	crdttrn_req_bizmsgid varchar(255) NULL,
	cre_dt timestamp NULL,
	creditor_acct_no varchar(255) NULL,
	creditor_acct_type varchar(255) NULL,
	creditor_id varchar(255) NULL,
	creditor_type varchar(255) NULL,
	debtor_acct_no varchar(255) NULL,
	debtor_acct_type varchar(255) NULL,
	debtor_id varchar(255) NULL,
	debtor_type varchar(255) NULL,
	intr_ref_id varchar(255) NULL,
	log_message_id int8 NULL,
	msg_type varchar(255) NULL,
	orign_bank varchar(255) NULL,
	recpt_bank varchar(255) NULL,
	reversal varchar(255) NULL,
	status varchar(255) NULL,
	CONSTRAINT credit_transfer_pkey PRIMARY KEY (id)
);


-- public.domain_code definition

-- Drop table

-- DROP TABLE public.domain_code;

CREATE TABLE public.domain_code (
	id int8 NOT NULL,
	grp varchar(255) NULL,
	"key" varchar(255) NULL,
	value varchar(255) NULL,
	CONSTRAINT domain_code_pkey PRIMARY KEY (id)
);


-- public.fault_class definition

-- Drop table

-- DROP TABLE public.fault_class;

CREATE TABLE public.fault_class (
	id int8 NOT NULL,
	exception_class varchar(255) NULL,
	reason varchar(255) NULL,
	CONSTRAINT fault_class_pkey PRIMARY KEY (id)
);


-- public.inbound_counter definition

-- Drop table

-- DROP TABLE public.inbound_counter;

CREATE TABLE public.inbound_counter (
	tanggal int4 NOT NULL,
	last_number int4 NULL,
	CONSTRAINT inbound_counter_pkey PRIMARY KEY (tanggal)
);


-- public.inbound_message definition

-- Drop table

-- DROP TABLE public.inbound_message;

CREATE TABLE public.inbound_message (
	id int8 NOT NULL,
	bizmsgid varchar(255) NULL,
	cihub_request_time timestamp NULL,
	cihub_response_time timestamp NULL,
	copydupl varchar(255) NULL,
	orgn_bank varchar(255) NULL,
	full_request_msg varchar(4000) NULL,
	full_response_msg varchar(4000) NULL,
	msg_name varchar(255) NULL,
	resp_bizmsgidr varchar(255) NULL,
	resp_reject_msg varchar(255) NULL,
	resp_status varchar(255) NULL,
	bizsvc varchar(255) NULL,
	copy_msg int4 NULL,
	dupl varchar(255) NULL,
	CONSTRAINT inbound_message_pkey PRIMARY KEY (id)
);


-- public.m_bic definition

-- Drop table

-- DROP TABLE public.m_bic;

CREATE TABLE public.m_bic (
	bank_code varchar(255) NOT NULL,
	bank_name varchar(255) NULL,
	bic_code varchar(255) NULL,
	change_who varchar(255) NULL,
	created_date timestamp NULL,
	last_update_date timestamp NULL,
	CONSTRAINT m_bic_pkey PRIMARY KEY (bank_code)
);


-- public.message_counter definition

-- Drop table

-- DROP TABLE public.message_counter;

CREATE TABLE public.message_counter (
	tanggal int8 NOT NULL,
	last_number int8 NULL,
	CONSTRAINT message_counter_pkey PRIMARY KEY (tanggal)
);


-- public.outbound_message definition

-- Drop table

-- DROP TABLE public.outbound_message;

CREATE TABLE public.outbound_message (
	id int8 NOT NULL,
	bizmsgid varchar(255) NULL,
	channel varchar(255) NULL,
	chnl_req_time timestamp NULL,
	chnl_resp_time timestamp NULL,
	cihub_req_time timestamp NULL,
	cihub_resp_time timestamp NULL,
	error_msg varchar(500) NULL,
	full_request_msg varchar(4000) NULL,
	full_response_msg varchar(4000) NULL,
	intrn_ref_id varchar(255) NULL,
	message_name varchar(255) NULL,
	recpt_bank varchar(255) NULL,
	resp_bizmsgid varchar(255) NULL,
	resp_status varchar(255) NULL,
	CONSTRAINT outbound_message_pkey PRIMARY KEY (id)
);


-- public.payment_status definition

-- Drop table

-- DROP TABLE public.payment_status;

CREATE TABLE public.payment_status (
	id int8 NOT NULL,
	bizmsgid varchar(255) NULL,
	error_msg varchar(255) NULL,
	orgn_endtoendid varchar(255) NULL,
	retry_count int4 NOT NULL,
	status varchar(255) NULL,
	update_dt timestamp NULL,
	saf varchar(255) NULL,
	CONSTRAINT payment_status_pkey PRIMARY KEY (id)
);


-- public.proxy_message definition

-- Drop table

-- DROP TABLE public.proxy_message;

CREATE TABLE public.proxy_message (
	id int8 NOT NULL,
	account_name varchar(255) NULL,
	account_number varchar(255) NULL,
	account_type varchar(255) NULL,
	customer_id varchar(255) NULL,
	customer_type varchar(255) NULL,
	display_name varchar(255) NULL,
	log_message_id int8 NULL,
	operation_type varchar(255) NULL,
	proxy_type varchar(255) NULL,
	proxy_value varchar(255) NULL,
	resident_status varchar(255) NULL,
	scnd_id_type varchar(255) NULL,
	scnd_value varchar(255) NULL,
	town_name varchar(255) NULL,
	resp_biz_msg_id varchar(255) NULL,
	resp_reason varchar(255) NULL,
	resp_status varchar(255) NULL,
	CONSTRAINT proxy_message_pkey PRIMARY KEY (id)
);


-- public.settlement definition

-- Drop table

-- DROP TABLE public.settlement;

CREATE TABLE public.settlement (
	id int8 NOT NULL,
	orgnl_crdt_trn_bizmsgid varchar(255) NULL,
	orign_bank varchar(255) NULL,
	recpt_bank varchar(255) NULL,
	settl_conf_bizmsgid varchar(255) NULL,
	crdt_account_no varchar(255) NULL,
	crdt_account_type varchar(255) NULL,
	crdt_id varchar(255) NULL,
	crdt_id_type varchar(255) NULL,
	dbtr_account_no varchar(255) NULL,
	dbtr_account_type varchar(255) NULL,
	dbtr_id varchar(255) NULL,
	dbtr_id_type varchar(255) NULL,
	log_message_id int8 NULL,
	ack varchar(255) NULL,
	amount numeric(19, 2) NULL,
	crdt_name varchar(255) NULL,
	dbtr_name varchar(255) NULL,
	for_reversal varchar(255) NULL,
	full_mesg varchar(6000) NULL,
	reversal_bizmsgid varchar(255) NULL,
	CONSTRAINT settlement_pkey PRIMARY KEY (id)
);


-- Insert Data domain_code
INSERT INTO public.domain_code (id,grp,"key",value) VALUES
	 (311,'PROXY.TYPE','03','IPT ID'),
	 (312,'PROXY.TYPE','01','Mobile Phone No'),
	 (313,'PROXY.TYPE','02','Email Address'),
	 (401,'ACCOUNT.TYPE','CACC','Current Account'),
	 (402,'ACCOUNT.TYPE','SVGS','Saving Account'),
	 (403,'ACCOUNT.TYPE','LOAN','Loan'),
	 (404,'ACCOUNT.TYPE','CCRD','Credit Card'),
	 (405,'ACCOUNT.TYPE','UESB','E-Money'),
	 (406,'ACCOUNT.TYPE','OTHR','Other'),
	 (411,'PROXY.SCDR.TYPE','01','National ID Number');
INSERT INTO public.domain_code (id,grp,"key",value) VALUES
	 (412,'PROXY.SCDR.TYPE','02','Passpord Number'),
	 (421,'CATEGORY.PURPOSE','01','Investment'),
	 (422,'CATEGORY.PURPOSE','02','Transfer of Wealth'),
	 (423,'CATEGORY.PURPOSE','03','Purchase'),
	 (424,'CATEGORY.PURPOSE','99','Others'),
	 (431,'CUSTOMER.RESIDENT.STATUS','01','Resident'),
	 (432,'CUSTOMER.RESIDENT.STATUS','02','Non-Resident'),
	 (101,'CUSTOMER.TYPE','01','Individual'),
	 (102,'CUSTOMER.TYPE','02','Corporate'),
	 (103,'CUSTOMER.TYPE','03','Government');
INSERT INTO public.domain_code (id,grp,"key",value) VALUES
	 (104,'CUSTOMER.TYPE','04','Remittance'),
	 (105,'CUSTOMER.TYPE','99','Others'),
	 (201,'TRANSACTION.TYPE','010','Credit Transfer'),
	 (202,'TRANSACTION.TYPE','019','FI to FI Credit Transfer'),
	 (203,'TRANSACTION.TYPE','011','Reverse Credit Transfer'),
	 (204,'TRANSACTION.TYPE','110','Proxy Credit Transfer'),
	 (205,'TRANSACTION.TYPE','510','Account Enquiry'),
	 (206,'TRANSACTION.TYPE','610','Proxy Lookup'),
	 (207,'TRANSACTION.TYPE','710','Proxy Registration'),
	 (208,'TRANSACTION.TYPE','720','Proxy Maintenance');
INSERT INTO public.domain_code (id,grp,"key",value) VALUES
	 (209,'TRANSACTION.TYPE','000','Network Management Messages'),
	 (301,'CHANNEL.TYPE','01','Internet Banking'),
	 (302,'CHANNEL.TYPE','02','Mobile Banking'),
	 (303,'CHANNEL.TYPE','03','Over the Counter'),
	 (304,'CHANNEL.TYPE','99','Other'),
	 (251,'TRANSACTION.CODE','crdttrns','Credit Transfer'),
	 (252,'TRANSACTION.CODE','ficrdttrns','FI to FI Credit Transfer'),
	 (255,'TRANSACTION.CODE','acctenqr','Account Enquiry'),
	 (501,'REQUEST.CLASS','bifast.outbound.credittransfer.ChnlCreditTransferRequestPojo','Credit Transfer'),
	 (253,'TRANSACTION.CODE','revcdttrns','Reverse Credit Transfer');
INSERT INTO public.domain_code (id,grp,"key",value) VALUES
	 (254,'TRANSACTION.CODE','prxycrdttrns','Proxy Credit Transfer'),
	 (259,'TRANSACTION.CODE','pymtsts','Payment Status'),
	 (258,'TRANSACTION.CODE','prxyrslt','Proxy Resolution'),
	 (257,'TRANSACTION.CODE','prxyrgst','Proxy Registration'),
	 (256,'TRANSACTION.CODE','prxylkup','Proxy Lookup'),
	 (502,'REQUEST.CLASS','bifast.outbound.accountenquiry.ChnlAccountEnquiryRequestPojo','Account Enquiry'),
	 (503,'REQUEST.CLASS','bifast.outbound.ficredittransfer.ChnlFICreditTransferRequestPojo','FI Credit Transfer'),
	 (504,'REQUEST.CLASS','bifast.outbound.proxyregistration.ChnlProxyRegistrationRequestPojo','Proxy Registration'),
	 (505,'REQUEST.CLASS','bifast.outbound.proxyregistration.ChnlProxyResolutionRequestPojo','Proxy Resolution');

COMMIT;

INSERT INTO public.fault_class (id,exception_class,reason) VALUES
	 (1,'com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException','Attribute tidak dikenal'),
	 (2,'com.fasterxml.jackson.core.io.JsonEOFException','Json parsing Exception'),
	 (4,'java.util.NoSuchElementException','Validation Error'),
	 (5,'org.apache.http.conn.HttpHostConnectException','Connection error'),
	 (6,'org.apache.http.NoHttpResponseException','Connection error'),
	 (7,'java.net.SocketException','Connection reset'),
	 (3,'java.net.SocketTimeoutException','Timeout');

INSERT INTO public.m_bic (bank_code,bank_name,bic_code,change_who,created_date,last_update_date) VALUES
	 ('213','BANK TABUNGAN PENSIUNAN NASIONAL','SUNIIDJA','system','2021-08-10 14:35:00','2021-08-10 14:35:00'),
	 ('008','BANK MANDIRI (PERSERO), PT','BMRIIDJA','system','2021-08-10 14:35:00','2021-08-10 14:35:00'),
	 ('014','PT Bank BCA','CENAIDJA','adminBIC@btpn.com','2021-08-12 14:21:28',NULL),
	 ('451','PT Bank Syariah Mandiri','BSMDIDJA','adminBIC@btpn.com','2021-08-12 16:15:45',NULL),
	 ('031','CITIBANK','CITYIDJA','adminBIC@bankmantap.com','2021-08-29 17:09:58','2021-08-29 17:33:04');

COMMIT;
