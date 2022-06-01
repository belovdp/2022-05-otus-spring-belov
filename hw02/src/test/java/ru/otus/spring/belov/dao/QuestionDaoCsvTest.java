package ru.otus.spring.belov.dao;

import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;
import ru.otus.spring.belov.domain.Question;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест чтения csv файла с вопросами")
class QuestionDaoCsvTest {

    @Test
    @DisplayName("Тест чтения вопросов с валидного файла")
    void testOk() {
        QuestionDaoCsv daoCsv = new QuestionDaoCsv("/questionsOk.csv");
        List<Question> questions = daoCsv.findAll();
        assertEquals(5, questions.size(), "Неверное количество записей");
        Question question = questions.get(2);
        assertAll("Неверное состояние вопроса N3",
                () -> assertEquals("Finish the inscription on Dobby's tombstone: \"Here lies Dobby...\"",
                        question.getQuestion(), "Вопрос"),
                () -> assertEquals(4, question.getAnswers().size(), "Количество ответов"),
                () -> assertEquals(2, question.getRightAnswerIndex(), "Ответ")
        );
    }

    @DisplayName("Тест на проверку обязательных полей")
    @ParameterizedTest(name = "Файл {0}")
    @ValueSource(strings = {"/requiredQuestion.csv", "/requiredRightAnswer.csv"})
    void testRequiredFields(String resourceName) {
        QuestionDaoCsv daoCsv = new QuestionDaoCsv(resourceName);
        Exception e = assertThrows(RuntimeException.class, daoCsv::findAll, "Ожидается ошибка о том что не заполнено обязательное поле");
        assertEquals(CsvRequiredFieldEmptyException.class, e.getCause().getClass(), "Ожидается другая ошибка");
    }

    /**
     * Тестирует валидацию вопросов
     * @param question вопрос
     * @param isValid  {@code true} вопрос вылидный, {@code false} - иначе
     */
    @ParameterizedTest
    @DisplayName("Тест валидации вопросов")
    @MethodSource("validateTestData")
    void validateTest(Question question, boolean isValid) {
        QuestionDaoCsv daoCsv = new QuestionDaoCsv(null);
        if (isValid) {
            ReflectionTestUtils.invokeMethod(daoCsv, "validateQuestions", List.of(question));
        } else {
            assertThrows(IllegalStateException.class, () -> ReflectionTestUtils.invokeMethod(daoCsv, "validateQuestions", List.of(question)),
                    "Ожидается ошибка о том что не заполнено обязательное поле");
        }
    }

    /**
     * Генерация тестовых наборов данных для {@link QuestionDaoCsvTest#validateTest}
     * @return тестовый набор данных
     */
    private static Stream<Arguments> validateTestData() {
        return Stream.of(
                Arguments.of(new Question("Question 1", List.of("a", "b", "c"), 1), true),
                Arguments.of(new Question("Question 2", List.of("a", "b"), 1), true),
                Arguments.of(new Question("Question 3", List.of("a", "b", "c", "d"), 3), true),
                Arguments.of(new Question("Question 4", List.of("a", "b", "c"), 3), false),
                Arguments.of(new Question("Question 5", List.of("a"), 0), false),
                Arguments.of(new Question("Question 6", List.of(), 0), false)
        );
    }
}