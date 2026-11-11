--liquibase formatted sql

--changeset sahiba:2
INSERT INTO legacy_users (username, email, password)
VALUES ('aysel', 'aysel@conference.az', 'secret123');

INSERT INTO legacy_users (username, email, password)
VALUES ('rashad', 'rashad@conference.az', 'secret456');

INSERT INTO conference_sponsors (name, tier)
VALUES ('AzTech Corp', 'GOLD');

INSERT INTO conference_sponsors (name, tier)
VALUES ('Baku Cloud Systems', 'SILVER');
--rollback DELETE FROM conference_sponsors; DELETE FROM legacy_users;
