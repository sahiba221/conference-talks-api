--liquibase formatted sql

--changeset sahiba:5
DROP TABLE IF EXISTS legacy_users;
--rollback CREATE TABLE legacy_users (id SERIAL PRIMARY KEY, username VARCHAR(50) NOT NULL UNIQUE, email VARCHAR(100) NOT NULL, password VARCHAR(100) NOT NULL);
