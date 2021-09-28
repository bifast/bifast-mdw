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
	cihub_elapsed_time int8 NULL,
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


-- public.vweb_tx_logs source

CREATE OR REPLACE VIEW public.vweb_tx_logs
AS SELECT chnl.request_time AS "timestamp",
    chnl.transaction_id AS uuid,
    ae.orign_bank AS source_bic,
    ae.recpt_bank AS destination_bic,
    chnl.debtor_account_number AS source_account_number,
    ae.account_no AS destination_account_number,
    ae.amount,
    'IDR'::text AS currency,
    chnl.status AS status_code,
    chnl.error_msg AS status_message,
    chnl.debtor_account_name AS source_account_name,
    chnl.creditor_account_name AS destination_account_name,
    'Account Enquiry'::text AS transaction_type,
    'Outbound'::text AS transaction_direction
   FROM channel_transaction chnl
     JOIN account_enquiry ae ON ae.chnl_trx_id = chnl.id
UNION
 SELECT chnl.request_time AS "timestamp",
    chnl.transaction_id AS uuid,
    ct.orign_bank AS source_bic,
    ct.recpt_bank AS destination_bic,
    ct.debtor_acct_no AS source_account_number,
    ct.creditor_acct_no AS destination_account_number,
    ct.amount,
    'IDR'::text AS currency,
    chnl.status AS status_code,
    chnl.error_msg AS status_message,
    chnl.debtor_account_name AS source_account_name,
    chnl.creditor_account_name AS destination_account_name,
    'Credit Transfer'::text AS transaction_type,
    'Outbound'::text AS transaction_direction
   FROM channel_transaction chnl
     JOIN credit_transfer ct ON ct.chnl_trx_id = chnl.id
UNION
 SELECT chnl.request_time AS "timestamp",
    chnl.transaction_id AS uuid,
    fict.debtor_bic AS source_bic,
    fict.credit_bic AS destination_bic,
    ''::character varying AS source_account_number,
    ''::character varying AS destination_account_number,
    fict.amount,
    'IDR'::text AS currency,
    chnl.status AS status_code,
    chnl.error_msg AS status_message,
    ''::character varying AS source_account_name,
    ''::character varying AS destination_account_name,
    'Credit Transfer'::text AS transaction_type,
    'Outbound'::text AS transaction_direction
   FROM channel_transaction chnl
     JOIN fi_credit_transfer fict ON fict.chnl_trx_id = chnl.id
UNION
 SELECT chnl.request_time AS "timestamp",
    chnl.transaction_id AS uuid,
    'SIHBIDJ1'::character varying AS source_bic,
    chnl.recpt_bank AS destination_bic,
    chnl.debtor_account_number AS source_account_number,
    chnl.creditor_account_number AS destination_account_number,
    chnl.amount,
    'IDR'::text AS currency,
    chnl.status AS status_code,
    chnl.error_msg AS status_message,
    chnl.debtor_account_name AS source_account_name,
    chnl.creditor_account_name AS destination_account_name,
    'FI Credit Transfer'::text AS transaction_type,
    'Outbound'::text AS transaction_direction
   FROM channel_transaction chnl
     JOIN payment_status ps ON ps.chnl_trx_id = chnl.id
