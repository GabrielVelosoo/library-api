package com.github.gabrielvelosoo.libraryapi.repositories;

import com.github.gabrielvelosoo.libraryapi.models.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void saveTest() {
        Author author = new Author();
        author.setName("José");
        author.setNationality("Brazilian");
        author.setBirthDate(LocalDate.of(1951, 1, 31));

        var savedAuthor = authorRepository.save(author);
        System.out.println("Saved author: " + savedAuthor);
    }

    @Test
    void updateTest() {
        var id = UUID.fromString("d07a709c-b4de-4302-aa56-8768f595933a");
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
        var id = UUID.fromString("19a0260a-2724-4a8e-a956-bf7dacbc39c4");
        authorRepository.deleteById(id);
    }

    @Test
    void deleteTest() {
        var id = UUID.fromString("d07a709c-b4de-4302-aa56-8768f595933a");
        Optional<Author> author = authorRepository.findById(id);

        if(author.isPresent()) {
            Author foundAuthor = author.get();
            authorRepository.delete(foundAuthor);
        }
    }
}
