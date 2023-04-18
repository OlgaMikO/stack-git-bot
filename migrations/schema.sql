--liquibase formatted sql

--changeset olga_mik_o:1

CREATE TABLE chats
(
    Id BIGINT UNIQUE PRIMARY KEY
);

CREATE TABLE links
(
    Id SERIAL PRIMARY KEY,
    URL TEXT,
    Chat BIGINT,
    Last_update TIMESTAMP WITH TIME ZONE,
    Last_activity TIMESTAMP WITH TIME ZONE,
    UNIQUE (URL, Chat),
    FOREIGN KEY (Chat) REFERENCES chats (Id) ON DELETE CASCADE
);
