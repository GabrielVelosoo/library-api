package com.github.gabrielvelosoo.libraryapi.dto;

import com.github.gabrielvelosoo.libraryapi.enums.BookGenre;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Find Book")
public record ResultSearchBookDTO(
        UUID id,
        String isbn,
        String title,
        LocalDate postDate,
        BookGenre genre,
        BigDecimal price,
        AuthorDTO author
    ) {
}
