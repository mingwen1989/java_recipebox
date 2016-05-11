--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: recipe_tag; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE recipe_tag (
    id integer NOT NULL,
    recipe_id integer,
    tag_id integer
);


ALTER TABLE recipe_tag OWNER TO "Guest";

--
-- Name: recipe_tag_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE recipe_tag_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE recipe_tag_id_seq OWNER TO "Guest";

--
-- Name: recipe_tag_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE recipe_tag_id_seq OWNED BY recipe_tag.id;


--
-- Name: recipes; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE recipes (
    title character varying,
    instructions character varying,
    rating integer,
    id integer NOT NULL
);


ALTER TABLE recipes OWNER TO "Guest";

--
-- Name: recipes2_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE recipes2_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE recipes2_id_seq OWNER TO "Guest";

--
-- Name: recipes2_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE recipes2_id_seq OWNED BY recipes.id;


--
-- Name: recipes_temp; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE recipes_temp (
    id integer NOT NULL,
    title character varying,
    instructions character varying,
    rating integer
);


ALTER TABLE recipes_temp OWNER TO "Guest";

--
-- Name: tags; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE tags (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE tags OWNER TO "Guest";

--
-- Name: tags_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE tags_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tags_id_seq OWNER TO "Guest";

--
-- Name: tags_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE tags_id_seq OWNED BY tags.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY recipe_tag ALTER COLUMN id SET DEFAULT nextval('recipe_tag_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY recipes ALTER COLUMN id SET DEFAULT nextval('recipes2_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY tags ALTER COLUMN id SET DEFAULT nextval('tags_id_seq'::regclass);


--
-- Data for Name: recipe_tag; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY recipe_tag (id, recipe_id, tag_id) FROM stdin;
2	1	4
\.


--
-- Name: recipe_tag_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('recipe_tag_id_seq', 2, true);


--
-- Data for Name: recipes; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY recipes (title, instructions, rating, id) FROM stdin;
Something Great	oiwejroinwelirj	5	1
Banana	owiejrowij	4	2
Apple	owiejrowij	3	3
Something else	weoijroij	1	4
\.


--
-- Name: recipes2_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('recipes2_id_seq', 4, true);


--
-- Data for Name: recipes_temp; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY recipes_temp (id, title, instructions, rating) FROM stdin;
\.


--
-- Data for Name: tags; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY tags (id, name) FROM stdin;
3	French
4	American
\.


--
-- Name: tags_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('tags_id_seq', 4, true);


--
-- Name: recipe_tag_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY recipe_tag
    ADD CONSTRAINT recipe_tag_pkey PRIMARY KEY (id);


--
-- Name: recipes2_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY recipes
    ADD CONSTRAINT recipes2_pkey PRIMARY KEY (id);


--
-- Name: tags_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY tags
    ADD CONSTRAINT tags_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

