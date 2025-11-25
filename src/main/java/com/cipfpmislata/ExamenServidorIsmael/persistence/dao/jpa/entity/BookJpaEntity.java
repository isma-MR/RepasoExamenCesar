package com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "book")
public class BookJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isbn;
    @Column(name = "title_es")
    private String titleEs;
    @Column(name = "price")
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private PublisherJpaEntity publisher;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookAuthorJpaEntity> bookAuthors = new ArrayList<>();

    public BookJpaEntity() {
    }

    public BookJpaEntity(Long id, String isbn, String titleEs,
            BigDecimal price, PublisherJpaEntity publisher, List<AuthorJpaEntity> authors) {
        this.id = id;
        this.isbn = isbn;
        this.titleEs = titleEs;
        this.price = price;
        this.publisher = publisher;
        setAuthors(authors);
    }

    public List<BookAuthorJpaEntity> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(List<BookAuthorJpaEntity> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }

    public List<AuthorJpaEntity> getAuthors() {
        return bookAuthors.stream().map(BookAuthorJpaEntity::getAuthor).collect(Collectors.toList());
    }

    public void setAuthors(List<AuthorJpaEntity> authors) {
        this.bookAuthors.clear();
        for (AuthorJpaEntity author : authors) {
            BookAuthorJpaEntity bookAuthor = new BookAuthorJpaEntity(this, author);
            this.bookAuthors.add(bookAuthor);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitleEs() {
        return titleEs;
    }

    public void setTitleEs(String titleEs) {
        this.titleEs = titleEs;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public PublisherJpaEntity getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherJpaEntity publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BookJpaEntity other)) {
            return false;
        }
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

}
