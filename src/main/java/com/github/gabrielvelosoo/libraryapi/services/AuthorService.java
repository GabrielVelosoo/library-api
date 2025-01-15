package com.github.gabrielvelosoo.libraryapi.services;

import com.github.gabrielvelosoo.libraryapi.exceptions.OperationNotAllowedException;
import com.github.gabrielvelosoo.libraryapi.models.Author;
import com.github.gabrielvelosoo.libraryapi.repositories.AuthorRepository;
import com.github.gabrielvelosoo.libraryapi.repositories.BookRepository;
import com.github.gabrielvelosoo.libraryapi.validators.AuthorValidator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.github.gabrielvelosoo.libraryapi.repositories.specs.AuthorSpecs.nameLike;
import static com.github.gabrielvelosoo.libraryapi.repositories.specs.AuthorSpecs.nationalityLike;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorValidator authorValidator;
    private final BookRepository bookRepository;

    public AuthorService(
            AuthorRepository authorRepository,
            AuthorValidator authorValidator,
            BookRepository bookRepository
    ) {
        this.authorRepository = authorRepository;
        this.authorValidator = authorValidator;
        this.bookRepository = bookRepository;
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
        return searchByExample(name, nationality);
    }

    public List<Author> searchByExample(String name, String nationality) {
        Specification<Author> specs = Specification.where( (root, query, cb) -> cb.conjunction() );
        if(name != null) {
            specs = specs.and(nameLike(name));
        }
        if(nationality != null) {
            specs = specs.and(nationalityLike(nationality));
        }
        return authorRepository.findAll(specs);
    }

    public Optional<Author> findAuthorById(UUID id) {
        return authorRepository.findById(id);
    }
}
