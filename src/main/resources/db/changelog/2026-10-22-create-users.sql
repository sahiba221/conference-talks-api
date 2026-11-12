--liquibase formatted sql

--changeset sahiba:201
CREATE TABLE IF NOT EXISTS legacy_users (
id SERIAL PRIMARY KEY,
username VARCHAR(50) NOT NULL UNIQUE,
email VARCHAR(100) NOT NULL,
password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS conference_sponsors (
id SERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL,
tier VARCHAR(50) NOT NULL
);
