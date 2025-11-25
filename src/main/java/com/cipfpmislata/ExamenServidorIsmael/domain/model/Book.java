package com.cipfpmislata.ExamenServidorIsmael.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private final Long id;
    private final String isbn;
    private final String titleEs;
    private final BigDecimal price;
    private Publisher publisher;
    private List<Author> authors;

    public Book(Long id, String isbn, String titleEs, BigDecimal price, Publisher publisher, List<Author> authors) {
        this.id = id;
        this.isbn = isbn;
        this.titleEs = titleEs;
        this.price = price;
        this.publisher = publisher;
        this.authors = (authors.isEmpty()) ? new ArrayList<>() : new ArrayList<>(authors);
    }

    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitleEs() {
        return titleEs;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
