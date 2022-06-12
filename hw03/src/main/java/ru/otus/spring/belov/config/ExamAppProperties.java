package ru.otus.spring.belov.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

/**
 * Настройки приложения
 */
@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "exam-app")
public class ExamAppProperties {

    /** Имя файла с вопросами */
    private final String filename;
    /** Количество требуемых правильных ответов */
    private final int requiredRightAnswers;
    /** Количество попыток на вопрос */
    private final int questionAttempts;
    /** Признак того что ответы на вопрос будут перемешаны */
    private final boolean shuffleAnswers;
}
