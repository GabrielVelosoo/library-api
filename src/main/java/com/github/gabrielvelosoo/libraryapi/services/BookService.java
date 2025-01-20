package com.github.gabrielvelosoo.libraryapi.services;

import com.github.gabrielvelosoo.libraryapi.enums.BookGenre;
import com.github.gabrielvelosoo.libraryapi.models.Book;
import com.github.gabrielvelosoo.libraryapi.models.User;
import com.github.gabrielvelosoo.libraryapi.repositories.BookRepository;
import com.github.gabrielvelosoo.libraryapi.security.SecurityService;
import com.github.gabrielvelosoo.libraryapi.validators.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.github.gabrielvelosoo.libraryapi.repositories.specs.BookSpecs.*;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookValidator bookValidator;
    private final SecurityService securityService;

    public void saveBook(Book book) {
        bookValidator.bookValidate(book);
        User user = securityService.getLoggedUser();
        book.setUser(user);
        bookRepository.save(book);
    }

    public void updateBook(Book book) {
        bookValidator.bookValidate(book);
        bookRepository.save(book);
    }

    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    public Page<Book> searchBooks(
            String isbn, String title, String authorName, BookGenre genre, Integer postYear, Integer page, Integer pageSize
    ) {
        Specification<Book> specifications = searchBookSpecifications(isbn, title, authorName, genre, postYear);
        Pageable pagination = bookPagination(page, pageSize);
        return bookRepository.findAll(specifications, pagination);
    }

    public Specification<Book> searchBookSpecifications(
            String isbn, String title, String authorName, BookGenre genre, Integer postYear
    ) {
        Specification<Book> specs = Specification.where( (root, query, cb) -> cb.conjunction() );
        if(isbn != null) {
            specs = specs.and(isbnEqual(isbn));
        }
        if(title != null) {
            specs = specs.and(titleLike(title));
        }
        if(authorName != null) {
            specs = specs.and(authorNameLike(authorName));
        }
        if(genre != null) {
            specs = specs.and(genreEqual(genre));
        }
        if(postYear != null) {
            specs = specs.and(postYearEqual(postYear));
        }
        return specs;
    }

    public Pageable bookPagination(Integer page, Integer pageSize) {
        return PageRequest.of(page, pageSize);
    }

    public Optional<Book> findBookById(UUID id) {
        return bookRepository.findById(id);
    }
}
