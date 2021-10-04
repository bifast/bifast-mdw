-- public.account_enquiry definition

-- Drop table

-- DROP TABLE public.account_enquiry;

CREATE TABLE public.account_enquiry (
	id int8 NOT NULL,
	account_no varchar(255) NULL,
	amount numeric(19, 2) NULL,
	intr_ref_id varchar(20) NULL,
	orign_bank varchar(255) NULL,
	recpt_bank varchar(255) NULL,
	bizmsgid varchar(50) NULL,
	call_status varchar(255) NULL,
	cihub_req_time timestamp NULL,
	error_message varchar(400) NULL,
	full_request_msg varchar(4000) NULL,
	full_response_msg varchar(4000) NULL,
	resp_bizmsgid varchar(50) NULL,
	response_status varchar(20) NULL,
	chnl_trx_id int8 NULL,
	cihub_elapsed_time int8 NULL,
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


-- public.client definition

-- Drop table

-- DROP TABLE public.client;

CREATE TABLE public.client (
	client_id varchar(255) NOT NULL,
	channel_code varchar(10) NULL,
	client_name varchar(100) NULL,
	create_dt timestamp NULL,
	modif_dt timestamp NULL,
	secret_key varchar(100) NULL,
	CONSTRAINT client_pkey PRIMARY KEY (client_id)
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
	cihub_elapsed_time int8 NULL,
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


-- public.notification_pool definition

-- Drop table

-- DROP TABLE public.notification_pool;

CREATE TABLE public.notification_pool (
	id int8 NOT NULL,
	ack_dt timestamp NULL,
	ack_status varchar(255) NULL,
	cre_dt timestamp NULL,
	customer_account varchar(255) NULL,
	customer_id varchar(255) NULL,
	customer_name varchar(255) NULL,
	destination varchar(255) NULL,
	distribution_channel varchar(255) NULL,
	email_addr varchar(255) NULL,
	event_ctgr varchar(255) NULL,
	event_grp varchar(255) NULL,
	message_desc varchar(255) NULL,
	notif_level varchar(255) NULL,
	phone_no varchar(255) NULL,
	ref_id varchar(255) NULL,
	urgency varchar(255) NULL,
	CONSTRAINT notification_pool_pkey PRIMARY KEY (id)
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
	cihub_elapsed_time int8 NULL,
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
	cihub_elapsed_time int8 NULL,
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


-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
	id int8 NOT NULL,
	"password" varchar(255) NULL,
	"role" varchar(255) NULL,
	username varchar(255) NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

INSERT INTO public.account_enquiry (id,account_no,amount,intr_ref_id,orign_bank,recpt_bank,bizmsgid,call_status,cihub_req_time,error_message,full_request_msg,full_response_msg,resp_bizmsgid,response_status,chnl_trx_id,cihub_elapsed_time) VALUES
	 (4339,'7755732444',4556700.56,NULL,'MNDRIDJA','SIHBIDJ1','20210926MNDRIDJA510ORB000003','SUCCESS','2021-09-26 19:53:41.226',NULL,'eJy1lEtvgkAQx+98CrLnahYUXzcePrap2uCml6YHBVRSAbOsia3xu3d5iYurpiYSIPCfmeU3Mzsc
JFkGxi4exysg9+QDe2WCvt2OXFIKTBpwr4ng+y4vpWKIwpiG6MLEjFO6JpcyM2TeYDyxbGS96oCz
HyXRc/F0fCkAcfR0wBkaGQxQeQzQ8H9ZlVFWV6BCVYFdtVUkrSlwahswORrgFMMCLG9ZxGznTlyH
sMMupQ47pZtJPIuelq3Bbk3VMOz2IGRnRptzACtydoEXUr65Po0GyIxpQEyXYrKslHJIqvuhoMsr
I8gmP8pkSlIcnLE2alDBSsHKOU8W0yXex6mzwptmlG4CFC4FXUxNY5qBmW/2EIgbVK4HsqTxPl/w
k0/zPaDC7dIPXRyx2/UaXHY0D2Wful+4c2wueRRSYnyneeoBFZB9zDc7L12/qWmtNoR1rVVFMJ2f
1ANZ9vUvmWuyMrLNZ/UNzPfAWtBsT1SCEl1fibhuTd+N+bv3i+BnsDKRfD7u09DEP4f/oYmqmSI7
joj5YdZ2W9PaDbXZbP6Dtpwc+Us615L7UTr+AVtfPQs=
','eJyNkU1vwjAMhv+Lz4DStKVLb/0YI0gMVLLTxGHqB6u0plUaJDa0/z4npWibhjQf3Lix/TivzxAf
+3V/gPAMUdctC2VOi8HXdTF8JZe9ltxGG/1qb00EO76Mebpy4BNtAqL9Z936Mc2wLrrUxfUHzsAN
HCihDmF0Prb2HZIx5hNjdwwmgJlpWQ3J3UveE0JnhDgzh+BlospUX7pMCZvSuXBY6LuhRwFBaZsf
m1LqYTzdLvi20TvdZ5399aBGBew4f08zjMKuNNHc5okTl1UkC0RA+IwiqIN8u5eFaNH9AIyKIGCT
xRbhIsEWYJcbqRczmeJkIZCtEoGheVSP+leWi0fzqq3S6h1znlAzlH5/7Z+VlblPCq2iPNfjnn5t
LQh8P3Cp53lgt91975lESWLXuTfuCzS/q6o=
','20210926SIHBIDJ1510R9950000089','RJCT',NULL,1504),
	 (4340,'77556444',4222200.00,NULL,'MNDRIDJA','SIHBIDJ1','20210926MNDRIDJA510ORB000004','SUCCESS','2021-09-26 20:11:26.762',NULL,'eJy1lElvgkAUx+98CjLnagYKbjcWl2mqNjjppelBWZRUwMCY2Bq/e4dNHBw1mjhRAm8Zfv/35rEX
RBHo22ScLIHYE/f0kRq0zWbkxJWBmgbMY2rwfYc1ZcYQhQkJ0ZmLOqdkFZ+bqSOPBuOJaSHzTQOM
/yDw7su7w0sJiKOnA87QSKeA0mOAuv9Hq4zyugIZyhLsyq1StCrBqaXDdCngmEMTTNcrczZzO2lC
2KF/qQk7VZgRuyY5btuA3YasYtjtQUh/OW3BAczI3gZuSNjm+iQaICMhQWw4BMderZTDuH4eSrqi
Mhw1xarEVKQ4OGF9bUAJSyUrEzxZTD28S7JgiXXNCFkHKPQ4XcxcY5KDGe/WEPAbVO0HctF4V2z4
xcr8CAj3uPRDB0f0crkG5x0tUumrbhfuFJsRj0IS6z+ZTi0gHLLP+XrrZvsrMl0QNmulTTXbv1kE
Mq3LbzJW8VLPD5/Z1zHbA3NB8jNRS0rt2pLHdW36rszfrU8EO4O1iWT1OE9D438c7kPjVTNDtm0e
88Os7baqthRFuYO1mhvxWzi1pdeDcPgHq0k8jw==
','eJyNkUtPwzAMx7+Lz9uUhD7U3vpgLJPGpi47oR1QH6MSTas0kwYT3x0nbREgJuGDGze2f87fV4jP
/aY/QXiFqOtWhTKn5eDruhi+ksteS26jrX6xtyaCPV/FPF1T+ECbgWj/Wbd5TDOsi8a6uH7HGbiB
AyOMkoB5U2uXkiwIXGIsYDADzEzLakjunvOeELYghC4owctElakeu8xJMGeeYCSkNGQ+ICht83NT
Sj2Mp9sl3zV6r/uss78e1KSAHefvacZR7iaaaG7zxIXLKpIFIiB8QhHUSb7ey0K06H4AJkUQsM1i
i3CQYAuwy43U0UymuFgIZOtEYGge1aP+leXi0bxqp7R6w5wDaobSH7/6Z2Vl7pNCqyjP9bSnX1vz
fdf1HMcBu+vue8ckShK7zKNxn8Z7qy0=
','20210926SIHBIDJ1510R9950000092','RJCT',NULL,335),
	 (4342,'1814449',1545500.00,'41468','SIHBIDJ1','BMRIIDJA','20210926SIHBIDJ1510O0100000018','SUCCESS','2021-09-26 21:32:25.446',NULL,'eJyVUl1PwjAU/S99BtLWjQBv68pHjYjBxhfjA2zjI7KOdJcEJfx3b1swaiRik3X39t7Tc3pyD0Ts
6nG9JL0DSbbbUW5dNAj7ep2Hv1GmBqN8NoGVr7qMPKqRUPKWkSOuBtHVlTgxnirEJSecWL+jBuXI
Caec0S5vn6+OGZ1QRv1iHdIg2CmLRWjezrK6RWkHP9airpraQsLpmibtNnlbc9a74T0eE2SSVbYr
CwNBH1QDldZQ2jQHbRfucGjPJnhFvwvCFbGo/cmnywuMDXI/nyz0vsY6w+wRYFMq45l8PAZHkd5N
h8QZEXTovW95PpCHEoJ7fZPrCrdLir5bhBf8pRzJlAErXr2MpPSOPM02u8JJjaM4phRddS/M3vBI
yanDpCu7FM552Rcai3IOzqxjiJIl/GNg/HOvwHwdloAJjB6dZXBu/AFjHRZFUdeP2IvbPgBlENam
','eJyNUW1PwjAQ/iumn4W0pcOxb6MDmUZYtvrBGD6QveAS6Ja1KEj47147ZtBI4i3ry/W5e+65O6Lx
Tj2pNfKOyK/rWdaY07RdyzJrdxlKpWVobwv9Zl/NDfHJ3A+DBx+dwG6RqP4Zl4SzMcSRc9y4/IQa
QkOOKKYEj+iwS+0QHGOKwcgdxugWATLIixZcr1LVx5jCT/rEvPImD/Q5TQ+PenQoCPMG1KMOAqag
SnfbXOq2Pl1Nw2irE63i2rrum64Ftp6/y7HmMqdjE9vrfGIfysKXGVAg7xW60KzlZiIzUcHyg6Br
CRAs8JmEuMBhQyDPFTAYI2xokGJvaZDPBYerkaVgBIVlhqPRFTW6OQDmGeKg+8vv/HFemHeeaas/
0gezzY22ZKdyWd28VOsSmXEZjJ+muhvnr+ESlzDGRhYq6ktS7nOO7MSTerMFX7DSK1vdRL5v6kt+
E4gwARk256OIehQzlzjugIEzVpnUrVoLEh/SlooHVtZpCR/YFxAF0ng=
','20210926CENAIDJA510R0200001700','ACTC',4341,1117),
	 (4169,'7712589',1990000.00,'46149','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000060','TIMEOUT-CIHUB','2021-09-24 08:03:35','Timeout menunggu response dari CIHUB','eJyVUstuwjAQ/BefAdkhPMItiQO4KlCB1UvVAyThoRIHOYtEi/j3rm0itVVRqaU4u94dz3i0ZxId
q0m1IYMzCQ+HcaZNNHT7bpe5vxKqAiVsNoOtrZqMLMQ4EvyBkQuuBpHlnbg4mYaIC6+4aPeBGoQh
Jx71GA08v766w+iMMmpXl5IGwU6er13zYZlWLUr7+LEW7WM11jmH6zVNGjQ9X3p0QNuDdocgEy/T
Y5ErcPqgHIq4gkLHGUi9NocjXZtgFf0uCJffZX5Q88niBmODTFeztTxVWGeYLQD2hVCWycYTsHY8
zkfEGOF0yJNteTmTpwKce4nKZInbLUXfLcIL/lKOZEKBjt6sjLCwjjwv98fcSA0C04mumhem73gk
+Nxg4q3eRMZ5nkQSi3wFxqyLi8IN/GNg7HPvwHwdFodxjBadplA3/oD1eszr9AM7Yq9m+wQOrNZz
',NULL,NULL,NULL,4168,NULL),
	 (4173,'1814449',54500.00,'4511168','SIHBIDJ1','BMRIIDJA','20210924SIHBIDJ1510O0100000062','SUCCESS','2021-09-24 08:11:08',NULL,'eJyVUstuwjAQ/BefAdmRg4BbHPNwVUoFVi9VD5CEh0oc5CwSLeLfu7ahaquiUktx1t6dnfFoj0Ts
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
','20210924CENAIDJA510R0200000400','ACTC',4172,NULL),
	 (4182,'110044292',89994500.00,'211411','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000066','SUCCESS','2021-09-24 08:27:12',NULL,'eJyVUstuwjAQ/BefAdlWoIRbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEolVRqaU4a+/Ozni0JxIf
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
','20210924CENAIDJA510R0200000332','ACTC',4181,NULL),
	 (4187,'8000292',8904000.00,'210071','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000069','SUCCESS','2021-09-24 08:34:10',NULL,'eJyVUstuwjAQ/BefAW3StE24JTEPVwUqsHqpeoAkPFTiIGeRaBH/3rVNpLYqKrUUZ+3d2RmP9siS
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
','20210924CENAIDJA510R0200000775','ACTC',4186,500),
	 (4171,'1819199',54500.00,'452168','SIHBIDJ1','BMRIIDJA','20210924SIHBIDJ1510O0100000061','SUCCESS','2021-09-24 08:08:40',NULL,'eJyVUstuwjAQ/BefAa2jBCXc4oSHq1IqsHqpeoAkPFSSIGeRaBH/3rUNVVsVlVqKs/bu7IxHe2Ri
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
','20210924CENAIDJA510R0200001381','ACTC',4170,444),
	 (4216,'8289331',1000200.00,'15502','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000089','TIMEOUT-CIHUB','2021-09-24 09:43:57','Timeout menunggu response dari CIHUB','eJyVUstuwjAQ/BefAdmGqAm3JObhqkAFVi9VDyUJD5U4yFkkWsS/d20Tqa2KSi3F2fXueMajPZHk
UE/qNemfSLzfj3Njo6Hft9vc/7XUNWjpshlsXNVmZCHHiRT3jJxxtYiqbsSlg2mMuPiCS7YfqEFa
csIpZzTivebqgNEZZdStMCItgp2iWPnm/WtWd/AcP9ahIVZTUwi4XNOmUZv3FGf9Xrcf3BFkElV2
KAsNXh9UQ5nWUJo0B2VW9nBkGhOcot8F4WJBQHnDp8orjC0yXc5W6lhjnWG2ANiVUjsmF0/A2fEw
HxFrhNehjq7l+UQeS/DuDXSuKtyuKfpuEV7wl3IkkxpM8uZkxKVz5Ol1dyisVGzjlKKr9oXZOx5J
MbeYdGPWiXVeDBKFRbEEa9bZR/Ea/jEw7rk3YL4Oi8d4RofOMmgaf8BCHkbdrh/NF7t9Aikc1nA=
',NULL,NULL,NULL,4215,NULL),
	 (4193,'2000292',8904000.00,'21861','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000074','SUCCESS','2021-09-24 09:05:53',NULL,'eJyVUl1PwjAU/S99RtLWoRtv28pHjYCBxhfjA2zjI7KOdJcEJfx3b1uWqJGITdbd9t7Tc+7JPZJk
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
','20210924CENAIDJA510R0200001817','ACTC',4192,555);
INSERT INTO public.account_enquiry (id,account_no,amount,intr_ref_id,orign_bank,recpt_bank,bizmsgid,call_status,cihub_req_time,error_message,full_request_msg,full_response_msg,resp_bizmsgid,response_status,chnl_trx_id,cihub_elapsed_time) VALUES
	 (4198,'2111132',8904000.00,'21862','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000077','SUCCESS','2021-09-24 09:14:38',NULL,'eJyVUstuwjAQ/BefAdkmLYFbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEaqui0pXi7Hp3POORTyQ+
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
','20210924CENAIDJA510R0200000183','ACTC',4197,533),
	 (4203,'2100323',800000.00,'21002','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000080','SUCCESS','2021-09-24 09:28:28',NULL,'eJyVUstuwjAQ/BefATmGSoFbEvNwVaACi0vVAyThoRIHOYtEi/j3rtdEaquiUivxY3dnZzzymcXH
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
','20210924CENAIDJA510R0200001597','ACTC',4202,562),
	 (4208,'9234323',1800200.00,'21002','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000083','SUCCESS','2021-09-24 09:32:48',NULL,'eJyVUstuwjAQ/BefAdlOKgVuSczDVYEKLC5VD5CEh0oc5CwSLeLfu7aJ1FZFpZbirHd3dsYjn0ly
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
','20210924CENAIDJA510R0200001635','RJCT',4207,523),
	 (4218,'8289331',1000200.00,'15530','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000090','SUCCESS','2021-09-24 09:47:26',NULL,'eJyVUstuwjAQ/BefAdkG2oRbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEaquiUktxdr07nvFoTyQ+
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
','20210924CENAIDJA510R0200000434','ACTC',4217,562),
	 (4271,'7712589',1990000.00,'46149','SIHBIDJ1','CENAIDJA','20210925SIHBIDJ1510O0100000023','TIMEOUT-CIHUB','2021-09-25 07:34:30','Timeout menunggu response dari CIHUB','eJyVUstuwjAQ/BefKbIDKZhbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEaquiUktxdr07nvFoTyQ+
VONqTXonEu33o8zYaOD37Tbzfy11BVq6bAobV7UZmctRLMUDI2dcDaLKG3FJfxIhLrrg4u0HapCW
nAQ0YJQHYX11yOiUMupW0CINgp0iX/nm/SKtmpR28WNN2sVqYnIBl2vuKL8LQsV4r9XutShBJlGm
hyLX4PVBOZBJBYVJMlBmZQ+HpjbBKfpdEK72PWvzmk8VVxgbZLKcrtSxwjrDbA6wK6R2TC4eg7Pj
cTYk1givQx1dy8uJPBXg3evrTJW4XVP03SK84C/lSCY1mPjNyYgK58jzYnfIrVTObSe6al+YvuOR
FDOLSTZmHVvnRT9WWBRLsGadfRSt4R8D4557A+brsHiMZ3ToNIW68Qes02FB2OVuxF7t9gkuqNaF
',NULL,NULL,NULL,4270,NULL),
	 (4277,'825331',422260.00,'11004','SIHBIDJ1','CENAIDJA','20210925SIHBIDJ1510O0100000026','SUCCESS','2021-09-25 07:39:26',NULL,'eJyVUstuwjAQ/BefAdmGIMItiXm4KlCB1UvVAyThoRIHOYtEi/j3rm0itVVRqaVs1t6dnfHIZxIf
q0m1If0ziQ6HcWZsNvRxt8v8X0tdgZZuN4Otq9odWchxLMUDIxdcDaLKO3HJYBohLrri4t0HapCW
nHDKGQ15UI8OGJ1RRt3iXdIg2CnytW8+LNOqRWkPP9aiPawmJhdwHdOkYZMHioX9dthHLDKJMj0W
uQavD8qhTCooTJKBMmt7ODK1CU7R74JwMYydmk8VNxgbZLqardWpwjrD3QJgX0jtmFw+AWfH43xE
rBFehzq5lpczeSrAuzfQmSox3FL03SIc8JdyJJMaTPzmZESFc+R5uT/mCOxwzrsUTbUXTN/xRIq5
hSRbs4mt8WIQKyyKFVivLj6LNvCP9+Juewfm61vxGM/o0GkKdeMPWI8H7bZ/mK82fAKXFdYH
','eJyNUdtOwkAQ/RUzz0B2t1Shb2ULUo1A2vXJ+ND0gjXttmkXFQn/7uyWGjSSuE1nL3M558wcYLZr
H9otOAdw63qZNPq06GyeJ90ufdkq6ZvbWr0Yr74Bn69c37tz4YhrAKL6Z17oL2eYR095s/wTOfga
HBhhlEyZ3Ze2KQkII7goWhgARnpp1gXXUdyOCGH40xHVXt6knjqVGZLpkNmCMseaOuwaEMmr4l2Z
StXxU9XC35QqVG1Qm6fbpm+B4fM3HcPFIqxHE+VlPPHhy8yVCUKA84RdaLaymMtEVGh+APQtQYA1
6UAI1hh0KVjnQrAmg3YMGszAgMsFx6uW1eIIMoOMR61r06hmjzGPmIfdf/6uH6SZ9vNEGf0btdfb
SmsLX6OsyYurZZTkoOelg9w4Vv08f013wmzLoiZS1Oeg3OUczMTDuijxzYtUZNjN5VtRn+PrRCAU
ZZiS92IzRPE34wkhuilBm0jVqTVB4l0aqsQyso7P+OH6Atxm0kk=
','20210925CENAIDJA510R0200001200','ACTC',4276,674),
	 (4275,'677589',1390000.00,'41449','SIHBIDJ1','CENAIDJA','20210925SIHBIDJ1510O0100000025','SUCCESS','2021-09-25 07:36:12',NULL,'eJyVUstuwjAQ/BefAdmBAOGWxAFcFajA6qXqAZLwUImDnEWiRfx71zaR2qqo1FKcXe+OZzzaM4mO
1aTakMGZhIfDONMmGrp9t8vcXwlVgRI2m8HWVk1GFmIcCf7AyAVXg8jyTlycTEPEhVdctPtADcKQ
E496jAaeX1/tMzqjjNrl+aRBsJPna9d8WKZVi9I+fqxF+1iNdc7hek2TBk3PlywYtLsD5hFk4mV6
LHIFTh+UQxFXUOg4A6nX5nCkaxOsot8F4eqwTieo+WRxg7FBpqvZWp4qrDPMFgD7QijLZOMJWDse
5yNijHA65Mm2vJzJUwHOvURlssTtlqLvFuEFfylHMqFAR29WRlhYR56X+2NupLYD04mumhem73gk
+Nxg4q3eRMZ5nkQSi3wFxqyLi8IN/GNg7HPvwHwdFodxjBadplA3/oB1ez2/H9gJezXbJ15b1lY=
','eJyNUdtuwjAM/ZUpz4CS0MLoW0hhdNMAlexp2kPXC0OiadWGMYb49zkundg0pLlqLo7tc459JONd
/ViviXckoixnSWVP02bdbJJm14GujQ7wtjBv+GpvRE7mIvDvBTmBdYgq/pm3CmZjyGPnvPHmEzgE
Fpxwyhkdcbct7TIaUk7RBgPSIRDpp1kTXEZx3aOUw896jMKrrFLfnMt06ajLXcW41x94jBNA8ot4
l6faNPxMMQ2WuVmZOizRdVe1LUA+f9NBc1y3RVP5dTz1EehM6AQgiPcMXajWejvRiSpg+QHQtgQA
FvQMwi0GpkCdK8GWC3OcEbFgCEOEVBKuVlYNI8gQGY5W17Iy1QFiniAPuv/yXT9MM/suE4P6l+Zg
t7nVJrQp9I3IX6NqH5kNsSOzcSKOTTvSXwMeDIfu7QgjVXmJK4WUBIe+Krc5+PzIREhwot+35SUF
m0goAyVY8kEtu6DfGQ4d1zrDOtGmEYxBaq+RLe2jstMLfGBfxYDTxw==
','20210925CENAIDJA510R0200000066','ACTC',4274,543),
	 (4312,'7723004444',4566634.56,NULL,'MNDRIDJA','SIHBIDJ1','20210925FASTIDJA510ORB000001','SUCCESS','2021-09-26 09:34:19.695',NULL,'eJy1lFlvgkAQx9/5FGSfq1lQ8Hjj8NimaoObvjR9UA4lFTCwJrbG797lEhdXTU3cKIH/zCy/mdnh
IIgi0HfJJFkBsS8e6CMVtO127MSVQKUh85gKvu+wUiaGKExIiC5M1Dgj6/hSpobcG0ympoXMVw0w
9qPAuy/vji8lII6eDjhHY50CSo8B6v4vrTLK6wpkKEuwJytDbY7TpBUJziwdpksCpxgaYLpeGbNd
2EkTwi79S03YrdyM2DXJadsG7DVkBcNeH0L6y2kLDmBG9i5wQ8I21yfREBkJCWLDITj2aqUcxfXz
UNIVleFkUyz5RFmR4uCMtdWAEpZKVsZ5upx5eJ9kzhJrmhOyCVDocbqYmSYkBzPerBHgN6jaD+RJ
432x4Seb5ntAuMdlEDo4opfrNbjsaBFKX3W/cOfYTPIoJLH+neWpBYRD9rHY7Nxs/7aiqmqr3VTU
OoJh/2QeyLSuv8lYxys9P3zmQMdsD8wlyc9ELSjVtRWP69b03Zi/exPIzmBtItl8nKeh8b9e/0Pj
VTNDtm0e88OsnY7cgrBN1z9oq8kRv4RzLb0eheMfB3E87w==
','eJyNkU1vwjAMhv+LzwW5aYG1t36MESQGKtlp4jD1gyGNtEqDtA3x3+ekLULTkOaDGze2nzf2GeJT
u2r3EJ4happFocxp3vnDoei+kstWS26jtX63tyaCLV/EPF26cCFzQNT/rFs9pxnVRX1dfPgmDdzA
gSFzMWDTofXExSwIJmjswQMHKDMtqy65ectbRDZGdMcu0mWiylT3XUYYjNhUYBB6fshIowNpnZ+O
pdSdPF3P+eaot7rNGvvrSQ0TsHL+VtNL8QeaON7niU8uq0gWhIDwlYag9vLjURaiJncLmMyjrTAT
IcA6iy3CJYItoC53UntjYFAWAtkyERSaR7U0/8py6WhetVFafVHOiym5XHbX/llZmfuk0CrKcz3s
6dfWZjPmIfpkYLfd3PZMoiSx69wZ9wMX4KuF
','20210926SIHBIDJ1510R9950000083','RJCT',NULL,1731),
	 (4315,'677589',137400.00,'47169','SIHBIDJ1','CENAIDJA','20210926SIHBIDJ1510O0100000002','SUCCESS','2021-09-26 10:11:20.856',NULL,'eJyVUstuwjAQ/BefAdkpJIRbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEolVRqaU4u94dz3i0JxIf
qnG1Jr0Tifb7UaZNNHD7dpu5vxKqAiVsNoWNrZqMzMUoFvyRkTOuBpHlnbikP4kQF11w8fYTNQhD
TjzqMRp6fn11h9EpZdQtjzQIdvJ85Zr3i7RqUdrFj7VoF6uJzjlcrmnSsOn5ktEeYz2PEmTiZXoo
cgVOH5QDkVRQ6CQDqVfmcKhrE6yi3wXhagfMD2s+WdxgbJDJcrqSxwrrDLM5wK4QyjLZeAzWjqfZ
kBgjnA55tC2vJ/JcgHOvrzJZ4nZL0XeL8IK/lCOZUKDjdysjKqwjL4vdITdSH4I2pWiqeWD6gSeC
zwwk2eh1bIzn/VhikS/BeHV2UbSGf8yLfe0dmOtZcRjHaNFpCnXjD5gfBJ1uaAfszWxfaz7WDA==
','eJyNUttugkAQ/ZVmntXsrgrCG4JWbKoEtw9N44PhYkllIbC2tcZ/7+wixjY16RD2embOnJk9wnhf
P9ZbsI/glOUsrtRq2oxZFjez8EUtha93S/mqb9UO3MnC8b25Aye0DvDin34rfzZGP3r2G2dfmIOv
yIERRonFjDb0kJKQMIJGLWpABxDpJWkDLjdR3SOE4U97lOCtWyWePIfpEqvLDE76NqU2Q7IOeEW0
zxMhm/xkMfWDXK5kHZb66L5qS6Dz+Tsdbcbgwsbz23z80xepI2KkAPsFq1BtxW4iYl7g8IOgLQkS
LMmZhDDk0C4Y5wYYbWBSwwJFpmnAcbmLWyWrxhakmhmXSldQyeqAmCf0w+qvL/HDJFX3biy1/kAe
1LRQ2p6LbXY3L94KUM1SCCeKZNvMX601THM4sjSSl9eMruO6oNu9Knc5nnkbudGpTcT7rrwmV45A
KGrQIR940GVkRMyh1VcvIKxjIRupGsQ/hM6T9LWm0xo/tG9ZUdFb
','20210926CENAIDJA510R0200001916','ACTC',4314,959),
	 (4317,'18831',492220.00,'15521','SIHBIDJ1','CENAIDJA','20210926SIHBIDJ1510O0100000003','SUCCESS','2021-09-26 10:12:41.426',NULL,'eJyVUttuwjAM/Zc8F5SEiwpvbcMl04AJor1Me4C2XDSaotRIbIh/n5NQaZuGxiw1tWMf++TIZxIf
q0m1If0ziQ6HcWasN/Tnbpf5v5a6Ai1dNIOty9qILOQ4luKBkQtaQFR5Jy4ZTCPERVdcvPtADtIO
J5xyRnu8W7fuMDqjjHprkYBgpcjXvviwTKsmpSF+rElDzCYmF3Bt06C9Bu8qRvuM99tIMiCiTI9F
rsHzg3IokwoKk2SgzNpejkwtgmP0OyE01ulwVs9TxY2JAZmuZmt1qjBvowXAvpDaTXL+BJwcj/MR
sUJ4HurkSl7O5KkAr95AZ6rE4xaj7xJhg7+Y4zCpwcRvjkZUOEWel/tjjsB2j3NOUVT7wPQdb6SY
W0iyNZvYCi8GscKkWIHV6uK9aAP/2Bf32jswX3fFY/xEh05TqAt/wFgYtvxevtrjE4lq1bs=
','eJyNUdtugkAQ/ZVmn9XsLmKFNwSstKk1sH1qfCBcLIksBNaqNf57ZxZpbFOTDmFvcznnzJzIbNc+
txtin4hT14u0wdO8W4si7XYZyFbJQN9e1Lv24o24/tIJvEeHnMEGRFT/zIuCxQzy2CVvVnwChwDB
CaecUYtP+tImoyHlFG3MKRkQiPSyvAuu46QdUcrhZyOGXrfJPHUpM6TWkE8ENWzG7TGADYhXJbsy
k6rjp6p5sCpVpNqw1k8PTd8CzedvOmiM3bMeTZS38cQhkLkjU4Ag9ht0odnIrS9TUcHyA6BvCQC8
0A6EUgMwdArUuRGMZEyTIxtx0DDEcYULV5TVwghyjQxH1LVqVHOEmFfIg+6vv+uHWY5+N1Va/0od
cVuitigud2nWxHd+sy8kwYlhmJMkqp/or/my6dRgOlDU16iu47pEjzyqtyW8ebGKNT1ffmzrawKY
SCjK0hWfxGoI6i2LGyZ2JWxTqTq5OkjspeZKDa3rvIYP7AvvlNMM
','20210926CENAIDJA510R0200000420','ACTC',4316,554);
INSERT INTO public.account_enquiry (id,account_no,amount,intr_ref_id,orign_bank,recpt_bank,bizmsgid,call_status,cihub_req_time,error_message,full_request_msg,full_response_msg,resp_bizmsgid,response_status,chnl_trx_id,cihub_elapsed_time) VALUES
	 (4322,'18831',4000557.00,'150051','SIHBIDJ1','CENAIDJA','20210926SIHBIDJ1510O0100000006','SUCCESS','2021-09-26 10:21:24.412',NULL,'eJyVUstuwjAQ/BefAdkpoYFbEvNwVaACq5eqB0hCiEoc5CwSLeLfu7aJ1FZFpZbirL07O+PRnkh0
qKd1TgYnEu73k1SbaOT2okjdXwlVgxL2NIetzZoTWYpJJPgDI2dcLSKrG3HxcBYiLrzgouIDNQhD
TjzqMdr3ek1rn9E5ZdStHmkRrOTZxhXvV0ndoTTAj3VogNlYZxwubdq03/Z6ktGBxwZelyATr5JD
mSlw+qAaibiGUscpSL0xl2PdmGAV/S4I2XxKfdbwyfIKY4vM1vONPNaYN9VLgF0plGWy8RSsHY+L
MTFGOB3yaEteTuSpBOfeUKWywu2aou8WYYO/lCOZUKCjNysjLK0jz6vdIUNgF+t8/x5dNS9M3vFK
8IXBxFudR8Z5PowkJvkajFlnF4U5/GNg7HNvwHwdFodxjBadJNAU/oCxILhzg/lqtk9RFtXx
','eJyNUdtOwkAQ/RWzz0BmF4rAW2lBihGassYHw0PTC5DQbdMuKhL+3ZktNWgkcZrubS5nzpwTGx+q
p2rDRidmF8UsLuk0rdfdLq535alKK8/clnprvHRjzmRhe+7cZme0FpP5P/NW3myMefySN959Yg8e
gTMBgsNQ9JvSFocABBgb9lmLYaSbpHVwEUZVB0Dgzzsc0OuUiasvZdowbIu+hO5I8JHoMURy8+iQ
JUrX/el86vmZXukqKMzTQ9mMwPTzdztkfNi9b9BkdhtPfngqtVWMEGz0ilMoN2o/UbHMcfkB0IwE
AZZQgwAQX5OCdW4EYy8WgMUZgRkYZjvSwSvRqlCC1CDjkXj5pS6PGPOMiTj99Xf9IEnJ78Ta8Pf1
kbYFcZvn21DdvexUqENGelGQHUW60fOXunww6HITKItrTMd2HGYEXxX7DN9cKkjNTdTbvriGp0QG
RMpUfJR+W6D8PcsSpHJQxUrXZE2QfFemU+gaVuc1fmhfPgTSPQ==
','20210926CENAIDJA510R0200000096','ACTC',4321,968),
	 (4327,'14551',4355557.00,'155221','SIHBIDJ1','CENAIDJA','20210926SIHBIDJ1510O0100000009','SUCCESS','2021-09-26 16:19:30.83',NULL,'eJyVUstuwjAQ/BefAdmBUMItiQO4KlCB1UvVAyThoRIHOYtEi/j3rm0i0aqo1FI2a++OZzzaE4kO
1bhak/6JhPv9KNMmG7i43Wbur4SqQAm7m8LGVs2OzMUoEvyRkTOuBpHlnbg4mYSICy+4aPuJGoQh
Jx71GA28bn21z+iUMupWQBoEO3m+cs37RVq1KO3hx1q0h9VY5xwu1zRp0PS6knX7LOi3KUEmXqaH
Ilfg9EE5EHEFhY4zkHplDoe6NsEq+l0Qsvm+57GaTxY3GBtkspyu5LHCuumeA+wKoSyTzcdg7Xia
DYkxwumQR9vyeiLPBTj3EpXJEsMtRd8twgv+Uo5kQoGO3q2MsLCOvCx2hxyBnbaP6wFdNS9MP/BI
8JnBxBu9jozzPIkkFvkSjFlnl4Vr+MfA2OfegbkeFodxjBadplA3/oCxju+7wXwz4QuT+tYb
','eJyNUctuwjAQ/JXKZ0C2aUDOzSRQ0qqAgnuqOER50EjEiRLTliL+vbsOQbQqUh3Fz9mdmd0jmeyb
52ZL3CORVTVPatzN2jnPk3bVgW6MDuxpad7sK56IN13IwH+U5ASjR1T5z7h1MJ9AHDvHTfIv0BAg
OeGUMyr4qEvtMBpSTnGw8Zj0CCD9NGvBVRQ3A0o5/GzAKLx6deqbc5o+FX0+UlS4TLhDIOsRv4z3
RapNq8+Us2BVmLVpwspePdRdCayev+W0WsSFTRW3+dRnoDOpE6Ag7itUod7q3VQnqoTpB0FXEiBY
0jMJFcBhQyDPDTBIcRzOGUEyS0Okpzw4oq0GWpBZZtiir1Vt6gNgXiAQqr+55A/TDN+9xFj/K3PA
ZYHeZJKbQ3QXpjon2C7EyDg2XTt/NZfdOw6zQFVdU3rS84jt97raFXDnRyay2qb6fVdds2MgoejJ
ZnxSqz6nQ8qFI/AybBJtWq8WpD60FQoQNHXawAfjG3Iy0d8=
','20210926CENAIDJA510R0200000177','ACTC',4326,1914),
	 (4332,'821751',4355557.00,'155221','SIHBIDJ1','CENAIDJA','20210926SIHBIDJ1510O0100000012','SUCCESS','2021-09-26 16:21:37.543',NULL,'eJyVUstuwjAQ/BefAdmm4XVLYh6uClRg9VL1AEl4qMRBziLRIv69a5tItCoqtZTN2rvjGY/2RKJD
OS7XpHci4X4/So3NBj5ut6n/a6lL0NLtprBxVbsjczmKpHhk5IyrRlRxJy7uT0LEhRdctP1EDdKS
E045o13eqq4OGJ1SRt1inNQIdops5Zv3i6RsUNrBjzVoB6uxyQRcrqnTbp23FGv1OOs12wSZRJEc
8kyD1wfFQMYl5CZOQZmVPRyaygSn6HdByBYEnLOKT+U3GGtkspyu1LHEuu2eA+xyqR2Ty8fg7Hia
DYk1wutQR9fyeiLPOXj3+jpVBYZbir5bhBf8pRzJpAYTvTsZYe4ceVnsDhkCH5oBrja6al+YfOCR
FDOLiTdmHVnnRT9SWBRLsGadfRau4R8D4557B+Z6WDzGMzp0kkDV+APW4awd+Ml8s+ELVBjWRw==
','eJyNUVtvgjAU/itLn9W0RbzwhsULM1OD3dPiA+HiSKAQWrY543/faZHFLTNZCb1+53zfd84ZzRr5
JI/IOSO3qlZxrXeLds6yuF2FL6QSvjlt1at51SfE5hvX9x5ddIHRQ7z8Z9zeX80gjlzjZtknaPA1
OaKYEjyloy61TXCAKYZBxpaFegiQXpK24CqM5ABjCj8ZEAyvrE48dU3Tx9M+HXE8dShxrDECJq+M
miIRqtWnyoW/K9ReyaAyV8u6K4HR87cco2UyHHZsvLjPxz98kboiBgrkvEAV6qPI5yLmJUw/CLqS
AMEWtySYUOAwIZDnDhhQtk0pQZrM0CCXcQZHbUtCC1LDDFvta1er+gSYZwiE6h++8wdJqt9ZrIz/
nTrpZaO9LbM8FMeHdSObIkS6YRrlRpHqGvqrvRNKxjYxSF7dsjKXMWRavq/yAu68UIVG3ly85dWt
AB2IsLZlUq75rk+xRXVqXflAxkK1dg2IvwujFVvG1+UAH4wvDi/S8Q==
','20210926CENAIDJA510R0200001733','ACTC',4331,576),
	 (4338,'921751',438417.00,'15551','SIHBIDJ1','CENAIDJA','20210926SIHBIDJ1510O0100000017','SUCCESS','2021-09-26 16:25:49.158',NULL,'eJyVUstuwjAQ/BefAdkpAcItiQO4KlCB1UvVAyThoRIHOYtEi/j3rm0i0aqo1FKctXdnZzzaE4kO
1bhak/6JhPv9KNMmGrh9u83cXwlVgRL2NIWNzZoTmYtRJPgjI2dcDSLLO3FxMgkRF15w0fYTNQhD
TjzqMRp4nbq1z+iUMmoX65IGwUqer1zxfpFWLUp7+LEW7WE21jmHS5smDZpeR7JO3/P77YAgEy/T
Q5ErcPqgHIi4gkLHGUi9MpdDXZtgFf0uyEjxfZ/VfLK4wdggk+V0JY8V5k31HGBXCGWZbDwGa8fT
bEiMEU6HPNqS1xN5LsC5l6hMlrjdUvTdImzwl3IkEwp09G5lhIV15GWxO+QIbD/02qyLppoHph94
I/jMQOKNXkfGeJ5EEpN8Ccars4vCNfxjXuxr78Bcz4rDOEaLTlOoC3/AAo91fTeYb2b7ArxG1is=
','eJyNkV1vgjAUhv/K0ms1p9UqcIegE82UYHe1eEEAnYlUAmXOmf33nRZZ3DKTldDP95znfFzIuK6e
qh1xLsQtilla6t20mff7tFllICslA3NaqVfzqk/EmyzdwJ+75BNHh4jjP+3WwWyMdvRqN95/YAyB
hhMGjILNhq1rTiECBjio3R+QDkGln20bcREnVQ+A4U97FPDVKzNfXd10we6yoQDbYdwZ2ARJ/jGp
80yqJj51nAZhrtaqigpz9Vi2JTDx/B2OicXiVksT+X2eeA/k1pUpIojzglUod/Iwkak44vQD0JYE
AStoIEBHyDAm6OeOWMs455RomMGQaO4JPOq0KmzB1pBxq/MKS1WeUfOMZlj9zbf/KNvqdy9VJv9Q
nfWy1Lkt6qrO44dJedpLovulRW6SqLafv7prMzri1ChFcQv1XM8jpuPr4pDjnR+r2EQ3kW+H4pav
DQnorIzLhQi7DCxrxNlQX0ZVKlWTrRGJkzShQh9AEzb44fgChfzS5A==
','20210926CENAIDJA510R0200001934','RJCT',4337,951),
	 (4175,'11844292',8650000.00,'21211','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000063','SUCCESS','2021-09-24 08:12:55',NULL,'eJyVUstuwjAQ/BefAdluggK3JObhqkAFVi9VDyUJD5U4yFkkWsS/d20Tqa2KSi1ls/bueMajPZHk
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
','20210924CENAIDJA510R0200001753','ACTC',4174,652),
	 (4210,'8289331',1000200.00,'15502','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000084','SUCCESS','2021-09-24 09:34:53',NULL,'eJyVUstuwjAQ/BefAdlOIgVuSczDVYEKrF6qHiAPQCUOShaJFvHvXdtEaquiUktxdr07nvFozyQ+
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
','20210924CENAIDJA510R0200001443','ACTC',4209,214),
	 (4230,'8211131',4222000.00,'12550','SIHBIDJ1','CENAIDJA','20210924SIHBIDJ1510O0100000100','SUCCESS','2021-09-24 10:05:37',NULL,'eJyVUstuwjAQ/BefIbINUYFbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEolVRqaWsH7uzMxnticSH
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
','20210924CENAIDJA510R0200001718','ACTC',4229,233),
	 (538,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000009','ERROR-CIHUB','2021-10-03 07:29:03.266','HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','eJyVUk1vwjAM/S85A3IoqMCtbQpkGjBBtMu0A7TlQ6Mpao3EhvjvcxIqbdPQWKSmduyX9/LkMwuP
1aTasMGZBYfDOC1NNHT7bpe6v5a6Qi1tNsOtrZqMLeQ4lOKBswutBlPFnbgongaEC664cPdBGqQh
Z21ocw7g1Vd3OcyADuzqswajTpGtXfNhmVQtgB59vAU9qkZlJvB6TZNDEzwF/qDdH4DHiEkUyTHP
NDp9WAxlVGFeRimqcm0OR2VtglX0uyBa3Y7vezWfym8wNth0NVurU0V1TtkCcZ9LbZlsPEFrx+N8
xIwRToc62ZaXM3vK0bkX61QVtN1S9N0iuuAv5UQmNZbhm5UR5NaR5+X+mBmpnt8BIFPNA5N3OpFi
biDRttyExngRh4qKYoXGq4uLgg3+Y17sa+/AfJ0Vh3GMFp0kWDf+gPl+t9e38/Vqtk9kiNW0
',NULL,NULL,NULL,537,6319),
	 (4283,'182531',467760.00,'15804','SIHBIDJ1','CENAIDJA','20210925SIHBIDJ1510O0100000031','SUCCESS','2021-09-25 07:43:19',NULL,'eJyVUstuwjAQ/BefAdmBQMItiXm4KlCB1UvVAyThoRIHOYtEi/j3rm0itVVRqaVs1t4dz3i0ZxIf
q0m1If0ziQ6HcaZNNnRxt8vcXwlVgRJ2N4OtrZodWYhxLPgDIxdcDSLLO3HJYBohLrri4t0HahCG
nHjUYzT0/Ppqn9EZZdSuNiMNgp08X7vmwzKtWpQG+LEWDbCa6JzD9ZomDZueL1nY77T7LCTIxMv0
WOQKnD4ohyKpoNBJBlKvzeFI1yZYRb8LwsX8gHZqPlncYGyQ6Wq2lqcK60b7AmBfCGWZbD4Ba8fj
fESMEU6HPNmWlzN5KsC5N1CZLDHcUvTdIrzgL+VIJhTo+M3KiArryPNyf8wR2On2el2KppoHpu94
IvjcQJKt3sTGeD6IJRb5CoxXF5dFG/jHvNjX3oH5OisO4xgtOk2hbvwBY4Hnt91gvprwCaoW1h0=
','eJyNUm1vgjAQ/itLP0/TFnHANwSdbJkS7D4tfmC8OBIpBOqcM/73XQ9Z3DKTldCX63P3PHfXI5ns
2qd2Q5wjcet6njZ6N+vmoki7VQayVTLA01K94a0+EW+6cAP/wSUnGLdEVP/0WwXzCfixs9+k+AQN
gSYnnHJGbW72oU1GI8opDmaQWwJIP8s7cB0n7ZBSDj8bMgq3XpP56hxmQO0BNwXjzshwmE2Aya+S
XZlJ1elT1SwIS7VSbVSj6b7pS4B6/paDSixm9WyivM4nPgKZuzIFCuK8QBWajdxOZSoqmH4Q9CUB
giXtSKjBgANdIM4VsBZjWnRENBnSENcTHhx1Wi20IEdm2Oq8wkY1B8A8gx9Uf/0dP8pyfe+lCvMP
1UEvC52bW77GzT5Wxc08TguiO6ZhbpKovqO/+sssbhoMkaK+pPVczyPY81W9LcHmxypGfVP5vq0v
FWhHQnUFMOSjCAfwDMY2v7PHYIzaVKouXwSJvUSx1MDETmv4YHwBvDzTLw==
','20210925CENAIDJA510R0200000013','ACTC',4282,456),
	 (2,'1814449',1545500.00,'40118','SIHBIDJ1','BMRIIDJA','20210929SIHBIDJ1510O0100000001','ERROR-CIHUB','2021-09-29 14:08:16.143','Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','eJyVUstuwjAQ/BefAdlRUiXc4piHq1IqsLhUPUASHipxkLNItIh/79qGqq2KSi1ls/bueMajPRK+
b0bNinSPJN3thoWxWd/Hzabwfy11A1q63RjWrmp3ZCqHXIp7Rk64WkTVN+L4aCIRl55xfPOOGqQl
JwENGE2C5HJ1xOiYMuoXIy2CnaJc+ubdPG86lMb4sQ6NsZqZUsD5mjZN2kGiWNilcZfdEWQSdb6v
Sg1eH9R9mTVQmawAZZb2cGAuJjhFvwvCFVLGPvlUdYWxRR4X46U6NFi32qcA20pqx+TyEViK7GEy
INYIr0MdXMvzkTxV4N3r6ULVGK4p+m4RXvCXciSTGgx/dTLSyjkym2/3pZUahVFEKbpqX5i/4ZEU
E4vJ1mbFrfOixxUWxQKsWSefpSv4x8C4596A+TosHuMZHTrP4dL4A8ZiFoZh4kbsxYYPYjPWoA==
',NULL,NULL,NULL,1,199);
INSERT INTO public.account_enquiry (id,account_no,amount,intr_ref_id,orign_bank,recpt_bank,bizmsgid,call_status,cihub_req_time,error_message,full_request_msg,full_response_msg,resp_bizmsgid,response_status,chnl_trx_id,cihub_elapsed_time) VALUES
	 (4,'677589',137400.00,'47169','SIHBIDJ1','CENAIDJA','20210929SIHBIDJ1510O0100000002','ERROR-CIHUB','2021-09-29 14:08:35.918','Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','eJyVUstuwjAQ/BefAdnhkYRbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEaquiUktxdr07nvFozyQ+
VpNqQ/pnEh0O40ybaOj23S5zfyVUBUrYbAZbWzUZWYhxLPgDIxdcDSLLO3HJYBohLrri4t0HahCG
nHjUYzT0wvrqLqMzyqhbHmkQ7OT52jUflmnVojTAj7VogNVE5xyu1zRp2PRCyTp9GvTbXYJMvEyP
Ra7A6YNyKJIKCp1kIPXaHI50bYJV9LsgXB2f9cKaTxY3GBtkupqt5anCOsNsAbAvhLJMNp6AteNx
PiLGCKdDnmzLy5k8FeDcG6hMlrjdUvTdIrzgL+VIJhTo+M3KiArryPNyf8yN1LbfoRRNNQ9M3/FE
8LmBJFu9iY3xfBBLLPIVGK8uLoo28I95sa+9A/N1VhzGMVp0mkLd+APW8/1uENoBezXbJ8H71j4=
',NULL,NULL,NULL,3,4),
	 (6,'677589',137400.00,'47169','SIHBIDJ1','CENAIDJA','20210929SIHBIDJ1510O0100000003','SUCCESS','2021-09-29 14:09:11.447',NULL,'eJyVUttuwjAM/Zc8F5SUSwlvbcMl04AJor1Me4C2XDSaotZIbIh/n5NQaZuGxiw1tWMf++TIZxId
q0m1If0zCQ+HcVoab+jO3S51fy11BVraaAZbmzURWchxJMUDIxc0j6jiTlw8mIaIC6+4aPeBHKQZ
TnzqM8p9XrfuMDqjjDprEY9gpcjWrviwTKompT38WJP2MBuXmYBrmwblDZ8r1u5T3mdI0iOiSI55
psHxg2Io4wryMk5BlWtzOSprESyj3wmhtQPW5fU8ld+Y6JHparZWpwrzJloA7HOp7STrT8DK8Tgf
ESOE46FOtuTlTJ5ycOoNdKoKPG4x+i4RNviLOQ6TGsrozdIIc6vI83J/zAzVVtCmFEU1D0ze8UaK
uYHE23ITGeHFIFKYFCswWl2cF27gH/tiX3sH5uuuOIybaNFJAnXhD1g3CDo9bhfs1Ryfs5TWNg==
','eJyNUd9vgjAQ/leWPk9zRQXhDUEnLlODXfaw+GD4oSRSCNRtzvi/73rI4paZ7Ahtr7277/vuTmx0
qJ/qLXNOzC3LaVzp06RZsyxudhnIWsmAvIXa0av2mDeeu4E/c9kZ7Z6J4p95q2A6wjx+yRtln8gh
0ODMAIODbdht6QGHEAzQZoLF7hlG+knaBJebqO4CGPjzLgd89arEV5cyHbA7hi3AcsB2uMEQyS+i
Q55I1fBTxSRY5mql6rCkq4eqbQHx+ZsOWZ9bLZrIb+OJj0CmrowRgjmv2IVqK/djGYsClx8AbUsQ
YAEXEOghBqVgnRvBmovFTZtpMIJhric8dLWsGkeQEjIeta5lpaojxjxjHnZ//V0/TFL97sWK9C/V
UW9zre0lk3F2N9sV8sj0uHSMG0WqHeev4ZqWNRjaFCnKa0zP9TxGA1+V+xzv/I3aELmxfNuX1/A6
kQFHFVTyUSw7BvTBHpp0GdaxVI1Y8sW7JKbQI1XnNX5oX2gR0lA=
','20210929CENAIDJA510R0200000607','ACTC',5,1654),
	 (8,'1814449',1545500.00,'40118','SIHBIDJ1','BMRIIDJA','20210929SIHBIDJ1510O0100000004','SUCCESS','2021-09-29 14:09:46.639',NULL,'eJyVUl1PwjAU/S99BtIunWG8rSsfNSIGGl+MD7iNj8g60l0SlPDfvW3BqJGITdbd9t5zz+nJPRCx
a8bNkvQOJN1uR4V10SDs63UR/kaZBozypwmsfNadyEyNhJK3jBxxtYiur8SJ8VQhLj3hxPodNShH
TiIaMZpEybl1zOiEMhoWJy2ClbJchOLtPG86lHbxYx3axWxmSwmnNm2atKNEM96jSY/fEGSSdb6r
SgNBH9QDlTVQ2awAbRfucmjPJnhFvwtySihjn3y6usDYIvcvk4XeN5hneJoBbCplPJOPx+Aosrvp
kDgjgg699yVPB/JQQXCvbwpd43ZJ0XeLsMFfypFMGbDi1ctIK+/I43yzK53UmMcxpeiqe2H+hldK
Th0mW9mlcM7LvtCYlC/gzDqGKF3CPwbGP/cKzNdhCZjA6NF5DufCHzDWZZzzxI/Ys9s+AHqE1q4=
','eJyNUdtuwjAM/ZUpz4CcUkbbt5LCKNMYKtnTxAPqBZBoWrVhG0P8+xyXTmwa0lI1F/vYx8c+sdGh
fqo3zDsxvyynSWVuk2bf7ZLmVKGqtQrp9ay35DUvJsZzPwxmPjvj6jBZ/DNuGU5HGMcvcaPdJ9YQ
GnJmgcXBtdw29YBDBBbgQvOQdRgigzRrwOU6rnsAFv68xwG9okoDfUnTBbdruRKGHriefc+QKSji
Q54q3dSni0m4yPVS11FJpoeqbQHV83c5VMvAtVs2md/mkx+hynyVIAXzXrEL1UbtxyqRBW4/CNqW
IMEzNCQAhoNCMM8NsEEB5w4zZETDfCEFPo2sGkeQETNeja5FpasjYl4wDru/+s4fpZnxi0ST/oU+
mmNutPlKF+puti3UkZlxGYwfx7od56/hcofbtu0SVJbXpMIXgtHEl+U+R1uw1muqbqze9uU1vwlk
wFEG5XyUiy6Kt4fgOH00RnWidKOWQPJdUanQJ1nnFX64vgBJ2tKL
','20210929CENAIDJA510R0200001097','ACTC',7,599),
	 (17,'871551',438417.00,'14145','SIHBIDJ1','CENAIDJA','20210930SIHBIDJ1510O0100000001','SUCCESS','2021-09-30 10:53:09.364',NULL,'eJyVUstuwjAQ/BefAdkkEYFbEgdwVaACq5eqB0jCQyUOchaJFvHvXdtEaquiUktx1t6dnfFozyQ+
1pN6QwZnEh0O41ybaOj23S53fyVUDUrY0wy2NmtOZCHGseAPjFxwtYis7sQl6TRCXHTFxbsP1CAM
OenSLqN9jzatA0ZnlFG3GGkRrOTF2hUfllndoTTEj3VoiNlEFxyubdq03/aoZHQQeAPaJ8jEq+xY
FgqcPqiGIqmh1EkOUq/N5Ug3JlhFvwsySnzmBw2fLG8wtsh0NVvLU415o30BsC+Fskw2noC143E+
IsYIp0OebMnLmTyV4NxLVS4r3G4p+m4RNvhLOZIJBTp+szKi0jryvNwfCwT6XuizHppqHpi9443g
cwNJtnoTG+N5GktM8hUYry4uijbwj3mxr70D83VWHMYxWnSWQVP4Axb2WBC4wXw12ydOL9Xr
','eJyNUdtuwjAM/ZUpz4CcQrm9hRZGNw1QyZ4mHlAvrBJNqyZsY4h/n+PSiU1Dmqvm5mOfY/vEJgf9
pHdsfGKiLOdxZU+zes2yuN5VoLRRAd2W5pW89sa86UIE/oNgZ7QWk8U/49bBfIJx/BI3yT5RQ2DJ
mQMOh1EXmtQuhxAcQONOf8BaDJF+ktbgchvpDoCDP+9wQK9XJb65pGnDqN0FyWHsdsfoRSa/iA55
okytzxSzYJWbtdFhSU/3VdMC0vO3HNICMGjYZH6bT34EKhUqRgo2fsEuVDu1n6pYFrj8IGhaggRL
qEmQBjkoBPPcAFtUj/dcZsmIhglPeni1ZWkcQUrMeLR1rSpTHRHzjHHY/c13/jBJrd+LDdW/Mke7
LWxtIs7Kg6mKu/Vxm+vDntmZWaCIItPM9NeEhwPuupyQsrwm9oTnMZr6utzn+OZvzZYUTtXbvrzW
YAMZ9YBSPspV24Fevw9DGnWoY2Xqigkk3xXJhS6Vdt7gh/YFgQPUIA==
','20210930CENAIDJA510R0200001267','ACTC',16,2262),
	 (182,'874551',438417.00,'147845','SIHBIDJ1','CENAIDJA','20211001SIHBIDJ1510O0100000001','ERROR-CIHUB','2021-10-01 15:16:46.627','komi-mock: Temporary failure in name resolution','eJyVUstuwjAQ/BefAdk0gYhbEvNwVaACq5eqB0hCiEoc5CwSLeLfu7aJRKuiUktx1t6dnfFoTyQ6
1NM6J4MTCff7SapNNHJ7UaTur4SqQQl7msPWZs2JLMUkEvyRkTOuFpHVnbh4OAsRF15wUfGJGoQh
J13aZYxS1rT2GZ1TvLCLkRbBSp5tXPF+ldQdSgP8WIcGmI11xuHSps1omzLJ/AHrDbweQSZeJYcy
U+D0QTUScQ2ljlOQemMux7oxwSr6XRCevH7g+Q2fLG8wtshsPd/IY415o30JsCuFskw2noK142kx
JsYIp0MebcnriTyX4NwbqlRWuN1S9N0ibPCXciQTCnT0bmWEpXXkZbU7ZAj0HgKP9dFU88DkA28E
XxhIvNV5ZIznw0hikq/BeHV2UZjDP+bFvvYOzPWsOIxjtOgkgabwByzoe77vBvPNbF8iAdXY
',NULL,NULL,NULL,179,434),
	 (408,'874551',438417.00,'147845','SIHBIDJ1','CENAIDJA','20211001SIHBIDJ1510O0100000002','ERROR-CIHUB','2021-10-01 15:51:38.401','komi-mock: Temporary failure in name resolution','eJyVUltrwjAY/S95Vkm0xeJb23jJmDo07GXsQXuzzKaSfoKb+N/3JbGwjclcoOl3Ozknh5xJdGzm
TUFGZxIeDrNUm2ji9rJM3V8J1YASNlvCznZNRtZiFgn+wMgFV4fI+k5cPF6EiAuvuKj8QA3CkJM+
7TNGKWuP9hldUizY1ScdgpM8y93wYZM0PUoD/FiPBtiNdcbhekyX0S5lkvkjn40GAUEmXifHKlPg
9EE9EXEDlY5TkDo3xaluTbCKfheEmTcMPL/lk9UNxg5ZbJe5PDXYZ5itAfaVUJbJxnOwdjyupsQY
4XTIkx15OZOnCpx7Y5XKGrdbir5bhAf8pRzJhAIdvVkZYWUded7sjxkCvUHgsSGaai6YvGNF8JWB
xDtdRMZ4Po4kNvkWjFcXF4UF/OO92Nvegfn6VhzGMVp0kkA7+AMWDD3fdw/z1WyfJVTV2g==
',NULL,NULL,NULL,407,12),
	 (607,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000051','TIMEOUT-CIHUB','2021-10-03 15:56:56.183',NULL,'eJyVUstuwjAQ/BefAdmENJRbEvNwVaACq5eqB0jCQyUJchaJFvHv3bWJ1FZFpVbi1+7sjEd7YtGh
Gldr1juxcL8fpYZ2Azdvt6lbC1VUUCh7msLGRunE5moUKfkg2BlHg+nyRlzcn4SICy+4aPuBGhSR
szZvC8G5V5f2BZ9yvKDhC9ZgmCmzlUveL5KqxXkXf9HiXYzGJpNwKdMUvMk9Lfyef4cfQyZZJoc8
K8Dpg3Kg4gpyE6egzYouh6Y2wSr6XRBJ6QSBV/Pp/Apjg02W05U+Vhgn7XOAXa4Ky2T3Y7B2PM6G
jIxwOvTRpryc2FMOzr1+keoSp2uKvluEBf5SjmSqABO9WRlhbh15XuwOGUn1gg7naCo9MHnHGyVn
BIk3Zh2R8bIfaQzKJZBXZ7cL1/CPfrGvvQHztVccxjFadJJAnfgDFgR+99721ytNn3Nr1bw=
',NULL,NULL,NULL,606,2230),
	 (410,'874551',438417.00,'147845','SIHBIDJ1','CENAIDJA','20211001SIHBIDJ1510O0100000003','SUCCESS','2021-10-01 22:54:35.668',NULL,'eJyVUltrwjAY/S95rpLUlhbf2sZLxtShYS9jD9rWC7OppJ/gJv73fUksbGMyF2j63U7OySFnkh6b
SbMh/TNJDodxoU00dPtuV7i/EqoBJWw2g63tmowsxDgV/IGRCy6PyPpOXDaYJohLrrh094EahCEn
PvUZo5S1R4eMzigW7OoRj+AkL9du+LDMmy6lMX6sS2PsZrrkcD2mw2iHMun7/TDo90KCTLzOj1Wp
wOmDeiiyBiqdFSD12hRHujXBKvpdEGZBFAdhyyerG4wema5ma3lqsM8wWwDsK6Esk40nYO14nI+I
McLpkCc78nImTxU49waqkDVutxR9twgP+Es5kgkFOn2zMpLKOvK83B9LBAa9OGARmmoumL9jRfC5
gWRbvUmN8XyQSmzyFRivLi5KNvCP92Jvewfm61txGMdo0XkO7eAPWBwFYege5qvZPgEhi9XY
','eJyNUW1vgjAQ/itLP6tpy4uOb1h0smVqsIsfFj8QCo5ECoE654z/fdcii1tmsiO0vbvn7rmXExrv
m+dmi7wT8qtqJmr9mrZnnov2lqFslAyNtlBvxqs1xCZzPwwefXQG6SFe/jNuFc7GEEcuceP8E2oI
NTmimBKCMelSOwRHmGIjlKIeAmSQZi24ipNmAHb4yYBg8LI6DdQlTZ/gPiacUs+xPctFwBSUyb5I
pWrrU+U0XBZqpZqoMqaHuhuBqefvcrQQ4rgdGy9u8/GPUGa+FECBvFeYQr2Vu4kUvITjB0E3EiBY
4JYEYws4TAjkuQEGzR6ObAdpMkODfMYZqLqtBlaQGWZ46r6WtaqPgHmBQJj+5jt/lGbaz4Qy/S/V
UV9z3ds6r+NDLO/WuShFifTGNMxPEtVt9Nd+R0PbcYhB8uqalvmMIbPzVbUrwBbEKjb1TeT7rrqu
QAciTKARk/KJL/sUj1z73rVsMEaNkKrt14D4QZpisWUaO2/gA/kCYF3TIA==
','20211001CENAIDJA510R0200000022','ACTC',409,1187),
	 (441,'677589',137400.00,'419','SIHBIDJ1','CENAIDJA','20211002SIHBIDJ1510O0100000001','SUCCESS','2021-10-02 08:54:12.25',NULL,'eJyVUk1vwjAM/S85A0o6GIVb2xTINGCCaJdpB2jLh0ZT1BqJDfHf5zhU2qahsUhNHdsv7+XJJxYe
qnG1Zv0TC/b7UVraaOD27TZ1f6NMBUbRaQobqtoTm6tRqOSDYGdcDaaLG3FRPAkQF1xw4fYDNShL
zjzuCcG5V1/dEXzKMUFLsAbDTpmtXPN+kVQtzn38RIv7WI3KTMLlmqbgTe5p7vc77b7wGDLJIjnk
mQGnD4qBiirIyygFXa5scljWJpCi3wXRaotezafzK4wNNllOV/pYYd1qnwPscmWIieIxkB2PsyGz
Rjgd+kgtLyf2lINzLzapLnC7pui7RXjBX8qRTBkowzeSEeTkyPNid8is1Ltum3M01T4weceMkjML
iTblOrTGyzjUWJRLsF6dXRSs4R/zQq+9AfN1VhzGMRI6SaBu/AG773Y7fo8G7NVun/BK1bo=
','eJyNUttuwjAM/ZUpz4CSQIH1LaQwumlQlexp4qHqhSHRtGrDBkP8+xyXTmwa0lw1V9vnHDsnMtnX
z/WGuCciynKeVHY1a8btNmlm7evaaB93S/OGt3ZH5HQhfO9RkDNYh6jin3Erfz6BOHaJm2w/gYNv
wQmnnDFKeZvaYTSknIIxOnRIh4Cnl2aNcxnFdQ+c4Wc9RuFWVqlnLmm6jHYpV3TsOgOXcQJIXhHv
81Sbhp8pZn6Qm5WpwxKPHqq2BMjnbzoNl/F9i6by23jq4OtM6AQgiPsKVag2ejfViSpg+AHQlgQA
lrQBARjAwBDIc8MZbcAsG3VAGCKkkrC1smpoQYbIsLS6gspUR/B5gSio/vo7f5hm9l4mBvUH5min
hdUmtCn0nUiinNhuWRcRx6bt5q/eDkcjB4qD76G8hpRCSoL9XpW7HM68yETIbarfd+U1ug0kqB5T
PqmgyynjrN8IDetEm0YrOqkPjURpH0Wd1/CBfQG1s9FL
','20211002CENAIDJA510R0200001065','ACTC',440,1372),
	 (443,'677589',137400.00,'5669','SIHBIDJ1','CENAIDJA','20211002SIHBIDJ1510O0100000002','SUCCESS','2021-10-02 08:57:16.503',NULL,'eJyVUstuwjAQ/BefAdlp84BbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEaquiUktxdr07nvFoTyQ5
1ON6TXonEu/3o1ybaOD27TZ3fyVUDUrYbAobWzUZmYtRIvgDI2dcLSKrG3FpfxIjLr7gku0HahCG
nHjUY4xSr7naZ3RK8cAuj7QIdvJi5Zr3i6zuUBrhxzo0wmqqCw6Xa9qMtqknadTzwx4LCDLxKjuU
hQKnD6qBSGsodZqD1CtzONSNCVbR74LM8oOg2/DJ8gpji0yW05U81lhnmM0BdqVQlsnGY7B2PM6G
xBjhdMijbXk5kacSnHt9lcsKt2uKvluEF/ylHMmEAp28WRlxaR15XuwOhZF6F95TiqaaB2bveCL4
zEDSjV4nxnjeTyQW+RKMV2cXxWv4x7zY196A+TorDuMYLTrLoGn8AQvC0I+6dsBezfYJLELV4g==
','eJyNUW1PwjAQ/iumn4G0lQ3Yt7GBVCMso34whg/LXnAJ65a2qEj47147ZtBI4i3ry/W5e567O6Lp
Xj2qLfKOyG+aRSbNad6uZZm1u2BCacHsbaVf7au5oWC29Fl476MTWA/x+p9xa7aYQhw5x03LT9DA
DDmimBKCMe1SOwTHmGIwQiZD1EOADPOiBTdJqgYAhp8MCIbXQOahPqfpE9zHlOOx54w84iJgCut0
X+VCt/p0PWdRpddaxY113cmuBVbP33KsFjp0OzZeXefjH0wUvsiAAnkv0AW5FbuZyHgNyw+CriVA
sMItCXiBw4ZAnitgY47rTpAhszTID3gAV1OWghEUlhmOpq5IankAzBOEQfc33/njvDDvQaZt/ZE+
mG1pamMiS6Sub573Wal0KRNkhmaQfprqbqi/RuyORs54YpG8uWQO/CBAduzrZleBL0x0YiXOxNuu
uRRhAhEmUItN+cCjPsUT7GLqmlnHKhO6LdmC+LuwevGtre20gQ/sC1mi1II=
','20211002CENAIDJA510R0200001194','ACTC',442,965);
INSERT INTO public.account_enquiry (id,account_no,amount,intr_ref_id,orign_bank,recpt_bank,bizmsgid,call_status,cihub_req_time,error_message,full_request_msg,full_response_msg,resp_bizmsgid,response_status,chnl_trx_id,cihub_elapsed_time) VALUES
	 (445,'677589',137400.00,'5669','SIHBIDJ1','CENAIDJA','20211002SIHBIDJ1510O0100000003','SUCCESS','2021-10-02 08:58:46.482',NULL,'eJyVUstuwjAQ/BefAdmBhMAtiXm4KlCB1UvVAyThoRIHOYtEi/j3rm0itVVRqaU4u94dz3i0ZxIf
q0m1If0ziQ6HcaZNNHT7bpe5vxKqAiVsNoOtrZqMLMQ4FvyBkQuuBpHlnbhkMI0QF11x8e4DNQhD
TjzqMUapV1/tMzqjeGBXmzQIdvJ87ZoPy7RqURrix1o0xGqicw7Xa5qMNqknadj3w34nIMjEy/RY
5AqcPiiHIqmg0EkGUq/N4UjXJlhFvwsyyw+CXs0nixuMDTJdzdbyVGGdYbYA2BdCWSYbT8Da8Tgf
EWOE0yFPtuXlTJ4KcO4NVCZL3G4p+m4RXvCXciQTCnT8ZmVEhXXkebk/5kZqu9uhFE01D0zf8UTw
uYEkW72JjfF8EEss8hUYry4uijbwj3mxr70D83VWHMYxWnSaQt34AxZ0u37YswP2arZPPdHV7A==
','eJyNUttugkAQ/ZVmn9XMolzK2wpYaVMluH1qfDBcLIksBNaqNf57ZxdpbNMmHcJez8w5M7NnMt23
z+2WuGfC6nqeNmo168aiSLtZhKKVItS7pXzTt2pHvGDBQv+RkQvagPDqn36rcD5FP3r1mxYfqCFU
5MQAg1IAow9tUojBADQKzoQMCCL9LO/A9SZpRwjGn44o4K3XZL68hhlSGILBwXFNx51YBJn8KtmX
mZCdPlnNwqiUK9nGtT56aPoSaD2/y9FaqHPfs/Hybz5+DEXORIoUxH3FKjRbsQtEyiscvhH0JUGC
JXQkAGPk0C4Y5w+wMtOylBp+1DSEedzDrUqrxRbkmhmXKq+okc0JMS/ohtVff8WPs1zde6nU+Ufy
pKaFyo0JWYm7oDkUgqh2KQxLEtm380dzLds2sTr6QdS3nB7zPKIbvqp3JZ75G7nR4gLxvqtv6ZUj
AYpZ6JBPPBoaYNswAVMdxm0qZJesBvGD0EphrLO6rPFD+wTyUNIG
','20211002CENAIDJA510R0200001084','ACTC',444,954),
	 (447,'677589',137400.00,'5669','SIHBIDJ1','CENAIDJA','20211002SIHBIDJ1510O0100000004','SUCCESS','2021-10-02 08:59:32.507',NULL,'eJyVUstuwjAQ/BefAdmBQOCWxAFcFajA6qXqAZLwUImDnEWiRfx71zaR2qqo1FKcXe+OZzzaM4mO
1aTakMGZhIfDONMmGrp9t8vcXwlVgRI2m8HWVk1GFmIcCf7AyAVXg8jyTlycTEPEhVdctPtADcKQ
E496jFHq1Vf7jM4oHtjVIQ2CnTxfu+bDMq1alAb4sRYNsBrrnMP1miajTepJGgz8/qDtEWTiZXos
cgVOH5RDEVdQ6DgDqdfmcKRrE6yi3wWZ5Xe7/ZpPFjcYG2S6mq3lqcI6w2wBsC+Eskw2noC143E+
IsYIp0OebMvLmTwV4NxLVCZL3G4p+m4RXvCXciQTCnT0ZmWEhXXkebk/5kZqu9ehFE01D0zf8UTw
uYHEW72JjPE8iSQW+QqMVxcXhRv4x7zY196B+TorDuMYLTpNoW78Aev2en7QtwP2arZPMvjV5g==
','eJyNUdtugkAQ/ZVmn9XMLqLCG4IX2lQNbp8aHwgXSwILgbWtNf57ZxcxtqlJh7C3OTNnzsyJTA/N
c7Mn9ok4VbWMa3Wat2uWxe0ufNFI4evbWr5pr7oRd7ZyfO/RIWe0HuHlP+O2/nKKcfQSN82+sAZf
kRMGjFIA1qU2KQTAQBkbAukRRHpJ2oKrMGoG6MCfDqjyunXiyUuaPoU+MA4T27RsgxFk8sroUCRC
tvXJcu5vCrmVTVDpp0XdtUDX83c5yqgFVzZe3Ofjn75IHREjBbFfsQv1XuQzEfMSlx8EXUuQYA0t
CcAQOXQI5rkDVmaORhZRZJqGOC538apkNTiCVDPjUena1LI+IuYFw7D7u2v+IEmV342l1r+RR7Wt
lDZHyFI8LLI8FHui5qVAThTJbp6/pjsaj82JpZG8uiV1HdcleuLbKi/wzQtlqKubife8uuVXgQQo
ytApn/imz8AyDAZjAx+DJhayVatB/EPoUsHQss47/NC+AZtS0j8=
','20211002CENAIDJA510R0200000240','ACTC',446,948),
	 (449,'677589',137400.00,'56573','SIHBIDJ1','CENAIDJA','20211002SIHBIDJ1510O0100000005','SUCCESS','2021-10-02 09:01:32.046',NULL,'eJyVUstuwjAQ/BefoVqHhgC3JObhqkAFVi9VD5CEh0oclCwSLeLfu7aJ1FZFpZbirL07O+PRnlh0
qMbVmvVOLNzvR2lpooHbt9vU/bXUFWppT1Pc2Kw5sbkcRVI8cHam1WCquBEX9ych4cILLtp+kAZp
yJkHHucAXt3a5zAFurDLZw1GlSJbueL9IqnuADr08TvoUDYuM4GXNk0OTfAUdHvAey2PEZMokkOe
aXT6sBjIuMK8jFNU5cpcDsvaBKvod0FGSdsPWjWfyq8wNthkOV2pY0V5Tqc54i6X2jLZeIzWjsfZ
kBkjnA51tCUvJ/aUo3Ovr1NV0HZN0XeLqMFfyolMaiyjNysjzK0jz4vdITNSW8E9AJlqHpi8040U
MwOJN+U6MsaLfqQoKZZovDq7KFzjP+bFvvYGzNdZcRjHaNFJgnXhD1g7CPxO1w7Yq9k+AQub1dA=
','eJyNUV1vgjAU/StLn6e5rSLKG4JOZqYGu4dl8YEIOBIphJZNZ/zvuy2yuGUmK6Gf595zzr0nMq7l
k9wR50TcspzFld5NmznL4mYVgZBKBOa0VG/mVZ+IN1m4gf/okjOOe8KLf8atg9kY4+glbpx9ooZA
kxMGjFIA1qa2KITAAAcdMpvcE0T6SdqAy2gruwjGn3Yp4KtXJb66pOlQ6ADjMHKAOj1GkMkvtnWe
CNXoU8U0WOVqrWRYmquHqi2B0fO3HKNlYPVaNp7f5uOHQKSuiJGCOK9YhWon9hMR8wKnHwRtSZBg
CQ0JgIUcJgTz3ABr1MCytRp+MDTE9biHR21LYgtSw4xb7WtVqeqImGeMw+pvvvOHSarfvVgZ/yt1
1MtCe5vXss6ju5c6zqTKqojonmmgu92qtqe/OjywbWs4MkheXhN7rucR0/V1uc/xzo9UZBROxPu+
vNagAwlQtGJSzvmqw6DXHw36bISXoYyFahwbEP8QRi70jLXzBj8cX2Fb1CU=
','20211002CENAIDJA510R0200001827','ACTC',448,973),
	 (540,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000010','ERROR-CIHUB','2021-10-03 07:32:47.253','HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','eJyVUstuwjAQ/BefAa0JKJRbEvNwVaCiVi9VD5CEh0oc5CwSLeLfu7aJ1FZFpZbi7Hp3POPRnlh8
qCbVmvVPLNrvx5mx0dDv223m/1rqCrV02Qw3rmoz9iTHsRT3nJ1pNZgqb8Qlg2lEuOiCi7cfpEFa
ctaGNucAQX11l8MM6MAuDqzBqFPkK9+8X6RVC6BHH29Bj6qJyQVermlyaEKgIOwH7X4nZMQkyvRQ
5Bq9PiyHMqmwMEmGyqzs4cjUJjhFvwui1e2EYVDzqeIKY4NNl7OVOlZU55Q9Ie4KqR2Tiyfo7HiY
j5g1wutQR9fycmKPBXr3BjpTJW3XFH23iC74SzmRSY0mfnMyosI58rzYHXIrNQg7AGSqfWD6TidS
zC0k2Zh1bI0Xg1hRUSzRenX2UbTGf8yLe+0NmK+z4jGe0aHTFOvGH7Aw7Pbu3Hy92u0TUPzVqA==
',NULL,NULL,NULL,539,6304),
	 (451,'677589',137400.00,'5573','SIHBIDJ1','CENAIDJA','20211002SIHBIDJ1510O0100000006','SUCCESS','2021-10-02 09:06:57.322',NULL,'eJyVUstuwjAQ/BefoVqHhgC3JObhqkAFVi9VD5CEh0oclCwSLeLfu7aJ1FZFpZbi7Hp3POPRnlh0
qMbVmvVOLNzvR2lpooHbt9vU/bXUFWppsylubNVkbC5HkRQPnJ1pNZgqbsTF/UlIuPCCi7YfpEEa
cuaBxzmAV1/tc5gCHdjVZg1GnSJbueb9IqnuADr08TvoUDUuM4GXa5ocmuAp6Pag3fMDRkyiSA55
ptHpw2Ig4wrzMk5RlStzOCxrE6yi3wWZ5ftBq+ZT+RXGBpsspyt1rKjOKZsj7nKpLZONx2jteJwN
mTHC6VBH2/JyYk85Ovf6OlUFbdcUfbeILvhLOZFJjWX0ZmWEuXXkebE7ZEZqK7gHIFPNA5N3OpFi
ZiDxplxHxnjRjxQVxRKNV2cXhWv8x7zY196A+TorDuMYLTpJsG78AWsHgd/p2gF7NdsnKbLV3g==
','eJyNUdtuwjAM/ZUpzwM5pRfoW0nLKNMAlexp4gH1wqrRtGrDGEP8+5yUTmwa0lw1F+fY59g+kfG+
eWq2xD0Rr6qmSa1Ok3bN86TdRSgaKUJ9W8hX/apuhAVzL/RnHjmj3RNe/jNuFU7HGEcvceP8EzWE
ipwYYFAKYHSpLQoRGIBGTdMg9wSRfpq14GoTN30E40/7FPCV1akvL2l6FHpgcBi5YLuWQ5DJL+N9
kQrZ6pPlJFwWciWbqNKuh7prgdbztxytZWjTjo0Xt/n4RygyTyRIQdwX7EK9FbtAJLzE5QdB1xIk
WEBLAmAjhw7BPDfAyizLGRBFpmmIxzjDqyqrwRFkmhmPqq5lLesjYp4xDLu//s4fpZl6Z4nU9S/l
UW1zVVtQH3JxNyvfSqKmpSBeHMtumr9mazuONRxpJK+uKZnHGNHzXlW7An3+Rm60tkC876prdhVI
QHVYp3zky54BjjO0TMtEZ9QkQra1ahA/CC0UBrqo8xo/tC/yjdGZ
','20211002CENAIDJA510R0200001442','ACTC',450,919),
	 (453,'677589',137400.00,'5573','SIHBIDJ1','CENAIDJA','20211002SIHBIDJ1510O0100000007','SUCCESS','2021-10-02 09:08:03.779',NULL,'eJyVUstuwjAQ/BefAa2Tpgnc8uDhqkAFVi9VD5CEh0oS5CwSLeLfu7aJ1FZFpZbi7Hp3POPRnlh0
qMf1mvVOLNzvR5nS0cDu221m/6UoayyFyaa4MVWdsbkYRSJ54OxMq8VkdSMu7k9CwoUXXLT9IA1C
kzMHHM4BnOZqj8MU6MAsn7UYdSb5yjbvF2ndAQjo4x0IqBqrPMHLNW0ObXAkdHsQ9MBlxJRU6aHI
S7T6sBqIuMZCxRlKtdKHQ9WYYBT9Lkgvz/Pdhk8WVxhbbLKcruSxpjqnbI64K0RpmEw8RmPH42zI
tBFWhzyalpcTeyrQutcvM1nRdk3Rd4vogr+UE5koUUVvRkZYGEeeF7tDrqW6/h0AmaofmL7TiUhm
GhJv1DrSxif9SFIxWaL26myjcI3/mBfz2hswX2fFYiyjQacpNo0/YPe+7wVdM2CvevsEFDPV0g==
','eJyNUdtugkAQ/ZVmn9XMQrnIG4JWNFWD26fGByLeElkILG2t8d87O0hjm5p0CHuZPTPnzMyZDerq
udox78z8ohinpT6NmvVwSJtdRrJSMqLbXO3pVd9YMJz5UTjx2QWtw0T+z7hlNB5gHL/GDQ6fqCHS
5MwAg3MAo01tcYjBADRu233WYYgMN9sGXCTrqodg/HmPA74G5SZU1zRdDl0wBPQ9cD0wGTKF+brO
NlI1+lQ+ihaZWqoqLsj1VLYtID1/yyEtJjgtm8ju84mPSG59mSIF816xC+VOHocyFTkuPwjaliDB
HBoSIA4KwTx3wNosyzGZJiMa5gciwKsuq8IRbIkZj7quRanKE2JeMAy7v/rOH2+2+j1IFdW/UCe9
zXRt07qqs+Rhku8TyfS8NMhfr1U7z1/TtR3HcvuEFMUtaeAHAaOJL4tjhr4wUQmpG8q3Y3HLrwMZ
cCyDUk7FomuA+2ibtuOiM65SqZpqCSTeJUkFk8q6rPBD+wK2MtJi
','20211002CENAIDJA510R0200001669','ACTC',452,969),
	 (455,'677589',137400.00,'556773','SIHBIDJ1','CENAIDJA','20211002SIHBIDJ1510O0100000008','SUCCESS','2021-10-02 09:16:58.215',NULL,'eJyVUsluwjAU/BefAdmhYbslMYurAhVYvVQ9QBIWlTgoeUi0iH/v8zORaFVUainO28YzHvnEwkM5
Ltesd2LBfj9KChsN3L7dJu5vlCnBKMqmsKGuzdhcjUIlHwU746oxnd+Ji/qTAHHBBRduP1GDsuTM
454QnHvV0b7gU44FWh1WYzgp05Ub3i/isoFl/ESDulGRSrgcUxe8zj3Nuz3R6vkdhkwyjw9ZasDp
g3ygohKyIkpAFytbHBaVCaTod0Gc+36r3W5WfDq7wVhjk+V0pY8l9gVmc4BdpgwxUTwGsuNpNmTW
CKdDH2nk9cSeM3Du9U2ic9xuKfpuER7wl3IkUwaK8J1kBBk58rLYHVIrtdl+4BxNtReMP7Ci5MxC
ok2xDq3xsh9qbMolWK/OLgrW8I/3Qre9A3P9VhzGMRI6jqEa/AHDW/qdLj2wN7t9AVoA1gA=
','eJyNUttugkAQ/ZVmn9XMoiDyhgtW2lQJbtOHxgfDxZLKQmBta43/3tlFGtvUpEPY65k5Z2b2SKb7
5qHZEudI3KqaJ7Vazdoxz5N2FoFopAj0bilf9K3aEeYv3MC7c8kJrUd4+U+/VTCfoh89+03zT9QQ
KHJigEEpgNGFNilEYICyETVIjyDSS7MWXG3iZoBg/OmAAt6yOvXkOUyfQh8MDhOHWo5pE2Tyynhf
pEK2+mQ5C8JCrmQTVfrotu5KoPX8LUcZtYZ2x8aL63z8IxCZKxKkIM4zVqHeip0vEl7i8IOgKwkS
LKElAVAc2gXjXAEDmKY1Hg+JItM0xGWc4Val1WALMs2MS5VXWMv6gJhHdMTqr7/jR2mm7lkidf6h
PKhpoXJ7ypMyKW/815KobimIG8ey6+av3qIY055oJK8uKZnLGNH9XlW7As+8jdxobb5421WX7MqR
AMUkdMh7HvbxCdj22JqM8DBqEiHbXDWIvwstFIY6qdMaP7Qv+CPRmQ==
','20211002CENAIDJA510R0200000412','ACTC',454,949),
	 (457,'677589',137400.00,'556773','SIHBIDJ1','CENAIDJA','20211002SIHBIDJ1510O0100000009','ERROR-CIHUB','2021-10-02 09:17:56.752','Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','eJyVUstuwjAQ/BefoVqHhgC3JObhqkAFVi9VD5CEh0oclCwSLeLfu7aJ1FZFpZbirL07O+PRnlh0
qMbVmvVOLNzvR2lpooHbt9vU/bXUFWppT1Pc2Kw5sbkcRVI8cHam1WCquBEX9ych4cILLtp+kAZp
yJkHHucAXt3a5zAFurCryxqMKkW2csX7RVLdAXTo43fQoWxcZgIvbZocmuAp6PZ40PPbjJhEkRzy
TKPTh8VAxhXmZZyiKlfmcljWJlhFvwsC8P12ELRqPpVfYWywyXK6UseK8pxOc8RdLrVlsvEYrR2P
syEzRjgd6mhLXk7sKUfnXl+nqqDtmqLvFlGDv5QTmdRYRm9WRphbR54Xu0NmpLaCewAy1Twweacb
KWYGEm/KdWSMF/1IUVIs0Xh1dlG4xn/Mi33tDZivs+IwjtGikwTrwh8weqXf6doBezXbJ1nN1gA=
',NULL,NULL,NULL,456,20),
	 (459,'677589',137400.00,'556773','SIHBIDJ1','CENAIDJA','20211002SIHBIDJ1510O0100000010','ERROR-CIHUB','2021-10-02 09:22:50.083','localhost:9006 failed to respond','eJyVUstuwjAQ/BefAa1DUx63JA7gqkAFVi9VD5CEh0oclCwSLeLfu7aJ1FZFpZbirL07O+PRnlh4
qMbVmvVPLNjvR2lpooHbt9vU/bXUFWppT1Pc2Kw5sbkchVI8cHam1WCquBEXxZOAcMEFF24/SIM0
5MwDj3MAr27tc5gCXZjFgTUYVYps5Yr3i6RqAXTp4y3oUjYqM4GXNk0OTfAU9Pqe1/eBEZMokkOe
aXT6sBjIqMK8jFJU5cpcDsvaBKvod0EAvn/f6bRrPpVfYWywyXK6UseK8pxOc8RdLrVlsvEYrR2P
syEzRjgd6mhLXk7sKUfnXqxTVdB2TdF3i6jBX8qJTGoswzcrI8itI8+L3SEzUtudOwAy1Twweacb
KWYGEm3KdWiMF3GoKCmWaLw6uyhY4z/mxb72BszXWXEYx2jRSYJ14Q8YvdLv9uyAvZrtExup1dw=
',NULL,NULL,NULL,458,9),
	 (461,'677589',137400.00,'556773','SIHBIDJ1','CENAIDJA','20211002SIHBIDJ1510O0100000011','ERROR-CIHUB','2021-10-02 09:22:58.371','localhost:9006 failed to respond','eJyVUstuwjAQ/BefAa1DUwK3JObhqkAFVi9VD5CEh0oclCwSLeLfu7aJ1FZFpZbirL07O+PRnlh0
qMbVmvVOLNzvR2lpooHbt9vU/bXUFWppT1Pc2Kw5sbkcRVI8cHam1WCquBEX9ych4cILLtp+kAZp
yJkHHucAXt3a5zAFujCLc9ZgVCmylSveL5KqBRDQx1sQUDYuM4GXNk0OTfAUdHue1/MDRkyiSA55
ptHpw2Ig4wrzMk5RlStzOSxrE6yi3wUB+P59p9Ou+VR+hbHBJsvpSh0ryhvtc8RdLrVlsvEYrR2P
syEzRjgd6mhLXk7sKUfnXl+nqqDtmqLvFlGDv5QTmdRYRm9WRphbR54Xu0NmpLY7dwBkqnlg8k43
UswMJN6U68gYL/qRoqRYovHq7KJwjf+YF/vaGzBfZ8VhHKNFJwnWhT9g9Eo/6NoBezXbJztc1e4=
',NULL,NULL,NULL,460,14);
INSERT INTO public.account_enquiry (id,account_no,amount,intr_ref_id,orign_bank,recpt_bank,bizmsgid,call_status,cihub_req_time,error_message,full_request_msg,full_response_msg,resp_bizmsgid,response_status,chnl_trx_id,cihub_elapsed_time) VALUES
	 (463,'677589',137400.00,'556773','SIHBIDJ1','CENAIDJA','20211002SIHBIDJ1510O0100000012','SUCCESS','2021-10-02 09:26:06.533',NULL,'eJyVUstuwjAQ/BefAa1Dw+uWxDxcFajA6qXqAZLwUImDkkWiRfx71zaRaFVUainO2ruzMx7tiYWH
clyuWe/Egv1+lBQmGrh9u03cX0tdopb2NMWNzZoTm8tRKMUjZ2daNabyO3FRfxIQLrjgwu0naZCG
nHngcQ7gVa19DlOgC7O4x2qMKkW6csX7RVw2ADr08QZ0KBsVqcBLmzqHOngKuj2v1YMWIyaRx4cs
1ej0YT6QUYlZESWoipW5HBaVCVbR74IAfL/VbjcrPpXdYKyxyXK6UseS8pxOc8RdJrVlsvEYrR1P
syEzRjgd6mhLXk/sOUPnXl8nKqftlqLvFlGDv5QTmdRYhO9WRpBZR14Wu0NqpDbbDwBkqnlg/EE3
UswMJNoU69AYL/qhoqRYovHq7KJgjf+YF/vaOzDXs+IwjtGi4xirwh8weqXf6doBezPbFzQZ1eo=
','eJyNUVFvgjAQ/itLn6e51iGONwScbJka7J4WH4yII5FCSp1zxv++6yGLW2ayI7S99rv7vrs7suGu
fq43zDsyv6rGqbanUbPmedrsKla1UTF5U/NGr9ZjQTTx4/DRZye0WybLf8bN4/EQ4/g5bph/oobY
kjMBgnMA0aZ2OCQgAI0PRJ/dMkSG66wBV8tV3UUw/rzLAV8DvQ7NOU2HQweEBOGJvgcuQ6awXO2K
tTKNPlOO4llh5qZOKrp60G0LSM/fcsgGXLRssrjOJz9ilfkqRQrmvWIX9EZtI5XKEpcfBG1LkGAK
ZxLioBDMcwUM4Dh91+0xS0Y0zA9kgK4tq8YRZMSMR1vXTBt9QMwLBmL3F9/5k3Vm34PUUP0zc7Db
xNbmp3m1M7q8ifQ+V8xOzML81cq0E/01XxTkDO4JKatL2sAPAkYzn1fbAu/CpVmSvki9b6tLBTaQ
AcdCKOWTnHUEDO5c4dJlUqfKNPWSL/eKxEKPCjst8EP7AoR40zo=
','20211002CENAIDJA510R0200001826','ACTC',462,1493),
	 (518,'677589',137400.00,'556773','SIHBIDJ1','CENAIDJA','20211002SIHBIDJ1510O0100000049','ERROR-CIHUB','2021-10-02 13:21:50.1','komi-mock: Temporary failure in name resolution','eJyVUstuwjAQ/BefAa0DKY9bEvNwVaACq5eqB0jCQyUOShaJFvHvXdtEolVRqaU4a+/Ozni0JxYe
ynG5Zr0TC/b7UVKYaOD27TZxfy11iVra0xQ3NmtObC5HoRSPnJ1p1ZjK78RF/UlAuOCCC7efpEEa
cuaBxzmAV7X2OUyBLsxqdVmNUaVIV654v4jLBkCHPt6ADmWjIhV4aVPnUAdP8WbP4z0fGDGJPD5k
qUanD/OBjErMiihBVazM5bCoTLCKfhcE4PsP7Xaz4lPZDcYamyynK3UsKc/pNEfcZVJbJhuP0drx
NBsyY4TToY625PXEnjN07vV1onLabin6bhE1+Es5kUmNRfhuZQSZdeRlsTukRmqz3QIgU80D4w+6
kWJmINGmWIfGeNEPFSXFEo1XZxcFa/zHvNjX3oG5nhWHcYwWHcdYFf6A0Sv9TtcO2JvZvgAuC9Xo
',NULL,NULL,NULL,517,678),
	 (603,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000049','ERROR-CIHUB','2021-10-03 15:46:31.39','Read timed out','eJyVUttuwjAM/Zc8dyih7Qq8tQ2XTAMmiPYy7QHactFoilIjsSH+fU5CpW0aGrPU1I597JMjn0hy
qMf1mvROJN7vR7k23sCd223u/kqoGpSw0RQ2NmsiMhejRPAHRs5oHpHVjbi0P4kRF19wyfYDOQgz
nLRpmzFK/aZ1yOiU4oWxoEs8gpW8WLni/SKrW5R28GMt2sFsqgsOlzZ3jN5RX7KwF9z3fCTpEV5l
h7JQ4PhBNRBpDaVOc5B6ZS6HuhHBMvqdEFoYRJHfzJPllYkemSynK3msMW+iOcCuFMpOsv4YrByP
syExQjge8mhLXk7kqQSnXl/lssLjGqPvEmGDv5jjMKFAJ2+WRlxaRZ4Xu0NhqPpRQCmKah6YveON
4DMDSTd6nRjheT+RmORLMFqdnRev4R/7Yl97A+brrjiMm2jRWQZN4Q9YFIWdrt2vV3N8Am5E1bo=
',NULL,NULL,NULL,602,2031),
	 (520,'677589',137400.00,'556773','SIHBIDJ1','CENAIDJA','20211002SIHBIDJ1510O0100000050','SUCCESS','2021-10-02 13:22:40.304',NULL,'eJyVUstuwjAQ/BefAa0TUii3PHi4KlCB1UvVAyThoRIHOYtEi/j3rm0itVVRqaU4a+/Ozni0JxYd
qnG1Zr0TC/f7UaZNNHD7dpu5vxKqQiXsaYobmzUnNhejSCQPnJ1pNZgsb8TF/UlIuPCCi7YfpEEY
cuaBxzmAV7cOOEyBLswKgDUYVSb5yhXvF2nVAujSx1vQpWys8wQvbZocmuBJ7vc8r9cGRkxJmR6K
XKHTh+VAxBUWOs5Q6pW5HOraBKvod0GkJLjrdPyaTxZXGBtsspyu5LGiPKfTHHFXCGWZbDxGa8fj
bMiMEU6HPNqSlxN7KtC511eZLGm7pui7RdTgL+VEJhTq6M3KCAvryPNid8iNVL/TBiBTzQPTd7oR
ycxA4o1eR8b4pB9JSiZLNF6dXRSu8R/zYl97A+brrDiMY7ToNMW68AeMXhl07+2AvZrtExN51dg=
','eJyNUctuwjAQ/JXKZ0C2UwPNzSRQQlVAwT1VHKI8AIk4UWLaUsS/d70hFa2KVEfxc3ZnZvdERof6
ud4Q90RkWU6Tyu4mzbzbJc2qA10bHeBpYbb4ak/EG89l4M8kOcPoEFX8M24VTEcQxy5xo90naAgs
OeGUM0Ypb1MLRkPKKQwmBCUdAkg/zRpwGcV1D8Dwsx6zr16V+uaSpstol3LFHJdz9x7IOsQv4kOe
atPoM8UkWOZmZeqwxKvHqi0B6vlbDmqhfdGyqfw2n/oIdCZ1AhTEfYUqVBu9H+tEFTD9IGhLAgQL
2pBQ9IshkOcGGFCiPxg4xJIhDZGe8uBobdXQggyZYWt9LStTHQHzAoFQ/fV3/jDN7LuXGPS/NEe7
zK23WbGN9J1MopzYblmIjGPTdvNXb0GMGD4gUpXXlJ70PIL9XpX7HO78yESobazf9uU1uw0klIEJ
TPmkll1Oh85QPAhb9rBOtGm8Iki9axRKHTR1XsMH4wuj3tFT
','20211002CENAIDJA510R0200001550','ACTC',519,1927),
	 (522,'677589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000001','ERROR-CIHUB','2021-10-03 05:33:41.162','Unrecognized token ''bifast'': was expecting (JSON String, Number, Array, Object or token ''null'', ''true'' or ''false'')
 at [Source: (ByteArrayInputStream); line: 1, column: 8]','eJyVUttuwjAM/Zc8F5RQujLe2oZLpkEniPYy7QHactFoilIjsSH+fU5CpW0aGrPU1I597JMjn0h8
qCf1mvRPJNrvx7k23tCd223u/kqoGpSwUQobmzURmYtxLPgDI2c0j8jqRlwymEaIiy64ePuBHIQZ
Tjq0wxilftM6YDSleGGNEY9gJS9Wrni/yOo2pT38WJv2MJvogsOlTYvRFvUlDfq+3+8iSY/wKjuU
hQLHD6qhSGoodZKD1CtzOdKNCJbR74TQgm4Y+s08WV6Z6JHpMl3JY415E80BdqVQdpL1J2DleJyN
iBHC8ZBHW/JyIk8lOPUGKpcVHtcYfZcIG/zFHIcJBTp+szSi0iryvNgdCkPVD7uUoqjmgdk73gg+
M5Bko9exEZ4PYolJvgSj1dl50Rr+sS/2tTdgvu6Kw7iJFp1l0BT+gN2FYdC7twv2ao5PC7DV0A==
','eJxLykxLLC7Ry8lMKkosqtTLLM43MjAwMtJLLi0uyc/VcyotzsxLLS72BeLE9FQHQxODRBPj5CQA
MPoTdg==
',NULL,NULL,521,1018),
	 (524,'677589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000002','SUCCESS','2021-10-03 05:45:58.433',NULL,'eJyVUstuwjAQ/BefAdmENCm3JObhqpAKrF6qHiAJD5U4yFkkWsS/d20Tqa2KSi3F2fXueMajPZH4
UE/qNemfSLTfj3NtoqHbt9vc/ZVQNShhsxQ2tmoyMhfjWPAHRs64WkRWN+KSwTRCXHTBxdsP1CAM
OenSLmOUes3VPqMpxQO7uqRFsJMXK9e8X2R1h9IQP9ahIVYTXXC4XNNmtE09Sf1+z+/7IUEmXmWH
slDg9EE1FEkNpU5ykHplDke6McEq+l0QLr8XBF7DJ8srjC0yXaYreayxzjCbA+xKoSyTjSdg7Xic
jYgxwumQR9vyciJPJTj3BiqXFW7XFH23CC/4SzmSCQU6frMyotI68rzYHQoj1Qt6lKKp5oHZO54I
PjOQZKPXsTGeD2KJRb4E49XZRdEa/jEv9rU3YL7OisM4RovOMmgaf8DugsAP7+2AvZrtEzYd1eg=
','eJyNUdtugkAQ/ZVmn9XsAluQN1ywYlMluH1qfDBclEQWAmutNf57ZxdpbFOTDmFvc2bOnJkzmhza
l3aL3DPy6nqWNuo07daiSLtdhKKVItS3pdxpr7ohFiy80J976AI2QLz6Z9wqnE0gjlzjJsUn1BAq
cmRggxCMzT41JTjGBlZmOA4aIED6Wd6B603SjsABPxkRDF7WZL68phkSPMQmx9S1qEsdBEx+lRzK
TMiuPllNw6iUK9nGtX56avoW6Hr+LkcZoQbt2Xh5n49/hCL3RAoUyH2DLjRbsQ9EyitYfhD0LQGC
Je5IQBhw6BDIcwcMRi3bNpEi0zTIY5zBVclqYQS5Zoaj0hU1sjkB5hXioPvr7/xxlis/S6XWH8mT
2hZKW9AcC/Ew31XihNS4FMZLEtmP89dwH22bOmON5PUtJ/MYQ3rgq3pfwpu/kRtdXCDe9/UtvQpE
mIAKnfKZR0MDE9uwrLEFj3GbCtmJ1SB+FLpSbGpVlzV8YF/059IR
','20211003CENAIDJA510R0200000288','ACTC',523,237),
	 (526,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000003','ERROR-CIHUB','2021-10-03 05:46:31.899','HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','eJyVUk1vwjAM/S85F5RSujJubVMg06ATRLtMO0A/AI2mKDUSG+K/z0motE1DY5aa2rGf/fLkE4kO
zbRZk+GJhPv9JFfaG9lzu83tX3LZgOQmSmFjsjoiCz6JOHtwyRnNIaK+ERcnsxBx4QUXbT+QA9fD
SY/2XJdSr23tuzSleGHMIw7BSlaUtni/zJoupQP83C4dYDZWBYNLm45LO9QT1B/274YeknQIq7ND
VUiw/KAe8biBSsU5CFXqy7FqRTCMfieE5veDwGvnierKRIfMVmkpjg3mdbQA2FVcmknGn4KR43E+
JloIy0McTcnLiTxVYNVLZC5qPK4x+i4RNviLOQ7jElT0ZmiElVHkebk7FJqqF/QpRVH1A7N3vOFs
riHxRq0jLTxLIoFJtgKt1dl64Rr+sS/mtTdgvu6KxdiJBp1l0Bb+gAWBP7g3+/Wqj09JetWk
',NULL,NULL,NULL,525,6020),
	 (528,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000004','ERROR-CIHUB','2021-10-03 05:50:08.693','HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','eJyVUstuwjAQ/BefAa0JUSi3PHi4KlCB1UvVAyThoRIHOYtEi/j3rm0i0aqo1FKcXe+OZzzaE4sO
1bhas96Jhfv9KNMmGrh9u83cXwlVoRI2m+LGVk3G5mIUieSRszOtBpPlnbi4PwkJF15w0faTNAhD
ztrQ5hzAq6/2OUyBDuzqsAajziRfueb9Iq1aAF36eAu6VI11nuDlmiaHJngS/J4PPaoSU1KmhyJX
6PRhORBxhYWOM5R6ZQ6HujbBKvpdEC2/EwRezSeLG4wNNllOV/JYUZ1TNkfcFUJZJhuP0drxNBsy
Y4TTIY+25fXEngt07vVVJkvabin6bhFd8JdyIhMKdfRuZYSFdeRlsTvkRqoXdADIVPPA9INORDIz
kHij15ExPulHkorJEo1XZxeFa/zHvNjX3oG5nhWHcYwWnaZYN/6ABYHffbDz9Wa2L0kf1aQ=
',NULL,NULL,NULL,527,6260),
	 (530,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000005','ERROR-CIHUB','2021-10-03 05:55:51.358','HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','eJyVUttuwjAM/Rc/F5SuVGW8tQ2XTAMmiPYy7QHactFoilIjsSH+fU5CpW0aGotaJ7F97JMjnyA5
1ON6Db0TxPv9KNfmNHB2u83droSqUQl7m+LGRs0N5mKUCP7gw5mWB7K6EZf2JzHh4gsu2X4QB2Ga
wx27833GgqZ06LMpI4ddIXhAmbxYueT9IqvbjHXp99usS9FUFxwvZVo+a7FAsrAX0kckPeBVdigL
hY4fVgOR1ljqNEepV8Y51I0IltHvhAyTThQFTT9ZXunowWQ5XcljTXFzmyPuSqFsJ3seo5XjcTYE
I4TjIY825eUETyU69foqlxWZa4y+S0QF/mJOzYRCnbxZGnFpFXle7A6FoRpEHcZIVPPA7J08gs8M
JN3odWKE5/1EUpAv0Wh1dqd4jf+YF/vaGzBfZ8VhXEeLzjJsEn/Aoijs3tv5ejXmE1c21aw=
',NULL,NULL,NULL,529,6232),
	 (532,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000006','ERROR-CIHUB','2021-10-03 06:00:21.912','HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','eJyVUttuwjAM/Zc8F+RSoIy3tuGSacAE0V6mPUBbLhpNUWokNsS/z0motE1D2yw1tWMf++TIZxYf
q0m1Yf0ziw6HcaaNN3Tnbpe5vxKqQiVsNMOtzZqILcQ4FvzeZxcyj8nyj7hkMI0IF11x8e6dOAgz
nLWg5fsAQd2648MM6MJal3mMKnm+dsWHZVo1AXr0+U3oUTbROcdrm4YPDQgkdPsA/RaR9Bgv02OR
K3T8sByKpMJCJxlKvTaXI12LYBn9TIis0w7DoJ4nixsTPTZdzdbyVFHeRAvEfSGUnWT9CVo5HuYj
ZoRwPOTJljyf2WOBTr2BymRJxy1GXyWiBr8xp2FCoY5fLY2osIo8LffH3FANwjYAiWoemL7RjeBz
A0m2ehMb4fkglpTkKzRaXZwXbfAf+2Jf+wfM511xGDfRotMU68JvsDDs9O7sfr2Y4wMvz9WW
',NULL,NULL,NULL,531,6251);
INSERT INTO public.account_enquiry (id,account_no,amount,intr_ref_id,orign_bank,recpt_bank,bizmsgid,call_status,cihub_req_time,error_message,full_request_msg,full_response_msg,resp_bizmsgid,response_status,chnl_trx_id,cihub_elapsed_time) VALUES
	 (534,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000007','ERROR-CIHUB','2021-10-03 07:19:59.695','HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','eJyVUstuwjAQ/BefAdmEyMAtiXm4KlCB1UvVAyThoRIHOYtEi/j3rm0itVVRqaU4u94dz3i0ZxIf
q0m1If0ziQ6HcWZsNPT7bpf5v5a6Ai1dNoOtq9qMLOQ4luKBkQuuBlHlnbhkMI0QF11x8e4DNUhL
Ttq0zRilQX11yOiM4oFbnDQIdop87ZsPy7RqUdrFj7VoF6uJyQVcr2ky2qSBorzPev2wR5BJlOmx
yDV4fVAOZVJBYZIMlFnbw5GpTXCKfheEK+xwHtR8qrjB2CDT1WytThXWGWYLgH0htWNy8QScHY/z
EbFGeB3q5FpezuSpAO/eQGeqxO2Wou8W4QV/KUcyqcHEb05GVDhHnpf7Y26lBrxDKZpqH5i+44kU
cwtJtmYTW+PFIFZYFCuwXl18FG3gH/PiXnsH5uuseIxndOg0hbrxB4zzsNtz8/Vqt0+BTNXE
',NULL,NULL,NULL,533,6338),
	 (536,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000008','ERROR-CIHUB','2021-10-03 07:26:08.342','HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','eJyVUstuwjAQ/BefATkEGsotiXm4KqQCq5eqB0jCQyUOchaJFvHvXa+J1FZFpZbirHd3dsYjn1h0
qCbVmvVPLNzvx5mx0dDt223m/lrqCrSkUwIbqtoTm8txJMWDx864GkyVN+LiwTREXHjBRdsP1CAt
OWvztudx7tejux5POCZo9ViDYafIV655v0irFqbx81pUjU0u4DKm6fEm9xUP+u27PlaRSZTpocg1
OH1QDmVcQWHiDJRZ2eTI1CaQot8F4ep2gsCv+VRxhbHBpstkpY4V1j08zQF2hdTERPEEyI7H2YhZ
I5wOdaSWlxN7KsC5N9CZKnG7pui7RTjgL+VIJjWY6I1khAU58rzYHXIr1Q86nKOp9oLpO2akmFlI
vDHryBovBpHColiC9ersonAN/3gvdNsbMF/fisM4RkKnKdSNP2BB0O3d0/t6tdsnaDnVtg==
',NULL,NULL,NULL,535,6259),
	 (605,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000050','TIMEOUT-CIHUB','2021-10-03 15:54:37.02',NULL,'eJyVUstuwjAQ/BefAdmEKJRbEvNwVaACq5eqB0hCiEoc5CwSLeLfu7aJRKuiUktx1t6dnfFoTyQ6
1NM6J4MTCff7SapNNHJ7UaTur4SqQQl7msPWZs2JLMUkEvyRkTOuFpHVnbh4OAsRF15wUfGJGoQh
J13aZYxSr2ntMzqneGGWT0mLYCXPNq54v0rqDqV9/FiH9jEb64zDpU2b0Tb1JPMHfm/gBQSZeJUc
ykyB0wfVSMQ1lDpOQeqNuRzrxgSr6HdBRkovCLyGT5Y3GFtktp5v5LHGPMPTEmBXCmWZbDwFa8fT
YkyMEU6HPNqS1xN5LsG5N1SprHC7pei7RdjgL+VIJhTo6N3KCEvryMtqd8iMVC/oUYqmmgcmH3gj
+MJA4q3OI2M8H0YSk3wNxquzi8Ic/jEv9rV3YK5nxWEco0UnCTSFP2BB4Pcf7Hy9me0LZWzVtA==
',NULL,NULL,NULL,604,2233),
	 (542,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000011','ERROR-CIHUB','2021-10-03 07:33:42.44','HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','eJyVUstuwjAQ/Jc9A7IJKJRbEvNwVaCiVi9VD5CEh0oc5CwSLeLfu7aJ1FZFpZbirHd3POPRniA+
VJNqDf0TRPv9ODM2Gvp9u838X0tdoZbuNMONq9oTPMlxLMU9hzOtBqjyRlwymEaEiy64ePtBGqQl
hzZrc85YUF/d5WzGKGEX59AA6hT5yjfvF2nVYqxHH2+xHlUTkwu8XNPkrMkCxcJ+EPQ7bSAmUaaH
Itfo9WE5lEmFhUkyVGZlkyNTm+AU/S6IVrcThkHNp4orjA2YLmcrdayobrU/Ie4KqR2Tiyfo7HiY
j8Aa4XWoo2t5OcFjgd69gc5USds1Rd8togv+Uk5kUqOJ35yMqHCOPC92h9xKDcIOY2SqfWD6Thkp
5haSbMw6tsaLQayoKJZovTr7KFrjP+bFvfYGzNdZ8RjP6NBpinXjD1gYdnt3br5e7fYJRivVog==
',NULL,NULL,NULL,541,6284),
	 (544,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000012','ERROR-CIHUB','2021-10-03 07:35:54.125','HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','eJyVUstuwjAQ/BefAdmEKJRbEvNwVaACq5eqB0hCiEoc5CwSLeLfu7aJRKuiUktxdr07nvFoTyQ6
1NM6J4MTCff7SapNNHJ7UaTur4SqQQmbzWFrqyYjSzGJBH9k5IyrRWR1Jy4ezkLEhRdcVHyiBmHI
SZd2GaPUa672GZ1TPDCLdUmLYCfPNq55v0rqDqV9/FiH9rEa64zD5Zo2o23qSRoMPH/g9wgy8So5
lJkCpw+qkYhrKHWcgtQbczjWjQlW0e+CcPm9IPAaPlneYGyR2Xq+kcca6wyzJcCuFMoy2XgK1o6n
xZgYI5wOebQtryfyXIJzb6hSWeF2S9F3i/CCv5QjmVCgo3crIyytIy+r3SEzUr2gRymaah6YfOCJ
4AsDibc6j4zxfBhJLPI1GK/OLgpz+Me82NfegbmeFYdxjBadJNA0/oAFgd9/sPP1ZrYvWzzVrg==
',NULL,NULL,NULL,543,6341),
	 (546,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000013','TIMEOUT-CIHUB','2021-10-03 07:43:28.741','HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','eJyVUstuwjAQ/BefAdmEKCm3JObhqkAFVi9VD5CEgEoc5CwSLeLfu7aJRKuiUktxdr07nvFoTyQ+
1JO6IP0Tifb7caZNNHT7dpu5vxKqBiVsNoONrZqMLMQ4FvyRkTOuFpHVnbhkMI0QF11w8fYTNQhD
Trq0yxilXnO1z+iM4oFZzCMtgp08X7vm/TKtO5SG+LEODbGa6JzD5Zo2o23qSRr0e16/GxJk4lV6
KHMFTh9UQ5HUUOokA6nX5nCkGxOsot8F4fJ7QeA1fLK8wdgi09VsLY811hlmC4BdKZRlsvEErB1P
8xExRjgd8mhbXk/kuQTn3kBlssLtlqLvFuEFfylHMqFAx+9WRlRaR16Wu0NupHpBj1I01Tww/cAT
wecGkmx0ERvj+SCWWOQrMF6dXRQV8I95sa+9A3M9Kw7jGC06TaFp/AELAj98sPP1ZrYvXoPVsA==
',NULL,NULL,NULL,545,6337),
	 (548,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000014','TIMEOUT-CIHUB','2021-10-03 08:13:37.958','HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','eJyVUstuwjAQ/BefAdmEKCm3JObhqkAFVi9VD5CEgEoc5CwSLeLfu7aJRKuiUktxdr07nvFoTyQ+
1JO6IP0Tifb7caZNNHT7dpu5vxKqBiVsNoONrZqMLMQ4FvyRkTOuFpHVnbhkMI0QF11w8fYTNQhD
Trq0yxilXnO1z+iM4oFZrEdaBDt5vnbN+2VadygN8WMdGmI10TmHyzVtRtvUkzTsM6/vBQSZeJUe
ylyB0wfVUCQ1lDrJQOq1ORzpxgSr6HdBuPxeEHgNnyxvMLbIdDVby2ONdYbZAmBXCmWZbDwBa8fT
fESMEU6HPNqW1xN5LsG5N1CZrHC7pei7RXjBX8qRTCjQ8buVEZXWkZfl7pAbqV7QoxRNNQ9MP/BE
8LmBJBtdxMZ4PoglFvkKjFdnF0UF/GNe7GvvwFzPisM4RotOU2gaf8CCwA8f7Hy9me0LWrbVrg==
',NULL,NULL,NULL,547,6266),
	 (550,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000015','TIMEOUT-CIHUB','2021-10-03 08:15:10.595','HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','eJyVUstuwjAQ/BefAa0boqTc8uDhqkAFVi9VD5CEh0oc5CwSLeLfu7aJ1FZFpZbi7Hp3POPRnlh8
qMf1mvVOLNrvR7k20cDt223u/kqoGpWw2RQ3tmoyNhejWKQPnJ1ptZisbsQl/UlEuOiCi7cfpEEY
cnYHd5wDeM3VPocp0IFZ3GctRp1psXLN+0VWdwBC+ngHQqomukjxck2bQxs8CWGP+z0OjJjSKjuU
hUKnD6uBSGosdZKj1CtzONSNCVbR74Jo+d0g8Bo+WV5hbLHJcrqSx5rqnLI54q4UyjLZeIzWjsfZ
kBkjnA55tC0vJ/ZUonOvr3JZ0XZN0XeL6IK/lBOZUKjjNysjKq0jz4vdoTBSvaALQKaaB2bvdCLS
mYEkG72OjfFpP5ZUTJdovDq7KFrjP+bFvvYGzNdZcRjHaNFZhk3jD1gQ+OG9na9Xs30CRUnVog==
',NULL,NULL,NULL,549,6264),
	 (552,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000016','TIMEOUT-CIHUB','2021-10-03 08:15:55.078','HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','eJyVUstuwjAQ/BefAdmENCm3JObhqpAKrF6qHiAJD5U4yFkkWsS/d20Tqa2KSi3F2fXueMajPZH4
UE/qNemfSLTfj3NtoqHbt9vc/ZVQNShhsxQ2tmoyMhfjWPAHRs64WkRWN+KSwTRCXHTBxdsP1CAM
OenSLmOUes3VPqMpxQOz2B1pEezkxco17xdZ3aE0xI91aIjVRBccLte0GW1TT9Kwz/y+7xNk4lV2
KAsFTh9UQ5HUUOokB6lX5nCkGxOsot8F4fJ7QeA1fLK8wtgi02W6ksca6wyzOcCuFMoy2XgC1o7H
2YgYI5wOebQtLyfyVIJzb6ByWeF2TdF3i/CCv5QjmVCg4zcrIyqtI8+L3aEwUr2gRymaah6YveOJ
4DMDSTZ6HRvj+SCWWORLMF6dXRSt4R/zYl97A+brrDiMY7ToLIOm8QcsCPzw3s7Xq9k+AWh81bY=
',NULL,NULL,NULL,551,6254),
	 (554,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000017','ERROR-CIHUB','2021-10-03 11:08:21.232','HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','eJyVUk1vwjAM/S85F5RQUDtubVMg04AJol2mHaAtHxpNUWokNsR/n5NQiU1DY5aa2rGf/fLkE4kP
9bhek/6JRPv9KNfGG7hzu83dXwlVgxI2msLGZk1E5mIUC/7IyBnNI7K6E5ekkwhx0QUXbz+RgzDD
SYd2GKPUb1r3GJ1SvDDGAuIRrOTFyhXvF1ndpjTEj7VpiNlEFxwubVqMtqgvGevTsN9Bkh7hVXYo
CwWOH1QDkdRQ6iQHqVfmcqgbESyj3wmh9bpB4DfzZHljokcmy+lKHmvMm2gOsCuFspOsPwYrx9Ns
SIwQjoc82pLXE3kuwamXqlxWeNxi9F0ibPAXcxwmFOj43dKISqvIy2J3KAxVP+hSiqKaB2YfeCP4
zECSjV7HRniexhKTfAlGq7PzojX8Y1/sa+/AXO+Kw7iJFp1l0BT+gAVBL3yw+/Vmji9EndWi
',NULL,NULL,NULL,553,6279);
INSERT INTO public.account_enquiry (id,account_no,amount,intr_ref_id,orign_bank,recpt_bank,bizmsgid,call_status,cihub_req_time,error_message,full_request_msg,full_response_msg,resp_bizmsgid,response_status,chnl_trx_id,cihub_elapsed_time) VALUES
	 (556,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000018','ERROR-CIHUB','2021-10-03 11:11:01.518','HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','eJyVUttuwjAM/Zc8F5RQUBlvvXDJNGCCaC/THqAtF42mKDUSG+LfZydUYtPQWNQmduxjnxz5xKJD
Na7WrHdi4X4/ygxZA7dvt5k7tdQVaGm9KWxslDw2l6NIJo+CnXF5TJV34uL+JERceMFF20/kIKk5
a/GWEJz7demO4FOOF7REl3kMM5N85ZL3i7Rqct7FXzQ5RWOTJ3Ap0xC8wX0lRA8/jiQ9lpTpocg1
OH5QDmRcQWHiDJRZ0eXQ1CJYRr8TwtVpB4Ff91PFjY4emyynK3WsME7eHGBXSG07WXsMVo6n2ZCR
EI6HOtqU1xN7LsCp19eZKnG7xei7RFjgL+bYTGow0bulERZWkZfF7pATVT9oc46i0gPTD7yRyYwg
8casIxI+6UcKg8kSSKuzs8I1/GNe7GvvwFzPisO4jhadplAn/oAFQaf7YOfrjbYvK3jVlA==
',NULL,NULL,NULL,555,6253),
	 (559,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000020','ERROR-CIHUB','2021-10-03 11:31:46.931','HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','eJyVUstuwjAQ/BefA7IJNJRbEvNwVUgFVi9VD5AEiEoc5CwSLeLfu7aJ1FZFpZbi7Hp3POPRnkh0
qKf1hgxOJNzvJ5k20cjtRZG5vxKqBiVslsDWVk1GFmISCf7AyBmXR2R1Iy4ezkLEhRdcVHygBmHI
SYd2GKPUb67uMZpQPDCrQ4lHsJPna9e8X6Z1m9I+fqxN+1iNdc7hck2L0Rb1JWMDnw26dwSZeJUe
ylyB0wfVSMQ1lDrOQOq1ORzrxgSr6HdBuHrdIPAbPlleYfTIbJWs5bHGOsNsAbArhbJMNp6CteNx
PibGCKdDHm3Ly4k8leDcG6pMVrhdU/TdIrzgL+VIJhTo6M3KCEvryPNyd8iNVD/oUoqmmgem73gi
+NxA4q3eRMZ4PowkFvkKjFdnF4Ub+Me82NfegPk6Kw7jGC06TaFp/AELgl7/3s7Xq9k+ATtJ1Zw=
',NULL,NULL,NULL,558,6317),
	 (562,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000022','ERROR-CIHUB','2021-10-03 11:40:06.988','Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','eJyVUstuwjAQ/BefAa0TaCi3JObhqkAFVi9VD5CEh0oS5CwSLeLfu7aJ1FZFpZbi7Hp3POPRnlh0
qMbVmvVOLNzvR6k20cDt223q/oUsKiykzaa4sVWTsbkcRVI8cHam1WCqvBEX9ych4cILLtp+kAZp
yJkHHucAfn11h8MU6MAsz2MNRp0iW7nm/SKpWgBd+ngLulSNdSbwck2TQxN8xXmvDT24Y8QkyuSQ
ZwU6fVgOZFxhruMUlV6Zw6GuTbCKfhdEq9MOAr/mU/kVxgabLKcrdayozimbI+5yWVgmG4/R2vE4
GzJjhNOhjrbl5cSecnTu9YtUlbRdU/TdIrrgL+VEJgvU0ZuVEebWkefF7pAZqX7QBiBTzQOTdzqR
YmYg8UavI2O86EeKimKJxquzi8I1/mNe7GtvwHydFYdxjBadJFg3/oAFQad7b+fr1WyfM73VmA==
',NULL,NULL,NULL,561,239),
	 (565,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000024','ERROR-CIHUB','2021-10-03 11:45:08.387','HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','eJyVUstuwjAQ/BefAdkkUSi3JA7gqkAFVi9VD5CEh0oc5CwSLeLfu7aJRKuiUktxdr07nvFoTyQ+
1ON6TfonEu33o1ybaOD27TZ3fyVUDUrYbAobWzUZmYtRLPgjI2dcLSKrO3FJOokQF11w8fYTNQhD
Trq0yxilXnN1wOiU4oFZXZ+0CHbyYuWa94us7lDaw491aA+riS44XK5pM9qmnmSs7wd9rCITr7JD
WShw+qAaiKSGUic5SL0yh0PdmGAV/S4IV+CHodfwyfIGY4tMltOVPNZYZ5jNAXalUJbJxmOwdjzN
hsQY4XTIo215PZHnEpx7qcplhdstRd8twgv+Uo5kQoGO362MqLSOvCx2h8JI9UKfUjTVPDD7wBPB
ZwaSbPQ6NsbzNJZY5EswXp1dFK3hH/NiX3sH5npWHMYxWnSWQdP4AxaGQe/Bzteb2b4AU0PVqg==
',NULL,NULL,NULL,564,7068),
	 (567,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000025','ERROR-CIHUB','2021-10-03 11:54:09.934','HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','eJyVUstuwjAQ/BefAdmEKMAtiXm4KlCB1UvVAyQhRCUOchaJFvHvXdtEaquiUktxdr07nvFozyQ6
1rM6J8MzCQ+HaapNNHZ7UaTur4SqQQmbLWBnqyYjKzGNBH9g5IKrRWR1Jy4ezUPEhVdcVHygBmHI
SZd2GaPUa672GV1QPDCr65MWwU6ebV3zYZ3UHUr7+LEO7WM11hmH6zVtRtvUk4wN/d6QDggy8So5
lpkCpw+qsYhrKHWcgtRbczjRjQlW0e+CcPm9IPAaPlneYGyR+Waxlaca6wyzFcC+FMoy2XgG1o7H
5YQYI5wOebItL2fyVIJzb6RSWeF2S9F3i/CCv5QjmVCgozcrIyytI8/r/TEzUr2gRymaah6YvOOJ
4EsDiXc6j4zxfBRJLPINGK8uLgpz+Me82Nfegfk6Kw7jGC06SaBp/AELAr8/sPP1arZPWiDVrg==
',NULL,NULL,NULL,566,6346),
	 (569,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000026','ERROR-CIHUB','2021-10-03 11:54:28.429','Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','eJyVUstuwjAQ/BefAdkJaVJuSczDVYEKrF6qHiAJD5U4yFkkWsS/d20Tqa2KSi3F2fXueMajPZHk
UI/rNemdSLzfj3JtooHbt9vc/ZVQNShhsylsbNVkZC5GieAPjJxxtYisbsSl/UmMuPiCS7YfqEEY
cuJRjzFK/ebqgNEpxQOzvDvSItjJi5Vr3i+yukNphB/r0AirqS44XK5pM9qmvmSsF3R7XkSQiVfZ
oSwUOH1QDURaQ6nTHKRemcOhbkywin4XhCvohqHf8MnyCmOLTJbTlTzWWGeYzQF2pVCWycZjsHY8
zobEGOF0yKNteTmRpxKce32Vywq3a4q+W4QX/KUcyYQCnbxZGXFpHXle7A6FkeqHXUrRVPPA7B1P
BJ8ZSLrR68QYz/uJxCJfgvHq7KJ4Df+YF/vaGzBfZ8VhHKNFZxk0jT9gYRhE93a+Xs32CWD/1bI=
',NULL,NULL,NULL,568,13),
	 (571,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000027','ERROR-CIHUB','2021-10-03 12:12:51.535','Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','eJyVUttuwjAM/Zc8F5S0VGW8tQ2XTAMmiPYy7QHactFoilIjsSH+fU5CJTYNjUVtYsc+9smRTyQ5
1ON6TXonEu/3o1wba+D27TZ3pxKqBiWsN4WNjRqPzMUoEfyRkTMuj8jqTlzan8SIiy+4ZPuJHIRp
TnzqM0Zp0JQOGZ1SvDDLj4hHMJMXK5e8X2R1m9Iu/qxNuxhNdcHhUqbFaIsGkvk9/EIk6RFeZYey
UOD4QTUQaQ2lTnOQemUuh7oRwTL6nRCusBNFQdNPljc6emSynK7ksca48eYAu1Io28naY7ByPM2G
xAjheMijTXk9kecSnHp9lcsKt1uMvkuEBf5ijs2EAp28WxpxaRV5WewOhaEaRB1KUVTzwOwDbwSf
GUi60evECM/7icQgX4LR6uyseA3/mBf72jsw17PiMK6jRWcZNIk/YFEUdh/sfL2Z7QtEXNWi
',NULL,NULL,NULL,570,301),
	 (573,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000028','ERROR-CIHUB','2021-10-03 12:59:56.285','Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','eJyVUstuwjAQ/BefAdkJaYBbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEolVRqaU4u94dz3i0JxIf
qnG1Jr0Tifb7UaZNNHD7dpu5vxKqAiVsNoWNrZqMzMUoFvyRkTOuBpHlnbikP4kQF11w8fYTNQhD
TjzqMUapX18dMDqleGCW1yENgp08X7nm/SKtWpR28GMtaqqJzjlcrmky2qS+ZF4v6PaCB4JMvEwP
Ra7A6YNyIJIKCp1kIPXKHA51bYJV9LsgXEE7DP2aTxY3GBtkspyu5LHCOsNsDrArhLJMNh6DteNp
NiTGCKdDHm3L64k8F+Dc66tMlrjdUvTdIrzgL+VIJhTo+N3KiArryMtid8iNVD9sU4qmmgemH3gi
+MxAko1ex8Z43o8lFvkSjFdnF0Vr+Me82NfegbmeFYdxjBadplA3/oCFYdDp2vl6M9sXgJfVxA==
',NULL,NULL,NULL,572,268),
	 (576,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000030','ERROR-CIHUB','2021-10-03 14:21:53.086','Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','eJyVUstuwjAQ/BefAdmEKJRbEvNwVaACq5eqB0hCiEoc5CwSLeLfu7aJRKuiUktxdr07nvFoTyQ6
1NM6J4MTCff7SapNNHJ7UaTur4SqQQmbzWFrqyYjSzGJBH9k5IyrRWR1Jy4ezkLEhRdcVHyiBmHI
SZd2GaPUa672GZ1TPDDLo6RFsJNnG9e8XyV1h9I+fqxD+1iNdcbhck2b0Tb1JOsNumzgewSZeJUc
ykyB0wfVSMQ1lDpOQeqNORzrxgSr6HdBuPxeEHgNnyxvMLbIbD3fyGONdYbZEmBXCmWZbDwFa8fT
YkyMEU6HPNqW1xN5LsG5N1SprHC7pei7RXjBX8qRTCjQ0buVEZbWkZfV7pAZqV7QoxRNNQ9MPvBE
8IWBxFudR8Z4PowkFvkajFdnF4U5/GNe7GvvwFzPisM4RotOEmgaf8CCwO8/2Pl6M9sXPrrVng==
',NULL,NULL,NULL,575,205),
	 (578,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000031','ERROR-CIHUB','2021-10-03 14:23:08.842','Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','eJyVUstuwjAQ/BefAdkEFMotiXm4KlBRq5eqB0jCQyUOchaJFvHv3bWJ1FZFpZbirHd3POPRnlh8
qCbVmvVPLNrvx5mlaOj37Tbzf6NMBUa50ww2rkon9qTGsZL3gp1xNZgub8Qlg2mEuOiCi7cfqEER
OWvzthCcB/XVXcFnHBO0AsEaDDtlvvLN+0VatTjv4SdavIfVxOYSLtc0BW/yQItOvx30sYpMskwP
RW7A64NyqJIKCptkoO2KkiNbm+AU/S4IV7cThkHNp4srjA02Xc5W+lhhnbQ/AewKZRyTiyfg7HiY
jxgZ4XXoo2t5ObHHArx7A5PpErdrir5bhBf8pRzJlAEbvzkZUeEceV7sDjlJDcIO52gqPTB9x4yS
c4IkG7uOyXg5iDUW5RLIq7OPojX8Y17ca2/AfJ0Vj/GMDp2mUDf+gIVht3fn5uuVtk9JIdWk
',NULL,NULL,NULL,577,242);
INSERT INTO public.account_enquiry (id,account_no,amount,intr_ref_id,orign_bank,recpt_bank,bizmsgid,call_status,cihub_req_time,error_message,full_request_msg,full_response_msg,resp_bizmsgid,response_status,chnl_trx_id,cihub_elapsed_time) VALUES
	 (580,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000032','TIMEOUT-CIHUB','2021-10-03 15:04:36.052','Timeout menunggu response dari CIHUB','eJyVUstuwjAQ/BefAdmENJRbEvNwVaACq5eqB8gLVOIgZ5FoEf/etU2ktioqtRRn17vjGY/2RKJD
Pa0LMjiRcL+fpNpEI7dvt6n7K6FqUMJmc9jYqsnIUkwiwR8YOeNqEVndiIuHsxBx4QUXbT9QgzDk
pEu7jFHqNVf7jM4pHpjldUmLYCfPcte8XyV1h9I+fqxD+1iNdcbhck2b0Tb1JPMHtDfw7ggy8So5
lJkCpw+qkYhrKHWcgtS5ORzrxgSr6HdBuPxeEHgNnyyvMLbIbD3P5bHGOsNsCbArhbJMNp6CteNx
MSbGCKdDHm3Ly4k8leDcG6pUVrhdU/TdIrzgL+VIJhTo6M3KCEvryPNqd8iMVC/oUYqmmgcm73gi
+MJA4o0uImM8H0YSi3wNxquzi8IC/jEv9rU3YL7OisM4RotOEmgaf8CCwO/f2/l6NdsnUATVqA==
',NULL,NULL,NULL,579,2207),
	 (584,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000035','ERROR-CIHUB','2021-10-03 15:12:02.923','Read timed out','eJyVUstuwjAQ/BefAdkJUSi3JA7gqkAFVi9VD5CEh0oc5CwSLeLfu7aJRKuiUktxdr07nvFoTyQ+
1ON6TfonEu33o1ybaOD27TZ3fyVUDUrYbAobWzUZmYtRLPgjI2dcLSKrO3FJOokQF11w8fYTNQhD
TjzqMUap31wdMDqleGCWH5AWwU5erFzzfpHVHUp7+LEO7WE10QWHyzVtRtvUlyzoM69PPYJMvMoO
ZaHA6YNqIJIaSp3kIPXKHA51Y4JV9LsgXEE3DP2GT5Y3GFtkspyu5LHGOsNsDrArhbJMNh6DteNp
NiTGCKdDHm3L64k8l+DcS1UuK9xuKfpuEV7wl3IkEwp0/G5lRKV15GWxOxRGqh92KUVTzQOzDzwR
fGYgyUavY2M8T2OJRb4E49XZRdEa/jEv9rV3YK5nxWEco0VnGTSNP2BhGPQe7Hy9me0LPZ3Vng==
',NULL,NULL,NULL,583,2271),
	 (586,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000036','ERROR-CIHUB','2021-10-03 15:13:36.105','Read timed out','eJyVUstuwjAQ/BefAdmENJRbEvNwVaACq5eqB8gLVOIgZ5FoEf/etU2ktioqtRRn17vjGY/2RKJD
Pa0LMjiRcL+fpNpEI7dvt6n7K6FqUMJmc9jYqsnIUkwiwR8YOeNqEVndiIuHsxBx4QUXbT9QgzDk
pEu7jFHqNVf7jM4pHpjl3ZEWwU6e5a55v0rqDqV9/FiH9rEa64zD5Zo2o23qSeYPmDdALDLxKjmU
mQKnD6qRiGsodZyC1Lk5HOvGBKvod0G4/F4QeA2fLK8wtshsPc/lscY6w2wJsCuFskw2noK143Ex
JsYIp0MebcvLiTyV4NwbqlRWuF1T9N0ivOAv5UgmFOjozcoIS+vI82p3yIxUL+hRiqaaBybveCL4
wkDijS4iYzwfRhKLfA3Gq7OLwgL+MS/2tTdgvs6KwzhGi04SaBp/wILA79/b+Xo12yddStWw
',NULL,NULL,NULL,585,2240),
	 (588,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000037','ERROR-CIHUB','2021-10-03 15:15:21.497','Read timed out','eJyVUk1vwjAM/S85F5RQqjJubVMg04AJol2mHaAtHxpNUWokNsR/n5NQiU1DY1Gb2LGf/fLkE4kP
9bhek/6JRPv9KNfGGrh9u83dqYSqQQnrTWFjo8YjczGKBX9k5IzLI7K6E5ekkwhx0QUXbz+RgzDN
SYd2GKPUb0oHjE4pXpjlh8QjmMmLlUveL7K6TWkPf9amPYwmuuBwKdNitEV9yYI+fh0k6RFeZYey
UOD4QTUQSQ2lTnKQemUuh7oRwTL6nRCuoBuGftNPljc6emSynK7ksca48eYAu1Io28naY7ByPM2G
xAjheMijTXk9kecSnHqpymWF2y1G3yXCAn8xx2ZCgY7fLY2otIq8LHaHwlD1wy6lKKp5YPaBN4LP
DCTZ6HVshOdpLDHIl2C0OjsrWsM/5sW+9g7M9aw4jOto0VkGTeIPWBgGvQc7X29m+wJSfdWq
',NULL,NULL,NULL,587,2262),
	 (592,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000040','ERROR-CIHUB','2021-10-03 15:21:03.406','Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','eJyVUstuwjAQ/BefAa0JUSi3JObhqkAFVi9VD5CEh0oclCwSLeLfu7aJRKuiUktxdr07nvFoTyw6
VONqzXonFu73o7Q00cDt223q/lrqCrW02RQ3tmoyNpejSIpHzs60GkwVd+Li/iQkXHjBRdtP0iAN
OWtDm3MAr77a5zAFOjCrA6zBqFNkK9e8XyRVC6BLH29Bl6pxmQm8XNPk0ARPcb/X5j3wGDGJIjnk
mUanD4uBjCvMyzhFVa7M4bCsTbCKfhdEy+8EgVfzqfwGY4NNltOVOlZU55TNEXe51JbJxmO0djzN
hswY4XSoo215PbHnHJ17fZ2qgrZbir5bRBf8pZzIpMYyercywtw68rLYHTIj1Qs6AGSqeWDyQSdS
zAwk3pTryBgv+pGiolii8ersonCN/5gX+9o7MNez4jCO0aKTBOvGH7Ag8LsPdr7ezPYFM+fVmA==
',NULL,NULL,NULL,591,225),
	 (601,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000048','ERROR-CIHUB','2021-10-03 15:44:24.851','Read timed out','eJyVUstuwjAQ/BefAdkkUSi3JA7gqkAFVi9VD5CEh0oc5CwSLeLfu7aJRKuiUktxdr07nvFoTyQ+
1ON6TfonEu33o1ybaOD27TZ3fyVUDUrYbAobWzUZmYtRLPgjI2dcLSKrO3FJOokQF11w8fYTNQhD
Trq0yxilXnN1wOiU4oFZfo+0CHbyYuWa94us7lDaw491qKkmuuBwuabNaJt6kgV93+93fYJMvMoO
ZaHA6YNqIJIaSp3kIPXKHA51Y4JV9LsgXIEfhl7DJ8sbjC0yWU5X8lhjnWE2B9iVQlkmG4/B2vE0
GxJjhNMhj7bl9USeS3DupSqXFW63FH23CC/4SzmSCQU6frcyotI68rLYHQoj1Qt9StFU88DsA08E
nxlIstHr2BjP01hikS/BeHV2UbSGf8yLfe0dmOtZcRjHaNFZBk3jD1gYBr0HO19vZvsCauXVuA==
',NULL,NULL,NULL,600,2214),
	 (609,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000052','TIMEOUT-CIHUB','2021-10-03 15:58:43.738',NULL,'eJyVUstuwjAQ/BefAdmEKCm3JA7gqkAFVi9VD5CEh0oc5CwSLeLfu7aJRKuiUktx1t6dnfFoTyQ+
1ON6TfonEu33o1ybaOD27TZ3fyVUDUrY0xQ2NmtOZC5GseCPjJxxtYis7sQl6SRCXHTBxdtP1CAM
OenSLmOUek1rn9EpxQuz/C5pEazkxcoV7xdZ3aE0xI91aIjZRBccLm3ajLapJ5nf98N+zyPIxKvs
UBYKnD6oBiKpodRJDlKvzOVQNyZYRb8LMlJ6QeA1fLK8wdgik+V0JY815hme5gC7UijLZOMxWDue
ZkNijHA65NGWvJ7IcwnOvVTlssLtlqLvFmGDv5QjmVCg43crIyqtIy+L3aEwUr2gRymaah6YfeCN
4DMDSTZ6HRvjeRpLTPIlGK/OLorW8I95sa+9A3M9Kw7jGC06y6Ap/AELAj98sPP1ZrYvb7LVug==
',NULL,NULL,NULL,608,2230),
	 (611,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000053','TIMEOUT-CIHUB','2021-10-03 16:00:11.681',NULL,'eJyVUsluwjAQ/RefAxoTaCi3JGZxVaACq5eqB0jCohIHJYNEi/j3jm0itVVRqaU4s72Z56c5sehQ
jas1651YuN+P0tJYA3dvt6n7a6kr1NJ6U9zYrPHYXI4iKR44O9PxmCpuxMX9SUi48IKLth/EQZrh
rAUtzgH8unWHwxQoYE7HZx6jSpGtXPF+kVRNgC59vAldysZlJvDSpsGhAb7idz2AHieSHhNFcsgz
jY4fFgMZV5iXcYqqXJngsKxFsIx+J2SotIPAr+ep/MpEj02W05U6VpQ33hxxl0ttJ1l7jFaOx9mQ
GSEcD3W0JS8n9pSjU6+vU1XQdY3Rd4mowV/MaZjUWEZvlkaYW0WeF7tDZqj6QRuARDUPTN4pIsXM
QOJNuY6M8KIfKUqKJRqtzs4K1/iPfbGvvQHzdVccxk206CTBuvAHLAg63Xu7X6/m+gQ2h9Wa
',NULL,NULL,NULL,610,2245),
	 (613,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000054','TIMEOUT-CIHUB','2021-10-03 16:00:46.478',NULL,'eJyVUstuwjAQ/BefAa0JEMotiXm4KlCB1UvVAyThoRIHJYtEi/j3rm0i0aqo1FKctXdnZzzaEwsP
5bhcs96JBfv9KClMNHD7dpu4v5a6RC3taYobmzUnNpejUIpHzs60akzld+Ki/iQgXHDBhdtP0iAN
OWtCk3MAr2rd5jAFujCr3WI1RpUiXbni/SIuGwBd+ngDupSNilTgpU2dQx08xTs9gF6rw4hJ5PEh
SzU6fZgPZFRiVkQJqmJlLodFZYJV9LsgK8X3vYpPZTcYa2yynK7UsaQ8p9MccZdJbZlsPEZrx9Ns
yIwRToc62pLXE3vO0LnX14nKabul6LtF1OAv5UQmNRbhu5URZNaRl8XukBqpnt8CIFPNA+MPupFi
ZiDRpliHxnjRDxUlxRKNV2cXBWv8x7zY196BuZ4Vh3GMFh3HWBX+gPl+u/tg5+vNbF9WLtWs
',NULL,NULL,NULL,612,2235),
	 (615,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000055','TIMEOUT-CIHUB','2021-10-03 16:02:04.305',NULL,'eJyVUstuwjAQ/BefAa0JaSi3JObhqkAFVi9VD5CEh0oclCwSLeLfu7aJ1FZFpZbirL07O+PRnlh0
qMbVmvVOLNzvR2lpooHbt9vU/bXUFWppT1Pc2Kw5sbkcRVI8cHam1WCquBEX9ych4cILLtp+kAZp
yFkb2pwDeHVrn8MU6MIs32cNRpUiW7ni/SKpWgBd+ngLupSNy0zgpU2TQxM8xe960O5BhxGTKJJD
nml0+rAYyLjCvIxTVOXKXA7L2gSr6HdBRkonCLyaT+VXGBtsspyu1LGiPKfTHHGXS22ZbDxGa8fj
bMiMEU6HOtqSlxN7ytG519epKmi7pui7RdTgL+VEJjWW0ZuVEebWkefF7pAZqV7QASBTzQOTd7qR
YmYg8aZcR8Z40Y8UJcUSjVdnF4Vr/Me82NfegPk6Kw7jGC06SbAu/AELAr97b+fr1WyfS1vVpg==
',NULL,NULL,NULL,614,2226);
INSERT INTO public.account_enquiry (id,account_no,amount,intr_ref_id,orign_bank,recpt_bank,bizmsgid,call_status,cihub_req_time,error_message,full_request_msg,full_response_msg,resp_bizmsgid,response_status,chnl_trx_id,cihub_elapsed_time) VALUES
	 (617,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000056','TIMEOUT-CIHUB','2021-10-03 16:02:46.215',NULL,'eJyVUstuwjAQ/BefAdkEEsotiXm4KlCB1UvVAyQBohIHOYtEi/j3rm0i0aqo1FKctXdnZzzaE4kO
1aTakP6JhPv9ONUmGro9z1P3V0JVoIQ9zWBrs+ZEFmIcCf7IyBlXg8jyTlw8mIaICy+4KP9EDcKQ
kzZtM0apV7fuMjqjeGFW1ycNgpU8W7vi/TKpWpT28GMt2sNsrDMOlzZNRpvUk8zv03a/4xNk4mVy
KDIFTh+UQxFXUOg4BanX5nKkaxOsot8FGSmdIPBqPlncYGyQ6Wq2lscK8wxPC4BdIZRlsvEErB1P
8xExRjgd8mhLXk/kuQDn3kClssTtlqLvFmGDv5QjmVCgo3crIyysIy/L3SEzUr2gQymaah6YfOCN
4HMDibd6Exnj+SCSmOQrMF6dXRRu4B/zYl97B+Z6VhzGMVp0kkBd+AMWBN3eg52vN7N9AWPw1bQ=
',NULL,NULL,NULL,616,2238),
	 (619,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000057','TIMEOUT-CIHUB','2021-10-03 16:04:02.995',NULL,'eJyVUstuwjAQ/BefAa0JNJRbEvNwVUgFVi9VD5CEh0oclCwSLeLfu7aJ1FZFpZbirL07O+PRnlh4
qCbVmvVPLNjvx2lpoqHbt9vU/bXUFWppTzFubNac2FyOQykeODvTajBV3IiLBtOAcMEFF24/SIM0
5KwNbc4BvLp1l0MMdGFW12cNRpUiW7ni/SKpWgA9+ngLepSNykzgpU2TQxM8xe/60OlDmxGTKJJD
nml0+rAYyqjCvIxSVOXKXI7K2gSr6HdBRkrH972aT+VXGBtsuoxX6lhRntNpjrjLpbZMNp6gteNx
NmLGCKdDHW3Jy4k95ejcG+hUFbRdU/TdImrwl3IikxrL8M3KCHLryPNid8iMVM/vAJCp5oHJO91I
MTOQaFOuQ2O8GISKkmKJxquzi4I1/mNe7GtvwHydFYdxjBadJFgX/oD5frd3b+fr1WyfUgnVqg==
',NULL,NULL,NULL,618,2218),
	 (624,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000061','ERROR-CIHUB','2021-10-03 16:22:15.558',NULL,'eJyVUstuwjAQ/BefAdkJIZRbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEolVRqaU4690dz3i0JxIf
qnG1Jr0Tifb7UaZNNHD7dpu5vxKqAiXsaQobWzUnMhejWPBHRs64GkSWd+KS/iRCXHTBxdtP1CAM
OfGoxxilfn11wOiUYsKsDiMNgp08X7nm/SKtWpR28WMt2sVqonMOl2uajDapL1mn53k9FhBk4mV6
KHIFTh+UA5FUUOgkA6lXJjnUtQlW0e+CcAXtMPRrPlncYGyQyXK6kscK60b7HGBXCGWZbDwGa8fT
bEiMEU6HPNqW1xN5LsC511eZLHG7pei7RXjBX8qRTCjQ8buVERXWkZfF7pAbqX7YphRNNQ9MPzAj
+MxAko1ex8Z43o8lFvkSjFdnF0Vr+Me82NfegbmeFYdxjBadplA3/oCFYdB9sPP1ZrYvT6TVqA==
',NULL,NULL,NULL,623,208),
	 (628,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000064','TIMEOUT-CIHUB','2021-10-03 16:26:01.696',NULL,'eJyVUk1vwjAM/S85F5RQoIxb2xTINGCCaJdpB2jLh0ZTlBqJDfHf5yRUYtPQmKWmduxnvzz5RKJD
Na7WpH8i4X4/yrTxBu7cbjP3V0JVoISNprCxWRORuRhFgj8yckbziCzvxMXJJERceMFF20/kIMxw
0qItxij169YdRqcUL4x128QjWMnzlSveL9KqSWkPP9akPczGOudwadNgtEF9ybr9VrdPkaRHeJke
ilyB4wflQMQVFDrOQOqVuRzqWgTL6HdCaJ12EPj1PFncmOiRyXK6kscK8yaaA+wKoewk64/ByvE0
GxIjhOMhj7bk9USeC3DqJSqTJR63GH2XCBv8xRyHCQU6erc0wsIq8rLYHXJD1Q/alKKo5oHpB94I
PjOQeKPXkRGeJ5HEJF+C0ersvHAN/9gX+9o7MNe74jBuokWnKdSFP2BB0Ok92P16M8cXViPVrA==
',NULL,NULL,NULL,627,2252),
	 (630,'77589',137400.00,'54773','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000065','TIMEOUT-CIHUB','2021-10-03 16:27:01.758',NULL,'eJyVUttuwjAM/Zc8F5RQShlvbcMl04AJor1Me4C2XDSaotRIbIh/n5NQiU1DY5aa2rGPfXLkE4kP
1bhak96JRPv9KNPGG7hzu83cXwlVgRI2msLGZk1E5mIUC/7IyBnNI7K8E5f0JxHiogsu3n4iB2GG
kxZtMUapX7cOGJ1SvDDWCYhHsJLnK1e8X6RVk9IufqxJu5hNdM7h0qbBaIP6knV6rbBHkaRHeJke
ilyB4wflQCQVFDrJQOqVuRzqWgTL6HdCaEE7DP16nixuTPTIZDldyWOFeRPNAXaFUHaS9cdg5Xia
DYkRwvGQR1vyeiLPBTj1+iqTJR63GH2XCBv8xRyHCQU6frc0osIq8rLYHXJD1Q/blKKo5oHpB94I
PjOQZKPXsRGe92OJSb4Eo9XZedEa/rEv9rV3YK53xWHcRItOU6gLf8DCMOg+2P16M8cXXQTVsA==
',NULL,NULL,NULL,629,2211),
	 (632,'77589',137400.00,'54563','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000066','TIMEOUT-CIHUB','2021-10-03 16:27:08.658',NULL,'eJyVUstuwjAQ/BefAdmEJJRbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEolVRqaU4u94dz3i0JxIf
qnG1Jr0Tifb7UaZNNHD7dpu5vxKqAiVsNoWNrZqMzMUoFvyRkTOuBpHlnbikP4kQF11w8fYTNQhD
Ttq0zRilXn21z+iU4oFZQUAaBDt5vnLN+0VatSjt4sdatIvVROccLtc0GW1ST7Kg1w57WEUmXqaH
Ilfg9EE5EEkFhU4ykHplDoe6NsEq+l0QLr/jB17NJ4sbjA0yWU5X8lhhnWE2B9gVQlkmG4/B2vE0
GxJjhNMhj7bl9USeC3Du9VUmS9xuKfpuEV7wl3IkEwp0/G5lRIV15GWxO+RGqhd2KEVTzQPTDzwR
fGYgyUavY2M878cSi3wJxquzi6I1/GNe7GvvwFzPisM4RotOU6gbf8DC0O8+2Pl6M9sXcTfVug==
',NULL,NULL,NULL,631,2015),
	 (634,'77589',137400.00,'5453','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000067','ERROR-CIHUB','2021-10-03 16:27:17.855',NULL,'eJyVUstuwjAQ/BefAdmEEMotiQO4KlCB1UvVAyThoRIHOYtEi/j3rm0i0aqo1FKcXe+OZzzaE4kO
1bhak/6JhPv9KNMmGrh9u83cXwlVgRI2m8LGVk1G5mIUCf7IyBlXg8jyTlycTELEhRdctP1EDcKQ
kzZtM0apV1/tMzqleGBWNyANgp08X7nm/SKtWpT28GMt2sNqrHMOl2uajDapJ1m33w76LCDIxMv0
UOQKnD4oByKuoNBxBlKvzOFQ1yZYRb8LMsvv+F7NJ4sbjA0yWU5X8lhhnWE2B9gVQlkmG4/B2vE0
GxJjhNMhj7bl9USeC3DuJSqTJW63FH23CC/4SzmSCQU6ercywsI68rLYHXIj1Qs6lKKp5oHpB54I
PjOQeKPXkTGeJ5HEIl+C8ersonAN/5gX+9o7MNez4jCO0aLTFOrGH7Ag8HsPdr7ezPYFZK7VsA==
',NULL,NULL,NULL,633,6),
	 (636,'77589',137400.00,'55453','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000068','TIMEOUT-CIHUB','2021-10-03 16:28:04.639',NULL,'eJyVUstuwjAQ/BefAdmEQMotiQO4KlCB1UvVAyThoRIHOYtEi/j3rm0i0aqo1FKcXe+OZzzaE4kO
1bhak/6JhPv9KNMmGrh9u83cXwlVgRI2m8LGVk1G5mIUCf7IyBlXg8jyTlycTELEhRdctP1EDcKQ
kzZtM0apV1/tMzqleGBWNyANgp08X7nm/SKtWpQG+LEWNdVY5xwu1zQZbVJPsm6/HfRphyATL9ND
kStw+qAciLiCQscZSL0yh0Ndm2AV/S4Il+93fK/mk8UNxgaZLKcreaywzjCbA+wKoSyTjcdg7Xia
DYkxwumQR9vyeiLPBTj3EpXJErdbir5bhBf8pRzJhAIdvVsZYWEdeVnsDrmR6vU6lKKp5oHpB54I
PjOQeKPXkTGeJ5HEIl+C8ersonAN/5gX+9o7MNez4jCO0aLTFOrGH7Bezw8e7Hy9me0LaqPVtg==
',NULL,NULL,NULL,635,7001),
	 (638,'177589',137400.00,'55453','SIHBIDJ1','CENAIDJA','20211003SIHBIDJ1510O0100000069','SUCCESS','2021-10-03 16:29:58.301',NULL,'eJyVUstuwjAQ/BefAdmEQOCWxDxcFVKB1UvVAyThoRIHOYtEi/j3rm0itVVRqaU4u94dz3i0ZxId
q2m1IYMzCQ+HSaZNNHL7bpe5vxKqAiVslsDWVk1GFmISCf7AyAVXg8jyTlw8nIWIC6+4aPeBGoQh
J23aZoxSr77aZzSheGBWt08aBDt5vnbNh2VatSgN8GMtGmA11jmH6zVNRpvUk6w7aPcHfkCQiZfp
scgVOH1QjkRcQaHjDKRem8Oxrk2win4XhMv3O75X88niBmODzFbJWp4qrDPMFgD7QijLZOMpWDse
52NijHA65Mm2vJzJUwHOvaHKZInbLUXfLcIL/lKOZEKBjt6sjLCwjjwv98fcSPV6HUrRVPPA9B1P
BJ8bSLzVm8gYz4eRxCJfgfHq4qJwA/+YF/vaOzBfZ8VhHKNFpynUjT9grNfzg74dsFezfQJk2NX9
','eJyNUttugkAQ/ZVmn9XsgnjhDRettKkS2D41PhAulgQWAmurNf57ZwdpbNMmHcJez8w5M7Nnsji0
T+2e2Gfi1PU6afRq1Y15nnSz9GSrpIe7rXrFW70jfLlxPPfBIRewARHVP/1Cb70AP3b1W+QfoMHT
5MSgBmOUmn1oi9GAGhSMGZZJBgSQbpp14DqK2xGlBvxsxCjc8iZ11TXMkNEhNQWb2MbctmYEmNwq
PpSpVJ0+Va08v1ShaoMaj+6bvgSo53c5qMWcsp5NlH/ziaMnM0cmQEHsF6hCs5fFUiaiguEbQV8S
INjSjoRO5sCBLhDnDzCYZY2xMuKINMThgsNWp9VCCzJkhqXOy29UcwLMM/hB9Xdf8YM00/c8UZi/
r0562ujcwkObF9UdvBQVlTnRHdMwJ45V39Ef/WXTqTWbI1LUt7Tc4Zxgz8O6KOHMjVSE+pbyrahv
FWhHQnWVMeSj8IfwDNjYhNcAh0GbSNXliyDxLlEsNTGxyw4+sE+199NH
','20211003CENAIDJA510R0200001253','ACTC',637,463);INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (4215,1000200.00,'Internet Banking','FARIS AI','8289331','Fandi Ahmad','7100004','Timeout menunggu response dari CIHUB','Credit Transfer','014','2021-09-24 21:43:57.07041','2021-09-24 21:43:59.218936','TIMEOUT-CIHUB','15502'),
	 (4172,54500.00,'Internet Banking',NULL,'1814449',NULL,NULL,NULL,'Account Enquiry','008','2021-09-24 20:11:08.394747','2021-09-24 20:11:09.388125','SUCCESS','4511168'),
	 (4217,1000200.00,'Internet Banking','FARIS AI','8289331','Fandi Ahmad','7110004',NULL,'Credit Transfer','014','2021-09-24 21:47:26.780557','2021-09-24 21:47:29.593947','SUCCESS','15530'),
	 (4337,438417.00,'Internet Banking','FARHAN AI','921751','Andrea Suo','7744404','Account Enquiry reject.','Credit Transfer','014','2021-09-26 16:25:49.114656','2021-09-26 16:25:50.132874','REJECT-CIHUB','15551'),
	 (4174,8650000.00,'Internet Banking','P AI','11844292','Fandi Wijaya','782444',NULL,'Credit Transfer','014','2021-09-24 20:12:55.068922','2021-09-24 20:12:56.296226','SUCCESS','21211'),
	 (4229,4222000.00,'Internet Banking','FARIS AI','8211131','Bambang Suo','7155504',NULL,'Credit Transfer','014','2021-09-24 22:05:37.514305','2021-09-24 22:05:40.95248','SUCCESS','12550'),
	 (4274,1390000.00,'Internet Banking',NULL,'677589',NULL,NULL,NULL,'Account Enquiry','014','2021-09-25 19:36:12.697836','2021-09-25 19:36:13.280701','SUCCESS','41449'),
	 (4179,781000.00,'Over the Counter',NULL,NULL,NULL,NULL,'rekening tidak aktif','FI Credit Transfer','008','2021-09-24 20:16:32.176756','2021-09-24 20:16:32.187235','REJECT-CB','962001'),
	 (4276,422260.00,'Internet Banking','FARHAN AI','825331','Bambang Suo','7155604',NULL,'Credit Transfer','014','2021-09-25 19:39:26.250063','2021-09-25 19:39:31.01464','SUCCESS','11004'),
	 (4181,89994500.00,'Internet Banking','P AI','110044292','Fandi Wijaya','785544',NULL,'Credit Transfer','014','2021-09-24 20:27:12.676883','2021-09-24 20:27:14.435353','SUCCESS','211411');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (4341,1545500.00,'Internet Banking',NULL,'1814449',NULL,NULL,NULL,'Account Enquiry','008','2021-09-26 21:32:25.290132','2021-09-26 21:32:26.629601','SUCCESS','41468'),
	 (4282,467760.00,'Internet Banking','FARHAN AI','182531','Bambang Suo','7785604',NULL,'Credit Transfer','014','2021-09-25 19:43:19.562176','2021-09-25 19:43:20.705859','SUCCESS','15804'),
	 (4186,8904000.00,'Internet Banking','FARIS AI','8000292','Fandi Wijaya','7850004','Timeout waiting CIHUB response','Credit Transfer','014','2021-09-24 20:34:10.874679','2021-09-24 20:34:15.684737','TIMEOUT-CIHUB','210071'),
	 (4287,711660.00,'Over the Counter',NULL,NULL,NULL,NULL,NULL,'FI Credit Transfer','008','2021-09-25 19:44:04.420082','2021-09-25 19:44:05.056608','SUCCESS','6011'),
	 (4257,781000.00,'Over the Counter',NULL,NULL,NULL,NULL,NULL,'FI Credit Transfer','008','2021-09-25 06:45:34.736657','2021-09-25 06:45:35.38268','SUCCESS','6241'),
	 (4192,8904000.00,'Internet Banking','FARIS AI','2000292','Fandi Wijaya','7850004',NULL,'Credit Transfer','014','2021-09-24 21:05:53.365443','2021-09-24 21:05:54.52164','SUCCESS','21861'),
	 (4261,781000.00,'Over the Counter',NULL,NULL,NULL,NULL,NULL,'FI Credit Transfer','008','2021-09-25 06:50:53.660159','2021-09-25 06:50:54.94655','SUCCESS','6000'),
	 (4197,8904000.00,'Internet Banking','FARIS AI','2111132','Fandi Wijaya','7154004',NULL,'Credit Transfer','014','2021-09-24 21:14:38.837862','2021-09-24 21:14:40.623406','SUCCESS','21862'),
	 (4202,800000.00,'Internet Banking','FARIS AI','2100323','Fandi Wijaya','7154004',NULL,'Credit Transfer','014','2021-09-24 21:28:28.581211','2021-09-24 21:28:30.827266','SUCCESS','21002'),
	 (4270,1990000.00,'Internet Banking',NULL,'7712589',NULL,NULL,'Timeout menunggu response dari CIHUB','Account Enquiry','014','2021-09-25 19:34:30.664236','2021-09-25 19:34:33.04725','TIMEOUT-CIHUB','46149');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (4168,1990000.00,'Internet Banking',NULL,'7712589',NULL,NULL,'Timeout menunggu response dari CIHUB','Account Enquiry','014','2021-09-24 20:03:35.1136','2021-09-24 20:03:37.204071','TIMEOUT-CIHUB','46149'),
	 (4207,1800200.00,'Internet Banking','FARIS AI','9234323','Fandi Ahmad','7100004','Account Enquiry reject.','Credit Transfer','014','2021-09-24 21:32:48.951207','2021-09-24 21:32:49.50314','REJECT-CIHUB','21002'),
	 (4170,54500.00,'Internet Banking',NULL,'1819199',NULL,NULL,NULL,'Account Enquiry','008','2021-09-24 20:08:40.30641','2021-09-24 20:08:41.145749','SUCCESS','452168'),
	 (4314,137400.00,'Internet Banking',NULL,'677589',NULL,NULL,NULL,'Account Enquiry','014','2021-09-26 10:11:20.807327','2021-09-26 10:11:21.832654','SUCCESS','47169'),
	 (4316,492220.00,'Internet Banking','FARHAN AI','18831','Bambang Suo','7785604',NULL,'Credit Transfer','014','2021-09-26 10:12:41.41432','2021-09-26 10:12:42.721917','SUCCESS','15521'),
	 (4321,4000557.00,'Internet Banking','FARHAN AI','18831','Bambang Suo','7785604',NULL,'Credit Transfer','014','2021-09-26 10:21:24.315449','2021-09-26 10:21:26.011274','SUCCESS','150051'),
	 (4326,4355557.00,'Internet Banking','FARHAN AI','14551','Bambang Suo','7744404',NULL,'Credit Transfer','014','2021-09-26 16:19:30.78414','2021-09-26 16:19:33.392218','SUCCESS','155221'),
	 (4331,4355557.00,'Internet Banking','FARHAN AI','821751','Bambang Suo','7744404',NULL,'Credit Transfer','014','2021-09-26 16:21:37.471574','2021-09-26 16:21:40.686754','SUCCESS','155221'),
	 (407,438417.00,'Internet Banking','FARHAN AI','874551','Andrea Suo','7744404','komi-mock: Temporary failure in name resolution','Credit Transfer','014','2021-10-01 15:51:38.352834','2021-10-01 15:51:38.423668','ERROR-CIHUB','147845'),
	 (1,1545500.00,'Internet Banking',NULL,'1814449',NULL,NULL,'Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','Account Enquiry','008','2021-09-29 14:08:16.100441','2021-09-29 14:08:16.360669','ERROR-CIHUB','40118');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (37,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-30 22:03:49.710404','2021-09-30 22:03:50.799211','SUCCESS','55140'),
	 (3,137400.00,'Internet Banking',NULL,'677589',NULL,NULL,'Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','Account Enquiry','014','2021-09-29 14:08:35.903716','2021-09-29 14:08:35.9298','ERROR-CIHUB','47169'),
	 (5,137400.00,'Internet Banking',NULL,'677589',NULL,NULL,NULL,'Account Enquiry','014','2021-09-29 14:09:11.377577','2021-09-29 14:09:13.161799','SUCCESS','47169'),
	 (45,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-30 23:36:36.124373','2021-09-30 23:36:37.065951','SUCCESS','5540'),
	 (7,1545500.00,'Internet Banking',NULL,'1814449',NULL,NULL,NULL,'Account Enquiry','008','2021-09-29 14:09:46.568211','2021-09-29 14:09:47.259131','SUCCESS','40118'),
	 (16,438417.00,'Internet Banking','FARHAN AI','871551','Andrea Suo','7744404',NULL,'Credit Transfer','014','2021-09-30 10:53:09.293951','2021-09-30 10:53:14.619186','SUCCESS','14145'),
	 (22,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-30 20:54:19.518163',NULL,NULL,'55140'),
	 (23,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-30 20:57:16.25369',NULL,NULL,'55140'),
	 (24,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-30 21:17:35.890569',NULL,NULL,'55140'),
	 (39,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-30 22:04:05.45655','2021-09-30 22:04:05.812993','SUCCESS','5540');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (26,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-30 21:29:06.544674','2021-09-30 21:29:07.402369','SUCCESS','55140'),
	 (29,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-30 21:56:18.366121',NULL,NULL,'55140'),
	 (31,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-30 21:58:52.952993',NULL,NULL,'55140'),
	 (33,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-30 22:01:15.811017',NULL,NULL,'55140'),
	 (35,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-30 22:02:27.723387',NULL,NULL,'55140'),
	 (42,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-30 23:28:36.233486','2021-09-30 23:28:37.111833','SUCCESS','5540'),
	 (179,438417.00,'Internet Banking','FARHAN AI','874551','Andrea Suo','7744404','komi-mock: Temporary failure in name resolution','Credit Transfer','014','2021-10-01 15:16:46.543945','2021-10-01 15:16:47.097534','ERROR-CIHUB','147845'),
	 (409,438417.00,'Internet Banking','FARHAN AI','874551','Andrea Suo','7744404',NULL,'Credit Transfer','014','2021-10-01 22:54:35.621627','2021-10-01 22:54:39.432398','SUCCESS','147845'),
	 (440,137400.00,'Internet Banking',NULL,'677589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-02 08:54:12.19465','2021-10-02 08:54:13.636637','SUCCESS','419'),
	 (442,137400.00,'Internet Banking',NULL,'677589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-02 08:57:16.460295','2021-10-02 08:57:17.488635','SUCCESS','5669');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (444,137400.00,'Internet Banking',NULL,'677589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-02 08:58:46.420848','2021-10-02 08:58:47.451139','SUCCESS','5669'),
	 (446,137400.00,'Internet Banking',NULL,'677589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-02 08:59:32.386796','2021-10-02 08:59:33.467969','SUCCESS','5669'),
	 (448,137400.00,'Internet Banking',NULL,'677589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-02 09:01:31.923015','2021-10-02 09:01:33.035205','SUCCESS','56573'),
	 (537,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','Account Enquiry','014','2021-10-03 07:29:03.166913','2021-10-03 07:29:09.680011','ERROR-CIHUB','54773'),
	 (450,137400.00,'Internet Banking',NULL,'677589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-02 09:06:57.200397','2021-10-02 09:06:58.261774','SUCCESS','5573'),
	 (497,438417.00,'Internet Banking','FARHAN AI','174551','Andrea Suo','7744404',NULL,'Credit Transfer','014','2021-10-02 11:28:19.882226','2021-10-02 11:28:21.037962','SUCCESS','122642'),
	 (452,137400.00,'Internet Banking',NULL,'677589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-02 09:08:03.655694','2021-10-02 09:08:04.773126','SUCCESS','5573'),
	 (454,137400.00,'Internet Banking',NULL,'677589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-02 09:16:58.173942','2021-10-02 09:16:59.181883','SUCCESS','556773'),
	 (529,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','Account Enquiry','014','2021-10-03 05:55:51.311652','2021-10-03 05:55:57.660512','ERROR-CIHUB','54773'),
	 (456,137400.00,'Internet Banking',NULL,'677589',NULL,NULL,'Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','Account Enquiry','014','2021-10-02 09:17:56.677025','2021-10-02 09:17:56.788541','ERROR-CIHUB','556773');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (501,438417.00,'Internet Banking','FARHAN AI','174551','Andrea Suo','7744404',NULL,'Credit Transfer','014','2021-10-02 11:36:07.438412','2021-10-02 11:36:09.188005','SUCCESS','126642'),
	 (458,137400.00,'Internet Banking',NULL,'677589',NULL,NULL,'localhost:9006 failed to respond','Account Enquiry','014','2021-10-02 09:22:50.068152','2021-10-02 09:22:50.099107','ERROR-CIHUB','556773'),
	 (505,438417.00,'Internet Banking',NULL,'174551',NULL,'7744404',NULL,'Credit Transfer','014','2021-10-02 12:31:24.709415',NULL,NULL,'12652'),
	 (460,137400.00,'Internet Banking',NULL,'677589',NULL,NULL,'localhost:9006 failed to respond','Account Enquiry','014','2021-10-02 09:22:58.353548','2021-10-02 09:22:58.397447','ERROR-CIHUB','556773'),
	 (507,438417.00,'Internet Banking',NULL,'174551',NULL,'7744404',NULL,'Credit Transfer','014','2021-10-02 12:37:43.65864',NULL,NULL,'12652'),
	 (462,137400.00,'Internet Banking',NULL,'677589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-02 09:26:06.458024','2021-10-02 09:26:08.088483','SUCCESS','556773'),
	 (464,438417.00,'Internet Banking','FARHAN AI','174551','Andrea Suo','7744404',NULL,'Credit Transfer','014','2021-10-02 09:39:04.578328',NULL,NULL,'14545'),
	 (509,438417.00,'Internet Banking',NULL,'174551',NULL,'7744404',NULL,'Credit Transfer','014','2021-10-02 12:41:04.318335',NULL,NULL,'12652'),
	 (465,438417.00,'Internet Banking','FARHAN AI','174551','Andrea Suo','7744404',NULL,'Credit Transfer','014','2021-10-02 09:41:23.184114','2021-10-02 09:41:24.150425','SUCCESS','14545'),
	 (468,438417.00,'Internet Banking','FARHAN AI','174551','Andrea Suo','7744404',NULL,'Credit Transfer','014','2021-10-02 10:01:01.704958','2021-10-02 10:01:03.209592','SUCCESS','14422');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (471,438417.00,'Internet Banking','FARHAN AI','174551','Andrea Suo','7744404',NULL,'Credit Transfer','014','2021-10-02 10:04:30.405728','2021-10-02 10:04:31.198426','SUCCESS','1242'),
	 (511,438417.00,'Internet Banking',NULL,'174551',NULL,'7744404',NULL,'Credit Transfer','014','2021-10-02 12:44:21.496273','2021-10-02 12:44:22.650367','SUCCESS','12652'),
	 (474,438417.00,'Internet Banking','FARHAN AI','174551','Andrea Suo','7744404',NULL,'Credit Transfer','014','2021-10-02 10:07:44.601044','2021-10-02 10:07:45.509434','SUCCESS','1242'),
	 (477,438417.00,'Internet Banking','FARHAN AI','174551','Andrea Suo','7744404',NULL,'Credit Transfer','014','2021-10-02 10:10:24.439513','2021-10-02 10:10:25.383277','SUCCESS','1242'),
	 (514,438417.00,'Internet Banking',NULL,'174551',NULL,'7744404',NULL,'Credit Transfer','014','2021-10-02 05:54:53.944319','2021-10-02 05:54:56.022891','SUCCESS','156652'),
	 (480,438417.00,'Internet Banking','FARHAN AI','174551','Andrea Suo','7744404',NULL,'Credit Transfer','014','2021-10-02 10:13:12.606991','2021-10-02 10:13:13.593278','SUCCESS','1242'),
	 (483,438417.00,'Internet Banking','FARHAN AI','174551','Andrea Suo','7744404','Timeout waiting CIHUB response','Credit Transfer','014','2021-10-02 10:27:27.433768','2021-10-02 10:27:28.043673','TIMEOUT-CIHUB','1242'),
	 (517,137400.00,'Internet Banking',NULL,'677589',NULL,NULL,'komi-mock: Temporary failure in name resolution','Account Enquiry','014','2021-10-02 13:21:50.01629','2021-10-02 13:21:50.81204','ERROR-CIHUB','556773'),
	 (487,438417.00,'Internet Banking','FARHAN AI','174551','Andrea Suo','7744404','Timeout waiting CIHUB response','Credit Transfer','014','2021-10-02 10:27:32.588504','2021-10-02 10:27:32.743143','TIMEOUT-CIHUB','1242'),
	 (491,438417.00,'Internet Banking','FARHAN AI','174551','Andrea Suo','7744404',NULL,'Credit Transfer','014','2021-10-02 10:47:02.748922','2021-10-02 10:47:04.215187','SUCCESS','1242');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (519,137400.00,'Internet Banking',NULL,'677589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-02 13:22:40.289482','2021-10-02 13:22:42.292951','SUCCESS','556773'),
	 (521,137400.00,'Internet Banking',NULL,'677589',NULL,NULL,'Unrecognized token ''bifast'': was expecting (JSON String, Number, Array, Object or token ''null'', ''true'' or ''false'')
 at [Source: (ByteArrayInputStream); line: 1, column: 8]','Account Enquiry','014','2021-10-03 05:33:41.108231','2021-10-03 05:33:42.263178','ERROR-CIHUB','54773'),
	 (531,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','Account Enquiry','014','2021-10-03 06:00:21.814887','2021-10-03 06:00:28.178537','ERROR-CIHUB','54773'),
	 (523,137400.00,'Internet Banking',NULL,'677589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 05:45:58.416194','2021-10-03 05:45:58.684096','SUCCESS','54773'),
	 (525,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','Account Enquiry','014','2021-10-03 05:46:31.889021','2021-10-03 05:46:37.928784','ERROR-CIHUB','54773'),
	 (545,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','Account Enquiry','014','2021-10-03 07:43:28.642186','2021-10-03 07:43:35.118809','TIMEOUT-CIHUB','54773'),
	 (527,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','Account Enquiry','014','2021-10-03 05:50:08.651997','2021-10-03 05:50:14.976101','ERROR-CIHUB','54773'),
	 (533,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','Account Enquiry','014','2021-10-03 07:19:59.598181','2021-10-03 07:20:06.073361','ERROR-CIHUB','54773'),
	 (539,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','Account Enquiry','014','2021-10-03 07:32:47.205583','2021-10-03 07:32:53.606545','ERROR-CIHUB','54773'),
	 (535,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','Account Enquiry','014','2021-10-03 07:26:08.302797','2021-10-03 07:26:14.641542','ERROR-CIHUB','54773');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (543,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','Account Enquiry','014','2021-10-03 07:35:54.07225','2021-10-03 07:36:00.504361','ERROR-CIHUB','54773'),
	 (541,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','Account Enquiry','014','2021-10-03 07:33:42.386292','2021-10-03 07:33:48.748565','ERROR-CIHUB','54773'),
	 (547,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','Account Enquiry','014','2021-10-03 08:13:37.854758','2021-10-03 08:13:44.240467','TIMEOUT-CIHUB','54773'),
	 (549,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','Account Enquiry','014','2021-10-03 08:15:10.545392','2021-10-03 08:15:16.872512','TIMEOUT-CIHUB','54773'),
	 (551,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','Account Enquiry','014','2021-10-03 08:15:55.038971','2021-10-03 08:16:01.419799','TIMEOUT-CIHUB','54773'),
	 (553,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','Account Enquiry','014','2021-10-03 11:08:21.193098','2021-10-03 11:08:27.549026','ERROR-CIHUB','54773'),
	 (587,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'Read timed out','Account Enquiry','014','2021-10-03 15:15:21.454337','2021-10-03 15:15:23.85232','ERROR-CIHUB','54773'),
	 (555,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','Account Enquiry','014','2021-10-03 11:11:01.4723','2021-10-03 11:11:07.813223','ERROR-CIHUB','54773'),
	 (557,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 11:31:01.10061',NULL,NULL,'54773'),
	 (589,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 15:17:46.350359',NULL,NULL,'54773');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (558,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','Account Enquiry','014','2021-10-03 11:31:46.827838','2021-10-03 11:31:53.287374','ERROR-CIHUB','54773'),
	 (560,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 11:36:15.934564',NULL,NULL,'54773'),
	 (590,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 15:19:19.340405',NULL,NULL,'54773'),
	 (561,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','Account Enquiry','014','2021-10-03 11:40:06.94006','2021-10-03 11:40:07.247951','ERROR-CIHUB','54773'),
	 (563,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 11:43:00.225889',NULL,NULL,'54773'),
	 (564,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','Account Enquiry','014','2021-10-03 11:45:08.367359','2021-10-03 11:45:15.551058','ERROR-CIHUB','54773'),
	 (627,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 16:26:01.582058','2021-10-03 16:26:03.990317','TIMEOUT-CIHUB','54773'),
	 (566,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'HTTP operation failed invoking http://localhost:9006/mock/cihub with statusCode: 504','Account Enquiry','014','2021-10-03 11:54:09.883248','2021-10-03 11:54:16.371822','ERROR-CIHUB','54773'),
	 (591,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','Account Enquiry','014','2021-10-03 15:21:03.289559','2021-10-03 15:21:03.647077','ERROR-CIHUB','54773'),
	 (568,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','Account Enquiry','014','2021-10-03 11:54:28.399435','2021-10-03 11:54:28.454106','ERROR-CIHUB','54773');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (593,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 15:21:42.393883',NULL,NULL,'54773'),
	 (570,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','Account Enquiry','014','2021-10-03 12:12:51.489745','2021-10-03 12:12:51.850759','ERROR-CIHUB','54773'),
	 (594,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 15:24:28.657349',NULL,NULL,'54773'),
	 (572,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','Account Enquiry','014','2021-10-03 12:59:56.186005','2021-10-03 12:59:56.570762','ERROR-CIHUB','54773'),
	 (574,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 14:04:57.589961',NULL,NULL,'54773'),
	 (595,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 15:25:53.752766',NULL,NULL,'54773'),
	 (575,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','Account Enquiry','014','2021-10-03 14:21:53.030106','2021-10-03 14:21:53.307517','ERROR-CIHUB','54773'),
	 (596,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 15:27:13.704864',NULL,NULL,'54773'),
	 (577,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','Account Enquiry','014','2021-10-03 14:23:08.789835','2021-10-03 14:23:09.103562','ERROR-CIHUB','54773'),
	 (579,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 15:04:36.008724',NULL,NULL,'54773');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (581,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 15:06:28.666291',NULL,NULL,'54773'),
	 (582,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 15:11:02.581954',NULL,NULL,'54773'),
	 (597,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 15:39:02.444399',NULL,NULL,'54773'),
	 (583,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'Read timed out','Account Enquiry','014','2021-10-03 15:12:02.823999','2021-10-03 15:12:05.272179','ERROR-CIHUB','54773'),
	 (598,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 15:42:39.924305',NULL,NULL,'54773'),
	 (585,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'Read timed out','Account Enquiry','014','2021-10-03 15:13:36.044226','2021-10-03 15:13:38.383813','ERROR-CIHUB','54773'),
	 (610,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 16:00:11.633639','2021-10-03 16:00:13.969583','TIMEOUT-CIHUB','54773'),
	 (599,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 15:43:25.514987',NULL,NULL,'54773'),
	 (600,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'Read timed out','Account Enquiry','014','2021-10-03 15:44:24.810106','2021-10-03 15:44:27.144677','ERROR-CIHUB','54773'),
	 (623,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 16:22:15.45636','2021-10-03 16:22:15.787662','ERROR-CIHUB','54773');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (602,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,'Read timed out','Account Enquiry','014','2021-10-03 15:46:31.317291','2021-10-03 15:46:33.442656','ERROR-CIHUB','54773'),
	 (612,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 16:00:46.434543','2021-10-03 16:00:48.746839','TIMEOUT-CIHUB','54773'),
	 (604,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 15:54:36.976008','2021-10-03 15:54:39.274239','TIMEOUT-CIHUB','54773'),
	 (606,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 15:56:56.085906','2021-10-03 15:56:58.430005','TIMEOUT-CIHUB','54773'),
	 (625,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 16:22:42.565609',NULL,NULL,'54773'),
	 (608,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 15:58:43.680878','2021-10-03 15:58:46.053801','TIMEOUT-CIHUB','54773'),
	 (614,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 16:02:04.262209','2021-10-03 16:02:06.57245','TIMEOUT-CIHUB','54773'),
	 (626,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 16:23:38.700496',NULL,NULL,'54773'),
	 (616,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 16:02:46.172652','2021-10-03 16:02:48.480816','TIMEOUT-CIHUB','54773'),
	 (618,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 16:04:02.954593','2021-10-03 16:04:05.299276','TIMEOUT-CIHUB','54773');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (620,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 16:05:03.914808',NULL,NULL,'54773'),
	 (621,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 16:11:09.72565',NULL,NULL,'54773'),
	 (622,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 16:18:41.620486',NULL,NULL,'54773'),
	 (629,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 16:27:01.709332','2021-10-03 16:27:03.998657','TIMEOUT-CIHUB','54773'),
	 (631,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 16:27:08.643361','2021-10-03 16:27:10.743592','TIMEOUT-CIHUB','54563'),
	 (633,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 16:27:17.844283','2021-10-03 16:27:17.868116','ERROR-CIHUB','5453'),
	 (635,137400.00,'Internet Banking',NULL,'77589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 16:28:04.576125','2021-10-03 16:28:11.656498','TIMEOUT-CIHUB','55453'),
	 (637,137400.00,'Internet Banking',NULL,'177589',NULL,NULL,NULL,'Account Enquiry','014','2021-10-03 16:29:58.260384','2021-10-03 16:29:58.789095','SUCCESS','55453');INSERT INTO public.corebank_transaction (transaction_id,addt_info,channel_ref_id,credit_amount,creditor_bank,cstm_account_name,cstm_account_no,cstm_account_type,debit_amount,debtor_bank,status,transaction_type,trns_dt,chnl_trx_id) VALUES
	 (4160,'Info tambahan disini','21211',NULL,NULL,'Fandi Wijaya','782444','CACC',8650000.00,NULL,'SUCCESS','Debit','2021-09-24 08:41:44.056383',4158),
	 (4176,'Info tambahan disini','21211',NULL,NULL,'Fandi Wijaya','782444','CACC',8650000.00,NULL,'SUCCESS','Debit','2021-09-24 20:12:55.663967',4174),
	 (4180,'rekening tidak aktif','962001',NULL,NULL,NULL,NULL,NULL,781000.00,NULL,'FAILED','FI Transfer','2021-09-24 20:16:32.18385',4179),
	 (4183,'Info tambahan disini','211411',NULL,NULL,'Fandi Wijaya','785544','CACC',89994500.00,NULL,'SUCCESS','Debit','2021-09-24 20:27:13.813803',4181),
	 (4188,'Info tambahan disini','210071',NULL,NULL,'Fandi Wijaya','7850004','CACC',8904000.00,NULL,'SUCCESS','Debit','2021-09-24 20:34:11.440369',4186),
	 (4194,'Info tambahan disini','21861',NULL,NULL,'Fandi Wijaya','7850004','CACC',8904000.00,NULL,'SUCCESS','Debit','2021-09-24 21:05:53.94185',4192),
	 (4199,'Info tambahan disini','21862',NULL,NULL,'Fandi Wijaya','7154004','CACC',8904000.00,NULL,'SUCCESS','Debit','2021-09-24 21:14:40.041811',4197),
	 (4204,'Info tambahan disini','21002',NULL,NULL,'Fandi Wijaya','7154004','CACC',800000.00,NULL,'SUCCESS','Debit','2021-09-24 21:28:30.19042',4202),
	 (4219,'Info tambahan disini','15530',NULL,NULL,'Fandi Ahmad','7110004','CACC',1000200.00,NULL,'SUCCESS','Debit','2021-09-24 21:47:27.35598',4217),
	 (4231,'Info tambahan disini','12550',NULL,NULL,'Bambang Suo','7155504','CACC',4222000.00,NULL,'SUCCESS','Debit','2021-09-24 22:05:38.661493',4229);
INSERT INTO public.corebank_transaction (transaction_id,addt_info,channel_ref_id,credit_amount,creditor_bank,cstm_account_name,cstm_account_no,cstm_account_type,debit_amount,debtor_bank,status,transaction_type,trns_dt,chnl_trx_id) VALUES
	 (4258,'Info tambahan disini','6241',NULL,NULL,NULL,NULL,NULL,781000.00,NULL,'SUCCESS','FI Transfer','2021-09-25 06:45:34.751895',4257),
	 (4262,'Info tambahan disini','6000',NULL,NULL,NULL,NULL,NULL,781000.00,NULL,'SUCCESS','FI Transfer','2021-09-25 06:50:53.750503',4261),
	 (4278,'Info tambahan disini','11004',NULL,NULL,'Bambang Suo','7155604','CACC',422260.00,NULL,'SUCCESS','Debit','2021-09-25 19:39:27.516226',4276),
	 (4284,'Info tambahan disini','15804',NULL,NULL,'Bambang Suo','7785604','CACC',467760.00,NULL,'SUCCESS','Debit','2021-09-25 19:43:20.127085',4282),
	 (4288,'Info tambahan disini','6011',NULL,NULL,NULL,NULL,NULL,711660.00,NULL,'SUCCESS','FI Transfer','2021-09-25 19:44:04.42843',4287),
	 (4318,'Info tambahan disini','15521',NULL,NULL,'Bambang Suo','7785604','CACC',492220.00,NULL,'SUCCESS','Debit','2021-09-26 10:12:42.017785',4316),
	 (4323,'Info tambahan disini','150051',NULL,NULL,'Bambang Suo','7785604','CACC',4000557.00,NULL,'SUCCESS','Debit','2021-09-26 10:21:25.405083',4321),
	 (4328,'Info tambahan disini','155221',NULL,NULL,'Bambang Suo','7744404','CACC',4355557.00,NULL,'SUCCESS','Debit','2021-09-26 16:19:32.76808',4326),
	 (4333,'Info tambahan disini','155221',NULL,NULL,'Bambang Suo','7744404','CACC',4355557.00,NULL,'SUCCESS','Debit','2021-09-26 16:21:38.139169',4331),
	 (18,'Info tambahan disini','14145',NULL,NULL,'Andrea Suo','7744404','CACC',438417.00,NULL,'SUCCESS','Debit','2021-09-30 10:53:11.657931',16);
INSERT INTO public.corebank_transaction (transaction_id,addt_info,channel_ref_id,credit_amount,creditor_bank,cstm_account_name,cstm_account_no,cstm_account_type,debit_amount,debtor_bank,status,transaction_type,trns_dt,chnl_trx_id) VALUES
	 (411,'Info tambahan disini','147845',NULL,NULL,'Andrea Suo','7744404','CACC',438417.00,NULL,'SUCCESS','Debit','2021-10-01 22:54:36.877096',409),
	 (466,'Info tambahan disini','14545',NULL,NULL,'Andrea Suo','7744404','CACC',438417.00,NULL,'SUCCESS','Debit','2021-10-02 09:41:23.219924',465),
	 (469,'Info tambahan disini','14422',NULL,NULL,'Andrea Suo','7744404','CACC',438417.00,NULL,'SUCCESS','Debit','2021-10-02 10:01:01.744266',468),
	 (472,'Info tambahan disini','1242',NULL,NULL,'Andrea Suo','7744404','CACC',438417.00,NULL,'SUCCESS','Debit','2021-10-02 10:04:30.41279',471),
	 (475,'Info tambahan disini','1242',NULL,NULL,'Andrea Suo','7744404','CACC',438417.00,NULL,'SUCCESS','Debit','2021-10-02 10:07:44.660293',474),
	 (478,'Info tambahan disini','1242',NULL,NULL,'Andrea Suo','7744404','CACC',438417.00,NULL,'SUCCESS','Debit','2021-10-02 10:10:24.501471',477),
	 (481,'Info tambahan disini','1242',NULL,NULL,'Andrea Suo','7744404','CACC',438417.00,NULL,'SUCCESS','Debit','2021-10-02 10:13:12.669157',480),
	 (484,'Info tambahan disini','1242',NULL,NULL,'Andrea Suo','7744404','CACC',438417.00,NULL,'SUCCESS','Debit','2021-10-02 10:27:27.447031',483),
	 (488,'Info tambahan disini','1242',NULL,NULL,'Andrea Suo','7744404','CACC',438417.00,NULL,'SUCCESS','Debit','2021-10-02 10:27:32.648425',487),
	 (492,'Info tambahan disini','1242',NULL,NULL,'Andrea Suo','7744404','CACC',438417.00,NULL,'SUCCESS','Debit','2021-10-02 10:47:02.809919',491);
INSERT INTO public.corebank_transaction (transaction_id,addt_info,channel_ref_id,credit_amount,creditor_bank,cstm_account_name,cstm_account_no,cstm_account_type,debit_amount,debtor_bank,status,transaction_type,trns_dt,chnl_trx_id) VALUES
	 (498,'Info tambahan disini','122642',NULL,NULL,'Andrea Suo','7744404','CACC',438417.00,NULL,'SUCCESS','Debit','2021-10-02 11:28:19.898439',497),
	 (502,'Info tambahan disini','126642',NULL,NULL,'Andrea Suo','7744404','CACC',438417.00,NULL,'SUCCESS','Debit','2021-10-02 11:36:07.472167',501),
	 (506,'Info tambahan disini','12652',NULL,NULL,NULL,'7744404','CACC',438417.00,NULL,'SUCCESS','Debit','2021-10-02 12:31:24.74356',505),
	 (508,'Info tambahan disini','12652',NULL,NULL,NULL,'7744404','CACC',438417.00,NULL,'SUCCESS','Debit','2021-10-02 12:37:43.693221',507),
	 (510,'Info tambahan disini','12652',NULL,NULL,NULL,'7744404','CACC',438417.00,NULL,'SUCCESS','Debit','2021-10-02 12:41:04.327918',509),
	 (512,'Info tambahan disini','12652',NULL,NULL,NULL,'7744404','CACC',438417.00,NULL,'SUCCESS','Debit','2021-10-02 12:44:21.513191',511),
	 (515,'Info tambahan disini','156652',NULL,NULL,NULL,'7744404','CACC',438417.00,NULL,'SUCCESS','Debit','2021-10-02 05:54:54.009785',514);INSERT INTO public.credit_transfer (id,settlconf_bizmsgid,amount,call_status,cihub_req_time,cihub_resp_time,crdttrn_req_bizmsgid,crdttrn_resp_bizmsgid,cre_dt,creditor_acct_no,creditor_acct_type,creditor_id,creditor_type,debtor_acct_no,debtor_acct_type,debtor_id,debtor_type,full_request_msg,full_response_msg,intr_ref_id,msg_type,orign_bank,recpt_bank,resp_status,reversal,chnl_trx_id,cihub_elapsed_time) VALUES
	 (4221,NULL,1000200.00,'TIMEOUT-CIHUB','2021-09-24 09:47:27','2021-09-24 09:47:29','20210924SIHBIDJ1010O0100000091',NULL,'2021-09-24 21:47:27.375','8289331','SVGS','C22403','02','7110004','CACC','014145','01','eJyVUstO6zAQ/RXkdUG2G9THLo+2GEFbNRYbxCIkbVqRpJEzRUDVf2c8SbgFtbpgyYnteZxzZmbP
vF11X6VsuGduWd4kxp7G9XezSep/oYoKCkW3GazJam8sVDeeCm4FO+DqML39ZZw/mroY5zZx3uYD
OSgLziSXgg+k06bmgs9w0xoI1mHoGSxXtXMZxdUV533c4or30eqbZQBNmks+uJSOlmLo9IayxxAp
2Ma7fFlAzQ+2Y+VXkBs/AW1W9nFi2iIQo9OEGjayxdP5GcQOmz7PVvqtQrvlHgJkuSoIic73QOW4
W0yYLUTNQ7+Ry+OezXOoqzcqEr3FzzlG30uECf7HHMEwuS4bMndxZntlcnuZGzDvGM+FdfMhfZ/v
TPnTgiZVgPFeSIibU00fomy3tGIRRXKOfbE1im2MChaUbm1Sz/YuGHkajcEzULmntobjqEg2F+46
jxI01dLn5hWOR+ixmSEuHOFcs8MTjZBN48YxtCP2Y+B6wjJyiLX+JsV3fZ99ZUjhDxNP/fpFzPG0
1zH/BLsLFV646kvtzKSnxPpSOrzbiiXY82L7sj/odsUJseHDJKTnsMxyfAoiiAhkVLxm5Nm2w8a1
A5AcP0mr4omIHD4Bm/466A==
','','15530','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL,4217,NULL),
	 (4280,NULL,422260.00,'TIMEOUT-CIHUB','2021-09-25 07:39:27','2021-09-25 07:39:29','20210925SIHBIDJ1010O0100000027',NULL,'2021-09-25 19:39:27.536','825331','SVGS','C22403','02','7155604','CACC','014145','01','eJyVUttu4jAQ/ZXKz7SyTdICb7lw8aoFRCxeqj6EBFK0uclxqraIf+94kiBagbZryYntuZ1zZg7E
raunKiGjA3HKchYrc5o03/0+bv65yCudC7wt9CtazY0EYuYK/w8jR1g9IotfxnnjuQNxThvn7j8B
gzDFCaec0SG3u9SU0QVsXPyB9Ah4+ttd41yGUXVH6QA2u6MDsHpq6+s2zS0d3nJbsuGoPxxBLFTy
i6jOtrlu8OliIrxKZ8qLtVQ78zhVnQiI6DKgFs2pnsyuVOyR+Waxk+8V2BncAq3TTORYCc9PGuV4
XE2JEaLBId/R5flAlplu1BvnsSzgcw3Rd4kgwb+QQzFILssWzGOUml6pzFyWSqsPiKfMuHk6+VjW
qvxpAZPItXL/IhEnQ03XYVpvwcHinN9TaIuRKDIhwl9htleVuKZ1/tiVYPQ3GtWeGwndMNuEeXIT
1AWYGuZL9abPJ+i5HSHKLGbZ5PiCE2TSOFGkuwn7MW8PzLbvqYWg5TcmnuN55JQh0f8x8NiuX8Sc
D3sTcyI8cVYzZ37jiBPdhUousfU4t2i/Y4t1r7MdcLvfZxfIButpgM9BmWbw5Ic6xBrj/C1Fz64d
Jq7rf3z+xA2LF8Rx/AIt5jsH
',NULL,'11004','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL,4276,NULL),
	 (4286,NULL,467760.00,'SUCCESS','2021-09-25 07:43:20','2021-09-25 07:43:20','20210925SIHBIDJ1010O0100000032','20210925CENAIDJA010R0200000696','2021-09-25 19:43:20.147','182531','SVGS','C22403','02','7785604','CACC','64145','01','eJyVUtlu4jAU/ZXKz7SynYTtLQuLRy0gYvWl6kNIIEWTTY6p2kH8+1zfJIhWoOlYcmL7buece4/E
O9RPdUrGR+JW1TxR5jRtvvt90vwLUdS6EHhb6je0mhsJxdwTwS9GTrB6RJY/jPMnCxfi3DbO2/8B
DMIUJ5xyRkfc6VJTRpewcVmc9Ah4Bttd41xFcf1A6RA2e6BDsPpqG+g2zT0d3XNHstHYtsacEqgU
lPEh3xa6wafLqfBrnSs/0VLtzONMdSIgouuAWjRWV0/mNyr2yGKz3MmPGuwMbqHWWS4KrITnJ41y
PK5nxAjR4JAf6PJyJKtcN+pNikSW8LmF6KtEkOBfyKEYJJdVC+YxzkyvVG4uK6XVJ8RTZtx8nX6u
Dqr6bgGTKLTyfiMRN0dNn6PssAUHuz8Y9Cm0xUgUmxARrDHbm0o907pg4kkwBhuNai+MhF6Ub6Ii
vQsPJZga5iv1ri8n6KUdob7NbIecXnGATBY3jnU3YN/GbTAYOn1qI2b5hYjv+j45Z0j1f8w7dusH
MZez3sSc+U7d9dxd3LnizHap0mtkfc5tanVsse5ttmzIHYtdIRs+z0J8Dqssh6cg0hHWmBTvGXp2
3TBxXfuTyyduWLwijtNf1IM6yQ==
','eJyNUttugkAQ/ZVmntXMLmrVNwSstKkS2D41PhBBayIrgfXSGv+9swuYptG0Q9jL7Mw5J2f3DON9
+VquYXQGO8+nSaFXk2rcbJJqlr4slfTNbq4+zKnegePNbN99tuFC0QKx+2df5E/H1MfqvvHmizT4
mhw4coZD3mugkWGIHHX0h31oAVW66aoqzuNl2UHk9LMOQzBQ0WGppYnQi4L5LPIo6xSpq2rwNg7b
vCcYH3WtEUcgfne33GepVJVqtZv4QaYiVYa5ST0VjTFG5W2ROhjDx4ZNZPf55sVabgnVlytbJkQE
o/dzlf3N0DhVMyBaFrSupbPMFNc2DIwNOIDLgu7idAPdk4nY0XCPYt7QWLxhIZy/9YiToQHbEQ5t
tXUlXf7KMNNSexcUqvikmjdqontfXPHDdKXPnUQZjwP1qaeZ9s8rjhv5EMXZPkmLGMxjifJtRkhu
rGID78nDNv8JIGgHyEiHUf4igja9n6416FIqLBOpKrGmRBylYULLqLos6KP4Bm3T4s4=
','15804','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,4282,NULL),
	 (4320,NULL,492220.00,'SUCCESS','2021-09-26 10:12:42.036','2021-09-26 10:12:42.694','20210926SIHBIDJ1010O0100000004','20210926CENAIDJA010R0200000850','2021-09-26 10:12:42.036','18831','SVGS','C224403','02','7785604','CACC','64145','01','eJyVUttO4zAQ/RXk54JsE0rat1x68Qraqol4QTykSRuqzU3OBMFW/PuOJ0lVUKtlLTmxPbdzzsyB
uU39WKdsfGBOVc0TbU7T9rvfJ+2/UEUNhaLbEl7Jam4sUHNX+b8E+8Q1YGH5wzhvsnAwzuni3P0f
xKBMcSa5FHwkh31qLvgSd7ssNmDo6W93rXMVxfUN5zZuccNttHp660OX5pqPruUwFHws5NiSDCv5
Zdzk2wJafFBOlVdDrr0EQr0zjzPdi0CIzgPq1l1fL8wvVBywxWa5C99rtAu8BQBZrgqqROdHIDke
1jNmhGhxhO/k8nxgqxxa9SZFEpb4uYToq0SY4F/IsRgmD6sOzEOcmV7p3FxWGvQHxnNh3DxIP1aN
rr5b0KQK0O5vIuLkpOlTlDVbdLBGUkqObTESxSZE+WvK9qpT17TOn7ghGv0NkNoLI6Eb5ZuoSK+C
pkRTy3yl3+B0gp67ERpawkIaLzRAJosTx9AP2Ldxu7+374aoDM3oFyKe43nsmCGF/5h36tYPYk5n
vY058p0667mzuHLUke1Sp+fIelJaFr/t6VLhy3SFbd+KM2SDp1lAz0GV5fjkRxBRjUnxlpFn3w0T
17c/OX2ShsULwfj8C4vxOrM=
','eJyNUtFugjAU/ZWlz2ouFQ36hoCTLQMC3dPiAxFwLFAIVKcz/vtui5hl0WyX0HLbe885nPZEFrv2
pd2S+YmYdb1KGvm17MY8T7qZu7wV3FWZL97VrsyI5Ximaz+Z5IwxIKz6Z1/krhbYp136FvkXanAl
OaFANZjRaQ8NGoRAQYYxATIgWGmnWVdcx5t2BEDx1Uaa3EWoaL+R0ljoRIHvRQ6uWk1qiwv4EGZD
OmUwnmt0rlOC/Ha12ZUpF51qUS3doBSRaMNaLT02vTFK5W2RKvTJtGdj5X0+v9nyAlFdnpk8QSIy
fzt1q78Zeqd6BoAJGVxLvVIVX2wwlA1gkPMaz+JwA93hCatwuEfhX2n0ngVx/tbDDoqGmBazMJXW
tXj4mWLGT+ld0IjmiDWv2IPnvr7ih2km961EKI8DcZSTJ/2LPuKsyYsHvKQiLnOibktUFyVC2bGI
Fb7D90X9E4FhRkBDIUr6MwuGeIH0sSF/KmwTLjq1qoR9ckUFYyXrvMYH4xtc9uM5
','15521','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,4316,0),
	 (4330,NULL,4355557.00,'SUCCESS','2021-09-26 16:19:32.779','2021-09-26 16:19:33.372','20210926SIHBIDJ1010O0100000010','20210926CENAIDJA010R0200000418','2021-09-26 16:19:32.778','14551','SVGS','C224403','02','7744404','CACC','64145','01','eJyVUttu4jAQ/ZXKz7SyQ4DCWy5cXLWAiNWXqg8hgRQ1NzlD1S7i33c8SRCtQNu15MT23M45Mwfm
7qunKmGjA3PKchZrc5rU390urv+5zCvIJd0W8EZWc2OBnLnSfxDsiKvDVPHLOG88dzDOaeLc3R/E
IE1xZnFL8KHVb1NzwRe4aQnOOgw9/c22di7DqLrj/B63uOP3aPX0xocmzS0f3lp9JfojMRx1LYaV
/CLaZ5scanxQTKRXQaa9GJTemsepbkUgRJcBNWhEW09lVyp22Hy92KrPCu3GOwBIM5lTJTo/Acnx
uJoyI0SNQ32Sy8uBLTOo1RvnsSrwcw3Rd4kwwb+QYzFMrsoGzGOUml7pzFyWGvQXxnNy8yD5Wu51
+dOCJpmDdt+JiJORps9hut+gg93t4RpgX4xGkYmR/orSvenENb3zx65Co78GkntuNHTDbB3myU2w
L9BUU1/qDzgfoZdmhvq2sHvs+EoTZLI4UQTthP2Yt8HAtm1uE2j1jYnneB47ZUjgPwae2vWLmPNh
r2NOfCfOaubMbxx5YrvQySWynmUhgW5Llwpfp4vC9MQFssHzNKDnoEwzfPJDCKnGOP9IybPtholr
+x+fP1mGxSvBOP4FzAQ68g==
','eJyNUk1vgkAQ/SvNnNXsojHqDQErNVUC21PjgQhaE1gIu1it8b93dhDTNJp2CPsxO/Pey9s9w7RW
r2oHkzPYZTlPKrOaNeN+nzSz9KXS0qfdSn/QqdmB4y1t332x4YLRAVH8sy/y51Ps49e+6f4LNfiG
HCxmcTa2hi004yxkFjMx4CPoAFa66bYpLuON6jFm4c97nAFBRYeNkSZCLwpWy8jDrFOlrr6Cd9m4
aw0FG0/4eNK3APndYlPnqdSNal3M/CDXkVZhSannqjWGVN4X2cSAtWwif8y3qnYyQ1Rfbm2ZIBFM
3s9N9jdD69SNgXPo3EqXORVfbRiRDWwElzXexfEOuicTUeDwiGLV0pCX1II4f+sRR6IB2xEObo11
Ci9/S8y4NN4Fla5OWPOGTXjv6xt+mG7NuZNo8jjQJzMtjX/RKc5VnT0talXnMdBjicosRyQ31jHB
e/KQlT8BBO6AGVmkfCGCLr6fQX80wFSoEqkbsVQiPiUxsT6puqzxw/gGkmPjAQ==
','155221','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,4326,593),
	 (4335,NULL,4355557.00,'TIMEOUT-CIHUB','2021-09-26 16:21:38.164','2021-09-26 16:21:40.182','20210926SIHBIDJ1010O0100000013',NULL,'2021-09-26 16:21:38.164','821751','SVGS','C224403','02','7744404','CACC','64145','01','eJyVUtlu6jAQ/ZXKz7SyQ1jKWxYWVy0gYvWl6kNIIEXNJmeo2ov49zueJIhWoNtryYnt2c45Mwfm
7qunKmGjA3PKchZrc5rU390urv+5zCvIJd0W8EZWc2OBnLnSfxDsiKvDVPHLOG88dzDOaeLc3R/E
IE1xZnFL8Hur36bmgi9w0xJd1mHo6W+2tXMZRtUd50Pc4o4P0erpjQ9Nmlt+f2v1leiPLDHqDhlW
8oton21yqPFBMZFeBZn2YlB6ax6nuhWBEF0G1KCx23oqu1Kxw+brxVZ9VmgXeAsA0kzmVInOT0By
PK6mzAhR41Cf5PJyYMsMavXGeawK/FxD9F0iTPAv5FgMk6uyAfMYpaZXOjOXpQb9hfFcGDcPkq/l
Xpc/LWiSOWj3nYg4GWn6HKb7DTrY3R6uAfbFaBSZGOmvKN2bTlzTO3/sKjT6ayC550ZDN8zWYZ7c
BPsCTTX1pf6A8xF6aWaobwu7x46vNEEmixNF0E7Yj3kbDGzb5jaBVt+YeI7nsVOGBP5j4Kldv4g5
H/Y65sR34qxmzvzGkSe2C51cIutZFhLotnSp8HW6Q0sMeuIC2+B5GtBzUKYZPvkhhFRknH+k5Nm2
w8S1AxCfP1mGxivhOP4FGPQ7NA==
',NULL,'155221','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL,4331,2018),
	 (4190,NULL,8904000.00,'TIMEOUT-CIHUB','2021-09-24 08:34:11','2021-09-24 08:34:13','20210924SIHBIDJ1010O0100000070',NULL,'2021-09-24 20:34:11.46','8000292','SVGS','C423403','02','7850004','CACC','01445','01','eJyVUl1P6kAQ/Stmn9FsS42Ft34ArlEgdKMP5j7UFmrvbZdmOxiR8N+dnbZeNBB1k213d77OOTM7
5m/quzpjwx3zquo61eY0br55njZ/JVQNStBtBs9kNTcWiWtfhDcW2+PqMbn+YVwwmnoY57Vxfv6G
GIQpzmxuW3xgO11qbvEZblpXnPUYeobLVeNcxUl9wbmL27rgLloDvQyhTXPOB+e2I20+7DtDC0H2
WLhONuVSQYMP1mMR1FDqIAWpV+ZxojsRCNFxQC0aq6snyxMVe2z6NFvJ1xrt5hYBFKVQVInOd0By
3C4mzAjR4JCv5PK4Y/MSGvVGKpVr/JxC9FkiTPAdciyGyWXVgrlNCtMrXZrLXIPeYjwntwCy7Xyj
q68WNAkF2v9HRLySNL2Pi80SHdwBd7AQ9sVolJgYES4o3bPOfNO7cORLNIZPQHJPjYbjWKX52UP+
N97GaGu4z/ULHM7QYztE3HKcS7b/QyNk0nhJAt2IfRm4K/cS4TiEWn6iEnhBwD4yZPCLiad+/SDm
cNqbmP+EvYWIzjzxQXams2NcA8fuO7zfsaW6p9m6yNUe2EfYRveTiJ6jqijxKYwhpioj9VKQZ9cP
E9dNQHr4ZNIiDANk/w4W3jti
','','210071','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL,4186,NULL),
	 (4325,NULL,4000557.00,'SUCCESS','2021-09-26 10:21:25.421','2021-09-26 10:21:25.982','20210926SIHBIDJ1010O0100000007','20210926CENAIDJA010R0200000069','2021-09-26 10:21:25.421','18831','SVGS','C224403','02','7785604','CACC','64145','01','eJyVUtlO6zAQ/RXk54LskC70LUsXI2irxuIF8ZAmbajIJmeC4Fb8+x1PkqqgVpdryYnt2c45Mwfm
1tVjlbDxgTllOY+1OU2b734fN/9c5hXkkm5LeCWrubFAzl3p3wv2havHVPHLOG+ycDDOaePc/R/E
IE1xZnFL8Dtr0KXmgi9xN2vIegw9/e2ucS7DqLrhfIRb3PARWj299aFNc83vrq2BEnxsibHVZ1jJ
L6I62+bQ4INiKr0KMu3FoPTOPM50JwIhOg+oXcd6KrtQsccWm+VOfVRoF3gLANJM5lSJzo9Acjys
Z8wI0eBQH+TyfGCrDBr1JnmsCvxcQvRdIkzwL+RYDJOrsgXzEKWmVzozl5UG/YnxXBg3D5LPVa3L
nxY0yRy0+0ZEnIw0fQrTeosONhbp94fYF6NRZGKkv6Z0rzpxTe/8iavQ6G+A5F4YDd0w24R5chXU
BZoa6iv9Dqcj9NzO0MAWNvb0hSbIZHGiCLoJ+zFvw+GoP+A2gVbfmHiO57FjhgT+Y+CpXb+IOR32
JubId+qs587iypFHtkudnCPrWZZt89uOLhW+TFeMRrfiDNngaRbQc1CmGT75IYRUY5K/p+TZdcPE
df2PT58sw+KFYHz9Bb2jOvM=
','eJyNUl1vgjAU/StLn9W09WPCGwJOtgwIdE+LD0bAkUghUJ3O+N93exGzLJrtEvpxe+85J6c9kdmu
eW02xDwRq6oWSa1X83bM86SdpScbJT3cBeoDT/WO2K5vec6zRc4QPSLKf/bF3mIGfezSN8u/QIOn
yQmnnFGDTzpoymhEOcWYGKRHoNJJs7a4Wq2bAaUcfjZglCBUvF9raSJy4zDwYxeydp066gLep0af
TwQdmpyZfEyA3ynXuyKVqlWtyrkXFipWTVRh6qnujEGVt0VijJnRsYniPl9Qb+QWUD2ZWTIBImK+
n9rsb4bOqY6B0inpXUv9AosvNkzRBig4L+EuDjfQXZmIEoZ7FMGV5rFjAZy/9YgD0hDLFjZstXUN
XH6GzLDU3oW1qo9Q8wY9cO/LK36UZvrcThR6HKqjnnztH+iv0wcryaudqkuCjyWutgUgOSu1QnhX
7rfVTwABO0IZ6EDlLyLsw/sZDacjSEVNIlUrFkvEp0QmOkRV5yV8EN98GuLY
','150051','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,4321,561),
	 (4233,NULL,4222000.00,'TIMEOUT-CIHUB','2021-09-24 10:05:38','2021-09-24 10:05:40','20210924SIHBIDJ1010O0100000101',NULL,'2021-09-24 22:05:38.679','8211131','SVGS','C22403','02','7155504','CACC','014145','01','eJyVUttu4jAQ/ZXKz7SyTaJS3nIB6qoFRKy+VH0ICaRoEydynKpdxL/veJIgWoHateTE9tzOOTN7
4jf1U52R8Z54VXWfanuatt/dLm3/SqjaKIG3hXlDq72RSNz7Inxg5ABrQGT5y7hgMvcgzuvi/N1f
wCBsccIpZ/SOO31qyugCtl1wJgMCnuFm2zpXcVLfUDqCzW7oCKyB3oSmS3NN7665IzkfU3c8HBGo
FJZJU2yUafGZciqC2hQ6SI3UW/s4070IiOg8oA4N7+vJ4kLFAZmvF1v5UYPdYo+MyQuhsBKenwzK
8biaEStEi0N+oMvLniwL06o3Uaks4XMJ0VeJIMFPyKEYJJdVB+YxyW2vdGEvS230J8RDLovJZJ/L
RlffLWASymj/DxLxCtT0Oc6bDTg4nHOoBH2xGiU2RoQrTPemM9/2Lpz4Eozh2qDcc6uhHxfrWGVX
UVOCqaW+1O/mdIReuhmizGGOSw6vOEI2jZckph+xbwN3y1zXpQ6ill+oBF4QkGOGzPzHxGO/fhFz
Ou1tzJHw1FuJ6MoTR7YLnZ0jG3Du0GFPFsteJjvijLEhO0M2ep5F+BxVeQFPYWxiLDJR7zl69u2w
cf0ApKdP3LJ4RSCHf4C2Oug=
',NULL,'12550','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL,4229,NULL),
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
','21211','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,4174,NULL);
INSERT INTO public.credit_transfer (id,settlconf_bizmsgid,amount,call_status,cihub_req_time,cihub_resp_time,crdttrn_req_bizmsgid,crdttrn_resp_bizmsgid,cre_dt,creditor_acct_no,creditor_acct_type,creditor_id,creditor_type,debtor_acct_no,debtor_acct_type,debtor_id,debtor_type,full_request_msg,full_response_msg,intr_ref_id,msg_type,orign_bank,recpt_bank,resp_status,reversal,chnl_trx_id,cihub_elapsed_time) VALUES
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
','211411','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,4181,NULL),
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
','21861','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,4192,NULL),
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
','21862','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,4197,NULL),
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
','21002','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,4202,NULL),
	 (20,NULL,438417.00,'TIMEOUT-CIHUB','2021-09-30 10:53:11.675','2021-09-30 10:53:13.695','20210930SIHBIDJ1010O0100000002',NULL,'2021-09-30 10:53:11.675','871551','SVGS','C224403','02','7744404','CACC','64145','01','eJyVUttO4zAQ/RXk54LsJKWlb07SixG0VWPxgngISRqqzU2Og2Cr/vuOJ0lVUCtYS05sz+2cM7Mn
blM/1imZ7AmvqkWszGnWfne7uP0Xoqh1IfC20m9oNTcSiIUr/HtGDrAGRJa/jPOmSw5xvItzd38B
gzDFiUUtRu9s2qemjK5gt8siAwKefrJtnaswqm8oHcNmN3QMVk8lvu7SXNO7a5tKRidDe8IA5ID4
ZdTkSaFbfLqcCa/WufJiLdXWPM5VLwIiOg+oW3ZfT+YXKg7I8nW1lR812M0t0DrLRYGV8PyoUY6H
zZwYIVoc8gNdnvdknetWvWkRyxI+lxB9lQgS/IQcikFyWXVgHqLM9Erl5rJWWn1CPEXJPJ1+rhtV
fbeASRRauX+QCM9R06cwaxJwcOyxw0bQFiNRZEKEv8Fsbyp1Tev8qSvB6L9qVHtpJORFrJLwKmhK
sLTE1+pdnw7QczdBtw5zhuTwgvNjkvAo0v18fZu20chxHOogZPmFh8c9jxwzpPo/xh2b9YuY01Fv
Y450Z3yz4MsrLo5sVyo9R9azLCBg93Sx8GW64xEbDtkZtsHTPMDnoMpyePJDHWKRafGeoWffDRPX
tz8+fbIMjRfEcfgHA5g6aQ==
',NULL,'14145','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL,16,2020),
	 (415,NULL,438417.00,'TIMEOUT-CIHUB','2021-10-01 22:54:36.89','2021-10-01 22:54:38.902','20211001SIHBIDJ1010O0100000004',NULL,'2021-10-01 22:54:36.89','874551','SVGS','C224403','02','7744404','CACC','64145','01','eJyVUttO4zAQ/RXk54Ls1KVV35ykFyNoq8biBfEQkjRU5CZngmAr/n3HTlIV1GpZS05sz+2cM3Mg
blM/1CmZHoioqmWszWnefvf7uP0XsqihkPa2hldrNTcSyKUr/TtGvnANiCp/GefNVgLjRBfn7v8g
BmmKE4c6jFHK+tSU0TXudnEyIOjpJ7vWuQqj+obSCW52Qydo9XTiQ5fmmtFrypTjTEd8OrwlWMkv
oyZPCmjxQTmXXg259mJQemceF7oXwSI6D6hbo76eyi9UHJDVy3qnPmq0M7wFAFkuC1vJnh/AynG/
XRAjRItDfViXpwPZ5NCqNytiVeLnEqLvEmGCfyHHYphcVR2Y+ygzvdK5uWw06E+Mp8y4eZB+bhpd
/bSgSRag3TdLRORW08cwaxJ04MMJZ2Nsi5EoMiHS39psrzp1Tev8mavQ6L+AVXtlJBRFrJPwKmhK
tLTEN/odTgfoqZugW844sni282OSiCiCfr5+TNt4zDlHYeyIfuPhCc8jxwwp/Me422b9IuZ01NuY
I9252C7F6krII9u1Ts+R9RwHCQx7urbwZbqTMR+N2Bm2weMisM9BleX45IcQ2iKz4j2znn03TFzf
/vj0yTE0ni2Or7+s3zpO
',NULL,'147845','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL,409,2012),
	 (416,NULL,50234.26,NULL,'2021-10-01 16:11:57.103','2021-10-01 16:11:57.468','20210915FASTIDJA010ORB9131111','20211001SIHBIDJ1010R9950000001','2021-10-01 16:11:57.469465','987654321','SVGS','0102030400708','01','123456789','CACC','8881234777','01','eJy9Vl1zojAUfe+vyPBsOwloxb7x0Sqdig5kuw+dPlBEZVYCE2K33Q7/fQNoEQjW3WnrKBPuzce5
557c69sZAJK+TafpSgJX4I2/coOWJJMFrQzcdFN7zQ1huKibCiOxSMqI1XJx54ytadvMHeVsybi2
Ncu81aSaPzsTjfejrLcHiOMvBzi1Tef/AerhH86yVfIqyVBGcIQGN5qL8z0hgjNHHyEF8Y/0voiv
MIPlflHi+ekFhCr/oQuoVtMMGpjsfd9zpJxDBSP1CkL+LeHugEhm7G+jgLB6dkMW31hGyiJqLBim
ywaXY9oUxB7djppmOHksqoL6SLl8R1khxVGFFXKsCKPRDmttsv00W+KXtJiM6i6XsU1kkaUgjYVr
ynaiunPGkjhD1X5SGTR+2W34UA9zHjGhXq7JAsf80cFBldJ+g4Vcri8nMHeIu9dAhBNx9Hf+Jlc4
jYRKnlNGX4tzIWrIuAHQYKvX+ZYmH20jH7kNNcwWYVT/VSRHi5gA+b232QbFpgMoK/0LuUWa4ZeH
WqbTzc3BOYd3gusMjXAhspbOjDVd6eUVM691XHeaT0xQFSS71LBmmxbQndlPuwlWJJmCumehmgrn
vgI9CJxVHVJVFXGChsOh1JqVgceGLTsxP3mcmu+LMtMRSle9rJDmMAeXQ3XUBJod1R7+SHWGZhin
6q6IayUK61gzOCk8yzZn7YZwOuW87HwVNHEz/Tdo3aq/nU1s4E4tPPk+1fNyKkMF9iEc8tb3icIv
svDJwh+pw8tBX5HRJwvfvR+7pwrf4W1L2CJ+8PawKAmX5t5r/m8AmEHq0zBhYUxATAGJWZD2wDYB
LAaoD4G/9qjns4CmICSArQOwCQmv1o+dx7vJJuKwTY95ouTy7vm8EYcrrrgHBOXNq9d2OumCMJel
R6bg32QnYS4l2EpOc0XHNfgOKN1JPuC8Gpcz8md2lv0FkThGIg==
','eJyNUt1vgjAQ/1eWPqu5g+mENxAdNREMdE+LD4uAM5NCoCY6s/991+LHHly2prn2eh+/u9/1xPx9
u2g3zD0xr67DrNG3WSe326w7JZetktxosXo3Vq2xlIc+D+bIvmj1mKj+GTeZRh7Feec4f/tJNXAN
ziywEAHwkhoQEscZglnIeow8g7zonOu3dQtgDcgyQCDjpMkDdc7SR+gDChy5iO7wiRFQUK33ZS5V
V56qZnxZqlS1SW2enpsLA6ac+9WcS7EuaKL8HS9uNnJHWbksPJkREHNfT93rTwRwcDjzUqEpiRPf
Gdv4iPaI9a6uUWmcdb/U7Ng0DGP2tSLSD3eyT2UmKhJ3IagJjYKEcgOhNH+XIw4GhSXziSBVM9fS
kAsDTFdN3bJRzZF8XgBsmu/qmj/JC/MBpGr8j1SpXXkbFdh9dASAa7amNlNmEEt11EekSZ7HYfSQ
LrgIzb9ZafENVkrJVQ==
',NULL,'Credit Transfer','CENAIDJA','MNDRIDJA','RJCT','',NULL,365),
	 (439,NULL,50234.26,NULL,'2021-10-02 00:39:27.824','2021-10-02 00:39:28.465','20210915FASTIDJA010ORB9131111','20211002SIHBIDJ1010R9950000001','2021-10-02 00:39:28.467349','987654321','SVGS','0102030400708','01','123456789','CACC','8881234777','01','eJy9Vl1zojAUfe+vyPBsOwloxb7x0Sqdig5kuw+dPlBEZVYCE2K33Q7/fQNoEQjW3WnrKBPuzce5
557c69sZAJK+TafpSgJX4I2/coOWJJMFrQzcdFN7zQ1huKibCiOxSMqI1XJx54ytadvMHeVsybi2
Ncu81aSaPzsTjfejrLcHiOMvBzi1Tef/AerhH86yVfIqyVBGcIQGN5qL8z0hgjNHHyEF8Y/0voiv
MIPlflHi+ekFhCr/oQuoVtMMGpjsfd9zpJxDBSP1CkL+LeHugEhm7G+jgLB6dkMW31hGyiJqLBim
ywaXY9oUxB7djppmOHksqoL6SLl8R1khxVGFFXKsCKPRDmttsv00W+KXtJiM6i6XsU1kkaUgjYVr
ynaiunPGkjhD1X5SGTR+2W34UA9zHjGhXq7JAsf80cFBldJ+g4Vcri8nMHeIu9dAhBNx9Hf+Jlc4
jYRKnlNGX4tzIWrIuAHQYKvX+ZYmH20jH7kNNcwWYVT/VSRHi5gA+b232QbFpgMoK/0LuUWa4ZeH
WqbTzc3BOYd3gusMjXAhspbOjDVd6eUVM691XHeaT0xQFSS71LBmmxbQndlPuwlWJJmCumehmgrn
vgI9CJxVHVJVFXGChsOh1JqVgceGLTsxP3mcmu+LMtMRSle9rJDmMAeXQ3XUBJod1R7+SHWGZhin
6q6IayUK61gzOCk8yzZn7YZwOuW87HwVNHEz/Tdo3aq/nU1s4E4tPPk+1fNyKkMF9iEc8tb3icIv
svDJwh+pw8tBX5HRJwvfvR+7pwrf4W1L2CJ+8PawKAmX5t5r/m8AmEHq0zBhYUxATAGJWZD2wDYB
LAaoD4G/9qjns4CmICSArQOwCQmv1o+dx7vJJuKwTY95ouTy7vm8EYcrrrgHBOXNq9d2OumCMJel
R6bg32QnYS4l2EpOc0XHNfgOKN1JPuC8Gpcz8md2lv0FkThGIg==
','eJyNkk1vgkAQhv9Ks2c1s1Ab4QaiZU0FA9tT46ERsKayEFgbrel/78ziRw827Ybs5+z7zL7Dkfm7
dt6umXtkXl2HWUOzaddvNlk3KqFarYRZxfrNnNKKpSL0RTDj7Atbj8nqn/fGk8jDe97pnr/5xBwE
wZkFFucA1lkaOCSOMwTTOOsxjAzyoguuX1ctxg7wZMCBGaX0Y0WEp4mX4Ma4yQN9ku1z6IMlAVzb
ca0RQ3JQrXZlrnSXr66mYlHqVLdJbbYem7MlJr/b6Z1ys840Wf7Oi5u12qKqUIWnMgQx9+XY7f4k
gMOHUy+V5FGc+M7I5vfcfmC9S2hUmmAyAF8/Mg4AIpZYhf0N9YnKZIXdTQQ+gigcKVcIyvydjtwb
CktmY4lLcq7FqhcGjFOybtHo5oAxzwA2Fnx50U/ywvwRSjf+e6r1tryWCuw+d8g685G1mTaFWOgD
DRGZPIvD6C6dCxmaH2lJ3TeaXs4B
',NULL,'Credit Transfer','CENAIDJA','MNDRIDJA','RJCT','',NULL,641),
	 (467,NULL,438417.00,'SUCCESS','2021-10-02 09:41:23.268','2021-10-02 09:41:24.126','20211002SIHBIDJ1010O0100000013','20211002CENAIDJA010R0200000484','2021-10-02 09:41:23.258','174551','SVGS','C224403','02','7744404','CACC','64145','01','eJyVU1tvmzAU/iuVn9PIBrJkeRqYXJjaJApWX6o+cDFJNjDMHKp2Uf77bANROiVah8TF5/Z95zuH
I/Ka+rHeoekRuVW1TKX+mrfPwyFt3yIQNYjAnNawN159QmGw9AL/O0EndQ0QKz+ZR2crV+W5XZ53
+K04BBocWdgiBGOrL40JXqvbXMRGA6QifZ61wVWU1EOMJ+omQzxRXiq5D12Ze4LvscXw16lDppaN
FJJfJk3BBbT8oJwHtIZC0hSYzLRxIXsRDKPrhDo2To/HihuIA7SK1xl7q5WfqFMIkBeBMEjm+xGM
HA/bBdJCtDzYmwl5PqJNAa16M5GyUj1uMfookSrwL+YKTBVnVUfmIcn1rGShDxsJ8l3lY6LDKOze
N42sLj3xIYtqGJYNxGUj0mEieXoAkJGoMy6HdC9yakysM235r4bXsCl/lN8sbsfZhNu6vKofCJDe
T6OGW5jBPEV5wxWKY08cMlaz1TonGjfwt4bSXu48PX9/5jHl9GMwI1vpObgilTy6C5tSeVr1NvIV
LrfwuVvDLw5xRuj0YpZQF3GTBPol/Wtlx2PHcbBjKLMPYlCXUnSusIP/+GfMxD+Rc/m/tDnndufu
dumu7tzg3O1a7q41Sy1LNWD37Rrg2+2SsTMakSvdhk+L0JjDKi+UyY8gMiAz8ZqbyH4aOq/fofTS
ZOk2XgyP0x9PWlTi
','eJyNUl1vgjAU/SvLfZ6mBZIZ3hBwsg8l2GUPiw9EwJFIIVCdzvjfd3uxy7JothJa7u295xxOe4Tx
tnvu1uAewWuaadbqr0k/l2XWrzKSnZIRRXP1Trs6Aj+ceVHw4MEJxy2I+p99i2g6xj5+7huXn6gh
0uRgMYtzxiwDzThLmMX0cEYO3AJWBnnRFzfpqhtiMb58yBkQ1GK30tKeQi/BhN/mgTrjDjgbMEsw
y3W4a9mA1EG92la5VL1gVU+iuFIL1SUNpe5b4wkJvKyvF3dnGzZRXeebt2u5QdRIFp7MkAjct2Of
/c1gTDIMjOvfN6WziorPDozIATaC0xKPYX8BPZSZqHG6RjE3NNw2LIjztx6xJxrwfOFjqK3r8NwL
YsZP7V3cqvaANS/YhEe+/MZP8kLv+5kij2N10MtM++dlpTqkN6+lTFUKdE0WzaZCoEAnNHood5vm
Z7/ACBhHGST8UcQDvDmOTfcm6TKpeq1UIj4kETGbRJ2W+OD4AgCc4Fo=
','14545','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,465,858),
	 (470,NULL,438417.00,'SUCCESS','2021-10-02 10:01:01.776','2021-10-02 10:01:03.105','20211002SIHBIDJ1010O0100000015','20211002CENAIDJA010R0200000198','2021-10-02 10:01:01.773','174551','SVGS','C224403','02','7744404','CACC','64145','01','eJyVU9tu4jAQ/ZXKzxTZIRTUp82FS1YtIGL1pepDSBygmzhZZ1K1i/j3HTsJoivQtlFunvHMOXNm
fCBuXT1WW3J/IE5ZzhOl/6bNe79Pmq8MZAUyMKsl7IxXr0gYzN3A/8nIEa8e4cUX47zJwsE4p41z
93+QQ6DBiUUtxii1utSU0SU+5mJD0iO40xdps7mM4qpP6Rgf1qdj9HpK+NCmuWX0llqc0XvK8CaI
5BdxnQsJDT8opoFXQa68BLhKtXGmOhEMo8uEWjZ3HR7PryD2yGKzTPl7hX69CgGyPJAGyfw/gpHj
YT0jWoiGB383W54PZJVDo95EJrzA1zVGnyXCBP9jjmCYnJctmYc4071SuV6sFKgPjG8k82D7sapV
ee7Z7NOogn5Rw6aoZdKPlUj2ACqSVSpU39vJzDMm3prW4nctKlgVr8UPSww26VgMdHrMH0hQ7i+j
hpObxjxFWS0QxR6MbTbC3mqdY40b+GtDaae2ru6/P3E5Ov0NmJYtdB8cmSgR3YR1gZ5GvZV6g/Mp
fG7H8M5m9pAcX8wQ6iROHEM3pP+M7Ghk2za1DWX+SQzP8TxyyrCFb5wZ0/EvxJyflybmVO7UWc+d
xY0TnKpdqu2lYj3LwgIGXbkG+Hq5bGQPh+xCteHTLDTmsMxyNPkRRAZkIt8ys7Prho7rZig5N1m6
jBfD4/gX+8dUyg==
','eJyNUk1vgkAQ/SvNnNXMojXWGwJWaqsGtqfGAxG0prIQWK3W+N87O4hpGk07hP2cee/lzR5hsC1f
yhX0j2Dn+SguzGpYjet1XM3KV6VWPu+m+p1vzQ4cb2L77pMNJ4oGyOyfdaE/GlCdONcN1l+kwTfk
YKElBKJVQ6PAAC00IR560ADKdJNllZxHi7JFyfSLlkBgqHC3MNKePTugA6dIXH3GbQpsoiUF9lH0
0QKidrPFNk2UrgTrbOjPUh3qMsj56LGoPWGB1/VxdLudmk2mt/mmxUptCNVXS1vFRAT9t2N1+puh
NqlmQNGFxiV1knLy2YEeO4A9OM2pDfsr6J6KZUbDLYppTSPuaxbC+VuP3DMN2I50aGusK6nvS2am
pfFuVujiQDmvVEQtn1/wg2Rp7p1Ys8czfTDTxPjnfWR34225TSPgNxLmm5RQ3EhHDO2p3Sb/WSxp
ByhIA6sey1mTnk2n3TN9CcpY6Uoop8hPxSzYZkWnOX0U3zNq3yk=
','14422','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,468,1329);
INSERT INTO public.credit_transfer (id,settlconf_bizmsgid,amount,call_status,cihub_req_time,cihub_resp_time,crdttrn_req_bizmsgid,crdttrn_resp_bizmsgid,cre_dt,creditor_acct_no,creditor_acct_type,creditor_id,creditor_type,debtor_acct_no,debtor_acct_type,debtor_id,debtor_type,full_request_msg,full_response_msg,intr_ref_id,msg_type,orign_bank,recpt_bank,resp_status,reversal,chnl_trx_id,cihub_elapsed_time) VALUES
	 (473,NULL,438417.00,'SUCCESS','2021-10-02 10:04:30.425','2021-10-02 10:04:31.185','20211002SIHBIDJ1010O0100000017','20211002CENAIDJA010R0200001428','2021-10-02 10:04:30.425','174551','SVGS','C224403','02','7744404','CACC','64145','01','eJyVU8lu2zAQ/ZWAZ8cgZbo2fKoWLyoS27CIXIIctHlpJUqlRkFSw/+eISUZTmGjqQAtnO29eTM6
EqeuHqsdmRyJXZaLROmvWfM8HJLmLX1ZgfTNaQV749UnEvgLx/d+MHLCq0dE8cU8d7q0Mc9u85zD
H+Tga3BiUYsxSq2uNGV0hbe52Ij0CEZ66bYJLsO46lM6xpv16Ri9rko9aMvcM3pPLcHohPLJgBJE
8oq4zlMJDT8oZr5bQa7cBITaauNcdSIYRtcJtWzOeCK/gdgjy2i1FW8V+hmeAoAs96VBMt+PYOR4
2MyJFqLhId5MyPORrHNo1JvKRBT4uMXos0RY4F/MEQyLi7Il8xBnelYq14e1AvWO+ZTpMBd27+ta
lZee6LANK+gXNURFLZN+rNLkAKBCWW1T1Xf3MnONSbSmTfq7TitYFz+L70NqcRZFiS6P9X0Jyvll
1LBzM5inMKtTROGDMWcjnK3WOda4vrcxlPZq5+j5e1NHoNOLwIxsqedgy0Sl4V1QF+hp1FurV7jc
wud2Db9xxofk9GKWUBex4xi6Jf1rZUcjzjnlhrL4JIZruy45V9jBf/wzZuJfyLn8X5qcc7sze7Ow
l3e2f+52pXbXmnUtCxsYdO0a4NvtshEfDtmVboOneWDMQZnlaPJCCA3IVL5mJrKbhs7rdii5NFm6
jRfD4/QBtndUng==
','eJyNUl1vgjAU/StLn9XcAtmIbwg42YcS6LKHxQcj4EikEKhOZ/zvu73AsiyarYS29/becw6nnNhk
1zw3GzY+MaeqZkmtd9N2zvOkXWUgGyUDihbqnU51xFx/7gTeg8POOAZMlP/si4PZBPt41zfJP1FD
oMmZAQbnAEYPDRwiMAAHtwybDRhWemnWFlerdTPCYnz5iAMjqHi/1tKefCfChFunnupwhxyGYAgO
Y7DGJjCk9sr1rkilagWrchqEhYpVE1WUuq97T0jgZX0kzrw1ezZRXOdb1Bu5RdRAZo5MkIiN305t
9jdDb1LHgBz68/vSeUHFnQM2OQA2Oy/xGg4X0H2ZiBKnaxSLnobf9SyI87cecSAa5rjCxVBb1+C9
Z8SMW+1dWKv6iDUv2IRXvvzGj9JMn7uJIo9DddTLXPv3msskv3GSvNqpumT0n8TVtkAkb6VWBO/L
/bb6CSAwYsBRByl/FOEQfx3LtC1MRU0iVSuWSsSHJCYwSdV5iQ+OL/HV4N8=
','1242','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,471,760),
	 (476,NULL,438417.00,'SUCCESS','2021-10-02 10:07:44.671','2021-10-02 10:07:45.499','20211002SIHBIDJ1010O0100000019','20211002CENAIDJA010R0200001832','2021-10-02 10:07:44.67','174551','SVGS','C224403','02','7744404','CACC','64145','01','eJyVU1tv2jAU/iuVnymyU7MwnpYLl0wtIGL1pepDyAXoEidzTqp2iP++Y4cgOoHWWnJin9v3nYv3
xG3qh3pDRnviVNUsUfo0ab+7XdL+ZSBrkIG5LWBrtPpGwmDmBv5PRg64ekSUn/TzxnMH/Zyjn7v7
gxwCDU4sajFGqdWFpowucJvFvpMeQUs/zVrjKorrPqVD3KxPh6j1VOrDMcwto7fUEoyOqD3inCCS
X8ZNkUpo+UE5CbwaCuUlIFSmhVPVFcEwukyoXRbt8ERxBbFH5utFJt5q1DO8hQB5EUiDZM4PYMpx
v5oSXYiWh3gzJk97siygrd5YJqLEzzVGH0uEAf7HHMEwuKiOZO7jXPdKFfqyVKDe0Z8ybebB5n3Z
qOpcs95lUQ39soF12cikH6s02QGoSNZZqvreVuaeEYmjaJX+btIaluVL+WNg2yyzrUiHx/iBBOX+
MtVwCtOYxyhvUkThd0PObOytrnOscQN/ZSht1cbV/ffHrkClvwbTsrnugyMTlUY3YVOipq3eUr3C
+RQ+HcfwG2d8QA7PZgh1ECeOoRvSf0bWtjnnlBvK4kMxPMfzyCnCBr7wZkzHP+Fz/l5an1O6E2c1
c+Y3TnDKdqE2l5L1LAsTuOvSNcDX02U2HwzYhWzDx2loxGGVFyjyI4gMyFi+5say64b262YoORdZ
Oo1nw+PwF5BNVIQ=
','eJyNUl1vgjAU/SvLfVbTosvQNwSc7EMNdA/L4gMRcCxQCFSnM/733V5gWZaZrYS29/becw6nnGC6
qx/rLUxOYJXlPKr0btbMaRo1q/RkraRH0VK90qmOwHYXlufcWXDG0QNR/LMv8OZT7ONt3zT9QA2e
JgeDGZwzZnTQjDOfGQwHN4cG9AArnThpistwUw+wGF8+4AwIKthvtLQH1/IxYVexo1rcPmd9ZgjO
JuxmMroGpHaKzS6PpWoEq2LmrXIVqNovKXVbdZ6QwN/1kbixaXRsIr/Mt6y2MkNUTyaWjJAIJi+n
JvuToTOpZcCM/sCudJFTceuASQ4wE85rvIbDL+iujESB0yWKZUfDxx0L4vytRxyIBixb2Bhq62q8
94SYcau9W1WqOmLNEzbhla+/8P040ed2pMjjlTrqZaH9ey626VXwFiZVmgH9JUGZ5YjjhCokcFfu
s/J7u8AIGEcVpPterPr444yG5ghTfh1J1UilEvEuiYcNSdN5jQ+OTzEO4As=
','1242','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,474,828),
	 (479,NULL,438417.00,'SUCCESS','2021-10-02 10:10:24.531','2021-10-02 10:10:25.372','20211002SIHBIDJ1010O0100000021','20211002CENAIDJA010R0200001091','2021-10-02 10:10:24.53','174551','SVGS','C224403','02','7744404','CACC','64145','01','eJyVU9tuozAQ/ZXKz2lku04T9WnB5MKqTaJg9aXqA+GW7IJhzVC1G+Xf1zYQpatE20WB4LmdM2eG
A3Kb+qnO0MMBOVW1iJV5m7XP/T5u/6Uva5C+Pa1gZ73mhAJ/4fred4KO+hogUX4xj0+Xjs5zujx3
/1tz8A04opgSgjHtS2OCV/q2FyVogHSkl6RtcBVG9RDjib7JEE+0l6vEg67MLcG3mAqCH/SPMqSR
vDJqikRCyw/Kmc9rKBSPQajUGOeqF8EyukyoY0N7PFFcQRyg5XaVivda+w33ACAvfGmR7PsTWDke
N3NkhGh5iHcb8nJA6wJa9aYyFqV+XGP0WSJd4F/MNZguLqqOzGOUm1mpwhzWCtSHzsfEhHHIPtaN
qs49230a1jAsG9iWjYyHkUriPYAKZZ0mash3MufWJDrTJvnVJDWsyx/ltzG7JykZUVNe1/clKPen
VcMp7GCew7xJNAq7mzAy1rM1OkcG1/c2ltJOZa6Zvzd1hXZ6W7AjW5o5ODJWSXgTNKX2tOqt1Ruc
b+FLt4b3jLAROr7aJTRFnCiCfkn/WtnxmDGGmaUsPonBHc7RqUIG//HN2Il/Ief8e2lzTu3OnM3C
Wd44/qnblcouNcsp1Q3c9e1a4OvtkjEbjciFboPneWDNQZUX2uSFEFqQqXzLbWQ/DZPX71B8bqKm
jVfL4/gHzxVUNg==
','eJyNUl1vgjAU/SvLfVbTVk2cbwg42YcS6LKHxQci4EikEKjOzfjfd3uBZVk0W4G29/b2nMNpTzDb
10/1FqYnsMpyEVdmNm/6LIubUXmq1sqjaKXfaNVEYLtLy3PuLThj64Es/rkv9BYz3MfbfbPsEzV4
hhwEE5wzJjpoxlnABMPG2S2HHmClk6RNcRlt6gEW48cHnAFBhYeNkfboWgEm7CpxdIvb56zPhORs
iq8YA1I7xWafJ0o3gnUx9/xch7oOSkrdVZ0nJPCyPhI35qJjk/l1vlW1VTtE9VRqqRiJYPp6arK/
GTqTWgbMGIaudJlTcevAhBxgEziv8RiOF9BdFcsCu2sUq45G8I4Fcf7WI49EA5YtbQyNdTWee0rM
ODXe+ZWuPrDmGTfhka+/8YMkNet2rMljX3+YYWn8e8lUpKMbvJ46yjOgexKWuxyRHFwgeFcdduVP
AIkRMPMDpPxB+n28OqPhZISpoI6VbsRSiXxXxMSGpOq8xgfbF9E14MA=
','1242','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,477,841),
	 (482,NULL,438417.00,'SUCCESS','2021-10-02 10:13:12.696','2021-10-02 10:13:13.581','20211002SIHBIDJ1010O0100000023','20211002CENAIDJA010R0200001687','2021-10-02 10:13:12.696','174551','SVGS','C224403','02','7744404','CACC','64145','01','eJyVU9tum0AQ/ZVonx1rF69ry0+FxReqxEYG5SXKAwZ8aWGhyxAltfzvmV3AcipbTZG47NzOmTPD
kTh19VjtyORI7LJcJEp/zZrn4ZA0b+nJCqRnTivYG68+kcBbOJ77g5ETXj0SFl/ME9OljXl2m+cc
/iAHT4MTi1qMUWp1pSmjK7zNZQ1Ij2Ckm26b4DKKqz6lY7xZn47RK1TqQlvmntF7aoWMTthgwiyC
SG4R13kqoeEHxcwTFeRKJBCqrTbOVSeCYXSdUMuGd3hhfgOxR5ab1TZ8q9DP8BQAZLknDZL5fgQj
x8N6TrQQDY/wzYQ8H4mfQ6PeVCZhgY9bjD5LhAX+xRzBsHhYtmQe4kzPSuX64CtQ75hPmQ4TsHv3
a1VeejaHbVRBv6hhU9Qy6ccqTQ4AKpLVNlV9sZeZMKawNa3T33VagV/8LL5bER/ROKW6PNb3JCjn
l1HDzs1gnqKsThGFD8acjXC2WudY43ru2lDaq52j5+9OnRCd7gbMyJZ6DrZMVBrdBXWBnkY9X73C
5RY+t2v4jTM+JKcXs4S6iB3H0C3pXys7GnHOKTeUw09iCFsIcq6wg//4Z8zEv5Bz+b80Oed2Z/Z6
YS/vbO/c7UrtrjUrLAsbGHTtGuDb7bIRHw7ZlW6Dp3lgzEGZ5WhyI4gMyFS+Ziaym4bO63YouTRZ
uo0Xw+P0AZbBVJQ=
','eJyNUl1vgjAU/SvLfVbTgtmIbwg42YcS6J4WH4iAM4FCoDqd8b/v9gJmWTRbgZZ7e+85J6c9wXTX
vDYbmJzArqp5Uuu/WTtvt0m7Sl82SvoULdUH7eoIHG9h++6TDWccAxDlP/sifz7FPt71TbdfqMHX
5GAwg3PGjB6acRYyg+Hg99YDDAAr3TRri6t43YywGD8+4gwIKtqvtbQXzw4x4dSpqzrcIWdDZgjO
JtzEF5DaLde7IpWqFazKmR8UKlJNWFHqse49IYHX9ZE4Y2z2bKK4zbesNzJHVF9mtkyQCCbvpzb7
m6E3qWPAzBgGl9JFQcWdAxY5wCw4r/AYDlfQPZmIEqdbFMuexjB7FsT5W484EA3YjnAw1NY1eO4Z
MeOv9i6oVX3EmjdswiNfXfDDNNP7TqLI40Ad9bLQ/kXHuGh2+Z2dxAXQLYmqvEAcN1YxgXtyn1c/
2wVGwDiqIN3PIhjixRmbltYZNolUrVQqEZ+SeJhJms4rfHB8AxkV3/o=
','1242','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,480,885),
	 (485,NULL,438417.00,'ERROR-CIHUB','2021-10-02 10:27:27.461','2021-10-02 10:27:27.47','20211002SIHBIDJ1010O0100000025',NULL,'2021-10-02 10:27:27.461','174551','SVGS','C224403','02','7744404','CACC','64145','01','eJyVU1lvozAQ/iuVn9PIdsmhPi1HDlZtEgWrL1UfuHJ0wbBmqNqN8t93bCBKV4m2RYHgub5vvhkO
xKmrx2pL7g/ELst5ovTbtHnu90nzL31ZgfTNaQk749UnEvhzx/d+MnLEq0dE8cU8d7KwMc9u85z9
H+Tga3DCKWeMUt6Vpowu8TYXH5AewUgv3TTBZRhXfUrHeLM+HaPXVakHbZlbRm8pF4ze8xH+CCJ5
RVznqYSGHxRT360gV24CQm20caY6EQyjy4RaNsMOT+RXEHtkES034r1CP8NTAJDlvjRI5v0RjBwP
6xnRQjQ8xLsJeT6QVQ6NehOZiAIf1xh9lggL/I85gmFxUbZkHuJMz0rl+rBSoD4wnzId5sL2Y1Wr
8twT7TdhBf2ihqioZdKPVZrsAVQoq02q+u5OZq4xida0Tn/XaQWr4rX4MYwYH4Y80uWxvi9BOb+M
GnZuBvMUZnWKKNbd2GIjnK3WOda4vrc2lHZq6+j5exNHoNOLwIxsoedgy0Sl4U1QF+hp1FupNzjf
wud2DYcWswbk+GKWUBex4xi6Jf1nZUcjy7KoZSiLT2K4tuuSU4UtfOObMRP/Qs7599LknNqd2uu5
vbix/VO3S7W91KzLOTZw17VrgK+3y0bWYMAudBs8zQJjDsosR5MXQmhAJvItM5HdNHRet0PJuYnr
Nl4Mj+Nf+VlUsg==
',NULL,'1242','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL,483,9),
	 (489,NULL,438417.00,'ERROR-CIHUB','2021-10-02 10:27:32.67','2021-10-02 10:27:32.681','20211002SIHBIDJ1010O0100000029',NULL,'2021-10-02 10:27:32.668','174551','SVGS','C224403','02','7744404','CACC','64145','01','eJyVU1tv2jAU/iuVn1tkh9AwnpY4XDK1gIjVl6oPIRdgS5zMOanaIf57jx2C6ARaZ8mJfW7fdy7e
E6+pH+sNGe2JW1WzROnTpP3udkn7l4GsQQbmtoCt0eobCYOZF/g/GDnguiWi/KIfH89d9HOPft7u
D3IINDixqMUYpVYXmjK6wG2W9Y3cErT006w1rqK47lE6xM16dIharlIfjmHuGL2jlmB0ZDmjvkUQ
yS/jpkgltPygnAS8hkLxBITKtHCquiIYRpcJtatPOzxRXEG8JfP1IhNvNeoZ3kKAvAikQTLnRzDl
eFhNiS5Ey0O8GZPnPVkW0FZvLBNR4ucao88lwgD/Yo5gGFxURzIPca57pQp9WSpQ7+hPmTbjsHlf
Nqo616x3WVRDr2xgXTYy6cUqTXYAKpJ1lqoe38qcG5E4ilbp7yatYVn+LL/b6XpAM+deh8f4gQTl
/TLVcAvTmKcob1JEsftDmznYW13nWOMG/spQ2qqNp/vvjz2BSn8NpmVz3QdXJiqNbsKmRE1bvaV6
hfMpfD6O4b3N7AE5vJgh1EHcOIZuSP8aWcexbZvahrL4VAzuck5OETbwH2/GdPwLPufvpfU5pTtx
VzN3fuMGp2wXanMpWW5ZmEC/S9cAX0+XOfZgwC5kGz5NQyMOq7xAkR9BZEDG8jU3ll03tF83Q8m5
yNJpvBgehw/2w1S1
',NULL,'1242','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL,487,11),
	 (493,NULL,438417.00,'SUCCESS','2021-10-02 10:47:02.837','2021-10-02 10:47:04.155','20211002SIHBIDJ1010O0100000033','20211002CENAIDJA010R0200000023','2021-10-02 10:47:02.837','174551','SVGS','C224403','02','7744404','CACC','64145','01','eJyVU9tuozAQ/ZXKz2lkE6dEeVowubBqkyhYfan6QLglu2BYM1TtRvn3tQ1E6SrRdpG4eG7nzJnh
iNymfqozND0ip6qWsdRf8/Z5OMTtW/iiBuGb0xr2xqtPKPCXru99J+ikrgHi5Rfz2GzlqDyny3MP
vxUHX4MjC1uEYGz1pTHBa3WbazRCA6QivSRtg6swqocYT9RNhniivEwmHnRl7gm+xxYneErtKbaQ
QvLKqCkSAS0/KOc+q6GQLAYuU21cyF4Ew+g6oY4N7fF4cQNxgFa7dcrfa+Un6hQA5IUvDJL5fgIj
x+N2gbQQLQ/+bkJejmhTQKveTMS8VI9bjD5LpAr8i7kCU8V51ZF5jHI9K1now0aC/FD5mOgwBtnH
ppHVpWd3SMMahmUDu7IR8TCSSXwAkKGo00QO2V7kzJh4Z9omv5qkhk35o/wWUzsdP4S6uirvC5Du
TyOGU5i5PId5kygQOppQYqvRapkjDet7W8NoLzNXj9+buVw5vR2Yia30GBwRyyS8C5pSeVrxNvIN
LpfwpdvCB0roGJ1ezQ7qIk4UQb+jf22sbVNKMTWU+SctmMMYOlfI4D9+GTPwL+Rc/i5tzrndubNd
Oqs7xz93u5bZtWaZZakGRn27Bvh2u8Sm4zG50m3wvAiMOajyQpm8EEIDMhNvuYnsp6Hz+hWKL02W
buPV8Dj9AcchVH8=
','eJyNUsFuwjAM/ZXJZ0BOgzTErbQwOjZAJTtNHCpKWSUaqjYwGOLf57gtmibQ5qpJ7NjvPdk5w2Bf
vpYb6J/BzfNxXNjTqFrTNK52HejS6IC9mfngW+uBN5y6gf/swoWsBWr3z7pFMB5QnajrBukXaQgs
OTjoCIHoNNAoMEQH2RwJLaBMf51UyXm0KjsUp190BAJDLQ4rK+1l6IYU8Iq1b2rctsA2Ogplv/vY
RwlE7e9W+2ytTSXY7EbBPDMLU4Y5h56Kpics8LY+a6InRcOmsvt8s2Kjt4Qa6MTVMRFB//1cRX8z
NE2qGRBlF1rX1GnGyXUHetwB7MFlSWM43kAf6ljtaLlHMWtopGxYCOdvPerINOB6yiPXtq6kuSfM
TEfbu3lhihPlvFERjXx5xQ/Xib33YsM9npuT3aa2f/QuTZSlD5N9uc8i4HeyyLcZIfmRiRh+qA/b
/CeAIg/QDoKVT9S8TU+nK3tWaVjG2lRiOUV9amZCyaouS/rIvgH6OODp
','1242','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,491,1318),
	 (499,NULL,438417.00,'SUCCESS','2021-10-02 11:28:19.929','2021-10-02 11:28:20.962','20211002SIHBIDJ1010O0100000035','20211002CENAIDJA010R0200001292','2021-10-02 11:28:19.928','174551','SVGS','C224403','02','7744404','CACC','64145','01','eJyVU1lvm0AQ/ivRPjvWLl4f8VM5fFAltmVWeYnygAEfLSx0GaKklv97ZxewnMpWUySOnev75pvh
SJyqfCp3ZHwkdlHMY6W/pvXzcIjrt/RlCdI3pyXsjVefSODPHd/7zsgJrw4R+Rfz3MnCxjy7yXMO
v5GDr8GJRS3GKLXa0pTRJd7m6vVJh2Ckl2zr4CKMyi6lI7xZl47Q66rEg6bMPaP31BKMja3RmD0Q
RPLyqMoSCTU/yKe+W0Km3BiE2mrjTLUiGEbXCTVsBi2eyG4gdshis9yK9xL9DE8BQJr50iCZ7ycw
cjyuZ0QLUfMQ7ybk5UhWGdTqTWQscnzcYvRZIizwL+YIhsVF0ZB5jFI9K5Xpw0qB+sB8ynSYC7uP
VaWKS8/msA1L6OYVbPJKxt1IJfEBQIWy3Caq6+5l6hqTaEzr5FeVlLDKf+TfBv3hhnL+oMtjfV+C
cn4aNezMDOY5TKsEUXhvxNkQZ6t1jjSu760Npb3aOXr+3sQR6PQ2YEa20HOwZayS8C6ocvTU6q3U
G1xu4UuzhgPOeJ+cXs0S6iJ2FEG7pH+t7HDIOafcUBafxHBt1yXnCjv4j3/GTPwLOZf/S51zbndq
r+f24s72z90u1e5as65lYQO9tl0DfLtdNuT9PrvSbfA8C4w5KNIMTV4IoQGZyLfURLbT0HntDsWX
Jku38Wp4nP4AdWpUaw==
','eJyNUk1vgkAQ/SvNnNXsLra13BCw0g8lsE0PjQcjaE1gIbDaWuN/7+wgpmk07RB2mdmZ917esofh
pn6uV2DvwSnLcVKZr1GzrtdJs6tA1VoFlE31O52aDFx/4gTegwMHjA7I4p9zcTAe4hw/zg3XX6gh
MOQgmOCcMdFCM84iJhgGF3cCOoCdXrpsmsv5ou5hM768xxkQVLxdGGlPvhNhwa1STx9xu5x1mZCc
22JgCwZI7RWLTZ4q3QjWxSgIcx3rOiqpdF+1npDA8/oo+tZtyybzy3zTaqUyRA3U0lEJEoH9tm+q
vxlak1oGZt1A59Q6yan56MCAHGADOMzwGj7PoPsqkQUulyimLY113bIgzt965CfRgONKF1NjXY33
viRm/DTehZWudtjzgkN45bMTfpQuzbmbaPI41DuzTYx/8W6e15vs6nWdFEkB9J/EZZYjkjfXc4L3
1TYrfwJIzIBx1EHKH2XYFeZqBn0sRXWidCOWWuSHIiZmkarDDB+MbxFL4Pw=
','122642','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,497,1033),
	 (503,NULL,438417.00,'SUCCESS','2021-10-02 11:36:07.538','2021-10-02 11:36:09.166','20211002SIHBIDJ1010O0100000037','20211002CENAIDJA010R0200001089','2021-10-02 11:36:07.526','174551','SVGS','C224403','02','7744404','CACC','64145','01','eJyVU9tum0AQ/ZVonx1rF+Ng+amw+EKV2JZZ5SXKAwZ8aWGhyxAltfzvnV3AcipbTZG47NzOmTPD
kXh19VTtyPhI3LKcJ0p/TZvn4ZA0bxnICmRgTkvYG68+kTCYe4H/nZETXj0iii/m8cnCxTy3zfMO
v5FDoMGJRS3GKLW60pTRJd7mGjikRzDST7dNcBnFVZ/SEd6sT0fo5Sr1oS1zz+g9tQRj48HDmDoE
kfwirvNUQsMPimnAK8gVT0CorTbOVCeCYXSdUMvmjCfyG4g9stgst+K9Qj/DUwiQ5YE0SOb7CYwc
j+sZ0UI0PMS7CXk5klUOjXoTmYgCH7cYfZYIC/yLOYJhcVG2ZB7jTM9K5fqwUqA+MJ8yHcZh97Gq
VXnp2Ry2UQX9ooZNUcukH6s0OQCoSFbbVPX5XmbcmERrWqe/6rSCVfGj+Mbs0SAe2Ykuj/UDCcr7
adRwczOY5yirU0SxByObOThbrXOscQN/bSjt1c7T8/cnnkCnvwEzsoWegysTlUZ3YV2gp1Fvpd7g
cgtf2jV8sJk9JKdXs4S6iBvH0C3pXyvrOLZtU9tQFp/E4C7n5FxhB//xz5iJfyHn8n9pcs7tTt31
3F3cucG526XaXWuWWxY2MOjaNcC322WOPRyyK92Gz7PQmMMyy9HkRxAZkIl8y0xkNw2d1+1Qcmmy
dBuvhsfpD8/vVJk=
','eJyNUl1vgjAU/StLn9XcgnHIG4JO9qEGuuxh8cFYdSRSCFSnM/733V7ALItmK6Htvb33nJPTnthg
V76UG+aemJfnY1mY3aiak0RWqwpVqVVI0VR/0KmJmD+ceGHw6LEzjhYT2T/74nA8wD5e9w2SL9QQ
GnJmgcU5gNVAA4cILMDBwemzFsPKYLWuivPFsuxgMf68w4ERVLxfGmnPQy/ChF+sAl3jtjm0wRLQ
de2eCw5D6iBb7tKV0pVgnY3CWapjXUY5pR6KxhMSeF0fietDr2ET6W2+abFRW0QN1dpTEomY+36q
sr8ZGpNqBgDbYa1L6SSl4toBhxwwFHO8hsMV9KGSIsPpFsW0obHvGxbE+VuPOBAN83zhY2isK/He
18SMW+PdrNDFEWtesQmvfH7Bj1Zrc+5LTR7P9NEsE+PfWyIzmd3h89SLNGH0TuJ8myJSsNALgh+q
/Tb/CSAwYsBRByl/ErM2Pp2u7XQxFZVS6UoslYhPRUxgk6rzHD8c3zMZ4P0=
','126642','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,501,1628);INSERT INTO public.domain_code (id,grp,"key",value) VALUES
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
	 (301,'CHANNEL.TYPE','01','Internet Banking'),
	 (302,'CHANNEL.TYPE','02','Mobile Banking'),
	 (303,'CHANNEL.TYPE','03','Over the Counter'),
	 (304,'CHANNEL.TYPE','99','Other');INSERT INTO public.fault_class (id,exception_class,reason) VALUES
	 (1,'com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException','Attribute tidak dikenal'),
	 (2,'com.fasterxml.jackson.core.io.JsonEOFException','Json parsing Exception'),
	 (4,'java.util.NoSuchElementException','Validation Error'),
	 (5,'org.apache.http.conn.HttpHostConnectException','Connection error'),
	 (6,'org.apache.http.NoHttpResponseException','Connection error'),
	 (7,'java.net.SocketException','Connection reset'),
	 (3,'java.net.SocketTimeoutException','Timeout'),
	 (8,'org.apache.camel.http.common.HttpOperationFailedException','HTTP call error'),
	 (10,'SocketTimeoutException','Timeout');INSERT INTO public.inbound_counter (tanggal,last_number) VALUES
	 (20210915,50000032),
	 (20210911,50000006),
	 (20210912,50000006),
	 (20210913,50000052),
	 (20210921,50000006),
	 (20210914,50000018),
	 (20210926,50000093),
	 (20210925,50000043),
	 (20211001,50000002),
	 (20211002,50000002);INSERT INTO public.m_bic (bank_code,bank_name,bic_code,change_who,created_date,last_update_date) VALUES
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
	 (20210925,35),
	 (20210914,51),
	 (20210921,284),
	 (20210922,211),
	 (20210916,184),
	 (20210919,190);
INSERT INTO public.message_counter (tanggal,last_number) VALUES
	 (20210924,104),
	 (20210926,18),
	 (20210923,64),
	 (20210929,4),
	 (20210930,29),
	 (20211002,50),
	 (20211001,5),
	 (20211003,69);INSERT INTO public.payment_status (id,bizmsgid,error_msg,intern_ref_id,orgn_endtoendid,request_dt,request_full_message,response_dt,response_full_message,retry_count,saf,status,chnl_trx_id,cihub_elapsed_time) VALUES
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
',0,NULL,'TIMEOUT-CIHUB',4186,NULL),
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
',0,NULL,'SUCCESS',4217,NULL),
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
',0,NULL,'SUCCESS',4223,NULL),
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
',0,NULL,'SUCCESS',4229,NULL),
	 (4281,'20210925SIHBIDJ1000O9900000029',NULL,'11004','20210925SIHBIDJ1010O0100000027','2021-09-25 07:39:30','eJyNUE1vwjAM/S8+Q+WkC116aykfmbR1Gr0hDhMtUImmXROkaYj/PqcpNw48RXYc288vvkJ6Me/m
CPEVkq5bl727Lb2t69J7rbSxWg1Rbk9D1kWwUetUZW8MboQJFO2TffPFR0J9ydiX1n+kQbnhwJEz
lFzcqRExlxIHcAkToMqsOvji7ntvAuSvASIL8IWy877K7EgzRTnlomAyDukg0KSs3V+aSluvz7ZL
9dnYjTVf1Y97WvX3FQx6HsvxIL5xWtFAzGYhF1EoIowEd5v4VfoA8Zb+3R/1eaHLoiXzmJJhjiMx
j+C2c0v5B37Gc9g=
','2021-09-25 07:39:30','eJyNUtFugjAU/ZXlPqtpq2bgGwJOtgwIdE+LD0TAmUkhUJ2b8d93W8Asi2YroeXe3nvO4bQnmO+b
52YDsxNYVbVMa/W1aOftNm1X4YlGCk9HgXzTuyoC2/Utz3m04IxjALz8Z1/sLefYR7u++fYLNXiK
HBhhlJhs2kMTSiLCCA46NQkMACudLG+Lq2TdjAhh+NIRVbsIFR/WShqP3DgM/NjFrF1njuzAh8Qc
simnbDY2Z+wekN8p1/siE7JVLcuFFxYylk1U6dRD3RujVV4X2Sqc0J6NF7f5gnojdojqidwSKRLB
7PXUZn8z9E51DPivBgwupX6hizsbDG0DMeC8wrM4XkF3RcpLnG5RBD0N6uxYEOdvPfyoacCyuY2h
sq7Bw881M34q78Ja1p9Y84JNeO6rC36U5WrfTqX2OJSfavGVf55Ik1qWd+57CfqmxNWuQBgnkYnG
dsVhV/3s5hgBUaegZT/xcIiXZzI2JpiKmlTIVqku4R9C05CxlnRe4YPjG8xx4ac=
',0,NULL,'SUCCESS',4276,NULL),
	 (4336,'20210926SIHBIDJ1000O9900000015',NULL,'155221','20210926SIHBIDJ1010O0100000013','2021-09-26 16:21:40.674','eJyNUE1vwjAM/S8+Q2WHEtHcWgojk7ZOo7dph4kWqETTrgnSNMR/n9OPG4f54MR5fs8vvkFytS/2
BOoGcdvuis7ftkOuqmI4jTbWGd1XmTv3qK9gr3eJTp8J7hwzyJt/8tab15h58chLql/2oP1wECgI
IyEnaUTMogj7oCXMgDvT8jg0t18HG6BYBYwFGDK67srUjTJzjOZC5iSVIBUi8KS0OVzr0rjBn2u2
+q12e2ffy2//9NRNK+j9PLYzepHTtLwGRXIhZLhieEnCb+JHmyOoD/53dzKXjSnyhtNjScIMR2Fa
wP3TL+UPcjJzvQ==
',NULL,'eJyNUtFugjAU/ZWlz2raYgz6hoCTLQMCXfaw+GAEHIkUAtXpjP++2wuYZdFsJbS9t/eec3LaM5nv
m5dmS2ZnYlXVMqn1btHOeZ60q/Rko6SHUaA+8FRHxHZ9y3OeLHKBMSCi/Gdf7C3n0Me6vnn+BRo8
TU445YxO+aSHpoxGlFMYjLMJGRCodNKsLa7Wm2ZEKYefjRglCBUfNlqaiNw4DPzYhaxdp47qwId0
OuQTQaczzmaGSYDfKTf7IpWqVa3KhRcWKlZNVGHqse6NQZW3RaJCkxs9myju8wX1Vu4A1ZOZJRMg
IrP3c5v9zdA71TEAx5gMrqV+gcWdDSbaQIFiBXdxvIHuykSUMN2jCHoaZvQsgPO3HnFEGmLZwoZQ
W9fA5WfIDFvtXVir+gQ1r9AE97664kdpps/tRKHHoTrpxdf+xfsmleXDWy7Xak3wrcTVrgAgRyc0
uisPu+pnv4CIUAYyUPizCIfwfMaGqYVGTSJVqxVLxKdEImqgqMsKPhjfqCnijA==
',0,NULL,'SUCCESS',4331,0),
	 (21,'20210930SIHBIDJ1000O9900000004',NULL,'14145','20210930SIHBIDJ1010O0100000002','2021-09-30 10:53:14.134','eJyNUMFuwjAM/RefobKTMi25tRRGJo1Oo7dph4kWVmlNuyZICMS/kzTtjcN8cOLYfu/lXSE9mTdz
BHmFpOs2Ze9v65DrugynVtpYrYYqtz9D11ewU5tUZa8ENxczKNp/7i1X28TtJeNeWl+cBuXJgSEj
FBwnaETMhcAQMczATWbVIQx333sTIXuOECkausu+yuwIM0cx51gQygWXFINjytr9qam0Dfpsu1bv
jd1Z81H9+aeXfrJg0PNYzhiLia1oQNITZ0IIEjHx2DtxVvoA8tP9uz/q35Uui9alx5CEOU7ADG5f
3pQ7anlzuQ==
',NULL,'eJyNUttugkAQ/ZVmntUMoKn6hoCVNlUC2/Sh8YEIWhJZCay3Gv+9s4OYptG0Q9jL7Mw5J2f3BKNt
9VqtYHgCuygmSalX43rMsqSepS8rJX3ezdQnn+odON7U9t1nG84ULRCbf/ZF/mREfcalb5R9kQZf
k4OJpoEDCxtoNDBEE3X0sQctoEo3XdbFRbyoOogm/UbHQGCoaLfQ0kToRcFsGnmUdcrUVRfwNg7a
FgoDhz1raJCEFribxTZPpapVq83YD3IVqSosOPVUNsawytsiOXrmY8Mm8vt8s3Il14Tqy6UtEyKC
4cepzv5maJxqGBAtaF1LpzkXX2zosw3Yh/Oc7uJwA92TidjQcI9idqUxGxbC+VuPODAN2I5waKut
q+jyl8xMS+1dUKrySDVv1EP3Pr/ih+lSnzuJYo8DddTTVPv3npXxPpYPXrnPJPBbiYp1TkBurGJG
9+RuXfzsF7QDNEgGC38RQZueT9fqdykVVolUtVYuEXvJRGixqPOcPopvQHXiSw==
',0,NULL,'SUCCESS',16,0),
	 (486,'20211002SIHBIDJ1000O9900000027','Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','1242','20211002SIHBIDJ1010O0100000025','2021-10-02 10:27:28.027','eJyNUE1vwjAM/S8+Q+WYMkpuLeUjk1in0RviMNHCKtG0a4I0gfjvc/px44APThzb7728O0RXszVn
kHcI63qTNe626nJRZN2plTZWq7ZK7E/bdRXs1CZS8buAB8cI0urFvcXyI+S9sN+LihtrUI4cCEkI
RBqgETGZz7ENmsEIeDLOT91w/X00HlLgIQoPfe4umjy2PcxY4BgpFShpJikAZoqr47XMte302Wql
Pku7s+Yr/3VP62awoNXzXE6vJRjY0hKkeJtMhD8lP2CRzok/pU8g9/zv5qwvS52lFafnkAIT7IFp
Co+DM+UfW9tzmw==
',NULL,NULL,0,NULL,'ERROR-CIHUB',483,0),
	 (490,'20211002SIHBIDJ1000O9900000031','Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','1242','20211002SIHBIDJ1010O0100000029','2021-10-02 10:27:32.73','eJyNUE1vwjAM/S8+Q2U7MNbcWgojk6DT6G3aYaKFVVrTrgkSAvHfl/TjxmE+OHFsv/fybhCfzdac
QN4gappN3vrbus9lmfenVtpYrboqtd9d11ewV5tYJa8EdxcTyOp/7i1Xu8jtRcNeXF6dBuXJgZGJ
EHmERsQ0DLELQTABN5kUx364+TqYAPk5QKQAZ667bIvEDjBTwilyRih5IQWDY0rqw7kqtO312Xqt
3iq7t+a9+PVPL+1oQafnsZxBC49sWQWSnoSg2ZznvBDonbgofQT54f7dnvTPSudZ7dJjSMIUB2AO
4f7pTfkDUR1zjA==
',NULL,NULL,0,NULL,'ERROR-CIHUB',487,0);INSERT INTO public.proxy_message (id,account_name,account_number,account_type,call_status,customer_id,customer_type,display_name,error_message,full_request_mesg,full_response_mesg,intrn_ref_id,operation_type,proxy_type,proxy_value,request_dt,resident_status,resp_status,response_dt,scnd_id_type,scnd_value,town_name,chnl_trx_id,cihub_elapsed_time) VALUES
	 (27,'Fras Manse','14556','SVGS','SUCCESS','220000022','01','Andi aja',NULL,NULL,NULL,'55140','NEWR','02','frans.mazhar@gmail.com','2021-09-30 21:29:06.607','01',NULL,'2021-09-30 21:29:07.378','01','44443333','0300',26,771),
	 (30,'Fras Manse','14556','SVGS','SUCCESS','220000022','01','Andi aja',NULL,NULL,NULL,'55140','NEWR','02','frans.mazhar@gmail.com','2021-09-30 21:56:18.487','01',NULL,'2021-09-30 21:56:19.502','01','44443333','0300',29,1015),
	 (32,'Fras Manse','14556','SVGS','SUCCESS','220000022','01','Andi aja',NULL,NULL,NULL,'55140','NEWR','02','frans.mazhar@gmail.com','2021-09-30 21:58:53.069','01',NULL,'2021-09-30 21:58:53.926','01','44443333','0300',31,857),
	 (34,'Fras Manse','14556','SVGS','SUCCESS','220000022','01','Andi aja',NULL,NULL,NULL,'55140','NEWR','02','frans.mazhar@gmail.com','2021-09-30 22:01:15.876','01',NULL,'2021-09-30 22:01:16.913','01','44443333','0300',33,1037),
	 (36,'Fras Manse','14556','SVGS','SUCCESS','220000022','01','Andi aja',NULL,NULL,NULL,'55140','NEWR','02','frans.mazhar@gmail.com','2021-09-30 22:02:27.818','01',NULL,'2021-09-30 22:02:28.896','01','44443333','0300',35,1078),
	 (38,'Fras Manse','14556','SVGS','SUCCESS','220000022','01','Andi aja',NULL,NULL,NULL,'55140','NEWR','02','frans.mazhar@gmail.com','2021-09-30 22:03:49.806','01',NULL,'2021-09-30 22:03:50.777','01','44443333','0300',37,971),
	 (40,'Fras Manse','14556','SVGS','SUCCESS','220000022','01','Andi aja',NULL,NULL,NULL,'5540','NEWR','02','frans.mazhar@gmail.com','2021-09-30 22:04:05.472','01',NULL,'2021-09-30 22:04:05.795','01','44443333','0300',39,323),
	 (43,'Fras Manse','14556','SVGS','SUCCESS','220000022','01','Andi aja',NULL,NULL,NULL,'5540','NEWR','02','frans.mazhar@gmail.com','2021-09-30 23:28:36.307','01',NULL,'2021-09-30 23:28:37.085','01','44443333','0300',42,778),
	 (46,'Fras Manse','14556','SVGS','SUCCESS','220000022','01','Andi aja',NULL,NULL,NULL,'5540','NEWR','02','frans.mazhar@gmail.com','2021-09-30 23:36:36.291','01',NULL,'2021-09-30 23:36:37.034','01','44443333','0300',45,743);INSERT INTO public.settlement (id,crdt_account_no,crdt_bank_account_no,dbtr_account_no,dbtr_bank_account_no,orgnl_crdt_trn_bizmsgid,orign_bank,recpt_bank,settl_conf_bizmsgid,crdt_account_type,crdt_id,crdt_id_type,dbtr_account_type,dbtr_id,dbtr_id_type,full_message,receive_date) VALUES
	 (496,'987654321','890123456','123456789','654321098','20210912MNDRIDJA010O0100000003','INDOIDJA','MNDRIDJA','20210912INDOIDJA010HRB12361264',NULL,NULL,NULL,NULL,NULL,NULL,'eJy1lVtzmkAUgN/zK5h9rpldVIS8IZiETEUHtu1DJw9W1DoVZGCTJs3433u4CQuLWqc4yuC57H7n
smc/biQJjV/iabxB0p30AX9BoIfhoxeVAhDdc38TwXbr8aJUGFhBzAKroQLljP2MmmJQZNbIss2Z
ZT7piNMfbkTvxdvhUwFI950DTm3TuR5wvP0DWbayvCIZywRrRC6CxgQ/OmMi9xUiKwN09AIXc7Uu
vMLFMr7FWIYfuSUYVRd3X5epjUvp51JhRCuTHXfsYa1HZEqGdxjDNwskR0TmfvnirwLG133L9vfW
3Gcui52Q1XL8ENUbpYDOUyYIsxFjyUn9Cmm/hwklWpWUS2hSsGgT7IDBCtZ64AFh4v6dZ0ltakB9
THggAh+VByr8bD/3zHOvprnHIuvLY5CeK1HQt7P8k8Cje3jUslo0JAQxg1/26QvIYAvOk4g8RyPe
k77lREg3qMHrkm6I4Sitm8SgBZXwHM0jFr2nK37B1XzU6pra6p7HdscN0EyCMg2Q9Fw9bdU0lqE6
q7XgcFsBi8a/XMZ2Pncg0hKl9UlKVIMwfzDxRJizd5EcNHZW/yd9OnGlsTP7ZqOa0eFk3MmW+nLJ
Tgwiwa5to6vihZIEDpWRqtWB6kgNqKQZwpady5oaumFcEetGHOqpSX1xyOIrRRjxSVDD6xjUmNj6
/wK9rmHnVJpa1r/VL01LJ72qqSNlOOjLpKNedb8+uGdirQ4afvaFOx/WMRdsIZx+k+B1JyRoHSdH
ZoQJqodTJgUGtQx31wAPsYJHtSsoN3ViL2DF3BavRn8Hec1hMdzIQiOdbT11KbUKrAowD2A7WWza
AXU+W9ra80SDnmnRMrKsQ7GmNpu02aaXNG4+ZrpkVjWc3QNXMrefkcrVXL5nFsnzcHP4C04jVas=
','2021-10-02 11:26:45.916359'),
	 (500,NULL,'123456',NULL,'123456','20211002SIHBIDJ1010O0100000035','CENAIDJA','SIHBIDJ1','20211002CENAIDJA010R0200000899',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUltvgjAY/StLn9W0oAvyhqCTXZRAlz0sPjAujgQKgep0Zv99X1vLw6KZJbT92tNzvtsJzXbd
S7dF9gk5TbNMW7FbqLkoUrUyn3Wc+dJa8095KyzkzleO7z066AfGANH6xneRv5zBO3J+Nyu+wQdf
iCMDG4RgbGhqTHCIDSyGNZ2iAQKkl+UK3MRJNwIw/GREMJJU0T4REpQ+g+22mcfPtEOCh9ighNiG
ZRsYgbJXJ7sqY1z5y+uFH1Q84l3YyKOHVqdE+nfZPTUm91qNVtf11u2WlcDqs9xhKQgh+/2kTv8q
6Bz1CqZQ0NBVJcHnBFgyAdhCPxuowuEC+5yltIbpmsRay5gTrQI8//tDD1IGOS51wRSp66DsuVSG
rchd0PL2CJhXeAQV3/T8YZaLe++Dt06ScN0efbPI5nBTLisQ8KNYViK70TGuul1591akdVojDbtG
AtdRU1bghRfzWLo2Z/uyEShNT8FCmEAMMuonGgyh68amNYajsEsZV4FKCP1i0g9sYtVGIoItv6iv
+IhhjqFF+ohuRUO64IPxC9nUD/I=
','2021-10-02 11:28:22.545532'),
	 (504,NULL,'123456',NULL,'123456','20211002SIHBIDJ1010O0100000037','CENAIDJA','SIHBIDJ1','20211002CENAIDJA010R0200000946',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUl1vgjAU/SvLfUbTAnPMNwSd7EMJdNnD4oMDdCRSCFSnM/vvuy2Wh0UzS2h729Nz7tcRRtvm
pVnD8AhuVU3TWu4m7ZznabvygDeCB8qai091Ky3wxjM38B9d+MFhACuvfBcH0xG+o6d3o/wbfQik
OJjEpJQQU1MTSiJiEjnu7QEYgEg/W7Xgapk0fQTjT/uUgKKKd4mUYOwZba/OfHGi7VHSIyYj9tAa
DIkDqOyXybbIuGj9FeUkCAsRiyaq1NFDrVOi/DvvnhoDamk1VlzWm9drvkHWgK9cnqIQDN+P7elf
BZ0jrUAsB4wOOisU+JQARyVASiywCvsz7GOeshKnSxJzLWPdaRXk+d8ftlcy4HrMQ1OmrsGyr5Qy
bmXuwlrUB8S84iOs+KLjj7KVvPc/RO0midDt0TWLag4vFaoCoTjIZSaz+5anZVreYO+KZZGDhl0i
weu42hTohb8US+XamO82lURpeoYWEIoxqKifWNjDrrMtx8ajqEm5aANVEPbFlR/EkhEZbQRrcVa/
5aOmZd8OoIvoWjSmCz8cv9c+D+U=
','2021-10-02 04:36:10.851618'),
	 (513,NULL,'123456',NULL,'123456','20211002SIHBIDJ1110O0100000045','CENAIDJA','SIHBIDJ1','20211002CENAIDJA010R0200000734',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUttugkAQ/ZVmntXsLmgtbwha6UWJbJ8aHyiIpZGFwGq1xn/v7AI2aTRxCZdhz5wzc2aPMNpW
r9UarCPYRTGNS/U1qZ9pGtdv4YlKCk9Hc/mpd1UEznhme+6TDSdcHeD5jXmBNx1hHm3yRukP1uAp
cWCEUUoIa6kJJQvCiFr3hgkdQKS7SmpwEUZVD8F40x4loKmCXaQkOH/B2ClXrmxou5R0CeOUWaZp
MQao7ObRNlsJWdcr84nnZzKQ1aLQvx7L1hJd3+Xy9DL7D60az67rzcu12CCrJxJbxCgE1vux/vtf
ofWINgrEHEDnDJ1lGtwYMNQGkCGcljiF/QX2sYh5jo9rEvO/RkBR6GSwHe5gqAypcJiJ5sNP5Yhf
yvKAmDdMwjkum9r4frFK1L77IUs7imQ79PMR0CN3Yql99eVBvWbKs+ArTMp0c2fHqTyE0MKukeB2
UGwyrMINZahLG4vdplColp5jBIRiD7rxZ+538SyZxlCdpEUVC1k3qiH8W+g6iKE66tQdrOVF/ZqP
MsPsD+Dc0a1otAsvXL+k4AWN
','2021-10-02 05:44:23.665842'),
	 (516,NULL,'123456',NULL,'123456','20211002SIHBIDJ1110O0100000047','CENAIDJA','SIHBIDJ1','20211002CENAIDJA010R0200001478',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUstuwjAQ/JVqz4DWISmIW0ighLaAwFUPFQeaByARJ0oMhSL+vWs74VCBhKM81p6dmd3NGfr7
8r1cQ+8Mbp6PokJ9Dc1zu43MWwSilCLQ0VRu9KmKwBtM3MAfu3Ch1QCePZi3CEZ9ymNVXn/7Sx4C
JQ4WWowhWjU1MpyjhbSY3elCAwjpx4kB56uwbBGYbtZiCJpqcQiVBOdvFHtF7MuKtsmwiRZHp+fY
PccBUvazcJ/GQhq/MhsGs1QuZDnP9dZLUbdE+7ttr/Jm1Wo8va83LdZiR6yBSFwRkRD0vs5m979C
3SNmFBBtVX0NnaQaXDWgqxuAXbgsaQrHG+wDEfGMHvckplUhaHdAUehkcD3uUagaUtIwE81Hn6oj
s0IWJ8J8UBLNcVl548d5nKhz/1sWbhjKeujXX0CP3Iuk7utMntRronr2uRUruXoabzJxghp0j4KO
F/kuJQ8+JWljA3HY5QpVk3OKABlVoMt+5bMm/Ul2u2vT1ryMhDRlagj/EdoFtlU9DeN/LW/qGz5m
tW3nGa71PIqmZtFF6w+1HATq
','2021-10-02 05:54:57.249671');INSERT INTO public.users (id,"password","role",username) VALUES
	 (2,'$2a$10$5e3dB36HeRcozRgp8xQfw.tfD3Qsut8xu/NT9g/DSpVKg9Kzuitrq	','ADMIN','admin'),
	 (1,'$2a$10$5e3dB36HeRcozRgp8xQfw.tfD3Qsut8xu/NT9g/DSpVKg9Kzuitrq','USER','user');
