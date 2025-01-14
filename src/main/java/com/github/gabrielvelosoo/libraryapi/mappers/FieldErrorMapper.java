package com.github.gabrielvelosoo.libraryapi.mappers;

import com.github.gabrielvelosoo.libraryapi.dto.errors.FieldErrorDTO;
import org.mapstruct.Mapper;
import org.springframework.validation.FieldError;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FieldErrorMapper {

    List<FieldErrorDTO> toDTOs(List<FieldError> fieldErrors);
}