UNION
 SELECT ae.cihub_req_time AS "timestamp",
    ae.bizmsgid AS uuid,
    ae.orign_bank AS source_bic,
    ae.recpt_bank AS destination_bic,
    ''::character varying AS source_account_number,
    ae.account_no AS destination_account_number,
    ae.amount,
    'IDR'::text AS currency,
    ae.call_status AS status_code,
    ''::character varying AS status_message,
    ''::character varying AS source_account_name,
    ''::character varying AS destination_account_name,
    'Account Enquiry'::text AS transaction_type,
    'Inbound'::text AS transaction_direction
   FROM account_enquiry ae
  WHERE ae.recpt_bank::text = 'SIHBIDJ1'::text;

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
','20210925CENAIDJA510R0200000013','ACTC',4282,456);INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
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
	 (4331,4355557.00,'Internet Banking','FARHAN AI','821751','Bambang Suo','7744404',NULL,'Credit Transfer','014','2021-09-26 16:21:37.471574','2021-09-26 16:21:40.686754','SUCCESS','155221');INSERT INTO public.corebank_transaction (transaction_id,addt_info,channel_ref_id,credit_amount,creditor_bank,cstm_account_name,cstm_account_no,cstm_account_type,debit_amount,debtor_bank,status,transaction_type,trns_dt,chnl_trx_id) VALUES
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
	 (4333,'Info tambahan disini','155221',NULL,NULL,'Bambang Suo','7744404','CACC',4355557.00,NULL,'SUCCESS','Debit','2021-09-26 16:21:38.139169',4331);INSERT INTO public.credit_transfer (id,settlconf_bizmsgid,amount,call_status,cihub_req_time,cihub_resp_time,crdttrn_req_bizmsgid,crdttrn_resp_bizmsgid,cre_dt,creditor_acct_no,creditor_acct_type,creditor_id,creditor_type,debtor_acct_no,debtor_acct_type,debtor_id,debtor_type,full_request_msg,full_response_msg,intr_ref_id,msg_type,orign_bank,recpt_bank,resp_status,reversal,chnl_trx_id,cihub_elapsed_time) VALUES
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
','21211','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,4174,NULL),
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
','211411','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,4181,NULL);
INSERT INTO public.credit_transfer (id,settlconf_bizmsgid,amount,call_status,cihub_req_time,cihub_resp_time,crdttrn_req_bizmsgid,crdttrn_resp_bizmsgid,cre_dt,creditor_acct_no,creditor_acct_type,creditor_id,creditor_type,debtor_acct_no,debtor_acct_type,debtor_id,debtor_type,full_request_msg,full_response_msg,intr_ref_id,msg_type,orign_bank,recpt_bank,resp_status,reversal,chnl_trx_id,cihub_elapsed_time) VALUES
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
','21002','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL,4202,NULL);INSERT INTO public.domain_code (id,grp,"key",value) VALUES
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
	 (8,'org.apache.camel.http.common.HttpOperationFailedException','HTTP call error');INSERT INTO public.fi_credit_transfer (id,settlconf_bizmsgid,amount,cre_dt,credit_bic,debtor_bic,intr_ref_id,orign_bank,req_bizmsgid,saf,saf_counter,status,call_status,cihub_req_time,cihub_resp_time,full_request_msg,full_response_msg,resp_bizmsgid,response_status,chnl_trx_id,cihub_elapsed_time) VALUES
	 (4260,NULL,781000.00,'2021-09-25 06:45:34.776','CENAIDJA','SUNIIDJA','6241','SIHBIDJ1','20210925SIHBIDJ1019O0300000015',NULL,NULL,NULL,'SUCCESS','2021-09-25 06:45:34','2021-09-25 06:45:35','eJyNUstuwjAQ/BefAdmB0JpbHhRclYeC6QVxgCRQ1MREzkaCIv69aye0qtSKWoq91u7szE58IX5V
Tso9GVyIVxTjRJvoqd4Ph6Q+lVAlKGFvM3izWXMjCzH2RfjMyBVXi8jjP3H+JBKI8xqcf/hADcKQ
E4c6jHLHvbWmjM9ol9rFXNIiWBmmu7q42MRlh1KOH+tQjtlApyE0bdqUtx1X0v6g5w66PYJM4TGu
8lRBrS9OQOqdiUf6NrsV8ruORkT/RiPzP4haZLqd7eSpxDzD2wIgy4WyTDaegKEIXqIRMfMHVoc8
2ZLVhcxzqE0bqkQecftL0U9nsME95UiGzWXRiAlgf55XujDxXIM+I5xzq0ko0P67Vevl1q/XTVal
WPDwyLAZWm58iA1EhJE1dwv63oNZTr9+vJ37HiAYTr1vQITG1MqXJWjMr1SVZevrdW0e0iezHs8/
','eJyNkt9vgjAQx/+XPqtp+WFW3hBwdsnAQH1afNgEnMkoBOriZva/71qocYtGm1B617v7fnK9I5rt
u+dui7wj8ptmkbfqNO/33S7v/4KJTgqmrUS+61tloSCKfRY++egH1gjx+s68jC1mkEeGvNnuGxiY
EkcWtgimlmNKY0JTSrFaU2KjEYLIsCj74OZ1000wtuAjE4KRLpV9bhQaT6NsmcRZBN6gLUI5FB9j
OrYcbtme43q2g0A/rDf7qhCyp5b1nC0rmckubbTrsTWN0ZSXIRUgcR9co8ar63pJuxUfUJWJ0hc5
CCHv5dh7/ym4plODAmhM0egUGlc6eGgD1W3A1ARc4HA5nhqONTzY4QJCJHJew3aNI8H2gOIaJahz
G5oftAzyAx6AqfrbwYSUWhmOqsHLVrZfELOCJBiO9al+WpTqPnyT+iH8rbw1YquYnY1mkN+b+Gem
AUFtv65q5/M=
','20210924CENAIDJA019R9900000613','ACTC',4257,NULL),
	 (4264,NULL,781000.00,'2021-09-25 06:50:53.783','CENAIDJA','SUNIIDJA','6000','SIHBIDJ1','20210925SIHBIDJ1019O0300000017',NULL,NULL,NULL,'SUCCESS','2021-09-25 06:50:53','2021-09-25 06:50:54','eJyNUstuwjAQ/BefAdlBKZhbHhRclYeC6QVxgCRQ1MREzkaCIv69aye0qlRELcVea3d2Zie+EL8q
J+WeDC7EK4pxok30XO+HQ1KfSqgSlLC3GbzbrLmRhRj7Inxh5IqrReTxnzh/EgnEeQ3OP3yiBmHI
iUMdRrnj3lpTxme0S+1iPdIiWBmmu7q42MRlh1KOH+tQjtlApyE0bdqUtx1X0qeBSwdulyBTeIyr
PFVQ64sTkHpn4pG+zW6F/K2jEdG/0cj8DlGLTLeznTyVmGd4WwBkuVCWycYTMBTBazQiZv7A6pAn
W7K6kHkOtWlDlcgjbvcU/XYGGzxSjmTYXBaNmAD253mlCxPPNegzwjm3moQC7X9YtV5u/XrbZFWK
Bb0+w2ZoufEhNhARRtbcLehHD2Y5/f7xdu5HgGA49X4AERpTK1+WoDG/UlWWra/XtXlIX7Qvz0E=
','eJyNklFvgjAQx7/LPatpQXTwhoCzSwYG6tPiwybgTEYhUBc343fftYDZFo02ofSud/f/5XpHmO2b
52YLzhHcqlqktTrN2323S9u/YKKRgmkrku/6VlngBaHL/CcXTrgGwMs78xK2mGEe7fJmu29kYEoc
DGJQYhvjvjShdmzbBBedTikMACP9LG+Dq9dNMyLEwI+OKAFdKvncKDQeB8kyCpMAvV6d+bIrPiT2
0Bhzw3Qs4lhjQH2/3OyLTMiWWpZztixkIpu40q7Hum+MprwMqQiJMTF7NV5c14vqrfjAqkzkrkhR
CJyXY+v9p2D1neoVCH2AwTk0LHRw1wZbt4HYfcAFDouTieYw4bTGBztcQAhEykvcrnFExOxQpr0S
1rkNzQ9aBlyPe2iq/jY4IblWxqNq8LKW9RfGrDAJh2N9rh9nubr336R+CHcrb43YKmS/RtNL7038
M9OIoLYfqWXn8A==
','20210924CENAIDJA019R9900001771','ACTC',4261,NULL),
	 (4290,NULL,711660.00,'2021-09-25 19:44:04.448','CENAIDJA','SUNIIDJA','6011','SIHBIDJ1','20210925SIHBIDJ1019O0300000034',NULL,NULL,NULL,'SUCCESS','2021-09-25 07:44:04','2021-09-25 07:44:05','eJyNUstuwjAQ/BefAdkhUJlbHhRclYeC6QVxgCRQ1MREzkaCIv69aye0qlRELcVea3d2Zie+EL8q
J+WeDC7EK4pxok30XO+HQ1KfSqgSlLC3GbzbrLmRhRj7Inxh5IqrReTxnzh/EgnEeQ3OP3yiBmHI
iUMdRrnTu7WmjM9ol9rVdUmLYGWY7uriYhOXHUo5fqxDOWYDnYbQtGlT3nZ6kvGB6w6oS5ApPMZV
niqo9cUJSL0z8UjfZrdC/tbRiOjdaGR+h6hFptvZTp5KzDO8LQCyXCjLZOMJGIrgNRoRM39gdciT
LVldyDyH2rShSuQRt3uKfjuDDR4pRzJsLotGTAD787zShYnnGvQZ4ZxbTUKB9j+sWi+3fr1tsirF
gifG+n2KlhsfYgMRYWTN3YJ+9GCW0+8fb+d+BAiGU+8HEKExtfJlCRrzK1Vl2fp6XZuH9AW7wM9I
','eJyNkl1vgjAUhv9Lr9W0BZeUOwScXTIwUK8WLzY+nMkoBOriZvbfd1rosi0abULpxznnffL2nNDi
0D/2O+SdkN+2q6LTq+Uw7/fF8Jdc9kpys0vUq7nVOxREsc/DBx99wZgg0dyYl/HVAvLImLfYfwID
1+KIYkowo3NbGhOWMoZhEHw3RxMEkWFZDcHtc97PMKbwkRnByJTK3nONJtIoWydxFsFp0JWhGotP
MZvSuSDUc10Puwj0wyY/1KVUA7Vqlnxdq0z1aWuO7jtrjKE8D2kIHYdaNVFf1ku6nXyDqlxWvixA
CHlPp+H0v4J1alTA2NEe2NC4NsGjDczYgJkNOM/BLMcWHux4BiGShWhgusSRYGdEca0S1LkOLY5G
BvmBCGCr/e2hQyqjDEtt8LpT3QfEbCAJmmP7Uz8tK30fvijzEP5OXWuxTcx/tWZQ3Jr4p6cBQU/f
lT3n5g==
','20210925CENAIDJA019R9900001065','ACTC',4287,NULL);INSERT INTO public.inbound_counter (tanggal,last_number) VALUES
	 (20210915,50000032),
	 (20210911,50000006),
	 (20210912,50000006),
	 (20210913,50000052),
	 (20210921,50000006),
	 (20210914,50000018),
	 (20210926,50000093),
	 (20210925,50000043);INSERT INTO public.m_bic (bank_code,bank_name,bic_code,change_who,created_date,last_update_date) VALUES
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
	 ('Hadi');INSERT INTO public.payment_status (id,bizmsgid,error_msg,intern_ref_id,orgn_endtoendid,request_dt,request_full_message,response_dt,response_full_message,retry_count,saf,status,chnl_trx_id,cihub_elapsed_time) VALUES
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
',0,NULL,'SUCCESS',4331,0);
