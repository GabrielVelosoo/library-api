package com.github.gabrielvelosoo.libraryapi.repositories.specs;

import com.github.gabrielvelosoo.libraryapi.enums.BookGenre;
import com.github.gabrielvelosoo.libraryapi.models.Book;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecs {

    public static Specification<Book> isbnEqual(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Book> titleLike(String title) {
        return (root, query, cb) -> cb.like( cb.upper( root.get("title") ),
                "%" + title.toUpperCase() + "%");
    }

    public static Specification<Book> authorNameLike(String authorName) {
        return (root, query, cb) -> {
            Join<Object, Object> joinAuthor = root.join("author", JoinType.INNER);
            return cb.like( cb.upper( joinAuthor.get("name") ), "%" + authorName.toUpperCase() + "%" );
        };
    }

    public static Specification<Book> genreEqual(BookGenre genre) {
        return (root, query, cb) -> cb.equal(root.get("genre"), genre);
    }

    public static Specification<Book> postYearEqual(Integer postYear) {
        return (root, query, cb) ->
                cb.equal( cb.function("to_char", String.class, root.get("postDate"), cb.literal("YYYY")),
                        postYear.toString() );
    }
}
