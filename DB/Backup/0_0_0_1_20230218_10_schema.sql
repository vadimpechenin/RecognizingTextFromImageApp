toc.dat                                                                                             0000600 0004000 0002000 00000010414 14374142421 0014441 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        PGDMP                           {            rti    15.1    15.1                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                    0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                    0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                    1262    19608    rti    DATABASE     w   CREATE DATABASE rti WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
    DROP DATABASE rti;
                postgres    false         �            1259    19900 	   documetns    TABLE     �   CREATE TABLE public.documetns (
    id character varying NOT NULL,
    userid character varying,
    title character varying,
    filepdf bytea,
    filetext bytea
);
    DROP TABLE public.documetns;
       public         heap    postgres    false         �            1259    19869    roles    TABLE     ]   CREATE TABLE public.roles (
    id character varying NOT NULL,
    role character varying
);
    DROP TABLE public.roles;
       public         heap    postgres    false         �            1259    19883 	   userroles    TABLE     �   CREATE TABLE public.userroles (
    id character varying NOT NULL,
    userid character varying NOT NULL,
    roleid character varying NOT NULL
);
    DROP TABLE public.userroles;
       public         heap    postgres    false         �            1259    19876    users    TABLE     �   CREATE TABLE public.users (
    id character varying NOT NULL,
    name character varying,
    surname character varying,
    email character varying,
    username character varying,
    password character varying
);
    DROP TABLE public.users;
       public         heap    postgres    false         w           2606    19906    documetns documetns_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.documetns
    ADD CONSTRAINT documetns_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.documetns DROP CONSTRAINT documetns_pkey;
       public            postgres    false    217         q           2606    19875    roles roles_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.roles DROP CONSTRAINT roles_pkey;
       public            postgres    false    214         u           2606    19889    userroles userroles_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.userroles
    ADD CONSTRAINT userroles_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.userroles DROP CONSTRAINT userroles_pkey;
       public            postgres    false    216         s           2606    19882    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    215         z           2606    19907    documetns documetns_userid_fkey    FK CONSTRAINT     }   ALTER TABLE ONLY public.documetns
    ADD CONSTRAINT documetns_userid_fkey FOREIGN KEY (userid) REFERENCES public.users(id);
 I   ALTER TABLE ONLY public.documetns DROP CONSTRAINT documetns_userid_fkey;
       public          postgres    false    215    3187    217         x           2606    19895    userroles userroles_roleid_fkey    FK CONSTRAINT     }   ALTER TABLE ONLY public.userroles
    ADD CONSTRAINT userroles_roleid_fkey FOREIGN KEY (roleid) REFERENCES public.roles(id);
 I   ALTER TABLE ONLY public.userroles DROP CONSTRAINT userroles_roleid_fkey;
       public          postgres    false    214    216    3185         y           2606    19890    userroles userroles_userid_fkey    FK CONSTRAINT     }   ALTER TABLE ONLY public.userroles
    ADD CONSTRAINT userroles_userid_fkey FOREIGN KEY (userid) REFERENCES public.users(id);
 I   ALTER TABLE ONLY public.userroles DROP CONSTRAINT userroles_userid_fkey;
       public          postgres    false    215    216    3187                                                                                                                                                                                                                                                            restore.sql                                                                                         0000600 0004000 0002000 00000007316 14374142421 0015375 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        --
-- NOTE:
--
-- File paths need to be edited. Search for $$PATH$$ and
-- replace it with the path to the directory containing
-- the extracted data files.
--
--
-- PostgreSQL database dump
--

-- Dumped from database version 15.1
-- Dumped by pg_dump version 15.1

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

DROP DATABASE rti;
--
-- Name: rti; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE rti WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';


ALTER DATABASE rti OWNER TO postgres;

\connect rti

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: documetns; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.documetns (
    id character varying NOT NULL,
    userid character varying,
    title character varying,
    filepdf bytea,
    filetext bytea
);


ALTER TABLE public.documetns OWNER TO postgres;

--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id character varying NOT NULL,
    role character varying
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- Name: userroles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.userroles (
    id character varying NOT NULL,
    userid character varying NOT NULL,
    roleid character varying NOT NULL
);


ALTER TABLE public.userroles OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id character varying NOT NULL,
    name character varying,
    surname character varying,
    email character varying,
    username character varying,
    password character varying
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: documetns documetns_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documetns
    ADD CONSTRAINT documetns_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: userroles userroles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.userroles
    ADD CONSTRAINT userroles_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: documetns documetns_userid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documetns
    ADD CONSTRAINT documetns_userid_fkey FOREIGN KEY (userid) REFERENCES public.users(id);


--
-- Name: userroles userroles_roleid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.userroles
    ADD CONSTRAINT userroles_roleid_fkey FOREIGN KEY (roleid) REFERENCES public.roles(id);


--
-- Name: userroles userroles_userid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.userroles
    ADD CONSTRAINT userroles_userid_fkey FOREIGN KEY (userid) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  