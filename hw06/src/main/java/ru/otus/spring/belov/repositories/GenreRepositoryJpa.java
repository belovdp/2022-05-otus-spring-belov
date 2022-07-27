package ru.otus.spring.belov.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.belov.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Репозиторий по работе с жанрами через JPA
 */
@Component
@RequiredArgsConstructor
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private final EntityManager entityManager;


    @Override
    public Genre save(Genre genre) {
        entityManager.persist(genre);
        return genre;
    }

    @Override
    public List<Genre> findAll() {
        return entityManager
                .createQuery("select g from Genre g", Genre.class)
                .getResultList();
    }

    @Override
    public Optional<Genre> findByName(String name) {
        return entityManager.createQuery("select g from Genre g where g.name = :name", Genre.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<Genre> findById(long id) {
        return ofNullable(entityManager.find(Genre.class, id));
    }
}
