-- public.account_enquiry definition

-- Drop table

-- DROP TABLE public.account_enquiry;

CREATE TABLE public.account_enquiry (
	id int8 NOT NULL,
	account_no varchar(255) NULL,
	amount numeric(19, 2) NULL,
	cre_dt timestamp NULL,
	intr_ref_id varchar(20) NULL,
	orign_bank varchar(255) NULL,
	recpt_bank varchar(255) NULL,
	bizmsgid varchar(50) NULL,
	call_status varchar(255) NULL,
	cihub_req_time timestamp NULL,
	cihub_resp_time timestamp NULL,
	error_message varchar(400) NULL,
	full_request_msg varchar(4000) NULL,
	full_response_msg varchar(4000) NULL,
	resp_bizmsgid varchar(50) NULL,
	response_status varchar(20) NULL,
	chnl_trx_id int8 NULL,
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


-- public.channel_transaction definition

-- Drop table

-- DROP TABLE public.channel_transaction;

CREATE TABLE public.channel_transaction (
	id int8 NOT NULL,
	amount numeric(19, 2) NULL,
	channel_code varchar(255) NULL,
	creditor_account_name varchar(255) NULL,
	creditor_account_number varchar(255) NULL,
	debtor_account_name varchar(255) NULL,
	debtor_account_number varchar(255) NULL,
	error_msg varchar(255) NULL,
	msg_name varchar(255) NULL,
	recpt_bank varchar(255) NULL,
	request_time timestamp NULL,
	response_time timestamp NULL,
	status varchar(255) NULL,
	transaction_id varchar(255) NULL,
	CONSTRAINT channel_transaction_pkey PRIMARY KEY (id)
);


-- public.corebank_transaction definition

-- Drop table

-- DROP TABLE public.corebank_transaction;

CREATE TABLE public.corebank_transaction (
	transaction_id int8 NOT NULL,
	addt_info varchar(255) NULL,
	channel_ref_id varchar(255) NULL,
	credit_amount numeric(19, 2) NULL,
	creditor_bank varchar(255) NULL,
	cstm_account_name varchar(255) NULL,
	cstm_account_no varchar(255) NULL,
	cstm_account_type varchar(255) NULL,
	debit_amount numeric(19, 2) NULL,
	debtor_bank varchar(255) NULL,
	status varchar(255) NULL,
	transaction_type varchar(255) NULL,
	trns_dt timestamp NULL,
	chnl_trx_id int8 NULL,
	CONSTRAINT corebank_transaction_pkey PRIMARY KEY (transaction_id)
);


-- public.credit_transfer definition

-- Drop table

-- DROP TABLE public.credit_transfer;

CREATE TABLE public.credit_transfer (
	id int8 NOT NULL,
	settlconf_bizmsgid varchar(255) NULL,
	amount numeric(19, 2) NULL,
	call_status varchar(20) NULL,
	cihub_req_time timestamp NULL,
	cihub_resp_time timestamp NULL,
	crdttrn_req_bizmsgid varchar(255) NULL,
	crdttrn_resp_bizmsgid varchar(255) NULL,
	cre_dt timestamp NULL,
	creditor_acct_no varchar(255) NULL,
	creditor_acct_type varchar(255) NULL,
	creditor_id varchar(255) NULL,
	creditor_type varchar(255) NULL,
	debtor_acct_no varchar(255) NULL,
	debtor_acct_type varchar(255) NULL,
	debtor_id varchar(255) NULL,
	debtor_type varchar(255) NULL,
	full_request_msg varchar(4000) NULL,
	full_response_msg varchar(4000) NULL,
	intr_ref_id varchar(255) NULL,
	msg_type varchar(255) NULL,
	orign_bank varchar(255) NULL,
	recpt_bank varchar(255) NULL,
	resp_status varchar(20) NULL,
	reversal varchar(255) NULL,
	chnl_trx_id int8 NULL,
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


-- public.fi_credit_transfer definition

-- Drop table

-- DROP TABLE public.fi_credit_transfer;

CREATE TABLE public.fi_credit_transfer (
	id int8 NOT NULL,
	settlconf_bizmsgid varchar(50) NULL,
	amount numeric(19, 2) NULL,
	cre_dt timestamp NULL,
	credit_bic varchar(255) NULL,
	debtor_bic varchar(255) NULL,
	intr_ref_id varchar(255) NULL,
	log_message_id int8 NULL,
	orign_bank varchar(255) NULL,
	req_bizmsgid varchar(50) NULL,
	saf varchar(20) NULL,
	saf_counter int4 NULL,
	status varchar(255) NULL,
	call_status varchar(20) NULL,
	cihub_req_time timestamp NULL,
	cihub_resp_time timestamp NULL,
	full_request_msg varchar(4000) NULL,
	full_response_msg varchar(4000) NULL,
	resp_bizmsgid varchar(50) NULL,
	response_status varchar(20) NULL,
	chnl_trx_id int8 NULL,
	CONSTRAINT fi_credit_transfer_pkey PRIMARY KEY (id)
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
	bizmsgid varchar(50) NULL,
	cihub_request_time timestamp NULL,
	cihub_response_time timestamp NULL,
	copydupl varchar(20) NULL,
	errormsg varchar(500) NULL,
	orgn_bank varchar(20) NULL,
	full_request_msg varchar(4000) NULL,
	full_response_msg varchar(4000) NULL,
	msg_name varchar(50) NULL,
	resp_bizmsgidr varchar(50) NULL,
	resp_reject_msg varchar(255) NULL,
	resp_status varchar(255) NULL,
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


-- public.mock_names definition

-- Drop table

-- DROP TABLE public.mock_names;

CREATE TABLE public.mock_names (
	"name" varchar(255) NOT NULL,
	CONSTRAINT mock_names_pkey PRIMARY KEY (name)
);


-- public.mock_pacs002 definition

-- Drop table

-- DROP TABLE public.mock_pacs002;

CREATE TABLE public.mock_pacs002 (
	id int8 NOT NULL,
	biz_msg_idr varchar(255) NULL,
	full_message varchar(5000) NULL,
	orgnl_end_to_end_id varchar(255) NULL,
	orgnl_msg_id varchar(255) NULL,
	orgnl_msg_name varchar(255) NULL,
	trx_type varchar(255) NULL,
	CONSTRAINT mock_pacs002_pkey PRIMARY KEY (id)
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


-- public.outbound_parameter definition

-- Drop table

-- DROP TABLE public.outbound_parameter;

CREATE TABLE public.outbound_parameter (
	code varchar(255) NOT NULL,
	notes varchar(255) NULL,
	value varchar(255) NULL,
	CONSTRAINT outbound_parameter_pkey PRIMARY KEY (code)
);


-- public."parameter" definition

-- Drop table

-- DROP TABLE public."parameter";

CREATE TABLE public."parameter" (
	id int4 NOT NULL,
	code varchar(255) NULL,
	"module" varchar(255) NULL,
	notes varchar(255) NULL,
	value varchar(255) NULL,
	CONSTRAINT parameter_pkey PRIMARY KEY (id)
);


-- public.payment_status definition

-- Drop table

-- DROP TABLE public.payment_status;

CREATE TABLE public.payment_status (
	id int8 NOT NULL,
	bizmsgid varchar(50) NULL,
	error_msg varchar(255) NULL,
	intern_ref_id varchar(255) NULL,
	orgn_endtoendid varchar(50) NULL,
	request_dt timestamp NULL,
	request_full_message varchar(5000) NULL,
	response_dt timestamp NULL,
	response_full_message varchar(5000) NULL,
	retry_count int4 NOT NULL,
	saf varchar(255) NULL,
	status varchar(255) NULL,
	chnl_trx_id int8 NULL,
	CONSTRAINT payment_status_pkey PRIMARY KEY (id)
);


-- public.proxy_message definition

-- Drop table

-- DROP TABLE public.proxy_message;

CREATE TABLE public.proxy_message (
	id int8 NOT NULL,
	account_name varchar(100) NULL,
	account_number varchar(255) NULL,
	account_type varchar(255) NULL,
	call_status varchar(50) NULL,
	customer_id varchar(255) NULL,
	customer_type varchar(255) NULL,
	display_name varchar(100) NULL,
	error_message varchar(400) NULL,
	full_request_mesg varchar(4000) NULL,
	full_response_mesg varchar(4000) NULL,
	intrn_ref_id varchar(255) NULL,
	operation_type varchar(255) NULL,
	proxy_type varchar(255) NULL,
	proxy_value varchar(255) NULL,
	request_dt timestamp NULL,
	resident_status varchar(255) NULL,
	resp_status varchar(50) NULL,
	response_dt timestamp NULL,
	scnd_id_type varchar(255) NULL,
	scnd_value varchar(255) NULL,
	town_name varchar(255) NULL,
	chnl_trx_id int8 NULL,
	CONSTRAINT proxy_message_pkey PRIMARY KEY (id)
);


-- public.settlement definition

-- Drop table

-- DROP TABLE public.settlement;

CREATE TABLE public.settlement (
	id int8 NOT NULL,
	crdt_account_no varchar(100) NULL,
	crdt_bank_account_no varchar(100) NULL,
	dbtr_account_no varchar(100) NULL,
	dbtr_bank_account_no varchar(100) NULL,
	orgnl_crdt_trn_bizmsgid varchar(50) NULL,
	orign_bank varchar(20) NULL,
	recpt_bank varchar(20) NULL,
	settl_conf_bizmsgid varchar(50) NULL,
	crdt_account_type varchar(255) NULL,
	crdt_id varchar(255) NULL,
	crdt_id_type varchar(255) NULL,
	dbtr_account_type varchar(255) NULL,
	dbtr_id varchar(255) NULL,
	dbtr_id_type varchar(255) NULL,
	full_message varchar(5000) NULL,
	receive_date timestamp NULL,
	CONSTRAINT settlement_pkey PRIMARY KEY (id)
);

INSERT INTO public.account_enquiry (id,account_no,amount,cre_dt,intr_ref_id,orign_bank,recpt_bank,bizmsgid,call_status,cihub_req_time,cihub_resp_time,error_message,full_request_msg,full_response_msg,resp_bizmsgid,response_status,chnl_trx_id) VALUES
	 (4169,'7712589',1990000.00,'2021-09-24 20:03:35.125','46149','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000060','TIMEOUT-CIHUB','2021-09-24 08:03:35','2021-09-24 08:03:37','Timeout menunggu response dari CIHUB','eJyVUstuwjAQ/BefAdkhPMItiQO4KlCB1UvVAyThoRIHOYtEi/j3rm0itVVRqaU4u94dz3i0ZxId
q0m1IYMzCQ+HcaZNNHT7bpe5vxKqAiVsNoOtrZqMLMQ4EvyBkQuuBpHlnbg4mYaIC6+4aPeBGoQh
Jx71GA08v766w+iMMmpXl5IGwU6er13zYZlWLUr7+LEW7WM11jmH6zVNGjQ9X3p0QNuDdocgEy/T
Y5ErcPqgHIq4gkLHGUi9NocjXZtgFf0uCJffZX5Q88niBmODTFeztTxVWGeYLQD2hVCWycYTsHY8
zkfEGOF0yJNteTmTpwKce4nKZInbLUXfLcIL/lKOZEKBjt6sjLCwjjwv98fcSA0C04mumhem73gk
+Nxg4q3eRMZ5nkQSi3wFxqyLi8IN/GNg7HPvwHwdFodxjBadplA3/oD1eszr9AM7Yq9m+wQOrNZz
',NULL,NULL,NULL,4168),
	 (4173,'1814449',54500.00,'2021-09-24 20:11:08.443','4511168','SIHBIDJ1','BMRIIDJA','20210924SIHBIDJ1510O0100000062','SUCCESS','2021-09-24 08:11:08','2021-09-24 08:11:09',NULL,'eJyVUstuwjAQ/BefAdmRg4BbHPNwVUoFVi9VD5CEh0oc5CwSLeLfu7ahaquiUktx1t6dnfFoj0Ts
63G9Ir0jSXa7UW5dNAj7ZpOHv1GmBqP8aQJrn3UnMlMjoeQdIydcDaKrG3FiPFWIS844sXlHDcqR
k4hGjHYjfmkdMzqhjPrVjkiDYKUslqF4N8/qFqUd/FiLdjCb2kLCuU2TdpsR1xHtMdbDLDLJKtuX
hYGgD6qBSmsobZqDtkt3ObQXE7yi3wVRHjPG2p98urzC2CAPi8lSH2rMMzzNALalMp7Jx2NwFOn9
dEicEUGHPviS5yN5LCG41ze5rnC7pui7RdjgL+VIpgxY8eplJKV35Gm+3RcIjHlMKXrq3pe94YWS
U4dI13YlnO+yLzQm5QKcVacQJSv4x7j4x96A+ToqARMYPTrL4FL4A8Y6jHPe9QP24rYPyXXWMg==
','eJyNUdtOg0AQ/RWzz7aZXSkB3ii0Fo0tKWt8MH0gXCoGFgJbtTb9d2eXYqqxiUPYy+yZOWdmDmS6
6x66LXEOxG2aRdqq07xfiyLtdxGITopA31byRb+qG/FmSzfw71xyRLsmvP5nXBQsphhHT3HT4hM1
BIqcMGAUbGYMqScU1sBAmQFArgki/SzvwU2cdGMAhj8dU/XqtZkvT2lGYI+YwRk4lDpgEWTy62RX
ZUL2+mQ9D8JKRrJbN9p12w4t0Hr+lqONTujAxqvLfPwjELkrUqQgzjN2od2KciZSXuPyg2BoCRKs
4ERiMuTQIZjnAhiMCaXUtIgi0zTE9biHV1VWhyPINTMeVV1hK9s9Yh4xPXZ/851/neXq3Uulrj+U
e7UtVW1PhYhlfBW9xnlblERNTMHcJJHDRH/Nl1rUMAxbQ3lzzuu5nkf00KOmrNDnY2otcCbeyuZc
ggokoNqsc97zcMTAtmwTTBOd6y4Vsi9Yg/i70GrhRld23OCH9gV0B9N7
','20210924CENAIDJA510R0200000400','ACTC',4172),
	 (4171,'1819199',54500.00,'2021-09-24 20:08:40.316','452168','SIHBIDJ1','BMRIIDJA','20210924SIHBIDJ1510O0100000061','SUCCESS','2021-09-24 08:08:40','2021-09-24 08:08:41',NULL,'eJyVUstuwjAQ/BefAa2jBCXc4oSHq1IqsHqpeoAkPFSSIGeRaBH/3rUNVVsVlVqKs/bu7IxHe2Ri
34ybFesdWbzbjXJtooHbN5vc/StZNVhJe5rg2mbNic3kSMj0jrMTrRZT9Y04MZ5KwsVnnNi8kwZp
yJkHHofI8y+tAw4T4GBXl7MWo8q0WLri3TxrOgAhfbwDIWUTXaR4btOGqO35yoMehD0fGDGldbYv
iwqdPqwHMmmw1EmOSi/N5VBfTLCKfhcE4Ace737yqfIKY4s9LCZLdWgob7TPELelrCyTjcdoKJL7
6ZAZI5wOdbAlz0f2WKJzr1/lqqbtmqLvFlGDv5QTmaxQi1crIy6tI0/z7b4gYOAHAOSpeV/2Rhcy
nRpEstYrYXxP+0JRMl2gserkoniF/xgX+9gbMF9HxWEco0VnGV4Kf8B4yCMeRXbAXsz2Ac2r1js=
','eJyNUW1vgjAQ/itLP6tpKzjgWy062TI12GUfFj8wXhyJFAJ1zhn/+65FFrfMZEfoy/W5e567O6Lx
rnlsNsg7IlZVs6TWp2m75nnS7jKQjZKBuS3Um3nVN8Qncxb49wydwHpIlP+MWwWzMcSRc9w4/wQN
gSZHFFOCXWp1qW2CQ0wxGBk6BPUQIP00a8FVFDcDjCn8ZEAwvPI69dU5TR+7fWoJij3seBZGwOSX
8a5IpWr1qXIaLAu1Uk1YGddd3bXA6PlbjjHLcTs2UVznEx+BzJhMgAJ5L9CFeiO3E5mIEpYfBF1L
gGCBzyQjXa8JgTxXwCDFpmTkIE1maBDjgsNVl9XACDLDDEdd17JW9QEwTxAI3V9/5w/TTL/zRJn6
l+qgt7mujRWvUb2PVH7znMskR3pkGsfiWHUj/TVg4hCXuK6BiuqSmDPOkZn6qtoW4PMjFRmFE/m+
rS416ECEdQ9Mzgex7FM8vLVH1LbAGTaJVG3FBiT20sjFQ1PaaQ0f2BeLsdP0
','20210924CENAIDJA510R0200001381','ACTC',4170),
	 (4175,'11844292',8650000.00,'2021-09-24 20:12:55.08','21211','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000063','SUCCESS','2021-09-24 08:12:55','2021-09-24 08:12:55',NULL,'eJyVUstuwjAQ/BefAdluggK3JObhqkAFVi9VDyUJD5U4yFkkWsS/d20Tqa2KSi1ls/bueMajPZHk
UE/qNemfSLzfj3Njs6GP223u/1rqGrR0uxlsXNXuyEKOEynuGTnjahFV3YhLB9MYcfEFl2w/UIO0
5IRTzmiPB83VIaMzyqhb3TvSItgpipVv3r9mdYfSCD/WoRFWU1MIuFzTpr02DxSnfcb7YUiQSVTZ
oSw0eH1QDWVaQ2nSHJRZ2cORaUxwin4XhIszzljDp8orjC0yXc5W6lhj3XYvAHal1I7J5RNwdjzM
R8Qa4XWoo2t5PpHHErx7A52rCsM1Rd8twgv+Uo5kUoNJ3pyMuHSOPL3uDgUCo25oO9FV+8LsHY+k
mFtMujHrxDovBonColiCNevss3gN/xgY99wbMF+HxWM8o0NnGTSNP2CMRUHAe9zN2IsNn8ZI1oM=
','eJyNUdtOwkAQ/RWzz0J2lxZo38oWpBqBtGt8MDyQXrAJ3TbtoiLh352dUoNGEqfp3uZyzpk5ksm+
eWy2xD0Sr6rmSW1Os3bN86TdVaAarQK8LfUres2NiOnCC/x7j5zAboks/5kXBfMJ5LFz3iT/BA6B
ASecckYdbnWlbUZDyikYG9kDcksg0k+zNrjaxE2fUg4/6zMKXlGnvj6X6VGnxy3Jqcu4a9sEkPwy
3hep0i0/Xc6CVaEj3YQVPt3VXQuQz990kIs1GndosriOJz8ClXkqAQjivkAX6q3aTVUiS1h+AHQt
AYAlbUHo0OjFFKhzJRiMM84YMWAIQzwhBVyNrAZGkCEyHI2uVa3rA8Q8QR50f/1dP0wz4xeJRv0r
fTDbwmh7ztVGb26ifZOqkpiBmSgvjnU30F/jZWxsWdzhGCurS1zhCUFw6FG1K+DNh9JIcKredtUl
BZNIqBGGRR/kqsfp0BkPRtT0JWwSpVvBGCTfFbKlA1R2WsMH9gWJV9Ni
','20210924CENAIDJA510R0200001753','ACTC',4174),
	 (4182,'110044292',89994500.00,'2021-09-24 20:27:12.717','211411','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000066','SUCCESS','2021-09-24 08:27:12','2021-09-24 08:27:13',NULL,'eJyVUstuwjAQ/BefAdlWoIRbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEolVRqaU4a+/Ozni0JxIf
qnG1Jr0Tifb7UWZsNPD7dpv5v5a6Ai3daQobl7UnMpejWIpHRs64GkSVd+KS/iRCXHTBxdtP1CAt
OeGUMxryoG7dZnRKGXWr0yENgpUiX/ni/SKtWpR28WMt2sVsYnIBlzZNGjZ5oDjt8Yce4wSZRJke
ilyD1wflQCYVFCbJQJmVvRya2gSn6HdBFK9YwFjNp4objA0yWU5X6lhh3lbPAXaF1I7JxWNwdjzN
hsQa4XWooyt5PZHnArx7fZ2pErdbir5bhA3+Uo5kUoOJ352MqHCOvCx2hxyB3TAMgzalaKt9YvqB
d1LMLCjZmHVsrRf9WGFSLMG6dfZRtIZ/TIx77x2Y62nxGM/o0GkKdeEPGMPnBgEPuZuyN7t9AXxm
1v4=
','eJyNUdtuwjAM/ZUpz4ASU259K20ZZRqgkj1Ne0C9MARNqzZsY4h/n53SiU1Dmqvm5mMfH/vExofq
sdow+8ScopjGJZ0m9brdxvWuAlVpFZjbQr8aL92Y68+dwJs57IzWYjL/Z9wqmI4xTlzixttPrCEg
cgYcBB+B1aTuCR5y4GTdLrAWQ6SXpDW4WEdVh3PAX3QER69bJp6+pGnzURssCdyGgS2AIZOXR4cs
UbquT+eTYJnpla7Cwjzdl00LTD1/l0MmhgNo2GR2m09+BCp1VIwUzH7GLpQbtfdVLHNcfhA0LUGC
Ba9JeL+PHCYE89wAo3ohLCEYkRka5rjSxSvJqnAEqWHGI+lalro8IuYJA7H7L9/5wyQlvxtro3+p
j7TNSZu/y+9m+S5nNCsCOFGkm1n+mqzAiiwLRmDAsrjmdB3XZWbgq2Kf4Zu31mtTnK/e9sU1PQUy
TqJM1ge5bAMXYPWGQH0Pq1jpWqwByXdlKuVdo+r8gh/aF5fm0Uk=
','20210924CENAIDJA510R0200000332','ACTC',4181),
	 (4187,'8000292',8904000.00,'2021-09-24 20:34:10.888','210071','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000069','SUCCESS','2021-09-24 08:34:10','2021-09-24 08:34:11',NULL,'eJyVUstuwjAQ/BefAW3StE24JTEPVwUqsHqpeoAkPFTiIGeRaBH/3rVNpLYqKrUUZ+3d2RmP9siS
fT2qV6x7ZPFuN8y1ifpu32xy91dC1aiEPU1wbbPmxGZimAj+4LETrRaT1ZW4tDeOCRefccnmgzQI
Q8588D2I/KBpfevBBDyw6y5iLUaVvFi64t08qzsAIX1eB0LKprrgeG7ThqjtB9KH7k3Q9YARE6+y
fVkodPqw6ou0xlKnOUq9NJcD3ZhgFf0uCMwV3HsNnywvMLbYeDFZykNNeVM9Q9yWQlkmG4/Q2vE4
HTBjhNMhD7bk5cieSnTu9VQuK9ouKfpuETX4SzmRCYU6ebMy4tI68jzf7gsChhEEVEqumhdm73Ql
+NRg0rVeJcZ53kskJfkCjVknF8Ur/MfA2Odegfk6LA7jGC06y7Ap/AELzWMj347Yq9k+AQBr1lU=
','eJyNUdtugkAQ/ZVmn9UM6wXhDUErNlWC26fGB8JFTWQhsLa1xn/vzCKNbWrSIezuzJyZM5czmxzr
53rL7DNzynKeVPSaNed+nzS39GWtpK+1ldppL2nMnS4d31s47ILSYaL4Z9zan08wzrjGTfafWINP
5IwDN8Digzb10IAQOJCY5pB1GCK9NGvAZRTXPQCOv9EzAL1ulXrqmqYLVpcPBAe7P7DRi0xeER/z
VKqmPlXM/CBXa1WHpTY9Vu0IdD1/l0NiWHzcson8Pp/48GXmyAQpmP2KU6i28jCViSjw+EHQjgQJ
VtCQwMhCDh2Cee6AgUxgGozINA1zXOGiSm3VuIJMM+OT+goqVZ0Q84KBOP3Nd/4wzcjvJkr3H6gT
XUvqzUmi/GGxK+SJ0bYI4sSxarf5a7djKsniGirKW07XcV2mF74uDznavEhFuripfDuUt/QUyICa
0jmfRNDlYPbBHHGOxrBOpGqa1SDxLnWl0NddXTb4oXwB6afRvA==
','20210924CENAIDJA510R0200000775','ACTC',4186),
	 (4193,'2000292',8904000.00,'2021-09-24 21:05:53.379','21861','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000074','SUCCESS','2021-09-24 09:05:53','2021-09-24 09:05:53',NULL,'eJyVUl1PwjAU/S99RtLWoRtv28pHjYCBxhfjA2zjI7KOdJcEJfx3b1uWqJGITdbd9t7Tc+7JPZJk
X4/qFekeSbzbDXNjo77fN5vc/7XUNWjpThNYu6w9kZkcJlI8MHLC1SKquhKX9sYx4uIzLtl8oAZp
yQmnnNGIB83THUYnlFG37gPSIlgpiqUv3s2zuk1piB9r0xCzqSkEnJ+5odENDxRnXdrpdm4JMokq
25eFBq8Pqr5MayhNmoMyS3s5MI0JTtHvgnBxFt6xhk+VFxhbZLyYLNWhxrytngFsS6kdk4tH4Ox4
nA6INcLrUAdX8nIkTyV493o6VxVulxR9twgf+Es5kkkNJnlzMuLSOfI83+4LBIYRDbASXbUdZu94
JcXUYtK1WSXWedFLFCbFAqxZJx/FK/jHwLh2r8B8HRaP8YwOnWXQFP6AcdtrxN2IvdrtExqh1mE=
','eJyNUttugkAQ/ZVmn9XsrnjjDQErbaoEt0+ND4SLJZGFLGutNf57ZwdpbNMmHcJeZ+acM7NnMj80
T82O2Gfi1PUyVWa1aMeiSNtZBrLRMsDdWr/irdkR1185gffgkAtYj4jqn3GbYDmHOHaNmxcfwCEw
4IRTzuiMW13qEaMR5RSMTdmE9Ah4elneOtdx0gwo5fCzAaNw66rM09c0fTrrc0twZtORPRoSQPKq
5FBmUrf8dLUIwlJvdBPVeHSvuhIgn9/poA2tcYcmyr/xxHsgc0emAEHsF6iC2sm9L1NRwfANoCsJ
AKzpFWRiAQaGQJ4/nME4m44ZMWAIQxxXuLA1shpoQY7IsDS6QqXVCXyeIQ6qv/3KH2W5uXdTjfpD
fTLTymjz1bGQd6BCZTEx/TJOTpLorp8/umu6xWccXUV9i+o6rkuw5Zt6X8KZF+sY6fnybV/fEjCB
hBpZmPNRhH1OrfGETy3zCqImlbqVi07iKJErHaKuyxY+sE/BIdKc
','20210924CENAIDJA510R0200001817','ACTC',4192),
	 (4198,'2111132',8904000.00,'2021-09-24 21:14:38.851','21862','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000077','SUCCESS','2021-09-24 09:14:38','2021-09-24 09:14:40',NULL,'eJyVUstuwjAQ/BefAdkmLYFbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEaqui0pXi7Hp3POORTyQ+
VONqTXonEu33o8zYbODX7Tbzfy11BVq6agob17UVmctRLMUDI2eMBlHljbikP4kQF11w8fYDNUhL
TjjljHZ5UB99x+iUMuqi0yENgpMiX/nh/SKtWpSG+LEWDbGbmFzA5Zgm7TZ5oDjrsaDXDgkyiTI9
FLkGrw/KgUwqKEySgTIruzk0tQlO0e+CMDgL73nNp4orjA0yWU5X6lhhn2E1B9gVUjsml4/B2fE4
GxJrhNehjm7k5USeCvDu9XWmSlyuKfpuER7wl3IkkxpM/OZkRIVz5HmxO+QIDLs0wEl01d4wfcct
KWYWk2zMOrbOi36ssCmWYM06+yxawz8ejLvuDZivj8VjPKNDpynUgz9gnGG0uXtir3b5BDHQ1ms=
','eJyNUdtugzAM/ZUpz2uVBNYLbym0K5vWIsiepj4wLh0SBETSdV3Vf58TytRNqzQjcvOxj499RLOd
fJJb5BwRa5pl2urToluLIu124QuphG9ua/VmvPqG3PmK+d4DQyewW8Trf8ZF/nIGceQcNys+oQZf
kyOKKcFTavep7wgOMcXayMRCtwiQXpZ34CZO5BBjCj8ZEgxet808dU4zwNMBtTklDrEda4qAyauT
XZUJ1dWn6oUfVCpSMmzM033bt8DU83c5xkbY7tl4dZ2Pf/giZyIFCuS8QBfarSjnIuU1LD8I+pYA
wRqfScZj4DAhkOcKGIySyYgiTWZoEHO5C1ctS8IIcsMMR60raFV7AMwzxEH3N9/5wyzXfjdVRn+g
DnpbaW3RIa7krrxh1Wvc7mNVID00jWRJovqh/hoxJWAWNVDeXFK7zHWRmXvUlBW8ebGKTY1z8V42
l1XoQIQJiDE5H3kwoBhEj+lUDzuUqVCdZgPie2EKxpYRd9rAB/YFZy3UxA==
','20210924CENAIDJA510R0200000183','ACTC',4197),
	 (4203,'2100323',800000.00,'2021-09-24 21:28:28.654','21002','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000080','SUCCESS','2021-09-24 09:28:28','2021-09-24 09:28:30',NULL,'eJyVUstuwjAQ/BefATmGSoFbEvNwVaACi0vVAyThoRIHOYtEi/j3rtdEaquiUivxY3dnZzzymcXH
alxtWO/MosNhlFm3G/h5t8v8apSpwCg6TWFLWXdiczWKlXwM2AVHg+nyTlzSn0SIi664ePeBGpQj
Z4KLgHdFp279EPApDziNkLMGw0qZr33xYZlWLYzjH7R4iNnE5hKubZq82xQdLYKeCPFjyCTL9Fjk
Brw+KAcqqaCwSQbarl1waGsTSNHvgnBgjIuaTxc3GBtsspqu9anCfICnOcC+UIaYaD8GsuNpNmTO
CK9Dn6jk5cyeC/Du9U2mS5xuKfpuETb4SzmSKQM2fiMZUUGOLJb7Y47AkFqhqe6C6TtGlJw5SLK1
m9gZL/uxxqRcgfPq4nfRBv7xXui2d2C+vhWP8YyETlOoC3/A3DXbok0v7NVNnz3Z1g4=
','eJyNUdtuwjAM/ZUpz4CScFnbt9DCKNOgKtnTxEPVCyDRtGrDBkP8+xyXTmwa0lw1F8f2Occ+k/Gh
fqk3xDkTUZazpDKnabPudkmzK1/VWvl4W+otvpobcScL4XtzQS5gHSKLf+at/NkY8tg1b7z7BA6+
ASecckZtPmhLDxkNKadgbGg/kg6BSC/NmuAyiusepRx+1mMUXt0q9fS1TJfaXT6QnDnccrhNAMkr
4kOeKt3w08XUD3K90nVYouupaluAfP6mgza0eIsm8/t48uirTKgEIIjzBl2oNmo/UYksYPkB0LYE
AJb0CmIZRZgCde4Eg4GPGjbyiDBEuNKFq5FVwwgyRIaj0RVUujpBzCvkQffX3/XDNDPvbqJRf6BP
ZlsYbULpQj3Mi22kiBmXiRFxrNtx/hquodPnfQyV5S2oK1yX4MRX5T4HnxfpCNlN1Pu+vMU3iYQy
kIE1n2XQ5aB8ZFlsBM6wTpRu1GKQ/FBIlfZR1mUNH9gX9qHSQQ==
','20210924CENAIDJA510R0200001597','ACTC',4202),
	 (4208,'9234323',1800200.00,'2021-09-24 21:32:48.965','21002','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000083','SUCCESS','2021-09-24 09:32:48','2021-09-24 09:32:49',NULL,'eJyVUstuwjAQ/BefAdlOKgVuSczDVYEKLC5VD5CEh0oc5CwSLeLfu7aJ1FZFpZbirHd3dsYjn0ly
rMf1hvTOJD4cRrmx0cDvu13u/1rqGrR0pylsXdWeyFyOEikeGbngahFV3YlL+5MYcfEVl+w+UIO0
5IRTzmiXh83oB0anlFG3ooC0CHaKYu2bD8us7mAeP9ahEVZTUwi4jmnTbpuHirNewHthRJBJVNmx
LDR4fVANZFpDadIclFnb5NA0JjhFvwvChTnKGz5V3mBskclqulanGusMT3OAfSm1Y3LxGJwdT7Mh
sUZ4HerkWl7O5LkE715f56rC7Zai7xbhgL+UI5nUYJI3JyMunSOL5f5YWKkRtlCKrtobZu+YkmJm
MenWbBLrvOgnCotiBdasi4/iDfzjwbjr3oH5+lg8xjM6dJZB0/gD1uVBGPDAPbFXu30C/sHWUA==
','eJyNUW1vgjAQ/ivLfVZTCmzCNwSduMwR6D4tfiC8qAkUQus2Z/bfdy2yuGUmO0Lbe32euzvB7CAe
xRbcE3htu8w79Vr0536f9zcPuZA81NqT3Gmv0sCfr70wWHnwiTIC1vwzLwmXM8wzznmz/QdyCBU4
UEIN4lBrKG0bJCaUoBi3pg0jwMigKPvgNs3EhBCKvzExCHr9rgjkucyYOGNqMWq4JnWtKSBS0GSH
uuCy5yebRRjVMpEibrXpvhtGoPn8TUeLfecMaKy+jsfeQ156PEcIcF9wCt2WV3OeswaPHwDDSBDg
iZxBpiZi6BSscyUYBW2EggLTMBCvfIaqakvgCkqNjE/VV9TJ7ogxzzgxnP7mu35clMrv51L3H8mj
utaqt1WzS/lNckxrcahALUxFeVkmh4X+Wq9DTcukpg5l7SWs7/k+6J0nbVWjLUhlqvnN+WvVXjJQ
iYAkR33NBxaNVfumTbQxFjmXfb9aZ29ckyUmIQphgx/KF+XC0zU=
','20210924CENAIDJA510R0200001635','RJCT',4207);
INSERT INTO public.account_enquiry (id,account_no,amount,cre_dt,intr_ref_id,orign_bank,recpt_bank,bizmsgid,call_status,cihub_req_time,cihub_resp_time,error_message,full_request_msg,full_response_msg,resp_bizmsgid,response_status,chnl_trx_id) VALUES
	 (4210,'8289331',1000200.00,'2021-09-24 21:34:53.955','15502','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000084','SUCCESS','2021-09-24 09:34:53','2021-09-24 09:34:54',NULL,'eJyVUstuwjAQ/BefAdlOIgVuSczDVYEKrF6qHiAPQCUOShaJFvHvXdtEaquiUktxdr07nvFozyQ+
NtNmQwZnEh0Ok6w20cjtu13m/lrqBrS02Ry2tmoyspSTWIoHRi64OkRVd+KS4SxCXHTFxbsP1CAN
OeGUM9rnfnt1wOicMmpX6JMOwU6RF675sEqbHp7jx3o0xGpS5wKu13Rpv8t9xdnA8weBR5BJVOmx
zDU4fVCNZNJAWScZqLowh+O6NcEq+l0QLhYElLd8qrzB2CGz9bxQpwbrDLMlwL6U2jLZeArWjsfF
mBgjnA51si0vZ/JUgnNvqDNV4XZL0XeL8IK/lCOZ1FDHb1ZGVFpHnlf7Y26kYhunFF01L0zf8UiK
hcEk23oTG+fFMFZYFGswZl1cFG3gHwNjn3sH5uuwOIxjtOg0hbbxByzkYd/z3Gi+mu0TCifWXg==
','eJyNUV1vgjAU/StLn9X0Awz4hkUnW6YEu+xh8cFQcCRSCNRtzvjfd1tkccuWrIR+nnvPOfee0PTQ
PrQ7NDmhoK4XsjG7eTcXhexWFalWq8ieVvrFvpoT4rNlEIV3ATrDGCBR/TNuHS2mEEcucdPiAzRE
hhxRTAn2qdOndglOMMUwiOMwNECADLO8A9fbtB1hTOEnI4LhlTdZqC9phtgfUkdQMmHOxGUImMIq
PZSZ0p0+Xc2juNRr3Sa1vbpt+hJYPb/LsVoYG/dsovybT7xHKg+UBAo0eYYqNDu1nykpKpi+EfQl
AYIV7kiw5wCHDYE8f4CNGNfFFBkyS4MCLjgcja0WWpBbZtgaX3GjmyNgHiEOqr/5yp9kuXnnUlv/
sT6aZWm8PRWyktVNkqkCmXYZTJCmum/nj+Z61PMZIxYq6mtSHnCObMfX9b6Eu3Crt1bdTL3u62t+
E4gwARs2572IhxR7Ph57vguXSSuV7txakHhTVipm1tZ5Ax+MT+bQ0lU=
','20210924CENAIDJA510R0200001443','ACTC',4209),
	 (4216,'8289331',1000200.00,'2021-09-24 21:43:57.135','15502','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000089','TIMEOUT-CIHUB','2021-09-24 09:43:57','2021-09-24 09:43:59','Timeout menunggu response dari CIHUB','eJyVUstuwjAQ/BefAdmGqAm3JObhqkAFVi9VDyUJD5U4yFkkWsS/d20Tqa2KSi3F2fXueMajPZHk
UE/qNemfSLzfj3Njo6Hft9vc/7XUNWjpshlsXNVmZCHHiRT3jJxxtYiqbsSlg2mMuPiCS7YfqEFa
csIpZzTivebqgNEZZdStMCItgp2iWPnm/WtWd/AcP9ahIVZTUwi4XNOmUZv3FGf9Xrcf3BFkElV2
KAsNXh9UQ5nWUJo0B2VW9nBkGhOcot8F4WJBQHnDp8orjC0yXc5W6lhjnWG2ANiVUjsmF0/A2fEw
HxFrhNehjq7l+UQeS/DuDXSuKtyuKfpuEV7wl3IkkxpM8uZkxKVz5Ol1dyisVGzjlKKr9oXZOx5J
MbeYdGPWiXVeDBKFRbEEa9bZR/Ea/jEw7rk3YL4Oi8d4RofOMmgaf8BCHkbdrh/NF7t9Aikc1nA=
',NULL,NULL,NULL,4215),
	 (4218,'8289331',1000200.00,'2021-09-24 21:47:26.793','15530','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000090','SUCCESS','2021-09-24 09:47:26','2021-09-24 09:47:27',NULL,'eJyVUstuwjAQ/BefAdkG2oRbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEaquiUktxdr07nvFoTyQ+
VONqTXonEu33o8zYaOD37Tbzfy11BVq6bAobV7UZmctRLMUDI2dcDaLKG3FJfxIhLrrg4u0HapCW
nHDKGQ15p766y+iUMupWSEmDYKfIV755v0irFqUBfqxFA6wmJhdwuaZJwybvKM56nfsevyPIJMr0
UOQavD4oBzKpoDBJBsqs7OHQ1CY4Rb8LwsW63Tat+VRxhbFBJsvpSh0rrDPM5gC7QmrH5OIxODse
Z0NijfA61NG1vJzIUwHevb7OVInbNUXfLcIL/lKOZFKDid+cjKhwjjwvdofcSsU2Tim6al+YvuOR
FDOLSTZmHVvnRT9WWBRLsGadfRSt4R8D4557A+brsHiMZ3ToNIW68Qcs4EHYbvvRfLXbJxEi1mI=
','eJyNUl1vgjAU/StLn9W0BZzwhgUnLnMEu6fFB8KHkkghUKfO+N93W2Rxy0x2Cf0895577u0ZTfft
S7tBzhm5dT1PG7WadWNRpN0sAtFKEejdq9zqW7VDzF+6gbdw0QVsgHj1T79VMJ+CH7n6TYtPyCFQ
5IhiSrBNzT60RXCEKVZmGiYaIEB6Wd6B6zhpRxhT+MmIYLhlTebJa5ghtofU5JQ45qNDxwiYvCrZ
l5mQXX6ymgVhKVeyjWp99NT0JdD5/J2OtrFFejZe3ufjx0DkrkiBAjnvUIVmI3a+SHkFww+CviRA
8IqvJLZSpF0gzh0wGLEsQyH5UdMgl3EGWyWrhRbkmhmWSlfYyOYEmDfwg+qvv+NHWa7uWSq1/lCe
1LRU2vzmUIiHRbWNBVLtUhg3SWTfzl/NndCJbRhEQ3l9S8pcxpDu+KrelXDmxTLW2fniY1ff8itH
hFWNdcxnHg7hEZgUtI7hMGpTITu1GsQPQqeKDS3rsoYP7Avqo9JE
','20210924CENAIDJA510R0200000434','ACTC',4217),
	 (4230,'8211131',4222000.00,'2021-09-24 22:05:37.558','12550','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000100','SUCCESS','2021-09-24 10:05:37','2021-09-24 10:05:38',NULL,'eJyVUstuwjAQ/BefIbINUYFbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEolVRqaWsH7uzMxnticSH
alytSe9Eov1+lBl7Gvi43WZ+11JXoKW7TWHjsvZG5nIUS/HIyBlXg6jyTlzSn0SIiy64ePuJGqQl
J5xyRru8XbcOGZ1SRu3CSBoEK0W+8sX7RVoFlHbwYwHtYDYxuYBLmybtNnlbcd6jYa/1QJBJlOmh
yDV4fVAOZFJBYZIMlFnZx6GpTXCKfhdktfAwpDWfKm4wNshkOV2pY4V5hrc5wK6Q2jG58xicHU+z
IbFGeB3q6EpeT+S5AO9eX2eqxHBL0XeLsMFfypFMajDxu5MRFc6Rl8XukCOwzTnHysA1S9IPfJJi
ZjHJxqxj67zoxwqTYgnWrLM/RWv4x8C4370Dcz0sHuMZHTpNoS78AetwxljLj+abDV/tsNZA
','eJyNUW1vgjAQ/itLP6tpi6jwDYtOZqYGu0+LHwgvjkQKoWXTGf/7rkUWt8xkR+jL9bl77rk7o2kj
n+UeuWfkVdUiqfVp3q55nrS7CIRUIjC3tXozr/qG2GzlBf6Thy5gPcTLf8Ztg8UU4sg1bpp/Qg2B
JkcUU4IdOuxS2wSHmGIwMiYT1EOA9NOsBVdRLAcYU/jJgGB4ZXXqq2uaPnb6dMgpdbHtWmMETH4Z
N0UqVFufKufBplBbJcPKuB7rrgWmnr/LMeY4tGPjxX0+fgxE5okEKJD7Cl2o9+IwEwkvYflB0LUE
CNa4JYEVOEwI5LkD1jhq2xrJj4YGeYwzuGpZEkaQGWY4al2bWtUnwLxAHHR/950/TDP9zhJl9G/U
SW8rrW3ZyKaIHhZRkiM9Lo3x4lh14/w13AklhFjEQHl1S8o8xpCZ+LY6FODzIxWZ6mbi/VDd8utA
hAnIMDmXfNOneERsbI8scIYyEapVa0D8Q5hSsWVkXXbwgX0B4U/SLA==
','20210924CENAIDJA510R0200001718','ACTC',4229);INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (4215,1000200.00,'Internet Banking','FARIS AI','8289331','Fandi Ahmad','7100004','Timeout menunggu response dari CIHUB','Credit Transfer','014','2021-09-24 21:43:57.07041','2021-09-24 21:43:59.218936','TIMEOUT-CIHUB','15502'),
	 (4172,54500.00,'Internet Banking',NULL,'1814449',NULL,NULL,NULL,'Account Enquiry','008','2021-09-24 20:11:08.394747','2021-09-24 20:11:09.388125','SUCCESS','4511168'),
	 (4217,1000200.00,'Internet Banking','FARIS AI','8289331','Fandi Ahmad','7110004',NULL,'Credit Transfer','014','2021-09-24 21:47:26.780557','2021-09-24 21:47:29.593947','SUCCESS','15530'),
	 (4174,8650000.00,'Internet Banking','P AI','11844292','Fandi Wijaya','782444',NULL,'Credit Transfer','014','2021-09-24 20:12:55.068922','2021-09-24 20:12:56.296226','SUCCESS','21211'),
	 (4229,4222000.00,'Internet Banking','FARIS AI','8211131','Bambang Suo','7155504',NULL,'Credit Transfer','014','2021-09-24 22:05:37.514305','2021-09-24 22:05:40.95248','SUCCESS','12550'),
	 (4179,781000.00,'Over the Counter',NULL,NULL,NULL,NULL,'rekening tidak aktif','FI Credit Transfer','008','2021-09-24 20:16:32.176756','2021-09-24 20:16:32.187235','REJECT-CB','962001'),
	 (4181,89994500.00,'Internet Banking','P AI','110044292','Fandi Wijaya','785544',NULL,'Credit Transfer','014','2021-09-24 20:27:12.676883','2021-09-24 20:27:14.435353','SUCCESS','211411'),
	 (4186,8904000.00,'Internet Banking','FARIS AI','8000292','Fandi Wijaya','7850004','Timeout waiting CIHUB response','Credit Transfer','014','2021-09-24 20:34:10.874679','2021-09-24 20:34:15.684737','TIMEOUT-CIHUB','210071'),
	 (4192,8904000.00,'Internet Banking','FARIS AI','2000292','Fandi Wijaya','7850004',NULL,'Credit Transfer','014','2021-09-24 21:05:53.365443','2021-09-24 21:05:54.52164','SUCCESS','21861'),
	 (4197,8904000.00,'Internet Banking','FARIS AI','2111132','Fandi Wijaya','7154004',NULL,'Credit Transfer','014','2021-09-24 21:14:38.837862','2021-09-24 21:14:40.623406','SUCCESS','21862');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (4202,800000.00,'Internet Banking','FARIS AI','2100323','Fandi Wijaya','7154004',NULL,'Credit Transfer','014','2021-09-24 21:28:28.581211','2021-09-24 21:28:30.827266','SUCCESS','21002'),
	 (4168,1990000.00,'Internet Banking',NULL,'7712589',NULL,NULL,'Timeout menunggu response dari CIHUB','Account Enquiry','014','2021-09-24 20:03:35.1136','2021-09-24 20:03:37.204071','TIMEOUT-CIHUB','46149'),
	 (4207,1800200.00,'Internet Banking','FARIS AI','9234323','Fandi Ahmad','7100004','Account Enquiry reject.','Credit Transfer','014','2021-09-24 21:32:48.951207','2021-09-24 21:32:49.50314','REJECT-CIHUB','21002'),
	 (4170,54500.00,'Internet Banking',NULL,'1819199',NULL,NULL,NULL,'Account Enquiry','008','2021-09-24 20:08:40.30641','2021-09-24 20:08:41.145749','SUCCESS','452168');INSERT INTO public.corebank_transaction (transaction_id,addt_info,channel_ref_id,credit_amount,creditor_bank,cstm_account_name,cstm_account_no,cstm_account_type,debit_amount,debtor_bank,status,transaction_type,trns_dt,chnl_trx_id) VALUES
	 (4160,'Info tambahan disini','21211',NULL,NULL,'Fandi Wijaya','782444','CACC',8650000.00,NULL,'SUCCESS','Debit','2021-09-24 08:41:44.056383',4158),
	 (4176,'Info tambahan disini','21211',NULL,NULL,'Fandi Wijaya','782444','CACC',8650000.00,NULL,'SUCCESS','Debit','2021-09-24 20:12:55.663967',4174),
	 (4180,'rekening tidak aktif','962001',NULL,NULL,NULL,NULL,NULL,781000.00,NULL,'FAILED','FI Transfer','2021-09-24 20:16:32.18385',4179),
	 (4183,'Info tambahan disini','211411',NULL,NULL,'Fandi Wijaya','785544','CACC',89994500.00,NULL,'SUCCESS','Debit','2021-09-24 20:27:13.813803',4181),
	 (4188,'Info tambahan disini','210071',NULL,NULL,'Fandi Wijaya','7850004','CACC',8904000.00,NULL,'SUCCESS','Debit','2021-09-24 20:34:11.440369',4186),
	 (4194,'Info tambahan disini','21861',NULL,NULL,'Fandi Wijaya','7850004','CACC',8904000.00,NULL,'SUCCESS','Debit','2021-09-24 21:05:53.94185',4192),
	 (4199,'Info tambahan disini','21862',NULL,NULL,'Fandi Wijaya','7154004','CACC',8904000.00,NULL,'SUCCESS','Debit','2021-09-24 21:14:40.041811',4197),
	 (4204,'Info tambahan disini','21002',NULL,NULL,'Fandi Wijaya','7154004','CACC',800000.00,NULL,'SUCCESS','Debit','2021-09-24 21:28:30.19042',4202),
	 (4219,'Info tambahan disini','15530',NULL,NULL,'Fandi Ahmad','7110004','CACC',1000200.00,NULL,'SUCCESS','Debit','2021-09-24 21:47:27.35598',4217),
	 (4231,'Info tambahan disini','12550',NULL,NULL,'Bambang Suo','7155504','CACC',4222000.00,NULL,'SUCCESS','Debit','2021-09-24 22:05:38.661493',4229);INSERT INTO public.credit_transfer (id,settlconf_bizmsgid,amount,call_status,cihub_req_time,cihub_resp_time,crdttrn_req_bizmsgid,crdttrn_resp_bizmsgid,cre_dt,creditor_acct_no,creditor_acct_type,creditor_id,creditor_type,debtor_acct_no,debtor_acct_type,debtor_id,debtor_type,full_request_msg,full_response_msg,intr_ref_id,msg_type,orign_bank,recpt_bank,resp_status,reversal,chnl_trx_id) VALUES
	 (4221,NULL,1000200.00,'TIMEOUT-CIHUB','2021-09-24 09:47:27','2021-09-24 09:47:29','20210924SIHBIDJ1010O0100000091',NULL,'2021-09-24 21:47:27.375','8289331','SVGS','C22403','02','7110004','CACC','014145','01','eJyVUstO6zAQ/RXkdUG2G9THLo+2GEFbNRYbxCIkbVqRpJEzRUDVf2c8SbgFtbpgyYnteZxzZmbP
vF11X6VsuGduWd4kxp7G9XezSep/oYoKCkW3GazJam8sVDeeCm4FO+DqML39ZZw/mroY5zZx3uYD
OSgLziSXgg+k06bmgs9w0xoI1mHoGSxXtXMZxdUV533c4or30eqbZQBNmks+uJSOlmLo9IayxxAp
2Ma7fFlAzQ+2Y+VXkBs/AW1W9nFi2iIQo9OEGjayxdP5GcQOmz7PVvqtQrvlHgJkuSoIic73QOW4
W0yYLUTNQ7+Ry+OezXOoqzcqEr3FzzlG30uECf7HHMEwuS4bMndxZntlcnuZGzDvGM+FdfMhfZ/v
TPnTgiZVgPFeSIibU00fomy3tGIRRXKOfbE1im2MChaUbm1Sz/YuGHkajcEzULmntobjqEg2F+46
jxI01dLn5hWOR+ixmSEuHOFcs8MTjZBN48YxtCP2Y+B6wjJyiLX+JsV3fZ99ZUjhDxNP/fpFzPG0
1zH/BLsLFV646kvtzKSnxPpSOrzbiiXY82L7sj/odsUJseHDJKTnsMxyfAoiiAhkVLxm5Nm2w8a1
A5AcP0mr4omIHD4Bm/466A==
','','15530','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL,4217),
	 (4233,NULL,4222000.00,'TIMEOUT-CIHUB','2021-09-24 10:05:38','2021-09-24 10:05:40','20210924SIHBIDJ1010O0100000101',NULL,'2021-09-24 22:05:38.679','8211131','SVGS','C22403','02','7155504','CACC','014145','01','eJyVUttu4jAQ/ZXKz7SyTaJS3nIB6qoFRKy+VH0ICaRoEydynKpdxL/veJIgWoHateTE9tzOOTN7
4jf1U52R8Z54VXWfanuatt/dLm3/SqjaKIG3hXlDq72RSNz7Inxg5ABrQGT5y7hgMvcgzuvi/N1f
wCBsccIpZ/SOO31qyugCtl1wJgMCnuFm2zpXcVLfUDqCzW7oCKyB3oSmS3NN7665IzkfU3c8HBGo
FJZJU2yUafGZciqC2hQ6SI3UW/s4070IiOg8oA4N7+vJ4kLFAZmvF1v5UYPdYo+MyQuhsBKenwzK
8biaEStEi0N+oMvLniwL06o3Uaks4XMJ0VeJIMFPyKEYJJdVB+YxyW2vdGEvS230J8RDLovJZJ/L
RlffLWASymj/DxLxCtT0Oc6bDTg4nHOoBH2xGiU2RoQrTPemM9/2Lpz4Eozh2qDcc6uhHxfrWGVX
UVOCqaW+1O/mdIReuhmizGGOSw6vOEI2jZckph+xbwN3y1zXpQ6ill+oBF4QkGOGzPzHxGO/fhFz
Ou1tzJHw1FuJ6MoTR7YLnZ0jG3Du0GFPFsteJjvijLEhO0M2ep5F+BxVeQFPYWxiLDJR7zl69u2w
cf0ApKdP3LJ4RSCHf4C2Oug=
',NULL,'12550','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL,4229),
	 (4178,NULL,8650000.00,'SUCCESS','2021-09-24 08:12:55','2021-09-24 08:12:56','20210924SIHBIDJ1010O0100000064','20210924CENAIDJA010R0200001857','2021-09-24 20:12:55.682','11844292','SVGS','C4403','02','782444','CACC','01445','01','eJyVUttu4jAQ/ZXKz7Sys6YbeMsFqFctoMbqPlT7kCaQZjcJkTNUZVH/veNJgmgF2q4lJ7bnds6Z
2TN/29w1GRvvmVfXN6mxp2n7zfO0/VeqaqBSdFvAM1ntjUXqxlfhD8HecA2Y3nwxLpjMPYzzujg/
/4sYlC3OHO4IPnJkn5oLvsBN61qyAUPPcLVunes4aa44d3GLK+6iNTCrELo0l3x06Ujt8LFwxsMh
w0rhJtmWqwpafLCZqqCB0gQpaLO2jzPTi0CITgPq0Az7ero8U3HA5k+LtX5t0C7wFgEUpaqoEp3v
gOS4vZ8xK0SLQ7+Sy+OeLUto1ZtUqd7g5xyijxJhgn8hx2KYXNcdmNuksL0ypb0sDZgdxnNh3QLI
dsutqT9b0KQqMP4fIuKVpOlDXGxX6OBeD20h7IvVKLExKryndM8m823vwomv0Rg+Ack9txpO4yrN
L37mv+NdjLaW+9K8wPEMPXZDxIWUSOQXjZBN4yUJ9CP2aeC+u46UkkDrD0wCLwjYIUEG/zHw1K4v
xBwPextz4Lu88NSB58Jkp2gGUvJvPU2qeJ6mEK6Uzsg5QTR6mEX0HNVFiU9hDDEVmVQvBXn2nbBx
fe/T4yebFnFYJG/vPNQ5+Q==
','eJyNUl1vgjAU/SvLfVbT4seUNwScbBkQ6J4WH4igM5FCoDqd8b/v9iJmWTRbCW1ve+85p6c9wXRX
v9ZrME9gleU8rfRs1vSbTdqM0pO1kh5FgfqgXR2B7fqW5zxbcMbWAVH8sy725lOs45e66eYLNXia
HAxmcDYxBi004yxiBsPGx8NH6ABmOtmqSS6TZd1jzMCf9zgDgor3Sy1NRG4cBn7s4qpdZY66gHfZ
pGsMhMFMbpjDISC/Uyx3eSZVo1oVMy/MVazqqKSlp6o1hlTeFkltxPstm8jv8wXVWm4R1ZMrS6ZI
BOb7qVn9zdA61TKw0RA611Q/p+SLDWOygY3hvMC7ONxAd2UqCuzuUQTXgwxaFsT5W484EA1YtrAx
1NbVePkrYsap9i6sVHXEnDcswntfXPGjbKX37VSRx6E66sHX/nkyTSpVPFjpRh0ToMcSl9sckZxE
JQTvyv22/AkgMALGUQcpfxFhF9/PoD/WZ4rqVKpGLKWIT0lMrE+qzgv8sH0Dewbi0w==
','21211','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,4174),
	 (4185,NULL,89994500.00,'SUCCESS','2021-09-24 08:27:13','2021-09-24 08:27:14','20210924SIHBIDJ1010O0100000067','20210924CENAIDJA010R0200001567','2021-09-24 20:27:13.834','110044292','SVGS','C4403','02','785544','CACC','01445','01','eJyVUttuozAQ/ZXKz2llqLMJeeOSpF61CSpW96HaBwoJZRcIMpOq2Sj/vuMBorRKtF1LBttzO+fM
7Jm3bR6ajE32zK3ru1Sb06z95nna/itZNVBJui3hlazmxiJ558ngu8UOuAZMbb4Y508XLsa5XZyX
/0EM0hRnNrct7tiiT80tvsRN69uIDRh6Bqt161zHSXPD+Ri3dcPHaPX1KoAuzTV3rm2hbD6xRxPr
lmGlYJNsy1UFLT7YzKTfQKn9FJRem8e57kUgROcBdWiO9VR5oeKALV6Wa/XeoN3CWwRQlLKiSnR+
AJLj/nHOjBAtDvVOLs97FpbQqjetUrXBzyVEHyXCBP9CjsUwuao7MPdJYXqlS3MJNegdxnPLuPmQ
7cKtrj9b0CQr0N5vIuKWpOlTXGxX6DB2HEcMOcfGGJESEySDR8r3qjPPNC+YegqNwQuQ3gsj4iyu
0vzqR/4r3sVoa8mH+g1Oh+i5myJuCTFkh580QyaNmyTQz9iniRuNh0MhCLX6QMV3fZ8dE2TwHxNP
/fpCzOm0tzFHvuGVK488lzo7R9MXgt/2NKniZZoWtlgI27HPMI2e5hE9R3VR4lMQQ0xVptVbQZ59
K0xc3/309MmkRSAGyuEvwo46eg==
','eJyNUttugkAQ/ZVmntUMaL3whoCVNlUC2/Sh8YGIWhJZCay3Gv+9s4OYptG0S9jdmZ055+TsnmC4
LV/LFVgnsPN8nBR6N6rmNE2qVfqyVNLnaKo++VRH4HgT23efbTjTaIDY/LMv8sdD6jMufcP0izT4
mhxMNA0cmJ0aGg0M0UQaxmO3Bw2gSnexrIrzeF62EE36jZaBwFDRbq6lidCLgukk8ijrFAtXXcCb
OGiaHWGiZfYsow3E727m22whVaVabUZ+kKlIlWHOqaeiNoZV3hbJCruINZvI7vNNi5VcE6ovl7ZM
iAisj1OV/c1QO3VhQOz2oXEtnWRcfLGhzzZgH84zuovDDXRPJmJD0z2KaU3DTnML4fytRxyYBmxH
OBRq60q6/CUz01Z7FxSqOFLNGzXRvc+u+OFiqc+dRLHHgTrqZaL9o8ep4ix9eE+LeB9L4NcS5euM
oNxYxYzvyd06/4kgKAI0SAhLfxFBkx5Qp93vUCosE6kqtVwi9pKpsM2yzjP6aHwDdPDjWA==
','211411','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,4181),
	 (4196,NULL,8904000.00,'SUCCESS','2021-09-24 09:05:53','2021-09-24 09:05:54','20210924SIHBIDJ1010O0100000075','20210924CENAIDJA010R0200000028','2021-09-24 21:05:53.965','2000292','SVGS','C423403','02','7850004','CACC','014145','01','eJyVUttu4jAQ/ZXKz7SyQ7IF3nIB6lULiFjdh6oPaQJpdpMQOUNVFvHvHU+SLq1A7VpyYnsu55yZ
2TNvW9/VKRvtmVtVN4k2p0nzzbKk+ZeyrKGUdJvDM1nNjYXyxpPBT8EOuHpMbb4Z549nLsa5bZyX
/UUO0oAzi1uCDy27S80Fn+Omde2wHkPPYLVunKsorq84H+AWV3yAVl+vAmjTXPLhpWUrS4y4M3L6
DJGCTbwtViU0/GAzkX4NhfYTUHptHqe6KwIxOk2oZfOjw1PFGcQemz3N1+q1RrvAWwiQF7IkJDrf
AZXjdjllphAND/VKLg97tiigqd64TNQGP+cYfSwRJviKOYJhclW1ZG7j3PRKF+ay0KB3GM+FcfMh
3S22uvpsQZMsQXt/SIhbUE3vo3y7QofBkNsIhH0xNYpNjAyWlO5Zp57pXTD2FBqDJ6Byz0wNJ1GZ
ZBe/st/RLkJbo32hX+B4hh7aIeLCFrbDDo80QyaPG8fQzdinibseOMjHJtrqgxbf9X32niGF/xh5
atg3Yo7HvYn5p9hdyvDCle9q5zo9Jda3rb7N+51awj2v1kKt1tA6oTa8n4b0HFZ5gU9BBBGhjMuX
nDy7hpi4bgSS4yeTFmkYIoc3fS87qw==
','eJyNUlFvgjAQ/ivLPaspoFN5Q8DJlgmBLntYfCBWHYkUAtXpjP9910PMssxsJbS96933fXzlBJNd
/VxvwD6BU5YzUendtJmzTDSrDGStZEBRqN7pVEfg+nMn8B4dOOPoAC/+2ZcEswn2GZe+SfaJGgJN
DiYzDTY2+y00M1jMTEbDHEEHsNJbrZviMl3WPczja/QMBgSV7JdaGo/9JArniY9Zt1p56gLeZeOu
2eemYbOBPbAA+b1iuctXUjWqVTENolwlqo5LSj1UrTGk8neRNIbMatl4fpsvrDZyi6iBXDtSIBHY
b6cm+5OhdaplYMN76FxL5zkVX2wYkQ1sBOcF3sXhF3RfCl7gdIsivH7IoGVBnL/18APRgONyF0Nt
XY2XvyZm3GrvokpVR6x5wSa898UVP16t9bkrFHkcqaNe5tq/QIq0UsXdayZFBvSvJOU2RyAvVSmh
+3K/Lb/3c4yAGSiDhD/xqIu/T98a9TEV10KqRiuV8A9JRMwiUecFPji+AH+e4m8=
','21861','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,4192),
	 (4201,NULL,8904000.00,'SUCCESS','2021-09-24 09:14:40','2021-09-24 09:14:40','20210924SIHBIDJ1010O0100000078','20210924CENAIDJA010R0200000877','2021-09-24 21:14:40.055','2111132','SVGS','C423403','02','7154004','CACC','014145','01','eJyVUttu4jAQ/ZXKz7Syg6sCb7kA9aoFRKzuQ7UPaQJpdpMQOUNVFvHvO54kXVqB2lpyYntu55yZ
PfO29X2dstGeuVV1mxh7mjTfLEuaf6nKGkpFtzk8k9XeWKhuPRX8EOyAq8f05otx/njmYpzbxnnZ
X8SgbHHmcEfwoSO71FzwOW5aNwPWY+gZrNaNcxXF9RXnA9ziilurb1YBtGku+fDSkdoRIyFHkjOs
FGzibbEqocEHm4nyayiMn4A2a/s4NZ0IhOg0oBbNsKunizMVe2z2NF/r1xrtAm8hQF6okirR+R5I
jrvllFkhGhz6lVwe92xRQKPeuEz0Bj/nEL2XCBN8hhyLYXJdtWDu4tz2yhT2sjBgdhjPhXXzId0t
tqb6aEGTKsF4f4iIW5CmD1G+XaHDYMglFsK+WI1iG6OCJaV7NqlnexeMPY3G4AlI7pnVcBKVSXbx
M/sd7SK0NdwX5gWOZ+ixHSIupJDX7PCLZsjmceMYuhn7MHE34hoBSYKt33HxXd9nbxlS+MbIU8O+
EHM87k3Mf8buUoUXrnpjOzfpKbK+dPqS9zu2VPc8W0fg6jsn2IYP05Cewyov8CmIIKIq4/IlJ8+u
ITauG4Hk+MmmRRgWyOEfgJ47qA==
','eJyNUlFvgjAQ/ivLPatpK4noGwJOXKYEuuxh8YEIKIkUAlXnjP991wJmWTTbEdre9e77vtz1AtND
/VpvYXIBqyzncaVOs2bNsrjZhSdqKTztreRO3yoPbHdpec7CgitaD3jxz7rQm0+xjrZ10+wLNXiK
HBhhlIyZ0UETSgLCiDJzNIIeYKaTpE1yGW3qASEMfzqgBDRUeNwoaTxwQ3+1DF2M2lXiyBa8T8Z9
ZnBGJ9SYGASQ3yk2hzwRslEti5nn5zKUdVDq0HPVNUarvC9SGTUZ7dh4/phvVW3FHlE9kVoiRiKY
fFya6G+GrlMtAyGjMfRuqctcJ7dtMHUbiAnXNc7i8w66K2Je4PKIYtXRjMyOBXH+1sM/NQ1YNrfR
Va2rcfipZsaj6p1fyeqMOW9YhHNf3/CDJFX3dix1j315VttS9e89q6JTJJ4Wu0KcQb+VsNznCORE
MtLorjjuy5/1HD0gag5a+Av3+/h8jKFpYCioYyEbrTqFn4QmIkMt6rrGD+0bpCPikw==
','21862','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,4197),
	 (4190,NULL,8904000.00,'TIMEOUT-CIHUB','2021-09-24 08:34:11','2021-09-24 08:34:13','20210924SIHBIDJ1010O0100000070',NULL,'2021-09-24 20:34:11.46','8000292','SVGS','C423403','02','7850004','CACC','01445','01','eJyVUl1P6kAQ/Stmn9FsS42Ft34ArlEgdKMP5j7UFmrvbZdmOxiR8N+dnbZeNBB1k213d77OOTM7
5m/quzpjwx3zquo61eY0br55njZ/JVQNStBtBs9kNTcWiWtfhDcW2+PqMbn+YVwwmnoY57Vxfv6G
GIQpzmxuW3xgO11qbvEZblpXnPUYeobLVeNcxUl9wbmL27rgLloDvQyhTXPOB+e2I20+7DtDC0H2
WLhONuVSQYMP1mMR1FDqIAWpV+ZxojsRCNFxQC0aq6snyxMVe2z6NFvJ1xrt5hYBFKVQVInOd0By
3C4mzAjR4JCv5PK4Y/MSGvVGKpVr/JxC9FkiTPAdciyGyWXVgrlNCtMrXZrLXIPeYjwntwCy7Xyj
q68WNAkF2v9HRLySNL2Pi80SHdwBd7AQ9sVolJgYES4o3bPOfNO7cORLNIZPQHJPjYbjWKX52UP+
N97GaGu4z/ULHM7QYztE3HKcS7b/QyNk0nhJAt2IfRm4K/cS4TiEWn6iEnhBwD4yZPCLiad+/SDm
cNqbmP+EvYWIzjzxQXams2NcA8fuO7zfsaW6p9m6yNUe2EfYRveTiJ6jqijxKYwhpioj9VKQZ9cP
E9dNQHr4ZNIiDANk/w4W3jti
','','210071','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL,4186),
	 (4206,NULL,800000.00,'SUCCESS','2021-09-24 09:28:30','2021-09-24 09:28:30','20210924SIHBIDJ1010O0100000081','20210924CENAIDJA010R0200000622','2021-09-24 21:28:30.202','2100323','SVGS','C22403','02','7154004','CACC','014145','01','eJyVUl1P6kAQ/Stmn9HsLjUib/0AXKNA6Mb7YHyoLdRe29JsByMS/vudnbZeNJDrbbJtd77OOTOz
Y96mvq9TNtwxt6puEmP/xs07y5LmW6qyhlLRbQYv5LU3FqobTwW3gu3x6TG9/mGeP5q6mOe2eV72
gRyUBWeSS8GvpdOV5oLP8NAzEKzHMDJYrprgKorrC7TjERd8gF7fLANoy5zz63PpaCmGcjDsc4ZI
wTreFMsSGn6wHiu/hsL4CWizssaJ6ZpAjI4TatnIDk8XJxB7bPo8W+n3Gv2WewiQF6okJPq/B2rH
3WLCbCMaHvqdQh53bF5A071Rmeg1vk4x+toiLPAv5giGxXXVkrmLczsrU9jL3IDZYj4XNsyHdDvf
mOq7B12qBOO9khC3oJ4+RPlmiQEDwsGx2BbFNkUFC6r2YlLPji4YeRqdwTNQt6e2heOoTLKzX9nv
aBuhr5E+N29wuEKP7Q5x4Qjnku2faIVsHTeOoVuxbwt3JS4dzh1irb9I8V3fZ58VUviPjad5/SDn
cNubnL+K3YUKz1z1qXZm0mNifSkd3u/EEuxpsTh13pf9I2LDh0lI5rDKCzQFEUQEMirfcors5mHz
ugVIDk3SqngiIvs/CeE7Jg==
','eJyNUstugzAQ/JXK5yQyBrWEGwHS0KqAwFUPVQ4oQIoUDALn1Sj/3vUSqqpK1C7Cj/XuzGjsE5lt
u5duTawTsZtmkbVqNe/Hssz6Wfiik8LHXSg/8FTtiOMFtu8+2eQMMSK8/mdf4i9m0Kdd+mblJ2jw
FTlhlGl0yowBmmo0poyquGeMjAhUunnRFzfpqptQyuDXJholCJXsVkoaj70kCoPEg6zT5q68gI/p
dMwMzjSLmZZOCfC79Wpb5UL2qmU996NKJrKLG0w9toMxqPK6SAz9YTqw8eo2X9iuxQZQfVHYIgMi
Yr2f+uxvhsGpgYGayoOhNKiw+GKDiTZQk5yXcBeHK+ieyHgNwy2KcKAxtYEFcP7Www9IQ2yHO7BV
1nVw+QUyw1J5F7WyPULNKzTBvS+/8eO8UOdOJtHjSB7VFCj/3so23afiLs5FSfCpJM2mAhw3lSmC
e2K3aX62c9gRquSj7mcejeH1GLppQCruMiF7qVjC9wJ5qI6azkv4IL4AmdjiCg==
','21002','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,4202);INSERT INTO public.domain_code (id,grp,"key",value) VALUES
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
	 (253,'TRANSACTION.CODE','revcdttrns','Reverse Credit Transfer'),
	 (254,'TRANSACTION.CODE','prxycrdttrns','Proxy Credit Transfer');
INSERT INTO public.domain_code (id,grp,"key",value) VALUES
	 (259,'TRANSACTION.CODE','pymtsts','Payment Status'),
	 (258,'TRANSACTION.CODE','prxyrslt','Proxy Resolution'),
	 (257,'TRANSACTION.CODE','prxyrgst','Proxy Registration'),
	 (256,'TRANSACTION.CODE','prxylkup','Proxy Lookup'),
	 (502,'REQUEST.CLASS','ChnlAccountEnquiryRequestPojo','Account Enquiry'),
	 (501,'REQUEST.CLASS','ChnlCreditTransferRequestPojo','Credit Transfer'),
	 (503,'REQUEST.CLASS','ChnlFICreditTransferRequestPojo','FI Credit Transfer'),
	 (504,'REQUEST.CLASS','ChnlProxyRegistrationRequestPojo','Proxy Registration'),
	 (505,'REQUEST.CLASS','ChnlProxyResolutionRequestPojo','Proxy Resolution');INSERT INTO public.fault_class (id,exception_class,reason) VALUES
	 (1,'com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException','Attribute tidak dikenal'),
	 (2,'com.fasterxml.jackson.core.io.JsonEOFException','Json parsing Exception'),
	 (4,'java.util.NoSuchElementException','Validation Error'),
	 (5,'org.apache.http.conn.HttpHostConnectException','Connection error'),
	 (6,'org.apache.http.NoHttpResponseException','Connection error'),
	 (7,'java.net.SocketException','Connection reset'),
	 (3,'java.net.SocketTimeoutException','Timeout'),
	 (8,'org.apache.camel.http.common.HttpOperationFailedException','HTTP call error');INSERT INTO public.inbound_counter (tanggal,last_number) VALUES
	 (20210915,50000032),
	 (20210911,50000006),
	 (20210912,50000006),
	 (20210913,50000052),
	 (20210921,50000006),
	 (20210914,50000018);INSERT INTO public.m_bic (bank_code,bank_name,bic_code,change_who,created_date,last_update_date) VALUES
	 ('213','BANK TABUNGAN PENSIUNAN NASIONAL','SUNIIDJA','system','2021-08-10 14:35:00','2021-08-10 14:35:00'),
	 ('008','BANK MANDIRI (PERSERO), PT','BMRIIDJA','system','2021-08-10 14:35:00','2021-08-10 14:35:00'),
	 ('014','PT Bank BCA','CENAIDJA','adminBIC@btpn.com','2021-08-12 14:21:28',NULL),
	 ('451','PT Bank Syariah Mandiri','BSMDIDJA','adminBIC@btpn.com','2021-08-12 16:15:45',NULL),
	 ('031','CITIBANK','CITYIDJA','adminBIC@bankmantap.com','2021-08-29 17:09:58','2021-08-29 17:33:04'),
	 ('564','BANK MANDIRI TASPEN','SIHBIDJ1','system','2021-08-29 17:09:58','2021-08-29 17:33:04'),
	 ('001','BANK INDONESIA','INDOIDJA','system',NULL,NULL),
	 ('009','BANK BNI','BNINIDJA','adminBIC@bni.com','2021-08-12 14:21:28',NULL);INSERT INTO public.message_counter (tanggal,last_number) VALUES
	 (20210920,129),
	 (20210917,178),
	 (20210918,376),
	 (20210915,140),
	 (20210914,51),
	 (20210921,284),
	 (20210922,211),
	 (20210916,184),
	 (20210919,190),
	 (20210924,104);
INSERT INTO public.message_counter (tanggal,last_number) VALUES
	 (20210923,64);INSERT INTO public.mock_names ("name") VALUES
	 ('Anton'),
	 ('Andrea'),
	 ('Windi'),
	 ('Jhony'),
	 ('Wirawan'),
	 ('Aditya'),
	 ('Suseno'),
	 ('Susilo'),
	 ('Ambarwati'),
	 ('Eko');
INSERT INTO public.mock_names ("name") VALUES
	 ('Adiputro'),
	 ('Johan'),
	 ('Adam'),
	 ('Bustami'),
	 ('Joko'),
	 ('Indarto'),
	 ('Kusuma'),
	 ('Yogi'),
	 ('Gilang'),
	 ('Samudera');
INSERT INTO public.mock_names ("name") VALUES
	 ('Andre'),
	 ('Sjafril'),
	 ('Widodo'),
	 ('Syamsul'),
	 ('Erwin'),
	 ('Reni'),
	 ('Winata'),
	 ('Hadi');INSERT INTO public.mock_pacs002 (id,biz_msg_idr,full_message,orgnl_end_to_end_id,orgnl_msg_id,orgnl_msg_name,trx_type) VALUES
	 (4232,'20210924CENAIDJA010R0200000627','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210924CENAIDJA010R0200000627","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-24T22:05:38"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210924CENAIDJA01000000771","CreDtTm":"2021-09-24T22:05:38"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210924SIHBIDJ101000000102","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210924SIHBIDJ1010O0100000101","OrgnlTxId":"20210924SIHBIDJ101000000102","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Adiputro Susilo"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210924SIHBIDJ1010O0100000101','20210924SIHBIDJ101000000102','pacs.008.001.08','CreditConfirmation'),
	 (4220,'20210924CENAIDJA010R0200001961','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210924CENAIDJA010R0200001961","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-24T21:47:27"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210924CENAIDJA01000000384","CreDtTm":"2021-09-24T21:47:27"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210924SIHBIDJ101000000092","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210924SIHBIDJ1010O0100000091","OrgnlTxId":"20210924SIHBIDJ101000000092","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Indarto Wirawan"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210924SIHBIDJ1010O0100000091','20210924SIHBIDJ101000000092','pacs.008.001.08','CreditConfirmation'),
	 (4226,'20210924CENAIDJA010R0200001310','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210924CENAIDJA010R0200001310","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-24T21:59:58"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210924CENAIDJA01000000338","CreDtTm":"2021-09-24T21:59:58"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210924SIHBIDJ101000000097","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210924SIHBIDJ1010O0100000096","OrgnlTxId":"20210924SIHBIDJ101000000097","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Sjafril Adam"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210924SIHBIDJ1010O0100000096','20210924SIHBIDJ101000000097','pacs.008.001.08','CreditConfirmation');INSERT INTO public.payment_status (id,bizmsgid,error_msg,intern_ref_id,orgn_endtoendid,request_dt,request_full_message,response_dt,response_full_message,retry_count,saf,status,chnl_trx_id) VALUES
	 (4191,'20210924SIHBIDJ1000O9900000072','Timeout menunggu response dari CIHUB','210071','20210924SIHBIDJ1010O0100000070','2021-09-24 08:34:13','eJyNUMtuwjAQ/Jc9Q7R+FLBvCYHiSjQIckM9VCRAJOKksZFQEf+OnceNA3tYe727M+O5Q3Q1a3MC
eYewrldZ42/LLhdF1p1aaWO1aqvEntuur2CnVpGKvwg8XIwgrd7cmy++Q7cX9ntR8e80KE8OFClB
QfkAjYiJENjGlMII3GScH7vh+vdgAqSzAJEEyF133uSx7WHGKMaUpxQl45IwcExxdbiWubadPlst
1aa0O2u2+Z9/+mwGC1o9r+X0WtjAlpYgyYRRLpB/sAlD78RN6SPIvft3c9KXhc7SyqXXkAQT7IGn
CI8fb8oTct1zxA==
','2021-09-24 08:34:15','eJyNUdtugkAQ/ZVmn9UM6wXhDUErNlWC26fGB8JFTWQhsLa1xn/vzCKNbWrSIezuzJyZM5czmxzr
53rL7DNzynKeVPSaNed+nzS39GWtpK+1ldppL2nMnS4d31s47ILSYaL4Z9zan08wzrjGTfafWINP
5IwDN8Digzb10IAQOJCY5pB1GCK9NGvAZRTXPQCOv9EzAL1ulXrqmqYLVpcPBAe7P7DRi0xeER/z
VKqmPlXM/CBXa1WHpTY9Vu0IdD1/l0NiWHzcson8Pp/48GXmyAQpmP2KU6i28jCViSjw+EHQjgQJ
VtCQwMhCDh2Cee6AgUxgGozINA1zXOGiSm3VuIJMM+OT+goqVZ0Q84KBOP3Nd/4wzcjvJkr3H6gT
XUvqzUmi/GGxK+SJ0bYI4sSxarf5a7djKsniGirKW07XcV2mF74uDznavEhFuripfDuUt/QUyICa
0jmfRNDlYPbBHHGOxrBOpGqa1SDxLnWl0NddXTb4oXwB6afRvA==
',0,NULL,'TIMEOUT-CIHUB',4186),
	 (4222,'20210924SIHBIDJ1000O9900000093',NULL,'15530','20210924SIHBIDJ1010O0100000091','2021-09-24 09:47:29','eJyNUE1vwjAM/S8+Q+WkEZDcWspHkFin0dvEAdEClWhamiBNQ/vvc9qyE4dZih3H9vPLe0B8t1t7
BvWAqGnWeetvy96XZd5Ho411RndZ6i5d1Wew0+tYJxsGP2QjyOp/zs0XbxHNRcNcXH4TB+2XA0fO
UHLxhEbEVErsTIYwAupMilPf3ByONkA+CxBZgIKq87ZI3AAzRjnmIuNMianiEmhTUh/vVWFcz8/V
S/1euZ2zH8XNP63apwQdn9d0Bi5/27IKFJuEXEgxoxOiV+JLmxOoT/p3ezbXhcmzmtxrSIYpDsCS
pNx7UX4Bgjxz5A==
','2021-09-24 09:47:29','eJyNUk1vgkAQ/SvNnNXsIqniDQErbaoEtumh8UAErIksBNavGv97ZwcxTaNph7C7Mzvz3mOGE4y3
9Wu9gtEJ7LKcJpU+TZp1vU6aXfqyVtInb64+6VZ74Hgz23efbTijdUAU/6yL/OkY6/ilbrz+Qg2+
JgeDGZxZhtlCM85CZjA0bj1y6ABmumnWJJfxsu4xZuDLe5wBQUW7pZYmQi8K5rPIw6hTpa66gHeZ
1TVMYfCRORgZA0B+t1hu81SqRrUqJn6Qq0jVYUmhp6ptDKm8LZKsPzRbNpHf55tXK7lBVF9mtkyQ
CEYfpyb6m6HtVMuAMehcU2c5JV/aMKQ2sCGcFziLww10TyaiwOUexbylsXjLgjh/6xEHogHbEQ66
unU1Dj8jZjzq3gWVqo6Y84ZFOPfFFT9MM33vJIp6HKij3ma6f75M4koVD+/rKt7HEuhvicpNjlBu
rGLC9+RuU/5EEOgB019A0l9E0MUfyGyGE9aJVI1aShF7SVSsT7LOC3zQvgGQweNh
',0,NULL,'SUCCESS',4217),
	 (4228,'20210924SIHBIDJ1000O9900000098',NULL,'15680','20210924SIHBIDJ1010O0100000096','2021-09-24 10:00:00','eJyNUE1vwjAM/S8+Q+VkpcO9tZSPTNo6jd4Qh4kWqETTrgnSNMR/n9OPGwesyI5j+/nl3SC+mndz
gvAGUdNs8tbdVr0vy7yPWmljteqy1J67qstgqzaxSt4E3NkmkNVPzi2WHxHPRcNcXP4xB+WWg0Qp
kKQ/QiNiSoSd0RwmwJ1Jceybm++D8VDOPUThoc/VRVskdoCZIk2ln0kZIvIB3pTUh2tVaNvzs/VK
fVZ2a81X8eOe1u0oQcfnMZ2BC43bsgpCEbxIn2YBYvBKTolfpY8Q7vjf7UlfljrPanaPIQWmOABT
APe9E+UfeoNz3w==
','2021-09-24 10:00:00','eJyNUttugkAQ/ZVmn9UsF1vgDQErbSoEtk+ND0TA0sBCYLVa4793dhDTNJp2CHuZnTnn5OweyWzb
vXQbYh2J3TSLtJWreT8WRdrP3Oed4D7uAvGOp3JHHG9p++6TTU4QI8Lqf/bF/mIGfcq5b1Z8gQZf
khOVqgo1VX2ApgqNqEohFE2hZESg0s3yvrhJ1t2EUhV+ZYKnABXv1lIai7w4DJaxB1mnzVxxBh9T
c6zqTFWsqWlNDQL8br3eVhkXvWpRz/2wErHoogZTj+1gDKq8LhJD04yBjVW3+YJ2w0tA9Xlu8xSI
iPV27LO/GQanBgZqPpDRpXRZYfHZBgNtoECxgrvYX0H3eMpqGG5RBAONeT+wAM7fetgeaYjtMAe2
0roOLj9HZlhK78JWtAeoeYUmuPfVBT/KcnnupAI9DsVBTkvpX/yR5G1R3tlpUhF8KnFTVoDjJiJB
cI/vyuZnO4MdoQqoQN3PLBzD69E1Q4dU1KVc9FKxhH1y5KEaajqt4IP4BrpZ4gU=
',0,NULL,'SUCCESS',4223),
	 (4234,'20210924SIHBIDJ1000O9900000103',NULL,'12550','20210924SIHBIDJ1010O0100000101','2021-09-24 10:05:40','eJyNUMFOwzAM/Reft8rxMtTk1q4bCxIUsd4QB7R2oxJNS5NJiIl/x2k7TjvMByeO/Z5f3hnSk3t0
R9BnSLpuW/bhthlzXZfjaY113pqhyv3H0A0V7Mw2NdmDgF+OGRTtjbjV+ilhXDLh0vqHNZiwHAhJ
oCJ5oUbEXCkMIXABM+DJrDqMw9373kVIccTNCCV3V32V+YlmjmpOsiDSuNQSgTdl7f7UVNaP+ny7
Mc+N33n3Un2Fp/v+YsGg57qcScv/tqIBLe4WJNVSSYwpDk58G3sA/cr/7o/2c23LouV0nVJgjmJi
ZSvfgil/cHxzxA==
','2021-09-24 10:05:40','eJyNUtFugjAU/ZWlz2pKZVN5Q8DJlgGB7mnxwQg4EigEitMZ/323FzDLMrOV0Pbe3nvO4ZQzWbbN
S7MnxpmYVbWOa7VbdXOWxd0qXNFI4WLky3c8VRGxHM907SeTXGCMCC//2Re56yX0aX3fMvsEDa4i
J4wyjS6YPkBTjYaUUTUe2IyMCFTaSdoVV9tdM6GUwatNNEoQKjrslDQeOlHge5EDWatObNmDj+li
zHTOmEHvjemcAL9d7toiEbJTLcuVGxQykk1YYeqxHoxBlb+LxDGbaQMbL27z+fVe5IDqitQUMRAR
4+3cZX8yDE4NDBplZHQt9Qos7m2Yow0UKDZwF8df0B0R8xKmWxR+TwP7gQVw/tbDj0hDTItbECrr
Grj8FJlhq7wLalmfoOYVuuDeN1f8MEnVuRVL9DiQJ7V4yj8zzqpW1uVd1DZZXhL8W6IqLwDK3sot
4jvikFffEThEBL8ApT/zYAw/kD6d65AKm1jITi2W8A+BVHSKsi4beGB8AXOX414=
',0,NULL,'SUCCESS',4229);
