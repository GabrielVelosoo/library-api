package com.github.gabrielvelosoo.libraryapi.repositories;

import com.github.gabrielvelosoo.libraryapi.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Optional<Author> findByNameAndBirthDateAndNationality(String name, LocalDate birthDate, String nationality);
}
