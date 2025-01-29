package com.github.gabrielvelosoo.libraryapi.controllers;

import com.github.gabrielvelosoo.libraryapi.dto.AuthorDTO;
import com.github.gabrielvelosoo.libraryapi.dto.errors.ResponseErrorDTO;
import com.github.gabrielvelosoo.libraryapi.mappers.AuthorMapper;
import com.github.gabrielvelosoo.libraryapi.models.Author;
import com.github.gabrielvelosoo.libraryapi.services.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/authors", produces = "application/json")
@RequiredArgsConstructor
@Tag(name = "Authors")
public class AuthorController implements GenericController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Save", description = "Register new author")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registered with success", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid token", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "409", description = "Author already registered",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ResponseErrorDTO.class)
                            )
                    }
            )
    })
    public ResponseEntity<Void> saveAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        authorService.saveAuthor(author);
        URI url = generateHeaderLocation(author.getId());
        return ResponseEntity.created(url).build();
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Update", description = "Updates an existing author")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully update"),
            @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid token", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Author already registered",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ResponseErrorDTO.class)
                            )
                    }
            )
    })
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
    @Operation(summary = "Delete", description = "Deletes an existing author")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully deleted", content = @Content),
            @ApiResponse(responseCode = "400", description = "Author has registered book(s)",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ResponseErrorDTO.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid token", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content)
    })
    public ResponseEntity<Object> deleteAuthor(@PathVariable String id) {
        return authorService.findAuthorById(fromString(id))
                .map(author -> {
                    authorService.deleteAuthor(author);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    @Operation(summary = "Search", description = "Search for authors by parameters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid token", content = @Content)
    })
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
    @Operation(summary = "Find", description = "Returns author data by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid token", content = @Content),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content)
    })
    public ResponseEntity<AuthorDTO> findAuthorById(@PathVariable String id) {
        return authorService
                .findAuthorById(fromString(id))
                .map(author -> {
                    AuthorDTO authorDTO = authorMapper.toDTO(author);
                    return ResponseEntity.ok(authorDTO);
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }
}
