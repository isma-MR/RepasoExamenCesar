package com.cipfpmislata.ExamenServidorIsmael.persistence.dao;

import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity.AuthorJpaEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorJpaDao {
    Optional<AuthorJpaEntity> findAuthorById(Long id);

    Optional<AuthorJpaEntity> findAuthorBySlug(String slug);

    List<AuthorJpaEntity> findAll();

    AuthorJpaEntity insert(AuthorJpaEntity authorDto);

    AuthorJpaEntity update(AuthorJpaEntity authorDto);

    void delete(Long id);

}
