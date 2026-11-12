--liquibase formatted sql

--changeset sahiba:203
UPDATE legacy_users SET username = 'sahiba' WHERE username = 'aysel';
