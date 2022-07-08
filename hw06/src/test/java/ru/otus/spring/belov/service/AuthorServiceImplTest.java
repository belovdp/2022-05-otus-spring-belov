package ru.otus.spring.belov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.belov.dao.AuthorDao;
import ru.otus.spring.belov.domain.Author;

import java.time.LocalDate;
import java.util.Optional;

import static java.util.Optional.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Тест сервиса по работе с авторами")
@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorDao authorDao;
    @InjectMocks
    private AuthorServiceImpl authorService;

    @DisplayName("Тест сохарения автора")
    @Test
    void saveTest() {
        var expectedSavedAuthor = Author.builder()
                .name("testName")
                .birthday(LocalDate.now())
                .build();
        authorService.save(expectedSavedAuthor.getName(), expectedSavedAuthor.getBirthday().toString());
        verify(authorDao).save(argThat(actualSavedAuthor -> {
            assertThat(actualSavedAuthor)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedSavedAuthor);
            return true;
        }));
    }

    @DisplayName("Тест поиска по идентификатору автора")
    @Test
    void findByIdTest() {
        when(authorDao.findById(1)).thenReturn(of(Author.builder().build()));
        when(authorDao.findById(2)).thenReturn(empty());
        assertThatCode(() -> authorService.findById(1))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> authorService.findById(2))
                .isInstanceOf(IllegalArgumentException.class);
    }
}