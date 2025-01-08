package com.github.gabrielvelosoo.libraryapi.services;

import com.github.gabrielvelosoo.libraryapi.models.Author;
import com.github.gabrielvelosoo.libraryapi.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    public Optional<Author> findAuthorById(UUID id) {
        return authorRepository.findById(id);
    }

    public void deleteAuthor(Author author) {
        authorRepository.delete(author);
    }
}
