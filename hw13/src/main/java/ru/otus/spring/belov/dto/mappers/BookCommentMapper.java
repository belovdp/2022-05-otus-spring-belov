package ru.otus.spring.belov.dto.mappers;

import org.mapstruct.Mapper;
import ru.otus.spring.belov.domain.BookComment;
import ru.otus.spring.belov.dto.BookCommentDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookCommentMapper {
   BookCommentDto toDto(BookComment bookComment);
   List<BookCommentDto> toDto(List<BookComment> genres);
}