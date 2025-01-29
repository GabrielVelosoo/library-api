package com.github.gabrielvelosoo.libraryapi.dto.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.util.List;

@Schema(name = "Response Error")
public record ResponseErrorDTO(int status, String message, List<FieldErrorDTO> errors) {

    public static ResponseErrorDTO defaultResponse(String message) {
        return new ResponseErrorDTO(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }

    public static ResponseErrorDTO conflictResponse(String message) {
        return new ResponseErrorDTO(HttpStatus.CONFLICT.value(), message, List.of());
    }
}
