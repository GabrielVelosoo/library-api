package com.github.gabrielvelosoo.libraryapi.repositories.specs;

import com.github.gabrielvelosoo.libraryapi.models.Author;
import org.springframework.data.jpa.domain.Specification;

public class AuthorSpecs {

    public static Specification<Author> nameLike(String name) {
        return (root, query, cb) -> cb.like( cb.upper( root.get("name") ), "%" + name.toUpperCase() + "%");
    }

    public static Specification<Author> nationalityLike(String nationality) {
        return (root, query, cb) -> cb.like( cb.upper( root.get("nationality") ), "%" + nationality.toUpperCase() + "%");
    }
}
