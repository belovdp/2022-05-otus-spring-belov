package ru.otus.spring.belov.service;

import ru.otus.spring.belov.domain.Exam;

/**
 * Контракт на проведение тестирования
 */
public interface ExamService {

    /**
     * Выполняет процесс экзамена
     * @param exam экзамен
     */
    void executeExam(Exam exam);
}
