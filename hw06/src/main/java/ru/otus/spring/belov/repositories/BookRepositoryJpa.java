package ru.otus.spring.belov.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.belov.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Репозиторий по работе с книгами через JDBC
 */
@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Book saveOrUpdate(Book book) {
        if (book.getId() == null) {
            entityManager.persist(book);
            return book;
        }
        return entityManager.merge(book);
    }

    @Override
    public void deleteById(long id) {
        entityManager.createQuery("delete from Book b where b.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public Optional<Book> findById(long id) {
        return ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        return entityManager
                .createQuery("select b from Book b join fetch b.genre g join fetch b.author a", Book.class)
                .getResultList();
    }

    @Override
    public List<Book> findAllByGenreName(String genreName) {
        return entityManager
                .createQuery("""
                            select b
                            from Genre g join g.books b
                            where g.name = :genreName
                        """, Book.class)
                .setParameter("genreName", genreName)
                .getResultList();
    }
}
