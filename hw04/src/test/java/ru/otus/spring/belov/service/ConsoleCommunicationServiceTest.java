package ru.otus.spring.belov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.otus.spring.belov.domain.Answer;
import ru.otus.spring.belov.domain.Exam;
import ru.otus.spring.belov.domain.Question;
import ru.otus.spring.belov.domain.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@DisplayName("Тест комуникации с пользователем")
@SpringBootTest
class ConsoleCommunicationServiceTest {

    @MockBean
    private MessageService messageService;
    @MockBean
    private IOService ioService;
    @SpyBean
    private CommunicationService communicationService;

    @Test
    @DisplayName("Тест перемешивания ответов")
    void shuffleTest() {
        var isShuffled = false;
        // Проверяет что ответы перемешиваются и сколько раз бы мы не перемешивали, объект Question содержит указатель на верный ответ
        for (int i = 0; i < 100; i++) {
            var question = new Question("Question 1", Arrays.asList("a", "b", "c"), 1);
            communicationService.askQuestion(question, true);
            assertEquals("b", question.getAnswers().get(question.getRightAnswerIndex()), "Неверный ответ");
            if (question.getRightAnswerIndex() != 1) {
                isShuffled = true;
            }
        }
        assertTrue(isShuffled, "Ответы не перемешались");
    }

    @Test
    @DisplayName("Тест на то что пока пользователь не введет валидный ответ, от него не отстанут")
    void parseIntTest() {
        when(ioService.readIntAnswer()).thenReturn(-1, 3, 5, 1);
        var answer = communicationService.getAnswer(new Question("Question 1", Arrays.asList("a", "b", "c"), 1), 1);
        verify(ioService, times(4)).readIntAnswer();
        assertEquals(1, answer.getAnswer(), "Неверный ответ");
    }

    @DisplayName("Тест на то что пользователю предложат несколько попыток ответить")
    @ParameterizedTest(name = "Попыток ответа: {0}")
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void attemptsTest(int attempts) {
        communicationService.getAnswer(new Question("Question 1", Arrays.asList("a", "b", "c", "d", "e", "f", "g"), 5), attempts);
        verify(communicationService, times(attempts)).getAnswer(any(), anyInt());
    }

    @DisplayName("Тест вывод результата")
    @ParameterizedTest(name = "Попыток ответа: {0}")
    @CsvSource({
            "1, false",
            "2, false",
            "3, true",
            "4, true",
            "5, true",
    })
    void printResultTest(int rightAnswer, boolean isPassed) {
        when(messageService.getMessage(anyString(), any())).thenReturn("");
        var exam = new Exam(3, false, 1, new User("sdf", "ggg"));
        exam.setQuestions(new ArrayList<>());
        IntStream.range(0, 5).forEach(index -> {
            var question = new Question("Question", List.of(), 3);
            var answer = new Answer(question, index < rightAnswer ? 3 : 2);
            exam.getQuestions().add(question);
            exam.addAnswer(answer);
        });
        communicationService.printResult(exam);
        verify(ioService, times(isPassed ? 0 : 1)).printError(anyString());
        verify(ioService, times(isPassed ? 3 : 2)).print(anyString());
        assertEquals(rightAnswer, exam.getRightAnswersCount(), "Неверное количество правильных ответов");
        assertEquals(5, exam.getQuestions().size(), "Неверное количество вопросов");
    }
}