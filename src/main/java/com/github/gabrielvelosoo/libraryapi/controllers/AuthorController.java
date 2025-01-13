package com.github.gabrielvelosoo.libraryapi.controllers;

import com.github.gabrielvelosoo.libraryapi.dto.AuthorDTO;
import com.github.gabrielvelosoo.libraryapi.dto.errors.ResponseErrorDTO;
import com.github.gabrielvelosoo.libraryapi.exceptions.DuplicateRecordException;
import com.github.gabrielvelosoo.libraryapi.exceptions.OperationNotAllowedException;
import com.github.gabrielvelosoo.libraryapi.mappers.AuthorMapper;
import com.github.gabrielvelosoo.libraryapi.models.Author;
import com.github.gabrielvelosoo.libraryapi.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/authors")
public class AuthorController implements GenericController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping
    public ResponseEntity<Object> saveAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        try {
            Author author = authorMapper.toEntity(authorDTO);
            authorService.saveAuthor(author);
            URI url = generateHeaderLocation(author.getId());
            return ResponseEntity.created(url).build();
        } catch(DuplicateRecordException e) {
            ResponseErrorDTO errorDTO = ResponseErrorDTO.conflictResponse(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable String id, @RequestBody @Valid AuthorDTO authorDTO) {
        try {
            UUID authorId = UUID.fromString(id);
            Optional<Author> optionalAuthor = authorService.findAuthorById(authorId);

            if (optionalAuthor.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Author author = optionalAuthor.get();
            author.setName(authorDTO.name());
            author.setBirthDate(authorDTO.birthDate());
            author.setNationality(authorDTO.nationality());
            authorService.updateAuthor(author);
            return ResponseEntity.noContent().build();
        } catch(DuplicateRecordException e) {
            ResponseErrorDTO errorDTO = ResponseErrorDTO.conflictResponse(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
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
            ResponseErrorDTO errorDTO = ResponseErrorDTO.defaultResponse(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> searchAuthor(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality
    ) {
        List<Author> result = authorService.searchAuthors(name, nationality);
        List<AuthorDTO> authors = authorMapper.toDtos(result);
        return ResponseEntity.ok(authors);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AuthorDTO> findAuthorById(@PathVariable String id) {
        UUID authorId = UUID.fromString(id);
        return authorService
                .findAuthorById(authorId)
                .map(author -> {
                    AuthorDTO authorDTO = authorMapper.toDto(author);
                    return ResponseEntity.ok(authorDTO);
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }
}
