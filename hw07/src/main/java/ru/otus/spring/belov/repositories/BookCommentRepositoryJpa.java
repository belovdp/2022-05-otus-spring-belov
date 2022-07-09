package ru.otus.spring.belov.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.belov.domain.BookComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Репозиторий по работе с комментариями книг через JPA
 */
@Repository
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
        entityManager.createQuery("delete from BookComment bc where bc.id = :id")
                .setParameter("id", id)
                .executeUpdate();
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
