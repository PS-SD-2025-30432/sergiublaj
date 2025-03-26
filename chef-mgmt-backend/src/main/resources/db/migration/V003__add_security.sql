CREATE TABLE IF NOT EXISTS "user" (
    id uuid NOT NULL,
    email character varying(255),
    password character varying(255),
    role character varying(255),
    CONSTRAINT user_pkey PRIMARY KEY (id)
);