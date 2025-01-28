package com.github.gabrielvelosoo.libraryapi.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Library API - Veloso",
                version = "1.0",
                description = "API for managing authors and books with validations, advanced security, social login with Google and authorization server OAuth2 with JWT",
                contact = @Contact(
                        name = "Gabriel Veloso",
                        email = "gaabrielvelooso@gmail.com",
                        url = "https://github.com/GabrielVelosoo"
                )
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )
        },
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "This authentication scheme uses JWT tokens to validate requests. After obtaining the token, add it to the 'Value' field or include it in the request header as 'Authorization: Bearer {your_token}' when testing with specific tools. Tokens are required for all protected endpoints.",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfiguration {
}
