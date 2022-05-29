package ru.otus.spring.belov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ru.otus.spring.belov.dao.QuestionDao;
import ru.otus.spring.belov.domain.Exam;
import ru.otus.spring.belov.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Тест сервиса на работу с тестированием")
@ExtendWith(MockitoExtension.class)
class ExamServiceImplTest {

    @Mock
    private QuestionDao questionDao;
    @Mock
    private UserCommunicationService userCommunicationService;

    /**
     * Тестирует валидацию вопросов
     * @param question вопрос
     * @param isValid  {@code true} вопрос вылидный, {@code false} - иначе
     */
    @ParameterizedTest
    @DisplayName("Тест валидации вопросов. Если проинициализировался сервис, значит вопросы валидны")
    @MethodSource("validateTestData")
    void validateTest(Question question, boolean isValid) {
        when(questionDao.findAll()).thenReturn(List.of(question));
        if (isValid) {
            new ExamServiceImpl(questionDao, userCommunicationService, 1, true, 1);
        } else {
            assertThrows(IllegalStateException.class, () -> new ExamServiceImpl(questionDao, userCommunicationService, 1, true, 1),
                    "Ожидается ошибка о том что не заполнено обязательное поле");
        }
    }

    @Test
    @DisplayName("Тест получиня фамилии и имени пользователя")
    void userInfoTest() {
        when(userCommunicationService.readAnswer()).thenReturn("Dmitriy", "Belov");
        when(questionDao.findAll()).thenReturn(List.of());
        ExamService examService = new ExamServiceImpl(questionDao, userCommunicationService, 1, true, 1);
        examService.executeExam();
        Exam exam = (Exam) ReflectionTestUtils.getField(examService, "exam");
        assert exam != null;
        assertEquals("Dmitriy", exam.getUser().getFirstName(), "Неверное имя");
        assertEquals("Belov", exam.getUser().getLastName(), "Неверная фамилия");
    }

