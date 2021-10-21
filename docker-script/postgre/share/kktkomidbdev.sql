--
-- PostgreSQL database dump
--

-- Dumped from database version 13.3
-- Dumped by pg_dump version 13.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: cube; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS cube WITH SCHEMA public;


--
-- Name: EXTENSION cube; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION cube IS 'data type for multidimensional cubes';


--
-- Name: earthdistance; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS earthdistance WITH SCHEMA public;


--
-- Name: EXTENSION earthdistance; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION earthdistance IS 'calculate great-circle distances on the surface of the Earth';


--
-- Name: fuzzystrmatch; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS fuzzystrmatch WITH SCHEMA public;


--
-- Name: EXTENSION fuzzystrmatch; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION fuzzystrmatch IS 'determine similarities and distance between strings';


--
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


--
-- Name: uuid10(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.uuid10() RETURNS character varying
    LANGUAGE sql
    AS $$SELECT string_agg (substr('abcdefghijklmnopqrstuvwxyz0123456789', ceil (random() * 36)::integer, 1), '') FROM generate_series(1, 10)$$;


ALTER FUNCTION public.uuid10() OWNER TO postgres;

--
-- Name: uuid10num(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.uuid10num() RETURNS character varying
    LANGUAGE sql
    AS $$SELECT string_agg (substr('0123456789', ceil (random() * 10)::integer, 1), '') FROM generate_series(1, 10)$$;


ALTER FUNCTION public.uuid10num() OWNER TO postgres;

--
-- Name: uuid4(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.uuid4() RETURNS character varying
    LANGUAGE sql
    AS $$SELECT string_agg (substr('ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', ceil (random() * 36)::integer, 1), '') FROM generate_series(1, 4)$$;


ALTER FUNCTION public.uuid4() OWNER TO postgres;

--
-- Name: uuid4num(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.uuid4num() RETURNS character varying
    LANGUAGE sql
    AS $$SELECT string_agg (substr('0123456789', ceil (random() * 10)::integer, 1), '') FROM generate_series(1, 4)$$;


ALTER FUNCTION public.uuid4num() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: m_accounttype; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.m_accounttype (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    code character varying(255),
    name_cb character varying(255),
    type_cb character varying(255),
    name_bf character varying(255),
    type_bf character varying(255),
    status smallint,
    id_tenant bigint,
    created_date time without time zone
);


ALTER TABLE public.m_accounttype OWNER TO postgres;

--
-- Name: m_bic; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.m_bic (
    bic_code character varying(100) NOT NULL,
    bank_name character varying(100),
    last_update_date time without time zone,
    change_who character varying(100),
    idtenant bigint DEFAULT 0,
    created_date timestamp without time zone DEFAULT now(),
    bank_code character varying(100),
    status integer DEFAULT 0
);


ALTER TABLE public.m_bic OWNER TO postgres;

--
-- Name: m_branch; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.m_branch (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    citycode character varying(10),
    cityname character varying(100),
    branchcode character varying(10),
    branchname character varying(100),
    status integer DEFAULT 0,
    last_update_date time without time zone,
    change_who character varying(100),
    idtenant bigint DEFAULT 0,
    created_date timestamp without time zone DEFAULT now()
);


ALTER TABLE public.m_branch OWNER TO postgres;

--
-- Name: m_channeltype; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.m_channeltype (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    channeltype character varying(225),
    status integer DEFAULT 0,
    created_date timestamp without time zone DEFAULT now(),
    change_who integer DEFAULT 0,
    last_update_date timestamp with time zone,
    idtenant integer DEFAULT 0,
    channelcode character varying(10)
);


ALTER TABLE public.m_channeltype OWNER TO postgres;



--
-- Name: m_customertype; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.m_customertype (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    code character varying,
    name character varying,
    type_cb character varying,
    type_bf character varying,
    status smallint,
    id_tenant bigint,
    created_date time without time zone
);


ALTER TABLE public.m_customertype OWNER TO postgres;
--
-- Name: m_datareport; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.m_datareport (
    headercol json,
    datacol json,
    created_date timestamp without time zone DEFAULT now(),
    srcpayload json,
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    filename character varying DEFAULT 'default'::character varying
);


ALTER TABLE public.m_datareport OWNER TO postgres;


CREATE TABLE public.m_idtype (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    code character varying(255),
    name character varying(255),
    type_cb character varying(255),
    type_bf character varying(255),
    status smallint,
    id_tenant bigint,
    created_date time without time zone
);


ALTER TABLE public.m_idtype OWNER TO postgres;

CREATE TABLE public.m_limit (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    limit_name character varying(255),
    tran_type character varying(255),
    min_tran character varying(255),
    max_limit character varying(255),
    status smallint,
    id_tenant bigint,
    created_date time without time zone
);


ALTER TABLE public.m_limit OWNER TO postgres;

CREATE TABLE public.m_prefund (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    idparticipant bigint,
    amount integer,
    last_update_date time without time zone,
    change_who character varying(100),
    idtenant bigint DEFAULT 0,
    created_date timestamp without time zone DEFAULT now()
);


ALTER TABLE public.m_prefund OWNER TO postgres;

CREATE TABLE public.m_proxy (
    registration_id character varying(12) DEFAULT public.uuid10num() NOT NULL,
    proxy_type character varying(100),
    proxy_value character varying(100),
    registrar_bic character varying(100),
    account_number character varying(100),
    account_type character varying(100),
    account_name character varying(100),
    identification_number_type character varying(100),
    identification_number_value character varying(100),
    proxy_status character varying(100),
    last_update_date time without time zone,
    created_date time without time zone DEFAULT now(),
    change_who character varying(100),
    idtenant bigint DEFAULT 0
);


ALTER TABLE public.m_proxy OWNER TO postgres;

--
-- Name: m_proxyalias; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.m_proxyalias (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(100),
    accnum character varying(100),
    prxaddress character varying(100),
    branch character varying(100),
    channel character varying(100),
    proxytype character varying(100),
    secondid character varying(100),
    custresident character varying(100),
    created_date timestamp without time zone DEFAULT now(),
    change_who character varying(100),
    last_update_date timestamp with time zone,
    idtenant bigint,
    stsprxadd integer,
    secondtype character varying(100),
    proxystat character varying(255)
);


ALTER TABLE public.m_proxyalias OWNER TO postgres;

--
-- Name: COLUMN m_proxyalias.branch; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.m_proxyalias.branch IS 'Please check Branch Code, and then Fill with the code';


--
-- Name: COLUMN m_proxyalias.custresident; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.m_proxyalias.custresident IS 'Please chect Resident code and put the code';


--
-- Name: COLUMN m_proxyalias.stsprxadd; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.m_proxyalias.stsprxadd IS '1=Active, 2=Inactive';


CREATE TABLE public.m_proxytype (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    proxycode character varying(50),
    proxyname character varying(100),
    status integer DEFAULT 0,
    created_date timestamp without time zone DEFAULT now(),
    change_who integer,
    last_update_date timestamp without time zone,
    idtenant integer DEFAULT 0
);


ALTER TABLE public.m_proxytype OWNER TO postgres;


CREATE TABLE public.m_resident (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    code character varying(255),
    name character varying(255),
    status_cb character varying(255),
    status_bf character varying(255),
    status smallint,
    id_tenant bigint,
    created_date time without time zone
);


ALTER TABLE public.m_resident OWNER TO postgres;


CREATE TABLE public.m_systemparam (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    paramname character varying(100),
    paramvalua character varying(225),
    status smallint DEFAULT 0,
    created_date timestamp without time zone DEFAULT now(),
    change_who bigint,
    last_update_date timestamp with time zone,
    idtenant integer DEFAULT 0
);


ALTER TABLE public.m_systemparam OWNER TO postgres;

--
-- Name: m_trxcost; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.m_trxcost (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    fee character varying(10),
    "values" integer,
    status integer DEFAULT 0,
    last_update_date time without time zone,
    change_who character varying(100),
    idtenant bigint DEFAULT 0,
    created_date timestamp without time zone DEFAULT now()
);


ALTER TABLE public.m_trxcost OWNER TO postgres;


CREATE TABLE public.outbound_message (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    bizmsgid character varying(255),
    channel character varying(255),
    chnl_req_time timestamp without time zone,
    chnl_resp_time timestamp without time zone,
    cihub_req_time timestamp without time zone,
    cihub_resp_time timestamp without time zone,
    error_msg character varying(500),
    full_request_msg character varying(4000),
    full_response_msg character varying(4000),
    intrn_ref_id character varying(255),
    message_name character varying(255),
    recpt_bank character varying(255),
    resp_bizmsgid character varying(255),
    resp_status character varying(255),
    rejct_msg character varying(255),
    saf_counter integer
);


ALTER TABLE public.outbound_message OWNER TO postgres;


CREATE TABLE public.prefund_dashboard (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    min bigint,
    max bigint,
    current bigint
);


ALTER TABLE public.prefund_dashboard OWNER TO postgres;


CREATE TABLE public.prefund_history (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    "refNum" character varying(255),
    "transactionType" smallint,
    "prefundAmount" character varying(255),
    datetime time without time zone
);


ALTER TABLE public.prefund_history OWNER TO postgres;

--
-- Name: COLUMN prefund_history."transactionType"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.prefund_history."transactionType" IS '1 incoming
2 outgoing';



CREATE TABLE public.system_param (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    param_name character varying(255) NOT NULL,
    param_value character varying(255) NOT NULL,
    category character varying(255),
    description character varying(255)
);


ALTER TABLE public.system_param OWNER TO postgres;

--
-- Name: trx_log; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.trx_log (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    status character varying(100),
    trxnumber character varying(50),
    refnumbifast character varying(50),
    channel character varying(100),
    destbank character varying(100),
    srcbank character varying(100),
    destaccnum character varying(100),
    srcaccnum character varying(100),
    destaccname character varying(100),
    srcaccname character varying(100),
    created_date timestamp without time zone DEFAULT now(),
    change_who character varying(100),
    last_updated_date timestamp without time zone,
    amount double precision DEFAULT 0.0,
    transacttype integer DEFAULT 0,
    idtenant integer,
    branchcode character varying(255),
    feecode character varying(255),
    feeamount character varying(255)
);


ALTER TABLE public.trx_log OWNER TO postgres;

--
-- Name: COLUMN trx_log.status; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.trx_log.status IS 'ACTC=accepted, RJCT=rejected ';


--
-- Name: COLUMN trx_log.transacttype; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.trx_log.transacttype IS '1= for incominig transaction, 2= for outgoing transaction';


--
-- Name: trx_reportlog; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.trx_reportlog (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    rpttype integer DEFAULT 0,
    status smallint DEFAULT 0,
    description text,
    pathfile text,
    created_date timestamp without time zone DEFAULT now(),
    idtenant bigint DEFAULT 0,
    change_who character varying(100),
    last_update_date timestamp without time zone
);


ALTER TABLE public.trx_reportlog OWNER TO postgres;

--
-- Name: COLUMN trx_reportlog.rpttype; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.trx_reportlog.rpttype IS '1=Incoming, 2=outgoing';


--
-- Name: COLUMN trx_reportlog.status; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.trx_reportlog.status IS '0=Progress, 1=Success, 2=error';


ALTER TABLE ONLY public.m_accounttype
    ADD CONSTRAINT m_accounttype_pkey PRIMARY KEY (id);


--
-- Name: m_branch m_branch_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.m_branch
    ADD CONSTRAINT m_branch_pkey PRIMARY KEY (id);


--
-- Name: m_channeltype m_channeltype_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.m_channeltype
    ADD CONSTRAINT m_channeltype_pkey PRIMARY KEY (id);


--
-- Name: m_customertype m_customertype_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.m_customertype
    ADD CONSTRAINT m_customertype_pkey PRIMARY KEY (id);


--
-- Name: m_datareport m_datareport_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.m_datareport
    ADD CONSTRAINT m_datareport_pkey PRIMARY KEY (id);


--
-- Name: m_idtype m_idtype_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.m_idtype
    ADD CONSTRAINT m_idtype_pkey PRIMARY KEY (id);


--
-- Name: m_limit m_limit_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.m_limit
    ADD CONSTRAINT m_limit_pkey PRIMARY KEY (id);


--
-- Name: m_prefund m_prefund_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.m_prefund
    ADD CONSTRAINT m_prefund_pkey PRIMARY KEY (id);


--
-- Name: m_proxy m_proxy_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.m_proxy
    ADD CONSTRAINT m_proxy_pkey PRIMARY KEY (registration_id);


--
-- Name: m_proxyalias m_proxyalias_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.m_proxyalias
    ADD CONSTRAINT m_proxyalias_pkey PRIMARY KEY (id);


--
-- Name: m_proxytype m_proxytype_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.m_proxytype
    ADD CONSTRAINT m_proxytype_pkey PRIMARY KEY (id);


--
-- Name: m_resident m_resident_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.m_resident
    ADD CONSTRAINT m_resident_pkey PRIMARY KEY (id);


--
-- Name: m_systemparam m_systemparam_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.m_systemparam
    ADD CONSTRAINT m_systemparam_pkey PRIMARY KEY (id);


--
-- Name: m_trxcost m_trxcost_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.m_trxcost
    ADD CONSTRAINT m_trxcost_pkey PRIMARY KEY (id);


--
-- Name: outbound_message outbound_message_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.outbound_message
    ADD CONSTRAINT outbound_message_pkey PRIMARY KEY (id);


--
-- Name: prefund_dashboard prefund_dashboard_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prefund_dashboard
    ADD CONSTRAINT prefund_dashboard_pkey PRIMARY KEY (id);


--
-- Name: prefund_history prefund_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prefund_history
    ADD CONSTRAINT prefund_history_pkey PRIMARY KEY (id);


--
-- Name: system_param system_param_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.system_param
    ADD CONSTRAINT system_param_pkey PRIMARY KEY (id);


--
-- Name: trx_log trx_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trx_log
    ADD CONSTRAINT trx_log_pkey PRIMARY KEY (id);


--
-- Name: trx_reportlog trx_reportlog_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trx_reportlog
    ADD CONSTRAINT trx_reportlog_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

