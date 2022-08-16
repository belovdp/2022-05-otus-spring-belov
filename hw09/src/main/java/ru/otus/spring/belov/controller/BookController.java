package ru.otus.spring.belov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.dto.BookDto;
import ru.otus.spring.belov.service.AuthorService;
import ru.otus.spring.belov.service.BookService;
import ru.otus.spring.belov.service.GenreService;

import javax.validation.Valid;

/**
 * Контроллер работы с книгами
 */
@Controller
@RequiredArgsConstructor
public class BookController {

    /** Форма по заведению книги */
    private static final String BOOK_FORM = "book/form";
    /** Сервис работы с книгами */
    private final BookService bookService;
    /** Сервис работы с авторами */
    private final AuthorService authorService;
    /** Сервис работы с жанрами */
    private final GenreService genreService;

    /**
     * Отображает книгу
     * @param id    идентификатор книги
     * @param model модель данных
     * @return отображение книги
     */
    @GetMapping("/book/{id}/view")
    public String showById(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "book/view";
    }

    /**
     * Отображает форму заведения новой книги
     * @param model модель данных
     * @return форма заведения новой книги
     */
    @GetMapping("book/new")
    public String newBook(Model model) {
        model.addAttribute("book", new BookDto());
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("genres", genreService.getAll());
        return BOOK_FORM;
    }

    /**
     * Обновление книги
     * @param id    идентификатор книги
     * @param model модель данных
     * @return форма обновления книги
     */
    @GetMapping("book/{id}/update")
    public String updateBook(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getById(id));
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("genres", genreService.getAll());
        return BOOK_FORM;
    }

    /**
     * Процесс сохранения книги
     * @param book          книга
     * @param bindingResult биндинги
     * @return форма отображаемая после обработки
     */
    @PostMapping("book")
    public String saveOrUpdate(@Valid @ModelAttribute("book") BookDto book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.getAll());
            model.addAttribute("genres", genreService.getAll());
            return BOOK_FORM;
        }
        BookDto savedBook = bookService.saveOrUpdate(book);
        return "redirect:/book/" + savedBook.getId() + "/view";
    }

    /**
     * Удаление книги
     * @param id идентификатор книги
     * @return форма отображаемая после удаления
     */
    @GetMapping("book/{id}/delete")
    public String deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }
}
