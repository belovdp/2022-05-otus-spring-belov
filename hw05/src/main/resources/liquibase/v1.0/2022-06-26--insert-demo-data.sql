--liquibase formatted sql

--changeset BelovDP:demoData
insert into authors (id, name, birthday)
values (1, 'Александр Сергеевич Пушкин', '1799-05-26'),
       (2, 'Джордж Реймонд Ричард Мартин', '1948-09-20');
ALTER TABLE authors ALTER COLUMN id RESTART WITH 3;

insert into genres (id, name)
values (1, 'Поэма'),
       (2, 'Фэнтези'),
       (3, 'Роман');
ALTER TABLE genres ALTER COLUMN id RESTART WITH 4;

insert into books (title, published, genre_id, author_id)
values ('Руслан и Людмила', '1820-01-01', 1, 1),
       ('Медный всадник', '1833-01-01', 1, 1),
       ('Игра престолов', '1996-08-06', 2, 2),
       ('Битва королей', '1998-11-16', 2, 2),
       ('Умирающий Свет', '1977-10-05', 3, 2);

