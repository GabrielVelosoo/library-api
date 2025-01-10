package com.github.gabrielvelosoo.libraryapi.controllers;

import com.github.gabrielvelosoo.libraryapi.dto.RegisterBookDTO;
import com.github.gabrielvelosoo.libraryapi.dto.errors.ResponseErrorDTO;
import com.github.gabrielvelosoo.libraryapi.exceptions.DuplicateRecordException;
import com.github.gabrielvelosoo.libraryapi.services.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Object> saveBook(@RequestBody @Valid RegisterBookDTO bookDTO) {
        try {

            return ResponseEntity.ok(bookDTO);
        } catch(DuplicateRecordException e) {
            ResponseErrorDTO errorDTO = ResponseErrorDTO.conflictResponse(e.getMessage());

            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }
}
