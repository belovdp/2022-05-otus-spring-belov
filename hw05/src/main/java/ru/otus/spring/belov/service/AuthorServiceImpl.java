package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.dao.AuthorDao;
import ru.otus.spring.belov.domain.Author;

import java.time.LocalDate;
import java.util.List;

/**
 * Сервис по работе с автоарми
 */
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    /** DAO По работе с авторами */
    private final AuthorDao authorDao;

    @Override
    public Author save(String name, String birthday) {
        var author = Author.builder()
                .name(name)
                .birthday(LocalDate.parse(birthday))
                .build();
        return authorDao.save(author);
    }

    @Override
    public List<Author> findAll() {
        return authorDao.findAll();
    }

    @Override
    public List<Author> findByNameContaining(String name) {
        return authorDao.findByNameContaining(name);
    }
}
