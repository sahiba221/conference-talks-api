--liquibase formatted sql

--changeset sahiba:3
UPDATE legacy_users
SET username = 'sahiba'
WHERE username = 'aysel';
--rollback UPDATE legacy_users SET username = 'aysel' WHERE username = 'sahiba';
