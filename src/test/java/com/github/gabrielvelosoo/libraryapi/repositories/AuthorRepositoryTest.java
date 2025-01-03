package com.github.gabrielvelosoo.libraryapi.repositories;

import com.github.gabrielvelosoo.libraryapi.models.Author;
import com.github.gabrielvelosoo.libraryapi.models.Book;
import com.github.gabrielvelosoo.libraryapi.models.enums.BookGenre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @Test
    void saveTest() {
        Author author = new Author();
        author.setName("José");
        author.setNationality("Brazilian");
        author.setBirthDate(LocalDate.of(1951, 1, 31));

        Author savedAuthor = authorRepository.save(author);
        System.out.println("Saved author: " + savedAuthor);
    }

    @Test
    void updateTest() {
        UUID id = UUID.fromString("d07a709c-b4de-4302-aa56-8768f595933a");
        Optional<Author> author = authorRepository.findById(id);

        if(author.isPresent()) {
            Author foundAuthor = author.get();
            System.out.println("Author details: ");
            System.out.println(foundAuthor);

            foundAuthor.setBirthDate(LocalDate.of(1960, 1, 30));
            authorRepository.save(foundAuthor);
        }
    }

    @Test
    void findAllTest() {
        List<Author> authors = authorRepository.findAll();
        authors.forEach(System.out::println);
    }

    @Test
    void countTest() {
        System.out.println("Authors count: " + authorRepository.count());
    }

    @Test
    void deleteByIdTest() {
        UUID id = UUID.fromString("95a50e98-c347-4fb6-a256-6c2b9abee56f");
        authorRepository.deleteById(id);
    }

    @Test
    void deleteTest() {
        UUID id = UUID.fromString("d07a709c-b4de-4302-aa56-8768f595933a");
        Optional<Author> author = authorRepository.findById(id);

        if(author.isPresent()) {
            Author foundAuthor = author.get();
            authorRepository.delete(foundAuthor);
        }
    }

    @Test
    void saveAuthorWithBooksTest() {
        Author author = new Author();
        author.setName("Antonio");
        author.setNationality("American");
        author.setBirthDate(LocalDate.of(1970, 8, 5));

        Book book = new Book();
        book.setIsbn("20847-84874");
        book.setPrice(BigDecimal.valueOf(204));
        book.setGenre(BookGenre.MYSTERY);
        book.setTitle("The haunted house robbery");
        book.setPostDate(LocalDate.of(1999, 1, 2));
        book.setAuthor(author);

        Book book2 = new Book();
        book2.setIsbn("99999-84874");
        book2.setPrice(BigDecimal.valueOf(650));
        book2.setGenre(BookGenre.MYSTERY);
        book2.setTitle("The haunted house robbery 2");
        book2.setPostDate(LocalDate.of(2000, 1, 2));
        book2.setAuthor(author);

        author.setBooks(new ArrayList<>());
        author.getBooks().add(book);
        author.getBooks().add(book2);

        authorRepository.save(author);
        //bookRepository.saveAll(author.getBooks());
    }

    @Test
    void listBookAuthor() {
        UUID id = UUID.fromString("df372a25-6026-406c-b844-f1f5f556b713");
        Optional<Author> author = authorRepository.findById(id);

        if(author.isPresent()) {
            Author foundAuthor = author.get();
            List<Book> books = bookRepository.findByAuthor(foundAuthor);
            foundAuthor.setBooks(books);
            foundAuthor.getBooks().forEach(System.out::println);
        }
    }
}
