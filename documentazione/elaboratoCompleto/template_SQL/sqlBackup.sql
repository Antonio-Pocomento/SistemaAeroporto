--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

-- Started on 2025-09-09 14:43:36

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 885 (class 1247 OID 25130)
-- Name: ruolo_utente; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.ruolo_utente AS ENUM (
    'generico',
    'amministratore'
);


ALTER TYPE public.ruolo_utente OWNER TO postgres;

--
-- TOC entry 861 (class 1247 OID 25000)
-- Name: stato_bagaglio; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.stato_bagaglio AS ENUM (
    'CARICATO',
    'RITIRABILE',
    'SMARRITO',
    'REGISTRATO'
);


ALTER TYPE public.stato_bagaglio OWNER TO postgres;

--
-- TOC entry 864 (class 1247 OID 25010)
-- Name: stato_prenotazione; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.stato_prenotazione AS ENUM (
    'CONFERMATA',
    'IN_ATTESA',
    'CANCELLATA'
);


ALTER TYPE public.stato_prenotazione OWNER TO postgres;

--
-- TOC entry 867 (class 1247 OID 25018)
-- Name: stato_volo; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.stato_volo AS ENUM (
    'PROGRAMMATO',
    'IN_RITARDO',
    'ATTERRATO',
    'DECOLLATO',
    'CANCELLATO'
);


ALTER TYPE public.stato_volo OWNER TO postgres;

--
-- TOC entry 888 (class 1247 OID 33198)
-- Name: tipo_bagaglio; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.tipo_bagaglio AS ENUM (
    'Zaino',
    'Borsa',
    'Trolley',
    'Valigia'
);


ALTER TYPE public.tipo_bagaglio OWNER TO postgres;

