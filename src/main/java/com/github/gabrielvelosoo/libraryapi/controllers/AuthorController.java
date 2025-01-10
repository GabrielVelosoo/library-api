package com.github.gabrielvelosoo.libraryapi.controllers;

import com.github.gabrielvelosoo.libraryapi.dto.AuthorDTO;
import com.github.gabrielvelosoo.libraryapi.dto.ResponseErrorDTO;
import com.github.gabrielvelosoo.libraryapi.exceptions.DuplicateRecordException;
import com.github.gabrielvelosoo.libraryapi.exceptions.OperationNotAllowedException;
import com.github.gabrielvelosoo.libraryapi.models.Author;
import com.github.gabrielvelosoo.libraryapi.services.AuthorService;
import com.github.gabrielvelosoo.libraryapi.utils.UriUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<Object> saveAuthor(@RequestBody AuthorDTO authordto) {
        try {
            Author author = authordto.mapToEntityAuthor();
            authorService.saveAuthor(author);
            URI location = UriUtil.buildLocationUri(author.getId());

            return ResponseEntity.created(location).build();
        } catch(DuplicateRecordException e) {
            ResponseErrorDTO dtoError = ResponseErrorDTO.conflictResponse(e.getMessage());

            return ResponseEntity.status(dtoError.status()).body(dtoError);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable String id, @RequestBody AuthorDTO authordto) {
        try {
            UUID authorId = UUID.fromString(id);
            Optional<Author> optionalAuthor = authorService.findAuthorById(authorId);

            if (optionalAuthor.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Author author = optionalAuthor.get();
            authordto.mapToEntityAuthorUpdate(author);
            authorService.updateAuthor(author);

            return ResponseEntity.noContent().build();
        } catch(DuplicateRecordException e) {
            ResponseErrorDTO dtoError = ResponseErrorDTO.conflictResponse(e.getMessage());

            return ResponseEntity.status(dtoError.status()).body(dtoError);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable String id) {
        try {
            UUID authorId = UUID.fromString(id);
            Optional<Author> optionalAuthor = authorService.findAuthorById(authorId);

            if (optionalAuthor.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            authorService.deleteAuthor(optionalAuthor.get());

            return ResponseEntity.noContent().build();
        } catch(OperationNotAllowedException e) {
            ResponseErrorDTO dtoError = ResponseErrorDTO.defaultResponse(e.getMessage());

            return ResponseEntity.status(dtoError.status()).body(dtoError);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> searchAuthor(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality) {
        List<Author> result = authorService.searchAuthors(name, nationality);
        List<AuthorDTO> authors = AuthorDTO.fromAuthors(result);

        return ResponseEntity.ok(authors);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AuthorDTO> findAuthorById(@PathVariable String id) {
        UUID authorId = UUID.fromString(id);
        Optional<Author> optionalAuthor = authorService.findAuthorById(authorId);

        if(optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            AuthorDTO dtoAuthor = AuthorDTO.fromAuthor(author);
            return ResponseEntity.ok(dtoAuthor);
        }

        return ResponseEntity.notFound().build();
    }
}
