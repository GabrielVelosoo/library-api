package com.github.gabrielvelosoo.libraryapi.repositories;

import com.github.gabrielvelosoo.libraryapi.models.Author;
import com.github.gabrielvelosoo.libraryapi.models.Book;
import com.github.gabrielvelosoo.libraryapi.enums.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    // Query method
    List<Book> findByAuthor(Author author);

    List<Book> findByTitle(String title);

    Book findByIsbn(String isbn);

    List<Book> findByTitleAndPrice(String title, BigDecimal price);

    List<Book> findByTitleOrIsbn(String title, String isbn);

    List<Book> findByPostDateBetween(LocalDate startDate, LocalDate finalDate);

    @Query(" select b from Book as b order by b.title ")
    List<Book> findAllOrderByTitle();

    @Query(" select a from Book b join b.author a ")
    List<Author> findBookAuthor();

    @Query(" select b from Book b where b.genre = :genre ")
    List<Book> findByGenre(@Param("genre") BookGenre genre);

    @Modifying
    @Transactional
    @Query(" delete from Book where genre = ?1 ")
    void deleteByGenre(BookGenre genre);

    @Modifying
    @Transactional
    @Query(" update Book set postDate = ?1 where genre = ?2 ")
    void updatePostDateByGenre(LocalDate newPostDate, BookGenre genre);
}
