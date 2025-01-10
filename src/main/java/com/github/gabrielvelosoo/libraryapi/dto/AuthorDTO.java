package com.github.gabrielvelosoo.libraryapi.dto;

import com.github.gabrielvelosoo.libraryapi.models.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record AuthorDTO(
        UUID id,

        @NotBlank(message = "required field")
        @Size(min = 2, max = 100, message = "field outside standard size")
        String name,

        @NotNull(message = "required field")
        @Past(message = "cannot be a future date")
        LocalDate birthDate,

        @NotBlank(message = "required field")
        @Size(min = 2, max = 50, message = "field outside standard size")
        String nationality
    ) {

    public Author mapToEntityAuthor() {
        Author author = new Author();
        author.setName(name);
        author.setBirthDate(birthDate);
        author.setNationality(nationality);

        return author;
    }

    public void mapToEntityAuthorUpdate(Author author) {
        author.setName(name);
        author.setBirthDate(birthDate);
        author.setNationality(nationality);
    }

    public static AuthorDTO fromAuthor(Author author) {
        return new AuthorDTO(author.getId(), author.getName(), author.getBirthDate(), author.getNationality());
    }

    public static List<AuthorDTO> fromAuthors(List<Author> authors) {
        List<AuthorDTO> authorDTOs = new ArrayList<>();

        for(Author author : authors) {
            AuthorDTO authorDTO = new AuthorDTO(author.getId(), author.getName(), author.getBirthDate(), author.getNationality());
            authorDTOs.add(authorDTO);
        }

        return authorDTOs;
    }
}
