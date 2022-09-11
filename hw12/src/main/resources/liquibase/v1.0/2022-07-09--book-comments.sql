--liquibase formatted sql

--changeset BelovDP:bookComments
CREATE TABLE book_comments
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    text      TEXT NOT NULL,
    book_id  BIGINT,
    FOREIGN KEY (book_id) REFERENCES books (id)
);

--changeset BelovDP:bookCommentsData context:!test
insert into book_comments (id, text, book_id)
values (1, 'Какой то комментарий', 1),
       (2, 'Какой то комментарий 2', 1),
       (3, 'Ещё какой то комментарий', 1),
       (4, 'А вот ещё', 1),
       (5, 'Трололо', 2),
       (6, 'Пыщ пыщ', 2),
       (7, 'Устал придумывать', 3),
       (8, 'Всё', 4);
ALTER TABLE book_comments ALTER COLUMN id RESTART WITH 9;

--changeset BelovDP:bookCommentsData context:test
insert into book_comments (id, text, book_id)
values (1, 'Какой то комментарий', 1),
       (2, 'Какой то комментарий 2', 1),
       (3, 'Ещё какой то комментарий', 1),
       (4, 'А вот ещё', 1),
       (5, 'Трололо', 2),
       (6, 'Пыщ пыщ', 2),
       (7, 'Устал придумывать', 3),
       (8, 'Всё', 4),
       (9, 'Нет не всё', 3),
       (10, 'Ещё ведь тесты', 4),
       (11, 'Ну пожалуй теперь точно всё', 2);
ALTER TABLE book_comments ALTER COLUMN id RESTART WITH 12;