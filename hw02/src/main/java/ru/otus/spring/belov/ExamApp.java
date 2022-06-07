package ru.otus.spring.belov;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.spring.belov.config.ExamAppConfig;
import ru.otus.spring.belov.service.ExamService;

/**
 * Приложение проведения тестирования пользователя
 */
public class ExamApp {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ExamAppConfig.class)) {
            ExamService service = context.getBean(ExamService.class);
            service.executeExam();
        }
    }
}
