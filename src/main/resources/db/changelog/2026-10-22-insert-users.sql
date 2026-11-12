--liquibase formatted sql

--changeset sahiba:202
INSERT INTO legacy_users (username, email, password) VALUES ('aysel', 'aysel@conference.az', 'secret123') ON CONFLICT (username) DO NOTHING;
INSERT INTO legacy_users (username, email, password) VALUES ('rashad', 'rashad@conference.az', 'secret456') ON CONFLICT (username) DO NOTHING;
INSERT INTO conference_sponsors (name, tier) VALUES ('AzTech Corp', 'GOLD');
INSERT INTO conference_sponsors (name, tier) VALUES ('Baku Cloud Systems', 'SILVER');
