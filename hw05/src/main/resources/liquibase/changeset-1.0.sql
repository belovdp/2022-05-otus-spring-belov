--liquibase formatted sql

--changeset BelovDP:createTables
CREATE TABLE Genres
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE Authors
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(255) NOT NULL,
    birthday DATE         NOT NULL
);

CREATE TABLE Books
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    title     VARCHAR(255) NOT NULL,
    published DATE         NOT NULL,
    genre_id  BIGINT,
    author_id BIGINT,
    FOREIGN KEY (genre_id) REFERENCES GENRES (id),
    FOREIGN KEY (author_id) REFERENCES AUTHORS (id)
);

--changeset BelovDP:demoData
insert into Authors (id, name, birthday)
values (1, 'Александр Сергеевич Пушкин', '1799-05-26'),
       (2, 'Джордж Реймонд Ричард Мартин', '1948-09-20');
ALTER TABLE Authors ALTER COLUMN id RESTART WITH 3;

insert into Genres (id, name)
values (1, 'Поэма'),
       (2, 'Фэнтези'),
       (3, 'Роман');
ALTER TABLE Genres ALTER COLUMN id RESTART WITH 4;

insert into Books (title, published, genre_id, author_id)
values ('Руслан и Людмила', '1820-01-01', 1, 1),
       ('Медный всадник', '1833-01-01', 1, 1),
       ('Игра престолов', '1996-08-06', 2, 2),
       ('Битва королей', '1998-11-16', 2, 2),
       ('Умирающий Свет', '1977-10-05', 3, 2);

