package ru.otus.spring.belov.dao;

import ru.otus.spring.belov.domain.Question;

import java.util.List;

/**
 * DAO по работе с вопросами тестирования
 */
public interface QuestionDao {

    /**
     * Возвращает все вопросы для тестирования
     * @return все вопросы для тестирования
     */
    List<Question> findAll();
}
