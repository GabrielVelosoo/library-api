package com.github.gabrielvelosoo.libraryapi.dto;

import com.github.gabrielvelosoo.libraryapi.models.Author;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,
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

    public static AuthorDTO fromAuthor(Author author) {
        return new AuthorDTO(author.getId(), author.getName(), author.getBirthDate(), author.getNationality());
    }
}
