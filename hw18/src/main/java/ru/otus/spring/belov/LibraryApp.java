package ru.otus.spring.belov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

/**
 * Приложение хранящее информацию о книгах в библиотеке
 */
@EnableCircuitBreaker
@SpringBootApplication
public class LibraryApp {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApp.class, args);
    }
}
