package com.cipfpmislata.ExamenServidorIsmael.domain.repository;

import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Optional<AuthorDto> findAuthorById(Long id);
    Optional<AuthorDto> findAuthorBySlug(String slug);
    List<AuthorDto> findAll();
    AuthorDto save(AuthorDto authorDto);
    void delete(Long id);
}
