package com.github.gabrielvelosoo.libraryapi.controllers;

import com.github.gabrielvelosoo.libraryapi.dto.RegisterBookDTO;
import com.github.gabrielvelosoo.libraryapi.dto.ResultSearchBookDTO;
import com.github.gabrielvelosoo.libraryapi.enums.BookGenre;
import com.github.gabrielvelosoo.libraryapi.mappers.BookMapper;
import com.github.gabrielvelosoo.libraryapi.models.Book;
import com.github.gabrielvelosoo.libraryapi.services.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BookController implements GenericController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping
    public ResponseEntity<Void> saveBook(@RequestBody @Valid RegisterBookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        bookService.saveBook(book);
        URI url = generateHeaderLocation(book.getId());
        return ResponseEntity.created(url).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable String id, @RequestBody @Valid RegisterBookDTO bookDTO) {
        return bookService
                .findBookById(fromString(id))
                .map(book -> {
                    Book bookEntity = bookMapper.toEntity(bookDTO);
                    book.setIsbn(bookEntity.getIsbn());
                    book.setTitle(bookEntity.getTitle());
                    book.setPostDate(bookEntity.getPostDate());
                    book.setGenre(bookEntity.getGenre());
                    book.setPrice(bookEntity.getPrice());
                    book.setAuthor(bookEntity.getAuthor());
                    bookService.updateBook(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable String id) {
        return bookService.findBookById(fromString(id))
                .map(book -> {
                    bookService.deleteBook(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @GetMapping
    public ResponseEntity<List<ResultSearchBookDTO>> searchBooks(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author-name", required = false) String authorName,
            @RequestParam(value = "genre", required = false) BookGenre genre,
            @RequestParam(value = "post-year", required = false) Integer postYear
    ) {
        List<Book> result = bookService.searchBooks(isbn, title, authorName, genre, postYear);
        List<ResultSearchBookDTO> books = bookMapper.toDTOs(result);
        return ResponseEntity.ok(books);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResultSearchBookDTO> findBookById(@PathVariable String id) {
        return bookService
                .findBookById(fromString(id))
                .map(book -> {
                    ResultSearchBookDTO bookDTO = bookMapper.toDTO(book);
                    return ResponseEntity.ok(bookDTO);
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }
}
