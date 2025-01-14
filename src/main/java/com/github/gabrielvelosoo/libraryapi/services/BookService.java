package com.github.gabrielvelosoo.libraryapi.services;

import com.github.gabrielvelosoo.libraryapi.enums.BookGenre;
import com.github.gabrielvelosoo.libraryapi.models.Book;
import com.github.gabrielvelosoo.libraryapi.repositories.BookRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.github.gabrielvelosoo.libraryapi.repositories.specs.BookSpecs.*;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public void updateBook(Book book) {
        if(book.getId() == null) {
            throw new IllegalArgumentException("Book id is null");
        }
        bookRepository.save(book);
    }

    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    public List<Book> searchBooks(
            String isbn, String title, String authorName, BookGenre genre, Integer postYear
    ) {
        return searchByExample(isbn, title, authorName, genre, postYear);
    }

    public List<Book> searchByExample(
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
        return bookRepository.findAll(specs);
    }

    public Optional<Book> findBookById(UUID id) {
        return bookRepository.findById(id);
    }
}
