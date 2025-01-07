package com.github.gabrielvelosoo.libraryapi.services;

import com.github.gabrielvelosoo.libraryapi.models.Author;
import com.github.gabrielvelosoo.libraryapi.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void save(Author author) {
        authorRepository.save(author);
    }
}
