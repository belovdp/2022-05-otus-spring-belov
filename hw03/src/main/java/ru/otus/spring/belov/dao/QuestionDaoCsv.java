package ru.otus.spring.belov.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;
import ru.otus.spring.belov.component.LocaleHolder;
import ru.otus.spring.belov.config.ExamAppProperties;
import ru.otus.spring.belov.domain.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import static java.text.MessageFormat.format;

/**
 * DAO по работе с вопросами тестирования
 * Считывает вопросы из CSV ресурса.
 * В качестве разделителя используется {@link QuestionDaoCsv#CSV_SEPARATOR}
 */
@Component
public class QuestionDaoCsv implements QuestionDao {

    /** Разделитель в CSV файле */
    private static final char CSV_SEPARATOR = ';';
    /** Навзание ресурса, содержащего вопросы для тестирвания */
    private final String csvResourceName;
    /** Компонент работы с локалью */
    private final LocaleHolder localeHolder;

    /**
     * Конструктор
     * @param examAppProperties настройки приложения
     * @param localeHolder      компонент работы с локалью
     */
    public QuestionDaoCsv(ExamAppProperties examAppProperties, LocaleHolder localeHolder) {
        this.csvResourceName = examAppProperties.getFilename();
        this.localeHolder = localeHolder;
    }

    @Override
    public List<Question> findAll() {
        var resourcePath = "/questions_" + localeHolder.getLocale() + "/" + csvResourceName;
        try (var reader = new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream(resourcePath), "Не найден файл с вопросами"))) {
            var questions = new CsvToBeanBuilder<Question>(reader)
                    .withType(Question.class)
                    .withSeparator(CSV_SEPARATOR)
                    .build()
                    .parse();
            validateQuestions(questions);
            return questions;
        } catch (IOException e) {
            throw new IllegalStateException("Error reading the questionnaire", e);
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
                throw new IllegalStateException(format("The question \"{0}\" should have several possible answers", question.getQuestion()));
            }
            if (question.getAnswers().size() - 1 < question.getRightAnswerIndex()) {
                throw new IllegalStateException(format("The question \"{0}\" has an incorrect answer", question.getQuestion()));
            }
        });
    }
}
