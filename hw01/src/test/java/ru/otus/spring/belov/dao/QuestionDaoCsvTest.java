package ru.otus.spring.belov.dao;

import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.otus.spring.belov.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест чтения csv файла с вопросами")
class QuestionDaoCsvTest {

    @Test
    @DisplayName("Тест чтения вопросов с валидного файла")
    void testOk() {
        QuestionDaoCsv daoCsv = new QuestionDaoCsv("questionsOk.csv");
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
    @ValueSource(strings = {"requiredQuestion.csv", "requiredRightAnswer.csv"})
    void testRequiredFields(String resourceName) {
        QuestionDaoCsv daoCsv = new QuestionDaoCsv(resourceName);
        Exception e = assertThrows(RuntimeException.class, daoCsv::findAll, "Ожидается ошибка о том что не заполнено обязательное поле");
        assertEquals(CsvRequiredFieldEmptyException.class, e.getCause().getClass(), "Ожидается другая ошибка");
    }
}