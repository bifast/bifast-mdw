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
	log_message_id int8 NULL,
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


-- public.cb_account definition

-- Drop table

-- DROP TABLE public.cb_account;

CREATE TABLE public.cb_account (
	id int8 NOT NULL,
	account_no varchar(255) NULL,
	account_type varchar(255) NULL,
	additional_info varchar(255) NULL,
	amount numeric(19, 2) NULL,
	creditor_account_number varchar(255) NULL,
	creditor_account_type varchar(255) NULL,
	creditor_id varchar(255) NULL,
	creditor_name varchar(255) NULL,
	creditor_resident_status varchar(255) NULL,
	creditor_status varchar(255) NULL,
	creditor_town_name varchar(255) NULL,
	creditor_type varchar(255) NULL,
	intr_ref_id varchar(255) NULL,
	CONSTRAINT cb_account_pkey PRIMARY KEY (id)
);


-- public.cb_credit_transfer_request definition

-- Drop table

-- DROP TABLE public.cb_credit_transfer_request;

CREATE TABLE public.cb_credit_transfer_request (
	id int8 NOT NULL,
	account_number varchar(255) NULL,
	account_type varchar(255) NULL,
	amount varchar(255) NULL,
	creditor_name varchar(255) NULL,
	payment_info varchar(255) NULL,
	request_time date NULL,
	transaction_id varchar(255) NULL,
	CONSTRAINT cb_credit_transfer_request_pkey PRIMARY KEY (id)
);


-- public.cb_debit_transfer_request definition

-- Drop table

-- DROP TABLE public.cb_debit_transfer_request;

CREATE TABLE public.cb_debit_transfer_request (
	id int8 NOT NULL,
	account_number varchar(255) NULL,
	account_type varchar(255) NULL,
	amount varchar(255) NULL,
	debitor_name varchar(255) NULL,
	payment_info varchar(255) NULL,
	request_time timestamp NULL,
	transaction_id varchar(255) NULL,
	CONSTRAINT cb_debit_transfer_request_pkey PRIMARY KEY (id)
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
	name varchar(255) NOT NULL,
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
	log_message_id int8 NULL,
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

INSERT INTO public.account_enquiry (id,account_no,amount,cre_dt,intr_ref_id,orign_bank,recpt_bank,bizmsgid,call_status,cihub_req_time,cihub_resp_time,error_message,full_request_msg,full_response_msg,resp_bizmsgid,response_status,log_message_id) VALUES
	 (3682,'417832',825200.00,'2021-09-22 09:31:31.205','3124615','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000034',NULL,'2021-09-22 09:31:31','2021-09-22 09:31:31',NULL,'eJyVUl1vwiAU/S88qwGqW/WtLX6wTF2U7GXZg7b1I7PU0GviZvzvu4BNtmVmjrRw4d5zz+GEE4kP
1bhak96JRPv9KDM2Gvh5u838qqWuQEu3m8LGZe2OzOUoluKBkTOOBlHljbikP4kQF11w8fYDNUhL
TjjljHY5r1t3GJ1SRt0I2qRBsFLkK1+8X6RVi9IQf9aiIWYTkwu4tGnSbpNzRbu9gOFHkEmU6aHI
NXh9UA5kUkFhkgyUWdnDoalNcIp+F0QDxtt3rFPzqeIKY4NMltOVOlaYt7s5wK6Q2jG5eAzOjsfZ
kFgjvA51dCUvJ/JUgHevrzNV4nRN0XeLsMFfypFMajDxm5MRFc6R58XukCMw5B1OKZpqL5i+44kU
MwtJNmYdW+NFP1aYFEuwXp19FK3hH+/F3fYGzNe34jGe0aHTFOrCH7A2uw8D7h7Yq50+AXMk1f0=
','eJyNUdtOwkAQ/RWzz0B2ty0Ib0sLUo3QlPXBGB6aXrAJ3TbtoiLh352dUoNGEqfd+5k5c2aOZLpv
HpstmRyJqKpFUpvdvJ3zPGlX5atGKx9PK/2Kr+ZE3NlS+N69ICewHpHlP/3W/mIKfuzsN80/IQff
kBNOOaNjzrvQDqMh5RSMsdGY9AggvTRrwVUUNwNKOQw2YBRe3Tr19DlMn477nEvKJxaDnwCTV8b7
IlW6zU+Xcz8o9Fo3YYVXd3VXAszn73TQHMfq2GRxnU9++CoTKgEKMnmBKtRbtZupRJYw/SDoSgIE
K3omsWzgQBeIcwVMLcbtIXOIIUMaIlzpwtHIaqAFGTLD1ugKal0fAPME4aH6m+/4YZqZdzfRqD/Q
B7MsjbbncpvfiCQqiGmWQYg41l0zf7XWZqNbiyNSVpeMrnBdgu1eV7sC7rxIR5jaTL3tqkty40go
Aw0Y8kEGfU6HjNkgHS7DJlG6lYog+a4wT2qhptMGPrAvHu3RDQ==
','20210922CENAIDJA510R0200001179','ACTC',NULL),
	 (3689,'417832',825200.00,'2021-09-22 09:40:26.017','312115','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000037',NULL,'2021-09-22 09:40:26','2021-09-22 09:40:26',NULL,'eJyVUstuwjAQ/BefAdkGSuCWxDxcFajA6qXqAZLwUImDnEWiRfx71zaRaFVUainO2ruzMx7tiUSH
clyuSe9Ewv1+lBobDfy+3ab+r6UuQUt3msLGZe2JzOUokuKRkTOuGlHFnbi4PwkRF15w0fYTNUhL
TjjljHY5r1q3GZ1SRt1qdkiNYKXIVr54v0jKBqUBfqxBA8zGJhNwaVOn3TrninZ7LdrjDwSZRJEc
8kyD1wfFQMYl5CZOQZmVvRyaygSn6HdBqIRxxtoVn8pvMNbIZDldqWOJeYanOcAul9oxuXgMzo6n
2ZBYI7wOdXQlryfynIN3r69TVeB2S9F3i7DBX8qRTGow0buTEebOkZfF7pAhMOBtTimaah+YfOCN
FDMLiTdmHVnjRT9SmBRLsF6dfRSu4R/z4l57B+Z6VjzGMzp0kkBV+APWYp2gyd2AvdntC3N11fk=
','eJyNUm1PwjAQ/ivmPgNpuwFu30oHMo1IRv1k+LDsBUlYt2xFRcJ/99oxg0YSb1lfn7vnnrseYbJv
HpsN+EfgVTVPa7OateN2m7azClWjVWh3T/rV3podiOmCh8E9hxNaD2T5T79VOJ+gHz37TbafmENo
yIERRonHWBd6SElEGDHmOh70AJFBlrfgKk6aASEMfzqgBG9FnQX6HKZPvD5jkjDfJT4bATIFZbIv
MqXb/HQ5C5eFXukmquzRXd2VwObzdzrWHOJ1bLK4zic/QpVzlSIF+C9YhXqjdlOVyhKHHwRdSZDg
iXQkY+SwLhjnChhRlFE6BENmaYALKXBrZDXYgtwy49LoWta6PiDmGR2x+uvv+FGWm3uRaqt/qQ9m
WhhtmH+dxTdc6VKB6ZcB8STRXT9/ddel41uHWaSsLkkFFwJsx1fVrsCzINaxzW6q3nbVJb9xBEJR
hg35IJd9RjzXHTn2EURNqnSr1oLku7KpYl+MrNMaP7Qv2KrSWQ==
','20210922CENAIDJA510R0200000439','ACTC',NULL),
	 (3696,'417832',825200.00,'2021-09-22 09:48:00.29','31415','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000040',NULL,'2021-09-22 09:48:00','2021-09-22 09:48:00',NULL,'eJyVUstuwjAQ/BefAa1NUAO3JObhqkAFVi9VD5CEh0oSlCwSLeLfu7aJRKuiUktxdr07nvFoTyw8
VONqzXonFuz3o6Q00cDt223i/rnKK8yVzaa4sVWTsbkahUo+cnam1WC6uBMX9ScB4YILLtx+kgZl
yJkAwaErRH11h8MUONjlAWsw6pTpyjXvF3HVAvDp4y3wqRqVqcTLNU3oNoXQ0O15fg+AEZMs4kOW
5uj0YTFQUYVZGSWoy5U5HJa1CVbR74JotbnHOzWfzm4wNthkOV3pY0V1TtkccZep3DLZeIzWjqfZ
kBkjnA59tC2vJ/acoXOvnye6oO2Wou8W0QV/KScylWMZvlsZQWYdeVnsDikBfdERAC37gCj+oBMl
ZwYSbcp1aIyX/VBTUS7ReHV2UbDGf8yLfe0dmOtZcRjHaNFxjHXjD5jHH/y2sAP2ZrYvYknV7w==
','eJyNUl1vgjAU/StLn9XcFkTHG4JOZqYEuqfFB8KHM5FCoGxzxv++2yKLW2ayS2hpe+495/RyIrO2
eWp2xD4Rp6qWaa2+Ft2436fdLHzRSOHr1Ua+6lO1Iu587fjeo0POGAPCy3/mRf5yhnn0kjfbf6IG
X5ETBozCPWN96TGFEBhgULCmZEAQ6WV5B67ipBkBMHzpiAKeunXmyUuZIdwPGePAbHNqAxBk8sqk
LTIhO32yXPhBISPZhJXeeqj7K9B6/pajtZgTo2fjxW0+/uGL3BEpUhD7BW+h3onDXKS8xOEHQX8l
SLCBjgRM5UinYJ0bYAyDmnRMFJmmIY7LXVwqWw22INfM+Kl8BbWsj4h5BiXwvP2uH2a5OndTqf0H
8qimtfIWxUWbZnV8t2qbtoiJapnCOUki+5b+arBJJ1ODaSSvrnldx3WJbnpUHQrc82IZa4Fz8Xao
riWoRAIUneiSKx4MGUwsyzJB/QdhkwrZGdYg/i60WjC0s/MWH4wvpFjTsg==
','20210922CENAIDJA510R0200001068','ACTC',NULL),
	 (3703,'417832',825200.00,'2021-09-22 09:49:43.809','31215','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000043',NULL,'2021-09-22 09:49:43','2021-09-22 09:49:44',NULL,'eJyVUl1vwiAU/S88qwGqWfWtLX6wTF2U7GXZg7b1I7PU0GviZvzvu4BN3DIzR1q4cO+553DCicSH
alytSe9Eov1+lBkbDfy83WZ+1VJXoKXbTWHjsnZH5nIUS/HIyBlHg6jyTlzSn0SIiy64ePuJGqQl
J5xyRruc1607jE4po260A9IgWCnylS/eL9KqRWmIP2vRELOJyQVc2jRpt8m5ot1eG7+AIJMo00OR
a/D6oBzIpILCJBkos7KHQ1Ob4BT9LghHwDjr1HyquMHYIJPldKWOFeYZ7uYAu0Jqx+TiMTg7nmZD
Yo3wOtTRlbyeyHMB3r2+zlSJ0y1F3y3CBn8pRzKpwcTvTkZUOEdeFrtDjsCQdzilaKq9YPqBJ1LM
LCTZmHVsjRf9WGFSLMF6dfZRtIZ/vBd32zsw12/FYzyjQ6cp1IU/YG32EAbcPbA3O30Bg2jWAQ==
','eJyNUttugkAQ/ZVmn9XsLnjjDUErbWoJbJ8aHwgXayILYde21vjvnR3E2KYmHcJez8yZM7NHMtur
J7UhzpG4db3MGrNatON2m7WzDKTSMsDds37DW7Mj3nzlBv6DS05gPSKqf/rFwXIGfuzsN9t+QQ6B
ISecckannHehh4xGlFMwZo046RFA+nnRguskVQNKOfxswCjcek3u63OYPp32OReUO/bUsW0CTH6V
7stc6jY/XS2CsNSxVlGNR/dNVwLM5+900Eb2hU2Ut/nEZyALV2ZAQZxXqEKzkbu5zEQFww+CriRA
8EzPJLYFHOgCcW6AwSzG2ZAYMqQhric82BpZClpQIDMsja6w0c0BMC/gB9VfX+JHeWHuvUyj/lAf
zLQy2uK9ymV1BzKaPCGmYQblpqnuGvqrvTYbTyyOSFFfs3qu5xFseVzvSjjzE51genP5vquvEzCO
hDLQgSEfRdiHVzAZ22M+hcNIZVK3chEkPiTmSi3UdVrDB/YN3WfS3Q==
','20210922CENAIDJA510R0200001362','ACTC',NULL),
	 (3710,'8417832',825200.00,'2021-09-22 09:53:36.272','32415','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000046',NULL,'2021-09-22 09:53:36','2021-09-22 09:53:36',NULL,'eJyVUstuwjAQ/BefAdkOoYFbEvNwVaACq5eqB0jCQyUOShaJFvHvXdtEolVRqaU4u94dz3i0JxId
qnG1Jr0TCff7UVqaaOD27TZ1fy11BVrabAobWzUZmctRJMUjI2dcDaKKO3FxfxIiLrzgou0napCG
nHDKGe1yXl/tMzqljNrV7pAGwU6RrVzzfpFULUoD/FiLBliNy0zA5Zom7TY5V7Tb872e1yHIJIrk
kGcanD4oBjKuIC/jFFS5MofDsjbBKvpdEC6Pt5lf86n8BmODTJbTlTpWWGeYzQF2udSWycZjsHY8
zYbEGOF0qKNteT2R5xyce32dqgK3W4q+W4QX/KUcyaSGMnq3MsLcOvKy2B0yBAbc55SiqeaByQee
SDEzkHhTriNjvOhHCotiCcars4vCNfxjXuxr78Bcz4rDOEaLThKoG3/AgjZ7CDxuJ+zNbF9eu9Y/
','eJyNUdtugkAQ/ZVmn9XsLiCXNwSt2FQJbJ8aHwgXawoLgbWtNf57ZxdpbFOTDmEvs2fmnJk5odmh
e+x2yDkht2mWWStPi37d77N+5wHvBA/UbSNe1Ku8IW++dgN/5aIz2Aix+p9xcbCcQRy5xM32n6Ah
kOSIYkqwTemQ2iA4whSDEWtqoRECpJ8XPbhJ0m6CMYWfTAiGV6/NfXFJM8b2mFKGqWNojjZFwOTX
6aHKuej1iXoRhJWIRRc1ynXfDi1Qev6Wo8zQ7YGNVbf52EfAC5dnQIGcZ+hCu+PlnGeshuUHwdAS
INjgC4k+BQ4VAnlugME0qhMDSTJFg1yPeXCVZXUwgkIxw1HWFbaiPQLmCeKg+9vv/FFeyHcvE6r+
UBzltpa1xcek6g7l3ap+rZGclwS5aSqGef6arqUT09KogrLmmtVzPQ+pkcdNWYHPT0Si5M35W9lc
C5CBCBOoQ+V8YOGYYts0TdvUwBl1GRd9uQrE3rnSijVV13kLH9gX93XS6w==
','20210922CENAIDJA510R0200001868','ACTC',NULL),
	 (3718,'8417832',825200.00,'2021-09-22 10:06:40.954','3225','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000049',NULL,'2021-09-22 10:06:41','2021-09-22 10:06:41',NULL,'eJyVUstuwjAQ/BefAdkG2sAtiXm4KqQCq5eqB0jCQyUOchaJFvHvXdtEaquiUktxdr07nvFoTyQ6
VJNqTfonEu7348zYaOj37Tbzfy11BVq6LIGNq9qMzOU4kuKBkTOuBlHljbh4MA0RF15w0fYDNUhL
TjjljPY4r6/uMppQRt3q9EiDYKfIV755v0irFqUBfqxFA6zGJhdwuaZJe03OFaN9etfvUIJMokwP
Ra7B64NyKOMKChNnoMzKHo5MbYJT9Lsgu9qcd2s+VVxhbJDpMlmpY4V1htkcYFdI7ZhcPAFnx+Ns
RKwRXoc6upaXE3kqwLs30Jkqcbum6LtFeMFfypFMajDRm5MRFs6R58XukCMw4F1OKZpqH5i+44kU
MwuJN2YdWePFIFJYFEuwXp19FK7hH/PiXnsD5uuseIxndOg0hbrxByzosPugzd2EvdrtEyr41iE=
','eJyNUW1vgjAQ/itLP6spRVH4VotOtkwJdp8WPzBeHIkUUuqcM/73XYssbpnJjtCX63P3PHd3QtN9
89RskXdCtK4XqdSnebsWRdruIhCNEoG5rdSbedU3xGZLGvgPFJ3BeohX/4xbB4spxFmXuGnxCRoC
TY4IJhZ2CelSjywcYYK1jSYO6iFA+lnegus4aQYYE/itgYXhlcnMV5c0fez2CeHY9rDjDYGsh/wq
2ZeZUK0+Vc2DsFRr1US1cd3LrgVGz99yjI3dccfGy9t8/CMQORUpUCDvBbogt2I3EymvYPlB0LUE
CFb4QjJ0gcOEQJ4bYG02ISOkyQwNoowzuOqyGhhBbpjhqOsKpZJHwDxDGHR/850/ynL9zlJl6g/V
UW9LXRvol1l8R8vXWB5iVSA9Mw2kSaK6mf6a8GRojSc2MVBeXzMzyhgyY1/XuxJ8fqxiI3Em3nf1
tQgdiLAFtZicjzzsQwcci7iODc6oSYVqSzYgfhBGL7ZNbecNfGBfUjLUMA==
','20210922CENAIDJA510R0200000586','ACTC',NULL),
	 (3727,'8417832',825200.00,'2021-09-22 10:16:29.935','32115','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000054',NULL,'2021-09-22 10:16:29','2021-09-22 10:16:30',NULL,'eJyVUstuwjAQ/BefAdmGtIFbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEaquiUktxdr07nvFoTyQ+
VONqTXonEu33o8zYaOD37Tbzfy11BVq6bAobV7UZmctRLMUDI2dcDaLKG3FJfxIhLrrg4u0HapCW
nHDKGe1yXl8dMDqljLoVdEiDYKfIV755v0irFqUhfqxFQ6wmJhdwuaZJu03OFaM9dtfjXYJMokwP
Ra7B64NyIJMKCpNkoMzKHg5NbYJT9LsgXG3OWFDzqeIKY4NMltOVOlZYZ5jNAXaF1I7JxWNwdjzO
hsQa4XWoo2t5OZGnArx7fZ2pErdrir5bhBf8pRzJpAYTvzkZUeEceV7sDjkCQx5wStFU+8D0HU+k
mFlIsjHr2Bov+rHColiC9erso2gN/5gX99obMF9nxWM8o0OnKdSNP2Bhh92Hbe4m7NVunzom1ik=
','eJyNUttugkAQ/ZVmn9XMLmiBNwSt2FQNbJ+aPhAu1hQWAmtba/z3zi7S2KYmHcJeZ+acM7NHMt23
D+2WOEfi1vUibdRq3o27XdrNIhCtFIHereWLvlU74s1WbuAvXXJCGxBe/TMuChZTjKPnuOnuEzkE
CpwwYBRsxvrUYwohMNBmTciAoKef5Z1zHSftCIDhT0cU8NZrMl+e0wzBHjLGwXDoxDGAIJJfJfsy
E7LjJ6t5sCllJNuw1kd3TV8CzedvOsqoSc0ejZfX8fhHIHJXpAhBnCesQrMVxUykvMLhB0BfEgRY
QwcCY4WhQzDPFWc0g1E6JgpMwxDX4x5ulawWW5BrZFwqXZtGNgf0ecQ4rP7zd/4wy9W9l0qtfyMP
alopbcvqtbqJDnHZ7gui+qWc3CSRfT9/ddcy6a1lMO3K60tUz/U8olse1UWJZ34sY01vJt6K+pKA
CiRAUYfOec83QwY2WKZtqlcQtqmQnVztxN+F5gqG1nV6xg/tC7m80sE=
','20210922CENAIDJA510R0200000086','ACTC',NULL),
	 (3736,'8417832',825200.00,'2021-09-22 10:27:22.635','33305','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000059',NULL,'2021-09-22 10:27:22','2021-09-22 10:27:22',NULL,'eJyVUstuwjAQ/BefAdlOIwK3JObhqkAFVi9VDyUJD5U4yFkkWsS/d20Tqa2KSi3F2fXueMajPZHk
UE/qNemfSLzfj3Njo6Hft9vc/7XUNWjpshlsXNVmZCHHiRT3jJxxtYiqbsSlg2mMuPiCS7YfqEFa
csIpZ7THeXN1yOiMMupW2CMtgp2iWPnm/WtWdyiN8GMdGmE1NYWAyzVt2mtzrhjt826fc4JMosoO
ZaHB64NqKNMaSpPmoMzKHo5MY4JT9LsgXEEQ0LDhU+UVxhaZLmcrdayxzjBbAOxKqR2Tiyfg7HiY
j4g1wutQR9fyfCKPJXj3BjpXFW7XFH23CC/4SzmSSQ0meXMy4tI58vS6OxQIjHjIKUVT7QOzdzyR
Ym4h6casE2u8GCQKi2IJ1quzj+I1/GNe3GtvwHydFY/xjA6dZdA0/oBFd6wbBdxN2IvdPgE+Z9Yt
','eJyNUdtugkAQ/ZVmn9UsS0HhbQWttKka2D41PhAulkQWAmtba/z3zg5ibFOTDmFvcznnzBzJdN8+
t1viHgmv60Xa6NO8W4si7XYZyFbJAG8r9YZefSPebMkD/5GTE9iAiOqfeVGwmEKecc6bFl/AIdDg
hFFmUIexvrRl0JAyimbbZEAg0s/yLriOk3ZEKYPfGBkUvF6T+epcZkidIWOCmi4bu4wRQPKrZF9m
UnX8VDUP1qWKVBvW+PTQ9C1APn/TQTOdC5oob+OJz0DmXKYAQdxX6EKzlbuZTEUFyw+AviUAsKJn
EMsBDEyBOjeCNRfTpBbRYAhDuCc8uGpZLYwgR2Q4al3rRjUHiHmBPOj+5lI/zHLt91KF+tfqoLel
1sbTQh3iuygu92nWxESPTMfxJFH9SH8NeHJvjCcmw1BRXwN73PMITj2qdyW8+bGKkeFMvu/qaw46
kVADpGDNJ7EeMmraNrMcLTdsU6k6xRgkPiTSpSZKO23gA/sGYpLT1w==
','20210922CENAIDJA510R0200000066','ACTC',NULL),
	 (3745,'96599',52000.00,'2021-09-22 12:11:51.142','61588','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000064',NULL,'2021-09-22 12:11:51','2021-09-22 12:11:51',NULL,'eJyVUk1vwjAM/S85F5REK6Lc2qZApgETRLtMO0BbPjSaotRIbIj/PieBaZuGxiLVtWM/++XJR5Ls
m1GzIr0jiXe7YWGs1/d2syn8X0vdgJYumsDaZW1EZnKYSHHPyAlPQFR9Iy7NxjHi4jMu2bwjB2mH
E045oxHnl9YhoxPKqDudOxIQrBTl0hfv5nnTprSLH2vTLmZTUwo4t2nRqMW5YrzHWC9EkgERdb6v
Sg2eH9R9mTZQmbQAZZb2cmAuIjhGvxOyVFjY/ZynqisTAzJeTJbq0GDeRjOAbSW1m+T8ETg5HqYD
YoXwPNTBlTwfyWMFXr1MF6pGc43Rd4mwwV/McZjUYJJXRyOunCJP8+2+RGDIsQ41te/L3/BCiqlF
pGuzSqzuIksUJsUCrFQn78Ur+Me6uMfegPm6Kh7jJzp0nsOl8Acs6oRR5NbrxZoP3lLVpg==
','eJyNUttugzAM/ZUpz22VpFABbxR6odNaRLOnqQ+IS4cEAUG6rZv277NDmbpplWZErsc+x3Y+yPzU
PXRH4nwQt2nWaYurZT8WRdrPMpCdkoHe7dSzvsUd8RZbN/A3LvkEGxFR/9NvH6zn4McufvPiHTQE
SE445YzanA+hTUYjyimaZRhkRADpZ3kPbuKkm1DK4WcTRuHWazNfXcKMqT3mXFDTYcwxgWxE/Do5
VZlUvT5VL4OwUnvVRY0+WrVDCbSev+X0ZpgDm6hu84m3QOauTIGCOE9QhfYoy4VMRQ3DD4KhJECw
oxeSGearXSDODTDCmGlZBMk0DYk2noAtptVBC3LNDEvMK2xVewbMI1QMqn/4jh9lOd57qdL5h+qM
0xZzWxVlLI93blo0J9XWBFuGODdJ1NDSXw22Z6Zta6Bormk91/OI7vm+KSs482MVa30L+VI21wrQ
kYDIUR/xXoRjeAbMMqZTGw6jLpWqz1eDxKvUYumUUmQ4wAf2Bf5B054=
','20210922CENAIDJA510R0200000844','RJCT',NULL),
	 (3747,'14555231',6200000.00,'2021-09-22 12:12:53.015','88432-722','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000065',NULL,'2021-09-22 12:12:53','2021-09-22 12:12:53',NULL,'eJyVUstuwjAQ/BefAdmGUOCWxDxcFajA6qXqAZLwUImDnEWiRfx71zaRaFVUaiXrx+7sjEc+kehQ
jss16Z1IuN+PUmNXAx+329TPWuoStHS7KWxc1u7IXI4iKR4ZOeOoEVXciYv7kxBx4QUXbT9Rg7Tk
hFPOaJfzqnXA6JQy6kY7IDWClSJb+eL9IikblHbwZw3awWxsMgGXNnXarXOuGO/hFzQJMokiOeSZ
Bq8PioGMS8hNnIIyK3s4NJUJTtHvgjqtJq8/cF7xqfwGY41MltOVOpaYZ7ibA+xyqR2TW4/B2fE0
GxJrhNehjq7k9USec/Du9XWqCgy3FH23CBv8pRzJpAYTvTsZYe4ceVnsDhkC29z1QlftDZMPPJJi
ZjHxxqwj67zoRwqTYgnWrLNfhWv4x4Nx170Dc/1YPMYzOnSSQFX4A8ZaQRDwpn+bbzZ8AdyO1o0=
','eJyNUV1vgjAU/StLn4dpC3XKG4LObpkS7LKHxQfChyORQqBuOuN/322RxS0zWQn9PPeec+49osmu
fWo3yD0ir67naaN3s24uirRbJZetktyclurNvOoT8qcLjwcPHjrBuEWi+mfcis8nEEfOcZPiEzRw
TY4opgSPKe1TM4IjTDEMMnIcdIsAGWR5B67jpB1gTOEnA4Lh1W+yQJ3TWHhsUSowcwl1mY2AKaiS
XZlJ1elT1YyHpVqpNqrN1X3Tl8Do+VuO0QKkPZsor/OJPZe5J1OgQO4rVKHZyO1UpqKC6QdBXxIg
WOKOBA8ZcJgQyHMFPHJsat1RrUbsDQ3yfOHDUdtqoQW5YYat9hU2qjkA5hnSQ/XX3/mjLNfvfqqM
/1Ad9LLQ3rhM40ZVNy+FjFWMdMc0zEsS1Xf0V3+JwxijNjFYUV8S+57vI9P1Vb0t4S7QObXCqXzf
1pcadCDCBKyYpI8itKAAznDMHG03alOpOscGJD6kkYttY+20hg/GF0vL06c=
','20210922CENAIDJA510R0200001844','ACTC',NULL);
INSERT INTO public.account_enquiry (id,account_no,amount,cre_dt,intr_ref_id,orign_bank,recpt_bank,bizmsgid,call_status,cihub_req_time,cihub_resp_time,error_message,full_request_msg,full_response_msg,resp_bizmsgid,response_status,log_message_id) VALUES
	 (3749,'934008199',3530000.00,'2021-09-22 12:14:50.869','4444122','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000066',NULL,'2021-09-22 12:14:50','2021-09-22 12:14:50',NULL,'eJyVUstuwjAQ/BefAdkOIMItiXm4KlCB1UvVAyThoRIHOYtEi/j3rm0itVVR6UpxbO/Ozni0ZxIf
q0m1If0ziQ6HcWbsbujX3S7zfy11BVq60wy2LmtPZCHHsRQPjFwwGkSVd+KSwTRCXHTFxbsP1CAt
OeGUMxpyXrfuMDqjjLrodkmDYKXI1774sEyrFqU9/FiL9jCbmFzAtU2Thk3OFeN91u53KEEmUabH
Itfg9UE5lEkFhUkyUGZtL0emNsEp+l0QbWMwzms+VdxgbJDparZWpwrzDE8LgH0htWNy+wk4Ox7n
I2KN8DrUyZW8nMlTAd69gc5UicstRd8twgZ/KUcyqcHEb05GVDhHnpf7Y47AoBPYXuiqfWH6jldS
zC0m2ZpNbJ0Xg1hhUqzAmnXxu2gD/xgY99w7MF+HxWM8o0OnKdSFP2Bh0MbJYGHohuzVLp+pGNbJ
','eJyNUd9vgjAQ/leWPqu5FjTCG4JONFOCXfaw+EAAHYlUQsumM/vfdy2yuGVLdoT+uH5333d3FzJp
5IPcE/dCvKqaZ7U+zdq1KLJ2F6GQSoTmtlYv5lXfiD9deWGw8MgHWo/w4z/jNuF8gnH0Gjcp3lFD
qMkJA0bBYaxLPaQQAwNttsVIjyAyyHctuEpSOQBg+NMBBXz16zxQ1zR9cPqMcRi61HaHQJApOKZN
mQvV6lPHWRiVaqNkXBnXfd21wOj5XY4xZjsdGy//5uOnUOw8kSEFcZ+xC/VeHKYi40dcvhF0LUGC
NVxJRiPkMCGY5w8w2GiU6c7wk6Eh8cLneNVlSRzBzjDjUdcV1ao+I+YRO4bd337lj/OdfvczZeqP
1FlvK13bspFNmdw9FSIriJ6XBnlpqrp5/piuY9kAY+o4BsyrW17f831ihr6pDiX6gkQlRuBUvB6q
Wwk6kKDKXpt1yaM+gxEM6WhsoTOWmVBtwQbE34RRCxaAZtjih/YJaFHTOQ==
','20210922CENAIDJA510R0200000432','RJCT',NULL),
	 (3762,'8417832',825200.00,'2021-09-22 13:23:52.916','33105','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000069',NULL,'2021-09-22 01:23:53','2021-09-22 01:23:53',NULL,'eJyVUl1PwjAU/S99RtJ2Tgdv28pHjYCBxhfjA2zjI7KOdJcEJfx3b1uWqJGITdbd23tPz+nJPZJk
X4/qFekeSbzbDXNjo77fN5vc/7XUNWjpsgmsXdVmZCaHiRQPjJxwtYiqrsSlvXGMuPiMSzYfqEFa
csIpZ7TDeXN1yOiEMurWXYe0CHaKYumbd/OsblMa4cfaNMJqagoB52tuaOeGc8WCLg+6ISfIJKps
XxYavD6o+jKtoTRpDsos7eHANCY4Rb8LwhUEjIYNnyovMLbIeDFZqkONdYbZDGBbSu2YXDwCZ8fj
dECsEV6HOriWlyN5KsG719O5qnC7pOi7RXjBX8qRTGowyZuTEZfOkef5dl8gMOIhpxRNtQ/M3vFE
iqmFpGuzSqzxopcoLIoFWK9OPopX8I95ca+9AvN1VjzGMzp0lkHT+AMW3bL7KOBuwl7t9glDoNYv
','eJyNUdtugzAM/ZUpz22VhNILbzS0K5vWIsiepj4gLh0SBETSbV3Vf58TytRNqzQjcvOxz7F9QouD
fJJ75JyQ2zTrtNWnVbcWRdrtwhdSCd/cturVePUNseXG9b0HF53BBojX/4yL/PUC4sglblF8ggZf
kyOKKcFzSvvUNsEhplgbtcZogADpZXkHbuJEjsABPxkRDF7WZp66pBni+ZBSjicOtRzbQsDk1cmh
yoTq9Kl65QeVipQMG/N03/YtMHr+lqONzMm0Z+PVbT7+4YvcFSlQIOcFutDuRbkUKa9h+UHQtwQI
trgjwZM5cJgQyHMDDGZZBNtIkxka5DLO4KrLkjCC3DDDUdcVtKo9AuYZ4qD7u+/8YZZrP0uVqT9Q
R71tdG3RMa7kobxbx2mB9Lw0yE0S1c/z13RnYzKdWdRAeXPNylzGkBl51JQVvHmxio28pXgrm2sB
OhBhAnWYnI88GFJs4ZlljyfwGMpUqK5cA+LvwmgFiK7rvIMP7AvECNKu
','20210922CENAIDJA510R0200000234','ACTC',NULL),
	 (3768,'8417832',825200.00,'2021-09-22 13:27:02.733','33105','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000074',NULL,'2021-09-22 01:27:02','2021-09-22 01:27:03',NULL,'eJyVUstuwjAQ/BefAdkOiMAtiXm4KlCB1UvVAyThoRIHOYtEi/j3rm0itVVRqaU4u94dz3i0ZxIf
q0m1If0ziQ6HcWZsNPT7bpf5v5a6Ai1dNoOtq9qMLOQ4luKBkQuuBlHlnbhkMI0QF11x8e4DNUhL
TjjljPY4r6/uMDqjjLrVbZMGwU6Rr33zYZlWLUpD/FiLhlhNTC7gek2T9pqcKxb0ebdPOUEmUabH
Itfg9UE5lEkFhUkyUGZtD0emNsEp+l0QriBgtFPzqeIGY4NMV7O1OlVYZ5gtAPaF1I7JxRNwdjzO
R8Qa4XWok2t5OZOnArx7A52pErdbir5bhBf8pRzJpAYTvzkZUeEceV7ujzkCQ97hlKKp9oHpO55I
MbeQZGs2sTVeDGKFRbEC69XFR9EG/jEv7rV3YL7Oisd4RodOU6gbf8DCNuuGAXcT9mq3TzLO1iU=
','eJyNUdtuwjAM/ZUpz4CchPtbSWF006Aq2dPEQ9ULq0TTqg0bDPHvc1I6sWmT5qq5OMc+x/aZzA71
U70j0zNxynIZV+a0aNYsi5tdearWyrO3tX61r+ZGxHzleO6DQy5oHSKLf8ZtvOUM4+g1bpZ9oAbP
kBMGjMKEsTb1gEIADIzR4YB0CCLdJG3AZRjVPQCGP+1RwFdRJa6+punCpMuYhOGUjabACTK5RXTI
E6UbfbpYeH6uN7oOSuu6r9oWWD2/y7FSeJ+3bDL/m08ePZU6KkYKMn3BLlQ7tZ+rWBa4fCNoW4IE
a2hIYNRHDhuCef4Ao3FOwXRGHi0NcYQUeDVl1TiC1DLj0dTlV7o6IeYZ47D726/8QZKadxFrW7+v
T2ZbmdqCRGV3TpzpU0jMuAzGiSLdjvPHcMd9OhpzZqGyvCUVjhDETnxT7nP0uaEOrbq5etuXt/wm
kADFMmzOR+l3GYwnfADcOIM6Vrqp1oLku7JSgduyLlv80D4BwdnSKg==
','20210922CENAIDJA510R0200000165','ACTC',NULL),
	 (3776,'8417832',825200.00,'2021-09-22 13:28:40.712','33105','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000079',NULL,'2021-09-22 01:28:40','2021-09-22 01:28:40',NULL,'eJyVUstuwjAQ/BefAdkOiMAtiXm4KlCB1UvVAyThoRIHOYtEi/j3rm0itVVRqaU4u94dz3i0ZxIf
q0m1If0ziQ6HcWZsNPT7bpf5v5a6Ai1dNoOtq9qMLOQ4luKBkQuuBlHlnbhkMI0QF11x8e4DNUhL
TjjljPY4r6/uMDqjjLrV7ZEGwU6Rr33zYZlWLUpD/FiLhlhNTC7gek2T9pqcKxb0edhvU4JMokyP
Ra7B64NyKJMKCpNkoMzaHo5MbYJT9LsgXEHAaKfmU8UNxgaZrmZrdaqwzjBbAOwLqR2Tiyfg7Hic
j4g1wutQJ9fyciZPBXj3BjpTJW63FH23CC/4SzmSSQ0mfnMyosI58rzcH3MEhrzDKUVT7QPTdzyR
Ym4hydZsYmu8GMQKi2IF1quLj6IN/GNe3GvvwHydFY/xjA6dplA3/oCFbdYNA+4m7NVun04v1jU=
','eJyNUdtOwkAQ/RUzz0B2tyBt35YWpBqBlPXBGB6aXrAJ3TbtoiLh353dUoNGEqfp3ubMnDkzR5js
m8dmC+4ReFXNk1qfZu2a50m7y0A2SgbmtlSvxqtv4E0XPPDvOZzQeiDKf8atg/kE4+g5bpJ/Yg2B
JgdGGCUOY13qESUhYUQbsx3oASL9NGvBVRQ3A3TgTweUoNerU1+d0/SJ02dMkFuX2e6QADL5Zbwv
Uqna+lQ5C1aFWqsmrMzTXd21wNTzdznaqGM5HZsorvOJj0BmXCZIAe4LdqHeyt1UJqLE5QdB1xIk
WJKWhIw1hwnBPFfAaJZFyQg0maEB7gkPr1pWgyPIDDMeta5VreoDYp4wDru/+c4fppn2e4ky+lfq
oLeF1saTqLh5Lrc56GFpBI9j1Q3z12jtIR3bFjNQUV1SetzzwMx7Xe0KfPMjFZnapvJtV12y60Ag
FEWYnA9i1WfEHo0pqsfHsEmkarUakHiXplBiGVGnDX5oXxqG0Wg=
','20210922CENAIDJA510R0200000289','ACTC',NULL),
	 (3784,'8417832',825200.00,'2021-09-22 13:30:13.632','311135','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000084',NULL,'2021-09-22 01:30:13','2021-09-22 01:30:13',NULL,'eJyVUstuwjAQ/BefAdkOqIFbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEolVRqaU4a+/Ozni0JxIf
qnG1Jr0Tifb7UWZsNPD7dpv5v5a6Ai3daQobl7UnMpejWIpHRs64GkSVd+KS/iRCXHTBxdtP1CAt
OeGUM9rlvG7dYXRKGXUrbJMGwUqRr3zxfpFWLbzHj7VoiNnE5AIubZq02+RcsaAX0B4LCDKJMj0U
uQavD8qBTCooTJKBMit7OTS1CU7R74IoDRhjQafmU8UNxgaZLKcrdawwz/A0B9gVUjsmF4/B2fE0
GxJrhNehjq7k9USeC/Du9XWmStxuKfpuETb4SzmSSQ0mfncyosI58rLYHXIEhrzDKUVT7QPTD7yR
YmYhycasY2u86McKk2IJ1quzj6I1/GNe3GvvwFzPisd4RodOU6gLf8DCNnsIA+4m7M1uXy0Z1iM=
','eJyNUdtugkAQ/ZVmn9XMLlCRNwSttKklsH1qfDBcrIkshF3bWuO/d3aRxjY16RD2MntmzpmZI5nu
5aPcEO9I/KZZ5K0+zbt1u827XURCKhGZ25N6Na/6RoLZ0o/Ce5+c0AaE1/+MS6PFFOPoOW66/UQN
kSYnDBiFCWN9aodCAgy0OeMJGRBEhkXZgZt1JkcADH86ooCvQVuE6pxmCJMhYxxuPQs8ahFkCuts
XxVCdfpUPY/iSqVKJo1x3bV9C4yev+Voowzzndl4dZ2Pf0Si9EWOFMR7wS60G7GbiZzXuPwg6FuC
BE/QkYBrI4cJwTxXwAAWpdRyiCYzNMQPeIBXXZbEEZSGGY+6rrhV7QExzxiI3V9950+KUr8HuTL1
x+qgt6WuDfW3xU26l4WoiZ6XBvlZpvp5/pqua9OxazED5c0la+AHATEjT5tdhb5wrdZG3ky87ZpL
ATqQAMU6TM4HHg8ZWMymju2iM5G5UF25BsTfhdEKlqnrtMIP7Qu1g9Kj
','20210922CENAIDJA510R0200000579','ACTC',NULL),
	 (3792,'8417832',825200.00,'2021-09-22 13:32:17.149','311135','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000089',NULL,'2021-09-22 01:32:17','2021-09-22 01:32:17',NULL,'eJyVUstuwjAQ/BefAdlOEYFbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEolVRqaU4a+/Ozni0JxIf
qnG1Jr0Tifb7UWZsNPD7dpv5v5a6Ai3daQobl7UnMpejWIpHRs64GkSVd+KS/iRCXHTBxdtP1CAt
OeGUM9rlvG7dZnRKGXUr7JIGwUqRr3zxfpFWLbzHj7VoiNnE5AIubZq02+RcsaAX8B7rEGQSZXoo
cg1eH5QDmVRQmCQDZVb2cmhqE5yi3wVRGjDGgnbNp4objA0yWU5X6lhhnuFpDrArpHZMLh6Ds+Np
NiTWCK9DHV3J64k8F+Dd6+tMlbjdUvTdImzwl3IkkxpM/O5kRIVz5GWxO+QIDHmbU4qm2gemH3gj
xcxCko1Zx9Z40Y8VJsUSrFdnH0Vr+Me8uNfegbmeFY/xjA6dplAX/oCFD6wTBtxN2JvdvgBTItY5
','eJyNUV1vgjAU/StLn6e5LaLCG4JOtkwNdtnD4gOh4EikEKjbnPG/77bI4paZrIR+nnvPOfceyWTf
PDZb4h6JV1VzUevdrJ3zXLSrDGWjZGhOS/VqXvWJ+NOFFwb3HjnhuCW8/GfcOpxPMI6e4yb5J2oI
NTlhwCg4jHWpbQoRMMBBLeqQW4LIIM1acBUnTR+A4U/7FPDVr9NAndP0wOkxxmHoWsylI4JMQZns
i1SqVp8qZ+GqUGvVRJW5uqu7Ehg9f8sxWpyh1bHx4jof/whl5kmBFMR9wSrUW7mbSsFLnH4QdCVB
giW0JDDWfk0I5rkCBrAopZZNNJmhIZ7PfTxqWw22IDPMuNW+VrWqD4h5wkCs/uY7f5Rm+t0Xyvhf
qYNeFtrbcy5KUd7MY5ET3S6N8ZJEde381dzxgI7GFjNQXl2S+p7vE9PxdbUr8C6IVWzUTeXbrrrk
14EEKNowOR/4qofmbXsAjrYaNUKq1q0B8XdppIJlbJ02+OH4Atfc0jE=
','20210922CENAIDJA510R0200001319','ACTC',NULL),
	 (3800,'8417832',825200.00,'2021-09-22 13:34:16.926','311135','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000094',NULL,'2021-09-22 01:34:17','2021-09-22 01:34:17',NULL,'eJyVUstuwjAQ/BefAdkOtIFbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEaquiUktx1t6dnfFoTyQ+
VONqTXonEu33o8zYaOD37Tbzfy11BVq60xQ2LmtPZC5HsRQPjJxxNYgqb8Ql/UmEuOiCi7cfqEFa
csIpZ7TLed26w+iUMupWt00aBCtFvvLF+0VatSgN8WMtGmI2MbmAS5sm7TY5VyzoBe0euyPIJMr0
UOQavD4oBzKpoDBJBsqs7OXQ1CY4Rb8LojRgjAWdmk8VVxgbZLKcrtSxwjzD0xxgV0jtmFw8BmfH
42xIrBFehzq6kpcTeSrAu9fXmSpxu6bou0XY4C/lSCY1mPjNyYgK58jzYnfIERjyDqcUTbUPTN/x
RoqZhSQbs46t8aIfK0yKJVivzj6K1vCPeXGvvQHzdVY8xjM6dJpCXfgDFrbZfRhwN2GvdvsESWrW
Mw==
','eJyNUdtugkAQ/ZVmn9XMLijKG4JW2lQNbJ+aPhAulgYWAmtba/z3zi7S2KYmXcJez8w5Z+ZI5vv2
od0R+0icul4ljdotuznPk24Vvmil8PVpI1/0qzoRd7F2fO/OISccA8Krf8aF/mqOcfQcN88/UYOv
yAkDRmHGWJ96TCEABjjoxDLJgCDSS7MOXEdxOwJg+NMRBXx1m9ST5zRDmA0Z4zCxDdOmFkEmr4r3
ZSpkp09WS39bylC2Qa2vbpu+BFrP33K0FsswejZeXufjH77IHJEgBbGfsArNThQLkfAKpx8EfUmQ
YAMdCcyUXx2Cea6AAQxKqTEmikzTEMflLh6VrRZbkGlm3Cpf20Y2B8Q8YiBW//k7f5Bm6t1NpPa/
lQe1rJW38DXKmry4QR9NGhHVMQVz4lj2Hf3V36lJranBNJTXl7yu47pENz2sixLvvEhGWuBCvBX1
pQQVSICiE53znm+HDMaWaVqmchu0iZCdYQ3i70KrBUM7Oz3jh+MLkXHTcA==
','20210922CENAIDJA510R0200001674','ACTC',NULL),
	 (3808,'84122222',825200.00,'2021-09-22 13:41:16.264','353865','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000099',NULL,'2021-09-22 01:41:16','2021-09-22 01:41:16',NULL,'eJyVUstuwjAQ/BefAdkOQQm3JObhqkAFVi9VD5CEh0oclCwSLeLfu7aJ1FZFpZbi7Hp3POPRnkl8
rCf1hvTPJDocxllloqHbd7vM/bXUNWhpsxlsbdVkZCHHsRQPjFxwtYgq78Qlg2mEuOiKi3cfqEEa
csIpZzTkvLnaZ3RGGbUrDEmLYKfI1675sEzrDqUBfqxDA6wmVS7gek2bhm3OFfP6XdZnPYJMokyP
Ra7B6YNyKJMaiirJQFVrcziqGhOsot8FUer5XtDzGz5V3GBskelqtlanGusMswXAvpDaMtl4AtaO
x/mIGCOcDnWyLS9n8lSAc2+gM1XidkvRd4vwgr+UI5nUUMVvVkZUWEeel/tjjsCA+5xSNNU8MH3H
EynmBpJsq01sjBeDWGFRrMB4dXFRtIF/zIt97R2Yr7PiMI7RotMUmsYfsKDLuFl2xF7N9glQ1tZ/
','eJyNUW1vgjAQ/itLP6tpiyjwDYtOXKYEuk+LHwwvSgKFQN10xv++a5HFLVuyI/Tl+tw9z91d0OzY
Prd75FyQW9fLpFGnRbfmedLtwhetFL6+beRBv6obYvO163srF13BBohX/4yL/OUM4sgtbpZ/gAZf
kSOKKcE2pX1qk+AQU6zMNikaIEB6adaB613cjjCm8JMRwfDKmtSTtzRDbA8p5XjijIlDJgiYvCo+
lqmQnT5ZLfyglJFsw1q7Hpu+BVrP73I6s8yejZd/8/GTLzJXJECBnFfoQrMXxVwkvILlG0HfEiDY
4BuJbQOHDoE8f4AxNkzDmig1/KRpkMs4g6sqq4URZJoZjqquoJHNGTAvEAjd337lD9NMvbNE6voD
eVbbWtUWHdu8qB5Wh0qckZqXArlxLPt5/piuNSZUmcby+p6WuYwhPfOoLkrweTu50/rm4q2o7xWo
QIQJFKKTPvFgSLE5nRpTYoEzbBMhu3o1iL8LLRYburDrFj6wT7gQ0xU=
','20210922CENAIDJA510R0200000952','ACTC',NULL),
	 (3816,'84122222',825200.00,'2021-09-22 13:41:22.693','353865','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000104',NULL,'2021-09-22 01:41:22','2021-09-22 01:41:22',NULL,'eJyVUstuwjAQ/BefAdmGVIFbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEolVRqaU4u94dz3i0JxIf
qnG1Jr0Tifb7UWZsNPD7dpv5v5a6Ai1dNoWNq9qMzOUoluKRkTOuBlHlnbikP4kQF11w8fYTNUhL
TjjljHY5r68OGJ1SRu1itEMaBDtFvvLN+0VatSgN8WMtGmI1MbmAyzVN2m1yrli712E9zgkyiTI9
FLkGrw/KgUwqKEySgTIrezg0tQlO0e+CKG0H7fAhqPlUcYOxQSbL6UodK6wzzOYAu0Jqx+TiMTg7
nmZDYo3wOtTRtbyeyHMB3r2+zlSJ2y1F3y3CC/5SjmRSg4nfnYyocI68LHaHHIEhDzilaKp9YPqB
J1LMLCTZmHVsjRf9WGFRLMF6dfZRtIZ/zIt77R2Y61nxGM/o0GkKdeMPWNhh3C43Ym92+wIar9Zf
','eJyNUdtOwkAQ/RWzz0JmtwVL30rLpRqBlDU+GB6aXrAJLE27qEj4d2en1KCRxGm6l9kzc87MHNlw
Xz/Wa+YemVeW07Qyp3GzFkXa7CpUtVYh3eb6lV7NjfmjmRcG9x47od0yuftn3DKcDjGOn+OGxSdq
CA05EyA4DIRoU/c4RCCAzHbYLUNkkOUNuIyTugsg8OddDvjqV1mgz2k6MOgIIaHv2twVgiFTsEv2
20zpRp/ejcPFVi91HZXkmlRtC0jP33LIBHdaNrm9zic/QpV7KkUK5r5gF6q12oxUKne4/CBoW4IE
c2hIONjIQSGY5woYwOpZTr/HDBnRMM+XPl5NWTWOICdmPJq6FpWuDoh5wkDs/uo7f5Tl5t1PNdW/
0AezzUxtz4VKi5tJsYnVmpl5GZCXJLqd56/pOjYXxggry0ta3/N9RjNflpst+oJYx6RvpN425aUC
E8iAYyGU9EEuOgLurB443EJnVKdKN/USSL4rEgsWFXZa4Yf2BWWo0rc=
','20210922CENAIDJA510R0200000048','ACTC',NULL),
	 (3824,'262222',825200.00,'2021-09-22 13:47:00.226','32221','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000109',NULL,'2021-09-22 01:47:00','2021-09-22 01:47:00',NULL,'eJyVUstuwjAQ/BefA7JNaYFbEvNwVaACq5eqB0jCQyVO5CwSLeLfu7aJRKuiUkvZrL07nvFojyTa
V+NqTXpHEpblKDU2G/i43ab+r6WuQEu3m8LGVe2OzOUokuKRkROugKjiRlzcn4SIC8+4aPuJGqQl
J5xyRruc11e3GZ1SRu3CcxIQ7BTZyjeXi6RqUtrBjzVpB6uxyQScr2nQboNzxVq9u4cepQSZRJHs
80yD1wfFQMYV5CZOQZmVPRya2gSn6HdBuFqcc1bzqfwKY0Amy+lKHSqs2+45wC6X2jG5fAzOjqfZ
kFgjvA51cC2vR/Kcg3evr1NVYLim6LtFeMFfypFMajDRu5MR5s6Rl8VunyGww9uc0qZ7QJx84IkU
MwuJN2YdWeNFP1JYFEuwXp18Fq7hH/PiXnsD5nJWPMYzOnSSQN34A8bv8Z3cDdibDV9VutXe
','eJyNUdtugkAQ/ZVmn9UM66WBN1y80KZKdPvU+EC4WBJYCKxtrfHfOztIY5uadAh7PTPnnJ0Tmx6a
p2bPnBNzq2oZ12Y1b8csi9tZ+arRyqfdWr/SrdkxMVu5vvfgsjNGj8nyn3lbfznFPOuSN80+UYNv
yBkHboHNeVd6bMEGOJiw+Jj1GCK9JG3BVRg1AwCOvzWwAG9FnXj6UqYPdp9zCRNndO8AMGTyyuhQ
JEq3+nQ594NCb3WzqehoUXdPQHr+lkNSbJt3bLK4zSc/fJW6KkYK5rzgK9R7lc9ULEscfhB0T4IE
a7iQgI0clIJ1boAxhpxzixkyomGukAK3xlaDLUiJGZfGV1Dr+oiYZzACz7vv+pskNfci1uQ/0Ecz
rYy3RZaHan+3DOOMmXYZjBtFumvnr+byCerhhJTVNadwhWDU8G2VF3jmhTokcTP1llfX9CaRgTFF
JR9l0OcwtFH0aIKHmyZWujVLIPmuSCkMydV5hx/GF92f0cE=
','20210922CENAIDJA510R0200000125','ACTC',NULL);
INSERT INTO public.account_enquiry (id,account_no,amount,cre_dt,intr_ref_id,orign_bank,recpt_bank,bizmsgid,call_status,cihub_req_time,cihub_resp_time,error_message,full_request_msg,full_response_msg,resp_bizmsgid,response_status,log_message_id) VALUES
	 (3831,'14555231',6200000.00,'2021-09-22 15:31:14.902','88432-722','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000112',NULL,'2021-09-22 03:31:14','2021-09-22 03:31:15',NULL,'eJyVUstuwjAQ/BefAdkOaYFbEvNwVaACq5eqB0jCQyUOchaJFvHvXdtEaquiUktx1t6dnfFoTyQ+
VONqTXonEu33o8zYaOD37Tbzfy11BVq60xQ2LmtPZC5HsRQPjJxxNYgqb8Ql/UmEuOiCi7cfqEFa
csIpZ7TLed06ZHRKGbWLMU4aBCtFvvLF+0VatSjt4MdatIPZxOQCLm2atNvkXLGwF7AeaxNkEmV6
KHINXh+UA5lUUJgkA2VW9nJoahOcot8FddoBb95zXvOp4gpjg0yW05U6VphneJoD7AqpHZOLx+Ds
eJwNiTXC61BHV/JyIk8FePf6OlMlbtcUfbcIG/ylHMmkBhO/ORlR4Rx5XuwOOQLvuGuGrtoXpu94
JcXMYpKNWcfWedGPFSbFEqxZZx9Fa/jHwLjn3oD5Oiwe4xkdOk2hLvwBY+0wDHngZ/PVbp/I0daB
','eJyNUslugzAQ/ZXK5xDZBgThRkwWWjVBiXuqckAsKRIYBE6bNMq/d2xClVaN1EF4fTPvzYzPaHro
nrs98s7Ib5pl2qrVvB+LIu1nEYpOilDv1vJN36odYrOVHwaPPrqAjRCv/+m3DZdT8CNXv2nxCRpC
RY4opgRPKB1C2wRvMMXKXGyiEQJkkOU9uImTbowxhZ+MCYZb1maBvIYx8MSglGPXM4lHbARMQZ0c
qkzIXp+s52FUya3sNo0+WrRDCbSev+VoM4kzsPHqPh8/hiL3RQoUyHuFKrR7Uc5EymsYfhAMJQGC
Ne5JCKHAoV0gzh2wa5nUcKhC8qOmQT7jDLYqrQ5akGtmWKq8ola2J8C8QHyo/u47/ibL1T1Lpc4/
kic1rVRui6KMxf4BXoqMqwKpjimYnyRy6Oiv/hLLtm1qEo3lzS0x8xlDuuvbpqzgLIhlrBXOxHvZ
3GpQjggTSEUHfeKRQbFjO5Y7seBw06VC9hlrEP8QWi42dWqXHXxgX0Fy06Q=
','20210922CENAIDJA510R0200000803','ACTC',NULL),
	 (3833,'262222',825200.00,'2021-09-22 17:15:58.642','32221','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000113',NULL,'2021-09-22 05:15:58','2021-09-22 05:15:58',NULL,'eJyVUstuwjAQ/BefAdlGaQO3JA7gqkAFVi9VD5CEh0oc5CwSLeLfu7aJ1FZFpZbirL07ntnRnkh8
qMf1mvRPJNrvR7mx0cDv223u/1rqGrR0pylsXNaeyFyOYikeGDnjahFV3YhL0kmEuOiCi7cfqEFa
csIpZ7THefN0wOiUMmoXY13SIlgpipUv3i+yukNpiB/r0BCziSkEXJ5p016bc8Xu+yzoByFBJlFl
h7LQ4PVBNZBJDaVJclBmZS+HpjHBKfpdEK4u55w1fKq8wtgik+V0pY415m31HGBXSu2YXDwGZ8fj
bEisEV6HOrqSlxN5KsG7l+pcVbhdU/TdInzgL+VIJjWY+M3JiErnyPNidygQGPKAU4qm2gazd7yR
YmYhycasY2u8SGOFSbEE69XZR9Ea/jEvrtsbMF9nxWM8o0NnGTSFP2D8DvvkbsBe7fYJb8PV7A==
','eJyNUW1PwjAQ/iumn4W0hSnbt7KBTCMso34yfJh7wSWsa7oiIuG/e+0YQSOJt6wv1+fuee7ugMbb
5rlZI++AmJSzTJnTtF3LMmt3EYpGi9DeFvrdvpob8idzFgaPDB3BbhGv/xm3DGdjiCOnuHH5BRpC
Q44opgS7lHapHYJjTDEYGY5cdIsAGeRFC5ZJ2vQxpvCTPsHw6qs80Kc0Pez2KOUEe8TxnBECpqBO
t1UudKtP19MwqvRSN7G0rgfVtcDq+VuONdc9s/HqOh//DEXBRAYUyHuFLqi12ExExmtYfhB0LQGC
BW5JCBkAhw2BPFfAYANKKUGGzNIg5nMfrqasBkZQWGY4mroipdUeMC8QB91fnfPHeWHe/Uzb+iO9
N9vc1MayUm61qm9Y9ZaoXaJLZKZmoCxNdTfVXzOmdyCLWiSXl9Q+831k576Umwp8QaITq3EiPjby
UoUJRNjUZlM+8ahHsUMcl94PwRk3mdBtzRbEd8IKxgNb3HEFH9g3fMXU8A==
','20210922CENAIDJA510R0200001489','ACTC',NULL),
	 (3888,'96599',54500.00,'2021-09-22 21:26:05.205','6118','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000175',NULL,'2021-09-22 09:26:05','2021-09-22 09:26:05',NULL,'eJyVUstuwjAQ/BefAdlWQwm3JA7gqkAFVi9VD5CEh0oc5CwSLeLfu7YBtVVRqaU4u94dz3i0BxLv
6mG9JN0DibbbQW5s1PP7ep37v5a6Bi1dNoaVq9qMTOUgluKBkSOuBlHVjbgkHUWIi064eP2BGqQl
J5xyRkPOz1cHjI4po3ax+4A0CHaKYuGbt7OsblHawY+1aAeriSkEnK5p0rDJueKsy9tdGhBkElW2
KwsNXh9UPZnUUJokB2UW9rBvziY4Rb8LsqvN2IVPlVcYG2Q0Hy/UvsY6w2wKsCmldkwuHoKz43HS
J9YIr0PtXcvLgTyV4N1Lda4q3K4p+m4RXvCXciSTGkz85mREpXPkebbZFQgM7gJK0VP7vuwdD6SY
WESyMsvY+i7SWGFRzMFadfRRtIR/jIt77A2Yr6PiMZ7RobMMzo0/YGE7CEM3Xq92+wTgPdWl
','eJyNUd9PgzAQ/ldMn7elrQMHbww2x4wbYTU+mD0QCpNkFAKdOo3/u3dlmGlc4hH64/rdfd/dfZDp
ob1vd8T9IF5dL2SDp3m3FoXsdhWqVqvQ3Nb62bzijfizlRcGS498gg2IqP4ZtwkXU4hjp7hp8Q4a
QiQnnHJGHc771BajMeUUbWxZZEAAGWR5B66TtB1RyuFnI0bh1W+yQJ/SDKkz5Fywscttl1oEmIIq
PZSZ0p0+Xc3DqNQb3ca1cd02fQuMnr/lGJuMec8myst84i1UuackUBD3CbrQ7NR+pqSoYPlB0LcE
CNa0I2E3WK8JgTwXwGg2YxOCZIaGxEtfwBXLamEEuWGGI9YVNbo5AuYB0kP3t9/54yzHd19qU3+k
j7itsDZPJuXVY6FkQXBaCPHSVPfT/DVbx7YcxwBFfc7oe75PzLg39b4EX5DoxEibqZd9fU6OgQT0
DbqMdyIacmo5E8a5Dc64lUp3pRqQeFVGJ72mFBm28IF9AXQM0Xs=
','20210922CENAIDJA510R0200000455','RJCT',NULL),
	 (3890,'19199',54500.00,'2021-09-22 21:28:14.118','61568','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000176',NULL,'2021-09-22 09:28:14','2021-09-22 09:28:14',NULL,'eJyVUstuwjAQ/BefAdkWoYRbEvNwVaACq5eqB0jCQyUOchaJFvHvXdukaquiUktxdr07nvFoTyQ+
VONqTXonEu33o8zYaOD37Tbzfy11BVq6bAobV7UZmctRLMU9I2dcDaLKG3FJfxIhLrrg4u07apCW
nHDKGQ05r68OGJ1SRu1idx3SINgp8pVv3i/SqkVpFz/Wol2sJiYXcLmmScMm54qzHu/2WJsgkyjT
Q5Fr8PqgHMikgsIkGSizsodDU5vgFP0uCFeHBZ1PPlVcYWyQyXK6UscK6wyzOcCukNoxuXgMzo6H
2ZBYI7wOdXQtzyfyWIB3r68zVeJ2TdF3i/CCv5QjmdRg4lcnIyqcI0+L3SFHYNAOKEVP7fvSNzyQ
YmYRycasY+u76McKi2IJ1qqzj6I1/GNc3GNvwHwdFY/xjA6dplA3/oCxkIWhG68Xu30ABHvVtg==
','eJyNUdtugkAQ/ZVmn9XsrkqFtxW0YlNLcPvU+EC4KIksBNa21vjvnR2ksU1NOoS9zZk5c2ZOZHpo
npotcU5EVNUiqc1p3q55nrS78lWjlY+3Z71Dr7kRd7YSvrcU5AzWI7L8Z9zaX0whjl3ipvkn1OAb
csIpZ9TmvEs9ZjSknKKxMekRQHpp1oKrKG4GlHL42YBR8Lp16ulLmj61+5xLNnL4xGEjAkxeGR+K
VOm2Pl3O/aDQa92EFT491F0LsJ6/y2lLYVbHJovbfPLDV5lQCVAQ5xW6UG/VfqYSWcLyg6BrCRA8
05aE3RsODIE8N8BgFhtbE2LIkIYIV7pwNbIaGEGGzHA0uoJa10fAvEAcdH/znT9MM+N3E436A300
28poW5a7SN2JJNfHiJh5GZCIY93N89d0mc1sG4GyuuZ0hesSHPi62hfw5kU6wuJm6m1fXdObQEIZ
qMCMjzLoczriQ2ZxM+WwSZRuxSJIviuslA5R1XkDH9gXH6DSKQ==
','20210922CENAIDJA510R0200000015','ACTC',NULL),
	 (3892,'19199',54500.00,'2021-09-22 21:29:42.973','61168','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000177',NULL,'2021-09-22 09:29:42','2021-09-22 09:29:43',NULL,'eJyVUstuwjAQ/BefAdkWr3BL4gCuClRg9VL1AEl4qMRBziLRIv69axtQWxWVWoqz693xjEd7JNG+
GlUr0juScLcbZsZGfb9vNpn/a6kr0NJlE1i7qs3ITA4jKR4YOeGqEVXeiYuTcYi48IyLNh+oQVpy
wilnNOD8cnWL0Qll1C7W6ZAawU6RL33zbp5WDUq7+LEG7WI1NrmA8zV1GtQ5V5z1eNBrcoJMokz3
Ra7B64OyL+MKChNnoMzSHg7MxQSn6HdBuNqMta98qrjBWCPjxWSpDhXWGWYzgG0htWNy8QicHY/T
AbFGeB3q4FpejuSpAO9eojNV4nZL0XeL8IK/lCOZ1GCiNycjLJwjz/PtPkdgq9miFD2170vf8UCK
qUXEa7OKrO8iiRQWxQKsVScfhSv4x7i4x96B+ToqHuMZHTpN4dL4A8YCFgRuvF7t9gkEZtW0
','eJyNUttugkAQ/ZVmntXsrhQLb7hopU2V4Pap8YFwsSSyEFjbWuO/d3aRxjY16RD2OjPnnJk9wnTf
PrVbcI/g1fUibfRq3o1FkXazDGSrZGB2K/VqbvUO+GzpBf6DBye0AYjqn3HrYDHFOHqOmxafyCHQ
4MAIo8RhrE99S0lEGEGjls1gAOjpZ3nnXMdJOyKE4U9HlOAtbzJfndMMiTNkTFDLZY5rMUAkv0r2
ZSZVx09V8yAs1Vq1UW2O7pu+BIbP33SM2WO7RxPldTzxEcjckylCgPuCVWi2cjeTqahw+AHQlwQB
VqQDoZMJYpgQzHPFWXOh1L4DDWZgwOOC41bLarEFuUHGpdYVNqo5oM8zxmH1N9/5oyzX9zxVRn+o
Dnpaam34RFRcFjeoo8li0B3Tbl6SqL6jv/pLHeo4xlHUl6jc4xxMy9f1rsQzP1axoTeTb7v6koAO
BEJRh8n4KMIhQ+mTsWPpqkRtKlUn1ziJd2m4krHRddrgh/YFEwrTIw==
','20210922CENAIDJA510R0200001462','ACTC',NULL),
	 (3894,'19199',54500.00,'2021-09-22 21:30:20.458','45168','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000178','ERROR-CIHUB','2021-09-22 09:30:20','2021-09-22 09:30:20','Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','eJyVUl1PwjAU/S99BtJWpoy3beWjRsBA44vxAbbxEVlHukuCEv67ty0zaiRik3X39t7Tc3pyjyTe
V6NqRbpHEu12w8zYqO/3zSbzfy11BVq6bAJrV7UZmclhLMU9IydcDaLKK3FJbxwhLjrj4s07apCW
nHDKGQ05r68OGJ1QRu1idx3SINgp8qVv3s3TqkVpBz/WoraamFzA+ZomDZucK866N7TLKUEmUab7
Itfg9UHZl0kFhUkyUGZpDwemNsEp+l0QrnbAbj/5VHGBsUHGi8lSHSqsM8xmANtCasfk4hE4Ox6m
A2KN8DrUwbU8H8ljAd69ns5UidslRd8twgv+Uo5kUoOJX52MqHCOPM23+xyBQTugFD2170vf8ECK
qUUka7OKre+iFyssigVYq04+ilbwj3Fxj70C83VUPMYzOnSaQt34A8ZCFoZuvF7s9gHiVNWi
',NULL,NULL,NULL,NULL),
	 (3896,'819199',54500.00,'2021-09-22 21:33:15.377','45168','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000179','TIMEOUT-CIHUB','2021-09-22 09:33:15','2021-09-22 09:33:17','Timeout menunggu response dari CIHUB','eJyVUstuwjAQ/BefAdmGtIRbEvNwVaACq5eqB0jCQyUOchaJFvHvXduA2qqo1FKcXe+OZzzaA4l3
1bBaks6BRNvtIDM26vl9vc78X0tdgZYuG8PKVW1GpnIQS/HAyBFXjajyRlzSHUWIi064eP2BGqQl
J5xyRkPOz1cHjI4po3ax+5DUCHaKfOGbt7O0alDaxo81aBurickFnK6p07DOueKs02x2WECQSZTp
rsg1eH1Q9mRSQWGSDJRZ2MO+OZvgFP0uCFcrYHcXPlVcYayR0Xy8UPsK6wyzKcCmkNoxuXgIzo7H
SZ9YI7wOtXctLwfyVIB3r6szVeJ2TdF3i/CCv5QjmdRg4jcnIyqcI8+zzS5HYNAKKEVP7fvSdzyQ
YmIRycosY+u76MYKi2IO1qqjj6Il/GNc3GNvwHwdFY/xjA6dpnBu/AFrs5CFoZuvV7t9AtI41eo=
',NULL,NULL,NULL,NULL),
	 (3917,'819199',54500.00,'2021-09-22 22:58:28.381','45168','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000198','TIMEOUT-CIHUB','2021-09-22 10:58:28','2021-09-22 10:58:30','Timeout menunggu response dari CIHUB','eJyVUstuwjAQ/BefAdkWqRJuSRzAVYEKrF6qHiAJD5U4yFkkWsS/d21D1VZFpZbi7Hp3POPRHkmy
b0bNivSOJN7thoWxUd/vm03h/1rqBrR02QTWrmozMpPDRIp7Rk64WkTVN+LSbBwjLj7jks07apCW
nHDKGY04v1wdMDqhjNrFopC0CHaKcumbd/O86VAa4sc61FZTUwo4X9OmUZtzxXkvCHs8JMgk6nxf
lRq8Pqj7Mm2gMmkByizt4cBcTHCKfheEqxuwu08+VV1hbJHxYrJUhwbrDLMZwLaS2jG5eATOjofp
gFgjvA51cC3PR/JYgXcv04Wqcbum6LtFeMFfypFMajDJq5MRV86Rp/l2XyIw6AaUoqf2ffkbHkgx
tYh0bVaJ9V1kicKiWIC16uSjeAX/GBf32BswX0fFYzyjQ+c5XBp/wEIWsShy8/Vitw8AUNYE
',NULL,NULL,NULL,NULL),
	 (3919,'1819199',54500.00,'2021-09-22 22:58:43.179','45168','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000199',NULL,'2021-09-22 10:58:43','2021-09-22 10:58:43',NULL,'eJyVUstuwjAQ/BefAdkuqRJuSczDVYEKrF6qHiAJD5U4yFkkWsS/d20DaquiUktxdr07nvFoDyTZ
1cN6SToHEm+3g9zYqOf39Tr3fy11DVq6bAwrV7UZmcpBIsUDI0dcDaKqG3FpdxQjLj7hkvUHapCW
nHDKGY04P18dMDqmjNrFoog0CHaKYuGbt7OsblEa4sdaNMRqagoBp2uaNGpyrjjvBGGnfUeQSVTZ
riw0eH1Q9WRaQ2nSHJRZ2MO+OZvgFP0uCFc7YPcXPlVeYWyQ0Xy8UPsa6wyzKcCmlNoxuXgIzo7H
SZ9YI7wOtXctLwfyVIJ3r6tzVeF2TdF3i/CCv5QjmdRgkjcnIy6dI8+zza5AYNAOKEVP7fuydzyQ
YmIR6cosE+u76CYKi2IO1qqjj+Il/GNc3GNvwHwdFY/xjA6dZXBu/AFjIYusGXbAXu32Ccyc1jE=
','eJyNUl1vgjAU/StLn9W0VQzwVgtOtkwJdk+LD4QPRyKFQJ1zxv++2yLGLTPZJfTz3nvOubcnNNu3
L+0WuSfE6nqRNno178aiSLtZBrJVMjC7lXo3t3qHuL9kgffE0BlsgET1z7h1sJhBHLnEzYov4BBo
cEQxJdihtE9tERxhisGIM8VogMDTy/LOuY6TdoQxhZ+MiL7lTeapS5ohdoaUCmK5lu1OxgiQvCrZ
l5lUHT9VzYOwVGvVRrU5emz6Ehg+f9MxXGx8RRPlfTzxGcicyRQgkPsGVWi2cufLVFQw/ADoSwIA
K3wBcRzAMCGQ544z2MQiUxtpMAODGBcctlpWCy3IDTIsta6wUc0RfF4hDqq/ueaPslzf81QZ/aE6
6mmptfnNoZAPLI1LpLulXViSqL6bv3pLbOJo5uZB1LeYnHGOTMPX9a6EMy9WsSHny49dfQuvAxEm
oMLkfBbhELQ7tjO1x3AYtalUnVjjJA7SMMVjo+q8gQ/sGxN40fM=
','20210922CENAIDJA510R0200001960','ACTC',NULL),
	 (3922,'1819199',54500.00,'2021-09-22 23:02:06.341','45168','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000200',NULL,'2021-09-22 11:02:06','2021-09-22 11:02:07',NULL,'eJyVUstuwjAQ/BefAa1dggi3JObhqkAFVi9VD5CEh0oSlCwSLeLfu7ZJ1VZFpZay9np3diYjn1h4
qMbVmvVOLNjvR0lpTgMXt9vE7bnKK8yVzaa4sVWTsbkahUrec3am1WC6uBEX9ScB4YILLty+kwZl
yJkAwcEXoh7tcZgCB7MEAGsw6pTpyjXvF3HVAujSx1vQpWpUphIvY5rgN4XQ4q4HogcdRkyyiA9Z
mqPTh8VARRVmZZSgLlfmcljWJlhFvwui1fZ455NPZ1cYG2yynK70saI6p2yOuMtUbpnseYzWjofZ
kBkjnA59tC3PJ/aYoXOvnye6oHBN0XeLaMBfyolM5ViGr1ZGkFlHnha7Q0pAr+0BtOyoKH6jCyVn
BhFtynVofJf9UFNRLtFYdXanYI3/eC72Z2/AfH0qDuMYLTqOsW78AeNd7nPftw/sxYQPbL/V+Q==
','eJyNUdtuwjAM/ZUpzwMlAQrtW0lhlGlQlUx7mHhAvUAlmlZt2MYQ/z7HpRObhjRXzc3HPj72iYwP
9VO9Jc6JuGU5iytzmjZrlsXNrnxVa+Xjbal36DU3IiYL1/fmLjmD3RNZ/DNu5c/GEMcucePsE2rw
DTnhlDNqc96mHjAaUk6NcatH7gkgvSRtwOUmqrvggJ91GQWvqBJPX9J0qN3hXDLLodyhFgEmr4gO
eaJ0U58upn6Q65WuwxKfHqq2BVjP3+WgDYe9lk3mt/nkh69SV8VAQZxX6EK1VfuJimUByw+CtiVA
sKQNCagGDgyBPDfAYP0Bs0bEkCENcYUUcDWyahhBisxwNLqCSldHwDxDHHR//Z0/TFLjF7FG/YE+
mm1htM13hTrevWQqzogZl8G4UaTbcf4aLhsxm9k2QmV5TSpcIQhOfFXuc3jzNnqD1U3U27685jeB
hDKQgTkfZdDhdGRRu085PIZ1rHSjFkHyXWGptIeyzmv4wL4A1ZHSSg==
','20210922CENAIDJA510R0200000263','ACTC',NULL);
INSERT INTO public.account_enquiry (id,account_no,amount,cre_dt,intr_ref_id,orign_bank,recpt_bank,bizmsgid,call_status,cihub_req_time,cihub_resp_time,error_message,full_request_msg,full_response_msg,resp_bizmsgid,response_status,log_message_id) VALUES
	 (3925,'262222',825200.00,'2021-09-22 23:05:52.5','31121','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000202',NULL,'2021-09-22 11:05:52','2021-09-22 11:05:53',NULL,'eJyVUstuwjAQ/BefAdlGqQK3JObhqkAFFpeqB0jCQyVO5CwSLeLfu7aJ1FZFpStls/bueMYjn0l8
rCf1lvTPJKqqcWZsNfR5v8/8X0tdg5ZuNYOd69oVWchxLMUjIxeMFlHlnbhkMI0QF11x8f4DNUhL
TjjljPY4b44OGJ1RRm1gi7QITop844erVVp3KA3xYx0aYjcxuYDrMW3aa3OueLdPg37ACTKJMj0W
uQavD8qhTGooTJKBMhu7OTKNCU7R74Iwuoxx1vCp4gZji0zXs4061di30wuAQyG1Y3L1BJwdT/MR
sUZ4HerkRl7O5LkA795AZ6rEdEvRd4vwgL+UI5nUYOI3JyMqnCPL1eGYIzDkAacUTbUXTN9xR4q5
hSQ7s42t8WIQK2yKNVivLr6KtvCP9+Juewfm61vxGM/o0GkKzeAPGH/gGO6Bvdr0CUOG1dI=
','eJyNUdtugkAQ/ZVmntXsrmKFN1y00qaWwPap8YFwsSSyEFjbWuO/d3aRxjY16RD2NmfmnJk5wnzf
PrZbcI7g1vUqbfRp2a1FkXa79GWrpG9uT+rVePUN+GLt+t69Cye0AYjqn3GRv5pjHD3HzYtP1OBr
cmCEUWIz1qe2KAkJI9psawYDQKSX5R24jpN2RAjDn44oQS9vMk+d0wyJPWRM0KlDLMdigExelezL
TKpOn6qWflCqSLVhbZ7umr4FRs/fcrRRcmv1bKK8zic+fJm7MkUKcF6wC81W7hYyFRUuPwj6liDB
E+lI0IUcJgTzXAGjjSllFDSZoQGXC45XXVaLI8gNMx51XUGjmgNinjEOu7/5zh9mufbzVJn6A3XQ
21rXtorT4iaKy32aNTHogWmUmySqH+iv8bIpQzNIUV+ycpdzMCOP6l2Jb16sYiNvId929aUAHQhE
l2VSPohgyMhkYs3sqY2PYZtK1ZVrQOJdGq1kbOo6bfBD+wLGdtK6
','20210922CENAIDJA510R0200000958','ACTC',NULL),
	 (3936,'262222',825200.00,'2021-09-22 23:15:24.342','31121','SIHBIDJ1','CENAIDJA','20210922SIHBIDJ1510O0100000209',NULL,'2021-09-22 11:15:24','2021-09-22 11:15:24',NULL,'eJyVUk1vwjAM/S85A0rMmIBb2xTINGCCaJdpB2jLh0ZT1BqJDfHf5yRU2qahMUt1ndgv7+UpJxYe
qnG1Zv0TC/b7UVraauDzdpv6v1GmQqPcaoob17UrNlejUMkHwc4UDaaLG3FRPAkIF1xw4faDNChL
zoCD4D2A+uiO4FMuuA3gPdZgNCmzlR/eL5KqxXmXPtHiXepGZSbxckyT95oAGtp90enDHSMmWSSH
PDPo9WExUFGFeRmlqMuV3RyWtQlO0e+CKNpCgKj5dH6FscEmy+lKHyvq2+k54i5XxjG5eozOjsfZ
kFkjvA59dCMvJ/aUo3cvNqkuKF1T9N0iOuAv5USmDJbhm5MR5M6R58XukBGwCx3gnEy1F0zeaUfJ
mYVEm3IdWuNlHGpqyiVar86+Ctb4j/fibnsD5utb8RjP6NBJgvXgDxjcA4V7YK82fQJazdXg
','eJyNUdtugkAQ/ZVmn9XsrpcqbwhaaVM1sH1qfKBcLAkshF2r1vjvnR2ksU1NOoS9zeXMOXMi0516
VltinYhdVYu4Nqd5s2ZZ3OzSk0pLD28r/Y5ecyPObGl77qNNzmAdIsp/5gXeYgp57JI3zT6hB8+A
E045oxPO29JDRn3KKRi754x0CES6SdoEV2GkepRy+FmPUfA6deLqS5kunXQ5F2xksaHFBwSQ3DLa
FYnUTX+6nHvrQgda+RU+PdStBNjP3+2gjXm/RRPFbTxx8GRqyxggiPUKKtRbmc9kLEpYfgC0kgDA
ijYgnE4AA1Ogzo1gsD5jqIw4IAyxHeHA1dBSMIIUkeFoeK1rXR8h5gXyQP3Nd30/SY3fiTXyX+uj
2ZaGm128hfU+1NldsFNZXhIzMxNoR5FuZ/prwnzEwTBSVNfAju04BKceVHkBb26oQ+xwJj/y6roH
k0ioYYYln8S6C5JQPh4OjDC+iqVuGGOQ2Etsl/aR2nkDH9gXk2XUHQ==
','20210922CENAIDJA510R0200001721','ACTC',NULL),
	 (3943,'262222',825200.00,'2021-09-23 05:39:00.883','31120','SIHBIDJ1','CENAIDJA','20210923SIHBIDJ1510O0100000001',NULL,'2021-09-23 05:39:01','2021-09-23 05:39:04',NULL,'eJyVUstuwjAQ/BefAa0dUQG3JObhqkAFFpeqB0jCQyUJchaJFvHvXdtEaqui0pXi2N6dnfFozyw6
VuNqw3pnFh4Oo9TY3cCvu13q/4UqKiyUO01x67L2xOZqFCn5yNmFosF0eScu7k9CwoVXXLT7IA3K
kjMBgkNXBHXrNocpcPDBWYNRpczWvviwTKoWQIc+3oIOZWOTSby2aUK3KQIN7V7Q7QEwYpJlcsyz
Ar0+LAcqrjA3cYrarO3l0NQmOEW/C6IIOBdQ8+n8BmODTVbTtT5VlLfa54j7XBWOye3H6Ox4mg2Z
NcLr0CdX8nJmzzl69/pFqktabin6bhE1+Es5kakCTfTmZIS5c2Sx3B8zAnZEWwC03APi5J1ulJxZ
SLw1m8gaL/uRpqRcofXq4nfhBv8xL+61d2C+zorHeEaHThKsC3/AxIOgcAP2apdPQOrV0A==
','eJyNUdtOg0AQ/RWzz22zu4gKbxRai8aWwPpk+oBcKgksBLa2tem/OzsU0xibOIS9zJ6ZM2fmSKbb
7qXbEPtInKZZpK0+zfu1KNJ+l77slPTxtlIf+KpvxJ0tHd97csgJbERE/c+4yF9MIY6d46bFF9Tg
a3LCKWfU4nxIbTIaUk7B2IPJyIgA0svyHtzESTehlMPPJozCq9tmnjqnGVNrzLng3DYsm3ICTF6d
bKtMqr4+Vc/9oFKR6sIGXY/t0AKs5+9y0CzTGNhEdZ1P7H2ZOzIFCmK/QRfajSxnMhU1LJcExtAS
IFjRMwnVejEE8lwBgxmMca1d7JGGOK5w4apldTCCHJnhqHUFrWoPgHmFOOj++id/mOX63U0V6g/U
QW9LrS3adkVZ3zjVe9zuYlUQPTMNdJJEDTP9NWF+x8EQKZpLYtdxXYJTj5qyAp8XqxgrnMnPsrms
QQcS7AGmfBbBmFNmcPPeugVn2KVS9YoRJHYSy6UGSjut4QP7BpMt1BQ=
','20210922CENAIDJA510R0200001851','ACTC',NULL),
	 (3950,'262222',825200.00,'2021-09-23 05:51:20.618','30920','SIHBIDJ1','CENAIDJA','20210923SIHBIDJ1510O0100000004',NULL,'2021-09-23 05:51:20','2021-09-23 05:51:21',NULL,'eJyVUstuwjAQ/BefAa1NUwG3JObhqkAFVi9VD5CEh0oclCwSLeLfu7aJ1FZFpSvFsb07O+PRnlh0
qMbVmvVOLNzvR2lpdwO/brep/xtlKjTKnaa4cVl7YnM1ipR84OxM0WC6uBEX9ych4cILLtp+kAZl
yZkAwaEr2nXrgMMUOPi4Yw1GlTJb+eL9IqlaAB36eAs6lI3LTOKlTRO6TdHWEPQC3hPAiEkWySHP
DHp9WAxUXGFexinqcmUvh2VtglP0uyCKNl1BzafzK4wNNllOV/pYUZ7TaY64y5VxTG4/RmfH42zI
rBFehz66kpcTe8rRu9c3qS5ouabou0XU4C/lRKYMltGbkxHmzpHnxe6QEbAjAgFAptoHJu90o+TM
QuJNuY6s8bIfaUrKJVqvzn4XrvEf8+JeewPm66x4jGd06CTBuvAHTNwLCjdgr3b5BE8R1dw=
','eJyNUdtuwjAM/ZUpz4CcMKD0raQwumlQlUx7mHioemGVaFq1YcAQ/z4npQhNQ5qr5uIc+/jYJzLZ
1a/1htgn4pTlPK70adasWRY3u/RkraRnbkv1aV71jfDpwvHcZ4ec0TpEFP+MW3nzCcbRS9wk+8Ya
PE1OGDAKY8ba1AMKATBAo5Y1Ih2CSDdJG3AZRnUPgOFPexTwlVeJqy5pujDuMiYYswfUZkCQyS2i
XZ5I1dSnipnn52ql6qA0rqeqbYGp5+9yjFls1LKJ/D6fOHgydWSMFMT+wC5UG7mdylgUuNwS9NuW
IMESLiTwiBwmBPPcAaP10aW1i4OhIQ4XHK9aVo0jSA0zHrUuv1LVETFvGIfdX1/zB0mq33msjH5f
HfW20Nresyrch/JhHsYZ0fPSICeKVDvPX9NlQ4ZmkKK8JeUO58RMfFVuc/S5oQpNdVP5tS1v+XUg
AYoyTMoX4XcZ0MFwCGMLnUEdS9WoNSCxl6ZU7IWWdV7jh/YD5rbSVw==
','20210922CENAIDJA510R0200001887','ACTC',NULL),
	 (3957,'262222',825200.00,'2021-09-23 07:52:51.321','30920','SIHBIDJ1','CENAIDJA','20210923SIHBIDJ1510O0100000007',NULL,'2021-09-23 07:52:51','2021-09-23 07:52:51',NULL,'eJyVUstuwjAQ/BefA3IcpTxuSRzAVYEKrF6qHiAJD5U4yFkkWsS/d20TiVZFpStlY+/ueMYjn0h8
qMf1mvRPJNrvR7k2q4HL223u/kqoGpSwuylsbNfsyFyMYsEffXLG8Iis7sQl6SRCXHTBxdtP1CAM
OWGU+bTHgubo0KdT6lMXHeIRnOTFyg3vF1ndprSLn9+mXewmuuBwOaZFey0WSNrph6wfokiP8Co7
lIUCpw+qgUhqKHWSg9QrUxzqxgSr6HdBGAGWaMMnyxuMHpkspyt5rLFvdnOAXSmUZbLrMVg7nmZD
YoxwOuTRjryeyHMJzr1U5bLCdEvRd4vwgL+UI5lQoON3KyMqrSMvi92hQGCXhYxSNNVcMPvAiuAz
A0k2eh0b43kaS2zyJRivzm4VreEf78Xe9g7M9VtxGMdo0VkGzeAPGHtgGPaBvZn0BXIC1fA=
','eJyNUt9vgjAQ/leWPqs5SnDKWwWdbJkS7J4WHww/HIkUAnXTGf/33RVZ3DKTHaGl17v7vu+OE5vs
m+dmy9wTE1U1T2r6mrVrniftrgLVaBWY01K/mVs6MW+6EIH/KNgZrcdk+c+8VTCfYJ51yZvkn8gh
IHDGgVsw5nZX2rEgAg5koyGwHsNIP83a4GoTNwMAjq81sOjWq1NfX8r0YdzntgRwHe46CNZjfhnv
i1Tplp8uZ0FY6JVuosq4HuquBYbP33TILNw6NFncxpOHQGVCJQjB3FfsQr1Vu6lKZInLD4CuJQiw
hBYE4B4xTArWuRGMZqOL2MiDgWHCkx4eSVaDI8gMMn6SrrDW9RFjXjAPu7/+rh+lGd17iTb6Q32k
bUHahNKluotSlTOaFoWIONbdNH/Nlg85momU1TWkJzyPmXmvql2BPn+jN4bbVL3vqmt0SmRgoQhT
8kmGfQ6Ojf/BiKMzahKlW60mSH4oQxQ7QaLOa3zQvgACINF3
','20210923CENAIDJA510R0200000860','ACTC',NULL),
	 (3964,'1819199',54500.00,'2021-09-23 07:56:19.268','45168','SIHBIDJ1','CENAIDJA','20210923SIHBIDJ1510O0100000010',NULL,'2021-09-23 07:56:19','2021-09-23 07:56:20',NULL,'eJyVUstuwjAQ/BefAdmBUMItiXm4KlCB1UvVA+QFKnGQs0i0iH/v2oaqrYpKLcXZ9e54xqM9kmhf
T+qC9I8k3O3GqTbR0O2bTer+SqgalLDZDNa2ajKyEONI8HtGTrgaRFY34uLBNERceMZFm3fUIAw5
8ajHaOC1L1f7jM4oo3YxShoEO3mWu+bdMqlblPbwYy3aw2qsMw7na5o0aHptSe/6frfPAoJMvEr2
ZabA6YNqKOIaSh2nIHVuDkf6YoJV9LsgXB2fdT/5ZHmFsUGmq1kuDzXWGWYLgG0plGWy8QSsHQ/z
ETFGOB3yYFuej+SxBOfeQKWywu2aou8W4QV/KUcyoUBHr1ZGWFpHnpbbfYZAv+NTip6a9yVveCD4
3CDitS4i4zsfRBKLfAXGqpOLwgL+MS72sTdgvo6KwzhGi04SuDT+gLEeC1gQ2AF7MdsHqNPWGw==
','eJyNUdtuwjAM/ZUpz4CclALpW2lhdNMAlexp4qHqhVWiadWGDYb49zkpndi0SXPVXBzb5xz7TKaH
5qnZEedM3KpaJLU+zds1z5N2l4FslAzMbaVezau+EW+2dAP/wSUXtB4R5T/zNsFiinn0mjfNP5BD
oMEJA0aBM6srbVMIgYG2CbdJj2Ckn2ZtcBXFzQCA4U8HFPDVq1NfXcv0gfeZJQAce+RQThDJL+ND
kUrV8lPlPFgXaqOasDKu+7prgeHzOx1tdMwnHZoo/sYTx0BmrkwQgjgv2IV6J/czmYgSl28AXUsQ
YAUtCBhFJgXr/BGMNrTpSLMRRwNDXE94eNWyGhxBZpDxqHWta1WfMOYZ87D726/6YZrpdy9RRv9a
nfS21NqQf53eLaIkJ3paOsSNY9VN88ds6YRyyrkJFdUtpud6HjED31T7An1+pCJDbibf9tUtvE4k
QFGFqfko1n0GY06HzLLQGTaJVK1YEyTepWEKllF12eKH9gkIrdHL
','20210923CENAIDJA510R0200000895','ACTC',NULL),
	 (3969,'1819199',54500.00,'2021-09-23 08:30:59.381','45168','SIHBIDJ1','BMRIIDJA','20210923SIHBIDJ1510O0100000014',NULL,'2021-09-23 08:30:59','2021-09-23 08:31:00',NULL,'eJyVUstuwjAQ/BefAdmBVAm3OObhqpQKrF6qHiAJD5U4yFkkWsS/d20DaquiUktxdr07nvFoD4Tv
6lG9JN0DSbbbYW5s1Pf7ep37v5a6Bi1dNoaVq9qMTOWQS3HPyBFXg6jqRhwfTSTikhOOrz9Qg7Tk
JKABo3HQPl8dMjqmjLrFOqRBsFMUC9+8nWV1i9IIP9aiEVZTUwg4XdOkcTNoKxp127QbxgSZRJXt
ykKD1wdVX6Y1lCbNQZmFPRyYswlO0e+CcHVCdnfhU+UVxgZ5nI8Xal9jnWE2BdiUUjsmF4/AUqQP
kwGxRngdau9aXg7kqQTvXk/nqsLtmqLvFuEFfylHMqnB8DcnIymdI8+zza5AYNgJKUVP7fuydzyQ
YmIR6cosufVd9LjCopiDteroo2QJ/xgX99gbMF9HxWM8o0NnGZwbf8BYxGIWx27AXu32Cd7s1kM=
','eJyNUdtugkAQ/ZVmn9XsLmKANwSt2FQJbp8aHwgXJZGFwNrWGv+9s4M0tqlJh7CX2TNzzsycyfTY
Prc74pyJW9eLtNGnebcWRdrtMpCtkgHe1mqPr/pGvNnKDfylSy5gAyKqf8ZtgsUU4tg1blp8goZA
kxNOOaM2N/rUJqMR5RSMcXtCBgSQfpZ34DpO2hGlHH42YhRevSbz1TXNkNpDbgjKHIM6pk2Aya+S
Y5lJ1elT1TwIS7VRbVSj67HpW4B6/paDNqGsZxPlfT7xEcjclSlQEOcVutDs5GEmU1HB8oOgbwkQ
rOmVhI2BA0Mgzx0w2NhkE4toMqQhric8uOqyWhhBjsxw1HWFjWpOgHmBOOj+9jt/lOX63UsV1h+q
k95WurZltY/lg5sW6hQTPS8NcpNE9fP8NV1mMZvZNkJFfcvquZ5HcOSb+lCCz49VjPJm8u1Q3wrQ
gQSbjDmfRDjk0BBqccsEZ9SmUnXlIki8S9RKDazrsoUP7Au8I9Ke
','20210923CENAIDJA510R0200001296','ACTC',NULL),
	 (3971,'1819199',54500.00,'2021-09-23 08:53:37.269','45168','SIHBIDJ1','BMRIIDJA','20210923SIHBIDJ1510O0100000015',NULL,'2021-09-23 08:53:37','2021-09-23 08:53:38',NULL,'eJyVUl1PwjAU/S99BtJuTDfe1pWPGhEDjS/GB9jGR2Qd2S4JSvjv3rbMqJGITdbd23tPz+nJPRK+
r8f1ivSOJN7tRlllooHbN5vM/bXUNWhpswmsbdVkZCZHXIo7Rk64WkSVV+L4eCoRF59xfPOOGqQh
Jx71GI08v7k6YHRCGbWLBaRFsFPkS9e8m6d1h9IQP9ahIVaTKhdwvqZNo7bnKxr2Ar/n3xJkEmW6
L3INTh+UA5nUUFRJBqpamsNh1ZhgFf0uCFc3YDeffKq4wNgiD4vJUh1qrDPMZgDbQmrLZOMxGIrk
fjokxginQx1sy/ORPBbg3OvrTJW4XVL03SK84C/lSCY1VPzVyogL68jTfLvPERh0A0rRU/O+9A0P
pJgaRLKuVtz4LvpcYVEswFh1clG8gn+Mi33sFZivo+IwjtGi0xSaxh8wFrKIRZEdsBezfQDl6dZH
','eJyNkW1vgjAQx7/K0tdq2gIqvMOCky1Tgt2rxReGB0cihZQ654zffdcii1u2ZCX08e5+9787o9mh
fWp3yDsjv2kWmdS7eTeXZdatIhKtEpE5rdSredUnxMKlHwUPPrrAGCBe/9NvHS1m4EeufrPyA3KI
NBxRTAl2qdWHdghOMMUwCKY2GiCwDPKiM262aTvCmMJPRgTDK5N5oK5hhtgdUotj4jmWZ00QkII6
PVS5UF1+qp5HcaXWqk0ac3Uv+xKYfH5Px+Ti2HZP49XfPP4eicIXGSCQ9wJVkDuxD0XGa5i+AfqS
AGCFOwhQgGFcIM4fxjBsh4ynSMMMBvmMMzhqWS20oDBk2GpdsVTyBDbP4AfV33zFT/JCv7NMGf2x
OullqbWF8liKO1Ahc6TbpW38NFV9O380l0yJS1zXmPLmFsp8xpDp+LrZV3AXbNXWZBeKt31zy9eO
CBOQYWI+8nhIsYvH44mjL5M2E6pTa4z4UZhUsWVkXTbwwfgE13bSRg==
','20210923CENAIDJA510R0200001024','ACTC',NULL),
	 (3973,'1819199',54500.00,'2021-09-23 09:24:08.599','45168','SIHBIDJ1','BMRIIDJA','20210923SIHBIDJ1510O0100000016',NULL,'2021-09-23 09:24:08','2021-09-23 09:24:09',NULL,'eJyVUstuwjAQ/BefAdmBIMItjnm4KqUCq5eqB0jCQyUOchaJFvHvXdukaquiUktxdr07nvFoT4Qf
qkm1Jv0Tiff7cWZsNPT7dpv5v5a6Ai1dNoWNq9qMzOWYS3HHyBlXg6jyRhyfzCTi4guOb99Rg7Tk
JKABo1HQrq8OGZ1SRt1iXdIg2CnylW/eL9KqRWkPP9aiPawmJhdwuaZJo2bQVjTqB50+VpFJlOmh
yDV4fVAOZVJBYZIMlFnZw5GpTXCKfheEqxOy7iefKq4wNsjDcrpSxwrrDLM5wK6Q2jG5eAKWIrmf
jYg1wutQR9fyfCKPBXj3BjpTJW7XFH23CC/4SzmSSQ2GvzoZceEceVrsDjkCw05IKXpq35e+4YEU
M4tINmbNre9iwBUWxRKsVWcfxWv4x7i4x96A+ToqHuMZHTpNoW78AWM9FrEocgP2YrcP3ozWQw==
','eJyNUdtugkAQ/ZVmn9XMrmiBNwQvtKkS3D41PhAulgQWAmtba/z3zi7S2KYmHcJeZs/MOTNzIrND
+9TuiX0iTl2vkkadFt2a50m3C1+0Uvj6tpGv+lXdiDtfO7734JAz2oDw6p9xW381wzh6iZvln6jB
V+SEAaNgsXGfekIhBAZolIFFBgSRXpp14DqK2xEAw5+OKOCr26SevKQZgjVkYw7MZoYNJkEmr4oP
ZSpkp09WCz8o5Va2Ya1dy6ZvgdbztxytxTDuezZe3ubjH77IHJEgBbFfsAvNXhRzkfAKlx8EfUuQ
YAMdCdApcugQzHMDjGZM6NQkikzTEMflLl5VWS2OINPMeFR1BY1sjoh5xjjs/u47f5hm6t1NpK4/
kEe1rVVtYSryu2VeRGJP1LgUxolj2Y/z13CpSS1qWRrK62tS13Fdoie+rYsSfV4kI61uLt6K+ppf
BRKgWIbO+ciDIYMpM5hlTtAZtomQXbUaxN+FlgpjXdZ5hx/aF9Gv0js=
','20210923CENAIDJA510R0200001209','ACTC',NULL),
	 (3975,'1819199',54500.00,'2021-09-23 09:29:27.027','45168','SIHBIDJ1','BMRIIDJA','20210923SIHBIDJ1510O0100000017',NULL,'2021-09-23 09:29:27','2021-09-23 09:29:28',NULL,'eJyVUl1vwiAU/S88q4Fqp/WtFD9Y5lyU7GXZg7b1I7PU0GviZvzvu4CabZmZIy1cuPfcczjhQPiu
GlVL0j2QeLsdZsZGfT+v15lftdQVaOl2Y1i5rN2RqRxyKe4ZOeKoEVXeiOOjiURcfMLx9QdqkJac
BDRgNAqa59Yho2PKqBusTWoEK0W+8MXbWVo1KO3gzxq0g9nE5AJObeo0qgdNRaNugF+bIJMo012R
a/D6oOzLpILCJBkos7CHA3M2wSn6XRCOVsjuLnyquMJYI4/z8ULtK8wz3E0BNoXUjsnFI7AUycNk
QKwRXofau5KXA3kqwLvX05kqcbqm6LtF2OAv5UgmNRj+5mTEhXPkebbZ5QgMWyGl6Km9X/qOB1JM
LCJZmSW3voseV5gUc7BWHX0UL+Efz8Vd9gbM16fiMZ7RodMUzoU/YKzDIhZF7oG92ukT90nWUQ==
','eJyNUttOwkAQ/RWzz0J2Fyq0b0sLUo3QlPXJ8FB7wSZ027SLiIR/d3ZKDRpJnKZ7nZlzzsweyWTX
PDUb4hyJqKp5UpvVrB3zPGln5atGKx93S/2Gt2ZH3OlC+N6DICewWyLLf8at/PkE4tg5bpJ/Agff
gBNOOaM2H3SpLUZDyikYszgjtwQ8vTRrnasobvqUcvhZn1G4devU0+c0PWr3+EBS7nDb4SMCSF4Z
74pU6ZafLmd+UOiVbsIKj+7rrgTI5286yMVGLogmi+t48sNXmVAJQBDnBapQb9R2qhJZwvADoCsJ
ACxpC0LZCDAwBPJccQYbWuxuTAwYwhDhShe2RlYDLcgQGZZGV1Dr+gA+zxAH1V9/5w/TzNy7iUb9
gT6YaWG0ieI1qveRzm/gseioyIlpmvEUcay7pv5qMRszm9k2usrqEtoVrkuw76tqW8CZF+kIOU7V
+7a6ZGECCTWVxpyPMuhxyrg1HNlGcNgkSrea0UnuFRKmAxR3WsMH9gVedtTP
','20210923CENAIDJA510R0200001521','ACTC',NULL);
INSERT INTO public.account_enquiry (id,account_no,amount,cre_dt,intr_ref_id,orign_bank,recpt_bank,bizmsgid,call_status,cihub_req_time,cihub_resp_time,error_message,full_request_msg,full_response_msg,resp_bizmsgid,response_status,log_message_id) VALUES
	 (3977,'1819199',54500.00,'2021-09-23 09:44:46.497','45168','SIHBIDJ1','BMRIIDJA','20210923SIHBIDJ1510O0100000018',NULL,'2021-09-23 09:44:46','2021-09-23 09:44:47',NULL,'eJyVUstuwjAQ/BefAdkhQYRbHPNwVUoFVi9VD5CEh0oc5CwSLeLfu7ahaquiUktxdr07nvFoj4Tv
63G9Ir0jSXa7UW5sNPD7ZpP7v5a6Bi1dNoG1q9qMzOSIS3HHyAlXg6jqRhwfTyXikjOOb95Rg7Tk
JKABo3HQvlwdMTqhjLrFuqRBsFMUS9+8m2d1i9IufqxFbTU1hYDzNU0aN4O2onEvDHthhyCTqLJ9
WWjw+qAayLSG0qQ5KLO0h0NzMcEp+l0QrjBinU8+VV5hbJCHxWSpDjXWGWYzgG0ptWNy8RgsRXo/
HRJrhNehDq7l+UgeS/Du9XWuKtyuKfpuEV7wl3IkkxoMf3UyktI58jTf7gsERmFEKXpq35e94YEU
U4tI12bFre+izxUWxQKsVScfJSv4x7i4x96A+ToqHuMZHTrL4NL4A8a6LGZx7AbsxW4f83rWTw==
','eJyNUdtuwjAM/ZUpzwM5obC2byWF0U0DVLKHaeKh6oVVomnVhg2G+Pc5KZ3YNKS5ai7Osc+xfSTj
XfPUbIh7JF5VzZJan6btmudJu8tANkoG5rZQb+ZV3wifzL3Af/DICe2WiPKfcatgNsY4eo4b55+o
IdDkhAGj4LBBl3pIIQQG2izbIbcEkX6ateAqips+AMOf9ingK69TX53T9MDpsYEA5lqWa40IMvll
vCtSqVp9qpwGy0KtVBNWxnVfdy0wev6Wo41SsDo2UVznE/tAZp5MkIK4r9iFeiO3E5mIEpcfBF1L
kGABLQlQGzlMCOa5AtZ9GdKRRoq9oSEeFxyvuqwGR5AZZjzqupa1qg+IecY47P76O3+YZvqdJ8rU
v1QHvc11bai/TqObl3KTEz0ujfHiWHXj/DVcalOHOo6BiuqSlHucEzPxVbUt0OdHKjLqJvJ9W13y
60ACFMswOR/FssfAurMse8TQGTaJVG21BiQ+pJEKA1PWaY0f2hfn0NJG
','20210923CENAIDJA510R0200000489','ACTC',NULL),
	 (3980,'1819199',54500.00,'2021-09-23 10:13:38.216','45168','SIHBIDJ1','BMRIIDJA','20210923SIHBIDJ1510O0100000020',NULL,'2021-09-23 10:13:38','2021-09-23 10:13:39',NULL,'eJyVUl1PwjAU/S99BtJuzGy8rSsfNSIGG1+MD7CNj8g60l0SlPDfvW3BqJGITdbd23tPz+nJPRC+
a8bNkvQOJN1uR4Wx0cDv63Xh/1rqBrR02QRWrmoz8ihHXIpbRo64WkTVV+L4eCoRl55wfP2OGqQl
JwENGE2C8Hx1xOiEMupWQEmLYKcoF755O8ubDqUxfqxDY6xmphRwuqZNk3YQKkZ7LOyFMUEmUee7
qtTg9UE9kFkDlckKUGZhD4fmbIJT9LsgXN2I3XzyqeoCY4vczycLtW+wzjB7BNhUUjsmF4/BUmR3
0yGxRngdau9ang/koQLvXl8XqsbtkqLvFuEFfylHMqnB8FcnI62cI0+zza5EYNSNKEVP7fvyNzyQ
YmoR2cosufVd9LnCopiDteroo3QJ/xgX99grMF9HxWM8o0PnOZwbf8BYzBKWJG7AXuz2AbS11is=
','eJyNUl1vgjAU/StLn9W0oAR4w6KTLVMC3dPiA+HDkUAhtGxzxv++2yKLW2ayS+jnuffcc29PaNmL
J3FA7gl5bbvJOrVaD2NZZsPMAy4kD/RuJ1/1rdohutp6gf/goTPYBLHmn35xsFmCH7n4LctPyCFQ
5MjABsGOYY6hFwRH2MDKHMdCEwRIPy8GcJukYoaxAT+ZEQy3tMt9eQkzxc7UMBk2XWK6po2AyW/S
vs65HPKTzToIaxlLEbX66L4bS6Dz+TsdZYTYi5GN1bf52EfAC49nQIHcF6hCd+DVimesgeEHwVgS
INjhgQRkA4d2gTg3wGDzBbFspMg0DfIoo7BVsgS0oNDMsFS6wk52R8A8gx9Uf/8dP8oLdU8zqfWH
8qimrdLmZWXby665i49JLfoKqZ4poJemcuzprw4TmzjEcTSUtdfM1KMU6bbHbVXDmZ/IRKe44m9V
e52EckSYgBYd85GFU3gJ1ty0LQMOI5FxOUjWIPbOdb7Y1NrOe/jAvgCmKtSU
','20210923CENAIDJA510R0200000996','ACTC',NULL),
	 (3982,'262222',825200.00,'2021-09-23 10:15:07.961','30920','SIHBIDJ1','BMRIIDJA','20210923SIHBIDJ1510O0100000021',NULL,'2021-09-23 10:15:07','2021-09-23 10:15:08',NULL,'eJyVUstuwjAQ/BefAa2NaIFbHPNwVUpFrV6qHiAJD5UkyFkkWsS/d20Tqa2KSleKY3t3dsajPTK5
rybVivWPLNrtxql1u2FYN5s0/AtdVFhof5ri2mfdiT3psdTqjrMTRYOZ8kqcnMw04aIzTm4+SIN2
5EyA4NAT7bp1h8MUOPgQnDUYVapsGYp386RqAXTp4y3oUja2mcJzmyb0mqJtOPR5pw+3jJhUmezz
rMCgD8uhjivMbZyisUt3ObK1CV7R74Io2nQFNZ/JLzA22MNiujSHivJO+xPiNteFZ/L7CTqK+H42
Ys6IoMMcfMnLkT3mGNwbFKkpabmk6LtF1OAv5USmC7TyzcuIcu/I83y7zwjYFR0BQKa6BybvdKPV
zEHitV1JZ7waSENJtUDn1SnsohX+Y178a6/AfJ2VgAmMHp0kWBf+gIkbQeEH7NUtn4KS1gI=
','eJyNUt9vgjAQ/leWPqspZWzKGxadbJkS6LKHxQfDD0cihdC66Yz/++6KGLfMZEdoae+7++6740DG
W/Ws1sQ9EK+uZ2mDX9N2LYq03WUglZaBOS30u/HiifDJ3Av8R48cwXpEVP+Mi4PZGOKsU9y4+IIa
AiQnjDKLjpjdpXYsGlFG0WznnvQIIP0sb8H1KlEDShm81sCi4OVN5utTmj4d9ZktqO1ajkuHBJj8
KtmWmdRtfbqaBmGpY62i2lw9NF0LTD1/l4NmDZnTsYnyOp/YBTL3ZAoUxH2DLjRruZnIVFSw/CDo
WgIEC9qSgAc4TAjkuQLGvsAVahc7Q0M8LjgcUZaCEeSGGT5RV9joZg+YF4iD7i/P+aMsRz9PtdEf
6j1uc9QWb1Umq5vXQqYFwXkhyEsS3c3z13TZHQMzSFFfknKPc2ImHtebEu78lV6Z6ibyY1Nf8mMg
oajfpHwSYZ/RW9N3/AkilUrdqjUg8SlNqdALlHVcwgP2DQPg0ns=
','20210923CENAIDJA510R0200000357','ACTC',NULL),
	 (3998,'262222',825200.00,'2021-09-23 10:48:33.656','30920','SIHBIDJ1','CENAIDJA','20210923SIHBIDJ1510O0100000028',NULL,'2021-09-23 10:48:33','2021-09-23 10:48:34',NULL,'eJyVUstuwjAQ/BefAdlOqQK3JA7gqkAFVi9VD5CEh0oc5CwSLeLfu7aJ1FZFpSvFsb07O+PRnkh8
qMf1mvRPJNrvR7mxu4Fft9vc/7XUNWjpTlPYuKw9kbkcxVI8MHLGaBFV3YhL0kmEuOiCi7cfqEFa
csIpZ7THg6Z1l9EpZdQFD0mLYKUoVr54v8jqDqUhfqxDbTYxhYBLmzbttXmgGO3fhf0gIMgkquxQ
Fhq8PqgGMqmhNEkOyqzs5dA0JjhFvwvCCPCKNnyqvMLYIpPldKWONeYZnuYAu1Jqx+T2Y3B2PM6G
xBrhdaijK3k5kacSvHupzlWFyzVF3y3CBn8pRzKpwcRvTkZUOkeeF7tDgcCQdzmlaKp9YPaON1LM
LCTZmHVsjRdprDAplmC9OvtdtIZ/zIt77Q2Yr7PiMZ7RobMMmsIfMH7PMdyAvdrlE3hF1fQ=
','eJyNUl1vgjAU/StLn9W0BY3yVotOXKYE69PiA+PDkUkhUKfO+N93W2Rxy0x2Cf0895577u0Zjff1
c71FzhmxspzFlV5NmzHL4maWnqyV9Mxuqd7Mrd4hPlkwz50zdAHrIFH802/lzcbgR65+4+wTcvA0
OaKYEjyiVhu6T3CAKQYjdp+iDgKkm6QNuAyjuocxhZ/0CIZbXiWuuobp4lGXWgJbjj10LAsBk1tE
+zyRqslPFVPPz9VK1UFpjh6rtgQmn7/TMTaw7ZZN5Pf5xNGTKZMxUCDnBapQbeVuImNRwPCDoC0J
ECzxlYQOgcO4QJw7YDALjrR2cTQ0iHHBYatl1dCC1DDDUuvyK1WdALMGP6j+5jt+kKT6nsfK6PfV
SU8LrY3lr2F1CFX2MC/eC6Q7pmEsilTb0V/9pQMKZpCivKXljHNker4qdzmcuaEKTX4T+bErbzPQ
jggTEGJCPgm/q+UTm9r6GQR1LFWj14DEQZpkoRpa2GUDH9gXx6rTLQ==
','20210923CENAIDJA510R0200001452','ACTC',NULL),
	 (4005,'262222',825200.00,'2021-09-23 10:52:15.401','30920','SIHBIDJ1','CENAIDJA','20210923SIHBIDJ1510O0100000031',NULL,'2021-09-23 10:52:15','2021-09-23 10:52:16',NULL,'eJyVUl1rwjAU/S95VkkiHepb2/iRMXVo8GXsQdtay2wq6RXcxP++m8TCNiZzgaY3996Tc3K4ZxId
62mdk8GZhIfDJDU2Gvm9KFL/11LXoKU7zWHnqvZElnISSfHIyAVXi6jqTlw8nIWIC6+4qPhADdKS
E045o33eba4OGJ1TRt3qMtIi2CmyrW8+rJO6Q2kPP9ahPazGJhNwvaZN+23eVYwOAj5gAUEmUSXH
MtPg9UE1knENpYlTUGZrk2PTmOAU/S7ISsEUbfhUeYOxRWab+Vadaqxb7UuAfSm1Y3LxFJwdT4sx
sUZ4HerkWl7O5LkE795Qp6rC7Zai7xbhBX8pRzKpwURvTkZYOkdW6/0xQ2CPB5xSNNU+MHnHjBQL
C4l3Jo+s8WIYKSyKDVivLj4Kc/jHvLjX3oH5Oise4xkdOkmgafwB4w8clxuwV7t9AlJ51d4=
','eJyNUdtugkAQ/ZVmn9UsS7DKGy5eqKkS3T41PhAulgQWAkurNfx7ZxZpbFOTzmbvZ+bMmbmQWVM/
10diX4hTlquowtOiW9M06nbpyVpJT9+26k3/4o3w+cbx3CeHtGADIop/+u291Qz8jKvfLP2EHDwk
J4wyg06Z2Ye2DLqjjIIZMMmAANKNkw5cBmE9opTBNEYG/vIqdtU1zJBOh8wU1LQtZhsWASa3CJs8
lqrLTxULz8/VXtW7Uj8tq74EOp+/0+lsbPRsIr/PJ06eTBwZAQWxX6EK1VFmcxmJApYfBH1JgGBL
ryQmcmgXiHMHjDB4Qu3ipGmIwwWHK8qqoQWJZoYj6vIrVZ0B84LFbNvDd/xdnOA/j5TW76szbhvU
tkyzQB4f1k3d5AHBhiHKCUPVN/RXe9mYgWmkKG9ZucM50S3fl1kOb26gAp3eXL5n5W0C6EgoFkCH
XAt/yKjFJpNHC7Xu6kiqTq4GiQ+pc4VioK72AAPsC6oo0rs=
','20210923CENAIDJA510R0200001000','ACTC',NULL),
	 (4017,'262222',825200.00,'2021-09-23 10:52:33.098','3050','SIHBIDJ1','CENAIDJA','20210923SIHBIDJ1510O0100000036',NULL,'2021-09-23 10:52:33','2021-09-23 10:52:33',NULL,'eJyVUstuwjAQ/BefAdmOgoBbEvNwVaACq5eqB8gDUImDnEWiRfx71zaR2qqo1FKcXe+OZzzaM4mP
9bTekMGZRIfDJDM2Gvl9t8v8X0tdg5Yum8PWVW1GlnISS/HAyAVXi6jqTlwynEWIi664ePeBGqQl
J5xyRvs8aK4OGZ1TRt0KuqRFsFPkhW8+rNK6Q2kPP9ahPawmJhdwvaZN+20eKEYHIR8EAUEmUaXH
Mtfg9UE1kkkNpUkyUKawh2PTmOAU/S7IaaEhbfhUeYOxRWbreaFONdYZZkuAfSm1Y3LxFJwdj4sx
sUZ4HerkWl7O5KkE795QZ6rC7Zai7xbhBX8pRzKpwcRvTkZUOkeeV/tjjsAeDzmlaKp9YPqOJ1Is
LCTZmk1sjRfDWGFRrMF6dfFRtIF/zIt77R2Yr7PiMZ7RodMUmsYfMN7luNyAvdrtE1M41dw=
','eJyNUU1vgkAQ/SvNntUsixDlhqB121QJbk+NB8KCJZGFwNpqTf97ZwdpbFOTDmG/5s28eTNnMju0
T+2OeGfi1/VSNua06NaikN2uuGq14nhb61f0mhsJ5iufhw8++QQbEFH9M27DlzOIsy5xs+IDauCG
nDDKLDpldp/asWhMGQWz3MmYDAggwyzvwHWStiNKGfzWyKLgDZos1Jc0QzodMltQ23OYZ9sEmMIq
PZSZ0l19ulrwqNQb3cY1Pt03fQuwnr/LQWPU6dlEeZtPHLnKfSWBgngv0IVmp/ZzJUUFyw+CviVA
sKYXEtsFDgyBPDfAiKOO0S6OSEP8QARwNbJaGEGOzHA0uqJGNyfAPEMYdH/7nT/OcuMPpEb9kT6Z
bWW0LRNZ3HElk0ZXxMzLgPw01f08f02XuQwMkaK+Jg38ICA48U29L+EtTHSC1c3V276+5jeBhFog
A1M+imjI6NS1J5OxaXzcSqU7tQgS7wpLhVYYWZ9b+MC+ANnb0lM=
','20210923CENAIDJA510R0200001684','ACTC',NULL),
	 (4029,'262222',825200.00,'2021-09-23 11:03:03.635','30501','SIHBIDJ1','CENAIDJA','20210923SIHBIDJ1510O0100000041',NULL,'2021-09-23 11:03:03','2021-09-23 11:03:04',NULL,'eJyVUstuwjAQ/BefAa2dUgG3JObhqkAFVi9VD5CEh0oclCwSLeLfu7aJ1FZFpatk/dgdz3jkE4sO
1bhas96Jhfv9KC3tbODzdpv60ShToVFuNcWNq9oVm6tRpOQDZ2eKBtPFjbi4PwkJF15w0faDNChL
zgQIDl0R1Ee3OUyBg4s7zhqMOmW28s37RVK1ADr08xZ0qBqXmcTLMU3oNkWgOe9BQB8jJlkkhzwz
6PVhMVBxhXkZp6jLld0clrUJTtHvgigCaAOv+XR+hbHBJsvpSh8rqtvuOeIuV8YxufkYnR2PsyGz
Rngd+uhaXk7sKUfvXt+kuqB0TdF3i+iAv5QTmTJYRm9ORpg7R54Xu0NGwI5oCwAy1V4weacdJWcW
Em/KdWSNl/1IU1Eu0Xp19rNwjf94L+62N2C+vhWP8YwOnSRYN/6AiXtB4R7Yq02fMzrVyg==
','eJyNUd9vgjAQ/leWPqu5FjXqG4JOXKYEu+xh8cFQcGTSEqibzvi/71pkcctMdtDf3913392JjPfV
Y7UloxNxi2ImSrOb1nOWiXqVgay0DOxpqV/tqzkRb7JwA3/ukjNai3D1T79VMBujH734jbNPzCEw
5IQBozBkThO6RyECBmiU9SlpEUT6SVqDi01cdQAYDtqhgK9emfj6EqYNwzZzOHRH4OBPkMlX8T5P
pK7z02oahLle6Soq7NV92ZTA5vN3Ota6vUHDxvPbfPwQyNSVAinI6AWrUG7lbiIFVzj9IGhKggRL
aEiMXuuCcW6A0RzogUHyg6Uhrsc9PBpZFbYgtcy4NbrCUpdHxDyhH1Z//R0/SlLz7glt9Yf6aJaF
0facCSXU3Vy9KWLaZTBuHOumnb+ay/oMzSJ5cc3puZ5HbMNXxS7HO3+jNza5iXzfFdf0xpFYUTbk
Aw/bDAZdh/bBdDmqhNS12Fr5h7SZYimMqvMaP7Qv6AHR4g==
','20210923CENAIDJA510R0200001261','ACTC',NULL),
	 (4041,'262222',825200.00,'2021-09-23 11:07:18.63','31401','SIHBIDJ1','CENAIDJA','20210923SIHBIDJ1510O0100000046',NULL,'2021-09-23 11:07:18','2021-09-23 11:07:19',NULL,'eJyVUstuwjAQ/BefAdnm0cAtiXm4KlCB1UvVAyThoRIHOYtEi/j3rm0i0aqodKVs1t4dz3jkE4kO
5bhck96JhPv9KDW2Gvi83ab+r6UuQUu3msLGde2KzOUokuKRkTNGjajiTlzcn4SICy+4aPuJGqQl
J5xyRru8WR3dZnRKGXXR6pAawUmRrfzwfpGUDUoD/FiDBtiNTSbgckydduu8qRjr0YceCwgyiSI5
5JkGrw+KgYxLyE2cgjIruzk0lQlO0e+CMJqsRVnFp/IbjDUyWU5X6lhi307PAXa51I7J1WNwdjzN
hsQa4XWooxt5PZHnHLx7fZ2qAtMtRd8twgP+Uo5kUoOJ3p2MMHeOvCx2hwyBAW9zStFUe8HkA3ek
mFlIvDHryBov+pHCpliC9ersq3AN/3gv7rZ3YK7fisd4RodOEqgGf8B4h2O4B/Zm0xdnbdXo
','eJyNUW1vgjAQ/itLP6u5FpXBNwSdzMwR7D4tfiC8OBIpBMqmM/73XYssbpnJjtD2Xp977k5k1jZP
zY7YJ+JU1TKp1WvRnXmedLfwRSOFr7Vn+aa9SiPufO343qNDzigDwst/5m385Qzz6CVvln9iD74C
JwwYBYsZfekJhRAYKLEoJQOCkV6adcFVFDcjAIY/HVFAr1unnryUGYI1ZAaHsQ2mTe8JInll3Bap
kF1/slz4QSE3sgkrbXqo+xHofv5uRwm1TKNH48VtPH7wReaIBCGI/YpTqHdiPxcJL/H4AdCPBAGe
oQOB8RQxdArWuRGMYtAxqMnwg4YhjstdVBWtBleQaWR8Kl5BLesjxrxgHk5/+10/TDPldxOp+Qfy
qK614hamIr9btU1bREStS8U4cSz7df5aLpsyFB3Jq2tM13Fdohe+qfYF2rxIRrq5uXjfV9fwKpFo
UrrkigdDBqYF5oQyNIZNImRHtmP+IXSnYGhW5y1+KF8gsNIT
','20210923CENAIDJA510R0200000911','ACTC',NULL),
	 (4048,'262222',825200.00,'2021-09-23 12:07:01.219','31411','SIHBIDJ1','CENAIDJA','20210923SIHBIDJ1510O0100000049',NULL,'2021-09-23 12:07:01','2021-09-23 12:07:02',NULL,'eJyVUttuwjAM/Zc8F5SksAFvbcMl04AJor1Me4C2XDSaotRIbIh/n5NQaZuGxiw1tWMf++TIJxIf
qnG1Jr0Tifb7UWasN/Dndpv5v5a6Ai1dNIWNy9qIzOUoluKBkTNaQFR5Iy7pTyLERRdcvP1ADtIO
J5xyRrs8rFu3GZ1SRp21uiQgWCnylS/eL9KqSWkHP9akHcwmJhdwadOg3QYPFeM9et+jSDIgokwP
Ra7B84NyIJMKCpNkoMzKXg5NLYJj9DshtJC1GKvnqeLKxIBMltOVOlaYt9EcYFdI7SY5fwxOjsfZ
kFghPA91dCUvJ/JUgFevrzNV4nGN0XeJsMFfzHGY1GDiN0cjKpwiz4vdIUdgh7c5pSiqfWD6jjdS
zCwk2Zh1bIUX/VhhUizBanX2XrSGf+yLe+0NmK+74jF+okOnKdSFP2D8jqO5BXu1xydbPtXi
','eJyNUl1vgjAU/StLn9W0xY/BWy062TIl0D0tPhAKzkQKgbrNGf/7bosYt8xkl9DPc+85994e0XTf
PDcb5B0Rq6qFrM1q3o7brWxnFahGq8DuVvrN3pod4rMlC/xHhk5gPSTKf/rFwWIKfuTsN91+gYbA
kCOKKcEudbrQI4IjTDEYcd171EOA9LO8BVdJ2gwwpvCTAcFwy+vM1+cwfez2qSPwyMMTDwNZD/ll
ui8ypVt9upwHYaFj3USVPXqouxJYPX/LaW18YRPFbT7xGaicKQkUyHuFKtQbtZspKUoYfhB0JQGC
FT6TDF3gsC4Q5wYYzCFDQpAhszSIccFha9JqoAW5ZYalySusdX0AzAv4QfXXl/hRlpt7LrXNP9QH
My1NbkwmxV2cFHuZ1QkyDTMolqa6a+iv9tIxBbNIUV2zcsY5si2Pq10BZ36iEytvpt531bUA44iw
ScuGfBJh37wCMiGOeQVRI5Vu07Ug8aGsVuzYvE5r+MC+AcHA0qE=
','20210923CENAIDJA510R0200001998','ACTC',NULL),
	 (4055,'1819199',54500.00,'2021-09-23 12:52:44.881','45168','SIHBIDJ1','BMRIIDJA','20210923SIHBIDJ1510O0100000052',NULL,'2021-09-23 12:52:44','2021-09-23 12:52:45',NULL,'eJyVUstuwjAQ/BefAdkhqQi3OObhqpQKrF6qHiAJD5U4yFkkWsS/d20DaquiUktxdr07nvFoD4Tv
6lG9JN0DSbbbYW5s1Pf7ep37v5a6Bi1dNoaVq9qMTOWQS3HPyBFXg6jqRhwfTSTikhOOrz9Qg7Tk
JKABo3HQPl8dMTqmjLoVBaRBsFMUC9+8nWV1i9IOfqxFO1hNTSHgdE2Txs2grVjQjYJuGBJkElW2
KwsNXh9UfZnWUJo0B2UW9nBgziY4Rb8LwhVG7O7Cp8orjA3yOB8v1L7GOsNsCrAppXZMLh6BpUgf
JgNijfA61N61vBzIUwnevZ7OVYXbNUXfLcIL/lKOZFKD4W9ORlI6R55nm12BwCiMKEVP7fuydzyQ
YmIR6cosufVd9LjCopiDteroo2QJ/xgX99gbMF9HxWM8o0NnGZwbf8BYh8Usjt2AvdrtE8ym1jk=
','eJyNUdtuwjAM/ZUpzwMlgQ7at5LC6KYBajPtYeKh6oVVomnVhg2G+Pc5Lp3YNKS5ai7Osc+xfSST
XfPUbIhzJG5VzZPanGbtmudJuytfNVr5eFvqN3w1NyKmC9f3HlxyArslsvxnXOjPJxDHznGT/BM0
+IaccMoZtfmgS20xGlBO0bhNbgkgvTRrwVUUN33ww8/6jMKrqFNPn9P0qN3jA0ktx+LO0CLA5JXx
rkiVbvXpcuavCh3qJqjQdV93LUA9f8tBY/a4Y5PFdT6591XmqgQoiPMKXag3ajtViSxh+UHQtQQI
lvRMYnHgwBDIcwUMNrTYnVEj90hDXCEFXE1ZDYwgQ2Y4mrpWta4PgHmGOOj++jt/kGbmXSQa61/p
g9kWpraXXCX5TRgVuyStI2ImZmBuHOtuor/my8bMZraNUFld8gpXCIJDD6ttAT4v0hEKnKr3bXUp
wQQSyqASzPkoVz1O2Ygxbo3AGTSJ0m3BCJIfCtXSAVZ2WsMH9gWl9NOH
','20210923CENAIDJA510R0200000029','ACTC',NULL);
INSERT INTO public.account_enquiry (id,account_no,amount,cre_dt,intr_ref_id,orign_bank,recpt_bank,bizmsgid,call_status,cihub_req_time,cihub_resp_time,error_message,full_request_msg,full_response_msg,resp_bizmsgid,response_status,log_message_id) VALUES
	 (4057,'1819199',54500.00,'2021-09-23 12:53:50.688','45168','SIHBIDJ1','BMRIIDJA','20210923SIHBIDJ1510O0100000053',NULL,'2021-09-23 12:53:50','2021-09-23 12:53:51',NULL,'eJyVUstuwjAQ/BefAdkJqQi3OObhqpQKrF6qHiAJD5U4yFkkWsS/d20DaquiUktxdr07nvFoD4Tv
6lG9JN0DSbbbYW5s1Pf7ep37v5a6Bi1dNoaVq9qMTOWQS3HPyBFXg6jqRhwfTSTikhOOrz9Qg7Tk
JKABo3EQnq+OGB1TRt2KQtIg2CmKhW/ezrK6RWkHP9aiHaymphBwuqZJ42YQKhZ0o7AbUYJMosp2
ZaHB64OqL9MaSpPmoMzCHg7M2QSn6HdBuNoRu7vwqfIKY4M8zscLta+xzjCbAmxKqR2Ti0dgKdKH
yYBYI7wOtXctLwfyVIJ3r6dzVeF2TdF3i/CCv5QjmdRg+JuTkZTOkefZZlcgMGpHlKKn9n3ZOx5I
MbGIdGWW3PouelxhUczBWnX0UbKEf4yLe+wNmK+j4jGe0aGzDM6NP2Csw2IWx27AXu32Ccjp1jc=
','eJyNUW1vgjAQ/itLP6tpizjKNwSdbJkj0H1a/GB4cSRSGijbnPG/71pkcctMdoS+XJ+757m7I5p3
7WO7Q+4ReVKuskaflv1allm/i1C0SoTm9qRezau+IX+x9sLg3kMnsBHi9T/jknA1hzhyjpuXn6Ah
1OSIYkowo9aQ2iY4xhSDkZnF0AgBMsiLHiy3aTvBmMJPJgTDq9/kgTqnGWM2phbHtmtbro0RMAV1
2lW5UL0+VS/DqFKJamNpXHfN0AKj5285xtgtHdh4dZ2Pf4Si8EQGFMh9gS40O7FfiIzXsPwgGFoC
BE/4TGJbwGFCIM8VMNjUJjMHaTJDgzyf+3DVZbUwgsIww1HXFTWqOQDmGeKg+5vv/HFe6Hc/U6b+
SB30tta1JV2bi/rGy0rZqaZGemQa56WpGkb6a8DEIYwwZqBcXhL7nu8jM/VE7ivwBVu1NQoX4m0v
LzXoQIQJlGJyPvBoTPGUOQ6dzsAZt5lQfcUGxN+FkYstU9ppAx/YF7t91DM=
','20210923CENAIDJA510R0200001639','ACTC',NULL),
	 (4059,'1819199',54500.00,'2021-09-23 12:55:42.251','45168','SIHBIDJ1','BMRIIDJA','20210923SIHBIDJ1510O0100000054',NULL,'2021-09-23 12:55:42','2021-09-23 12:55:43',NULL,'eJyVUstuwjAQ/BefAdkhrgi3OObhqpQKrF6qHiAJD5U4KFkkWsS/d20DaquiUktxdr07nvFoD0Ts
6lG9JN0DibfbYVbZqO/39Trzf6NMDUa5bAwrV7UZmaqhUPKekSOuBtHljTgxmijExSecWH+gBmXJ
SUADRqOgfb6aMzqmjLrFQ9Ig2CnzhW/eztK6RWkHP9aiHawmVS7hdE2TRs2grVnQ5bwbBgSZZJnu
ityA1wdlXyU1FFWSga4W9nBQnU1win4XhCvk7O7Cp4srjA3yOB8v9L7GOsNsCrAplHFMLh6BpUge
JgNijfA69N61vBzIUwHevZ7JdInbNUXfLcIL/lKOZMpAJd6cjLhwjjzPNrscgTzklKKn9n3pOx4o
ObGIZFUthfVd9oTGopyDteroo3gJ/xgX99gbMF9HxWM8o0OnKZwbf8BYh0UsityAvdrtE9bq1j8=
','eJyNUW1PwjAQ/iumn4G0HUW2b6UDmUYko34yfCB7wSWsa7aiIuG/e+2YQSOJt6wv1+fuee7uiCb7
5rHZouCIuNbztLanWbsWRdruKlKNUZG7PZlX92pvSEwXPArvOTqB9ZCs/hm3iuYTiCPnuEnxCRoi
S44opgT71OtSM4JjTDEYuEeohwAZZnkL1pukGWBM4ScDguFV1Flozmn62O9TT2IWMBYMKQKmsEr2
ZaZMq89Us2hZmpVpYu1cd3XXAqfnbzmt3Q47Nlle55Mfkcq5SoECBS/QhXqrdlOVygqWHwRdS4Dg
CZ9JmOVwIZDnChhsyMhojCyZo0FcSAFXW1YDI8gdMxxtXcva1AfAPEMcdH/9nT/OcvsuUuPqX5qD
3Ra2Np4Wem/q6ibOVIHswCyKJ4npBvprvGRMfOL7Dir1Ja3gQiA385XeleALN2bj9E3V205fKrCB
CBMoxOV8kMs+xR5lbOzbQcdNqkxbrwPJd+XEYs8VdlrDB/YFzvTTOA==
','20210923CENAIDJA510R0200001096','ACTC',NULL),
	 (4061,'1819199',54500.00,'2021-09-23 12:56:45.317','45168','SIHBIDJ1','BMRIIDJA','20210923SIHBIDJ1510O0100000055',NULL,'2021-09-23 12:56:45','2021-09-23 12:56:46',NULL,'eJyVUstuwjAQ/BefAdkBI8Itjnm4KqUCq5eqB0jCQyUOShaJFvHvXdukaquiUktxdr07nvFoT0Qc
qkm1Jv0Tifb7cVraaOj37Tb1f6NMBUa5bAobV7UZmauxUPKOkTOuBtHFjTgxmSnERRec2L6jBmXJ
SUADRsOgXV/NGZ1SRt3inDQIdsps5Zv3i6RqUdrDj7VoD6txmUm4XNOkYTNoaxb0ebff4QSZZJEc
8syA1wfFUMUV5GWcgi5X9nBU1iY4Rb8LwtXhrPvJp/MrjA3ysJyu9LHCOsNsDrDLlXFMLp6ApYjv
ZyNijfA69NG1PJ/IYw7evYFJdYHbNUXfLcIL/lKOZMpAKV6djCh3jjwtdocMgbzDKUVP7fuSNzxQ
cmYR8aZcC+u7HAiNRbkEa9XZR9Ea/jEu7rE3YL6Oisd4RodOEqgbf8BYj4UsDN2AvdjtA+hz1kk=
','eJyNUW1vgjAQ/itLP0/T8qKWb1h0smVKoMs+LH4wFByJFAJ1mzP+912LLG6ZyY7Ql+tz9zx3d0TT
ffvYbpF3RH5dL0SjT/NuLQrR7TKUrZKhua3Uq3nVN8RmSz8M7n10ArtFvPpnXBIuphBHznHT4hM0
hJocWdgimFp2n9olOMYWBiPOxEG3CJBBlnfgepO2Q4wt+MmQYHhlTRaoc5oBpgPL5tj13JHnuAiY
girdl5lUnT5VzcOoVIlq49q47pq+BUbP33KMFtsmPRsvr/Pxj1DmvhRAgbwX6EKzlbuZFLyC5QdB
3xIgWOGOBLsucJgQyHMFDOa4ZDRBmszQIJ9xBlddVgsjyA0zHHVdUaOaA2CeIA66v/7OH2e5fmdC
mfojddDbUteW7NtMVjfPhahEhfTANMpPU9UP9Nd4yYRQQqmB8vqSlvmMITPzpN6V4As2amP0zeTb
rr5UoAMR1l02OR94NLAwHdMxpjY441ZI1dVrQPxdGrHYNoWd1vCBfQHm6NNY
','20210923CENAIDJA510R0200001484','ACTC',NULL),
	 (4063,'1819199',54500.00,'2021-09-23 13:01:24.124','45168','SIHBIDJ1','BMRIIDJA','20210923SIHBIDJ1510O0100000056',NULL,'2021-09-23 01:01:24','2021-09-23 01:01:24',NULL,'eJyVUstuwjAQ/BefAdkJQYRbHPNwVUoFVi9VD5CEh0oc5CwSLeLfu7ahaquiUktxdr07nvFoj4Tv
63G9Ir0jSXa7UW5sNPD7ZpP7v5a6Bi1dNoG1q9qMzOSIS3HHyAlXg6jqRhwfTyXikjOOb95Rg7Tk
JKABo3EQXq6OGJ1QRt2KOqRBsFMUS9+8m2d1i9IufqxFu1hNTSHgfE2Txs0gVCzsUdYL2gSZRJXt
y0KD1wfVQKY1lCbNQZmlPRyaiwlO0e+CcLUj1vnkU+UVxgZ5WEyW6lBjnWE2A9iWUjsmF4/BUqT3
0yGxRngd6uBano/ksQTvXl/nqsLtmqLvFuEFfylHMqnB8FcnIymdI0/z7b5AYNSOKEVP7fuyNzyQ
YmoR6dqsuPVd9LnColiAterko2QF/xgX99gbMF9HxWM8o0NnGVwaf8BYl8Usjt2AvdjtA8D41jM=
','eJyNUW1vgjAQ/itLP6tpQVD4VotOZqYGu0+LHwgvzkQKoWXTGf/7rkUWt8xkR+jL9bl7nrs7o0kj
n+UO+WdEq2qe1vo0a9f9Pm13EQqpRGhuK/VmXvUNsemShsETRRewHuLlP+M24XwCceQaN9l/goZQ
kyMLWwR7lt2ldgiOsIW1jWwH9RAggyxvwVWcyAHGFvxkQDC8sjoL1DVNH3t9y+bY9THxrSECpqBM
miITqtWnylm4LtRGyagyrse6a4HR87ccY95o3LHx4j4fP4YipyIFCuS/QhfqnThMRcpLWH4QdC0B
ghW+kjgucJgQyHMHDDZ0iKvV8KOhQZRxBlddloQR5IYZjrquda3qE2BeIA66v/3OH2W5fmepMvWv
1UlvS13bopFNET/QNC6QHpfG0CRR3Th/DZeMiUc8z0B5dUvKKGPITHxTHQrwBbGKjbqpeD9Ut/w6
EGECZZicC77uW9h2iO2OhuCMZCpUW60B8Q9hpGLblHXZwgf2BeZ70kY=
','20210923CENAIDJA510R0200000735','ACTC',NULL);INSERT INTO public.cb_account (id,account_no,account_type,additional_info,amount,creditor_account_number,creditor_account_type,creditor_id,creditor_name,creditor_resident_status,creditor_status,creditor_town_name,creditor_type,intr_ref_id) VALUES
	 (1,'7723004444','CACC','Satu dua tiga',200000.00,'7723004444','CACC','123455555','ANDI ANDI','Resident','ACCEPTED','Jakarta','Private','040502345'),
	 (2,'7723002222','CACC','Satu dua tiga',200000.00,'7723004444','CACC','123455555','ANDI ANDI','Resident','HOLD','Jakarta','Private','023232323');INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (3855,NULL,'',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-22 19:08:15.787567',NULL,NULL,'14163'),
	 (3858,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-22 19:16:37.780211','2021-09-22 19:16:38.301113','SUCCESS','56163'),
	 (3860,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-22 19:17:43.298519','2021-09-22 19:17:43.34571','SUCCESS','56163'),
	 (3921,54500.00,'Internet Banking',NULL,'1819199',NULL,NULL,NULL,'Account Enquiry','014','2021-09-22 23:02:06.289914','2021-09-22 23:02:07.275889','SUCCESS','45168'),
	 (3862,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-22 19:18:38.914352','2021-09-22 19:18:38.9727','SUCCESS','5000'),
	 (3864,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-22 19:20:08.080297',NULL,NULL,'5000'),
	 (3923,825200.00,'Internet Banking','P AI','262222','Fandi Wijaya','783784',NULL,'Credit Transfer','014','2021-09-22 23:02:11.770006',NULL,NULL,'31121'),
	 (3866,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556','Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','Proxy Registration',NULL,'2021-09-22 19:39:40.527346','2021-09-22 19:39:40.844548','ERROR-CIHUB','5100'),
	 (3872,NULL,'Internet Banking',NULL,NULL,NULL,NULL,NULL,'Proxy Resolution',NULL,'2021-09-22 19:59:17.287835',NULL,NULL,'1B41'),
	 (3931,NULL,'Internet Banking',NULL,NULL,NULL,NULL,NULL,'Payment Status',NULL,'2021-09-22 23:07:53.72538',NULL,NULL,'241256');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (3874,NULL,'Internet Banking',NULL,NULL,NULL,NULL,NULL,'Proxy Resolution',NULL,'2021-09-22 20:29:48.340365','2021-09-22 20:29:48.834448','SUCCESS','1B41'),
	 (3876,NULL,'Internet Banking',NULL,NULL,NULL,NULL,NULL,'Proxy Resolution',NULL,'2021-09-22 20:30:49.047744',NULL,NULL,'1B41'),
	 (3881,NULL,'Internet Banking',NULL,NULL,NULL,NULL,NULL,'Proxy Resolution',NULL,'2021-09-22 20:42:45.033861','2021-09-22 20:42:45.566125','SUCCESS','11441'),
	 (3883,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-22 20:43:36.071887','2021-09-22 20:43:36.19764','SUCCESS','55140'),
	 (3886,54500.00,'Internet Banking',NULL,'96599',NULL,NULL,NULL,'Account Enquiry','014','2021-09-22 21:23:37.493766',NULL,NULL,'6118'),
	 (3942,825200.00,'Internet Banking','P AI','262222','Fandi Wijaya','783784',NULL,'Credit Transfer','014','2021-09-23 05:39:00.814677','2021-09-23 05:39:05.664156','SUCCESS','31120'),
	 (3895,54500.00,'Internet Banking',NULL,'819199',NULL,NULL,'Timeout menunggu response dari CIHUB','Account Enquiry','014','2021-09-22 21:33:15.364908','2021-09-22 21:33:17.456222','TIMEOUT-CIHUB','45168'),
	 (3956,825200.00,'Internet Banking','P AI','262222','Fandi Wijaya','783784',NULL,'Credit Transfer','014','2021-09-23 07:52:51.305439','2021-09-23 07:52:52.58417','SUCCESS','30920'),
	 (3965,54500.00,'Internet Banking',NULL,'1819199',NULL,NULL,NULL,'Account Enquiry','008','2021-09-23 08:05:39.212959',NULL,NULL,'45168'),
	 (3967,54500.00,'Internet Banking',NULL,'1819199',NULL,NULL,NULL,'Account Enquiry','008','2021-09-23 08:28:33.466875',NULL,NULL,'45168');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (3903,NULL,'',NULL,NULL,NULL,NULL,NULL,'Payment Status',NULL,'2021-09-22 22:29:59.923453','2021-09-22 22:29:59.966173','SUCCESS','244556'),
	 (3907,NULL,'Internet Banking',NULL,NULL,NULL,NULL,NULL,'Payment Status',NULL,'2021-09-22 22:37:06.464476','2021-09-22 22:37:06.968841','SUCCESS','2456256'),
	 (3909,NULL,'Internet Banking',NULL,NULL,NULL,NULL,'Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','Payment Status',NULL,'2021-09-22 22:38:19.926935','2021-09-22 22:38:19.961686','ERROR-CIHUB','2456256'),
	 (3970,54500.00,'Internet Banking',NULL,'1819199',NULL,NULL,NULL,'Account Enquiry','008','2021-09-23 08:53:37.219829','2021-09-23 08:53:38.460015','SUCCESS','45168'),
	 (3912,NULL,'Internet Banking',NULL,NULL,NULL,NULL,'Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','Payment Status',NULL,'2021-09-22 22:51:51.872978','2021-09-22 22:51:52.147619','ERROR-CIHUB','241256'),
	 (3914,825200.00,'Internet Banking','P AI','262222','Fandi Wijaya','783784',NULL,'Credit Transfer','014','2021-09-22 22:55:33.776716',NULL,NULL,'32221'),
	 (3915,825200.00,'Internet Banking','P AI','262222','Fandi Wijaya','783784',NULL,'Credit Transfer','014','2021-09-22 22:56:30.233554',NULL,NULL,'31121'),
	 (3916,54500.00,'Internet Banking',NULL,'819199',NULL,NULL,'Timeout menunggu response dari CIHUB','Account Enquiry','014','2021-09-22 22:58:28.368542','2021-09-22 22:58:30.484841','TIMEOUT-CIHUB','45168'),
	 (3918,54500.00,'Internet Banking',NULL,'1819199',NULL,NULL,NULL,'Account Enquiry','014','2021-09-22 22:58:43.167202','2021-09-22 22:58:43.952641','SUCCESS','45168'),
	 (3920,825200.00,'Internet Banking','P AI','262222','Fandi Wijaya','783784',NULL,'Credit Transfer','014','2021-09-22 22:58:52.921397',NULL,NULL,'31121');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (3974,54500.00,'Internet Banking',NULL,'1819199',NULL,NULL,NULL,'Account Enquiry','008','2021-09-23 09:29:26.858138','2021-09-23 09:29:28.21352','SUCCESS','45168'),
	 (3978,54500.00,'Internet Banking',NULL,'1819199',NULL,NULL,NULL,'Account Enquiry','008','2021-09-23 10:04:55.907519',NULL,NULL,'45168'),
	 (3988,825200.00,'Internet Banking','P AI','262222','Fandi Wijaya','783784',NULL,'Credit Transfer','008','2021-09-23 10:21:48.046258',NULL,NULL,'30920'),
	 (3993,825200.00,'Internet Banking','P AI','262222','Fandi Wijaya','783784',NULL,'Credit Transfer','008','2021-09-23 10:44:35.701362',NULL,NULL,'30920'),
	 (4004,825200.00,'Internet Banking','P AI','262222','Fandi Wijaya','783784',NULL,'Credit Transfer','014','2021-09-23 10:52:15.311139','2021-09-23 10:52:17.020135','SUCCESS','30920'),
	 (4011,825200.00,'Internet Banking','P AI','262222','Fandi Wijaya','783784',NULL,'Credit Transfer','008','2021-09-23 10:52:21.678397','2021-09-23 10:52:22.297777','SUCCESS','321'),
	 (4058,54500.00,'Internet Banking',NULL,'1819199',NULL,NULL,NULL,'Account Enquiry','008','2021-09-23 12:55:42.188617','2021-09-23 12:55:43.244532','SUCCESS','45168'),
	 (3856,NULL,'',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-22 19:09:53.373537','2021-09-22 19:09:53.943524','SUCCESS','14163'),
	 (3865,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-22 19:37:50.129346',NULL,NULL,'5000'),
	 (3868,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556','Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','Proxy Registration',NULL,'2021-09-22 19:43:21.773247','2021-09-22 19:43:22.162234','ERROR-CIHUB','51200');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (3924,825200.00,'Internet Banking','P AI','262222','Fandi Wijaya','783784',NULL,'Credit Transfer','014','2021-09-22 23:05:52.376315','2021-09-22 23:05:54.167942','SUCCESS','31121'),
	 (3870,NULL,'Internet Banking',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-22 19:44:09.276634','2021-09-22 19:44:10.274767','SUCCESS','5500'),
	 (3873,NULL,'Internet Banking',NULL,NULL,NULL,NULL,NULL,'Proxy Resolution',NULL,'2021-09-22 20:24:57.230248',NULL,NULL,'1B41'),
	 (3853,NULL,'',NULL,NULL,'Fras Manse','14556',NULL,'Proxy Registration',NULL,'2021-09-22 18:55:46.192191','2021-09-22 18:55:46.745577','SUCCESS','24563'),
	 (3877,NULL,'Internet Banking',NULL,NULL,NULL,NULL,'Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','Proxy Resolution',NULL,'2021-09-22 20:32:13.59356','2021-09-22 20:32:13.946089','ERROR-CIHUB','1B41'),
	 (3879,NULL,'Internet Banking',NULL,NULL,NULL,NULL,NULL,'Proxy Resolution',NULL,'2021-09-22 20:34:37.559258','2021-09-22 20:34:38.662939','SUCCESS','1B41'),
	 (3885,54500.00,'Internet Banking',NULL,'96599',NULL,NULL,NULL,'Account Enquiry','014','2021-09-22 21:20:55.4411',NULL,NULL,'6118'),
	 (3933,NULL,'Internet Banking',NULL,NULL,NULL,NULL,NULL,'Payment Status',NULL,'2021-09-22 23:15:20.022842','2021-09-22 23:15:20.656622','SUCCESS','241256'),
	 (3887,54500.00,'Internet Banking',NULL,'96599',NULL,NULL,NULL,'Account Enquiry','014','2021-09-22 21:26:05.155585','2021-09-22 21:26:05.667665','SUCCESS','6118'),
	 (3889,54500.00,'Internet Banking',NULL,'19199',NULL,NULL,NULL,'Account Enquiry','014','2021-09-22 21:28:14.104553','2021-09-22 21:28:14.145075','SUCCESS','61568');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (3891,54500.00,'Internet Banking',NULL,'19199',NULL,NULL,NULL,'Account Enquiry','014','2021-09-22 21:29:42.909142','2021-09-22 21:29:43.015948','SUCCESS','61168'),
	 (3935,825200.00,'Internet Banking','P AI','262222','Fandi Wijaya','783784',NULL,'Credit Transfer','014','2021-09-22 23:15:24.266861','2021-09-22 23:15:25.584296','SUCCESS','31121'),
	 (3893,54500.00,'Internet Banking',NULL,'19199',NULL,NULL,'Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','Account Enquiry','014','2021-09-22 21:30:20.444486','2021-09-22 21:30:20.471614','ERROR-CIHUB','45168'),
	 (3949,825200.00,'Internet Banking','P AI','262222','Fandi Wijaya','783784',NULL,'Credit Transfer','014','2021-09-23 05:51:20.574006','2021-09-23 05:51:22.648434','SUCCESS','30920'),
	 (3905,NULL,'Internet Banking',NULL,NULL,NULL,NULL,NULL,'Payment Status',NULL,'2021-09-22 22:32:26.418337','2021-09-22 22:32:26.937477','SUCCESS','2476356'),
	 (3911,NULL,'Internet Banking',NULL,NULL,NULL,NULL,'DirectConsumerNotAvailableException','Payment Status',NULL,'2021-09-22 22:50:42.560479',NULL,'ERROR-KM','241256'),
	 (3963,54500.00,'Internet Banking',NULL,'1819199',NULL,NULL,NULL,'Account Enquiry','014','2021-09-23 07:56:19.226997','2021-09-23 07:56:20.365599','SUCCESS','45168'),
	 (3966,54500.00,'Internet Banking',NULL,'1819199',NULL,NULL,NULL,'Account Enquiry','008','2021-09-23 08:27:51.431142',NULL,NULL,'45168'),
	 (3968,54500.00,'Internet Banking',NULL,'1819199',NULL,NULL,NULL,'Account Enquiry','008','2021-09-23 08:30:59.27351','2021-09-23 08:31:00.556113','SUCCESS','45168'),
	 (3972,54500.00,'Internet Banking',NULL,'1819199',NULL,NULL,NULL,'Account Enquiry','008','2021-09-23 09:24:08.485084','2021-09-23 09:24:09.851323','SUCCESS','45168');
INSERT INTO public.channel_transaction (id,amount,channel_code,creditor_account_name,creditor_account_number,debtor_account_name,debtor_account_number,error_msg,msg_name,recpt_bank,request_time,response_time,status,transaction_id) VALUES
	 (3976,54500.00,'Internet Banking',NULL,'1819199',NULL,NULL,NULL,'Account Enquiry','008','2021-09-23 09:44:46.307711','2021-09-23 09:44:47.798765','SUCCESS','45168'),
	 (3979,54500.00,'Internet Banking',NULL,'1819199',NULL,NULL,NULL,'Account Enquiry','008','2021-09-23 10:13:38.167185','2021-09-23 10:13:39.374003','SUCCESS','45168'),
	 (3981,825200.00,'Internet Banking','P AI','262222','Fandi Wijaya','783784',NULL,'Credit Transfer','008','2021-09-23 10:15:07.933002','2021-09-23 10:15:09.409063','SUCCESS','30920'),
	 (3989,825200.00,'Internet Banking','P AI','262222','Fandi Wijaya','783784',NULL,'Credit Transfer','008','2021-09-23 10:40:16.086519',NULL,NULL,'30920'),
	 (4060,54500.00,'Internet Banking',NULL,'1819199',NULL,NULL,NULL,'Account Enquiry','008','2021-09-23 12:56:45.197509','2021-09-23 12:56:46.275477','SUCCESS','45168'),
	 (3997,825200.00,'Internet Banking','P AI','262222','Fandi Wijaya','783784',NULL,'Credit Transfer','014','2021-09-23 10:48:33.623316','2021-09-23 10:48:35.214138','SUCCESS','30920'),
	 (4062,54500.00,'Internet Banking',NULL,'1819199',NULL,NULL,NULL,'Account Enquiry','008','2021-09-23 13:01:24.085329','2021-09-23 13:01:24.664506','SUCCESS','45168');INSERT INTO public.corebank_transaction (transaction_id,addt_info,channel_ref_id,credit_amount,creditor_bank,cstm_account_name,cstm_account_no,cstm_account_type,debit_amount,debtor_bank,status,transaction_type,trns_dt) VALUES
	 (3623,'Info tambahan disini','312575',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 00:36:39.761222'),
	 (3626,'Info tambahan disini','310005',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 00:39:11.878137'),
	 (3629,'Info tambahan disini','311115',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 07:44:25.429889'),
	 (3646,'Info tambahan disini','3125615',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 08:25:05.348097'),
	 (3649,'Info tambahan disini','3125615',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 08:27:21.548963'),
	 (3656,'Info tambahan disini','3125615',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 08:32:44.091039'),
	 (3673,'Info tambahan disini','3125615',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 09:23:24.941696'),
	 (3678,'Info tambahan disini','3124615',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 09:27:16.850065'),
	 (3683,'Info tambahan disini','3124615',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 09:31:31.287429'),
	 (3690,'Info tambahan disini','312115',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 09:40:26.488708');
INSERT INTO public.corebank_transaction (transaction_id,addt_info,channel_ref_id,credit_amount,creditor_bank,cstm_account_name,cstm_account_no,cstm_account_type,debit_amount,debtor_bank,status,transaction_type,trns_dt) VALUES
	 (3697,'Info tambahan disini','31415',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 09:48:00.817371'),
	 (3704,'Info tambahan disini','31215',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 09:49:44.233562'),
	 (3711,'Info tambahan disini','32415',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 09:53:36.695981'),
	 (3719,'Info tambahan disini','3225',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 10:06:41.43645'),
	 (3728,'Info tambahan disini','32115',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 10:16:30.371972'),
	 (3737,'Info tambahan disini','33305',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 10:27:23.087629'),
	 (3751,'Info tambahan disini','1176564',NULL,NULL,NULL,NULL,NULL,60030700.00,NULL,'SUCCESS','FI Transfer','2021-09-22 12:16:33.282552'),
	 (3755,'Info tambahan disini','962101',NULL,NULL,NULL,NULL,NULL,7849000.00,NULL,'FAILED','FI Transfer','2021-09-22 13:22:06.002863'),
	 (3757,'Info tambahan disini','962101',NULL,NULL,NULL,NULL,NULL,7849000.00,NULL,'FAILED','FI Transfer','2021-09-22 13:22:50.303051'),
	 (3759,'Info tambahan disini','962101',NULL,NULL,NULL,NULL,NULL,7849000.00,NULL,'FAILED','FI Transfer','2021-09-22 13:22:59.35478');
INSERT INTO public.corebank_transaction (transaction_id,addt_info,channel_ref_id,credit_amount,creditor_bank,cstm_account_name,cstm_account_no,cstm_account_type,debit_amount,debtor_bank,status,transaction_type,trns_dt) VALUES
	 (3761,'Info tambahan disini','33105',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 13:23:53.454147'),
	 (3769,'Info tambahan disini','33105',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 13:27:03.374989'),
	 (3777,'Info tambahan disini','33105',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 13:28:41.191571'),
	 (3785,'Info tambahan disini','311135',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 13:30:14.183363'),
	 (3793,'Info tambahan disini','311135',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 13:32:17.748865'),
	 (3801,'Info tambahan disini','311135',NULL,NULL,'Fandi Wijaya','788884','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 13:34:17.67198'),
	 (3809,'Info tambahan disini','353865',NULL,NULL,'Fandi Wijaya','783784','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 13:41:16.750363'),
	 (3817,'Info tambahan disini','353865',NULL,NULL,'Fandi Wijaya','783784','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 13:41:22.728973'),
	 (3825,'Info tambahan disini','32221',NULL,NULL,'Fandi Wijaya','783784','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 13:47:00.762522'),
	 (3834,'Info tambahan disini','32221',NULL,NULL,'Fandi Wijaya','783784','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 17:15:59.1752');
INSERT INTO public.corebank_transaction (transaction_id,addt_info,channel_ref_id,credit_amount,creditor_bank,cstm_account_name,cstm_account_no,cstm_account_type,debit_amount,debtor_bank,status,transaction_type,trns_dt) VALUES
	 (3926,'Info tambahan disini','31121',NULL,NULL,'Fandi Wijaya','783784','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 23:05:53.499825'),
	 (3937,'Info tambahan disini','31121',NULL,NULL,'Fandi Wijaya','783784','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-22 23:15:24.936432'),
	 (3944,'Info tambahan disini','31120',NULL,NULL,'Fandi Wijaya','783784','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-23 05:39:04.579495'),
	 (3951,'Info tambahan disini','30920',NULL,NULL,'Fandi Wijaya','783784','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-23 05:51:21.807545'),
	 (3958,'Info tambahan disini','30920',NULL,NULL,'Fandi Wijaya','783784','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-23 07:52:51.888103'),
	 (3983,'Info tambahan disini','30920',NULL,NULL,'Fandi Wijaya','783784','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-23 10:15:08.574373'),
	 (3999,'Info tambahan disini','30920',NULL,NULL,'Fandi Wijaya','783784','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-23 10:48:34.60081'),
	 (4006,'Info tambahan disini','30920',NULL,NULL,'Fandi Wijaya','783784','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-23 10:52:16.375007'),
	 (4018,'Info tambahan disini','3050',NULL,NULL,'Fandi Wijaya','783784','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-23 10:52:33.637247'),
	 (4030,'Info tambahan disini','30501',NULL,NULL,'Fandi Wijaya','783784','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-23 11:03:04.22223');
INSERT INTO public.corebank_transaction (transaction_id,addt_info,channel_ref_id,credit_amount,creditor_bank,cstm_account_name,cstm_account_no,cstm_account_type,debit_amount,debtor_bank,status,transaction_type,trns_dt) VALUES
	 (4042,'Info tambahan disini','31401',NULL,NULL,'Fandi Wijaya','783784','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-23 11:07:19.189486'),
	 (4049,'Info tambahan disini','31411',NULL,NULL,'Fandi Wijaya','783784','CACC',825200.00,NULL,'SUCCESS','Debit','2021-09-23 12:07:02.203981');INSERT INTO public.credit_transfer (id,settlconf_bizmsgid,amount,call_status,cihub_req_time,cihub_resp_time,crdttrn_req_bizmsgid,crdttrn_resp_bizmsgid,cre_dt,creditor_acct_no,creditor_acct_type,creditor_id,creditor_type,debtor_acct_no,debtor_acct_type,debtor_id,debtor_type,full_request_msg,full_response_msg,intr_ref_id,msg_type,orign_bank,recpt_bank,resp_status,reversal) VALUES
	 (3658,NULL,825200.00,'SUCCESS','2021-09-22 08:32:44','2021-09-22 08:32:44','20210922SIHBIDJ1010O0100000018','20210922CENAIDJA010R0200001793','2021-09-22 08:32:44.106','57852','SVGS','C4403','02','788884','CACC','01445','01','eJyVUttO4zAQ/RXk54Ick4q0b7m0xStoI2KxD2gfQtKG7CZp5EwR3ar/zniSVAW1Aiw5sT23c87M
jnmb5r7J2HjH3Lq+TbU5Tdtvnqftv5JVA5Wk2wJeyGpuLJK3ngx+WWyPa8DU+ptx/mTuYpzbxXn5
f8QgTXEmuLD4SIg+Nbf4Ajcty2EDhp7BctU613HSXHHu4LauuLH6ehlAl+aSjy6FUNwZX4uxbTOs
FKyTTbmsoMUH66n0Gyi1n4LSK/M4070IhOg0oA7NqK+nyjMVB2z+vFiptwbtFt4igKKUFVWi8z2Q
HHcPM2aEaHGoN3J52rGwhFa9SZWqNX7OIfooESb4CjkWw+Sq7sDcJYXplS7NJdSgtxjPLePmQ7YN
N7r+bEGTrEB7/4iIW5Kmj3GxWaKDI4aCc2yLkSgxITJ4oGwvOvNM64KJp9AYPAOpPTcSTuMqzS9+
53/jbYy2lnqoX+F4hJ66GeKWbQ/Z/g9NkEnjJgn0E/Zp3m4cXDZhVh+I+K7vs0OCDH4w79Stb8Qc
z3obc+AbXrjywHOhs1M0fdvm1z1Nqnie5vDGGYoTLKPHWUTPUV2U+BTEEFOFSfVakGffBhPX9z09
fjJpEYSBsX8HtLY5PQ==
','eJyNUlFvgjAQ/ivmntW0lWTCGwJOTCYG2NPCA7OgZFII1EVn/O+7FtjDotmaUHrH3fd9fNcrLE7t
S7sH6wp2Xa94o07Lbi8K3r2FL1opfB0F8qC/qggcb2P77tqGG64xxNU/+yJ/tcA+2vctii/U4Cty
YIRRYjI2QBNKQsIILvpkzmAMWOlmeVdcp7t2SgjDh04pAQ0Vfe6UtDj0om2wiTzMOk3myh58QswJ
YzGh1oxZhgHI71a7U5kJ2amW1dLfljKSbVjr1HMzGKNV3heplzEzBra4fMwXNHtxRFRf5LbgSATW
27XL/mYYnBoYCDVh/FO6KXVxb8Nc20DmcEtwFuc76J7gcYXbI4pgoKHzgQVx/tYTnzUNhGsnxlBZ
1+Lwc82MR+XdtpHNBWtesUeZYHMuj10NrNJUFqOD2t5TcUk/RnUmivqUCkjUz/RKwixXSA6Xahp4
dRJ1f74BFifMQg==
','3125615','Credit Transfer','SIHBIDJ1','CENAIDJA','RJCT',NULL),
	 (3666,NULL,825200.00,'SUCCESS','2021-09-22 09:04:47','2021-09-22 09:04:47','20210922SIHBIDJ1010O0100000023','20210922CENAIDJA010R0200000648','2021-09-22 09:04:47.011','57852','SVGS','C4403','02','788884','CACC','01445','01','eJyVUttu4jAQ/ZXKz7Ry3CACb7kA9aqFqLG6D9U+pAmk2U1C5AxVWcS/dzxJEK1A27XkxPbczjkz
e+Ztm4cmY5M9c+v6LtXmNGu/eZ62/0pWDVSSbkt4Jau5sUjeeTL4YbEDrgFTm2/G+dOFi3FuF+fl
fxGDNMWZ4MLiYyH61NziS9y0xC0bMPQMVuvWuY6T5oZzB7d1wx20+noVQJfmmo+vhVB8POH2xB4x
rBRskm25qqDFB5uZ9BsotZ+C0mvzONe9CIToPKAOjd3XU+WFigO2eFmu1XuDdgtvEUBRyooq0fkB
SI77xzkzQrQ41Du5PO9ZWEKr3rRK1QY/lxB9lggT/As5FsPkqu7A3CeF6ZUuzSXUoHcYzy3j5kO2
C7e6/mpBk6xAe3+IiFuSpk9xsV2hgyOGgnNsi5EoMSEyeKRsrzrzTOuCqafQGLwAqb0wEs7iKs2v
fua/412MtpZ6qN/gdISeuxnilm0P2eEXTZBJ4yYJ9BP2Zd5GDi6bMKtPRHzX99kxQQb/Me/UrW/E
nM56G3PkG1658shzqbNzNH3b5rc9Tap4meZw5AzFGZbR0zyi56guSnwKYoipwrR6K8izb4OJ6/ue
nj6ZtAjCwDh8AJqkOTM=
','eJyNUl1vgjAU/SvmPqspHdmQN76cNZkYYE+LD8yCkkkhUBed8b/vtsAeFs12Ey5tOT3ncNoLuMf2
pd2BfQGnrhe8UaN514uCd2/BRCsF07NQ7vVXNQMvWDnMXzpwxRpDUv1zX8wWLu4z+n1u8YUemBIH
SqhBZpQO1MQgEaFE1aNpwRgQ6Wd5B67TbTslhOJjTA0Cmir+3CprSRTE63AVB7jqNZkve/IJmU0o
TQi1iWmbT4D6frU9lpmQnWtZzdm6lLFso1ovPTdDMNrlbZO6LIsOakl5Xy9sduKArEzkjuAoBPbb
pVv9rTAkNSgQasL4B7oqNbiPwdIxEAuuGzyL0w32QPCkwnZPIhxk6MOggjx/+0lOWgaipZfgVEXX
4uHnWhmHKrt1I5szYl7RpArB4VweOgws0lQWo71q76k4px+jOhNFfUwFbNTP9E6iLFdMHpfqNPDq
bNT9+QYcdsxB
','3125615','Credit Transfer','SIHBIDJ1','CENAIDJA','RJCT',NULL),
	 (3670,NULL,825200.00,'SUCCESS','2021-09-22 09:18:10','2021-09-22 09:18:11','20210922SIHBIDJ1010O0100000026','20210922CENAIDJA010R0200001404','2021-09-22 09:18:10.608','57852','SVGS','C4403','02','788884','CACC','01445','01','eJyVUttuozAQ/ZXKz2llvGRD8sYlSb1qE1Ss7kO1DxQSyi4QZCZVs1H+veMBorRKtF1LBttzO+fM
7Jm3be6bjE32zK3r21Sb06z95nna/itZNVBJui3hhazmxiJ568ngh8UOuAZMbb4Y508XLsa5XZyX
/0UM0hRngguLj4XoU3OLL3HTEt/ZgKFnsFq3znWcNDecO7itG+6g1derALo013x8LYTi44nlTCzO
sFKwSbblqoIWH2xm0m+g1H4KSq/N41z3IhCi84A6NKO+niovVBywxfNyrd4atFt4iwCKUlZUic73
QHLcPcyZEaLFod7I5WnPwhJa9aZVqjb4uYToo0SY4F/IsRgmV3UH5i4pTK90aS6hBr3DeG4ZNx+y
XbjV9WcLmmQF2vtDRNySNH2Mi+0KHRwxFJxjW4xEiQmRwQNle9GZZ1oXTD2FxuAZSO2FkXAWV2l+
9TP/He9itLXUQ/0KpyP01M0Qt2x7yA6/aIJMGjdJoJ+wT/M2cnDZhFl9IOK7vs+OCTL4j3mnbn0h
5nTW25gj3/DKlUeeS52do+nbNv/W06SKl2kOR85QnGEZPc4jeo7qosSnIIaYKkyr14I8+zaYuL7v
6emTSYsgDIzDO51xOTU=
','eJyNUtFugjAU/RVzn9W0jdmUNwScNZkYYE+LD8yCkkkhUBed8d93W+jLotmaUHpv7z3n9LRXmJ/a
13YPzhXcul6KRq8W3VwUovtLLlsluYlCdTC7OgIvWLvcX7lwwzGEpPpnX8yXc+yjfd+8+EYNXJMD
I4ySGWMWmlASEUZw0AmZwBCw0s/yrrhOd+2YEIYfHVMCBir+2mlpSRTEm3AdB5j1msxXPfiIzEaM
JYQ5dOpgD/L71e5UZlJ1qlW14JtSxaqNapN6aawxRuV9kUYhI8+WLSkf84XNXh4RlcvclQKJwHm/
dtnfDNapngHPqhls6bo0xb0NU2MDmcJti3dxvoMeSJFUOD2iCC0Ne7IsiPO3nuRsaCBaeQmG2roW
Lz83zLjU3m0a1Vyw5g1FahNcIdSxq4FlmqpicNDTRyov6eegzmRRn1IJW32YXkmU5RrJE0rfBj6d
rX4/P/VfzC8=
','3125615','Credit Transfer','SIHBIDJ1','CENAIDJA','RJCT',NULL),
	 (3675,NULL,825200.00,'SUCCESS','2021-09-22 09:23:24','2021-09-22 09:23:25','20210922SIHBIDJ1010O0100000029','20210922CENAIDJA010R0200001541','2021-09-22 09:23:24.957','57852','SVGS','C4403','02','788884','CACC','01445','01','eJyVUl1P6kAQ/Stmn9FslxILb/0AXKPQ2I0+GB9qC7X3tqXZDkYk/Hdnp5SggVzvJtvu7nydc2a2
zFs3903GRlvm1vVNqs1p0n7zPG3/lawaqCTd5vBGVnNjkbzxZHBrsR2uHlOrX8b545mLce4+zss/
EYM0xZngwuJDIbrU3OJz3LTEkPUYegaLZetcx0lzxbmD27riDlp9vQhgn+aSDy+FUHw4Ev2RsBlW
ClbJulxU0OKD1UT6DZTaT0HppXmc6k4EQnQaULv6vKunyjMVe2z2Ol+qjwbtFt4igKKUFVWi8z2Q
HHcPU2aEaHGoD3J53rKwhFa9cZWqFX7OIfouESb4F3IshslVvQdzlxSmV7o0l1CD3mA8t4ybD9km
XOv6pwVNsgLt/SUibkmaPsbFeoEOjhgIzrEtRqLEhMjggbK96cwzrQvGnkJj8Aqk9sxIOImrNL94
yv/EmxhtLfVQv8PxCD3vZ4hbtj1guxeaIJPGTRLoJuzHvF07uGzCrL4R8V3fZ4cEGfzHvFO3fhFz
POttzIFveOHKA8+5zk7R9G2b9zuaVPE8zcG1MxAnWEaP04ieo7oo8SmIIaYK4+q9IM+uDSau63t6
/GTSIggDY/cFlfg5MQ==
','eJyNUtFugjAU/RVzn9W01SXKGwJOTCYG2NPCAxNQMikE6qIz/vtuW7qHRbPdhNLb3nvO6WmvsDh1
L90erCvYTbPKWjlb6rEsM/3nPu8E91UWiIPalRk43sb23bUNN4whxPU/+yJ/tcA+2vctyi/U4Ety
YIRRMmfMQBNKQsIIBn2aUhgCVrp5oYubdNeNCWH40TEloKCiz52UFodetA02kYerTpu7ogcfkfmI
sZgwi00sNgXkd+vdqcq50KpFvfS3lYhEFzZq6bk1xiiV90XqmDLDFleP+YJ2z4+I6vPC5hkSgfV2
1au/GYxTPwwTeUpTuqlUcW/DTNlAZnBL8C7Od9A9nsU1Do8oAkPD5oYFcf7WE58VDYRrJ8ZUWtfh
5ReKGafSu20r2gvWvKJIaYKdZeKoa2CVpqIcHOTwnvJL+jFocl42p5RDIg/TKwnzQiI5mZC3gU8n
ke/nG+9ZzCY=
','3125615','Credit Transfer','SIHBIDJ1','CENAIDJA','RJCT',NULL),
	 (3680,NULL,825200.00,'SUCCESS','2021-09-22 09:27:16','2021-09-22 09:27:17','20210922SIHBIDJ1010O0100000032','20210922CENAIDJA010R0200000829','2021-09-22 09:27:16.863','57852','SVGS','C4403','02','788884','CACC','01445','01','eJyVUttu4jAQ/ZXKz7RyTFgCb7kA9aqFqLG6D9U+pAmk6ZIQOUNVFvHvO54kiFagbS05sT23c87M
nnnb+r7O2HjP3Kq6TbU5TZtvnqfNv5RlDaWk2wJeyGpuLJK3ngx+WuyAq8fU5otx/mTuYpzbxnn5
X8QgTXEmuLD4SIguNbf4AjetvmA9hp7BctU4V3FS33Du4LZuuINWXy8DaNNc89G1EIqPxmI4tn4w
rBRskm2xLKHBB5up9GsotJ+C0ivzONOdCIToPKAWTb+rp4oLFXts/rxYqfca7RbeIoB1IUuqROd7
IDnuHmbMCNHgUO/k8rRnYQGNepMyVRv8XEL0USJM8D/kWAyTq6oFc5esTa90YS6hBr3DeG4ZNx+y
XbjV1WcLmmQJ2vtDRNyCNH2M19slOjhiIDjHthiJEhMigwfK9qIzz7QumHgKjcEzkNpzI+E0LtP8
6lf+Gu9itDXUQ/0GpyP01M4Qt2x7wA6/aYJMGjdJoJuwT/M2dHDZhFl9IOK7vs+OCTL4xrxTt74Q
czrrTcyRb3jlyiPPhc7O0fRtm/c7mlTxMs3B0BmIMyyjx1lEz1G1LvApiCGmCpPybU2eXRtMXNf3
9PTJpEUQBsbhH6DmOTU=
','eJyNUtFugjAU/RVzn9Xc1sQpbwg4MZkYYE8LD0xAyaQQqIvO+O+7LbCHRbPdhEtbTs85nPYKi1Pz
0uzBuIJZVaukVqNl2/M8ad/CFY0Urp558qC/qhlYzsZ07bUJN6ohhOU/9wXuakH7WLdvkX+RB1eJ
A0fOcM55T40MfeSoasbnMARC2mnWgqt414wROT1szBA0VfC5U9ZC3wm23iZwaNWqU1t25COcjzgP
kRv8yWBTIH273J2KVMjWtSyX7raQgWz8Si89130w2uV9k6oYTrFXC4vHel69F0didUVmioSEwHi7
tqu/FfqkOgXEyQSGP9BNocFdDDMdA87gFtFZnO+wOyIJS2qPJLxeZsJ7FeL520941jLgr62Qpiq6
hg4/08o0VNlta1lfCPNKJlUIZpLIY4uBVRzLfHBQ7T0Wl/hjUKUir06xgEj9TOfETzPFZCVSnQZd
nUjdn28Rwsw5
','3124615','Credit Transfer','SIHBIDJ1','CENAIDJA','RJCT',NULL),
	 (3722,NULL,825200.00,'TIMEOUT-CIHUB','2021-09-22 10:06:41','2021-09-22 10:06:43','20210922SIHBIDJ1010O0100000050',NULL,'2021-09-22 10:06:41.453','8417832','SVGS','C4403','02','788884','CACC','01445','01','eJyVUl1Po0AU/StmnqsZkK7YNxjaOhttiUzcB+MDQou4QMlwa+w2/e975wJNNW3USQZm5n6dc+7d
Mn/d3DUZG22ZV9c3qTanSfvN87T9V7JqoJJ0m8MLWc2NRfLGl8Fvi+1wDZhafTNOjGcexnldnJ//
QwzSFGc2ty1+bdt9am7xOW5aQ84GDD2DxbJ1ruOkueDcxW1dcBetQi8C6NKc8+tz21YWH/FfIwdB
DliwStblooIWH6wmUjRQapGC0kvzONW9CIToOKAOjdXXU+WJigM2e54v1XuDdnOLAIpSVlSJzndA
ctzeT5kRosWh3snlccvCElr1xlWqVvg5heijRJjgK+RYDJOrugNzmxSmV7o0l1CD3mA8JzcB2SZc
6/qzBU2yAu3/JSJeSZo+xMV6gQ6uPbQ5x7YYiRITIoN7yvaiM9+0Lhj7Co3BM5DaMyPhJK7S/OxP
/hpvYrS11EP9Bocj9NjNELccZ8h2TzRBJo2XJNBP2Kd5u3JxOYRZfSAiPCHYPkEGP5h36tY3Yg5n
vY3Z8w3PPLnnOdfZMZrCcfhlT5MqnqbpOtaVe2kf4Rk9TCN6juqixKcghphqjKu3gjz7Rpi4vvPp
4ZNJizAMkN1/rCA5gQ==
',NULL,'3225','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL),
	 (3686,NULL,825200.00,'SUCCESS','2021-09-22 09:31:31','2021-09-22 09:31:31','20210922SIHBIDJ1010O0100000035','20210922CENAIDJA010R0200001586','2021-09-22 09:31:31.317','417832','SVGS','C4403','02','788884','CACC','01445','01','eJyVUttuozAQ/ZXKz2llHLIlfeOSpF61CSpW96HaBwoJpQsEmUnVbJR/3/EAUVol2tYCgz23c87M
jnmb5r7J2M2OuXV9m2rzN233PE/bbyWrBipJpwW8kNWcWCRvPRn8tNge14Cp9Rfj/MncxTi3i/Py
v4hBmuJMcGHxsRB9am7xBb60hiM2YOgZLFetcx0nzRXnDr7WFXfQ6utlAF2aSz6+FELx8c3Qwodh
pWCdbMplBS0+WE+l30Cp/RSUXpnLme5FIESnAXVofvT1VHmm4oDNnxcr9d6g3ZwigKKUFVWi/3sg
Oe4eZswI0eJQ7+TytGNhCa16kypVa9zOIfooESb4H3IshslV3YG5SwrTK12aQ6hBbzGek2Q+ZNtw
o+vPFjTJCrT3h4i4JWn6GBebJTo4YiQ4x7YYiRITIoMHyvaiM8+0Lph4Co3BM5DacyPhNK7S/OJX
/hpvY7S11EP9Bscj9NTNELdse8T2v2mCTBo3SaCfsE/zdu3gsgmz+kDEd32fHRJk8I15p259IeZ4
1tuYA9/wwpUHngudnaLp2zYf9jSp4nmatnXtDMUJmtHjLKLrqC5KvApiiKnEpHoryLPvg4nrG58e
X5m0iMLg2P8DvMA5Xw==
','eJyNUl1vgjAU/SvLfVbTFt3QNwTcuk0w2mUPiw8O0JlIIdB9Gv/7bovdkkWdl8/Sc+8593C3MHyt
x/UKBlvwyvImrfTbqLmv12nzlFzWSnKzitWL2dUr8MPI48GtBzuMFojizLwZvxliHt3nDddfqIFr
cmCEUdJnzJYmlEwJIxi0515CCxAZZMsGXC6SukMIw4t2KAFTavaWaIpQiPtwHEbCj6MRn449weMI
EX6VBWpP1Cb9NmOCsIFD8QTUEhTJa55J1XSgihGf5Gqm6mlpPl1X1iSj+LBgE06/Z9lEfpwvrlZy
g1W5XHoyRSIYPG2br38ZrGuWgTjaDwuNcgPeW+IaS4gLuzn+l48D1UOZigJvxyjin0Z6lgXr/K9H
fBga8Hzh41JbV+MgLA0zvmrvJpWqPhHzgEk4A/Of+tNsqfeDZ1V5SaLswPwZnysXowtmdvxUmY2J
+tSPSFv9uE6LtLjAjqsMLOhEvS69ch22H8ZZuclRXbBQCyM5lG+bUkMtk8AVEIq9meQ7MWnjfHYd
VNTCBlOpGgMMRLxLI4k4utNW09lKnRBDmdPtXf42dy4abcQD4xt2mhnV
','3124615','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL),
	 (3693,NULL,825200.00,'SUCCESS','2021-09-22 09:40:26','2021-09-22 09:40:27','20210922SIHBIDJ1010O0100000038','20210922CENAIDJA010R0200001416','2021-09-22 09:40:26.504','417832','SVGS','C4403','02','788884','CACC','01445','01','eJyVUttuozAQ/ZXKz2llHLoleeOSpF61CSpW96HaBwoJpQsEmUnVbJR/3/EAUVol2taSwfbczjkz
O+ZtmvsmY+Mdc+v6NtXmNG2/eZ62/0pWDVSSbgt4Iau5sUjeejL4abE9rgFT6y/G+ZO5i3FuF+fl
fxGDNMWZ4MLiIyH61NziC9y0hg4bMPQMlqvWuY6T5opzB7d1xY3V18sAujSXfHQphOKjsc3H4gfD
SsE62ZTLClp8sJ5Kv4FS+ykovTKPM92LQIhOA+rQjPp6qjxTccDmz4uVem/QbuEtAihKWVElOt8D
yXH3MGNGiBaHeieXpx0LS2jVm1SpWuPnHKKPEmGC/yHHYphc1R2Yu6QwvdKluYQa9BbjuWXcfMi2
4UbXny1okhVo7w8RcUvS9DEuNkt0cMS14BzbYiRKTIgMHijbi84807pg4ik0Bs9Aas+NhNO4SvOL
X/lrvI3R1lIP9Rscj9BTN0Pcsu1rtv9NE2TSuEkC/YR9mrcbB5dNmNUHIr7r++yQIINvzDt16wsx
x7Pexhz4hheuPPBc6OwUTd+2+bCnSRXP07StG2coTtCMHmcRPUd1UeJTEENMJSbVW0GefR9MXN/4
9PjJpEUUBsf+H/a5OXM=
','eJyNUttuozAQ/ZVqnklkGzYheSNANt5tIArep6oPWSDZSMEgcHqL+u8dG9yVqt4GYTP2mTlnhrnA
4tytuwPMLxA0zapo9deyX4/Hot8ll52S3Hip+mdutQdhnAQ8+hXAM5oDov5mXMZXC4yjQ9zi+IQa
uCYHRhglM8ZsakLJljCCRj06AQcQGZX7Htzs8m5MCMOXjikBkyq7yzVFLMR1vI4TEabJkm/XgeBp
goiwLSM1EI3IbMSYIGzukTmbAGqJ6vxclVL1Fah6yTeVylS3bczRz9Y2ySh+X7BRy9jUsonqY760
PcgTZuVyH8gCiWB+c+lP3zLYrg0MhLgzcF6hSWXAQ0t80xLiw/Mt/peHd7LHshA1Lh9RpJbG9S0L
5vlaj3gwNBCEIkRXt67DQdgbZvzUvdu0qn1EzB8Mwhm4fc2/Lff6Pvqr2iDPlR2YN+Mz9dE8MLMT
FspcbNSj3hLd6uzclbK+CopdBRbzSTqPTn2XDbOYNacKxUU7tTOKY3l3ajTUEgn0gFAszQT/FpsR
jqfnoiAH6yuk6us3EHEvjSLi6kKdvrCD+kQMZa73Y/K/tu+isYv4oL0ASSEZeQ==
','312115','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL),
	 (3700,NULL,825200.00,'SUCCESS','2021-09-22 09:48:00','2021-09-22 09:48:01','20210922SIHBIDJ1010O0100000041','20210922CENAIDJA010R0200000340','2021-09-22 09:48:00.831','417832','SVGS','C4403','02','788884','CACC','01445','01','eJyVUk1vozAQ/SuVz2lkXKqS3MAkqVdtgorVPVR7oJBQdsFBxqmajfLfOx4gSqtEbS0ZbM/Xe29m
R4JNc9/kZLwjfl3fZtqepu23KLL2r4RqjBJ4W5gXtNobicVtIMJfDtnDGhC5/mYcn8x9iPO7uKD4
DxiELU4YZQ4dMdanpg5dwMblOmRAwDNcrlrnOkmbIaUebGdIPbByvQxNl+aSji4Zk3Q0dr0xpQQq
het0Uy2VafGZ9VTwxlSaZ0bqlX2c6V4ERHQaUIeG9fVkdabigMyfFyv51oDdYo+NKSuhsBKe7w3K
cfcwI1aIFod8Q5enHYkq06o3UZlcw+ccoo8SQYKvkEMxSC7rDsxdWtpe6cpeIm30FuKpY924ybfR
RtefLWASyujgHxLxK9T0MSk3S3Dw2DWjdIgS8NSGiPABs73oPLCtCyeBBGP4bFDtuZVwmqisuPhd
/E22Cdha6pF+Nccj9NTNEHVc95rs/+AE2TR+mpp+wj7N240Hy0XM8gMR7nNODgly84N5x259I+Z4
1tuYA9/owhcHngudn6LJXZde9TSx4nmarnPjXbETNOPHWYzPcV1W8BQmJsESE/VaomffBxvXNz47
frJpAYXFsX8HtBc5Ww==
','eJyNUsFymzAQ/ZWOznZmJWhDfMOAG6U1eIxyyvTggnGdGMGAkib15N+zK1Ayk0nSLIOEtE/7nh57
ZPPbftnv2OzIwrY9Lzv6Wgzjfl8Os5a6N1raVWb+2CytWJSkoYwvQvaIMWGq+eS5XJ7P8Rwfz833
/1CDJHImQHA4E8KVBg5rEEDh+cAmDJHxthrA7aboTwAEvvyEUxZL5XcFUSRK/UyWSaqiLF3I9TJU
MksREXXb2IxEUzibCqFAzPxgBsBQS9wUt/VWm+EGplnIVW1y069bu/W9cyZZxW8LpuBeEDg2Vb/P
l3U7fcCqUlehLpGIza6Ow+5rBufayADgCzZ5hqa1BY+WBNYSCNjjL/wv929UT3SpGhzeo8gcjc8d
C9b5vx51b2lYGKkIl2Rdj41QWWb8JO9WnekeEHMJZAJJHOuvtxXl49+mC4vCuIZ51T6nAYbPbO9E
pbGJlXmgKSWr8+tN1e0PXy6am4Y50Af1fH4aeGJsxrw91Kgu3piNlZzou0NLUMekcMWATLGHf6jV
FPvT91DRBC9YajMYYCHqr7aSwBvbi262Mx+I4cLzv357udxn0WgjPhhPZ38Z2A==
','31415','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL),
	 (3707,NULL,825200.00,'SUCCESS','2021-09-22 09:49:44','2021-09-22 09:49:44','20210922SIHBIDJ1010O0100000044','20210922CENAIDJA010R0200000414','2021-09-22 09:49:44.244','417832','SVGS','C4403','02','788884','CACC','01445','01','eJyVUttO4zAQ/RXk54IcY0TKWy5t8QraiFjsA9qHkLQhu0kaOVNEt+LfGU+SqqBWy1qJE3tu55yZ
HfM37X2bs5sd85rmNjP2b9rtRZF131rVLdSKTgt4Ias9sVjd+ir84bB3XCOm19+MCyZzD+O8Ps4v
/iIGZYszwYXDx0IMqbnDF/jSkpKNGHqGy1Xn3CRpe8G5i69zwV20BmYZQp/mnI/PhdB8fCPxkQwr
het0Uy1r6PDBeqqCFioTZKDNyl7OzCACIToOqEdzNdTT1YmKIzZ/Xqz0W4t2B08xQFmpmirR/z2Q
HHcPM2aF6HDoN3J52rGogk69SZ3pNW6nEH2WCBP8CzkWw+S66cHcpaXtlansITJgthjPHesWQL6N
Nqb5akGTqsH4f4iIV5Gmj0m5WaKDK64E59gWK1FqQ1T4QNleTO7b1oUTX6MxfAZSe24lnCZ1Vpz9
LH4n2wRtHfXIvMLhCD31M8QdaXn8ogmyabw0hWHCvszbtYtLEmb9iUjgBQHbJ8jhP+aduvWNmMNZ
72L2fKMzT+15Lkx+jGYgJb8caFLF0zSlc+1eiiM048dZTNdxU1Z4FSaQUIlJ/VqS59AHGzc0Pju8
smkRhcXx/gEM4Tl5
','eJyNUttO4zAQ/RU0zy2yXQNJ39IkXQw0qVLzhHjoJmm3UuNGjrlW/Dtjpy4SApaJcrF9Zs6Zk9nD
5KGbdWsY7yFq28tK269p/9xsqv6thOqMEm6Vm3/u1K4gTrNIJFcRvGEMQO5+mbcQlxPMo4e8yeYV
NQhLDowwSkLGfGlCSUEYscEphwEgMqlXPbhdlt0pIQxvekoJuFKLx9JSpFLepLM0k3GeTUUxi6TI
M0TEuk7MgWhIwiFjkrAxD8ecA2pJduVDUyvTd2B2UzFvzMJ0Reu2/mhvklP8tWAXYRh4Ntl8z5fr
tdpiVaFWkaqQCMZ3+373M4N3zTMQfgaDIzRrHPhgSeAsIQG83eN/ef6ieqoqucPHdxS5p+Hcs2Cd
/+uRz44GoljGuLTWdTgIK8eMn9a7uTb6BTG3mIQzcH+sX9Qre578NToqS+MH5tP4XAQYHNzsxJVx
B3PzYl+ZtTrVTxt1gg3rGjzmh3KcXgQjdpjFRbttUFyyNEunOFWP29ZCPZHEFRCKrbnkazkf4njy
UWBtKrpKmb5/B5FPyikiI9vooG9sbX4QQ9mIn51/9PZbNLqIF8Y7ftIZjA==
','31215','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL);
INSERT INTO public.credit_transfer (id,settlconf_bizmsgid,amount,call_status,cihub_req_time,cihub_resp_time,crdttrn_req_bizmsgid,crdttrn_resp_bizmsgid,cre_dt,creditor_acct_no,creditor_acct_type,creditor_id,creditor_type,debtor_acct_no,debtor_acct_type,debtor_id,debtor_type,full_request_msg,full_response_msg,intr_ref_id,msg_type,orign_bank,recpt_bank,resp_status,reversal) VALUES
	 (3714,NULL,825200.00,'TIMEOUT-CIHUB','2021-09-22 09:53:36','2021-09-22 09:53:38','20210922SIHBIDJ1010O0100000047',NULL,'2021-09-22 09:53:36.712','8417832','SVGS','C4403','02','788884','CACC','01445','01','eJyVUl1P6kAQ/Stmn9FsS7kU3voBuEahsRvvg/GhtlDrbUuzHYxI+O93dtoSNBB1k213d77OOTM7
5m7quzpl4x1zquo6Ufo0bb5ZljT/UpQ1lIJuC3ghq76xUFy7wr8x2B5Xj8n1D+O8ydzBOKeNc7MP
xCB0cWZy0+Aj0+xSc4MvcNOyhqzH0NNfrhrnKorrK85t3MYVt9HqqaUPbZpLPro0TclH40F/3P/D
sJK/jjfFsoQGH6ynwquhUF4CUq3040x1IhCi04BaNId6sjhTscfmz4uVfK/RbuAtBMgLUVIlOt8B
yXF7P2NaiAaHfCeXxx0LCmjUm5SJXOPnHKLPEmGC75BjMUwuqxbMbZzrXqlCXwIFaovx3NBuHqTb
YKOqrxY0iRKU+4+IOAVp+hDlmyU62ObA5BzboiWKdYjw7ynbi0pd3Tp/4ko0+s9Aas+1hNOoTLKL
v9lrtI3Q1lAP1Bscj9BjO0PcsKwB2z/RBOk0ThxDN2Ff5m1o47IIs/xExHM8jx0SpPCLeadu/SDm
eNabmAPf4MIRB54LlZ6i6VkW73c0qeJ5mrZlDO2+eYJn+DAL6Tms8gKf/AgiqjEp33Ly7Bqh47rO
J8dPOi3C0ED2/wFFmzm1
',NULL,'32415','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL),
	 (3715,NULL,825200.00,'TIMEOUT-CIHUB','2021-09-22 09:53:36','2021-09-22 09:53:38','20210922SIHBIDJ1010O0100000047',NULL,'2021-09-22 09:53:36.712','8417832','SVGS','C4403','02','788884','CACC','01445','01','eJyVUl1P6kAQ/Stmn9FsS7kU3voBuEahsRvvg/GhtlDrbUuzHYxI+O93dtoSNBB1k213d77OOTM7
5m7quzpl4x1zquo6Ufo0bb5ZljT/UpQ1lIJuC3ghq76xUFy7wr8x2B5Xj8n1D+O8ydzBOKeNc7MP
xCB0cWZy0+Aj0+xSc4MvcNOyhqzH0NNfrhrnKorrK85t3MYVt9HqqaUPbZpLPro0TclH40F/3P/D
sJK/jjfFsoQGH6ynwquhUF4CUq3040x1IhCi04BaNId6sjhTscfmz4uVfK/RbuAtBMgLUVIlOt8B
yXF7P2NaiAaHfCeXxx0LCmjUm5SJXOPnHKLPEmGC75BjMUwuqxbMbZzrXqlCXwIFaovx3NBuHqTb
YKOqrxY0iRKU+4+IOAVp+hDlmyU62ObA5BzboiWKdYjw7ynbi0pd3Tp/4ko0+s9Aas+1hNOoTLKL
v9lrtI3Q1lAP1Bscj9BjO0PcsKwB2z/RBOk0ThxDN2Ff5m1o47IIs/xExHM8jx0SpPCLeadu/SDm
eNabmAPf4MIRB54LlZ6i6VkW73c0qeJ5mrZlDO2+eYJn+DAL6Tms8gKf/AgiqjEp33Ly7Bqh47rO
J8dPOi3C0ED2/wFFmzm1
',NULL,'32415','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL),
	 (3731,NULL,825200.00,'TIMEOUT-CIHUB','2021-09-22 10:16:30','2021-09-22 10:16:32','20210922SIHBIDJ1010O0100000055',NULL,'2021-09-22 10:16:30.385','8417832','SVGS','C4403','02','788884','CACC','01445','01','eJyVUttuozAQ/ZXKz2llE9LSvHFJUq/aBBWr+1DtA4WEsgsEmUnVbJR/73iAKK0SbdeSwfbczjkz
O+ZtmocmY+Mdc+v6LtXmNG2/eZ62/0pWDVSSbgt4Jau5sUjeeTL4Idge14Cp9Tfj/MncxTi3i/Py
v4hBmuLM4pbgt5bVp+aCL3DTGo3YgKFnsFy1znWcNFecO7jFFXfQ6utlAF2aS357aVlK8LG4Hg85
w0rBOtmUywpafLCeSr+BUvspKL0yjzPdi0CITgPq0Fz39VR5puKAzV8WK/XeoF3gLQIoSllRJTo/
AMlx/zhjRogWh3onl+cdC0to1ZtUqVrj5xyizxJhgn8hx2KYXNUdmPukML3SpbmEGvQW47kwbj5k
23Cj668WNMkKtPeHiLglafoUF5slOjjWyOIc22IkSkyIDB4p26vOPNO6YOIpNAYvQGrPjYTTuErz
i5/573gbo62lHuo3OB6h526GuLDtEdv/ogkyadwkgX7CvszbjYPLJszqExHf9X12SJDBf8w7desb
Mcez3sYc+IYXrjzwXOjsFE3ftvmwp0kVz9N0bHHjDK0TPKOnWUTPUV2U+BTEEFONSfVWkGffCBPX
dz49fjJpEYYBsv8A3cM5kw==
',NULL,'32115','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL),
	 (3740,NULL,825200.00,'TIMEOUT-CIHUB','2021-09-22 10:27:23','2021-09-22 10:27:25','20210922SIHBIDJ1010O0100000060',NULL,'2021-09-22 10:27:23.103','8417832','SVGS','C4403','02','788884','CACC','01445','01','eJyVUttuozAQ/ZXKz2llHLqheeOSpF61CSpW96HaBwoJpQsEmUnVbJR/3/EAUVol2taSwfbczjkz
O+ZtmvsmY+Mdc+v6NtXmNG2/eZ62/0pWDVSSbgt4Iau5sUjeejL4abE9rgFT6y/G+ZO5i3FuF+fl
fxGDNMWZ4MLiN0L0qbnFF7hp/eBswNAzWK5a5zpOmivOHdzWFXfQ6utlAF2aS35zKYSy+FiMxmLI
sFKwTjblsoIWH6yn0m+g1H4KSq/M40z3IhCi04A6NFZfT5VnKg7Y/HmxUu8N2o13BFCUsqJKdL4H
kuPuYcaMEC0O9U4uTzsWltCqN6lStcbPOUQfJcIE/0OOxTC5qjswd0lheqVLcwk16C3Gc3LzIduG
G11/tqBJVqC9P0TELUnTx7jYLNHBEdeCc2yLkSgxITJ4oGwvOvNM64KJp9AYPAOpPTcSTuMqzS9+
5a/xNkZbSz3Ub3A8Qk/dDHHLtq/Z/jdNkEnjJgn0E/Zp3kYOLpswqw9EfNf32SFBBt+Yd+rWF2KO
Z72NOfANL1x54LnQ2Smavm3zYU+TKp6n6djWyBmKEzyjx1lEz1FdlPgUxBBTjUn1VpBn3wgT13c+
PX4yaRGGAbL/B8nPOYs=
',NULL,'33305','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL),
	 (3765,NULL,825200.00,'TIMEOUT-CIHUB','2021-09-22 01:23:53','2021-09-22 01:23:55','20210922SIHBIDJ1010O0100000070',NULL,'2021-09-22 13:23:53.473','8417832','SVGS','C4403','02','788884','CACC','01445','01','eJyVUttu4jAQ/ZXKz7RynCBS3nIB6lULqLG6D9U+pAmk2U1C5AxVWcS/dzxJEK1A27XkxPbczjkz
e+Zvm4cmY+M98+r6LtXmNG2/eZ62/0pWDVSSbgt4Jau5sUje+TL8YbEDrgFTm2/GBZO5h3FeF+fn
fxGDNMWZ4MLit0L0qbnFF7hpjTgbMPQMV+vWuY6T5oZzF7d1w120BnoVQpfmmt9eC6Eseyzs8dBm
WCncJNtyVUGLDzZTGTRQ6iAFpdfmcaZ7EQjReUAdGquvp8oLFQds/rJYq/cG7cY7AihKWVElOj8A
yXH/OGNGiBaHeieX5z1bltCqN6lStcHPJUSfJcIE/0KOxTC5qjsw90lheqVLc1lq0DuM5+QWQLZb
bnX91YImWYH2/xARryRNn+Jiu0IHVwwF59gWI1FiQmT4SNledeab1oUTX6ExfAFSe24knMZVml/9
zH/HuxhtLfWlfoPTEXruZohbjjNkh180QSaNlyTQT9iXeRu5uBzCrD4RCbwgYMcEGfzHvFO3vhFz
OuttzJHv8sqTR54LnZ2jGTgOt3uaVPEyTdexRq4tzvCMnmYRPUd1UeJTGENMNSbVW0GefSNMXN/5
9PTJpEUYBsjhA+FYOZM=
',NULL,'33105','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL),
	 (3772,NULL,825200.00,'TIMEOUT-CIHUB','2021-09-22 01:27:03','2021-09-22 01:27:05','20210922SIHBIDJ1010O0100000075',NULL,'2021-09-22 13:27:03.386','8417832','SVGS','C4403','02','788884','CACC','01445','01','eJyVUltP6kAQ/itmn9FslyKVt14A90ShsRvPg/GhtlCr7dJsByOH8N+d3baIBnJ0k2137t98M1vi
revbOiOjLXGr6jpV+jVpvnmeNn/JZQ2SG2kOz8aqJRLxa48Hfyyyw9MjYvXDOH88czHObeO8/B9i
4Lo4YZRZ9IqxLjW16ByvOcMB6RH0DBbLxrmKk/qCUgevdUEdtPpqEUCb5pxenTMmrP6IDUe0T7BS
sErW5UJCgw9WE+7XUCo/BaGWWjlVHQkG0XFALZrLrp4oT1TskdnTfCnea7RbKEUARcmlqWTet2Do
uLmbEk1Eg0O8G5eHLQlLaNgby1Ss8HMK0VeKMMH/kGMxTC6qFsxNUuhZqVILoQK1wXhqaTcfsk24
VtV3C5q4BOW9mkbc0nB6HxfrBTo4bMAoxbFoihIdwoM7k+1ZZZ4eXTD2BBqDJzBszzSFk1im+dnf
/CXexGhrWg/VGxyu0EO7Q9Sy7QHZPZoN0mncJIFuw77t29DBYxvM4ksjvuv7ZJ8gg1/su5nWD2IO
d72J2fcbnrl83+dcZcfa9G1br+7jZ8XTbTq2NXT67Eif0f00MuqoKkpUBTHEpsZYvhXGsxuEjusm
nx6qdFqEoYHsPgATEjml
',NULL,'33105','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL),
	 (3780,NULL,825200.00,'TIMEOUT-CIHUB','2021-09-22 01:28:41','2021-09-22 01:28:43','20210922SIHBIDJ1010O0100000080',NULL,'2021-09-22 13:28:41.207','8417832','SVGS','C4403','02','788884','CACC','01445','01','eJyVUltPq0AQ/itmn6tZthixb1zaukZbIht9MD4gtMg5QMkyNdam/93ZAZpq2hzPJlx2rt/3zWyZ
t27um4yNtsyt65tUm79J+87ztP1WsmqgknSbwxt5zY1F8saTwa3FdngGTK1+meePZy7muV2el38i
BmmaM8GFxa+F6Etzi8/xoeNwNmAYGSyWbXAdJ80F2vGxLriDXl8vAujKnPPrcyGUNRwJZ2QjyAEL
Vsm6XFTQ4oPVRPoNlNpPQemlMU51LwIhOg6oQ2P1/VR5ouOAzV7nS/XRoN/cIoCilBV1ov97IDnu
HqbMCNHiUB8U8rxlYQmteuMqVSt8nUL0XSIs8C/k2AyLq7oDc5cUZla6NJdQg95gPqcwH7JNuNb1
Tw+6ZAXa+0tE3JI0fYyL9QIDHHEpOMexGIkSkyKDB6r2pjPPjC4YewqdwSuQ2jMj4SSu0vzsKf8T
b2L0tdRD/Q6HK/Tc7RC3bPuS7V5og0wZN0mg37Af+3bl4LEJs/pGxHd9n+0LZPAf+07T+kXO4a63
OXu+4Zkr9zznOjtG07dtPuxpUsfTNB3bunKG4gjP6HEakTmqixJNQQwx9RhX7wVF9oMwef3k00OT
KYswDJDdF/jrOZs=
',NULL,'33105','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL),
	 (3788,NULL,825200.00,'TIMEOUT-CIHUB','2021-09-22 01:30:14','2021-09-22 01:30:16','20210922SIHBIDJ1010O0100000085',NULL,'2021-09-22 13:30:14.205','8417832','SVGS','C4403','02','788884','CACC','01445','01','eJyVUttuozAQ/ZXKz2llE7KleeOSpF61CSpW96HaBwoJpQsEmUnVbJR/3/EAUVol2tYSF8/1nDOz
Y96muW8yNt4xt65vU23+pu07z9P2W8mqgUrSbQEv5DU3FslbTwY/BdvjGTC1/mKeP5m7mOd2eV7+
FzFI05xZ3BL8xrL60lzwBT50nBEbMIwMlqs2uI6T5grt+Igr7qDX18sAujKX/ObSspQYjod8LGyG
nYJ1simXFbT4YD2VfgOl9lNQemWMM92LQIhOA+rQ/Oj7qfJMxwGbPy9W6r1Bv8BbBFCUsqJO9H8P
JMfdw4wZIVoc6p1CnnYsLKFVb1Klao2vc4g+SoQF/occm2FxVXdg7pLCzEqX5hJq0FvM58KE+ZBt
w42uP3vQJSvQ3h8i4pak6WNcbJYY4Fgji3Mci5EoMSkyeKBqLzrzzOiCiafQGTwDqT03Ek7jKs0v
fuWv8TZGX0s91G9wvEJP3Q5xYdsjtv9NG2TKuEkC/YZ92rdrB49NmNUHIr7r++xQIINv7DtN6ws5
x7ve5hz4hheuPPBc6OwUTd+2+bCnSR3P03Rsce0MrRM8o8dZROaoLko0BTHE1GNSvRUU2Q/C5PWT
T49NpizCMED2/wAFhTmh
',NULL,'311135','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL),
	 (3796,NULL,825200.00,'TIMEOUT-CIHUB','2021-09-22 01:32:17','2021-09-22 01:32:19','20210922SIHBIDJ1010O0100000090',NULL,'2021-09-22 13:32:17.765','8417832','SVGS','C4403','02','788884','CACC','01445','01','eJyVUttu4jAQ/ZXKz7RyTCoCb7kA9aqFqLG6D9U+pAmk2U1C5AxVWcS/dzxJEK1A27XkxPbczjkz
e+Ztm4cmY5M9c+v6LtXmNGu/eZ62/0pWDVSSbkt4Jau5sUjeeTL4YbEDrgFTm2/G+dOFi3FuF+fl
fxGDNMWZ4MLiYyH61NziS9y0xpwNGHoGq3XrXMdJc8O5g9u64Q5afb0KoEtzzcfXQihrOBmKiTVi
WCnYJNtyVUGLDzYz6TdQaj8Fpdfmca57EQjReUAdGquvp8oLFQds8bJcq/cG7cY7AihKWVElOj8A
yXH/OGdGiBaHeieX5z0LS2jVm1ap2uDnEqLPEmGCfyHHYphc1R2Y+6QwvdKluYQa9A7jObn5kO3C
ra6/WtAkK9DeHyLilqTpU1xsV+jgiFvBObbFSJSYEBk8UrZXnXmmdcHUU2gMXoDUXhgJZ3GV5lc/
89/xLkZbSz3Ub3A6Qs/dDHHLtm/Z4RdNkEnjJgn0E/Zl3kYOLpswq09EfNf32TFBBv8x79Stb8Sc
znobc+QbXrnyyHOps3M0fdvmw54mVbxM07GtkTMUZ3hGT/OInqO6KPEpiCGmGtPqrSDPvhEmru98
evpk0iIMA+TwAfeoOZs=
',NULL,'311135','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL),
	 (3804,NULL,825200.00,'TIMEOUT-CIHUB','2021-09-22 01:34:17','2021-09-22 01:34:19','20210922SIHBIDJ1010O0100000095',NULL,'2021-09-22 13:34:17.69','8417832','SVGS','C4403','02','788884','CACC','01445','01','eJyVUttu4jAQ/ZXKz7SyQ1gCb7kA9aqFqLG6D9U+pAmk6ZIQOUNVFvHvO54kiFagbS05sT23c87M
nnnb+r7O2HjP3Kq6TbU5TZtvnqfNv5RlDaWk2wJeyGpuLJK3ngx+CnbA1WNq88U4fzJ3Mc5t47z8
L2KQpjizuCX4yLK61FzwBW5aowHrMfQMlqvGuYqT+oZzB7e44Q5afb0MoE1zzUfXlqVEf9y3x2LI
sFKwSbbFsoQGH2ym0q+h0H4KSq/M40x3IhCi84BaND+6eqq4ULHH5s+LlXqv0S7wFgGsC1lSJTrf
A8lx9zBjRogGh3onl6c9Cwto1JuUqdrg5xKijxJhgv8hx2KYXFUtmLtkbXqlC3MJNegdxnNh3HzI
duFWV58taJIlaO8PEXEL0vQxXm+X6OBYA4tzbIuRKDEhMnigbC8680zrgomn0Bg8A6k9NxJO4zLN
r37lr/EuRltDPdRvcDpCT+0McWHbA3b4TRNk0rhJAt2EfZq3oYPLJszqAxHf9X12TJDBN+aduvWF
mNNZb2KOfMMrVx55LnR2jqZv27zf0aSKl2k6thg6fesMz+hxFtFzVK0LfApiiKnGpHxbk2fXCBPX
dT49fTJpEYYBcvgHO9I5sw==
',NULL,'311135','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL);
INSERT INTO public.credit_transfer (id,settlconf_bizmsgid,amount,call_status,cihub_req_time,cihub_resp_time,crdttrn_req_bizmsgid,crdttrn_resp_bizmsgid,cre_dt,creditor_acct_no,creditor_acct_type,creditor_id,creditor_type,debtor_acct_no,debtor_acct_type,debtor_id,debtor_type,full_request_msg,full_response_msg,intr_ref_id,msg_type,orign_bank,recpt_bank,resp_status,reversal) VALUES
	 (3812,NULL,825200.00,'TIMEOUT-CIHUB','2021-09-22 01:41:16','2021-09-22 01:41:18','20210922SIHBIDJ1010O0100000100',NULL,'2021-09-22 13:41:16.766','84122222','SVGS','C4403','02','783784','CACC','01445','01','eJyVUsFuozAQ/ZXK5zSyCd3S3MAkqVdtgorVPVR7oJBQukCQcapmo/z7jscQpVWibS3ZYM/Mm/dm
ZkeCTXvf5mS8I37T3GbK/E3tWRSZ/daibnUt8LbQL2g1NxKL20CEPxnZwxoQuf5iHJ/MfYjzu7ig
+AschElOHOoweuM4PTRldAHbLDjJgIBnuFxZ5yZJ2yGlHmw2pB5YuVqGuoO5pDeXjiPZaOyyMftB
IFO4TjfVstaWn15PBW91pXimpVqZx5nqi4CMThPq2LA+n6zOZByQ+fNiJd9bsBvvWOuyEjVmwv97
jeW4e5gRUwjLQ76jy9OORJW21ZvUmVzDcY7RxxIBwP+YQzIAl01H5i4tTa9UZS6R0moL8daN63wb
bVTz2QImUWsV/EEhfoU1fUzKzRIcPOfKoXSIbHhqQkT4gGgvKg9M68JJIMEYPmus9tyUcJrUWXHx
q3hNtgnYrPRIvenjEXrqZogy170i+984QQbGT1PdT9inebv2Rteei5zlByHc55wcAHL9jXnHbn0h
5njWbcxBb3Thi4POhcpPyeSuS0e9TMx4XqbnMsesE0Ljx1mMz3FTVvAUJjrBJJP6rUTPvhMmrm99
dvxkYIGHYbL/B7kdOZk=
',NULL,'353865','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL),
	 (3820,NULL,825200.00,'TIMEOUT-CIHUB','2021-09-22 01:41:22','2021-09-22 01:41:24','20210922SIHBIDJ1010O0100000105',NULL,'2021-09-22 13:41:22.749','84122222','SVGS','C4403','02','783784','CACC','01445','01','eJyVUk1vozAQ/SuVz2llO2RLcwOTpF61CSpW91DtgUJC6QJBZlI1G+W/79hAlFaJtrVksD1f772Z
HfE3zX2TkfGOeHV9m2pzmrbfPE/bfyWrBippbwt4sVZzI5G89WXwk5E9rgFR6y/Gicncwzivi/Pz
v4hBmuKEU87oDed9asroArdZjI7IgKBnsFy1znWcNFeUurjZFXXRKvQygC7NJb255Fyx4dhhY84J
VgrWyaZcVtDig/VUigZKLVJQemUeZ7oXwSI6DahD86Ovp8ozFQdk/rxYqfcG7QxvEUBRyspWsud7
sHLcPcyIEaLFod6ty9OOhCW06k2qVK3xcw7RR4kwwf+QYzFMruoOzF1SmF7p0lxCDXqL8ZQZNwHZ
Ntzo+rMFTbIC7f+xRLzSavoYF5slOrh8xCnFthiJEhMigweb7UVnvmldMPEVGoNnsGrPjYTTuErz
i1/5a7yN0dZSD/UbHI/QUzdDlDnOiOx/2wkyabwkgX7CPs3btTu8dh2LWX0gIjwhyCFBBt+Yd9ut
L8Qcz3obc+AbXnjywHOhs1M0hePQYU/TVjxP03UYN+sE0ehxFtnnqC5KfApiiG2RSfVWWM++Eyau
b316/GTSIg6DZP8P3n45pw==
',NULL,'353865','Credit Transfer','SIHBIDJ1','CENAIDJA',NULL,NULL),
	 (3828,NULL,825200.00,'SUCCESS','2021-09-22 01:47:00','2021-09-22 01:47:01','20210922SIHBIDJ1010O0100000110','20210922CENAIDJA010R0200000988','2021-09-22 13:47:00.782','262222','SVGS','C4403','02','783784','CACC','01445','01','eJyVUl1vmzAU/SuVn9PIduhC8wYmST21CSpW91DtgUJC2YAg41TNovz3XV8gSqtE6ywZbN+vc869
e+Jvm4cmI5M98er6LtX2NGu/eZ62/0pWjakk3pbmFa32RiJ558vgOyMHWAOiNl+ME9OFB3FeF+fn
fwCDtMUJp5zRW8771JTRJWy7GKNkQMAzWK1b5zpOmiGlLmw2pC5YhV4FpktzTW+vOVdsNHHGE0oJ
VAo2ybZcVabFZzYzKRpTapEapdf2ca57ERDReUAdGtbXU+WFigOyeFmu1XsDdusdGVOUssJKeH4w
KMf945xYIVoc6h1dnvckLE2r3rRK1QY+lxB9lAgS/As5FIPkqu7A3CeF7ZUu7SXURu8gnqKbMNku
3Or6swVMsjLa/41EvBI1fYqL7QocXH7DKR2iBCKxITJ4xGyvOvNt64Kpr8AYvBhUe2ElnMVVml/9
yH/FuxhsLfVQv5nTEXruZogyx7khh584QTaNlySmn7BP8zZ2R2PXQczqAxHhCUGOCTLzH/OO3fpC
zOmstzFHvuGVJ488lzo7R1M4Dh31NLHiZZr8G4d1hmb0NI/wOaqLEp6C2MRYYlq9FejZ98HG9Y1P
T59sWkBhcRz+AmI4OTQ=
','eJyNUlFvmzAQ/iuTn5Pq7KQJyRsB0rpdIEo89WHaQ4aTDCkYBG7XLup/753Bm1S1XQ9hY/vzfd99
3Jkt7ttVe2TzMwvr+lo39LXsxqLQ3Wykaa2RbpXZX+6UVixK0lDGNyF7xhgwVX3y3lZeL/Ae7+8t
ij+oQRI5EyA4zITwqYHDBgRQzIKADRgi4/2hA9e7vL0AEPjyCw7Mpdo+5ESRKPU1WSWpirJ0KTer
UMksRUTU7GPbEw1hNhRCwWQ+ns4BGGqJq/y+3BvbVWCrpVyXdmvbTe22rhpvklP8tmAKzgPu2VT5
Pl/WHM0Js0pzCI1GIjb/fu52XzN413oGpCAGD01LB+4tCZwlELDnH/hfHt/InhitKhzeo8j+FgKe
BfP8X496dDQsjFSES7KuxUY4OGb8JO/WjW2eEPMNyASS2Off7A90Hv+0TZjn1jfMq/aZBqNpMGau
dyJt3cHaPtGUktV3hdHFl7tCV7piHvRBPjERGH0zbutTierind05yYl5ONUE9UwKVwyoVHf5Vq2H
2J/jESoaYIHa2M4AB1G/jZMEo769qLKj/UAMF6Px5eRfcZ9Fo434YLwAfj8Z0g==
','32221','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL),
	 (3837,NULL,825200.00,'SUCCESS','2021-09-22 05:15:59','2021-09-22 05:15:59','20210922SIHBIDJ1010O0100000114','20210922CENAIDJA010R0200001197','2021-09-22 17:15:59.191','262222','SVGS','C4403','02','783784','CACC','01445','01','eJyVUttO4zAQ/RXk54Jsk27TvuXSFq+gjYjFPiAeQtKG7CZu5EwR3Yp/37GTVAW1grXkxPbczjkz
e+Jvm7smJ5M98er6JtPmNGu/RZG1fyVUA0rY2xJerNXcSCxufBH+ZOQd14DIzTfjgunCwzivi/OL
v4hBmOKEU87omPM+NWV0idssxhwyIOgZrtatc52kzRWlLm52RV20BnoVQpfmko4vOZdsNGHDyXBM
sFK4SbfVSkGLDzYzETRQ6SADqdfmca57ESyi04A6NMO+nqzOVByQxfNyLd8atDO8xQBlJZStZM93
YOW4vZ8TI0SLQ75Zl8c9iSpo1ZuqTG7wcw7RR4kwwVfIsRgml3UH5jYtTa90ZS6RBr3DeMqMWwD5
Ltrq+rMFTUKB9v9YIl5lNX1Iyu0KHVw+5JRiW4xEqQkR4b3N9qJz37QunPoSjeEzWLUXRsJZorLi
4lfxO9klaGupR/oVjkfosZshyhwHeTzZCTJpvDSFfsI+zdvIvR65jsUsPxAJvCAghwQ5/Me82259
I+Z41tuYA9/owhMHnkudn6IZOA697mnaiudp8h8c1wma8cM8ts9xXVb4FCaQ2BJT9Vpaz74PJq5v
fHb8ZNIiCoPj/R/erjle
','eJyNUl1PgzAU/SvmPm9L2zHH9saAadXBwuqT8WHCNpeMQqD4tfjfvS1UE6PTElrannvPuYd7hFlT
L+odTI/gleVlVumveTvv91m7Si5rJbnZxerR3Ood+GHk8eDKg3ccPRDFP+NW/HKGcbSLm+3fUAPX
5MAIo2TCmE1NKEkIIzgonYyhB4gMNtsWXK7TekAIw5cOKAGTavWUaopQiJtwEUbCj6M5Txae4HGE
CL/aBKoj6pNJnzFByZSOpqMJoJagSJt8I1VbgSrmfJmrlaqT0hxdVNYko/hnwUatQ8eWTeS/88XV
Th4wK5dbT2ZIBNO7Y3v6ncG61jGgISPofUKj3IA7S1xjCXHh/R7/y8sP2UOZiQKn3yhiWwh1LAvm
+VuPeDE04PnCx622rsZG2Bpm/NTeLStVvSLmFqOwB+4/8yebrb4PHlTlpamyDfOtfcbucOw6YHrH
z5S5WKpXvUTa6uumbvL1mZfty0ZVBVjciZTsnOHo+nFVHnIUGKzV2qgO5dOh1FBLJnAHhGJ5Jvha
LPvYos7Q1VYldSZV64GBiGdpVJGhLrbXFrdTJ8RQNnRG51/1/ReNTuKD4wNaXhs+
','32221','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL),
	 (3929,NULL,825200.00,'SUCCESS','2021-09-22 11:05:53','2021-09-22 11:05:54','20210922SIHBIDJ1010O0100000203','20210922CENAIDJA010R0200001526','2021-09-22 23:05:53.514','262222','SVGS','C4403','02','783784','CACC','01445','01','eJyVUk1vozAQ/SuVz2llHLKluYFJUq/aBBWre6j2QCGhdMFBZlI1G+W/79hAlFaJtrVksD1f772Z
HQk2zX2Tk/GO+HV9m2lzmrbfosjavxKqASXsbQEv1mpuJBa3gQh/OmSPa0Dk+otxfDL3Mc7v4oLi
L2IQpjhhlDn0hrE+NXXoArdZjA7JgKBnuFy1znWSNleUeridK+qhletlCF2aS3pzyZhkwzEdjUdD
gpXCdbqplgpafLCeCt5ApXkGUq/M40z3IlhEpwF1aNy+nqzOVByQ+fNiJd8btDt4iwHKSihbyZ7v
wcpx9zAjRogWh3y3Lk87ElXQqjdRmVzj5xyijxJhgv8hx2KYXNYdmLu0NL3SlblEGvQW46lj3Djk
22ij688WNAkFOvhjifiV1fQxKTdLdPDYiFGKbTESpSZEhA8224vOA9O6cBJINIbPYNWeGwmnicqK
i1/Fa7JN0NZSj/QbHI/QUzdD1HHdEdn/thNk0vhpCv2EfZq3a2947bkWs/xAhPuck0OCHL4x77Zb
X4g5nvU25sA3uvDFgedC56doctfFnnY0bcXzNNkPhusEzfhxFtvnuC4rfAoTSGyJiXorrWffBxPX
Nz47fjJpEYXBsf8Hlf05Rg==
','eJyNUttuozAQ/ZXVPCeVbXKheSNAtm43EBH3qeoDC0k2UjAIpt12o/77jg3uSlXb7SAuts/MOXOY
MywfunV3gMUZgqa5Klvzteqfx2PZv7XUHWppVyn+sqdmBWGcBDK6DuCFYgSq/mLeVl4tKY8Pecvj
H9IgDTkIJji7FMKVZpxlTDAKPhUzGAEho92+Bzd50V0wJujmF5yBLbV9LAxFrNSPeB0nKkyTlczW
gZJpQoiw3UU4EI3Z5VgIxWcLNl1MPSAtUV08VDuNfQdYr+Smwi12WWO3vrfOJKv4fcE2xHzi2FT1
MV/aHvSJqkq9D3RJRLC4O/e7bxmca68MzDA4aFJZ8GCJby1hPrzc0395eqd6rEtV0+MjinSgEcxz
LFTn/3rUk6WBIFQhLY11HQ3C3jLTp/Fu02L7TJhbyqIZuH+tn+325jz6iW1QFOgG5s34zH1v7k/A
zk5Yoj3Y4LN5JcZqmmnMq+M3qcu8xRoc7pOSYiYohnncNqeKBEY55lZ1rB9PjYE6MkUrYJzas8k3
ajOmEZ14vjEg60qNvQcWon5rq4p5ptlR39wBPxHDhTeZzv7191U0OUkXxV8smBsd
','31121','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL),
	 (3940,NULL,825200.00,'SUCCESS','2021-09-22 11:15:24','2021-09-22 11:15:25','20210922SIHBIDJ1010O0100000210','20210922CENAIDJA010R0200001495','2021-09-22 23:15:24.953','262222','SVGS','C4403','02','783784','CACC','01445','01','eJyVUttuozAQ/ZXKz2llHLKleeOSpF61CSpW96HaBwoJpQsEmUnVbJR/3/EAUVol2taSwfbczjkz
O+ZtmvsmY+Mdc+v6NtXmNG2/eZ62/0pWDVSSbgt4Iau5sUjeejL4abE9rgFT6y/G+ZO5i3FuF+fl
fxGDNMWZ4MLiN0L0qbnFF7jNQgMbMPQMlqvWuY6T5opzB7d1xR20+noZQJfmkt9cCqHEcGyNxsJm
WClYJ5tyWUGLD9ZT6TdQaj8FpVfmcaZ7EQjRaUAdGquvp8ozFQds/rxYqfcG7cY7AihKWVElOt8D
yXH3MGNGiBaHeieXpx0LS2jVm1SpWuPnHKKPEmGC/yHHYphc1R2Yu6QwvdKluYQa9BbjObn5kG3D
ja4/W9AkK9DeHyLilqTpY1xslujgiJHgHNtiJEpMiAweKNuLzjzTumDiKTQGz0Bqz42E07hK84tf
+Wu8jdHWUg/1GxyP0FM3Q9yy7RHb/6YJMmncJIF+wj7N27UzvHZswqw+EPFd32eHBBl8Y96pW1+I
OZ71NubAN7xw5YHnQmenaPq2zYc9Tap4nqb4IXCdoBk9ziJ6juqixKcghphKTKq3gjz7Ppi4vvHp
8ZNJiygMjv0/eZk5PA==
','eJyNUstuqzAQ/ZWrWZPKdiCvHQHSum0gStxFVXWRQpJGCgaB+7pR/73jAVqp6ssIG9tn5pw5zBGm
D/W83sHkCH5ZnmWV/Zo1836fNauWujZa0i4x93RrdxBEsS/Dcx9ecTigij/GreTZFON4Gzfd/0cN
0pKDYIKzsRBdasbZkgmGg7tjDxxAZLjZNuByndYnjAl8+QlnQKlWj6mliJS6jOZRrIIknsnl3Fcy
iRERVJvQtEQ9Nu4Jofhgwr2JcAG1hEX6kG+0aSowxUwucrMy9bKko9OqM4kUfy2Y1HresGNT+fd8
SbXTB8wq9dbXGRLB5ObYnH5m6FxrGfCUg/MOjXMCt5aMyBI2gtdb/C/PX2SPdKYKnL6jSFoaQb5S
COb5XY96JhrwAxXg1lpXYyNsiRk/rXeLylQviLnCKOyB2/f8y83W3od3pvLT1HQN86l9hqP+cOQC
9U6QGbpYmBe7xNZqqbN1ZYp/18VuDx3oh3xiIHC0zbgqDzmqC9dmTZIj/XgoLbRjUrgDZkul4Au1
6GF/un1U5GCBmTaNAQRRT5oksb6t1Gkq25kfxHDRd73BR3F/RaON+OB4A5RFGeI=
','31121','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL),
	 (3947,NULL,825200.00,'SUCCESS','2021-09-23 05:39:04','2021-09-23 05:39:05','20210923SIHBIDJ1010O0100000002','20210922CENAIDJA010R0200001189','2021-09-23 05:39:04.617','262222','SVGS','C4403','02','783784','CACC','01445','01','eJyVUttuozAQ/ZXKz2llDNmSvHFJUq/aBBWr+1D1gUJC6QJBZlI1G+Xfd2xDNq0SbTuSweO5nTkz
O+Jv2rs2J+Md8ZrmJpPqNjXfosjMv+Z1CzXX2gJetFVpJOY3Pg9/WmSPMiBi/cW4YDL3MM7r4vzi
D2LgqjhhlFl0xOw+NbXoAo8RRgYEPcPlyjg3SdpeUerisa6oi9ZALkPo0lzS0SWzBR2O7dGYOgQr
het0Uy1rMPhgPeVBC5UMMhBypR5nsidBIzoNqBO7ryeqMxUHZP68WIn3Fu0WajFAWfFaV9L3O9B0
3N7PiCLC4BDv2uVxR6IKDHuTOhNr/JxD9JEiTPA/5FgMk4umA3OblmpWslJKJEFuMZ5ayi2AfBtt
ZPPZgiZeg/R/60a8SnP6kJSbJTq4bMgoxbEoilIVwsN7ne1F5r4aXTjxBRrDZ9BszxWF06TOiotf
xWuyTdBmWo/kGxyv0GO3Q9RynCHZP+kNUmm8NIV+wz7t27VrX7uOxiw+NBJ4QUAOCXL4xr7raX0h
5njXTcyh3+jC44c+FzI/1WbgOGpcT/8qnm+T/WAoJ9qMH2axfo6bssKnMIFEl5jUb6X27Oeg4vrB
Z8dPKi2iUDj2fwGYvTlG
','eJyNUl1PgzAU/SvmPm+mLTjZ3hgwV3WwjPpkfJiwTZJRCHR+Lf53b8uqidnUS/hoe+495x7uHsa7
dtZuYLQHv66neaO/Jt2zKPLuLblsleRmlagnc6pXEESxz8NrHz4weiCqf+alfDrGPHrIGxfvqIFr
cmCEUTJkzJYmlCwIIxiUekPoASLD1boD18usPSeE4U3PKQFTKn3ONEUkxG00i2IRJPGEL2a+4EmM
iKBZhepA1CfDPmOCsZEzHBEXUEtYZbtyJVXXgaomfF6qVLWL2mxdNdYko/i4YBOu61g2UZ7mS5qN
3GJVLte+zJEIRvf7bvcHg2NdswyEaAYLjUsDPljiGUuIBx8P+F9ej1SPZC4qfJyiSL5omGXBOn/r
Ea+GBvxABLjU1rU4CGvDjJ/au3mjmjfE3GEOzsDDV/3Faq3Pw0fV+Fmm7MD8GJ9Lz7n0XDCzE+TK
HMzVm37F2uqoeSnkWbpri20FFvRLPTZgGIdhTOttierCpVoayZF83tYaapkEroBQ7M0k34h5H+fT
dVBRDxvMpeoMMBDxIo0k4uhOe11nG/WLGMoc92Lw3dx/0WgjXhifoccZ+A==
','31120','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL),
	 (3954,NULL,825200.00,'SUCCESS','2021-09-23 05:51:21','2021-09-23 05:51:22','20210923SIHBIDJ1010O0100000005','20210922CENAIDJA010R0200001334','2021-09-23 05:51:21.84','262222','SVGS','C4403','02','783784','CACC','01445','01','eJyVUl1Pq0AQ/Stmn6tZKCj2jY+2rtGWyMb7YO4DQosoULJMjbXpf3d2gKaaNte7ycLuztc5Z2bL
vHVz32RstGVuXd+kSp8m7TfP0/ZfiaqBStBtDi9k1TcWiRtPBLcG2+EaMLn6ZZw/nrkY53ZxXv6J
GIQuzkxuGvzaHPapucHnuNtlswFDz2CxbJ3rOGkuOHdwGxfcQauvFgF0ac759bk5lNwe2cbIRJAD
FqySdbmooMUHq4nwGyiVn4JUS/04Vb0IhOg4oG5d9vVkeaLigM2e50v50aBd3yKAohQVVaLzPZAc
dw9TpoVoccgPcnnasrCEVr1xlcoVfk4h+i4RJvgXciyGyWXdgblLCt0rVepLqEBtMJ6TZD5km3Ct
6p8WNIkKlPdGRNySNH2Mi/UCHRzTNjnHtmiJEh0iggfK9qIyT7cuGHsSjcEzkNozLeEkrtL87E/+
Gm9itLXUQ/UOhyP01M0QNyzLZru/NEE6jZsk0E/Yj3m7coZXjkWY5Tcivuv7bJ8gg/+Yd+rWL2IO
Z72N2fMNz1yx5zlX2TGavmXxYU+TKp6maV6auI7QjB6nET1HdVHiUxBDTCXG1XtBnn0fdFzf+PTw
SadFFBrH7guPDjlE
','eJyNUm1PgzAQ/ivmPm9LW9jEfWPAtOpgYTV+MH5A2ObiKATq6+J/91pWTYwvO0LLtc/d89xxO5g8
trN2DeMd+HV9VjT6a9qtm03R7ZLLVkluvETdm1vtQRDFPg/PfXhH64GoDoxb8LMJxtF93GTzhhq4
JgdGGCUnjNnUhJKUMIJGHceFHiAyXK46cJ3l7YAQhi8dUAIm1eIp1xSREJfRLIpFkMRTns58wZMY
EUGzDNWeqE9O+owJxsZDOmYopwdhlT+WS6m6ClQ15fNSLVSb1ubotLFNMop/FmyMuSPLJsrf+ZJm
LbeYlcuVLwskgvHNrjv9xuDYrlkGQjSDhcalAe9b4pmWEA/eb/G/vPyQPZKFqHD5jSL5pBlaFszz
vx7xYmjAD0SArm5di4OwMsz4qXs3b1TzipgrjMEZuP3Mny5X+j68U42f58oOzLfxOfacY88FMztB
oczFXL3qLdatjh6qo+tNkz1nEizmj3RsxND2s7iotyWKCzOVGcWRfNrWGmqJBHpAKJZmgi/EvI/j
6TqeHs60LaTq6jcQ8SyNIuLoQntdYWv1hxjKHHc4+qrtUDR2ER+0Dx7wGWA=
','30920','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL),
	 (3961,NULL,825200.00,'SUCCESS','2021-09-23 07:52:51','2021-09-23 07:52:52','20210923SIHBIDJ1010O0100000008','20210923CENAIDJA010R0200001434','2021-09-23 07:52:51.923','262222','SVGS','C4403','02','783784','CACC','01445','01','eJyVUttu4jAQ/ZXKz7RyQlgub7kA9aqFqLG6D9U+pAmk6SYhcoaqLOLfdzxOEK1A21rKxXM958zs
mbdt7puMTfbMrevbVOm/mXnneWq+lagaqATdlvBCXn1jkbj1RPDTYgc8PSY3X8zzpwsX89w2z8v/
IgahmzOb2xYf2/2uNLf4Eh9zRqzHMDJYrU1wHSfNDZrxsW7I66tVAG2Zaz6+tvuSDycDezJAkD0W
bJJtuarA4IPNTPgNlMpPQaq1Ns5VJwIhOg+oPeOunywvdOyxxfNyLd8b9OtbBFCUoqJO9H8PJMfd
w5xpIQwO+U4hT3sWlmDUm1ap3ODrEqKPEmGB/yHHZlhc1i2Yu6TQs1KlvoQK1A7zOUnmQ7YLt6r+
7EGXqEB5f4iIW5Kmj3GxXWHAyB7YnONYtESJThHBA1V7UZmnRxdMPYnO4BlI7YWWcBZXaX71K3+N
dzH6DPVQvcHpCj21O8Qtxxmww2/aIF3GTRLoNuzTvg1H/eHIIczyAxHf9X12LJDBN/adpvWFnNNd
NzlHvuGVK448lyo7R9N3HN7vaFLHyzTtHzaeMzSjx3lE5qguSjQFMcTUYlq9FRTZzUHndYNPT026
LKLQOA7/ANVzOVw=
','eJyNUl1vgjAU/SvLfcalFHToGwLObhMMdk/LHhygM5FKSvdp9t93W8AlRrddwkfbc+8593D3MH6p
Z/UaRnvwq2qaS/01aZ6bTd68BRO1EsysEvVsTvUKgij2WXjjwxeGBXz3z7wFm44xz27zxptP1MA0
OVBCbTKkTlea2CQllGDYruOCBYgMi1UDrpZZfUkIxdu+tAmYUovXTFNEnN9FsyjmQRJPWDrzOUti
RASyCFVL1CPDHnU4IaM+HfVRjgXhLnspC6GaDtRuwualWqg6rczWtexMMopPCzZqh67dsfHyPF8i
12KLVZlY+SJHIhg97JvdY4bOtZYBYwjWARqXBtxa4hlLiAdfj/hf3k9Uj0TOd/g4R5EcaLyOBev8
rYe/GxrwAx7gUltX4yCsDDN+au/mUskPxNxjDs7A46F+Wqz0efikpJ9lqhuYo/G58pwrzwUzO0Gu
zMFcfehXrK3GVmWxvIjk20ZAB/qlHh1QjHYYF9W2RHXhUi2N5Ei8bisN7Zg4roDon2uSb/m8h/Pp
Op6ezrTOhWoMMBD+Jowk4uhOraaztfpFjE0dtz/4ae6/aLQRL4xvac4ZyQ==
','30920','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL),
	 (3986,NULL,825200.00,'SUCCESS','2021-09-23 10:15:08','2021-09-23 10:15:09','20210923SIHBIDJ1010O0100000022','20210923CENAIDJA010R0200000495','2021-09-23 10:15:08.609','262222','SVGS','C4403','02','783784','CACC','01445','01','eJyVUttu4jAQ/ZXKz7RynLBNecsFqFelRMTqPlR9SBNIs5uYyBmqsoh/79hJEKxA7VpyYnsu55yZ
2RF/08yanIx2xKvr+0zp06T9FkXW/iWXDUhubnN4M1Z9IzG/93n40yJ7XAMi1t+M82cLjnFeF+cX
f5ED1+CEUWbRO2b3qalF57jNYowMCHqGy1XrXCdpc0Opi9u6oS5aA7UMoUtzTe+umS0sOrKGI7Qi
UrhON9VSQssP1hMeNFCpIAOhVvpxqvoiGEbnCXVs7B5PVBcQB+Txdb4SHw3aLbzFAGXFpUEy5xlo
iOBhMSW6EC0P8WFcnnckqqCt3lhmYo2fS4xOS4QJvmKOYJhc1B2Zh7TUvVKVvkQK1BbjqaXdAsi3
0UbVJxZm6HIJyv9jhHiVqelTUm6W6OCyIaMU26JLlOoQHi5MtjeV+7p14dgXaAxfwVT7UZdwksis
uPpV/E62Cdpa6ZF6h+MReu5miFqOMyT7FzNBOo2XptBP2D/zduvat65jOIsTIYEXBOSQIIf/mHfT
rW/EHM96G3PQG115/KBzrvJzMgPHoXYv0yBelsl+MFxnZMZP09g8x3VZ4VOYQGIgxvK9NJ59H3Rc
3/js+EmnRRaax/4T4mE5aQ==
','eJyNUttygjAQ/ZVOntVJAtbLGwLWtBUcTZ86fbCA1hkJDGyvTv+9m0DaGce2LsMlydk9Zw97IJPn
el5vyfhAvLKcpZX+mjbP3S5t3kqoGpQwqxiezKleET+MPBFce+QTo0NkcWbeSswmmMfavMnuAzUI
TU445YyOuGNLU0aXlFMd7qhPOgSRQbZpwOU6qXuUcrxZj1FiSq1eEk0RSnkbzsNI+nE0Fcu5J0Uc
IcKvsgBaoi4ddbkjqTNm/TEdEtQSFMlzniloOoBiKhY5rKBelmbrqrImGcWnBetgA+pYNpn/zhdX
W7XHqkJtPJUiERnfH5rdYwbrWsuAfWsGC41yA24tGRpLNMUD/pe3E9VDlcoCH79RxJaGc8uCdf7X
I98MDfF86eNSW1fjIGwMM35q7xYVVO+IucMknIGH7/rLbKPPg0eovCQBOzBH4zMYOoOhS8zs+CmY
gwW861ekrfYUFOpCqHRdQUEs6o+C/JJjtNO4Kvc5ygvWsDaaQ/WyLzXUUklcEcqwOZN8IxddHFDX
QUkd7DBV0DhgIPJVGU3U0a12mta28IcYxh23f/nT3blo9BEvjC+/JBpI
','30920','Credit Transfer','SIHBIDJ1','BMRIIDJA','ACTC',NULL);
INSERT INTO public.credit_transfer (id,settlconf_bizmsgid,amount,call_status,cihub_req_time,cihub_resp_time,crdttrn_req_bizmsgid,crdttrn_resp_bizmsgid,cre_dt,creditor_acct_no,creditor_acct_type,creditor_id,creditor_type,debtor_acct_no,debtor_acct_type,debtor_id,debtor_type,full_request_msg,full_response_msg,intr_ref_id,msg_type,orign_bank,recpt_bank,resp_status,reversal) VALUES
	 (4002,NULL,825200.00,'SUCCESS','2021-09-23 10:48:34','2021-09-23 10:48:35','20210923SIHBIDJ1010O0100000029','20210923CENAIDJA010R0200001963','2021-09-23 10:48:34.614','262222','SVGS','C4403','02','783784','CACC','01445','01','eJyVUttu4jAQ/ZXKz7RyLt0G3nIB6lULUWN1H6p9SBNI001C5AxVWcS/73hMEK1A21pyYntu55yZ
LQvW3X1XsNGW+W17myt9mphvWebm34img0bQbQ4vZNU3lojbQEQ/LbbDNWBy9cW4cDzzMc7fxwXl
X8QgdHFmc9viQ9vpU3OLz3HTsodswNAzWiyNc5tm3RXnHm7rintoDdUign2aSz68tB1p8ZHrjRyX
YaVola3rRQMGH6wmIuygVmEOUi3141T1IhCi04DMcnhfT9ZnKg7Y7Hm+lO8d2i28JQBVLRqqROd7
IDnuHqZMC2FwyHdyedqyuAaj3rjJ5Qo/5xB9lAgT/A85FsPkst2Ducsq3StV60usQG0wnlvaLYRi
E69V+9mCJtGACv4QEb8mTR/Tar1AB8++tjnHtmiJMh0iogfK9qKKQLcuGgcSjdEzkNozLeEkbfLy
4lf5mm5StBnqsXqD4xF62s8Qt1z3mu1+0wTpNH6WQT9hn+btxnNuPJcwyw9EQj8M2SFBAd+Yd+rW
F2KOZ93EHPjGF7448Jyr4hTN0HW509Okiudp2j9sXCdoJo/ThJ6TtqrxKUohpRLj5q0iz74POq5v
fH78pNMiCo1j9w/HwDlW
','eJyNUttuozAQ/ZVqnpPK2DQleSNAWnc3ECXep6oPWUiykYJBxr1G/fcdD/GuVPU2iIvtM3POHOYI
0/tu3u1gcoS4ba8r475m/XO/r/q3lrqzWtKqsH/o1K0gyfJYpjcxvGIMQDXfzFvJ6ynmBae86f4F
NUhHDpzxgI258KVZwJaMM4xgPBIwAESmm20Pbtdld84Yxzs4DxhQqdVD6SgypX5m8yxXSZHP5HIe
K1nkiEjMJrUnoiEbD7lQTEzCaCJCQC1pU97XG237Dmwzk4varmy3bGnryniTSPH7gikueOTZVP0x
X2F2+oBVpd7GukIimNwe+923DN41z8CE69hD85rAJ0sisoRF8HqH/+XpneqZrlSDj48oCk/Dx54F
63ytRz0RDcSJSnDprOtwELbEjJ/Ou4Wx5hkxvzAJZ+DuX/3lZuvO09/WxGVp/cC8GZ/LSFxGIdDs
JJWlg4V9dq/cWS11tTa2OcvM416DR31SkI84xmkaV+2hRnnp2q5Jc6YfDq2DeiqFK2ABNkfJP9Ri
iAMaCpQ0wA4rbXsHCKIeNWlCcxjNl2ttZz8RE3ARXoz+d/ddNPqIF8Zf8BoaXw==
','30920','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL),
	 (4009,NULL,825200.00,'SUCCESS','2021-09-23 10:52:15','2021-09-23 10:52:16','20210923SIHBIDJ1010O0100000032','20210923CENAIDJA010R0200001645','2021-09-23 10:52:16.386','262222','SVGS','C4403','02','783784','CACC','01445','01','eJyVUttuozAQ/ZXKz2llDGlp3rgkqVdtEhWr+1DtA4WEsgsEmUnVbJR/73iAKK0SbdeSwfbczjkz
O+ZvmocmY6Md8+r6LtXmNGm/eZ62/0pWDVSSbnN4Jau5sUje+TL8YbE9rgFT62/GBeOZh3FeF+fn
fxGDNMWZ4MLit8LuU3OLz3HTsgUbMPQMl6vWuY6T5opzF7d1xV20BnoZQpfmkt9eCltZfDQUI+ua
YaVwnWzKZQUtPlhPZNBAqYMUlF6Zx6nuRSBEpwF1aOy+nirPVByw2ct8pd4btFt4iwCKUlZUic4P
QHLcP06ZEaLFod7J5XnHFiW06o2rVK3xcw7RZ4kwwb+QYzFMruoOzH1SmF7p0lwWGvQW47ll3ALI
touNrr9a0CQr0P4fIuKVpOlTXGyW6OCKoeAc22IkSkyIDB8p26vOfNO6cOwrNIYvQGrPjISTuErz
i5/573gbo62lvtBvcDxCz90Mcctxhmz/iybIpPGSBPoJ+zJvN6594zqEWX0iEnhBwA4JMviPeadu
fSPmeNbbmAPfxYUnDzznOjtFM3Acbvc0qeJ5muJa4DpBM3qaRvQc1UWJT2EMMZUYV28FefZ9MHF9
49PjJ5MWURgc+w+VFDlG
','eJyNUttuozAQ/ZXVPJNqbOfWvBEgW283ECWu+lD1IQtJNlJwEDjddqP++44N7kpVb0bY2D4z58xh
zjA9NfNmB5MzhFV1VdT2a9bO+33Rrlrqxmjpdpn57W7tDqIkDWX8I4RnGgGo4xfjVvJqSnGsi5vu
/5IGacmBI2d4yYVPjQyXyJEGG/YHEAAh4822BVfrvLlA5PSyC4bgUq0eckuRKPUzmSepirJ0Jpfz
UMksJURUb2LTEfXwsseFQjEZ8AkbAmmJj/mp3GjTVmCOM7kozco0y8odfa+9SU7x24KdWhyhZ1Pl
+3xZvdMHyir1NtQFEcHk7tyevmbwrnUMiEJA8AJNSwfuLBk7S3AMz/f0Xx7fyJ7oQh1peo8i8zSC
exbK87ke9ehoIIxURFtrXUONsHXM9Gm9W9SmfiLMDQVRD9y/5F9utvY+/mXqMM+Nb5hX7TMai9G4
D653osK4i4V5sktqrb4+Nady/e12r9dmDR71QUI+5DS6blxVh5LkxTbWak70w6GyUE+laAfIqDgX
fK0WPWrQviBJAVVYaNM64CDqj3aaUNhSg7a0nflADOOiPxj+r+6raPKRHhr/AMC1Gk8=
','30920','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL),
	 (4014,NULL,825200.00,'SUCCESS','2021-09-23 10:52:21','2021-09-23 10:52:22','20210923SIHBIDJ1010O0100000034','20210923CENAIDJA010R0200000018','2021-09-23 10:52:21.713','262222','SVGS','C4403','02','783784','CACC','01445','01',NULL,NULL,'321','Credit Transfer','SIHBIDJ1','BMRIIDJA','ACTC',NULL),
	 (4021,NULL,825200.00,'SUCCESS','2021-09-23 10:52:33','2021-09-23 10:52:33','20210923SIHBIDJ1010O0100000037','20210923CENAIDJA010R0200001860','2021-09-23 10:52:33.658','262222','SVGS','C4403','02','783784','CACC','01445','01','eJyVUttO4zAQ/RXk54KcS7ehb7m0xQjaiFjwgPYhJG3IbpJGzhRRqv4740lSFdRqWUtObM/tnDOz
Y96muW8yNt4xt65vUqVP0/ab52n7r0TVQCXotoBXsuobi8SNJ4Jbg+1xDZhc/zDOn8xdjHO7OC//
QAxCF2cmNw1+bVp9am7wBW5a1ogNGHoGy1XrXMdJc8W5g9u44g5afbUMoEtzya8vTUsafDw0x5bF
sFKwTjblsoIWH6ynwm+gVH4KUq3040z1IhCi04A6NId6sjxTccDmL4uVfG/QbuAtAihKUVElOt8D
yXH3MGNaiBaHfCeX5x0LS2jVm1SpXOPnHKKvEmGCfyHHYphc1h2Yu6TQvVKlvoQK1BbjuaHdfMi2
4UbV3y1oEhUo7y8RcUvS9DEuNkt0cMyhyTm2RUuU6BARPFC2V5V5unXBxJNoDF6A1J5rCadxleYX
T/mfeBujraUeqjc4HqHnboa4YdtDtv9NE6TTuEkC/YR9m7eRY40cmzDLL0R81/fZIUEG/zHv1K0f
xBzPehtz4BteuOLAc6GyUzR92+ZWT5Mqnqdp/jJxnaAZPc4ieo7qosSnIIaYSkyqt4I8+z7ouL7x
6fGTTosoNI79J8ajOVg=
','eJyNUm1vgjAQ/ivLfcaltL4wvyHg7DbBaJd9WPbBgToSqQSq0xn/+67FbsmytyO0tPfcPc8dd4TB
th7XK+gfwS/LUVbpr2Gz5nnW7JLLWkluTol6MV59giCKfR7e+HBCc0Bs/hk346MBxrnnuEH+hhq4
JgdKqEuuKLOpiUumhBI01+sScACR4WLZgMt5Wl8SQvF1L13txVSzXaopIiHuonEUiyCJh3w69gVP
YkQE1SJUZ6IWuWpRJgjrd2ifMUAt4SbdFgupmgrUZsgnhZqpelqaq+vKNsko/l6wMdrtWDZR/MyX
VCu5xqxcLn2ZIRH0H4/N7VcG2zXLQJgHzgc0Lgz43BLPtIR4cHrC/7L/JnskM7HB5SeKxNKwnmXB
PH/rEXtDA34gAjzq1tU4CEvDjJ+6d5NKVQfE3GMQzsDTR/7pYqn94bOq/DRVdmC+jE/PYz2vDWZ2
gkwZx0Qd9BbrVo/mWX7xkMu5moPF/JKOdinaeRZn5bpAcaGO1YojuVuXGmqJBJ6AuFiaCb4VkxaO
Z5uhIAfry6Rq6jcQ8SqNIsJ0oU5T2Er9IsalrN3pftb2XzR2ER+0d0XgGWA=
','3050','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL),
	 (4026,NULL,825200.00,'SUCCESS','2021-09-23 11:02:24','2021-09-23 11:02:25','20210923SIHBIDJ1010O0100000039','20210923CENAIDJA010R0200001446','2021-09-23 11:02:24.342','262222','SVGS','C4403','02','783784','CACC','01445','01','eJyVUttu4jAQ/ZXKz7RynHQbeMsFqFelIGJ1H6o+pAmk2U1C5AxVWcS/dzwhCFagdi05sT2Xc87M
bJm/biZNxgZb5tX1farNadR+8zxt/5WsGqgk3abwRlZzY5G892X402I7XD2mVt+M8ydziXHePs7P
/yIHacCZ4MLifWF3qbnFp7hp2X3WY+gZLpatcx0nzQ3nLm7rhrtoDfQihH2aa96/FrayrAEXA+Ew
RApXybpcVNDyg9VIBg2UOkhB6aV5HOuuCMToPKF2ObzDU+UFxB57fJ0u1UeDdgtvEUBRyoqQ6DwB
AxE8zMfMFKLloT7I5XnLZiW01RtWqVrh5xKj0xJhgq+YIxgmV/WezENSmF7p0lxmGvQG47ll3ALI
NrO1rk8sgujKCrT/h4R4JdX0KS7WC3Rwxa3gHNtiSpSYEBnOKdubznzTunDoKzSGr0DVfjQlHMVV
ml/9yn/HmxhtrfSZfofjEXrezxC3HOeW7V5ogkwaL0mgm7B/5u3Ote9chzirEyGBFwTskCCD/5h3
6tY3Yo5nvY056J1defKgc6qzczIDx+F2J5MQL8sUPwSuMzKjp3FEz1FdlPgUxhATxLB6L8iz64OJ
6xqfHj+ZtMjC8Nh9Au3JOW0=
','eJyNUttSgzAQ/RVnn1tnCVjbvlGgGrXQKfHJ8aECrR1LYCD11vHf3YRGZxxvy5Cwydk9Z5fdw2TX
zto1jPfg1/V53uivabduNnm3Sy5bJbnxEnVvbrUHQRT7PLzw4Y2sB6L6Z1zKzycU5xziJptX0sA1
OTBkDo6Ya1OjgwtkSOZ43gB6QMiwWHXgepm1x4iMXufYQTCp0sdMU0RCXEWzKBZBEk/5YuYLnsSE
CJoiVAeiPo76zBXojZGNmQekJayyXVlI1VWgqimflypV7aI2R2eNbZJR/L1gYwNEyybKn/mSZi23
lJXLlS9zIoLxzb47/cpgu2YZ0NMMFhqXBnxoydC0BIfwdkv/5fmb7JHMRUXLTxSJpXFHloXy/K1H
PBsa8AMRkKtb19IgrAwzferezRvVvBDmmoJoBm4/8i+Klb4P71TjZ5myA/NlfE6H7unQAzM7Qa7M
xVy96C3Wrb6oHqqjdNcWsgKL+SUdGzCywyym9bYkceFSLY3iSD5uaw21RII8QIdKM8GXYt6n8fRc
EtSj+nKpuvoNRDxJowhdXWivK2ytfhHjMNc7GXzW9l80dZEesnc+qRl7
','321','Credit Transfer','SIHBIDJ1','BMRIIDJA','ACTC',NULL),
	 (4033,NULL,825200.00,'SUCCESS','2021-09-23 11:03:03','2021-09-23 11:03:04','20210923SIHBIDJ1010O0100000042','20210923CENAIDJA010R0200001338','2021-09-23 11:03:04.237','262222','SVGS','C4403','02','783784','CACC','01445','01','eJyVUttuozAQ/ZXKz2llLt3SvHFJUq/aBBWr+1DtA4WEsgsOMpOq2Sj/vuMBorRKtF1LBttzO+fM
7FiwaR/ago13zG+au1yb07T7lmXe/ZVQLShBtwW8ktXcWCLuAhF9t9ge14jJ9Rfjwsncxzi/jwvK
P4hBmOLM5rbFb21nSM0tvsBNy7XZiKFntFx1zk2atVece7itK+6hNdTLCPo0l/z20nakZY25M+Yu
w0rROtvUSwUdPlhPRdhCrcMcpF6Zx5keRCBEpwH1aJyhnqzPVByx+ctiJd9btFt4SwCqWiiqROcH
IDnuH2fMCNHhkO/k8rxjcQ2dehOVyzV+ziH6KBEm+BdyLIbJZdODuc8q0ytdm0usQW8xnlvGLYRi
G29089mCJqFAB7+JiF+Tpk9ptVmig2df25xjW4xEmQkR0SNle9VFYFoXTQKJxugFSO25kXCaqry8
+FH+Srcp2jrqsX6D4xF67meIW657zfY/aYJMGj/LYJiwT/N24zk3nkuY5QcioR+G7JCggP+Yd+rW
F2KOZ72LOfCNL3xx4LnQxSmaoetyZ6BJFc/TtL/ZuE7QTJ5mCT0nTVXjU5RCSiUm6q0iz6EPJm5o
fH78ZNIiCoNj/xd7Szk+
','eJyNUu9vmzAQ/Vem+0yqw7CU5hsBsnpbIArep6ofMkiySMEgcLp2Uf/3ng/opKq/jLCx/e7eu8ed
YX7qlt0eZmcIm+a6bO3Xop8Ph7JftdSd0ZJ3mfnDt3YHUZKGMv4ewiMNB1T9ybhcXs8pzh3i5od/
pEFachAoXLwS3pgaXVyjQBqu5wXgACHj7a4HN5uiu0AU9LoXLgKnyu8KS5Eo9TNZJqmKsnQh18tQ
ySwlRNRuYzMQTfBqIjyF/gy9GfpAWuK6OFVbbfoKTL2Qq8rkpls3fPStHU1ixa8LZrVTX4xsqnqb
L2v3+khZpd6FuiQimN2c+9OXDKNrAwOi74HzDE0rBg+WBGwJBvB4S//l/pXsiS5VTdNbFNlIw3Vw
COX5WI+6ZxoIIxXR1lrXUSPsmJk+rXer1rQPhPlFQdQDt8/519udvY9/mzYsCjM2zIv2uQy8y8AH
7p2oNHyxMg92Sa3V+ak7HOsvoTa1hhH0Tj4xFTSGZsybY0Xq4o3ZsORE3x0bCx2ZFO0AXaqNg3+o
1UTY2kmRQwWW2vQGMET91SwJPVup01e2N++IcYXnf53+L+6zaLKRHhpPh9oZ7w==
','30501','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL),
	 (4038,NULL,825200.00,'SUCCESS','2021-09-23 11:07:08','2021-09-23 11:07:09','20210923SIHBIDJ1010O0100000044','20210923CENAIDJA010R0200000054','2021-09-23 11:07:08.426','262222','SVGS','C4403','02','783784','CACC','01445','01','eJyVUttuozAQ/ZXKz2llHLqheeOSpF41DQpW96HaBwoJZRcIMpOq2aj/3vEAUbJKtF1LBttzOefM
zJ5522beZGy8Z25d36fanKbtN8/T9l/JqoFK0m0Br2Q1NxbJe08G3y32gWvA1OaLcd58KTHO7eK8
/A9ykAacCS4sfieGfWpu8QVuWrbNBgw9g9W6da7jpLnh3MFt3XAHrb5eBdClueZ312KoLGvMR2O0
IlKwSbblqoKWH2ym0m+g1H4KSq/N40z3RSBG5wl1bG57PFVeQBywx5fFWr03aLfwFgEUpawIic5z
MBD+w3LGTCFaHuqdXJ73LCyhrd6kStUGP5cYnZYIE/yLOYJhclV3ZB6SwvRKl+YSatA7jOeWcfMh
24VbXZ9YBNGVFWjvNwlxS6rpU1xsV+jgiFvBObbFlCgxITJYUrZXnXmmdcHEU2gMXoCq/WhKOI2r
NL/6kf+KdzHaWumhfoPjEXruZohbttHxkybIpHGTBPoJ+2veRs5w5NjEWZ0I8V3fZ4cEGfzHvFO3
vhBzPOttzEFveOXKg86Fzs7J9G2bD3uZhHhZpvgmcJ2RGT3NInqO6qLEpyCGmCAm1VtBnn0fTFzf
+PT4yaRFFobHxycbXDl9
','eJyNUl1vgjAU/StLn3W5FFT0DQFntwkGu4dl2YMDdWZSCHSfZv99t4VuidFtl/DR9tx7zj3cPRk/
17N6Q0Z74pXlNKvU16R5brdZ8xZM1FIwvYrloz5VK+KHkceCS498YnQIL/6Zt2DTMeZZbd54+4Ea
mCInFKgFQ2qb0mBBAhR09BzSIYgMVusGXC7T+hyA4m2dW0B0qcVLqihCzq/DWRhxP44mLJl5nMUR
IvxqFciWqAvDLrU5OCMYjMAlqCUo0ud8JWTTgSwmbJ7LhayTUm9dVMYkrfi4YBXWYNgzbDw/zRdX
G7HDqkysPZEhERnd7ZvdQwbjWssA4CgGA41yDW4tcbUliuIe/8vbkeqhyHiBj1MUsaFxHMOCdf7W
w980DfF87uNSWVfjIKw1M34q7+aVrN4Rc4NJOAP33/WT1VqdBw+y8tJUmoE5GJ+Baw9ch+jZ8TOp
D+byXb0iZfVl8VSc3RabLTGIX4rRPsVoJ3FR7nKUFizlUusNxcuuVFBDw3FFwMLGdPIVn3dxOB3b
VSYldSZk072G8Feh9YCt2uw0bW3kL2Isaju9/k9n/0Wjh3hhfAEGZRi0
','321','Credit Transfer','SIHBIDJ1','BMRIIDJA','ACTC',NULL),
	 (4045,NULL,825200.00,'SUCCESS','2021-09-23 11:07:19','2021-09-23 11:07:19','20210923SIHBIDJ1010O0100000047','20210923CENAIDJA010R0200001683','2021-09-23 11:07:19.211','262222','SVGS','C4403','02','783784','CACC','01445','01','eJyVUttuozAQ/ZXKz2llE7oheeOSpF61CSpW96HaBwoJZRcIMpOq2Sj/vuMBorRKtF1LBttzO+fM
7Jm3bR6ajE32zK3ru1Sb06z95nna/itZNVBJui3hlazmxiJ558ngu2AHXAOmNl+M86cLF+PcLs7L
/yAGaYozi1uCj61hn5oLvsRNyx6xAUPPYLVunes4aW44d3CLG+6g1derALo013x8bQ2VEBM+mogx
w0rBJtmWqwpafLCZSb+BUvspKL02j3Pdi0CIzgPq0BzrqfJCxQFbvCzX6r1Bu8BbBFCUsqJKdH4A
kuP+cc6MEC0O9U4uz3sWltCqN61StcHPJUQfJcIE/0KOxTC5qjsw90lheqVLcwk16B3Gc2HcfMh2
4VbXny1okhVo7zcRcUvS9Ckutit0cKxbi3Nsi5EoMSEyeKRsrzrzTOuCqafQGLwAqb0wEs7iKs2v
fuS/4l2MtpZ6qN/gdISeuxniwrZv2eEnTZBJ4yYJ9BP2ad5GznDk2IRZfSDiu77Pjgky+I95p259
IeZ01tuYI9/wypVHnkudnaPp2zYf9jSp4mWa1jcL1xma0dM8oueoLkp8CmKIqcS0eivIs++Diesb
n54+mbSIwuA4/AXwfDlm
','eJyNUttum0AQ/ZVqnnG0F2pjv2HAzaY1WGb7VOWBAnatmgXBOk1q5d8zu3hTKUrSDOKyu2fmnDnM
GZanYT3sYXGGsOuuq958rcbn4VCNbyXUoJWwq0z/sqdmBVGShiK+CeERwwPZfjAvF9dLzKOXvOXh
L2oQhhwYYZTMGXelCSVbwggGnQYcPEBkXO9GcFeUwxUhDG96RQnYUvldaSgSKb8l6ySVUZauxHYd
SpGliIj6OtYXogmZTxiXxF+Q2YLOAbXEbXlqaqXHDnS7EptG53rYdnbrS+9MsopfF2yDc+7YZPM2
X9bv1RGrCrULVYVEsPhxHndfMjjXHAPxA/CeoWljwRdLAmsJCeDxFv/L/SvVE1XJFh9vUWSOxp85
Fqzzfz3y3tJAGMkIl8a6AQdhZ5nx03i36XX/gJjvmIQzcPtcf1vvzHn8U/dhWWo3MC/GZxbwWeCD
nZ2o0vZgox/MKzVW50Vzquq++HTT/m7Bod4pyKYM4zKNeXdsUF5c6MJqTtTdsTNQRyVxBYRiczb5
q9xMcEB9jpI87LBSenTAQuQfZTURblr1xtb2+h0xlHH/8/Rfdx9Fo494YTwB6ZcaVw==
','31401','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL),
	 (4052,NULL,825200.00,'SUCCESS','2021-09-23 12:07:02','2021-09-23 12:07:02','20210923SIHBIDJ1010O0100000050','20210923CENAIDJA010R0200000842','2021-09-23 12:07:02.225','262222','SVGS','C4403','02','783784','CACC','01445','01','eJyVUttuozAQ/ZXKz2llHLKheeOSpF61CSpW96HaBwoJZRcIMpOq2Sj/vuMBorRKtF1LBttzO+fM
7Jm3bR6ajE32zK3ru1Sb06z95nna/itZNVBJui3hlazmxiJ558ngu8UOuAZMbb4Y508XLsa5XZyX
/0EM0hRngguL34phn5pbfImb1oizAUPPYLVunes4aW44d3BbN9xBq69XAXRprvnttRgqS0z4eMIF
w0rBJtmWqwpafLCZSb+BUvspKL02j3Pdi0CIzgPq0Fh9PVVeqDhgi5flWr03aDfeEUBRyooq0fkB
SI77xzkzQrQ41Du5PO9ZWEKr3rRK1QY/lxB9lAgT/As5FsPkqu7A3CeF6ZUuzSXUoHcYz8nNh2wX
bnX92YImWYH2fhMRtyRNn+Jiu0IHR4wE59gWI1FiQmTwSNledeaZ1gVTT6ExeAFSe2EknMVVml/9
yH/FuxhtLfVQv8HpCD13M8Qt2x6xw0+aIJPGTRLoJ+zTvI2d4dixCbP6QMR3fZ8dE2TwH/NO3fpC
zOmstzFHvuGVK488lzo7R9O3bT7saVLFyzTFN4HrDM3oaR7Rc1QXJT4FMcRUYlq9FeTZ98HE9Y1P
T59MWkRhcBz+AoK8OUA=
','eJyNUlFvmzAQ/ivTPZPqbJKG5o0AWb0tECXe09SHDJIsWjAI3K5d1P/e8wGdVLVdD2Fz9nf3fXfc
Gea37bI9wOwMYV1fF437WnTr8Vh0u1GmtUaxl9lffOs8iJI0VPGXEB7JPNDVB+M26npOcaKPmx//
kgblyEGiFHgl/SE1ClyjRGfBWIIHhIx3+w5cb/P2AlHSKy4EAqfa3OWOItH6W7JMUh1l6UKtl6FW
WUqIqNnFtica4dVI+honM5zOUAJpiav8ttwZ21Vgq4ValXZj23XNR5+boUms+HXBzoRgPcymy7f5
suZgTpRVmX1oCiKC2Y9zd/qSYehaz4A4EeA9Q9OSwX1LAm4JBvB4Q//l/pXsiSl0RctbFNlAM8GB
hfL8X4++ZxoIIx2R61rX0iDsmZk+Xe9WjW0eCPOdgmgGbp7zr3d7dx//tE2Y53YYmBfjMw38aTAG
np2osHyxsg9uS12rw2Jbfkp+VzAA3sklLyVZP4ib+lSSsnhrtyw3MXen2kEHFk0eoCuTg7/q1Yhm
c+yTGo+KK4ztimeI/mNYDvquSq+r6mDfESOkP55c/ivso2hqIT1kT1uYF/o=
','31411','Credit Transfer','SIHBIDJ1','CENAIDJA','ACTC',NULL);INSERT INTO public.domain_code (id,grp,"key",value) VALUES
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
	 (8,'org.apache.camel.http.common.HttpOperationFailedException','HTTP call error');INSERT INTO public.fi_credit_transfer (id,settlconf_bizmsgid,amount,cre_dt,credit_bic,debtor_bic,intr_ref_id,log_message_id,orign_bank,req_bizmsgid,saf,saf_counter,status,call_status,cihub_req_time,cihub_resp_time,full_request_msg,full_response_msg,resp_bizmsgid,response_status) VALUES
	 (3753,NULL,60030700.00,'2021-09-22 12:16:33.308','CENAIDJA','SUNIIDJA','1176564',NULL,'SIHBIDJ1','20210922SIHBIDJ1019O0100000067',NULL,NULL,NULL,'SUCCESS','2021-09-22 12:16:33','2021-09-22 12:16:33','eJyNUstuwjAQ/BefAa2NBDW3PCi4Kg8F0wviAEmgqImJHEeCIv69aye0qlRELcVea3d2Zie+EL8q
J+WeDC7EK4pxom30XO+HQ1KfSqjSKOFuM/PusvZGFmLsi/CFkiuuFpHHf+L8SSQQ5zU4//CJGoQl
JwwYBc7YrTVQPgMKbvX6pEWwMkx3dXGxicsOAMePdoBjNtBpaJo2beBtxiRlA9obdLsEmcJjXOWp
MrW+ODFS72w80rfZnZC/dTQinm40Mr9D1CLT7WwnTyXmKd4WxmS5UI7JxRNjKYLXaETs/IHTIU+u
ZHUh89zUpg1VIo+43VP02xls8Eg5kmFzWTRiArM/zytd2HiujT4jnHOnSSij/Q+n1sudX2+brEqx
oAfQhT4Amm6diC1IhJGzd2v0oyeznH7/ejf5I0AwnHo/gAitqbUvS6Mxv1JVlq2v17V9Sl8dC8+Z
','eJyNkm1vgjAQx79LX6tp68NW3iHg7JKBgfpq8cXGgzMZhUBd3My++66Fmm3RaBNK73p3/1+ud0Tz
ffvUbpFzRG5dL7NGnxbdvttl3V9y2SrJjRWpN3OrLeQFocv9Rxd9wxogUd2Yl/DlHPJInzfffQED
1+KIYkowo9SWxoTFjGFYhJEpGiCI9POiC65f0naEMYWPjAhGplTykWo0EQfJKgqTALxek/uqLz7E
bEipwFOHzJzxGIG+X6X7Mpeqo1bVgq9Klag2ro3robGNMZTnITUhnkyIVRPlZb2o2cp3qMpl4coM
hJDzfOy8/xVsp6wCnt2jwSk0LE1w3wZm2oCZDTjLQajl2MCDHc4gBDITFWyXOCJMepQ7qwR1rkOL
g5FBric8MHV/W5iQwijDUTd41ajmE2LWkATDsTnVj/NC3/uvyjyEu1XXRmwd8l+j6WW3Jv6ZaUDQ
2w+NgOfi
','20210922CENAIDJA019R9900001915','ACTC');INSERT INTO public.inbound_counter (tanggal,last_number) VALUES
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
	 (20210923,56);INSERT INTO public.mock_names (name) VALUES
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
INSERT INTO public.mock_names (name) VALUES
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
INSERT INTO public.mock_names (name) VALUES
	 ('Andre'),
	 ('Sjafril'),
	 ('Widodo'),
	 ('Syamsul'),
	 ('Erwin'),
	 ('Reni'),
	 ('Winata'),
	 ('Hadi');INSERT INTO public.mock_pacs002 (id,biz_msg_idr,full_message,orgnl_end_to_end_id,orgnl_msg_id,orgnl_msg_name,trx_type) VALUES
	 (3587,'20210921CENAIDJA019R9900000813','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210921CENAIDJA019R9900000813","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-21T21:40:23"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210921CENAIDJA01900000978","CreDtTm":"2021-09-21T21:40:23"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210921SIHBIDJ101900000279","OrgnlMsgNmId":"pacs.009.001.09","OrgnlCreDtTm":"2021-09-21T21:40:22"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210921SIHBIDJ1019O0300000278","OrgnlTxId":"20210921SIHBIDJ101900000279","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Dbtr":{"Agt":{"FinInstnId":{"Othr":{"Id":"SUNIIDJA"}}}},"Cdtr":{"Agt":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}}}}]}}}}','20210921SIHBIDJ1019O0300000278','20210921SIHBIDJ101900000279','pacs.009.001.09','FICreditConfirmation'),
	 (3591,'20210921CENAIDJA019R9900001279','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210921CENAIDJA019R9900001279","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-21T21:41:22"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210921CENAIDJA01900001128","CreDtTm":"2021-09-21T21:41:22"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210921SIHBIDJ101900000281","OrgnlMsgNmId":"pacs.009.001.09","OrgnlCreDtTm":"2021-09-21T21:41:22"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210921SIHBIDJ1019O0300000280","OrgnlTxId":"20210921SIHBIDJ101900000281","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Dbtr":{"Agt":{"FinInstnId":{"Othr":{"Id":"SUNIIDJA"}}}},"Cdtr":{"Agt":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}}}}]}}}}','20210921SIHBIDJ1019O0300000280','20210921SIHBIDJ101900000281','pacs.009.001.09','FICreditConfirmation'),
	 (3595,'20210921CENAIDJA019R9900001690','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210921CENAIDJA019R9900001690","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-21T21:44:39"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210921CENAIDJA01900001048","CreDtTm":"2021-09-21T21:44:39"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210921SIHBIDJ101900000283","OrgnlMsgNmId":"pacs.009.001.09","OrgnlCreDtTm":"2021-09-21T21:44:39"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210921SIHBIDJ1019O0300000282","OrgnlTxId":"20210921SIHBIDJ101900000283","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Dbtr":{"Agt":{"FinInstnId":{"Othr":{"Id":"SUNIIDJA"}}}},"Cdtr":{"Agt":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}}}}]}}}}','20210921SIHBIDJ1019O0300000282','20210921SIHBIDJ101900000283','pacs.009.001.09','FICreditConfirmation'),
	 (3657,'20210922CENAIDJA010R0200001793','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001793","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T01:32:44"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000434","CreDtTm":"2021-09-22T01:32:44"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000019","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000018","OrgnlTxId":"20210922SIHBIDJ101000000019","TxSts":"RJCT","StsRsnInf":[{"Rsn":{"Prtry":"U001"},"AddtlInf":["Haati hati banyak penipuan"]}],"OrgnlTxRef":{"Cdtr":{}}}]}}}}','20210922SIHBIDJ1010O0100000018','20210922SIHBIDJ101000000019','pacs.008.001.08','FICreditConfirmation'),
	 (3665,'20210922CENAIDJA010R0200000648','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000648","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T02:04:47"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000882","CreDtTm":"2021-09-22T02:04:47"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000024","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000023","OrgnlTxId":"20210922SIHBIDJ101000000024","TxSts":"RJCT","StsRsnInf":[{"Rsn":{"Prtry":"U001"},"AddtlInf":["Haati hati banyak penipuan"]}],"OrgnlTxRef":{"Cdtr":{}}}]}}}}','20210922SIHBIDJ1010O0100000023','20210922SIHBIDJ101000000024','pacs.008.001.08','FICreditConfirmation'),
	 (3669,'20210922CENAIDJA010R0200001404','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001404","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T02:18:10"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000001207","CreDtTm":"2021-09-22T02:18:10"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000027","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000026","OrgnlTxId":"20210922SIHBIDJ101000000027","TxSts":"RJCT","StsRsnInf":[{"Rsn":{"Prtry":"U001"},"AddtlInf":["Haati hati banyak penipuan"]}],"OrgnlTxRef":{"Cdtr":{}}}]}}}}','20210922SIHBIDJ1010O0100000026','20210922SIHBIDJ101000000027','pacs.008.001.08','FICreditConfirmation'),
	 (3674,'20210922CENAIDJA010R0200001541','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001541","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T02:23:24"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000042","CreDtTm":"2021-09-22T02:23:24"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000030","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000029","OrgnlTxId":"20210922SIHBIDJ101000000030","TxSts":"RJCT","StsRsnInf":[{"Rsn":{"Prtry":"U001"},"AddtlInf":["Haati hati banyak penipuan"]}],"OrgnlTxRef":{"Cdtr":{}}}]}}}}','20210922SIHBIDJ1010O0100000029','20210922SIHBIDJ101000000030','pacs.008.001.08','FICreditConfirmation'),
	 (3679,'20210922CENAIDJA010R0200000829','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000829","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T02:27:16"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000001060","CreDtTm":"2021-09-22T02:27:16"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000033","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000032","OrgnlTxId":"20210922SIHBIDJ101000000033","TxSts":"RJCT","StsRsnInf":[{"Rsn":{"Prtry":"U001"},"AddtlInf":["Haati hati banyak penipuan"]}],"OrgnlTxRef":{"Cdtr":{}}}]}}}}','20210922SIHBIDJ1010O0100000032','20210922SIHBIDJ101000000033','pacs.008.001.08','FICreditConfirmation'),
	 (3684,'20210922CENAIDJA010R0200001398','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001398","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T02:31:31"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000263","CreDtTm":"2021-09-22T02:31:31"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000036","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000035","OrgnlTxId":"20210922SIHBIDJ101000000036","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Widodo Andre"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000035','20210922SIHBIDJ101000000036','pacs.008.001.08','FICreditConfirmation'),
	 (3685,'20210922CENAIDJA010R0200001586','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001398","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T02:31:31"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000263","CreDtTm":"2021-09-22T02:31:31"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000036","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000035","OrgnlTxId":"20210922SIHBIDJ101000000036","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Widodo Andre"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000035','20210922SIHBIDJ101000000036','pacs.008.001.08','SettlementConfirmation');
INSERT INTO public.mock_pacs002 (id,biz_msg_idr,full_message,orgnl_end_to_end_id,orgnl_msg_id,orgnl_msg_name,trx_type) VALUES
	 (3691,'20210922CENAIDJA010R0200001583','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001583","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T02:40:26"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000986","CreDtTm":"2021-09-22T02:40:26"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000039","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000038","OrgnlTxId":"20210922SIHBIDJ101000000039","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Suseno Adam"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000038','20210922SIHBIDJ101000000039','pacs.008.001.08','FICreditConfirmation'),
	 (3692,'20210922CENAIDJA010R0200001416','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001583","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T02:40:26"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000986","CreDtTm":"2021-09-22T02:40:26"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000039","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000038","OrgnlTxId":"20210922SIHBIDJ101000000039","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Suseno Adam"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000038','20210922SIHBIDJ101000000039','pacs.008.001.08','SettlementConfirmation'),
	 (3698,'20210922CENAIDJA010R0200001754','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001754","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T02:48:00"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000139","CreDtTm":"2021-09-22T02:48:00"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000042","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000041","OrgnlTxId":"20210922SIHBIDJ101000000042","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Sjafril Joko"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000041','20210922SIHBIDJ101000000042','pacs.008.001.08','FICreditConfirmation'),
	 (3699,'20210922CENAIDJA010R0200000340','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001754","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T02:48:00"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000139","CreDtTm":"2021-09-22T02:48:00"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000042","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000041","OrgnlTxId":"20210922SIHBIDJ101000000042","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Sjafril Joko"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000041','20210922SIHBIDJ101000000042','pacs.008.001.08','SettlementConfirmation'),
	 (3705,'20210922CENAIDJA010R0200000372','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000372","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T02:49:44"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000001684","CreDtTm":"2021-09-22T02:49:44"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000045","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000044","OrgnlTxId":"20210922SIHBIDJ101000000045","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Erwin Andre"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000044','20210922SIHBIDJ101000000045','pacs.008.001.08','FICreditConfirmation'),
	 (3706,'20210922CENAIDJA010R0200000414','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000372","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T02:49:44"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000001684","CreDtTm":"2021-09-22T02:49:44"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000045","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000044","OrgnlTxId":"20210922SIHBIDJ101000000045","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Erwin Andre"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000044','20210922SIHBIDJ101000000045','pacs.008.001.08','SettlementConfirmation'),
	 (3712,'20210922CENAIDJA010R0200000173','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000173","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T02:53:36"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000078","CreDtTm":"2021-09-22T02:53:36"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000048","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000047","OrgnlTxId":"20210922SIHBIDJ101000000048","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Winata Erwin"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000047','20210922SIHBIDJ101000000048','pacs.008.001.08','FICreditConfirmation'),
	 (3713,'20210922CENAIDJA010R0200001791','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000173","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T02:53:36"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000078","CreDtTm":"2021-09-22T02:53:36"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000048","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000047","OrgnlTxId":"20210922SIHBIDJ101000000048","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Winata Erwin"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000047','20210922SIHBIDJ101000000048','pacs.008.001.08','SettlementConfirmation'),
	 (3720,'20210922CENAIDJA010R0200001431','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001431","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T03:06:41"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000619","CreDtTm":"2021-09-22T03:06:41"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000051","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000050","OrgnlTxId":"20210922SIHBIDJ101000000051","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Widodo Sjafril"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000050','20210922SIHBIDJ101000000051','pacs.008.001.08','FICreditConfirmation'),
	 (3721,'20210922CENAIDJA010R0200001148','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001431","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T03:06:41"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000619","CreDtTm":"2021-09-22T03:06:41"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000051","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000050","OrgnlTxId":"20210922SIHBIDJ101000000051","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Widodo Sjafril"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000050','20210922SIHBIDJ101000000051','pacs.008.001.08','SettlementConfirmation');
INSERT INTO public.mock_pacs002 (id,biz_msg_idr,full_message,orgnl_end_to_end_id,orgnl_msg_id,orgnl_msg_name,trx_type) VALUES
	 (3729,'20210922CENAIDJA010R0200001277','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001277","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T03:16:30"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000434","CreDtTm":"2021-09-22T03:16:30"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000056","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000055","OrgnlTxId":"20210922SIHBIDJ101000000056","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Johan Yogi"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000055','20210922SIHBIDJ101000000056','pacs.008.001.08','FICreditConfirmation'),
	 (3730,'20210922CENAIDJA010R0200001465','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001277","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T03:16:30"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000434","CreDtTm":"2021-09-22T03:16:30"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000056","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000055","OrgnlTxId":"20210922SIHBIDJ101000000056","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Johan Yogi"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000055','20210922SIHBIDJ101000000056','pacs.008.001.08','SettlementConfirmation'),
	 (3738,'20210922CENAIDJA010R0200001303','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001303","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T03:27:23"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000001620","CreDtTm":"2021-09-22T03:27:23"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000061","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000060","OrgnlTxId":"20210922SIHBIDJ101000000061","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Hadi Joko"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000060','20210922SIHBIDJ101000000061','pacs.008.001.08','FICreditConfirmation'),
	 (3739,'20210922CENAIDJA010R0200001613','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001303","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T03:27:23"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000001620","CreDtTm":"2021-09-22T03:27:23"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000061","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000060","OrgnlTxId":"20210922SIHBIDJ101000000061","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Hadi Joko"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000060','20210922SIHBIDJ101000000061','pacs.008.001.08','SettlementConfirmation'),
	 (3752,'20210922CENAIDJA019R9900001915','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA019R9900001915","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T05:16:33"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01900000441","CreDtTm":"2021-09-22T05:16:33"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101900000068","OrgnlMsgNmId":"pacs.009.001.09","OrgnlCreDtTm":"2021-09-22T12:16:33"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1019O0100000067","OrgnlTxId":"20210922SIHBIDJ101900000068","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Dbtr":{"Agt":{"FinInstnId":{"Othr":{"Id":"SUNIIDJA"}}}},"Cdtr":{"Agt":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}}}}]}}}}','20210922SIHBIDJ1019O0100000067','20210922SIHBIDJ101900000068','pacs.009.001.09','FICreditConfirmation'),
	 (3763,'20210922CENAIDJA010R0200001939','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001939","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T06:23:53"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000138","CreDtTm":"2021-09-22T06:23:53"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000071","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000070","OrgnlTxId":"20210922SIHBIDJ101000000071","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Adiputro Kusuma"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000070','20210922SIHBIDJ101000000071','pacs.008.001.08','FICreditConfirmation'),
	 (3764,'20210922CENAIDJA010R0200001359','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001939","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T06:23:53"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000138","CreDtTm":"2021-09-22T06:23:53"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000071","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000070","OrgnlTxId":"20210922SIHBIDJ101000000071","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Adiputro Kusuma"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000070','20210922SIHBIDJ101000000071','pacs.008.001.08','SettlementConfirmation'),
	 (3770,'20210922CENAIDJA010R0200000020','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000020","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T06:27:03"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000001485","CreDtTm":"2021-09-22T06:27:03"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000076","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000075","OrgnlTxId":"20210922SIHBIDJ101000000076","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Reni Erwin"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000075','20210922SIHBIDJ101000000076','pacs.008.001.08','FICreditConfirmation'),
	 (3771,'20210922CENAIDJA010R0200001034','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000020","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T06:27:03"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000001485","CreDtTm":"2021-09-22T06:27:03"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000076","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000075","OrgnlTxId":"20210922SIHBIDJ101000000076","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Reni Erwin"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000075','20210922SIHBIDJ101000000076','pacs.008.001.08','SettlementConfirmation'),
	 (3778,'20210922CENAIDJA010R0200000557','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000557","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T06:28:41"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000001549","CreDtTm":"2021-09-22T06:28:41"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000081","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000080","OrgnlTxId":"20210922SIHBIDJ101000000081","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Yogi Johan"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000080','20210922SIHBIDJ101000000081','pacs.008.001.08','FICreditConfirmation');
INSERT INTO public.mock_pacs002 (id,biz_msg_idr,full_message,orgnl_end_to_end_id,orgnl_msg_id,orgnl_msg_name,trx_type) VALUES
	 (3779,'20210922CENAIDJA010R0200001540','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000557","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T06:28:41"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000001549","CreDtTm":"2021-09-22T06:28:41"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000081","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000080","OrgnlTxId":"20210922SIHBIDJ101000000081","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Yogi Johan"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000080','20210922SIHBIDJ101000000081','pacs.008.001.08','SettlementConfirmation'),
	 (3786,'20210922CENAIDJA010R0200001925','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001925","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T06:30:14"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000001302","CreDtTm":"2021-09-22T06:30:14"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000086","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000085","OrgnlTxId":"20210922SIHBIDJ101000000086","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Andre Johan"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000085','20210922SIHBIDJ101000000086','pacs.008.001.08','FICreditConfirmation'),
	 (3787,'20210922CENAIDJA010R0200001795','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001925","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T06:30:14"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000001302","CreDtTm":"2021-09-22T06:30:14"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000086","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000085","OrgnlTxId":"20210922SIHBIDJ101000000086","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Andre Johan"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000085','20210922SIHBIDJ101000000086','pacs.008.001.08','SettlementConfirmation'),
	 (3794,'20210922CENAIDJA010R0200001653','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001653","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T06:32:17"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000415","CreDtTm":"2021-09-22T06:32:17"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000091","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000090","OrgnlTxId":"20210922SIHBIDJ101000000091","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Ambarwati Adam"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000090','20210922SIHBIDJ101000000091','pacs.008.001.08','FICreditConfirmation'),
	 (3795,'20210922CENAIDJA010R0200000522','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001653","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T06:32:17"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000415","CreDtTm":"2021-09-22T06:32:17"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000091","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000090","OrgnlTxId":"20210922SIHBIDJ101000000091","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Ambarwati Adam"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000090','20210922SIHBIDJ101000000091','pacs.008.001.08','SettlementConfirmation'),
	 (3802,'20210922CENAIDJA010R0200001327','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001327","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T06:34:17"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000036","CreDtTm":"2021-09-22T06:34:17"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000096","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000095","OrgnlTxId":"20210922SIHBIDJ101000000096","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Kusuma Susilo"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000095','20210922SIHBIDJ101000000096','pacs.008.001.08','FICreditConfirmation'),
	 (3803,'20210922CENAIDJA010R0200000941','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001327","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T06:34:17"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000036","CreDtTm":"2021-09-22T06:34:17"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000096","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000095","OrgnlTxId":"20210922SIHBIDJ101000000096","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Kusuma Susilo"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000095','20210922SIHBIDJ101000000096','pacs.008.001.08','SettlementConfirmation'),
	 (3810,'20210922CENAIDJA010R0200000466','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000466","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T06:41:16"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000417","CreDtTm":"2021-09-22T06:41:16"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000101","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000100","OrgnlTxId":"20210922SIHBIDJ101000000101","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Syamsul Johan"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000100','20210922SIHBIDJ101000000101','pacs.008.001.08','FICreditConfirmation'),
	 (3811,'20210922CENAIDJA010R0200000545','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000466","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T06:41:16"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000417","CreDtTm":"2021-09-22T06:41:16"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000101","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000100","OrgnlTxId":"20210922SIHBIDJ101000000101","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Syamsul Johan"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000100','20210922SIHBIDJ101000000101','pacs.008.001.08','SettlementConfirmation'),
	 (3818,'20210922CENAIDJA010R0200000383','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000383","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T06:41:22"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000001243","CreDtTm":"2021-09-22T06:41:22"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000106","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000105","OrgnlTxId":"20210922SIHBIDJ101000000106","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Anton Adiputro"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000105','20210922SIHBIDJ101000000106','pacs.008.001.08','FICreditConfirmation');
INSERT INTO public.mock_pacs002 (id,biz_msg_idr,full_message,orgnl_end_to_end_id,orgnl_msg_id,orgnl_msg_name,trx_type) VALUES
	 (3819,'20210922CENAIDJA010R0200000949','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000383","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T06:41:22"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000001243","CreDtTm":"2021-09-22T06:41:22"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000106","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000105","OrgnlTxId":"20210922SIHBIDJ101000000106","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Anton Adiputro"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000105','20210922SIHBIDJ101000000106','pacs.008.001.08','SettlementConfirmation'),
	 (3826,'20210922CENAIDJA010R0200000455','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000455","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T06:47:00"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000042","CreDtTm":"2021-09-22T06:47:00"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000111","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000110","OrgnlTxId":"20210922SIHBIDJ101000000111","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Windi Widodo"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000110','20210922SIHBIDJ101000000111','pacs.008.001.08','FICreditConfirmation'),
	 (3827,'20210922CENAIDJA010R0200000988','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000455","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T06:47:00"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000042","CreDtTm":"2021-09-22T06:47:00"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000111","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000110","OrgnlTxId":"20210922SIHBIDJ101000000111","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Windi Widodo"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000110','20210922SIHBIDJ101000000111','pacs.008.001.08','SettlementConfirmation'),
	 (3835,'20210922CENAIDJA010R0200000735','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000735","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T10:15:59"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000265","CreDtTm":"2021-09-22T10:15:59"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000115","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000114","OrgnlTxId":"20210922SIHBIDJ101000000115","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Kusuma Adiputro"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000114','20210922SIHBIDJ101000000115','pacs.008.001.08','FICreditConfirmation'),
	 (3836,'20210922CENAIDJA010R0200001197','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000735","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T10:15:59"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000265","CreDtTm":"2021-09-22T10:15:59"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000115","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000114","OrgnlTxId":"20210922SIHBIDJ101000000115","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Kusuma Adiputro"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000114','20210922SIHBIDJ101000000115','pacs.008.001.08','SettlementConfirmation'),
	 (3927,'20210922CENAIDJA010R0200001852','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001852","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T16:05:53"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000001822","CreDtTm":"2021-09-22T16:05:53"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000204","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000203","OrgnlTxId":"20210922SIHBIDJ101000000204","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Bustami Indarto"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000203','20210922SIHBIDJ101000000204','pacs.008.001.08','FICreditConfirmation'),
	 (3928,'20210922CENAIDJA010R0200001526','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200001852","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T16:05:53"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000001822","CreDtTm":"2021-09-22T16:05:53"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000204","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000203","OrgnlTxId":"20210922SIHBIDJ101000000204","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Bustami Indarto"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000203','20210922SIHBIDJ101000000204','pacs.008.001.08','SettlementConfirmation'),
	 (3938,'20210922CENAIDJA010R0200000640','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000640","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T16:15:24"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000584","CreDtTm":"2021-09-22T16:15:24"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000211","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000210","OrgnlTxId":"20210922SIHBIDJ101000000211","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Indarto Yogi"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000210','20210922SIHBIDJ101000000211','pacs.008.001.08','FICreditConfirmation'),
	 (3939,'20210922CENAIDJA010R0200001495','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000640","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T16:15:24"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000584","CreDtTm":"2021-09-22T16:15:24"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210922SIHBIDJ101000000211","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210922SIHBIDJ1010O0100000210","OrgnlTxId":"20210922SIHBIDJ101000000211","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Indarto Yogi"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210922SIHBIDJ1010O0100000210','20210922SIHBIDJ101000000211','pacs.008.001.08','SettlementConfirmation'),
	 (3945,'20210922CENAIDJA010R0200000174','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000174","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T22:39:04"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000001352","CreDtTm":"2021-09-22T22:39:04"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000003","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000002","OrgnlTxId":"20210923SIHBIDJ101000000003","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Erwin Susilo"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000002','20210923SIHBIDJ101000000003','pacs.008.001.08','FICreditConfirmation');
INSERT INTO public.mock_pacs002 (id,biz_msg_idr,full_message,orgnl_end_to_end_id,orgnl_msg_id,orgnl_msg_name,trx_type) VALUES
	 (3946,'20210922CENAIDJA010R0200001189','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000174","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T22:39:04"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000001352","CreDtTm":"2021-09-22T22:39:04"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000003","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000002","OrgnlTxId":"20210923SIHBIDJ101000000003","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Erwin Susilo"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000002','20210923SIHBIDJ101000000003','pacs.008.001.08','SettlementConfirmation'),
	 (3952,'20210922CENAIDJA010R0200000251','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000251","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T22:51:21"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000021","CreDtTm":"2021-09-22T22:51:21"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000006","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000005","OrgnlTxId":"20210923SIHBIDJ101000000006","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Eko Wirawan"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000005','20210923SIHBIDJ101000000006','pacs.008.001.08','FICreditConfirmation'),
	 (3953,'20210922CENAIDJA010R0200001334','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210922CENAIDJA010R0200000251","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-22T22:51:21"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210922CENAIDJA01000000021","CreDtTm":"2021-09-22T22:51:21"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000006","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000005","OrgnlTxId":"20210923SIHBIDJ101000000006","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Eko Wirawan"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000005','20210923SIHBIDJ101000000006','pacs.008.001.08','SettlementConfirmation'),
	 (3959,'20210923CENAIDJA010R0200000570','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200000570","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T00:52:51"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000000295","CreDtTm":"2021-09-23T00:52:51"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000009","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000008","OrgnlTxId":"20210923SIHBIDJ101000000009","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Andrea Erwin"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000008','20210923SIHBIDJ101000000009','pacs.008.001.08','FICreditConfirmation'),
	 (3960,'20210923CENAIDJA010R0200001434','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200000570","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T00:52:51"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000000295","CreDtTm":"2021-09-23T00:52:51"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000009","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000008","OrgnlTxId":"20210923SIHBIDJ101000000009","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Andrea Erwin"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000008','20210923SIHBIDJ101000000009','pacs.008.001.08','SettlementConfirmation'),
	 (3984,'20210923CENAIDJA010R0200000662','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200000662","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T03:15:08"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000000038","CreDtTm":"2021-09-23T03:15:08"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000023","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000022","OrgnlTxId":"20210923SIHBIDJ101000000023","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Anton Indarto"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000022','20210923SIHBIDJ101000000023','pacs.008.001.08','FICreditConfirmation'),
	 (3985,'20210923CENAIDJA010R0200000495','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200000662","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T03:15:08"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000000038","CreDtTm":"2021-09-23T03:15:08"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000023","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000022","OrgnlTxId":"20210923SIHBIDJ101000000023","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Anton Indarto"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000022','20210923SIHBIDJ101000000023','pacs.008.001.08','SettlementConfirmation'),
	 (3990,'20210923CENAIDJA010R0200000092','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200000092","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T03:40:16"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001352","CreDtTm":"2021-09-23T03:40:16"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000025","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000024","OrgnlTxId":"20210923SIHBIDJ101000000025","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Samudera Windi"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000024','20210923SIHBIDJ101000000025','pacs.008.001.08','FICreditConfirmation'),
	 (3991,'20210923CENAIDJA010R0200001847','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200000092","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T03:40:16"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001352","CreDtTm":"2021-09-23T03:40:16"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000025","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000024","OrgnlTxId":"20210923SIHBIDJ101000000025","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Samudera Windi"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000024','20210923SIHBIDJ101000000025','pacs.008.001.08','SettlementConfirmation'),
	 (3994,'20210923CENAIDJA010R0200001243','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200001243","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T03:44:35"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001167","CreDtTm":"2021-09-23T03:44:35"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000027","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000026","OrgnlTxId":"20210923SIHBIDJ101000000027","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Andre Anton"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000026','20210923SIHBIDJ101000000027','pacs.008.001.08','FICreditConfirmation');
INSERT INTO public.mock_pacs002 (id,biz_msg_idr,full_message,orgnl_end_to_end_id,orgnl_msg_id,orgnl_msg_name,trx_type) VALUES
	 (3995,'20210923CENAIDJA010R0200001527','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200001243","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T03:44:35"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001167","CreDtTm":"2021-09-23T03:44:35"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000027","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000026","OrgnlTxId":"20210923SIHBIDJ101000000027","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Andre Anton"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000026','20210923SIHBIDJ101000000027','pacs.008.001.08','SettlementConfirmation'),
	 (4000,'20210923CENAIDJA010R0200001465','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200001465","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T03:48:34"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001562","CreDtTm":"2021-09-23T03:48:34"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000030","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000029","OrgnlTxId":"20210923SIHBIDJ101000000030","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Indarto Erwin"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000029','20210923SIHBIDJ101000000030','pacs.008.001.08','FICreditConfirmation'),
	 (4001,'20210923CENAIDJA010R0200001963','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200001465","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T03:48:34"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001562","CreDtTm":"2021-09-23T03:48:34"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000030","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000029","OrgnlTxId":"20210923SIHBIDJ101000000030","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Indarto Erwin"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000029','20210923SIHBIDJ101000000030','pacs.008.001.08','SettlementConfirmation'),
	 (4007,'20210923CENAIDJA010R0200001835','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200001835","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T03:52:16"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001873","CreDtTm":"2021-09-23T03:52:16"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000033","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000032","OrgnlTxId":"20210923SIHBIDJ101000000033","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Kusuma Winata"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000032','20210923SIHBIDJ101000000033','pacs.008.001.08','FICreditConfirmation'),
	 (4008,'20210923CENAIDJA010R0200001645','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200001835","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T03:52:16"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001873","CreDtTm":"2021-09-23T03:52:16"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000033","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000032","OrgnlTxId":"20210923SIHBIDJ101000000033","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Kusuma Winata"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000032','20210923SIHBIDJ101000000033','pacs.008.001.08','SettlementConfirmation'),
	 (4012,'20210923CENAIDJA010R0200000719','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200000719","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T03:52:21"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001850","CreDtTm":"2021-09-23T03:52:21"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000035","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000034","OrgnlTxId":"20210923SIHBIDJ101000000035","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Kusuma Andrea"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000034','20210923SIHBIDJ101000000035','pacs.008.001.08','FICreditConfirmation'),
	 (4013,'20210923CENAIDJA010R0200000018','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200000719","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T03:52:21"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001850","CreDtTm":"2021-09-23T03:52:21"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000035","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000034","OrgnlTxId":"20210923SIHBIDJ101000000035","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Kusuma Andrea"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000034','20210923SIHBIDJ101000000035','pacs.008.001.08','SettlementConfirmation'),
	 (4019,'20210923CENAIDJA010R0200001764','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200001764","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T03:52:33"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001307","CreDtTm":"2021-09-23T03:52:33"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000038","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000037","OrgnlTxId":"20210923SIHBIDJ101000000038","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Hadi Winata"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000037','20210923SIHBIDJ101000000038','pacs.008.001.08','FICreditConfirmation'),
	 (4020,'20210923CENAIDJA010R0200001860','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200001764","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T03:52:33"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001307","CreDtTm":"2021-09-23T03:52:33"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000038","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000037","OrgnlTxId":"20210923SIHBIDJ101000000038","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Hadi Winata"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000037','20210923SIHBIDJ101000000038','pacs.008.001.08','SettlementConfirmation'),
	 (4024,'20210923CENAIDJA010R0200001871','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200001871","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T04:02:24"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001080","CreDtTm":"2021-09-23T04:02:24"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000040","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000039","OrgnlTxId":"20210923SIHBIDJ101000000040","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Joko Suseno"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000039','20210923SIHBIDJ101000000040','pacs.008.001.08','FICreditConfirmation');
INSERT INTO public.mock_pacs002 (id,biz_msg_idr,full_message,orgnl_end_to_end_id,orgnl_msg_id,orgnl_msg_name,trx_type) VALUES
	 (4025,'20210923CENAIDJA010R0200001446','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200001871","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T04:02:24"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001080","CreDtTm":"2021-09-23T04:02:24"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000040","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000039","OrgnlTxId":"20210923SIHBIDJ101000000040","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Joko Suseno"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000039','20210923SIHBIDJ101000000040','pacs.008.001.08','SettlementConfirmation'),
	 (4031,'20210923CENAIDJA010R0200000646','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200000646","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T04:03:04"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001476","CreDtTm":"2021-09-23T04:03:04"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000043","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000042","OrgnlTxId":"20210923SIHBIDJ101000000043","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Susilo Anton"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000042','20210923SIHBIDJ101000000043','pacs.008.001.08','FICreditConfirmation'),
	 (4032,'20210923CENAIDJA010R0200001338','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200000646","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T04:03:04"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001476","CreDtTm":"2021-09-23T04:03:04"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000043","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000042","OrgnlTxId":"20210923SIHBIDJ101000000043","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Susilo Anton"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000042','20210923SIHBIDJ101000000043','pacs.008.001.08','SettlementConfirmation'),
	 (4036,'20210923CENAIDJA010R0200000324','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200000324","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T04:07:08"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000000794","CreDtTm":"2021-09-23T04:07:08"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000045","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000044","OrgnlTxId":"20210923SIHBIDJ101000000045","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Joko Yogi"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000044','20210923SIHBIDJ101000000045','pacs.008.001.08','FICreditConfirmation'),
	 (4037,'20210923CENAIDJA010R0200000054','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200000324","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T04:07:08"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000000794","CreDtTm":"2021-09-23T04:07:08"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000045","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000044","OrgnlTxId":"20210923SIHBIDJ101000000045","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Joko Yogi"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000044','20210923SIHBIDJ101000000045','pacs.008.001.08','SettlementConfirmation'),
	 (4043,'20210923CENAIDJA010R0200001758','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200001758","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T04:07:19"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001476","CreDtTm":"2021-09-23T04:07:19"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000048","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000047","OrgnlTxId":"20210923SIHBIDJ101000000048","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Samudera Joko"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000047','20210923SIHBIDJ101000000048','pacs.008.001.08','FICreditConfirmation'),
	 (4044,'20210923CENAIDJA010R0200001683','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200001758","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T04:07:19"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001476","CreDtTm":"2021-09-23T04:07:19"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000048","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000047","OrgnlTxId":"20210923SIHBIDJ101000000048","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Samudera Joko"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000047','20210923SIHBIDJ101000000048','pacs.008.001.08','SettlementConfirmation'),
	 (4050,'20210923CENAIDJA010R0200001458','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200001458","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T05:07:02"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001211","CreDtTm":"2021-09-23T05:07:02"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000051","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000050","OrgnlTxId":"20210923SIHBIDJ101000000051","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Adam Eko"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000050','20210923SIHBIDJ101000000051','pacs.008.001.08','FICreditConfirmation'),
	 (4051,'20210923CENAIDJA010R0200000842','{"BusMsg":{"AppHdr":{"Fr":{"Fiid":{"FinInstnId":{"Othr":{"Id":"CENAIDJA"}}}},"To":{"Fiid":{"FinInstnId":{"Othr":{"Id":"SIHBIDJ1"}}}},"BizMsgIdr":"20210923CENAIDJA010R0200001458","MsgDefIdr":"pacs.002.001.10","BizSvc":"CTRESPONSE","CreDt":"2021-09-23T05:07:02"},"Document":{"FitoFIPmtStsRpt":{"GrpHdr":{"MsgId":"20210923CENAIDJA01000001211","CreDtTm":"2021-09-23T05:07:02"},"OrgnlGrpInfAndSts":[{"OrgnlMsgId":"20210923SIHBIDJ101000000051","OrgnlMsgNmId":"pacs.008.001.08"}],"TxInfAndSts":[{"OrgnlEndToEndId":"20210923SIHBIDJ1010O0100000050","OrgnlTxId":"20210923SIHBIDJ101000000051","TxSts":"ACTC","StsRsnInf":[{"Rsn":{"Prtry":"U000"}}],"OrgnlTxRef":{"Cdtr":{"Pty":{"Nm":"Adam Eko"}}},"SplmtryData":[{"Envlp":{"Cdtr":{"Tp":"01","Id":"KTP-2004384","RsdntSts":"01","TwnNm":"0300"}}}]}]}}}}','20210923SIHBIDJ1010O0100000050','20210923SIHBIDJ101000000051','pacs.008.001.08','SettlementConfirmation');INSERT INTO public.outbound_message (id,bizmsgid,channel,chnl_req_time,chnl_resp_time,cihub_req_time,cihub_resp_time,error_msg,full_request_msg,full_response_msg,intrn_ref_id,message_name,recpt_bank,resp_bizmsgid,resp_status) VALUES
	 (2980,'20210920SIHBIDJ1010O0100000106','Internet Banking','2021-09-20 21:08:35.367156','2021-09-20 09:08:36','2021-09-20 09:08:35','2021-09-20 09:08:36',NULL,'eJyVUttuozAQ/ZWVn9PKppSQvHFJUq/aJCpW96HqA4WEsgsEmUnVbNR/73iAKK0SbdeSwfbczjkz
e+Zvm7smY+M98+r6JtXmNG2/eZ62/0pWDVSSbgt4Iau5sUje+DL8Kdg7rgFTm2/GBZO5h3FeF+fn
fxGDNMWZxS3BRxbvU3PBF7jNEtxhA4ae4WrdOtdx0lxy7uIWl9xFa6BXIXRpLvjowuLKEmPujq+u
GVYKN8m2XFXQ4oPNVAYNlDpIQem1eZzpXgRCdBpQh2bY11PlmYoDNn9erNVbg3aBtwigKGVFleh8
ByTH7f2MGSFaHOqNXB73bFlCq96kStUGP+cQfZYIE/wLORbD5KruwNwmhemVLs1lqUHvMJ4L4xZA
tltudf3VgiZZgfb/EBGvJE0f4mK7Qgd3aF1zjm0xEiUmRIb3lO1FZ75pXTjxFRrDZyC150bCaVyl
+Y9f+e94F6Otpb7Ur3A8Qo/dDHFh29jTJ5ogk8ZLEugn7Mu8DW3HsQmy+sQj8IKAHeIz+I9xp2Z9
I+Z41NuYA92lI+WB5kJnp1gGts2vepZU8TxLIVxndIJl9DCL6DmqixKfwhhiqjCpXgvy7Ltg4vq2
p8dPloH/RDDePwBlJjkG
','eJyNUttuozAQ/ZVqntNqDJSSvBEgW3cbiIhXfVj1IQu5IIFB4PQW9d87NqErtdt0B+HL+HjOmfEc
YLrv5t0WJgfwm+Y6b/Vq1o9Fkfez5LJTkptdonb69JVsBKL+Fgh6B0t+PeXhDYP+3rR4IVKu2cBC
i+HYQrkvS2SYooXaHNuGERAqXG96YLPKugtEi352wRBMmOVDpsNHQtxG8ygWQRLPeDr3BU9iQgTt
OlRHknMcn1soLDZBb2JfAukI62xfraXq1at6xheVWqoubYzrRztUxKj9LFYb82wcmET1NVfSbmVJ
Ebnc+DInEpj8PvTej9GHah0ZaLqC0Ts0rgz4WA7PlAM9eL2n93j6R/RI5qKm4SuKZEgE3YGF4nyv
RzwZGvADEdBWl62jBtgYZlrqui1a1T4T5hfdore/f4+frjf6PPyjWj/L1NAoH9rmynFdB0zLBLky
/oV61lOsK31T71by7K6QeQED5kQ0xjx3fOzAZVNWJC1cqZXRG8mHstHIgUfQDpBRYubuT7GgB6Wu
9BxypV0uVZ+9gYhHaQShrdMc9Wlt1Sktlu1cun9T+1801ZA+sjdaxxRs
','42413','Credit Transfer','014','20210920null010R0200000433','SUCCESS'),
	 (2987,'20210920SIHBIDJ1010O0100000109','Internet Banking','2021-09-20 21:15:37.805791','2021-09-20 09:15:38','2021-09-20 09:15:37','2021-09-20 09:15:38',NULL,'eJyVUl1Pq0AQ/Stmn6vZRfr5xkdb12hLZON9MD4gtMgVKFmmxtr0vzs7lKaaNtdLssDOzJk5Z2a2
zF3X93XKRlvmVNVNos3fpHlnWdJ8S1nWUEq6zeGVvObGQnnjSv9WsB0+HaZWv8R545mDOGePc7NP
5CBNcWZxS/ChxdvUXPA5HvOgnXUYRvqLZRNcRXF9xfkAj7jiA/R6euHDPs0lH15aXFliJLqj6z7D
Sv4qXheLEhp+sJpIr4ZCewkovTTGqW6bQIxOE2rYCN7WU8WZih02e5kv1UeNfoG3ECAvZEmV6P8e
qB13D1NmGtHwUB8U8rRlQQFN98Zlolb4Osfoe4swwb+YYzFMrqo9mbs4N7PShbkEGvQG8VyYMA/S
TbDW1U8PumQJ2n0jIU5BPX2M8vUCAwZ9q8s5jsW0KDYQ6T9QtledumZ0/thV6PRfgLo9My2cRGWS
XfzJ/kabCH2N9EC/w/EKPe13iAvb7rLdM22QSePEMbQb9mPf+navZxNl9U2H53geO+BT+I91p2H9
AnO86g3mIDfoSXmQOdfpKZWebfPrViVVPK9SiEFveEJl+DgNyRxWeYEmP4KIKozL95wi2ykYXDv2
5NhkGfrPRGP3BVebOQA=
','eJyNUstuqzAQ/ZWrWaeVbQhNsiNAWrcNROCuqi5SIGmkYBA4fdyo/96xwa1U9WXEw8OZOWeO5wjz
Q7fstjA7gt80F0Wrvxb9c7cr+rfkslOSm12iHsxfvYMgin0eXvrwimsEov5jXsYv5phHh7z57j9q
4JocGGGUTBmxpQklKWFEL89zYASIDMtND27WeXdKCMObnlICplT2mGuKSIjraBnFIkjiBU+XvuBJ
jIigLUM1EJ2Q6QkjgtEZHc+cM0AtYZ0fqlKqvgNVL/iqUpnq0saEzltrklH8tWCzmDu2bKL6ni9p
t3KPVbnc+LJAIpjdHvvoZwbrmmWgpmMLjSsDHiyZGEvIBF7v8Fyev6geyULU+PiOIhloMG5ZsM7v
esSzoQE/EAFutXUdDsLGMOOn9m7VqvYFMTeYhTNw914/LTf6f3ivWj/PlR2YT+Nz5nqeC2Z0gkKZ
+Eq96FesnfaLXXNQbf0vO3SlrMHifqhI6cSbDtOYNfsK5YVrtTaaI/m4bzTScgncAaHYnMm9Eis8
VOI6ExdDaVdI1TtgIOJJGlHE0a2O+ta26ictzHHH3kd7f0Wjj3jhegMY8xrH
','42413','Credit Transfer','014','20210920CENAIDJA010R0200000663','SUCCESS'),
	 (2994,'20210920SIHBIDJ1010O0100000112','Internet Banking','2021-09-20 21:19:27.595375','2021-09-20 09:19:28','2021-09-20 09:19:27','2021-09-20 09:19:28',NULL,'eJyVUttO4zAQ/ZWVnwuyQ+jtLZe2eAVtRCz2AfEQkjaETdLImSK6Vf+d8SSpCmq1YMmJ7Zkzc87M
7Ji7qe/qlI13zKmqm0Sb07T5ZlnS/EtZ1lBKui3ghazmxkJ540r/t2B7XD2m1t/EeZO5gzinxbnZ
P+QgTXJmcUvwkcW70FzwBW6zhLBYj6Gnv1w1zlUU15ecD3GLSz5Eq6eXPrRhLvjowuLKEmMxGlsD
hpn8dbwpliU0/GA9lV4NhfYSUHplHme6KwIxOk2oZXPV5VPFmYw9Nn9erNR7jXaBtxAgL2RJmeh8
B1SO2/sZM4VoeKh3cnncsaCApnqTMlFr/Jxj9LlEGOB/zDEZBldVS+Y2zk2vdGEugQa9RTwXxs2D
dBtsdPXVgiZZgnb/khCnoJo+RPlmiQ7DgXXNObbFlCg2EOnfU7QXnbqmdf7EVWj0n4GqPTclnEZl
kv36k71G2whtjfRAv8HxCD22M8SFbV+z/RNNkAnjxDF0E/Zl3gZ2v28TZfVJh+d4HjvgU/jBuFOz
voE5HvUGc5Ab9KU8yFzo9JRKz7b5VaeSMp5XKcSwPzqhMnyYhfQcVnmBT34EEWWYlG85eXZdMLiu
7cnxk2XoPxGN/QdWRTkA
','eJyNUl1vozAQ/CunfU4r23CU5I0Aubq9QERc9eHUhxxOckjBIHB7zUX9710b3EpV26sR+IPZndnx
nmB+3y/7PcxOELXtpezMajF8q0oOs+Kq14rbXa7/2L9mB3GaRTy5iuAJxwRE88W4Nb+cYxwd4+bV
P9TADTkwwiiZMuJSE0oKwogZYejBBBCZbHcDuN2U/TkhDF96TgnYVOuH0lCkQvxMl2km4jxb8GIZ
CZ5niIi7baJHojMyPWNEMDqj0xm7ANSSNOV9vVV6qEA3C76q9Vr3RWuPfnTOJKv4fcFm0MCjjk3U
H/Pl3V4dMCtXu0hJJILZr9Nw+pbBuTYyEEqNHw6a1RY8WhJaS0gIT3d4L4/vZE+VFA1+PqLIXSGU
ORbM83894tHSQBSLGLfGuh4bYWeZcWm8W3W6OyLmBqOwB+5e8hfbnfmf/NZdVJbaNcyb9rnwg8AH
2zqx1PZ8pY9myozTkaz0cfPttpKNbMChPslHaRhMx15ct4caxSUbvbGKU/VwaA3SMQncATF3a2Ov
xQqvlPhe6ONR0Uulh/otRPxVVhLxTKGTobC9/kwL8/zvwWtxX0Wji/jgeAaaMBnO
','40003','Credit Transfer','014','20210920CENAIDJA010R0200000883','SUCCESS'),
	 (3381,'20210921SIHBIDJ1000O9900000180','Other','2021-09-21 16:28:35.477735','2021-09-21 04:28:35','2021-09-21 04:28:35','2021-09-21 04:28:35','Timeout terima message dari CI-HUB','eJyNUMFugzAM/RefW2SHwkhupaxrJk1May7TtMNUaIc0AiOpNK3qvy8hcOthPjhxnv3eiy+Qn82T
OYG4wLrvd9Xgb9uQm6YKp5baWC3HqrSfI+or2MtdLotHgquLBajun3MbqV7d3Hqay5tf50F6cWDI
CDmjmRoRS87RB2UIC3CdRX0Mzf3HwUTIssiBEa4cuhnqwk40S+RLRopSwTIRJ+CUiu5wbmttgz/b
beVza/fWvNTf/ulhmFcw+rltZ/JCs5pqQVAaM0ZpQskqIb+JH6mPIN7cv4eT/rrXlepcuk1JvMQ4
sN5lcH33S/kDmmJz9g==
','eJzLK83JAQAEXwG8
','21252','Payment Status','031',NULL,'TIMEOUT'),
	 (3394,'20210921SIHBIDJ1000O9900000188','Other','2021-09-21 16:40:15.009801','2021-09-21 04:40:15','2021-09-21 04:40:15','2021-09-21 04:40:15',NULL,'eJyNUE1PwzAM/S8+b5WdVaXJbV0ZCxIqYrkgxAGt3ahE09JkEmLaf8fpx20HfHDiPPu9F18gO7sn
dwJ1gXXX7co+3LZjrutyPK22zls9VIX/HNBQwV7vMp0/Elw5FmDaf85ttHnlufU0l9W/7EEHcRAo
CKWgmRoRCykxBKUpLIA78+o4NncfBxehSCMGI4wZ3fRV7ieaJcqlIEOJilFRDKyUt4dzU1k/+vPt
Vj83fu/dS/Udnh76eQWDn9t2Ji9yVjMNKEpWQtCdoFimGDbxo+0R1Bv/uz/Zr3tbmpbTbUqSBa4m
1gSu72Epf5+AdAA=
','eJydUl1PwjAU/S/3GUg7BNze9oXUxI1s5cnwoGxDEtctWzEo4b97263GGBBjk3W9t+fec3J6j+Dt
24d2C84R3LpeZI06zbt9t8u6v2CilYLpKJYv+lZF4IeRy4J7F064BsCrP9albOFhHe3rvN0HamCK
HCxiUWJb1LQm1E5sm+Cik8kMBoDIIC86cP20aUeEWPjRESWgW6VvGyWNJ2G6jKM0xKzf5IHsmw+J
PbQop1Pnhjh0DMgfVJt9mQvZqZbVnC1Lmco2qXXqrjHGaJXnRWqFswk1bLy8zBc3W/GKXZkoXJEh
ETiPxy77k8E41TMQeqs8MNCo1ODeBlvbQGwD+E2HBac1PtjhjIRQZLzC7ZKOmIx7KVPDhH2ui+YH
TQOuz30Mlb8tTkihmfGoDF42snlHzAqrcDjWX/2TvFD3wbPUD+Fu5bURW0Xs22j62b8KUYLaPgGL
+OgJ
','21252','Payment Status','031','20210921CENAIDJA019R9900001557','SUCCESS'),
	 (3003,'20210920SIHBIDJ1010O0100000116','Internet Banking','2021-09-20 21:30:53.340816','2021-09-20 09:30:54','2021-09-20 09:30:53','2021-09-20 09:30:54',NULL,'eJyVUttu4jAQ/ZWVn2llhxAub7kA9aqFqLG6D1Uf0gTSdBMTOUNVFvHvHTsJohVoW0tObM+cmXNm
Zk+8bX1XZ2SyJ25V3aRKn2bNN8/T5i+5rEFyc1vCi7HqG4n4jceD34wccPWI2HwT508XLuLcFufl
/5AD18mJRS1GxxbtQlNGl7j1YswhPYKewWrdOFdxUl9TOsLNrukIrb5aBdCGuaLjK4sKi036dDLo
E8wUbJJtuZLQ8IPNjPs1lMpPQai1fpyrrgiG0XlCLZthl0+UFzL2yOJ5uRbvNdoZ3iKAouTSZDLn
OzDluL2fE12Ihod4Ny6PexKW0FRvKlOxwc8lRp9LhAH+xxyTYXBRtWRuk0L3SpX6EipQO8RTpt18
yHbhVlVfLWjiEpT31whxS1PTh7jYrtBhNLQGlGJbdIkSDeHBvYn2ojJPty6YegKNwTOYai90CWex
TPNff/LXeBejrZEeqjc4HaHHdoYos+0BOTyZCdJh3CSBbsK+zNvQdhzbUBafdPiu75MjPoMfjLtp
1jcwp6PeYI5yQ4fzo8ylys6p9G2b9juVJuNllYyNnPEZldHDPDLPUVWU+BTEEJsMU/lWGM+uCxrX
tT09fbI0/SdD4/ABUZE5AA==
','eJyNUttOwzAM/RXk54GctpRtb13bQYC1UxfEA+JhtNuYtGZVG64T/46TLCAhbqmai3Psc+x4B6OH
btKtYLiDqGnOqlbvxnZeryu7Si47Jbk55ere3OoTxGkW8eQ8gjcaPRDbf/rN+NmI/Njeb7R+JQ1c
k4OHHsOBhy40MizQQxpsEA6gB4RMFksLbuZld4To0c+OGIIJNXssNUUqxGU6STMR59mYF5NI8Dwj
RNwuErUnOsTBoYfCY0Mfh8c+kJZkWz7UC6lsBmo75tNazVRXNMZ02roiGcXfC7Zq2YljE/XPfHm7
khuKyuUykhURwfBmZ61fGVzV9gzIDIODZrUB70vSNyXBPrzd0rs8fxM9lZXY0vQTRe4SYaFjoTh/
6xHPhgaiWMR01KXrqBGWhpm2unbTVrUvhLkiL+qB24/4xWKp75M71UZlqVzDfGmfkyAMAzCtE1fK
2KfqRS+ZrnRUzeuD67Wcqzk4zC/RGOtTY9lOnDWbmqQl2lXrTeXjptFIxyPoBMgoMeN7Iab0oBj4
/YBMRVdJZbM3EPEkjSD0dZo9m9ZK/abF84Pj8DO1/6KphvTReAcnjRjs
','4103','Credit Transfer','014','20210920CENAIDJA010R0200001969','SUCCESS'),
	 (3011,'20210920SIHBIDJ1010O0100000119','Internet Banking','2021-09-20 21:38:15.500543','2021-09-20 09:38:16','2021-09-20 09:38:15','2021-09-20 09:38:16',NULL,'eJyVUl1PszAU/ium19O0iJPtjo9t9o1uRBq9MF4gbIgvMFLOjHPxv3t6GMs0W9Qmhbbn63meczbM
WzU3TcaGG+bW9VWqzWncfvM8bf+VrBqoJN1m8ExWc2ORvPJk8E+wD1w9ppa/jPNHUxfj3G2cl78j
BmmKM4tbgg8s3qXmgs9wmyXEgPUYegbzRetcx0lzxrmDW5xxB62+ngewTXPKB6cWV5YYnjtDccGw
UrBMVuW8ghYfLMfSb6DUfgpKL8zjRHciEKLDgFo0Fu/qqfJIxR6bPs0W6q1Bu8BbBFCUsqJKdL4B
kuP6dsKMEC0O9UYuDxsWltCqN6pStcTPMURfJcIEPyHHYphc1Vsw10lheqVLcwk16DXGc2HcfMjW
4UrX3y1okhVo7z8RcUvS9C4uVnN0cC6tC86xLUaixITI4JayPevMM60LRp5CY/AEpPbUSDiOqzQ/
uc9f4nWMtpZ6qF9hf4QetjPEhW1jTx9pgkwaN0mgm7Bv83Zp9/s2QVZfePiu77NdfAZ/GHdq1i9i
9ke9jdnRDftS7mjOdHaIpW/b/LxjSRWPsxTC6Q8OsIzuJhE9R3VR4lMQQ0wVRtVrQZ5dF0xc1/Z0
/8ky8B8JxscnaPg5Bg==
','eJyNUlFvgjAQ/itLn9Vcq9uQNwScbJkS6LKHxQcj6EikEKhOZ/zvux6yLItmK6HtXe++7+MrRzba
1s/1mtlH5pTlJKnMbtzMWZY0qwpUrVVA0Uy/06mJmOtPncB7dNgJR4fJ4p99cTAZYR8/942yT9QQ
GHImQHAYCmihgUMEAnDw+8Ed6zCs9NJVU1wulnUPQODLexwYQcW7pZEmIz8OZ9PYx6xbpZ4+g3dh
2BUgBbf7ls1vGfJ7xXKbp0o3qnUxDsJcx7qOSko9VK0xpPKySFLYF1bLJvPrfLNqrTaIGqiVoxIk
Yvbbscn+ZmidOjMAF+Yr29JpTsVnGyyyASx2muNd7C+g+yqRBU7XKGbth/Bhy4I4f+uRe6Jhjitd
DI11NV7+iphxa7wLK10dsOYFu/De59/4Uboy526iyeNQH8wyNf7F2zpVxc1rppKM0a8Sl5sccbyF
XhC4r3ab8me7xIgBRxWk+0mG6D4M+tYAU1GdKN1IpRL5oYgH+qTpNMcHxxd+tOH+
','41643','Credit Transfer','014','20210920CENAIDJA010R0200001746','SUCCESS'),
	 (3017,'20210920SIHBIDJ1010O0100000122','Internet Banking','2021-09-20 21:41:34.327306','2021-09-20 09:41:34','2021-09-20 09:41:34','2021-09-20 09:41:34',NULL,'eJyVUk1v4jAQ/Ssrn2llh5QCt3wA9aqFqLG6h6qHNIE03SREzlCVRfz3jicJohVoW0tObM+8mfdm
ZsfcTX1Xp2y8Y05V3STanKbNN8uS5l/KsoZS0m0BL2Q1NxbKG1f6vwXb4+oxtf4mzpvMHcQ5Lc7N
/iEHaZIzi1uCjyzeheaCL3CbJSyL9Rh6+stV41xFcX3J+RC3uORDtHp66UMb5oKPLiyuLDG2xbhv
M8zkr+NNsSyh4QfrqfRqKLSXgNIr8zjTXRGI0WlCLZt+l08VZzL22Px5sVLvNdoF3kKAvJAlZaLz
HVA5bu9nzBSi4aHeyeVxx4ICmupNykSt8XOO0ecSYYD/McdkGFxVLZnbODe90oW5BBr0FvFcGDcP
0m2w0dVXC5pkCdr9S0Kcgmr6EOWbJToMr60rzrEtpkSxgUj/nqK96NQ1rfMnrkKj/wxU7bkp4TQq
k+zXn+w12kZoa6QH+g2OR+ixnSEubPuK7Z9ogkwYJ46hm7Av83ZtDwY2UVafdHiO57EDPoUfjDs1
6xuY41FvMAe5wUDKg8yFTk+p9Gyb9zuVlPG8SiGGg9EJleHDLKTnsMoLfPIjiCjDpHzLybPrgsF1
bU+OnyxD/4lo7D8ANm449g==
','eJyNUl1vmzAU/SvTfU4r29CM5I0AWd0tEIH7VPWB8ZGlCg4ybtcu6n/ftcGdVLVdjcAfnHvPucf3
BKv7YTPsYHmCsO8va2VW6/G739fjLLkctOR2l+lf9q/ZQZSkIY+vQnjGMQNx/GRcwS9XGEenuNX+
D2rghhwYYZQsGHGpCSU5YQQH9S8CmAEi46YdwX1ZDeeEMHzpOSVgUxUPlaFIhPiRbJJURFm65vkm
FDxLERGpJtYT0RlZnDEiGF36dOn5gFriY3XfNVKPFejjmm87Xegh7+3RN+VMsorfFjyq9QLHJrr3
+TK1kwfMymUbyhqJYHlzGk9fMzjXJgZCmQezF2jaWfBkSWAtIQE83+K9PL6RPZG1OOLnPYrMFcKY
Y8E8/9cjHi0NhJGIcGusG7ARWsuMS+PdVmn1hJhrjMIeuH3Jnzet+R//1CqsKu0a5lX7fPXncx9s
60S1tudb/WSm1Dhd3JWt2h++YMWqKcHBPkhIaTBfTM1Y9IcO1cWlLq3kRD4ceoN0VAJ3QCjWZmO/
iy3eKcHr9vEoH2qpRwMsRPyWVhPxTKWzsbKd/kgL8/yL+b/qPotGG/HB8Remzxob
','7653','Credit Transfer','014','20210920CENAIDJA010R0200001458','SUCCESS'),
	 (3025,'20210920SIHBIDJ1010O0100000125','Internet Banking','2021-09-20 21:52:03.990904','2021-09-20 09:52:04','2021-09-20 09:52:04','2021-09-20 09:52:04',NULL,'eJyVUk1v4jAQ/SuVz7SyvSEFbvkA6lULUWN1DxWHNIE03SREzlCVIv57x07C0gq0rSUntmfezJs3
syPupr6rUzLaEaeqbhKlT5Pmm2VJ8y9FWUMpzG0Oz8aqbyQUN67wfzOyx9Ujcv1NnDeeOYhzWpyb
vSMHoZMTTjmjQ0670JTROW69GO+THkFPf7lqnKsorq8oHeBmV3SAVk8tfWjDXNLhJaeSs1Gfj+gv
gpn8dbwpliU0/GA9EV4NhfISkGqlH6eqE8EwOk2oZWN3+WRxJmOPzJ7mK/lWo53hLQTIC1GaTOZ8
B0aO2/sp0UI0POSbcXnckaCARr1xmcg1fs4x+iwRBvgfc0yGwWXVkrmNc90rVehLoEBtEU+ZdvMg
3QYbVX21oEmUoNy/phCnMJo+RPlmiQ6Da96nFNuiJYo1RPj3JtqzSl3dOn/sSjT6T2DUnmkJJ1GZ
ZBd/spdoG6GtKT1Qr3A8Qo/tDFFmWX2yX5gJ0mGcOIZuwr7M27Vl25ahLD/V4TmeRw74FH4w7qZZ
38Acj3qDOZQb2EIcypyr9FSVnmXpyV38y3i+SsYG9vBEleHDNDTPYZUX+ORHEJkM4/I1N55dFzSu
a3ty/MQ1/YWhsf8AS4c4/g==
','eJyNUtFugjAU/ZWlz9O0VTflDQGVLQMCXfaw+EAsOBIoBKrTGf99twXMsmi2Etre23vPOZxyQvNd
89JskXFCZlWteK12i3bOMt6uwhWNFK6OfPmhT1WELMczXfvJRGcY94iV/+yL3NUc+kjXN8++QIOr
yBHFlOAZxT00JjjEFKtBRw/oHkGlnaRtcRVvmiEcwEuGBCMNFe03ShoLnSjwvciBrFUntuzAB3g2
oJhRYkyogccI+O1ysysSIVvVsly4QSEj2YSVTi3r3hit8rpINQiljz0bK27z+fVW5IDqitQUHIiQ
8X5qs78Zeqc6BqBQHvSlXqGLOxum2gY8Rec13MXhCrojOCthukXhXz5k0rMAzt962EHTINNiFoTK
ugYuP9XMsFXeBbWsj1DzCl1w7+sLfpik6tziUnscyKNaPOXfW8ZLXt4tszwWW6T/lajKCwCyYxlr
dEfs8+pnP4MIYQIytPBnFoD9eDyajiEVNlzIVqsuYZ9CE+GRFnVewwPjGzn/4jI=
','763453','Credit Transfer','014','20210920CENAIDJA010R0200000236','SUCCESS'),
	 (3260,'20210921SIHBIDJ1019O0300000122','Over the Counter','2021-09-21 11:39:05.950245',NULL,NULL,NULL,'Connection error','eJyVUk1vwjAM/S8+A0qCOIRbPxhkGh+CbBfEYbSFobWhSl0Jhvjvc9JymDS0EampLfv5PT/lAmFd
Tas9DC8QlOUktS56au7DIW3+RpkKjfLZHD981WWwUpNQxc8crnQ6oI//xEWjWUC4oMWFhy/SoBw5
CCY4k4LfRjMu56zP3OFCQAeoM852TXP5nlQ9xiR9vMckVSObxdiO6TLZFVxzPuzLIRsAMcXHpC4y
g42+JEVtdy4e29vuXsjvOloR/RuNLu4QdWC2ne/0qaI6p2yFmBfKeCYfT9G78LIcg9s/8jr0ybes
L7AosDFtZFJ9pOueop/O0IC/lBMZDddlKybC/XlR29LFC4v2THApvSZl0IafXm1QeL/e3vM6c/PF
YMAYWe58SBxExUtv7hbtAw/G7/0YYEnGNMpfK7RUX5s6zzfX68Y9pG9Ce87e
',NULL,'2747','FI Credit Transfer','014',NULL,'ERROR-KM');
INSERT INTO public.outbound_message (id,bizmsgid,channel,chnl_req_time,chnl_resp_time,cihub_req_time,cihub_resp_time,error_msg,full_request_msg,full_response_msg,intrn_ref_id,message_name,recpt_bank,resp_bizmsgid,resp_status) VALUES
	 (3263,'20210921SIHBIDJ1019O0300000124','Over the Counter','2021-09-21 11:50:16.006875','2021-09-21 11:50:17','2021-09-21 11:50:16','2021-09-21 11:50:17',NULL,'eJyVUk1vwjAM/S85A0q6dVK49YNBpvEhyHZBHKAtDK0NVepKMMR/n+OWw6ShjUhNbdnP7/kpZxbW
1bjasf6ZBWU5Sq2Lnpt7v0+bv1GmAqMom8IHVV3GFmoUqvhFsAueDtOHf+KiwSRAXNDiwv0XalCO
nHncE1x64jqaCznlD9wd4T2yDsPOONs2zeU6qXqcS/xEj0usRjaLoR3T5bLrCS1E3+d98cSQKT4k
dZEZaPQlKWi7dfHQXncnIb/raEX4Vxpd3CDqsMlmutXHCusCswVAXihDTBSPgVx4nQ+Z2z8iHfpI
LcszmxXQmDYwqT7gdUvRT2dwwF/KkQyH67IVE8HuNKtt6eKZBXtCuJSkSRmw4SepDQry632d15mb
7/k+52i58yFxEBXPydwN2DseDO19H2COxjTK3yqwWF+aOs9Xl8vKPaRvPMTO3A==
','eJydUlFPgzAQ/i/3PJcec9PyxoC5mjgWqE9mDzpgLpFCoDPTxf/utcAezJapTSi963f3ffl6B5ju
modmA+4BvKqap7U5zdp9u03bvxKq0UrYKNKv9tZE4IcLTwT3HnzRGoAsf1mXiPmU6rCrm24/SYMw
5OAwBxl3sG/NkMecM7M4jmAAhAyyvAVXz+tmyJhDHw6RgW2VvK+NNBmHyTJaJCFl/ToLdNf8ivEr
ByWiO2YuToD4g3K9KzKlW9W6nIlloRPdxJVN3dW9MVblaZFGIE5ub3o2WZzni+qNeqOuQuWeSokI
3KdDm/3J0DvVMTB0xjA4QheFBXc2cGsD4z3ggo4VPdj+hIRQpbKk7ZyOiI06Kdc9E/W5LFruLQ14
vvQpNP42NCG5ZaajMXhZ6/qDMI9URcOxOvaPs9zcBy/aPoS30X8bMT/9VyFJMNs3YufnoA==
','2747','FI Credit Transfer','014','20210921CENAIDJA019R9900000913','SUCCESS'),
	 (3388,'20210921SIHBIDJ1000O9900000184','Other','2021-09-21 16:32:13.891886',NULL,NULL,NULL,NULL,'eJyNUE1vwjAM/S8+Q2UnrGtyoxRGJk2dRi5o2mGihVWiadcEaRrivy/px43DfHDiPPu9F18hvdgX
ewJ5hWXbbosu3DZDrqpiOI0y1hnVV7n76tFQwU5tU5U9E9x8zEA3/5xbKb33c8txLq1+vQcVxIEh
IxSMJmpEzIXAEJQsYAa+MyuPQ3P7ebARsiTyYIQBXXVl5kaaOYo5I02x5EwSB6+UNYdLXRo3+HPN
Rr3WbufsW/kdnp66aQW9n/t2Ri8Pk5quQVLMGaP4kfMkWYRN/ChzBPnu/92dzHltCt34dJ+SRI58
ZGVw+whL+QOcgHP8
',NULL,'21252','Payment Status','031',NULL,NULL),
	 (3401,'20210921SIHBIDJ1000O9900000192','Other','2021-09-21 16:59:20.589271','2021-09-21 04:59:20','2021-09-21 04:59:20','2021-09-21 04:59:20',NULL,'eJyNUE1vwjAM/S8+Q+W4oyK5UTpGJk2dRi4T2mGiBSrRtGuCNA3tv8/px43DfHDiPPu9F98gvboX
dwJ1g1Xbbosu3DZDrqpiOK22zlvdV7k/92ioYKe3qc6eBfxyzMA0/5xba/POc6txLq1+2IMO4kBI
AiWJiRoRcykxhJAEM+DOrDwOze3nwUVIy4jBCB8YXXdl5keaOco5CSMStZCKEFgpaw7XurR+8Oeb
jX6t/c67t/IrPD110wp6P/ftjF7iSc3UoEQSE4llnOAiobCJb22PoPb87+5kL4+2MA2n+5RC5hiP
rOzyIyzlD52Zc/c=
','eJydUlFvgjAQ/i/3rKaF6VbeEHB2ycAAPi0+bALOZBQCdXEz/vddCzXLonNZE0rv+t19X77eAaa7
9rHdgHMAt67nWaNOs27fbrPuL7hopeA6iuSrvlUReEHocv/BhSOuAaTVH+sSPp9iHe3rpttP1MAV
OVjEooRZ1LQmlMWMEbUmtg0DQKSfFx24fl63I0Is/OiIEtCtkve1kpbGQbKIwiTArNfkvuybDwkb
WjSlE2fMHHoHyO9X612ZC9mpltWML0qZyDaudeq+McZoledFaoU344lhS8vLfFGzEW/YlYvCFRkS
gfN06LI/GYxThoEyCoMTNCw1uLeBaRsIM4DfdNzCcYUPtj8jIRBZWuF2SUdE7F4KMUzY57rodK9p
wPVSD0Plb4sTUmhmPCqDF41sPhCzxCocjtWpf5wX6t5/kfoh3I28NmLLkH8bTS/7VyFKUNsXu1zo
Ig==
','21252','Payment Status','031','20210921CENAIDJA019R9900000633','SUCCESS'),
	 (3577,'20210921SIHBIDJ1000O9900000272','Other','2021-09-21 21:28:56.153028','2021-09-21 09:28:56','2021-09-21 09:28:56','2021-09-21 09:28:56','Timeout terima message dari CI-HUB','eJyNUE1vwjAM/S8+Q2U7paO50RVGJqFO0Nu0w0QLVFrTrgnSNMR/X9KPG4f54MSx/d7Lu0FyNTtz
BnmDVdtui87fNkOuqmI4tdLGatVXmb30XV/BQW0Tlb4S3F3MIG/+uZfs9srtrca9pPp1GpQnB0Ym
jJkmaETM4hh98BPDDNxkWp6G4fbzaALkZYBIAYau+9yVqR1h5hjPmXImyUu5iMAxpc3xWpfaDvps
s1FvtT1Ysy+//dNLN1nQ63ksZ9QiJra8BkmRYBbhQkQUknfiR+kTyHf37+6sv9a6yBuXHkNSnKEY
URHuH96UP4ruc+I=
','eJzLK83JAQAEXwG8
','122642','Payment Status','008',NULL,'TIMEOUT'),
	 (3723,'20210922SIHBIDJ1000O9900000052','Other','2021-09-22 10:06:43.710866','2021-09-22 10:06:43','2021-09-22 10:06:43','2021-09-22 10:06:43',NULL,'eJyNUE1vwjAM/S8+Q+W4hdHcWgojSFun0RviMNHCKq1paYI0DfHf5/TjxgEfHH+95xffIL6aN3MG
eYOoaTZ566J178sy71+ttLFadVlqv7uuy2CnNrFKtgLubBPI6idxy9V7xLhowMXlH2tQbjkQksCQ
aKRGxDQMsbMZwQR4MilO/XDzdTQe0sJDFB4G3F22RWIHmimGU6JMoMS5DHzgTUl9vFaFtr0+W6/V
R2V31nwWF1d6bccTdHoeyxm0+OO2rAIp5j7Rgsv+CwbuEr9Kn0Du+d/tWf+sdJ7V7B5TCkxxIJ4h
3A/uKP9pXXOt
','eJyNUttugkAQ/ZVmn9UMeInyhoCVNkUC2/Sh8YGIWBpZCKxWa/z3zg5u0zSadgh7mZ055+Tsnth0
1zw1G2admF1V87RWq1k75nnazsIXjRQ+7RbyjU7VjjleYPvug83OGB3Gy3/2xf58in3GpW+af6IG
X5EzE0wDJqapocGACEzAMAZ9g3UYVrrrrC2uklXTAzDxN3oGMIKK9ysljUdeHC6C2MOsU69deQHv
wqRrmhz6FoysAUroMLdc7Yq1kK1qWc78sJCxbKKKUve1NoZUXhdJMTImmo0Xt/kW9UZsEdUXmS1S
JGLW66nN/mbQTmkGGCoPdGlQUPHFhjHZAGN2XuJdHK6geyLlJQ63KBaaZgiaBXH+1sMPRMNshzu4
VdY1ePkZMeNSeRfWsj5izTM24b0vv/GjdabOnVSSx6E8qilQ/r3kaZmWd/F7ktX5ltFjiattgUhu
IhOC98R+W/0E4LhjoGSR8kcedvH9DPrjAaaiJhWyFUsl/EMQE/RJ1XmJH8YXP3Pisg==
','3225','Payment Status','014','20210922CENAIDJA010R0200001431','SUCCESS'),
	 (3732,'20210922SIHBIDJ1000O9900000057','Other','2021-09-22 10:16:32.625271','2021-09-22 10:16:32','2021-09-22 10:16:32','2021-09-22 10:16:32',NULL,'eJyNUEtvwjAM/i8+Q+UYypreWgojSKzT6G3aYaIFKq1paYI0gfjvc/q4cZgPThz7e8R3iK9mZ04Q
3iFqmk3eutu6z2WZ96dW2lituiq1567rKtirTaySrYAHxwSy+p+45eotYlw04OLyxh6UEwdCEiiJ
RmpETKXELvwXmABPJsWxH26+D8ZDCjxE4eGcu8u2SOxAM0U5JcoEhmIRzghYKakP16rQtvdn67V6
r+zemo/i4p5e23EFnZ/ndgYvwaiWVcACM6IAfUkLId0mfpU+QvjJ/25P+mel86zm9JxSYIoDse/D
48st5Q90VXPN
','eJyNUk1vgkAQ/SvNnNUs6zc3BKzYVAlsD03jwQioiSwEVqs1/vfODmKaRtMOYZfZnXnv5Q1nGO3L
13IN5hmsPJ9Ehf4aV+t2G1W79GSppEfZXG3oVmdguzPLc6YWXDAaILJ/9oXeZIR9xrVvtP1CDZ4m
B864wYac19DMYAHjDMPg/T40ACudOKmK8+WqbDHG8TVaBgOCCg8rLU0EbujPZ6GLp3YRO+oK3mTD
JueCtU2jZ7YZIL+TrfZpLFWlWmVjz09VqMogp6PnojaGVN4XSdFpd2o2kT7mmxdruUNUTyaWjJAI
zI9zdfqboXaqZmDdHjRupbOUiq82DMgGNoDLAmdxvIPuykhkuDyimNc03W7Ngjh/6xFHogHLFjam
2roSh58QM35q7/xCFSesecMmnPvihh/Eib63I0Ue++qkt5n2b5ptlvLpPVtvgX6UMN+liOIs1ZKg
XXnY5T+bBWbADNRAql+E3+R6LAM9l6CMpKqEUon4lMTC2qTossAH4xua+eEZ
','32115','Payment Status','014','20210922CENAIDJA010R0200001277','SUCCESS'),
	 (3741,'20210922SIHBIDJ1000O9900000062','Other','2021-09-22 10:27:25.325973','2021-09-22 10:27:25','2021-09-22 10:27:25','2021-09-22 10:27:25',NULL,'eJyNUMuOwjAM/BefoXJcKCS3lvLISktXS2+rPSBaoBJNu02Q0CL+naSPGwd8cOLYnpnMHaKr/tQn
EHcI63qTNe626nJRZN2ppNJGybZKzLntugp2chPJ+IPBw8YI0urNvcVyG9q9sN+Lin+rQTpyICSG
nGiARsSEc2wjIBiBnYzzYzdc7w/aQ5p7iMzDie0umjw2PcwY+ZgoZShoJmgKlimuDtcyV6bTZ6qV
/CrNzujv/M89rZvBglbPazm9Fn9gS0sQLPCJ5owmU59x58RNqiOIH/vv5qQuS5WllU2vIRkm2AMH
CI9fZ8oTb9lzvg==
','eJyNUlFvgjAQ/ivLPas5inHqGwJOXKYEuqfFByPqzKQQqE5n/O+7HmKWRbOV0Paud9/35WtPMNiV
L+Ua+idw8nyUFGY3rObNJqlWFahSq4CjqX7nUxOB60+cwBs7cKbRAJn9sy8ORgPqsy59g80XaQgM
OQgUFvaEqKHRwggF0rBstKEBVOktV1VxPl+ULURBv9WyEBgq3i+MNBn5cTidxD5l3WLp6Qt4E3tN
ISTaffHYFzYQv5ctdulS6Uq1zoZBmOpYl1HOqaeiNoZV3hbJCjsCazaZ3uebFmu1JdRArRyVEBH0
305V9jdD7dSFAbFjQeNaOkm5+GJDl23ALpxndBeHG+i+SmRG0z2KaU3TwZqFcP7WIw9MA44rXQqN
dSVd/oqZaWu8CwtdHKnmlZro3mdX/Gi5MuduotnjUB/NMjH+jebJ5mGcfWTA7yTOtymBeHM9Z2Rf
7bf5z15JEaBRxKKfZdikp9O2u21KRWWidKWTS+SnYhK0WdB5Rh+Nb4pu4Io=
','33305','Payment Status','014','20210922CENAIDJA010R0200001303','SUCCESS');INSERT INTO public.payment_status (id,bizmsgid,error_msg,intern_ref_id,orgn_endtoendid,request_dt,request_full_message,response_dt,response_full_message,retry_count,saf,status) VALUES
	 (3908,'20210922SIHBIDJ1000O9900000192',NULL,'2456256','20210922SIHBIDJ1010O0100000100','2021-09-22 10:37:06','eJyNUMFugzAM/RefW+Q4LVtygzHWTJqYRm7TDlOhLdIIDFJpWtV/X0Lg1sN8cOLYfu/lXSA9jy/j
EeQFkr7fVYO/5SE3TRVOo8xojZqqwp6mrq+gVLtUZc8Mri5WoLt/7uVJqd1eMu+lza/ToDw5EBJD
QbRAI2IhBPpggmAFbjKrD2G4/9yPEdJ95JoRblz3YagzO8OsUayJNJHkdxJjcExZtz+3tbFBn+1y
9dra0o5v9bd/ehoWCyY9t+XMWvjCpluQLObEaYsUb4l7J36UOYB8d/8ejubr0VS6c+k2JMMCA7DL
cP3wpvwBjs5z1A==
','2021-09-22 10:37:06','eJyNUtFugjAU/ZXlPqsplTD1DQEnLgMC3dPiAxFQEygEqtMZ/323BcyyaLYS2t7be885Oe0F5ofm
rdnC7AJmVS2TWu4W7bzfJ+3KXd4I7qrIFzt1KiOwHM907ZUJVxwDYOU/+yJ3Occ+reub779QgyvJ
gRKqkSmlPTTRSEgokUM3DBgAVtpp1hZX8aYZEULx10YaAQUVHTdSGgudKPC9yMGsVae26MCHZDqk
lBFjpmszzQDkt8vNoUi5aFWLcuEGhYhEE1Yq9VL3xiiV90W2CrXnno0Vj/n8estzRHV5ZvIEiWD2
cWmzvxl6p3oG3MDgVuoVqrizYaJsIBO4rvEuTnfQHZ6wEqdHFH5Hg3PPgjh/62EnRQOmxSwMpXUN
Xn6mmHErvQtqUZ+x5h278N7XN/wwzeS5lQjlcSDOcvGkf9E5LppD/rQqdzEH9VaiKi8QyI5FrNAd
fsyrn/0MI1CqlPBXFgzx+ejjiY6psEm4aLW2wj+5IiJjJeq6xg/HN2dN4l4=
',0,NULL,'SUCCESS'),
	 (3910,'20210922SIHBIDJ1000O9900000194','Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','2456256','20210922SIHBIDJ1010O0100000100','2021-09-22 10:38:19','eJyNUMFugzAM/RefW+SYdmtyg1HWTJqYRm7VDlOhHVIJjKTStKr/voTArYf54MSx/d7Lu0J6Ma/m
BOIKSd/vqsHf8pCbpgqnltpYLceqsF9j11dQyl0qsxcGNxcLUN0/9/KkVG4vmfbS5tdpkJ4cCIkh
J5qhEbHgHH0wvoIFuMmsPobh/vNgIqRN5JoR+u7TUGd2glkiXxIpIhFvBOPgmLLucGlrbYM+2+Xy
rbWlNe/1t396HmYLRj335Uxa1jObakGwh5hiWiPnfPXonfiR+ghi7/49nPR5qyvVuXQfkmGBAdhl
uH14U/4Amltz8Q==
','2021-09-22 10:38:19',NULL,0,NULL,'ERROR-CIHUB'),
	 (3913,'20210922SIHBIDJ1000O9900000196','Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','241256','20210922SIHBIDJ1010O0100000100','2021-09-22 10:51:51','eJyNUMFugzAM/RefW+SYtWu4wShrJk1Mg1u1w1RoizQCI6k0req/zyFw62FWZMd59vOLr5BczKs5
QXSFuO931eBumfdNU/molTZWqzHL7XlEXQaF2iUqfRFwY1tA2f2zL4uLkvviqS9pflmDcsOBkARK
opkaEXMp0ZmQa1gAV6b10Rf3nwcTIG0CBgN8YPRpqFM70SxRLolKomgl+ABPSrvDpa219fpsl6m3
1hbWvNff7ul5mFcw6rkvZ9LyOE8rW4jEOqSQVlIIGW7cJn6UPkK0538PJ/211VXZsbtPKTBHT8we
bh9uKX+U1XPl
','2021-09-22 10:51:52',NULL,0,NULL,'ERROR-CIHUB'),
	 (3932,'20210922SIHBIDJ1000O9900000205',NULL,'241256','20210922SIHBIDJ1010O0100000100','2021-09-22 11:07:53','eJyNUE1vgzAM/S8+t8g4Kx+5wRhrJk1MI7dph6nQDmkERlJpWtX/voTArYf54MSx/d7Lu0B+1s/6
BPwC2Tjum8ndSp+7rvGnEkobJeaqMp9z11VQi30uiqcQrjY2IId/7pVZLe1etuzl3a/VIBw5EFKI
KdEKjYhVmqILwh1swE4W7dEPjx8HHSAlAWIY4J3t3k9tYRaYLaZbIkmMY8x3DCxTMRzOfauM12eG
Urz0pjb6tf12T4/TasGs57acRUu0sskeeBgxYhQlMYuTyDnxI9QR+Jv993RSXw+qkYNNtyFDrNAD
2wzXd2fKH46Ic+A=
','2021-09-22 11:07:54','eJyNUtFugjAU/ZXlPqsplTD1DQEnLgMC3dPiAxFQEygEqtMZ/323BcyyaLYS2t7be885Oe0F5ofm
rdnC7AJmVS2TWu4W7bzfJ+3KXd4I7qrIFzt1KiOwHM907ZUJVxwDYOU/+yJ3Occ+reub779QgyvJ
gRKqkSmlPTTRSEgokUM3DBgAVtpp1hZX8aYZEULx10YaAQUVHTdSGgudKPC9yMGsVae26MCHZDqk
lBFjpmszzQDkt8vNoUi5aFWLcuEGhYhEE1Yq9VL3xiiV90W2CrXnno0Vj/n8estzRHV5ZvIEiWD2
cWmzvxl6p3oG3MDgVuoVqrizYaJsIBO4rvEuTnfQHZ6wEqdHFH5Hg3PPgjh/62EnRQOmxSwMpXUN
Xn6mmHErvQtqUZ+x5h278N7XN/wwzeS5lQjlcSDOcvGkf9E5LppD/rQqdzEH9VaiKi8QyI5FrNAd
fsyrn/0MI1CqlPBXFgzx+ejjiY6psEm4aLW2wj+5IiJjJeq6xg/HN2dN4l4=
',0,NULL,'SUCCESS'),
	 (3934,'20210922SIHBIDJ1000O9900000207',NULL,'241256','20210922SIHBIDJ1010O0100000100','2021-09-22 11:15:20','eJyNUMFugzAM/RefW+SYrZTcYIw2lSamkVu1w1RohzQCJalUreq/LyFw62E+OHFsv/fybpBe9Js+
Ab9B0vfbanC33OemqfyphNJGibEqzPfYdRWUYpuKbMfgbmMBsvvnXp6U0u4l017a/FoNwpEDITGM
iWZoRCziGF0QRrAAO5nVRz/cfx10gLQOEFmAT7b7MtSZmWCWGC+JJIWcPXNCsExZd7i0tTJen+ly
8d6a0uiP+uyeNsNswajnsZxJy3pmky1wtgoppCgkZNHKOXEV6gh8b/89nNTPq6pkZ9NjSIYFemCb
4f7pTPkDhvVzyg==
','2021-09-22 11:15:20','eJyNUtFugjAU/ZXlPqsplTD1DQEnLgMC3dPiAxFQEygEqtMZ/323BcyyaLYS2t7be885Oe0F5ofm
rdnC7AJmVS2TWu4W7bzfJ+3KXd4I7qrIFzt1KiOwHM907ZUJVxwDYOU/+yJ3Occ+reub779QgyvJ
gRKqkSmlPTTRSEgokUM3DBgAVtpp1hZX8aYZEULx10YaAQUVHTdSGgudKPC9yMGsVae26MCHZDqk
lBFjpmszzQDkt8vNoUi5aFWLcuEGhYhEE1Yq9VL3xiiV90W2CrXnno0Vj/n8estzRHV5ZvIEiWD2
cWmzvxl6p3oG3MDgVuoVqrizYaJsIBO4rvEuTnfQHZ6wEqdHFH5Hg3PPgjh/62EnRQOmxSwMpXUN
Xn6mmHErvQtqUZ+x5h278N7XN/wwzeS5lQjlcSDOcvGkf9E5LppD/rQqdzEH9VaiKi8QyI5FrNAd
fsyrn/0MI1CqlPBXFgzx+ejjiY6psEm4aLW2wj+5IiJjJeq6xg/HN2dN4l4=
',0,NULL,'SUCCESS');INSERT INTO public.proxy_message (id,account_name,account_number,account_type,call_status,customer_id,customer_type,display_name,error_message,full_request_mesg,full_response_mesg,intrn_ref_id,operation_type,proxy_type,proxy_value,request_dt,resident_status,resp_status,response_dt,scnd_id_type,scnd_value,town_name) VALUES
	 (3869,'Fras Manse','14556','SVGS','ERROR-CIHUB','220000022','01','Andi aja','Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','eJyVkt1PwjAQwP8V02cgbWEaeHIwPmYCGLbgg/Gh2caY2crSFhWI/7t3LTNqNJFLtrb3+btrT2S4
13Odk8GJ+HU9SxXuJu5fFKlbZSi1kaE9Lc3WWvFEonA2DIM7Rt5BWiTe/TMuXARLiPPPccPiCAwh
Fiecckb7nDepbxhdUkZRmNcjLQKeQbZxzrV6O3TA4D4G1pHKAnNO06b9Nucx6w963QEHyBYJdsm+
yqRBknsIXmW5xP1UNb1bkN85zhBeUyau/ihkISPpEvq5uWQaANlA4RrX4LAYP6wgKQKj3uooB81a
lLDdKCF1pxLHrVC3eSWKspPsKmz3a4uBrssFEvsyLa7Es4D4y9jAP0lMY/rhyHqed03sK6jddI06
4BNZTyNEsaUnSuirOdBmqIoSmbpMriP22VEPpAtCztOIjEbwUby2FaK6rCB7IIwgg8cTGcuX0hYd
aVOp7wndZXJ7dxxnttKpNC6hdYhfpWWjXUqxyye8hA801OXd
',NULL,'51200','NEWR','02','frans.mazhar@gmail.com','2021-09-22 07:43:21','01',NULL,'2021-09-22 07:43:22','01','44443333','0300'),
	 (3871,'Fras Manse','14556','SVGS','SUCCESS','220000022','01','Andi aja',NULL,'eJyVUm1PwjAQ/iukn4G0ZUjgk8B4mQlg2IIfjB+abYyZrSxtUYH4371rmVGjiVyy9Xovzz131zMZ
HfRCZ2RwJsOqmicKtan753niThlIbWRgbyuzs168kTCYjwL/jpF3kCaJ9v/MC5b+CvKGl7xRfgIO
ARYnnHJG+5zX0D1GV5RRFNa9IU0CkX66dcGVeju2weE+Bt6xSn1zgWnRfovziPUHnjegfQKV/H18
KFNpkMk9JK/TTKI+U3XvlsjvPC4kenWZqPyjkCUZSgc4zMw10wCSNSk8owoClpOHNYAiYbRbG+Vg
2YgC1K0SUrdLcdoJdZuVIi/a8b7Edr+26OuqWCLjoUzyhngWkH8dN4iPY1O7fgQyrwvrsa+gctM1
6ohPZDMLkYotPVVCNxbANkVTGMvEIbmO2GdHHkgHhFymERqNxMfRxlYIq6IEdF8YQQaPZzKRL4Ut
OtamVN8B3TK53R3Hma11Io0DtAHRq7TcaIdS7PIJl/ABWQfl7w==
','eJyNklFvgjAQx79Ln9VcC7qVNxQ3MRkaZNnDsgeiyEykNrRuc8bvvusBi3tYtpJCr3f3v/tdOLPx
0TyYkgVnFmo929TudNe8d7tN81WxMlbFZC3sK3mdxSbTJIyjecguuHosO/wzbxXPxpjH27zx7hN7
iF1xJkBwkEJ00jccUuDglgSP9RhGRsW2Cdb52gwABG4+4IDeSV1EtpXpg+wLkXER+H4AkmGl6LA+
VoWyrpNl/XFKi1KlRitn39cdP4lkFQv4yBMe90e+lN6QSqdrTclhaX9lvDRUi7pUexSN1ZacziTM
K8puEEi5aCn5cMR639FJRfEaeyVI2rwL+NmoJ299iQpY+5oLOd1xZQ0KpfNJhtlopEa1nS1rW5/Q
94jirGvcKWQab5PpU4oZ3bRY8PwnPerrfYWiUW5zSpiqt72m0Rpb0YxJm0gIcOgBzkQICdIhuPvs
XSWVC/IA3I/ygo9bXywPzcA=
','5500','NEWR','02','frans.mazhar@gmail.com','2021-09-22 07:44:09','01','RJCT','2021-09-22 07:44:10','01','44443333','0300'),
	 (3875,'James Brown','2040606090112','CACC','SUCCESS','1020304050607080','01','Mr. James Brown',NULL,'eJyVkE1vgzAMhv+Lzy1KwtQCp8HQ1kzdqAqbdkUtpWgQUEintRX/fTYf2mWHzlI+bOe1n/gKwal9
aXPwruA3zWqv6fY47EWxH04lVWuU7L3IHPsseRDLVSDDZw4d2gyS+kadfA0j1PmjLiguyCCpOQgm
OHOFmEovOIsYZ2R8IWAG+DLMDsPjRn+fLcZsXNxiHLMPOgvNWGbO3LkQiWCecL07B7BTWO9OVaYM
kWxQvK7rz7eGvCc9/b5H+ZtkxLCnRkkFHrrC5kvbcWx32ePFaijk5+Y/80O8XxyCi1R5pvsaYwlG
YfOxjbH1DXQk3xr9VZK+1zKa3XuKATjoVLVWlV6Oqb7Pq7QorV1dDQxd9wMrJZqf
','eJyVU9tymzAU/BWPnhPPEWAwfiqXpiGN7Qwmfen0gdrEdQNCFXJunvx7zxHGIW7SafEFJO1q96wO
OxZum2mzZpMdC6Q8Xyl6Omv/N5tVexeJaLRIzGiuf5hVGrGzYJEl8UXAnvE6YVn9j7xkFs97vHDz
hB4SEmcWWBxs4B0ky7LzNOSW7Yxcb8xOGCLj4qYFS/XwOARw8MeHwHE1UkWs99ucgn0KPLNgAvRl
qBTXy21VCE1OrpB8Wde31zJtpKCZT6pLwNjpuekK9Ti8uPE7vaxiE+7alue5DtBlXKZLaXSCtf4z
jhlS2FUtt2WuBmEubnGvVxGFwewzVmUSQuNztRYl+kvEjUmThscmu8jcvkmKrEPPKoPfp2a/pGYA
r0txfe6PsRTUvuxFZJDvqR5LUsKpVnclMTOJpOkiThbxDAFfcpxl4FvO2BvZ/thiz33Wr0arAwus
A+FnXhXN8Luq78WH4iGvZFkMl3V1IMeNLE22UzUcXBB4EBK4sxQsl5r2pOPX6hGBURBFRE+Lteiq
RAv0uNANAoIoi5COg7QR+/w78jW0fUWe/9Mu6RGF7iZQDM5vI0T6X+p4s5/efb0QjyV3S0dACxxw
8YO6HA+AXuE3szFO+i7aplzIskJgnOucTb7u2EdxVxp+1Oiqd3zUYkaPg4Ut48AINT0YA86nzUro
NmiDy+6FUUMckMw3kvoNOAFedQ==
','1B41','PXRS','02','frans.mazhar@gmail.com','2021-09-22 08:29:48','01','ACTC','2021-09-22 08:29:48',NULL,NULL,'0300'),
	 (3878,NULL,NULL,NULL,'ERROR-CIHUB',NULL,NULL,NULL,'Connect to localhost:9006 [localhost/127.0.0.1] failed: Connection refused','eJyVkF1PgzAUhv/Lud7IaRsh48oh0dVMWQYab8nGGBEKKZ1xW/jvnvIRb7yYTdqer/ecp71CcGpf
2hz8KyybZrXX1noczqLYD7eSqjVK9l5kjn3WehDLVSDDZwYdrRkk9Y06+RpGpFuOuqC4EIO0w4Ej
Z7jgfGrtMoyQoV3MdWEGVBlmh6G40d9nB1HQZg4yyj7oLDRjmzku5pwnHH3BfSaAJoX17lRlyliS
DYnXdf351ljvSU+v71H+JhkxvGlQUoHPXMEF8+6E8ATv8WI1NFrm5j//R3i/OBYuUuXZ2muKJRSF
zcc2ptE30Fn51uiv0up7LXIKv6cUgINOVetU6eWY6vu8SovS2dXVwNB1PxlOmo4=
',NULL,'1B41','PXRS','02','frans.mazhar@gmail.com','2021-09-22 08:32:13',NULL,NULL,'2021-09-22 08:32:13',NULL,NULL,NULL),
	 (3880,'James Brown','2040606090112','CACC','SUCCESS','1020304050607080','01','Mr. James Brown',NULL,'eJyVkF1PgzAUhv/Lud7IaVEQrhwSXc2UZaDxlmyMEaGQ0hm3hf/uKR/xxovZpO35es952gsEx/al
zcG/wKJpljtlrMfhLIrdcEshWy1F70X60GeNB7FYBiJ8ZtDRmkFSX6kTr2FEusWoC4ozMQgzHDhy
hh7nU2uHYYQMzWLOHcyAKsNsPxQ36vtkIdq0mYWMsg8qC/XYZo7enPOEo2/f+LYLNCmst8cqk9qQ
rEm8quvPt8Z4T2p6fY/yN8mI4U2Dkgp85tjcZq7jureu1+PFcmi0yPV//o/wfnEMXCTLk7FXFEso
CuuPTUyjr6Az8o1WX6XR91rkFH5PKQB7lcrWqtLzIVX3eZUWpbWtq4Gh634ANX+arg==
','eJyVU9tymzAU/BWPnhPPEWAwfiqXpiGN7Qwmfen0gdrEdQNCFXJunvx7zxHGIW7SafEFJO1q96wO
OxZum2mzZpMdC6Q8Xyl6Omv/N5tVexeJaLRIzGiuf5hVGrGzYJEl8UXAnvE6YVn9j7xkFs97vHDz
hB4SEmcWWBxs4B0ky7LzNOSW7Yxcb8xOGCLj4qYFS/XwOARw8MeHwHE1UkWs99ucgn0KPLNgAvRl
qBTXy21VCE1OrpB8Wde31zJtpKCZT6pLwNjpuekK9Ti8uPE7vaxiE+7alue5DtBlXKZLaXSCtf4z
jhlS2FUtt2WuBmEubnGvVxGFwewzVmUSQuNztRYl+kvEjUmThscmu8jcvkmKrEPPKoPfp2a/pGYA
r0txfe6PsRTUvuxFZJDvqR5LUsKpVnclMTOJpOkiThbxDAFfcpxl4FvO2BvZ/thiz33Wr0arAwus
A+FnXhXN8Luq78WH4iGvZFkMl3V1IMeNLE22UzUcXBB4EBK4sxQsl5r2pOPX6hGBURBFRE+Lteiq
RAv0uNANAoIoi5COg7QR+/w78jW0fUWe/9Mu6RGF7iZQDM5vI0T6X+p4s5/efb0QjyV3S0dACxxw
8YO6HA+AXuE3szFO+i7aplzIskJgnOucTb7u2EdxVxp+1Oiqd3zUYkaPg4Ut48AINT0YA86nzUro
NmiDy+6FUUMckMw3kvoNOAFedQ==
','1B41','PXRS','02','frans.mazhar@gmail.com','2021-09-22 08:34:37','01','ACTC','2021-09-22 08:34:38',NULL,NULL,'0300'),
	 (3882,'James Brown','2040606090112','CACC','SUCCESS','1020304050607080','01','Mr. James Brown',NULL,'eJyVkN9vgjAQx/+Xe1ZyLcqEJ2Fks4sbRtiyV6KIZFBIqcvU+L/vCri97ME1aXu/vnef9gzBoX1u
c/DO4DfNYquM9dCfRbHtbylkq6XovEjvu6zxIBaLQIRPDC60RpDUN+rESxiRzh90QXEiBmGGA0fO
0OX82tphGCFDs9gdwgioMsx2fXGjvo4Wok2bWcgoe6+yUA9txuiOOU84ehPuTaZAk8J6c6gyqQ3J
isTLuv54bYz3qK6v71D+JhkwfgYlFXjMsbnNZsyZ4szt8GLZN/Jz/Z//I7xfHAMXyfJo7CXFEorC
6n0d0+gb6Ix8rdVnafSdFjmF31IKwE6lsrWq9LRP1Tyv0qK0NnXVM1wu3xkfmow=
','eJyVU9tymzAU/BWPnhPPEWAwfiqXpiGN7Qwmfen0gdrEdQNCFXJunvx7zxHGIW7SafEFJO1q96wO
OxZum2mzZpMdC6Q8Xyl6Omv/N5tVexeJaLRIzGiuf5hVGrGzYJEl8UXAnvE6YVn9j7xkFs97vHDz
hB4SEmcWWBxs4B0ky7LzNOSW7Yxcb8xOGCLj4qYFS/XwOARw8MeHwHE1UkWs99ucgn0KPLNgAvRl
qBTXy21VCE1OrpB8Wde31zJtpKCZT6pLwNjpuekK9Ti8uPE7vaxiE+7alue5DtBlXKZLaXSCtf4z
jhlS2FUtt2WuBmEubnGvVxGFwewzVmUSQuNztRYl+kvEjUmThscmu8jcvkmKrEPPKoPfp2a/pGYA
r0txfe6PsRTUvuxFZJDvqR5LUsKpVnclMTOJpOkiThbxDAFfcpxl4FvO2BvZ/thiz33Wr0arAwus
A+FnXhXN8Luq78WH4iGvZFkMl3V1IMeNLE22UzUcXBB4EBK4sxQsl5r2pOPX6hGBURBFRE+Lteiq
RAv0uNANAoIoi5COg7QR+/w78jW0fUWe/9Mu6RGF7iZQDM5vI0T6X+p4s5/efb0QjyV3S0dACxxw
8YO6HA+AXuE3szFO+i7aplzIskJgnOucTb7u2EdxVxp+1Oiqd3zUYkaPg4Ut48AINT0YA86nzUro
NmiDy+6FUUMckMw3kvoNOAFedQ==
','11441','PXRS','02','frans.mazhar@gmail.com','2021-09-22 08:42:45','01','ACTC','2021-09-22 08:42:45',NULL,NULL,'0300'),
	 (3884,'Fras Manse','14556','SVGS','SUCCESS','220000022','01','Andi aja',NULL,'eJyVkm1PwjAQx78K6WsgXQcSeOVgPMwEMGzBF8YXzTbGzFaWtqhA/O7etZtRo4k02fpwd//73bUX
Mj6qpcrI6EK8qlokElcz+8/zxM4iEEqLwOzWem+suCNhsBgH/p1D3mG0SXT4Z1yw8tcQ59Vx4/wM
DAEmJ4wyhw4Za6QHDl1Th+JwBoy0CXj66c46V/Lt1AWD/RywTmTq61qmQ4cdxiJGRz135N4QyOQf
4mOZCo0k9xC8STOB67lsajcgv3PUEG6TJir/SGQgQ2EFvUxf0w2AbKBwjipwWE0fNiCKwHhuzij2
YssLWO4kF6pb8vOey9us5HnRjQ8llvu1RF9VxQqJPZHkLf7MIf46NvCPY92Yfjg6vX4fWoyvoLLd
1fKET2Q7DxHFpJ5JrlpLoE3xKIxFYpVsRc5nRT0YLgxSdyPUCsEn0dZkCKuiBHWfa05GjxcyFS+F
STpRupTfBe1lMnN3DHu2UYnQVtA4RK/CsFGXUqzyCS/hAyya5dk=
','eJyNUFFvgjAQ/i99VtNrHQ7eENxkydAgyx6WPRhFZiK1oXWbM/z3XQ9I3MOylbT0evd99313YdOT
eTQlCy4s1Hq+rd3trj33+237V4kyViUULewbZV3EolkaJvFDyBpcA5Yf/4lbJfMp4qDDTfdfqCFx
zZngArgvRE89AZ5x4LgAQLABw8q42LXFer0xI84FbhgBx2xUF7HtaIbcHwqRgwzGMpAew07xcXOq
CmWdkmX9ec6KUmVGKxff171/IskrFoAnhYRbAR5MJLXONprAYWl/9di0rhZ1qQ5ImqgdJV1INq9c
9oNAl4vWJYeJc9lXpxXVa9RKJmlDX/BT6FiOwUMG7H3tC32668oaJAqjPEI0BplRnbJlbesz5p4Q
y3rhjiHX+JrOnjNE9NNiwcuf7pFfHyokjdd2TYCZej9oGq2xFc2YuMkJGbyRHGcihM993w0B3/MP
lVauSDpZTfOKn1vf6NjNgw==
','55140','NEWR','02','frans.mazhar@gmail.com','2021-09-22 08:43:36','01','ACTC','2021-09-22 08:43:36','01','44443333','0300');INSERT INTO public.settlement (id,crdt_account_no,crdt_bank_account_no,dbtr_account_no,dbtr_bank_account_no,log_message_id,orgnl_crdt_trn_bizmsgid,orign_bank,recpt_bank,settl_conf_bizmsgid,crdt_account_type,crdt_id,crdt_id_type,dbtr_account_type,dbtr_id,dbtr_id_type,full_message,receive_date) VALUES
	 (2998,'11869','123456','74664','123456',NULL,'20210920SIHBIDJ1010O0100000112','CENAIDJA','SIHBIDJ1','20210920CENAIDJA010R0200000883',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUl1vozAQ/CunfU4r23CU5I0Aubq9QERc9eHUhxxOckjBIHB7zUX9710b3EpV26sR+IPZndnx
nmB+3y/7PcxOELXtpezMajF8q0oOs+Kq14rbXa7/2L9mB3GaRTy5iuAJxwRE88W4Nb+cYxwd4+bV
P9TADTkwwiiZMuJSE0oKwogZYejBBBCZbHcDuN2U/TkhDF96TgnYVOuH0lCkQvxMl2km4jxb8GIZ
CZ5niIi7baJHojMyPWNEMDqj0xm7ANSSNOV9vVV6qEA3C76q9Vr3RWuPfnTOJKv4fcFm0MCjjk3U
H/Pl3V4dMCtXu0hJJILZr9Nw+pbBuTYyEEqNHw6a1RY8WhJaS0gIT3d4L4/vZE+VFA1+PqLIXSGU
ORbM83894tHSQBSLGLfGuh4bYWeZcWm8W3W6OyLmBqOwB+5e8hfbnfmf/NZdVJbaNcyb9rnwg8AH
2zqx1PZ8pY9myozTkaz0cfPttpKNbMChPslHaRhMx15ct4caxSUbvbGKU/VwaA3SMQncATF3a2Ov
xQqvlPhe6ONR0Uulh/otRPxVVhLxTKGTobC9/kwL8/zvwWtxX0Wji/jgeAaaMBnO
','2021-09-20 14:19:27.755124'),
	 (3357,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000166','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000878',NULL,NULL,NULL,NULL,NULL,NULL,'eJydkl1vgjAUhv9Lr9G0uKF4VwFnlwkLdBfLsgvHhzORQqAaN+N/32kBsxidZiWUfrznvE8P3aPJ
pp7XSzTeI1qWs6RSo2nTr1ZJ8xVM1FIwPQvkp95VM+R4PmXuI0UHaAbixY1xEZtNII60cZPVNzAw
ZY5MbBJsm6RLjYkdYhOrNhqOkIFA6aZZIy4Xcd3H2ISX9AlGOlW0jZWFx/mTN/d87gT+lIVzylng
g8KpUle2Rj1s90zCiTUmZIwBx0BuEW/yVMjmBLKYsudcRrIOS730UHVF0sTngTUtMXHnxvPLfkG1
FGvIykRGRQJGaPy2b1ZPHbqqHR2sITKOUj/X4rYkti4JtjvBXxwYHd7h5+3OIHgi4QV0lzgCPGhR
rM4J8lyH5jttg6jDHZiq+tZwWzLtDENV4OdKVl+gecGKUCG2+cM0U/vuh9Q/gi7ltWvK+Ouva+ok
/wqEyKhc5wDlLuRCk3piuy47FkhH41h2wSepiDm4u7fQEeBWNRwcHmg/XjgQxg==
','2021-09-21 09:11:02.472455'),
	 (3366,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000172','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200001515',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtPWr8EbAs4uExboHpbFB8eHM5FCoBo343/fpVCzGJ1mJfTznHtOb+8BTbbV
vFoh84CsopjFZT2bNv16HTejYKKSgqmVLz/Vab1CtutZzHmy0BFaB/H8Tl7IZhPgkZY3WX+DB1aL
I4opwQYlOjQmRoAphkaGZIg6CJBOkjbgYhlVPYwp/KRHMFKhwl1US7icP7tz1+O2701ZMLc48z1A
2GXiyFaoi40uJZyMTDIw6QMCL04ebbNEyOYGMp+yl0yGsgoKtfVY6iQpx5cN126xgUdajWfX9fxy
JTYQlYnUEjEIIfP90OyeK+isaQUy7qPOCeplCtymxFApwYYG/OVjjI4LeLz9BQuuiHkO3TUfPu63
VqhWgji3TfO9kkGWzW1Y1vmtoFpSpQzTOsEvpSy/APMKLCiUxSl+kKT1ufMh1UNYK3mrTBl/+1Wm
dvwvIjDDYpOBKWcpl8qpK3abQnuBcFYUSU0+C0VofzAcoZOBe9Fwcfig/QCZARDi
','2021-09-21 09:14:29.123807'),
	 (3371,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000174','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000715',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtPiV/CtAs4uEwzUp8UHx4czkUKgGjez/77bQs1iNJoVKP0495zT23tG00O9
qLdocka0LOdJpUazpt/tkuYvmKilYHoWyE+9q2bI8XzK3FeKfqB1EC+ejIvYfApxpI2b7r7BA1Pi
yMIWwbZFDDUmdogtrNqYDFEHAdJNswZcbuK6h7EFH+kRjDRVdIyVhMf5m7fwfO4E/oyFC8pZ4APC
qVJXtkJdbHctwsloAi8eIfDiFvEhT4VsTiCLGVvmMpJ1WOqll8okSTu+bVi7tfDYqPH8vl5QbcUe
WJnIqEhACE3ez83qtYLJmlEgY5UPA/VzDW5TYuuUYNsAHvhYw+WdbljwRMIL6O75CHC/tTIwSsDz
2DQ/aRlEHe7AVOW3hmrJtDIMVYKXlay+ALOCKCiU9YU/TDO1735IfRF0Kx+V28pnf8rUSf4VCJFR
uc/BlLuRG+3UE8d9abwAHY1jaYKvqIjVHwxH6GLgWTQcHB5ov5Q4EOo=
','2021-09-21 09:16:07.373595'),
	 (3376,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000176','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000360',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtOiYuCtAs4uEwzUp8UHx4czkUKgGjez/77bAmYxGs1K6Oc595ze3jOaHupF
vUX2GdGynCeVms2afrdLmlEwUUvB9CqQn/pUrZDj+ZS5rxT9QOshXjzJi9h8CjzS8qa7b/DAlDgy
sEGwZZAuNCZWiA2s2tDEqIcA6aZZAy43cT3A2ICfDIg6hVDRMVYSHudv3sLzuRP4MxYuKGeBDwin
Sl3ZCvWx1TcIJ6ZtjO2xhcCLW8SHPBWyuYEsZmyZy0jWYam3XqouSdrxbcPKLBlNcKfG8/t6QbUV
e4jKREZFAkLIfj83u9cKXdZaBUwmE9S7QP1cg9uUWDol2OoAD3ys4fFONyx4IuEFdPd8BHjYWjE7
JYjz2DQ/aRlEHe7AUuW3hmrJtDJMVYKXlay+ALMCFhTK+hI/TDN17n5I/RB0Kx+V28pnf8rUSf5F
BGZU7nMw5W7kRjv1xHFfdl4gHI1j2ZGvQhFjOBqb6GLgWTRcHD5ov8vnEQc=
','2021-09-21 09:26:00.1518'),
	 (3383,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000178','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200001448',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtMW54S3Cji7TDBSnxYfHB/ORAqBatzM/vsu5SOL0WhWQj/Puef09p7R5FDO
yy2yzojl+Swqqtm07ne7qB4ll6WSXK989alPqxWyXY9x55WhH2g9JLIHeQGfTYBHGt5k9w0eeCWO
KKYEm5S0oTExl5hiaGQ4HKMeAqQTJzU434TlAGMKPxkQjHSo4BhWEq4Qb+7c9YTte1O+nDPBfQ8Q
dhE7qhHqY7NPiSAji44tw0DgxcnCQxpLVd9AZVO+SFWgymWut16KNkna8XXDtdsRbtVEelvPL7Zy
D1G5TJiMQAhZ7+d691KhzVqjgMmziXod1Es1uEmJqVOCO8AdH2t4vNMVC66MRAbdLR8+Nhor41YJ
4tw3LU5aBjFb2LCs8ltCtSRaGaZVgheFKr4AswIWFMq6i7+Mk+rc+VD6IdhW3Su3lcf/lKkd/YsI
zCDfp2DK2aiNdurK4z5vvUA4FoaqJV+EItQYPo1QZ+BRNFwcPmi/yl4RBQ==
','2021-09-21 09:28:37.667554'),
	 (3389,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000182','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200001301',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtOWzQhvFXB2mWCwPi0+OD6ciRQC1biZ/fddCjWL0c3sEko/zrnncHtPaLyv
Z/UGOSfEynKaVM1s0o7bbdJ+JZe1klyvQvWuT5sVcv2Ace+ZoS+IHhLFnbwFn46BRzreePsJHngj
jiimBNuUmNSY2BGmGIJYmKAeAqSXZi24XMf1AGMKLxkQjHSqxSFuJHwhXvyZHwg3DCY8mjHBwwAQ
bpV6qhPqY7tPiSBDx6IOoQi8eEW8z1Op2j9QxYTPc7VQdVTqrafKFEk7vm4Y6xjZRk3kt/XCaiN3
kJXLjMkEhJDzemp3LxVM1YwCGVmod4YGuQZ3JbF1SbBtAL/5gGtYweUdr1jwZSIKGG75CLHVWaFG
CfL8bVoctQxirnBh2dS3hm7JtDJMmwLPK1V9AGYJLGiU1Tl/lGbNufem9EWwjfqr3ZYB/9GmbvIv
IjAX5S4HU95arbVTXx52pfEC6VgcK0O+SEWo9fA4RGcD96Lhx+GB+AZoXBDX
','2021-09-21 09:32:16.224589'),
	 (3396,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000186','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200001090',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtOic4O3Cji7TFikPi0+OD6ciRQC1bgZ//tuCzWL0WlWQj/PPef09h7QaFtP
6xVyDoiW5SSp1Gzc9Ot10oyCiVoKpleh/NSnaoVcP6DMe6HoCK2DeHFnXMQmI4gjbdxo/Q0emBJH
FrYIti1iqDGxZ9jC0GAbow4CpJdmDbhcxnUPYwt+0iPqFKiiXawkfM5f/akfcDcMxmw2pZyFASDc
KvVkK9TFdtcinAydAXZIH4EXr4i3eSpkcwNZjNlbLiNZz0q99VyZJGnHlw03bsmjUeP5db2wWokN
sDKRUZGAEHLeD83uuYLJWquAyZNSMNAg1+A2JbZOCbYN4C8fFjou4PH2Fyz4IuEFdNd8hLjfWhka
JeC5bZrvtQyiLndhqfJbQ7VkWhmmKsFvlay+ADOHKCiUxYl/lmbq3PuQ+iHoSt4qt3nAfpWpm/wr
ECKjcpODKW8pl9qpL3ab0ngBOhrH0gSfURGrP3gYopOBe9Fwcfig/QB24RDg
','2021-09-21 09:40:17.077849'),
	 (3403,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000190','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200001237',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtPi1+CtAs4uEwzUp8UHx4czkUKgGjfjf9+lULMY3cxK6Oc595ze3hOa7Kt5
tUHWCdGimMVlPZs2/XYbN6NgopKCqZUvP9RpvUK261HmvFB0htZBPH+QF7LZBHik5U22X+CB1eLI
wAbBpkF0aEzMABsYGjH6Y9RBgHSStAEX66jqYWzAT3oEIxUqPES1hMv5qzt3PW773pQFc8qZ7wHC
LhNHtkJdbHYNwsnIGpoWeULgxcmjfZYI2dxA5lO2yGQoq6BQW8+lTpJyfNtw7RYP+lir8ey+nl9u
xA6iMpFSEYMQst5Oze61gs6aViAmQZ0L1MsUuE2JqVKCTQ34zccYnVfweMcbFlwR8xy6ez583G+t
YK0Ecf42zY9KBlGb27Cs81tBtaRKGaZ1ghelLD8BswQWFMrqEj9I0vrceZfqIehG/lVuS4/9KFM7
/hcRmGGxy8CUs5Zr5dQVh12hvUA4GkVSk69CQfUOhiN0MfAoGi4OH7RvxbgQ/w==
','2021-09-21 09:59:21.699958'),
	 (3408,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000194','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000160',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtMiusEbAs4uEwzUp8UHx4czkUKgGjez/77bQs1iNJqV0M9z7jm9vSc02Tfz
ZoPsE3KqapbWcjZt++02bUdOeSM4VatQfKpTuUKuHzjUe3XQD7QeYuWDvJjOJsAjHW+y/QYPVIoj
AxsEWwbRoTGxImxg2cgYox4CpJflLbhaJ80AYwN+MiDyFELFh0RK+Iy9+XM/YG4YTGk0dxgNA0C4
deaJTqiPrb5BGHmyiWGbzwi8eGWyLzIu2huIckoXhYhFE1Vq66XWSVKOrxtWbk3T0mqsuK0X1hu+
g6iU5w5PQQjZ76d291JBZ00rEGuEemdoUChwlxJLpQRbGnDHxwoe73jFgs9TVkJ3y0eIh50VUytB
nPum2VHJIMdlLixlfhuollwpw1QmeFGL+gswS2BBoazO8aMsl+feh1AP4WzEvXJbBvRPmbrpv4jA
jKtdAaa8tVgrpz4/7CrtBcI5SSI0+SIUMYbmaIzOBh5Fw8Xhg/YLsR8Q+w==
','2021-09-21 10:12:52.182525');
INSERT INTO public.settlement (id,crdt_account_no,crdt_bank_account_no,dbtr_account_no,dbtr_bank_account_no,log_message_id,orgnl_crdt_trn_bizmsgid,orign_bank,recpt_bank,settl_conf_bizmsgid,crdt_account_type,crdt_id,crdt_id_type,dbtr_account_type,dbtr_id,dbtr_id_type,full_message,receive_date) VALUES
	 (3413,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000196','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000673',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtOCQuANAWeXCQbq0+KD48OZSCFQjZvZf99tAbMYjWYl9POce05v7xlND82i
2SL7jJyqmqe1nM3afrdL25FT3ghO1SoUn+pUrpDrBw71Xh30A22AWPkkL6bzKfBIx5vuvsEDleJI
wxrBlkb60JhYEdawbIapowECpJflLbjaJM0IYw1+MiIYqVDxMZESPmNv/sIPmBsGMxotHEbDABBu
nXmiExpia6gRRkybGDYZI/DilcmhyLhobyDKGV0WIhZNVKmtl7pPknJ827A0S8yJ3qux4r5eWG/5
HqJSnjs8BSFkv5/b3WuFPmudAgwmGlygQaHAXUoslRJs9YAHPtbweKcbFnyeshK6ez5CrHdWjF4J
4jw2zU5KBjkuc2Ep89tAteRKGaYywcta1F+AWQELCmV9iR9luTz3PoR6CGcrHpXbKqB/ytRN/0UE
ZlztCzDlbcRGOfX5cV/1XiCckySiJ1+FIpo+nhjoYuBZNFwcPmi/v48RAA==
','2021-09-21 10:16:18.108658'),
	 (3418,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000198','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000697',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUltvgjAY/S99VtOCN3irgLPLBAP1afHBcXEmUghU42b23/dRLlmMRrMSej3nO6dfvwuaHctl
uUPmBdE8X0RFNZvX/X4f1aNgopSCqZUnP9VptUKW41Jmv1L0A62HePYkL2CLGfBIw5vtv8EDq8SR
hjWCDY20oTExfKzhqo2NCeohQNpxUoPzbVgOMNbgJwOCkQoVnMJKwuH8zVk6Lrc8d878JeXMcwFh
FbEtG6E+Nvoa4WRikqmp6wi82Fl4TGMh6xvIbM5WqQxk6edq66Vok6Qc3zas3I7Gw1aNp/f1vGIn
DhCViYSKCISQ+X6pd68V2qy1CsQwUK+DuqkCNykxVEpwB3jgYwOPd75hwRERz6C758PDemNl2ipB
nMem+VnJIGpxC5ZVfkuolkQpw7RK8KqQxRdg1sCCQtl08f04qc7tD6kegu7ko3Jbu+xPmVrRv4jA
DPJDCqbsrdwqp444HfLWC4SjYShb8lUoounD0Rh1Bp5Fw8Xhg/YL65sRFA==
','2021-09-21 10:18:37.49458'),
	 (3426,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000202','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200001966',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtPWDQNvFXB2mWCwPi0+OD6ciRQC1biZ/fddCjWL0WhWQj/Puef09p7QeF/P
6g1yToiV5TSpmtmk7bfbpB0ll7WSXK9C9alPmxVy/YBx75WhH2g9JIoHeQs+HQOPdLzx9hs88EYc
UUwJtikxoTGxI0wxNGJbFuohQHpp1oLLdVwPMKbwkwHBSIdaHOJGwhfizZ/5gXDDYMKjGRM8DADh
VqmnOqE+tvuUCDJyqOXgEQIvXhHv81Sq9gaqmPB5rhaqjkq99VKZJGnH1w03brE1okZN5Lf1wmoj
dxCVy4zJBISQ835qdy8VTNaMAsVD1DtDg1yDu5TYOiXYNoA7PlbweMcrFnyZiAK6Wz5CPOysUKME
ce6bFkctg5grXFg2+a2hWjKtDNMmwfNKVV+AWQILCmV1jh+lWXPufSj9EGyj7pXbMuB/ytRN/kUE
5qLc5WDKW6u1durLw640XiAci2NlyBehCB0+PVvobOBRNFwcPmi/rEgQ6g==
','2021-09-21 10:26:11.061506'),
	 (3434,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000206','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000704',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtMWPwJvCDi7TDBYnxYfHB/ORAqBatyM/323BcxidJqV0M9z7zk9vSc03lez
aoOsE7KLYhqXajap++02rkfBRCUF06tAfupTtUKO59vMfbXRGVoH8fzJuAWbjiGONHHj7TdoYIoc
UUwJNilpU2Nihphi1Ua4jzoIkG6S1uBiHVU9jCn8pEcw0qkWh0hReJy/eTPP507gT1g4szkLfEA4
ZeLKhqiLzS4lnIwsg1jUQKDFzaN9lghZ30DmEzbP5EJWYaG3XsrWJK34tmAllgyGZsvGs/t8QbkR
O8jKRGqLGIiQ9X6qd68ZWtcaBnBlhDoXqJ9pcGOJqS3BZgv4SwdF5xU83vGGBE/EPIfuno4AG42U
YcsEeR6L5kdNg2yHO7BU/lZQLalmhqkyeF7K8gswS4iCQlld8odJqs7dD6kfwt7IR+W29NmvMnXi
fwVC5KLYZSDKXcu1VuqJw65otUA6O4pkG3yVilCjPxiii4Bn0XBx+KD9AILeEN4=
','2021-09-21 10:31:26.818627'),
	 (3439,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000208','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000082',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtNWXYC3Cji7TDBQnxYfHB/ORAqBatzM/vsuBdxiNJqV0M9z7zk9vSc02Vfz
aoOsE2JFMYvLejZt+u02bkbJZaUk1ytffejTeoVs12PceWHoG1oPifzBuJDPJhBH2rjJ9gs08Joc
UUwJNinpUmNiBphi3QyKegiQTpI24GIdVQOMKfxkQDDSqcJDVFO4Qry6c9cTtu9NeTBngvseIOwy
cVRL1MdmnxJBDIsa1shEoMXJo32WSNXcQOVTvshUqKqg0FvPZWeSVnxdcK2VjIbjjk1kt/n8ciN3
kJXLlMkYiJD1dmp2Lxk611oGcMVEvTPUyzS4tcTUlvwC7uhYweMdr0hwZSxy6G7p8PGwlWJ0TJDn
vmhx1DSI2cKGZe1vBdWSamaY1gYvSlV+AmYJUVAoq3P+IEnrc+dd6YdgG3Wv3JYe/1OmdvyvQIgM
i10Gopy1Wmulrjzsik4LpGNRpLrgi1SEDkfjJ3QW8CgaLg4ftB/ZDhEJ
','2021-09-21 11:28:53.175786'),
	 (3444,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000210','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000067',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtPipuJbBZxdJhioT4sPjg9nIoVANW5m/323hZrFaDQrUPpxzj2nt/eEJvt6
Xm/Q+IRoWc6SSo2mTb/dJs1fMFFLwfQskJ96V82Q4/mUua8U/UDrIF48yIvYbAI80vIm22/wwJQ4
srBFsG0RExoTO8QW1m0wRB0ESDfNGnC5jusexhZ8pEcw0qGiQ6wkPM7fvLnncyfwpyycU84CHxBO
lbqyFepiu2sRTkbjPoEXgRe3iPd5KmRzAllM2SKXkazDUi+9VCZJ2vF1w8orIaOhUeP5bb2g2ogd
RGUioyIBITR+PzWrlwoma60CrBLUOUP9XIPblNg6Jdg2gDs+VnB5xysWPJHwArpbPgLcb61gowRx
7pvmRy2DqMMdmKr81lAtmVaGoUrwopLVF2CWwIJCWZ3jh2mm9t0PqS+CbuS9clv67E+ZOsm/iMCM
yl0Opty1XGunnjjsSuMFwtE4loZ8EYpY/afnATobeBQNB4cH2i9quhDO
','2021-09-21 11:31:35.301254'),
	 (3451,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000214','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000422',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtPWjwlvCDi7TDBQnxYfHB/ORAqBatzM/vtuC5jF6DQroZ/nnnN6e09osq/m
1QaZJ2QVxSwu1Wxa99ttXI+CiUoKple+/NCnaoVs17OY82Khb2gdxPMH40I2m0AcaeIm2y/wwJQ4
opgSbFDSUmNiBJhi1QaUog4CpJOkNbhYR1UPYwo/6RGMNFV4iJSEy/mrO3c9bvvelAVzizPfA4Rd
Jo5shLrY6FLCydjsj01CEXhx8mifJULWN5D5lC0yGcoqKPTWc9kmSTu+bliZJaMn2qrx7LaeX27E
DliZSC0RgxAy30717qVCm7VGAXaHqHOGepkGNykxdEqw0QL+8gHPsILHO16x4IqY59Dd8uHjfmNl
0CoBz33T/KhlkGVzG5YqvxVUS6qVYaoSvChl+QmYJURBoazO/EGSqnPnXeqHsDbyXrktPfarTO34
X4EQGRa7DEw5a7nWTl1x2BWtF6Czoki2wRdUhPYHwxE6G3gUDReHD9oPkNwQ5Q==
','2021-09-21 11:38:16.507528'),
	 (3460,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000218','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000018',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtPWaYS3Cji7TFiwPi0+OD6ciRQC1biZ/fddCiWL0WhWQj/Puef09p7R9FAt
qi2yz4gVxTwu69ms6Xe7uBkll5WSXK8C9alP6xVyPJ9x94WhH2g9JPIHeUs+nwKPtLzp7hs88Foc
UUwJtigxoTGxQkyxbmSCegiQbpI24GITVQOMKfxkQDDSoZbHqJbwhHj1Fp4vnMCf8XDBBA98QDhl
4qpWqI+tPiWCTOwRtUcWAi9uHh2yRKrmBiqf8bdMLVUVFnrruTRJ0o6vG9ZmrfHQqInstl5QbuUe
onKZMhmDELLfz83upYLJmlGgxEK9DupnGtymxNIpwR3gjo81PN7pigVPxiKH7paPAA9bKxOjBHHu
mxYnLYOYIxxY1vmtoFpSrQzTOsFvpSq/ALMCFhTKuosfJml97n4o/RBsq+6V28rnf8rUif9FBOay
2Gdgyt2ojXbqyeO+MF4gHIsiZcgXoQgdPo3GqDPwKBouDh+0X9kvEQo=
','2021-09-21 11:53:03.414595'),
	 (3474,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000224','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000689',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vwiAU/S88qwH8yOgbtnWyzHap+LT44Fp1JhabFo2b2X/fhRazGI1mNOXznHsOl3tCw301
qdbIOyFeFOOsNLNR3W82WT0qoSqthF3F+tOemhXyw4iL4IWjH2gtJHcP8qZiPAQeaXjDzTd4EEYc
UUwJZpS40JiwBFNs2uCJoRYCZLBc1eBikVYdjCn8pEMwsqGmh9RIhFK+hpMwkn4cjUQy4VLEESD8
chnoRqiNWZsSSZjX7XqYIvAS7NJ9vlS6voHejcRbrqe6Sgq79Vy6JFnH1w1bt4z1nZrMb+vF5Vpt
IapQK64yEELe+6nevVRwWXMKlBoFB41yC25SwmxKMHOAOz7m8HjHKxZClckddLd8xLjbWOk5JYhz
37Q8WhnEfenD0uS3gmpZWWWYmgS/lbr8AswMWFAo83P8ZLky58GHtg/B1/peuc0i8adM/exfRGBO
i20OpoKFXlinoTpsC+cFwvE01Y58EYrQbq8/QGcDj6Lh4vBB+wWzphDw
','2021-09-21 12:33:05.828999'),
	 (3479,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000226','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000892',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99dqatHxPeKuDsMsFIfVp8cHw4EykEqnEz+++7LWAWo9GshH6ec8/p7T2h8b6a
VRtknxArimlc6tmk7rfbuB4ll5WS3KwC9WlO9Qo5ns+4+8rQD7QOEvmDvJBPx8AjDW+8/QYPXIsj
iinBFiVtaEysBaZYt5FFUQcB0k3SGlyso6qLMYWfdAlGJlR4iLSEJ8SbN/N84QT+hC9mTPDAB4RT
Jq5qhJ6w9USJIJbdp3ZvhMCLm0f7LJGqvoHKJ3yeqVBVi8JsvZRtkozj64a1WTIY0lZNZLf1gnIj
dxCVy5TJGISQ/X6qdy8V2qw1CpjSZ9Q5Q/3MgJuUWCYl2GoBd3ys4PGOVyx4MhY5dLd8BLjXWBm2
ShDnvmlxNDKIOcKBpc5vBdWSGmWY6gTPS1V+AWYJLCiU1Tn+Ikn1ufuhzEOwjbpXbkuf/ylTJ/4X
EZhhscvAlLtWa+PUk4dd0XqBcCyKVEu+CEVorz8YorOBR9Fwcfig/QLWCxEE
','2021-09-21 12:42:42.013415');
INSERT INTO public.settlement (id,crdt_account_no,crdt_bank_account_no,dbtr_account_no,dbtr_bank_account_no,log_message_id,orgnl_crdt_trn_bizmsgid,orign_bank,recpt_bank,settl_conf_bizmsgid,crdt_account_type,crdt_id,crdt_id_type,dbtr_account_type,dbtr_id,dbtr_id_type,full_message,receive_date) VALUES
	 (3484,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000228','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200001158',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtMWNcJbBZxdJhisT4sPjg9nIoVANW5m/32X8pHFaDQroZ/n3HN6ey9oeiwX
5Q5ZF8TyfB4V1WxW9/t9VI+Sy1JJrle++tSn1QrZrse488rQD7QeEtmTvBWfT4FHGt50/w0eeCWO
KKYEm5S0oTExA0wxNEJGE9RDgHTipAbn27AcYEzhJwOCkQ61OoWVhCvEm7twPWH73owHCya47wHC
LmJHNUJ9bPYpEcS0hmPLGCLw4mThMY2lqm+gshlfpmqlyiDXWy9FmyTt+Lbhxq3Rqon0vp5f7OQB
onKZMBmBELLeL/XutUKbtUYBU2qiXgf1Ug1uUmLqlOAO8MDHBh7vfMOCKyORQXfPh4+NxsqkVYI4
j02Ls5ZBzBY2LKv8llAtiVaGaZXgZaGKL8CsgQWFsuniB3FSnTsfSj8E26lH5bb2+J8ytaN/EYG5
yg8pmHK2aquduvJ0yFsvEI6FoWrJV6EINYajMeoMPIuGi8MH7RfJ9xEC
','2021-09-21 12:46:38.342968'),
	 (3490,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000230','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000970',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtOWuQXeEHB2mbBAfVp8cHw4EykEqnEz+++7LdQsRqNZCf08557T23tEk107
b9fIOSK3rmdZo2bTrt9ssm4UTLRSML2K5Kc+VSvkBaHL/BcX/UAbIF7dyUvYbAI80vMmm2/wwJQ4
opgSbFNiQmNix5hi1ewnjAYIkH5edOB6lbYjjCn8ZETUKYRK9qmSCDh/DeZByL0onLJ47nIWhYDw
mtyXvdAQ20NKOLGdMXYsG4EXv0p3ZS5kdwNZTdlbKRPZxrXeem5MkrTjy4aVWTKmxKjx8rpe1KzF
FqIyUbgiAyHkvB+73XMFk7VeAVNLKRhoWGpwnxJbpwTbBnDDxxIe73DBQiAyXkF3zUeErd4KNkoQ
57ZpftAyyPW4B0uV3xaqpdDKMFUJfmtk8wWYBbCgUJan+HFeqHP/Q+qHcNfyVrktQvanTL3sX0Rg
JvW2BFP+Sq6000Dst7XxAuHcNJWGfBaKUOth/IhOBu5Fw8Xhg/YLqvYQ7Q==
','2021-09-21 12:50:42.925452'),
	 (3496,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000232','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000794',NULL,NULL,NULL,NULL,NULL,NULL,'eJydkl1vgjAUhv9Lr9W0RV3gDgFnlwkG69WyC8eHM5FCoBo3s/++00LNYjSalVD68Z7zPj30hCb7
Zt5skHNCblXN0lqNpm2/3abtVzDRSMH0LJKfelfNkBeELvNfXPQDrYd4+WDcks0mEEe6uMn2GxiY
MkcUU4JtSkxqTOwYU6zakz1EPQRKP8tbcbVOmgHGFF4yIBjpVMtDoiwCzl+DeRByLwqnLJ67nEUh
KLw682Vn1Md2nxJObGdEHYwRsPhlsi8yIdsTyHLKFoVcyiau9NJzbYqkia8D4xZ3bNx4cdsvqjdi
B1mZyF2RghFy3k7t6qWDqZpxoJaFemdpWGhxVxJblwTbRnCH4x1+3vEKQiBSXkJ3iyPCVodCjRPk
uQ/Nj9oGuR73YKrq28BtybUzDFWBF7Wsv0CzwopQIXb54yxX+/6H1D/C3ch7120Vsj/X1Ev/FQiR
y2pXAJS/lmtNGojDrjIskM5NEmmCL1IRag1HY3QGeFQNB4cH2i+IsRDd
','2021-09-21 12:52:04.391616'),
	 (3502,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000234','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000240',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtOCzMAbAs4uExaoT4sPjg9nIoVANW5m/323hZrFaDQrobS9595zOL0nNN23
i3aDnBNy63qeNXI16+btNuu+nPJWcKp2kfhUUblDXhC61H9x0Q+MAWLVg3kJnU8hj/R50+03aKCS
HBnYINg2iC6NiR1jA8thjDEaIED6edGB63XajiAALxkRGYVSySGVFAFjr8EiCJkXhTMaL1xGoxAQ
XpP7oicaYntoEEZsxzId00Sgxa/SfZlz0f2BqGb0rRSJaONaHT032iSl+LpgpdaaTDQbK2/zRc2G
76Aq5YXLMyBCzvupO71k0K5pBsO00OAMDUsF7i2xlSXY1oA7OlZweccrEgKesQqmWzoibPZSxpoJ
6twXzY6KBrke82Ar/W2hWwrFDEtp8Fsjmi/ALCELGmV1rh/nhYz7H0JdhLsR99ptGdI/bepl/0qE
zKTelSDKX4u1Uhrww67WWqCcm6ZCJ1+UImCR9YTOAh5Fw4/DA+MXovAQ8A==
','2021-09-21 12:53:37.075353'),
	 (3508,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000236','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000161',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtPWyYQ3BJxdJhioT4sPjg9nIoVANW5m/32XQs1iNJqV0M9z7jm9vSc02dfz
eoOsE7LLcpZUzWza9ttt0o6CiVoKplaB/FSnzQo5nm8z99VGP9B6iBcP8iI2mwCPdLzJ9hs8sEYc
UUwJNinRoTExQ0xx04hBUA8B0k2zFlyu43qAMYWfDAhGKlR0iBsJj/M3b+753An8KQvnNmeBDwin
Sl3ZCfWx2aeEE9MaGRYxEXhxi3ifp0K2N5DFlC1yGck6LNXWS6WTpBxfN6zMkvFYq/H8tl5QbcQO
ojKR2SIBIWS9n9rdSwWdtU4B0+Ez6p2hfq7AXUpMlRJsasAdHyt4vOMVC55IeAHdLR8BHnZWDK0E
ce6b5kclg2yHO7Bs8ltDtWRKGaZNgheVrL4AswQWFMrqHD9Ms+bc/ZDqIeyNvFduS5/9KVMn+RcR
mFG5y8GUu5Zr5dQTh12pvUA4O46lJl+EInT4NDLQ2cCjaLg4fNB+AdzREQw=
','2021-09-21 12:56:23.063325'),
	 (3514,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000238','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000930',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtNW3cS3Cji7TDBSnxYfHB/ORAqBatyM/323BcxidJqV0M9zzzm9vUc02pXT
co2GR8TyfBIVejau+s0mqkbJZakkNytffZpTvUK26zHuvDJ0gtZCInswLuCTEcSROm60+QYPXIsj
iinBFiUNNSbWHFOsm9XFqIUA6cRJBc5XYdnBmMJPOkSfAlWwD7WEK8SbO3U9YfvemM+nTHDfA4Rd
xI6qhdrYalMiiDXsPw+7FgIvThbu0liq6gYqG/NZqgJVznOz9VI0STKOrxvWZgntDRo1kd7W84u1
3AIrlwmTEQih4fux2r1UaLJWK2AKDK0z1EsNuE6JZVKCz4C/fAzQaQmPd7hiwZWRyKC75cPH3drK
oFECnvumxcHIIGYLG5Y6vyVUS2KUYaoTPCtU8QWYBURBoSzP/PM40efOhzIPwdbqXrktPP6rTO3o
X4EQGeTbFEw5K7UyTl253+aNF6BjYaia4AsqQru9/hM6G3gUDReHD9oP/KkRGw==
','2021-09-21 12:57:42.558304'),
	 (3519,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000240','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200001971',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VnNb3Ry+IeDsMsFA97T44ECdiRQC1bgZ//suhZrF6DQrUPpx7jmnt/dAhtty
Uq7I4EDsPB8nRTUa1f16ndR/yWWpJNezQH3q3WpGHM+3uftikyO2FhHZnXERHw8xjjZxw/U3euCV
OGHAKFiMGmqgVggMsFGrT0mLINJdLGtwPo/LDgDDj3YoEE0V7eJKwhPi1Zt4vnACf8TDiS144CPC
KRauaoTaYLUZFQwGgO8TQS9uFm/ThVT1CVQ24tNURaoMc730XJgkaceXDdduAYyaSK/rBcVKbpCV
y6UtExQig/dDvXquYLLWKADrVfkwUD/V4CYllk4JWAbwl48+Oc7w8vYXLHgyERl213wE0G2sgFFC
ntumxV7LENsRDk6r/JZYLUutjMMqwdNCFV+IecMoLJTZiT9cLKt990Ppi7BX6la5vfn8V5k6yb8C
MTLKNymacudqrp16crfJjReks+NYmeAzKsq6vYdHcjJwLxoPjg+2H0fCEL8=
','2021-09-21 13:00:11.667109'),
	 (3524,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000242','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000972',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99RtMWNwNvCDi7TDBQnxYfHB/ORAqBatzM/vtuC5jFaDQroZ/n3HN6e09osm/m
zQbZJ+RU1Syt1Wza9ttt2o6CiUYKpleh/NSnaoVcP3CY9+qgH2gG4uWDvJjNJsAjHW+y/QYPTIkj
iinBFiV9aEysCFOsmjWmyECA9LK8BVfrpBliTOEnQ4KRDhUfEiXhc/7mz/2Au2EwZdHc4SwMAOHW
mSc7oQG2BpRwim1s2ibYMZBXJvsiE7K9gSynbFHIWDZRpbde6j5J2vF1w8oswXTcq/Hitl5Yb8QO
ojKROyIFIWS/n9rdS4U+a50CpiMTGWdoUGhwlxJLpwRbPeCOjxU83vGKBV+kvITulo8Qm50V2itB
nPum+VHLIMflLixVfhuollwrw1QleFHL+gswS2BBoazO8aMsV+feh9QP4WzkvXJbBuxPmbrpv4jA
jKtdAaa8tVxrp7447KreC4RzkkT25ItQhJqjp2d0NvAoGi4OH7RfTA8Qww==
','2021-09-21 13:03:35.580204'),
	 (3531,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000246','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200001854',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtPWT3hDwNllgpH6tPjg+HAmUghU42b877sUahaj06yEfp5zz+ntPaHxvpyV
G2SekJXn06ioZpO6326jehRMlFIwtfLlpzqtVsh2PYs5rxY6Q2shnj3JC9h0DDzS8Mbbb/DAKnFE
MSXYoESHxsRYYIqhkVG/h1oIkE6c1OB8HZYdjCn8pEMwUqGCQ1hJuJy/uTPX47bvTdhiZnHme4Cw
i9iRjVAbG21KOMUmHplkhMCLk4X7NBayvoHMJmyeykCWi1xtvRQ6ScrxbcPKrUG7Wo2n9/X8YiN2
EJWJxBIRCCHz/VTvXivorDUKmPaGqHWBeqkCNykxVEqwoQF/+Rii8woe73jDgisinkF3z4ePu42V
gVaCOI9N86OSQZbNbVhW+S2hWhKlDNMqwfNCFl+AWQILCmV1ib+Ik+rc+ZDqIayNfFRuS4/9KlM7
+hcRmEG+S8GUs5Zr5dQVh12uvUA4KwylJl+FIrTb6w/QxcCzaLg4fNB+AKirEPE=
','2021-09-21 13:08:22.069815'),
	 (3536,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000248','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200001456',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUltvgjAY/S99VtPWSwZvFXB2mWCgPi0+OC7ORAqBatyM/30f5ZJl0WlWQq/nfOf063dG00O5
KLfIPCOW5/OoqGazut/tonqUXJZKcr3y1Ic+rVbIclzG7ReGLtB6SGQP8gI+nwKPNLzp7gs88Eoc
UUwJNihpQ2Ni+JhiaGQ0nqAeAqQdJzU434TlAGMKPxkQjHSo4BhWEo4Qr87CcYXluTPuL5jgngsI
q4ht1Qj1sdGnRFBskpGJJwi82Fl4SGOp6huobMaXqQpU6ed667lok6QdXzes3WJKWzWR3tbziq3c
Q1QuEyYjEELm27ne/a3QZq1RwHRkoF4HdVMNblJi6JTgDvCXjzG6rOHxTlcsODISGXS3fHh42Fh5
apUgzn3T4qRlELOEBcsqvyVUS6KVYVoleFmo4hMwK2BBoay7+H6cVOf2u9IPwbbqXrmtXP6jTK3o
X0RgBvk+BVP2Rm20U0ce93nrBcKxMFQt+VcoQodV/XYGHkXDxeGD9g1tzhDZ
','2021-09-21 13:14:10.425377');
INSERT INTO public.settlement (id,crdt_account_no,crdt_bank_account_no,dbtr_account_no,dbtr_bank_account_no,log_message_id,orgnl_crdt_trn_bizmsgid,orign_bank,recpt_bank,settl_conf_bizmsgid,crdt_account_type,crdt_id,crdt_id_type,dbtr_account_type,dbtr_id,dbtr_id_type,full_message,receive_date) VALUES
	 (3542,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000250','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000693',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VtMWdcG3Cji7TFigPi0+OD6ciRQC1biZ/fddCjWL0WhWoPTj3HNOb+8JTff1
ot6gyQmxspwnVTOatf12m7R/yWWtJNezQH3q3WaGHM9n3H1h6AdaD4niwbiIz6cQR7q46fYbPPBG
HFFMCbYpMdSY2CGmuGlj20I9BEg3zVpwuY7rAcYUPjIgGGmq6BA3Ep4Qr97C84UT+DMeLpjggQ8I
p0pd1Qn1sd2nRFA8GcJrIfDiFvE+T6VqT6CKGX/LVaTqsNRLz5VJknZ83XDr9gkbNZHf1guqjdwB
K5cZkwkIocn7qV29VDBZMwp0RFDvDPVzDe5SYuuUYNsA7vhYweUdr1jwZCIK6G75CLDVWcFGCXju
mxZHLYOYIxyYNvmtoVoyrQzDJsFvlaq+ALOEKCiU1Zk/TLNm3/1Q+iLYRt0rt6XP/5Spk/wrECKj
cpeDKXet1tqpJw+70ngBOhbHygRfUBFqDUdjdDbwKBoODg+0X2hiEM8=
','2021-09-21 13:40:47.371618'),
	 (3550,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000254','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200000482',NULL,NULL,NULL,NULL,NULL,NULL,'eJydUl1vgjAU/S99VlMqusFbBZxdJhioT4sPjg9nIoVANW5m/32XQs1iNJqV0M9z7jm9vSc02dfz
eoPsE6JlOUuqZjZt++02aUfBRC0FU6tAfqrTZoUcz6fMfaXoB1oP8eJBXsRmE+AZHW+y/QYPrBFH
BBMDW8TQobFhhZjgppnPBPUQIN00a8HlOq4HGBP4jYGBkQoVHeJGwuP8zZt7PncCf8rCOeUs8AHh
VKkrO6E+tvrE4ATb5pM9xAi8uEW8z1Mh2xvIYsoWuYxkHZZq66XSSVKOrxtu3eKxVuP5bb2g2ogd
RGUioyIBIWS/n9rdSwWdNa1ARiPUO0P9XIG7lFgqJdjSgDs+VvB4xysWPJHwArpbPgI87KyYWgni
3DfNj0oGUYc7sGzyW0O1ZEoZpk2CF5WsvgCzBBYUyuocP0yz5tz9kOoh6EbeK7elz/6UqZP8iwjM
qNzlYMpdy7Vy6onDrtReIByNY6nJF6EMMjRHY3Q28CgaLg4ftF97GhDd
','2021-09-21 13:47:34.153039'),
	 (3555,NULL,'123456',NULL,'123456',NULL,'20210921SIHBIDJ1019O0300000256','CENAIDJA','SIHBIDJ1','20210921CENAIDJA019R0200001983',NULL,NULL,NULL,NULL,NULL,NULL,'eJydkl1vgjAUhv9Lr9W0RbfBXQWcXSYYqVeLF44PZyKFQDVuZv99h0LNYlw0K6H04z3nfXroCY33
9azeIOeEWFlOk6oZTdp+u03ar+SyVpLrWag+9G4zQ64fMO69MPQNrYdEcWdcxKdjiCNd3Hj7BQy8
MUcUU4JtSkxqTOwFphgasZ8s1EOg9NKsFZfruB5gTOElA4KRThUd4sbCF+LVn/mBcMNgwhczJngY
gMKtUk91Rn1s9ykRFDujoYMtBCxeEe/zVKr2BKqY8HmuIlUvSr30XJkiaeLrwFjjalrtJvK//cJq
I3eQlcuMyQSMkPN2alcvHUzVjAMdPaLeWRrkWtyVxNYlwbYR3OBYwc87XkHwZSIK6P7iCLHVoTwY
J8hzG1octQ1irnBh2tS3htuSaWcYNgWeV6r6BM0SouCirM75F2nW7HvvSv8ItlG3rtsy4L+uqZv8
KxAio3KXA5S3VmtN6svDrjQskI7FsTLBF6kItYZQpDPAvWo4ODzQfgCQtRDm
','2021-09-21 13:54:07.451796'),
	 (3687,'417832','123456','788884','123456',NULL,'20210922SIHBIDJ1010O0100000035','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200001586',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUl1vgjAU/SvLfVbTFt3QNwTcuk0w2mUPiw8O0JlIIdB9Gv/7bovdkkWdl8/Sc+8593C3MHyt
x/UKBlvwyvImrfTbqLmv12nzlFzWSnKzitWL2dUr8MPI48GtBzuMFojizLwZvxliHt3nDddfqIFr
cmCEUdJnzJYmlEwJIxi0515CCxAZZMsGXC6SukMIw4t2KAFTavaWaIpQiPtwHEbCj6MRn449weMI
EX6VBWpP1Cb9NmOCsIFD8QTUEhTJa55J1XSgihGf5Gqm6mlpPl1X1iSj+LBgE06/Z9lEfpwvrlZy
g1W5XHoyRSIYPG2br38ZrGuWgTjaDwuNcgPeW+IaS4gLuzn+l48D1UOZigJvxyjin0Z6lgXr/K9H
fBga8Hzh41JbV+MgLA0zvmrvJpWqPhHzgEk4A/Of+tNsqfeDZ1V5SaLswPwZnysXowtmdvxUmY2J
+tSPSFv9uE6LtLjAjqsMLOhEvS69ch22H8ZZuclRXbBQCyM5lG+bUkMtk8AVEIq9meQ7MWnjfHYd
VNTCBlOpGgMMRLxLI4k4utNW09lKnRBDmdPtXf42dy4abcQD4xt2mhnV
','2021-09-22 02:31:35.679857'),
	 (3694,'417832','123456','788884','123456',NULL,'20210922SIHBIDJ1010O0100000038','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200001416',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUttuozAQ/ZVqnklkGzYheSNANt5tIArep6oPWSDZSMEgcHqL+u8dG9yVqt4GYTP2mTlnhrnA
4tytuwPMLxA0zapo9deyX4/Hot8ll52S3Hip+mdutQdhnAQ8+hXAM5oDov5mXMZXC4yjQ9zi+IQa
uCYHRhglM8ZsakLJljCCRj06AQcQGZX7Htzs8m5MCMOXjikBkyq7yzVFLMR1vI4TEabJkm/XgeBp
goiwLSM1EI3IbMSYIGzukTmbAGqJ6vxclVL1Fah6yTeVylS3bczRz9Y2ySh+X7BRy9jUsonqY760
PcgTZuVyH8gCiWB+c+lP3zLYrg0MhLgzcF6hSWXAQ0t80xLiw/Mt/peHd7LHshA1Lh9RpJbG9S0L
5vlaj3gwNBCEIkRXt67DQdgbZvzUvdu0qn1EzB8Mwhm4fc2/Lff6Pvqr2iDPlR2YN+Mz9dE8MLMT
FspcbNSj3hLd6uzclbK+CopdBRbzSTqPTn2XDbOYNacKxUU7tTOKY3l3ajTUEgn0gFAszQT/FpsR
jqfnoiAH6yuk6us3EHEvjSLi6kKdvrCD+kQMZa73Y/K/tu+isYv4oL0ASSEZeQ==
','2021-09-22 02:40:30.107157'),
	 (3701,'417832','123456','788884','123456',NULL,'20210922SIHBIDJ1010O0100000041','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200000340',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUsFymzAQ/ZWOznZmJWhDfMOAG6U1eIxyyvTggnGdGMGAkib15N+zK1Ayk0nSLIOEtE/7nh57
ZPPbftnv2OzIwrY9Lzv6Wgzjfl8Os5a6N1raVWb+2CytWJSkoYwvQvaIMWGq+eS5XJ7P8Rwfz833
/1CDJHImQHA4E8KVBg5rEEDh+cAmDJHxthrA7aboTwAEvvyEUxZL5XcFUSRK/UyWSaqiLF3I9TJU
MksREXXb2IxEUzibCqFAzPxgBsBQS9wUt/VWm+EGplnIVW1y069bu/W9cyZZxW8LpuBeEDg2Vb/P
l3U7fcCqUlehLpGIza6Ow+5rBufayADgCzZ5hqa1BY+WBNYSCNjjL/wv929UT3SpGhzeo8gcjc8d
C9b5vx51b2lYGKkIl2Rdj41QWWb8JO9WnekeEHMJZAJJHOuvtxXl49+mC4vCuIZ51T6nAYbPbO9E
pbGJlXmgKSWr8+tN1e0PXy6am4Y50Af1fH4aeGJsxrw91Kgu3piNlZzou0NLUMekcMWATLGHf6jV
FPvT91DRBC9YajMYYCHqr7aSwBvbi262Mx+I4cLzv357udxn0WgjPhhPZ38Z2A==
','2021-09-22 02:48:04.415279'),
	 (3708,'417832','123456','788884','123456',NULL,'20210922SIHBIDJ1010O0100000044','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200000414',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUttO4zAQ/RU0zy2yXQNJ39IkXQw0qVLzhHjoJmm3UuNGjrlW/Dtjpy4SApaJcrF9Zs6Zk9nD
5KGbdWsY7yFq28tK269p/9xsqv6thOqMEm6Vm3/u1K4gTrNIJFcRvGEMQO5+mbcQlxPMo4e8yeYV
NQhLDowwSkLGfGlCSUEYscEphwEgMqlXPbhdlt0pIQxvekoJuFKLx9JSpFLepLM0k3GeTUUxi6TI
M0TEuk7MgWhIwiFjkrAxD8ecA2pJduVDUyvTd2B2UzFvzMJ0Reu2/mhvklP8tWAXYRh4Ntl8z5fr
tdpiVaFWkaqQCMZ3+373M4N3zTMQfgaDIzRrHPhgSeAsIQG83eN/ef6ieqoqucPHdxS5p+Hcs2Cd
/+uRz44GoljGuLTWdTgIK8eMn9a7uTb6BTG3mIQzcH+sX9Qre578NToqS+MH5tP4XAQYHNzsxJVx
B3PzYl+ZtTrVTxt1gg3rGjzmh3KcXgQjdpjFRbttUFyyNEunOFWP29ZCPZHEFRCKrbnkazkf4njy
UWBtKrpKmb5/B5FPyikiI9vooG9sbX4QQ9mIn51/9PZbNLqIF8Y7ftIZjA==
','2021-09-22 02:49:47.828609'),
	 (3716,'8417832','123456','788884','123456',NULL,'20210922SIHBIDJ1010O0100000047','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200001791',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUstugzAQ/JVqz6SyDSkkNwKkddtAlLjqoeohhSRFCg4C9xn137s2cSpVfWQRD9uzO7PD7mD0
1E7aNQx3ENb1RdHor3H3LMuie0suWyW5WWXq0ZzqFURJGvL4MoQPDAfE9si8Ob8YYR7d543Kd9TA
NTkwwigZMGZLE0pmhBEM6g8oOIDIeLnqwPUib08JYXjTU0rAlJo/55oiEeI6mSSpiLJ0zGeTUPAs
RUTULGO1J+qRQY8xQdiw7w7dM0At8TZ/qpZSdR2o7ZhPKzVX7aw2W+eNNcko/llwF4Fv2UT1O1/W
rOUGq3K5CmWBRDC823W73xmsawcGLwDnAE0rA95bEhhLSAAf9/hfXn+onshCbPHxG0VmaTzfsmCd
//WIV0MDYSQiXGrrWhyElWHGT+3dtFHNG2JuMAln4P5Qf7Zc6fP4QTVhnis7MN/Gxw8wPDCzExXK
HEzVm36l2urbUi7U4iRpXkoJFvRHvcCjfuCy/TTO602F8mIsYTQn8nlTa6ylErgComfRZF+JaQ8H
1HNRkoMdFlJ1DhiIeJFGE3F1q07X2lr9oYYy1+uffXV3LBp9xAvjE8vKGj0=
','2021-09-22 02:53:40.279858'),
	 (3725,'8417832','123456','788884','123456',NULL,'20210922SIHBIDJ1010O0100000050','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200001148',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUl1vozAQ/CsnP5NqbUhD8kaAtL5rIAo+9eHUhxyEHFUwCNyvi/rfb21wK1Vtr0bY2J7dmR32
RJZ3/bo/kMWJBG17WXT6azXMVVUMq+SyV5KbXar+mFu9I2GcBDz6HpBnHA4RzRfjMn65xDg6xi2r
v6iBa3LCgFGYM2ZTA4UtMMBBqecThyAy2pcDuN3l/RkAw5eeUSAmVXafa4pYiKt4HSciTJMV364D
wdMEEWG3j9RINIH5hDEB7gLOFx7KcUjU5Hf1XqqhAtWs+KZWmeq3rTm66KxJRvH7go1afza3bKL+
mC/tDvKIWbksA1kgEVn8Og2nbxmsayMDwJQS5wWa1AY8WuIbS8Anzzf4Xx7fyR7LQjQ4fUSRWpop
WBbM83894tHQkCAUIW61dT02QmmY8VN7t+lU94SYnxiEPXDzkn+7L/V99Ft1QZ4r2zBv2mfm4/CI
6Z2wUOZio570kmirr6uiKZpv2e2u7KojsbBPMvoenfkuG/sxa481Cox2amdUx/L+2GqsJRO4I6Cr
NdE/xGaCLeq5KMrBGgupBg8MRDxIowpcXawzFHdQn6ihzPWm56/1fRWNTuKD4x8Bzxrv
','2021-09-22 03:06:45.016717'),
	 (3734,'8417832','123456','788884','123456',NULL,'20210922SIHBIDJ1010O0100000055','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200001465',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUttuozAQ/ZXVPCfV2JCU5I0A2bq7gSjxPlRVHlLITQoGgdttN+q/79jEeah6G8TF9pk5Zw5z
gsljO2t3MD5BWNc3RWO+pt3zcCi6txKq1UrYVab39tSsIErSUMS3IbxS9EBW38xbipsJ5bFz3uTw
jzQIQw4cOcMR5640MlwgRwrmDwfQA0LGm20Hrtd5e4XI6WZXDMGWWj7lhiKR8ncyS1IZZelULGah
FFlKiKjZxPpM1MdRn3OJ3pgNxx4CaYmr/LHcKN11oKupmJd6qdtFbbd+Ns4kq/h9wTYG3sixyfJj
vqzZqSNVFWobqoKIYHx/6nbfMjjXHAMOhtC7QNPSgs+WBNYSDOB1Rf/l+Z3qiSpkRY+PKLJLIwPH
QnW+1iOfLQ2EkYxoaaxraRC2lpk+jXfzRjcvhPlDSTQDq0v9xWZrzuMH3YR5rt3AvBmf64DCBzs7
UaHtwVy/mFdqrL6t9mv1467aHcBBPqkW+Ow68Ph5Fpf1sSRx8VqvreJEPR1rg3VEklaAjFqz2b/k
vE/j6XskqEf9FUp3/VuI/KusIvSwmy7T2E5/ooZxzycfL719F00u0kXxHywVGU0=
','2021-09-22 03:16:33.945525');
INSERT INTO public.settlement (id,crdt_account_no,crdt_bank_account_no,dbtr_account_no,dbtr_bank_account_no,log_message_id,orgnl_crdt_trn_bizmsgid,orign_bank,recpt_bank,settl_conf_bizmsgid,crdt_account_type,crdt_id,crdt_id_type,dbtr_account_type,dbtr_id,dbtr_id_type,full_message,receive_date) VALUES
	 (3743,'8417832','123456','788884','123456',NULL,'20210922SIHBIDJ1010O0100000060','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200001613',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUttygjAQ/ZXOPmMnCYrUNwSsaSs4mj51fLCg1qkEBtKr4793E0w707GXZUjY5Oyes8vuYfjU
TJoNDPYQVNU4r/XXqF2327zdJZeNktx4qXowt9qDME4CHl0FcEBzQJT/jJvz8RDj6DFuuH1HDVyT
AyOMkgvGbGpCyYwwgkY96oIDiIxW6xZcLbPmnBCGLz2nBEyq+XOmKWIhbuJJnIgwTUZ8NgkETxNE
hPUqUkeiDrnoMCaIO2D9AXMBtURl9lSspGorUOWITws1V82sMkeXtW2SUXxasLGe17NsoviZL603
codZuVwHMkciGNzt29PvDLZrloF4FJxPaFIY8LElvmkJ8eGwwP/yeiJ7LHNR4vITRWppPGJZMM/f
esSroYEgFCG6unUNDsLaMOOn7t20VvUbYm4xCGdg8Zl/tlrr++he1UGWKTsw38an76N1wcxOmCtz
MVVvekt0q8fLfHt2VT6WYBG/JPO7tO+77DiK82pXoLZoqZZGcCyfd5XGWh6BHhBdqIm+FtMOTmfX
RT0OlpdL1ZZvIOJFGkHE1XU6bV0b9Ysaytxuz/sq7b9obCI+aB/OWhjE
','2021-09-22 03:27:26.670158'),
	 (3766,'8417832','123456','788884','123456',NULL,'20210922SIHBIDJ1010O0100000070','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200001359',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUl1PgzAU/SvmPrOlLZuwvTFgWnWwbPXJ+DBhm0tGIVD8Wvzv3pZVE6PTS/hoe+495x7uASZt
M2u2MD5AUFWXea2/pt1zt8u7t+SyUZKbVaoezaleQRgnAY+uAnjHcECU/8xb8ssJ5tFj3mT3hhq4
JgdGGCUjxmxpQsmCMIJB3eEIHEBktN504GqVNX1CGN60TwmYUsunTFPEQtzEszgRYZpM+WIWCJ4m
iAjrdaSORD0y6jEmyPmYueOhC6glKrO2WEvVdaDKKZ8XaqmaRWW2LmprklH8s2ATjHmWTRS/86X1
Vu6xKpebQOZIBOO7Q7f7ncG6ZhmIR8H5hCaFAR8t8Y0lxIf3e/wvLz9Uj2UuSnz8RpFaGo9YFqzz
tx7xYmggCEWIS21dg4OwMcz4qb2b16p+RcwtJuEM3H/WX6w3+jx6UHWQZcoOzLfx8XyMAZjZCXNl
DubqVb8SbXWQ76pW1eXZddu0xQos7kRJf0A932XHgVxW+wIVRiu1MrJj+bSvNNayCVwB0e2a7Gsx
7+GMDlxU5WCTuVSdCQYinqWRRVzdrdN1t1Un1FDmDobnXw3+F41W4oXxAWrLG4I=
','2021-09-22 06:23:57.041291'),
	 (3774,'8417832','123456','788884','123456',NULL,'20210922SIHBIDJ1010O0100000075','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200001034',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUt9vmzAQ/leme06qs6GB5o0AWb0tEBHvaepDBiRDCg4C99ei/u87m7iVqrbrIWzO/u6+7447
weJ2WA17mJ8g6rrrqjdfy3FtmmrclVCDVsJ6uf5jb40HcZpFIvkWwRPZBOTxk3Ebcb2gOHaOWzR/
SYMw5MCRM7zi3KVGhgVyJGPo+TABQib1bgR323K4QOT0sguGYFNt7kpDkUr5I12lmYzzbCmKVSRF
nhEi7utEn4mmeDXlXOJszoM5ekBakmN529ZKjxXo41KsW73RQ9HZo6+9a5JV/LZga8Fl6Nhk+z5f
3u/VgbIKtYtURUQw/3UaT18zuK45BgxmMHmGZq0Fn1sS2pZgCE839F8e3sieqkoeaXmPIn8pxLFQ
nv/rkQ+WBqJYxuSa1g00CDvLTJ+md+te94+E+UlBNAM3z/mLemfuk9+6j8pSu4F5NT5BSOaDnZ24
0vZirR/NlplWF7VqvqT9faPAQT7IFvosCD1+nsVNd2hJXLLVW6s4VXeHzmAdkSQPkFFpNvq7XE9p
PH0vNMNZDJXSY/0WIu+VVYSeKXQyFrbXH6hh3PMvZy+1fRZNXaSH7B9I1Rlj
','2021-09-22 06:27:06.947565'),
	 (3782,'8417832','123456','788884','123456',NULL,'20210922SIHBIDJ1010O0100000080','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200001540',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUlFP4zAM/ivIzx1y0g7K3rq2O8Ld2mkLD6cTD7t2G5PWtGoDB0z8d5y0AQkBh6smTfzZ32fX
R5jedfNuB5MjRE1zWbbma9av+33Z70qoTithT7m+tV5zgjjNIpFcRfBM5oGsvxm3EpdTimND3HT/
RBqEIQeOnOEF5y41MlwiRzI2DhA8IGSy2fbgZl10p4icXnbKjJdSre4LQ5FK+Sudp5mM82wmlvNI
ijwjRNxuEj0QjfBixLnEswkPJwHJ8SCpi7tqo3Rfga5nYlHple6Wjb360bomWcUfC7ZqfT52bLL6
nC9vd+pAWYXaRqokIpj8Ofa37xlc1wYGxJCB9wrNKgseWhLalmAIzzf0Xx4+yJ6qUta0fEaRO5oQ
HQvl+b8e+WBpIIplTEfTuo4GYWuZ6dP0btHq9pEw1xREM3Dzmn+52Rp/8le3UVFoNzDvxuc8JAvA
zk5cautY6EezZabVv+vd/uSqvl0rcJAvsoUBOw99PsziqjlUJC5Z67VVnKr7Q2OwjkjSCdBUaqN/
ysWIxjPwSZBH9ZVK9/VbiPynrCL0TaFeX9hOf6GGcT8Yn73V9l00dZEeshcpDhlL
','2021-09-22 06:28:44.769372'),
	 (3790,'8417832','123456','788884','123456',NULL,'20210922SIHBIDJ1010O0100000085','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200001795',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUu9vmzAQ/Vem+0yqsyEJyTcCZHW3QJR4n6Z+yICkkYJB4Hbtov7vO5u4k6q26yF+2H53793j
zrC471f9AeZniNr2uuzM13J4Ho/l8FZC9VoJu8r1nT01K4jTLBLJTQTPFB7I5pN5W3G9oDx2yVsc
/5AGYciBI2c449yVRoYb5EjBprMxeEDIpNoP4HZX9FeInG52xRBsqe1DYShSKb+nqzSTcZ4txWYV
SZFnhIi7KtEXohHORpxLnMx9nLMASEvSFPd1pfTQgW6WYl3rre43rd362jmTrOK3BduYTtCxyfp9
vrw7qBNVFWofqZKIYP7zPOy+ZnCuOQYMJ+C9QLPagi+WhNYSDOH5lv7L4xvVU1XKhh7vUeSOJhw7
Fqrzfz3y0dJAFMuYlsa6ngZhb5np03i37nT3RJgflEQzcPtSf1PtzXnyS3dRUWg3MK/GZxpSBGBn
Jy61PVjrJ/PKjNXUald9uWnudgoc5oNyYcCmoc8vw7htTzWpS3Z6ZyWn6uHUGqxjkrQCZNSbzf4m
1yOaz8AnRR41WCo9GGAh8reyktA3nXpDZwf9gRrG/WA8+dfcZ9FkI10UfwFpNxms
','2021-09-22 06:30:17.813738'),
	 (3798,'8417832','123456','788884','123456',NULL,'20210922SIHBIDJ1010O0100000090','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200000522',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUstu2zAQ/JViz3KwpJxY9o2W5IZtLRk2eypyUCTbNWDRgsS8auTfu6TEFAiSNCvoQXJ2Z3a0
Z5jfdctuD7MziKa5rlr7teifh0PVv7XUndHSrXLz253aFcRpJmTyTcAzRQDq9Mm8jbyeUx4b8uaH
P6RBWnLgyBlOOfelkeEaOdq45BwCIGSy3fXgpii7C0RON7tgCK7U5r60FKlSP9Jlmqk4zxZyvRRK
5hkh4nabmIFohNMR5wqvZiGfsQmQluRU3tVbbfoOzGkhV7XZmG7duK2vrTfJKX5bsA3GJ9yzqfp9
vrzd6yNVlXondEVEMPt17ndfM3jXBgbEKYPgBZrVDjxYEjlLMILnG/ovj29UT3WlTvR4jyL3NFP0
LFTn/3rUo6MBEauYlta6jgZh55jp03q3ak37RJiflEQzcPNSf73d2fPk1rSiLI0fmFfjM4koxuBm
J66MO1iZJ/vKrNWivi3ah8IcvoiqqMHDPqgYjdkkCvkwj5vmWJPApDCFU53q+2NjsZ5M0QrQduuy
v6vViEZ0HJKogHqstOk9cBD1oJ0qDG2zQd/c3nyghvFwfHn1r7/PoslJuij+AtzKGs0=
','2021-09-22 06:32:21.323804'),
	 (3806,'8417832','123456','788884','123456',NULL,'20210922SIHBIDJ1010O0100000095','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200000941',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUttuozAQ/ZVqnkllG5KQvBEgrdsNRMH7VPUhC0k2UjAITLdt1H/v2OCuVPU2iIvtM3POHOYM
i65dtQeYnyGo6+ui0V/L/nk8Fv1bctkqyc0qVX/NqV5BGCcBj24CeMFwQFQ/zMv49QLz6JC3OD6j
Bq7JgRFGyYwxW5pQsiGM6Jh5FBxAZLTb9+B6m7eXhDC86SUlYEplD7mmiIX4Fa/iRIRpsuSbVSB4
miAibHaRGohGZDZiTJDJ3PXmdAqoJaryrtxJ1XegqiVflypT7aY2W1eNNcko/liwDsro2LKJ8nO+
tDnIE1blch/IAolgfnfud98zWNcGBjRkAs4bNCkNeLDEN5YQH17u8b88flA9loWo8PEZRWppZmPL
gnW+1yMeDQ0EoQhxqa1rcRD2hhk/tXfrRjVPiPmNSTgD92/1N7u9Po/+qCbIc2UH5t34TH0MD8zs
hIUyB2v1pF+Jtvq2a7tye5F17fFUgUV9UdD36NR32TCOWX0qUV+0VVsjOpYPp1pjLZfAFRA9jCb7
VqxHOKGei5ocbLGQqrfAQMQ/aUQRV/fq9L0d1BdqKHO98eR/ez9Fo5F4YbwCI2kazA==
','2021-09-22 06:34:21.266213'),
	 (3814,'84122222','123456','783784','123456',NULL,'20210922SIHBIDJ1010O0100000100','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200000545',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUl1vozAQ/CunfU4q25CU5o0Aad1eIAL3qeoDhSSNFAwCp9dc1P/etcGtVPVrERjj2Z3ZYU8w
P3TLbguzE/hNc1W2+m3RP3e7sl8ll52S3OwS9WhO9Q6CKPZ5eO3DC8YIRP3LvIxfzTGPDnnz3X/U
wDU5MMIouWDMliaUpIQRHRN3AiNAZLje9OAmL7ozQhje9IwSMKWyp0JTREL8jZZRLIIkXvB06Que
xIgI2nWoBqIxuRgzJsh05tIZnQJqCeviUK2l6jtQ9YKvKpWpLm3Mp8vWmmQUfy5YB6WOa9lE9TVf
0m7lHqtyufFliUQwuzv1Xz8yWNcGBlwojN6gcWXAgyWesYR48HKP/+X5k+qRLEWNj68oEtsIIZYF
6/ysRzwbGvADEeBWW9fhIGwMM75q71atao+IucUsnIH7t/rpeqPPwwfV+kWh7MB8GJ9zzzn3XDCz
E5TKHKzUUS+xtjo75lV32P+5rh9zCRb1TUHPpUzHMI9Zs69QYJir3KiO5NO+0WBLJnAHpluTfiNW
YxxR1/H0L0+7Uqreg96Qf9KoIo5udtQ3t1XfyKHMcSfT9/5+i0Yn8cJ4Ba7/Gqg=
','2021-09-22 06:41:20.324347'),
	 (3822,'84122222','123456','783784','123456',NULL,'20210922SIHBIDJ1010O0100000105','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200000949',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUl1vozAQ/CvVPpPKdmgKeXOAXN02ECXuU9WHHCS5SMEgcPoV9b93bXAqVW2vi8AYz+7MDnuE
yaGdtVsYH4HX9VXRmLdp99ztim5VQrVaCbvL9D97anYQJSkX8TWHNwwPZPXLvKW4mmAe7fMmu1fU
IAw5MMIoCRlzpQklC8KIidAPwQNExutNB65XeXtOCMObnlMCttTyMTcUiZS3ySxJZZSlU7GYcSmy
FBFRs451TzQg4YAxSUZjn44ZA9QSV/mhXCvddaCrqZiXeqnbRW0//WmcSVbx14JN0DAMHJssv+fL
mq3aY1WhNlwVSATj+2P39TODc61nwGUE3gmalhbcWxJYS0gAbw/4X56/qJ6oQlb4+I4ic42QC8eC
df6vRz5bGuCRjHBrrGtxEDaWGV+Nd/NGNy+IucMsnIGHU/3FemPO47+64Xmu3cB8Gp/LYHgZ+GBn
Jyq0PZjrF7OkxmqudKXOeLGrD7qpwMF+qBj4lJnoB3JZ70tUGK/0yspO1OO+NmDHJnEHhGJ/Nv1G
zgc4o/4QVXnYZKF0Z4KFyCdlZZGh6dbrutvqH+RQNvQvRh8N/haNVuKF8Q42VRs1
','2021-09-22 06:41:26.296857'),
	 (3829,'262222','123456','783784','123456',NULL,'20210922SIHBIDJ1010O0100000110','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200000988',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUlFvmzAQ/iuTn5Pq7KQJyRsB0rpdIEo89WHaQ4aTDCkYBG7XLup/753Bm1S1XQ9hY/vzfd99
3Jkt7ttVe2TzMwvr+lo39LXsxqLQ3Wykaa2RbpXZX+6UVixK0lDGNyF7xhgwVX3y3lZeL/Ae7+8t
ij+oQRI5EyA4zITwqYHDBgRQzIKADRgi4/2hA9e7vL0AEPjyCw7Mpdo+5ESRKPU1WSWpirJ0KTer
UMksRUTU7GPbEw1hNhRCwWQ+ns4BGGqJq/y+3BvbVWCrpVyXdmvbTe22rhpvklP8tmAKzgPu2VT5
Pl/WHM0Js0pzCI1GIjb/fu52XzN413oGpCAGD01LB+4tCZwlELDnH/hfHt/InhitKhzeo8j+FgKe
BfP8X496dDQsjFSES7KuxUY4OGb8JO/WjW2eEPMNyASS2Off7A90Hv+0TZjn1jfMq/aZBqNpMGau
dyJt3cHaPtGUktV3hdHFl7tCV7piHvRBPjERGH0zbutTierind05yYl5ONUE9UwKVwyoVHf5Vq2H
2J/jESoaYIHa2M4AB1G/jZMEo769qLKj/UAMF6Px5eRfcZ9Fo434YLwAfj8Z0g==
','2021-09-22 06:47:04.408034');
INSERT INTO public.settlement (id,crdt_account_no,crdt_bank_account_no,dbtr_account_no,dbtr_bank_account_no,log_message_id,orgnl_crdt_trn_bizmsgid,orign_bank,recpt_bank,settl_conf_bizmsgid,crdt_account_type,crdt_id,crdt_id_type,dbtr_account_type,dbtr_id,dbtr_id_type,full_message,receive_date) VALUES
	 (3838,'262222','123456','783784','123456',NULL,'20210922SIHBIDJ1010O0100000114','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200001197',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUl1PgzAU/SvmPm9L2zHH9saAadXBwuqT8WHCNpeMQqD4tfjfvS1UE6PTElrannvPuYd7hFlT
L+odTI/gleVlVumveTvv91m7Si5rJbnZxerR3Ood+GHk8eDKg3ccPRDFP+NW/HKGcbSLm+3fUAPX
5MAIo2TCmE1NKEkIIzgonYyhB4gMNtsWXK7TekAIw5cOKAGTavWUaopQiJtwEUbCj6M5Txae4HGE
CL/aBKoj6pNJnzFByZSOpqMJoJagSJt8I1VbgSrmfJmrlaqT0hxdVNYko/hnwUatQ8eWTeS/88XV
Th4wK5dbT2ZIBNO7Y3v6ncG61jGgISPofUKj3IA7S1xjCXHh/R7/y8sP2UOZiQKn3yhiWwh1LAvm
+VuPeDE04PnCx622rsZG2Bpm/NTeLStVvSLmFqOwB+4/8yebrb4PHlTlpamyDfOtfcbucOw6YHrH
z5S5WKpXvUTa6uumbvL1mZfty0ZVBVjciZTsnOHo+nFVHnIUGKzV2qgO5dOh1FBLJnAHhGJ5Jvha
LPvYos7Q1VYldSZV64GBiGdpVJGhLrbXFrdTJ8RQNnRG51/1/ReNTuKD4wNaXhs+
','2021-09-22 10:16:02.746544'),
	 (3930,'262222','123456','783784','123456',NULL,'20210922SIHBIDJ1010O0100000203','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200001526',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUttuozAQ/ZXVPCeVbXKheSNAtm43EBH3qeoDC0k2UjAIpt12o/77jg3uSlXb7SAuts/MOXOY
MywfunV3gMUZgqa5Klvzteqfx2PZv7XUHWppVyn+sqdmBWGcBDK6DuCFYgSq/mLeVl4tKY8Pecvj
H9IgDTkIJji7FMKVZpxlTDAKPhUzGAEho92+Bzd50V0wJujmF5yBLbV9LAxFrNSPeB0nKkyTlczW
gZJpQoiw3UU4EI3Z5VgIxWcLNl1MPSAtUV08VDuNfQdYr+Smwi12WWO3vrfOJKv4fcE2xHzi2FT1
MV/aHvSJqkq9D3RJRLC4O/e7bxmca68MzDA4aFJZ8GCJby1hPrzc0395eqd6rEtV0+MjinSgEcxz
LFTn/3rUk6WBIFQhLY11HQ3C3jLTp/Fu02L7TJhbyqIZuH+tn+325jz6iW1QFOgG5s34zH1v7k/A
zk5Yoj3Y4LN5JcZqmmnMq+M3qcu8xRoc7pOSYiYohnncNqeKBEY55lZ1rB9PjYE6MkUrYJzas8k3
ajOmEZ14vjEg60qNvQcWon5rq4p5ptlR39wBPxHDhTeZzv7191U0OUkXxV8smBsd
','2021-09-22 16:05:57.159551'),
	 (3941,'262222','123456','783784','123456',NULL,'20210922SIHBIDJ1010O0100000210','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200001495',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUstuqzAQ/ZWrWZPKdiCvHQHSum0gStxFVXWRQpJGCgaB+7pR/73jAVqp6ssIG9tn5pw5zBGm
D/W83sHkCH5ZnmWV/Zo1836fNauWujZa0i4x93RrdxBEsS/Dcx9ecTigij/GreTZFON4Gzfd/0cN
0pKDYIKzsRBdasbZkgmGg7tjDxxAZLjZNuByndYnjAl8+QlnQKlWj6mliJS6jOZRrIIknsnl3Fcy
iRERVJvQtEQ9Nu4Jofhgwr2JcAG1hEX6kG+0aSowxUwucrMy9bKko9OqM4kUfy2Y1HresGNT+fd8
SbXTB8wq9dbXGRLB5ObYnH5m6FxrGfCUg/MOjXMCt5aMyBI2gtdb/C/PX2SPdKYKnL6jSFoaQb5S
COb5XY96JhrwAxXg1lpXYyNsiRk/rXeLylQviLnCKOyB2/f8y83W3od3pvLT1HQN86l9hqP+cOQC
9U6QGbpYmBe7xNZqqbN1ZYp/18VuDx3oh3xiIHC0zbgqDzmqC9dmTZIj/XgoLbRjUrgDZkul4Au1
6GF/un1U5GCBmTaNAQRRT5oksb6t1Gkq25kfxHDRd73BR3F/RaON+OB4A5RFGeI=
','2021-09-22 16:15:28.536034'),
	 (3948,'262222','123456','783784','123456',NULL,'20210923SIHBIDJ1010O0100000002','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200001189',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUl1PgzAU/SvmPm+mLTjZ3hgwV3WwjPpkfJiwTZJRCHR+Lf53b8uqidnUS/hoe+495x7uHsa7
dtZuYLQHv66neaO/Jt2zKPLuLblsleRmlagnc6pXEESxz8NrHz4weiCqf+alfDrGPHrIGxfvqIFr
cmCEUTJkzJYmlCwIIxiUekPoASLD1boD18usPSeE4U3PKQFTKn3ONEUkxG00i2IRJPGEL2a+4EmM
iKBZhepA1CfDPmOCsZEzHBEXUEtYZbtyJVXXgaomfF6qVLWL2mxdNdYko/i4YBOu61g2UZ7mS5qN
3GJVLte+zJEIRvf7bvcHg2NdswyEaAYLjUsDPljiGUuIBx8P+F9ej1SPZC4qfJyiSL5omGXBOn/r
Ea+GBvxABLjU1rU4CGvDjJ/au3mjmjfE3GEOzsDDV/3Faq3Pw0fV+Fmm7MD8GJ9Lz7n0XDCzE+TK
HMzVm37F2uqoeSnkWbpri20FFvRLPTZgGIdhTOttierCpVoayZF83tYaapkEroBQ7M0k34h5H+fT
dVBRDxvMpeoMMBDxIo0k4uhOe11nG/WLGMoc92Lw3dx/0WgjXhifoccZ+A==
','2021-09-22 22:39:09.250832'),
	 (3955,'262222','123456','783784','123456',NULL,'20210923SIHBIDJ1010O0100000005','CENAIDJA','SIHBIDJ1','20210922CENAIDJA010R0200001334',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUm1PgzAQ/ivmPm9LW9jEfWPAtOpgYTV+MH5A2ObiKATq6+J/91pWTYwvO0LLtc/d89xxO5g8
trN2DeMd+HV9VjT6a9qtm03R7ZLLVkluvETdm1vtQRDFPg/PfXhH64GoDoxb8LMJxtF93GTzhhq4
JgdGGCUnjNnUhJKUMIJGHceFHiAyXK46cJ3l7YAQhi8dUAIm1eIp1xSREJfRLIpFkMRTns58wZMY
EUGzDNWeqE9O+owJxsZDOmYopwdhlT+WS6m6ClQ15fNSLVSb1ubotLFNMop/FmyMuSPLJsrf+ZJm
LbeYlcuVLwskgvHNrjv9xuDYrlkGQjSDhcalAe9b4pmWEA/eb/G/vPyQPZKFqHD5jSL5pBlaFszz
vx7xYmjAD0SArm5di4OwMsz4qXs3b1TzipgrjMEZuP3Mny5X+j68U42f58oOzLfxOfacY88FMztB
oczFXL3qLdatjh6qo+tNkz1nEizmj3RsxND2s7iotyWKCzOVGcWRfNrWGmqJBHpAKJZmgi/EvI/j
6TqeHs60LaTq6jcQ8SyNIuLoQntdYWv1hxjKHHc4+qrtUDR2ER+0Dx7wGWA=
','2021-09-22 22:51:25.5486'),
	 (3962,'262222','123456','783784','123456',NULL,'20210923SIHBIDJ1010O0100000008','CENAIDJA','SIHBIDJ1','20210923CENAIDJA010R0200001434',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUl1vgjAU/SvLfcalFHToGwLObhMMdk/LHhygM5FKSvdp9t93W8AlRrddwkfbc+8593D3MH6p
Z/UaRnvwq2qaS/01aZ6bTd68BRO1EsysEvVsTvUKgij2WXjjwxeGBXz3z7wFm44xz27zxptP1MA0
OVBCbTKkTlea2CQllGDYruOCBYgMi1UDrpZZfUkIxdu+tAmYUovXTFNEnN9FsyjmQRJPWDrzOUti
RASyCFVL1CPDHnU4IaM+HfVRjgXhLnspC6GaDtRuwualWqg6rczWtexMMopPCzZqh67dsfHyPF8i
12KLVZlY+SJHIhg97JvdY4bOtZYBYwjWARqXBtxa4hlLiAdfj/hf3k9Uj0TOd/g4R5EcaLyOBev8
rYe/GxrwAx7gUltX4yCsDDN+au/mUskPxNxjDs7A46F+Wqz0efikpJ9lqhuYo/G58pwrzwUzO0Gu
zMFcfehXrK3GVmWxvIjk20ZAB/qlHh1QjHYYF9W2RHXhUi2N5Ei8bisN7Zg4roDon2uSb/m8h/Pp
Op6ezrTOhWoMMBD+Jowk4uhOraaztfpFjE0dtz/4ae6/aLQRL4xvac4ZyQ==
','2021-09-23 00:52:55.597931'),
	 (3987,'262222','123456','783784','123456',NULL,'20210923SIHBIDJ1010O0100000022','CENAIDJA','SIHBIDJ1','20210923CENAIDJA010R0200000495',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUttygjAQ/ZVOntVJAtbLGwLWtBUcTZ86fbCA1hkJDGyvTv+9m0DaGce2LsMlydk9Zw97IJPn
el5vyfhAvLKcpZX+mjbP3S5t3kqoGpQwqxiezKleET+MPBFce+QTo0NkcWbeSswmmMfavMnuAzUI
TU445YyOuGNLU0aXlFMd7qhPOgSRQbZpwOU6qXuUcrxZj1FiSq1eEk0RSnkbzsNI+nE0Fcu5J0Uc
IcKvsgBaoi4ddbkjqTNm/TEdEtQSFMlzniloOoBiKhY5rKBelmbrqrImGcWnBetgA+pYNpn/zhdX
W7XHqkJtPJUiERnfH5rdYwbrWsuAfWsGC41yA24tGRpLNMUD/pe3E9VDlcoCH79RxJaGc8uCdf7X
I98MDfF86eNSW1fjIGwMM35q7xYVVO+IucMknIGH7/rLbKPPg0eovCQBOzBH4zMYOoOhS8zs+CmY
gwW861ekrfYUFOpCqHRdQUEs6o+C/JJjtNO4Kvc5ygvWsDaaQ/WyLzXUUklcEcqwOZN8IxddHFDX
QUkd7DBV0DhgIPJVGU3U0a12mta28IcYxh23f/nT3blo9BEvjC+/JBpI
','2021-09-23 03:15:12.284702'),
	 (3992,'262222','123456','783784','123456',NULL,'20210923SIHBIDJ1010O0100000024','CENAIDJA','SIHBIDJ1','20210923CENAIDJA010R0200001847',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUtFumzAU/ZXqPieVbWhC8kaArN4WiMDTHqY+MCAZUnAQOF3bKP/ea4M7qWq7GmFj+9x7zj3c
M6xO/abfw/IMftvelp3+Wg9zXZfDKrnsleRml6g/5lbvIIhin4dffbjgmIA4fjIu47crjKNj3Kp+
Qg1ckwMjjJIFc2xqQklKGMFBPXcOE0BkWO0GcJsX/TUhDF96TQmYVNl9oSkiIb5HmygWQRKvebrx
BU9iRARdFaqRaEoWU+YI4ixdsqQzQC3hsTg1lVRDBeq45ttGZapPW3P0pbMmGcVvCzZq6cKxbKJ5
ny/p9vKAWbnc+bJEIlj+Og+nrxmsayMD1n0Dkxdo3BjwaIlnLCEeXO7wvzy8kT2SpTji9B5FYmmY
a1kwz//1iAdDA34gAtxq63pshJ1hxk/t3bZT3SNifmAQ9sDdS/602un78Lfq/KJQtmFetc/cc+ae
C6Z3glKZi6161Eusrc7y5lRWXX71s5ZlDRb2QUY2YzjGdszaQ4P6wlzlRnQk7w+thlougTsgFKsz
wd/Edood6jqedirtS6kGCwxE/JVGFHF0rZOhtr36QAxljnsz+1feZ9FoJD44ngHzMBqp
','2021-09-23 03:40:20.087179'),
	 (3996,'262222','123456','783784','123456',NULL,'20210923SIHBIDJ1010O0100000026','CENAIDJA','SIHBIDJ1','20210923CENAIDJA010R0200001527',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUl1vozAQ/CvVPieVsUlI80aAtL67QJS4T1UfckDSSMEgcHtto/73rpf4ToraXhfxYXt2Z3bY
I8weu0W3g+kRwqa5KVr7Ne+f+33Rv7XUndGSVpl5oFO7gihJQxn/COENYwCq/mbeWt7MMM875c32
r6hBWnLgjHvsigtXmnlsxTjD8EY8gAEgMi63PbjZ5N0lYxxv79JjQKXWT7mlSJT6lSySVEVZOper
RahkliIiasvYnIiG7GrIhWJi6vtTMQLUEtf5Y1Vq03dg6rlcVmZtulVDW9etM4kUfyyYQgSBY1PV
53xZu9MHrCr1NtQFEsH07tjvnjM41xwDIz8cNK0IfLJkQpawCbzd4395/qB6ogtV4+MziszR8LFj
wTr/16OeiQbCSEW4tNZ1OAhbYsZP692yNe0LYm4xCWfg/m/9Vbm15/Fv04Z5btzAnI1PMBHBxAea
nagwdLA0L/aVWqux1ba8CLWpNTjMF+X4mGOcZnHdHCoUF2/MhhQn+unQWKgjUrgC5mFrlPxTLYc4
nr5AQQPsr9Cm758g6o8mRUzYRgd9YzvzhRiPC380/tfbd9HoIl4Y72QkGXQ=
','2021-09-23 03:44:39.515655'),
	 (4003,'262222','123456','783784','123456',NULL,'20210923SIHBIDJ1010O0100000029','CENAIDJA','SIHBIDJ1','20210923CENAIDJA010R0200001963',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUttuozAQ/ZVqnpPK2DQleSNAWnc3ECXep6oPWUiykYJBxr1G/fcdD/GuVPU2iIvtM3POHOYI
0/tu3u1gcoS4ba8r475m/XO/r/q3lrqzWtKqsH/o1K0gyfJYpjcxvGIMQDXfzFvJ6ynmBae86f4F
NUhHDpzxgI258KVZwJaMM4xgPBIwAESmm20Pbtdld84Yxzs4DxhQqdVD6SgypX5m8yxXSZHP5HIe
K1nkiEjMJrUnoiEbD7lQTEzCaCJCQC1pU97XG237Dmwzk4varmy3bGnryniTSPH7gikueOTZVP0x
X2F2+oBVpd7GukIimNwe+923DN41z8CE69hD85rAJ0sisoRF8HqH/+XpneqZrlSDj48oCk/Dx54F
63ytRz0RDcSJSnDprOtwELbEjJ/Ou4Wx5hkxvzAJZ+DuX/3lZuvO09/WxGVp/cC8GZ/LSFxGIdDs
JJWlg4V9dq/cWS11tTa2OcvM416DR31SkI84xmkaV+2hRnnp2q5Jc6YfDq2DeiqFK2ABNkfJP9Ri
iAMaCpQ0wA4rbXsHCKIeNWlCcxjNl2ttZz8RE3ARXoz+d/ddNPqIF8Zf8BoaXw==
','2021-09-23 03:48:38.169429');
INSERT INTO public.settlement (id,crdt_account_no,crdt_bank_account_no,dbtr_account_no,dbtr_bank_account_no,log_message_id,orgnl_crdt_trn_bizmsgid,orign_bank,recpt_bank,settl_conf_bizmsgid,crdt_account_type,crdt_id,crdt_id_type,dbtr_account_type,dbtr_id,dbtr_id_type,full_message,receive_date) VALUES
	 (4010,'262222','123456','783784','123456',NULL,'20210923SIHBIDJ1010O0100000032','CENAIDJA','SIHBIDJ1','20210923CENAIDJA010R0200001645',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUttuozAQ/ZXVPJNqbOfWvBEgW283ECWu+lD1IQtJNlJwEDjddqP++44N7kpVb0bY2D4z58xh
zjA9NfNmB5MzhFV1VdT2a9bO+33Rrlrqxmjpdpn57W7tDqIkDWX8I4RnGgGo4xfjVvJqSnGsi5vu
/5IGacmBI2d4yYVPjQyXyJEGG/YHEAAh4822BVfrvLlA5PSyC4bgUq0eckuRKPUzmSepirJ0Jpfz
UMksJURUb2LTEfXwsseFQjEZ8AkbAmmJj/mp3GjTVmCOM7kozco0y8odfa+9SU7x24KdWhyhZ1Pl
+3xZvdMHyir1NtQFEcHk7tyevmbwrnUMiEJA8AJNSwfuLBk7S3AMz/f0Xx7fyJ7oQh1peo8i8zSC
exbK87ke9ehoIIxURFtrXUONsHXM9Gm9W9SmfiLMDQVRD9y/5F9utvY+/mXqMM+Nb5hX7TMai9G4
D653osK4i4V5sktqrb4+Nady/e12r9dmDR71QUI+5DS6blxVh5LkxTbWak70w6GyUE+laAfIqDgX
fK0WPWrQviBJAVVYaNM64CDqj3aaUNhSg7a0nflADOOiPxj+r+6raPKRHhr/AMC1Gk8=
','2021-09-23 03:52:19.96604'),
	 (4015,'262222','123456','783784','123456',NULL,'20210923SIHBIDJ1010O0100000034','CENAIDJA','SIHBIDJ1','20210923CENAIDJA010R0200000018',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUstugzAQ/JVqz6QyNmlJbgRI67aBiLinqgcKJI0UHAROX1H/vWsTp1LV1yIetmd3Zofdw2TX
zboVjPcQNM1l2eqvaf9cr8v+LbnslORmlapHc6pXEMZJwKOrAN4xHBDbf+Yt+OUE89xD3mT9hhq4
JgdKqEtGlNnSxCUZocSE64MDiIyqZQ9u8qI7JYTi7Z66BEypxVOhKWIhbuJZnIgwTaY8mwWCpwki
wraK1IFoQEYDygRh4yEdU5TjQLQtdnUlVd+B2k75vFYL1WWN2bporUlG8feCTQxHnmUT9c98abuS
G6zK5TKQJRLB+G7f735lsK5ZBsKG4ByhSW3AB0t8Ywnx4f0e/8vLN9VjWYotPn6iSC0N8ywL1vlb
j3gxNBCEIsSltq7DQVgaZvzU3s1b1b4i5haTcAbuj/WzaqnPowfVBkWh7MB8GZ9zn537HpjZCUtl
DubqVb8SbfX1rtvV+Ql23FY5WNQvBekZxThM46LZ1CgvylVuNMfyadNoqKUSuALiYnMm+VrMBzig
HvO1UVlXStU7YCDiWRpNhOlWnb61lfpFjEuZNzz77O6/aPQRL4wPoUwaNw==
','2021-09-23 03:52:25.274696'),
	 (4022,'262222','123456','783784','123456',NULL,'20210923SIHBIDJ1010O0100000037','CENAIDJA','SIHBIDJ1','20210923CENAIDJA010R0200001860',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUm1vgjAQ/ivLfcaltL4wvyHg7DbBaJd9WPbBgToSqQSq0xn/+67FbsmytyO0tPfcPc8dd4TB
th7XK+gfwS/LUVbpr2Gz5nnW7JLLWkluTol6MV59giCKfR7e+HBCc0Bs/hk346MBxrnnuEH+hhq4
JgdKqEuuKLOpiUumhBI01+sScACR4WLZgMt5Wl8SQvF1L13txVSzXaopIiHuonEUiyCJh3w69gVP
YkQE1SJUZ6IWuWpRJgjrd2ifMUAt4SbdFgupmgrUZsgnhZqpelqaq+vKNsko/l6wMdrtWDZR/MyX
VCu5xqxcLn2ZIRH0H4/N7VcG2zXLQJgHzgc0Lgz43BLPtIR4cHrC/7L/JnskM7HB5SeKxNKwnmXB
PH/rEXtDA34gAjzq1tU4CEvDjJ+6d5NKVQfE3GMQzsDTR/7pYqn94bOq/DRVdmC+jE/PYz2vDWZ2
gkwZx0Qd9BbrVo/mWX7xkMu5moPF/JKOdinaeRZn5bpAcaGO1YojuVuXGmqJBJ6AuFiaCb4VkxaO
Z5uhIAfry6Rq6jcQ8SqNIsJ0oU5T2Er9IsalrN3pftb2XzR2ER+0d0XgGWA=
','2021-09-23 03:52:37.228299'),
	 (4027,'262222','123456','783784','123456',NULL,'20210923SIHBIDJ1010O0100000039','CENAIDJA','SIHBIDJ1','20210923CENAIDJA010R0200001446',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUttSgzAQ/RVnn1tnCVjbvlGgGrXQKfHJ8aECrR1LYCD11vHf3YRGZxxvy5Cwydk9Z5fdw2TX
zto1jPfg1/V53uivabduNnm3Sy5bJbnxEnVvbrUHQRT7PLzw4Y2sB6L6Z1zKzycU5xziJptX0sA1
OTBkDo6Ya1OjgwtkSOZ43gB6QMiwWHXgepm1x4iMXufYQTCp0sdMU0RCXEWzKBZBEk/5YuYLnsSE
CJoiVAeiPo76zBXojZGNmQekJayyXVlI1VWgqimflypV7aI2R2eNbZJR/L1gYwNEyybKn/mSZi23
lJXLlS9zIoLxzb47/cpgu2YZ0NMMFhqXBnxoydC0BIfwdkv/5fmb7JHMRUXLTxSJpXFHloXy/K1H
PBsa8AMRkKtb19IgrAwzferezRvVvBDmmoJoBm4/8i+Klb4P71TjZ5myA/NlfE6H7unQAzM7Qa7M
xVy96C3Wrb6oHqqjdNcWsgKL+SUdGzCywyym9bYkceFSLY3iSD5uaw21RII8QIdKM8GXYt6n8fRc
EtSj+nKpuvoNRDxJowhdXWivK2ytfhHjMNc7GXzW9l80dZEesnc+qRl7
','2021-09-23 04:02:28.132197'),
	 (4034,'262222','123456','783784','123456',NULL,'20210923SIHBIDJ1010O0100000042','CENAIDJA','SIHBIDJ1','20210923CENAIDJA010R0200001338',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUu9vmzAQ/Vem+0yqw7CU5hsBsnpbIArep6ofMkiySMEgcLp2Uf/3ng/opKq/jLCx/e7eu8ed
YX7qlt0eZmcIm+a6bO3Xop8Ph7JftdSd0ZJ3mfnDt3YHUZKGMv4ewiMNB1T9ybhcXs8pzh3i5od/
pEFachAoXLwS3pgaXVyjQBqu5wXgACHj7a4HN5uiu0AU9LoXLgKnyu8KS5Eo9TNZJqmKsnQh18tQ
ySwlRNRuYzMQTfBqIjyF/gy9GfpAWuK6OFVbbfoKTL2Qq8rkpls3fPStHU1ixa8LZrVTX4xsqnqb
L2v3+khZpd6FuiQimN2c+9OXDKNrAwOi74HzDE0rBg+WBGwJBvB4S//l/pXsiS5VTdNbFNlIw3Vw
COX5WI+6ZxoIIxXR1lrXUSPsmJk+rXer1rQPhPlFQdQDt8/519udvY9/mzYsCjM2zIv2uQy8y8AH
7p2oNHyxMg92Sa3V+ak7HOsvoTa1hhH0Tj4xFTSGZsybY0Xq4o3ZsORE3x0bCx2ZFO0AXaqNg3+o
1UTY2kmRQwWW2vQGMET91SwJPVup01e2N++IcYXnf53+L+6zaLKRHhpPh9oZ7w==
','2021-09-23 04:03:07.797473'),
	 (4039,'262222','123456','783784','123456',NULL,'20210923SIHBIDJ1010O0100000044','CENAIDJA','SIHBIDJ1','20210923CENAIDJA010R0200000054',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUl1vgjAU/StLn3W5FFT0DQFntwkGu4dl2YMDdWZSCHSfZv99t4VuidFtl/DR9tx7zj3cPRk/
17N6Q0Z74pXlNKvU16R5brdZ8xZM1FIwvYrloz5VK+KHkceCS498YnQIL/6Zt2DTMeZZbd54+4Ea
mCInFKgFQ2qb0mBBAhR09BzSIYgMVusGXC7T+hyA4m2dW0B0qcVLqihCzq/DWRhxP44mLJl5nMUR
IvxqFciWqAvDLrU5OCMYjMAlqCUo0ud8JWTTgSwmbJ7LhayTUm9dVMYkrfi4YBXWYNgzbDw/zRdX
G7HDqkysPZEhERnd7ZvdQwbjWssA4CgGA41yDW4tcbUliuIe/8vbkeqhyHiBj1MUsaFxHMOCdf7W
w980DfF87uNSWVfjIKw1M34q7+aVrN4Rc4NJOAP33/WT1VqdBw+y8tJUmoE5GJ+Baw9ch+jZ8TOp
D+byXb0iZfVl8VSc3RabLTGIX4rRPsVoJ3FR7nKUFizlUusNxcuuVFBDw3FFwMLGdPIVn3dxOB3b
VSYldSZk072G8Feh9YCt2uw0bW3kL2Isaju9/k9n/0Wjh3hhfAEGZRi0
','2021-09-23 04:07:12.167375'),
	 (4046,'262222','123456','783784','123456',NULL,'20210923SIHBIDJ1010O0100000047','CENAIDJA','SIHBIDJ1','20210923CENAIDJA010R0200001683',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUttum0AQ/ZVqnnG0F2pjv2HAzaY1WGb7VOWBAnatmgXBOk1q5d8zu3hTKUrSDOKyu2fmnDnM
GZanYT3sYXGGsOuuq958rcbn4VCNbyXUoJWwq0z/sqdmBVGShiK+CeERwwPZfjAvF9dLzKOXvOXh
L2oQhhwYYZTMGXelCSVbwggGnQYcPEBkXO9GcFeUwxUhDG96RQnYUvldaSgSKb8l6ySVUZauxHYd
SpGliIj6OtYXogmZTxiXxF+Q2YLOAbXEbXlqaqXHDnS7EptG53rYdnbrS+9MsopfF2yDc+7YZPM2
X9bv1RGrCrULVYVEsPhxHndfMjjXHAPxA/CeoWljwRdLAmsJCeDxFv/L/SvVE1XJFh9vUWSOxp85
Fqzzfz3y3tJAGMkIl8a6AQdhZ5nx03i36XX/gJjvmIQzcPtcf1vvzHn8U/dhWWo3MC/GZxbwWeCD
nZ2o0vZgox/MKzVW50Vzquq++HTT/m7Bod4pyKYM4zKNeXdsUF5c6MJqTtTdsTNQRyVxBYRiczb5
q9xMcEB9jpI87LBSenTAQuQfZTURblr1xtb2+h0xlHH/8/Rfdx9Fo494YTwB6ZcaVw==
','2021-09-23 04:07:22.771473'),
	 (4053,'262222','123456','783784','123456',NULL,'20210923SIHBIDJ1010O0100000050','CENAIDJA','SIHBIDJ1','20210923CENAIDJA010R0200000842',NULL,NULL,NULL,NULL,NULL,NULL,'eJyNUlFvmzAQ/ivTPZPqbJKG5o0AWb0tECXe09SHDJIsWjAI3K5d1P/e8wGdVLVdD2Fz9nf3fXfc
Gea37bI9wOwMYV1fF437WnTr8Vh0u1GmtUaxl9lffOs8iJI0VPGXEB7JPNDVB+M26npOcaKPmx//
kgblyEGiFHgl/SE1ClyjRGfBWIIHhIx3+w5cb/P2AlHSKy4EAqfa3OWOItH6W7JMUh1l6UKtl6FW
WUqIqNnFtica4dVI+honM5zOUAJpiav8ttwZ21Vgq4ValXZj23XNR5+boUms+HXBzoRgPcymy7f5
suZgTpRVmX1oCiKC2Y9zd/qSYehaz4A4EeA9Q9OSwX1LAm4JBvB4Q//l/pXsiSl0RctbFNlAM8GB
hfL8X4++ZxoIIx2R61rX0iDsmZk+Xe9WjW0eCPOdgmgGbp7zr3d7dx//tE2Y53YYmBfjMw38aTAG
np2osHyxsg9uS12rw2Jbfkp+VzAA3sklLyVZP4ib+lSSsnhrtyw3MXen2kEHFk0eoCuTg7/q1Yhm
c+yTGo+KK4ztimeI/mNYDvquSq+r6mDfESOkP55c/ivso2hqIT1kT1uYF/o=
','2021-09-23 05:07:05.783991');
