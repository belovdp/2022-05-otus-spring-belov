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

---
#### ДЗ 06
#### [Переписать приложение для хранения книг на ORM](https://github.com/belovdp/2022-05-otus-spring-belov/tree/dev/hw06)
Домашнее задание выполняется переписыванием предыдущего на JPA.
1. Использовать JPA, Hibernate только в качестве JPA-провайдера.
2. Для решения проблемы N+1 можно использовать специфические для Hibernate аннотации @Fetch и @BatchSize.
3. Добавить сущность "комментария к книге", реализовать CRUD для новой сущности.
4. Покрыть репозитории тестами, используя H2 базу данных и соответствующий H2 Hibernate-диалект для тестов.
5. Не забудьте отключить DDL через Hibernate
6. @Transactional рекомендуется ставить только на методы сервиса.\
   Это домашнее задание будет использоваться в качестве основы для других ДЗ Данная работа не засчитывает предыдущую!

---
#### ДЗ 07
#### [Переписать библиотеку на Spring Data JPA](https://github.com/belovdp/2022-05-otus-spring-belov/tree/dev/hw07)
Домашнее задание выполняется переписыванием предыдущего на JPA.
1. Переписать все репозитории по работе с книгами на Spring Data JPA репозитории.
2. Используйте spring-boot-starter-data-jpa.
3. Кастомные методы репозиториев (или с хитрым @Query) покрыть тестами, используя H2.
4. @Transactional рекомендуется ставить на методы сервисов, а не репозиториев.\
   Это домашнее задание будет использоваться в качестве основы для других ДЗ Данная работа не засчитывает предыдущую!

---
#### ДЗ 08
#### [Использовать MongoDB и spring-data для хранения информации о книгах](https://github.com/belovdp/2022-05-otus-spring-belov/tree/dev/hw08)
Задание может выполняться на основе предыдущего, а может быть выполнено самостоятельно
1. Использовать Spring Data MongoDB репозитории, а если не хватает функциональности, то и *Operations
2. Тесты можно реализовать с помощью Flapdoodle Embedded MongoDB
3. Hibernate, равно, как и JPA, и spring-boot-starter-data-jpa не должно остаться в зависимостях, если ДЗ выполняется на основе предыдущего.
4. Как хранить книги, авторов, жанры и комментарии решать Вам. Но перенесённая с реляционной базы структура не всегдабудет подходить для MongoDB.

---
#### ДЗ 09
#### [CRUD приложение с Web UI и хранением данных в БД](https://github.com/belovdp/2022-05-otus-spring-belov/tree/dev/hw09)
1. Создать приложение с хранением сущностей в БД (можно взять библиотеку и DAO/репозитории из прошлых занятий)
2. Использовать классический View на Thymeleaf, classic Controllers.
3. Для книг (главной сущности) на UI должны быть доступные все CRUD операции. CRUD остальных сущностей - по желанию/необходимости.
4. Локализацию делать НЕ нужно - она строго опциональна.
5. Данное задание НЕ засчитывает предыдущие!
6. Это домашнее задание частично будет использоваться в дальнейшем

---
#### ДЗ 10
#### [Переписать приложение с использованием AJAX и REST-контроллеров](https://github.com/belovdp/2022-05-otus-spring-belov/tree/dev/hw10)
1. Переписать приложение с классических View на AJAX архитектуру и REST-контроллеры.
2. Минимум: получение одной сущности и отображение её на странице с помощью XmlHttpRequest, fetch api или jQuery
3. Опционально максимум: полноценное SPA приложение на React/Vue/Angular, только REST-контроллеры.
4. В случае разработки SPA - рекомендуется вынести репозиторий с front-end. Используйте proxy при разработке (настройки CORS не должно быть).
5. Данное задание, выполненное в виде SPA засчитывает ДЗ №9 (Занятие №15).

---
#### ДЗ 11
#### [Использовать WebFlux](https://github.com/belovdp/2022-05-otus-spring-belov/tree/dev/hw11)
1. За основу для выполнения работы можно взять ДЗ с Ajax + REST (классическое веб-приложение для этого луче не использовать).\
   Но можно выбрать другую доменную модель (не библиотеку).
2. Необходимо использовать Reactive Spring Data Repositories.
3. В качестве БД лучше использовать MongoDB или Redis. Использовать PostgreSQL и экспериментальную R2DBC не рекомендуется.
4. RxJava vs Project Reactor - на Ваш вкус.
5. Вместо классического Spring MVC и embedded Web-сервера использовать WebFlux.
6. Опционально: реализовать на Functional Endpoints

---
#### ДЗ 12
#### [В CRUD Web-приложение добавить механизм аутентификации](https://github.com/belovdp/2022-05-otus-spring-belov/tree/dev/hw12)
1. Добавить в приложение новую сущность - пользователь.\
   Не обязательно реализовывать методы по созданию пользователей - допустимо добавить пользователей только через БД-скрипты.
2. В существующее CRUD-приложение добавить механизм Form-based аутентификации.
3. UsersServices реализовать самостоятельно.
4. Авторизация на всех страницах - для всех аутентифицированных. Форма логина - доступна для всех.
5. Написать тесты контроллеров, которые проверяют, что все необходимые ресурсы действительно защищены.

---
#### ДЗ 13
#### [Ввести авторизацию на основе URL и/или доменных сущностей](https://github.com/belovdp/2022-05-otus-spring-belov/tree/dev/hw13)
1. Минимум: настроить в приложении авторизацию на уровне URL.
2. Максимум: настроить в приложении авторизацию на основе доменных сущностей и методов сервиса.\
   Рекомендации по выполнению:
3. Не рекомендуется выделять пользователей с разными правами в разные классы - т.е. просто один класс пользователя.
4. В случае авторизации на основе доменных сущностей и PostgreSQL не используйте GUID для сущностей.
5. Написать тесты контроллеров, которые проверяют, что все необходимые ресурсы действительно защищены\
   Данное задание НЕ засчитывает предыдущие!