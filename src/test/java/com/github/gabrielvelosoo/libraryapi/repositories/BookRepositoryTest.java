package com.github.gabrielvelosoo.libraryapi.repositories;

import com.github.gabrielvelosoo.libraryapi.models.Author;
import com.github.gabrielvelosoo.libraryapi.models.Book;
import com.github.gabrielvelosoo.libraryapi.models.enums.BookGenre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
        book.setIsbn("43256-12454");
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
        book.setIsbn("43256-12454");
        book.setPrice(BigDecimal.valueOf(320));
        book.setGenre(BookGenre.MYSTERY);
        book.setTitle("Amazing adventure");
        book.setPostDate(LocalDate.of(2004, 4, 10));

        Author author = new Author();
        author.setName("Gabriel");
        author.setNationality("Brazilian");
        author.setBirthDate(LocalDate.of(2004, 4, 10));

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

    @Test
    void updateTest() {
        UUID id = UUID.fromString("e2e6d902-c9c0-4685-ac35-13ded1f9d53d");
        Book book = bookRepository.findById(id).orElse(null);
        if(book != null) {
            book.setGenre(BookGenre.SCIENCE);
            bookRepository.save(book);
        }
    }

    @Test
    void updateAuthorTest() {
        UUID bookId = UUID.fromString("58fa9496-5052-4703-a043-e550a207336e");
        Book book = bookRepository.findById(bookId).orElse(null);

        UUID authorId = UUID.fromString("435ef251-8b2f-4260-9ffe-95d231c26199");
        Author author = authorRepository.findById(authorId).orElse(null);

        if(book != null && author != null) {
            book.setAuthor(author);
            bookRepository.save(book);
        }
    }

    @Test
    void deleteByIdTest() {
        UUID id = UUID.fromString("afa7d65b-8ce3-4b94-9739-4fb0dae28779");
        bookRepository.deleteById(id);
    }

    @Test
    @Transactional
    void findByIdTest() {
        UUID id = UUID.fromString("e2e6d902-c9c0-4685-ac35-13ded1f9d53d");
        Book book = bookRepository.findById(id).orElse(null);

        if(book != null) {
            System.out.println("Book: ");
            System.out.println(book.getTitle());

            System.out.println("Author: ");
            System.out.println(book.getAuthor().getName());
        }
    }

    @Test
    void searchBooksByTitleTest() {
        List<Book> books = bookRepository.findByTitle("The haunted house robbery 2");
        books.forEach(System.out::println);
    }

    @Test
    void searchBookByIsbnTest() {
        Book book = bookRepository.findByIsbn("90887-84874");
        System.out.println(book);
    }

    @Test
    void searchBooksByTitleAndPriceTest() {
        String title = "The haunted house robbery";
        BigDecimal price = BigDecimal.valueOf(204.00);

        List<Book> books = bookRepository.findByTitleAndPrice(title, price);
        books.forEach(System.out::println);
    }

    @Test
    void searchBooksByTitleOrIsbnTest() {
        String title = "The haunted house robbery 2";
        String isbn = null;

        List<Book> books = bookRepository.findByTitleOrIsbn(title, isbn);
        books.forEach(System.out::println);
    }

    @Test
    void searchBooksByPostDateBetweenTest() {
        LocalDate startDate = LocalDate.of(1980, 1, 1);
        LocalDate finalDate = LocalDate.of(2000, 12, 31);

        List<Book> books = bookRepository.findByPostDateBetween(startDate, finalDate);
        books.forEach(System.out::println);
    }

    @Test
    void findAllOrderByTitleTest() {
        List<Book> books = bookRepository.findAllOrderByTitle();
        books.forEach(System.out::println);
    }

    @Test
    void findBookAuthorTest() {
        List<Author> authors = bookRepository.findBookAuthor();
        authors.forEach(System.out::println);
    }

    @Test
    void findByGenreTest() {
        List<Book> books = bookRepository.findByGenre(BookGenre.MYSTERY);
        books.forEach(System.out::println);
    }

    @Test
    void deleteByGenreTest() {
        bookRepository.deleteByGenre(BookGenre.FANTASY);
    }

    @Test
    void updatePostDateByGenre() {
        bookRepository.updatePostDateByGenre(LocalDate.of(2020, 6, 13), BookGenre.MYSTERY);
    }
}
