package ru.otus.spring.belov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Приложение хранящее информацию о книгах в библиотеке
 */
@SpringBootApplication
@EnableMongoRepositories
public class MigrationApp {

    public static void main(String[] args) {
        SpringApplication.run(MigrationApp.class, args);
    }
}
