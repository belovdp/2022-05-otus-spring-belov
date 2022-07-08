# 2022-05-otus-spring-belov
ДЗ по курсу "Разработчик на Spring Framework"

Белов Д.П.

---
#### ДЗ 01
#### [Приложение по проведению тестирования студентов - только вывод вопросов и вариантов ответа (если имеются)](https://github.com/belovdp/2022-05-otus-spring-belov/tree/dev/hw01)
В ресурсах хранятся вопросы и различные ответы к ним в виде CSV файла (5 вопросов).
Вопросы могут быть с выбором из нескольких вариантов или со свободным ответом - на Ваше желание и усмотрение.
Приложение должна просто вывести вопросы теста из CSV-файла с возможными вариантами ответа (если имеются).

---
#### ДЗ 02
#### [Приложение по проведению тестирования студентов (с самим тестированием)](https://github.com/belovdp/2022-05-otus-spring-belov/tree/dev/hw02)
Программа должна спросить у пользователя фамилию и имя, спросить 5 вопросов из CSV-файла и вывести результат тестирования.
Выполняется на основе предыдущего домашнего задания + , собственно, сам функционал тестирования.

---
#### ДЗ 03
#### [Перенести приложение для тестирования студентов на Spring Boot](https://github.com/belovdp/2022-05-otus-spring-belov/tree/dev/hw03)
1. Создать проект, используя Spring Boot Initializr (https://start.spring.io)
2. Перенести приложение проведения опросов из прошлого домашнего задания.
3. Перенести все свойства в application.yml
4. Локализовать выводимые сообщения и вопросы (в CSV-файле). MesageSource должен быть из автоконфигурации Spring Boot.
5. Сделать собственный баннер для приложения.
6. Перенести тесты и использовать spring-boot-test-starter для тестирования

---
#### ДЗ 04
#### [Перевести приложение для проведения опросов на Spring Shell](https://github.com/belovdp/2022-05-otus-spring-belov/tree/dev/hw04)
1. Подключить Spring Shell, используя spring-starter.
2. Написать набор команд, позволяющий проводить опрос.
3. Написать Unit-тесты с помощью spring-boot-starter-test, учесть, что Spring Shell в тестах нужно отключить.
   Набор команд зависит только от Вашего желания.
   Вы можете сделать одну команду, запускающую Ваш Main, а можете построить полноценный интерфейс на Spring Shell.
   Локализовывать команды Spring Shell НЕ НУЖНО (хотя можно, но это долго и непросто).
   Задание сдаётся в виде ссылки на pull-request в чат с преподавателем.
   Вопросы можно задавать в чате, но для оперативности рекомендуем Slack. Данное задание НЕ засчитывает предыдущие!
   Это домашнее задание больше нигде не будет использоваться. Но интерфейс Spring Shell мы будет использовать в дальнейшем.

---
#### ДЗ 05
#### [Создать приложение хранящее информацию о книгах в библиотеке](https://github.com/belovdp/2022-05-otus-spring-belov/tree/dev/hw05)
1. Использовать Spring JDBC и реляционную базу (H2 или настоящую реляционную БД). Настоятельно рекомендуем использовать NamedParametersJdbcTemplate
2. Предусмотреть таблицы авторов, книг и жанров.
3. Предполагается отношение многие-к-одному (у книги один автор и жанр). Опциональное усложнение - отношения многие-ко-многим (у книги может быть много авторов и/или жанров).
4. Интерфейс выполняется на Spring Shell (CRUD книги обязателен, операции с авторами и жанрами - как будет удобно).
5. Скрипт создания таблиц и скрипт заполнения данными должны автоматически запускаться с помощью spring-boot-starter-jdbc.
6. Написать тесты для всех методов DAO и сервиса работы с книгами. Рекомендации к выполнению работы:
7. НЕ делать AbstractDao.
8. НЕ делать наследования в тестах Это домашнее задание является основой для следующих.