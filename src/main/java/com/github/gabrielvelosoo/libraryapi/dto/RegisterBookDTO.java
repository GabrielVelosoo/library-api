package com.github.gabrielvelosoo.libraryapi.dto;

import com.github.gabrielvelosoo.libraryapi.enums.BookGenre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RegisterBookDTO(
        @ISBN
        @NotBlank(message = "required field")
        String isbn,

        @NotBlank(message = "required field")
        String title,

        @NotNull(message = "required field")
        @Past(message = "cannot be a future date")
        LocalDate postDate,

        BookGenre genre,
        BigDecimal price,

        @NotNull(message = "required field")
        UUID authorId
    ) {
}
