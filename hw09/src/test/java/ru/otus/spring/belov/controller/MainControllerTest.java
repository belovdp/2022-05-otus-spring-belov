package ru.otus.spring.belov.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.belov.dto.BookDto;
import ru.otus.spring.belov.service.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MainController.class)
class MainControllerTest {

    @MockBean
    private BookService bookService;
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Тест главной страницы")
    @Test
    public void getIndexPageTest() throws Exception {
        List<BookDto> booksList = new ArrayList<>();
        when(bookService.getAll()).thenReturn(booksList);
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("books", is(booksList)));
    }
}