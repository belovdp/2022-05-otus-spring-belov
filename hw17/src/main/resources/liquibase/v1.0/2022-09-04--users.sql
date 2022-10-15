--liquibase formatted sql

--changeset BelovDP:users
CREATE TABLE users(
     id BIGINT PRIMARY KEY AUTO_INCREMENT,
     username VARCHAR (100) NOT NULL,
     email VARCHAR(255),
     password VARCHAR(255) NOT NULL,
     role VARCHAR(100)
);

-- Добавление пользователя. Пароль: password
insert into users(username, email, password, role)
values ('admin', 'admin@test.ru', '$2a$10$nYuJaLaWDwFY6s/qXqQVhuSUsLptV8OMl7zZOCp5iR2CyJsZykV2i', 'ROLE_ADMIN');
-- Добавление пользователя. Пароль: password
insert into users(username, email, password, role)
values ('user', 'user@test.ru', '$2a$10$Sg.7p0G/BxmAsW9IgWWSA.a1NKzNB770.6rRIUD4tI5TTVZdV30oS', 'ROLE_USER');