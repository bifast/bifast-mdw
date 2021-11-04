INSERT INTO public.kc_channel (channel_id,channel_name,channel_type,create_dt,daily_limit_amount,merchant_code,modif_dt,secret_key,transaction_limit_amount) VALUES
	 ('CB','Corebank','03',NULL,NULL,'6010',NULL,'$2a$12$r4o5RRFYnOMhDwg07YzlvebQxWMD8Bh4lDzfD1Ts.HfHunnv0wjiW',NULL),
	 ('CMS','Internet Banking','01',NULL,NULL,'5001',NULL,'$2a$12$r4o5RRFYnOMhDwg07YzlvebQxWMD8Bh4lDzfD1Ts.HfHunnv0wjiW',NULL),
	 ('MB','Mobile Banking','02',NULL,NULL,'5002',NULL,'$2a$12$r4o5RRFYnOMhDwg07YzlvebQxWMD8Bh4lDzfD1Ts.HfHunnv0wjiW',NULL),
	 ('PORTAL','Komi-Portal','04',NULL,NULL,'5003',NULL,'$2a$12$r4o5RRFYnOMhDwg07YzlvebQxWMD8Bh4lDzfD1Ts.HfHunnv0wjiW',NULL);INSERT INTO public.kc_channel_transaction (id,amount,call_status,channel_id,channel_ref_id,elapsed_time,error_msg,komi_trns_id,msg_name,recpt_bank,request_time,response_code,text_message) VALUES
	 (1290,120500.50,'SUCCESS','CMS','346225562',10039,'SocketTimeoutException: Read timed out','30600155','CTReq','BMNDIDJA','2021-11-02 09:53:59.138814','ACTC','{
  "CreditTransferRequest" : {
    "NoRef" : "346225562",   
    "TerminalId" : "B0434",
    "CategoryPurpose" : "01",
    "DebtorName" : "JONI",
    "DebtorId" : "6055",
    "DebtorType" : "01",
    "DebtorAccountNumber" : "2514214",
    "DebtorAccountType" : "CACC",
    "DebtorResidentialStatus": "01",
    "DebtorTownName": "0300",
    "Amount" : "120500.50",
    "FeeTransfer" : "4000.00",
    "RecipientBank" : "BMNDIDJA",
    "CreditorName" : "JONI N",
    "CreditorId" : "C12103",
    "CreditorType" : "01",
    "CreditorAccountNumber" : "8864296",
    "CreditorAccountType" : "SVGS",
    "CreditorResidentialStatus": "01",
    "CreditorTownName": "0300",
    "PaymentInformation" : "bayaran"
  }
}'),
	 (1285,120500.50,'SUCCESS','CMS','3462256',10120,'SocketTimeoutException: Read timed out','30600141','CTReq','BMNDIDJA','2021-11-02 08:04:18.640422','ACTC','{
  "CreditTransferRequest" : {
    "NoRef" : "3462256",   
    "TerminalId" : "B0434",
    "CategoryPurpose" : "01",
    "DebtorName" : "JONI",
    "DebtorId" : "6055",
    "DebtorType" : "01",
    "DebtorAccountNumber" : "2514214",
    "DebtorAccountType" : "CACC",
    "DebtorResidentialStatus": "01",
    "DebtorTownName": "0300",
    "Amount" : "120500.50",
    "FeeTransfer" : "4000.00",
    "RecipientBank" : "BMNDIDJA",
    "CreditorName" : "JONI N",
    "CreditorId" : "C12103",
    "CreditorType" : "01",
    "CreditorAccountNumber" : "8864296",
    "CreditorAccountType" : "SVGS",
    "CreditorResidentialStatus": "01",
    "CreditorTownName": "0300",
    "PaymentInformation" : "bayaran"
  }
}'),
	 (1288,120500.50,'SUCCESS','CB','34622556',10137,'SocketTimeoutException: Read timed out','30600148','CTReq','BMNDIDJA','2021-11-02 08:41:08.141342','ACTC','{
  "CreditTransferRequest" : {
    "NoRef" : "34622556",   
    "TerminalId" : "B0434",
    "CategoryPurpose" : "01",
    "DebtorName" : "JONI",
    "DebtorId" : "6055",
    "DebtorType" : "01",
    "DebtorAccountNumber" : "2514214",
    "DebtorAccountType" : "CACC",
    "DebtorResidentialStatus": "01",
    "DebtorTownName": "0300",
    "Amount" : "120500.50",
    "FeeTransfer" : "4000.00",
    "RecipientBank" : "BMNDIDJA",
    "CreditorName" : "JONI N",
    "CreditorId" : "C12103",
    "CreditorType" : "01",
    "CreditorAccountNumber" : "8864296",
    "CreditorAccountType" : "SVGS",
    "CreditorResidentialStatus": "01",
    "CreditorTownName": "0300",
    "PaymentInformation" : "bayaran"
  }
}'),
	 (1297,120500.50,'SUCCESS','CMS','34622555622',10253,'SocketTimeoutException: Read timed out','30600175','CTReq','BMNDIDJA','2021-11-02 12:03:52.627448','RJCT','{
  "CreditTransferRequest" : {
    "NoRef" : "34622555622",   
    "TerminalId" : "B0434",
    "CategoryPurpose" : "01",
    "DebtorName" : "JONI",
    "DebtorId" : "6055",
    "DebtorType" : "01",
    "DebtorAccountNumber" : "514214",
    "DebtorAccountType" : "CACC",
    "DebtorResidentialStatus": "01",
    "DebtorTownName": "0300",
    "Amount" : "120500.50",
    "FeeTransfer" : "4000.00",
    "RecipientBank" : "BMNDIDJA",
    "CreditorName" : "JONI N",
    "CreditorId" : "C12103",
    "CreditorType" : "01",
    "CreditorAccountNumber" : "8864296",
    "CreditorAccountType" : "SVGS",
    "CreditorResidentialStatus": "01",
    "CreditorTownName": "0300",
    "PaymentInformation" : "bayaran"
  }
}'),
	 (1293,120500.50,'SUCCESS','CMS','3462255622',10065,'SocketTimeoutException: Read timed out','30600162','CTReq','BMNDIDJA','2021-11-02 09:57:19.905176','RJCT','{
  "CreditTransferRequest" : {
    "NoRef" : "3462255622",   
    "TerminalId" : "B0434",
    "CategoryPurpose" : "01",
    "DebtorName" : "JONI",
    "DebtorId" : "6055",
    "DebtorType" : "01",
    "DebtorAccountNumber" : "514214",
    "DebtorAccountType" : "CACC",
    "DebtorResidentialStatus": "01",
    "DebtorTownName": "0300",
    "Amount" : "120500.50",
    "FeeTransfer" : "4000.00",
    "RecipientBank" : "BMNDIDJA",
    "CreditorName" : "JONI N",
    "CreditorId" : "C12103",
    "CreditorType" : "01",
    "CreditorAccountNumber" : "8864296",
    "CreditorAccountType" : "SVGS",
    "CreditorResidentialStatus": "01",
    "CreditorTownName": "0300",
    "PaymentInformation" : "bayaran"
  }
}'),
	 (1302,NULL,'SUCCESS','CMS','652452',16,NULL,'30600184','AcctCustmrInfo',NULL,'2021-11-02 12:43:37.742006','ACTC','{
  "AccountCustomerInfo" : {
    "NoRef" : "652452",   
    "AccountNumber" : "515554"
  }
}');INSERT INTO public.kc_corebank_transaction (id,credit_amount,cstm_account_name,cstm_account_no,cstm_account_type,date_time,debit_amount,fee_amount,komi_noref,komi_trns_id,orgnl_chnl_noref,orgnl_date_time,reason,response,retry_counter,transaction_type,trns_date,update_time,full_text_request) VALUES
	 (1286,NULL,'JONI','2514214','CACC','2021-11-02T08:04:18.649',120500.50,4000.00,'KOM21110208041800103','30600141','3462256','2021-11-02T08:04:18.644','U000','ACTC',NULL,'DebitAccount','20211102','2021-11-02 08:04:18.650939','{"transactionId":"000000","dateTime":"2021-11-02T08:04:18.649","merchantType":"5001","terminalId":"B0434","noRef":"KOM21110208041800103","originalNoRef":"3462256","originalDateTime":"2021-11-02T08:04:18.644","categoryPurpose":"01","debtorName":"JONI","debtorType":"01","debtorId":"6055","debtorAccountNumber":"2514214","debtorAccountType":"CACC","debtorResidentStatus":"01","debtorTownName":"0300","amount":"120500.50","feeTransfer":"4000.00","recipientBank":"SIHBIDJ1","creditorName":"JONI N","creditorType":"01","creditorId":"C12103","creditorAccountNumber":"8864296","creditorAccountType":"SVGS","creditorResidentStatus":"01","creditorTownName":"0300","paymentInformation":"bayaran"}'),
	 (1291,NULL,'JONI','2514214','CACC','2021-11-02T09:53:59.219',120500.50,4000.00,'KOM21110209535900104','30600155','346225562','2021-11-02T09:53:59.151','U000','ACTC',NULL,'DebitAccount','20211102','2021-11-02 09:53:59.225217','{"transactionId":"000000","dateTime":"2021-11-02T09:53:59.219","merchantType":"5001","terminalId":"B0434","noRef":"KOM21110209535900104","originalNoRef":"346225562","originalDateTime":"2021-11-02T09:53:59.151","categoryPurpose":"01","debtorName":"JONI","debtorType":"01","debtorId":"6055","debtorAccountNumber":"2514214","debtorAccountType":"CACC","debtorResidentStatus":"01","debtorTownName":"0300","amount":"120500.50","feeTransfer":"4000.00","recipientBank":"SIHBIDJ1","creditorName":"JONI N","creditorType":"01","creditorId":"C12103","creditorAccountNumber":"8864296","creditorAccountType":"SVGS","creditorResidentStatus":"01","creditorTownName":"0300","paymentInformation":"bayaran"}'),
	 (1294,NULL,'JONI','514214','CACC','2021-11-02T09:57:19.915',120500.50,4000.00,'KOM21110209571900105','30600162','3462255622','2021-11-02T09:57:19.912','U000','ACTC',NULL,'DebitAccount','20211102','2021-11-02 09:57:19.917412','{"transactionId":"000000","dateTime":"2021-11-02T09:57:19.915","merchantType":"5001","terminalId":"B0434","noRef":"KOM21110209571900105","originalNoRef":"3462255622","originalDateTime":"2021-11-02T09:57:19.912","categoryPurpose":"01","debtorName":"JONI","debtorType":"01","debtorId":"6055","debtorAccountNumber":"514214","debtorAccountType":"CACC","debtorResidentStatus":"01","debtorTownName":"0300","amount":"120500.50","feeTransfer":"4000.00","recipientBank":"SIHBIDJ1","creditorName":"JONI N","creditorType":"01","creditorId":"C12103","creditorAccountNumber":"8864296","creditorAccountType":"SVGS","creditorResidentStatus":"01","creditorTownName":"0300","paymentInformation":"bayaran"}'),
	 (1296,120500.50,'JONI','514214','CACC','2021-11-02T11:03:05.902',NULL,4000.00,'KOM21110211030500109','30600162','3462255622','2021-11-02T09:57:19.905','U000','ACTC',NULL,'DebitReversal','20211102','2021-11-02 11:03:05.938992',NULL),
	 (1298,NULL,'JONI','514214','CACC','2021-11-02T12:03:52.672',120500.50,4000.00,'KOM21110212035200110','30600175','34622555622','2021-11-02T12:03:52.640','U000','ACTC',NULL,'DebitAccount','20211102','2021-11-02 12:03:52.68412','{"transactionId":"000000","dateTime":"2021-11-02T12:03:52.672","merchantType":"5001","terminalId":"B0434","noRef":"KOM21110212035200110","originalNoRef":"34622555622","originalDateTime":"2021-11-02T12:03:52.640","categoryPurpose":"01","debtorName":"JONI","debtorType":"01","debtorId":"6055","debtorAccountNumber":"514214","debtorAccountType":"CACC","debtorResidentStatus":"01","debtorTownName":"0300","amount":"120500.50","feeTransfer":"4000.00","recipientBank":"SIHBIDJ1","creditorName":"JONI N","creditorType":"01","creditorId":"C12103","creditorAccountNumber":"8864296","creditorAccountType":"SVGS","creditorResidentStatus":"01","creditorTownName":"0300","paymentInformation":"bayaran"}'),
	 (1300,120500.50,'JONI','514214','CACC','2021-11-02T12:04:04.339',NULL,4000.00,'KOM21110212040400111','30600175','34622555622','2021-11-02T12:03:52.627','U000','ACTC',NULL,'DebitReversal','20211102','2021-11-02 12:04:04.35594',NULL),
	 (1301,120500.50,'JONI','514214','CACC','2021-11-02T12:07:21.235',NULL,4000.00,'KOM21110212072100112','30600175','34622555622','2021-11-02T12:03:52.627','U000','ACTC',NULL,'DebitReversal','20211102','2021-11-02 12:07:21.250516',NULL),
	 (1303,NULL,'Abang Andre','515554','CACC','2021-11-02T12:43:37.747',NULL,NULL,'KOM21110212433700113','30600184',NULL,NULL,'U000','ACTC',NULL,'AccountCustomerInfo','20211102','2021-11-02 12:43:37.754405',NULL);INSERT INTO public.kc_credit_transfer (id,amount,call_status,cihub_elapsed_time,cihub_req_time,req_bizmsgid,resp_bizmsgid,create_dt,creditor_acct_no,creditor_acct_type,creditor_id,creditor_type,debtor_acct_no,debtor_acct_type,debtor_id,debtor_type,error_message,full_request_msg,full_response_msg,komi_trns_id,last_update_dt,msg_type,orign_bank,ps_counter,reason_code,recpt_bank,response_code,reversal) VALUES
	 (1299,120500.50,'SUCCESS',10062,'2021-11-02 12:04:02.837667','20211102SIHBIDJ1010O0130600177',NULL,'2021-11-02 12:03:52.713','8864296','SVGS','C12103','01','514214','CACC','6055','01','SocketTimeoutException: Read timed out','eJyVUttu4jAQ/ZWVnykaBwIsb7lQ6qpcRLx9QX0ICVC0iYmcoVsW8e8dO6SCVVHZSISMx2fmnJlz
YP6uHJVr1j8wrygeUm2+7qv3ZpNW/0qoEpWw0QRfbdZELBIPvggfOTvS02ByeyPOH41DwnknnL/5
SxyEac4ccDjn4NSlgcMEeAs6ALzbZQ1GN8PlqrpcxEnZBOjRjzehR9lAL0M8lbnj/A4cyZ0+tPqu
w6hTuE12+VJhxQ+39yIoMddBilKvzOFQ10OwjL4mZB/e/ewn8ysdG2y8mKzke0l5TlGEmOVC2U72
e4SmRfA0GzIziIqHfLdX5gc2zbGa3kClckuva4wuR0QFvmNOzai4LE5knpLM7ErnJphq1HvCAzfX
AlzvpztdXGbAJCkrFGr/t9Xi5Xasz3G2Wxq9DrgATRfMlBKDEuHMFnzVa99sLxz4kpLhAu3Ax2aK
j5OxoLNK9VS/4bl75if7dMB12fHFesegvSTB2lv/OM3lbYe3LVV5ISHwgoB9Fljjfzjd7ukGzLnL
K8yFzB/jb4UG3OHQqqXartel9nqdtvOz84XW6HkY2eMZ+ala+C9aNoHmbBHvYx0r9kLpqMhyQoQx
xpbDQL1ltlC9IlPW2IIqlanCCMs6ln+UFQYtAHam9lbEkTQalccP2E5S1A==
',NULL,'30600175','2021-11-02 12:07:21.166814','Credit Transfer','SIHBIDJ1',2,'U110','BMNDIDJA','RJCT',NULL),
	 (1287,120500.50,'SUCCESS',9990,'2021-11-02 08:04:28.672119','20211102SIHBIDJ1010O0130600143',NULL,'2021-11-02 08:04:18.678','8864296','SVGS','C12103','01','2514214','CACC','6055','01','SocketTimeoutException: Read timed out','eJyVUl1v4jAQ/CsnP1O0DgnK8ZYPSlOVgIivL6gPIQGKmpjIWXrlEP/91g45wamoNBIh6/XszuzO
gfm7elyv2eDAvKp6yJX+um/em03e/MtI1igjE03w1WR1xJLowY/CR86O9HSY2N6I88dxSDjvhPM3
f4hDpJszCyzOOVhtaeAwAd6DPgC3e6zD6Ga4XDWXqzSruwAu/XgXXMoGahniqcwd53dgCXAHYA+4
y6hTuM125VJiww+391FQY6mCHIVa6cORaodgGH1OyDzcttt+orzSscPixWQlPmrKc4oSxKKMpOlk
vseoWwRPsxHTg2h4iA9zZX5g0xKb6Q1lLrb0usbockRU4Cvm1IyKi+pE5ikr9K5UqYOpQrUnPHB9
LcD1frpT1WUGdJKykUTlvxktXmnG+pwWu6XWa4ED0HVATynTqCicmYKvau3r7YVDX1AyXKAZeKyn
+DiJIzprVE/VO567Z36yTx8chx1fjHc02ssybL31n9Msh9sWtw1XcaEh8IKA/auwxm9Y3SzqBsy5
zRvMhc4f8ZdKA25x6LVaTdfrWl23b1s/+59oTZ5HiTmekaGajf+ibRNozhbpPlWpZC+UTqqiJESY
Ymo4DOV7YQq1O9JltS+oUp1LTLBuY/FbGmHQA2Bnam9FHEmjVnn8C90VUvo=
',NULL,'30600141','2021-11-02 08:07:19.972153','Credit Transfer','SIHBIDJ1',1,'U000','BMNDIDJA','ACTC',NULL),
	 (1289,120500.50,'SUCCESS',10004,'2021-11-02 08:41:18.17674','20211102SIHBIDJ1010O0330600150',NULL,'2021-11-02 08:41:08.165','8864296','SVGS','C12103','01','2514214','CACC','6055','01','SocketTimeoutException: Read timed out','eJyVUl1v4jAQ/CsnP1O0DgTleMsHpalKQMTXF8RDSICiJiZyll45xH/v2iF3cCoqjUSIvTuzM7t7
YN6uGlVr1j8wtywfMqW/7uv3ZpPV/zKUFcrQnMb4YqL6xOLwwQuDR86O9LSY2N6I80ZRQDj3hPM2
f0hDqIszCyzOOVgNNXAYQ6cDPQBuA2sxygyWqzq5TNKqDeDQj7fBoaivlgGeaO44vwNLgNPv8j5F
qVKwTXfFUmKtD7f3oV9hofwMhVrpy6FqmmAUfS7IPNzmTT1RXKnYYtFivBLvFcV1doyYF6E0lcz3
CHUJ/2k6ZLoRtQ7xblJmBzYpsO7eQGZiS69rii5bRARfKadiRC7Kk5inNNezUoU+TBSqPeGho9N8
XO8nO1VeRIiJG8WhROW9Gi9uYdr6nOS7pfZrgQ3QNoL8VKPCYGoIX9Ta09MLBp6gYLBA0/BId/Fx
HIV0V7ueqDc8357ZaX16YNvsODe7o9FummKzW/9tmmXzrsW7Rqu48OC7vs/+MqzxG6tuBnUD5nzN
a8yFzx/Rl059bnE9hvm/qte9Ok6va/3sfeI1fh7G5npKC1VP/BdNm0Aztkj2iUokm1M4LvOCEEGC
idEwkG+5IWpmpGlp+qR7WmUSY6yas/gtjTHoALAzt7cijuRRuzx+ANZLUvg=
',NULL,'30600148','2021-11-02 08:41:19.943367','Credit Transfer','SIHBIDJ1',1,'U000','BMNDIDJA','ACTC',NULL),
	 (1292,120500.50,'SUCCESS',9911,'2021-11-02 09:54:09.153477','20211102SIHBIDJ1010O0130600157',NULL,'2021-11-02 09:53:59.24','8864296','SVGS','C12103','01','2514214','CACC','6055','01','SocketTimeoutException: Read timed out','eJyVUttu4jAQ/ZWVnykaB8ICb7lQ6qpcRLx9QX0ICVC0iYmcoVsW8e8dO6SCVVHZSISMx2fmnJlz
YP6uHJVr1j8wrygeUm2+7qv3ZpNW/0qoEpWw0QRfbdZELBIPvggfOTvS02ByeyPOH41DwnknnL/5
SxyEac4ccDjn4NSlgcMEeAs6ANz9yRqMbobLVXW5iJOyCdClH29Cl7KBXoZ4KnPH+R04Enp9t9V3
e4w6hdtkly8VVvxwey+CEnMdpCj1yhwOdT0Ey+hrQvbh7mc/mV/p2GDjxWQl30vKc4oixCwXynay
3yM0LYKn2ZCZQVQ85Lu9Mj+waY7V9AYqlVt6XWN0OSIq8B1zakbFZXEi85RkZlc6N8FUo94THri5
FuB6P93p4jIDJklZoVD7v60WL7djfY6z3dLodcAFaLpgppQYlAhntuCrXvtme+HAl5QMF2gHPjZT
fJyMBZ1Vqqf6Dc/dMz/ZpwOuy44v1jsG7SUJ1t76x2mOy9sOb1uu8kJD4AUB+6ywxv+wul3UDZhz
m1eYC50/xt8qDbjDoVVrtV2va+12O22n1/lCa/Q8jOzxjAxVbfwXbZtAc7aI97GOFXuhdFRkOSHC
GGPLYaDeMluo3pEpa3xBlcpUYYRlHcs/ygqDFgA7U3sr4kgajcrjB2D9UyI=
',NULL,'30600155','2021-11-02 09:54:10.411729','Credit Transfer','SIHBIDJ1',1,'U000','BMNDIDJA','ACTC',NULL),
	 (1295,120500.50,'SUCCESS',9987,'2021-11-02 09:57:29.933631','20211102SIHBIDJ1010O0130600164',NULL,'2021-11-02 09:57:19.942','8864296','SVGS','C12103','01','514214','CACC','6055','01','SocketTimeoutException: Read timed out','eJyVUl1v4jAQ/CsnP1O0DiQHvOWD0lQlIOLrC+pDSICiJiZyll45xH+/tUNOcCoqjUTIej27M7tz
YN6uGldrNjgwtywfMqW/7uv3ZpPV/zKUFcrQRBN8NVkdsTh88MLgkbMjPS0mtjfivHEUEM494bzN
H+IQ6ubMAotzDlZTGjhMgHfAAeBOl7UY3QyWq/pymaRVG6BHP96GHmV9tQzwVOaO8zuwBPQH9s8B
7zPqFGzTXbGUWPPD7X3oV1goP0OhVvpwpJohGEafEzIPd+ymnyiudGyxaDFZiY+K8pyiGDEvQmk6
me8x6hb+02zE9CBqHuLDXJkf2LTAenpDmYktva4xuhwRFfiKOTWj4qI8kXlKc70rVehgqlDtCQ9c
X/NxvZ/uVHmZAZ2kbChReW9Gi1uYsT4n+W6p9VpgA7Rt0FNKNSoMZqbgq1p7envB0BOUDBZoBh7p
KT5OopDOatVT9Y7n7pmf7OOATQpejHc02k1TbLz1n9Ns3rV411AVFxJ81/fZvwJr/IbTzZ5uwJy7
vMZcyPwRfSnU5xaHTiPVdL0utddzulbf+URr/DyKzfGM/FQv/Bctm0Bztkj2iUoke6F0XOYFIYIE
E8NhKN9zU6hZkS6rbUGVqkxijFUTi9/SCIMOADtTeyviSBq1yuNfIgNS6A==
',NULL,'30600162','2021-11-02 11:03:05.887641','Credit Transfer','SIHBIDJ1',4,'U110','BMNDIDJA','RJCT',NULL);INSERT INTO public.kc_domain_code (id,grp,"key",value) VALUES
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
	 (20211102,184),
	 (20211030,429),
	 (20211031,463),
	 (20211101,228);INSERT INTO public.kc_parameter (id,code,"module",notes,value) VALUES
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