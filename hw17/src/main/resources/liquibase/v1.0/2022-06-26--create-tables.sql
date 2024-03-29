--liquibase formatted sql

--changeset BelovDP:createTables
CREATE TABLE genres
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY
        CONSTRAINT PK_genres PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE authors
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY
        CONSTRAINT PK_authors PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    birthday DATE         NOT NULL
);

CREATE TABLE books
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY
        CONSTRAINT PK_books PRIMARY KEY,
    title     VARCHAR(255) NOT NULL,
    published DATE         NOT NULL,
    genre_id  BIGINT,
    author_id BIGINT,
    FOREIGN KEY (genre_id) REFERENCES genres (id),
    FOREIGN KEY (author_id) REFERENCES authors (id)
);