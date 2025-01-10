package com.github.gabrielvelosoo.libraryapi.validators;

import com.github.gabrielvelosoo.libraryapi.exceptions.DuplicateRecordException;
import com.github.gabrielvelosoo.libraryapi.models.Author;
import com.github.gabrielvelosoo.libraryapi.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorValidator {

    private final AuthorRepository authorRepository;

    public void validate(Author author) {
        if(isExistsAuthor(author)) {
            throw new DuplicateRecordException("Author already exists");
        }
    }

    private boolean isExistsAuthor(Author author) {
        Optional<Author> optionalAuthor = authorRepository.findByNameAndBirthDateAndNationality(author.getName(), author.getBirthDate(), author.getNationality());

        if(author.getId() == null) {
            return optionalAuthor.isPresent();
        }

        return !author.getId().equals(optionalAuthor.get().getId()) && optionalAuthor.isPresent();
    }
}
