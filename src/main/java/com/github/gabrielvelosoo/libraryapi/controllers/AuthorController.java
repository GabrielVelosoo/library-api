package com.github.gabrielvelosoo.libraryapi.controllers;

import com.github.gabrielvelosoo.libraryapi.dto.AuthorDTO;
import com.github.gabrielvelosoo.libraryapi.mappers.AuthorMapper;
import com.github.gabrielvelosoo.libraryapi.models.Author;
import com.github.gabrielvelosoo.libraryapi.services.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/authors")
@RequiredArgsConstructor
public class AuthorController implements GenericController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> saveAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        authorService.saveAuthor(author);
        URI url = generateHeaderLocation(author.getId());
        return ResponseEntity.created(url).build();
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Object> updateAuthor(@PathVariable String id,
                                               @RequestBody @Valid AuthorDTO authorDTO) {
        return authorService.findAuthorById(fromString(id))
                .map(author -> {
                    Author authorEntity = authorMapper.toEntity(authorDTO);
                    author.setName(authorEntity.getName());
                    author.setBirthDate(authorEntity.getBirthDate());
                    author.setNationality(authorEntity.getNationality());
                    authorService.updateAuthor(author);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Object> deleteAuthor(@PathVariable String id) {
        return authorService.findAuthorById(fromString(id))
                .map(author -> {
                    authorService.deleteAuthor(author);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    public ResponseEntity<List<AuthorDTO>> searchAuthors(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality
    ) {
        List<Author> result = authorService.searchAuthors(name, nationality);
        List<AuthorDTO> authors = authorMapper.toDTOs(result);
        return ResponseEntity.ok(authors);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    public ResponseEntity<AuthorDTO> findAuthorById(@PathVariable String id) {
        return authorService
                .findAuthorById(fromString(id))
                .map(author -> {
                    AuthorDTO authorDTO = authorMapper.toDTO(author);
                    return ResponseEntity.ok(authorDTO);
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }
}
