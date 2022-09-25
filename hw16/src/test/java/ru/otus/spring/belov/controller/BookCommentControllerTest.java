package ru.otus.spring.belov.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.belov.domain.BookComment;
import ru.otus.spring.belov.service.BookCommentService;
import ru.otus.spring.belov.service.BookService;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BookCommentController.class)
class BookCommentControllerTest {

    @MockBean
    private BookService bookService;
    @MockBean
    private BookCommentService bookCommentService;
    @MockBean
    private UserDetailsService userDetailsService;
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Тест формы с новым комментарием")
    @WithMockUser(username = "user")
    @Test
    void newComment() throws Exception {
        mockMvc.perform(get("/book/{bookId}/comment/new", 2))
                .andExpect(status().isOk())
                .andExpect(view().name("book/comment/form"))
                .andExpect(model().attribute("bookId", is(2L)))
                .andExpect(model().attribute("comment", equalTo(new BookComment())));
    }

    @DisplayName("Тест сохранения комментария")
    @WithMockUser(username = "user")
    @Test
    void save() throws Exception {
        mockMvc.perform(post("/book/{bookId}/comment", 2)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("text", "123"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/book/2/view"));
        verify(bookCommentService).save(any(), anyLong());
    }
}