--
-- TOC entry 239 (class 1255 OID 25029)
-- Name: aggiorna_posti_disponibili(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.aggiorna_posti_disponibili() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    -- Caso INSERT: decrementa posti_disponibili se prenotazione attiva
    IF TG_OP = 'INSERT' THEN
        IF NEW.stato != 'CANCELLATA' THEN
            UPDATE volo
            SET posti_disponibili = posti_disponibili - 1
            WHERE codice = NEW.codice_volo;
        END IF;

    -- Caso DELETE: incrementa posti_disponibili se prenotazione attiva
    ELSIF TG_OP = 'DELETE' THEN
        IF OLD.stato != 'CANCELLATA' THEN
            UPDATE volo
            SET posti_disponibili = posti_disponibili + 1
            WHERE codice = OLD.codice_volo;
        END IF;

    -- Caso UPDATE: controlla variazioni di stato e codice_volo
    ELSIF TG_OP = 'UPDATE' THEN
        -- Se lo stato passa da attivo a cancellato: incremento posti
        IF OLD.stato != 'CANCELLATA' AND NEW.stato = 'CANCELLATA' THEN
            UPDATE volo
            SET posti_disponibili = posti_disponibili + 1
            WHERE codice = NEW.codice_volo;

        -- Se cambia il volo mantenendo stato attivo: aggiorna posti_disponibili su entrambi i voli
        ELSIF OLD.codice_volo <> NEW.codice_volo THEN
            IF OLD.stato != 'CANCELLATA' THEN
                UPDATE volo
                SET posti_disponibili = posti_disponibili + 1
                WHERE codice = OLD.codice_volo;
            END IF;

            IF NEW.stato != 'CANCELLATA' THEN
                UPDATE volo
                SET posti_disponibili = posti_disponibili - 1
                WHERE codice = NEW.codice_volo;
            END IF;
        END IF;
    END IF;

    RETURN NEW;
END;
$$;


ALTER FUNCTION public.aggiorna_posti_disponibili() OWNER TO postgres;

--
-- TOC entry 240 (class 1255 OID 25030)
-- Name: blocca_update(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.blocca_update() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  IF OLD.stato = 'CANCELLATA' THEN
    RAISE EXCEPTION 'Modifiche non permesse su prenotazioni cancellate';
  END IF;
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.blocca_update() OWNER TO postgres;

--
-- TOC entry 226 (class 1255 OID 25157)
-- Name: check_amministratore(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_amministratore() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    v_ruolo TEXT;
BEGIN
    -- Recupera il ruolo dell'utente
    SELECT ruolo
    INTO v_ruolo
    FROM utente
    WHERE nomeutente = NEW.amministratore;

    -- Controlla se l'utente esiste
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Utente "%" non esistente.', NEW.amministratore;
    END IF;

    -- Verifica che sia un amministratore
    IF v_ruolo <> 'amministratore' THEN
        RAISE EXCEPTION 'Un utente generico non può gestire un volo.';
    END IF;

    RETURN NEW;
END;
$$;


ALTER FUNCTION public.check_amministratore() OWNER TO postgres;

--
-- TOC entry 224 (class 1255 OID 33195)
-- Name: check_date(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_date() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    -- Sull'inserimento: sempre verifica
    IF TG_OP = 'INSERT' THEN
        IF NEW.data < current_date THEN
            RAISE EXCEPTION 'La data del volo deve essere oggi o successiva.';
        END IF;
    END IF;

    -- Sugli update: verifica solo se la data cambia
    IF TG_OP = 'UPDATE' THEN
        IF NEW.data <> OLD.data THEN
            IF NEW.data < current_date THEN
                RAISE EXCEPTION 'Non puoi modificare la data del volo a una data passata.';
            END IF;
        END IF;
    END IF;

    RETURN NEW;
END;
$$;


ALTER FUNCTION public.check_date() OWNER TO postgres;

--
-- TOC entry 242 (class 1255 OID 25031)
-- Name: check_doppia_prenotazione(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_doppia_prenotazione() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  IF EXISTS (
    SELECT 1
    FROM prenotazione
    WHERE codice_volo = NEW.codice_volo
      AND codice_fiscale = NEW.codice_fiscale
      AND stato != 'CANCELLATA'
	  AND (TG_OP = 'INSERT' OR numero_biglietto <> NEW.numero_biglietto)
  ) AND NEW.stato != 'CANCELLATA' THEN
    RAISE EXCEPTION 'Esiste già una prenotazione attiva per questo passeggero e questo volo';
  END IF;

  RETURN NEW;
END;
$$;


ALTER FUNCTION public.check_doppia_prenotazione() OWNER TO postgres;

--
-- TOC entry 241 (class 1255 OID 25032)
-- Name: check_unique_posto(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_unique_posto() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    n INT;
BEGIN
    SELECT COUNT(*) INTO n
    FROM prenotazione
    WHERE codice_volo = NEW.codice_volo
      AND posto_assegnato = NEW.posto_assegnato
      AND stato != 'CANCELLATA'
      AND numero_biglietto <> NEW.numero_biglietto;

    IF n > 0 AND NEW.stato != 'CANCELLATA' THEN
        RAISE EXCEPTION 'Posto "%” sul volo "%" è già prenotato da un altro utente attivo.', NEW.posto_assegnato, NEW.codice_volo;
    END IF;

    RETURN NEW;
END;
$$;


ALTER FUNCTION public.check_unique_posto() OWNER TO postgres;

--
-- TOC entry 225 (class 1255 OID 25149)
-- Name: check_utente_non_amministratore(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_utente_non_amministratore() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    v_ruolo TEXT;
BEGIN
    -- Recupera il ruolo dell'utente
    SELECT ruolo
    INTO v_ruolo
    FROM utente
    WHERE nomeutente = NEW.nome_utente;

    -- Controlla se l'utente esiste
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Utente "%" non esistente.', NEW.nome_utente;
    END IF;

    -- Verifica che NON sia un amministratore
    IF v_ruolo = 'amministratore' THEN
        RAISE EXCEPTION 'Un amministratore non può effettuare prenotazioni.';
    END IF;

    RETURN NEW;
END;
$$;


ALTER FUNCTION public.check_utente_non_amministratore() OWNER TO postgres;

--
-- TOC entry 243 (class 1255 OID 25147)
-- Name: check_validita_volo(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_validita_volo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    v_stato stato_volo;
    v_posti INTEGER;
    v_origine TEXT;
BEGIN
    -- Recupera informazioni sul volo
    SELECT stato, posti_disponibili, aeroporto_origine
    INTO v_stato, v_posti, v_origine
    FROM volo
    WHERE codice = NEW.codice_volo;

    -- Controlla se il volo esiste
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Il volo "%" non esiste.', NEW.codice_volo;
    END IF;

    -- 1.1) Volo non cancellato
    IF v_stato = 'CANCELLATO' THEN
        RAISE EXCEPTION 'Il volo "%" è stato cancellato.', NEW.codice_volo;
    END IF;

	-- 1.2) Volo non decollato
    IF v_stato = 'DECOLLATO' THEN
        RAISE EXCEPTION 'Il volo "%" è già decollato.', NEW.codice_volo;
    END IF;

    -- 2) Posti disponibili
    IF v_posti <= 0 THEN
        RAISE EXCEPTION 'Nessun posto disponibile sul volo "%".', NEW.codice_volo;
    END IF;

    -- 3) Aeroporto di partenza
    IF v_origine <> 'Napoli' THEN
        RAISE EXCEPTION 'Il volo "%" non parte da Napoli.', NEW.codice_volo;
    END IF;

    RETURN NEW;
END;
$$;


ALTER FUNCTION public.check_validita_volo() OWNER TO postgres;

--
-- TOC entry 227 (class 1255 OID 25161)
-- Name: delete_bagagli(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.delete_bagagli() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    -- Se lo stato NON sta diventando 'CANCELLATA', non fare niente
    IF NEW.stato <> 'CANCELLATA' THEN
        RETURN NEW;
    END IF;

    -- Elimina i bagagli di questo passeggero per questo volo
    DELETE FROM bagaglio
    WHERE codice_fiscale_passeggero = NEW.codice_fiscale
      AND codice_volo = NEW.codice_volo;

    RETURN NEW;
END;
$$;


ALTER FUNCTION public.delete_bagagli() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 25036)
-- Name: bagaglio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bagaglio (
    stato public.stato_bagaglio DEFAULT 'REGISTRATO'::public.stato_bagaglio NOT NULL,
    tipo public.tipo_bagaglio NOT NULL,
    codice_fiscale_passeggero character varying NOT NULL,
    codice integer NOT NULL,
    codice_volo character varying(100) NOT NULL
);


ALTER TABLE public.bagaglio OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 25042)
-- Name: bagaglio_codice_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.bagaglio_codice_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.bagaglio_codice_seq OWNER TO postgres;

--
-- TOC entry 4987 (class 0 OID 0)
-- Dependencies: 218
-- Name: bagaglio_codice_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.bagaglio_codice_seq OWNED BY public.bagaglio.codice;


--
-- TOC entry 219 (class 1259 OID 25043)
-- Name: passeggero; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.passeggero (
    codice_fiscale character varying NOT NULL,
    nome character varying NOT NULL,
    secondo_nome character varying,
    cognome character varying NOT NULL,
    CONSTRAINT chk_campi_solo_lettere CHECK ((((cognome)::text ~ '^[[:alpha:]'' ]+$'::text) AND ((nome)::text ~ '^[[:alpha:]'' ]+$'::text) AND ((secondo_nome IS NULL) OR ((secondo_nome)::text ~ '^[[:alpha:]'' ]+$'::text)))),
    CONSTRAINT chk_codice_fiscale CHECK (((codice_fiscale)::text ~ '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$'::text)),
    CONSTRAINT lunghezza_campi_passeggero CHECK (((length(TRIM(BOTH FROM nome)) > 0) AND (length(TRIM(BOTH FROM cognome)) > 0) AND ((secondo_nome IS NULL) OR (length(TRIM(BOTH FROM secondo_nome)) > 0))))
);


ALTER TABLE public.passeggero OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 25050)
-- Name: prenotazione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.prenotazione (
    numero_biglietto integer NOT NULL,
    posto_assegnato character varying NOT NULL,
    stato public.stato_prenotazione DEFAULT 'IN_ATTESA'::public.stato_prenotazione NOT NULL,
    codice_volo character varying NOT NULL,
    nome_utente character varying NOT NULL,
    codice_fiscale character varying NOT NULL,
    CONSTRAINT chk_posto CHECK (((posto_assegnato)::text ~ '^[1-9][0-9]?[A-HJ-K]$'::text))
);


ALTER TABLE public.prenotazione OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 25056)
-- Name: prenotazione_numerobiglietto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.prenotazione_numerobiglietto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.prenotazione_numerobiglietto_seq OWNER TO postgres;

--
-- TOC entry 4988 (class 0 OID 0)
-- Dependencies: 221
-- Name: prenotazione_numerobiglietto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.prenotazione_numerobiglietto_seq OWNED BY public.prenotazione.numero_biglietto;


--
-- TOC entry 222 (class 1259 OID 25057)
-- Name: utente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.utente (
    nomeutente character varying(255) NOT NULL,
    passwordutente character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    ruolo public.ruolo_utente DEFAULT 'generico'::public.ruolo_utente NOT NULL,
    CONSTRAINT chk_caratteri_validi CHECK ((((nomeutente)::text ~ '^[A-Za-z0-9?!@#$%^&*()_+=-]+$'::text) AND ((passwordutente)::text ~ '^[A-Za-z0-9?!@#$%^&*()_+=-]+$'::text))),
    CONSTRAINT chk_email CHECK (((email)::text ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'::text)),
    CONSTRAINT lunghezza_campi_utente CHECK (((length(TRIM(BOTH FROM nomeutente)) >= 3) AND (length(TRIM(BOTH FROM passwordutente)) >= 8)))
);


ALTER TABLE public.utente OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 25066)
-- Name: volo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.volo (
    codice character varying(100) NOT NULL,
    n_posti integer NOT NULL,
    posti_disponibili integer NOT NULL,
    compagnia_aerea character varying(100) NOT NULL,
    aeroporto_origine character varying(100) NOT NULL,
    aeroporto_destinazione character varying(100) NOT NULL,
    data date NOT NULL,
    orario time without time zone NOT NULL,
    ritardo time without time zone,
    stato public.stato_volo DEFAULT 'PROGRAMMATO'::public.stato_volo NOT NULL,
    numero_gate integer,
    amministratore character varying(100) NOT NULL,
    CONSTRAINT chk_codice_volo CHECK (((codice)::text ~ '^[A-Z0-9]{2}[0-9]{1,4}[A-Z]?$'::text)),
    CONSTRAINT gate_partenza_volo CHECK ((((numero_gate IS NULL) AND ((aeroporto_destinazione)::text = 'Napoli'::text)) OR ((numero_gate IS NOT NULL) AND ((aeroporto_origine)::text = 'Napoli'::text)))),
    CONSTRAINT lunghezza_campi_volo CHECK (((length(TRIM(BOTH FROM aeroporto_origine)) > 0) AND (length(TRIM(BOTH FROM aeroporto_destinazione)) > 0) AND (length(TRIM(BOTH FROM compagnia_aerea)) > 0))),
    CONSTRAINT ritardo_volo CHECK ((((ritardo IS NULL) AND (stato <> 'IN_RITARDO'::public.stato_volo)) OR ((ritardo IS NOT NULL) AND (stato = 'IN_RITARDO'::public.stato_volo)))),
    CONSTRAINT volo_check_aeroporti CHECK (((((aeroporto_origine)::text = 'Napoli'::text) OR ((aeroporto_destinazione)::text = 'Napoli'::text)) AND ((aeroporto_origine)::text <> (aeroporto_destinazione)::text))),
    CONSTRAINT volo_check_posti CHECK (((n_posti > 0) AND (posti_disponibili >= 0) AND (posti_disponibili <= n_posti)))
);


ALTER TABLE public.volo OWNER TO postgres;

--
-- TOC entry 4784 (class 2604 OID 25076)
-- Name: bagaglio codice; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bagaglio ALTER COLUMN codice SET DEFAULT nextval('public.bagaglio_codice_seq'::regclass);


--
-- TOC entry 4785 (class 2604 OID 25077)
-- Name: prenotazione numero_biglietto; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prenotazione ALTER COLUMN numero_biglietto SET DEFAULT nextval('public.prenotazione_numerobiglietto_seq'::regclass);


--
-- TOC entry 4975 (class 0 OID 25036)
-- Dependencies: 217
-- Data for Name: bagaglio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.bagaglio VALUES ('REGISTRATO', 'Valigia', 'RSSMRC80A01H501Z', 67, 'IA452');


--
-- TOC entry 4977 (class 0 OID 25043)
-- Dependencies: 219
-- Data for Name: passeggero; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.passeggero VALUES ('RSSMRC80A01H501Z', 'Marco', NULL, 'Rossi');


--
-- TOC entry 4978 (class 0 OID 25050)
-- Dependencies: 220
-- Data for Name: prenotazione; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.prenotazione VALUES (80, '12A', 'IN_ATTESA', 'IA452', 'MarcoRossi', 'RSSMRC80A01H501Z');


--
-- TOC entry 4980 (class 0 OID 25057)
-- Dependencies: 222
-- Data for Name: utente; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.utente VALUES ('admin', 'admin123', 'admin@adm.com', 'amministratore');
INSERT INTO public.utente VALUES ('MarcoRossi', 'MarcoRossi!2025', 'marco.rossi@gmail.com', 'generico');


--
-- TOC entry 4981 (class 0 OID 25066)
-- Dependencies: 223
-- Data for Name: volo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.volo VALUES ('IA452', 180, 179, 'ItalAir', 'Napoli', 'Milano', '2025-09-10', '14:20:00', NULL, 'PROGRAMMATO', 1, 'admin');


--
-- TOC entry 4989 (class 0 OID 0)
-- Dependencies: 218
-- Name: bagaglio_codice_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bagaglio_codice_seq', 67, true);


--
-- TOC entry 4990 (class 0 OID 0)
-- Dependencies: 221
-- Name: prenotazione_numerobiglietto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.prenotazione_numerobiglietto_seq', 80, true);


--
-- TOC entry 4803 (class 2606 OID 25081)
-- Name: bagaglio bagaglio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bagaglio
    ADD CONSTRAINT bagaglio_pkey PRIMARY KEY (codice);


--
-- TOC entry 4805 (class 2606 OID 25083)
-- Name: passeggero passeggero_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passeggero
    ADD CONSTRAINT passeggero_pkey PRIMARY KEY (codice_fiscale);


--
-- TOC entry 4810 (class 2606 OID 25085)
-- Name: utente pk_nomeutente; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT pk_nomeutente PRIMARY KEY (nomeutente);


--
-- TOC entry 4807 (class 2606 OID 25087)
-- Name: prenotazione prenotazione_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prenotazione
    ADD CONSTRAINT prenotazione_pkey PRIMARY KEY (numero_biglietto);


--
-- TOC entry 4812 (class 2606 OID 25089)
-- Name: utente uq_email; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT uq_email UNIQUE (email);


--
-- TOC entry 4814 (class 2606 OID 25093)
-- Name: volo volo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.volo
    ADD CONSTRAINT volo_pkey PRIMARY KEY (codice);


--
-- TOC entry 4808 (class 1259 OID 25094)
-- Name: unique_active_booking; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX unique_active_booking ON public.prenotazione USING btree (codice_volo, codice_fiscale) WHERE (stato <> 'CANCELLATA'::public.stato_prenotazione);


--
-- TOC entry 4821 (class 2620 OID 25095)
-- Name: prenotazione trg_aggiorna_posti_disponibili; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_aggiorna_posti_disponibili AFTER INSERT OR DELETE OR UPDATE ON public.prenotazione FOR EACH ROW EXECUTE FUNCTION public.aggiorna_posti_disponibili();


--
-- TOC entry 4822 (class 2620 OID 25096)
-- Name: prenotazione trg_blocca_update; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_blocca_update BEFORE UPDATE ON public.prenotazione FOR EACH ROW EXECUTE FUNCTION public.blocca_update();


--
-- TOC entry 4828 (class 2620 OID 25159)
-- Name: volo trg_check_amministratore; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_check_amministratore BEFORE INSERT OR UPDATE ON public.volo FOR EACH ROW EXECUTE FUNCTION public.check_amministratore();


--
-- TOC entry 4829 (class 2620 OID 33196)
-- Name: volo trg_check_date; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_check_date BEFORE INSERT OR UPDATE ON public.volo FOR EACH ROW EXECUTE FUNCTION public.check_date();


--
-- TOC entry 4823 (class 2620 OID 25146)
-- Name: prenotazione trg_check_doppia_prenotazione; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_check_doppia_prenotazione BEFORE INSERT OR UPDATE ON public.prenotazione FOR EACH ROW EXECUTE FUNCTION public.check_doppia_prenotazione();


--
-- TOC entry 4824 (class 2620 OID 25098)
-- Name: prenotazione trg_check_unique_posto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_check_unique_posto BEFORE INSERT OR UPDATE ON public.prenotazione FOR EACH ROW EXECUTE FUNCTION public.check_unique_posto();


--
-- TOC entry 4825 (class 2620 OID 25150)
-- Name: prenotazione trg_check_utente_non_amministratore; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_check_utente_non_amministratore BEFORE INSERT OR UPDATE ON public.prenotazione FOR EACH ROW EXECUTE FUNCTION public.check_utente_non_amministratore();


--
-- TOC entry 4826 (class 2620 OID 25160)
-- Name: prenotazione trg_check_validita_volo; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_check_validita_volo BEFORE INSERT ON public.prenotazione FOR EACH ROW EXECUTE FUNCTION public.check_validita_volo();


--
-- TOC entry 4827 (class 2620 OID 25163)
-- Name: prenotazione trg_delete_bagagli; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_delete_bagagli AFTER UPDATE ON public.prenotazione FOR EACH ROW WHEN (((old.stato IS DISTINCT FROM new.stato) AND (new.stato = 'CANCELLATA'::public.stato_prenotazione))) EXECUTE FUNCTION public.delete_bagagli();


--
-- TOC entry 4820 (class 2606 OID 33238)
-- Name: volo fk_amministratore; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.volo
    ADD CONSTRAINT fk_amministratore FOREIGN KEY (amministratore) REFERENCES public.utente(nomeutente) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 4815 (class 2606 OID 25109)
-- Name: bagaglio fk_codice_fiscale; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bagaglio
    ADD CONSTRAINT fk_codice_fiscale FOREIGN KEY (codice_fiscale_passeggero) REFERENCES public.passeggero(codice_fiscale) ON DELETE CASCADE;


--
-- TOC entry 4817 (class 2606 OID 25141)
-- Name: prenotazione fk_codice_fiscale; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prenotazione
    ADD CONSTRAINT fk_codice_fiscale FOREIGN KEY (codice_fiscale) REFERENCES public.passeggero(codice_fiscale) ON DELETE CASCADE;


--
-- TOC entry 4816 (class 2606 OID 33190)
-- Name: bagaglio fk_codice_volo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bagaglio
    ADD CONSTRAINT fk_codice_volo FOREIGN KEY (codice_volo) REFERENCES public.volo(codice) ON DELETE CASCADE;


--
-- TOC entry 4818 (class 2606 OID 25136)
-- Name: prenotazione fk_nome_utente; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prenotazione
    ADD CONSTRAINT fk_nome_utente FOREIGN KEY (nome_utente) REFERENCES public.utente(nomeutente) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4819 (class 2606 OID 25119)
-- Name: prenotazione fk_volo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prenotazione
    ADD CONSTRAINT fk_volo FOREIGN KEY (codice_volo) REFERENCES public.volo(codice) ON DELETE CASCADE;


-- Completed on 2025-09-09 14:43:36

--
-- PostgreSQL database dump complete
--

