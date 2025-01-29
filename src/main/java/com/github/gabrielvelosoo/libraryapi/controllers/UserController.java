package com.github.gabrielvelosoo.libraryapi.controllers;

import com.github.gabrielvelosoo.libraryapi.dto.UserDTO;
import com.github.gabrielvelosoo.libraryapi.mappers.UserMapper;
import com.github.gabrielvelosoo.libraryapi.models.User;
import com.github.gabrielvelosoo.libraryapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
@RequestMapping(value = "/users")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController implements GenericController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Save", description = "Register new user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registered with success", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid token", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    public ResponseEntity<Void> saveUser(@RequestBody @Valid UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        userService.saveUser(user);
        URI url = generateHeaderLocationUser(user.getLogin());
        return ResponseEntity.created(url).build();
    }
}
