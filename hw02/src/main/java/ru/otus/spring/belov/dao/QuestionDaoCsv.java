package ru.otus.spring.belov.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.spring.belov.domain.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;

import static java.text.MessageFormat.format;

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

    /**
     * Конструктор
     * @param scvResourcePath путь до ресурса, содержащего вопросы для тестирвания
     */
    public QuestionDaoCsv(@Value("/${examApp.filename}") String scvResourcePath) {
        this.scvResourcePath = scvResourcePath;
    }

    @Override
    public List<Question> findAll() {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(
                this.getClass().getResourceAsStream(scvResourcePath), "Не найден файл с вопросами"))) {
            List<Question> questions = new CsvToBeanBuilder<Question>(reader)
                    .withType(Question.class)
                    .withSeparator(CSV_SEPARATOR)
                    .build()
                    .parse();
            validateQuestions(questions);
            return questions;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения вопросника", e);
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
                throw new IllegalStateException(format("У вопроса \"{0}\" должно быть несколько вариантов ответа", question.getQuestion()));
            }
            if (question.getAnswers().size() - 1 < question.getRightAnswerIndex()) {
                throw new IllegalStateException(format("У вопроса \"{0}\" указан неверный ответ", question.getQuestion()));
            }
        });
    }
}
