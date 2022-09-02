package ru.otus.spring.belov.dto.mappers;

import org.mapstruct.Mapper;
import ru.otus.spring.belov.domain.BookComment;
import ru.otus.spring.belov.dto.BookCommentDto;

import java.util.List;

/**
 * Конвертер DTO <-> DO для комментариев
 */
@Mapper(componentModel = "spring")
public interface BookCommentMapper {
   BookCommentDto toDto(BookComment bookComment);
   List<BookCommentDto> toDto(List<BookComment> genres);
}