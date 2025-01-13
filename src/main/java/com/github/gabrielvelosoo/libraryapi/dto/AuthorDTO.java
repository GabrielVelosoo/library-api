package com.github.gabrielvelosoo.libraryapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,

        @NotBlank(message = "required field")
        @Size(min = 2, max = 100, message = "field outside standard size")
        String name,

        @NotNull(message = "required field")
        @Past(message = "cannot be a future date")
        LocalDate birthDate,

        @NotBlank(message = "required field")
        @Size(min = 2, max = 50, message = "field outside standard size")
        String nationality
    ) {
}
