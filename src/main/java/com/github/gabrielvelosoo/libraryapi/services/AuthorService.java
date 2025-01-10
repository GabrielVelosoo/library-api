package com.github.gabrielvelosoo.libraryapi.services;

import com.github.gabrielvelosoo.libraryapi.exceptions.OperationNotAllowedException;
import com.github.gabrielvelosoo.libraryapi.models.Author;
import com.github.gabrielvelosoo.libraryapi.repositories.AuthorRepository;
import com.github.gabrielvelosoo.libraryapi.repositories.BookRepository;
import com.github.gabrielvelosoo.libraryapi.validators.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorValidator authorValidator;
    private final BookRepository bookRepository;

    public Optional<Author> findAuthorById(UUID id) {
        return authorRepository.findById(id);
    }

    public void saveAuthor(Author author) {
        authorValidator.validate(author);
        authorRepository.save(author);
    }

    public void updateAuthor(Author author) {
        authorValidator.validate(author);
        authorRepository.save(author);
    }

    public void deleteAuthor(Author author) {
        String exceptionMessage = "It is not possible to exclude an author who has registered books!";
        if(hasBook(author)) {
            throw new OperationNotAllowedException(exceptionMessage);
        }

        authorRepository.delete(author);
    }

    public boolean hasBook(Author author) {
        return bookRepository.existsByAuthor(author);
    }

    public List<Author> searchAuthors(String name, String nationality) {
        return searchByParams(name, nationality);
    }

    public List<Author> searchByParams(String name, String nationality) {
        if(name != null && nationality != null) {
            return authorRepository.findByNameAndNationality(name, nationality);
        }

        if(name != null) {
            return authorRepository.findByName(name);
        }

        if(nationality != null) {
            return authorRepository.findByNationality(nationality);
        }

        return authorRepository.findAll();
    }
}
