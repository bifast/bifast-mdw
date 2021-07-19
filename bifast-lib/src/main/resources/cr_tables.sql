-- public.credit_transfer definition

-- Drop table

-- DROP TABLE public.credit_transfer;

CREATE TABLE public.credit_transfer (
	id int8 NOT NULL,
	settlconf_bizmsgid varchar(255) NULL,
	amount numeric(19, 2) NULL,
	crdttrn_req_bizmsgid varchar(255) NULL,
	crdttrn_resp_bizmsgid varchar(255) NULL,
	crdttrn_resp_status varchar(255) NULL,
	cre_dt timestamp NULL,
	creditor_acct_no varchar(255) NULL,
	debtor_acct_no varchar(255) NULL,
	intr_ref_id varchar(255) NULL,
	msg_type varchar(255) NULL,
	orign_bank varchar(255) NULL,
	recpt_bank varchar(255) NULL,
	CONSTRAINT credit_transfer_pkey PRIMARY KEY (id)
);


-- public.inbound_message definition

-- Drop table

-- DROP TABLE public.inbound_message;

CREATE TABLE public.inbound_message (
	id int8 NOT NULL,
	msg_name varchar(255) NULL,
	bizmsgid varchar(255) NULL,
	bizsvc varchar(255) NULL,
	orgn_bank varchar(255) NULL,
	copy_msg varchar(255) NULL,
	dupl varchar(255) NULL,
	receive_dt timestamp NULL,
	resp_bizmsgidr varchar(250) NULL,
	resp_status varchar(250) NULL,
	CONSTRAINT inbound_message_pkey PRIMARY KEY (id)
);


-- public.message_counter definition

-- Drop table

-- DROP TABLE public.message_counter;

CREATE TABLE public.message_counter (
	tanggal int4 NOT NULL,
	last_number int4 NULL,
	CONSTRAINT message_counter_pkey PRIMARY KEY (tanggal)
);


-- public.outbound_message definition

-- Drop table

-- DROP TABLE public.outbound_message;

CREATE TABLE public.outbound_message (
	id int8 NOT NULL,
	bizmsgid varchar(255) NULL,
	message_name varchar(255) NULL,
	saf_counter int4 NULL,
	send_time timestamp NULL,
	recpt_bank varchar(255) NULL,
	resp_bizmsgid varchar(255) NULL,
	rejct_msg varchar(3000) NULL,
	resp_status varchar(250) NULL,
	CONSTRAINT outbound_message_pkey PRIMARY KEY (id)
);


-- public.settlement definition

-- Drop table

-- DROP TABLE public.settlement;

CREATE TABLE public.settlement (
	id int8 NOT NULL,
	ack varchar(255) NULL,
	orgnl_crdt_trn_bizmsgid varchar(255) NULL,
	reversal_bizmsgid varchar(255) NULL,
	settl_conf_bizmsgid varchar(255) NULL,
	orign_bank varchar(255) NULL,
	recpt_bank varchar(255) NULL,
	for_reversal varchar(255) NULL,
	full_mesg varchar(6000) NULL,
	CONSTRAINT settlement_pk PRIMARY KEY (id)
);

create sequence public.hibernate_sequence;
