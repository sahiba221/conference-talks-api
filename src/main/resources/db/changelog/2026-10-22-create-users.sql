--liquibase formatted sql

--changeset sahiba:1
create table users(
    id serial primary key,
    username varchar(50) not null unique,
    email varchar(100) not null,
    password varchar (50) not null
);

--rollback Drop Table users;