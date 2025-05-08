
CREATE TABLE IF NOT EXISTS book
(
    id bigserial NOT NULL,
    book_name character varying,
    book_description character varying
);

create unique index if not exists idx_id_uuid on book (id);

CREATE USER pinhaubr WITH PASSWORD 'pinhaubr';
GRANT CONNECT ON DATABASE pinha TO pinhaubr;
GRANT USAGE, CREATE ON SCHEMA public TO pinhaubr;
GRANT TRUNCATE ON ALL TABLES IN SCHEMA public TO pinhaubr;
GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE ON ALL TABLES IN SCHEMA public TO pinhaubr;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO pinhaubr;
ALTER USER pinhaubr WITH SUPERUSER;
