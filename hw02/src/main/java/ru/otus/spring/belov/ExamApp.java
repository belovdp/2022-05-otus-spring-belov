package ru.otus.spring.belov;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.belov.service.ExamService;

/**
 * Приложение проведения тестирования пользователя
 */
@ComponentScan
@PropertySource("classpath:application.properties")
public class ExamApp {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ExamApp.class)) {
            ExamService service = context.getBean(ExamService.class);
            service.executeExam();
        }
    }
}
