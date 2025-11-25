package com.cipfpmislata.ExamenServidorIsmael.persistence.dao;

import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity.BookJpaEntity;

import java.util.List;
import java.util.Optional;

public interface BookJpaDao {
    Optional<BookJpaEntity> findBookById(Long id);

    Optional<BookJpaEntity> findBookByIsbn(String isbn);

    List<BookJpaEntity> findAll(int page, int size);

    BookJpaEntity insert(BookJpaEntity bookDto);

    BookJpaEntity update(BookJpaEntity bookDto);

    void deleteByIsbn(String isbn);

    long count();

}
