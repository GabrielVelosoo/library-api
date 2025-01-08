package com.github.gabrielvelosoo.libraryapi.controllers;

import com.github.gabrielvelosoo.libraryapi.dto.AuthorDTO;
import com.github.gabrielvelosoo.libraryapi.models.Author;
import com.github.gabrielvelosoo.libraryapi.services.AuthorService;
import com.github.gabrielvelosoo.libraryapi.utils.UriUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(final AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Void> saveAuthor(@RequestBody AuthorDTO author) {
        Author entityAuthor = author.mapToEntityAuthor();
        authorService.saveAuthor(entityAuthor);

        URI location = UriUtil.buildLocationUri(entityAuthor.getId());

        return ResponseEntity.created(location).build();
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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable String id) {
        UUID authorId = UUID.fromString(id);
        Optional<Author> optionalAuthor = authorService.findAuthorById(authorId);

        if(optionalAuthor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        authorService.deleteAuthor(optionalAuthor.get());
        return ResponseEntity.noContent().build();
    }
}
