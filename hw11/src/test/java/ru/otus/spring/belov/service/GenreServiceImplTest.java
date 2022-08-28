package ru.otus.spring.belov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.belov.domain.Genre;
import ru.otus.spring.belov.repositories.GenreRepository;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Тест сервиса по работе с жанрами")
@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    @Mock
    private GenreRepository genreRepository;
    @InjectMocks
    private GenreServiceImpl genreService;

    @DisplayName("Тест сохарения жанра")
    @Test
    void saveTest() {
        var expectedSavedGenre = Genre.builder()
                .name("testName")
                .build();
        genreService.save(expectedSavedGenre.getName());
        verify(genreRepository).save(argThat(actualSavedAuthor -> {
            assertThat(actualSavedAuthor)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedSavedGenre);
            return true;
        }));
    }

    @DisplayName("Тест поиска по идентификатору жанра")
    @Test
    void findByIdTest() {
        when(genreRepository.findById("1")).thenReturn(of(Genre.builder().build()));
        when(genreRepository.findById("2")).thenReturn(empty());
        assertThatCode(() -> genreService.findById("1"))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> genreService.findById("2"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}