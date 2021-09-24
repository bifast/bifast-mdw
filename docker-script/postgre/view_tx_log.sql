drop view vweb_tx_logs;
create or replace view vweb_tx_logs as
select 
	chnl.request_time as timestamp,
	chnl.transaction_id as uuid,
	ae.orign_bank as source_bic,
	ae.recpt_bank as destination_bic,
	chnl.debtor_account_number as source_account_number,
	ae.account_no as destination_account_number,
	ae.amount as amount,
	'IDR' as currency,
	chnl.status  as status_code,
	chnl.error_msg as status_message,
	chnl.debtor_account_name as source_account_name,
	chnl.creditor_account_name as destination_account_name,
	'Account Enquiry' as transaction_type,
	'Outbound' as transaction_direction
from channel_transaction chnl
join account_enquiry ae on ae.chnl_trx_id = chnl.id 
union
select 
	chnl.request_time as timestamp,
	chnl.transaction_id as uuid,
	ct.orign_bank as source_bic,
	ct.recpt_bank as destination_bic,
	ct.debtor_acct_no as source_account_number,
	ct.creditor_acct_no as destination_account_number,
	ct.amount as amount,
	'IDR' as currency,
	chnl.status  as status_code,
	chnl.error_msg as status_message,
	chnl.debtor_account_name as source_account_name,
	chnl.creditor_account_name as destination_account_name,
	'Credit Transfer' as transaction_type,
	'Outbound' as transaction_direction
from channel_transaction chnl
join credit_transfer ct on ct.chnl_trx_id = chnl.id 
union
select 
	chnl.request_time as timestamp,
	chnl.transaction_id as uuid,
	fict.debtor_bic as source_bic,
	fict.credit_bic as destination_bic,
	'' as source_account_number,
	'' as destination_account_number,
	fict.amount as amount,
	'IDR' as currency,
	chnl.status  as status_code,
	chnl.error_msg as status_message,
	'' as source_account_name,
	'' as destination_account_name,
	'Credit Transfer' as transaction_type,
	'Outbound' as transaction_direction
from channel_transaction chnl
join fi_credit_transfer fict on fict.chnl_trx_id = chnl.id
union
select 
	chnl.request_time as timestamp,
	chnl.transaction_id as uuid,
	'SIHBIDJ1' as source_bic,
	chnl.recpt_bank as destination_bic,
	chnl.debtor_account_number as source_account_number,
	chnl.creditor_account_number as destination_account_number,
	chnl.amount as amount,
	'IDR' as currency,
	chnl.status  as status_code,
	chnl.error_msg as status_message,
	chnl.debtor_account_name as source_account_name,
	chnl.creditor_account_name as destination_account_name,
	'FI Credit Transfer' as transaction_type,
	'Outbound' as transaction_direction
from channel_transaction chnl
join payment_status ps on ps.chnl_trx_id = chnl.id ;