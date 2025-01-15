package com.github.gabrielvelosoo.libraryapi.validators;

import com.github.gabrielvelosoo.libraryapi.exceptions.DuplicateRecordException;
import com.github.gabrielvelosoo.libraryapi.models.Author;
import com.github.gabrielvelosoo.libraryapi.repositories.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {

    private final AuthorRepository authorRepository;

    public AuthorValidator(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void authorValidate(Author author) {
        if(isExistsAuthor(author)) {
            throw new DuplicateRecordException("Author already exists");
        }
    }

    private boolean isExistsAuthor(Author author) {
        Optional<Author> foundAuthor = authorRepository.findByNameAndBirthDateAndNationality(author.getName(), author.getBirthDate(), author.getNationality());
        if(author.getId() == null) {
            return foundAuthor.isPresent();
        }
        return foundAuthor
                .map(Author::getId)
                .stream()
                .anyMatch(id -> !id.equals(author.getId()));
    }
}
