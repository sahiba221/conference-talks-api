--liquibase formatted sql

--changeset sahiba:4
DELETE FROM legacy_users
WHERE username = 'sahiba';
--rollback INSERT INTO legacy_users (username, email, password) VALUES ('sahiba', 'aysel@conference.az', 'secret123');
