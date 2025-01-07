package com.github.gabrielvelosoo.libraryapi.dto;

import com.github.gabrielvelosoo.libraryapi.models.Author;

import java.time.LocalDate;

public record AuthorDTO(
        String name,
        LocalDate birthDate,
        String nationality) {

    public Author mapToEntityAuthor() {
        Author author = new Author();

        author.setName(name);
        author.setBirthDate(birthDate);
        author.setNationality(nationality);

        return author;
    }
}
