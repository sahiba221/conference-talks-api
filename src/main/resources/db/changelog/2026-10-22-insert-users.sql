-- liquibase formatted sql

-- changeset sahiba:2

insert into users (username, email, password)
values ('admin', 'admin@email.com', 'secret123');
-- rollback DELETE FROM users WHERE username = 'admin';