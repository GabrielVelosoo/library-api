package com.github.gabrielvelosoo.libraryapi.dto.errors;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ResponseErrorDTO(int status, String message, List<FieldErrorDTO> errors) {

    public static ResponseErrorDTO defaultResponse(String message) {
        return new ResponseErrorDTO(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }

    public static ResponseErrorDTO conflictResponse(String message) {
        return new ResponseErrorDTO(HttpStatus.CONFLICT.value(), message, List.of());
    }
}
