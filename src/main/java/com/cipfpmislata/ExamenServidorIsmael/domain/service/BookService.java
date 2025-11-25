package com.cipfpmislata.ExamenServidorIsmael.domain.service;

import com.cipfpmislata.ExamenServidorIsmael.domain.model.Page;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.BookDto;

import java.util.Optional;

public interface BookService {
    Page<BookDto> findAll(int page, int size);
    Optional<BookDto> findByIsbn(String isbn);
    BookDto create(BookDto bookDto);
    BookDto update(BookDto bookDto);
    void deleteByIsbn(String isbn);
}
