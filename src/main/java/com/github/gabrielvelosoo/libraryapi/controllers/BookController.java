package com.github.gabrielvelosoo.libraryapi.controllers;

import com.github.gabrielvelosoo.libraryapi.dto.RegisterBookDTO;
import com.github.gabrielvelosoo.libraryapi.dto.ResultSearchBookDTO;
import com.github.gabrielvelosoo.libraryapi.dto.errors.ResponseErrorDTO;
import com.github.gabrielvelosoo.libraryapi.enums.BookGenre;
import com.github.gabrielvelosoo.libraryapi.mappers.BookMapper;
import com.github.gabrielvelosoo.libraryapi.models.Book;
import com.github.gabrielvelosoo.libraryapi.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "/books", produces = "application/json")
@RequiredArgsConstructor
@Tag(name = "Books")
public class BookController implements GenericController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    @Operation(summary = "Save", description = "Register new book")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registered with success", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid token", content = @Content),
            @ApiResponse(responseCode = "422", description = "Validation error",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ResponseErrorDTO.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "409", description = "ISBN already exists",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ResponseErrorDTO.class)
                            )
                    }
            )
    })
    public ResponseEntity<Void> saveBook(@RequestBody @Valid RegisterBookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        bookService.saveBook(book);
        URI url = generateHeaderLocation(book.getId());
        return ResponseEntity.created(url).build();
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    @Operation(summary = "Update", description = "Updates an existing book")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully update", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid token", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "ISBN already registered",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ResponseErrorDTO.class)
                            )
                    }
            )
    })
    public ResponseEntity<Object> updateBook(@PathVariable String id,
                                             @RequestBody @Valid RegisterBookDTO bookDTO) {
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
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    @Operation(summary = "Delete", description = "Deletes an existing book")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid token", content = @Content)
    })
    public ResponseEntity<Object> deleteBook(@PathVariable String id) {
        return bookService.findBookById(fromString(id))
                .map(book -> {
                    bookService.deleteBook(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    @Operation(summary = "Search", description = "Search for books by parameters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid token", content = @Content)
    })
    public ResponseEntity<Page<ResultSearchBookDTO>> searchBooks(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author-name", required = false) String authorName,
            @RequestParam(value = "genre", required = false) BookGenre genre,
            @RequestParam(value = "post-year", required = false) Integer postYear,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "page-size", defaultValue = "10") Integer pageSize
    ) {
        Page<Book> pageResult = bookService.searchBooks(isbn, title, authorName, genre, postYear, page, pageSize);
        Page<ResultSearchBookDTO> result = pageResult.map(bookMapper::toDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    @Operation(summary = "Find", description = "Returns book data by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book found"),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid token", content = @Content)
    })
    public ResponseEntity<ResultSearchBookDTO> findBookById(@PathVariable String id) {
        return bookService
                .findBookById(fromString(id))
                .map(book -> {
                    ResultSearchBookDTO bookDTO = bookMapper.toDTO(book);
                    return ResponseEntity.ok(bookDTO);
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }
}
