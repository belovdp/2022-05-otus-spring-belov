package ru.otus.spring.belov.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.belov.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Репозиторий по работе с авторами через JDBC
 */
@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Author save(Author author) {
        entityManager.persist(author);
        return author;
    }

    @Override
    public List<Author> findAll() {
        return entityManager
                .createQuery("select a from Author a", Author.class)
                .getResultList();
    }

    @Override
    public List<Author> findByNameContaining(String name) {
        return entityManager
                .createQuery("select a from Author a where lower(a.name) like CONCAT('%', lower(:name), '%')", Author.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public Optional<Author> findById(long id) {
        return ofNullable(entityManager.find(Author.class, id));
    }
}
