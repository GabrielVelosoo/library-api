package com.github.gabrielvelosoo.libraryapi.exceptions.handles;

import com.github.gabrielvelosoo.libraryapi.dto.errors.FieldErrorDTO;
import com.github.gabrielvelosoo.libraryapi.dto.errors.ResponseErrorDTO;
import com.github.gabrielvelosoo.libraryapi.exceptions.DuplicateRecordException;
import com.github.gabrielvelosoo.libraryapi.exceptions.InvalidFieldException;
import com.github.gabrielvelosoo.libraryapi.exceptions.OperationNotAllowedException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseErrorDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<FieldErrorDTO> errorList = fieldErrors
                .stream()
                .map(fe -> new FieldErrorDTO(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseErrorDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error.", errorList);
    }

    @ExceptionHandler(DuplicateRecordException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseErrorDTO handleDuplicateRecordException(DuplicateRecordException e) {
        return ResponseErrorDTO.conflictResponse(e.getMessage());
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseErrorDTO handleOperationNotAllowedException(OperationNotAllowedException e) {
        return ResponseErrorDTO.defaultResponse(e.getMessage());
    }

    @ExceptionHandler(InvalidFieldException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseErrorDTO handleInvalidFieldException(InvalidFieldException e) {
        return new ResponseErrorDTO(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error.",
                List.of(new FieldErrorDTO(e.getField(), e.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseErrorDTO handleAccessDeniedException(AccessDeniedException e) {
        return new ResponseErrorDTO(HttpStatus.FORBIDDEN.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseErrorDTO handleUnhandledErrorsException(RuntimeException e) {
        return new ResponseErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), List.of());
    }
}
