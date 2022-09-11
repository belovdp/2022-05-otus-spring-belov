package ru.otus.spring.belov.dto.mappers;

import org.mapstruct.Mapper;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.dto.BookDto;
import ru.otus.spring.belov.dto.BookWithCommentsDto;

import java.util.List;

/**
 * Конвертер DTO <-> DO для книги
 */
@Mapper(componentModel = "spring", uses = {AuthorMapper.class, GenreMapper.class})
public interface BookMapper {
    BookDto toDto(Book book);

    BookWithCommentsDto toDtoWithComments(Book book);

    List<BookDto> toDto(List<Book> books);

    Book fromDto(BookDto bookDto);
}