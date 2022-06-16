package ru.otus.spring.belov.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;
import ru.otus.spring.belov.config.ExamAppProperties;
import ru.otus.spring.belov.domain.Question;
import ru.otus.spring.belov.service.MessageService;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

/**
 * DAO по работе с вопросами тестирования
 * Считывает вопросы из CSV ресурса {@link QuestionDaoCsv#scvResourcePath}.
 * В качестве разделителя используется {@link QuestionDaoCsv#CSV_SEPARATOR}
 */
@Component
public class QuestionDaoCsv implements QuestionDao {

    /** Разделитель в CSV файле */
    private static final char CSV_SEPARATOR = ';';
    /** Путь до ресурса, содержащего вопросы для тестирвания */
    private final String scvResourcePath;
    /** Компонент локализаци */
    private final MessageService messageService;

    /**
     * Конструктор
     * @param examAppProperties настройки приложения
     * @param messageService    сервис работы с сообщениями
     */
    public QuestionDaoCsv(ExamAppProperties examAppProperties, MessageService messageService) {
        this.scvResourcePath = examAppProperties.getFilename();
        this.messageService = messageService;
    }

    @Override
    public List<Question> findAll() {
        try (var reader = new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream(scvResourcePath), "Не найден файл с вопросами"))) {
            var questions = new CsvToBeanBuilder<Question>(reader)
                    .withType(Question.class)
                    .withSeparator(CSV_SEPARATOR)
                    .build()
                    .parse();
            validateQuestions(questions);
            return questions;
        } catch (IOException e) {
            throw new RuntimeException(messageService.getMessage("error.dao.cant_read"), e);
        }
    }

    /**
     * Проверяет валидность вопросов
     * @param questions список вопросов
     * @throws IllegalStateException если на вопрос меньше двух возможных ответов или невозможно определить правильный ответ
     */
    private void validateQuestions(List<Question> questions) {
        questions.forEach(question -> {
            if (question.getAnswers() == null || question.getAnswers().size() < 2) {
                throw new IllegalStateException(messageService.getMessage("error.dao.need_more_answers", question.getQuestion()));
            }
            if (question.getAnswers().size() - 1 < question.getRightAnswerIndex()) {
                throw new IllegalStateException(messageService.getMessage("error.dao.invalid_answer", question.getQuestion()));
            }
        });
    }
}
