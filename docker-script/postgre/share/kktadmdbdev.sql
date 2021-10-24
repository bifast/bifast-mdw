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
-- Name: cdid_adminnotification; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cdid_adminnotification (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    iduser bigint,
    cd_busparam character varying(225),
    idtenant bigint,
    idapp bigint,
    created_byid bigint,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone
);


ALTER TABLE public.cdid_adminnotification OWNER TO postgres;

--
-- Name: cdid_dbcorellation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cdid_dbcorellation (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    description character varying(225),
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone
);


ALTER TABLE public.cdid_dbcorellation OWNER TO postgres;

--
-- Name: cdid_dbcorellation_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE TABLE public.cdid_liscenses (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    licensetokens text,
    licensejson json,
    created_byid bigint,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone,
    expiredate timestamp without time zone DEFAULT now(),
    cdid_tenant bigint,
    id_application bigint,
    paidstatus numeric DEFAULT 0,
    active smallint DEFAULT 1,
    defaultactive smallint DEFAULT 0
);


ALTER TABLE public.cdid_liscenses OWNER TO postgres;

--
-- Name: TABLE cdid_liscenses; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.cdid_liscenses IS 'Table berisikan kumpulan dari liscense';


--
-- Name: COLUMN cdid_liscenses.created_byid; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.cdid_liscenses.created_byid IS '0= if System, else is tenant_user id';


--
-- Name: COLUMN cdid_liscenses.paidstatus; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.cdid_liscenses.paidstatus IS '0=unpaid, 1=paid';


--
-- Name: COLUMN cdid_liscenses.active; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.cdid_liscenses.active IS '0=inactive, 1 active';
--
-- Name: cdid_mytask; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cdid_mytask (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    subject character varying(225),
    tasks text,
    name character varying(100),
    relatedto character varying(100),
    accountid bigint DEFAULT 0,
    accountstr character varying(225),
    completed smallint DEFAULT 0,
    created_byid bigint,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp with time zone,
    duedate_at timestamp without time zone,
    idtenant bigint,
    idapp bigint,
    datetask timestamp without time zone
);


ALTER TABLE public.cdid_mytask OWNER TO postgres;

--
-- Name: COLUMN cdid_mytask.completed; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.cdid_mytask.completed IS '0=Not Done, 1=Done';
--
-- Name: cdid_orgapplications; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cdid_orgapplications (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    idorg bigint DEFAULT 0,
    idapp bigint DEFAULT 0,
    idtenant bigint DEFAULT 0,
    idsubtenant bigint DEFAULT 0
);


ALTER TABLE public.cdid_orgapplications OWNER TO postgres;

