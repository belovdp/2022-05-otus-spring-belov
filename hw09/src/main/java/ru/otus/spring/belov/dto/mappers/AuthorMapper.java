package ru.otus.spring.belov.dto.mappers;

import org.mapstruct.Mapper;
import ru.otus.spring.belov.domain.Author;
import ru.otus.spring.belov.dto.AuthorDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
   AuthorDto toDto(Author author);
   List<AuthorDto> toDto(List<Author> author);
}