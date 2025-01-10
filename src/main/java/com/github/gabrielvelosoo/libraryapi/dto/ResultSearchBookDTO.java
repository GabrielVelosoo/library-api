package com.github.gabrielvelosoo.libraryapi.dto;

import com.github.gabrielvelosoo.libraryapi.enums.BookGenre;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

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
