package com.github.gabrielvelosoo.libraryapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UserDTO(
        @NotBlank(message = "required field")
        String login,
        @Email(message = "invalid e-mail")
        @NotBlank(message = "required field")
        String email,
        @NotBlank(message = "required field")
        String password,
        List<String> roles
    ) {
}
