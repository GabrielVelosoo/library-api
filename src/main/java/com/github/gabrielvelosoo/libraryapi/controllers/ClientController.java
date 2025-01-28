package com.github.gabrielvelosoo.libraryapi.controllers;

import com.github.gabrielvelosoo.libraryapi.dto.ClientDTO;
import com.github.gabrielvelosoo.libraryapi.mappers.ClientMapper;
import com.github.gabrielvelosoo.libraryapi.models.Client;
import com.github.gabrielvelosoo.libraryapi.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "/clients")
@RequiredArgsConstructor
@Tag(name = "Clients")
public class ClientController implements GenericController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Save", description = "Register new client")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registered with success.")
    })
    public ResponseEntity<Void> saveClient(@RequestBody @Valid ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        clientService.saveClient(client);
        URI url = generateHeaderLocation(client.getId());
        return ResponseEntity.created(url).build();
    }
}
