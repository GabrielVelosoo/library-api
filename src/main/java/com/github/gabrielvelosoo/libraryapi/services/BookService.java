package com.github.gabrielvelosoo.libraryapi.services;

import com.github.gabrielvelosoo.libraryapi.models.Book;
import com.github.gabrielvelosoo.libraryapi.repositories.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }
}
