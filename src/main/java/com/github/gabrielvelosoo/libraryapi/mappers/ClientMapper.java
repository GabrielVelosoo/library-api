package com.github.gabrielvelosoo.libraryapi.mappers;

import com.github.gabrielvelosoo.libraryapi.dto.ClientDTO;
import com.github.gabrielvelosoo.libraryapi.models.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientDTO clientDTO);
}