--
-- Name: cdid_orgapplications_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--
CREATE TABLE public.cdid_orguserassign (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    userid bigint DEFAULT 0,
    orgid bigint DEFAULT 0,
    grpid bigint DEFAULT 0,
    created_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.cdid_orguserassign OWNER TO postgres;
--
-- Name: cdid_owner; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cdid_owner (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    ownname character varying(100),
    created_at timestamp without time zone DEFAULT now(),
    update_at timestamp without time zone,
    ownstatus smallint,
    owntype smallint
);


ALTER TABLE public.cdid_owner OWNER TO postgres;

--
-- Name: COLUMN cdid_owner.ownstatus; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.cdid_owner.ownstatus IS '0=inactive 1=active';


--
-- Name: COLUMN cdid_owner.owntype; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.cdid_owner.owntype IS '0=SaaS, 1=On Prem';


CREATE TABLE public.cdid_tenant (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    tnname character varying(100),
    created_at timestamp without time zone DEFAULT now(),
    update_at timestamp without time zone,
    tnstatus smallint,
    tntype smallint,
    cdidowner bigint,
    tnflag integer DEFAULT 1,
    tnparentid integer DEFAULT 0,
    cdtenant character varying(10) DEFAULT public.uuid4num()
);


ALTER TABLE public.cdid_tenant OWNER TO postgres;

--
-- Name: COLUMN cdid_tenant.tnstatus; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.cdid_tenant.tnstatus IS '0=not approved, 1=approved';


--
-- Name: COLUMN cdid_tenant.tntype; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.cdid_tenant.tntype IS '1= Owner as Tenant, 2 = Tenant from external, 3= SubTenant of tenant';


--
-- Name: COLUMN cdid_tenant.tnflag; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.cdid_tenant.tnflag IS '1=Root Tenant, 2 = Sub Tenant';


--
-- Name: COLUMN cdid_tenant.tnparentid; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.cdid_tenant.tnparentid IS '0=if no parent, 1=id tenant parent';
--
-- Name: cdid_tenant_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cdid_tenant_user (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    fullname character varying(100) NOT NULL,
    userid character varying(100),
    pwd text,
    creator_stat bigint DEFAULT 0,
    creator_byid bigint DEFAULT 0,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone,
    idtenant bigint NOT NULL,
    idsubtenant numeric DEFAULT 1,
    leveltenant numeric DEFAULT 0,
    active smallint DEFAULT 0,
    total_attempt integer DEFAULT 0,
    locked_at timestamp without time zone,
    last_login timestamp without time zone,
    last_change_password timestamp without time zone
);


ALTER TABLE public.cdid_tenant_user OWNER TO postgres;

--
-- Name: COLUMN cdid_tenant_user.creator_stat; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.cdid_tenant_user.creator_stat IS '0=By System, 1=By Users';


--
-- Name: COLUMN cdid_tenant_user.creator_byid; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.cdid_tenant_user.creator_byid IS '0= by system, else idtenant code';


--
-- Name: COLUMN cdid_tenant_user.idsubtenant; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.cdid_tenant_user.idsubtenant IS '0=Not Subtenant, else id of sub tenant';


--
-- Name: COLUMN cdid_tenant_user.leveltenant; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.cdid_tenant_user.leveltenant IS '0=Owner Root, 1= Owner User, 2=Tenant Administrator  3=Tenant User 4=Subtenant User';

--
-- Name: cdid_tenant_user_groupacl; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cdid_tenant_user_groupacl (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    iduser bigint DEFAULT 0,
    idgroupuseracl bigint,
    idtenant bigint,
    idapplication bigint,
    created_at timestamp without time zone DEFAULT now(),
    created_byid bigint,
    updated_at timestamp without time zone,
    grouptype smallint DEFAULT 0
);


ALTER TABLE public.cdid_tenant_user_groupacl OWNER TO postgres;

--
-- Name: COLUMN cdid_tenant_user_groupacl.grouptype; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.cdid_tenant_user_groupacl.grouptype IS '0=For Platfotm, 1=For Application/Tenant';

CREATE TABLE public.cicd_menuuseracl (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    iduser bigint,
    idmodule bigint,
    idmenu bigint,
    idgroupuser bigint,
    stat smallint
);


ALTER TABLE public.cicd_menuuseracl OWNER TO postgres;


CREATE TABLE public.event_log (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    request_ip character varying(255),
    request_url character varying(255),
    request_method character varying(255),
    request_service character varying(255),
    request_date timestamp without time zone,
    response_data json,
    response_date timestamp without time zone,
    request_token character varying(255),
    request_body json,
    request_header json
);


ALTER TABLE public.event_log OWNER TO postgres;

--
-- Name: mst_application; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mst_application (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    appname character varying(225),
    applabel character varying(225),
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp with time zone,
    description text,
    apptype smallint DEFAULT 1,
    routelink character varying(225)
);


ALTER TABLE public.mst_application OWNER TO postgres;

--
-- Name: COLUMN mst_application.appname; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.mst_application.appname IS 'Nama Aplikasi';


--
-- Name: COLUMN mst_application.applabel; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.mst_application.applabel IS 'Nama yang terlihat di portal';


--
-- Name: mst_application_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE TABLE public.mst_biodata_corell (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    bioname character varying(100),
    bioemailactive character varying(225),
    biophoneactive character varying(20),
    bioaddress text,
    bioidcorel integer,
    bionik character varying(20),
    bionpwp character varying(20),
    biocorelobjid integer,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp with time zone,
    bioidtipenik smallint
);


ALTER TABLE public.mst_biodata_corell OWNER TO postgres;

--
-- Name: COLUMN mst_biodata_corell.bioidcorel; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.mst_biodata_corell.bioidcorel IS 'id dari cdid_dbcorellation';


--
-- Name: COLUMN mst_biodata_corell.biocorelobjid; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.mst_biodata_corell.biocorelobjid IS 'id dari tabe cdid_[sesuai id corellation]';

--
-- Name: mst_bussines_param; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mst_bussines_param (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    category character varying(100),
    label character varying(100),
    valuestr character varying(100),
    valueint integer,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone
);


ALTER TABLE public.mst_bussines_param OWNER TO postgres;

--
-- Name: mst_menu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mst_menu (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(100),
    title character varying(100),
    routelink character varying(225),
    routepath character varying(225),
    iconcode character varying(10),
    defaultid integer DEFAULT 0,
    created_byid integer DEFAULT 0,
    created_at timestamp with time zone DEFAULT now(),
    updated_at timestamp without time zone,
    idmodule integer,
    description text
);


ALTER TABLE public.mst_menu OWNER TO postgres;

--
-- Name: COLUMN mst_menu.defaultid; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.mst_menu.defaultid IS '0=Framework Menu, 1=menu modules';


--
-- Name: COLUMN mst_menu.created_byid; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.mst_menu.created_byid IS '0=by system,  else by user id';


--
-- Name: mst_module; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mst_module (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    modulename character varying(225),
    created_byid bigint DEFAULT 0,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp with time zone,
    status integer DEFAULT 1,
    idapplication bigint,
    modulecode character varying(10),
    moduletype smallint DEFAULT 1
);


ALTER TABLE public.mst_module OWNER TO postgres;

--
-- Name: COLUMN mst_module.created_byid; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.mst_module.created_byid IS '0=by System, else by user id';


--
-- Name: COLUMN mst_module.moduletype; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.mst_module.moduletype IS '0=Owner 1=Users';

--
-- Name: mst_moduleby_tenant; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mst_moduleby_tenant (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    modulename character varying(225),
    created_byid bigint DEFAULT 0,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp with time zone,
    status integer DEFAULT 1,
    idapplication bigint,
    idowner bigint DEFAULT 0,
    idtenant bigint DEFAULT 0,
    modulecode character varying(10),
    moduletype integer DEFAULT 1
);


ALTER TABLE public.mst_moduleby_tenant OWNER TO postgres;

--
-- Name: COLUMN mst_moduleby_tenant.created_byid; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.mst_moduleby_tenant.created_byid IS '0=by System, else by user id';

--
-- Name: mst_organizations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mst_organizations (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    orgname character varying(225),
    orgdescription text,
    idowner bigint DEFAULT 0,
    idtenant bigint DEFAULT 0,
    idsubtenant bigint,
    orglevel smallint DEFAULT 0,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone,
    created_byid bigint,
    orgcode character varying(10) DEFAULT public.uuid4()
);


ALTER TABLE public.mst_organizations OWNER TO postgres;

--
-- Name: mst_tenantgroup; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mst_tenantgroup (
    groupname character varying(225),
    idtenant bigint,
    created_byid bigint,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone,
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    issubgroup smallint DEFAULT 0,
    idapplication bigint,
    idowner bigint
);


ALTER TABLE public.mst_tenantgroup OWNER TO postgres;

--
-- Name: COLUMN mst_tenantgroup.issubgroup; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.mst_tenantgroup.issubgroup IS '0=normal, 1=subgroup';

--
-- Name: mst_usergroup; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mst_usergroup (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    groupname character varying(225),
    idtenant bigint,
    created_byid bigint DEFAULT 0,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    active smallint DEFAULT 1,
    groupcode character varying(10),
    idapplication bigint DEFAULT 0,
    grouptype integer DEFAULT 1
);


ALTER TABLE public.mst_usergroup OWNER TO postgres;

--
-- Name: COLUMN mst_usergroup.grouptype; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.mst_usergroup.grouptype IS '0=seen by super, 1= seen by admin';

--
-- Name: mst_usergroupacl; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mst_usergroupacl (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    idgroup bigint,
    idtenant bigint,
    idmodule bigint,
    idmenu bigint,
    fcreate smallint DEFAULT 0,
    fread smallint DEFAULT 0,
    fupdate smallint,
    fdelete smallint DEFAULT 0,
    fview smallint DEFAULT 0,
    fapproval smallint DEFAULT 0,
    created_byid bigint,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);


ALTER TABLE public.mst_usergroupacl OWNER TO postgres;

--
-- Name: pass_hist; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pass_hist (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    user_id bigint,
    pwd character varying
);


ALTER TABLE public.pass_hist OWNER TO postgres;

--
-- Name: system_param; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.system_param (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    param_name character varying(255) NOT NULL,
    param_value character varying(255) NOT NULL,
    category character varying(255),
    description character varying(255),
    idtenant integer DEFAULT 0,
    idapp integer DEFAULT 0,
    typeparam smallint DEFAULT 0
);


ALTER TABLE public.system_param OWNER TO postgres;

--
-- Name: COLUMN system_param.typeparam; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.system_param.typeparam IS '0=Open, 1=speciffied';

--
-- Name: trx_eventlog; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.trx_eventlog (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    created_by bigint DEFAULT 0,
    created_at timestamp without time zone DEFAULT now(),
    valuejson json,
    value character varying,
    idapp bigint,
    idmenu bigint,
    description character varying(225),
    cdaction bigint,
    refevent character varying(100)
);


ALTER TABLE public.trx_eventlog OWNER TO postgres;

--
-- Name: trx_sessionlog; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.trx_sessionlog (
    id character varying(100) DEFAULT public.uuid10() NOT NULL,
    created_by bigint,
    created_date timestamp without time zone DEFAULT now(),
    updated_date timestamp without time zone,
    userinfo json,
    expire_date timestamp without time zone,
    status smallint DEFAULT 1
);


ALTER TABLE public.trx_sessionlog OWNER TO postgres;

--
-- Name: COLUMN trx_sessionlog.status; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.trx_sessionlog.status IS '1= Opened, 2=Destroyed';


ALTER TABLE ONLY public.cdid_adminnotification
    ADD CONSTRAINT cdid_adminnotification_pkey PRIMARY KEY (id);


--
-- Name: cdid_dbcorellation cdid_dbcorellation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cdid_dbcorellation
    ADD CONSTRAINT cdid_dbcorellation_pkey PRIMARY KEY (id);


--
-- Name: cdid_liscenses cdid_liscenses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cdid_liscenses
    ADD CONSTRAINT cdid_liscenses_pkey PRIMARY KEY (id);


--
-- Name: cdid_orgapplications cdid_orgapplications_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cdid_orgapplications
    ADD CONSTRAINT cdid_orgapplications_pkey PRIMARY KEY (id);


--
-- Name: cdid_orguserassign cdid_orguserassign_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cdid_orguserassign
    ADD CONSTRAINT cdid_orguserassign_pkey PRIMARY KEY (id);


--
-- Name: cdid_owner cdid_owner_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cdid_owner
    ADD CONSTRAINT cdid_owner_pkey PRIMARY KEY (id);


--
-- Name: cdid_tenant cdid_tenant_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cdid_tenant
    ADD CONSTRAINT cdid_tenant_pkey PRIMARY KEY (id);


--
-- Name: cdid_tenant_user_groupacl cdid_tenant_user_groupacl_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cdid_tenant_user_groupacl
    ADD CONSTRAINT cdid_tenant_user_groupacl_pkey PRIMARY KEY (id);


--
-- Name: cdid_tenant_user cdid_tenant_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cdid_tenant_user
    ADD CONSTRAINT cdid_tenant_user_pkey PRIMARY KEY (id);


--
-- Name: cicd_menuuseracl cicd_menuuseracl_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cicd_menuuseracl
    ADD CONSTRAINT cicd_menuuseracl_pkey PRIMARY KEY (id);


--
-- Name: event_log event_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event_log
    ADD CONSTRAINT event_log_pkey PRIMARY KEY (id);


--
-- Name: mst_application mst_application_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mst_application
    ADD CONSTRAINT mst_application_pkey PRIMARY KEY (id);


--
-- Name: mst_biodata_corell mst_biodata_corell_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mst_biodata_corell
    ADD CONSTRAINT mst_biodata_corell_pkey PRIMARY KEY (id);


--
-- Name: mst_bussines_param mst_bussines_param_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mst_bussines_param
    ADD CONSTRAINT mst_bussines_param_pkey PRIMARY KEY (id);


--
-- Name: mst_menu mst_menu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mst_menu
    ADD CONSTRAINT mst_menu_pkey PRIMARY KEY (id);


--
-- Name: mst_module mst_module_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mst_module
    ADD CONSTRAINT mst_module_pkey PRIMARY KEY (id);


--
-- Name: mst_moduleby_tenant mst_moduleby_tenant_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mst_moduleby_tenant
    ADD CONSTRAINT mst_moduleby_tenant_pkey PRIMARY KEY (id);


--
-- Name: mst_organizations mst_organizations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mst_organizations
    ADD CONSTRAINT mst_organizations_pkey PRIMARY KEY (id);


--
-- Name: mst_tenantgroup mst_tenantgroup_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mst_tenantgroup
    ADD CONSTRAINT mst_tenantgroup_pkey PRIMARY KEY (id);


--
-- Name: mst_usergroup mst_usergroup_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mst_usergroup
    ADD CONSTRAINT mst_usergroup_pkey PRIMARY KEY (id);


--
-- Name: mst_usergroupacl mst_usergroupacl_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mst_usergroupacl
    ADD CONSTRAINT mst_usergroupacl_pkey PRIMARY KEY (id);


--
-- Name: system_param system_param_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.system_param
    ADD CONSTRAINT system_param_pkey PRIMARY KEY (id);


--
-- Name: trx_sessionlog trx_sessionlog_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trx_sessionlog
    ADD CONSTRAINT trx_sessionlog_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

