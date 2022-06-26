package ru.otus.spring.belov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.belov.config.ExamAppProperties;

/**
 * Приложение проведения тестирования пользователя
 */

@SpringBootApplication
@EnableConfigurationProperties(ExamAppProperties.class)
public class ExamApp {

    public static void main(String[] args) {
        SpringApplication.run(ExamApp.class, args);
    }
}
