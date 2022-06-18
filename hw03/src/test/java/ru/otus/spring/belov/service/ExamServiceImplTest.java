package ru.otus.spring.belov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.belov.component.LocaleHolder;
import ru.otus.spring.belov.config.ExamAppProperties;
import ru.otus.spring.belov.dao.QuestionDao;
import ru.otus.spring.belov.domain.Answer;
import ru.otus.spring.belov.domain.Question;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Тест сервиса на работу с тестированием")
@SpringBootTest
class ExamServiceImplTest {

    @MockBean
    private QuestionDao questionDao;
    @MockBean
    private CommunicationService communicationService;
    @MockBean
    private ExamAppProperties examAppProperties;
    @MockBean
    private LocaleHolder localeHolder;
    @Autowired
    private ExamServiceImpl examService;

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
        when(questionDao.findAll()).thenReturn(Arrays.asList(null, null, null, null, null));
        when(communicationService.getAnswer(any(), anyInt())).thenReturn(
                new Answer(new Question("Question 1", List.of("a", "b", "c", "d", "e", "f", "g"), 0), 1),
                new Answer(new Question("Question 2", List.of("a", "b", "c", "d", "e", "f", "g"), 1), 2),
                new Answer(new Question("Question 3", List.of("a", "b", "c", "d", "e", "f", "g"), 0), 0),
                new Answer(new Question("Question 4", List.of("a", "b", "c", "d", "e", "f", "g"), 2), 2),
                new Answer(new Question("Question 5", List.of("a", "b", "c", "d", "e", "f", "g"), 0), 0)
        );
        when(examAppProperties.getRequiredRightAnswers()).thenReturn(requiredRightAnswers);
        var exam = examService.executeExam();
        assertEquals(3, exam.getRightAnswersCount(), "Количество правильных ответов не совпадает");
        assertEquals(isPassed, exam.isExamPassed(), "Результат не совпадает");
    }

    @DisplayName("Тест процесса проведения экзамена")
    @Test
    void examTest() {
        List<Question> mockQuestions = Arrays.asList(null, null, null, null, null);
        when(questionDao.findAll()).thenReturn(mockQuestions);
        when(communicationService.getAnswer(any(), anyInt())).thenReturn(null);

        when(examAppProperties.getRequiredRightAnswers()).thenReturn(3);
        examService.executeExam();
        verify(communicationService, times(1)).getUserInfo();
        verify(communicationService, times(5)).askQuestion(any(), anyBoolean());
        verify(communicationService, times(5)).getAnswer(any(), anyInt());
        verify(communicationService, times(1)).printResult(any());
    }
}