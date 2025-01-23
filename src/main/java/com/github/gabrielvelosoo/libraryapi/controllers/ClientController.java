package com.github.gabrielvelosoo.libraryapi.controllers;

import com.github.gabrielvelosoo.libraryapi.dto.ClientDTO;
import com.github.gabrielvelosoo.libraryapi.mappers.ClientMapper;
import com.github.gabrielvelosoo.libraryapi.models.Client;
import com.github.gabrielvelosoo.libraryapi.services.ClientService;
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
public class ClientController implements GenericController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> saveClient(@RequestBody @Valid ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        clientService.saveClient(client);
        URI url = generateHeaderLocation(client.getId());
        return ResponseEntity.created(url).build();
    }
}
