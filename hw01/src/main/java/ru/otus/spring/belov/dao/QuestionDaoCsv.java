package ru.otus.spring.belov.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.spring.belov.domain.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;

/**
 * DAO по работе с вопросами тестирования
 * Считывает вопросы из CSV ресурса {@link QuestionDaoCsv#scvResourceName}.
 * В качестве разделителя используется {@link QuestionDaoCsv#CSV_SEPARATOR}
 */
@RequiredArgsConstructor
public class QuestionDaoCsv implements QuestionDao {

    /** Разделитель в CSV файле */
    private static final char CSV_SEPARATOR = ';';
    /** Путь до ресурса, содержащего вопросы для тестирвания */
    private final String scvResourceName;

    @Override
    public List<Question> findAll() {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(
                this.getClass().getResourceAsStream("/" + scvResourceName), "Не найден файл с вопросами"))) {
            return new CsvToBeanBuilder<Question>(reader)
                    .withType(Question.class)
                    .withSeparator(CSV_SEPARATOR)
                    .build()
                    .parse();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения вопросника", e);
        }
    }
}
