package com.github.gabrielvelosoo.libraryapi.mappers;

import com.github.gabrielvelosoo.libraryapi.dto.RegisterBookDTO;
import com.github.gabrielvelosoo.libraryapi.dto.ResultSearchBookDTO;
import com.github.gabrielvelosoo.libraryapi.models.Book;
import com.github.gabrielvelosoo.libraryapi.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
@RequiredArgsConstructor
public abstract class BookMapper {

    AuthorRepository authorRepository;

    @Mapping(target = "author", expression = "java( authorRepository.findById(bookDTO.authorId()).orElse(null) )")
    public abstract Book toEntity(RegisterBookDTO bookDTO);
    public abstract ResultSearchBookDTO toDTO(Book book);
}
