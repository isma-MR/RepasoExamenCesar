package com.cipfpmislata.ExamenServidorIsmael.domain.service;


import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Optional<AuthorDto> findAuthorById(Long id);
    Optional<AuthorDto> findAuthorBySlug(String slug);
    List<AuthorDto> findAll();
    AuthorDto create(AuthorDto authorDto);
    AuthorDto update(AuthorDto authorDto);
    void delete(Long id);
}
