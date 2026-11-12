--liquibase formatted sql

--changeset sahiba:204
DELETE FROM legacy_users WHERE username = 'sahiba';
