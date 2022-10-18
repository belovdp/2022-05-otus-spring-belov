package ru.otus.spring.belov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.belov.domain.User;

import java.util.Optional;

/**
 * Репозиторий по работе с пользователями
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Поиск пользователя по имени
     * @param username имя
     * @return пользователь
     */
    Optional<User> findByUsername(String username);
}
