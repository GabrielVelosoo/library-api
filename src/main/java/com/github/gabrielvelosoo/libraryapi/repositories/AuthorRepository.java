package com.github.gabrielvelosoo.libraryapi.repositories;

import com.github.gabrielvelosoo.libraryapi.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

    List<Author> findByName(String name);

    List<Author> findByNationality(String nationality);

    List<Author> findByNameAndNationality(String name, String nationality);
}
