--liquibase formatted sql

--changeset olga_mik_o:1

CREATE TABLE chats
(
    Id BIGINT PRIMARY KEY
);

CREATE TABLE links
(
    Id SERIAL PRIMARY KEY,
    URL TEXT,
    Chat BIGINT,
    FOREIGN KEY (Chat) REFERENCES chats (Id) ON DELETE CASCADE
);
