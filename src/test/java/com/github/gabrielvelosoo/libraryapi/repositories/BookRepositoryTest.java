package com.github.gabrielvelosoo.libraryapi.repositories;

import com.github.gabrielvelosoo.libraryapi.models.Author;
import com.github.gabrielvelosoo.libraryapi.models.Book;
import com.github.gabrielvelosoo.libraryapi.models.enums.BookGenre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void saveTest() {
        Book book = new Book();
        book.setIsbn("90887-84874");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGenre(BookGenre.FICTION);
        book.setTitle("UFO");
        book.setPostDate(LocalDate.of(1980, 1, 2));

        Author author = authorRepository.findById(UUID.fromString("7cfd4e9e-a749-4050-b9eb-8b0bd98c91b6")).orElse(null);
        book.setAuthor(author);

        bookRepository.save(book);
    }

    @Test
    void saveAuthorAndBookTest() {
        Book book = new Book();
        book.setIsbn("91837-41864");
        book.setPrice(BigDecimal.valueOf(80));
        book.setGenre(BookGenre.MYSTERY);
        book.setTitle("Third book");
        book.setPostDate(LocalDate.of(1994, 4, 8));

        Author author = new Author();
        author.setName("José");
        author.setNationality("Brazilian");
        author.setBirthDate(LocalDate.of(1951, 1, 31));

        authorRepository.save(author);

        book.setAuthor(author);

        bookRepository.save(book);
    }

    @Test
    void saveCascadeTest() {
        Book book = new Book();
        book.setIsbn("91837-41864");
        book.setPrice(BigDecimal.valueOf(74.25));
        book.setGenre(BookGenre.FANTASY);
        book.setTitle("Other book");
        book.setPostDate(LocalDate.of(1994, 4, 8));

        Author author = new Author();
        author.setName("João");
        author.setNationality("Brazilian");
        author.setBirthDate(LocalDate.of(1954, 4, 10));

        book.setAuthor(author);

        bookRepository.save(book);
    }
}