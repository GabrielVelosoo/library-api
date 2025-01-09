package com.github.gabrielvelosoo.libraryapi.services;

import com.github.gabrielvelosoo.libraryapi.models.Author;
import com.github.gabrielvelosoo.libraryapi.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void deleteAuthor(Author author) {
        authorRepository.delete(author);
    }

    public Optional<Author> findAuthorById(UUID id) {
        return authorRepository.findById(id);
    }

    public void updateAuthor(Author author) {
        updateException(author);
        authorRepository.save(author);
    }

    public void updateException(Author author) {
        if(author.getId() == null) {
            throw new IllegalArgumentException("To update, the author must already be saved in the database!");
        }
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
