package ru.otus.spring.belov.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.belov.dto.AuthorDto;
import ru.otus.spring.belov.dto.BookWithCommentsDto;
import ru.otus.spring.belov.dto.GenreDto;
import ru.otus.spring.belov.service.AuthorService;
import ru.otus.spring.belov.service.BookService;
import ru.otus.spring.belov.service.GenreService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @MockBean
    private BookService bookService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private AuthorService authorService;
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Тест отображения формы с книгой")
    @Test
    void showById() throws Exception {
        var book = new BookWithCommentsDto(2L, "sdf", LocalDate.now(), new GenreDto(), new AuthorDto(), new ArrayList<>());
        when(bookService.getById(2L)).thenReturn(book);
        mockMvc.perform(get("/book/{id}/view", 2))
                .andExpect(status().isOk())
                .andExpect(view().name("book/view"))
                .andExpect(model().attribute("book", equalTo(book)));
    }

    @DisplayName("Тест отображения формы новой книги")
    @Test
    void newBook() throws Exception {
        var authors = List.of(new AuthorDto());
        when(authorService.getAll()).thenReturn(authors);
        var genres = List.of(new GenreDto());
        when(genreService.getAll()).thenReturn(genres);
        mockMvc.perform(get("/book/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/form"))
                .andExpect(model().attribute("authors", authors))
                .andExpect(model().attribute("genres", genres));
    }

    @DisplayName("Тест отображения формы  изменения книги")
    @Test
    void updateBook() throws Exception {
        var authors = List.of(new AuthorDto());
        when(authorService.getAll()).thenReturn(authors);
        var genres = List.of(new GenreDto());
        when(genreService.getAll()).thenReturn(genres);
        var book = new BookWithCommentsDto(2L, "sdf", LocalDate.now(), new GenreDto(), new AuthorDto(), new ArrayList<>());
        when(bookService.getById(2)).thenReturn(book);
        mockMvc.perform(get("/book/{id}/update", 2))
                .andExpect(status().isOk())
                .andExpect(view().name("book/form"))
                .andExpect(model().attribute("book", book))
                .andExpect(model().attribute("authors", authors))
                .andExpect(model().attribute("genres", genres));
    }

    @DisplayName("Тест удаления книги")
    @Test
    void deleteById() throws Exception {
        mockMvc.perform(get("/book/{id}/delete", 2))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/"));
        verify(bookService).deleteById(anyLong());
    }
}