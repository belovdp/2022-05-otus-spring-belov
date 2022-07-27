package ru.otus.spring.belov.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.belov.domain.BookComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Репозиторий по работе с комментариями книг через JPA
 */
@Component
@RequiredArgsConstructor
public class BookCommentRepositoryJpa implements BookCommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public BookComment saveOrUpdate(BookComment bookComment) {
        if (bookComment.getId() == null) {
            entityManager.persist(bookComment);
            return bookComment;
        }
        return entityManager.merge(bookComment);
    }

    @Override
    public void deleteById(long id) {
        entityManager.remove(entityManager.find(BookComment.class, id));
    }

    @Override
    public Optional<BookComment> findById(long id) {
        return ofNullable(entityManager.find(BookComment.class, id));
    }

    @Override
    public List<BookComment> findAll() {
        return entityManager
                .createQuery("select bc from BookComment bc", BookComment.class)
                .getResultList();
    }
}
