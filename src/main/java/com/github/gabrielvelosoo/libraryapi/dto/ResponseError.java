package com.github.gabrielvelosoo.libraryapi.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ResponseError(int status, String message, List<FieldError> errors) {

    public static ResponseError defaultResponse(String message) {
        return new ResponseError(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }

    public static ResponseError conflictResponse(String message) {
        return new ResponseError(HttpStatus.CONFLICT.value(), message, List.of());
    }
}
