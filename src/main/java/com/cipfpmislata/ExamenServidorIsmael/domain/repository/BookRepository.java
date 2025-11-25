package com.cipfpmislata.ExamenServidorIsmael.domain.repository;

import com.cipfpmislata.ExamenServidorIsmael.domain.model.Page;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.BookDto;

import java.util.Optional;

public interface BookRepository {
    Page<BookDto> findAll(int page, int size);
    Optional<BookDto> findByIsbn(String isbn);
    BookDto save(BookDto bookDto);
    void deleteByIsbn(String isbn);
}
