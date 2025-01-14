package com.github.gabrielvelosoo.libraryapi.mappers;

import com.github.gabrielvelosoo.libraryapi.dto.AuthorDTO;
import com.github.gabrielvelosoo.libraryapi.models.Author;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toEntity(AuthorDTO authorDTO);
    AuthorDTO toDTO(Author author);
    List<AuthorDTO> toDTOs(List<Author> authors);
}
