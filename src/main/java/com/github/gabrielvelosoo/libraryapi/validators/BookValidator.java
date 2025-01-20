package com.github.gabrielvelosoo.libraryapi.validators;

import com.github.gabrielvelosoo.libraryapi.exceptions.DuplicateRecordException;
import com.github.gabrielvelosoo.libraryapi.exceptions.InvalidFieldException;
import com.github.gabrielvelosoo.libraryapi.models.Book;
import com.github.gabrielvelosoo.libraryapi.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private static final int YEAR_PRICE_REQUIREMENT = 2020;

    private final BookRepository bookRepository;

    public void bookValidate(Book book) {
        if(isIsbnAlreadyRegistered(book)) {
            throw new DuplicateRecordException("ISBN already exists");
        }
        if(isRequiredPriceNull(book)) {
            throw new InvalidFieldException("price", "For books published in 2020 or later, pricing is mandatory.");
        }
    }

    private boolean isIsbnAlreadyRegistered(Book book) {
        Optional<Book> foundBook = bookRepository.findByIsbn(book.getIsbn());
        if(book.getId() == null) {
            return foundBook.isPresent();
        }
        return foundBook
                .map(Book::getId)
                .stream()
                .anyMatch(id -> !id.equals(book.getId()));
    }

    private boolean isRequiredPriceNull(Book book) {
        return book.getPrice() == null &&
                book.getPostDate().getYear() >= YEAR_PRICE_REQUIREMENT;
    }
}