    @Test
    @DisplayName("Тест перемешивания ответов")
    void shuffleTest() {
        when(userCommunicationService.readAnswer()).thenReturn("Dmitriy", "Belov", "1");
        when(questionDao.findAll()).thenReturn(List.of(new Question("Question 1", Arrays.asList("a", "b", "c"), 1)));
        ExamService examService = new ExamServiceImpl(questionDao, userCommunicationService, 1, true, 1);
        boolean isShuffled = false;
        // Проверяет что ответы перемешиваются и сколько раз бы мы не перемешивали, объект Question содержит указатель на верный ответ
        for (int i = 0; i < 100; i++) {
            examService.executeExam();
            Exam exam = (Exam) ReflectionTestUtils.getField(examService, "exam");
            assert exam != null;
            Question question = exam.getQuestions().get(0);
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
        when(userCommunicationService.readAnswer()).thenReturn("Dmitriy", "Belov", "a", "b", "c", "d", "e", "f", "-1", "3", "5", "1");
        when(questionDao.findAll()).thenReturn(List.of(new Question("Question 1", Arrays.asList("a", "b", "c"), 1)));
        ExamService examService = new ExamServiceImpl(questionDao, userCommunicationService, 1, false, 1);
        examService.executeExam();
        Exam exam = (Exam) ReflectionTestUtils.getField(examService, "exam");
        assert exam != null;
        verify(userCommunicationService, times(9)).printError(anyString());
        assertEquals(1, exam.getAnswers().get(0).getAnswer(), "Неверный ответ");
    }

    @DisplayName("Тест на то что пользователю предложат несколько попыток ответить")
    @ParameterizedTest(name = "Попыток ответа: {0}")
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void attemptsTest(int attempts) {
        when(userCommunicationService.readAnswer()).thenReturn("Dmitriy", "Belov", "0", "1", "2", "3", "4", "5");
        when(questionDao.findAll()).thenReturn(List.of(new Question("Question 1", Arrays.asList("a", "b", "c", "d", "e", "f", "g"), 5)));
        ExamService examService = new ExamServiceImpl(questionDao, userCommunicationService, 1, false, attempts);
        examService.executeExam();
        Exam exam = (Exam) ReflectionTestUtils.getField(examService, "exam");
        assert exam != null;
        verify(userCommunicationService, times(attempts)).printError(anyString());
    }

    @DisplayName("Тест необходимого количества правильных ответов")
    @ParameterizedTest(name = "Требуемое количество правильных ответов: {0}. Экзамен пройден: {1}.")
    @CsvSource({
            "1, true",
            "2, true",
            "3, true",
            "4, false",
            "5, false",
    })
    void requiredRightAnswersTest(int requiredRightAnswers, boolean isPassed) {
        when(userCommunicationService.readAnswer()).thenReturn("Dmitriy", "Belov", "0");
        when(questionDao.findAll()).thenReturn(List.of(
                new Question("Question 1", List.of("a", "b", "c", "d", "e", "f", "g"), 0),
                new Question("Question 2", List.of("a", "b", "c", "d", "e", "f", "g"), 1),
                new Question("Question 3", List.of("a", "b", "c", "d", "e", "f", "g"), 0),
                new Question("Question 4", List.of("a", "b", "c", "d", "e", "f", "g"), 2),
                new Question("Question 5", List.of("a", "b", "c", "d", "e", "f", "g"), 0)
        ));
        ExamService examService = new ExamServiceImpl(questionDao, userCommunicationService, requiredRightAnswers, false, 1);
        examService.executeExam();
        Exam exam = (Exam) ReflectionTestUtils.getField(examService, "exam");
        assert exam != null;
        assertEquals(3, exam.getRightAnswersCount(), "Количество правильных ответов не совпадает");
        assertEquals(isPassed, exam.isExamPassed(), "Результат не совпадает");
    }

    @DisplayName("Тест проведения экзаменов")
    @ParameterizedTest(name = "Ответы: {0}. Количество правильных: {1}. Экзамен пройден: {1}.")
    @MethodSource("examTestData")
    void examTest(List<String> answers, int rightAnswers, boolean isPassed) {
        List<String> mockAnswers = new ArrayList<>();
        mockAnswers.add("Dmitriy");
        mockAnswers.add("Belov");
        mockAnswers.addAll(answers);
        when(userCommunicationService.readAnswer()).thenAnswer(AdditionalAnswers.returnsElementsOf(mockAnswers));
        when(questionDao.findAll()).thenReturn(List.of(
                new Question("Question 1", List.of("a", "b", "c", "d", "e", "f", "g"), 0),
                new Question("Question 2", List.of("a", "b", "c", "d", "e", "f", "g"), 1),
                new Question("Question 3", List.of("a", "b", "c", "d", "e", "f", "g"), 2),
                new Question("Question 4", List.of("a", "b", "c", "d", "e", "f", "g"), 0),
                new Question("Question 5", List.of("a", "b", "c", "d", "e", "f", "g"), 1)
        ));
        ExamService examService = new ExamServiceImpl(questionDao, userCommunicationService, 3, false, 1);
        examService.executeExam();
        Exam exam = (Exam) ReflectionTestUtils.getField(examService, "exam");
        assert exam != null;
        assertEquals(rightAnswers, exam.getRightAnswersCount(), "Количество правильных ответов не совпадает");
        assertEquals(isPassed, exam.isExamPassed(), "Результат не совпадает");
    }

    /**
     * Генерация тестовых наборов данных для {@link ExamServiceImplTest#validateTest}
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

    /**
     * Генерация тестовых наборов данных для {@link ExamServiceImplTest#examTest}
     * @return тестовый набор данных
     */
    private static Stream<Arguments> examTestData() {
        return Stream.of(
                Arguments.of(List.of("0", "1", "2", "0", "1"), 5, true),
                Arguments.of(List.of("0", "2", "2", "0", "1"), 4, true),
                Arguments.of(List.of("0", "2", "1", "0", "1"), 3, true),
                Arguments.of(List.of("1", "0", "0", "2", "0"), 0, false),
                Arguments.of(List.of("1", "0", "0", "0", "0"), 1, false)
        );
    }
}