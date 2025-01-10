package com.github.gabrielvelosoo.libraryapi.models;

import com.github.gabrielvelosoo.libraryapi.enums.BookGenre;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_book", schema = "public")
@EntityListeners(AuditingEntityListener.class)
public class Book implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false, unique = true)
    private String isbn;

    @Column(name = "title", length = 150, nullable = false)
    private String title;

    @Column(name = "dt_post", nullable = false)
    private LocalDate postDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", length = 30)
    private BookGenre genre;

    @Column(name = "price", precision = 18, scale = 2)
    private BigDecimal price;

    @CreatedDate
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @LastModifiedDate
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "user_id")
    private UUID userId;

    @ManyToOne(
            //cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "id_author")
    private Author author;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public BookGenre getGenre() {
        return genre;
    }

    public void setGenre(BookGenre genre) {
        this.genre = genre;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", postDate=" + postDate +
                ", genre=" + genre +
                ", price=" + price +
                '}';
    }
}
