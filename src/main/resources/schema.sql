CREATE TABLE IF NOT EXISTS public.accounts
(
    id integer,
    name character varying(50) COLLATE pg_catalog."default",
    main_flag boolean DEFAULT false,
    value real
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.accounts
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.daily_expenses
(
    id integer NOT NULL,
    type_id integer,
    summ real,
    comment character varying(100) COLLATE pg_catalog."default" DEFAULT NULL::character varying,
    date date,
    CONSTRAINT daily_expenses_pkey PRIMARY KEY (id),
    CONSTRAINT daily_expenses_type_fkey FOREIGN KEY (type_id)
        REFERENCES public.type_expenses (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.daily_expenses
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.daily_incomes
(
    id integer,
    type_id integer,
    summ real,
    comment character varying(100) COLLATE pg_catalog."default",
    date date
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.daily_incomes
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.type_expenses
(
    id integer NOT NULL,
    name character varying(20) COLLATE pg_catalog."default",
    CONSTRAINT type_expenses_pkey PRIMARY KEY (id),
    CONSTRAINT type_expenses_name_key UNIQUE (name)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.type_expenses
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.type_incomes
(
    id integer NOT NULL,
    name character varying(30) COLLATE pg_catalog."default",
    CONSTRAINT type_income_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.type_incomes
    OWNER to postgres;