package ru.otus.spring.belov.dto.mappers;

import org.mapstruct.Mapper;
import ru.otus.spring.belov.domain.Genre;
import ru.otus.spring.belov.dto.GenreDto;

import java.util.List;

/**
 * Конвертер DTO <-> DO для жанров
 */
@Mapper(componentModel = "spring")
public interface GenreMapper {
   GenreDto toDto(Genre genre);
   List<GenreDto> toDto(List<Genre> genres);
   Genre fromDto(GenreDto genreDto);
}