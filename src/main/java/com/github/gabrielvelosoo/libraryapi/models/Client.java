package com.github.gabrielvelosoo.libraryapi.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_client")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Client implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "client_id", length = 150, nullable = false)
    private String clientId;

    @Column(name = "client_secret", length = 400, nullable = false)
    private String clientSecret;

    @Column(name = "redirect_uri", length = 200, nullable = false)
    private String redirectURI;

    @Column(name = "scope", length = 50)
    private String scope;

    @CreatedDate
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}
