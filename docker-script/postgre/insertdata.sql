INSERT INTO public.kc_channel (channel_id,channel_name,channel_type,create_dt,daily_limit_amount,merchant_code,modif_dt,secret_key,transaction_limit_amount) VALUES
	 ('CB','Corebank','03',NULL,NULL,'6010',NULL,'$2a$12$r4o5RRFYnOMhDwg07YzlvebQxWMD8Bh4lDzfD1Ts.HfHunnv0wjiW',NULL),
	 ('CMS','Internet Banking','01',NULL,NULL,'5001',NULL,'$2a$12$r4o5RRFYnOMhDwg07YzlvebQxWMD8Bh4lDzfD1Ts.HfHunnv0wjiW',NULL),
	 ('MB','Mobile Banking','02',NULL,NULL,'5002',NULL,'$2a$12$r4o5RRFYnOMhDwg07YzlvebQxWMD8Bh4lDzfD1Ts.HfHunnv0wjiW',NULL),
	 ('PORTAL','Komi-Portal','04',NULL,NULL,'5003',NULL,'$2a$12$r4o5RRFYnOMhDwg07YzlvebQxWMD8Bh4lDzfD1Ts.HfHunnv0wjiW',NULL);INSERT INTO public.kc_credit_transfer (id,amount,call_status,cihub_elapsed_time,cihub_req_time,req_bizmsgid,resp_bizmsgid,create_dt,creditor_acct_no,creditor_acct_type,creditor_id,creditor_type,debtor_acct_no,debtor_acct_type,debtor_id,debtor_type,error_message,full_request_msg,full_response_msg,komi_trns_id,last_update_dt,msg_type,orign_bank,ps_counter,reason_code,recpt_bank,response_code,reversal) VALUES
	 (1099,4670500.00,'SUCCESS',524,'2021-10-30 19:33:24.396126','20211030SIHBIDJ1010O0130300397','20211030BMNDIDJA010R0200000890','2021-10-30 19:33:23.811','5211896','SVGS','C12103','01','511154','CACC','6055','01',NULL,'eJyVUttu4jAQ/ZWVn2llk4YCb7lQ6qokiHj7gvoQEqBoExM5Q7cs4t87tgmCVS80EiH2zJk5Z+bs
iL+pR/WS9HfEq6r7XOmvO/terXL7L7msQXJziuHFRPWJJPze5+EDI3t8WkSsL8T5oyhEnHfA+at/
yIHr5qRN24xRhzalKaMxZQ7eUKd3S1oEM8P5wiZXaVZfU9rFH7umXYwGah7CocwVo1cOFazXd5x+
2yHYKVxnm3IuwfKD9R0PaihVkINQC305VM0QDKOPCZnH6R37ifKTji0SzeKFeKsxzvCUABQll6aT
+R6BbhE8ToZED8LyEG8mZboj4xLs9AYyF2t8fcbofERY4Dvm2AyLi+pA5jEr9K5UqQ9jBWqLeMp0
WgDL7XijqvMI1UGMcgnK/2O0eKUZ61NabOaYc9O5pS6luBo9pkzDeDgxFV/U0tfrCwe+wGA4AzPx
SI/Ri0Qc4aXVPVavcOqf6cFAHeq6ZP9s3KPhXpZB467/vOYyxtwbQ1aciQi8ICDHAkv4gdfNpi7A
nPrcYo46H+KI//peaMDauMJGqun6hVTcd7fX+UBr8jRMzPUEHWVX/hvXjaApmaXbVKWSPGM4qYoS
EWEKqeEwkK+FKdTsSJfVxsBKdS4hgbo5i7/SCNM2JCdqL0XsUaNWuX8HqhZTbA==
','eJyNUsFuozAQ/RU056QaQ9SS3EhIW7JbEhHvqeqBxiGlAoPA7bYb9d87Y2BVVY1aS2DPeOa952cf
Yf7U3rQHmB0hqOtr1fDqsvvnuepmHenW6MhGa/NgdzmC+U0cRuEqgDcaI5DVD/u20fWc+kTfN8//
kYaIycFFVwj0cIBGgQm6yMOfIoyAKsN91hXX6a49Q3TpE2eCdwlq+7yjrcXvZZBQYtHsQ9PjjgWO
PZTCnXnezJ0AUYfV7qnca9MJNtVltCnN1rRJbVNXzeCJFfi1Ph5CeGJgk+VpvnVz0AWhRjoLtCIi
mN0eu+xnhsGkngG9qQ+j/6VxaYt7B3zrAPrwdkfX8PIF+lIrWdHvFMUahUcZYrkYWAjnez3yxdJA
slpICtm6lu49s8y0ZO82jWleqebPFAWbEChliq6G17nJK50WDmUqJ72/3zlFqx4zZ3IOd3yeXkyy
zxhsoYy9kI155Slms1frOHJisI9pWxcl0YWpSa2GpX4u6o+NkiJAvi17vF9yM6b3NfH8CaWSVmnT
nciWyL/aMrA1jE+C+M2+A/mp7Ec=
','30300395','2021-10-30 19:33:24.400034','Credit Transfer','SIHBIDJ1',NULL,'U901','BMNDIDJA','RJCT',NULL);INSERT INTO public.kc_domain_code (id,grp,"key",value) VALUES
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
INSERT INTO public.kc_domain_code (id,grp,"key",value) VALUES
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
INSERT INTO public.kc_domain_code (id,grp,"key",value) VALUES
	 (104,'CUSTOMER.TYPE','04','Remittance'),
	 (105,'CUSTOMER.TYPE','99','Others'),
	 (301,'CHANNEL.TYPE','01','Internet Banking'),
	 (302,'CHANNEL.TYPE','02','Mobile Banking'),
	 (303,'CHANNEL.TYPE','03','Over the Counter'),
	 (304,'CHANNEL.TYPE','99','Other');INSERT INTO public.kc_fault_class (id,exception_class,reason) VALUES
	 (6,'org.apache.http.NoHttpResponseException','U902'),
	 (8,'org.apache.camel.http.common.HttpOperationFailedException','U902'),
	 (5,'org.apache.http.conn.HttpHostConnectException','K006'),
	 (7,'java.net.SocketException','K010'),
	 (3,'java.net.SocketTimeoutException','K000'),
	 (21,'bifast.outbound.exception.DuplicateIdException','K002'),
	 (1,'com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException','K003'),
	 (2,'com.fasterxml.jackson.core.io.JsonEOFException','K003'),
	 (4,'java.util.NoSuchElementException','K003'),
	 (22,'org.springframework.dao.InvalidDataAccessApiUsageException','K003');
INSERT INTO public.kc_fault_class (id,exception_class,reason) VALUES
	 (23,'com.fasterxml.jackson.databind.exc.MismatchedInputException','K003'),
	 (24,'bifast.outbound.exception.InputValidationException','K003'),
	 (25,'org.springframework.dao.DataIntegrityViolationException','K007');INSERT INTO public.kc_inbound_counter (tanggal,last_number) VALUES
	 (20211001,50000002),
	 (20211002,50000002),
	 (20211027,50000003);INSERT INTO public.kc_message_counter (tanggal,last_number) VALUES
	 (20211025,112),
	 (20211026,145),
	 (20211027,173),
	 (20211029,34),
	 (20211030,398);INSERT INTO public.kc_parameter (id,code,"module",notes,value) VALUES
	 (70,'SLA.CHNL.ENQUIRY','OUTBOUND','SLA ke channel unt call jenis enquiry (ms)','5000'),
	 (20,'LIMIT.DAILY.FREQ','OUTBOUND','Batas berapa kali transaksi perhari','100'),
	 (10,'LIMIT.DAILY.AMOUNT','OUTBOUND','Batas nilai transaksi perhari','1000000000'),
	 (30,'LIMIT.TRNS.AMOUNT','OUTBOUND','Batas nilai per transaksi','100000000'),
	 (40,'PS.RETRY.WARN','OUTBOUND','Berapa kali PaymentStatus SAF sebelum dilaporkan ke admin','5'),
	 (80,'SLA.CHNL.TRANSACTION','OUTBOUND','SLA ke channel untuk call transactional (ms)','10000');INSERT INTO public.kc_status_reason (status_reason_code,description,status_code) VALUES
	 ('U000','Success/ Transaction Accepted','ACSP'),
	 ('U002','Success/ Transaction Accepted Stored in SAF','ACTC'),
	 ('U101','Tenant Not Found','RJCT'),
	 ('U102','Tenant Not Active','RJCT'),
	 ('U103','Tenant Undefined','RJCT'),
	 ('U104','Inbound Scheme Not Found','RJCT'),
	 ('U106','Original Payment Not Found','OTHR'),
	 ('U107','Original Payment Rejected','OTHR'),
	 ('U108','Original Payment Not Responded','OTHR'),
	 ('U111','Minimum Amount Check Failed','RJCT');
INSERT INTO public.kc_status_reason (status_reason_code,description,status_code) VALUES
	 ('U112','Maximum Amount Check Failed','RJCT'),
	 ('U114','Mod 10 check failed','RJCT'),
	 ('U115','Date Sent Tolerance Check failed','RJCT'),
	 ('U116','Date Settlement Tolerance Check failed','RJCT'),
	 ('U117','Original Date Tolerance configuration error','RJCT'),
	 ('U118','Original Date Tolerance check failed','RJCT'),
	 ('U119','Session Validation Failed','RJCT'),
	 ('U121','Inbound Bank Not Found','RJCT'),
	 ('U122','Inbound Bank Not Active','RJCT'),
	 ('U123','Product Undefined','RJCT');
INSERT INTO public.kc_status_reason (status_reason_code,description,status_code) VALUES
	 ('U124','Bank Code Not Found','RJCT'),
	 ('U125','Inbound Bank Id Cannot be Determined (Bank Classifier not found)','RJCT'),
	 ('U126','Outbound Bank Cannot be Determined','RJCT'),
	 ('U128','Outbound Bank Not Active','RJCT'),
	 ('U129','Payee Bank Unavailable','RJCT'),
	 ('U130','Inbound Settlement Bank not found','RJCT'),
	 ('U131','Outbound Settlement Bank not found','RJCT'),
	 ('U132','Inbound Settlement Bank Inactive','RJCT'),
	 ('U134','Outbound Settlement Bank Inactive','RJCT'),
	 ('U135','Pre-Authorization Match Not Found','RJCT');
INSERT INTO public.kc_status_reason (status_reason_code,description,status_code) VALUES
	 ('U136','Pre-Authorization Bank Account Check Failed','RJCT'),
	 ('U137','Pre-Authorization Timeout','RJCT'),
	 ('U138','Pre-Authorization Config Not Found','RJCT'),
	 ('U139','Pre-Authorization Endpoint or Channel Not Configured','RJCT'),
	 ('U140','Pre-Authorization Exception No Response Returned','RJCT'),
	 ('U141','Pre-Authorization Exception','RJCT'),
	 ('U142','Pre-Authorization Stand-In Limit Exceeded','RJCT'),
	 ('U143','Pre-Authorization Stand-In Account Not Found','RJCT'),
	 ('U144','Pre-Authorization Stand-In Account Inactive','RJCT'),
	 ('U145','Pre-Authorization Stand-In Insufficient Funds','RJCT');
INSERT INTO public.kc_status_reason (status_reason_code,description,status_code) VALUES
	 ('U146','Pre-Authorization Stand-In Not Permitted','RJCT'),
	 ('U147','Pre-Authorization No Stand-In Response','RJCT'),
	 ('U149','Duplicate Transaction','RJCT'),
	 ('U150','Notification Configuration Not Found','RJCT'),
	 ('U151','Notification Endpoint Or Channel Not Configured','RJCT'),
	 ('U155','Fraud Check Failed','RJCT'),
	 ('U156','Sanction Check Failed','RJCT'),
	 ('U157','AML Check Failed','RJCT'),
	 ('U159','Pre-Authorization Stand-In Rejected with Default Status','RJCT'),
	 ('U160','Settlement Rules Not Found','RJCT');
INSERT INTO public.kc_status_reason (status_reason_code,description,status_code) VALUES
	 ('U161','Settlement Endpoint Or Channel Not Configured','RJCT'),
	 ('U162','Settlement Confirmation Not Received','RJCT'),
	 ('U163','Forwarded Settlement Confirmation Response Not Received','RJCT'),
	 ('U164','Settlement Total Management Not Configured','RJCT'),
	 ('U170','Online Route Not Found','RJCT'),
	 ('U171','Online Authorization Exception','RJCT'),
	 ('U172','Online Authorization Exception No Response returned','RJCT'),
	 ('U173','Online Authorization Exception Timeout','RJCT'),
	 ('U180','Authorization Service is Not Configured','RJCT'),
	 ('U181','Stand-In Limit Exceeded','RJCT');
INSERT INTO public.kc_status_reason (status_reason_code,description,status_code) VALUES
	 ('U182','Stand In Account Not Found','RJCT'),
	 ('U183','Stand In Account Inactive','RJCT'),
	 ('U184','Stand In Insufficient Funds','RJCT'),
	 ('U185','Stand In Not Permitted','RJCT'),
	 ('U186','Stand In No Response','RJCT'),
	 ('U187','Stand In forwarding Advice cannot be delivered','RJCT'),
	 ('U189','Stand-In Rejected With Default Status','RJCT'),
	 ('U191','Liquidity Rules Not Found','RJCT'),
	 ('U192','Liquidity Position Validation Rule not found (Threshold limits)','RJCT'),
	 ('U193','Liquidity Position Validation Failure','RJCT');
INSERT INTO public.kc_status_reason (status_reason_code,description,status_code) VALUES
	 ('U194','Insufficient Liquidity to process payment','RJCT'),
	 ('U195','Liquidity Forced Payment Rejection','RJCT'),
	 ('U196','Liquidity Parameter Invalid Or Null','RJCT'),
	 ('U197','Liquidity Parameters Not Found','RJCT'),
	 ('U198','Liquidity Validation Not Found','RJCT'),
	 ('U199','Liquidity Position Definition Not Found','RJCT'),
	 ('U200','LQM Maximum Amount Not Found','RJCT'),
	 ('U201','LQM Maximum Amount Exceeded','RJCT'),
	 ('U202','LQM Net Sender Threshold Limit Not Found','RJCT'),
	 ('U203','LQM Insufficient Net Sender Funds','RJCT');
INSERT INTO public.kc_status_reason (status_reason_code,description,status_code) VALUES
	 ('U205','Scheme Settlement Body Unavailable','RJCT'),
	 ('U206','Settlement Rejection','RJCT'),
	 ('U207','Default Settlement Body Not Defined','RJCT'),
	 ('U208','Settlement Forward Confirmation Send Failed','RJCT'),
	 ('U209','Settlement Forward Confirmation Receive Failed','RJCT'),
	 ('U210','Outbound Scheme Not Found','RJCT'),
	 ('U211','Outbound Scheme Not Active','RJCT'),
	 ('U212','Connector Not Found','RJCT'),
	 ('U213','Connector Configuration Error','RJCT'),
	 ('U214','Clearing Rejection','RJCT');
INSERT INTO public.kc_status_reason (status_reason_code,description,status_code) VALUES
	 ('U215','Message Rejected with Admi.002','RJCT'),
	 ('U220','Exception Handler Failure','RJCT'),
	 ('U799','Alias Management Rule not found','RJCT'),
	 ('U801','Addressing Agency cannot be determined','RJCT'),
	 ('U802','Addressing Privilege not defined','RJCT'),
	 ('U803','Insufficient Privilege','RJCT'),
	 ('U804','Alias Not Found Or Inactive','RJCT'),
	 ('U805','Alias Is Suspended / Activated','RJCT'),
	 ('U806','Alias belongs to same FI but different account','RJCT'),
	 ('U807','Alias already registered with another FI','RJCT');
INSERT INTO public.kc_status_reason (status_reason_code,description,status_code) VALUES
	 ('U808','Alias already registered with the same account','RJCT'),
	 ('U809','Not sufficient privilege to perform addressing action on an alias','RJCT'),
	 ('U810','Alias Request Failed','RJCT'),
	 ('U811','Alias suspended by Administrator','RJCT'),
	 ('U812','Alias Destination Not Configured','RJCT'),
	 ('K002','NoRefDuplicate','KSTS'),
	 ('U813','Cache Alias Maintenance Failed','RJCT'),
	 ('U814','Alias already registered with same FI','RJCT'),
	 ('U815','Duplicate Alias Service Request','RJCT'),
	 ('U900','Internal Timeout','RJCT');
INSERT INTO public.kc_status_reason (status_reason_code,description,status_code) VALUES
	 ('U901','System Malfunction','RJCT'),
	 ('U902','Connection Or Communication Error','RJCT'),
	 ('U904','Endpoint Error','RJCT'),
	 ('U990','Session Error','RJCT'),
	 ('U992','Response Not Delivered','RJCT'),
	 ('U993','Reason Code not determined in response ','RJCT'),
	 ('U999','Signature Verification Failure','RJCT'),
	 ('U221','MandateRulesNotFound','RJCT'),
	 ('U222','MandateMasterUpdateError','RJCT'),
	 ('U223','MandateRecordNotFound','RJCT');
INSERT INTO public.kc_status_reason (status_reason_code,description,status_code) VALUES
	 ('U224','MandateStartDateLessThanCurrentDate','RJCT'),
	 ('U225','MandateStartDateFormatError','RJCT'),
	 ('U226','MandateEndDateLessThanCurrentDate','RJCT'),
	 ('U227','MandateEndDateFormatError','RJCT'),
	 ('U228','MandateFrequencyTypeValidationError','RJCT'),
	 ('U229','MandateUpdaterNotKnown','RJCT'),
	 ('U230','MandateSenderNotAllowedToUpdate','RJCT'),
	 ('U231','MandateStatusValidationError','RJCT'),
	 ('U232','AmountGreaterThanMaximumLimit','RJCT'),
	 ('U233','MadateAmountMissing','RJCT');
INSERT INTO public.kc_status_reason (status_reason_code,description,status_code) VALUES
	 ('U234','RequestBeforeMandateStartDate','RJCT'),
	 ('U235','FutureMandateCannotBeUpdated','RJCT'),
	 ('U236','ExpiredMandateCannotBeUpdated','RJCT'),
	 ('U237','CancelledMandateCannotBeUpdated','RJCT'),
	 ('U238','RequestAfterMandateEndDate','RJCT'),
	 ('U239','HoldDateExceedsExipryDate','RJCT'),
	 ('U851','General Purpose Rules Not Found','RJCT'),
	 ('U312','RequestForPayNotFound','RJCT'),
	 ('U313','RequestForPayAlreadyProcessed','RJCT'),
	 ('U314','RequestForPayAlreadyRejected','RJCT');
INSERT INTO public.kc_status_reason (status_reason_code,description,status_code) VALUES
	 ('U315','RequestForPayInvalidState','RJCT'),
	 ('U316','RequestForPayAlreadyReceived','RJCT'),
	 ('U317','RequestForPayInvalidDbtrAgt','RJCT'),
	 ('U318','ExpiryDateLessThanCurrentDate','RJCT'),
	 ('U319','ExpiryDateFormatError','RJCT'),
	 ('U320','RequestedExecutionDateLessThanCurrentDate','RJCT'),
	 ('U321','RequestedExecutionDateFormatError','RJCT'),
	 ('U310','RequestForPayExpired','RJCT'),
	 ('U311','RequestForPayAlreadyTransferred','RJCT'),
	 ('U322','MandateIsNotActive','RJCT');
INSERT INTO public.kc_status_reason (status_reason_code,description,status_code) VALUES
	 ('U323','InvalidDebtorAgent','RJCT'),
	 ('U324','InvalidOriginalDebtorAgent','RJCT'),
	 ('U325','MandateAlreadyTransferred','RJCT'),
	 ('U300-U309','Reserved for private use ','RJCT'),
	 ('U326','RequestForPayAlreadyAccepted','RJCT'),
	 ('U327','MerchantNotActive','RJCT'),
	 ('U328','MerchantProductNotFound','RJCT'),
	 ('U329','MerchantNotFound','RJCT'),
	 ('U330','MerchantParameterNotFound','RJCT'),
	 ('U331','MerchantMinAmountCheckFailed','RJCT');
INSERT INTO public.kc_status_reason (status_reason_code,description,status_code) VALUES
	 ('U332','MerchantMaxAmountCheckFailed','RJCT'),
	 ('U333','DateXpryCheckFailed','RJCT'),
	 ('U334','MandateIsNotPending','RJCT'),
	 ('U335','RPFLookupRulesNotFound','RJCT'),
	 ('U336','PreAuthExpiryDateFormatError','RJCT'),
	 ('U337','PreAuthExpiryDateExceedsMandateExipryDate','RJCT'),
	 ('U338','PreAuthExpiryDateLessThanCreDtTm','RJCT'),
	 ('U339','PreAuthExpiryDateLessThanCurrentDate','RJCT'),
	 ('U340','MechantProductNotActive','RJCT'),
	 ('U341','MerchantValidationRuleNotFound','RJCT');
INSERT INTO public.kc_status_reason (status_reason_code,description,status_code) VALUES
	 ('U342','MerchantProductBankCodeNotFound','RJCT'),
	 ('K000','Timeout/ On-progress','KSTS'),
	 ('K003','InputValidationFailure','KSTS'),
	 ('K001','NoRefNotFound','KSTS'),
	 ('K004','TransactionLimitViolation','KSTS'),
	 ('K005','AuthenticationFailure','KSTS'),
	 ('K006','Communication Error','KSTS'),
	 ('K010','KomiInternalError','KSTS'),
	 ('K007','Database table error','KSTS'),
	 ('U110','Payment Not Accepted','RJCT');