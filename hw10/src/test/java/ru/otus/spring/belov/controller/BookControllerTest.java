package ru.otus.spring.belov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.belov.dto.AuthorDto;
import ru.otus.spring.belov.dto.BookDto;
import ru.otus.spring.belov.dto.BookWithCommentsDto;
import ru.otus.spring.belov.dto.GenreDto;
import ru.otus.spring.belov.service.AuthorService;
import ru.otus.spring.belov.service.BookService;
import ru.otus.spring.belov.service.GenreService;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Тест получения книги по идентификатору")
    @Test
    void getBook() throws Exception {
        var book = new BookWithCommentsDto(2L, "sdf", LocalDate.now(), new GenreDto(), new AuthorDto(), new ArrayList<>());
        when(bookService.getById(2L)).thenReturn(book);
        mockMvc.perform(get("/book/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.published").value(book.getPublished().toString()));
    }

    @DisplayName("Тест удаления книги")
    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/book/{bookId}", 2))
                .andExpect(status().isOk());
        verify(bookService).deleteById(2L);
    }

    @DisplayName("Тест получения книг")
    @Test
    void getBooks() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk());
        verify(bookService).getAll(any());
    }

    @DisplayName("Тест сохранения")
    @Test
    void saveOrUpdate() throws Exception {
        var genre = new GenreDto();
        genre.setId(2L);
        var authorDto = new AuthorDto();
        authorDto.setId(2L);
        var book = new BookDto(2L, "sdbvbv", LocalDate.now(), genre, authorDto);
        when(bookService.saveOrUpdate(any())).thenReturn(book);
        mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.published").value(book.getPublished().toString()));
    }

}