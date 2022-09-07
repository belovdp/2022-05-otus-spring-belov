--liquibase formatted sql

--changeset BelovDP:users
CREATE TABLE users(
     id BIGINT PRIMARY KEY AUTO_INCREMENT,
     username VARCHAR (100) NOT NULL,
     email VARCHAR(255),
     password VARCHAR(255) NOT NULL
);

-- Добавление пользователя. Пароль: password
insert into users(username, email, password)
values ('user', 'user@test.ru', '$2a$10$nYuJaLaWDwFY6s/qXqQVhuSUsLptV8OMl7zZOCp5iR2CyJsZykV2i');