package ru.otus.spring.belov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.belov.config.ExamAppProperties;
import ru.otus.spring.belov.service.ExamService;

/**
 * Приложение проведения тестирования пользователя
 */

@SpringBootApplication
@EnableConfigurationProperties(ExamAppProperties.class)
public class ExamApp {

    public static void main(String[] args) {
        try (var context = SpringApplication.run(ExamApp.class, args)) {
            context.getBean(ExamService.class).executeExam();
        }
    }
}
