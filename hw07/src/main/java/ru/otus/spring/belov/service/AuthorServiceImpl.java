package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.belov.domain.Author;
import ru.otus.spring.belov.repositories.AuthorRepository;

import java.time.LocalDate;
import java.util.List;

import static java.lang.String.format;

/**
 * Сервис по работе с автоарми
 */
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    /** Репозиторий По работе с авторами */
    private final AuthorRepository authorRepository;

    @Transactional
    @Override
    public Author save(String name, String birthday) {
        var author = Author.builder()
                .name(name)
                .birthday(LocalDate.parse(birthday))
                .build();
        return authorRepository.save(author);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> findByNameContaining(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    @Override
    public Author findById(long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден автор с идентификатором %d", id)));
    }
}
