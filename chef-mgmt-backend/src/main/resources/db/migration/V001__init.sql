CREATE TABLE IF NOT EXISTS chef (
    id uuid NOT NULL,
    name character varying(255),
    rating double precision,
    CONSTRAINT chef_pkey PRIMARY KEY (id)
);