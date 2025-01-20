package com.github.gabrielvelosoo.libraryapi.controllers;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public interface GenericController {

    default URI generateHeaderLocation(UUID id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    default URI generateHeaderLocationUser(String login) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{login}")
                .buildAndExpand(login)
                .toUri();
    }

    default UUID fromString(String id) {
        return UUID.fromString(id);
    }
}
