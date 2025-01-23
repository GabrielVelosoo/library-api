package com.github.gabrielvelosoo.libraryapi.dto;

import jakarta.validation.constraints.NotBlank;

public record ClientDTO(
        @NotBlank(message = "required field")
        String clientId,
        @NotBlank(message = "required field")
        String clientSecret,
        @NotBlank(message = "required field")
        String redirectURI,
        String scope
    ) {
}
