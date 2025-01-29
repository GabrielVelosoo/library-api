package com.github.gabrielvelosoo.libraryapi.dto.errors;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Field Error")
public record FieldErrorDTO(String field, String message) {
}
