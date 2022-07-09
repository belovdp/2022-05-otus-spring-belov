package ru.otus.spring.belov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.belov.domain.Author;
import ru.otus.spring.belov.repositories.AuthorRepository;

import java.time.LocalDate;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Тест сервиса по работе с авторами")
@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;
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
        verify(authorRepository).save(argThat(actualSavedAuthor -> {
            assertThat(actualSavedAuthor)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedSavedAuthor);
            return true;
        }));
    }

    @DisplayName("Тест поиска по идентификатору автора")
    @Test
    void findByIdTest() {
        when(authorRepository.findById(1L)).thenReturn(of(Author.builder().build()));
        when(authorRepository.findById(2L)).thenReturn(empty());
        assertThatCode(() -> authorService.findById(1))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> authorService.findById(2))
                .isInstanceOf(IllegalArgumentException.class);
    }
}